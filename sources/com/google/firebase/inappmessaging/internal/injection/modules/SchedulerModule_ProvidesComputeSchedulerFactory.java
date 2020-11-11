package com.google.firebase.inappmessaging.internal.injection.modules;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import p019io.reactivex.Scheduler;

public final class SchedulerModule_ProvidesComputeSchedulerFactory implements Factory<Scheduler> {
    private final SchedulerModule module;

    public SchedulerModule_ProvidesComputeSchedulerFactory(SchedulerModule module2) {
        this.module = module2;
    }

    public Scheduler get() {
        return providesComputeScheduler(this.module);
    }

    public static SchedulerModule_ProvidesComputeSchedulerFactory create(SchedulerModule module2) {
        return new SchedulerModule_ProvidesComputeSchedulerFactory(module2);
    }

    public static Scheduler providesComputeScheduler(SchedulerModule instance) {
        return (Scheduler) Preconditions.checkNotNull(instance.providesComputeScheduler(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
