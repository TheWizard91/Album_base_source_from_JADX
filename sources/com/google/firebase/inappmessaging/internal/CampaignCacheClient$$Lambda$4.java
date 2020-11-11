package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.FetchEligibleCampaignsResponse;
import p019io.reactivex.functions.Predicate;

/* compiled from: CampaignCacheClient */
final /* synthetic */ class CampaignCacheClient$$Lambda$4 implements Predicate {
    private final CampaignCacheClient arg$1;

    private CampaignCacheClient$$Lambda$4(CampaignCacheClient campaignCacheClient) {
        this.arg$1 = campaignCacheClient;
    }

    public static Predicate lambdaFactory$(CampaignCacheClient campaignCacheClient) {
        return new CampaignCacheClient$$Lambda$4(campaignCacheClient);
    }

    public boolean test(Object obj) {
        return this.arg$1.isResponseValid((FetchEligibleCampaignsResponse) obj);
    }
}
