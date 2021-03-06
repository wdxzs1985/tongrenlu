package info.tongrenlu.www;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.HomeMusicService;
import info.tongrenlu.service.TagService;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping(value = "/tag")
@Transactional
public class HomeTagController {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private TagService tagService = null;
    @Autowired
    private HomeMusicService musicService = null;

    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/search")
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

    @RequestMapping(method = RequestMethod.GET, value = "/{tagId}")
    public String doGetTagMusic(@PathVariable final Integer tagId,
                                @RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                final Model model, final Locale locale) {
        final TagBean tagBean = this.tagService.getById(tagId);

        if (tagBean == null) {
            throw new PageNotFoundException(this.messageSource.getMessage("error.pageNotFound",
                                                                          null,
                                                                          locale));
        }

        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("tagBean", tagBean);
        page.addParam("loginUser", loginUser);
        this.tagService.searchMusicByTag(page);

        model.addAttribute("tagBean", tagBean);
        model.addAttribute("page", page);

        final List<MusicBean> ranking = this.musicService.getRanking(20);
        model.addAttribute("ranking", ranking);

        return "home/tag/music";
    }

}
