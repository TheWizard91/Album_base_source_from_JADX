package com.google.firebase.inappmessaging.internal;

import p019io.reactivex.functions.Action;

/* compiled from: DisplayCallbacksImpl */
final /* synthetic */ class DisplayCallbacksImpl$$Lambda$9 implements Action {
    private static final DisplayCallbacksImpl$$Lambda$9 instance = new DisplayCallbacksImpl$$Lambda$9();

    private DisplayCallbacksImpl$$Lambda$9() {
    }

    public static Action lambdaFactory$() {
        return instance;
    }

    public void run() {
        Logging.logd("Rate limiter client write success");
    }
}
