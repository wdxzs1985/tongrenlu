package info.tongrenlu.mapper;

import info.tongrenlu.domain.UserBean;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    public void insert(UserBean userBean);

    public UserBean fetchBean(Map<String, Object> param);

    public List<UserBean> fetchList(Map<String, Object> param);

    public void update(Map<String, Object> param);

}
