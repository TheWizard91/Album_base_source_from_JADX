package retrofit2;

import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;

final class RequestBuilder {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final String PATH_SEGMENT_ALWAYS_ENCODE_SET = " \"<>^`{}|\\?#";
    private static final Pattern PATH_TRAVERSAL = Pattern.compile("(.*/)?(\\.|%2e|%2E){1,2}(/.*)?");
    private final HttpUrl baseUrl;
    @Nullable
    private RequestBody body;
    @Nullable
    private MediaType contentType;
    @Nullable
    private FormBody.Builder formBuilder;
    private final boolean hasBody;
    private final Headers.Builder headersBuilder;
    private final String method;
    @Nullable
    private MultipartBody.Builder multipartBuilder;
    @Nullable
    private String relativeUrl;
    private final Request.Builder requestBuilder = new Request.Builder();
    @Nullable
    private HttpUrl.Builder urlBuilder;

    RequestBuilder(String method2, HttpUrl baseUrl2, @Nullable String relativeUrl2, @Nullable Headers headers, @Nullable MediaType contentType2, boolean hasBody2, boolean isFormEncoded, boolean isMultipart) {
        this.method = method2;
        this.baseUrl = baseUrl2;
        this.relativeUrl = relativeUrl2;
        this.contentType = contentType2;
        this.hasBody = hasBody2;
        if (headers != null) {
            this.headersBuilder = headers.newBuilder();
        } else {
            this.headersBuilder = new Headers.Builder();
        }
        if (isFormEncoded) {
            this.formBuilder = new FormBody.Builder();
        } else if (isMultipart) {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            this.multipartBuilder = builder;
            builder.setType(MultipartBody.FORM);
        }
    }

    /* access modifiers changed from: package-private */
    public void setRelativeUrl(Object relativeUrl2) {
        this.relativeUrl = relativeUrl2.toString();
    }

    /* access modifiers changed from: package-private */
    public void addHeader(String name, String value) {
        if (HttpHeaders.CONTENT_TYPE.equalsIgnoreCase(name)) {
            try {
                this.contentType = MediaType.get(value);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Malformed content type: " + value, e);
            }
        } else {
            this.headersBuilder.add(name, value);
        }
    }

    /* access modifiers changed from: package-private */
    public void addHeaders(Headers headers) {
        this.headersBuilder.addAll(headers);
    }

    /* access modifiers changed from: package-private */
    public void addPathParam(String name, String value, boolean encoded) {
        if (this.relativeUrl != null) {
            String newRelativeUrl = this.relativeUrl.replace("{" + name + "}", canonicalizeForPath(value, encoded));
            if (!PATH_TRAVERSAL.matcher(newRelativeUrl).matches()) {
                this.relativeUrl = newRelativeUrl;
                return;
            }
            throw new IllegalArgumentException("@Path parameters shouldn't perform path traversal ('.' or '..'): " + value);
        }
        throw new AssertionError();
    }

    private static String canonicalizeForPath(String input, boolean alreadyEncoded) {
        int i = 0;
        int limit = input.length();
        while (i < limit) {
            int codePoint = input.codePointAt(i);
            if (codePoint < 32 || codePoint >= 127 || PATH_SEGMENT_ALWAYS_ENCODE_SET.indexOf(codePoint) != -1 || (!alreadyEncoded && (codePoint == 47 || codePoint == 37))) {
                Buffer out = new Buffer();
                out.writeUtf8(input, 0, i);
                canonicalizeForPath(out, input, i, limit, alreadyEncoded);
                return out.readUtf8();
            }
            i += Character.charCount(codePoint);
        }
        return input;
    }

