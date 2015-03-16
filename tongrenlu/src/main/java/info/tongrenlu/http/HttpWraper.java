package info.tongrenlu.http;

import java.io.IOException;
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
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.util.EntityUtils;

public class HttpWraper {

    private final Log log = LogFactory.getLog(this.getClass());

    private HttpClient client;

    private BasicCookieStore cookieStore;

    private String encoding;

    public HttpResponse get(final String url) {
        if (this.log.isInfoEnabled()) {
            this.log.info(String.format("[%4s] %s", "GET", url));
        }
        final HttpGet httpget = new HttpGet(url);

        // final HttpClientContext localContext = new HttpClientContext();
        // // Bind custom cookie store to the local context
        // localContext.setCookieStore(this.getCookieStore());
        try {
            // Pass local context as a parameter
            final HttpResponse response = this.getClient().execute(httpget);
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
        // final HttpClientContext localContext = new HttpClientContext();
        // // Bind custom cookie store to the local context
        // localContext.setCookieStore(this.getCookieStore());
        try {
            final UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(nvps, this.getEncoding());
            httppost.setEntity(postEntity);
            return this.getClient().execute(httppost);
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
            result = EntityUtils.toString(entity, this.getEncoding());
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

    public HttpClient getClient() {
        return this.client;
    }

    public void setClient(final HttpClient client) {
        this.client = client;
    }

    public BasicCookieStore getCookieStore() {
        return this.cookieStore;
    }

    public void setCookieStore(final BasicCookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }

}
