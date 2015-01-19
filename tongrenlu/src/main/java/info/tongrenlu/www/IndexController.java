package info.tongrenlu.www;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.HomeMusicService;

import java.util.List;

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
    private final HomeMusicService musicService = null;

    // private final Log log = LogFactory.getLog(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String doGetIndex(@ModelAttribute final UserBean loginUser, final Model model) {

        final List<MusicBean> musicRanking = this.musicService.getTopping(30);
        model.addAttribute("musicRanking", musicRanking);

        return "home/index";
    }
}
