package info.tongrenlu.mapper;

import info.tongrenlu.domain.ComicBean;

import java.util.List;
import java.util.Map;

public interface ComicMapper {

    public void insert(ComicBean comicBean);

    int count(Map<String, Object> params);

    List<ComicBean> fetchList(Map<String, Object> params);

    ComicBean fetchBean(Map<String, Object> param);

    public void update(Map<String, Object> param);

    public List<ComicBean> fetchRanking(Map<String, Object> param);

    public List<ComicBean> fetchTopping(Map<String, Object> param);
}
