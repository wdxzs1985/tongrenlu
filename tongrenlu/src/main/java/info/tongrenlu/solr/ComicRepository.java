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

//@Repository
public class ComicRepository
        extends SimpleSolrRepository<ComicDocument, String> {

    @Autowired
    public ComicRepository(
                           final SolrTemplate solrTemplate) {
        super(solrTemplate);
    }

    public Page<ComicDocument> findComic(
                                         final List<String> queries,
                                         final Pageable pageable) {

        final Query query = new SimpleQuery();
        query.setPageRequest(pageable);
        query.addFilterQuery(new SimpleFilterQuery(new Criteria("category").is("comic")));
        query.addCriteria(new Criteria("xtag_ja").is(queries).or(new Criteria("xtag_ja").contains(queries)));

        return this.getSolrOperations().queryForPage(query,
                                                     this.getEntityClass());
    }
}
