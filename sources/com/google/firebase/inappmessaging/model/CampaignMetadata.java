package com.google.firebase.inappmessaging.model;

public class CampaignMetadata {
    private final String campaignId;
    private final String campaignName;
    private final boolean isTestMessage;

    public CampaignMetadata(String campaignId2, String campaignName2, boolean isTestMessage2) {
        this.campaignId = campaignId2;
        this.campaignName = campaignName2;
        this.isTestMessage = isTestMessage2;
    }

    public String getCampaignId() {
        return this.campaignId;
    }

    public String getCampaignName() {
        return this.campaignName;
    }

    public boolean getIsTestMessage() {
        return this.isTestMessage;
    }
}
