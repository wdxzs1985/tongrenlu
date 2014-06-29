package info.tongrenlu.mapper;

import info.tongrenlu.domain.TagBean;

import java.util.List;
import java.util.Map;

public interface TagMapper {

    public int count(Map<String, Object> param);

    public List<TagBean> search(Map<String, Object> params);

    public TagBean fetchBean(Map<String, Object> param);

    public void insert(TagBean tagBean);

}
