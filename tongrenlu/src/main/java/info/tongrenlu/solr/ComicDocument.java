package info.tongrenlu.solr;

import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "collection3")
public class ComicDocument extends ArticleDocument {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static final String COMIC = "comic";

    public static boolean isComic(final ArticleDocument articleDocument) {
        return ComicDocument.COMIC.equals(articleDocument.getCategory());
    }

    public ComicDocument() {
        this.setCategory(ComicDocument.COMIC);
    }

    public ComicDocument(final Integer articleId) {
        this.setCategory(COMIC);
        this.setId("c" + articleId);
        this.setArticleId(articleId);
    }
}
