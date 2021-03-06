package info.tongrenlu.solr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface ArticleRepository extends
        SolrCrudRepository<ArticleDocument, String> {

    @Query(value = "xtag_ja:?0 OR xtag_ja:*?0* OR xtag_en:?0 OR xtag_en:*?0* OR xtag_zh:?0 OR xtag_zh:*?0*", filters = "category:music")
    Page<ArticleDocument> findMusic(List<String> queries, Pageable pageable);

    @Query(value = "xtag_ja:?0 OR xtag_ja:*?0* OR xtag_en:?0 OR xtag_en:*?0* OR xtag_zh:?0 OR xtag_zh:*?0*", filters = "category:comic")
    Page<ArticleDocument> findComic(List<String> queries, Pageable pageable);
}
