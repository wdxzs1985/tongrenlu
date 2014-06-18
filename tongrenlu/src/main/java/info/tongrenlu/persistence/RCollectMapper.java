package info.tongrenlu.persistence;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.MusicBean;

import java.util.List;
import java.util.Map;

public interface RCollectMapper {

    public int count(Map<String, Object> param);

    public void insert(Map<String, Object> param);

    public void delete(Map<String, Object> param);

    public int countForComic(Map<String, Object> param);

    public List<ComicBean> fetchListForComic(Map<String, Object> param);

    public int countForMusic(Map<String, Object> param);

    public List<MusicBean> fetchListForMusic(Map<String, Object> param);

}
