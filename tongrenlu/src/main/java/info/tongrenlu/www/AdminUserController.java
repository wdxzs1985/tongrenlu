package info.tongrenlu.www;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.domain.UserProfileBean;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.AdminUserService;
import info.tongrenlu.service.ConsoleLibraryService;
import info.tongrenlu.service.ConsoleMusicService;
import info.tongrenlu.support.PaginateSupport;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(method = RequestMethod.GET, value = "{userId}")
    public String doGetView(@PathVariable final Integer userId,
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
        return "admin/user/view";
    }

    @RequestMapping(method = RequestMethod.POST, value = "{userId}/library")
    public String doPostLibrary(@PathVariable final Integer userId,
                                final Integer articleId,
                                final Model model,
                                final Locale locale) {
        final UserProfileBean userBean = this.userService.getById(userId);
        if (this.libraryService.updateStatus(articleId, userBean, locale)) {
            return "redirect:/admin/user/" + userId;
        } else {
            throw new PageNotFoundException(this.messageSource.getMessage("error.pageNotFound", null, locale));
        }
    }
}
