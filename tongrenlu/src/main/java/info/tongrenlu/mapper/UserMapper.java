package info.tongrenlu.mapper;

import info.tongrenlu.domain.UserBean;

import java.util.Map;

public interface UserMapper {

    public UserBean fetchBean(Map<String, Object> param);

    public void insert(UserBean userBean);

    public void update(Map<String, Object> param);

}
