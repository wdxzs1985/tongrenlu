package info.tongrenlu.service;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.CommentBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.ArticleManager;
import info.tongrenlu.manager.CommentManager;
import info.tongrenlu.manager.FileManager;
import info.tongrenlu.manager.LikeManager;
import info.tongrenlu.manager.TagManager;
import info.tongrenlu.support.PaginateSupport;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HomeComicService {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private ArticleManager articleManager = null;
    @Autowired
    private TagManager tagManager = null;
    @Autowired
    private LikeManager likeManager = null;
    @Autowired
    private CommentManager commentManager = null;

    public List<FileBean> getPictureList(final Integer articleId) {
        return this.articleManager.getFiles(articleId, FileManager.IMAGE);
    }

    public void searchComic(final PaginateSupport<ComicBean> paginate) {
        final int itemCount = this.articleManager.countComic(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<ComicBean> items = this.articleManager.searchComic(paginate.getParams());
        paginate.setItems(items);
    }

    public List<ComicBean> getRanking(final int pageSize) {
        return this.articleManager.getComicRanking(pageSize);
    }

    public ComicBean getById(final Integer id) {
        return this.articleManager.getComicById(id);
    }

    public List<TagBean> getTagList(final Integer articleId) {
        final ComicBean comicBean = this.getById(articleId);
        return this.articleManager.getTags(comicBean);
    }

    public void addAccess(final ComicBean comicBean, final UserBean loginUser) {
        this.articleManager.addAccess(comicBean, loginUser);
    }

    public void isLike(final Integer articleId,
                       final UserBean loginUser,
                       final Map<String, Object> model,
                       final Locale locale) {
        int result = LikeManager.RESULT_NOT_LIKE;
        if (this.likeManager.validateUserIsSignin(loginUser, model, locale)) {
            final ComicBean comicBean = this.getById(articleId);
            result = this.likeManager.countLike(loginUser, comicBean);
        } else {
            result = LikeManager.RESULT_NEED_SIGN;
        }
        model.put("result", result);
    }

    @Transactional
    public void doLike(final Integer articleId,
                       final UserBean loginUser,
                       final Map<String, Object> model,
                       final Locale locale) {
        int result = LikeManager.RESULT_NOT_LIKE;
        if (this.likeManager.validateUserIsSignin(loginUser, model, locale)) {
            final ComicBean comicBean = this.getById(articleId);
            final int count = this.likeManager.countLike(loginUser, comicBean);
            if (count == 0) {
                this.likeManager.addLike(loginUser, comicBean);
                result = LikeManager.RESULT_LIKE;
            } else {
                this.likeManager.removeLike(loginUser, comicBean);
                result = LikeManager.RESULT_NOT_LIKE;
            }
        } else {
            result = LikeManager.RESULT_NEED_SIGN;
        }
        model.put("result", result);
    }

    public void searchComment(final PaginateSupport<CommentBean> paginate) {
        final int itemCount = this.commentManager.countComicComment(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<CommentBean> items = this.commentManager.searchComicComment(paginate.getParams());
        paginate.setItems(items);
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
