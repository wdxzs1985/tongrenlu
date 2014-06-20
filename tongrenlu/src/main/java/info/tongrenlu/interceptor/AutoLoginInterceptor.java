package info.tongrenlu.interceptor;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.CookieGenerator;

public class AutoLoginInterceptor extends HandlerInterceptorAdapter {

    private Log log = LogFactory.getLog(AutoLoginInterceptor.class);
    @Autowired
    private UserService loginService = null;
    @Autowired
    private CookieGenerator autoLoginCookie = null;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        final HttpSession session = request.getSession();
        UserBean loginUser = (UserBean) session.getAttribute(CommonConstants.LOGIN_USER);
        if (loginUser == null) {
            final Cookie[] cookies = request.getCookies();
            if (ArrayUtils.isNotEmpty(cookies)) {
                for (final Cookie cookie : request.getCookies()) {
                    if (CommonConstants.FINGERPRINT.equals(cookie.getName())) {
                        this.log.info("auto login...");
                        String fingerprint = cookie.getValue();
                        loginUser = this.loginService.doAutoLogin(fingerprint);
                        if (loginUser != null) {
                            session.setAttribute(CommonConstants.LOGIN_USER,
                                                 loginUser);
                            fingerprint = loginUser.getFingerprint();
                            this.autoLoginCookie.addCookie(response,
                                                           fingerprint);
                            this.log.info("auto login ok.");
                        } else {
                            this.autoLoginCookie.removeCookie(response);
                        }
                        break;
                    }
                }
            }
        }
        return true;
    }
}
