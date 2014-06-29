package info.tongrenlu.mapper;

import info.tongrenlu.domain.ArticleTagBean;
import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;

import java.util.List;
import java.util.Map;

public interface ArticleTagMapper {

    public void insert(ArticleTagBean articleTagBean);

    public void delete(ArticleTagBean articleTagBean);

    public List<TagBean> fetchList(Map<String, Object> param);

    public int countMusic(Map<String, Object> params);

    public List<MusicBean> searchMusic(Map<String, Object> params);

    public int countComic(Map<String, Object> params);

    public List<ComicBean> searchComic(Map<String, Object> params);
}
