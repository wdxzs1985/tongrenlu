package info.tongrenlu.domain;

import java.io.Serializable;
import java.util.Date;

public abstract class DtoBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Date updDate;

    private String delFlg;

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Date getUpdDate() {
        return this.updDate;
    }

    public void setUpdDate(final Date updDate) {
        this.updDate = updDate;
    }

    public String getDelFlg() {
        return this.delFlg;
    }

    public void setDelFlg(final String delFlg) {
        this.delFlg = delFlg;
    }

}
