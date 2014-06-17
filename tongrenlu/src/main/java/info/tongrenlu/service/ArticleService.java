package info.tongrenlu.service;

import info.tongrenlu.constants.RedFlg;
import info.tongrenlu.constants.TranslateFlg;
import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.dao.ArticleDao;
import info.tongrenlu.service.dao.ComicDao;
import info.tongrenlu.service.dao.FileDao;
import info.tongrenlu.service.dao.MusicDao;
import info.tongrenlu.service.dao.TagDao;
import info.tongrenlu.service.dao.UserDao;
import info.tongrenlu.support.PaginateSupport;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleDao articleDao = null;
    @Autowired
    private UserDao userDao = null;
    @Autowired
    private TagDao tagDao = null;
    @Autowired
    private FileDao fileDao = null;
    @Autowired
    private ComicDao comicDao = null;
    @Autowired
    private MusicDao musicDao = null;
    // @Autowired
    // private GameDao gameDao = null;
    @Autowired
    private SolrService solrService = null;

    public String doGetHomeIndex(final UserBean loginUser, final Model model) {
        final String redFlg = RedFlg.NOT_RED;
        String translateFlg = TranslateFlg.NOT_TRANSLATED;
        if (loginUser != null) {
            // redFlg = loginUser.getRedFlg();
            translateFlg = loginUser.getTranslateFlg();
        }
        // music
        model.addAttribute("lastCommentMusic",
                           this.musicDao.getlastCommentMusic(30));
        // comic
        model.addAttribute("lastCommentComic",
                           this.comicDao.getlastCommentComic(redFlg,
                                                             translateFlg,
                                                             20));

        return "home/index";
    }

    public Map<String, Object> doPostRemoveTag(final String articleId,
                                               final String tagId) {
        final Map<String, Object> model = new HashMap<String, Object>();
        this.tagDao.removeTag(articleId, tagId);
        model.put("result", true);
        return model;
    }

    public void doGetDeleteFile(final String fileId) {
        final FileBean fileBean = this.fileDao.getFileInfo(fileId);
        final String ext = fileBean.getExtension();
        if (StringUtils.equalsIgnoreCase(ext, FileDao.MP3)) {
            this.fileDao.deleteMp3File(fileBean);
        } else {
            this.fileDao.deleteJpgFile(fileBean);
        }
    }

    public Map<String, Object> doPostArticleCollect(final UserBean loginUser,
                                                    final String articleId) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        if (this.userDao.validateUserOnline(loginUser, model)) {
            final String userId = loginUser.getUserId();
            final boolean collected = this.articleDao.hasCollected(userId,
                                                                   articleId);
            if (collected) {
                this.articleDao.removeCollect(userId, articleId);
            } else {
                this.articleDao.addCollect(userId, articleId);
            }
            model.put("collected", !collected);
            model.put("result", true);
        }
        return model;
    }

    public Map<String, Object> doPostArticleRecommend(final String articleId) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        final ArticleBean articleBean = this.articleDao.getArticleBean(articleId);
        if (articleBean != null) {
            String recommend = articleBean.getRecommend();
            if (StringUtils.equals("1", recommend)) {
                recommend = "0";
            } else {
                recommend = "1";
            }
            this.articleDao.recommendArticle(articleId, recommend);
            model.put("recommend", recommend);
            model.put("result", true);
        }
        return model;
    }

    public String doGetSearch(final UserBean loginUser,
                              final Integer page,
                              final String searchQuery,
                              final Model model) {
        if (StringUtils.isBlank(searchQuery)) {
            return "search/index";
        }

        model.addAttribute("searchQuery", searchQuery);

        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        paginate.compute();

        model.addAttribute("page",
                           this.solrService.search(searchQuery, paginate));
        return "search/results";
    }

}
