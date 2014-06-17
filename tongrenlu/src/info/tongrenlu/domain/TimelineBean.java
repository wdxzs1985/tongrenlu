package info.tongrenlu.domain;

import java.util.Date;

public class TimelineBean {

    private UserBean userBean = null;

    private String articleId = null;

    private String title = null;

    private String content = null;

    private Date timeline = null;

    private String action = null;

    private String type = null;

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

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

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
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

}
