package info.tongrenlu;

import info.tongrenlu.http.HttpWraper;

import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    @Value("${http.client.maxTotalConnection:200}")
    private Integer maxTotalConnection;

    @Value("${http.client.defaultMaxPerRoute:20}")
    private Integer defaultMaxPerRoute;

    @Value("${http.client.userAgent}")
    private String userAgent = null;

    @Value("${http.client.timeout:20000}")
    private Integer timeout;

    @Value("${http.client.encoding:utf-8}")
    private String defaultEncoding;

    @Value("${http.client.toranoana.encoding:shift_jis}")
    private String toranoanaEncoding;

    private final Log log = LogFactory.getLog(this.getClass());

    @Bean
    public HttpWraper toranoanaClient() {
        final HttpWraper wraper = this.httpWraper();
        wraper.setEncoding(this.toranoanaEncoding);

        final BasicClientCookie cookie = new BasicClientCookie("afg", "0");
        cookie.setDomain("toranoana.jp");
        cookie.setPath("/");

        final Calendar expiryDate = Calendar.getInstance();
        expiryDate.add(Calendar.YEAR, 99);
        cookie.setExpiryDate(expiryDate.getTime());

        wraper.getCookieStore().addCookie(cookie);
        return wraper;
    }

    @Bean
    public HttpWraper melonbooksClient() {
        final HttpWraper wraper = this.httpWraper();
        return wraper;
    }

    public HttpWraper httpWraper() {
        final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 200
        connManager.setMaxTotal(this.maxTotalConnection);
        // Increase default max connection per route to 20
        connManager.setDefaultMaxPerRoute(this.defaultMaxPerRoute);

        final BasicCookieStore cookieStroe = new BasicCookieStore();
        final RequestConfig defaultRequestConfig = RequestConfig.custom()
                                                                .setSocketTimeout(this.timeout)
                                                                .setConnectTimeout(this.timeout)
                                                                .setConnectionRequestTimeout(this.timeout)
                                                                .setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
                                                                .build();

        final HttpClientBuilder clientBuilder = HttpClients.custom();
        clientBuilder.setConnectionManager(connManager);
        clientBuilder.setDefaultCookieStore(cookieStroe);
        clientBuilder.setDefaultRequestConfig(defaultRequestConfig);
        clientBuilder.setUserAgent(this.userAgent);
        clientBuilder.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE);
        final Collection<Header> defaultHeaders = new LinkedList<Header>();
        defaultHeaders.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));
        defaultHeaders.add(new BasicHeader("Accept-Language", "ja"));
        defaultHeaders.add(new BasicHeader("Cache-Control", "no-cache"));
        defaultHeaders.add(new BasicHeader("Connection", "keep-alive"));
        defaultHeaders.add(new BasicHeader("Pragma", "no-cache"));
        defaultHeaders.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
        clientBuilder.setDefaultHeaders(defaultHeaders);

        final HttpWraper wraper = new HttpWraper();
        wraper.setCookieStore(cookieStroe);
        wraper.setClient(clientBuilder.build());
        wraper.setEncoding(this.defaultEncoding);
        return wraper;
    }

}
