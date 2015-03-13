package info.tongrenlu;

import info.tongrenlu.constants.CommonConstants;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
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

    @Value("${http.client.toranoana.encoding:shift_jis}")
    private String encodingForToranoana;

    @Bean
    public HttpClientWraper httpClientForToranoana() {
        final HttpClientWraper wraper = this.commonHttpClient();
        wraper.encoding = this.encodingForToranoana;

        final BasicClientCookie cookie = new BasicClientCookie("afg", "0");
        cookie.setDomain("toranoana.jp");
        cookie.setPath("/");
        cookie.setExpiryDate(new Date(System.currentTimeMillis() + CommonConstants.MONTH * 12));
        wraper.cookieStore.addCookie(cookie);
        return wraper;
    }

    public HttpClientWraper commonHttpClient() {
        final HttpClientWraper wraper = new HttpClientWraper();
        final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 200
        connManager.setMaxTotal(this.maxTotalConnection);
        // Increase default max connection per route to 20
        connManager.setDefaultMaxPerRoute(this.defaultMaxPerRoute);

        wraper.cookieStore = new BasicCookieStore();

        final RequestConfig defaultRequestConfig = RequestConfig.custom()
                                                                .setSocketTimeout(this.timeout)
                                                                .setConnectTimeout(this.timeout)
                                                                .setConnectionRequestTimeout(this.timeout)
                                                                .setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
                                                                .build();

        final HttpClientBuilder clientBuilder = HttpClients.custom();
        clientBuilder.setConnectionManager(connManager);
        clientBuilder.setDefaultCookieStore(wraper.cookieStore);
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

        wraper.client = clientBuilder.build();
        return wraper;
    }

    public class HttpClientWraper {

        private final Log log = LogFactory.getLog(this.getClass());

        private HttpClient client;

        private BasicCookieStore cookieStore;

        private String encoding;

        public HttpResponse get(final String url) {
            if (this.log.isInfoEnabled()) {
                this.log.info(String.format("[%4s] %s", "GET", url));
            }
            final HttpGet httpget = new HttpGet(url);

            final HttpClientContext localContext = new HttpClientContext();
            // Bind custom cookie store to the local context
            localContext.setCookieStore(this.cookieStore);
            try {
                // Pass local context as a parameter
                final HttpResponse response = this.client.execute(httpget, localContext);
                return response;
            } catch (final ClientProtocolException e) {
                throw new RuntimeException(e);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }

        public HttpResponse post(final String url, final List<? extends NameValuePair> nvps) {
            if (this.log.isInfoEnabled()) {
                this.log.info(String.format("[%4s] %s", "POST", url));
                for (final NameValuePair nvp : nvps) {
                    this.log.info(String.format("%s=%s", nvp.getName(), nvp.getValue()));
                }
            }
            final HttpPost httppost = new HttpPost(url);
            final HttpClientContext localContext = new HttpClientContext();
            // Bind custom cookie store to the local context
            localContext.setCookieStore(this.cookieStore);
            try {
                final UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(nvps, this.encoding);
                httppost.setEntity(postEntity);
                return this.client.execute(httppost, localContext);
            } catch (final ClientProtocolException e) {
                throw new RuntimeException("网络故障", e);
            } catch (final IOException e) {
                throw new RuntimeException("网络故障", e);
            }
        }

        public String getForHtml(final String url) {
            // Pass local context as a parameter
            final HttpResponse response = this.get(url);
            final HttpEntity entity = response.getEntity();
            String result = null;
            try {
                result = this.entityToString(entity);
                if (this.log.isDebugEnabled()) {
                    this.log.debug(result);
                }
                return result;
            } catch (final ParseException e) {
                throw new RuntimeException(e);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            } finally {
                EntityUtils.consumeQuietly(entity);
            }
        }

        public String postForHtml(final String url, final List<? extends NameValuePair> nvps) {
            final HttpResponse response = this.post(url, nvps);
            final HttpEntity entity = response.getEntity();
            String result;
            try {
                result = this.entityToString(entity);
                if (this.log.isDebugEnabled()) {
                    this.log.debug(result);
                }
                return result;
            } catch (final ParseException e) {
                throw new RuntimeException(e);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            } finally {
                EntityUtils.consumeQuietly(entity);
            }
        }

        protected String entityToString(final HttpEntity entity) throws ParseException, IOException {
            String result = null;
            if (this.isGzip(entity)) {
                result = EntityUtils.toString(new GzipDecompressingEntity(entity));
            } else {
                result = EntityUtils.toString(entity, this.encoding);
            }
            return result;
        }

        protected boolean isGzip(final HttpEntity entity) {
            final Header contentEncodingHeader = entity.getContentEncoding();
            if (contentEncodingHeader != null) {
                final String contentEncoding = contentEncodingHeader.getValue();
                if (StringUtils.equalsIgnoreCase(contentEncoding, "gzip")) {
                    return true;
                }
            }
            return false;
        }
    }
}
