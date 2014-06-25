package info.tongrenlu.www;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.exception.ForbiddenException;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.HomeMusicService;
import info.tongrenlu.support.PaginateSupport;

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

    protected void throwExceptionWhenNotAllow(final MusicBean musicBean) {
        if (musicBean == null) {
            throw new PageNotFoundException();
        } else if (!CommonConstants.is(musicBean.getPublishFlg())) {
            throw new ForbiddenException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/music")
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @RequestParam(value = "q", required = false) final String query,
                             final Model model) {

        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("query", query);
        page.addParam("publishFlg", CommonConstants.CHR_TRUE);
        this.musicService.searchMusic(page);
        model.addAttribute("page", page);

        return "home/music/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/music/{articleId}")
    public String doGetView(@PathVariable final Integer articleId,
                            @ModelAttribute final UserBean loginUser,
                            final Model model) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean);

        final String[] tags = this.musicService.getTags(musicBean);

        model.addAttribute("articleBean", musicBean);
        model.addAttribute("tags", tags);

        return "home/music/view";
    }

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

}
