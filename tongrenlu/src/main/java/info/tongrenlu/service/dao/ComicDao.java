package info.tongrenlu.service.dao;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.persistence.MArticleMapper;
import info.tongrenlu.persistence.RCollectMapper;
import info.tongrenlu.persistence.RComicMapper;
import info.tongrenlu.service.validator.ArticleBeanValidator;
import info.tongrenlu.support.PaginateSupport;
import info.tongrenlu.support.SequenceSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ComicDao extends SequenceSupport {

    @Autowired
    private ArticleBeanValidator validator = null;
    @Autowired
    private MArticleMapper articleMapper = null;
    @Autowired
    private RComicMapper comicMapper = null;
    @Autowired
    private RCollectMapper collectMapper = null;

    public PaginateSupport getComicList(final String searchQuery,
                                        final String tagId,
                                        final String redFlg,
                                        final String translateFlg,
                                        final String collectUserId,
                                        final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("searchQuery", searchQuery);
        param.put("tagId", tagId);
        param.put("publishFlg", "1");
        param.put("redFlg", redFlg);
        param.put("translateFlg", translateFlg);
        param.put("collectUserId", collectUserId);

        final int itemCount = this.comicMapper.count(param);
        paginate.setItemCount(itemCount);
        paginate.compute();

        param.put("order", "A.ARTICLE_ID DESC");
        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        final List<ComicBean> items = this.comicMapper.fetchList(param);
        paginate.setItems(items);
        return paginate;
    }

    public PaginateSupport getAdminComicList(final PaginateSupport paginate,
                                             final String searchQuery) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("searchQuery", searchQuery);
        final int itemCount = this.comicMapper.count(param);
        paginate.setItemCount(itemCount);
        paginate.compute();
        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        param.put("order", "A.ARTICLE_ID DESC");
        final List<ComicBean> items = this.comicMapper.fetchList(param);
        paginate.setItems(items);
        return paginate;
    }

    public ComicBean getComicById(final String articleId,
                                  final String collectUserId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        param.put("collectUserId", collectUserId);
        return this.comicMapper.fetchBean(param);
    }

    public List<ComicBean> getComicLastest(final String searchQuery,
                                           final String tagId,
                                           final String redFlg,
                                           final String translateFlg,
                                           final int size) {
        return this.getComicRank(searchQuery,
                                 tagId,
                                 redFlg,
                                 translateFlg,
                                 "A.PUBLISH_DATE DESC",
                                 size);
    }

    public List<ComicBean> getComicAccess(final String redFlg,
                                          final String translateFlg,
                                          final int size) {
        return this.getComicRank(null,
                                 null,
                                 redFlg,
                                 translateFlg,
                                 "NVL(V_ACCESS.CNT, 0) DESC",
                                 size);
    }

    public List<ComicBean> getComicForIndex(final String redFlg,
                                            final String translateFlg,
                                            final int size) {
        return this.getComicRank(null,
                                 null,
                                 redFlg,
                                 translateFlg,
                                 "NVL(V_COMMENT_2.COMMENT_ID,'0') DESC, A.PUBLISH_DATE DESC",
                                 size);
    }

    private List<ComicBean> getComicRank(final String searchQuery,
                                         final String tagId,
                                         final String redFlg,
                                         final String translateFlg,
                                         final String order,
                                         final int size) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("searchQuery", searchQuery);
        param.put("tagId", tagId);
        param.put("publishFlg", "1");
        param.put("redFlg", redFlg);
        param.put("translateFlg", translateFlg);
        param.put("order", order);
        param.put("start", 1);
        param.put("end", size);
        final List<ComicBean> items = this.comicMapper.fetchList(param);
        return items;
    }

    public List<ComicBean> getUserComicList(final UserBean userBean,
                                            final String redFlg,
                                            final String translateFlg,
                                            final int start,
                                            final int end) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userBean", userBean);
        param.put("publishFlg", "1");
        param.put("redFlg", redFlg);
        param.put("translateFlg", translateFlg);
        param.put("order", "A.ARTICLE_ID DESC");
        param.put("start", start);
        param.put("end", end);
        final List<ComicBean> items = this.comicMapper.fetchList(param);
        return items;
    }

    public PaginateSupport getUserComicList(final UserBean userBean,
                                            final String redFlg,
                                            final String translateFlg,
                                            final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userBean", userBean);
        param.put("publishFlg", "1");
        param.put("redFlg", redFlg);
        param.put("translateFlg", translateFlg);
        final int itemCount = this.comicMapper.count(param);
        paginate.setItemCount(itemCount);
        paginate.compute();

        param.put("order", "A.ARTICLE_ID DESC");
        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        final List<ComicBean> items = this.comicMapper.fetchList(param);
        paginate.setItems(items);
        return paginate;
    }

    public boolean validateCreateComic(final ComicBean comic,
                                       final Map<String, Object> model) {
        boolean isValid = true;
        if (!this.validator.validateTitle(comic.getTitle(), model)) {
            isValid = false;
        }
        if (!this.validator.validateDescription(comic.getDescription(), model)) {
            isValid = false;
        }
        return isValid;
    }

    @Transactional
    public void createComic(final ComicBean comic) {
        this.articleMapper.insert(comic);
        this.comicMapper.insert(comic);
    }

    public boolean validateEditComic(final ComicBean comic,
                                     final MultipartFile cover,
                                     final Map<String, Object> model) {
        boolean isValid = true;
        if (!this.validator.validateTitle(comic.getTitle(), model)) {
            isValid = false;
        }
        if (!this.validator.validateDescription(comic.getDescription(), model)) {
            isValid = false;
        }
        return isValid;
    }

    @Transactional
    public void editComic(final ComicBean comic) {
        this.articleMapper.update(comic);
        this.comicMapper.update(comic);
    }

    public PaginateSupport getConsoleComicList(final UserBean userBean,
                                               final String searchQuery,
                                               final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userBean", userBean);
        param.put("searchQuery", searchQuery);
        final int itemCount = this.comicMapper.count(param);
        paginate.setItemCount(itemCount);
        paginate.compute();
        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        param.put("order", "A.ARTICLE_ID DESC");
        final List<ComicBean> items = this.comicMapper.fetchList(param);
        paginate.setItems(items);
        return paginate;
    }

    public void deleteComic(final ComicBean comic) {
        this.articleMapper.delete(comic);
        this.comicMapper.delete(comic);
    }

    public PaginateSupport getComicCollectList(final UserBean userBean,
                                               final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userBean", userBean);
        final int itemCount = this.collectMapper.countForComic(param);
        paginate.setItemCount(itemCount);
        paginate.compute();
        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        final List<ComicBean> items = this.collectMapper.fetchListForComic(param);
        paginate.setItems(items);
        return paginate;
    }

    public int countUnpublish() {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("publishFlg", "0");
        return this.comicMapper.count(param);
    }

}
