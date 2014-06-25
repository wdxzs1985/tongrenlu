package info.tongrenlu.solr;

import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "collection3")
public class MusicDocument extends ArticleDocument {

    public static final String MUSIC = "music";

    public static boolean isMusic(final ArticleDocument articleDocument) {
        return MUSIC.equals(articleDocument.getCategory());
    }

    public MusicDocument() {
        this.setCategory(MUSIC);
    }
}
