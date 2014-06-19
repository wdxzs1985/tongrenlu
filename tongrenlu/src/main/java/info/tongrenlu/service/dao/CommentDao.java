package info.tongrenlu.service.dao;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.ArticleCommentBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.persistence.MCommentMapper;
import info.tongrenlu.support.PaginateSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class CommentDao {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private MCommentMapper commentMapper;

    public PaginateSupport getArticleCommentList(final ArticleBean articleBean,
                                                 final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleBean", articleBean);
        final int itemCount = this.commentMapper.count(param);
        paginate.setItemCount(itemCount);
        paginate.compute();
        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        final List<ArticleCommentBean> items = this.commentMapper.fetchList(param);
        paginate.setItems(items);
        return paginate;
    }

    public void addArticleComment(final UserBean sender,
                                  final ArticleBean articleBean,
                                  final String content) {
        final ArticleCommentBean commentBean = new ArticleCommentBean();
        commentBean.setContent(content);
        commentBean.setArticleBean(articleBean);
        commentBean.setUserBean(sender);
        this.commentMapper.insert(commentBean);
    }

    public boolean validateAddComment(final String content,
                                      final Map<String, Object> model) {
        boolean isValid = true;
        if (StringUtils.isBlank(content)) {
            isValid = false;
            model.put("error",
                      this.messageSource.getMessage("CommentBean.content[Blank]",
                                                    null,
                                                    null));
        } else if (StringUtils.length(content) > 1000) {
            isValid = false;
            model.put("error",
                      this.messageSource.getMessage("CommentBean.content[TooLong]",
                                                    new Integer[] { 1000 },
                                                    null));
        }
        return isValid;
    }

}
