package info.tongrenlu.www;

import info.tongrenlu.domain.TagBean;
import info.tongrenlu.service.TagService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/tag/search")
    @ResponseBody
    public List<String> doGetSearchTag(@RequestParam final String query) {
        if (this.log.isDebugEnabled()) {
            this.log.info("query = " + query);
        }
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

}
