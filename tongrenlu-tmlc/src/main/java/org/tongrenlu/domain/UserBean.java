package org.tongrenlu.domain;

public class UserBean extends DtoBean {

    public static final Integer ROLE_GUEST = 0;
    // 1
    public static final Integer ROLE_MEMBER = Integer.valueOf("0000000001", 2);
    // 3
    public static final Integer ROLE_EDITOR = Integer.valueOf("0000000010", 2);
    // 7
    public static final Integer ROLE_EDIT_ADMIN = Integer.valueOf("0000000100",
                                                                  2);
    // 9
    public static final Integer ROLE_SHOP_ADMIN = Integer.valueOf("0000001000",
                                                                  2);
    // 513
    public static final Integer ROLE_SUPER_ADMIN = Integer.valueOf("1000000000",
                                                                   2);
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

    private Integer role = ROLE_GUEST;

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

    public boolean isGuest() {
        return this.role == ROLE_GUEST;
    }

    public boolean isMember() {
        return (ROLE_MEMBER & (this.role)) == ROLE_MEMBER;
    }

    public boolean isEditor() {
        return (ROLE_EDITOR & (this.role)) == ROLE_EDITOR;
    }

    public boolean isEditAdmin() {
        return (ROLE_EDIT_ADMIN & (this.role)) == ROLE_EDIT_ADMIN;
    }

    public boolean isShopAdmin() {
        return (ROLE_SHOP_ADMIN & (this.role)) == ROLE_SHOP_ADMIN;
    }

    public boolean isSuperAdmin() {
        return (ROLE_SUPER_ADMIN & (this.role)) == ROLE_SUPER_ADMIN;
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
