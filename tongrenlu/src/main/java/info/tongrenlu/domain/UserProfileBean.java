package info.tongrenlu.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class UserProfileBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String nickname;

    private String signature;

    private int musicCount = 0;

    private int comicCount = 0;

    private int collectCount = 0;

    private int followCount = 0;

    private int fansCount = 0;

    public String getNickname() {
        return this.nickname + "#" + this.getId();
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

    @Override
    public String toString() {
        return this.nickname + "#" + this.getId();
    }
}
