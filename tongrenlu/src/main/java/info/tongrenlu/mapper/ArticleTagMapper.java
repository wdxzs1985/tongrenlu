package info.tongrenlu.mapper;

import info.tongrenlu.domain.ArticleTagBean;

import java.util.List;
import java.util.Map;

public interface ArticleTagMapper {

    public void insert(ArticleTagBean articleTagBean);

    public List<String> fetchTags(Map<String, Object> param);

    public void delete(ArticleTagBean articleTagBean);
}
