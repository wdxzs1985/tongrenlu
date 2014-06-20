package info.tongrenlu.www;

import info.tongrenlu.service.ArticleService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConsoleController {

    @Autowired
    protected ArticleService articleService = null;

    @RequestMapping(method = RequestMethod.POST, value = "/console/tag/remove")
    @ResponseBody
    public Map<String, Object> doPostRemoveTag(final String articleId,
                                               final String tagId) {

        return this.articleService.doPostRemoveTag(articleId, tagId);
    }

}
