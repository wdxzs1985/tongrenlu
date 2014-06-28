package info.tongrenlu.www;

import info.tongrenlu.domain.TimelineBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.HomeUserSerivce;
import info.tongrenlu.support.PaginateSupport;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
@RequestMapping("/user")
public class HomeUserController {

    @Autowired
    private HomeUserSerivce userService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    public String doGetIndex(@PathVariable final Integer userId,
                             final Model model,
                             final Locale locale) {
        final UserBean userBean = this.userService.getById(userId);
        model.addAttribute("userBean", userBean);
        return "home/user/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/follow")
    @ResponseBody
    public Map<String, Object> doGetFollow(@PathVariable final Integer userId,
                                           @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                           final Locale locale) {
        final Map<String, Object> model = new HashMap<>();
        this.userService.isFollower(userId, loginUser, model, locale);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{userId}/follow")
    @ResponseBody
    public Map<String, Object> doPostFollow(@PathVariable final Integer userId,
                                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                            final Locale locale) {
        final Map<String, Object> model = new HashMap<>();
        this.userService.doFollow(userId, loginUser, model, locale);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/timeline")
    @ResponseBody
    public Map<String, Object> doGetTimeline(@PathVariable final Integer userId,
                                             @RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                             final Locale locale) {
        final Map<String, Object> model = new HashMap<>();
        final PaginateSupport<TimelineBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("userId", userId);
        page.addParam("loginUser", loginUser);
        this.userService.searchTimeline(page);
        model.put("page", page);
        return model;
    }
}
