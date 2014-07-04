package info.tongrenlu.entity;

public class TrackEntity extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public TrackEntity(final Integer id, final String objectId, final String name, final String artist, final String original) {
        super(id, objectId);
        this.name = name;
        this.artist = artist;
        this.original = original;
    }

    public TrackEntity() {
    }

    private String name;

    private String artist;

    private String instrumental;

    private String original;

    private FileEntity file;

    public String getFileId() {
        return this.getObjectId();
    }

    public void setFileId(final String fileId) {
        this.setObjectId(fileId);
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(final String artist) {
        this.artist = artist;
    }

    public String getInstrumental() {
        return this.instrumental;
    }

    public void setInstrumental(final String instrumental) {
        this.instrumental = instrumental;
    }

    public String getOriginal() {
        return this.original;
    }

    public void setOriginal(final String original) {
        this.original = original;
    }

    public FileEntity getFile() {
        return this.file;
    }

    public void setFile(final FileEntity file) {
        this.file = file;
    }

}
