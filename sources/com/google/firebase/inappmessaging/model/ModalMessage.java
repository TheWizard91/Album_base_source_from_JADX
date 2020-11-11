package com.google.firebase.inappmessaging.model;

import android.text.TextUtils;
import java.util.Map;
import javax.annotation.Nullable;

public class ModalMessage extends InAppMessage {
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
        int imageHash = 0;
        int bodyHash = text != null ? text.hashCode() : 0;
        Action action2 = this.action;
        int actionHash = action2 != null ? action2.hashCode() : 0;
        ImageData imageData2 = this.imageData;
        if (imageData2 != null) {
            imageHash = imageData2.hashCode();
        }
        return this.title.hashCode() + bodyHash + this.backgroundHexColor.hashCode() + actionHash + imageHash;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ModalMessage)) {
            return false;
        }
        ModalMessage m = (ModalMessage) o;
        if (hashCode() != m.hashCode()) {
            return false;
        }
        Text text = this.body;
        if ((text == null && m.body != null) || (text != null && !text.equals(m.body))) {
            return false;
        }
        Action action2 = this.action;
        if ((action2 == null && m.action != null) || (action2 != null && !action2.equals(m.action))) {
            return false;
        }
        ImageData imageData2 = this.imageData;
        if ((imageData2 != null || m.imageData == null) && ((imageData2 == null || imageData2.equals(m.imageData)) && this.title.equals(m.title) && this.backgroundHexColor.equals(m.backgroundHexColor))) {
            return true;
        }
        return false;
    }

    private ModalMessage(CampaignMetadata campaignMetadata, Text title2, @Nullable Text body2, @Nullable ImageData imageData2, @Nullable Action action2, String backgroundHexColor2, @Nullable Map<String, String> data) {
        super(campaignMetadata, MessageType.MODAL, data);
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

    public String getBackgroundHexColor() {
        return this.backgroundHexColor;
    }

    @Nullable
    public Action getAction() {
        return this.action;
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

        public ModalMessage build(CampaignMetadata campaignMetadata, @Nullable Map<String, String> data) {
            if (this.title != null) {
                Action action2 = this.action;
                if (action2 != null && action2.getButton() == null) {
                    throw new IllegalArgumentException("Modal model action must be null or have a button");
                } else if (!TextUtils.isEmpty(this.backgroundHexColor)) {
                    return new ModalMessage(campaignMetadata, this.title, this.body, this.imageData, this.action, this.backgroundHexColor, data);
                } else {
                    throw new IllegalArgumentException("Modal model must have a background color");
                }
            } else {
                throw new IllegalArgumentException("Modal model must have a title");
            }
        }
    }
}
