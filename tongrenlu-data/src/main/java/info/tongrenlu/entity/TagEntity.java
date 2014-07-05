package info.tongrenlu.entity;

public class TagEntity extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public TagEntity(final String objectId, final String tag) {
        super(0, objectId);
        this.tag = tag;
    }

    public TagEntity() {
    }

    private String tag;

    public String getTagId() {
        return this.getObjectId();
    }

    public void setTagId(final String tagId) {
        this.setObjectId(tagId);
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

}
