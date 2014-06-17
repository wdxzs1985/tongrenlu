package info.tongrenlu.persistence;

import info.tongrenlu.domain.UserBean;

import java.util.List;
import java.util.Map;


public interface RFollowMapper {

    public int countFollow(Map<String, Object> param);

    public void deleteFollow(Map<String, Object> param);

    public void insertFollow(Map<String, Object> param);

    public List<UserBean> fetchFollowList(Map<String, Object> param);

}
