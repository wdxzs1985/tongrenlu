package info.tongrenlu.www;

import info.tongrenlu.service.AdminToolsService;
import info.tongrenlu.support.ControllerSupport;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminToolsController extends ControllerSupport {

    @Autowired
    private AdminToolsService toolsService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/admin/tools")
    public String doGetToolsIndex(final Model model) {
        return this.toolsService.doGetToolsIndex(model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/convert-cover")
    @ResponseBody
    public void doGetConvertCover(final String articleId,
                                  final HttpServletRequest request) {
        this.toolsService.doGetConvertCover(articleId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/convert-avatar")
    @ResponseBody
    public void doGetConvertAvatar(final String articleId,
                                   final HttpServletRequest request) {
        this.toolsService.doGetConvertAvatar(articleId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/convert-thumbnail")
    @ResponseBody
    public void doPostConvertThumbnail(final String articleId,
                                       final HttpServletRequest request) {
        this.toolsService.doPostConvertThumbnail(articleId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/create-solr-index")
    @ResponseBody
    public void doPostCreateSolrIndex(final HttpServletRequest request)
            throws SolrServerException, IOException {
        this.toolsService.doPostCreateSolrIndex();
    }
}
