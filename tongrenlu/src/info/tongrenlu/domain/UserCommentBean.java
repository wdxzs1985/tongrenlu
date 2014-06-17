package info.tongrenlu.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class UserCommentBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8331662772295951459L;

    private String commentId = null;

    private String content = null;

    private Date addDate = null;

    private UserBean userBean = null;

    private UserBean sender = null;

    public String getCommentId() {
        return this.commentId;
    }

    public void setCommentId(final String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

    public Date getAddDate() {
        return this.addDate;
    }

    public void setAddDate(final Date addDate) {
        this.addDate = addDate;
    }

    public UserBean getSender() {
        return this.sender;
    }

    public void setSender(final UserBean sender) {
        this.sender = sender;
    }

}
