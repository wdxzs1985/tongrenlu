package info.tongrenlu.persistence;

import info.tongrenlu.domain.TagBean;

import java.util.List;
import java.util.Map;

public interface MTagMapper {

    public List<TagBean> fetchListByTag(Map<String, Object> param);

}
