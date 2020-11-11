package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.FetchEligibleCampaignsResponse;
import p019io.reactivex.functions.Consumer;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$28 implements Consumer {
    private final InAppMessageStreamManager arg$1;

    private InAppMessageStreamManager$$Lambda$28(InAppMessageStreamManager inAppMessageStreamManager) {
        this.arg$1 = inAppMessageStreamManager;
    }

    public static Consumer lambdaFactory$(InAppMessageStreamManager inAppMessageStreamManager) {
        return new InAppMessageStreamManager$$Lambda$28(inAppMessageStreamManager);
    }

    public void accept(Object obj) {
        this.arg$1.impressionStorageClient.clearImpressions((FetchEligibleCampaignsResponse) obj).subscribe();
    }
}
