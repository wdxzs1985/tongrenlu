package info.tongrenlu.service;

import info.tongrenlu.domain.CommentBean;
import info.tongrenlu.manager.CommentManager;
import info.tongrenlu.support.PaginateSupport;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentManager commentManager = null;

    public void searchComment(final PaginateSupport<CommentBean> paginate) {
        final int itemCount = this.commentManager.countMusicComment(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<CommentBean> items = this.commentManager.searchMusicComment(paginate.getParams());
        paginate.setItems(items);
    }

    public CommentBean getComment(final Integer commentId) {
        return this.commentManager.getComment(commentId);
    }

    public boolean doComment(final CommentBean commentBean,
                             final Map<String, Object> model,
                             final Locale locale) {
        if (this.validateForComment(commentBean, model, locale)) {
            this.commentManager.addComment(commentBean);
            return true;
        }
        return false;
    }

    private boolean validateForComment(final CommentBean commentBean,
                                       final Map<String, Object> model,
                                       final Locale locale) {
        boolean isValid = true;
        if (!this.commentManager.validateContent(commentBean.getContent(),
                                                 "error",
                                                 model,
                                                 locale)) {
            isValid = false;
        }
        return isValid;
    }

}
