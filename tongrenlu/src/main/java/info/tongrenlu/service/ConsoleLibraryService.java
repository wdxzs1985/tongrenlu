package info.tongrenlu.service;

import info.tongrenlu.domain.AuthFileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.domain.UserProfileBean;
import info.tongrenlu.manager.ArticleManager;
import info.tongrenlu.manager.FileManager;
import info.tongrenlu.manager.LibraryManager;
import info.tongrenlu.support.PaginateSupport;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ConsoleLibraryService {

    @Autowired
    private final ArticleManager articleManager = null;
    @Autowired
    private LibraryManager libraryManager = null;
    @Autowired
    private FileManager fileManager = null;

    public void searchLibrary(final PaginateSupport<MusicBean> paginate) {
        final int itemCount = this.libraryManager.countMusic(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<MusicBean> items = this.libraryManager.searchMusic(paginate.getParams());
        paginate.setItems(items);
    }

    public boolean updateStatus(final MusicBean musicBean,
                                final UserBean userBean) {
        if (musicBean != null) {
            if (!this.libraryManager.isOwner(userBean, musicBean, null)) {
                this.libraryManager.addToLibrary(userBean, musicBean, 1);
                return true;
            } else {
                this.libraryManager.updateStatus(userBean, musicBean, 1);
                return true;
            }
        }
        return false;
    }

    public List<AuthFileBean> getAuthFiles(final UserBean userBean) {
        return this.libraryManager.fetchAuthFileList(userBean);
    }

    public AuthFileBean saveAuthFile(final MultipartFile upload,
                                     final AuthFileBean authFileBean,
                                     final Map<String, Object> fileModel,
                                     final Locale locale) {
        if (this.validateForAuthFile(upload, fileModel, locale)) {
            this.libraryManager.insertAuthFile(authFileBean);
            this.saveFile(authFileBean, upload);
            this.convertImage(authFileBean);
            return authFileBean;
        }
        return null;
    }

    private boolean validateForAuthFile(final MultipartFile upload,
                                        final Map<String, Object> fileModel,
                                        final Locale locale) {
        boolean isValid = true;
        if (!this.fileManager.validateContentType(upload.getContentType(),
                                                  new String[] { "image/jpeg" },
                                                  "error",
                                                  fileModel,
                                                  locale)) {
            isValid = false;
        }
        return isValid;
    }

    private void saveFile(final AuthFileBean authFileBean,
                          final MultipartFile fileItem) {
        final String dirId = String.format("u%d/auth",
                                           authFileBean.getUserBean().getId());
        final String name = String.format("%s.jpg", authFileBean.getChecksum());
        final File file = this.fileManager.getFile(dirId, name);
        try {
            FileUtils.copyInputStreamToFile(fileItem.getInputStream(), file);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void convertImage(final AuthFileBean authFileBean) {
        final String dirId = String.format("u%d/auth",
                                           authFileBean.getUserBean().getId());
        final String name = String.format("%s.jpg", authFileBean.getChecksum());
        final File inputFile = this.fileManager.getFile(dirId, name);

        for (final int size : FileManager.COVER_SIZE_ARRAY) {
            final String outputName = String.format("%s_%d.jpg",
                                                    authFileBean.getChecksum(),
                                                    size);
            final File outputFile = this.fileManager.getFile(dirId, outputName);
            this.fileManager.convertCover(inputFile, outputFile, size);
        }
        for (final int size : FileManager.IMAGE_SIZE_ARRAY) {
            final String outputName = String.format("%s_%d.jpg",
                                                    authFileBean.getChecksum(),
                                                    size);
            final File outputFile = this.fileManager.getFile(dirId, outputName);
            this.fileManager.convertImage(inputFile, outputFile, size);
        }
    }

    public AuthFileBean check(final Integer authFileId) {
        final AuthFileBean authFileBean = this.libraryManager.getAuthFile(authFileId);
        if (authFileBean != null) {
            authFileBean.setStatus(1);
            this.libraryManager.updateStatus(authFileId, 1);
        }
        return authFileBean;
    }

    public boolean delete(final Integer authFileId) {
        final AuthFileBean authFileBean = this.libraryManager.getAuthFile(authFileId);
        if (authFileBean != null) {
            this.libraryManager.deleteAuthFile(authFileBean);
            this.deleteFile(authFileBean);
            return true;
        }
        return false;
    }

    public boolean delete(final Integer authFileId, final UserBean loginUser) {
        final AuthFileBean authFileBean = this.libraryManager.getAuthFile(authFileId);
        if (authFileBean == null) {
            return false;
        } else if (!loginUser.equals(authFileBean.getUserBean())) {
            return false;
        }
        this.libraryManager.deleteAuthFile(authFileBean);
        this.deleteFile(authFileBean);
        return true;
    }

    private void deleteFile(final AuthFileBean authFileBean) {
        final String dirId = String.format("u%d/auth",
                                           authFileBean.getUserBean().getId());
        final String name = String.format("f%d.jpg", authFileBean.getId());
        final File file = this.fileManager.getFile(dirId, name);
        FileUtils.deleteQuietly(file);

        for (final int size : FileManager.COVER_SIZE_ARRAY) {
            final String outputName = String.format("f%d_%d.jpg",
                                                    authFileBean.getId(),
                                                    size);
            final File outputFile = this.fileManager.getFile(dirId, outputName);
            FileUtils.deleteQuietly(outputFile);
        }
        for (final int size : FileManager.IMAGE_SIZE_ARRAY) {
            final String outputName = String.format("f%d_%d.jpg",
                                                    authFileBean.getId(),
                                                    size);
            final File outputFile = this.fileManager.getFile(dirId, outputName);
            FileUtils.deleteQuietly(outputFile);
        }
    }

    public int countAuthUser() {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", 0);
        return this.libraryManager.countUser(params);
    }

    public void searchUser(final PaginateSupport<UserProfileBean> paginate) {
        final int itemCount = this.libraryManager.countUser(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<UserProfileBean> items = this.libraryManager.searchUser(paginate.getParams());
        paginate.setItems(items);
    }

}
