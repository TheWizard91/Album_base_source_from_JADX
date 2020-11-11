package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.CampaignImpressionList;
import p019io.reactivex.Maybe;
import p019io.reactivex.functions.Function;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$24 implements Function {
    private final InAppMessageStreamManager arg$1;
    private final Maybe arg$2;

    private InAppMessageStreamManager$$Lambda$24(InAppMessageStreamManager inAppMessageStreamManager, Maybe maybe) {
        this.arg$1 = inAppMessageStreamManager;
        this.arg$2 = maybe;
    }

    public static Function lambdaFactory$(InAppMessageStreamManager inAppMessageStreamManager, Maybe maybe) {
        return new InAppMessageStreamManager$$Lambda$24(inAppMessageStreamManager, maybe);
    }

    public Object apply(Object obj) {
        return InAppMessageStreamManager.lambda$createFirebaseInAppMessageStream$20(this.arg$1, this.arg$2, (CampaignImpressionList) obj);
    }
}
