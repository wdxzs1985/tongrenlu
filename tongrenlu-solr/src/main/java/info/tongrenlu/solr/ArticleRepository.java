package info.tongrenlu.solr;

import java.util.List;

import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface ArticleRepository extends SolrCrudRepository<ArticleDocument, String> {

    @Query(filters = { "category:music" })
    List<ArticleDocument> findByTitleStartingWith(String title);

}
