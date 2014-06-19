package info.tongrenlu.service;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.service.dao.ComicDao;
import info.tongrenlu.service.dao.FileDao;
import info.tongrenlu.service.dao.MusicDao;
import info.tongrenlu.service.dao.TagDao;
import info.tongrenlu.support.PaginateSupport;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolrService {

    private Log log = LogFactory.getLog(SolrService.class);

    @Autowired
    private FileDao fileDao = null;
    @Autowired
    private ComicDao comicDao = null;
    @Autowired
    private MusicDao musicDao = null;
    @Autowired
    private TagDao tagDao = null;
    @Autowired
    private SolrServer solrServer = null;

    public void createComicIndex(final ComicBean comicBean, final boolean commit) {
        final SolrInputDocument inputDocument = new SolrInputDocument();
        inputDocument.addField("category", "comic");
        inputDocument.addField("red_flg", comicBean.getRedFlg());
        inputDocument.addField("translate_flg", comicBean.getTranslateFlg());
        this.createArticleIndexDocument(inputDocument, comicBean);
        this.addArticleTagIndex(inputDocument, comicBean);
        try {
            this.solrServer.add(inputDocument);
            if (commit) {
                this.solrServer.commit();
            }
        } catch (final SolrServerException e) {
            this.log.error(e.getMessage(), e);
        } catch (final IOException e) {
            this.log.error(e.getMessage(), e);
        }
    }

    public void createMusicIndex(final MusicBean musicBean, final boolean commit) {
        final SolrInputDocument inputDocument = new SolrInputDocument();
        inputDocument.addField("category", "music");
        this.createArticleIndexDocument(inputDocument, musicBean);
        this.addArticleTagIndex(inputDocument, musicBean);
        this.addMusicTrachIndex(inputDocument, musicBean);
        try {
            this.solrServer.add(inputDocument);
            if (commit) {
                this.solrServer.commit();
            }
        } catch (final SolrServerException e) {
            this.log.error(e.getMessage(), e);
        } catch (final IOException e) {
            this.log.error(e.getMessage(), e);
        }
    }

    private void createArticleIndexDocument(final SolrInputDocument inputDocument,
                                            final ArticleBean articleBean) {
        inputDocument.addField("article_id", articleBean.getId());
        inputDocument.addField("title", articleBean.getTitle());
        inputDocument.addField("description", articleBean.getDescription());
        inputDocument.addField("publish_date", articleBean.getPublishDate());
    }

    private void addArticleTagIndex(final SolrInputDocument inputDocument,
                                    final ArticleBean articleBean) {
        // final Integer articleId = articleBean.getId();
        // final List<TagBean> tagBeans = this.tagDao.getArticleTag(articleId);
        // for (final TagBean tagBean : tagBeans) {
        // inputDocument.addField("tag", tagBean.getTag());
        // }
    }

    private void addMusicTrachIndex(final SolrInputDocument inputDocument,
                                    final MusicBean musicBean) {
        // final Integer articleId = articleBean.getId();
        // final List<TrackBean> trackBeans =
        // this.fileDao.getMusicTracks(articleId);
        // for (final TrackBean trackBean : trackBeans) {
        // inputDocument.addField("song_title", trackBean.getTrack());
        // inputDocument.addField("lead_artist", trackBean.getArtist());
        // inputDocument.addField("original_title",
        // trackBean.getOriginalTitle());
        // }
    }

    public PaginateSupport search(final String searchQuery,
                                  final PaginateSupport paginate) {
        final SolrQuery params = new SolrQuery();
        final String escaped = this.escapeForSolr(searchQuery);
        params.setQuery(String.format("index_ja:%s", escaped));
        params.add("start", String.valueOf(paginate.getStart() - 1));
        params.add("rows", String.valueOf(paginate.getSize()));
        try {
            final QueryResponse response = this.solrServer.query(params);
            final SolrDocumentList docs = response.getResults();
            if (!StringUtils.contains(searchQuery, "*") && docs.getNumFound() == 0) {
                this.search(searchQuery + "*", paginate);
            } else {
                paginate.setItems(docs);
                paginate.setItemCount((int) docs.getNumFound());
                paginate.compute();
            }
        } catch (final SolrServerException e) {
            this.log.error(e.getMessage(), e);
        }
        return paginate;
    }

    public static final String[] ESCAPE_CHARS = new String[] { "\\",
            "+",
            "-",
            "&",
            "|",
            "!",
            "(",
            ")",
            "{",
            "}",
            "[",
            "]",
            "^",
            "~",
            "*",
            "?",
            ":",
            "\"",
            ";",
            " " };

    public static final String[] ESCAPED_CHARS = new String[] { "\\\\",
            "\\+",
            "\\-",
            "\\&",
            "\\|",
            "\\!",
            "\\(",
            "\\)",
            "\\{",
            "\\}",
            "\\[",
            "\\]",
            "\\^",
            "\\~",
            "\\*",
            "\\?",
            "\\:",
            "\\\"",
            "\\;",
            "\\ " };

    public String escapeForSolr(final String input) {
        return StringUtils.replaceEach(input,
                                       SolrService.ESCAPE_CHARS,
                                       SolrService.ESCAPED_CHARS);
    }

    public void commit() {
        try {
            this.solrServer.commit();
        } catch (final SolrServerException e) {
            this.log.error(e.getMessage(), e);
        } catch (final IOException e) {
            this.log.error(e.getMessage(), e);
        }
    }

}
