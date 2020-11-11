package com.google.internal.firebase.inappmessaging.p015v1.sdkserving;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;

/* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionOrBuilder */
public interface CampaignImpressionOrBuilder extends MessageLiteOrBuilder {
    String getCampaignId();

    ByteString getCampaignIdBytes();

    long getImpressionTimestampMillis();
}
