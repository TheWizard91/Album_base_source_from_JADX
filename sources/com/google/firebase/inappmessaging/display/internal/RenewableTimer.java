package com.google.firebase.inappmessaging.display.internal;

import android.os.CountDownTimer;
import javax.inject.Inject;

public class RenewableTimer {
    private CountDownTimer mCountDownTimer;

    public interface Callback {
        void onFinish();
    }

    @Inject
    RenewableTimer() {
    }

    public void start(Callback c, long duration, long interval) {
        final Callback callback = c;
        this.mCountDownTimer = new CountDownTimer(duration, interval) {
            public void onTick(long l) {
            }

            public void onFinish() {
                callback.onFinish();
            }
        }.start();
    }

    public void cancel() {
        CountDownTimer countDownTimer = this.mCountDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.mCountDownTimer = null;
        }
    }
}
