package info.tongrenlu.interceptor;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.support.LoginUserSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class AdminAuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        final UserBean user = LoginUserSupport.getLoginUser(request);
        if (user != null) {
            if (StringUtils.equals("1", user.getAdminFlg())) {
                return true;
            }
        }
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }
}
