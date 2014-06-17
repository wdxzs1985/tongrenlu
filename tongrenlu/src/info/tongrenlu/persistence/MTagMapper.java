package info.tongrenlu.persistence;

import info.tongrenlu.domain.ArticleTagBean;
import info.tongrenlu.domain.TagBean;

import java.util.List;
import java.util.Map;


public interface MTagMapper {

    public void insertTag(TagBean tagBean);

    public List<TagBean> fetchTagList(Map<String, Object> param);

    public void insertArticleTag(ArticleTagBean articleTagBean);

    public TagBean fetchTag(Map<String, Object> param);

}
