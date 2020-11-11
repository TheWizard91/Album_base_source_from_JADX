package com.google.firebase.inappmessaging.internal;

import p019io.reactivex.functions.Predicate;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$6 implements Predicate {
    private static final InAppMessageStreamManager$$Lambda$6 instance = new InAppMessageStreamManager$$Lambda$6();

    private InAppMessageStreamManager$$Lambda$6() {
    }

    public static Predicate lambdaFactory$() {
        return instance;
    }

    public boolean test(Object obj) {
        return InAppMessageStreamManager.lambda$getContentIfNotRateLimited$23((Boolean) obj);
    }
}
