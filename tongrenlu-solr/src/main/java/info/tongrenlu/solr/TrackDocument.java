package info.tongrenlu.solr;

import info.tongrenlu.PlaylistTrackBean;
import info.tongrenlu.TrackBean;

import java.util.Collection;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "collection3")
public class TrackDocument {

    @Id
    @Field
    private String id;

    @Field
    private final String category = "track";

    @Field
    private String articleId;

    @Field
    private String title;

    @Field
    private String fileId;

    @Field
    private String track;

    @Field("artist")
    private Collection<String> artistList;

    @Field
    private Boolean vocalOff;

    @Field
    private Integer trackNumber;

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

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

    public Collection<String> getArtistList() {
        return this.artistList;
    }

    public void setArtistList(final Collection<String> artistList) {
        this.artistList = artistList;
    }

    public Boolean getVocalOff() {
        return this.vocalOff;
    }

    public void setVocalOff(final Boolean vocalOff) {
        this.vocalOff = vocalOff;
    }

    public String getCategory() {
        return this.category;
    }

    public Integer getTrackNumber() {
        return this.trackNumber;
    }

    public void setTrackNumber(final Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public TrackBean toTrackBean() {
        final TrackBean trackBean = new TrackBean();
        trackBean.setArticleId(this.getArticleId());
        trackBean.setFileId(this.getFileId());
        trackBean.setTitle(this.getTitle());
        trackBean.setTrack(this.getTrack());
        trackBean.setVocalOff(this.getVocalOff());
        return trackBean;
    }

    public PlaylistTrackBean toPlaylistTrackBean() {
        final PlaylistTrackBean trackBean = new PlaylistTrackBean();
        trackBean.setTitle(this.getTrack());
        trackBean.setMp3("http://www.jplayer.org/audio/mp3/TSP-01-Cro_magnon_man.mp3");
        trackBean.setPoster("http://www.jplayer.org/audio/poster/The_Stark_Palace_640x360.png");
        return trackBean;
    }

}
