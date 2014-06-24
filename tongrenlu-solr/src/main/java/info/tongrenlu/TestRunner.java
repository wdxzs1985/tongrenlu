package info.tongrenlu;

import info.tongrenlu.solr.MusicDocument;
import info.tongrenlu.solr.MusicRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestRunner implements CommandLineRunner {

    @Autowired
    private MusicRepository repository;

    @Override
    public void run(final String... args) throws Exception {

        this.repository.deleteAll();

        final MusicDocument document = new MusicDocument();
        document.setId("m" + 1);
        document.setArticleId(1);
        document.setTitle("[ESQUARIA] extreme");
        document.setDescription("百度分享：");
        document.setTags(new String[] { "ESQUARIA", "例大祭11" });

        this.repository.save(document);

        final MusicDocument document2 = this.repository.findOne("m" + 1);
        document2.setTitle("[ESQUARIA] extreme2");
        document2.setDescription("百度分享：2");

        this.repository.save(document2);

        // fetch all
        System.out.println("findAll():");
        System.out.println("----------------------------");
        for (final MusicDocument musicDocument : this.repository.findAll()) {
            System.out.println(musicDocument);
        }
        System.out.println();
    }
}
