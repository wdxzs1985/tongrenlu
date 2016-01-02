package org.tongrenlu.domain;

public class TrackBean extends DtoBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private FileBean fileBean = null;

    private String name = null;

    private String artist = null;

    private String original = null;

    private String instrumental = null;

    private int disc = 0;

    private int rate = 0;

    public FileBean getFileBean() {
        return this.fileBean;
    }

    public void setFileBean(final FileBean fileBean) {
        this.fileBean = fileBean;
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

    public String getOriginal() {
        return this.original;
    }

    public void setOriginal(final String original) {
        this.original = original;
    }

    public String getInstrumental() {
        return this.instrumental;
    }

    public void setInstrumental(final String instrumental) {
        this.instrumental = instrumental;
    }

    public int getRate() {
        return this.rate;
    }

    public void setRate(final int rate) {
        this.rate = rate;
    }

    public int getDisc() {
        return this.disc;
    }

    public void setDisc(int disc) {
        this.disc = disc;
    }

}
