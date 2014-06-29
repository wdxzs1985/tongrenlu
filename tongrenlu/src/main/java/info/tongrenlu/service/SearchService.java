package info.tongrenlu.service;

import info.tongrenlu.solr.ArticleDocument;
import info.tongrenlu.solr.ArticleRepository;

import org.apache.solr.client.solrj.util.ClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    private ArticleRepository articleRepository = null;

    public Page<ArticleDocument> findArticle(final String query,
                                             final Pageable pageable) {
        final String escapedQuery = ClientUtils.escapeQueryChars(query);
        return this.articleRepository.findByTitleOrTitleContainsOrTagsOrTagsContainsOrderByArticleIdDesc(escapedQuery,
                                                                                                         escapedQuery,
                                                                                                         escapedQuery,
                                                                                                         escapedQuery,
                                                                                                         pageable);
    }
}
