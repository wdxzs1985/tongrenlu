package info.tongrenlu.domain;

import info.tongrenlu.constants.CommonConstants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class UserBean extends DtoBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String nickname;

    private String signature;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String password2;

    @JsonIgnore
    private String salt;

    @JsonIgnore
    private String fingerprint;

    @JsonIgnore
    private String role;

    @JsonIgnore
    private String includeRedFlg;

    @JsonIgnore
    private String onlyTranslateFlg;

    @JsonIgnore
    private String onlyVocalFlg;

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

    public String getRole() {
        return this.role;
    }

    public void setRole(final String role) {
        this.role = role;
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

    public String getIncludeRedFlg() {
        return this.includeRedFlg;
    }

    public void setIncludeRedFlg(final String includeRedFlg) {
        this.includeRedFlg = includeRedFlg;
    }

    public String getOnlyTranslateFlg() {
        return this.onlyTranslateFlg;
    }

    public void setOnlyTranslateFlg(final String onlyTranslateFlg) {
        this.onlyTranslateFlg = onlyTranslateFlg;
    }

    public String getOnlyVocalFlg() {
        return this.onlyVocalFlg;
    }

    public void setOnlyVocalFlg(final String onlyVocalFlg) {
        this.onlyVocalFlg = onlyVocalFlg;
    }

    @Override
    public String toString() {
        if (this.getId() > 0) {
            return this.nickname + "#" + this.getId();
        }
        return this.nickname;
    }

    public boolean isGuest() {
        return this.role == null;
    }

    public boolean isMember() {
        return CommonConstants.ROLE_MEMBER.equals(this.role);
    }

    public boolean isEditor() {
        return CommonConstants.ROLE_EDITOR.equals(this.role);
    }

    public boolean isAdmin() {
        return CommonConstants.ROLE_ADMIN.equals(this.role);
    }

    public boolean isIncludeRed() {
        return CommonConstants.is(this.includeRedFlg);
    }

    public boolean isOnlyTranslate() {
        return CommonConstants.is(this.onlyTranslateFlg);
    }

    public boolean isOnlyVocal() {
        return CommonConstants.is(this.onlyVocalFlg);
    }
}
