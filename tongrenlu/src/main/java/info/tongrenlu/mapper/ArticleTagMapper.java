package info.tongrenlu.mapper;

import info.tongrenlu.domain.ArticleTagBean;
import info.tongrenlu.domain.TagBean;

import java.util.List;
import java.util.Map;

public interface ArticleTagMapper {

    public void insert(ArticleTagBean articleTagBean);

    public void delete(ArticleTagBean articleTagBean);

    public List<TagBean> fetchTagList(Map<String, Object> param);
}
