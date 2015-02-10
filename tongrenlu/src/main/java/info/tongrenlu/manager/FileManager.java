package info.tongrenlu.manager;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.DtoBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManager {

    public static final int[] COVER_SIZE_ARRAY = new int[] { 60, 120, 180, 400 };
    public static final int[] IMAGE_SIZE_ARRAY = new int[] { 480, 1080 };

    public static final String USER = "u";
    public static final String COMIC = "c";
    public static final String MUSIC = "m";
    public static final String FILE = "f";

    public static final String COVER = "cover.jpg";
    public static final String XFD = "xfd.mp3";

    public static final String JPG = "jpg";
    public static final String MP3 = "mp3";

    public static final String[] ACCEPT_AUDIO = { "audio/mp3" };

    public static final String[] ACCEPT_IMAGE = { "image/jpeg", "image/png" };

    public static final String AUDIO = "audio";

    public static final String IMAGE = "image";

    private Log log = LogFactory.getLog(this.getClass());

    @Value("${file.inputPath}")
    private String inputPath = null;

    @Value("${file.outputPath}")
    private String outputPath = null;

    @Value("${file.convertPath}")
    private String convertPath = null;

    public String getInputPath() {
        return this.inputPath;
    }

    public String getOutputPath() {
        return this.outputPath;
    }

    public String getConvertPath() {
        return this.convertPath;
    }

    @Autowired
    private MessageSource messageSource = null;

    public void saveCover(final DtoBean dtoBean, final MultipartFile fileItem) {
        String dirId = null;
        if (dtoBean instanceof UserBean) {
            dirId = FileManager.USER + dtoBean.getId();
        } else if (dtoBean instanceof MusicBean) {
            dirId = FileManager.MUSIC + dtoBean.getId();
        } else {
            return;
        }
        final File outputFile = this.getFile(dirId, FileManager.COVER);
        if (fileItem != null && !fileItem.isEmpty()) {
            this.saveFile(fileItem, outputFile);
            this.convertCover(outputFile, dirId);
        } else {
            if (!outputFile.exists()) {
                for (final int size : FileManager.COVER_SIZE_ARRAY) {
                    this.copyDefaultCover(dirId, size);
                }
            }
        }
    }

    public void saveXFD(final ArticleBean articleBean,
                        final MultipartFile fileItem) {
        final String dirId = FileManager.MUSIC + articleBean.getId();
        final File outputFile = this.getFile(dirId, FileManager.XFD);
        if (fileItem != null && !fileItem.isEmpty()) {
            this.saveFile(fileItem, outputFile);
        }
    }

    public void saveXFD(Integer articleId, String checksum) {
        final String dirId = FileManager.MUSIC + articleId;
        final File outputFile = this.getFile(dirId, FileManager.XFD);
        if (!outputFile.isFile()) {
            final String inputFileName = String.format("%s.%s",
                                                       checksum,
                                                       FileManager.MP3);
            final File inputFile = this.getFile(dirId, inputFileName);
            try {
                FileUtils.copyFile(inputFile, outputFile);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveFile(final String articleType, final FileBean fileBean,
                         final MultipartFile fileItem) {
        final String dirId = articleType + fileBean.getArticleId();
        final String name = String.format("%s.%s",
                                          fileBean.getChecksum(),
                                          fileBean.getExtension());
        final File file = this.getFile(dirId, name);
        this.saveFile(fileItem, file);
    }

    public void deleteFile(final String articleType, final FileBean fileBean) {
        final String dirId = articleType + fileBean.getArticleId();
        final String name = String.format("%s.%s",
                                          fileBean.getChecksum(),
                                          fileBean.getExtension());
        final File file = this.getFile(dirId, name);
        FileUtils.deleteQuietly(file);

        if (FileManager.IMAGE.equals(fileBean.getContentType())) {
            for (final int size : FileManager.COVER_SIZE_ARRAY) {
                final String thumbnail = String.format("%s_%d.jpg",
                                                       fileBean.getChecksum(),
                                                       size);
                final File thumbnailFile = this.getFile(dirId, thumbnail);
                FileUtils.deleteQuietly(thumbnailFile);
            }
            for (final int size : FileManager.IMAGE_SIZE_ARRAY) {
                final String thumbnail = String.format("%s_%d.jpg",
                                                       fileBean.getChecksum(),
                                                       size);
                final File thumbnailFile = this.getFile(dirId, thumbnail);
                FileUtils.deleteQuietly(thumbnailFile);
            }
        }
    }

    public File getFile(final String dirId, final String name) {
        final String rootPath = this.getInputPath();
        return new File(rootPath + "/" + dirId + "/" + name);
    }

    public void saveFile(final MultipartFile fileItem, final File file) {
        try {
            FileUtils.copyInputStreamToFile(fileItem.getInputStream(), file);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void convertCover(final File inputFile, final String id) {
        for (final int size : FileManager.COVER_SIZE_ARRAY) {
            final String name = String.format("cover_%d.jpg", size);
            final File outputFile = this.getFile(id, name);
            this.convertCover(inputFile, outputFile, size);
        }
    }

    public void convertCover(final File input, final File output, final int size) {
        final ConvertCmd cmd = new ConvertCmd();
        // cmd.setAsyncMode(true);
        cmd.setSearchPath(this.getConvertPath());
        // create the operation, add images and operators/options
        final IMOperation op = this.getImOperation();
        op.addImage(input.getAbsolutePath());
        op.resize(size, size, "^");
        op.gravity("center");
        op.extent(size, size);
        op.addImage(output.getAbsolutePath());
        // execute the operation
        if (this.log.isDebugEnabled()) {
            this.log.debug(this.getConvertPath());
            this.log.debug(op.toString());
        }
        try {
            cmd.run(op);
        } catch (final IOException e) {
            // throw new RuntimeException(e);
            this.log.error(e, e);
        } catch (final InterruptedException e) {
            // throw new RuntimeException(e);
            this.log.error(e, e);
        } catch (final IM4JavaException e) {
            // throw new RuntimeException(e);
            this.log.error(e, e);
        } finally {
            // FileUtils.deleteQuietly(input);
        }
    }

    public void convertImage(final String articleType, final FileBean fileBean) {
        final String dirId = articleType + fileBean.getArticleId();
        final String inputName = String.format("%s.%s",
                                               fileBean.getChecksum(),
                                               fileBean.getExtension());
        final File inputFile = this.getFile(dirId, inputName);

        for (final int size : FileManager.COVER_SIZE_ARRAY) {
            final String outputName = String.format("%s_%d.jpg",
                                                    fileBean.getChecksum(),
                                                    size);
            final File outputFile = this.getFile(dirId, outputName);
            this.convertCover(inputFile, outputFile, size);
        }
        for (final int size : FileManager.IMAGE_SIZE_ARRAY) {
            final String outputName = String.format("%s_%d.jpg",
                                                    fileBean.getChecksum(),
                                                    size);
            final File outputFile = this.getFile(dirId, outputName);
            this.convertImage(inputFile, outputFile, size);
        }
    }

    public void convertImage(final File input, final File output, final int size) {
        final ConvertCmd cmd = new ConvertCmd();
        // cmd.setAsyncMode(true);
        cmd.setSearchPath(this.getConvertPath());
        // create the operation, add images and operators/options
        final IMOperation op = this.getImOperation();
        op.addImage(input.getAbsolutePath());
        op.resize(null, size, ">");
        op.addImage(output.getAbsolutePath());
        // execute the operation
        if (this.log.isDebugEnabled()) {
            this.log.debug(this.getConvertPath());
            this.log.debug(op.toString());
        }
        try {
            cmd.run(op);
        } catch (final IOException e) {
            // throw new RuntimeException(e);
            this.log.error(e, e);
        } catch (final InterruptedException e) {
            // throw new RuntimeException(e);
            this.log.error(e, e);
        } catch (final IM4JavaException e) {
            // throw new RuntimeException(e);
            this.log.error(e, e);
        } finally {
            // FileUtils.deleteQuietly(input);
        }
    }

    protected IMOperation getImOperation() {
        final IMOperation operation = new IMOperation();
        operation.density(72).quality(90d);
        return operation;
    }

    public void copyDefaultCover(final String dirId, final int size) {
        final String name = String.format("cover_%d.jpg", size);
        final File src = this.getFile("default", name);
        final File dest = this.getFile(dirId, name);

        try {
            FileUtils.copyFile(src, dest);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteArticle(final ArticleBean articleBean) {
        String dirId = null;
        if (articleBean instanceof MusicBean) {
            dirId = FileManager.MUSIC + articleBean.getId();
        } else {
            return;
        }
        final String rootPath = this.getInputPath();
        final File dir = new File(rootPath + "/" + dirId);

        try {
            FileUtils.deleteDirectory(dir);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateContentType(final String contentType,
                                       final String[] accepted,
                                       final String errorAttribute,
                                       final Map<String, Object> model,
                                       final Locale locale) {
        boolean isValid = true;
        if (!ArrayUtils.contains(accepted, contentType)) {
            final String message = this.messageSource.getMessage("error.fileNotAccept",
                                                                 null,
                                                                 locale);
            model.put(errorAttribute, message);
            isValid = false;
        }
        return isValid;
    }
}
