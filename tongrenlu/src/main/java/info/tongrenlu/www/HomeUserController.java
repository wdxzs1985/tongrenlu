package info.tongrenlu.www;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.CommentService;
import info.tongrenlu.service.UserProfileService;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
public class HomeUserController {

    @Autowired
    private UserProfileService userService = null;
    @Autowired
    private CommentService commentService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}")
    public String doGetIndex(@PathVariable final Integer id,
                             final Model model,
                             final HttpServletRequest request) {
        return "user/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/follow")
    public String doGetFollow(@PathVariable final String userId,
                              @RequestParam(required = false) final Integer page,
                              final Model model,
                              final HttpServletRequest request) {
        return "user/follow";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/fans")
    public String doGetFans(@PathVariable final String userId,
                            @RequestParam(required = false) final Integer page,
                            final Model model,
                            final HttpServletRequest request) {
        return "user/fans";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/comic")
    public String doGetComic(@PathVariable final String userId,
                             @RequestParam(required = false) final Integer page,
                             final Model model,
                             final HttpServletRequest request) {
        return "user/comic";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/music")
    public String doGetMusic(@PathVariable final String userId,
                             @RequestParam(required = false) final Integer page,
                             final Model model,
                             final HttpServletRequest request) {
        return "user/music";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/{userId}/follow")
    @ResponseBody
    public Map<String, Object> doPostFollow(@PathVariable final String userId,
                                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                            final Model model) {
        return this.userService.doPostFollow(loginUser, userId);
    }
}
