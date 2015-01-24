package info.tongrenlu.www;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.ConsoleMusicService;
import info.tongrenlu.service.FileService;
import info.tongrenlu.support.PaginateSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
@Transactional
public class ConsoleLibraryController {

    public static final int PAGE_SIZE = 12;

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private ConsoleMusicService musicService = null;
    @Autowired
    private FileService fileService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/console/library")
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber,
                PAGE_SIZE);
        page.addParam("userBean", loginUser);
        page.addParam("status", 1);
        this.musicService.searchLibrary(page);
        model.addAttribute("page", page);
        return "console/user/library";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/library/auth")
    public String doGetUpload(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Model model) {
        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber,
                                                                      PAGE_SIZE);
        page.addParam("userBean", loginUser);
        page.addParam("status", 1);
        this.musicService.searchLibrary(page);
        model.addAttribute("page", page);
        return "console/user/library_auth";
    }
}
