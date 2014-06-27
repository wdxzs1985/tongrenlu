package info.tongrenlu.manager;

import info.tongrenlu.domain.CommentBean;
import info.tongrenlu.mapper.CommentMapper;

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

    public int count(final Map<String, Object> params) {
        return this.commentMapper.count(params);
    }

    public List<CommentBean> search(final Map<String, Object> params) {
        return this.commentMapper.fetchList(params);
    }

    public void addComment(final CommentBean commentBean) {
        this.commentMapper.insert(commentBean);
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
