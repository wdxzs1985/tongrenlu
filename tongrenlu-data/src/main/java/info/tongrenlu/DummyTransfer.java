package info.tongrenlu;

import info.tongrenlu.dummy.AccessManagerDummy;
import info.tongrenlu.dummy.ArticleManagerDummy;
import info.tongrenlu.dummy.ArticleTagManagerDummy;
import info.tongrenlu.dummy.CollectManagerDummy;
import info.tongrenlu.dummy.ComicManagerDummy;
import info.tongrenlu.dummy.CommentManagerDummy;
import info.tongrenlu.dummy.FollowManagerDummy;
import info.tongrenlu.dummy.MusicManagerDummy;
import info.tongrenlu.dummy.TagManagerDummy;
import info.tongrenlu.dummy.TrackManagerDummy;
import info.tongrenlu.dummy.UserManagerDummy;
import info.tongrenlu.entity.AccessEntity;
import info.tongrenlu.entity.ArticleEntity;
import info.tongrenlu.entity.ArticleTagEntity;
import info.tongrenlu.entity.CollectEntity;
import info.tongrenlu.entity.ComicEntity;
import info.tongrenlu.entity.CommentEntity;
import info.tongrenlu.entity.DtoBean;
import info.tongrenlu.entity.FollowEntity;
import info.tongrenlu.entity.MusicEntity;
import info.tongrenlu.entity.TagEntity;
import info.tongrenlu.entity.UserEntity;
import info.tongrenlu.file.FileService;
import info.tongrenlu.jdbc.ObjectManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DummyTransfer {

    @Autowired
    private ObjectManager objectManager = null;

    @Autowired
    private UserManagerDummy userManager;
    @Autowired
    private ArticleManagerDummy articleManager;
    @Autowired
    private MusicManagerDummy musicManager;
    @Autowired
    private ComicManagerDummy comicManager;

    @Autowired
    private ArticleTagManagerDummy articleTagManager;
    @Autowired
    private TagManagerDummy tagManager;

    @Autowired
    private AccessManagerDummy accessManager;
    @Autowired
    private CommentManagerDummy commentManager;
    @Autowired
    private CollectManagerDummy collectManager;
    @Autowired
    private FollowManagerDummy followManager;
    @Autowired
    private TrackManagerDummy trackManager;
    @Autowired
    private FileService fileService;

    private Log log = LogFactory.getLog(this.getClass());

    /*
     * (non-Javadoc)
     * 
     * @see info.tongrenlu.TransferService#doTransfer()
     */
    public void doTransfer() {
        // this.begin();

        this.log.info("Transfer User...");
        // this.transferUser();
        this.log.info("Transfer User...ok");

        this.log.info("Transfer Music...");
        this.transferMusic();
        this.log.info("Transfer Music...ok");

        this.log.info("Transfer Comic...");
        // this.transferComic();
        this.log.info("Transfer Comic...ok");

    }

    /*
     * (non-Javadoc)
     * 
     * @see info.tongrenlu.TransferService#begin()
     */
    @Transactional
    public void begin() {

        // this.followManager.deleteAll();

        this.articleTagManager.deleteAll();
        this.accessManager.deleteAll();
        this.collectManager.deleteAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.tongrenlu.TransferService#transferUser()
     */
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

                this.transferCover(user, "u");
            }
        }
    }

    private void transferCover(final DtoBean dtoBean, final String category) {
        File input = new File(this.fileService.getInputPath(),
                              dtoBean.getObjectId() + "/" + FileService.COVER);
        if (!input.exists()) {
            input = new File(this.fileService.getInputPath(), "default" + "/"
                    + FileService.COVER);
        }

        final String dirId = category + dtoBean.getId();
        final File output = new File(this.fileService.getOutputPath(),
                                     dirId + "/" + FileService.COVER);
        this.fileService.saveFile(input, output);
        this.fileService.convertCover(output, dirId);
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

                    final List<String> tagList = new ArrayList<String>();
                    this.transferTag(article, tagList);

                    this.transferAccess(article);
                    this.transferComment(article);
                    this.transferCollect(article, "m");
                    // copy files
                    this.transferCover(article, "m");

                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.tongrenlu.TransferService#transferComic()
     */
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

                    final List<String> tagList = new ArrayList<String>();
                    this.transferTag(article, tagList);

                    this.transferAccess(article);
                    this.transferComment(article);
                    this.transferCollect(article, "c");
                    // copy files
                    this.transferCover(article, "c");

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

}
