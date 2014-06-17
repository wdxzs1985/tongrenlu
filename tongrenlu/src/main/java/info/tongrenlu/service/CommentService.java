package info.tongrenlu.service;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.dao.CommentDao;
import info.tongrenlu.support.PaginateSupport;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {

    public static final String DEFAULT_USER = "000000000000000";

    @Autowired
    private CommentDao commentDao = null;

    public Map<String, Object> doGetArticleComment(final UserBean loginUser,
                                                   final ArticleBean articleBean,
                                                   final Integer page) {
        final Map<String, Object> model = new HashMap<String, Object>();
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(50);
        model.put("page",
                  this.commentDao.getArticleCommentList(articleBean, paginate));
        return model;
    }

    public Map<String, Object> doPostArticleComment(final UserBean loginUser,
                                                    final ArticleBean articleBean,
                                                    final String content) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);

        UserBean sender = loginUser;
        if (sender == null) {
            sender = new UserBean();
            sender.setUserId(CommentService.DEFAULT_USER);
        }

        if (this.commentDao.validateAddComment(content, model)) {
            this.commentDao.addArticleComment(sender, articleBean, content);
            model.put("result", true);
        }
        return model;
    }

    public Map<String, Object> doGetUserComment(final UserBean loginUser,
                                                final UserBean userBean,
                                                final Integer page) {
        final Map<String, Object> model = new HashMap<String, Object>();
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(50);
        model.put("page",
                  this.commentDao.getUserCommentList(userBean, paginate));
        return model;
    }

    public Map<String, Object> doPostUserComment(final UserBean loginUser,
                                                 final UserBean userBean,
                                                 final String content) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);

        UserBean sender = loginUser;
        if (sender == null) {
            sender = new UserBean();
            sender.setUserId(CommentService.DEFAULT_USER);
        }

        if (this.commentDao.validateAddComment(content, model)) {
            this.commentDao.addUserComment(sender, userBean, content);
            model.put("result", true);
        }
        return model;
    }

}
