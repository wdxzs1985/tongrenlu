package info.tongrenlu.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class UserProfileBean extends UserBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Integer musicCount;

    private Integer comicCount;

    private Integer followCount;

    private Integer followerCount;

    public Integer getMusicCount() {
        return this.musicCount;
    }

    public void setMusicCount(final Integer musicCount) {
        this.musicCount = musicCount;
    }

    public Integer getComicCount() {
        return this.comicCount;
    }

    public void setComicCount(final Integer comicCount) {
        this.comicCount = comicCount;
    }

    public Integer getFollowCount() {
        return this.followCount;
    }

    public void setFollowCount(final Integer followCount) {
        this.followCount = followCount;
    }

    public Integer getFollowerCount() {
        return this.followerCount;
    }

    public void setFollowerCount(final Integer followerCount) {
        this.followerCount = followerCount;
    }

}
