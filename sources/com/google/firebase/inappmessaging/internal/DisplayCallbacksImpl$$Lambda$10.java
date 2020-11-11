package com.google.firebase.inappmessaging.internal;

import com.google.android.gms.tasks.TaskCompletionSource;
import p019io.reactivex.functions.Consumer;

/* compiled from: DisplayCallbacksImpl */
final /* synthetic */ class DisplayCallbacksImpl$$Lambda$10 implements Consumer {
    private final TaskCompletionSource arg$1;

    private DisplayCallbacksImpl$$Lambda$10(TaskCompletionSource taskCompletionSource) {
        this.arg$1 = taskCompletionSource;
    }

    public static Consumer lambdaFactory$(TaskCompletionSource taskCompletionSource) {
        return new DisplayCallbacksImpl$$Lambda$10(taskCompletionSource);
    }

    public void accept(Object obj) {
        this.arg$1.setResult(obj);
    }
}
