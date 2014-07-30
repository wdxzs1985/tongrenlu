package info.tongrenlu;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;

@Configuration
public class SolrContext {

    static final String SOLR_HOST = "solr.host";

    @Resource
    private Environment environment;

    @Bean
    public SolrServer solrServer() {
        final String solrHost = this.environment.getRequiredProperty(SolrContext.SOLR_HOST);
        return new HttpSolrServer(solrHost);
    }

    @Bean
    public SolrTemplate solrTemplate() {
        return new SolrTemplate(this.solrServer(), "collection3");
    }
}
