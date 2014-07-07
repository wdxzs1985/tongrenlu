package info.tongrenlu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Launcher implements CommandLineRunner {

    @Autowired
    private TransferService jdbcTransfer = null;
    @Autowired
    private SolrTransfer solrTransfer = null;
    @Autowired
    private DummyTransfer dummyTransfer = null;

    @Override
    public void run(final String... arg0) throws Exception {
        this.jdbcTransfer.doTransfer();

        // this.solrTransfer.doTransfer();

        // this.dummyTransfer.doTransfer();
    }
}
