package com.google.firebase.inappmessaging.internal.injection.modules;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import p019io.reactivex.Scheduler;

public final class SchedulerModule_ProvidesMainThreadSchedulerFactory implements Factory<Scheduler> {
    private final SchedulerModule module;

    public SchedulerModule_ProvidesMainThreadSchedulerFactory(SchedulerModule module2) {
        this.module = module2;
    }

    public Scheduler get() {
        return providesMainThreadScheduler(this.module);
    }

    public static SchedulerModule_ProvidesMainThreadSchedulerFactory create(SchedulerModule module2) {
        return new SchedulerModule_ProvidesMainThreadSchedulerFactory(module2);
    }

    public static Scheduler providesMainThreadScheduler(SchedulerModule instance) {
        return (Scheduler) Preconditions.checkNotNull(instance.providesMainThreadScheduler(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
