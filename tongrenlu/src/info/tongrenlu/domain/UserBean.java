package info.tongrenlu.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class UserBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7273825309853765682L;

    private String userId;

    private String nickname;

    private String email;

    private String password;

    private Date lastLoginTime;

    private String lastLoginIp;

    private String lastLoginUa;

    private String adminFlg;

    private String redFlg;

    private String translateFlg;

    private String signature;

    private int musicCount = 0;

    private int comicCount = 0;

    private int gameCount = 0;

    private int collectCount = 0;

    private int followCount = 0;

    private int fansCount = 0;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

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

    public Date getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(final Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return this.lastLoginIp;
    }

    public void setLastLoginIp(final String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginUa() {
        return this.lastLoginUa;
    }

    public void setLastLoginUa(final String lastLoginUa) {
        this.lastLoginUa = lastLoginUa;
    }

    public static long getSerialversionuid() {
        return UserBean.serialVersionUID;
    }

    public String getRedFlg() {
        return this.redFlg;
    }

    public void setRedFlg(final String redFlg) {
        this.redFlg = redFlg;
    }

    public String getAdminFlg() {
        return this.adminFlg;
    }

    public void setAdminFlg(final String adminFlg) {
        this.adminFlg = adminFlg;
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
        return "@" + this.nickname + "(" + this.userId + ")";
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

    public int getGameCount() {
        return this.gameCount;
    }

    public void setGameCount(final int gameCount) {
        this.gameCount = gameCount;
    }
}
