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

    private String weibo;

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
    private Integer role = CommonConstants.ROLE_GUEST;

    @JsonIgnore
    private Integer includeRedFlg;

    @JsonIgnore
    private Integer onlyTranslateFlg;

    @JsonIgnore
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

    public boolean isGuest() {
        return this.role == CommonConstants.ROLE_GUEST;
    }

    public boolean isMember() {
        return (CommonConstants.ROLE_MEMBER & (this.role)) == CommonConstants.ROLE_MEMBER;
    }

    public boolean isEditor() {
        return (CommonConstants.ROLE_EDITOR & (this.role)) == CommonConstants.ROLE_EDITOR;
    }

    public boolean isEditAdmin() {
        return (CommonConstants.ROLE_EDIT_ADMIN & (this.role)) == CommonConstants.ROLE_EDIT_ADMIN;
    }

    public boolean isShopAdmin() {
        return (CommonConstants.ROLE_SHOP_ADMIN & (this.role)) == CommonConstants.ROLE_SHOP_ADMIN;
    }

    public boolean isSuperAdmin() {
        return (CommonConstants.ROLE_SUPER_ADMIN & (this.role)) == CommonConstants.ROLE_SUPER_ADMIN;
    }

    public boolean isOnlyVocal() {
        return CommonConstants.is(this.onlyVocalFlg);
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
