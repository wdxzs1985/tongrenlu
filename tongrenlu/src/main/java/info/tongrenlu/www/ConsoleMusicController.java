package info.tongrenlu.www;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.ConsoleMusicService;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ConsoleMusicController extends ControllerSupport {

    @Autowired
    private ConsoleMusicService musicService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/console/music")
    public String doGetIndex(@RequestParam(required = false) final Integer page,
                             @RequestParam(required = false) final String q,
                             final Model model,
                             final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        final String searchQuery = this.decodeQuery(q);
        return this.musicService.doGetIndex(loginUser, page, searchQuery, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/music/input")
    public String doGetInput(final Model model) {
        return "console/music/input";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/music/input")
    public String doPostInput(@ModelAttribute final MusicBean musicBean,
                              @RequestParam final MultipartFile cover,
                              @RequestParam(value = "tagId[]", required = false) final String[] tagIdArray,
                              @RequestParam(value = "tag[]", required = false) final String[] tagArray,
                              final Model model,
                              final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.musicService.doPostInput(loginUser,
                                             musicBean,
                                             cover,
                                             tagIdArray,
                                             tagArray,
                                             model.asMap());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/music/finish")
    public String doGetFinish(final Model model) {
        return "console/music/finish";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/music/{articleId}")
    public String doGetEdit(@PathVariable final String articleId,
                            final Model model,
                            final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.musicService.doGetEdit(loginUser, articleId, model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/music/{articleId}")
    public String doPostEdit(@PathVariable final String articleId,
                             @ModelAttribute final MusicBean musicBean,
                             @RequestParam(value = "tagId[]", required = false) final String[] tagIdArray,
                             @RequestParam(value = "tag[]", required = false) final String[] tagArray,
                             @RequestParam final MultipartFile cover,
                             final Model model,
                             final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.musicService.doPostEdit(loginUser,
                                            articleId,
                                            musicBean,
                                            cover,
                                            tagIdArray,
                                            tagArray,
                                            model.asMap());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/music/{articleId}/delete")
    public String doGetDelete(@PathVariable final String articleId,
                              final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);

        return this.musicService.doGetDelete(loginUser, articleId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/music/collect")
    public String doGetCollect(@RequestParam(required = false) final Integer page,
                               final Model model,
                               final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);

        return this.musicService.doGetCollect(loginUser, page, model);
    }
}
