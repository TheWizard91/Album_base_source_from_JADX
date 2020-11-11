package com.google.firebase.inappmessaging.model;

import android.text.TextUtils;
import java.util.Map;

public class CardMessage extends InAppMessage {
    private final String backgroundHexColor;
    private final Text body;
    private final ImageData landscapeImageData;
    private final ImageData portraitImageData;
    private final Action primaryAction;
    private final Action secondaryAction;
    private final Text title;

    public int hashCode() {
        Text text = this.body;
        int landscapeImageHash = 0;
        int bodyHash = text != null ? text.hashCode() : 0;
        Action action = this.secondaryAction;
        int secondaryActionHash = action != null ? action.hashCode() : 0;
        ImageData imageData = this.portraitImageData;
        int portraitImageHash = imageData != null ? imageData.hashCode() : 0;
        ImageData imageData2 = this.landscapeImageData;
        if (imageData2 != null) {
            landscapeImageHash = imageData2.hashCode();
        }
        return this.title.hashCode() + bodyHash + this.backgroundHexColor.hashCode() + this.primaryAction.hashCode() + secondaryActionHash + portraitImageHash + landscapeImageHash;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CardMessage)) {
            return false;
        }
        CardMessage c = (CardMessage) o;
        if (hashCode() != c.hashCode()) {
            return false;
        }
        Text text = this.body;
        if ((text == null && c.body != null) || (text != null && !text.equals(c.body))) {
            return false;
        }
        Action action = this.secondaryAction;
        if ((action == null && c.secondaryAction != null) || (action != null && !action.equals(c.secondaryAction))) {
            return false;
        }
        ImageData imageData = this.portraitImageData;
        if ((imageData == null && c.portraitImageData != null) || (imageData != null && !imageData.equals(c.portraitImageData))) {
            return false;
        }
        ImageData imageData2 = this.landscapeImageData;
        if ((imageData2 != null || c.landscapeImageData == null) && ((imageData2 == null || imageData2.equals(c.landscapeImageData)) && this.title.equals(c.title) && this.primaryAction.equals(c.primaryAction) && this.backgroundHexColor.equals(c.backgroundHexColor))) {
            return true;
        }
        return false;
    }

    private CardMessage(CampaignMetadata campaignMetadata, Text title2, Text body2, ImageData portraitImageData2, ImageData landscapeImageData2, String backgroundHexColor2, Action primaryAction2, Action secondaryAction2, Map<String, String> data) {
        super(campaignMetadata, MessageType.CARD, data);
        this.title = title2;
        this.body = body2;
        this.portraitImageData = portraitImageData2;
        this.landscapeImageData = landscapeImageData2;
        this.backgroundHexColor = backgroundHexColor2;
        this.primaryAction = primaryAction2;
        this.secondaryAction = secondaryAction2;
    }

    public ImageData getPortraitImageData() {
        return this.portraitImageData;
    }

    public ImageData getLandscapeImageData() {
        return this.landscapeImageData;
    }

    public String getBackgroundHexColor() {
        return this.backgroundHexColor;
    }

    public Action getPrimaryAction() {
        return this.primaryAction;
    }

    public Action getSecondaryAction() {
        return this.secondaryAction;
    }

    @Deprecated
    public Action getAction() {
        return this.primaryAction;
    }

    public Text getTitle() {
        return this.title;
    }

    public Text getBody() {
        return this.body;
    }

    @Deprecated
    public ImageData getImageData() {
        return this.portraitImageData;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        String backgroundHexColor;
        Text body;
        ImageData landscapeImageData;
        ImageData portraitImageData;
        Action primaryAction;
        Action secondaryAction;
        Text title;

        public Builder setPortraitImageData(ImageData portraitImageData2) {
            this.portraitImageData = portraitImageData2;
            return this;
        }

        public Builder setLandscapeImageData(ImageData landscapeImageData2) {
            this.landscapeImageData = landscapeImageData2;
            return this;
        }

        public Builder setBackgroundHexColor(String backgroundHexColor2) {
            this.backgroundHexColor = backgroundHexColor2;
            return this;
        }

        public Builder setPrimaryAction(Action primaryAction2) {
            this.primaryAction = primaryAction2;
            return this;
        }

        public Builder setSecondaryAction(Action secondaryAction2) {
            this.secondaryAction = secondaryAction2;
            return this;
        }

        public Builder setTitle(Text title2) {
            this.title = title2;
            return this;
        }

        public Builder setBody(Text body2) {
            this.body = body2;
            return this;
        }

        public CardMessage build(CampaignMetadata campaignMetadata, Map<String, String> data) {
            Action action = this.primaryAction;
            if (action == null) {
                throw new IllegalArgumentException("Card model must have a primary action");
            } else if (action.getButton() != null) {
                Action action2 = this.secondaryAction;
                if (action2 != null && action2.getButton() == null) {
                    throw new IllegalArgumentException("Card model secondary action must be null or have a button");
                } else if (this.title == null) {
                    throw new IllegalArgumentException("Card model must have a title");
                } else if (this.portraitImageData == null && this.landscapeImageData == null) {
                    throw new IllegalArgumentException("Card model must have at least one image");
                } else if (!TextUtils.isEmpty(this.backgroundHexColor)) {
                    return new CardMessage(campaignMetadata, this.title, this.body, this.portraitImageData, this.landscapeImageData, this.backgroundHexColor, this.primaryAction, this.secondaryAction, data);
                } else {
                    throw new IllegalArgumentException("Card model must have a background color");
                }
            } else {
                throw new IllegalArgumentException("Card model must have a primary action button");
            }
        }
    }
}
