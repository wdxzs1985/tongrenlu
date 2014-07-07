package info.tongrenlu.solr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface MusicRepository extends
        SolrCrudRepository<ArticleDocument, String> {

    @Query(value = "title:?0 OR title:*?0* OR tags:?0 OR tags:*?0*", filters = "-category:track")
    Page<MusicDocument> findMusicOrderByArticleIdDesc(List<String> queries,
                                                      Pageable pageable);

}
