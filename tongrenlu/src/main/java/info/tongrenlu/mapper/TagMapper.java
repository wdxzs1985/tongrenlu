package info.tongrenlu.mapper;

import info.tongrenlu.domain.TagBean;

import java.util.List;
import java.util.Map;

public interface TagMapper {

    public List<TagBean> fetchListByTag(Map<String, Object> param);

    public TagBean fetchBean(Map<String, Object> param);

    public void insert(TagBean tagBean);

}
