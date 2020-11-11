package com.google.firebase.inappmessaging.internal;

import p019io.reactivex.functions.Consumer;

/* compiled from: DisplayCallbacksImpl */
final /* synthetic */ class DisplayCallbacksImpl$$Lambda$8 implements Consumer {
    private static final DisplayCallbacksImpl$$Lambda$8 instance = new DisplayCallbacksImpl$$Lambda$8();

    private DisplayCallbacksImpl$$Lambda$8() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    public void accept(Object obj) {
        Logging.loge("Rate limiter client write failure");
    }
}
