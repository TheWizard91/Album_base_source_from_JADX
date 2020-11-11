package com.google.firebase.inappmessaging.internal;

import p019io.reactivex.functions.Predicate;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$34 implements Predicate {
    private static final InAppMessageStreamManager$$Lambda$34 instance = new InAppMessageStreamManager$$Lambda$34();

    private InAppMessageStreamManager$$Lambda$34() {
    }

    public static Predicate lambdaFactory$() {
        return instance;
    }

    public boolean test(Object obj) {
        return InAppMessageStreamManager.lambda$createFirebaseInAppMessageStream$9((Boolean) obj);
    }
}
