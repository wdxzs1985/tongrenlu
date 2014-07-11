package info.tongrenlu.solr;

import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "collection3")
public class MusicDocument extends ArticleDocument {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static final String MUSIC = "music";

    public static boolean isMusic(final ArticleDocument articleDocument) {
        return MUSIC.equals(articleDocument.getCategory());
    }

    public MusicDocument() {
        this.setCategory(MUSIC);
    }

    public MusicDocument(final Integer articleId) {
        this.setCategory(MUSIC);
        this.setId("m" + articleId);
        this.setArticleId(articleId);
    }
}
