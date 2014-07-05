package info.tongrenlu.entity;

public class ArticleTagEntity extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String articleId;

    private String tagId;

    private ArticleEntity article;

    private TagEntity tag;

    public ArticleTagEntity(final String articleId, final String tagId) {
        super();
        this.articleId = articleId;
        this.tagId = tagId;
    }

    public ArticleTagEntity() {
        // TODO Auto-generated constructor stub
    }

    public String getArticleId() {
        return this.articleId;
    }

    public void setArticleId(final String articleId) {
        this.articleId = articleId;
    }

    public String getTagId() {
        return this.tagId;
    }

    public void setTagId(final String tagId) {
        this.tagId = tagId;
    }

    public ArticleEntity getArticle() {
        return this.article;
    }

    public void setArticle(final ArticleEntity article) {
        this.article = article;
    }

    public TagEntity getTag() {
        return this.tag;
    }

    public void setTag(final TagEntity tag) {
        this.tag = tag;
    }

}
