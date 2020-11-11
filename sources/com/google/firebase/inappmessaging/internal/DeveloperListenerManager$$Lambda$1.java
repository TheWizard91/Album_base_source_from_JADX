package com.google.firebase.inappmessaging.internal;

import com.google.firebase.inappmessaging.internal.DeveloperListenerManager;
import com.google.firebase.inappmessaging.model.InAppMessage;

/* compiled from: DeveloperListenerManager */
final /* synthetic */ class DeveloperListenerManager$$Lambda$1 implements Runnable {
    private final DeveloperListenerManager.ImpressionExecutorAndListener arg$1;
    private final InAppMessage arg$2;

    private DeveloperListenerManager$$Lambda$1(DeveloperListenerManager.ImpressionExecutorAndListener impressionExecutorAndListener, InAppMessage inAppMessage) {
        this.arg$1 = impressionExecutorAndListener;
        this.arg$2 = inAppMessage;
    }

    public static Runnable lambdaFactory$(DeveloperListenerManager.ImpressionExecutorAndListener impressionExecutorAndListener, InAppMessage inAppMessage) {
        return new DeveloperListenerManager$$Lambda$1(impressionExecutorAndListener, inAppMessage);
    }

    public void run() {
        this.arg$1.getListener().impressionDetected(this.arg$2);
    }
}
