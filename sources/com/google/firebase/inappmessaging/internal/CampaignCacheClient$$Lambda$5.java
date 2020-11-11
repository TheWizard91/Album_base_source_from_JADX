package com.google.firebase.inappmessaging.internal;

import p019io.reactivex.functions.Consumer;

/* compiled from: CampaignCacheClient */
final /* synthetic */ class CampaignCacheClient$$Lambda$5 implements Consumer {
    private final CampaignCacheClient arg$1;

    private CampaignCacheClient$$Lambda$5(CampaignCacheClient campaignCacheClient) {
        this.arg$1 = campaignCacheClient;
    }

    public static Consumer lambdaFactory$(CampaignCacheClient campaignCacheClient) {
        return new CampaignCacheClient$$Lambda$5(campaignCacheClient);
    }

    public void accept(Object obj) {
        this.arg$1.cachedResponse = null;
    }
}
