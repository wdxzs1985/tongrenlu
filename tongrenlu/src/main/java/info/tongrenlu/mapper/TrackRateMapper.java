package info.tongrenlu.mapper;

import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.domain.TrackRateBean;

import java.util.List;
import java.util.Map;

public interface TrackRateMapper {

    int count(Map<String, Object> params);

    void insert(TrackRateBean trackRateBean);

    void update(Map<String, Object> params);

    List<TrackBean> fetchList(Map<String, Object> param);

}
