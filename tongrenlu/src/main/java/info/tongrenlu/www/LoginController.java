package info.tongrenlu.www;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.LoginService;
import info.tongrenlu.support.LoginUserSupport;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
public class LoginController {

    @Autowired
    private LoginService loginService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/prelogin")
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
                                           final String remember,
                                           final Locale locale,
                                           final HttpServletRequest request,
                                           final HttpServletResponse response) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);

        final String salt = (String) request.getSession().getAttribute("salt");

        final UserBean userBean = new UserBean();
        userBean.setEmail(StringUtils.lowerCase(email));
        userBean.setPassword(password);
        userBean.setSalt(salt);
        userBean.setRemember(remember);

        this.loginService.doLogin(userBean, model, locale);

        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/signup")
    public String doGetRegister(final Model model) {
        return "login/register";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public String doPostRegister(@ModelAttribute final UserBean userBean,
                                 final Model model) {

        return this.loginService.doPostRegister(userBean, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/signup/finish")
    public String doGetRegisterFinish(final Model model,
                                      final HttpServletRequest request,
                                      final HttpServletResponse response) {
        return "login/register_finish";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/signout")
    @ResponseBody
    public void doGetLogout(final HttpServletRequest request,
                            final HttpServletResponse response) {
        LoginUserSupport.removeLoginUser(request);
        this.loginService.removeCookie(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/forgot")
    public String doGetForgot(final Model model,
                              final HttpServletRequest request,
                              final HttpServletResponse response) {
        return "login/forgot";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/forgot")
    public String doPostForgot(final String email,
                               final Model model,
                               final HttpServletRequest request) {
        return this.loginService.doPostForgot(email, model, request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/forgot/change")
    public String doGetChangePassword(final Model model,
                                      final HttpServletRequest request,
                                      final HttpServletResponse response) {
        return "login/forgot_change";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/forgot/change")
    public String doPostChangePassword(final String password,
                                       final Model model,
                                       final HttpServletRequest request,
                                       final HttpServletResponse response) {
        return this.loginService.doPostChangePassword(password, model, request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/forgot/finish")
    public String doGetForgotFinish(final Model model,
                                    final HttpServletRequest request,
                                    final HttpServletResponse response) {
        // send mail
        return "login/forgot_finish";
    }
}
