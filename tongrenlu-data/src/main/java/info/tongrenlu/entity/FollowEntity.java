package info.tongrenlu.entity;

public class FollowEntity extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String followId;

    private String userId;

    private UserEntity follow;

    private UserEntity user;

    private String category;

    public FollowEntity(final String followId, final String userId) {
        super();
        this.followId = followId;
        this.userId = userId;
    }

    public FollowEntity() {
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public UserEntity getUser() {
        return this.user;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    public String getFollowId() {
        return this.followId;
    }

    public void setFollowId(final String followId) {
        this.followId = followId;
    }

    public UserEntity getFollow() {
        return this.follow;
    }

    public void setFollow(final UserEntity follow) {
        this.follow = follow;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
