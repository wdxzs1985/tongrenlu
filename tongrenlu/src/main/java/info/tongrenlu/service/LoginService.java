package info.tongrenlu.service;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.persistence.MUserMapper;
import info.tongrenlu.service.dao.FileDao;
import info.tongrenlu.service.validator.UserBeanValidator;
import info.tongrenlu.support.IPSupport;
import info.tongrenlu.support.LoginUserSupport;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.util.CookieGenerator;

@Service
@Transactional
public class LoginService {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private UserBeanValidator validator = null;
    @Autowired
    private MUserMapper userMapper = null;
    @Autowired
    private FileDao fileDao = null;
    @Autowired
    private CookieGenerator autoLoginCookieGenerator = null;

    public Map<String, Object> doLogin(final UserBean userBean,
                                       final Map<String, Object> model,
                                       final Locale locale) {
        if (this.validateUserLogin(userBean, model, locale)) {
            final UserBean loginUser = this.userDao.doUserLogin(userBean, model);
            if (loginUser != null) {
                final boolean isAutoLogin = StringUtils.equals(remember, "1");
                this.doLoginSuccess(loginUser, isAutoLogin, request, response);
                model.put("userId", loginUser.getUserId());
                model.put("nickname", loginUser.getNickname());
                model.put("result", true);
            }
        }
        return model;
    }

    private boolean validateUserLogin(final UserBean user,
                                      final Map<String, Object> model,
                                      final Locale locale) {
        boolean isValid = true;
        if (!this.validator.validateEmail(user.getEmail(),
                                          "email_error",
                                          model,
                                          locale)) {
            isValid = false;
        }
        if (!this.validator.validatePassword(user.getPassword(), model)) {
            isValid = false;
        }
        return isValid;
    }

    public void doLoginSuccess(final UserBean userBean,
                               final boolean isAutoLogin,
                               final HttpServletRequest request,
                               final HttpServletResponse response) {
        final String userId = userBean.getUserId();
        final String loginIp = IPSupport.getClientAddress(request);
        final String loginUa = StringUtils.left(request.getHeader("User-Agent"),
                                                250);
        final String token = this.userDao.updateUserLogin(userId,
                                                          loginIp,
                                                          loginUa);
        // write cookie
        if (isAutoLogin) {
            this.autoLoginCookieGenerator.addCookie(response, token);
        }
        final UserBean loginUser = this.userDao.getUserInfo(userId);
        LoginUserSupport.setLoginUser(loginUser, request);
    }

    public String doPostRegister(final UserBean userBean, final Model model) {
        userBean.setEmail(StringUtils.lowerCase(userBean.getEmail()));
        //
        if (this.userDao.validateUserRegister(userBean, model.asMap())) {
            this.userDao.doUserRegister(userBean);
            this.fileDao.saveAvatarFile(userBean, null);
            return "redirect:/register/finish";
        }
        return "login/register";
    }

    public UserBean doAutoLogin(final String token,
                                final HttpServletRequest request) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        if (!Base64.isBase64(token)) {
            return null;
        }
        final UserBean userBean = this.userDao.decodeCookieToken(token);
        if (userBean == null) {
            return null;
        }
        final String userId = userBean.getUserId();
        final UserBean userInDb = this.userDao.getUserById(userId);
        if (userInDb == null) {
            return null;
        }
        // request.getRemoteAddr();
        // final String loginUa =
        // StringUtils.left(request.getHeader("User-Agent"),
        // 250);
        // if (!StringUtils.equals(userBean.getLastLoginIp(), loginIp)) {
        // return null;
        // }
        // if (!StringUtils.equals(userBean.getLastLoginUa(), loginUa)) {
        // return null;
        // }
        if (System.currentTimeMillis() - userInDb.getLastLoginTime().getTime() > CommonConstants.MONTH) {
            return null;
        }
        return userInDb;
    }

    public String doPostForgot(final String email,
                               final Model model,
                               final HttpServletRequest request) {
        if (this.userDao.validateForgotPassword(email, model.asMap())) {
            final UserBean userBean = this.userDao.getUserByEmail(email);
            request.getSession().setAttribute("forgot_user", userBean);
            return "redirect:/forgot/change";
        }
        return "login/forgot";
    }

    public String doPostChangePassword(final String password,
                                       final Model model,
                                       final HttpServletRequest request) {
        if (StringUtils.isBlank(password)) {
            model.addAttribute("password_error",
                               this.messageSource.getMessage("UserBean.password[Blank]",
                                                             null,
                                                             null));
            return "login/forgot_change";
        }

        final UserBean userBean = (UserBean) request.getSession()
                                                    .getAttribute("forgot_user");
        if (userBean == null) {
            return "login/forgot";
        }
        this.userDao.updateUserPassword(userBean, password);
        return "redirect:/forgot/finish";
    }

    public void removeCookie(final HttpServletResponse response) {
        this.autoLoginCookieGenerator.removeCookie(response);
    }

}
