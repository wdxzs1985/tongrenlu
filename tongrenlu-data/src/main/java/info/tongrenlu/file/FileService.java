package info.tongrenlu.file;

import info.tongrenlu.entity.FileEntity;

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

@Component
public class FileService {

    public static final int[] COVER_SIZE_ARRAY = new int[] { 60, 120, 180, 400 };
    public static final int[] IMAGE_SIZE_ARRAY = new int[] { 480, 1080 };

    public static final String USER = "u";
    public static final String COMIC = "c";
    public static final String MUSIC = "m";
    public static final String FILE = "f";

    public static final String COVER = "cover.jpg";

    public static final String JPG = "jpg";
    public static final String MP3 = "mp3";

    public static final String[] ACCEPT_AUDIO = { "audio/mp3" };

    public static final String[] ACCEPT_IMAGE = { "image/jpeg", "image/png" };

    public static final String AUDIO = "audio";

    public static final String IMAGE = "image";

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

    private Log log = LogFactory.getLog(this.getClass());

    public File getFile(final String dirId, final String name) {
        final String rootPath = this.getInputPath();
        return new File(rootPath + "/" + dirId + "/" + name);
    }

    public void convertCover(final File inputFile, final String id) {
        for (final int size : FileService.COVER_SIZE_ARRAY) {
            final String name = String.format("cover_%d.jpg", size);
            final File outputFile = this.getFile(id, name);
            this.convertCover(inputFile, outputFile, size);
        }
    }

    public void convertImage(final String articleType, final FileEntity fileBean) {
        final String dirId = articleType + fileBean.getArticle().getId();
        final String inputName = String.format("f%d.%s",
                                               fileBean.getId(),
                                               fileBean.getExtension());
        final File inputFile = this.getFile(dirId, inputName);

        for (final int size : FileService.COVER_SIZE_ARRAY) {
            final String outputName = String.format("f%d_%d.jpg",
                                                    fileBean.getId(),
                                                    size);
            final File outputFile = this.getFile(dirId, outputName);
            this.convertCover(inputFile, outputFile, size);
        }
        for (final int size : FileService.IMAGE_SIZE_ARRAY) {
            final String outputName = String.format("f%d_%d.jpg",
                                                    fileBean.getId(),
                                                    size);
            final File outputFile = this.getFile(dirId, outputName);
            this.convertImage(inputFile, outputFile, size);
        }
    }

    protected void convertCover(final File input,
                                final File output,
                                final int size) {
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
        this.log.info(this.getConvertPath());
        this.log.info(op.toString());
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

    protected void convertImage(final File input,
                                final File output,
                                final int size) {
        final ConvertCmd cmd = new ConvertCmd();
        // cmd.setAsyncMode(true);
        cmd.setSearchPath(this.getConvertPath());
        // create the operation, add images and operators/options
        final IMOperation op = this.getImOperation();
        op.addImage(input.getAbsolutePath());
        op.resize(null, size, "^");
        op.addImage(output.getAbsolutePath());
        // execute the operation
        this.log.info(this.getConvertPath());
        this.log.info(op.toString());
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
        operation.density(72).quality(90d).strip();
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

    public void saveFile(final File inputFile, final File outputFile) {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = FileUtils.openInputStream(inputFile);
            output = FileUtils.openOutputStream(outputFile);
            IOUtils.copy(input, output);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
    }
}
