package com.google.firebase.inappmessaging.internal;

import java.util.List;
import p019io.reactivex.Observable;
import p019io.reactivex.functions.Function;

/* compiled from: ImpressionStorageClient */
final /* synthetic */ class ImpressionStorageClient$$Lambda$5 implements Function {
    private static final ImpressionStorageClient$$Lambda$5 instance = new ImpressionStorageClient$$Lambda$5();

    private ImpressionStorageClient$$Lambda$5() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    public Object apply(Object obj) {
        return Observable.fromIterable((List) obj);
    }
}
