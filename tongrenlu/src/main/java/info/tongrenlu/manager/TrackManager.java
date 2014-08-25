package info.tongrenlu.manager;

import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.domain.TrackRateBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.mapper.TrackMapper;
import info.tongrenlu.mapper.TrackRateMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackManager {

    @Autowired
    private final TrackMapper trackMapper = null;
    @Autowired
    private final TrackRateMapper trackRateMapper = null;

    public List<TrackBean> getTrackList(final Integer articleId) {
        final Map<String, Object> param = new HashMap<>();
        param.put("articleId", articleId);
        return this.trackMapper.fetchList(param);
    }

    public List<TrackBean> getRatedTrackList(final Integer articleId,
                                             final UserBean userBean) {
        final Map<String, Object> param = new HashMap<>();
        param.put("articleId", articleId);
        param.put("userBean", userBean);
        return this.trackRateMapper.fetchList(param);
    }

    public void addTrack(final TrackBean trackBean) {
        this.trackMapper.insert(trackBean);
    }

    public void updateTrack(final TrackBean trackBean) {
        final Map<String, Object> param = new HashMap<>();
        param.put("id", trackBean.getId());
        param.put("name", trackBean.getName());
        param.put("artist", trackBean.getArtist());
        param.put("original", trackBean.getOriginal());
        param.put("instrumental", trackBean.getInstrumental());
        this.trackMapper.update(param);
    }

    public void deleteTrack(final TrackBean trackBean) {
        this.trackMapper.delete(trackBean);
    }

    public int countRate(final TrackRateBean trackRateBean) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("trackRateBean", trackRateBean);
        return this.trackRateMapper.count(params);
    }

    public void insertRate(final TrackRateBean trackRateBean) {
        this.trackRateMapper.insert(trackRateBean);
    }

    public void updateRate(final TrackRateBean trackRateBean) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("trackRateBean", trackRateBean);
        this.trackRateMapper.update(params);
    }
}
