package info.tongrenlu.www;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.ConsoleLibraryService;
import info.tongrenlu.service.HomeMusicService;
import info.tongrenlu.service.SearchService;
import info.tongrenlu.solr.MusicDocument;
import info.tongrenlu.solr.TrackDocument;
import info.tongrenlu.support.PaginateSupport;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
@RequestMapping("/fm")
@Transactional
public class FmController {

    @Autowired
    private MessageSource messageSource = null;

    @Autowired
    private HomeMusicService musicService = null;
    @Autowired
    private ConsoleLibraryService libraryService = null;

    @Autowired
    private SearchService searchService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/lucky")
    @ResponseBody
    public Map<String, Object> lucky(@ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final Map<String, Object> model = new HashMap<String, Object>();
        final MusicBean musicBean = this.musicService.getRandomMusic(loginUser);
        model.put("music", musicBean);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search/music")
    @ResponseBody
    public Map<String, Object> musicSearch(@RequestParam(value = "p", defaultValue = "0") final Integer pageNumber,
                                           @RequestParam(value = "q", required = true) final String q) throws UnsupportedEncodingException {

        final Map<String, Object> model = new HashMap<String, Object>();

        final Pageable pageable = new PageRequest(pageNumber, PaginateSupport.PAGESIZE);

        if (StringUtils.isNotBlank(q)) {
            final String query = URLDecoder.decode(q, "utf-8");
            final Page<MusicDocument> searchResult = this.searchService.findMusic(query, pageable);
            model.put("query", query);
            model.put("searchResult", searchResult);
            model.put("result", true);
        } else {
            model.put("result", false);
        }
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search/track")
    @ResponseBody
    public Map<String, Object> trackSearch(@RequestParam(value = "p", defaultValue = "0") final Integer pageNumber,
                                           @RequestParam(value = "q", required = true) final String q) throws UnsupportedEncodingException {

        final Map<String, Object> model = new HashMap<String, Object>();

        final Pageable pageable = new PageRequest(pageNumber, PaginateSupport.PAGESIZE);

        if (StringUtils.isNotBlank(q)) {
            final String query = URLDecoder.decode(q, "utf-8");
            final Page<TrackDocument> searchResult = this.searchService.findTrack(query, pageable);
            model.put("query", query);
            model.put("searchResult", searchResult);
            model.put("result", true);
        } else {
            model.put("result", false);
        }
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/music")
    @ResponseBody
    public Map<String, Object> doGetMusic(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber) {
        final Map<String, Object> model = new HashMap<String, Object>();

        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("publishFlg", new String[] { ArticleBean.PUBLISH, ArticleBean.FREE });
        this.musicService.searchMusic(page);
        model.put("page", page);
        model.put("result", true);

        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/music/{articleId}/track")
    @ResponseBody
    public Map<String, Object> doGetTrack(@PathVariable final Integer articleId,
                                          @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                          final Locale locale) {
        final Map<String, Object> model = new HashMap<>();
        final MusicBean musicBean = this.musicService.getById(articleId);

        List<Map<String, Object>> playlist;
        if (musicBean.isFree() || this.musicService.isOwner(loginUser, musicBean)) {
            playlist = this.musicService.getPlaylist(articleId, loginUser);
        } else {
            playlist = new ArrayList<Map<String, Object>>();
            final Map<String, Object> playable = new HashMap<String, Object>();
            playable.put("title", musicBean.getTitle());
            playable.put("xfd", true);
            playlist.add(playable);
        }
        model.put("musicBean", musicBean);
        model.put("playlist", playlist);
        model.put("result", true);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/library")
    @ResponseBody
    public Map<String, Object> doGetLibrary(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                            @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final Map<String, Object> model = new HashMap<String, Object>();

        if (loginUser.isMember()) {
            final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber, PaginateSupport.PAGESIZE);
            page.addParam("userBean", loginUser);
            page.addParam("status", 1);
            page.addParam("order", "byTitle");
            this.libraryService.searchLibrary(page);
            model.put("page", page);
            model.put("result", true);
        } else {
            model.put("result", false);
        }

        return model;
    }

}
