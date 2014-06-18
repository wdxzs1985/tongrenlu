package info.tongrenlu.persistence;

import info.tongrenlu.domain.ComicBean;

import java.util.List;
import java.util.Map;

public interface RComicMapper {

    public List<ComicBean> fetchList(Map<String, Object> param);

    public ComicBean fetchBean(Map<String, Object> param);

    public int count(Map<String, Object> param);

    public void insert(ComicBean comic);

    public void update(ComicBean comic);

    public void delete(ComicBean comic);

    // public List<ComicBean> getComicRank(Map<String, Object> param);

}
