package info.tongrenlu.interceptor;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.support.LoginUserSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ConsoleAuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        final UserBean user = LoginUserSupport.getLoginUser(request);
        if (user != null) {
            return true;
        }

        if (request.getMethod().equals("GET")) {
            final String disp = "/login";
            final RequestDispatcher dispatch = request.getRequestDispatcher(disp);
            dispatch.forward(request, response);
        } else {
            final String forward = request.getContextPath() + "/login";
            request.getRequestDispatcher(forward).forward(request, response);
            // response.sendRedirect(request.getContextPath() + "/login");
        }
        return false;
    }
}
