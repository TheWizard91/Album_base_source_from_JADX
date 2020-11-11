package com.google.firebase.inappmessaging.internal;

import dagger.internal.Factory;
import javax.inject.Provider;
import p019io.reactivex.Scheduler;

public final class Schedulers_Factory implements Factory<Schedulers> {
    private final Provider<Scheduler> computeSchedulerProvider;
    private final Provider<Scheduler> ioSchedulerProvider;
    private final Provider<Scheduler> mainThreadSchedulerProvider;

    public Schedulers_Factory(Provider<Scheduler> ioSchedulerProvider2, Provider<Scheduler> computeSchedulerProvider2, Provider<Scheduler> mainThreadSchedulerProvider2) {
        this.ioSchedulerProvider = ioSchedulerProvider2;
        this.computeSchedulerProvider = computeSchedulerProvider2;
        this.mainThreadSchedulerProvider = mainThreadSchedulerProvider2;
    }

    public Schedulers get() {
        return new Schedulers(this.ioSchedulerProvider.get(), this.computeSchedulerProvider.get(), this.mainThreadSchedulerProvider.get());
    }

    public static Schedulers_Factory create(Provider<Scheduler> ioSchedulerProvider2, Provider<Scheduler> computeSchedulerProvider2, Provider<Scheduler> mainThreadSchedulerProvider2) {
        return new Schedulers_Factory(ioSchedulerProvider2, computeSchedulerProvider2, mainThreadSchedulerProvider2);
    }

    public static Schedulers newInstance(Scheduler ioScheduler, Scheduler computeScheduler, Scheduler mainThreadScheduler) {
        return new Schedulers(ioScheduler, computeScheduler, mainThreadScheduler);
    }
}
