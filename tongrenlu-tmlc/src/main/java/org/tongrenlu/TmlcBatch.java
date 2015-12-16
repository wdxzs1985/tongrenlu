package org.tongrenlu;

import java.io.File;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TmlcBatch implements CommandLineRunner {

    private static final Log LOG = LogFactory.getLog(TmlcBatch.class);

    @Value("${tmlc.path}")
    private String tmlcPath;

    @Override
    public void run(String... args) throws Exception {

        File rootDir = new File(this.tmlcPath);
        LOG.debug(rootDir.getAbsolutePath());

        Collection<File> artistList = FileUtils.listFilesAndDirs(rootDir,
                                                                 FileFilterUtils.directoryFileFilter(),
                                                                 FileFilterUtils.trueFileFilter());

        LOG.debug(artistList.size());

        ItemReader<File> artistReader = new FileItemReader(artistList);

        File subDir;
        while ((subDir = artistReader.read()) != null) {
            String dirName = subDir.getName();
            if (Pattern.matches("\\[.*\\]", dirName)) {
                // LOG.debug(dirName);
            } else {
                Pattern albumPattern = Pattern.compile("^[\\d]{4}.[\\d]{2}.[\\d]{2}( \\[(.*)\\])? (.*?) (\\[(.*)\\])?$");
                Matcher matcher = albumPattern.matcher(dirName);
                if (matcher.find()) {
                    LOG.debug(dirName);
                    // LOG.debug("group(1): " + matcher.group(1));
                    LOG.debug("group(2): " + matcher.group(2));
                    LOG.debug("group(3): " + matcher.group(3));
                    // LOG.debug("group(4): " + matcher.group(4));
                    LOG.debug("group(5): " + matcher.group(5));
                } else {
                    // LOG.debug(dirName);
                }
            }
        }

    }

}
