package info.tongrenlu.mapper;

import info.tongrenlu.domain.UserBean;

import java.util.List;
import java.util.Map;

public interface LikeMapper {

    int count(Map<String, Object> params);

    void insert(Map<String, Object> params);

    void delete(Map<String, Object> params);

    int countFollow(Map<String, Object> params);

    int countFollower(Map<String, Object> params);

    List<UserBean> searchFollow(Map<String, Object> params);

    List<UserBean> searchFollower(Map<String, Object> params);

}
