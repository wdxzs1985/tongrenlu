package info.tongrenlu.entity;

public class UserEntity extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public UserEntity() {
    }

    public UserEntity(final Integer id, final String objectId, final String nickname, final String email, final String password, final String adminFlg, final String redFlg, final String translateFlg, final String signature) {
        super(id, objectId);
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.adminFlg = adminFlg;
        this.redFlg = redFlg;
        this.translateFlg = translateFlg;
        this.signature = signature;
    }

    String nickname;

    String email;

    String password;

    String adminFlg;

    String redFlg;

    String translateFlg;

    String signature;

    public String getUserId() {
        return this.getObjectId();
    }

    public void setUserId(final String userId) {
        this.setObjectId(userId);
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

}
