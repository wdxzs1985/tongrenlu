package info.tongrenlu.www;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.exception.ForbiddenException;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.ConsoleMusicService;
import info.tongrenlu.service.FileService;
import info.tongrenlu.support.PaginateSupport;

import java.util.List;
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
                              @RequestParam(value = "tags[]", required = false) final String[] tags,
                              @RequestParam final MultipartFile cover,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Model model,
                              final Locale locale) {
        final MusicBean inputMusic = new MusicBean();
        inputMusic.setUserBean(loginUser);
        inputMusic.setTitle(title);
        inputMusic.setDescription(description);

        final boolean result = this.musicService.doCreate(inputMusic,
                                                          tags,
                                                          model.asMap(),
                                                          locale);
        if (result) {
            this.fileService.saveCover(inputMusic, cover);
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
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @RequestParam(value = "q", required = false) final String query,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        final PaginateSupport<MusicBean> page = this.musicService.searchMusicByUser(loginUser,
                                                                                    query,
                                                                                    pageNumber);
        model.addAttribute("page", page);
        return "console/music/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/music/{articleId}")
    public String doGetEdit(@PathVariable final Integer articleId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }
        if (!loginUser.equals(musicBean.getUserBean()) && !loginUser.isAdmin()) {
            throw new ForbiddenException();
        }

        final List<String> tags = this.musicService.getTags(musicBean);

        model.addAttribute("articleBean", musicBean);
        model.addAttribute("tags", tags);

        return "console/music/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/music/{articleId}")
    public String doPostEdit(@PathVariable final Integer articleId,
                             final String title,
                             final String description,
                             @RequestParam(value = "tags[]", required = false) final String[] tags,
                             @RequestParam final MultipartFile cover,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model,
                             final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }
        if (!loginUser.equals(musicBean.getUserBean()) && !loginUser.isAdmin()) {
            throw new ForbiddenException();
        }

        musicBean.setTitle(title);
        musicBean.setDescription(description);

        final boolean result = this.musicService.doEdit(musicBean,
                                                        tags,
                                                        model.asMap(),
                                                        locale);

        if (result) {
            this.fileService.saveCover(musicBean, cover);
            return "redirect:/console/music";
        }

        model.addAttribute("articleBean", musicBean);
        model.addAttribute("tags", tags);

        return "console/music/edit";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/music/{articleId}/delete")
    public String doGetDelete(@PathVariable final Integer articleId,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }
        if (!loginUser.equals(musicBean.getUserBean()) && !loginUser.isAdmin()) {
            throw new ForbiddenException();
        }

        this.musicService.doDelete(musicBean);

        return "redirect:/console/music";
    }
}
