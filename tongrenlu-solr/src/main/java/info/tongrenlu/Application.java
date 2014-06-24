package info.tongrenlu;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    static final String SOLR_HOST = "solr.host";

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Resource
    private Environment environment;

    @Bean
    public SolrServer solrServer() {
        final String solrHost = this.environment.getRequiredProperty(Application.SOLR_HOST);
        return new HttpSolrServer(solrHost);
    }

}
