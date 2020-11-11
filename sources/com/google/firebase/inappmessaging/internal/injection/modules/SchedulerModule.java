package com.google.firebase.inappmessaging.internal.injection.modules;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import p019io.reactivex.Scheduler;
import p019io.reactivex.android.schedulers.AndroidSchedulers;
import p019io.reactivex.schedulers.Schedulers;

@Module
public class SchedulerModule {
    @Singleton
    @Provides
    @Named("io")
    public Scheduler providesIOScheduler() {
        return Schedulers.m207io();
    }

    @Singleton
    @Provides
    @Named("compute")
    public Scheduler providesComputeScheduler() {
        return Schedulers.computation();
    }

    @Singleton
    @Provides
    @Named("main")
    public Scheduler providesMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
