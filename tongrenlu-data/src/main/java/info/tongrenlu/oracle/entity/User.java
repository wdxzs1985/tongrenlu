package info.tongrenlu.oracle.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "M_USER")
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USER_ID")
    String userId;

    @Column(name = "NICKNAME")
    String nickname;

    @Column(name = "EMAIL")
    String email;

    @Column(name = "PASSWORD")
    String password;

    @Column(name = "ADMIN_FLG")
    String adminFlg;

    @Column(name = "RED_FLG")
    String redFlg;

    @Column(name = "TRANSLATE_FLG")
    String translateFlg;

    @Column(name = "SIGNATURE")
    String signature;

    @Column(name = "DEL_FLG")
    String delFlg;

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

    public String getAdminFlg() {
        return this.adminFlg;
    }

    public void setAdminFlg(final String adminFlg) {
        this.adminFlg = adminFlg;
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

    public String getDelFlg() {
        return this.delFlg;
    }

    public void setDelFlg(final String delFlg) {
        this.delFlg = delFlg;
    }

}
