package info.tongrenlu.solr;

import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "collection3")
public class TrackDocument extends ArticleDocument {

    public static final String TRACK = "track";

    public static boolean isTrack(final ArticleDocument articleDocument) {
        return TRACK.equals(articleDocument.getCategory());
    }

    public TrackDocument() {
        this.setCategory(TRACK);
    }

}
