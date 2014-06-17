package info.tongrenlu.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class FileBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6430711548835136372L;

    private String fileId = null;

    private String articleId = null;

    private String rawId = null;

    private String name = null;

    private String extension = null;

    private long size = 0L;

    private int orderNo = 0;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(final long size) {
        this.size = size;
    }

    public String getFileId() {
        return this.fileId;
    }

    public void setFileId(final String fileId) {
        this.fileId = fileId;
    }

    public String getArticleId() {
        return this.articleId;
    }

    public void setArticleId(final String articleId) {
        this.articleId = articleId;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(final String extension) {
        this.extension = extension;
    }

    public String getRawId() {
        return this.rawId;
    }

    public void setRawId(final String rawId) {
        this.rawId = rawId;
    }

    public int getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(final int orderNo) {
        this.orderNo = orderNo;
    }

}
