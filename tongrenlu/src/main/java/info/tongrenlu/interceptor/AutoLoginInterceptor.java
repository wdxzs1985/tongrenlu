package info.tongrenlu.interceptor;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.CookieGenerator;

@Component
public class AutoLoginInterceptor extends HandlerInterceptorAdapter {

    private final Log log = LogFactory.getLog(AutoLoginInterceptor.class);
    @Autowired
    private final LoginService loginService = null;
    @Autowired
    private final CookieGenerator autoLoginCookie = null;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        final HttpSession session = request.getSession();
        final String userAgent = request.getHeader("User-Agent");
        UserBean loginUser = (UserBean) session.getAttribute(CommonConstants.LOGIN_USER);

        if (loginUser == null) {
            synchronized (this) {
                final Cookie[] cookies = request.getCookies();
                if (ArrayUtils.isNotEmpty(cookies)) {
                    for (final Cookie cookie : request.getCookies()) {
                        if (CommonConstants.FINGERPRINT.equals(cookie.getName())) {
                            this.log.info("auto login...");
                            String fingerprint = cookie.getValue();
                            if (StringUtils.isNotBlank(fingerprint)) {
                                loginUser = this.loginService.doAutoLogin(fingerprint, userAgent);
                                if (loginUser != null) {
                                    session.setAttribute(CommonConstants.LOGIN_USER, loginUser);
                                    fingerprint = loginUser.getFingerprint();
                                    this.autoLoginCookie.addCookie(response, fingerprint);
                                    this.log.info("auto login ok.");
                                    return true;
                                }
                            }
                            this.autoLoginCookie.removeCookie(response);
                        }
                    }
                }
            }
        }
        return true;
    }
}
