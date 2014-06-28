package info.tongrenlu.mapper;

import info.tongrenlu.domain.CommentBean;

import java.util.List;
import java.util.Map;

public interface CommentMapper {

    void insert(CommentBean commentBean);

    int countComicComment(Map<String, Object> params);

    List<CommentBean> fetchComicComment(Map<String, Object> params);

    int countMusicComment(Map<String, Object> params);

    List<CommentBean> fetchMusicComment(Map<String, Object> params);

}
