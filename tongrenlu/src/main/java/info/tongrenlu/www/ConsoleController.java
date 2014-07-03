package info.tongrenlu.www;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TimelineBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.ConsoleUserService;
import info.tongrenlu.support.PaginateSupport;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
@Transactional
public class ConsoleController {

    @Autowired
    private ConsoleUserService userService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/console")
    public String doGetIndex(@ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        final Integer id = loginUser.getId();
        final UserBean userBean = this.userService.getById(id);
        model.addAttribute("userBean", userBean);
        return "console/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/timeline")
    @ResponseBody
    public Map<String, Object> doGetTimeline(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                             @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final Map<String, Object> model = new HashMap<>();
        final PaginateSupport<TimelineBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("loginUser", loginUser);
        this.userService.searchTimeline(page);
        model.put("page", page);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/like/music")
    public String doGetLikeMusic(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                 @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                 final Model model) {
        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("userBean", loginUser);
        this.userService.searchLikeMusic(page);
        model.addAttribute("page", page);
        return "console/user/music";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/like/comic")
    public String doGetLikeComic(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                 @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                 final Model model) {
        final PaginateSupport<ComicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("userBean", loginUser);
        this.userService.searchLikeComic(page);
        model.addAttribute("page", page);

        return "console/user/comic";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/follow")
    public String doGetFollow(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Model model) {
        final PaginateSupport<UserBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("userBean", loginUser);
        this.userService.searchFollow(page);
        model.addAttribute("page", page);

        return "console/user/follow";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/follower")
    public String doGetFollower(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                final Model model) {
        final PaginateSupport<UserBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("userBean", loginUser);
        this.userService.searchFollower(page);
        model.addAttribute("page", page);

        return "console/user/follower";
    }

}
