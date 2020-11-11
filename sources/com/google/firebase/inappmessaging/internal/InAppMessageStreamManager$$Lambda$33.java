package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.CampaignProto;
import p019io.reactivex.functions.Consumer;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$33 implements Consumer {
    private final CampaignProto.ThickContent arg$1;

    private InAppMessageStreamManager$$Lambda$33(CampaignProto.ThickContent thickContent) {
        this.arg$1 = thickContent;
    }

    public static Consumer lambdaFactory$(CampaignProto.ThickContent thickContent) {
        return new InAppMessageStreamManager$$Lambda$33(thickContent);
    }

    public void accept(Object obj) {
        InAppMessageStreamManager.logImpressionStatus(this.arg$1, (Boolean) obj);
    }
}
