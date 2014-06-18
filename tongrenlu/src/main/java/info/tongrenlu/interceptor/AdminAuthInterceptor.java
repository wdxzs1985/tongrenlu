package info.tongrenlu.interceptor;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.support.LoginUserSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminAuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        final UserBean user = LoginUserSupport.getLoginUser(request);
        if (user != null && user.isAdmin()) {
            return true;
        }
        response.sendError(403);
        return false;
    }
}
