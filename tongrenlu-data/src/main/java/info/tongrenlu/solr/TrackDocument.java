package info.tongrenlu.solr;

import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "collection3")
public class TrackDocument extends ArticleDocument {

    public static final String TRACK = "track";

    public static boolean isTrack(final ArticleDocument articleDocument) {
        return TrackDocument.TRACK.equals(articleDocument.getCategory());
    }

    public TrackDocument() {
        this.setCategory(TrackDocument.TRACK);
    }

    public TrackDocument(final Integer fileId) {
        this.setCategory(TrackDocument.TRACK);
        this.setId("t" + fileId);
        this.setArticleId(fileId);
    }
}
