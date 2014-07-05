package info.tongrenlu.entity;

public class CollectEntity extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String articleId;

    private String userId;

    private ArticleEntity article;

    private UserEntity user;

    private String category;

    public CollectEntity(final String articleId, final String userId) {
        super();
        this.articleId = articleId;
        this.userId = userId;
    }

    public CollectEntity() {
    }

    public String getArticleId() {
        return this.articleId;
    }

    public void setArticleId(final String articleId) {
        this.articleId = articleId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public ArticleEntity getArticle() {
        return this.article;
    }

    public void setArticle(final ArticleEntity article) {
        this.article = article;
    }

    public UserEntity getUser() {
        return this.user;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }
}
