package info.tongrenlu.persistence;

import info.tongrenlu.domain.ArticleBean;

import java.util.Map;

public interface MArticleMapper {

    public void insert(ArticleBean param);

    public void delete(ArticleBean param);

    public ArticleBean fetchBean(Map<String, Object> params);

    public void update(Map<String, Object> param);

    public int countByUser(Map<String, Object> param);

}
