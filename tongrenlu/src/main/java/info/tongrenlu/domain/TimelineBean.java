package info.tongrenlu.domain;

import java.util.Date;

public class TimelineBean {

    private UserBean userBean = null;

    private ArticleBean articleBean = null;

    private String content = null;

    private Date createDate = null;

    private String action = null;

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    public ArticleBean getArticleBean() {
        return this.articleBean;
    }

    public void setArticleBean(final ArticleBean articleBean) {
        this.articleBean = articleBean;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

}