    private static void canonicalizeForPath(Buffer out, String input, int pos, int limit, boolean alreadyEncoded) {
        Buffer utf8Buffer = null;
        int i = pos;
        while (i < limit) {
            int codePoint = input.codePointAt(i);
            if (!alreadyEncoded || !(codePoint == 9 || codePoint == 10 || codePoint == 12 || codePoint == 13)) {
                if (codePoint < 32 || codePoint >= 127 || PATH_SEGMENT_ALWAYS_ENCODE_SET.indexOf(codePoint) != -1 || (!alreadyEncoded && (codePoint == 47 || codePoint == 37))) {
                    if (utf8Buffer == null) {
                        utf8Buffer = new Buffer();
                    }
                    utf8Buffer.writeUtf8CodePoint(codePoint);
                    while (!utf8Buffer.exhausted()) {
                        int b = utf8Buffer.readByte() & 255;
                        out.writeByte(37);
                        char[] cArr = HEX_DIGITS;
                        out.writeByte((int) cArr[(b >> 4) & 15]);
                        out.writeByte((int) cArr[b & 15]);
                    }
                } else {
                    out.writeUtf8CodePoint(codePoint);
                }
            }
            i += Character.charCount(codePoint);
        }
    }

    /* access modifiers changed from: package-private */
    public void addQueryParam(String name, @Nullable String value, boolean encoded) {
        String str = this.relativeUrl;
        if (str != null) {
            HttpUrl.Builder newBuilder = this.baseUrl.newBuilder(str);
            this.urlBuilder = newBuilder;
            if (newBuilder != null) {
                this.relativeUrl = null;
            } else {
                throw new IllegalArgumentException("Malformed URL. Base: " + this.baseUrl + ", Relative: " + this.relativeUrl);
            }
        }
        if (encoded) {
            this.urlBuilder.addEncodedQueryParameter(name, value);
        } else {
            this.urlBuilder.addQueryParameter(name, value);
        }
    }

    /* access modifiers changed from: package-private */
    public void addFormField(String name, String value, boolean encoded) {
        if (encoded) {
            this.formBuilder.addEncoded(name, value);
        } else {
            this.formBuilder.add(name, value);
        }
    }

    /* access modifiers changed from: package-private */
    public void addPart(Headers headers, RequestBody body2) {
        this.multipartBuilder.addPart(headers, body2);
    }

    /* access modifiers changed from: package-private */
    public void addPart(MultipartBody.Part part) {
        this.multipartBuilder.addPart(part);
    }

    /* access modifiers changed from: package-private */
    public void setBody(RequestBody body2) {
        this.body = body2;
    }

    /* access modifiers changed from: package-private */
    public <T> void addTag(Class<T> cls, @Nullable T value) {
        this.requestBuilder.tag(cls, value);
    }

    /* access modifiers changed from: package-private */
    public Request.Builder get() {
        HttpUrl url;
        HttpUrl.Builder urlBuilder2 = this.urlBuilder;
        if (urlBuilder2 != null) {
            url = urlBuilder2.build();
        } else {
            url = this.baseUrl.resolve(this.relativeUrl);
            if (url == null) {
                throw new IllegalArgumentException("Malformed URL. Base: " + this.baseUrl + ", Relative: " + this.relativeUrl);
            }
        }
        RequestBody body2 = this.body;
        if (body2 == null) {
            FormBody.Builder builder = this.formBuilder;
            if (builder != null) {
                body2 = builder.build();
            } else {
                MultipartBody.Builder builder2 = this.multipartBuilder;
                if (builder2 != null) {
                    body2 = builder2.build();
                } else if (this.hasBody) {
                    body2 = RequestBody.create((MediaType) null, new byte[0]);
                }
            }
        }
        MediaType contentType2 = this.contentType;
        if (contentType2 != null) {
            if (body2 != null) {
                body2 = new ContentTypeOverridingRequestBody(body2, contentType2);
            } else {
                this.headersBuilder.add(HttpHeaders.CONTENT_TYPE, contentType2.toString());
            }
        }
        return this.requestBuilder.url(url).headers(this.headersBuilder.build()).method(this.method, body2);
    }

    private static class ContentTypeOverridingRequestBody extends RequestBody {
        private final MediaType contentType;
        private final RequestBody delegate;

        ContentTypeOverridingRequestBody(RequestBody delegate2, MediaType contentType2) {
            this.delegate = delegate2;
            this.contentType = contentType2;
        }

        public MediaType contentType() {
            return this.contentType;
        }

        public long contentLength() throws IOException {
            return this.delegate.contentLength();
        }

        public void writeTo(BufferedSink sink) throws IOException {
            this.delegate.writeTo(sink);
        }
    }
}
