package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.analytics.connector.AnalyticsConnector;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AppMeasurementModule_ProvidesAnalyticsConnectorFactory implements Factory<AnalyticsConnector> {
    private final AppMeasurementModule module;

    public AppMeasurementModule_ProvidesAnalyticsConnectorFactory(AppMeasurementModule module2) {
        this.module = module2;
    }

    public AnalyticsConnector get() {
        return providesAnalyticsConnector(this.module);
    }

    public static AppMeasurementModule_ProvidesAnalyticsConnectorFactory create(AppMeasurementModule module2) {
        return new AppMeasurementModule_ProvidesAnalyticsConnectorFactory(module2);
    }

    public static AnalyticsConnector providesAnalyticsConnector(AppMeasurementModule instance) {
        return (AnalyticsConnector) Preconditions.checkNotNull(instance.providesAnalyticsConnector(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
