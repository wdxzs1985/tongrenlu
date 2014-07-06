package info.tongrenlu.service;

import info.tongrenlu.solr.ArticleDocument;
import info.tongrenlu.solr.ArticleRepository;
import info.tongrenlu.solr.MusicDocument;
import info.tongrenlu.solr.MusicRepository;
import info.tongrenlu.solr.TrackDocument;
import info.tongrenlu.solr.TrackRepository;

import org.apache.solr.client.solrj.util.ClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    private ArticleRepository articleRepository = null;
    @Autowired
    private MusicRepository musicRepository = null;
    @Autowired
    private TrackRepository trackRepository = null;

    public Page<ArticleDocument> findArticle(final String query,
                                             final Pageable pageable) {
        final String escapedQuery = ClientUtils.escapeQueryChars(query);
        return this.articleRepository.findByTitleOrTitleContainsOrTagsOrTagsContainsOrderByArticleIdDesc(escapedQuery,
                                                                                                         escapedQuery,
                                                                                                         escapedQuery,
                                                                                                         escapedQuery,
                                                                                                         pageable);
    }

    public Page<MusicDocument> findMusic(final String query,
                                         final Pageable pageable) {
        final String escapedQuery = ClientUtils.escapeQueryChars(query);
        return this.musicRepository.findByTitleOrTitleContainsOrTagsOrTagsContainsOrderByArticleIdDesc(escapedQuery,
                                                                                                       escapedQuery,
                                                                                                       escapedQuery,
                                                                                                       escapedQuery,
                                                                                                       pageable);
    }

    public Page<TrackDocument> findTrack(final String query,
                                         final Pageable pageable) {
        final String escapedQuery = ClientUtils.escapeQueryChars(query);
        return this.trackRepository.findByTrackOrTrackContainsOrArtistOrArtistContainsOrOriginalOrOriginalOrTitleOrTitleContainsOrTagsOrTagsContainsOrderByArticleIdDesc(escapedQuery,
                                                                                                                                                                         escapedQuery,
                                                                                                                                                                         escapedQuery,
                                                                                                                                                                         escapedQuery,
                                                                                                                                                                         escapedQuery,
                                                                                                                                                                         escapedQuery,
                                                                                                                                                                         escapedQuery,
                                                                                                                                                                         escapedQuery,
                                                                                                                                                                         escapedQuery,
                                                                                                                                                                         escapedQuery,
                                                                                                                                                                         pageable);
    }
}
