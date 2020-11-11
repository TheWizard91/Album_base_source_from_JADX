package com.google.firebase.inappmessaging.internal;

import p019io.reactivex.Completable;
import p019io.reactivex.functions.Function;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$38 implements Function {
    private static final InAppMessageStreamManager$$Lambda$38 instance = new InAppMessageStreamManager$$Lambda$38();

    private InAppMessageStreamManager$$Lambda$38() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    public Object apply(Object obj) {
        return Completable.complete();
    }
}
