package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.CampaignProto;
import p019io.reactivex.functions.Function;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$20 implements Function {
    private static final InAppMessageStreamManager$$Lambda$20 instance = new InAppMessageStreamManager$$Lambda$20();

    private InAppMessageStreamManager$$Lambda$20() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    public Object apply(Object obj) {
        return InAppMessageStreamManager.lambda$createFirebaseInAppMessageStream$13((CampaignProto.ThickContent) obj);
    }
}
