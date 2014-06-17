package info.tongrenlu.www;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.LoginService;
import info.tongrenlu.support.ControllerSupport;
import info.tongrenlu.support.LoginUserSupport;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController extends ControllerSupport {

    @Autowired
    private LoginService loginService = null;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    @ResponseBody
    public Map<String, Object> doPostLogin(@ModelAttribute final UserBean userBean,
                                           final String remember,
                                           final HttpServletRequest request,
                                           final HttpServletResponse response) {
        return this.loginService.doPostLogin(userBean,
                                             remember,
                                             request,
                                             response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public String doGetRegister(final Model model) {
        return "login/register";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public String doPostRegister(@ModelAttribute final UserBean userBean,
                                 final Model model) {

        return this.loginService.doPostRegister(userBean, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register/finish")
    public String doGetRegisterFinish(final Model model,
                                      final HttpServletRequest request,
                                      final HttpServletResponse response) {
        return "login/register_finish";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
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
