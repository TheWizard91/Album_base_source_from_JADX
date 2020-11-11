package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.FetchEligibleCampaignsResponse;
import p019io.reactivex.functions.Consumer;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$17 implements Consumer {
    private final InAppMessageStreamManager arg$1;

    private InAppMessageStreamManager$$Lambda$17(InAppMessageStreamManager inAppMessageStreamManager) {
        this.arg$1 = inAppMessageStreamManager;
    }

    public static Consumer lambdaFactory$(InAppMessageStreamManager inAppMessageStreamManager) {
        return new InAppMessageStreamManager$$Lambda$17(inAppMessageStreamManager);
    }

    public void accept(Object obj) {
        this.arg$1.campaignCacheClient.put((FetchEligibleCampaignsResponse) obj).doOnComplete(InAppMessageStreamManager$$Lambda$36.lambdaFactory$()).doOnError(InAppMessageStreamManager$$Lambda$37.lambdaFactory$()).onErrorResumeNext(InAppMessageStreamManager$$Lambda$38.lambdaFactory$()).subscribe();
    }
}
