package info.tongrenlu.service.dao;

import info.tongrenlu.constants.ConstantsFactoryBean;
import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.persistence.MFileMapper;
import info.tongrenlu.persistence.MTrackMapper;
import info.tongrenlu.support.PaginateSupport;
import info.tongrenlu.support.SequenceSupport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileDao extends SequenceSupport {

    public static final int[] COVER_SIZE_ARRAY = new int[] { 60,
                                                            90,
                                                            120,
                                                            180,
                                                            400 };
    public static final int[] COMIC_SIZE_ARRAY = new int[] { 120,
                                                            300,
                                                            800,
                                                            1200,
                                                            1600 };
    public static final String JPG = "jpg";
    public static final String MP3 = "mp3";

    @Autowired
    protected ConstantsFactoryBean constantsBean = null;
    @Autowired
    private MFileMapper fileMapper = null;
    @Autowired
    private MTrackMapper trackMapper = null;

    public FileBean createImageFileInfo(final String articleId,
                                        final MultipartFile fileItem) {
        final FileBean fileBean = this.createFileInfo(articleId,
                                                      fileItem,
                                                      FileDao.JPG);
        final File inputFile = this.getJpgFile(fileBean.getArticleId(),
                                               fileBean.getFileId());
        this.saveUpload(fileItem, inputFile);
        this.convertThumbnail(fileBean, inputFile);
        return fileBean;
    }

    public FileBean createMusicTrackInfo(final String articleId,
                                         final MultipartFile fileItem) {
        final FileBean fileBean = this.createFileInfo(articleId,
                                                      fileItem,
                                                      FileDao.MP3);
        final File inputFile = this.getMp3File(fileBean.getArticleId(),
                                               fileBean.getFileId());
        this.saveUpload(fileItem, inputFile);
        this.saveTrackInfo(inputFile, fileBean);
        return fileBean;
    }

    private FileBean createFileInfo(final String articleId,
                                    final MultipartFile fileItem,
                                    final String extension) {
        final String name = fileItem.getOriginalFilename();
        final FileBean fileBean = new FileBean();
        fileBean.setFileId(this.getNextId());
        fileBean.setArticleId(articleId);
        fileBean.setName(name);
        fileBean.setExtension(extension);
        fileBean.setSize(fileItem.getSize());
        this.fileMapper.insertFile(fileBean);
        return fileBean;
    }

    private void saveUpload(final MultipartFile fileItem, final File file) {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = fileItem.getInputStream();
            output = FileUtils.openOutputStream(file);
            IOUtils.copy(input, output);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
    }

    public void convertThumbnail(final FileBean fileBean, final File inputFile) {
        for (final int size : FileDao.COMIC_SIZE_ARRAY) {
            final String name = fileBean.getFileId() + "_" + size;
            final File outputFile = this.getJpgFile(fileBean.getArticleId(),
                                                    name);
            this.convertComic(inputFile.getAbsolutePath(),
                              outputFile.getAbsolutePath(),
                              size);
        }
    }

    private void saveTrackInfo(final File file, final FileBean fileBean) {
        final TrackBean trackBean = new TrackBean();
        trackBean.setFileBean(fileBean);
        final String songTitle = FilenameUtils.getBaseName(fileBean.getName());
        trackBean.setSongTitle(StringUtils.left(songTitle, 255));
        this.trackMapper.insertTrack(trackBean);
    }

    public File getJpgFile(final String dirId, final String name) {
        return this.getFile(dirId, name, FileDao.JPG);
    }

    public File getMp3File(final String dirId, final String name) {
        return this.getFile(dirId, name, FileDao.MP3);
    }

    public File getFile(final String dirId, final String name, final String ext) {
        final String rootPath = this.constantsBean.getInputPath();
        return new File(rootPath + "/" + dirId + "/" + name + "." + ext);
    }

    public void deleteJpgFile(final FileBean fileBean) {
        this.fileMapper.deleteFileInfo(fileBean);
        final File originalFile = this.getJpgFile(fileBean.getArticleId(),
                                                  fileBean.getFileId());
        FileUtils.deleteQuietly(originalFile);
        for (final int size : FileDao.COMIC_SIZE_ARRAY) {
            final File thumbnail = this.getJpgFile(fileBean.getArticleId(),
                                                   fileBean.getFileId() + "_"
                                                           + size);
            FileUtils.deleteQuietly(thumbnail);
        }
    }

    public void deleteMp3File(final FileBean fileBean) {
        this.fileMapper.deleteFileInfo(fileBean);
        this.deleteTrack(fileBean.getFileId());
        final File mp3File = this.getMp3File(fileBean.getArticleId(),
                                             fileBean.getFileId());
        FileUtils.deleteQuietly(mp3File);
    }

    private void deleteTrack(final String fileId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("fileId", fileId);
        this.trackMapper.deleteTrack(param);
    }

    public List<FileBean> getArticleFiles(final String articleId,
                                          final String extension) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        param.put("extension", extension);
        final List<FileBean> files = this.fileMapper.getArticleFiles(param);
        return files;
    }

    public void sortFiles(final String[] fileIdArray) {
        int orderNo = 1;
        for (final String fileId : fileIdArray) {
            final FileBean fileInfo = new FileBean();
            fileInfo.setFileId(fileId);
            fileInfo.setOrderNo(orderNo);
            this.fileMapper.updateFileOrder(fileInfo);
            orderNo++;
        }
    }

    public void saveCoverFile(final ArticleBean articleBean,
                              final MultipartFile fileItem) {
        final String id = articleBean.getArticleId();
        final String prefix = "cover";
        final File inputFile = this.getJpgFile(id, prefix);
        if (fileItem != null && !fileItem.isEmpty()) {
            this.saveUpload(fileItem, inputFile);
            this.convertCover(id, prefix);
        } else {
            if (!inputFile.exists()) {
                for (final int size : FileDao.COVER_SIZE_ARRAY) {
                    this.copyDefaultCover(id, prefix + "_", size);
                }
            }
        }
    }

    public void convertCover(final String id, final String prefix) {
        final File inputFile = this.getJpgFile(id, prefix);
        if (!inputFile.exists()) {
            for (final int size : FileDao.COVER_SIZE_ARRAY) {
                this.copyDefaultCover(id, prefix + "_", size);
            }
        } else {
            for (final int size : FileDao.COVER_SIZE_ARRAY) {
                final File outputFile = this.getJpgFile(id, prefix + "_" + size);
                this.convertCover(inputFile.getAbsolutePath(),
                                  outputFile.getAbsolutePath(),
                                  size);
            }
        }
    }

    public void saveAvatarFile(final UserBean userBean,
                               final MultipartFile fileItem) {
        final String id = userBean.getUserId();
        final String prefix = "avatar";
        final File inputFile = this.getJpgFile(id, prefix);
        if (fileItem != null && !fileItem.isEmpty()) {
            this.saveUpload(fileItem, inputFile);
            this.convertCover(id, prefix);
        } else {
            if (!inputFile.exists()) {
                for (final int size : FileDao.COVER_SIZE_ARRAY) {
                    this.copyDefaultCover(id, prefix + "_", size);
                }
            }
        }
    }

    private void convertComic(final String input,
                              final String output,
                              final int size) {
        final ConvertCmd cmd = new ConvertCmd();
        // cmd.setAsyncMode(true);
        cmd.setSearchPath(this.constantsBean.getConvertPath());
        // create the operation, add images and operators/options
        final IMOperation op = new IMOperation();
        op.density(72);
        op.quality(75d);
        op.addImage(input);
        op.thumbnail(size, size, '>');
        op.addImage(output);
        // execute the operation
        try {
            cmd.run(op);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        } catch (final IM4JavaException e) {
            throw new RuntimeException(e);
        }
    }

    private void convertCover(final String input,
                              final String output,
                              final int size) {
        final ConvertCmd cmd = new ConvertCmd();
        // cmd.setAsyncMode(true);
        cmd.setSearchPath(this.constantsBean.getConvertPath());
        // create the operation, add images and operators/options
        final IMOperation op = new IMOperation();
        op.density(72);
        op.addImage(input);
        op.thumbnail(size, null, '>').crop(size, size, 0, 0);
        op.addImage(output);
        // execute the operation
        try {
            cmd.run(op);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        } catch (final IM4JavaException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TrackBean> getMusicTracks(final String articleId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        return this.trackMapper.getTrackList(param);
    }

    public FileBean getFileInfo(final String fileId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("fileId", fileId);
        return this.fileMapper.getFileInfo(param);
    }

    public void updateTracks(final String[] fileIdArray,
                             final String[] songTitleArray,
                             final String[] leadArtistArray,
                             final String[] originalTitleArray,
                             final MusicBean music) {

        for (int i = 0; i < fileIdArray.length; i++) {
            final String fileId = fileIdArray[i];
            final int orderNo = i + 1;
            final FileBean fileBean = new FileBean();
            fileBean.setFileId(fileId);
            fileBean.setOrderNo(orderNo);
            this.fileMapper.updateFileOrder(fileBean);

            final TrackBean trackBean = new TrackBean();
            trackBean.setFileBean(fileBean);
            trackBean.setSongTitle(songTitleArray[i]);

            if (ArrayUtils.isNotEmpty(leadArtistArray)) {
                trackBean.setLeadArtist(leadArtistArray[i]);
            }

            if (ArrayUtils.isNotEmpty(originalTitleArray)) {
                trackBean.setOriginalTitle(originalTitleArray[i]);
            }
            trackBean.setTrackNumber(orderNo);
            this.trackMapper.updateTrackBean(trackBean);
        }
    }

    public FileBean createScreenshotInfo(final String articleId,
                                         final MultipartFile fileItem) {
        final FileBean fileBean = this.createFileInfo(articleId,
                                                      fileItem,
                                                      FileDao.JPG);
        final String fileId = fileBean.getFileId();
        final File inputFile = this.getJpgFile(articleId, fileId);
        this.saveUpload(fileItem, inputFile);
        for (final int size : FileDao.COMIC_SIZE_ARRAY) {
            final String name = fileId + "_" + size;
            final File outputFile = this.getJpgFile(articleId, name);
            this.convertScreenshot(inputFile.getAbsolutePath(),
                                   outputFile.getAbsolutePath(),
                                   size);
        }
        return fileBean;
    }

    private void convertScreenshot(final String input,
                                   final String output,
                                   final int size) {
        final ConvertCmd cmd = new ConvertCmd();
        // cmd.setAsyncMode(true);
        cmd.setSearchPath(this.constantsBean.getConvertPath());
        // create the operation, add images and operators/options
        final IMOperation op = new IMOperation();
        op.density(72);
        op.quality(75d);
        op.addImage(input);
        op.thumbnail(size, null, '>');
        op.addImage(output);
        // execute the operation
        try {
            cmd.run(op);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        } catch (final IM4JavaException e) {
            throw new RuntimeException(e);
        }
    }

    public void copyDefaultCover(final String id,
                                 final String prefix,
                                 final int size) {
        final File src = this.getJpgFile("default", "default_" + size);
        final File dest = this.getJpgFile(id, prefix + size);

        try {
            FileUtils.copyFile(src, dest);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> prepareJpgFileBeanModel(final FileBean fileBean,
                                                       final HttpServletRequest request) {

        final String serverName = request.getServerName();
        final boolean isLocal = StringUtils.contains(serverName, "127.0.0.1") || StringUtils.contains(serverName,
                                                                                                      "192.168.11.");
        final String FILE_PATH = isLocal ? "http://192.168.11.9/resource"
                                        : "/resource";

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", fileBean.getName());
        model.put("size", fileBean.getSize());
        model.put("url", FILE_PATH + "/"
                         + fileBean.getArticleId()
                         + "/"
                         + fileBean.getFileId()
                         + "_800.jpg");
        model.put("thumbnailUrl", FILE_PATH + "/"
                                  + fileBean.getArticleId()
                                  + "/"
                                  + fileBean.getFileId()
                                  + "_120.jpg");
        model.put("deleteUrl", request.getContextPath() + "/admin/file/"
                               + fileBean.getFileId()
                               + "/delete");
        model.put("deleteType", "GET");
        return model;
    }

    public Map<String, Object> prepareMp3FileBeanModel(final FileBean fileBean,
                                                       final HttpServletRequest request) {
        final String serverName = request.getServerName();
        final boolean isLocal = StringUtils.contains(serverName, "127.0.0.1") || StringUtils.contains(serverName,
                                                                                                      "192.168.11.");
        final String FILE_PATH = isLocal ? "http://192.168.11.9/resource"
                                        : "/resource";

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", fileBean.getName());
        model.put("size", fileBean.getSize());
        model.put("url", FILE_PATH + "/"
                         + fileBean.getArticleId()
                         + "/"
                         + fileBean.getFileId()
                         + ".mp3");
        model.put("thumbnailUrl", FILE_PATH + "/"
                                  + fileBean.getArticleId()
                                  + "/cover_60.jpg");
        model.put("deleteUrl", request.getContextPath() + "/admin/file/"
                               + fileBean.getFileId()
                               + "/delete");
        model.put("deleteType", "GET");
        return model;
    }

    public PaginateSupport searchMusicTracks(final String searchQuery,
                                             final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("searchQuery", searchQuery);
        final int itemCount = this.trackMapper.getTrackCount(param);
        paginate.setItemCount(itemCount);
        paginate.compute();
        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        final List<TrackBean> items = this.trackMapper.getTrackList(param);
        paginate.setItems(items);
        return paginate;
    }
}
