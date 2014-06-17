package info.tongrenlu.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class WikiBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3480506669523352221L;

    private String wikiId = null;

    private String keyword = null;

    private int version = 0;

    private String type = null;

    private String content = null;

    private String userId = null;

    public String getWikiId() {
        return this.wikiId;
    }

    public void setWikiId(final String wikiId) {
        this.wikiId = wikiId;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }
}
