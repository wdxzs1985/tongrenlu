package info.tongrenlu.mapper;

import info.tongrenlu.domain.TimelineBean;

import java.util.List;
import java.util.Map;

public interface TimelineMapper {

    int countUserTimeline(Map<String, Object> params);

    List<TimelineBean> searchUserTimeline(Map<String, Object> params);

    int countFollowTimeline(Map<String, Object> params);

    List<TimelineBean> searchFollowTimeline(Map<String, Object> params);

}
