package info.tongrenlu.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class TagBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String tag = null;

    private String type = null;

    private Integer articleCount = 0;

    public String getTag() {
        return this.tag;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    public int getArticleCount() {
        return this.articleCount;
    }

    public void setArticleCount(final int articleCount) {
        this.articleCount = articleCount;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
