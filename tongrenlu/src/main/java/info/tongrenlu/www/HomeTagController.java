package info.tongrenlu.www;

import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.TagService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

    @RequestMapping(method = RequestMethod.GET, value = "/tag/search")
    @ResponseBody
    public List<String> doGetSearchTag(final String query) {
        if (StringUtils.isBlank(query)) {
            return Collections.emptyList();
        }
        final List<String> tagList = new ArrayList<String>();
        final List<TagBean> tagBeanList = this.tagService.getTagListByTag(query,
                                                                          1,
                                                                          10);
        for (final TagBean tagBean : tagBeanList) {
            tagList.add(tagBean.getTag());
        }
        return tagList;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/tag/input")
    @ResponseBody
    public Map<String, Object> doPostInputTag(final String tag) {
        return this.tagService.doPostInputTag(tag);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tag/{tagId}")
    public String doGetView(@PathVariable final String tagId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model) {
        return this.tagService.doGetView(loginUser, tagId, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tag/{tagId}/comic")
    public String doGetComic(@PathVariable final String tagId,
                             @RequestParam(required = false) final Integer page,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        return this.tagService.doGetTagComic(loginUser, tagId, page, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tag/{tagId}/music")
    public String doGetMusic(@PathVariable final String tagId,
                             @RequestParam(required = false) final Integer page,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        return this.tagService.doGetTagMusic(loginUser, tagId, page, model);
    }

}
