package info.tongrenlu.www;

import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.exception.ForbiddenException;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.ConsoleMusicService;
import info.tongrenlu.service.FileService;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
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
            return "redirect:/console/music/" + inputMusic.getId();
        }

        model.addAttribute("articleBean", inputMusic);
        model.addAttribute("tags", tags);
        return "console/music/input";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/music")
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @RequestParam(value = "q", required = false) final String query,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("query", query);
        page.addParam("userBean", loginUser);
        this.musicService.searchMusic(page);
        model.addAttribute("page", page);
        return "console/music/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/music/{articleId}")
    public String doGetView(@PathVariable final Integer articleId,
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

        return "console/music/view";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/music/{articleId}/edit")
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

    @RequestMapping(method = RequestMethod.POST, value = "/console/music/{articleId}/edit")
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
            return "redirect:/console/music/" + musicBean.getId();
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

    @RequestMapping(method = RequestMethod.GET, value = "/console/music/{articleId}/track/upload")
    public String doGetTrackUpload(@PathVariable final Integer articleId,
                                   @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                   final Model model) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }
        if (!loginUser.equals(musicBean.getUserBean()) && !loginUser.isAdmin()) {
            throw new ForbiddenException();
        }

        model.addAttribute("articleBean", musicBean);

        return "console/music/track_upload";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/music/{articleId}/track/file")
    @ResponseBody
    public Map<String, Object> doGetTrackFile(@PathVariable final Integer articleId,
                                              @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }
        if (!loginUser.equals(musicBean.getUserBean()) && !loginUser.isAdmin()) {
            throw new ForbiddenException();
        }

        final Map<String, Object> model = new HashMap<String, Object>();
        final List<FileBean> files = this.musicService.getTrackFiles(musicBean);
        model.put("files", files);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/music/{articleId}/track/file")
    @ResponseBody
    public Map<String, Object> doPostTrackFile(@PathVariable final Integer articleId,
                                               @RequestParam(value = "files[]") final MultipartFile[] uploads,
                                               @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }
        if (!loginUser.equals(musicBean.getUserBean()) && !loginUser.isAdmin()) {
            throw new ForbiddenException();
        }

        final Map<String, Object> model = new HashMap<String, Object>();
        final List<FileBean> files = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(uploads)) {
            for (final MultipartFile upload : uploads) {
                final FileBean fileBean = this.musicService.addTrackFile(articleId,
                                                                         upload);
                files.add(fileBean);
            }
        }

        model.put("files", files);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/music/{articleId}/{fileId}/delete")
    @ResponseBody
    public Map<String, Object> doPostTrackDelete(@PathVariable final Integer articleId,
                                                 @PathVariable final Integer fileId,
                                                 @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }
        if (!loginUser.equals(musicBean.getUserBean()) && !loginUser.isAdmin()) {
            throw new ForbiddenException();
        }

        final Map<String, Object> model = new HashMap<String, Object>();
        final FileBean fileBean = new FileBean();
        fileBean.setId(fileId);
        fileBean.setArticleId(articleId);
        this.musicService.removeTrackFile(fileBean);

        model.put("result", true);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/music/{articleId}/track/sort")
    public String doGetTrackSort(@PathVariable final Integer articleId,
                                 @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                 final Model model) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }
        if (!loginUser.equals(musicBean.getUserBean()) && !loginUser.isAdmin()) {
            throw new ForbiddenException();
        }

        model.addAttribute("articleBean", musicBean);

        return "console/music/track_sort";
    }
}
