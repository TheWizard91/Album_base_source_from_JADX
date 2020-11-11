package com.google.firebase.inappmessaging.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import p019io.reactivex.Scheduler;

@Singleton
public class Schedulers {
    private final Scheduler computeScheduler;
    private final Scheduler ioScheduler;
    private final Scheduler mainThreadScheduler;

    @Inject
    Schedulers(@Named("io") Scheduler ioScheduler2, @Named("compute") Scheduler computeScheduler2, @Named("main") Scheduler mainThreadScheduler2) {
        this.ioScheduler = ioScheduler2;
        this.computeScheduler = computeScheduler2;
        this.mainThreadScheduler = mainThreadScheduler2;
    }

    /* renamed from: io */
    public Scheduler mo54430io() {
        return this.ioScheduler;
    }

    public Scheduler mainThread() {
        return this.mainThreadScheduler;
    }

    public Scheduler computation() {
        return this.computeScheduler;
    }
}
