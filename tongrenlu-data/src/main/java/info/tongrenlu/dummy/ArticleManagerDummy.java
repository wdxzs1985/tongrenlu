package info.tongrenlu.dummy;

import info.tongrenlu.entity.ArticleEntity;
import info.tongrenlu.jdbc.ArticleManager;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ArticleManagerDummy extends ArticleManager {

    @Override
    public ArticleEntity findByArticleId(final String articleId) {
        final ArticleEntity article = new ArticleEntity();
        article.setArticleId(articleId);
        article.setUserId(DummyHelper.randomUserId());
        article.setTitle("Article" + articleId);
        article.setPublishFlg("1");
        article.setPublishDate(new Date());
        article.setRecommend("0");
        return article;
    }
}
