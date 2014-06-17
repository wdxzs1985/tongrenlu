package info.tongrenlu.special;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.Comiket84Service;
import info.tongrenlu.support.ControllerSupport;
import info.tongrenlu.support.LoginUserSupport;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/c84")
public class Comiket84Controller extends ControllerSupport {

    @Autowired
    private Comiket84Service service = null;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String doGetIndex(final Model model) {
        return this.service.doGetIndex(model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/circle/{area}")
    @ResponseBody
    public Map<String, Object> doGetCircle(@PathVariable final String area,
                                           final Model model,
                                           final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        final String searchArea = this.decodeQuery(area);
        return this.service.doGetCircle(loginUser, searchArea);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/like")
    @ResponseBody
    public Map<String, Object> doGetLike(final Model model,
                                         final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.service.doGetLike(loginUser);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/like/{circleId}")
    @ResponseBody
    public Map<String, Object> doPostLike(@PathVariable final String circleId,
                                          final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.service.doPostLike(loginUser, circleId);
    }
}
