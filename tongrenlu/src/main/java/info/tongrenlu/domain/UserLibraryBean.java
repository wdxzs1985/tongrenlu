package info.tongrenlu.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class UserLibraryBean extends DtoBean {

    private UserBean userBean;

    private ArticleBean articleBean;

    private Integer status;

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

    public ArticleBean getArticleBean() {
        return this.articleBean;
    }

    public void setArticleBean(final ArticleBean articleBean) {
        this.articleBean = articleBean;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

}
