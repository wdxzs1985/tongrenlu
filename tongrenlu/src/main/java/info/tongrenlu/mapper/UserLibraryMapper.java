package info.tongrenlu.mapper;

import info.tongrenlu.domain.MusicBean;

import java.util.List;
import java.util.Map;

public interface UserLibraryMapper {

    public int count(Map<String, Object> params);

    public List<MusicBean> fetchList(Map<String, Object> params);

    public void insert(Map<String, Object> params);

}
