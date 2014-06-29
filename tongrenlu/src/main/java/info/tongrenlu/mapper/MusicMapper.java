package info.tongrenlu.mapper;

import info.tongrenlu.domain.MusicBean;

import java.util.List;
import java.util.Map;

public interface MusicMapper {

    public void insert(MusicBean musicBean);

    public int count(Map<String, Object> params);

    public List<MusicBean> fetchList(Map<String, Object> params);

    public MusicBean fetchBean(Map<String, Object> param);

    public List<MusicBean> fetchRanking();

}
