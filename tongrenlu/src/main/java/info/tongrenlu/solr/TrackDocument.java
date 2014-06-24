package info.tongrenlu.solr;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "collection3")
public class TrackDocument {

    @Id
    @Field
    private String id;

    @Field
    private String category = "track";

    @Field
    private Integer articleId;

    @Field
    private String title;

    @Field
    private Integer fileId;

    @Field
    private String track;

    @Field
    private String[] artist;

    @Field
    private String[] original;

    @Field
    private Boolean instrumental = false;

    @Field
    private Integer trackNumber;

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public Integer getArticleId() {
        return this.articleId;
    }

    public void setArticleId(final Integer articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Integer getFileId() {
        return this.fileId;
    }

    public void setFileId(final Integer fileId) {
        this.fileId = fileId;
    }

    public String getTrack() {
        return this.track;
    }

    public void setTrack(final String track) {
        this.track = track;
    }

    public String[] getArtist() {
        return this.artist;
    }

    public void setArtist(final String[] artist) {
        this.artist = artist;
    }

    public String[] getOriginal() {
        return this.original;
    }

    public void setOriginal(final String[] original) {
        this.original = original;
    }

    public Boolean getInstrumental() {
        return this.instrumental;
    }

    public void setInstrumental(final Boolean instrumental) {
        this.instrumental = instrumental;
    }

    public Integer getTrackNumber() {
        return this.trackNumber;
    }

    public void setTrackNumber(final Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

}
