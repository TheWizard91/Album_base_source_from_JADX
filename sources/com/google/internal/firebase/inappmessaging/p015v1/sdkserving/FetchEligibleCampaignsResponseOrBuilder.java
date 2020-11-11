package com.google.internal.firebase.inappmessaging.p015v1.sdkserving;

import com.google.internal.firebase.inappmessaging.p015v1.CampaignProto;
import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;

/* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.FetchEligibleCampaignsResponseOrBuilder */
public interface FetchEligibleCampaignsResponseOrBuilder extends MessageLiteOrBuilder {
    long getExpirationEpochTimestampMillis();

    CampaignProto.ThickContent getMessages(int i);

    int getMessagesCount();

    List<CampaignProto.ThickContent> getMessagesList();
}
