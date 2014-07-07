package info.tongrenlu.dummy;

import info.tongrenlu.entity.ComicEntity;
import info.tongrenlu.jdbc.ComicManager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ComicManagerDummy extends ComicManager {

    @Override
    public List<ComicEntity> findAll() {
        final List<ComicEntity> result = new ArrayList<ComicEntity>();

        for (int i = 1; i <= 100; i++) {
            final ComicEntity article = new ComicEntity();
            article.setArticleId(DummyHelper.genComicId(i));
            article.setRedFlg("0");
            article.setTranslateFlg("0");
            result.add(article);
        }

        return result;
    }
}
