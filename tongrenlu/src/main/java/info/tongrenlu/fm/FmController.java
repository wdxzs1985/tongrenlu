package info.tongrenlu.fm;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.FmService;
import info.tongrenlu.service.LoginService;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class FmController {

    @Autowired
    private FmService fmService = null;
    @Autowired
    private LoginService loginService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/fm")
    public String doGetIndex(final Model model, final HttpServletRequest request) {
        return "fm/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fm/music")
    @ResponseBody
    public Map<String, Object> doGetMusicList(@RequestParam(value = "p", required = false) final Integer page,
                                              @RequestParam(value = "s", required = false) final Integer size,
                                              @RequestParam(required = false) final String q,
                                              @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        return this.fmService.doGetMusicAsJson(loginUser, page, size, q);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fm/music/{articleId}")
    @ResponseBody
    public Map<String, Object> doGetMusicInfo(@PathVariable final String articleId,
                                              @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        return this.fmService.doGetMusicInfo(loginUser, articleId, null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fm/playlist")
    @ResponseBody
    public Map<String, Object> doGetPlaylist(@RequestParam(value = "p", required = false) final Integer page,
                                             @RequestParam(value = "s", required = false) final Integer size) {
        return this.fmService.doGetPlaylist(page, size);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fm/playlist/{articleId}")
    @ResponseBody
    public Map<String, Object> doGetPlaylistInfo(@PathVariable final String articleId,
                                                 @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        return this.fmService.doGetPlaylistInfo(loginUser, articleId, null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fm/my/playlist")
    @ResponseBody
    public Map<String, Object> doGetMyPlaylist(@RequestParam(value = "p", required = false) final Integer page,
                                               @RequestParam(value = "s", required = false) final Integer size,
                                               @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        return this.fmService.doGetMyPlaylist(loginUser, page, size);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/fm/my/playlist")
    @ResponseBody
    public Map<String, Object> doPostMyPlaylist(final String title,
                                                @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        return this.fmService.doPostMyPlaylist(loginUser, title);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/fm/my/playlist/remove")
    @ResponseBody
    public Map<String, Object> doPostRemoveMyPlaylist(final String articleId,
                                                      @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        return this.fmService.doPostRemoveMyPlaylist(loginUser, articleId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fm/my/playlist/name")
    @ResponseBody
    public Map<String, Object> doGetMyPlaylistName(@ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        return this.fmService.doGetMyPlaylistName(loginUser);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/fm/my/playlist/addtrack")
    @ResponseBody
    public Map<String, Object> doPostAddTrack(final String articleId,
                                              @RequestParam(value = "fileId[]", required = false) final String[] fileIdArray,
                                              @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        return this.fmService.doPostAddTrack(loginUser, articleId, fileIdArray);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/fm/my/playlist/removetrack")
    @ResponseBody
    public Map<String, Object> doPostRemoveTrack(final String articleId,
                                                 final String fileId,
                                                 @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        return this.fmService.doPostRemoveTrack(loginUser, articleId, fileId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fm/login")
    @ResponseBody
    public Map<String, Object> doGetLoginAsJson(@ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        if (loginUser != null) {
            model.put("loginUser", loginUser);
            model.put("result", true);
        }
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/fm/login")
    @ResponseBody
    public Map<String, Object> doPostLoginAsJson(final HttpServletRequest request,
                                                 final HttpServletResponse response) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fm/my/collect")
    @ResponseBody
    public Map<String, Object> doGetMyCollect(@RequestParam(value = "p", required = false) final Integer page,
                                              @RequestParam(value = "s", required = false) final Integer size,
                                              @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        return this.fmService.doGetMyCollect(loginUser, page, size);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fm/library")
    @ResponseBody
    public Map<String, Object> doGetSearchLibrary(@RequestParam(value = "p", required = false) final Integer page,
                                                  @RequestParam(value = "s", required = false) final Integer size,
                                                  @RequestParam(required = false) final String q,
                                                  @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        return this.fmService.doGetTrackAsJson(loginUser, page, size, q, null);
    }
}
