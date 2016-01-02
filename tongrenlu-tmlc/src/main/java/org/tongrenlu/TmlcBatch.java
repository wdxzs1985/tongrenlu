package org.tongrenlu;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.tongrenlu.domain.ArticleBean;
import org.tongrenlu.domain.ArticleTagBean;
import org.tongrenlu.domain.FileBean;
import org.tongrenlu.domain.MusicBean;
import org.tongrenlu.domain.TagBean;
import org.tongrenlu.domain.TrackBean;

@Component
public class TmlcBatch implements CommandLineRunner {

    private static final Log LOG = LogFactory.getLog(TmlcBatch.class);

    private static final Pattern ARTIST_PATTERN = Pattern.compile("^\\[(.*)\\]$");
    private static final Pattern ALBUM_PATTERN = Pattern.compile("^[\\d]{4}.[\\d]{2}.[\\d]{2}( \\[(.*)\\])? (.*?) (\\[(.*)\\])?$");

    private static final Pattern TRACK_PATTERN = Pattern.compile("([\\d]{2})\\. (.*) - (.*)");

    @Value("${tmlc.path}")
    private String tmlcPath;

    private Map<String, MusicBean> musicBeanMap;
    private Map<String, TagBean> tagBeanMap;
    private List<ArticleTagBean> articleTagBeanList;
    private List<FileBean> fileBeanList;
    private List<TrackBean> trackBeanList;

    @Override
    public void run(String... args) throws Exception {

        this.musicBeanMap = new HashMap<>();
        this.tagBeanMap = new HashMap<>();
        this.articleTagBeanList = new ArrayList<>();
        this.fileBeanList = new ArrayList<>();
        this.trackBeanList = new ArrayList<>();

        File rootDir = new File(this.tmlcPath);
        LOG.debug(rootDir.getAbsolutePath());

        Collection<File> artistList = FileUtils.listFilesAndDirs(rootDir,
                                                                 FileFilterUtils.directoryFileFilter(),
                                                                 FileFilterUtils.trueFileFilter());

        LOG.debug(artistList.size());

        ItemReader<File> artistReader = new FileItemReader(artistList);

        File file;

        String artist = null;
        while ((file = artistReader.read()) != null) {
            if (file.isDirectory()) {
                String dirName = file.getName();
                Matcher artistMatcher = ARTIST_PATTERN.matcher(dirName);
                if (artistMatcher.find()) {
                    artist = artistMatcher.group(1);
                } else {
                    Matcher albumMatcher = ALBUM_PATTERN.matcher(dirName);
                    if (albumMatcher.find()) {

                        MusicBean musicBean = this.createMusicBean(artist,
                                                                   albumMatcher.group(3));

                        this.createArticleTagBean(musicBean,
                                                  this.createTagBean(artist));
                        this.createArticleTagBean(musicBean,
                                                  this.createTagBean(albumMatcher.group(2)));
                        this.createArticleTagBean(musicBean,
                                                  this.createTagBean(albumMatcher.group(5)));

                        Collection<File> jpgList = FileUtils.listFiles(file,
                                                                       FileFilterUtils.suffixFileFilter("jpg"),
                                                                       FileFilterUtils.trueFileFilter());

                        for (File imageFile : jpgList) {
                            FileBean fileBean = this.createImageFileBean(imageFile,
                                                                         musicBean);
                        }

                        Collection<File> audioList = FileUtils.listFiles(file,
                                                                         FileFilterUtils.suffixFileFilter("mp3"),
                                                                         FileFilterUtils.trueFileFilter());

                        for (File audioFile : audioList) {
                            FileBean fileBean = this.createAudioFileBean(audioFile,
                                                                         musicBean);

                            this.createTrackBean(fileBean, musicBean);
                        }

                    } else {
                        // LOG.debug(dirName);
                    }
                }
            }
        }

        LOG.debug(String.format("Music size: %d", this.musicBeanMap.size()));
        LOG.debug(String.format("TAG size: %d", this.tagBeanMap.size()));
        LOG.debug(String.format("articleTag size: %d",
                                this.articleTagBeanList.size()));
        LOG.debug(String.format("fileBeanList size: %d",
                                this.fileBeanList.size()));
        LOG.debug(String.format("trackBeanList size: %d",
                                this.trackBeanList.size()));
    }

    private MusicBean createMusicBean(String artist, String subTitle) {
        String title = String.format("[%s] %s", artist, subTitle);

        String description = "资源来自 Touhou Lossless Music Collection";

        MusicBean musicBean = new MusicBean();
        musicBean.setTitle(title);
        musicBean.setDescription(description);

        this.musicBeanMap.put(title, musicBean);

        return musicBean;

    }

    private ArticleTagBean createArticleTagBean(MusicBean musicBean,
                                                TagBean tagBean) {
        ArticleTagBean articleTagBean = new ArticleTagBean();
        articleTagBean.setArticleBean(musicBean);
        articleTagBean.setTagBean(tagBean);

        this.articleTagBeanList.add(articleTagBean);
        return articleTagBean;
    }

    private TagBean createTagBean(String tag) {
        TagBean tagBean = new TagBean();
        tagBean.setTag(tag);

        this.tagBeanMap.put(tag, tagBean);

        return tagBean;
    }

    private FileBean createFileBean(File file) {
        String filename = file.getName();

        FileBean fileBean = new FileBean();
        fileBean.setName(FilenameUtils.getBaseName(filename));
        fileBean.setExtension(FilenameUtils.getExtension(filename));
        fileBean.setChecksum(this.checksum(file));

        this.fileBeanList.add(fileBean);
        return fileBean;
    }

    private String checksum(File file) {
        InputStream ins = null;
        try {
            ins = FileUtils.openInputStream(file);
            return DigestUtils.md5Hex(ins);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(ins);
        }

    }

    private FileBean createImageFileBean(File file, ArticleBean articleBean) {
        FileBean fileBean = this.createFileBean(file);
        fileBean.setArticleId(articleBean.getId());
        fileBean.setContentType("image");
        fileBean.setOrderNo(0);
        return fileBean;
    }

    private FileBean createAudioFileBean(File file, ArticleBean articleBean) {
        FileBean fileBean = this.createFileBean(file);
        fileBean.setArticleId(articleBean.getId());
        fileBean.setContentType("audio");

        Matcher matcher = TRACK_PATTERN.matcher(fileBean.getName());
        if (matcher.find()) {
            fileBean.setOrderNo(Integer.parseInt(matcher.group(1)));
        }

        return fileBean;
    }

    private TrackBean createTrackBean(FileBean fileBean,
                                      ArticleBean articleBean) {
        Matcher matcher = TRACK_PATTERN.matcher(fileBean.getName());
        if (matcher.find()) {
            TrackBean trackBean = new TrackBean();
            trackBean.setFileBean(fileBean);
            trackBean.setArtist(matcher.group(2));
            trackBean.setName(matcher.group(3));
            this.trackBeanList.add(trackBean);
            return trackBean;
        }

        return null;
    }
}
