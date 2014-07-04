package info.tongrenlu.entity;

public class MusicEntity extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MusicEntity(final Integer id, final String objectId) {
        super(id, objectId);
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
