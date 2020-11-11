package com.google.firebase.inappmessaging.internal.injection.modules;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import p019io.reactivex.Scheduler;

public final class SchedulerModule_ProvidesIOSchedulerFactory implements Factory<Scheduler> {
    private final SchedulerModule module;

    public SchedulerModule_ProvidesIOSchedulerFactory(SchedulerModule module2) {
        this.module = module2;
    }

    public Scheduler get() {
        return providesIOScheduler(this.module);
    }

    public static SchedulerModule_ProvidesIOSchedulerFactory create(SchedulerModule module2) {
        return new SchedulerModule_ProvidesIOSchedulerFactory(module2);
    }

    public static Scheduler providesIOScheduler(SchedulerModule instance) {
        return (Scheduler) Preconditions.checkNotNull(instance.providesIOScheduler(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
