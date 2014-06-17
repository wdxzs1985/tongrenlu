package info.tongrenlu.persistence;

import info.tongrenlu.domain.TrackBean;

import java.util.List;
import java.util.Map;

public interface MTrackMapper {

    public void insertTrack(TrackBean trackBean);

    public void deleteTrack(Map<String, Object> param);

    public void updateTrackBean(TrackBean trackBean);

    public int getTrackCount(Map<String, Object> param);

    public List<TrackBean> getTrackList(Map<String, Object> param);

}
