package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.inappmessaging.internal.time.Clock;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class SystemClockModule_ProvidesSystemClockModuleFactory implements Factory<Clock> {
    private final SystemClockModule module;

    public SystemClockModule_ProvidesSystemClockModuleFactory(SystemClockModule module2) {
        this.module = module2;
    }

    public Clock get() {
        return providesSystemClockModule(this.module);
    }

    public static SystemClockModule_ProvidesSystemClockModuleFactory create(SystemClockModule module2) {
        return new SystemClockModule_ProvidesSystemClockModuleFactory(module2);
    }

    public static Clock providesSystemClockModule(SystemClockModule instance) {
        return (Clock) Preconditions.checkNotNull(instance.providesSystemClockModule(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
