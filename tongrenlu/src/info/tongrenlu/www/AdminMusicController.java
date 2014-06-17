package info.tongrenlu.www;

import info.tongrenlu.service.AdminMusicService;
import info.tongrenlu.support.ControllerSupport;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminMusicController extends ControllerSupport {

    @Autowired
    private AdminMusicService musicService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music")
    public String doGetMusicIndex(@RequestParam(required = false) final Integer page,
                                  @RequestParam(required = false) final String q,
                                  final Model model) {
        final String searchQuery = this.decodeQuery(q);
        return this.musicService.doGetMusicIndex(page, searchQuery, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music/{articleId}")
    public String doGetMusicView(@PathVariable final String articleId,
                                 final Model model) {

        return this.musicService.doGetMusicView(articleId, model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/music/{articleId}/publish")
    public String doPostMusicPublish(@PathVariable final String articleId,
                                     final Model model) {

        return this.musicService.doPostMusicPublish(articleId, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music/{articleId}/track/upload")
    public String doGetTrackUpload(@PathVariable final String articleId,
                                   final Model model) {
        return this.musicService.doGetUploadTrack(articleId, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music/{articleId}/track/file")
    @ResponseBody
    public Map<String, Object> doGetArticleFile(@PathVariable final String articleId,
                                                final HttpServletRequest request) {
        return this.musicService.doGetMusicTrackFile(articleId, request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/music/{articleId}/track/file")
    @ResponseBody
    public Map<String, Object> doPostArticleFile(@PathVariable final String articleId,
                                                 @RequestParam(value = "files[]", required = false) final MultipartFile[] files,
                                                 final HttpServletRequest request) {
        return this.musicService.doPostMusicTrackFile(articleId, files, request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music/{articleId}/track/sort")
    public String doGetTrack(@PathVariable final String articleId,
                             final Model model) {
        return this.musicService.doGetTrackSort(articleId, model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/music/{articleId}/track/sort")
    @Transactional
    public String doPostTrack(@PathVariable final String articleId,
                              @RequestParam(value = "fileId[]", required = false) final String[] fileIdArray,
                              @RequestParam(value = "songTitle[]", required = false) final String[] songTitleArray,
                              @RequestParam(value = "leadArtist[]", required = false) final String[] leadArtistArray,
                              @RequestParam(value = "originalTitle[]", required = false) final String[] originalTitleArray) {
        return this.musicService.doPostTrackSort(articleId,
                                                 fileIdArray,
                                                 songTitleArray,
                                                 leadArtistArray,
                                                 originalTitleArray);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music/{articleId}/booklet/upload")
    public String doGetBookletUpload(@PathVariable final String articleId,
                                     final Model model) {
        return this.musicService.doGetBookletUpload(articleId, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music/{articleId}/booklet/file")
    @ResponseBody
    public Map<String, Object> doGetBookletFile(@PathVariable final String articleId,
                                                final HttpServletRequest request) {
        return this.musicService.doGetBookletFile(articleId, request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/music/{articleId}/booklet/file")
    @ResponseBody
    public Map<String, Object> doPostBookletFile(@PathVariable final String articleId,
                                                 @RequestParam(value = "files[]", required = false) final MultipartFile[] files,
                                                 final HttpServletRequest request) {
        return this.musicService.doPostBookletFile(articleId, files, request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music/{articleId}/booklet/sort")
    public String doGetBookletSort(@PathVariable final String articleId,
                                   final Model model) {
        return this.musicService.doGetBookletSort(articleId, model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/music/{articleId}/booklet/sort")
    public String doPostBookletSort(@PathVariable final String articleId,
                                    @RequestParam(value = "fileId[]", required = false) final String[] fileIdArray) {
        return this.musicService.doPostBookletSort(articleId, fileIdArray);
    }

}
