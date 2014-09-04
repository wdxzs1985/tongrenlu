package info.tongrenlu.www;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.HomeComicService;
import info.tongrenlu.service.HomeMusicService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
@Transactional
public class IndexController {

    @Autowired
    private HomeComicService comicService = null;
    @Autowired
    private HomeMusicService musicService = null;

    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String doGetIndex(@ModelAttribute final UserBean loginUser,
                             final Model model) {

        final List<MusicBean> musicRanking = this.musicService.getTopping(30);
        model.addAttribute("musicRanking", musicRanking);

        final List<ComicBean> comicRanking = this.comicService.getTopping(20);
        model.addAttribute("comicRanking", comicRanking);

        return "home/index";
    }
}
