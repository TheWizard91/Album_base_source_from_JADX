package com.google.firebase.inappmessaging;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.inappmessaging.internal.Logging;

/* compiled from: FirebaseInAppMessaging */
final /* synthetic */ class FirebaseInAppMessaging$$Lambda$1 implements OnSuccessListener {
    private static final FirebaseInAppMessaging$$Lambda$1 instance = new FirebaseInAppMessaging$$Lambda$1();

    private FirebaseInAppMessaging$$Lambda$1() {
    }

    public static OnSuccessListener lambdaFactory$() {
        return instance;
    }

    public void onSuccess(Object obj) {
        Logging.logi("Starting InAppMessaging runtime with Installation ID " + ((String) obj));
    }
}
