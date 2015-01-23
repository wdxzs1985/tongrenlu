package info.tongrenlu.domain;

public class NotificationBean extends DtoBean {

    private UserBean userBean;

    private UserBean sender;

    private ArticleBean articleBean;

    private String category;

    private String action;

    private String content;

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

    public UserBean getSender() {
        return this.sender;
    }

    public void setSender(final UserBean sender) {
        this.sender = sender;
    }

    public ArticleBean getArticleBean() {
        return this.articleBean;
    }

    public void setArticleBean(final ArticleBean articleBean) {
        this.articleBean = articleBean;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(final String action) {
        this.action = action;
    }

}
