package info.tongrenlu.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class TrackBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int trackNumber = 0;

    private FileBean fileBean = null;

    private String name = null;

    private String artist = null;

    private String originalTitle = null;

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

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(final String artist) {
        this.artist = artist;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
