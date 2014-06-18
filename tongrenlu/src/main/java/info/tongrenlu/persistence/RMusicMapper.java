package info.tongrenlu.persistence;

import info.tongrenlu.domain.MusicBean;

import java.util.List;
import java.util.Map;

public interface RMusicMapper {

    public void insert(MusicBean music);

    public void delete(MusicBean music);

    // public void updateMusic(MusicBean music);

    public int count(Map<String, Object> param);

    public List<MusicBean> fetchList(Map<String, Object> param);

    public MusicBean fetchBean(Map<String, Object> param);

}
