package info.tongrenlu.domain;

public class AccessBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private ArticleBean articleBean;

    private UserBean userBean;

    public ArticleBean getArticleBean() {
        return this.articleBean;
    }

    public void setArticleBean(final ArticleBean articleBean) {
        this.articleBean = articleBean;
    }

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }
}
