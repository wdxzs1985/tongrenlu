package info.tongrenlu.www;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.HomeComicService;
import info.tongrenlu.service.HomeMusicService;
import info.tongrenlu.service.SearchService;
import info.tongrenlu.solr.ArticleDocument;
import info.tongrenlu.support.PaginateSupport;

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

        final PaginateSupport<MusicBean> musicPage = new PaginateSupport<>(1,
                                                                           30);
        musicPage.addParam("loginUser", loginUser);
        musicPage.addParam("publishFlg", CommonConstants.CHR_TRUE);
        this.musicService.searchMusic(musicPage);

        final PaginateSupport<ComicBean> comicPage = new PaginateSupport<>(1,
                                                                           20);
        comicPage.addParam("loginUser", loginUser);
        musicPage.addParam("publishFlg", CommonConstants.CHR_TRUE);
        this.comicService.searchComic(comicPage);

        model.addAttribute("musicPage", musicPage);
        model.addAttribute("comicPage", comicPage);

        return "home/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public String doGetSearch(@RequestParam(value = "p", defaultValue = "0") final Integer pageNumber,
                              @RequestParam(value = "q", required = false) final String query,
                              final Model model) {
        if (StringUtils.isNotBlank(query)) {

            final Pageable pageable = new PageRequest(pageNumber,
                                                      PaginateSupport.PAGESIZE);

            final Page<ArticleDocument> searchResult = this.searchService.findArticle(query,
                                                                                      pageable);
            model.addAttribute("query", query);
            model.addAttribute("searchResult", searchResult);
        }
        return "home/search";
    }

}
