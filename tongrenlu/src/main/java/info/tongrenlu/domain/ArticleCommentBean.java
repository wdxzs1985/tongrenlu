package info.tongrenlu.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class ArticleCommentBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String content = null;

    private Date addDate = null;

    private ArticleBean articleBean = null;

    private UserBean userBean = null;

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public ArticleBean getArticleBean() {
        return this.articleBean;
    }

    public void setArticleBean(final ArticleBean articleBean) {
        this.articleBean = articleBean;
    }

    public Date getAddDate() {
        return this.addDate;
    }

    public void setAddDate(final Date addDate) {
        this.addDate = addDate;
    }

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

}
