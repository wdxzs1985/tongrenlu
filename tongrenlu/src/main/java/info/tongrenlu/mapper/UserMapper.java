package info.tongrenlu.mapper;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.domain.UserProfileBean;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    public UserBean fetchBean(Map<String, Object> param);

    public UserProfileBean fetchProfile(Map<String, Object> param);

    public void insert(UserBean userBean);

    public void update(Map<String, Object> param);

    public int count(Map<String, Object> params);

    public List<UserBean> fetchList(Map<String, Object> params);

}
