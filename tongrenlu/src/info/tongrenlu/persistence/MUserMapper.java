package info.tongrenlu.persistence;

import info.tongrenlu.domain.UserBean;

import java.util.List;
import java.util.Map;

public interface MUserMapper {

    public void insertUser(UserBean userBean);

    public int countUser(Map<String, Object> param);

    public UserBean fetchUser(Map<String, Object> param);

    public void updateUser(Map<String, Object> param);

    public UserBean fetchUserInfo(Map<String, Object> param);

    public List<UserBean> fetchUserList(Map<String, Object> param);

}
