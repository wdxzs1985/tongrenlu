package info.tongrenlu.solr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface TrackRepository extends SolrCrudRepository<ArticleDocument, String> {

    @Query(filters = "category:track")
    List<TrackDocument> findByArticleId(Integer articleId);

    @Query(filters = "category:track")
    Page<TrackDocument> findByTrackContainsOrArtistContainsOrOriginalOrTitleContainsOrTagsContainsOrderByArticleIdDesc(String track,
                                                                                                                       String artist,
                                                                                                                       String original,
                                                                                                                       String title,
                                                                                                                       String tags,
                                                                                                                       Pageable pageable);

}
