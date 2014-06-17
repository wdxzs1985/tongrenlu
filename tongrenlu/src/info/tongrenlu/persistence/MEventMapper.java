package info.tongrenlu.persistence;

import info.tongrenlu.domain.EventBean;

import java.util.List;
import java.util.Map;


public interface MEventMapper {

    public int countEvent(Map<String, Object> param);

    public List<EventBean> fetchEventList(Map<String, Object> param);

    public void insertEvent(EventBean event);

}
