package info.tongrenlu.mapper;

import info.tongrenlu.domain.AuthFileBean;
import info.tongrenlu.domain.UserProfileBean;

import java.util.List;
import java.util.Map;

public interface AuthFileMapper {

    public List<AuthFileBean> fetchList(Map<String, Object> params);

    public void insert(AuthFileBean authFileBean);

    public void delete(AuthFileBean authFileBean);

    public AuthFileBean fetchBean(Map<String, Object> params);

    public void updateStatus(Map<String, Object> params);

    public int countUser(Map<String, Object> params);

    public List<UserProfileBean> searchUserList(Map<String, Object> params);

}
