package info.tongrenlu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class UserBean extends DtoBean {

    public static final String ROLE_NORMAL = "0";

    public static final String ROLE_EDITOR = "1";

    public static final String ROLE_ADMIN = "9";

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
    private String redFlg;

    @JsonIgnore
    private String translateFlg;

    private int musicCount = 0;

    private int comicCount = 0;

    private int collectCount = 0;

    private int followCount = 0;

    private int fansCount = 0;

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
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

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(final String signature) {
        this.signature = signature;
    }

    public int getCollectCount() {
        return this.collectCount;
    }

    public void setCollectCount(final int collectCount) {
        this.collectCount = collectCount;
    }

    public int getFansCount() {
        return this.fansCount;
    }

    public void setFansCount(final int fansCount) {
        this.fansCount = fansCount;
    }

    public int getFollowCount() {
        return this.followCount;
    }

    public void setFollowCount(final int followCount) {
        this.followCount = followCount;
    }

    @Override
    public String toString() {
        return this.nickname + "#" + this.getId();
    }

    public int getMusicCount() {
        return this.musicCount;
    }

    public void setMusicCount(final int musicCount) {
        this.musicCount = musicCount;
    }

    public int getComicCount() {
        return this.comicCount;
    }

    public void setComicCount(final int comicCount) {
        this.comicCount = comicCount;
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

    public boolean isEditor() {
        return UserBean.ROLE_EDITOR.equals(this.role);
    }

    public boolean isAdmin() {
        return UserBean.ROLE_ADMIN.equals(this.role);
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

}
