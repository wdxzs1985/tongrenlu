package info.tongrenlu.interceptor;

import info.tongrenlu.support.IPSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {

    public static final String ACCESS_COUNT = "accessCount";

    public static final String LAST_ACCESS_INFO = "LAST_ACCESS_INFO";

    private final Log log = LogFactory.getLog(AccessInterceptor.class);

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        final String method = request.getMethod();
        final String uri = request.getRequestURI();
        final String realIP = IPSupport.getClientAddress(request);
        final String remoteIP = request.getRemoteAddr();
        final String sid = request.getRequestedSessionId();
        final String userAgent = request.getHeader("User-Agent");
        final String locale = request.getLocale().getDisplayName();

        final StringBuilder builder = new StringBuilder();
        builder.append("[REALIP]=").append(realIP);
        builder.append(", [REMOTEIP]=").append(remoteIP);
        builder.append(", [URI]=").append(uri);
        builder.append(", [METHOD]=").append(method);
        builder.append(", [SID]=").append(sid);
        builder.append(", [UA]=").append(userAgent);
        builder.append(", [LOCALE]=").append(locale);
        final String message = builder.toString();
        this.log.info(message);

        final HttpSession session = request.getSession();
        this.saveAccessInfo(session, message);
        this.increaseAccessCount(session);
        return true;
    }

    private void saveAccessInfo(final HttpSession session, final String message) {
        session.setAttribute(AccessInterceptor.LAST_ACCESS_INFO, message);
    }

    private void increaseAccessCount(final HttpSession session) {
        Integer accessCount = (Integer) session.getAttribute(AccessInterceptor.ACCESS_COUNT);
        if (accessCount == null) {
            accessCount = 1;
        } else {
            accessCount++;
        }
        session.setAttribute(AccessInterceptor.ACCESS_COUNT, accessCount);
    }
}
