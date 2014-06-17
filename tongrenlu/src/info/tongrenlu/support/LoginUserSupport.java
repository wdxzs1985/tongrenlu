package info.tongrenlu.support;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

public class LoginUserSupport {

    public static UserBean getLoginUser(final HttpServletRequest request) {
        return (UserBean) request.getSession()
                                 .getAttribute(CommonConstants.LOGIN_USER);
    }

    public static void setLoginUser(final UserBean userBean,
                                    final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final String loginIp = IPSupport.getClientAddress(request);
        final String loginUa = StringUtils.left(request.getHeader("User-Agent"),
                                                250);
        userBean.setLastLoginIp(loginIp);
        userBean.setLastLoginUa(loginUa);
        session.setAttribute(CommonConstants.LOGIN_USER, userBean);
    }

    public static void removeLoginUser(final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        session.removeAttribute(CommonConstants.LOGIN_USER);
        session.invalidate();
    }

}
