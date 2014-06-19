package info.tongrenlu.manager;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.validator.ArticleBeanValidator;
import info.tongrenlu.support.PaginateSupport;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MusicDao {

    @Autowired
    private ArticleBeanValidator validator = null;

    // @Autowired
    // private MArticleMapper articleMapper = null;
    // @Autowired
    // private RMusicMapper musicMapper = null;
    // @Autowired
    // private RCollectMapper collectMapper = null;

    public PaginateSupport getMusicList(final String searchQuery,
                                        final String tagId,
                                        final String collectUserId,
                                        final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("searchQuery", searchQuery);
        param.put("tagId", tagId);
        param.put("publishFlg", "1");
        param.put("collectUserId", collectUserId);

        // final int itemCount = this.musicMapper.count(param);
        // paginate.setItemCount(itemCount);
        paginate.compute();

        param.put("order", "A.ARTICLE_ID DESC");
        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        // final List<MusicBean> items = this.musicMapper.fetchList(param);
        // paginate.setItems(items);
        return paginate;
    }

    public MusicBean getMusicById(final String articleId,
                                  final String collectUserId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        param.put("collectUserId", collectUserId);
        // return this.musicMapper.fetchBean(param);
        return null;
    }

    public List<MusicBean> getMusicLastest(final String searchQuery,
                                           final String tagId,
                                           final int size) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("searchQuery", searchQuery);
        param.put("tagId", tagId);
        param.put("publishFlg", "1");
        param.put("order", "A.PUBLISH_DATE DESC");
        param.put("start", 1);
        param.put("end", size);
        return this.getMusicRank(param);
    }

    public List<MusicBean> getMusicAccess(final int size) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("publishFlg", "1");
        param.put("order", "NVL(V_ACCESS.CNT, 0) DESC");
        param.put("start", 1);
        param.put("end", size);
        return this.getMusicRank(param);
    }

    public List<MusicBean> getMusicForIndex(final int size) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("recommend", "1");
        param.put("publishFlg", "1");
        param.put("order",
                  "NVL(V_COMMENT_2.COMMENT_ID,'0') DESC, A.PUBLISH_DATE DESC");
        param.put("start", 1);
        param.put("end", size);
        return this.getMusicRank(param);
    }

    private List<MusicBean> getMusicRank(final Map<String, Object> param) {
        // final List<MusicBean> items = this.musicMapper.fetchList(param);
        // return items;
        return Collections.emptyList();
    }

    public List<MusicBean> getUserMusicList(final UserBean userBean,
                                            final int start,
                                            final int end) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userBean", userBean);
        param.put("publishFlg", "1");
        param.put("order", "A.ARTICLE_ID DESC");
        param.put("start", start);
        param.put("end", end);
        // final List<MusicBean> items = this.musicMapper.fetchList(param);
        // return items;

        return Collections.emptyList();
    }

    public PaginateSupport getAdminMusicList(final PaginateSupport paginate,
                                             final String searchQuery) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("searchQuery", searchQuery);
        // final int itemCount = this.musicMapper.count(param);
        // paginate.setItemCount(itemCount);
        paginate.compute();
        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        param.put("order", "A.ARTICLE_ID DESC");
        // final List<MusicBean> items = this.musicMapper.fetchList(param);
        // paginate.setItems(items);
        return paginate;
    }

    public boolean validateCreateMusic(final MusicBean music,
                                       final Map<String, Object> model) {
        boolean isValid = true;
        if (!this.validator.validateTitle(music.getTitle(), model)) {
            isValid = false;
        }
        if (!this.validator.validateDescription(music.getDescription(), model)) {
            isValid = false;
        }
        return isValid;
    }

    @Transactional
    public void createMusic(final MusicBean music) {
        // this.articleMapper.insert(music);
        // this.musicMapper.insert(music);
    }

    public boolean validateEditMusic(final MusicBean music,
                                     final MultipartFile cover,
                                     final Map<String, Object> model) {
        boolean isValid = true;
        if (!this.validator.validateTitle(music.getTitle(), model)) {
            isValid = false;
        }
        if (!this.validator.validateDescription(music.getDescription(), model)) {
            isValid = false;
        }
        return isValid;
    }

    @Transactional
    public void editMusic(final MusicBean music) {
        // this.articleMapper.update(music);
        // this.musicMapper.updateMusic(music);
    }

    public PaginateSupport getUserMusicList(final UserBean userBean,
                                            final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userBean", userBean);
        param.put("publishFlg", "1");
        // final int itemCount = this.musicMapper.count(param);
        // paginate.setItemCount(itemCount);
        paginate.compute();
        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        param.put("order", "A.ARTICLE_ID DESC");
        // final List<MusicBean> items = this.musicMapper.fetchList(param);
        // paginate.setItems(items);
        return paginate;
    }

    public PaginateSupport getConsoleMusicList(final UserBean userBean,
                                               final String searchQuery,
                                               final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userBean", userBean);
        param.put("searchQuery", searchQuery);
        // final int itemCount = this.musicMapper.count(param);
        // paginate.setItemCount(itemCount);
        paginate.compute();
        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        param.put("order", "A.ARTICLE_ID DESC");
        // final List<MusicBean> items = this.musicMapper.fetchList(param);
        // paginate.setItems(items);
        return paginate;
    }

    @Transactional
    public void deleteMusic(final MusicBean music) {
        // this.articleMapper.delete(music);
        // this.musicMapper.delete(music);
    }

    public PaginateSupport getMusicCollectList(final UserBean userBean,
                                               final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userBean.getId());
        // final int itemCount = this.collectMapper.countForMusic(param);
        // paginate.setItemCount(itemCount);
        paginate.compute();
        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        // final List<MusicBean> items =
        // this.collectMapper.fetchListForMusic(param);
        // paginate.setItems(items);
        return paginate;
    }

    public int countUnpublish() {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("publishFlg", "0");
        // return this.musicMapper.count(param);
        return 0;
    }

}
