package info.tongrenlu.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import info.tongrenlu.solr.ComicDocument;
import info.tongrenlu.solr.ComicRepository;
import info.tongrenlu.solr.MusicDocument;
import info.tongrenlu.solr.MusicRepository;
import info.tongrenlu.solr.TrackDocument;
import info.tongrenlu.solr.TrackRepository;

//@Service
public class SearchService {

    // @Autowired
    private MusicRepository musicRepository = null;
    // @Autowired
    private ComicRepository comicRepository = null;
    // @Autowired
    private TrackRepository trackRepository = null;

    public Page<MusicDocument> findMusic(
                                         final String query,
                                         final Pageable pageable) {
        final List<String> queries = new ArrayList<>();
        for (final String text : StringUtils.split(query)) {
            queries.add(ClientUtils.escapeQueryChars(text));
        }
        return this.musicRepository.findMusic(queries, pageable);
    }

    public Page<ComicDocument> findComic(
                                         final String query,
                                         final Pageable pageable) {
        final List<String> queries = new ArrayList<>();
        for (final String text : StringUtils.split(query)) {
            queries.add(ClientUtils.escapeQueryChars(text));
        }
        return this.comicRepository.findComic(queries, pageable);
    }

    public Page<TrackDocument> findTrack(
                                         final String query,
                                         final Pageable pageable) {
        final List<String> queries = new ArrayList<>();
        for (final String text : StringUtils.split(query)) {
            queries.add(ClientUtils.escapeQueryChars(text));
        }
        return this.trackRepository.findTrackOrderById(queries, pageable);
    }
}
