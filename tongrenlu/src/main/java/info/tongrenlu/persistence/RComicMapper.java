package info.tongrenlu.persistence;

import info.tongrenlu.domain.ComicBean;

import java.util.List;
import java.util.Map;


public interface RComicMapper {

    public List<ComicBean> getComicList(Map<String, Object> param);

    public void insertComic(ComicBean comic);

    public int getComicCount(Map<String, Object> param);

    public ComicBean getComic(Map<String, Object> param);

    public void updateComic(ComicBean comic);

    public void deleteComic(Map<String, Object> param);

    // public List<ComicBean> getComicRank(Map<String, Object> param);

}
