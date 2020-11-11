package com.google.firebase.inappmessaging.internal;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import p019io.reactivex.BackpressureStrategy;
import p019io.reactivex.flowables.ConnectableFlowable;
import p019io.reactivex.subjects.BehaviorSubject;

public class ForegroundNotifier implements Application.ActivityLifecycleCallbacks {
    public static final long DELAY_MILLIS = 1000;
    private Runnable check;
    private boolean foreground = false;
    private final BehaviorSubject<String> foregroundSubject = BehaviorSubject.create();
    private final Handler handler = new Handler();
    private boolean paused = true;

    public ConnectableFlowable<String> foregroundFlowable() {
        return this.foregroundSubject.toFlowable(BackpressureStrategy.BUFFER).publish();
    }

    public void onActivityResumed(Activity activity) {
        this.paused = false;
        boolean wasBackground = !this.foreground;
        this.foreground = true;
        Runnable runnable = this.check;
        if (runnable != null) {
            this.handler.removeCallbacks(runnable);
        }
        if (wasBackground) {
            Logging.logi("went foreground");
            this.foregroundSubject.onNext(InAppMessageStreamManager.ON_FOREGROUND);
        }
    }

    public void onActivityPaused(Activity activity) {
        this.paused = true;
        Runnable runnable = this.check;
        if (runnable != null) {
            this.handler.removeCallbacks(runnable);
        }
        Handler handler2 = this.handler;
        Runnable lambdaFactory$ = ForegroundNotifier$$Lambda$1.lambdaFactory$(this);
        this.check = lambdaFactory$;
        handler2.postDelayed(lambdaFactory$, 1000);
    }

    static /* synthetic */ void lambda$onActivityPaused$0(ForegroundNotifier foregroundNotifier) {
        boolean z = foregroundNotifier.foreground;
        foregroundNotifier.foreground = (!z || !foregroundNotifier.paused) && z;
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onActivityDestroyed(Activity activity) {
    }
}
