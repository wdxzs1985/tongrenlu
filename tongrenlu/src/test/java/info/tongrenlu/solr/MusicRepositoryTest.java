package info.tongrenlu.solr;

import info.tongrenlu.Application;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class MusicRepositoryTest {

    @Autowired
    private MusicRepository musicRepository;

    private Log log = LogFactory.getLog(this.getClass());

    @Test
    public void testFindMusic() {
        final String query = "東方夢時空／Selection";
        final List<String> queries = new ArrayList<>();
        for (final String text : StringUtils.split(query)) {
            queries.add(ClientUtils.escapeQueryChars(text));
        }

        final Page<MusicDocument> searchResult = this.musicRepository.findMusic(queries,
                                                                                new PageRequest(1,
                                                                                                10));

        for (final MusicDocument musicDocument : searchResult) {
            this.log.debug(musicDocument);
        }

        Assert.assertTrue(searchResult.hasContent());
    }

}
