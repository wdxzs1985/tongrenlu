package info.tongrenlu.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class ArticleBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private UserBean userBean;

    private String title;

    private String description;

    private String publishFlg;

    private Date publishDate;

    private String recommendFlg;

    private int accessCount = 0;

    private int likeCount = 0;

    private int commentCount = 0;

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

    public Integer getAccessCount() {
        return this.accessCount;
    }

    public void setAccessCount(final Integer accessCount) {
        this.accessCount = accessCount;
    }

    public Integer getCommentCount() {
        return this.commentCount;
    }

    public void setCommentCount(final Integer commentCount) {
        this.commentCount = commentCount;
    }

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

    public String getRecommendFlg() {
        return this.recommendFlg;
    }

    public void setRecommendFlg(final String recommendFlg) {
        this.recommendFlg = recommendFlg;
    }

    public int getLikeCount() {
        return this.likeCount;
    }

    public void setLikeCount(final int likeCount) {
        this.likeCount = likeCount;
    }

}
