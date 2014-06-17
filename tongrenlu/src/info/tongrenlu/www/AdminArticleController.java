package info.tongrenlu.www;

import info.tongrenlu.service.ArticleService;
import info.tongrenlu.support.ControllerSupport;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminArticleController extends ControllerSupport {

    @Autowired
    private ArticleService articleService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/admin/file/{fileId}/delete")
    @ResponseBody
    public void doGetDeleteFile(@PathVariable final String fileId,
                                final Model model,
                                final HttpServletRequest request) {
        this.articleService.doGetDeleteFile(fileId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/article/{articleId}/recommend")
    @ResponseBody
    public Map<String, Object> doPostArticleRecommend(@PathVariable final String articleId) {
        return this.articleService.doPostArticleRecommend(articleId);
    }
}
