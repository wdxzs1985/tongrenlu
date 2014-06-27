package info.tongrenlu.mapper;

import info.tongrenlu.domain.CommentBean;

import java.util.List;
import java.util.Map;

public interface CommentMapper {

    int count(Map<String, Object> params);

    List<CommentBean> fetchList(Map<String, Object> params);

    void insert(CommentBean commentBean);

}
