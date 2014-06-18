package info.tongrenlu.www;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.ArticleService;
import info.tongrenlu.service.CommentService;

import java.util.Collections;
import java.util.Map;

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
public class HomeArticleController {

    @Autowired
    private ArticleService articleService = null;
    @Autowired
    private CommentService commentService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String doGetIndex(@ModelAttribute final UserBean loginUser,
                             final Model model) {

        return "home/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public String doGetSearch(@RequestParam(required = false) final Integer page,
                              @RequestParam(required = false) final String q,
                              @ModelAttribute final UserBean loginUser,
                              final Model model) {
        return "search/index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/article/{articleId}/collect")
    @ResponseBody
    public Map<String, Object> doPostArticleCollect(@PathVariable final String articleId,
                                                    @ModelAttribute final UserBean loginUser) {
        return Collections.emptyMap();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/article/{articleId}/comment")
    @ResponseBody
    public Map<String, Object> doGetArticleComment(@PathVariable final String articleId,
                                                   @RequestParam final Integer page,
                                                   @ModelAttribute final UserBean loginUser) {
        return Collections.emptyMap();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/article/{articleId}/comment")
    @ResponseBody
    public Map<String, Object> doPostArticleComment(@PathVariable final String articleId,
                                                    final String content,
                                                    @ModelAttribute final UserBean loginUser) {
        return Collections.emptyMap();
    }
}
