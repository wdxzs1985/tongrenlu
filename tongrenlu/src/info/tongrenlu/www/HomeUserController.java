package info.tongrenlu.www;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.CommentService;
import info.tongrenlu.service.UserService;
import info.tongrenlu.support.ControllerSupport;
import info.tongrenlu.support.LoginUserSupport;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeUserController extends ControllerSupport {

    @Autowired
    private UserService userService = null;
    @Autowired
    private CommentService commentService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    public String doGetIndex(@PathVariable final String userId,
                             final Model model,
                             final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.userService.doGetUserIndex(loginUser, userId, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/follow")
    public String doGetFollow(@PathVariable final String userId,
                              @RequestParam(required = false) final Integer page,
                              final Model model,
                              final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.userService.doGetFollow(loginUser, userId, page, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/fans")
    public String doGetFans(@PathVariable final String userId,
                            @RequestParam(required = false) final Integer page,
                            final Model model,
                            final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.userService.doGetFans(loginUser, userId, page, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/comic")
    public String doGetComic(@PathVariable final String userId,
                             @RequestParam(required = false) final Integer page,
                             final Model model,
                             final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.userService.doGetComic(loginUser, userId, page, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/music")
    public String doGetMusic(@PathVariable final String userId,
                             @RequestParam(required = false) final Integer page,
                             final Model model,
                             final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.userService.doGetMusic(loginUser, userId, page, model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/{userId}/follow")
    @ResponseBody
    public Map<String, Object> doPostFollow(@PathVariable final String userId,
                                            final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.userService.doPostFollow(loginUser, userId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/comment")
    @ResponseBody
    public Map<String, Object> doGetUserComment(@PathVariable final String userId,
                                                @RequestParam final Integer page,
                                                final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        final UserBean userBean = new UserBean();
        userBean.setUserId(userId);
        return this.commentService.doGetUserComment(loginUser, userBean, page);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/{userId}/comment")
    @ResponseBody
    public Map<String, Object> doPostUserComment(@PathVariable final String userId,
                                                 final String content,
                                                 final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        final UserBean userBean = new UserBean();
        userBean.setUserId(userId);
        return this.commentService.doPostUserComment(loginUser,
                                                     userBean,
                                                     content);
    }
}
