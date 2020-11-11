package com.google.firebase.inappmessaging.internal;

import p019io.reactivex.functions.Consumer;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$22 implements Consumer {
    private static final InAppMessageStreamManager$$Lambda$22 instance = new InAppMessageStreamManager$$Lambda$22();

    private InAppMessageStreamManager$$Lambda$22() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    public void accept(Object obj) {
        Logging.logw("Impressions store read fail: " + ((Throwable) obj).getMessage());
    }
}
