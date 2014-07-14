package info.tongrenlu;

import info.tongrenlu.entity.ArticleTagEntity;
import info.tongrenlu.entity.ComicEntity;
import info.tongrenlu.entity.MusicEntity;
import info.tongrenlu.entity.TagEntity;
import info.tongrenlu.entity.TrackEntity;
import info.tongrenlu.jdbc.ArticleManagerMysql;
import info.tongrenlu.jdbc.ArticleTagManagerMysql;
import info.tongrenlu.jdbc.ComicManagerMysql;
import info.tongrenlu.jdbc.FileManager;
import info.tongrenlu.jdbc.MusicManagerMysql;
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
import org.apache.commons.lang3.ArrayUtils;
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
    private ArticleManagerMysql articleManager = null;
    @Autowired
    private MusicManagerMysql musicManager = null;
    @Autowired
    private ComicManagerMysql comicManager = null;
    @Autowired
    private TrackManager trackManager = null;
    @Autowired
    private ArticleTagManagerMysql articleTagManager = null;
    @Autowired
    private TagManager tagManager = null;

    private Log log = LogFactory.getLog(this.getClass());

    public void doTransfer() {
        this.begin();

        this.log.info("Transfer Music...");
        // this.transferMusic();
        this.log.info("Transfer Music...ok");

        this.log.info("Transfer Comic...");
        this.transferComic();
        this.log.info("Transfer Comic...ok");
    }

    @Transactional
    public void begin() {
        // this.articleRepository.deleteAll();
    }

    @Transactional
    public void transferMusic() {
        final List<MusicEntity> musicList = this.musicManager.findAll();
        if (CollectionUtils.isNotEmpty(musicList)) {
            for (final MusicEntity music : musicList) {
                final Integer articleId = music.getId();
                final String id = "m" + articleId;
                ArticleDocument document = this.articleRepository.findOne(id);
                if (document == null) {
                    document = new MusicDocument(music.getId());
                }
                document.setTitle(music.getTitle());
                document.setDescription(music.getDescription());

                final Set<String> tagSet = this.findTagByArticleId(articleId);

                final List<TrackEntity> trackList = this.trackManager.findByArticleId(articleId);
                if (CollectionUtils.isNotEmpty(trackList)) {
                    for (final TrackEntity track : trackList) {
                        final String tid = "t" + track.getId();
                        ArticleDocument tdocument = this.articleRepository.findOne(tid);
                        if (tdocument == null) {
                            tdocument = new TrackDocument(track.getId());
                            tdocument.setArticleId(document.getArticleId());
                            tdocument.setTitle(document.getTitle());
                        }
                        tdocument.setInstrumental("1".equals(track.getInstrumental()));

                        tdocument.setTrack(track.getName());
                        tdocument.setArtist(StringUtils.split(track.getArtist(),
                                                              ","));
                        tdocument.setOriginal(StringUtils.split(track.getOriginal(),
                                                                "\n"));
                        this.articleRepository.save(tdocument);

                        tagSet.add(tdocument.getTrack());
                        if (ArrayUtils.isNotEmpty(tdocument.getArtist())) {
                            tagSet.addAll(Arrays.asList(tdocument.getArtist()));
                        }
                        if (ArrayUtils.isNotEmpty(tdocument.getOriginal())) {
                            tagSet.addAll(Arrays.asList(tdocument.getOriginal()));
                        }
                    }
                }

                document.setTags(tagSet.toArray(new String[] {}));
                this.articleRepository.save(document);
            }
        }
    }

    @Transactional
    public void transferComic() {
        final List<ComicEntity> comicList = this.comicManager.findAll();
        if (CollectionUtils.isNotEmpty(comicList)) {
            for (final ComicEntity comic : comicList) {
                final String id = "c" + comic.getId();
                ArticleDocument document = this.articleRepository.findOne(id);
                if (document == null) {
                    document = new ComicDocument(comic.getId());
                }
                document.setTitle(comic.getTitle());
                document.setDescription(comic.getDescription());
                document.setTags(this.findTagByArticleId(comic.getId())
                                     .toArray(new String[] {}));

                this.articleRepository.save(document);
            }
        }
    }

    private Set<String> findTagByArticleId(final Integer articleId) {
        final Set<String> tags = new HashSet<>();
        final List<ArticleTagEntity> articleTagEntities = this.articleTagManager.findByArticleId(articleId);
        for (final ArticleTagEntity articleTagEntity : articleTagEntities) {
            final TagEntity tagEntity = articleTagEntity.getTag();
            if (tagEntity != null) {
                tags.add(tagEntity.getTag());
            }
        }
        return tags;
    }
}
