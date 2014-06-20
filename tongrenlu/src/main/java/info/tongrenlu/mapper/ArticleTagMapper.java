package info.tongrenlu.mapper;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.ArticleTagBean;

public interface ArticleTagMapper {

    public void insert(ArticleTagBean articleTagBean);

    public void deleteByArticleId(ArticleBean articleBean);
}
