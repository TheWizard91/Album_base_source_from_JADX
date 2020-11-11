package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.FetchEligibleCampaignsResponse;
import p019io.reactivex.functions.Consumer;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$29 implements Consumer {
    private final AnalyticsEventsManager arg$1;

    private InAppMessageStreamManager$$Lambda$29(AnalyticsEventsManager analyticsEventsManager) {
        this.arg$1 = analyticsEventsManager;
    }

    public static Consumer lambdaFactory$(AnalyticsEventsManager analyticsEventsManager) {
        return new InAppMessageStreamManager$$Lambda$29(analyticsEventsManager);
    }

    public void accept(Object obj) {
        this.arg$1.updateContextualTriggers((FetchEligibleCampaignsResponse) obj);
    }
}
