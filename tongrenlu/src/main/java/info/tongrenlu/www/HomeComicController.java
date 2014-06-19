package info.tongrenlu.www;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.HomeComicService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
public class HomeComicController {

    @Autowired
    private HomeComicService comicService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/comic")
    public String doGetIndex(@RequestParam(required = false) final Integer page,
                             @RequestParam(required = false) final String q,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        return this.comicService.doGetIndex(loginUser, page, q, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comic/{articleId}")
    public String doGetView(@PathVariable final String articleId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model) {
        return this.comicService.doGetView(loginUser, articleId, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comic/{articleId}/playlist")
    @ResponseBody
    public List<Object> doGetPlaylist(@PathVariable final String articleId,
                                      @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        return this.comicService.doGetPlaylist(loginUser, articleId, null);
    }

}
