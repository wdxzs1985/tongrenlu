package info.tongrenlu;

public class TrackBean {

    private String articleId;

    private String title;

    private String fileId;

    private String track;

    private String vocal;

    private String arranger;

    private Boolean vocalOff;

    private Integer trackNumber;

    public String getArticleId() {
        return this.articleId;
    }

    public void setArticleId(final String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getFileId() {
        return this.fileId;
    }

    public void setFileId(final String fileId) {
        this.fileId = fileId;
    }

    public String getTrack() {
        return this.track;
    }

    public void setTrack(final String track) {
        this.track = track;
    }

    public String getVocal() {
        return this.vocal;
    }

    public void setVocal(final String vocal) {
        this.vocal = vocal;
    }

    public String getArranger() {
        return this.arranger;
    }

    public void setArranger(final String arranger) {
        this.arranger = arranger;
    }

    public Boolean getVocalOff() {
        return this.vocalOff;
    }

    public void setVocalOff(final Boolean vocalOff) {
        this.vocalOff = vocalOff;
    }

    public Integer getTrackNumber() {
        return this.trackNumber;
    }

    public void setTrackNumber(final Integer trackNumber) {
        this.trackNumber = trackNumber;
    }
}
