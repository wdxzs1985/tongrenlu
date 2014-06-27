package info.tongrenlu.persistence;

import info.tongrenlu.domain.CommentBean;

import java.util.List;
import java.util.Map;

public interface MCommentMapper {

    public int count(Map<String, Object> param);

    public List<CommentBean> fetchList(Map<String, Object> param);

    public void insert(CommentBean commentBean);

}
