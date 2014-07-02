package info.tongrenlu.www;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.HomeMusicService;
import info.tongrenlu.service.SearchService;
import info.tongrenlu.solr.MusicDocument;
import info.tongrenlu.solr.TrackDocument;
import info.tongrenlu.support.PaginateSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/fm")
public class FmController {

    @Autowired
    private HomeMusicService musicService = null;

    @Autowired
    private SearchService searchService = null;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String index() {

        return "fm/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/music")
    @ResponseBody
    public Map<String, Object> music(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                     @RequestParam(value = "q", required = false) final String query) {

        final Map<String, Object> model = new HashMap<String, Object>();

        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("query", query);
        page.addParam("publishFlg", CommonConstants.CHR_TRUE);
        this.musicService.searchMusic(page);

        model.put("page", page);

        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/music/{articleId}")
    @ResponseBody
    public Map<String, Object> music(@PathVariable final Integer articleId) {

        final Map<String, Object> model = new HashMap<String, Object>();

        final MusicBean musicBean = this.musicService.getById(articleId);

        if (musicBean == null) {
            throw new PageNotFoundException();
        }

        final List<TrackBean> trackList = this.musicService.getTrackList(articleId);

        model.put("music", musicBean);
        model.put("trackList", trackList);

        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search/music")
    @ResponseBody
    public Map<String, Object> musicSearch(@RequestParam(value = "p", defaultValue = "0") final Integer pageNumber,
                                           @RequestParam(value = "q", required = true) final String query) {

        final Map<String, Object> model = new HashMap<String, Object>();

        final Pageable pageable = new PageRequest(pageNumber,
                                                  PaginateSupport.PAGESIZE);

        final Page<MusicDocument> searchResult = this.searchService.findMusic(query,
                                                                              pageable);
        model.put("query", query);
        model.put("searchResult", searchResult);

        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search/track")
    @ResponseBody
    public Map<String, Object> trackSearch(@RequestParam(value = "p", defaultValue = "0") final Integer pageNumber,
                                           @RequestParam(value = "q", required = true) final String query) {

        final Map<String, Object> model = new HashMap<String, Object>();

        final Pageable pageable = new PageRequest(pageNumber,
                                                  PaginateSupport.PAGESIZE);

        final Page<TrackDocument> searchResult = this.searchService.findTrack(query,
                                                                              pageable);
        model.put("query", query);
        model.put("searchResult", searchResult);

        return model;
    }
}
