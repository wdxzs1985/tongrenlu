package info.tongrenlu.mapper;

import info.tongrenlu.domain.TrackBean;

import java.util.List;
import java.util.Map;

public interface TrackMapper {

    void insert(TrackBean trackBean);

    void delete(TrackBean trackBean);

    void update(Map<String, Object> param);

    List<TrackBean> fetchList(Map<String, Object> param);

}
