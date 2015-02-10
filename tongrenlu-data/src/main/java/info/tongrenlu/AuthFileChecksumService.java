package info.tongrenlu;

import info.tongrenlu.file.FileService;
import info.tongrenlu.jdbc.FileManager;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class AuthFileChecksumService implements CommandLineRunner {

    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    private FileService fileService = null;
    @Autowired
    private FileManager fileManager = null;

    @Override
    public void run(final String... arg0) throws Exception {
        List<Map<String, Object>> filelist = this.fileManager.fetchAuthFileList();

        this.log.debug("find: " + filelist.size());

        for (Map<String, Object> file : filelist) {
            String dirId = FileService.USER + file.get("userId");
            String nameBefore = String.format("auth/f%d.jpg", file.get("id"));
            File before = this.fileService.getFile(dirId, nameBefore);
            if (!before.isFile()) {
                this.log.debug("file not find: " + before.getAbsolutePath());
                continue;
            }

            byte[] data = FileUtils.readFileToByteArray(before);
            file.put("checksum", DigestUtils.md5Hex(data));
            this.fileManager.updateAuthFileChecksum(file);

            String checksum = (String) file.get("checksum");
            String nameAfter = String.format("auth/%s.jpg", checksum);
            File after = this.fileService.getFile(dirId, nameAfter);
            FileUtils.copyFile(before, after);
            FileUtils.deleteQuietly(this.fileService.getFile(dirId,
                                                             String.format("%s.jpg",
                                                                           checksum)));

            for (final int size : FileService.COVER_SIZE_ARRAY) {
                final String inputName = String.format("auth/f%d_%d.jpg",
                                                       file.get("id"),
                                                       size);
                String outputName = String.format("auth/%s_%d.jpg",
                                                  checksum,
                                                  size);
                final File inputFile = this.fileService.getFile(dirId,
                                                                inputName);
                final File outputFile = this.fileService.getFile(dirId,
                                                                 outputName);
                FileUtils.copyFile(inputFile, outputFile);
                FileUtils.deleteQuietly(this.fileService.getFile(dirId,
                                                                 String.format("%s_%d.jpg",
                                                                               checksum,
                                                                               size)));
            }
            for (final int size : FileService.IMAGE_SIZE_ARRAY) {
                final String inputName = String.format("auth/f%d_%d.jpg",
                                                       file.get("id"),
                                                       size);
                String outputName = String.format("auth/%s_%d.jpg",
                                                  checksum,
                                                  size);
                final File inputFile = this.fileService.getFile(dirId,
                                                                inputName);
                final File outputFile = this.fileService.getFile(dirId,
                                                                 outputName);
                FileUtils.copyFile(inputFile, outputFile);
                FileUtils.deleteQuietly(this.fileService.getFile(dirId,
                                                                 String.format("%s_%d.jpg",
                                                                               checksum,
                                                                               size)));
            }
        }
    }
}
