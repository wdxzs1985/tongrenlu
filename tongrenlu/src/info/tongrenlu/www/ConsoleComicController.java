package info.tongrenlu.www;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.ConsoleComicService;
import info.tongrenlu.support.ControllerSupport;
import info.tongrenlu.support.LoginUserSupport;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SessionAttributes("login_user")
public class ConsoleComicController extends ControllerSupport {

    @Autowired
    private ConsoleComicService comicService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/console/comic")
    public String doGetIndex(@RequestParam(required = false) final Integer page,
                             @RequestParam(required = false) final String q,
                             final Model model,
                             final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        final String searchQuery = this.decodeQuery(q);
        return this.comicService.doGetIndex(loginUser, page, searchQuery, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/comic/input")
    public String doGetInput(final Model model) {
        return "console/comic/input";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/comic/input")
    public String doPostInput(@ModelAttribute final ComicBean comicBean,
                              @RequestParam final MultipartFile cover,
                              @RequestParam(value = "tagId[]", required = false) final String[] tagIdArray,
                              @RequestParam(value = "tag[]", required = false) final String[] tagArray,
                              final Model model,
                              final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.comicService.doPostInput(loginUser,
                                             comicBean,
                                             cover,
                                             tagIdArray,
                                             tagArray,
                                             model.asMap());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/comic/finish")
    public String doGetFinish(final Model model) {
        return "console/comic/finish";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/comic/{articleId}")
    public String doGetEdit(@PathVariable final String articleId,
                            final Model model,
                            final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.comicService.doGetEdit(loginUser, articleId, model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/comic/{articleId}")
    public String doPostEdit(@PathVariable final String articleId,
                             @ModelAttribute final ComicBean comicBean,
                             @RequestParam(value = "tagId[]", required = false) final String[] tagIdArray,
                             @RequestParam(value = "tag[]", required = false) final String[] tagArray,
                             @RequestParam final MultipartFile cover,
                             final Model model,
                             final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);

        return this.comicService.doPostEdit(loginUser,
                                            articleId,
                                            comicBean,
                                            cover,
                                            tagIdArray,
                                            tagArray,
                                            model.asMap());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/comic/{articleId}/delete")
    public String doGetDelete(@PathVariable final String articleId,
                              final Model model,
                              final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);

        return this.comicService.doGetDelete(loginUser, articleId, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/comic/collect")
    public String doGetCollect(@RequestParam(required = false) final Integer page,
                               final Model model,
                               final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);

        return this.comicService.doGetCollect(loginUser, page, model);
    }
}
