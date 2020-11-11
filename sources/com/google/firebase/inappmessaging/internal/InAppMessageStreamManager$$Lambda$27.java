package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.FetchEligibleCampaignsResponse;
import java.util.Locale;
import p019io.reactivex.functions.Consumer;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$27 implements Consumer {
    private static final InAppMessageStreamManager$$Lambda$27 instance = new InAppMessageStreamManager$$Lambda$27();

    private InAppMessageStreamManager$$Lambda$27() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    public void accept(Object obj) {
        Logging.logi(String.format(Locale.US, "Successfully fetched %d messages from backend", new Object[]{Integer.valueOf(((FetchEligibleCampaignsResponse) obj).getMessagesList().size())}));
    }
}
