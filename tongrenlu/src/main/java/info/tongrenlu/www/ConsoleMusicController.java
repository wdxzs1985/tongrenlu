package info.tongrenlu.www;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.exception.ForbiddenException;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.manager.FileManager;
import info.tongrenlu.service.ConsoleMusicService;
import info.tongrenlu.service.FileService;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
@RequestMapping("/console/music")
@Transactional
public class ConsoleMusicController {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private ConsoleMusicService musicService = null;
    @Autowired
    private FileService fileService = null;

    protected void throwExceptionWhenNotAllow(final MusicBean musicBean,
                                              final UserBean loginUser,
                                              final Locale locale) {
        if (musicBean == null) {
            throw new PageNotFoundException(this.messageSource.getMessage("error.pageNotFound",
                                                                          null,
                                                                          locale));
        }
        if (!loginUser.equals(musicBean.getUserBean()) && !loginUser.isAdmin()) {
            throw new ForbiddenException(this.messageSource.getMessage("error.forbidden",
                                                                       null,
                                                                       locale));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/input")
    public String doGetInput(final Model model) {
        final MusicBean inputMusic = new MusicBean();
        final String[] tags = {};
        model.addAttribute("articleBean", inputMusic);
        model.addAttribute("tags", tags);
        return "console/music/input";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/input")
    public String doPostInput(final String title,
                              final String description,
                              @RequestParam(value = "tags[]", required = false) final String[] tags,
                              @RequestParam(required = false) final MultipartFile cover,
                              @RequestParam(required = false) final MultipartFile xfd,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Model model, final Locale locale) {
        final MusicBean inputMusic = new MusicBean();
        inputMusic.setUserBean(loginUser);
        inputMusic.setTitle(title);
        inputMusic.setDescription(description);

        final boolean result = this.musicService.doCreate(inputMusic,
                                                          tags,
                                                          model.asMap(),
                                                          locale);
        if (result) {
            this.fileService.saveCover(inputMusic, cover);
            this.fileService.saveXFD(inputMusic, xfd);
            return "redirect:/console/music/" + inputMusic.getId();
        }

        model.addAttribute("articleBean", inputMusic);
        model.addAttribute("tags", tags);
        return "console/music/input";
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @RequestParam(value = "q", required = false) final String query,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("query", query);
        page.addParam("userBean", loginUser);
        this.musicService.searchMusic(page);
        model.addAttribute("page", page);

        if (loginUser.isAdmin()) {
            final int unpublishMusicCount = this.musicService.countUnpublish();
            model.addAttribute("unpublishMusicCount", unpublishMusicCount);
        }
        return "console/music/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}")
    public String doGetView(@PathVariable final Integer articleId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model, final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);

        model.addAttribute("articleBean", musicBean);

        return "console/music/view";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/track")
    @ResponseBody
    public Map<String, Object> doGetTrack(@PathVariable final Integer articleId,
                                          @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                          final Locale locale) {
        final Map<String, Object> model = new HashMap<>();
        final MusicBean musicBean = this.musicService.getById(articleId);
        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);
        // model.put("trackList", trackList);
        final List<Map<String, Object>> playlist = new ArrayList<Map<String, Object>>();
        final List<TrackBean> trackList = this.musicService.getTrackList(musicBean);
        for (final TrackBean trackBean : trackList) {
            final Map<String, Object> playable = new HashMap<String, Object>();
            playable.put("id", trackBean.getId());
            playable.put("title", trackBean.getName());
            playable.put("artist", trackBean.getArtist());
            playable.put("original",
                         StringUtils.split(trackBean.getOriginal(), '\n'));
            playable.put("instrumental",
                         CommonConstants.is(trackBean.getInstrumental()));
            playable.put("rate", trackBean.getRate());
            playlist.add(playable);
        }
        model.put("playlist", playlist);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/edit")
    public String doGetEdit(@PathVariable final Integer articleId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model, final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);

        final String[] tags = this.musicService.getTags(musicBean);

        model.addAttribute("articleBean", musicBean);
        model.addAttribute("tags", tags);

        return "console/music/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/edit")
    public String doPostEdit(@PathVariable final Integer articleId,
                             final String title,
                             final String description,
                             @RequestParam(value = "tags[]", required = false) final String[] tags,
                             @RequestParam(required = false) final MultipartFile cover,
                             @RequestParam(required = false) final MultipartFile xfd,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model, final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);

        musicBean.setTitle(title);
        musicBean.setDescription(description);

        final boolean result = this.musicService.doEdit(musicBean,
                                                        tags,
                                                        model.asMap(),
                                                        locale);

        if (result) {
            this.fileService.saveCover(musicBean, cover);
            this.fileService.saveXFD(musicBean, xfd);
            return "redirect:/console/music/" + musicBean.getId();
        }

        model.addAttribute("articleBean", musicBean);
        model.addAttribute("tags", tags);

        return "console/music/edit";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/delete")
    public String doGetDelete(@PathVariable final Integer articleId,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);

        this.musicService.doDelete(musicBean);

        return "redirect:/console/music";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/{fileId}/delete")
    @ResponseBody
    public Map<String, Object> doPostFileDelete(@PathVariable final Integer articleId,
                                                @PathVariable final Integer fileId,
                                                @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                                final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);

        this.musicService.removeFile(fileId);

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", true);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/track/upload")
    public String doGetTrackUpload(@PathVariable final Integer articleId,
                                   @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                   final Model model, final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);

        model.addAttribute("articleBean", musicBean);

        return "console/music/track_upload";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/track/file")
    @ResponseBody
    public Map<String, Object> doGetTrackFile(@PathVariable final Integer articleId,
                                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                              final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);

        final Map<String, Object> model = new HashMap<String, Object>();
        final List<FileBean> fileList = this.musicService.getTrackFileList(musicBean);
        model.put("files", this.musicService.wrapFileBeanList(fileList));
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/track/file")
    @ResponseBody
    public Map<String, Object> doPostTrackFile(@PathVariable final Integer articleId,
                                               @RequestParam(value = "files[]") final MultipartFile[] uploads,
                                               @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                               final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);

        final Map<String, Object> model = new HashMap<String, Object>();
        final List<Map<String, Object>> files = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(uploads)) {
            for (final MultipartFile upload : uploads) {
                final String filename = upload.getOriginalFilename();
                final String name = FilenameUtils.getBaseName(filename);
                final String extention = FilenameUtils.getExtension(filename);

                final FileBean fileBean = new FileBean();
                fileBean.setArticleId(articleId);
                fileBean.setName(name);
                fileBean.setExtension(extention);
                fileBean.setContentType(FileManager.AUDIO);

                final Map<String, Object> fileModel = new HashMap<String, Object>();
                this.musicService.addTrackFile(fileBean,
                                               upload,
                                               fileModel,
                                               locale);
                files.add(this.musicService.wrapFileBean(fileBean, fileModel));
            }
        }

