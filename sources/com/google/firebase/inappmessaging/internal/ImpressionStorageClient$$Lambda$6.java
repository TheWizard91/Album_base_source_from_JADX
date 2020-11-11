package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.CampaignImpression;
import p019io.reactivex.functions.Function;

/* compiled from: ImpressionStorageClient */
final /* synthetic */ class ImpressionStorageClient$$Lambda$6 implements Function {
    private static final ImpressionStorageClient$$Lambda$6 instance = new ImpressionStorageClient$$Lambda$6();

    private ImpressionStorageClient$$Lambda$6() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    public Object apply(Object obj) {
        return ((CampaignImpression) obj).getCampaignId();
    }
}
