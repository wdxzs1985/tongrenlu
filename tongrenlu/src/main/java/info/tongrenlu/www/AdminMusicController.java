package info.tongrenlu.www;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.ConsoleMusicService;
import info.tongrenlu.service.FileService;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminMusicController {

    @Autowired
    private ConsoleMusicService musicService = null;
    @Autowired
    private FileService fileService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music")
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @RequestParam(value = "q", required = false) final String query,
                             final Model model) {
        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("query", query);
        this.musicService.searchMusic(page);
        model.addAttribute("page", page);
        return "admin/music/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music/{articleId}")
    public String doGetView(@PathVariable final Integer articleId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }

        final List<String> tags = this.musicService.getTags(musicBean);

        model.addAttribute("articleBean", musicBean);
        model.addAttribute("tags", tags);

        return "admin/music/view";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music/{articleId}/edit")
    public String doGetEdit(@PathVariable final Integer articleId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }

        final List<String> tags = this.musicService.getTags(musicBean);

        model.addAttribute("articleBean", musicBean);
        model.addAttribute("tags", tags);

        return "admin/music/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/music/{articleId}/edit")
    public String doPostEdit(@PathVariable final Integer articleId,
                             final String title,
                             final String description,
                             @RequestParam(value = "tags[]", required = false) final String[] tags,
                             @RequestParam final MultipartFile cover,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model,
                             final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }

        musicBean.setTitle(title);
        musicBean.setDescription(description);

        final boolean result = this.musicService.doEdit(musicBean,
                                                        tags,
                                                        model.asMap(),
                                                        locale);

        if (result) {
            this.fileService.saveCover(musicBean, cover);
            return "redirect:/admin/music/" + musicBean.getId();
        }

        model.addAttribute("articleBean", musicBean);
        model.addAttribute("tags", tags);

        return "admin/music/edit";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music/{articleId}/delete")
    public String doGetDelete(@PathVariable final Integer articleId,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }

        this.musicService.doDelete(musicBean);

        return "redirect:/admin/music";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music/{articleId}/track/upload")
    public String doGetTrackUpload(@PathVariable final Integer articleId,
                                   @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                   final Model model) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }

        model.addAttribute("articleBean", musicBean);

        return "admin/music/track_upload";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music/{articleId}/track/file")
    @ResponseBody
    public Map<String, Object> doGetTrackFile(@PathVariable final Integer articleId,
                                              @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }

        final Map<String, Object> model = new HashMap<String, Object>();
        final List<FileBean> files = this.musicService.getTrackFiles(musicBean);
        model.put("files", files);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/music/{articleId}/track/file")
    @ResponseBody
    public Map<String, Object> doPostTrackFile(@PathVariable final Integer articleId,
                                               @RequestParam(value = "files[]") final MultipartFile[] uploads,
                                               @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }

        final Map<String, Object> model = new HashMap<String, Object>();
        final List<FileBean> files = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(uploads)) {
            for (final MultipartFile upload : uploads) {
                final FileBean fileBean = this.musicService.addTrackFile(articleId,
                                                                         upload);
                files.add(fileBean);
            }
        }

        model.put("files", files);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/music/{articleId}/{fileId}/delete")
    @ResponseBody
    public Map<String, Object> doPostTrackDelete(@PathVariable final Integer articleId,
                                                 @PathVariable final Integer fileId,
                                                 @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }

        final Map<String, Object> model = new HashMap<String, Object>();
        final FileBean fileBean = new FileBean();
        fileBean.setId(fileId);
        fileBean.setArticleId(articleId);
        this.musicService.removeTrackFile(fileBean);

        model.put("result", true);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music/{articleId}/track/sort")
    public String doGetTrackSort(@PathVariable final Integer articleId,
                                 @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                 final Model model) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }

        model.addAttribute("articleBean", musicBean);

        final List<TrackBean> trackList = this.musicService.getTrackList(articleId);
        model.addAttribute("trackList", trackList);

        return "admin/music/track_sort";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/music/{articleId}/track/sort")
    public String doPostTrackSort(@PathVariable final Integer articleId,
                                  @RequestParam(value = "trackId[]") final Integer[] trackId,
                                  @RequestParam(value = "name[]") final String[] name,
                                  @RequestParam(value = "artist[]", required = false) final String[] artist,
                                  @RequestParam(value = "original[]", required = false) final String[] original,
                                  @RequestParam(value = "instrumental[]", required = false) final Integer[] instrumental,
                                  @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                  final Model model,
                                  final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }

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

        this.musicService.updateTrack(trackList);
        return "redirect:/admin/music/" + articleId;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/music/{articleId}/publish")
    public String doGetMusicPublish(@PathVariable final Integer articleId,
                                    final Model model) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            throw new PageNotFoundException();
        }

        this.musicService.publish(musicBean);

        return "redirect:/admin/music";
    }

}
