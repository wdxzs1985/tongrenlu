package info.tongrenlu.persistence;

import info.tongrenlu.domain.ArticleTagBean;
import info.tongrenlu.domain.TagBean;

import java.util.List;
import java.util.Map;


public interface RArticleTagMapper {

    public void insertArticleTag(ArticleTagBean articleTagBean);

    public List<TagBean> fetchArticleTag(Map<String, Object> param);

    public void removeArticleTag(Map<String, Object> param);

    public int countArticleTag(ArticleTagBean articleBean);

}
