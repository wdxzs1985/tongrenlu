package info.tongrenlu.mapper;

import java.util.Map;

public interface LikeMapper {

    int count(Map<String, Object> params);

    void insert(Map<String, Object> params);

    void delete(Map<String, Object> params);

}
