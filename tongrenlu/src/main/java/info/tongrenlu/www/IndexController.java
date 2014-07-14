package info.tongrenlu.www;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.HomeComicService;
import info.tongrenlu.service.HomeMusicService;
import info.tongrenlu.service.SearchService;
import info.tongrenlu.solr.ComicDocument;
import info.tongrenlu.solr.MusicDocument;
import info.tongrenlu.support.PaginateSupport;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
@Transactional
public class IndexController {

    @Autowired
    private HomeComicService comicService = null;
    @Autowired
    private HomeMusicService musicService = null;
    @Autowired
    private SearchService searchService = null;

    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String doGetIndex(@ModelAttribute final UserBean loginUser,
                             final Model model) {

        final List<MusicBean> musicRanking = this.musicService.getRanking(30);
        model.addAttribute("musicRanking", musicRanking);

        final List<ComicBean> comicRanking = this.comicService.getRanking(20);
        model.addAttribute("comicRanking", comicRanking);

        return "home/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search/music")
    public String doGetSearchMusic(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                   @RequestParam(value = "q", required = false) final String query,
                                   final Model model) {
        if (StringUtils.isNotBlank(query)) {

            final Pageable pageable = new PageRequest(Math.max(pageNumber, 1) - 1,
                                                      PaginateSupport.PAGESIZE);

            final Page<MusicDocument> searchResult = this.searchService.findMusic(query,
                                                                                  pageable);
            model.addAttribute("query", query);
            model.addAttribute("searchResult", searchResult);
            return "home/search/music";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search/comic")
    public String doGetSearchComic(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                   @RequestParam(value = "q", required = false) final String query,
                                   final Model model) {
        if (StringUtils.isNotBlank(query)) {

            final Pageable pageable = new PageRequest(Math.max(pageNumber, 1) - 1,
                                                      PaginateSupport.PAGESIZE);

            final Page<ComicDocument> searchResult = this.searchService.findComic(query,
                                                                                  pageable);
            model.addAttribute("query", query);
            model.addAttribute("searchResult", searchResult);
            return "home/search/comic";
        } else {
            return "redirect:/";
        }
    }
}
