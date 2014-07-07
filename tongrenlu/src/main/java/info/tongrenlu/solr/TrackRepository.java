package info.tongrenlu.solr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface TrackRepository extends
        SolrCrudRepository<ArticleDocument, String> {

    @Query(filters = "category:track")
    List<TrackDocument> findByArticleId(Integer articleId);

    @Query(value = "track:?0 OR artist:?0 OR original:?0 OR title:?0 OR track:*?0* OR artist:*?0* OR original:*?0* OR title:*?0*", filters = "category:track")
    Page<TrackDocument> findTrackByArticleIdDesc(List<String> queries,
                                                 Pageable pageable);

}
