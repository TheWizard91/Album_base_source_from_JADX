package com.google.firebase.inappmessaging.internal;

import com.google.android.gms.tasks.Task;
import p019io.reactivex.MaybeEmitter;
import p019io.reactivex.MaybeOnSubscribe;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$12 implements MaybeOnSubscribe {
    private final Task arg$1;

    private InAppMessageStreamManager$$Lambda$12(Task task) {
        this.arg$1 = task;
    }

    public static MaybeOnSubscribe lambdaFactory$(Task task) {
        return new InAppMessageStreamManager$$Lambda$12(task);
    }

    public void subscribe(MaybeEmitter maybeEmitter) {
        InAppMessageStreamManager.lambda$taskToMaybe$30(this.arg$1, maybeEmitter);
    }
}
