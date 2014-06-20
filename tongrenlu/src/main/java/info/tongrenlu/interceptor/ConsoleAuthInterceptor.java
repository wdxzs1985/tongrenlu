package info.tongrenlu.interceptor;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

public class ConsoleAuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        final HttpSession session = request.getSession();
        final UserBean loginUser = (UserBean) session.getAttribute(CommonConstants.LOGIN_USER);
        if (loginUser != null) {
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/");
        return false;
    }
}
