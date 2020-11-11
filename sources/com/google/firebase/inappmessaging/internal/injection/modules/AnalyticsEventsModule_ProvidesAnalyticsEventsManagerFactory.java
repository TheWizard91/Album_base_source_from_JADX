package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.inappmessaging.internal.AnalyticsEventsManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AnalyticsEventsModule_ProvidesAnalyticsEventsManagerFactory implements Factory<AnalyticsEventsManager> {
    private final Provider<AnalyticsConnector> analyticsConnectorProvider;
    private final AnalyticsEventsModule module;

    public AnalyticsEventsModule_ProvidesAnalyticsEventsManagerFactory(AnalyticsEventsModule module2, Provider<AnalyticsConnector> analyticsConnectorProvider2) {
        this.module = module2;
        this.analyticsConnectorProvider = analyticsConnectorProvider2;
    }

    public AnalyticsEventsManager get() {
        return providesAnalyticsEventsManager(this.module, this.analyticsConnectorProvider.get());
    }

    public static AnalyticsEventsModule_ProvidesAnalyticsEventsManagerFactory create(AnalyticsEventsModule module2, Provider<AnalyticsConnector> analyticsConnectorProvider2) {
        return new AnalyticsEventsModule_ProvidesAnalyticsEventsManagerFactory(module2, analyticsConnectorProvider2);
    }

    public static AnalyticsEventsManager providesAnalyticsEventsManager(AnalyticsEventsModule instance, AnalyticsConnector analyticsConnector) {
        return (AnalyticsEventsManager) Preconditions.checkNotNull(instance.providesAnalyticsEventsManager(analyticsConnector), "Cannot return null from a non-@Nullable @Provides method");
    }
}
