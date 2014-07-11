package info.tongrenlu.solr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface ArticleRepository extends SolrCrudRepository<ArticleDocument, String> {

    @Query(value = "xtag:?0 OR xtag:*?0*", filters = "-category:track")
    Page<ArticleDocument> findAllOrderByArticleIdDesc(List<String> queries,
                                                      Pageable pageable);
}
