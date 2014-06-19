package info.tongrenlu.www;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.LoginService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.util.CookieGenerator;

@Controller
@SessionAttributes("LOGIN_USER")
public class LoginController {

    public static final String FORGOT_USER = "FORGOT_USER";

    @Autowired
    private LoginService loginService = null;
    @Autowired
    private CookieGenerator autoLoginCookie = null;

    @RequestMapping(method = RequestMethod.GET, value = "/salt")
    @ResponseBody
    public Map<String, Object> doGetPreLogin(final HttpServletRequest request) {
        final Map<String, Object> model = new HashMap<String, Object>();
        final String salt = RandomStringUtils.randomAlphanumeric(4);
        model.put("salt", salt);
        request.getSession().setAttribute("salt", salt);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signin")
    @ResponseBody
    public Map<String, Object> doPostLogin(final String email,
                                           final String password,
                                           final String autoLogin,
                                           final Locale locale,
                                           final HttpServletRequest request,
                                           final HttpServletResponse response) {
        final HttpSession session = request.getSession();

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);

        final UserBean userBean = new UserBean();
        userBean.setEmail(StringUtils.lowerCase(email));
        userBean.setPassword(password);
        userBean.setSalt((String) session.getAttribute("salt"));

        final UserBean loginUser = this.loginService.doSignIn(userBean,
                                                              model,
                                                              locale);
        if (loginUser != null) {
            session.setAttribute(CommonConstants.LOGIN_USER, loginUser);
            if (CommonConstants.is(autoLogin)) {
                this.autoLoginCookie.addCookie(response,
                                               loginUser.getFingerprint());
            } else {
                this.autoLoginCookie.removeCookie(response);
            }
            model.put("loginUser", loginUser);
            model.put("result", true);
        }

        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/signup")
    public String doGetSignup(final Model model) {
        final UserBean userBean = new UserBean();
        model.addAttribute("userBean", userBean);
        return "login/signup";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public String doPostSignup(final String nickname,
                               final String email,
                               final String password,
                               final String password2,
                               final Model model,
                               final Locale locale) {
        final UserBean userBean = new UserBean();
        userBean.setNickname(nickname);
        userBean.setEmail(StringUtils.lowerCase(email));
        userBean.setPassword(password);
        userBean.setPassword2(password2);

        final UserBean newUser = this.loginService.doSignup(userBean,
                                                            model.asMap(),
                                                            locale);
        if (newUser != null) {
            // send mail
            return "redirect:/signup/finish";
        }

        model.addAttribute("userBean", userBean);
        return "login/signup";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/signup/finish")
    public String doGetSignupFinish(final Model model) {
        return "login/signup_finish";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/signout")
    public String doGetSignout(final SessionStatus sessionStatus,
                               final HttpServletResponse response) {
        sessionStatus.setComplete();
        this.autoLoginCookie.removeCookie(response);
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/forgot")
    public String doGetForgot(final Model model) {
        final UserBean userBean = new UserBean();
        model.addAttribute("userBean", userBean);
        return "login/forgot";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/forgot")
    public String doPostForgot(final String email,
                               final String nickname,
                               final Model model,
                               final Locale locale,
                               final HttpServletRequest request) {
        final UserBean inputUser = new UserBean();
        inputUser.setEmail(StringUtils.lowerCase(email));
        inputUser.setNickname(nickname);
        final UserBean userBean = this.loginService.doFindForgotUser(inputUser,
                                                                     model.asMap(),
                                                                     locale);
        if (userBean != null) {
            request.getSession().setAttribute(LoginController.FORGOT_USER,
                                              userBean);
            return "login/forgot_change";
        } else {
            model.addAttribute("userBean", inputUser);
            return "login/forgot";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/forgot/change")
    public String doPostChangePassword(final String password,
                                       final String password2,
                                       final Model model,
                                       final Locale locale,
                                       final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final UserBean userBean = (UserBean) session.getAttribute(LoginController.FORGOT_USER);
        if (userBean == null) {
            session.removeAttribute(LoginController.FORGOT_USER);
            return "redirect:/forgot";
        }

        userBean.setPassword(password);
        userBean.setPassword(password2);

        if (this.loginService.doChangePassword(userBean, model.asMap(), locale)) {
            session.removeAttribute(LoginController.FORGOT_USER);
            return "redirect:/forgot/finish";
        }
        return "login/forgot_change";

    }

    @RequestMapping(method = RequestMethod.GET, value = "/forgot/finish")
    public String doGetForgotFinish(final Model model) {
        // send mail
        return "login/forgot_finish";
    }
}
