package info.tongrenlu;

import info.tongrenlu.solr.ArticleDocument;
import info.tongrenlu.solr.ArticleRepository;
import info.tongrenlu.solr.TrackDocument;
import info.tongrenlu.solr.TrackRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private ArticleRepository articleRepo = null;
    @Autowired
    private TrackRepository trackRepo = null;

    @RequestMapping("")
    public String index(final Model model) {
        final List<ArticleDocument> page = this.articleRepo.findByTitleStartingWith("");
        model.addAttribute("page", page);
        return "index";
    }

    @RequestMapping(value = "/music", method = RequestMethod.GET)
    public String getMusicInput(final Model model) {
        final ArticleBean articleBean = new ArticleBean();
        model.addAttribute("articleBean", articleBean);

        return "music_input";
    }

    @RequestMapping(value = "/music", method = RequestMethod.POST)
    public String postMusicInput(@ModelAttribute final ArticleBean articleBean,
                                 @RequestParam("circle[]") final String[] circles,
                                 final Model model) {
        if (StringUtils.isBlank(articleBean.getArticleId())) {
            model.addAttribute("articleBean", articleBean);
            return "music_input";
        }

        if (ArrayUtils.isNotEmpty(circles)) {
            articleBean.setCircleList(Arrays.asList(circles));
        }

        final ArticleDocument articleDocument = articleBean.toDocument();
        articleDocument.setEvent("C85");
        articleDocument.setRelease(2014);
        this.articleRepo.save(articleDocument);
        model.addAttribute("articleId", articleBean.getArticleId());
        return "redirect:/{articleId}";
    }

    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    public String getMusic(@PathVariable final String articleId,
                           final Model model) {
        final ArticleDocument articleDocument = this.articleRepo.findOne(articleId);
        model.addAttribute("articleBean", articleDocument.toArticleBean());

        final List<TrackDocument> trackDocuments = this.trackRepo.findByArticleId(articleId);
        final List<PlaylistTrackBean> trackList = new ArrayList<PlaylistTrackBean>();
        for (final TrackDocument document : trackDocuments) {
            trackList.add(document.toPlaylistTrackBean());
        }
        model.addAttribute("trackList", trackList);

        return "music";
    }

    @RequestMapping(value = "/{articleId}/input", method = RequestMethod.GET)
    public String getMusicInput2(@PathVariable final String articleId,
                                 @ModelAttribute final ArticleBean articleBean,
                                 final Model model) {
        final ArticleDocument articleDocument = this.articleRepo.findOne(articleId);
        model.addAttribute("articleBean", articleDocument.toArticleBean());
        return "music_input";
    }

    @RequestMapping(value = "/{articleId}/input", method = RequestMethod.POST)
    public String postMusicInput2(@PathVariable final String articleId,
                                  @ModelAttribute final ArticleBean articleBean,
                                  @RequestParam("circle[]") final String[] circles,
                                  final Model model) {
        final ArticleDocument articleDocument = this.articleRepo.findOne(articleId);
        articleDocument.setTitle(articleBean.getTitle());
        articleDocument.setEvent("C85");
        articleDocument.setRelease(2014);
        if (ArrayUtils.isNotEmpty(circles)) {
            articleDocument.setCircleList(Arrays.asList(circles));
        }
        this.articleRepo.save(articleDocument);
        model.addAttribute("articleId", articleId);
        return "redirect:/{articleId}";
    }

    @RequestMapping(value = "/{articleId}/track", method = RequestMethod.GET)
    public String getMusicTrackInput(@PathVariable final String articleId,
                                     final Model model) {
        final ArticleDocument articleDocument = this.articleRepo.findOne(articleId);
        model.addAttribute("articleBean", articleDocument.toArticleBean());

        final List<TrackDocument> trackDocuments = this.trackRepo.findByArticleId(articleId);
        final List<TrackBean> trackList = new ArrayList<TrackBean>();
        for (final TrackDocument document : trackDocuments) {
            trackList.add(document.toTrackBean());
        }
        model.addAttribute("trackList", trackList);
        return "track";
    }

    @RequestMapping(value = "/{articleId}/track", method = RequestMethod.POST)
    public String postMusicTrackInput(@PathVariable final String articleId,
                                      @RequestParam("fileId[]") final String[] fileIds,
                                      @RequestParam("track[]") final String[] tracks,
                                      final Model model) {
        final ArticleDocument articleDocument = this.articleRepo.findOne(articleId);
        final List<TrackDocument> entities = new ArrayList<TrackDocument>();
        for (int i = 0; i < fileIds.length; i++) {
            final String fileId = fileIds[i];
            final String track = tracks[i];
            if (StringUtils.isBlank(fileId)) {
                continue;
            }
            final TrackDocument trackDocument = new TrackDocument();
            trackDocument.setArticleId(articleId);
            trackDocument.setTitle(articleDocument.getTitle());
            trackDocument.setId(fileId);
            trackDocument.setFileId(fileId);
            trackDocument.setTrack(track);
            entities.add(trackDocument);
        }
        this.trackRepo.save(entities);

        model.addAttribute("articleId", articleId);
        return "redirect:/{articleId}";
    }

    @RequestMapping("/reset")
    public String reset(final Model model) {
        this.articleRepo.deleteAll();

        final ArticleDocument articleBean = new ArticleDocument();
        articleBean.setId("201406101261392");
        articleBean.setArticleId("201406101261392");
        articleBean.setTitle("[1569＋EX永遠亭] 千終樂 -SIDE BADEND-");
        articleBean.setCircleList(Arrays.asList("1569", "EX永遠亭"));
        articleBean.setRelease(2014);
        articleBean.setEvent("例大祭11");
        articleBean.setTagList(Arrays.asList("1569", "EX永遠亭", "例大祭11"));
        // insert some products
        this.articleRepo.save(articleBean);

        articleBean.setId("201406071259178");
        articleBean.setArticleId("201406071259178");
        articleBean.setTitle("[子猫奪回屋] Mind games ～幻想心遊塔～");
        articleBean.setCircleList(Arrays.asList("子猫奪回屋"));
        articleBean.setEvent("例大祭11");
        articleBean.setRelease(2014);
        articleBean.setTagList(Arrays.asList("子猫奪回屋", "例大祭11"));
        // insert some products
        this.articleRepo.save(articleBean);
        return "redirect:/";
    }
}
