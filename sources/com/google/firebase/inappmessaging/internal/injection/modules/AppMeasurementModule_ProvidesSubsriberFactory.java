package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.events.Subscriber;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AppMeasurementModule_ProvidesSubsriberFactory implements Factory<Subscriber> {
    private final AppMeasurementModule module;

    public AppMeasurementModule_ProvidesSubsriberFactory(AppMeasurementModule module2) {
        this.module = module2;
    }

    public Subscriber get() {
        return providesSubsriber(this.module);
    }

    public static AppMeasurementModule_ProvidesSubsriberFactory create(AppMeasurementModule module2) {
        return new AppMeasurementModule_ProvidesSubsriberFactory(module2);
    }

    public static Subscriber providesSubsriber(AppMeasurementModule instance) {
        return (Subscriber) Preconditions.checkNotNull(instance.providesSubsriber(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
