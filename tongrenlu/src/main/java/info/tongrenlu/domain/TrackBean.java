package info.tongrenlu.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class TrackBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3132800283486952794L;

    private String album = null;

    private String songTitle = null;

    private String leadArtist = null;

    private String originalTitle = null;

    private int trackNumber = 0;

    private FileBean fileBean = null;

    // SongComment

    // SongLyric

    // AuthorComposer

    public String getSongTitle() {
        return this.songTitle;
    }

    public void setSongTitle(final String songTitle) {
        this.songTitle = songTitle;
    }

    public String getLeadArtist() {
        return this.leadArtist;
    }

    public void setLeadArtist(final String leadArtist) {
        this.leadArtist = leadArtist;
    }

    public FileBean getFileBean() {
        return this.fileBean;
    }

    public void setFileBean(final FileBean fileBean) {
        this.fileBean = fileBean;
    }

    public int getTrackNumber() {
        return this.trackNumber;
    }

    public void setTrackNumber(final int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public void setOriginalTitle(final String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getAlbum() {
        return this.album;
    }

    public void setAlbum(final String album) {
        this.album = album;
    }

}
