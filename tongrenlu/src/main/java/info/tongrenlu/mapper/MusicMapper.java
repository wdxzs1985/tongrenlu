package info.tongrenlu.mapper;

import info.tongrenlu.domain.MusicBean;

import java.util.List;
import java.util.Map;

public interface MusicMapper {

    public void insert(MusicBean music);

    public void delete(Integer id);

    public int count(Map<String, Object> param);

    public List<MusicBean> fetchList(Map<String, Object> param);

    public MusicBean fetchBean(Map<String, Object> param);

}
