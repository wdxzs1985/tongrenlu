package info.tongrenlu;

import info.tongrenlu.solr.ArticleDocument;
import info.tongrenlu.solr.ArticleRepository;
import info.tongrenlu.solr.MusicRepository;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestRunner implements CommandLineRunner {

    @Autowired
    private ArticleRepository articleRepo;
    @Autowired
    private MusicRepository musicRepo;

    @Override
    public void run(final String... args) throws Exception {

        // this.musicRepo.deleteAll();

        // this.getTop10Music();

        // this.musicRepo.deleteAll();

        final ArticleDocument document = this.articleRepo.findOne("m1311");
        System.out.println(document);
        this.articleRepo.save(document);

        System.out.println("----- findByXTag");
        final List<String> queryList = Arrays.asList(StringUtils.split("Diverse System"));
        for (final ArticleDocument music : this.musicRepo.findByXTag(queryList)) {
            System.out.println(music);
        }

    }

}
