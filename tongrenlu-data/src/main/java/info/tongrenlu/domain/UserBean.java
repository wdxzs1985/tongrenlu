package info.tongrenlu.domain;


public class UserBean extends DtoBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String nickname;

    private String signature;

    private String weibo;

    private String email;

    private String password;

    private String password2;

    private String salt;

    private String fingerprint;

    private Integer role = 0;

    private Integer includeRedFlg;

    private Integer onlyTranslateFlg;

    private Integer onlyVocalFlg;

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(final String signature) {
        this.signature = signature;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(final String salt) {
        this.salt = salt;
    }

    public String getFingerprint() {
        return this.fingerprint;
    }

    public void setFingerprint(final String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getPassword2() {
        return this.password2;
    }

    public void setPassword2(final String password2) {
        this.password2 = password2;
    }

    @Override
    public String toString() {
        if (this.getId() > 0) {
            return this.nickname + "#" + this.getId();
        }
        return this.nickname;
    }

    public String getWeibo() {
        return this.weibo;
    }

    public void setWeibo(final String weibo) {
        this.weibo = weibo;
    }

    public Integer getRole() {
        return this.role;
    }

    public void setRole(final Integer role) {
        this.role = role;
    }

    public Integer getIncludeRedFlg() {
        return this.includeRedFlg;
    }

    public void setIncludeRedFlg(final Integer includeRedFlg) {
        this.includeRedFlg = includeRedFlg;
    }

    public Integer getOnlyTranslateFlg() {
        return this.onlyTranslateFlg;
    }

    public void setOnlyTranslateFlg(final Integer onlyTranslateFlg) {
        this.onlyTranslateFlg = onlyTranslateFlg;
    }

    public Integer getOnlyVocalFlg() {
        return this.onlyVocalFlg;
    }

    public void setOnlyVocalFlg(final Integer onlyVocalFlg) {
        this.onlyVocalFlg = onlyVocalFlg;
    }

}
