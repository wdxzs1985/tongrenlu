package info.tongrenlu;

import info.tongrenlu.solr.ArticleDocument;

import java.util.Collection;

public class ArticleBean {

    private String articleId;

    private String title;

    private Collection<String> circleList;

    public String getArticleId() {
        return this.articleId;
    }

    public void setArticleId(final String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Collection<String> getCircleList() {
        return this.circleList;
    }

    public void setCircleList(final Collection<String> circleList) {
        this.circleList = circleList;
    }

    public ArticleDocument toDocument() {
        final ArticleDocument articleDocument = new ArticleDocument();
        articleDocument.setId(this.getArticleId());
        articleDocument.setArticleId(this.getArticleId());
        articleDocument.setTitle(this.getTitle());
        articleDocument.setCircleList(this.getCircleList());
        return articleDocument;
    }

}
