package info.tongrenlu.www;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.service.AdminMusicService;
import info.tongrenlu.service.ConsoleMusicService;
import info.tongrenlu.support.PaginateSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminMusicController {

    @Autowired
    private ConsoleMusicService musicService = null;
    @Autowired
    private AdminMusicService adminMmusicService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music")
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @RequestParam(value = "q", required = false) final String query,
                             final Model model) {
        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("query", query);
        this.musicService.searchMusic(page);
        model.addAttribute("page", page);
        return "admin/music/index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/music/{articleId}/publish")
    public String doPostMusicPublish(@PathVariable final String articleId,
                                     final Model model) {
        return this.adminMmusicService.doPostMusicPublish(articleId, model);
    }

}
