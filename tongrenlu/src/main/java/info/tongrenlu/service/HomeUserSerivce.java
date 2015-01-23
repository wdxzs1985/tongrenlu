package info.tongrenlu.service;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TimelineBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.domain.UserProfileBean;
import info.tongrenlu.manager.ArticleManager;
import info.tongrenlu.manager.LikeManager;
import info.tongrenlu.manager.UserManager;
import info.tongrenlu.support.PaginateSupport;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HomeUserSerivce {

    @Autowired
    private UserManager userManager = null;
    @Autowired
    private ArticleManager articleManager = null;
    @Autowired
    private LikeManager likeManager = null;

    public UserProfileBean getById(final Integer userId) {
        return this.userManager.getById(userId);
    }

    @Transactional
    public void isFollower(final Integer userId, final UserBean loginUser,
                           final Map<String, Object> model, final Locale locale) {
        int result = LikeManager.RESULT_NOT_LIKE;
        if (this.userManager.validateUserIsSignin(loginUser, model, locale)) {
            final UserBean userBean = this.userManager.getById(userId);
            if (this.likeManager.validateUserNotSame(loginUser, userBean)) {
                result = this.likeManager.countLike(loginUser, userBean);
            } else {
                result = LikeManager.RESULT_SELF;
            }
        } else {
            result = LikeManager.RESULT_NEED_SIGN;
        }
        model.put("result", result);
    }

    @Transactional
    public void doFollow(final Integer userId, final UserBean loginUser,
                         final Map<String, Object> model, final Locale locale) {
        int result = LikeManager.RESULT_NOT_LIKE;
        if (this.userManager.validateUserIsSignin(loginUser, model, locale)) {
            final UserBean userBean = this.userManager.getById(userId);
            if (this.likeManager.validateUserNotSame(loginUser, userBean)) {
                final int count = this.likeManager.countLike(loginUser,
                                                             userBean);
                if (count == 0) {
                    this.likeManager.addLike(loginUser, userBean);
                    result = LikeManager.RESULT_LIKE;
                } else {
                    this.likeManager.removeLike(loginUser, userBean);
                    result = LikeManager.RESULT_NOT_LIKE;
                }
            } else {
                result = LikeManager.RESULT_SELF;
            }
        } else {
            result = LikeManager.RESULT_NEED_SIGN;
        }
        model.put("result", result);
    }

    public void searchTimeline(final PaginateSupport<TimelineBean> paginate) {
        final int itemCount = this.userManager.countUserTimeline(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<TimelineBean> items = this.userManager.searchUserTimeline(paginate.getParams());
        paginate.setItems(items);
    }

    public void searchMusic(final PaginateSupport<MusicBean> paginate) {
        final int itemCount = this.articleManager.countMusic(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<MusicBean> items = this.articleManager.searchMusic(paginate.getParams());
        paginate.setItems(items);

    }
}
