package info.tongrenlu.entity;

import java.util.Date;

public class ArticleEntity extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ArticleEntity(final Integer id, final String objectId, final String title, final String description, final String userId, final String publishFlg, final Date publishDate, final String recommend) {
        super(id, objectId);
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.publishFlg = publishFlg;
        this.publishDate = publishDate;
        this.recommend = recommend;
    }

    public ArticleEntity() {
    }

    private String title;
    private String description;
    private String userId;
    private String publishFlg;
    private Date publishDate;
    private String recommend;

    private UserEntity user;

    public String getArticleId() {
        return this.getObjectId();
    }

    public void setArticleId(final String articleId) {
        this.setObjectId(articleId);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getPublishFlg() {
        return this.publishFlg;
    }

    public void setPublishFlg(final String publishFlg) {
        this.publishFlg = publishFlg;
    }

    public Date getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(final Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getRecommend() {
        return this.recommend;
    }

    public void setRecommend(final String recommend) {
        this.recommend = recommend;
    }

    public UserEntity getUser() {
        return this.user;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }

}
