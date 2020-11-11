package com.google.firebase.inappmessaging.internal;

import com.google.android.gms.tasks.OnSuccessListener;
import p019io.reactivex.MaybeEmitter;

/* compiled from: InAppMessageStreamManager */
final /* synthetic */ class InAppMessageStreamManager$$Lambda$13 implements OnSuccessListener {
    private final MaybeEmitter arg$1;

    private InAppMessageStreamManager$$Lambda$13(MaybeEmitter maybeEmitter) {
        this.arg$1 = maybeEmitter;
    }

    public static OnSuccessListener lambdaFactory$(MaybeEmitter maybeEmitter) {
        return new InAppMessageStreamManager$$Lambda$13(maybeEmitter);
    }

    public void onSuccess(Object obj) {
        InAppMessageStreamManager.lambda$taskToMaybe$28(this.arg$1, obj);
    }
}
