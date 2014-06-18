package info.tongrenlu.domain;

import java.util.Date;

public class TimelineBean {

    private UserBean userBean = null;

    private ArticleBean articleBean = null;

    private Date timeline = null;

    private String action = null;

    private String type = null;

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

    public Date getTimeline() {
        return this.timeline;
    }

    public void setTimeline(final Date timeline) {
        this.timeline = timeline;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public ArticleBean getArticleBean() {
        return this.articleBean;
    }

    public void setArticleBean(final ArticleBean articleBean) {
        this.articleBean = articleBean;
    }

}
