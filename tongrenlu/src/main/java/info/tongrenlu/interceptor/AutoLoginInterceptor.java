package info.tongrenlu.interceptor;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.LoginService;
import info.tongrenlu.support.LoginUserSupport;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AutoLoginInterceptor extends HandlerInterceptorAdapter {

    private Log log = LogFactory.getLog(AutoLoginInterceptor.class);
    @Autowired
    private LoginService loginService = null;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        UserBean loginUser = LoginUserSupport.getLoginUser(request);
        if (loginUser != null) {
            return true;
        }
        final Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (final Cookie cookie : request.getCookies()) {
                if (StringUtils.equals(CommonConstants.AUTO_LOGIN,
                                       cookie.getName())) {
                    this.log.debug("auto login...");
                    final String token = cookie.getValue();
                    loginUser = this.loginService.doAutoLogin(token, request);
                    if (loginUser != null) {
                        this.loginService.doLoginSuccess(loginUser,
                                                         true,
                                                         request,
                                                         response);
                        this.log.debug("auto login ok.");
                    } else {
                        this.loginService.removeCookie(response);
                    }
                    break;
                }
            }
        }
        return true;
    }
}
