package info.tongrenlu.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class TagBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4139169633724833648L;

    private String tagId = null;

    private String tag = null;

    private String style = null;

    private String delFlg = null;

    private int articleCount = 0;

    public String getTagId() {
        return this.tagId;
    }

    public void setTagId(final String tagId) {
        this.tagId = tagId;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(final String style) {
        this.style = style;
    }

    public String getDelFlg() {
        return this.delFlg;
    }

    public void setDelFlg(final String delFlg) {
        this.delFlg = delFlg;
    }

    public int getArticleCount() {
        return this.articleCount;
    }

    public void setArticleCount(final int articleCount) {
        this.articleCount = articleCount;
    }
}
