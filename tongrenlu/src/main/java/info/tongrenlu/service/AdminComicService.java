package info.tongrenlu.service;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.manager.ArticleDao;
import info.tongrenlu.manager.ComicDao;
import info.tongrenlu.manager.TagDao;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class AdminComicService {

    @Autowired
    private ComicDao comicDao = null;
    @Autowired
    private ArticleDao articleDao = null;
    @Autowired
    private FileService fileDao = null;
    @Autowired
    private TagDao tagDao = null;
    @Autowired
    private SolrService solrService = null;

    public String doGetComicIndex(final Integer page,
                                  final String searchQuery,
                                  final Model model) {
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute(this.comicDao.getAdminComicList(paginate,
                                                           searchQuery));
        return "admin/comic/index";
    }

    public String doGetComicView(final String articleId, final Model model) {
        final ComicBean comicBean = this.comicDao.getComicById(articleId, null);
        if (comicBean == null) {
            return "cosole/error/404";
        }
        model.addAttribute("articleBean", comicBean);
        model.addAttribute(this.fileDao.getArticleFiles(articleId,
                                                        FileService.JPG));
        model.addAttribute(this.tagDao.getArticleTag(articleId));
        return "admin/comic/view";
    }

    public String doPostComicPublish(final String articleId, final Model model) {
        final ComicBean articleBean = this.comicDao.getComicById(articleId,
                                                                 null);
        if (articleBean == null) {
            return "cosole/error/404";
        }
        this.articleDao.publish(articleBean);
        this.solrService.createComicIndex(articleBean, true);
        return "redirect:/admin/comic";
    }

    public String doGetUpload(final String articleId, final Model model) {
        final ComicBean comic = this.comicDao.getComicById(articleId, null);
        if (comic == null) {
            return "cosole/error/404";
        }
        model.addAttribute("articleBean", comic);
        return "admin/comic/upload";
    }

    public Map<String, Object> doGetComicFile(final String articleId,
                                              final HttpServletRequest request) {
        final Map<String, Object> modelMap = new HashMap<String, Object>();
        final List<Object> fileList = new ArrayList<Object>();
        final List<FileBean> files = this.fileDao.getArticleFiles(articleId,
                                                                  FileService.JPG);
        for (final FileBean fileBean : files) {
            fileList.add(this.fileDao.prepareJpgFileBeanModel(fileBean, request));
        }
        modelMap.put("files", fileList);
        return modelMap;
    }

    public Map<String, Object> doPostComicFile(final String articleId,
                                               final MultipartFile[] files,
                                               final HttpServletRequest request) {
        final Map<String, Object> modelMap = new HashMap<String, Object>();
        final List<Object> fileList = new ArrayList<Object>();
        if (ArrayUtils.isNotEmpty(files)) {
            for (final MultipartFile fileItem : files) {
                final FileBean fileBean = this.fileDao.createImageFileInfo(articleId,
                                                                           fileItem);
                fileList.add(this.fileDao.prepareJpgFileBeanModel(fileBean,
                                                                  request));
            }
        }
        modelMap.put("files", fileList);
        return modelMap;
    }

    public String doGetSort(final String articleId, final Model model) {
        final ComicBean comic = this.comicDao.getComicById(articleId, null);
        if (comic == null) {
            return "cosole/error/404";
        }
        model.addAttribute("articleBean", comic);
        final List<FileBean> fileList = this.fileDao.getArticleFiles(articleId,
                                                                     FileService.JPG);
        model.addAttribute(fileList);
        return "admin/comic/sort";
    }

    public String doPostSort(final String articleId, final String[] fileIdArray) {
        final ComicBean comic = this.comicDao.getComicById(articleId, null);
        if (comic == null) {
            return "cosole/error/404";
        }
        if (ArrayUtils.isNotEmpty(fileIdArray)) {
            this.fileDao.sortFiles(fileIdArray);
        }
        return "redirect:/admin/comic/" + articleId;
    }

    public int countUnpublish() {
        return 0;
    }

}
