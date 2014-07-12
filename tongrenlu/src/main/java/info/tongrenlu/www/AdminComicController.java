package info.tongrenlu.www;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.ConsoleComicService;
import info.tongrenlu.service.FileService;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/admin/comic")
@Transactional
public class AdminComicController {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private ConsoleComicService comicService = null;
    @Autowired
    private FileService fileService = null;

    private void throwExceptionWhenNotFound(final ComicBean comicBean,
                                            final Locale locale) {
        if (comicBean == null) {
            throw new PageNotFoundException(this.messageSource.getMessage("error.pageNotFound",
                                                                          null,
                                                                          locale));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @RequestParam(value = "q", required = false) final String query,
                             final Model model) {
        final PaginateSupport<ComicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("query", query);
        this.comicService.searchComic(page);
        model.addAttribute("page", page);
        return "admin/comic/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}")
    public String doGetView(@PathVariable final Integer articleId,
                            final Model model,
                            final Locale locale) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotFound(comicBean, locale);

        model.addAttribute("articleBean", comicBean);

        return "admin/comic/view";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/edit")
    public String doGetEdit(@PathVariable final Integer articleId,
                            final Model model,
                            final Locale locale) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotFound(comicBean, locale);

        final String[] tags = this.comicService.getTags(comicBean);

        model.addAttribute("articleBean", comicBean);
        model.addAttribute("tags", tags);

        return "admin/comic/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/edit")
    public String doPostEdit(@PathVariable final Integer articleId,
                             final String title,
                             final String description,
                             final String redFlg,
                             final String translateFlg,
                             @RequestParam(value = "tags[]", required = false) final String[] tags,
                             @RequestParam final MultipartFile cover,
                             final Model model,
                             final Locale locale) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotFound(comicBean, locale);

        comicBean.setTitle(title);
        comicBean.setDescription(description);
        comicBean.setRedFlg(redFlg);
        comicBean.setTranslateFlg(translateFlg);

        final boolean result = this.comicService.doEdit(comicBean,
                                                        tags,
                                                        model.asMap(),
                                                        locale);

        if (result) {
            this.fileService.saveCover(comicBean, cover);
            return "redirect:/admin/comic/" + comicBean.getId();
        }

        model.addAttribute("articleBean", comicBean);
        model.addAttribute("tags", tags);

        return "admin/comic/edit";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/delete")
    public String doGetDelete(@PathVariable final Integer articleId,
                              final Locale locale) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotFound(comicBean, locale);

        this.comicService.doDelete(comicBean);

        return "redirect:/admin/comic";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/picture/upload")
    public String doGetPictureUpload(@PathVariable final Integer articleId,
                                     @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                     final Model model,
                                     final Locale locale) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotFound(comicBean, locale);

        model.addAttribute("articleBean", comicBean);

        return "admin/comic/picture_upload";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/picture/sort")
    public String doGetPictureSort(@PathVariable final Integer articleId,
                                   final Model model,
                                   final RedirectAttributes redirectAttr,
                                   final Locale locale) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotFound(comicBean, locale);

        final List<FileBean> fileList = this.comicService.getPictureFileList(comicBean);
        if (CollectionUtils.isNotEmpty(fileList)) {
            model.addAttribute("articleBean", comicBean);
            model.addAttribute("fileList", fileList);
            return "admin/comic/picture_sort";
        } else {
            final String error = this.messageSource.getMessage("console.article.sort.noFile",
                                                               null,
                                                               locale);
            redirectAttr.addFlashAttribute("error", error);
            return "redirect:/admin/comic/" + articleId + "/picture/upload";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/picture/sort")
    public String doPostPictureSort(@PathVariable final Integer articleId,
                                    @RequestParam(value = "fileId[]") final Integer[] fileId,
                                    final Model model,
                                    final Locale locale) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotFound(comicBean, locale);

        final List<FileBean> fileList = new ArrayList<FileBean>();
        for (int i = 0; i < fileId.length; i++) {
            final FileBean fileBean = new FileBean();
            fileBean.setId(fileId[i]);
            fileBean.setOrderNo(i + 1);

            fileList.add(fileBean);
        }
        this.comicService.updatePictureFile(fileList);

        return "redirect:/admin/comic/" + articleId;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/publish")
    public String doGetComicPublish(@PathVariable final Integer articleId,
                                    final Model model,
                                    final Locale locale) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotFound(comicBean, locale);

        this.comicService.publish(comicBean);

        return "redirect:/admin/comic";
    }
}
