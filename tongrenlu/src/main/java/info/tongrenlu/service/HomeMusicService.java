package info.tongrenlu.service;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TrackBean;
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
public class HomeMusicService {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private ArticleManager articleManager = null;
    @Autowired
    private TagManager tagManager = null;
    @Autowired
    private LikeManager likeManager = null;

    public List<TrackBean> getTrackList(final Integer articleId) {
        return this.articleManager.getTrackList(articleId);
    }

    public List<FileBean> getBookletList(final Integer articleId) {
        return this.articleManager.getFiles(articleId, FileManager.IMAGE);
    }

    public void searchMusic(final PaginateSupport<MusicBean> paginate) {
        final int itemCount = this.articleManager.countMusic(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<MusicBean> items = this.articleManager.searchMusic(paginate.getParams());
        paginate.setItems(items);
    }

    public MusicBean getById(final Integer id) {
        return this.articleManager.getMusicById(id);
    }

    public String[] getTags(final MusicBean musicBean) {
        return this.articleManager.getTags(musicBean).toArray(new String[] {});
    }

    @Transactional
    public void addAccess(final ArticleBean articleBean, final UserBean userBean) {
        this.articleManager.addAccess(articleBean, userBean);
    }

    public void isLike(final Integer articleId,
                       final UserBean loginUser,
                       final Map<String, Object> model,
                       final Locale locale) {
        boolean result = false;
        if (this.validateUserForLike(loginUser, model, locale)) {
            final MusicBean musicBean = this.getById(articleId);
            final int count = this.likeManager.countLike(loginUser, musicBean);
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
            final MusicBean musicBean = this.getById(articleId);
            final int count = this.likeManager.countLike(loginUser, musicBean);
            if (count != 0) {
                this.likeManager.removeLike(loginUser, musicBean);
                result = false;
            } else {
                this.likeManager.addLike(loginUser, musicBean);
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
