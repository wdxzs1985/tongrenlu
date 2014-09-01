package info.tongrenlu.dummy;

import info.tongrenlu.entity.MusicEntity;
import info.tongrenlu.jdbc.MusicManager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MusicManagerDummy extends MusicManager {

    @Override
    public List<MusicEntity> findAll() {
        final List<MusicEntity> result = new ArrayList<MusicEntity>();

        for (int i = 1; i <= 2000; i++) {
            final MusicEntity article = new MusicEntity();
            article.setArticleId(DummyHelper.genMusicId(i));
            // article.setTitle("Article" + i);
            // article.setPublishFlg("1");
            // article.setPublishDate(new Date());
            // article.setUserId(prefix + userId);
            result.add(article);
        }

        return result;
    }
}
