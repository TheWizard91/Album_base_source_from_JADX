package com.google.firebase.inappmessaging.internal;

import java.util.concurrent.Callable;

/* compiled from: CampaignCacheClient */
final /* synthetic */ class CampaignCacheClient$$Lambda$2 implements Callable {
    private final CampaignCacheClient arg$1;

    private CampaignCacheClient$$Lambda$2(CampaignCacheClient campaignCacheClient) {
        this.arg$1 = campaignCacheClient;
    }

    public static Callable lambdaFactory$(CampaignCacheClient campaignCacheClient) {
        return new CampaignCacheClient$$Lambda$2(campaignCacheClient);
    }

    public Object call() {
        return this.arg$1.cachedResponse;
    }
}
