package info.tongrenlu.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class PlaylistTrackBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private PlaylistBean playlistBean = null;

    private TrackBean trackBean = null;

    private int orderNo = 0;

    public PlaylistBean getPlaylistBean() {
        return this.playlistBean;
    }

    public void setPlaylistBean(final PlaylistBean playlistBean) {
        this.playlistBean = playlistBean;
    }

    public TrackBean getTrackBean() {
        return this.trackBean;
    }

    public void setTrackBean(final TrackBean trackBean) {
        this.trackBean = trackBean;
    }

    public int getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(final int orderNo) {
        this.orderNo = orderNo;
    }
}
