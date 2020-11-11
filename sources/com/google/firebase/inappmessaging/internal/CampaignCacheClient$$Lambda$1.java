package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.FetchEligibleCampaignsResponse;
import p019io.reactivex.functions.Action;

/* compiled from: CampaignCacheClient */
final /* synthetic */ class CampaignCacheClient$$Lambda$1 implements Action {
    private final CampaignCacheClient arg$1;
    private final FetchEligibleCampaignsResponse arg$2;

    private CampaignCacheClient$$Lambda$1(CampaignCacheClient campaignCacheClient, FetchEligibleCampaignsResponse fetchEligibleCampaignsResponse) {
        this.arg$1 = campaignCacheClient;
        this.arg$2 = fetchEligibleCampaignsResponse;
    }

    public static Action lambdaFactory$(CampaignCacheClient campaignCacheClient, FetchEligibleCampaignsResponse fetchEligibleCampaignsResponse) {
        return new CampaignCacheClient$$Lambda$1(campaignCacheClient, fetchEligibleCampaignsResponse);
    }

    public void run() {
        this.arg$1.cachedResponse = this.arg$2;
    }
}
