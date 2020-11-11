package com.google.firebase.inappmessaging.internal.time;

public class SystemClock implements Clock {
    public long now() {
        return System.currentTimeMillis();
    }
}
