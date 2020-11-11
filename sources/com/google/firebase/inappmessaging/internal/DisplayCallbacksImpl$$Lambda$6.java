package com.google.firebase.inappmessaging.internal;

import p019io.reactivex.functions.Consumer;

/* compiled from: DisplayCallbacksImpl */
final /* synthetic */ class DisplayCallbacksImpl$$Lambda$6 implements Consumer {
    private static final DisplayCallbacksImpl$$Lambda$6 instance = new DisplayCallbacksImpl$$Lambda$6();

    private DisplayCallbacksImpl$$Lambda$6() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    public void accept(Object obj) {
        Logging.loge("Impression store write failure");
    }
}
