package info.tongrenlu.solr;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface ArticleRepository extends
        SolrCrudRepository<ArticleDocument, String> {

    @Query(filters = "-category:track")
    Page<ArticleDocument> findByTitleOrTitleContainsOrTagsOrTagsContainsOrderByArticleIdDesc(String title,
                                                                                             String titleContains,
                                                                                             String tags,
                                                                                             String tagsContains,
                                                                                             Pageable pageable);

}
