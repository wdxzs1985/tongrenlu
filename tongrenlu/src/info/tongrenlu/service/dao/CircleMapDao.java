package info.tongrenlu.service.dao;

import info.tongrenlu.domain.CircleBean;
import info.tongrenlu.persistence.RCircleMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CircleMapDao {

    @Autowired
    private RCircleMapper circleMapper = null;

    public List<CircleBean> getCircleList(final String eventCode,
                                          final String searchArea,
                                          final String userId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("searchArea", searchArea);
        param.put("eventCode", eventCode);
        param.put("userId", userId);
        final List<CircleBean> items = this.circleMapper.getCircleListByArea(param);
        return items;
    }

    public List<CircleBean> getCircleLikeList(final String eventCode,
                                              final String userId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("eventCode", eventCode);
        param.put("userId", userId);
        final List<CircleBean> items = this.circleMapper.getCircleListByUser(param);
        return items;
    }

    public void createLike(final String userId, final String circleId) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("circleId", circleId);
        this.circleMapper.createUserCircle(params);
    }

    public void removeLike(final String userId, final String circleId) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("circleId", circleId);
        this.circleMapper.removeUserCircle(params);
    }

    public List<CircleBean> getCircleListByUser(final String userId) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return this.circleMapper.getCircleListByUser(params);
    }

    public int countCircleByUser(final String userId, final String circleId) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("circleId", circleId);
        return this.circleMapper.countCircleByUser(params);
    }

    public List<Object> getCircleCountByArea(final String eventCode) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("eventCode", eventCode);
        return this.circleMapper.getCircleCountByArea(params);
    }
}
