package info.tongrenlu.entity;

public class MusicEntity extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MusicEntity(final String objectId) {
        super(0, objectId);
    }

    public MusicEntity() {
    }

    public String getArticleId() {
        return this.getObjectId();
    }

    public void setArticleId(final String articleId) {
        this.setObjectId(articleId);
    }

}
