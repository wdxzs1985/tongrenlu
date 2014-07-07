package info.tongrenlu.dummy;

import info.tongrenlu.entity.TagEntity;
import info.tongrenlu.jdbc.TagManager;

import org.springframework.stereotype.Component;

@Component
public class TagManagerDummy extends TagManager {
    @Override
    public TagEntity findByTagId(final String tagId) {
        final TagEntity tag = new TagEntity(tagId, "tag" + tagId);
        return tag;
    }
}
