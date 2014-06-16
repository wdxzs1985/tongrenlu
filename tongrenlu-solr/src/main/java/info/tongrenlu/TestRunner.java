package info.tongrenlu;

import info.tongrenlu.solr.ArticleDocument;
import info.tongrenlu.solr.ArticleRepository;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class TestRunner implements CommandLineRunner {

    @Autowired
    private ArticleRepository repository;

    @Override
    public void run(final String... args) throws Exception {

        this.repository.deleteAll();

        final ArticleDocument articleBean = new ArticleDocument();
        articleBean.setId("201406101261392");
        articleBean.setArticleId("201406101261392");
        articleBean.setTitle("[1569＋EX永遠亭] 千終樂 -SIDE BADEND-");
        articleBean.setCircleList(Arrays.asList("1569", "EX永遠亭"));
        articleBean.setRelease(2014);
        articleBean.setEvent("例大祭11");
        articleBean.setTagList(Arrays.asList("1569", "EX永遠亭", "例大祭11"));
        // insert some products
        this.repository.save(articleBean);

        articleBean.setId("201406071259178");
        articleBean.setArticleId("201406071259178");
        articleBean.setTitle("[子猫奪回屋] Mind games ～幻想心遊塔～");
        articleBean.setCircleList(Arrays.asList("子猫奪回屋"));
        articleBean.setEvent("例大祭11");
        articleBean.setRelease(2014);
        articleBean.setTagList(Arrays.asList("子猫奪回屋", "例大祭11"));
        // insert some products
        this.repository.save(articleBean);

        // fetch all
        System.out.println("findAll():");
        System.out.println("----------------------------");
        for (final ArticleDocument article : this.repository.findAll()) {
            System.out.println(article);
        }
        System.out.println();
    }

}
