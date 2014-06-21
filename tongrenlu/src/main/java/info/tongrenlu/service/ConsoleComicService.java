package info.tongrenlu.service;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.ComicDao;
import info.tongrenlu.manager.TagDao;
import info.tongrenlu.support.PaginateSupport;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ConsoleComicService {

    @Autowired
    private ComicDao comicDao = null;
    @Autowired
    private FileService fileDao = null;
    @Autowired
    private TagDao tagDao = null;
    @Autowired
    private SolrService solrService = null;

    public String doGetIndex(final UserBean loginUser,
                             final Integer page,
                             final String searchQuery,
                             final Model model) {
        final PaginateSupport paginate = new PaginateSupport(page);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute(this.comicDao.getConsoleComicList(loginUser,
                                                             searchQuery,
                                                             paginate));
        return "console/comic/index";
    }

    public String doPostInput(final UserBean loginUser,
                              final ComicBean comicBean,
                              final MultipartFile cover,
                              final String[] tagIdArray,
                              final String[] tagArray,
                              final Map<String, Object> model) {
        comicBean.setUserBean(loginUser);
        if (this.comicDao.validateCreateComic(comicBean, model)) {
            this.comicDao.createComic(comicBean);
            this.tagDao.addArticleTag(comicBean, tagIdArray);
            this.fileDao.saveCover(comicBean, cover);
            return "redirect:/console/comic/finish";
        }

        model.put("articleBean", comicBean);
        model.put("inputTagBeanList",
                  this.tagDao.resolveInputTagBeanList(tagIdArray, tagArray));
        return "console/comic/input";
    }

    public String doGetEdit(final UserBean loginUser,
                            final String articleId,
                            final Model model) {
        final ComicBean comicBean = this.comicDao.getComicById(articleId, null);
        if (comicBean == null) {
            return "console/error/404";
        }
        if (!comicBean.getUserBean().equals(loginUser)) {
            return "console/error/403";
        }
        model.addAttribute("articleBean", comicBean);
        model.addAttribute(this.tagDao.getArticleTag(articleId));
        return "console/comic/edit";
    }

    public String doPostEdit(final UserBean loginUser,
                             final String articleId,
                             final ComicBean comicBean,
                             final MultipartFile cover,
                             final String[] tagIdArray,
                             final String[] tagArray,
                             final Map<String, Object> model) {
        final ComicBean comic = this.comicDao.getComicById(articleId, null);
        if (comic == null) {
            return "console/error/404";
        }
        if (!comicBean.getUserBean().equals(loginUser)) {
            return "console/error/403";
        }
        if (this.comicDao.validateEditComic(comicBean, cover, model)) {
            this.comicDao.editComic(comicBean);
            this.tagDao.addArticleTag(comic, tagIdArray);
            this.fileDao.saveCover(comicBean, cover);
            this.solrService.createComicIndex(comicBean, true);
            return "redirect:/console/comic";
        }
        model.put("articleBean", comicBean);
        model.put("inputTagBeanList",
                  this.tagDao.resolveInputTagBeanList(tagIdArray, tagArray));
        return "console/comic/edit";
    }

    public String doGetDelete(final UserBean loginUser,
                              final String articleId,
                              final Model model) {
        final ComicBean comicBean = this.comicDao.getComicById(articleId, null);
        if (comicBean == null) {
            return "console/error/404";
        }
        if (!comicBean.getUserBean().equals(loginUser)) {
            return "console/error/403";
        }
        this.comicDao.deleteComic(comicBean);
        return "redirect:/console/comic";
    }

    public String doGetCollect(final UserBean loginUser,
                               final Integer page,
                               final Model model) {
        final PaginateSupport paginate = new PaginateSupport(page);
        model.addAttribute(this.comicDao.getComicCollectList(loginUser,
                                                             paginate));
        return "console/comic/collect";
    }

}
