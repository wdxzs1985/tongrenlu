package info.tongrenlu.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class TrackRateBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private TrackBean trackBean;

    private UserBean userBean;

    private Integer rate;

    public TrackBean getTrackBean() {
        return this.trackBean;
    }

    public void setTrackBean(final TrackBean trackBean) {
        this.trackBean = trackBean;
    }

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

    public Integer getRate() {
        return this.rate;
    }

    public void setRate(final Integer rate) {
        this.rate = rate;
    }

}
