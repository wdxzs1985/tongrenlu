package info.tongrenlu.dummy;

import info.tongrenlu.entity.ArticleTagEntity;
import info.tongrenlu.jdbc.ArticleTagManager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ArticleTagManagerDummy extends ArticleTagManager {

    @Override
    public List<ArticleTagEntity> findByArticleId(final String articleId) {
        final List<ArticleTagEntity> result = new ArrayList<ArticleTagEntity>();
        for (int i = 1; i < 10; i++) {
            final ArticleTagEntity article = new ArticleTagEntity();
            article.setArticleId(articleId);
            article.setTagId(DummyHelper.randomTagId());
            result.add(article);
        }
        return result;
    }
}
