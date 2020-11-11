package com.facebook.imagepipeline.producers;

import android.net.Uri;
import com.facebook.common.time.MonotonicClock;
import com.facebook.common.time.RealtimeSinceBootClock;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.annotation.Nullable;

public class HttpUrlConnectionNetworkFetcher extends BaseNetworkFetcher<HttpUrlConnectionNetworkFetchState> {
    private static final String FETCH_TIME = "fetch_time";
    public static final int HTTP_DEFAULT_TIMEOUT = 30000;
    public static final int HTTP_PERMANENT_REDIRECT = 308;
    public static final int HTTP_TEMPORARY_REDIRECT = 307;
    private static final String IMAGE_SIZE = "image_size";
    private static final int MAX_REDIRECTS = 5;
    private static final int NUM_NETWORK_THREADS = 3;
    private static final String QUEUE_TIME = "queue_time";
    private static final String TOTAL_TIME = "total_time";
    private final ExecutorService mExecutorService;
    private int mHttpConnectionTimeout;
    private final MonotonicClock mMonotonicClock;
    @Nullable
    private String mUserAgent;

    public static class HttpUrlConnectionNetworkFetchState extends FetchState {
        /* access modifiers changed from: private */
        public long fetchCompleteTime;
        /* access modifiers changed from: private */
        public long responseTime;
        /* access modifiers changed from: private */
        public long submitTime;

        public HttpUrlConnectionNetworkFetchState(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
            super(consumer, producerContext);
        }
    }

    public HttpUrlConnectionNetworkFetcher() {
        this((String) null, (MonotonicClock) RealtimeSinceBootClock.get());
    }

    public HttpUrlConnectionNetworkFetcher(int httpConnectionTimeout) {
        this((String) null, (MonotonicClock) RealtimeSinceBootClock.get());
        this.mHttpConnectionTimeout = httpConnectionTimeout;
    }

    public HttpUrlConnectionNetworkFetcher(String userAgent, int httpConnectionTimeout) {
        this(userAgent, (MonotonicClock) RealtimeSinceBootClock.get());
        this.mHttpConnectionTimeout = httpConnectionTimeout;
    }

    HttpUrlConnectionNetworkFetcher(@Nullable String userAgent, MonotonicClock monotonicClock) {
        this.mExecutorService = Executors.newFixedThreadPool(3);
        this.mMonotonicClock = monotonicClock;
        this.mUserAgent = userAgent;
    }

    public HttpUrlConnectionNetworkFetchState createFetchState(Consumer<EncodedImage> consumer, ProducerContext context) {
        return new HttpUrlConnectionNetworkFetchState(consumer, context);
    }

    public void fetch(final HttpUrlConnectionNetworkFetchState fetchState, final NetworkFetcher.Callback callback) {
        long unused = fetchState.submitTime = this.mMonotonicClock.now();
        final Future<?> future = this.mExecutorService.submit(new Runnable() {
            public void run() {
                HttpUrlConnectionNetworkFetcher.this.fetchSync(fetchState, callback);
            }
        });
        fetchState.getContext().addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
                if (future.cancel(false)) {
                    callback.onCancellation();
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void fetchSync(HttpUrlConnectionNetworkFetchState fetchState, NetworkFetcher.Callback callback) {
        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            connection = downloadFrom(fetchState.getUri(), 5);
            long unused = fetchState.responseTime = this.mMonotonicClock.now();
            if (connection != null) {
                is = connection.getInputStream();
                callback.onResponse(is, -1);
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (connection == null) {
                return;
            }
        } catch (IOException e2) {
            callback.onFailure(e2);
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e3) {
                }
            }
            if (connection == null) {
                return;
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e4) {
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
            throw th;
        }
        connection.disconnect();
    }

    private HttpURLConnection downloadFrom(Uri uri, int maxRedirects) throws IOException {
        String message;
        HttpURLConnection connection = openConnectionTo(uri);
        String str = this.mUserAgent;
        if (str != null) {
            connection.setRequestProperty(HttpHeaders.USER_AGENT, str);
        }
        connection.setConnectTimeout(this.mHttpConnectionTimeout);
        int responseCode = connection.getResponseCode();
        if (isHttpSuccess(responseCode)) {
            return connection;
        }
        if (isHttpRedirect(responseCode)) {
            String nextUriString = connection.getHeaderField(HttpHeaders.LOCATION);
            connection.disconnect();
            Uri nextUri = nextUriString == null ? null : Uri.parse(nextUriString);
            String originalScheme = uri.getScheme();
            if (maxRedirects > 0 && nextUri != null && !nextUri.getScheme().equals(originalScheme)) {
                return downloadFrom(nextUri, maxRedirects - 1);
            }
            if (maxRedirects == 0) {
                message = error("URL %s follows too many redirects", uri.toString());
            } else {
                message = error("URL %s returned %d without a valid redirect", uri.toString(), Integer.valueOf(responseCode));
            }
            throw new IOException(message);
        }
        connection.disconnect();
        throw new IOException(String.format("Image URL %s returned HTTP code %d", new Object[]{uri.toString(), Integer.valueOf(responseCode)}));
    }

    static HttpURLConnection openConnectionTo(Uri uri) throws IOException {
        return (HttpURLConnection) UriUtil.uriToUrl(uri).openConnection();
    }

    public void onFetchCompletion(HttpUrlConnectionNetworkFetchState fetchState, int byteSize) {
        long unused = fetchState.fetchCompleteTime = this.mMonotonicClock.now();
    }

    private static boolean isHttpSuccess(int responseCode) {
        return responseCode >= 200 && responseCode < 300;
    }

    private static boolean isHttpRedirect(int responseCode) {
        if (responseCode == 307 || responseCode == 308) {
            return true;
        }
        switch (responseCode) {
            case 300:
            case 301:
            case 302:
            case 303:
                return true;
            default:
                return false;
        }
    }

    private static String error(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }

    public Map<String, String> getExtraMap(HttpUrlConnectionNetworkFetchState fetchState, int byteSize) {
        Map<String, String> extraMap = new HashMap<>(4);
        extraMap.put(QUEUE_TIME, Long.toString(fetchState.responseTime - fetchState.submitTime));
        extraMap.put(FETCH_TIME, Long.toString(fetchState.fetchCompleteTime - fetchState.responseTime));
        extraMap.put(TOTAL_TIME, Long.toString(fetchState.fetchCompleteTime - fetchState.submitTime));
        extraMap.put(IMAGE_SIZE, Integer.toString(byteSize));
        return extraMap;
    }
}
