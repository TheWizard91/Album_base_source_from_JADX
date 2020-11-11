package com.google.firebase.inappmessaging.internal;

import p019io.reactivex.functions.Consumer;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$5 implements Consumer {
    private static final InAppMessageStreamManager$$Lambda$5 instance = new InAppMessageStreamManager$$Lambda$5();

    private InAppMessageStreamManager$$Lambda$5() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    public void accept(Object obj) {
        Logging.logi("App foreground rate limited ? : " + ((Boolean) obj));
    }
}
