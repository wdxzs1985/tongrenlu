package info.tongrenlu.www;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.domain.UserProfileBean;
import info.tongrenlu.service.ConsoleUserService;
import info.tongrenlu.service.FileService;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.CookieGenerator;

@Controller
@SessionAttributes("LOGIN_USER")
public class ConsoleUserController {

    @Autowired
    private ConsoleUserService userService = null;
    @Autowired
    private FileService fileService = null;
    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private CookieGenerator autoLoginCookie = null;

    @RequestMapping(method = RequestMethod.GET, value = "/console")
    public String doGetIndex(@ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        final Integer id = loginUser.getId();
        final UserProfileBean userProfileBean = this.userService.getProfileById(id);
        model.addAttribute("userProfileBean", userProfileBean);
        return "console/profile/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/setting")
    public String doGetUserSetting(@ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                   final Model model) {
        model.addAttribute("userBean", loginUser);
        return "console/profile/setting";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/setting")
    public String doPostUserSetting(final String nickname,
                                    final String signature,
                                    @RequestParam(defaultValue = CommonConstants.CHR_FALSE) final String includeRedFlg,
                                    @RequestParam(defaultValue = CommonConstants.CHR_FALSE) final String onlyTranslateFlg,
                                    @RequestParam(defaultValue = CommonConstants.CHR_FALSE) final String onlyVocalFlg,
                                    @RequestParam final MultipartFile avatar,
                                    @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                    final Model model,
                                    final RedirectAttributes redirectAttributes,
                                    final Locale locale) {
        final UserBean inputUser = new UserBean();
        inputUser.setId(loginUser.getId());
        inputUser.setNickname(nickname);
        inputUser.setSignature(signature);
        inputUser.setIncludeRedFlg(includeRedFlg);
        inputUser.setOnlyTranslateFlg(onlyTranslateFlg);
        inputUser.setOnlyVocalFlg(onlyVocalFlg);

        if (this.userService.saveSetting(inputUser, model.asMap(), locale)) {
            loginUser.setNickname(nickname);
            loginUser.setSignature(signature);
            loginUser.setIncludeRedFlg(includeRedFlg);
            loginUser.setOnlyTranslateFlg(onlyTranslateFlg);
            loginUser.setOnlyVocalFlg(onlyVocalFlg);

            this.fileService.saveAvatarFile(loginUser, avatar);

            final String message = this.messageSource.getMessage("console.profile.setting.finish",
                                                                 null,
                                                                 locale);
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/console/setting";
        }
        model.addAttribute("userBean", inputUser);
        return "console/profile/setting";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/password")
    public String doGetPassword(final Model model) {
        return "console/profile/password";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/password")
    public String doPostPassword(final String password,
                                 final String password2,
                                 @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                 final Model model,
                                 final RedirectAttributes redirectAttributes,
                                 final Locale locale,
                                 final HttpServletResponse response) {
        final UserBean inputUser = new UserBean();
        inputUser.setId(loginUser.getId());
        inputUser.setPassword(password);
        inputUser.setPassword2(password2);
        if (this.userService.changePassword(inputUser, model.asMap(), locale)) {

            final String message = this.messageSource.getMessage("console.profile.password.finish",
                                                                 null,
                                                                 locale);
            redirectAttributes.addFlashAttribute("message", message);
            this.autoLoginCookie.removeCookie(response);
            return "redirect:/console/password";
        }
        return "console/profile/password";
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
