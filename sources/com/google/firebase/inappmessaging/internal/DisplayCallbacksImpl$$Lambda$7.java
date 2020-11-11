package com.google.firebase.inappmessaging.internal;

import p019io.reactivex.functions.Action;

/* compiled from: DisplayCallbacksImpl */
final /* synthetic */ class DisplayCallbacksImpl$$Lambda$7 implements Action {
    private static final DisplayCallbacksImpl$$Lambda$7 instance = new DisplayCallbacksImpl$$Lambda$7();

    private DisplayCallbacksImpl$$Lambda$7() {
    }

    public static Action lambdaFactory$() {
        return instance;
    }

    public void run() {
        Logging.logd("Impression store write success");
    }
}
