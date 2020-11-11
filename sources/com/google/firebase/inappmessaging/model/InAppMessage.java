package com.google.firebase.inappmessaging.model;

import java.util.Map;

public abstract class InAppMessage {
    @Deprecated
    Button actionButton;
    @Deprecated
    String backgroundHexColor;
    @Deprecated
    Text body;
    @Deprecated
    String campaignId;
    CampaignMetadata campaignMetadata;
    @Deprecated
    String campaignName;
    private Map<String, String> data;
    @Deprecated
    ImageData imageData;
    @Deprecated
    String imageUrl;
    @Deprecated
    Boolean isTestMessage;
    MessageType messageType;
    @Deprecated
    Text title;

    @Deprecated
    public abstract Action getAction();

    @Deprecated
    public InAppMessage(Text title2, Text body2, String imageUrl2, ImageData imageData2, Button actionButton2, Action action, String backgroundHexColor2, String campaignId2, String campaignName2, Boolean isTestMessage2, MessageType messageType2, Map<String, String> data2) {
        this.title = title2;
        this.body = body2;
        this.imageUrl = imageUrl2;
        this.imageData = imageData2;
        this.actionButton = actionButton2;
        this.backgroundHexColor = backgroundHexColor2;
        this.campaignId = campaignId2;
        this.campaignName = campaignName2;
        this.isTestMessage = isTestMessage2;
        this.messageType = messageType2;
        this.campaignMetadata = new CampaignMetadata(campaignId2, campaignName2, isTestMessage2.booleanValue());
        this.data = data2;
    }

    public InAppMessage(CampaignMetadata campaignMetadata2, MessageType messageType2, Map<String, String> data2) {
        this.campaignMetadata = campaignMetadata2;
        this.messageType = messageType2;
        this.data = data2;
    }

    @Deprecated
    public Text getTitle() {
        return this.title;
    }

    @Deprecated
    public Text getBody() {
        return this.body;
    }

    @Deprecated
    public String getImageUrl() {
        return this.imageUrl;
    }

    @Deprecated
    public ImageData getImageData() {
        return this.imageData;
    }

    @Deprecated
    public Button getActionButton() {
        if (getAction() != null) {
            return getAction().getButton();
        }
        return this.actionButton;
    }

    @Deprecated
    public String getBackgroundHexColor() {
        return this.backgroundHexColor;
    }

    @Deprecated
    public String getCampaignId() {
        return this.campaignMetadata.getCampaignId();
    }

    @Deprecated
    public String getCampaignName() {
        return this.campaignMetadata.getCampaignName();
    }

    @Deprecated
    public Boolean getIsTestMessage() {
        return Boolean.valueOf(this.campaignMetadata.getIsTestMessage());
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    public CampaignMetadata getCampaignMetadata() {
        return this.campaignMetadata;
    }

    public Map<String, String> getData() {
        return this.data;
    }
}
