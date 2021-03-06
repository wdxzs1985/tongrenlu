package info.tongrenlu.www;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.CommentBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.CommentService;
import info.tongrenlu.service.ConsoleMusicService;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/music")
@Transactional
public class AdminMusicController {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private ConsoleMusicService musicService = null;
    @Autowired
    private final CommentService commentService = null;

    private void throwExceptionWhenNotFound(final MusicBean musicBean, final Locale locale) {
        if (musicBean == null) {
            throw new PageNotFoundException(this.messageSource.getMessage("error.pageNotFound", null, locale));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @RequestParam(value = "q", required = false) final String query,
                             final Model model) {
        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("query", query);
        this.musicService.searchMusic(page);
        model.addAttribute("page", page);

        final int unpublishMusicCount = this.musicService.countUnpublish();
        model.addAttribute("unpublishMusicCount", unpublishMusicCount);

        return "admin/music/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "unpublish")
    public String doGetUnpublish(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                 @RequestParam(value = "q", required = false) final String query,
                                 final Model model) {
        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("query", query);
        page.addParam("publishFlg", new String[] { ArticleBean.UNPUBLISH });
        this.musicService.searchMusic(page);
        model.addAttribute("page", page);

        final int unpublishMusicCount = this.musicService.countUnpublish();
        model.addAttribute("unpublishMusicCount", unpublishMusicCount);

        return "admin/music/unpublish";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}")
    public String doGetView(@PathVariable final Integer articleId, final Model model, final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotFound(musicBean, locale);

        model.addAttribute("articleBean", musicBean);

        return "admin/music/view";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/edit")
    public String doGetEdit(@PathVariable final Integer articleId, final Model model, final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        this.throwExceptionWhenNotFound(musicBean, locale);
        final String[] tags = this.musicService.getTags(musicBean);
        model.addAttribute("articleBean", musicBean);
        model.addAttribute("tags", tags);
        return "admin/music/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/edit")
    public String doPostEdit(@PathVariable final Integer articleId,
                             final String title,
                             final String description,
                             @RequestParam(value = "tags[]", required = false) final String[] tags,
                             @RequestParam(required = false) final MultipartFile cover,
                             @RequestParam(required = false) final MultipartFile xfd,
                             final Model model,
                             final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotFound(musicBean, locale);

        musicBean.setTitle(title);
        musicBean.setDescription(description);

        final boolean result = this.musicService.doEdit(musicBean, tags, cover, xfd, model.asMap(), locale);

        if (result) {
            return "redirect:/admin/music/" + musicBean.getId();
        }

        model.addAttribute("articleBean", musicBean);
        model.addAttribute("tags", tags);

        return "admin/music/edit";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/delete")
    public String doGetDelete(@PathVariable final Integer articleId, final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotFound(musicBean, locale);

        this.musicService.doDelete(musicBean);

        return "redirect:/admin/music";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/track/upload")
    public String doGetTrackUpload(@PathVariable final Integer articleId, final Model model, final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotFound(musicBean, locale);

        model.addAttribute("articleBean", musicBean);

        return "admin/music/track_upload";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/{fileId}/delete")
    @ResponseBody
    public Map<String, Object> doPostTrackDelete(@PathVariable final Integer articleId,
                                                 @PathVariable final Integer fileId,
                                                 final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotFound(musicBean, locale);

        this.musicService.removeFile(fileId);

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", true);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/track/sort")
    public String doGetTrackSort(@PathVariable final Integer articleId,
                                 final Model model,
                                 final RedirectAttributes redirectAttr,
                                 final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotFound(musicBean, locale);

        final List<TrackBean> trackList = this.musicService.getTrackList(musicBean);
        if (CollectionUtils.isNotEmpty(trackList)) {
            model.addAttribute("articleBean", musicBean);
            model.addAttribute("trackList", trackList);
            return "admin/music/track_sort";
        } else {
            final String error = this.messageSource.getMessage("console.article.sort.noFile", null, locale);
            redirectAttr.addFlashAttribute("error", error);
            return "redirect:/admin/music/" + articleId + "/track/upload";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/track/sort")
    public String doPostTrackSort(@PathVariable final Integer articleId,
                                  @RequestParam(value = "trackId[]") final Integer[] trackId,
                                  @RequestParam(value = "name[]") final String[] name,
                                  @RequestParam(value = "artist[]", required = false) final String[] artist,
                                  @RequestParam(value = "original[]", required = false) final String[] original,
                                  @RequestParam(value = "instrumental[]", required = false) final Integer[] instrumental,
                                  final Model model,
                                  final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotFound(musicBean, locale);

        model.addAttribute("articleBean", musicBean);

        final List<TrackBean> trackList = new ArrayList<TrackBean>();
        for (int i = 0; i < trackId.length; i++) {
            final TrackBean trackBean = new TrackBean();
            trackBean.setId(trackId[i]);
            trackBean.setName(name[i]);

            if (ArrayUtils.isNotEmpty(artist)) {
                trackBean.setArtist(artist[i]);
            }
            if (ArrayUtils.isNotEmpty(original)) {
                trackBean.setOriginal(original[i]);
            }
            if (ArrayUtils.isNotEmpty(instrumental)) {
                if (ArrayUtils.contains(instrumental, trackId[i])) {
                    trackBean.setInstrumental(CommonConstants.CHR_TRUE);
                } else {
                    trackBean.setInstrumental(CommonConstants.CHR_FALSE);
                }
            }

            final FileBean fileBean = new FileBean();
            fileBean.setId(trackId[i]);
            fileBean.setOrderNo(i + 1);
            trackBean.setFileBean(fileBean);

            trackList.add(trackBean);
        }

        this.musicService.updateTrackList(trackList, musicBean);
        return "redirect:/admin/music/" + articleId;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/booklet/upload")
    public String doGetBookletUpload(@PathVariable final Integer articleId,
                                     @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                     final Model model,
                                     final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotFound(musicBean, locale);

        model.addAttribute("articleBean", musicBean);

        return "admin/music/booklet_upload";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/booklet/sort")
    public String doGetBookletSort(@PathVariable final Integer articleId,
                                   final Model model,
                                   final RedirectAttributes redirectAttr,
                                   final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotFound(musicBean, locale);

        final List<FileBean> fileList = this.musicService.getBookletFileList(musicBean);
        if (CollectionUtils.isNotEmpty(fileList)) {
            model.addAttribute("articleBean", musicBean);
            model.addAttribute("fileList", fileList);
            return "admin/music/booklet_sort";
        } else {
            final String error = this.messageSource.getMessage("console.article.sort.noFile", null, locale);
            redirectAttr.addFlashAttribute("error", error);
            return "redirect:/admin/music/" + articleId + "/booklet/upload";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/booklet/sort")
    public String doPostBookletSort(@PathVariable final Integer articleId,
                                    @RequestParam(value = "fileId[]") final Integer[] fileId,
                                    final Model model,
                                    final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotFound(musicBean, locale);

        final List<FileBean> fileList = new ArrayList<FileBean>();
        for (int i = 0; i < fileId.length; i++) {
            final FileBean fileBean = new FileBean();
            fileBean.setId(fileId[i]);
            fileBean.setOrderNo(i + 1);

            fileList.add(fileBean);
        }
        this.musicService.updateBookletFile(fileList);

        return "redirect:/admin/music/" + articleId;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/publish")
    public String doGetMusicPublish(@PathVariable final Integer articleId, final Model model, final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        this.throwExceptionWhenNotFound(musicBean, locale);
        this.musicService.publish(musicBean);
        return "redirect:/admin/music";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/free")
    public String doGetMusicFree(@PathVariable final Integer articleId, final Model model, final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        this.throwExceptionWhenNotFound(musicBean, locale);
        this.musicService.free(musicBean);
        return "redirect:/admin/music";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/comment")
    public String doGetComment(@PathVariable final Integer articleId,
                               @RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                               @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                               final Model model,
                               final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        this.throwExceptionWhenNotFound(musicBean, locale);
        model.addAttribute("articleBean", musicBean);

        final PaginateSupport<CommentBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("articleId", articleId);
        this.commentService.searchMusicComment(page);
        model.addAttribute("page", page);
        return "admin/music/comment";
    }
}