        model.put("files", files);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/track/sort")
    public String doGetTrackSort(@PathVariable final Integer articleId,
                                 @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                 final Model model,
                                 final RedirectAttributes redirectAttr,
                                 final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);

        final List<TrackBean> trackList = this.musicService.getTrackList(musicBean);
        if (CollectionUtils.isNotEmpty(trackList)) {
            model.addAttribute("articleBean", musicBean);
            model.addAttribute("trackList", trackList);
            return "console/music/track_sort";
        } else {
            final String error = this.messageSource.getMessage("console.article.sort.noFile",
                                                               null,
                                                               locale);
            redirectAttr.addFlashAttribute("error", error);
            return "redirect:/console/music/" + articleId + "/track/upload";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/track/sort")
    public String doPostTrackSort(@PathVariable final Integer articleId,
                                  @RequestParam(value = "trackId[]") final Integer[] trackId,
                                  @RequestParam(value = "name[]") final String[] name,
                                  @RequestParam(value = "artist[]", required = false) final String[] artist,
                                  @RequestParam(value = "original[]", required = false) final String[] original,
                                  @RequestParam(value = "instrumental[]", required = false) final Integer[] instrumental,
                                  @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                  final Model model, final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);

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

        return "redirect:/console/music/" + articleId;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/booklet/upload")
    public String doGetBookletUpload(@PathVariable final Integer articleId,
                                     @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                     final Model model, final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);

        model.addAttribute("articleBean", musicBean);

        return "console/music/booklet_upload";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/booklet/file")
    @ResponseBody
    public Map<String, Object> doGetBookletFile(@PathVariable final Integer articleId,
                                                @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                                final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);

        final Map<String, Object> model = new HashMap<String, Object>();
        final List<FileBean> fileList = this.musicService.getBookletFileList(musicBean);
        model.put("files", this.musicService.wrapFileBeanList(fileList));
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/booklet/file")
    @ResponseBody
    public Map<String, Object> doPostBookletFile(@PathVariable final Integer articleId,
                                                 @RequestParam(value = "files[]") final MultipartFile[] uploads,
                                                 @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                                 final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);

        final Map<String, Object> model = new HashMap<String, Object>();
        final List<Map<String, Object>> files = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(uploads)) {
            for (final MultipartFile upload : uploads) {
                final String filename = upload.getOriginalFilename();
                final String name = FilenameUtils.getBaseName(filename);
                final String extention = FilenameUtils.getExtension(filename);

                final FileBean fileBean = new FileBean();
                fileBean.setArticleId(articleId);
                fileBean.setName(name);
                fileBean.setExtension(extention);
                fileBean.setContentType(FileManager.IMAGE);

                final Map<String, Object> fileModel = new HashMap<String, Object>();

                this.musicService.addBookletFile(fileBean,
                                                 upload,
                                                 fileModel,
                                                 locale);
                files.add(this.musicService.wrapFileBean(fileBean, fileModel));
            }
        }

        model.put("files", files);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/booklet/sort")
    public String doGetBookletSort(@PathVariable final Integer articleId,
                                   @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                   final Model model,
                                   final RedirectAttributes redirectAttr,
                                   final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);

        final List<FileBean> fileList = this.musicService.getBookletFileList(musicBean);
        if (CollectionUtils.isNotEmpty(fileList)) {
            model.addAttribute("articleBean", musicBean);
            model.addAttribute("fileList", fileList);
            return "console/music/booklet_sort";
        } else {
            final String error = this.messageSource.getMessage("console.article.sort.noFile",
                                                               null,
                                                               locale);
            redirectAttr.addFlashAttribute("error", error);
            return "redirect:/console/music/" + articleId + "/booklet/upload";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/booklet/sort")
    public String doPostBookletSort(@PathVariable final Integer articleId,
                                    @RequestParam(value = "fileId[]") final Integer[] fileId,
                                    @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                    final Model model, final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, loginUser, locale);

        final List<FileBean> fileList = new ArrayList<FileBean>();
        for (int i = 0; i < fileId.length; i++) {
            final FileBean fileBean = new FileBean();
            fileBean.setId(fileId[i]);
            fileBean.setOrderNo(i + 1);

            fileList.add(fileBean);
        }
        this.musicService.updateBookletFile(fileList);

        return "redirect:/console/music/" + articleId;
    }
}
