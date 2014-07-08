package info.tongrenlu.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class CommentBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String content = null;

    private Date createDate = null;

    private ArticleBean articleBean = null;

    private UserBean userBean = null;

    private CommentBean parent = null;

    private CommentBean root = null;

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

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    public CommentBean getParent() {
        return this.parent;
    }

    public void setParent(final CommentBean parent) {
        this.parent = parent;
    }

    public CommentBean getRoot() {
        return this.root;
    }

    public void setRoot(final CommentBean root) {
        this.root = root;
    }

}
