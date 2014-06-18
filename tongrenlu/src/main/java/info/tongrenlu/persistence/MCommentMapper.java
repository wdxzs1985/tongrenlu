package info.tongrenlu.persistence;

import info.tongrenlu.domain.ArticleCommentBean;

import java.util.List;
import java.util.Map;

public interface MCommentMapper {

    public int count(Map<String, Object> param);

    public List<ArticleCommentBean> fetchList(Map<String, Object> param);

    public void insert(ArticleCommentBean commentBean);

}
