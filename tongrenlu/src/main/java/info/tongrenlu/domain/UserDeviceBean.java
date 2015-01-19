package info.tongrenlu.domain;

public class UserDeviceBean extends DtoBean {

    private String fingerprint;

    private String userAgent;

    private UserBean userBean;

    public String getFingerprint() {
        return this.fingerprint;
    }

    public void setFingerprint(final String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

}
