package info.tongrenlu.manager;

import info.tongrenlu.domain.TagBean;
import info.tongrenlu.mapper.TagMapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagManager {

    @Autowired
    private TagMapper tagMapper = null;

    public TagBean getByTag(final String tag) {
        final Map<String, Object> param = new HashMap<>();
        param.put("tag", tag);
        return this.tagMapper.fetchBean(param);
    }

    public void insert(final TagBean tagBean) {
        this.tagMapper.insert(tagBean);
    }

}
