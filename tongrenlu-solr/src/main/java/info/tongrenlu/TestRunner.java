package info.tongrenlu;

import info.tongrenlu.solr.ArticleRepository;
import info.tongrenlu.solr.MusicDocument;
import info.tongrenlu.solr.MusicRepository;
import info.tongrenlu.solr.TrackDocument;
import info.tongrenlu.solr.TrackRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TestRunner implements CommandLineRunner {

    @Autowired
    private MusicRepository musicRepo;
    @Autowired
    private TrackRepository trackRepo;
    @Autowired
    private ArticleRepository articleRepo;

    final RestTemplate rest = new RestTemplate();

    @Override
    public void run(final String... args) throws Exception {

        this.musicRepo.deleteAll();

        // this.getTop10Music();

        // this.musicRepo.deleteAll();
    }

    public void getTop10Music() {
        final String result = this.rest.getForObject("http://www.tongrenlu.info/fm/music",
                                                     String.class);

        System.out.println(result);

        final JSONObject resultJSON = JSONObject.fromObject(result);
        final JSONObject page = resultJSON.optJSONObject("page");
        final JSONArray items = page.optJSONArray("items");
        for (final Object next : items) {
            final JSONObject item = (JSONObject) next;

            final MusicDocument document = new MusicDocument();
            document.setId(item.optString("articleId"));
            document.setArticleId(item.optInt("articleId"));
            document.setTitle(item.optString("title"));
            document.setDescription(item.optString("description"));

            this.musicRepo.save(document);

            this.getMusicTrack(item.optString("articleId"));
        }
    }

    public void getMusicTrack(final String articleId) {
        final String result = this.rest.getForObject("http://www.tongrenlu.info/fm/music/" + articleId,
                                                     String.class);

        System.out.println(result);

        final JSONObject resultJSON = JSONObject.fromObject(result);
        final JSONObject articleBean = resultJSON.optJSONObject("articleBean");
        final JSONArray playlist = resultJSON.optJSONArray("playlist");
        for (final Object next : playlist) {
            final JSONObject item = (JSONObject) next;

            final TrackDocument document = new TrackDocument();
            document.setId(item.optString("fileId"));
            document.setArticleId(articleBean.optInt("articleId"));
            document.setTitle(articleBean.optString("title"));

            document.setFileId(item.optInt("fileId"));
            document.setArtist(StringUtils.split(item.optString("artist"), ","));
            document.setOriginal(StringUtils.split(item.optString("original"),
                                                   "\n"));

            this.trackRepo.save(document);

        }

    }
}
