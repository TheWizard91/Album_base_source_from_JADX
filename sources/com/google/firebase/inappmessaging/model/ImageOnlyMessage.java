package com.google.firebase.inappmessaging.model;

import java.util.Map;
import javax.annotation.Nullable;

public class ImageOnlyMessage extends InAppMessage {
    @Nullable
    private Action action;
    private ImageData imageData;

    public int hashCode() {
        Action action2 = this.action;
        return this.imageData.hashCode() + (action2 != null ? action2.hashCode() : 0);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ImageOnlyMessage)) {
            return false;
        }
        ImageOnlyMessage i = (ImageOnlyMessage) o;
        if (hashCode() != i.hashCode()) {
            return false;
        }
        Action action2 = this.action;
        if ((action2 != null || i.action == null) && ((action2 == null || action2.equals(i.action)) && this.imageData.equals(i.imageData))) {
            return true;
        }
        return false;
    }

    private ImageOnlyMessage(CampaignMetadata campaignMetadata, ImageData imageData2, @Nullable Action action2, @Nullable Map<String, String> data) {
        super(campaignMetadata, MessageType.IMAGE_ONLY, data);
        this.imageData = imageData2;
        this.action = action2;
    }

    public ImageData getImageData() {
        return this.imageData;
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
        ImageData imageData;

        public Builder setImageData(@Nullable ImageData imageData2) {
            this.imageData = imageData2;
            return this;
        }

        public Builder setAction(@Nullable Action action2) {
            this.action = action2;
            return this;
        }

        public ImageOnlyMessage build(CampaignMetadata campaignMetadata, @Nullable Map<String, String> data) {
            if (this.imageData != null) {
                return new ImageOnlyMessage(campaignMetadata, this.imageData, this.action, data);
            }
            throw new IllegalArgumentException("ImageOnly model must have image data");
        }
    }
}
