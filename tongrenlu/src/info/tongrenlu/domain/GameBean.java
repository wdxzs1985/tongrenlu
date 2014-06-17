package info.tongrenlu.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class GameBean extends ArticleBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7645042649885498544L;
    private String screenshotId = null;

    public String getScreenshotId() {
        return this.screenshotId;
    }

    public void setScreenshotId(final String screenshotId) {
        this.screenshotId = screenshotId;
    }
}
