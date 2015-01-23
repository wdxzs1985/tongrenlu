package info.tongrenlu.mapper;

import info.tongrenlu.domain.CommentBean;

import java.util.List;
import java.util.Map;

public interface CommentMapper {

    void insert(CommentBean commentBean);

    int countMusicComment(Map<String, Object> params);

    List<CommentBean> fetchMusicComment(Map<String, Object> params);

    CommentBean fetchBean(Map<String, Object> params);

}
