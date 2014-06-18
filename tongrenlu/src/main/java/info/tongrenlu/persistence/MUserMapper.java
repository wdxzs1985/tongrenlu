package info.tongrenlu.persistence;

import info.tongrenlu.domain.UserBean;

import java.util.List;
import java.util.Map;

public interface MUserMapper {

    public void insert(UserBean userBean);

    public void delete(UserBean userBean);

    public int count(Map<String, Object> param);

    public UserBean fetchBean(Map<String, Object> param);

    public List<UserBean> fetchList(Map<String, Object> param);

    public void update(Map<String, Object> param);

}
