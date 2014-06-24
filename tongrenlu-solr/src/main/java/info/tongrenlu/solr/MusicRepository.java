package info.tongrenlu.solr;

import java.util.List;

import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface MusicRepository extends
        SolrCrudRepository<MusicDocument, String> {

    @Query(filters = { "category:music" })
    List<MusicDocument> findByTitleStartingWith(String title);

}
