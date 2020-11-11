package com.google.firebase.inappmessaging.internal;

import p019io.reactivex.functions.Consumer;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$32 implements Consumer {
    private static final InAppMessageStreamManager$$Lambda$32 instance = new InAppMessageStreamManager$$Lambda$32();

    private InAppMessageStreamManager$$Lambda$32() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    public void accept(Object obj) {
        Logging.logw("Impression store read fail: " + ((Throwable) obj).getMessage());
    }
}
