package info.tongrenlu;

import info.tongrenlu.entity.ArticleEntity;
import info.tongrenlu.entity.ComicEntity;
import info.tongrenlu.entity.DtoBean;
import info.tongrenlu.entity.FileEntity;
import info.tongrenlu.entity.MusicEntity;
import info.tongrenlu.entity.TrackEntity;
import info.tongrenlu.entity.UserEntity;
import info.tongrenlu.service.ArticleRepository;
import info.tongrenlu.service.ComicRepository;
import info.tongrenlu.service.FileRepository;
import info.tongrenlu.service.MusicRepository;
import info.tongrenlu.service.ObjectRepository;
import info.tongrenlu.service.TrackRepository;
import info.tongrenlu.service.UserRepository;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransferService implements CommandLineRunner {

    @Autowired
    private ObjectRepository objectRepository = null;
    @Autowired
    private UserRepository userRepository = null;
    @Autowired
    private ArticleRepository articleRepository = null;
    @Autowired
    private MusicRepository musicRepository = null;
    @Autowired
    private ComicRepository comicRepository = null;
    @Autowired
    private FileRepository fileRepository = null;
    @Autowired
    private TrackRepository trackRepository = null;

    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void run(final String... arg0) throws Exception {
        this.begin();

        this.log.info("Transfer User...");
        this.transferUser();
        this.log.info("Transfer User...ok");

        this.log.info("Transfer Article...");
        this.transferArticle();
        this.log.info("Transfer Article...ok");

        this.log.info("Transfer Music...");
        this.transferMusic();
        this.log.info("Transfer Music...ok");

        this.log.info("Transfer Comic...");
        this.transferComic();
        this.log.info("Transfer Comic...ok");

        this.log.info("Transfer File...");
        this.transferFile();
        this.log.info("Transfer File...ok");

        this.log.info("Transfer Track...");
        this.transferTrack();
        this.log.info("Transfer Track...ok");

    }

    @Transactional
    public void begin() {
        this.objectRepository.deleteAll();
    }

    @Transactional
    public void transferUser() {
        this.userRepository.deleteAll();
        final List<UserEntity> userList = this.userRepository.findAll();
        if (CollectionUtils.isNotEmpty(userList)) {
            for (final UserEntity user : userList) {
                this.userRepository.save(user);
                this.objectRepository.save(user);

                // copy files
            }
        }
    }

    @Transactional
    public void transferArticle() {
        this.articleRepository.deleteAll();
        final List<ArticleEntity> articleList = this.articleRepository.findAll();
        if (CollectionUtils.isNotEmpty(articleList)) {
            for (final ArticleEntity article : articleList) {

                article.setUser(this.findUser(article.getUserId()));

                this.articleRepository.save(article);
                this.objectRepository.save(article);
            }
        }
    }

    @Transactional
    public void transferMusic() {
        this.musicRepository.deleteAll();
        final List<MusicEntity> musicList = this.musicRepository.findAll();
        if (CollectionUtils.isNotEmpty(musicList)) {
            for (final MusicEntity music : musicList) {

                final ArticleEntity article = this.findArticle(music.getArticleId());
                music.setId(article.getId());

                this.musicRepository.save(music);

                // copy files
            }
        }
    }

    @Transactional
    public void transferComic() {
        this.comicRepository.deleteAll();
        final List<ComicEntity> comicList = this.comicRepository.findAll();
        if (CollectionUtils.isNotEmpty(comicList)) {
            for (final ComicEntity comic : comicList) {

                final ArticleEntity article = this.findArticle(comic.getArticleId());
                comic.setId(article.getId());

                this.comicRepository.save(comic);

                // copy cover files
            }
        }
    }

    @Transactional
    private void transferFile() {
        this.fileRepository.deleteAll();
        final List<FileEntity> fileList = this.fileRepository.findAll();
        if (CollectionUtils.isNotEmpty(fileList)) {
            for (final FileEntity file : fileList) {

                final ArticleEntity article = this.findArticle(file.getArticleId());
                file.setArticle(article);

                file.setContentType(this.getContentType(file.getExtension()));

                this.fileRepository.save(file);
                this.objectRepository.save(file);
                // copy files
            }
        }
    }

    private void transferTrack() {
        this.trackRepository.deleteAll();
        final List<TrackEntity> trackList = this.trackRepository.findAll();
        if (CollectionUtils.isNotEmpty(trackList)) {
            for (final TrackEntity track : trackList) {

                final FileEntity file = this.findFile(track.getFileId());
                track.setFile(file);

                this.trackRepository.save(track);
            }
        }
    }

    protected UserEntity findUser(final String objectId) {
        final DtoBean userObject = this.objectRepository.findOne(objectId);
        return this.userRepository.findOne(userObject.getId());
    }

    protected ArticleEntity findArticle(final String objectId) {
        final DtoBean articleObject = this.objectRepository.findOne(objectId);
        return this.articleRepository.findOne(articleObject.getId());
    }

    protected FileEntity findFile(final String objectId) {
        final DtoBean fileObject = this.objectRepository.findOne(objectId);
        return this.fileRepository.findOne(fileObject.getId());
    }

    protected String getContentType(final String extension) {
        if (extension.equals("mp3")) {
            return "audio";
        } else {
            return "image";
        }
    }
}
