package info.tongrenlu.persistence;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.GameBean;
import info.tongrenlu.domain.MusicBean;

import java.util.List;
import java.util.Map;


public interface RCollectMapper {

    public int countCollect(Map<String, Object> param);

    public void insertCollect(Map<String, Object> param);

    public void deleteCollect(Map<String, Object> param);

    public int countComicCollect(Map<String, Object> param);

    public List<ComicBean> fetchComicCollect(Map<String, Object> param);

    public int countMusicCollect(Map<String, Object> param);

    public List<MusicBean> fetchMusicCollect(Map<String, Object> param);

    public int countGameCollect(Map<String, Object> param);

    public List<GameBean> fetchGameCollect(Map<String, Object> param);

}
