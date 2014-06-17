package info.tongrenlu.persistence;

import info.tongrenlu.domain.TimelineBean;

import java.util.List;
import java.util.Map;

public interface MTimelineMapper {

    public int countTimeline(Map<String, Object> param);

    public List<TimelineBean> fetchTimelineList(Map<String, Object> param);
}
