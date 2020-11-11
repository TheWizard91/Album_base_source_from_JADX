package androidx.navigation;

import android.content.Intent;
import android.net.Uri;
import java.util.regex.Pattern;

public class NavDeepLinkRequest {
    private final String mAction;
    private final String mMimeType;
    private final Uri mUri;

    NavDeepLinkRequest(Intent intent) {
        this(intent.getData(), intent.getAction(), intent.getType());
    }

    NavDeepLinkRequest(Uri uri, String action, String mimeType) {
        this.mUri = uri;
        this.mAction = action;
        this.mMimeType = mimeType;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public String getAction() {
        return this.mAction;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NavDeepLinkRequest");
        sb.append("{");
        if (this.mUri != null) {
            sb.append(" uri=");
            sb.append(this.mUri.toString());
        }
        if (this.mAction != null) {
            sb.append(" action=");
            sb.append(this.mAction);
        }
        if (this.mMimeType != null) {
            sb.append(" mimetype=");
            sb.append(this.mMimeType);
        }
        sb.append(" }");
        return sb.toString();
    }

    public static final class Builder {
        private String mAction;
        private String mMimeType;
        private Uri mUri;

        private Builder() {
        }

        public static Builder fromUri(Uri uri) {
            Builder builder = new Builder();
            builder.setUri(uri);
            return builder;
        }

        public static Builder fromAction(String action) {
            if (!action.isEmpty()) {
                Builder builder = new Builder();
                builder.setAction(action);
                return builder;
            }
            throw new IllegalArgumentException("The NavDeepLinkRequest cannot have an empty action.");
        }

        public static Builder fromMimeType(String mimeType) {
            Builder builder = new Builder();
            builder.setMimeType(mimeType);
            return builder;
        }

        public Builder setUri(Uri uri) {
            this.mUri = uri;
            return this;
        }

        public Builder setAction(String action) {
            if (!action.isEmpty()) {
                this.mAction = action;
                return this;
            }
            throw new IllegalArgumentException("The NavDeepLinkRequest cannot have an empty action.");
        }

        public Builder setMimeType(String mimeType) {
            if (Pattern.compile("^[-\\w*.]+/[-\\w+*.]+$").matcher(mimeType).matches()) {
                this.mMimeType = mimeType;
                return this;
            }
            throw new IllegalArgumentException("The given mimeType " + mimeType + " does not match to required \"type/subtype\" format");
        }

        public NavDeepLinkRequest build() {
            return new NavDeepLinkRequest(this.mUri, this.mAction, this.mMimeType);
        }
    }
}
