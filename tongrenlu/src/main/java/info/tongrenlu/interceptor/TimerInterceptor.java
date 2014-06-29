package info.tongrenlu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TimerInterceptor implements HandlerInterceptor {

    protected Log log = LogFactory.getLog(TimerInterceptor.class);

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object controller) throws Exception {
        this.log.debug(String.format("PreHandle %s.", controller));
        final long starttime = System.currentTimeMillis();
        request.setAttribute("TimerInterceptor.starttime", starttime);
        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final Object controller,
                           final ModelAndView modelAndView) throws Exception {
        final long endtime = System.currentTimeMillis();
        final long starttime = (Long) request.getAttribute("TimerInterceptor.starttime");
        this.log.debug(String.format("PostHandle %s. Cost: %d ms.",
                                     controller,
                                     endtime - starttime));
    }

    @Override
    public void afterCompletion(final HttpServletRequest request,
                                final HttpServletResponse response,
                                final Object controller,
                                final Exception exception) throws Exception {
        final long endtime = System.currentTimeMillis();
        final long starttime = (Long) request.getAttribute("TimerInterceptor.starttime");
        this.log.debug(String.format("AfterCompletion %s. Cost: %d ms.",
                                     controller,
                                     endtime - starttime));
    }

}
