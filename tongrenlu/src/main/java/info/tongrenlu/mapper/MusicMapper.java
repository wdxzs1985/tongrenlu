package info.tongrenlu.mapper;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.MusicBean;

import java.util.List;
import java.util.Map;

public interface MusicMapper {

    public void insert(MusicBean musicBean);

    public int countForSearch(Map<String, Object> params);

    public List<MusicBean> fetchListForSearch(Map<String, Object> params);

    public MusicBean fetchBean(Map<String, Object> param);

    public void delete(ArticleBean articleBean);

}
