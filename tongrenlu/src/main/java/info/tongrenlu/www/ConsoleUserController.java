package info.tongrenlu.www;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.domain.UserProfileBean;
import info.tongrenlu.service.UserProfileService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SessionAttributes("LOGIN_USER")
public class ConsoleUserController {

    @Autowired
    private UserProfileService userService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/console")
    public String doGetIndex(@ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        final UserProfileBean userProfileBean = this.userService.getUserProfile(loginUser);
        model.addAttribute("userProfileBean", userProfileBean);
        return "console/profile/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/setting")
    public String doGetUserSetting(@ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                   final Model model) {
        final UserBean inputUser = this.userService.getUserById(loginUser.getId());
        model.addAttribute("userBean", loginUser);

        final UserProfileBean userProfileBean = this.userService.getUserProfile(inputUser);
        model.addAttribute("userProfileBean", userProfileBean);
        return "console/profile/setting";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/setting")
    public String doPostUserSetting(final String nickname,
                                    final String signature,
                                    final String includeRedFlg,
                                    final String onlyTranslateFlg,
                                    final String onlyVocalFlg,
                                    @RequestParam final MultipartFile avatar,
                                    @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                    final Model model) {
        final UserBean inputUser = this.userService.getUserById(loginUser.getId());
        inputUser.setIncludeRedFlg(includeRedFlg);
        inputUser.setOnlyTranslateFlg(onlyTranslateFlg);
        inputUser.setOnlyVocalFlg(onlyVocalFlg);
        model.addAttribute("userBean", loginUser);

        final UserProfileBean userProfileBean = this.userService.getUserProfile(inputUser);
        userProfileBean.setNickname(nickname);
        userProfileBean.setSignature(signature);
        model.addAttribute("userProfileBean", userProfileBean);

        return "console/profile/setting";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/password")
    public String doGetPassword(final Model model) {
        return "console/password";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/password")
    public String doPostPassword(final String password,
                                 final String password2,
                                 @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                 final Model model) {
        return "console/password";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/follow")
    public String doGetFollow(@RequestParam(required = false) final Integer page,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Model model) {
        return this.userService.doGetConsoleFollow(loginUser, page, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/fans")
    public String doGetFans(@RequestParam(required = false) final Integer page,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model) {
        return this.userService.doGetConsoleFans(loginUser, page, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/timeline")
    @ResponseBody
    public Map<String, Object> doGetTimeline(@RequestParam(required = false) final Integer page,
                                             @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        return this.userService.doGetTimeline(loginUser, page);
    }
}
