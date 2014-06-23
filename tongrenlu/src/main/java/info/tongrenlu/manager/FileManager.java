package info.tongrenlu.manager;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.DtoBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    public static final String USER = "u";
    public static final String COMIC = "c";
    public static final String MUSIC = "m";
    public static final String FILE = "f";

    public static final String COVER = "cover";

    public static final String JPG = "jpg";
    public static final String MP3 = "mp3";

    private Log log = LogFactory.getLog(this.getClass());

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

    public void saveCover(final DtoBean dtoBean, final MultipartFile fileItem) {
        String dirId = null;
        if (dtoBean instanceof UserBean) {
            dirId = FileManager.USER + dtoBean.getId();
        } else if (dtoBean instanceof MusicBean) {
            dirId = FileManager.MUSIC + dtoBean.getId();
        } else if (dtoBean instanceof ComicBean) {
            dirId = FileManager.COMIC + dtoBean.getId();
        } else {
            return;
        }
        final File inputFile = this.getFile(dirId,
                                            FileManager.COVER,
                                            FileManager.JPG);
        if (fileItem != null && !fileItem.isEmpty()) {
            this.saveFile(fileItem, inputFile);
            this.convertCover(inputFile, dirId);
        } else {
            if (!inputFile.exists()) {
                for (final int size : COVER_SIZE_ARRAY) {
                    this.copyDefaultCover(dirId, size);
                }
            }
        }
    }

    public void saveMp3(final FileBean fileBean, final MultipartFile fileItem) {
        final String dirId = FileManager.MUSIC + fileBean.getArticleId();
        final String name = FileManager.FILE + fileBean.getId();

        final File file = this.getFile(dirId, name, FileManager.MP3);

        this.saveFile(fileItem, file);
    }

    public void deleteFile(final FileBean fileBean) {
        final String dirId = FileManager.MUSIC + fileBean.getArticleId();
        final String name = FileManager.FILE + fileBean.getId();

        final File file = this.getFile(dirId, name, FileManager.MP3);

        FileUtils.deleteQuietly(file);
    }

    public File getFile(final String dirId, final String name, final String ext) {
        final String rootPath = this.getInputPath();
        return new File(rootPath + "/" + dirId + "/" + name + "." + ext);
    }

    protected void saveFile(final MultipartFile fileItem, final File file) {
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

    public void convertCover(final File inputFile, final String id) {
        for (final int size : COVER_SIZE_ARRAY) {
            final String name = String.format("%s_%d", FileManager.COVER, size);
            final File outputFile = this.getFile(id, name, FileManager.JPG);
            this.convertCover(inputFile, outputFile, size);
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
        op.adaptiveResize(size, null, '^')
          .gravity("center")
          .extent(size)
          .crop(size);
        op.addImage(output.getAbsolutePath());
        // execute the operation
        try {
            cmd.run(op);
        } catch (final IOException e) {
            // throw new RuntimeException(e);
            FileUtils.deleteQuietly(output);
        } catch (final InterruptedException e) {
            // throw new RuntimeException(e);
            FileUtils.deleteQuietly(output);
        } catch (final IM4JavaException e) {
            // throw new RuntimeException(e);
            FileUtils.deleteQuietly(output);
        } finally {
            // FileUtils.deleteQuietly(input);
        }
    }

    public void copyDefaultCover(final String id, final int size) {
        final String name = String.format("%s_%d", FileManager.COVER, size);
        final File src = this.getFile("default", name, FileManager.JPG);
        final File dest = this.getFile(id, name, FileManager.JPG);

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
        } else if (articleBean instanceof ComicBean) {
            dirId = FileManager.COMIC + articleBean.getId();
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
}
