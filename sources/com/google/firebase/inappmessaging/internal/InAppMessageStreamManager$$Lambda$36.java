package com.google.firebase.inappmessaging.internal;

import p019io.reactivex.functions.Action;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$36 implements Action {
    private static final InAppMessageStreamManager$$Lambda$36 instance = new InAppMessageStreamManager$$Lambda$36();

    private InAppMessageStreamManager$$Lambda$36() {
    }

    public static Action lambdaFactory$() {
        return instance;
    }

    public void run() {
        Logging.logd("Wrote to cache");
    }
}
