package info.tongrenlu.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class FileBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Integer articleId = null;

    private String name = null;

    private String extension = null;

    private int orderNo = 0;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(final String extension) {
        this.extension = extension;
    }

    public int getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(final int orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getArticleId() {
        return this.articleId;
    }

    public void setArticleId(final Integer articleId) {
        this.articleId = articleId;
    }

}
