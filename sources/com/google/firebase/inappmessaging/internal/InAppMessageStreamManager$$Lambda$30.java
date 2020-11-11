package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.FetchEligibleCampaignsResponse;
import p019io.reactivex.functions.Consumer;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$30 implements Consumer {
    private final TestDeviceHelper arg$1;

    private InAppMessageStreamManager$$Lambda$30(TestDeviceHelper testDeviceHelper) {
        this.arg$1 = testDeviceHelper;
    }

    public static Consumer lambdaFactory$(TestDeviceHelper testDeviceHelper) {
        return new InAppMessageStreamManager$$Lambda$30(testDeviceHelper);
    }

    public void accept(Object obj) {
        this.arg$1.processCampaignFetch((FetchEligibleCampaignsResponse) obj);
    }
}
