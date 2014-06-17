package info.tongrenlu.www;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.TagService;
import info.tongrenlu.support.ControllerSupport;
import info.tongrenlu.support.LoginUserSupport;

import java.util.List;
import java.util.Map;

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
public class HomeTagController extends ControllerSupport {

    @Autowired
    private TagService tagService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/tag/search")
    @ResponseBody
    public List<String> doGetSearchTag(final String q) {
        final String searchQuery = this.decodeQuery(q);
        return this.tagService.doGetSearchTag(searchQuery);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/tag/input")
    @ResponseBody
    public Map<String, Object> doPostInputTag(final String tag) {
        return this.tagService.doPostInputTag(tag);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tag/{tagId}")
    public String doGetView(@PathVariable final String tagId,
                            final Model model,
                            final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.tagService.doGetView(loginUser, tagId, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tag/{tagId}/comic")
    public String doGetComic(@PathVariable final String tagId,
                             @RequestParam(required = false) final Integer page,
                             final Model model,
                             final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.tagService.doGetTagComic(loginUser, tagId, page, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tag/{tagId}/music")
    public String doGetMusic(@PathVariable final String tagId,
                             @RequestParam(required = false) final Integer page,
                             final Model model,
                             final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.tagService.doGetTagMusic(loginUser, tagId, page, model);
    }

}
