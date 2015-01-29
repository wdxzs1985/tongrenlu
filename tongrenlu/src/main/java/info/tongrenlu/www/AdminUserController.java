package info.tongrenlu.www;

import info.tongrenlu.domain.AuthFileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.domain.UserProfileBean;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.AdminUserService;
import info.tongrenlu.service.ConsoleLibraryService;
import info.tongrenlu.service.ConsoleMusicService;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/user")
@Transactional
public class AdminUserController {

    public static final int PAGE_SIZE = 12;

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private AdminUserService userService;
    @Autowired
    private ConsoleMusicService musicService = null;
    @Autowired
    private ConsoleLibraryService libraryService = null;

    private void throwExceptionWhenNotFound(final UserBean userBean, final Locale locale) {
        if (userBean == null) {
            throw new PageNotFoundException(this.messageSource.getMessage("error.pageNotFound", null, locale));
        }
    }

    private void throwExceptionWhenNotFound(final MusicBean musicBean, final Locale locale) {
        if (musicBean == null) {
            throw new PageNotFoundException(this.messageSource.getMessage("error.pageNotFound", null, locale));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @RequestParam(value = "q", required = false) final String query,
                             final Model model) {
        final PaginateSupport<UserBean> page = new PaginateSupport<>(pageNumber, PAGE_SIZE);
        page.addParam("query", query);
        this.userService.searchUser(page);
        model.addAttribute("page", page);

        return "admin/user/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "auth")
    public String doGetAuth(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                            @RequestParam(value = "q", required = false) final String query,
                            final Model model) {
        final PaginateSupport<UserBean> page = new PaginateSupport<>(pageNumber, PAGE_SIZE);
        page.addParam("query", query);
        this.userService.searchUser(page);
        model.addAttribute("page", page);

        return "admin/user/auth";
    }

    @RequestMapping(method = RequestMethod.GET, value = "{userId}/auth")
    public String doGetAuthView(@PathVariable final Integer userId,
                                @RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                final Model model,
                                final Locale locale) {
        final UserProfileBean userBean = this.userService.getById(userId);
        this.throwExceptionWhenNotFound(userBean, locale);

        model.addAttribute("userBean", userBean);

        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber, PAGE_SIZE);
        page.addParam("userBean", userBean);
        page.addParam("status", 0);
        this.libraryService.searchLibrary(page);
        model.addAttribute("page", page);
        return "admin/user/auth_view";
    }

    @RequestMapping(method = RequestMethod.POST, value = "{userId}/auth")
    public String doPostAuth(@PathVariable final Integer userId,
                             final Integer articleId,
                             final Model model,
                             final Locale locale) {
        final UserProfileBean userBean = this.userService.getById(userId);
        this.throwExceptionWhenNotFound(userBean, locale);
        final MusicBean musicBean = this.musicService.getById(articleId);
        this.throwExceptionWhenNotFound(musicBean, locale);
        this.libraryService.updateStatus(musicBean, userBean, locale);
        return "redirect:/admin/user/" + userId + "/auth";
    }

    @RequestMapping(method = RequestMethod.GET, value = "{userId}/auth/upload")
    public String doGetAuthFile(@PathVariable final Integer userId, final Model model, final Locale locale) {
        final UserProfileBean userBean = this.userService.getById(userId);
        this.throwExceptionWhenNotFound(userBean, locale);
        model.addAttribute("userBean", userBean);
        return "admin/user/auth_upload";
    }

    @RequestMapping(method = RequestMethod.GET, value = "{userId}/auth/file")
    @ResponseBody
    public Map<String, Object> doGetAuthFile(@PathVariable final Integer userId, final Locale locale) {
        final UserProfileBean userBean = this.userService.getById(userId);
        this.throwExceptionWhenNotFound(userBean, locale);
        final Map<String, Object> model = new HashMap<String, Object>();
        final List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
        final List<AuthFileBean> fileBeanList = this.libraryService.getAuthFiles(userBean);
        for (final AuthFileBean authFileBean : fileBeanList) {
            final Map<String, Object> fileModel = new HashMap<String, Object>();
            fileModel.put("id", authFileBean.getId());
            fileModel.put("userId", authFileBean.getUserBean().getId());
            files.add(fileModel);
        }
        model.put("files", files);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{userId}/auth/file")
    @ResponseBody
    public Map<String, Object> doPostAuthFile(@PathVariable final Integer userId,
                                              @RequestParam(value = "files[]") final MultipartFile[] uploads,
                                              final Locale locale) {
        final UserProfileBean userBean = this.userService.getById(userId);
        this.throwExceptionWhenNotFound(userBean, locale);
        final Map<String, Object> model = new HashMap<String, Object>();
        final List<Map<String, Object>> files = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(uploads)) {
            for (final MultipartFile upload : uploads) {
                final Map<String, Object> fileModel = new HashMap<String, Object>();
                this.libraryService.saveAuthFile(upload, userBean, fileModel, locale);
                files.add(fileModel);
            }
        }
        model.put("files", files);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{userId}/auth/file/{authFileId}/delete")
    @ResponseBody
    public Map<String, Object> doPostDeleteAuthFile(@PathVariable final Integer userId,
                                                    @PathVariable final Integer authFileId,
                                                    final Locale locale) {
        final UserProfileBean userBean = this.userService.getById(userId);
        this.throwExceptionWhenNotFound(userBean, locale);
        final Map<String, Object> model = new HashMap<String, Object>();
        this.libraryService.delete(authFileId, userBean);
        model.put("true", true);
        return model;
    }
}
