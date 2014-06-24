package info.tongrenlu.www;

import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.HomeMusicService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class HomeMusicController {

    @Autowired
    private HomeMusicService musicService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/music/{articleId}/track")
    @ResponseBody
    public Map<String, Object> doGetTrack(@PathVariable final Integer articleId) {
        final Map<String, Object> model = new HashMap<>();
        final List<TrackBean> trackList = this.musicService.getTrackList(articleId);
        model.put("trackList", trackList);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/music/{articleId}/booklet")
    @ResponseBody
    public Map<String, Object> doGetBooklet(@PathVariable final Integer articleId) {
        final Map<String, Object> model = new HashMap<>();
        final List<FileBean> fileList = this.musicService.getBookletList(articleId);
        model.put("fileList", fileList);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/music")
    public String doGetIndex(@RequestParam(required = false) final Integer page,
                             @RequestParam(required = false) final String q,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        throw new PageNotFoundException();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/music/{articleId}")
    public String doGetView(@PathVariable final String articleId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model) {
        throw new PageNotFoundException();
    }

}
