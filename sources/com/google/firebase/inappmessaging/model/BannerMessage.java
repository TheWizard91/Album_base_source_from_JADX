package com.google.firebase.inappmessaging.model;

import android.text.TextUtils;
import java.util.Map;
import javax.annotation.Nullable;

public class BannerMessage extends InAppMessage {
    @Nullable
    private final Action action;
    private final String backgroundHexColor;
    @Nullable
    private final Text body;
    @Nullable
    private final ImageData imageData;
    private final Text title;

    public int hashCode() {
        Text text = this.body;
        int actionHash = 0;
        int bodyHash = text != null ? text.hashCode() : 0;
        ImageData imageData2 = this.imageData;
        int imageHash = imageData2 != null ? imageData2.hashCode() : 0;
        Action action2 = this.action;
        if (action2 != null) {
            actionHash = action2.hashCode();
        }
        return this.title.hashCode() + bodyHash + imageHash + actionHash + this.backgroundHexColor.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BannerMessage)) {
            return false;
        }
        BannerMessage b = (BannerMessage) o;
        if (hashCode() != b.hashCode()) {
            return false;
        }
        Text text = this.body;
        if ((text == null && b.body != null) || (text != null && !text.equals(b.body))) {
            return false;
        }
        ImageData imageData2 = this.imageData;
        if ((imageData2 == null && b.imageData != null) || (imageData2 != null && !imageData2.equals(b.imageData))) {
            return false;
        }
        Action action2 = this.action;
        if ((action2 != null || b.action == null) && ((action2 == null || action2.equals(b.action)) && this.title.equals(b.title) && this.backgroundHexColor.equals(b.backgroundHexColor))) {
            return true;
        }
        return false;
    }

    private BannerMessage(CampaignMetadata campaignMetadata, Text title2, @Nullable Text body2, @Nullable ImageData imageData2, @Nullable Action action2, String backgroundHexColor2, @Nullable Map<String, String> data) {
        super(campaignMetadata, MessageType.BANNER, data);
        this.title = title2;
        this.body = body2;
        this.imageData = imageData2;
        this.action = action2;
        this.backgroundHexColor = backgroundHexColor2;
    }

    public Text getTitle() {
        return this.title;
    }

    @Nullable
    public Text getBody() {
        return this.body;
    }

    @Nullable
    public ImageData getImageData() {
        return this.imageData;
    }

    @Nullable
    public Action getAction() {
        return this.action;
    }

    public String getBackgroundHexColor() {
        return this.backgroundHexColor;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        @Nullable
        Action action;
        @Nullable
        String backgroundHexColor;
        @Nullable
        Text body;
        @Nullable
        ImageData imageData;
        @Nullable
        Text title;

        public Builder setTitle(@Nullable Text title2) {
            this.title = title2;
            return this;
        }

        public Builder setBody(@Nullable Text body2) {
            this.body = body2;
            return this;
        }

        public Builder setImageData(@Nullable ImageData imageData2) {
            this.imageData = imageData2;
            return this;
        }

        public Builder setAction(@Nullable Action action2) {
            this.action = action2;
            return this;
        }

        public Builder setBackgroundHexColor(@Nullable String backgroundHexColor2) {
            this.backgroundHexColor = backgroundHexColor2;
            return this;
        }

        public BannerMessage build(CampaignMetadata campaignMetadata, @Nullable Map<String, String> data) {
            if (this.title == null) {
                throw new IllegalArgumentException("Banner model must have a title");
            } else if (!TextUtils.isEmpty(this.backgroundHexColor)) {
                return new BannerMessage(campaignMetadata, this.title, this.body, this.imageData, this.action, this.backgroundHexColor, data);
            } else {
                throw new IllegalArgumentException("Banner model must have a background color");
            }
        }
    }
}
