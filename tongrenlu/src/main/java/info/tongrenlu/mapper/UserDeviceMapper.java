package info.tongrenlu.mapper;

import info.tongrenlu.domain.UserBean;

import java.util.Map;

public interface UserDeviceMapper {

    public UserBean fetchBean(Map<String, Object> param);

    public void delete(Map<String, Object> params);

    public void insert(Map<String, Object> params);

}
