package info.tongrenlu.service;

import info.tongrenlu.constants.RedFlg;
import info.tongrenlu.constants.TranslateFlg;
import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.ArticleDao;
import info.tongrenlu.manager.ComicDao;
import info.tongrenlu.manager.TagDao;
import info.tongrenlu.manager.UserDao;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Transactional
public class HomeComicService {
    @Autowired
    private ComicDao comicDao = null;
    @Autowired
    private ArticleDao articleDao = null;
    @Autowired
    private UserDao userDao = null;
    @Autowired
    private FileService fileDao = null;
    @Autowired
    private TagDao tagDao = null;

    public String doGetIndex(final UserBean loginUser,
                             final Integer page,
                             final String searchQuery,
                             final Model model) {
        final String redFlg = RedFlg.NOT_RED;
        final String translateFlg = TranslateFlg.NOT_TRANSLATED;
        final String userId = null;
        if (loginUser != null) {
            // redFlg = loginUser.getRedFlg();
            // translateFlg = loginUser.getTranslateFlg();
            // userId = loginUser.getId();
        }

        final PaginateSupport paginate = new PaginateSupport(page);

        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("page", this.comicDao.getComicList(searchQuery,
                                                              null,
                                                              redFlg,
                                                              translateFlg,
                                                              userId,
                                                              paginate));
        model.addAttribute("access10Comic",
                           this.comicDao.getComicAccess(redFlg,
                                                        translateFlg,
                                                        20));
        return "home/comic/index";
    }

    public String doGetView(final UserBean loginUser,
                            final String articleId,
                            final Model model) {
        final String redFlg = RedFlg.NOT_RED;
        final String translateFlg = TranslateFlg.NOT_TRANSLATED;
        final String userId = null;
        if (loginUser != null) {
            // redFlg = loginUser.getRedFlg();
            // translateFlg = loginUser.getTranslateFlg();
            // userId = loginUser.getUserId();
        }
        final ComicBean comicBean = this.comicDao.getComicById(articleId,
                                                               userId);
        if (comicBean == null) {
            return "home/error/404";
        } else if (loginUser == null) {
            if (!StringUtils.equals(comicBean.getPublishFlg(), "1")) {
                return "home/error/403";
            } else if (StringUtils.equals(comicBean.getRedFlg(), "1")) {
                return "home/error/403";
            }
        } else if (!loginUser.isAdmin()) {
            if (!comicBean.getUserBean().equals(loginUser)) {
                if (!StringUtils.equals(comicBean.getPublishFlg(), "1")) {
                    return "home/error/403";
                } else if (StringUtils.equals(comicBean.getRedFlg(), "1")) {
                    if (!StringUtils.equals(redFlg, "1")) {
                        return "home/error/403";
                    }
                }
            }
        }
        model.addAttribute("articleBean", comicBean);
        model.addAttribute(this.tagDao.getArticleTag(articleId));
        model.addAttribute("articleBeanList",
                           this.comicDao.getUserComicList(comicBean.getUserBean(),
                                                          redFlg,
                                                          translateFlg,
                                                          1,
                                                          6));
        if (loginUser != null) {
            // model.addAttribute("hasFollowed",
            // this.userDao.hasFollowed(loginUser,
            // comicBean.getUserBean()));
        }
        // access
        this.articleDao.addAccess(comicBean, loginUser);
        return "home/comic/view";
    }

    public List<Object> doGetPlaylist(final UserBean loginUser,
                                      final String articleId,
                                      final HttpServletRequest request) {
        final List<Object> modelList = new ArrayList<Object>();
        // final List<FileBean> fileList =
        // this.fileDao.getArticleFiles(articleId,
        // FileService.JPG);
        // for (final FileBean fileBean : fileList) {
        // modelList.add(this.prepareFileBeanModel(fileBean, request));
        // }
        return modelList;
    }

    private Object prepareFileBeanModel(final FileBean fileBean,
                                        final HttpServletRequest request) {

        final String serverName = request.getServerName();
        final boolean isLocal = StringUtils.contains(serverName, "127.0.0.1") || StringUtils.contains(serverName,
                                                                                                      "192.168.11.");
        final String FILE_PATH = isLocal ? "http://192.168.11.9/resource"
                : "/resource";
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("normal_img", FILE_PATH + "/"
                + fileBean.getId()
                + "/"
                + fileBean.getId()
                + "_800.jpg");
        model.put("large_img", FILE_PATH + "/"
                + fileBean.getId()
                + "/"
                + fileBean.getId()
                + "_1600.jpg");
        return model;
    }
}
