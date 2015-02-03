package info.tongrenlu.mapper;

import info.tongrenlu.domain.CommentBean;

import java.util.List;
import java.util.Map;

public interface CommentMapper {

    public void insert(CommentBean commentBean);

    public int countMusicComment(Map<String, Object> params);

    public List<CommentBean> fetchMusicComment(Map<String, Object> params);

    public CommentBean fetchBean(Map<String, Object> params);

    public void delete(CommentBean commentBean);

}
