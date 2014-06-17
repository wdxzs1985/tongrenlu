package info.tongrenlu.persistence;

import info.tongrenlu.domain.MusicBean;

import java.util.List;
import java.util.Map;


public interface RMusicMapper {

    public void insertMusic(MusicBean music);

    // public void updateMusic(MusicBean music);

    public int getMusicCount(Map<String, Object> param);

    public List<MusicBean> getMusicList(Map<String, Object> param);

    public MusicBean getMusic(Map<String, Object> param);

    public void deleteMusic(Map<String, Object> param);

}
