package com.google.firebase.inappmessaging.internal;

import p019io.reactivex.functions.Predicate;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$25 implements Predicate {
    private static final InAppMessageStreamManager$$Lambda$25 instance = new InAppMessageStreamManager$$Lambda$25();

    private InAppMessageStreamManager$$Lambda$25() {
    }

    public static Predicate lambdaFactory$() {
        return instance;
    }

    public boolean test(Object obj) {
        return InAppMessageStreamManager.validIID((InstallationIdResult) obj);
    }
}
