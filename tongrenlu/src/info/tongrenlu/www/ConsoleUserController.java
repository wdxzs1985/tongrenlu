package info.tongrenlu.www;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.UserService;
import info.tongrenlu.support.ControllerSupport;
import info.tongrenlu.support.LoginUserSupport;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ConsoleUserController extends ControllerSupport {

    @Autowired
    private UserService userService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/console")
    public String doGetIndex(final Model model, final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.userService.doGetConsoleIndex(loginUser, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/user/setting")
    public String doGetUserSetting(final Model model) {
        return "console/user/setting";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/user/setting")
    public String doPostUserSetting(final UserBean userBean,
                                    @RequestParam final MultipartFile avatar,
                                    final Model model,
                                    final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.userService.doPostUserSetting(loginUser,
                                                  userBean,
                                                  avatar,
                                                  model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/user/password")
    public String doGetPassword(final Model model) {
        return "console/user/password";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/user/password")
    public String doPostPassword(final String oldPassword,
                                 final String password,
                                 final String passwordAgain,
                                 final Model model,
                                 final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);

        return this.userService.doPostPassword(loginUser,
                                               oldPassword,
                                               password,
                                               passwordAgain,
                                               model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/user/finish")
    public String doGetUserSettingFinish(final Model model) {
        return "console/user/finish";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/user/follow")
    public String doGetFollow(@RequestParam(required = false) final Integer page,
                              final Model model,
                              final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.userService.doGetConsoleFollow(loginUser, page, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/user/fans")
    public String doGetFans(@RequestParam(required = false) final Integer page,
                            final Model model,
                            final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.userService.doGetConsoleFans(loginUser, page, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/user/timeline")
    @ResponseBody
    public Map<String, Object> doGetTimeline(@RequestParam(required = false) final Integer page,
                                             final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.userService.doGetTimeline(loginUser, page);
    }
}
