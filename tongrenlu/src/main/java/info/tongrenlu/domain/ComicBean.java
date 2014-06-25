package info.tongrenlu.domain;

import info.tongrenlu.constants.CommonConstants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class ComicBean extends ArticleBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String redFlg = null;

    private String translateFlg = null;

    public String getRedFlg() {
        return this.redFlg;
    }

    public void setRedFlg(final String redFlg) {
        this.redFlg = redFlg;
    }

    public String getTranslateFlg() {
        return this.translateFlg;
    }

    public void setTranslateFlg(final String translateFlg) {
        this.translateFlg = translateFlg;
    }

    public boolean isRed() {
        return CommonConstants.is(this.redFlg);
    }

    public boolean isTranslate() {
        return CommonConstants.is(this.translateFlg);
    }
}
