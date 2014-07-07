package info.tongrenlu;

import info.tongrenlu.entity.AccessEntity;
import info.tongrenlu.entity.ArticleEntity;
import info.tongrenlu.entity.ArticleTagEntity;
import info.tongrenlu.entity.CollectEntity;
import info.tongrenlu.entity.ComicEntity;
import info.tongrenlu.entity.CommentEntity;
import info.tongrenlu.entity.DtoBean;
import info.tongrenlu.entity.FileEntity;
import info.tongrenlu.entity.FollowEntity;
import info.tongrenlu.entity.MusicEntity;
import info.tongrenlu.entity.TagEntity;
import info.tongrenlu.entity.TrackEntity;
import info.tongrenlu.entity.UserEntity;
import info.tongrenlu.file.FileService;
import info.tongrenlu.jdbc.AccessManager;
import info.tongrenlu.jdbc.ArticleManager;
import info.tongrenlu.jdbc.ArticleTagManager;
import info.tongrenlu.jdbc.CollectManager;
import info.tongrenlu.jdbc.ComicManager;
import info.tongrenlu.jdbc.CommentManager;
import info.tongrenlu.jdbc.FileManager;
import info.tongrenlu.jdbc.FollowManager;
import info.tongrenlu.jdbc.MusicManager;
import info.tongrenlu.jdbc.ObjectManager;
import info.tongrenlu.jdbc.TagManager;
import info.tongrenlu.jdbc.TrackManager;
import info.tongrenlu.jdbc.UserManager;
import info.tongrenlu.solr.ArticleDocument;
import info.tongrenlu.solr.ArticleRepository;
import info.tongrenlu.solr.MusicDocument;
import info.tongrenlu.solr.TrackDocument;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JdbcTransfer implements TransferService {

    @Autowired
    private ObjectManager objectManager = null;
    @Autowired
    private UserManager userManager = null;
    @Autowired
    private ArticleManager articleManager = null;
    @Autowired
    private MusicManager musicManager = null;
    @Autowired
    private ComicManager comicManager = null;
    @Autowired
    private FileManager fileManager = null;
    @Autowired
    private TrackManager trackManager = null;
    @Autowired
    private ArticleTagManager articleTagManager = null;
    @Autowired
    private TagManager tagManager = null;
    @Autowired
    private AccessManager accessManager = null;
    @Autowired
    private CommentManager commentManager = null;
    @Autowired
    private CollectManager collectManager = null;
    @Autowired
    private FollowManager followManager = null;
    @Autowired
    private ArticleRepository articleRepository = null;
    @Autowired
    private FileService fileService = null;

    @Value("${jdbc.transfer.user:false}")
    private boolean transferUser = false;

    @Value("${jdbc.transfer.article:false}")
    private boolean transferArticle = false;

    @Value("${solr.transfer:false}")
    private boolean transferSolr = false;

    @Value("${file.transfer:false}")
    private boolean transferFile = false;

    private Log log = LogFactory.getLog(this.getClass());

    /*
     * (non-Javadoc)
     * 
     * @see info.tongrenlu.TransferService#doTransfer()
     */
    @Override
    public void doTransfer() {
        this.begin();

        if (this.transferUser) {
            this.log.info("Transfer User...");
            this.transferUser();
            this.log.info("Transfer User...ok");
        }

        if (this.transferArticle) {
            this.log.info("Transfer Music...");
            this.transferMusic();
            this.log.info("Transfer Music...ok");

            this.log.info("Transfer Comic...");
            this.transferComic();
            this.log.info("Transfer Comic...ok");
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see info.tongrenlu.TransferService#begin()
     */
    @Override
    @Transactional
    public void begin() {
        if (this.transferSolr) {
            this.articleRepository.deleteAll();
        }

        if (this.transferUser) {
            this.followManager.deleteAll();
            this.collectManager.deleteAll();
        }

        if (this.transferArticle) {
            this.articleTagManager.deleteAll();
            this.accessManager.deleteAll();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.tongrenlu.TransferService#transferUser()
     */
    @Override
    @Transactional
    public void transferUser() {
        final List<UserEntity> userList = this.userManager.findAll();
        if (CollectionUtils.isNotEmpty(userList)) {
            for (final UserEntity user : userList) {
                final UserEntity userEntity = this.findUser(user.getUserId());
                if (userEntity == null) {
                    this.userManager.save(user);
                    this.objectManager.save(user);
                } else {
                    user.setId(userEntity.getId());
                }
                this.transferFollow(user);

                if (this.transferFile) {
                    this.transferCover(user, "u");
                }
            }
        }
    }

    private void transferFollow(final UserEntity user) {
        final String userId = user.getUserId();
        final List<FollowEntity> followEntities = this.followManager.findByUserId(userId);
        if (CollectionUtils.isNotEmpty(followEntities)) {
            for (final FollowEntity followEntity : followEntities) {
                final UserEntity follow = this.findUser(followEntity.getFollowId());
                if (follow != null) {
                    followEntity.setUser(user);
                    followEntity.setFollow(follow);
                    followEntity.setCategory("u");
                    this.followManager.save(followEntity);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.tongrenlu.TransferService#transferMusic()
     */
    @Override
    @Transactional
    public void transferMusic() {
        final List<MusicEntity> musicList = this.musicManager.findAll();
        if (CollectionUtils.isNotEmpty(musicList)) {
            for (final MusicEntity music : musicList) {
                final String articleId = music.getArticleId();
                final ArticleEntity article = this.articleManager.findByArticleId(articleId);
                if (article != null) {

                    final ArticleEntity articleEntity = this.findArticle(article.getArticleId());
                    if (articleEntity == null) {
                        article.setUser(this.findUser(article.getUserId()));
                        this.articleManager.save(article);
                        this.objectManager.save(article);

                        music.setId(article.getId());
                        this.musicManager.save(music);
                    } else {
                        article.setId(articleEntity.getId());
                        music.setId(articleEntity.getId());
                    }

                    final String id = "m" + article.getId();
                    ArticleDocument document = this.articleRepository.findOne(id);
                    if (document == null) {
                        document = new MusicDocument();
                        document.setId(id);
                        document.setArticleId(article.getId());
                    }
                    document.setTitle(article.getTitle());
                    document.setDescription(article.getDescription());

                    final List<String> tagList = new ArrayList<String>();
                    this.transferTag(article, tagList);
                    document.setTags(tagList.toArray(new String[] {}));
                    this.articleRepository.save(document);

                    if (this.transferFile) {
                        this.transferCover(article, "m");
                        this.transferFile(article, document, "m");
                    }

                    this.transferAccess(article);
                    this.transferComment(article);
                    this.transferCollect(article, "m");
                    // copy files

                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.tongrenlu.TransferService#transferComic()
     */
    @Override
    @Transactional
    public void transferComic() {
        final List<ComicEntity> comicList = this.comicManager.findAll();
        if (CollectionUtils.isNotEmpty(comicList)) {
            for (final ComicEntity comic : comicList) {
                final String articleId = comic.getArticleId();
                final ArticleEntity article = this.articleManager.findByArticleId(articleId);
                if (article != null) {
                    final ArticleEntity articleEntity = this.findArticle(article.getArticleId());
                    if (articleEntity == null) {
                        article.setUser(this.findUser(article.getUserId()));
                        this.articleManager.save(article);
                        this.objectManager.save(article);

                        comic.setId(article.getId());
                        this.comicManager.save(comic);
                    } else {
                        article.setId(articleEntity.getId());
                        comic.setId(articleEntity.getId());
                    }

                    final String id = "c" + article.getId();
                    ArticleDocument document = this.articleRepository.findOne(id);
                    if (document == null) {
                        document = new MusicDocument();
                        document.setId(id);
                        document.setArticleId(article.getId());
                    }
                    document.setTitle(article.getTitle());
                    document.setDescription(article.getDescription());

                    final List<String> tagList = new ArrayList<String>();
                    this.transferTag(article, tagList);
                    document.setTags(tagList.toArray(new String[] {}));
                    this.articleRepository.save(document);

                    if (this.transferFile) {
                        this.transferCover(article, "c");
                        this.transferFile(article, null, "c");
                    }

                    this.transferAccess(article);
                    this.transferComment(article);
                    this.transferCollect(article, "c");
                    // copy files

                }
            }
        }
    }

    private void transferTag(final ArticleEntity article,
                             final List<String> tagList) {
        final String articleId = article.getArticleId();
        final List<ArticleTagEntity> articleTagEntities = this.articleTagManager.findByArticleId(articleId);
        if (CollectionUtils.isNotEmpty(articleTagEntities)) {
            for (final ArticleTagEntity articleTagEntity : articleTagEntities) {
                articleTagEntity.setArticle(article);
                final String tagId = articleTagEntity.getTagId();
                final TagEntity tag = this.tagManager.findByTagId(tagId);
                if (tag != null) {
                    final TagEntity tagEntity = this.findTag(tagId);
                    if (tagEntity == null) {
                        this.tagManager.save(tag);
                        this.objectManager.save(tag);
                    } else {
                        tag.setId(tagEntity.getId());
                    }
                    articleTagEntity.setTag(tag);

                    this.articleTagManager.save(articleTagEntity);

                    tagList.add(tag.getTag());
                }
            }
        }
    }

    private void transferFile(final ArticleEntity article,
                              final ArticleDocument document,
                              final String category) {
        final String articleId = article.getArticleId();
        final List<FileEntity> fileList = this.fileManager.findByArticleId(articleId);
        if (CollectionUtils.isNotEmpty(fileList)) {
            for (final FileEntity file : fileList) {
                file.setArticle(article);
                file.setContentType(this.getContentType(file.getExtension()));

                final String fileId = file.getFileId();
                final FileEntity fileEntity = this.findFile(fileId);
                if (fileEntity == null) {
                    this.fileManager.save(file);
                    this.objectManager.save(file);

                } else {
                    file.setId(fileEntity.getId());
                }

                if ("audio".equals(file.getContentType())) {
                    this.transferTrack(file, document);
                }

                this.transferFiles(article, file, category);

                // copy files
            }
        }
    }

    private void transferTrack(final FileEntity file,
                               final ArticleDocument document) {
        final String fileId = file.getFileId();
        final TrackEntity track = this.trackManager.findByFileId(fileId);
        if (track != null) {
            track.setFile(file);
            this.trackManager.save(track);

            final String id = "t" + file.getId();
            ArticleDocument tdocument = this.articleRepository.findOne(id);
            if (tdocument == null) {
                tdocument = new TrackDocument();
                tdocument.setId(id);
                tdocument.setFileId(file.getId());
                tdocument.setArticleId(document.getArticleId());
                tdocument.setTitle(document.getTitle());
                tdocument.setTags(document.getTags());
            }
            tdocument.setTrack(track.getName());
            tdocument.setInstrumental(false);
            tdocument.setArtist(StringUtils.split(track.getArtist(), ","));
            tdocument.setOriginal(StringUtils.split(track.getOriginal(), "\n"));

            this.articleRepository.save(tdocument);
        }
    }

    private void transferAccess(final ArticleEntity article) {
        final String articleId = article.getArticleId();
        final List<AccessEntity> accessEntities = this.accessManager.findByArticleId(articleId);
        if (CollectionUtils.isNotEmpty(accessEntities)) {
            for (final AccessEntity accessEntity : accessEntities) {
                accessEntity.setArticle(article);
                if (StringUtils.isNotBlank(accessEntity.getUserId())) {
                    final UserEntity user = this.findUser(accessEntity.getUserId());
                    if (user != null) {
                        accessEntity.setUser(user);
                    } else {
                        accessEntity.setUser(new UserEntity());
                    }
                } else {
                    accessEntity.setUser(new UserEntity());
                }

                this.accessManager.save(accessEntity);
            }
        }
    }

    private void transferComment(final ArticleEntity article) {
        final String articleId = article.getArticleId();
        final List<CommentEntity> commentEntities = this.commentManager.findByArticleId(articleId);
        if (CollectionUtils.isNotEmpty(commentEntities)) {
            for (final CommentEntity comment : commentEntities) {
                final String commentId = comment.getCommentId();
                final CommentEntity commentEntity = this.findComment(commentId);
                if (commentEntity == null) {
                    comment.setArticle(article);
                    if (StringUtils.isNotBlank(comment.getUserId())) {
                        final UserEntity user = this.findUser(comment.getUserId());
                        if (user != null) {
                            comment.setUser(user);
                        } else {
                            comment.setUser(new UserEntity());
                        }
                    } else {
                        comment.setUser(new UserEntity());
                    }
                    this.commentManager.save(comment);
                    this.objectManager.save(comment);
                } else {
                    comment.setId(commentEntity.getId());
                }
            }
        }
    }

    private void transferCollect(final ArticleEntity article,
                                 final String category) {
        final String articleId = article.getArticleId();
        final List<CollectEntity> collectEntities = this.collectManager.findByArticleId(articleId);
        if (CollectionUtils.isNotEmpty(collectEntities)) {
            for (final CollectEntity collectEntity : collectEntities) {
                collectEntity.setArticle(article);
                collectEntity.setCategory(category);
                final UserEntity user = this.findUser(collectEntity.getUserId());
                if (user != null) {
                    collectEntity.setUser(user);
                    this.collectManager.save(collectEntity);
                }
            }
        }
    }

    private void transferCover(final DtoBean dtoBean, final String category) {
        File input = new File(this.fileService.getInputPath(),
                              dtoBean.getObjectId() + "/avatar.jpg");
        if (!input.exists()) {
            input = new File(this.fileService.getOutputPath(),
                             "default" + "/" + FileService.COVER);
        }

        final String dirId = category + dtoBean.getId();
        final File output = new File(this.fileService.getOutputPath(),
                                     dirId + "/" + FileService.COVER);
        this.fileService.saveFile(input, output);
        this.fileService.convertCover(output, dirId);
    }

    private void transferFiles(final ArticleEntity article,
                               final FileEntity file,
                               final String category) {
        final File input = new File(this.fileService.getInputPath(),
                                    article.getArticleId() + "/"
                                            + file.getFileId()
                                            + "."
                                            + file.getExtension());

        if (!input.exists()) {
            return;
        }

        final String dirId = category + article.getId();
        final String fileId = FileService.FILE + file.getId();
        final File output = new File(this.fileService.getOutputPath(),
                                     dirId   + "/"
                                             + fileId
                                             + "."
                                             + file.getExtension());
        this.fileService.saveFile(input, output);

        if ("image".equals(file.getContentType())) {
            this.fileService.convertImage(category, file);
        }
    }

    private UserEntity findUser(final String objectId) {
        final DtoBean userObject = this.objectManager.findOne(objectId);
        if (userObject == null) {
            return null;
        }
        return this.userManager.findOne(userObject.getId());
    }

    private ArticleEntity findArticle(final String objectId) {
        final DtoBean articleObject = this.objectManager.findOne(objectId);
        if (articleObject == null) {
            return null;
        }
        return this.articleManager.findOne(articleObject.getId());
    }

    private FileEntity findFile(final String objectId) {
        final DtoBean fileObject = this.objectManager.findOne(objectId);
        if (fileObject == null) {
            return null;
        }
        return this.fileManager.findOne(fileObject.getId());
    }

    private TagEntity findTag(final String objectId) {
        final DtoBean tagObject = this.objectManager.findOne(objectId);
        if (tagObject == null) {
            return null;
        }
        return this.tagManager.findOne(tagObject.getId());
    }

    private CommentEntity findComment(final String objectId) {
        final DtoBean commentObject = this.objectManager.findOne(objectId);
        if (commentObject == null) {
            return null;
        }
        return this.commentManager.findOne(commentObject.getId());
    }

    private String getContentType(final String extension) {
        if (extension.equals("mp3")) {
            return "audio";
        } else {
            return "image";
        }
    }
}
