package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.CampaignImpressionList;
import p019io.reactivex.functions.Action;

/* compiled from: ImpressionStorageClient */
final /* synthetic */ class ImpressionStorageClient$$Lambda$8 implements Action {
    private final ImpressionStorageClient arg$1;
    private final CampaignImpressionList arg$2;

    private ImpressionStorageClient$$Lambda$8(ImpressionStorageClient impressionStorageClient, CampaignImpressionList campaignImpressionList) {
        this.arg$1 = impressionStorageClient;
        this.arg$2 = campaignImpressionList;
    }

    public static Action lambdaFactory$(ImpressionStorageClient impressionStorageClient, CampaignImpressionList campaignImpressionList) {
        return new ImpressionStorageClient$$Lambda$8(impressionStorageClient, campaignImpressionList);
    }

    public void run() {
        this.arg$1.initInMemCache(this.arg$2);
    }
}
