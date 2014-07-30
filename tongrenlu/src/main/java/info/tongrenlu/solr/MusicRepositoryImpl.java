package info.tongrenlu.solr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleFilterQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MusicRepositoryImpl extends SimpleSolrRepository<MusicDocument, String> implements MusicRepository {

    @Autowired
    public MusicRepositoryImpl(final SolrTemplate solrTemplate) {
        super(solrTemplate);
    }

    @Override
    public Page<MusicDocument> findMusic(final List<String> queries,
                                         final Pageable pageable) {

        final Query query = new SimpleQuery();
        query.setPageRequest(pageable);
        query.addFilterQuery(new SimpleFilterQuery(new Criteria("category").is("music")));
        for (final String queryString : queries) {
            query.addCriteria(new Criteria("xtag_ja").is(queryString)
                                                     .or(new Criteria("xtag_en").is(queryString))
                                                     .or(new Criteria("xtag_en").contains(queryString))
                                                     .or(new Criteria("xtag_zh").is(queryString)));
        }
        return this.getSolrOperations().queryForPage(query,
                                                     this.getEntityClass());
    }
}
