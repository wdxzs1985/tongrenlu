package info.tongrenlu.service;

import info.tongrenlu.domain.CommentBean;
import info.tongrenlu.domain.NotificationBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.CommentManager;
import info.tongrenlu.manager.NotificationManager;
import info.tongrenlu.support.PaginateSupport;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private CommentManager commentManager = null;
    @Autowired
    private NotificationManager notificationManager = null;

    public void searchMusicComment(final PaginateSupport<CommentBean> paginate) {
        final int itemCount = this.commentManager.countMusicComment(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<CommentBean> items = this.commentManager.searchMusicComment(paginate.getParams());
        paginate.setItems(items);
    }

    public boolean doComment(final CommentBean commentBean,
                             final Integer parentId,
                             final Map<String, Object> model,
                             final Locale locale) {
        if (this.validateForComment(commentBean, model, locale)) {
            this.commentManager.addComment(commentBean);

            final Collection<UserBean> users = this.notificationManager.findUserFromString(commentBean.getContent());
            for (final UserBean userBean : users) {
                if (!userBean.getId().equals(commentBean.getUserBean().getId())) {
                    final NotificationBean notificationBean = new NotificationBean();
                    notificationBean.setUserBean(userBean);
                    notificationBean.setSender(commentBean.getUserBean());
                    notificationBean.setArticleBean(commentBean.getArticleBean());
                    notificationBean.setCategory("m");
                    notificationBean.setAction("comment");
                    notificationBean.setContent(StringUtils.left(commentBean.getContent(),
                                                                 140));

                    this.notificationManager.sendNotification(notificationBean);
                }
            }
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
