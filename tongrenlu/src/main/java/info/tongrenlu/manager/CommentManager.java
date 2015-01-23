package info.tongrenlu.manager;

import info.tongrenlu.domain.CommentBean;
import info.tongrenlu.mapper.CommentMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class CommentManager {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    CommentMapper commentMapper = null;

    public int countMusicComment(final Map<String, Object> params) {
        return this.commentMapper.countMusicComment(params);
    }

    public List<CommentBean> searchMusicComment(final Map<String, Object> params) {
        return this.commentMapper.fetchMusicComment(params);
    }

    public void addComment(final CommentBean commentBean) {
        this.commentMapper.insert(commentBean);
    }

    public CommentBean getComment(final Integer commentId) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", commentId);
        return this.commentMapper.fetchBean(params);
    }

    public boolean validateContent(final String content,
                                   final String errorAttribute,
                                   final Map<String, Object> model,
                                   final Locale locale) {
        boolean isValid = true;
        if (StringUtils.isBlank(content)) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("error.emptyComment",
                                                    null,
                                                    locale));
            isValid = false;
        }
        return isValid;
    }

}
