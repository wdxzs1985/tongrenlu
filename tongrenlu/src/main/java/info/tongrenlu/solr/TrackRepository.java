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

    @Query(filters = "category:track")
    Page<TrackDocument> findByTrackOrTrackContainsOrArtistOrArtistContainsOrOriginalOrOriginalOrTitleOrTitleContainsOrTagsOrTagsContainsOrderByArticleIdDesc(String track,
                                                                                                                                                             String trackContains,
                                                                                                                                                             String artist,
                                                                                                                                                             String artistContains,
                                                                                                                                                             String original,
                                                                                                                                                             String originalContains,
                                                                                                                                                             String title,
                                                                                                                                                             String titleContains,
                                                                                                                                                             String tags,
                                                                                                                                                             String tagsContains,
                                                                                                                                                             Pageable pageable);

}
