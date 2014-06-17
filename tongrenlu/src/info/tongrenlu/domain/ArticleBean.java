package info.tongrenlu.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class ArticleBean {

    private String articleId;

    private String title;

    private String description;

    private String publishFlg;

    private Date publishDate;

    private String recommend;

    private String type = null;

    private Integer accessCount;

    private Integer collectCount;

    private Integer collectFlg;

    private Integer commentCount;

    private UserBean userBean;

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

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Integer getCollectCount() {
        return this.collectCount;
    }

    public void setCollectCount(final Integer collectCount) {
        this.collectCount = collectCount;
    }

    public String getRecommend() {
        return this.recommend;
    }

    public void setRecommend(final String recommend) {
        this.recommend = recommend;
    }

    public Integer getCollectFlg() {
        return this.collectFlg;
    }

    public void setCollectFlg(final Integer collectFlg) {
        this.collectFlg = collectFlg;
    }

}
