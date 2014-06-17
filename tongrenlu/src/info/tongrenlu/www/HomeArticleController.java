package info.tongrenlu.www;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.ArticleService;
import info.tongrenlu.service.CommentService;
import info.tongrenlu.support.ControllerSupport;
import info.tongrenlu.support.LoginUserSupport;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeArticleController extends ControllerSupport {

    @Autowired
    private ArticleService articleService = null;
    @Autowired
    private CommentService commentService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String doGetIndex(final Model model, final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.articleService.doGetHomeIndex(loginUser, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public String doGetSearch(@RequestParam(required = false) final Integer page,
                              @RequestParam(required = false) final String q,
                              final Model model,
                              final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        final String searchQuery = this.decodeQuery(q);
        return this.articleService.doGetSearch(loginUser,
                                               page,
                                               searchQuery,
                                               model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/article/{articleId}/collect")
    @ResponseBody
    public Map<String, Object> doPostArticleCollect(@PathVariable final String articleId,
                                                    final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        return this.articleService.doPostArticleCollect(loginUser, articleId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/article/{articleId}/comment")
    @ResponseBody
    public Map<String, Object> doGetArticleComment(@PathVariable final String articleId,
                                                   @RequestParam final Integer page,
                                                   final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        final ArticleBean articleBean = new ArticleBean();
        articleBean.setArticleId(articleId);
        return this.commentService.doGetArticleComment(loginUser,
                                                       articleBean,
                                                       page);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/article/{articleId}/comment")
    @ResponseBody
    public Map<String, Object> doPostArticleComment(@PathVariable final String articleId,
                                                    final String content,
                                                    final HttpServletRequest request) {
        final UserBean loginUser = LoginUserSupport.getLoginUser(request);
        final ArticleBean articleBean = new ArticleBean();
        articleBean.setArticleId(articleId);
        return this.commentService.doPostArticleComment(loginUser,
                                                        articleBean,
                                                        content);
    }
}
