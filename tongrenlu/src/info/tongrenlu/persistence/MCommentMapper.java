package info.tongrenlu.persistence;

import info.tongrenlu.domain.ArticleCommentBean;
import info.tongrenlu.domain.UserCommentBean;

import java.util.List;
import java.util.Map;

public interface MCommentMapper {

    public int getArticleCommentCount(Map<String, Object> param);

    public List<ArticleCommentBean> getArticleCommentList(Map<String, Object> param);

    public void insertArticleComment(ArticleCommentBean commentBean);

    public int getUserCommentCount(Map<String, Object> param);

    public List<UserCommentBean> getUserCommentList(Map<String, Object> param);

    public void insertUserComment(UserCommentBean commentBean);

}
