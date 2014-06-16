package info.tongrenlu.solr;

import java.util.List;

import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface TrackRepository extends SolrCrudRepository<TrackDocument, String> {

    @Query(filters = { "category:track" })
    List<TrackDocument> findByArticleId(String articleId);
}
