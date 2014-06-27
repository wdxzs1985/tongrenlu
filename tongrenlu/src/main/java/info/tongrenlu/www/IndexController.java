package info.tongrenlu.www;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.CommentService;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
public class IndexController {

    @Autowired
    private SearchService searchService = null;

    @Autowired
    private CommentService commentService = null;

    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String doGetIndex(@ModelAttribute final UserBean loginUser,
                             final Model model) {
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
