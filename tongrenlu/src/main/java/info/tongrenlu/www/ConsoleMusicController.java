package info.tongrenlu.www;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.ConsoleMusicService;
import info.tongrenlu.service.FileService;

import java.util.Locale;

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
    @Autowired
    private FileService fileService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/console/music/input")
    public String doGetInput(final Model model) {
        final MusicBean inputMusic = new MusicBean();
        final String[] tags = {};
        model.addAttribute("articleBean", inputMusic);
        model.addAttribute("tags", tags);
        return "console/music/input";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/music/input")
    public String doPostInput(final String title,
                              final String description,
                              final MultipartFile cover,
                              @RequestParam(value = "tags[]", required = false) final String[] tags,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Model model,
                              final Locale locale) {
        final MusicBean inputMusic = new MusicBean();
        inputMusic.setUserBean(loginUser);
        inputMusic.setTitle(title);
        inputMusic.setDescription(description);

        final MusicBean musicBean = this.musicService.doCreate(inputMusic,
                                                               cover,
                                                               tags,
                                                               model.asMap(),
                                                               locale);
        if (musicBean != null) {
            this.fileService.saveCoverFile(musicBean, cover);
            return "redirect:/console/music/finish";
        }

        model.addAttribute("articleBean", inputMusic);
        model.addAttribute("tags", tags);
        return "console/music/input";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/music/finish")
    public String doGetFinish(final Model model) {
        return "console/music/input_finish";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/music")
    public String doGetIndex(@RequestParam(required = false) final Integer page,
                             @RequestParam(required = false) final String q,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        return this.musicService.doGetIndex(loginUser, page, q, model);
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
