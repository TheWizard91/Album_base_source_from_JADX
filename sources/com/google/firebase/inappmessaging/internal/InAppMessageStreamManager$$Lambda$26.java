package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.CampaignImpressionList;
import p019io.reactivex.functions.Function;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$26 implements Function {
    private final InAppMessageStreamManager arg$1;
    private final CampaignImpressionList arg$2;

    private InAppMessageStreamManager$$Lambda$26(InAppMessageStreamManager inAppMessageStreamManager, CampaignImpressionList campaignImpressionList) {
        this.arg$1 = inAppMessageStreamManager;
        this.arg$2 = campaignImpressionList;
    }

    public static Function lambdaFactory$(InAppMessageStreamManager inAppMessageStreamManager, CampaignImpressionList campaignImpressionList) {
        return new InAppMessageStreamManager$$Lambda$26(inAppMessageStreamManager, campaignImpressionList);
    }

    public Object apply(Object obj) {
        return this.arg$1.apiClient.getFiams((InstallationIdResult) obj, this.arg$2);
    }
}
