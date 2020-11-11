package com.google.internal.firebase.inappmessaging.p015v1.sdkserving;

import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;

/* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionListOrBuilder */
public interface CampaignImpressionListOrBuilder extends MessageLiteOrBuilder {
    CampaignImpression getAlreadySeenCampaigns(int i);

    int getAlreadySeenCampaignsCount();

    List<CampaignImpression> getAlreadySeenCampaignsList();
}
