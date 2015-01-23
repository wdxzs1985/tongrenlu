package info.tongrenlu.service;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.ArticleManager;
import info.tongrenlu.manager.LikeManager;
import info.tongrenlu.manager.TagManager;
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
public class TagService {

    @Autowired
    private TagManager tagManager = null;
    @Autowired
    private ArticleManager articleManager = null;
    @Autowired
    private UserManager userManager = null;
    @Autowired
    private final LikeManager likeManager = null;

    public void searchTag(final PaginateSupport<TagBean> paginate) {
        final int itemCount = this.tagManager.countTag(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<TagBean> items = this.tagManager.searchTag(paginate.getParams());
        paginate.setItems(items);
    }

    public TagBean getById(final Integer tagId) {
        return this.tagManager.getById(tagId);
    }

    public TagBean getByTag(final String tag) {
        return this.tagManager.getByTag(tag);
    }

    public void searchMusicByTag(final PaginateSupport<MusicBean> paginate) {
        final int itemCount = this.tagManager.countMusic(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<MusicBean> items = this.tagManager.searchMusic(paginate.getParams());
        paginate.setItems(items);
    }

    public List<TagBean> getTagByArticle(final ArticleBean articleBean) {
        return this.articleManager.getTags(articleBean);
    }

    public boolean addTag(final ArticleBean articleBean, final String[] tags,
                          final UserBean loginUser,
                          final Map<String, Object> model, final Locale locale) {
        boolean result = false;
        if (this.userManager.validateUserIsSignin(loginUser, model, locale)) {
            for (final String tag : tags) {
                final TagBean tagBean = this.tagManager.create(tag);
                this.articleManager.addTag(articleBean, tagBean);
            }
            result = true;
        }
        return result;
    }

    public int isLike(final Integer tagId, final UserBean loginUser,
                      final Map<String, Object> model, final Locale locale) {
        int result = LikeManager.RESULT_NOT_LIKE;
        if (this.userManager.validateUserIsSignin(loginUser, model, locale)) {
            final TagBean tagBean = this.getById(tagId);
            result = this.likeManager.countLike(loginUser, tagBean);
        } else {
            result = LikeManager.RESULT_NEED_SIGN;
        }
        return result;
    }

    public int doLike(final Integer tagId, final UserBean loginUser,
                      final Map<String, Object> model, final Locale locale) {
        int result = LikeManager.RESULT_NOT_LIKE;
        if (this.userManager.validateUserIsSignin(loginUser, model, locale)) {
            final TagBean tagBean = this.getById(tagId);
            final int count = this.likeManager.countLike(loginUser, tagBean);
            if (count == 0) {
                this.likeManager.addLike(loginUser, tagBean);
                result = LikeManager.RESULT_LIKE;
            } else {
                this.likeManager.removeLike(loginUser, tagBean);
                result = LikeManager.RESULT_NOT_LIKE;
            }
        } else {
            result = LikeManager.RESULT_NEED_SIGN;
        }
        return result;
    }
}
