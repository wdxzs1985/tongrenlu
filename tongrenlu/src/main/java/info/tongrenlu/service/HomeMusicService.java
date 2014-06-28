package info.tongrenlu.service;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.CommentBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.TrackBean;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HomeMusicService {

    @Autowired
    private ArticleManager articleManager = null;
    @Autowired
    private TagManager tagManager = null;
    @Autowired
    private LikeManager likeManager = null;
    @Autowired
    private CommentManager commentManager = null;

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

    public List<TagBean> getTagList(final Integer articleId) {
        final MusicBean musicBean = this.getById(articleId);
        return this.articleManager.getTags(musicBean);
    }

    @Transactional
    public void addAccess(final ArticleBean articleBean, final UserBean userBean) {
        this.articleManager.addAccess(articleBean, userBean);
    }

    public int isLike(final Integer articleId,
                      final UserBean loginUser,
                      final Map<String, Object> model,
                      final Locale locale) {
        int result = LikeManager.RESULT_NOT_LIKE;
        if (this.likeManager.validateUserIsSignin(loginUser, model, locale)) {
            final MusicBean musicBean = this.getById(articleId);
            if (this.likeManager.validateUserNotSame(loginUser,
                                                     musicBean.getUserBean())) {
                result = this.likeManager.countLike(loginUser, musicBean);
            } else {
                result = LikeManager.RESULT_SELF;
            }
        } else {
            result = LikeManager.RESULT_NEED_SIGN;
        }
        return result;
    }

    @Transactional
    public int doLike(final Integer articleId,
                      final UserBean loginUser,
                      final Map<String, Object> model,
                      final Locale locale) {
        int result = LikeManager.RESULT_NOT_LIKE;
        if (this.likeManager.validateUserIsSignin(loginUser, model, locale)) {
            final MusicBean musicBean = this.getById(articleId);
            if (this.likeManager.validateUserNotSame(loginUser,
                                                     musicBean.getUserBean())) {
                final int count = this.likeManager.countLike(loginUser,
                                                             musicBean);
                if (count == 0) {
                    this.likeManager.addLike(loginUser, musicBean);
                    result = LikeManager.RESULT_LIKE;
                } else {
                    this.likeManager.removeLike(loginUser, musicBean);
                    result = LikeManager.RESULT_NOT_LIKE;
                }
            } else {
                result = LikeManager.RESULT_SELF;
            }
        } else {
            result = LikeManager.RESULT_NEED_SIGN;
        }
        return result;
    }

    public void searchComment(final PaginateSupport<CommentBean> paginate) {
        final int itemCount = this.commentManager.countMusicComment(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<CommentBean> items = this.commentManager.searchMusicComment(paginate.getParams());
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
