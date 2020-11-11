package com.google.firebase.inappmessaging.internal;

import p019io.reactivex.functions.Consumer;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$37 implements Consumer {
    private static final InAppMessageStreamManager$$Lambda$37 instance = new InAppMessageStreamManager$$Lambda$37();

    private InAppMessageStreamManager$$Lambda$37() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    public void accept(Object obj) {
        Logging.logw("Cache write error: " + ((Throwable) obj).getMessage());
    }
}
