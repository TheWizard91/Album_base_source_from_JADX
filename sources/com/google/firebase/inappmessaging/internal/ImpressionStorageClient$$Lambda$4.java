package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.CampaignImpressionList;
import p019io.reactivex.functions.Function;

/* compiled from: ImpressionStorageClient */
final /* synthetic */ class ImpressionStorageClient$$Lambda$4 implements Function {
    private static final ImpressionStorageClient$$Lambda$4 instance = new ImpressionStorageClient$$Lambda$4();

    private ImpressionStorageClient$$Lambda$4() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    public Object apply(Object obj) {
        return ((CampaignImpressionList) obj).getAlreadySeenCampaignsList();
    }
}
