package com.google.firebase.inappmessaging;

import com.google.firebase.inappmessaging.model.TriggeredInAppMessage;
import p019io.reactivex.functions.Consumer;

/* compiled from: FirebaseInAppMessaging */
final /* synthetic */ class FirebaseInAppMessaging$$Lambda$2 implements Consumer {
    private final FirebaseInAppMessaging arg$1;

    private FirebaseInAppMessaging$$Lambda$2(FirebaseInAppMessaging firebaseInAppMessaging) {
        this.arg$1 = firebaseInAppMessaging;
    }

    public static Consumer lambdaFactory$(FirebaseInAppMessaging firebaseInAppMessaging) {
        return new FirebaseInAppMessaging$$Lambda$2(firebaseInAppMessaging);
    }

    public void accept(Object obj) {
        this.arg$1.triggerInAppMessage((TriggeredInAppMessage) obj);
    }
}
