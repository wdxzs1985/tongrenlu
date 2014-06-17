package info.tongrenlu.persistence;

import info.tongrenlu.domain.CircleBean;

import java.util.List;
import java.util.Map;

public interface RCircleMapper {

    public void createUserCircle(Map<String, Object> params);

    public void removeUserCircle(Map<String, Object> params);

    public List<CircleBean> getCircleListByArea(Map<String, Object> params);

    public List<CircleBean> getCircleListByUser(Map<String, Object> params);

    public int countCircleByUser(Map<String, Object> params);

    public List<Object> getCircleCountByArea(Map<String, Object> params);

}
