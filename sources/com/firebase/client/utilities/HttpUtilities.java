package com.firebase.client.utilities;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtilities {

    public enum HttpRequestType {
        GET,
        POST,
        DELETE,
        PUT
    }

    public static URI buildUrl(String server, String path, Map<String, String> params) {
        try {
            URI serverURI = new URI(server);
            URI uri = new URI(serverURI.getScheme(), serverURI.getAuthority(), path, (String) null, (String) null);
            String query = null;
            if (params != null) {
                StringBuilder queryBuilder = new StringBuilder();
                boolean first = true;
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (!first) {
                        queryBuilder.append("&");
                    }
                    first = false;
                    queryBuilder.append(URLEncoder.encode(entry.getKey(), "utf-8"));
                    queryBuilder.append("=");
                    queryBuilder.append(URLEncoder.encode(entry.getValue(), "utf-8"));
                }
                query = queryBuilder.toString();
            }
            if (query != null) {
                return new URI(uri.toASCIIString() + "?" + query);
            }
            return uri;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Couldn't build valid auth URI.", e);
        } catch (URISyntaxException e2) {
            throw new RuntimeException("Couldn't build valid auth URI.", e2);
        }
    }

    private static void addMethodParams(HttpEntityEnclosingRequestBase request, Map<String, String> params) {
        if (params != null) {
            List<NameValuePair> postParams = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            try {
                request.setEntity(new UrlEncodedFormEntity(postParams, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Didn't find utf-8 encoding", e);
            }
        }
    }

    /* renamed from: com.firebase.client.utilities.HttpUtilities$1 */
    static /* synthetic */ class C10181 {

        /* renamed from: $SwitchMap$com$firebase$client$utilities$HttpUtilities$HttpRequestType */
        static final /* synthetic */ int[] f99xaa443286;

        static {
            int[] iArr = new int[HttpRequestType.values().length];
            f99xaa443286 = iArr;
            try {
                iArr[HttpRequestType.GET.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f99xaa443286[HttpRequestType.DELETE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f99xaa443286[HttpRequestType.POST.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f99xaa443286[HttpRequestType.PUT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static HttpUriRequest requestWithType(String server, String path, HttpRequestType type, Map<String, String> urlParams, Map<String, String> requestParams) {
        int i = C10181.f99xaa443286[type.ordinal()];
        if (i == 1 || i == 2) {
            urlParams = new HashMap<>(urlParams);
            urlParams.putAll(requestParams);
        }
        if (type == HttpRequestType.DELETE) {
            urlParams.put("_method", "delete");
        }
        URI url = buildUrl(server, path, urlParams);
        int i2 = C10181.f99xaa443286[type.ordinal()];
        if (i2 == 1) {
            return new HttpGet(url);
        }
        if (i2 == 2) {
            return new HttpDelete(url);
        }
        if (i2 == 3) {
            HttpPost post = new HttpPost(url);
            if (requestParams != null) {
                addMethodParams(post, requestParams);
            }
            return post;
        } else if (i2 == 4) {
            HttpPut put = new HttpPut(url);
            if (requestParams != null) {
                addMethodParams(put, requestParams);
            }
            return put;
        } else {
            throw new IllegalStateException("Shouldn't reach here!");
        }
    }
}
