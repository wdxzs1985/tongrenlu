package info.tongrenlu.mapper;

import info.tongrenlu.domain.ArticleBean;

import java.util.Map;

public interface ArticleMapper {

    public void insert(ArticleBean articleBean);

    public void update(Map<String, Object> param);

    public void delete(ArticleBean articleBean);

    public void publish(Map<String, Object> param);

}
