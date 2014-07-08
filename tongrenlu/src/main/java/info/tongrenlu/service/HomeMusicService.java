package info.tongrenlu.service;

import info.tongrenlu.domain.ArticleBean;
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

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

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

    public MusicBean getRandomMusic() {
        final Map<String, Object> params = new HashMap<>();
        final int itemCount = this.articleManager.countMusic(params);

        if (itemCount == 0) {
            return null;
        }

        final int start = new Random(System.currentTimeMillis()).nextInt(itemCount);

        params.put("start", start);
        params.put("pageSize", 1);

        final List<MusicBean> items = this.articleManager.searchMusic(params);
        return items.get(0);
    }

    public List<MusicBean> getRanking(final int pageSize) {
        return this.articleManager.getMusicRanking(pageSize);
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
            result = this.likeManager.countLike(loginUser, musicBean);
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
            final int count = this.likeManager.countLike(loginUser, musicBean);
            if (count == 0) {
                this.likeManager.addLike(loginUser, musicBean);
                result = LikeManager.RESULT_LIKE;
            } else {
                this.likeManager.removeLike(loginUser, musicBean);
                result = LikeManager.RESULT_NOT_LIKE;
            }
        } else {
            result = LikeManager.RESULT_NEED_SIGN;
        }
        return result;
    }

}
