package info.tongrenlu.interceptor;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminAuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        final HttpSession session = request.getSession();
        final UserBean loginUser = (UserBean) session.getAttribute(CommonConstants.LOGIN_USER);
        if (loginUser != null && loginUser.isAdmin()) {
            return true;
        }
        response.sendError(403);
        return false;
    }
}
