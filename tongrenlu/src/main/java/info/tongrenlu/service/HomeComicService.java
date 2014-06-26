package info.tongrenlu.service;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.ArticleManager;
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

    public ComicBean getById(final Integer id) {
        return this.articleManager.getComicById(id);
    }

    public String[] getTags(final ComicBean comicBean) {
        return this.articleManager.getTags(comicBean).toArray(new String[] {});
    }

    public void addAccess(final ComicBean comicBean, final UserBean loginUser) {
        this.articleManager.addAccess(comicBean, loginUser);
    }

    public void isLike(final Integer articleId,
                       final UserBean loginUser,
                       final Map<String, Object> model,
                       final Locale locale) {
        boolean result = false;
        if (this.validateUserForLike(loginUser, model, locale)) {
            final ComicBean comicBean = this.getById(articleId);
            final int count = this.likeManager.countLike(loginUser, comicBean);
            if (count != 0) {
                result = true;
            }
        }
        model.put("result", result);
    }

    @Transactional
    public void doLike(final Integer articleId,
                       final UserBean loginUser,
                       final Map<String, Object> model,
                       final Locale locale) {
        boolean result = false;
        if (this.validateUserForLike(loginUser, model, locale)) {
            final ComicBean comicBean = this.getById(articleId);
            final int count = this.likeManager.countLike(loginUser, comicBean);
            if (count != 0) {
                this.likeManager.removeLike(loginUser, comicBean);
                result = false;
            } else {
                this.likeManager.addLike(loginUser, comicBean);
                result = true;
            }
        }
        model.put("result", result);
    }

    private boolean validateUserForLike(final UserBean loginUser,
                                        final Map<String, Object> model,
                                        final Locale locale) {
        boolean isValid = true;
        if (loginUser.isGuest()) {
            final String error = this.messageSource.getMessage("error.needSignin",
                                                               null,
                                                               locale);
            model.put("error", error);
            isValid = false;
        }
        return isValid;
    }
}
