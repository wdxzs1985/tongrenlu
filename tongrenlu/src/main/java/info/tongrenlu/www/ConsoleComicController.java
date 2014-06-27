package info.tongrenlu.www;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.exception.ForbiddenException;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.manager.FileManager;
import info.tongrenlu.service.ConsoleComicService;
import info.tongrenlu.service.FileService;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
@RequestMapping("/console/comic")
public class ConsoleComicController {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private FileService fileService = null;
    @Autowired
    private ConsoleComicService comicService = null;

    protected void throwExceptionWhenNotAllow(final ComicBean comicBean,
                                              final UserBean loginUser) {
        if (comicBean == null) {
            throw new PageNotFoundException();
        }
        if (!loginUser.equals(comicBean.getUserBean()) && !loginUser.isAdmin()) {
            throw new ForbiddenException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/input")
    public String doGetInput(final Model model) {
        final ComicBean inputComic = new ComicBean();
        final String[] tags = {};
        model.addAttribute("articleBean", inputComic);
        model.addAttribute("tags", tags);
        return "console/comic/input";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/input")
    public String doPostInput(final String title,
                              final String description,
                              final String redFlg,
                              final String translateFlg,
                              @RequestParam(value = "tags[]", required = false) final String[] tags,
                              @RequestParam final MultipartFile cover,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Model model,
                              final Locale locale) {
        final ComicBean inputComic = new ComicBean();
        inputComic.setUserBean(loginUser);
        inputComic.setTitle(title);
        inputComic.setDescription(description);
        inputComic.setRedFlg(redFlg);
        inputComic.setTranslateFlg(translateFlg);

        final boolean result = this.comicService.doCreate(inputComic,
                                                          tags,
                                                          model.asMap(),
                                                          locale);
        if (result) {
            this.fileService.saveCover(inputComic, cover);
            return "redirect:/console/comic/" + inputComic.getId();
        }

        model.addAttribute("articleBean", inputComic);
        model.addAttribute("tags", tags);
        return "console/comic/input";
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @RequestParam(value = "q", required = false) final String query,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        final PaginateSupport<ComicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("query", query);
        page.addParam("userBean", loginUser);
        this.comicService.searchComic(page);
        model.addAttribute("page", page);
        return "console/comic/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}")
    public String doGetView(@PathVariable final Integer articleId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotAllow(comicBean, loginUser);

        model.addAttribute("articleBean", comicBean);

        return "console/comic/view";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/edit")
    public String doGetEdit(@PathVariable final Integer articleId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotAllow(comicBean, loginUser);

        final String[] tags = this.comicService.getTags(comicBean);

        model.addAttribute("articleBean", comicBean);
        model.addAttribute("tags", tags);

        return "console/comic/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/edit")
    public String doPostEdit(@PathVariable final Integer articleId,
                             final String title,
                             final String description,
                             final String redFlg,
                             final String translateFlg,
                             @RequestParam(value = "tags[]", required = false) final String[] tags,
                             @RequestParam final MultipartFile cover,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model,
                             final Locale locale) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotAllow(comicBean, loginUser);

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
            return "redirect:/console/comic/" + comicBean.getId();
        }

        model.addAttribute("articleBean", comicBean);
        model.addAttribute("tags", tags);

        return "console/comic/edit";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/delete")
    public String doGetDelete(@PathVariable final Integer articleId,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotAllow(comicBean, loginUser);

        this.comicService.doDelete(comicBean);

        return "redirect:/console/comic";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/{fileId}/delete")
    @ResponseBody
    public Map<String, Object> doPostFileDelete(@PathVariable final Integer articleId,
                                                @PathVariable final Integer fileId,
                                                @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotAllow(comicBean, loginUser);

        this.comicService.removeFile(fileId);

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", true);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/picture/upload")
    public String doGetPictureUpload(@PathVariable final Integer articleId,
                                     @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                     final Model model) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotAllow(comicBean, loginUser);

        model.addAttribute("articleBean", comicBean);

        return "console/comic/picture_upload";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/picture/file")
    @ResponseBody
    public Map<String, Object> doGetPictureFile(@PathVariable final Integer articleId,
                                                @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotAllow(comicBean, loginUser);

        final Map<String, Object> model = new HashMap<String, Object>();
        final List<FileBean> fileList = this.comicService.getPictureFileList(comicBean);
        model.put("files", this.comicService.wrapFileBeanList(fileList));
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/picture/file")
    @ResponseBody
    public Map<String, Object> doPostPictureFile(@PathVariable final Integer articleId,
                                                 @RequestParam(value = "files[]") final MultipartFile[] uploads,
                                                 @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                                 final Locale locale) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotAllow(comicBean, loginUser);

        final Map<String, Object> model = new HashMap<String, Object>();
        final List<Map<String, Object>> files = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(uploads)) {
            for (final MultipartFile upload : uploads) {
                final String filename = upload.getOriginalFilename();
                final String name = FilenameUtils.getBaseName(filename);
                final String extention = FilenameUtils.getExtension(filename);

                final FileBean fileBean = new FileBean();
                fileBean.setArticleId(articleId);
                fileBean.setName(name);
                fileBean.setExtension(extention);
                fileBean.setContentType(FileManager.IMAGE);

                final Map<String, Object> fileModel = new HashMap<String, Object>();

                this.comicService.addPictureFile(fileBean,
                                                 upload,
                                                 fileModel,
                                                 locale);
                files.add(this.comicService.wrapFileBean(fileBean, fileModel));
            }
        }

        model.put("files", files);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/picture/sort")
    public String doGetPictureSort(@PathVariable final Integer articleId,
                                   @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                   final Model model,
                                   final RedirectAttributes redirectAttr,
                                   final Locale locale) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotAllow(comicBean, loginUser);

        final List<FileBean> fileList = this.comicService.getPictureFileList(comicBean);
        if (CollectionUtils.isNotEmpty(fileList)) {
            model.addAttribute("articleBean", comicBean);
            model.addAttribute("fileList", fileList);
            return "console/comic/picture_sort";
        } else {
            final String error = this.messageSource.getMessage("console.article.sort.noFile",
                                                               null,
                                                               locale);
            redirectAttr.addFlashAttribute("error", error);
            return "redirect:/console/comic/" + articleId + "/picture/upload";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/picture/sort")
    public String doPostPictureSort(@PathVariable final Integer articleId,
                                    @RequestParam(value = "fileId[]") final Integer[] fileId,
                                    @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                    final Model model,
                                    final Locale locale) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotAllow(comicBean, loginUser);

        final List<FileBean> fileList = new ArrayList<FileBean>();
        for (int i = 0; i < fileId.length; i++) {
            final FileBean fileBean = new FileBean();
            fileBean.setId(fileId[i]);
            fileBean.setOrderNo(i + 1);

            fileList.add(fileBean);
        }

        return "redirect:/console/comic/" + articleId;
    }
}
