package com.google.firebase.inappmessaging.internal;

import com.google.firebase.installations.InstallationTokenResult;
import p019io.reactivex.functions.BiFunction;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$23 implements BiFunction {
    private static final InAppMessageStreamManager$$Lambda$23 instance = new InAppMessageStreamManager$$Lambda$23();

    private InAppMessageStreamManager$$Lambda$23() {
    }

    public static BiFunction lambdaFactory$() {
        return instance;
    }

    public Object apply(Object obj, Object obj2) {
        return InstallationIdResult.create((String) obj, (InstallationTokenResult) obj2);
    }
}
