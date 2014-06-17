package info.tongrenlu.www;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.HomeComicService;
import info.tongrenlu.support.ControllerSupport;
import info.tongrenlu.support.LoginUserSupport;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeComicController extends ControllerSupport {

    @Autowired
    private HomeComicService comicService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/comic")
    public String doGetIndex(@RequestParam(required = false) final Integer page,
                             @RequestParam(required = false) final String q,
                             final Model model,
                             final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        final String searchQuery = this.decodeQuery(q);
        return this.comicService.doGetIndex(loginUser, page, searchQuery, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comic/{articleId}")
    public String doGetView(@PathVariable final String articleId,
                            final Model model,
                            final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.comicService.doGetView(loginUser, articleId, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comic/{articleId}/playlist")
    @ResponseBody
    public List<Object> doGetPlaylist(@PathVariable final String articleId,
                                      final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.comicService.doGetPlaylist(loginUser, articleId, request);
    }

}
