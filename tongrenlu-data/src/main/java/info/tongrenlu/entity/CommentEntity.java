package info.tongrenlu.entity;

public class CommentEntity extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String articleId;

    private String userId;

    private ArticleEntity article;

    private UserEntity user;

    public CommentEntity(final String commentId, final String articleId,
            final String userId, final String content) {
        super(0, commentId);
        this.articleId = articleId;
        this.userId = userId;
        this.content = content;
    }

    public CommentEntity() {
        // TODO Auto-generated constructor stub
    }

    private String content;

    public String getCommentId() {
        return this.getObjectId();
    }

    public void setCommentId(final String commentId) {
        this.setObjectId(commentId);
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

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

}
