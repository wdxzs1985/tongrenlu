package info.tongrenlu.persistence;

import info.tongrenlu.domain.ArticleBean;

import java.util.List;
import java.util.Map;

public interface MArticleMapper {

    public void insertArticle(ArticleBean param);

    public int getArticleCount(Map<String, Object> param);

    public void updateArticleInfo(ArticleBean param);

    public void publishArticle(ArticleBean param);

    public void insertAccess(ArticleBean param);

    public void deleteArticle(Map<String, Object> param);

    public void recommendArticle(Map<String, Object> param);

    public ArticleBean getArticleBean(Map<String, Object> param);

    public List<ArticleBean> getArticleList(Map<String, Object> param);

}
