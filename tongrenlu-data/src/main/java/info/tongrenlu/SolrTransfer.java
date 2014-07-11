package info.tongrenlu;

import info.tongrenlu.entity.ArticleEntity;
import info.tongrenlu.entity.ArticleTagEntity;
import info.tongrenlu.entity.ComicEntity;
import info.tongrenlu.entity.DtoBean;
import info.tongrenlu.entity.FileEntity;
import info.tongrenlu.entity.MusicEntity;
import info.tongrenlu.entity.TagEntity;
import info.tongrenlu.entity.TrackEntity;
import info.tongrenlu.jdbc.ArticleManager;
import info.tongrenlu.jdbc.ArticleTagManager;
import info.tongrenlu.jdbc.ComicManager;
import info.tongrenlu.jdbc.FileManager;
import info.tongrenlu.jdbc.MusicManager;
import info.tongrenlu.jdbc.ObjectManager;
import info.tongrenlu.jdbc.TagManager;
import info.tongrenlu.jdbc.TrackManager;
import info.tongrenlu.solr.ArticleDocument;
import info.tongrenlu.solr.ArticleRepository;
import info.tongrenlu.solr.ComicDocument;
import info.tongrenlu.solr.MusicDocument;
import info.tongrenlu.solr.TrackDocument;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolrTransfer {

    @Autowired
    private ArticleRepository articleRepository = null;

    @Autowired
    private ObjectManager objectManager = null;
    @Autowired
    private FileManager fileManager = null;
    @Autowired
    private ArticleManager articleManager = null;
    @Autowired
    private MusicManager musicManager = null;
    @Autowired
    private ComicManager comicManager = null;
    @Autowired
    private TrackManager trackManager = null;
    @Autowired
    private ArticleTagManager articleTagManager = null;
    @Autowired
    private TagManager tagManager = null;

    private Log log = LogFactory.getLog(this.getClass());

    public void doTransfer() {
        this.begin();

        this.log.info("Transfer Music...");
        this.transferMusic();
        this.log.info("Transfer Music...ok");

        this.log.info("Transfer Comic...");
        this.transferComic();
        this.log.info("Transfer Comic...ok");
    }

    @Transactional
    public void begin() {
        this.articleRepository.deleteAll();
    }

    @Transactional
    public void transferMusic() {
        final List<MusicEntity> musicList = this.musicManager.findAll();
        if (CollectionUtils.isNotEmpty(musicList)) {
            for (final MusicEntity music : musicList) {
                final String articleId = music.getArticleId();
                final ArticleEntity article = this.articleManager.findByArticleId(articleId);
                if (article != null) {
                    final ArticleEntity articleEntity = this.findArticle(article.getArticleId());
                    if (articleEntity != null) {
                        final String id = "m" + articleEntity.getId();
                        ArticleDocument document = this.articleRepository.findOne(id);
                        if (document == null) {
                            document = new MusicDocument();
                            document.setId(id);
                            document.setArticleId(articleEntity.getId());
                        }
                        document.setTitle(article.getTitle());
                        document.setDescription(article.getDescription());

                        final Set<String> tagSet = this.findTagByArticleId(articleId);

                        final List<FileEntity> fileList = this.fileManager.findByArticleId(articleId);
                        if (CollectionUtils.isNotEmpty(fileList)) {
                            for (final FileEntity file : fileList) {
                                if (file.getExtension().equals("jpg")) {
                                    continue;
                                }
                                final String fileId = file.getFileId();
                                final FileEntity fileEntity = this.findFile(fileId);
                                if (fileEntity != null) {
                                    final TrackEntity track = this.trackManager.findByFileId(fileId);
                                    if (track != null) {
                                        final String tid = "t" + fileEntity.getId();
                                        ArticleDocument tdocument = this.articleRepository.findOne(tid);
                                        if (tdocument == null) {
                                            tdocument = new TrackDocument();
                                            tdocument.setId(tid);
                                            tdocument.setFileId(fileEntity.getId());
                                            tdocument.setArticleId(document.getArticleId());
                                            tdocument.setTitle(document.getTitle());
                                            tdocument.setTags(document.getTags());
                                        }
                                        tdocument.setInstrumental(false);

                                        tdocument.setTrack(track.getName());
                                        tdocument.setArtist(StringUtils.split(track.getArtist(),
                                                                              ","));
                                        tdocument.setOriginal(StringUtils.split(track.getOriginal(),
                                                                                "\n"));
                                        this.articleRepository.save(tdocument);

                                        tagSet.add(tdocument.getTrack());
                                        tagSet.addAll(Arrays.asList(tdocument.getArtist()));
                                        tagSet.addAll(Arrays.asList(tdocument.getOriginal()));
                                    }
                                }
                            }
                        }

                        document.setTags(tagSet.toArray(new String[] {}));
                        this.articleRepository.save(document);
                    }
                }
            }
        }
    }

    @Transactional
    public void transferComic() {
        final List<ComicEntity> comicList = this.comicManager.findAll();
        if (CollectionUtils.isNotEmpty(comicList)) {
            for (final ComicEntity comic : comicList) {
                final String articleId = comic.getArticleId();
                final ArticleEntity article = this.articleManager.findByArticleId(articleId);
                if (article != null) {
                    final ArticleEntity articleEntity = this.findArticle(article.getArticleId());
                    if (articleEntity != null) {
                        final String id = "c" + articleEntity.getId();
                        ArticleDocument document = this.articleRepository.findOne(id);
                        if (document == null) {
                            document = new ComicDocument();
                            document.setId(id);
                            document.setArticleId(articleEntity.getId());
                        }
                        document.setTitle(article.getTitle());
                        document.setDescription(article.getDescription());
                        document.setTags(this.findTagByArticleId(articleId)
                                             .toArray(new String[] {}));

                        this.articleRepository.save(document);
                    }
                }
            }
        }
    }

    private Set<String> findTagByArticleId(final String articleId) {
        final Set<String> tags = new HashSet<>();
        final List<ArticleTagEntity> articleTagEntities = this.articleTagManager.findByArticleId(articleId);
        for (final ArticleTagEntity articleTagEntity : articleTagEntities) {
            final String tagId = articleTagEntity.getTagId();
            final TagEntity tagEntity = this.tagManager.findByTagId(tagId);
            if (tagEntity != null) {
                tags.add(tagEntity.getTag());
            }
        }
        return tags;
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
}
