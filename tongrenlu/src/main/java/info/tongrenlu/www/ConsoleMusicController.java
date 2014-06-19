package info.tongrenlu.www;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.ConsoleMusicService;

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
@SessionAttributes("LOGIN_USER")
public class ConsoleMusicController {

    @Autowired
    private ConsoleMusicService musicService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/console/music")
    public String doGetIndex(@RequestParam(required = false) final Integer page,
                             @RequestParam(required = false) final String q,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        return this.musicService.doGetIndex(loginUser, page, q, model);
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
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Model model) {
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
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model) {
        return this.musicService.doGetEdit(loginUser, articleId, model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/music/{articleId}")
    public String doPostEdit(@PathVariable final String articleId,
                             @ModelAttribute final MusicBean musicBean,
                             @RequestParam(value = "tagId[]", required = false) final String[] tagIdArray,
                             @RequestParam(value = "tag[]", required = false) final String[] tagArray,
                             @RequestParam final MultipartFile cover,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
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
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        return this.musicService.doGetDelete(loginUser, articleId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/music/collect")
    public String doGetCollect(@RequestParam(required = false) final Integer page,
                               @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                               final Model model) {
        return this.musicService.doGetCollect(loginUser, page, model);
    }
}
