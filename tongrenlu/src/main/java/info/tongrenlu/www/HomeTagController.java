package info.tongrenlu.www;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.ArticleManager;
import info.tongrenlu.service.TagService;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class HomeTagController {

    @Autowired
    private TagService tagService = null;
    @Autowired
    private ArticleManager articleManager = null;

    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/tag/search")
    @ResponseBody
    public List<String> doGetSearchTag(@RequestParam final String query) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("query = " + query);
        }
        if (StringUtils.isBlank(query)) {
            return Collections.emptyList();
        }

        final PaginateSupport<TagBean> page = new PaginateSupport<>(1);
        page.addParam("tag", query);

        this.tagService.searchTag(page);

        if (CollectionUtils.isNotEmpty(page.getItems())) {
            final List<String> tagList = new ArrayList<String>();
            for (final TagBean tagBean : page.getItems()) {
                tagList.add(tagBean.getTag());
            }
            return tagList;
        }

        return Collections.emptyList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tag/{tag}")
    public String doGetTag(@PathVariable final String tag,
                           @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                           final Model model) {
        final TagBean tagBean = this.tagService.getTagByTag(tag);

        if (tagBean == null) {
            model.addAttribute("tag", tag);
            return "home/tag/error";
        }

        final PaginateSupport<MusicBean> musicPage = new PaginateSupport<>(1,
                                                                           12);
        musicPage.addParam("tagBean", tagBean);
        musicPage.addParam("loginUser", loginUser);
        this.tagService.searchMusicByTag(musicPage);

        final PaginateSupport<ComicBean> comicPage = new PaginateSupport<>(1,
                                                                           12);
        comicPage.addParam("tagBean", tagBean);
        comicPage.addParam("loginUser", loginUser);
        this.tagService.searchComicByTag(comicPage);

        model.addAttribute("tagBean", tagBean);
        model.addAttribute("musicPage", musicPage);
        model.addAttribute("comicPage", comicPage);
        return "home/tag/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tag/{tag}/music")
    public String doGetTagMusic(@PathVariable final String tag,
                                @RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                final Model model) {
        final TagBean tagBean = this.tagService.getTagByTag(tag);

        if (tagBean == null) {
            model.addAttribute("tag", tag);
            return "home/tag/error";
        }

        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("tagBean", tagBean);
        page.addParam("loginUser", loginUser);
        this.tagService.searchMusicByTag(page);

        model.addAttribute("tagBean", tagBean);
        model.addAttribute("page", page);
        return "home/tag/music";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tag/{tag}/comic")
    public String doGetTagComic(@PathVariable final String tag,
                                @RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                final Model model) {
        final TagBean tagBean = this.tagService.getTagByTag(tag);

        if (tagBean == null) {
            model.addAttribute("tag", tag);
            return "home/tag/error";
        }

        final PaginateSupport<ComicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("tagBean", tagBean);
        page.addParam("loginUser", loginUser);
        this.tagService.searchComicByTag(page);

        model.addAttribute("tagBean", tagBean);
        model.addAttribute("page", page);
        return "home/tag/comic";
    }
}
