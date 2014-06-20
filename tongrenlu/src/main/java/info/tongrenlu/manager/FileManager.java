package info.tongrenlu.manager;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.support.PaginateSupport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManager {

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

    public static final String CAT_USER = "user";
    public static final String CAT_COMIC = "comic";
    public static final String CAT_MUSIC = "music";

    public static final String COVER = "cover";

    public static final String JPG = "jpg";
    public static final String MP3 = "mp3";

    @Value("${file.inputPathWindows}")
    private String inputPathWindows = null;

    @Value("${file.inputPathLinux}")
    private String inputPathLinux = null;

    @Value("${file.outputPathWindows}")
    private String outputPathWindows = null;

    @Value("${file.outputPathLinux}")
    private String outputPathLinux = null;

    @Value("${file.convertPathWindows}")
    private String convertPathWindows = null;

    @Value("${file.convertPathLinux}")
    private String convertPathLinux = null;

    public String getInputPath() {
        return SystemUtils.IS_OS_WINDOWS ? this.inputPathWindows
                : this.inputPathLinux;
    }

    public String getOutputPath() {
        return SystemUtils.IS_OS_WINDOWS ? this.outputPathWindows
                : this.outputPathLinux;
    }

    public String getConvertPath() {
        return SystemUtils.IS_OS_WINDOWS ? this.convertPathWindows
                : this.convertPathLinux;
    }

    // @Autowired
    // private MFileMapper fileMapper = null;
    // @Autowired
    // private MTrackMapper trackMapper = null;

    public FileBean createImageFileInfo(final String articleId,
                                        final MultipartFile fileItem) {
        final FileBean fileBean = this.createFileInfo(articleId,
                                                      fileItem,
                                                      FileManager.JPG);
        // final File inputFile = this.getJpgFile(fileBean.getArticleId(),
        // fileBean.getFileId());
        // this.saveUpload(fileItem, inputFile);
        // this.convertThumbnail(fileBean, inputFile);
        return fileBean;
    }

    public FileBean createMusicTrackInfo(final String articleId,
                                         final MultipartFile fileItem) {
        final FileBean fileBean = this.createFileInfo(articleId,
                                                      fileItem,
                                                      FileManager.MP3);
        // final File inputFile = this.getMp3File(fileBean.getArticleId(),
        // fileBean.getFileId());
        // this.saveUpload(fileItem, inputFile);
        // this.saveTrackInfo(inputFile, fileBean);
        return fileBean;
    }

    private FileBean createFileInfo(final String articleId,
                                    final MultipartFile fileItem,
                                    final String extension) {
        final String name = fileItem.getOriginalFilename();
        final FileBean fileBean = new FileBean();
        // fileBean.setArticleId(articleId);
        fileBean.setName(name);
        fileBean.setExtension(extension);
        // fileBean.setSize(fileItem.getSize());
        // this.fileMapper.insertFile(fileBean);
        return fileBean;
    }

    protected void saveUpload(final MultipartFile fileItem, final File file) {
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
        for (final int size : FileManager.COMIC_SIZE_ARRAY) {
            // final String name = fileBean.getId() + "_" + size;
            // final File outputFile = this.getJpgFile(fileBean.getArticleId(),
            // name);
            // this.convertComic(inputFile.getAbsolutePath(),
            // outputFile.getAbsolutePath(),
            // size);
        }
    }

    protected void saveTrackInfo(final File file, final FileBean fileBean) {
        final TrackBean trackBean = new TrackBean();
        trackBean.setFileBean(fileBean);
        final String track = FilenameUtils.getBaseName(fileBean.getName());
        trackBean.setTrack(StringUtils.left(track, 255));
        // this.trackMapper.insertTrack(trackBean);
    }

    public File getJpgFile(final String category,
                           final String dirId,
                           final String name) {
        return this.getFile(category, dirId, name, FileManager.JPG);
    }

    public File getMp3File(final String category,
                           final String dirId,
                           final String name) {
        return this.getFile(category, dirId, name, FileManager.MP3);
    }

    public File getFile(final String category,
                        final String dirId,
                        final String name,
                        final String ext) {
        final String rootPath = this.getInputPath();
        return new File(rootPath + "/"
                + category
                + "/"
                + dirId
                + "/"
                + name
                + "."
                + ext);
    }

    public void deleteJpgFile(final FileBean fileBean) {
        // this.fileMapper.deleteFileInfo(fileBean);
        // final File originalFile = this.getJpgFile(fileBean.getArticleId(),
        // fileBean.getFileId());
        // FileUtils.deleteQuietly(originalFile);
        for (final int size : FileManager.COMIC_SIZE_ARRAY) {
            // final File thumbnail = this.getJpgFile(fileBean.getArticleId(),
            // fileBean.getFileId() + "_"
            // + size);
            // FileUtils.deleteQuietly(thumbnail);
        }
    }

    public void deleteMp3File(final FileBean fileBean) {
        // this.fileMapper.deleteFileInfo(fileBean);
        // this.deleteTrack(fileBean.getFileId());
        // final File mp3File = this.getMp3File(fileBean.getArticleId(),
        // fileBean.getFileId());
        // FileUtils.deleteQuietly(mp3File);
    }

    protected void deleteTrack(final String fileId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("fileId", fileId);
        // this.trackMapper.deleteTrack(param);
    }

    public List<FileBean> getArticleFiles(final String articleId,
                                          final String extension) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        param.put("extension", extension);
        // final List<FileBean> files = this.fileMapper.getArticleFiles(param);
        // return files;
        return Collections.emptyList();
    }

    public void sortFiles(final String[] fileIdArray) {
        int orderNo = 1;
        for (final String fileId : fileIdArray) {
            final FileBean fileInfo = new FileBean();
            // fileInfo.setId(fileId);
            fileInfo.setOrderNo(orderNo);
            // this.fileMapper.updateFileOrder(fileInfo);
            orderNo++;
        }
    }

    public void saveCoverFile(final ArticleBean articleBean,
                              final MultipartFile fileItem) {
        // final String id = articleBean.getArticleId();
        // final String prefix = "cover";
        // final File inputFile = this.getJpgFile(id, prefix);
        // if (fileItem != null && !fileItem.isEmpty()) {
        // this.saveUpload(fileItem, inputFile);
        // this.convertCover(id, prefix);
        // } else {
        // if (!inputFile.exists()) {
        // for (final int size : FileDao.COVER_SIZE_ARRAY) {
        // this.copyDefaultCover(id, prefix + "_", size);
        // }
        // }
        // }
    }

    public void convertCover(final File inputFile,
                             final String category,
                             final String id) {
        for (final int size : FileManager.COVER_SIZE_ARRAY) {
            final File outputFile = this.getJpgFile(category,
                                                    id,
                                                    FileManager.COVER + "_"
                                                            + size);
            this.convertCover(inputFile, outputFile, size);
        }
    }

    public void saveAvatarFile(final UserBean userBean,
                               final MultipartFile fileItem) {
        final String userId = String.format("%s_%d",
                                            FileManager.CAT_USER,
                                            userBean.getId());
        final File inputFile = this.getJpgFile(FileManager.CAT_USER,
                                               userId,
                                               FileManager.COVER);
        if (fileItem != null && !fileItem.isEmpty()) {
            this.saveUpload(fileItem, inputFile);
            this.convertCover(inputFile, FileManager.CAT_USER, userId);
        } else {
            if (!inputFile.exists()) {
                for (final int size : FileManager.COVER_SIZE_ARRAY) {
                    this.copyDefaultCover(FileManager.CAT_USER,
                                          userId,
                                          FileManager.COVER,
                                          size);
                }
            }
        }
    }

    protected void convertComic(final String input,
                                final String output,
                                final int size) {
        final ConvertCmd cmd = new ConvertCmd();
        // cmd.setAsyncMode(true);
        cmd.setSearchPath(this.getConvertPath());
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

    protected void convertCover(final File input,
                                final File output,
                                final int size) {
        final ConvertCmd cmd = new ConvertCmd();
        // cmd.setAsyncMode(true);
        cmd.setSearchPath(this.getConvertPath());
        // create the operation, add images and operators/options
        final IMOperation op = new IMOperation();
        op.density(72);
        op.addImage(input.getAbsolutePath());
        op.thumbnail(size, size, '>').crop(size, size, 0, 0);
        op.addImage(output.getAbsolutePath());
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
        // return this.trackMapper.getTrackList(param);
        return Collections.emptyList();
    }

    public FileBean getFileInfo(final String fileId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("fileId", fileId);
        // return this.fileMapper.getFileInfo(param);
        return null;
    }

    public void updateTracks(final String[] fileIdArray,
                             final String[] songTitleArray,
                             final String[] leadArtistArray,
                             final String[] originalTitleArray,
                             final MusicBean music) {

        for (int i = 0; i < fileIdArray.length; i++) {
            final int orderNo = i + 1;
            final FileBean fileBean = new FileBean();
            // fileBean.setFileId(fileId);
            fileBean.setOrderNo(orderNo);
            // this.fileMapper.updateFileOrder(fileBean);

            final TrackBean trackBean = new TrackBean();
            trackBean.setFileBean(fileBean);
            trackBean.setTrack(songTitleArray[i]);

            if (ArrayUtils.isNotEmpty(leadArtistArray)) {
                trackBean.setArtist(leadArtistArray[i]);
            }

            if (ArrayUtils.isNotEmpty(originalTitleArray)) {
                trackBean.setOriginalTitle(originalTitleArray[i]);
            }
            trackBean.setTrackNumber(orderNo);
            // this.trackMapper.updateTrackBean(trackBean);
        }
    }

    public FileBean createScreenshotInfo(final String articleId,
                                         final MultipartFile fileItem) {
        final FileBean fileBean = this.createFileInfo(articleId,
                                                      fileItem,
                                                      FileManager.JPG);
        // final String fileId = fileBean.getFileId();
        // final File inputFile = this.getJpgFile(articleId, fileId);
        // this.saveUpload(fileItem, inputFile);
        // for (final int size : FileDao.COMIC_SIZE_ARRAY) {
        // final String name = fileId + "_" + size;
        // final File outputFile = this.getJpgFile(articleId, name);
        // this.convertScreenshot(inputFile.getAbsolutePath(),
        // outputFile.getAbsolutePath(),
        // size);
        // }
        return fileBean;
    }

    protected void convertScreenshot(final String input,
                                     final String output,
                                     final int size) {
        final ConvertCmd cmd = new ConvertCmd();
        // cmd.setAsyncMode(true);
        cmd.setSearchPath(this.getConvertPath());
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

    public void copyDefaultCover(final String category,
                                 final String id,
                                 final String type,
                                 final int size) {
        final String name = type + "_" + size;
        final File src = this.getJpgFile(category, "default", name);
        final File dest = this.getJpgFile(category, id, name);

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
        // model.put("size", fileBean.getSize());
        model.put("url", FILE_PATH + "/"
                + fileBean.getArticleBean().getId()
                + "/"
                + fileBean.getId()
                + "_800.jpg");
        model.put("thumbnailUrl", FILE_PATH + "/"
                + fileBean.getArticleBean().getId()
                + "/"
                + fileBean.getId()
                + "_120.jpg");
        model.put("deleteUrl", request.getContextPath() + "/admin/file/"
                + fileBean.getId()
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
        // model.put("size", fileBean.getSize());
        model.put("url", FILE_PATH + "/"
                + fileBean.getArticleBean().getId()
                + "/"
                + fileBean.getId()
                + ".mp3");
        model.put("thumbnailUrl", FILE_PATH + "/"
                + fileBean.getArticleBean().getId()
                + "/cover_60.jpg");
        model.put("deleteUrl", request.getContextPath() + "/admin/file/"
                + fileBean.getId()
                + "/delete");
        model.put("deleteType", "GET");
        return model;
    }

    public PaginateSupport searchMusicTracks(final String searchQuery,
                                             final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("searchQuery", searchQuery);
        // final int itemCount = this.trackMapper.getTrackCount(param);
        // paginate.setItemCount(itemCount);
        paginate.compute();
        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        // final List<TrackBean> items = this.trackMapper.getTrackList(param);
        // paginate.setItems(items);
        return paginate;
    }
}
