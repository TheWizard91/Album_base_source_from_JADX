package com.google.firebase.inappmessaging.internal;

import p019io.reactivex.functions.Consumer;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$31 implements Consumer {
    private static final InAppMessageStreamManager$$Lambda$31 instance = new InAppMessageStreamManager$$Lambda$31();

    private InAppMessageStreamManager$$Lambda$31() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    public void accept(Object obj) {
        Logging.logw("Service fetch error: " + ((Throwable) obj).getMessage());
    }
}
