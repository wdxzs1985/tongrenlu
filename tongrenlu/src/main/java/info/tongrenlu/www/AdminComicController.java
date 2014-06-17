package info.tongrenlu.www;

import info.tongrenlu.service.AdminComicService;
import info.tongrenlu.support.ControllerSupport;

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
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminComicController extends ControllerSupport {

    @Autowired
    private AdminComicService comicService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/admin/comic")
    public String doGetComicIndex(@RequestParam(required = false) final Integer page,
                                  @RequestParam(required = false) final String q,
                                  final Model model) {
        final String searchQuery = this.decodeQuery(q);
        return this.comicService.doGetComicIndex(page, searchQuery, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/comic/{articleId}")
    public String doGetComicView(@PathVariable final String articleId,
                                 final Model model) {
        return this.comicService.doGetComicView(articleId, model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/comic/{articleId}/publish")
    public String doPostComicPublish(@PathVariable final String articleId,
                                     final Model model) {
        return this.comicService.doPostComicPublish(articleId, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/comic/{articleId}/upload")
    public String doGetUpload(@PathVariable final String articleId,
                              final Model model) {
        return this.comicService.doGetUpload(articleId, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/comic/{articleId}/file")
    @ResponseBody
    public Map<String, Object> doGetComicFile(@PathVariable final String articleId,
                                              final HttpServletRequest request) {
        return this.comicService.doGetComicFile(articleId, request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/comic/{articleId}/file")
    @ResponseBody
    public Map<String, Object> doPostComicFile(@PathVariable final String articleId,
                                               @RequestParam(value = "files[]", required = false) final MultipartFile[] files,
                                               final HttpServletRequest request) {
        return this.comicService.doPostComicFile(articleId, files, request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/comic/{articleId}/sort")
    public String doGetSort(@PathVariable final String articleId,
                            final Model model) {
        return this.comicService.doGetSort(articleId, model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/comic/{articleId}/sort")
    public String doPostSort(@PathVariable final String articleId,
                             @RequestParam(value = "fileId[]", required = false) final String[] fileIdArray) {
        return this.comicService.doPostSort(articleId, fileIdArray);
    }
}
