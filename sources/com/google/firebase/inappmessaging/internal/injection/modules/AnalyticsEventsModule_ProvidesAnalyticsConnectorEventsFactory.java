package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.inappmessaging.internal.AnalyticsEventsManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import p019io.reactivex.flowables.ConnectableFlowable;

public final class AnalyticsEventsModule_ProvidesAnalyticsConnectorEventsFactory implements Factory<ConnectableFlowable<String>> {
    private final Provider<AnalyticsEventsManager> analyticsEventsManagerProvider;
    private final AnalyticsEventsModule module;

    public AnalyticsEventsModule_ProvidesAnalyticsConnectorEventsFactory(AnalyticsEventsModule module2, Provider<AnalyticsEventsManager> analyticsEventsManagerProvider2) {
        this.module = module2;
        this.analyticsEventsManagerProvider = analyticsEventsManagerProvider2;
    }

    public ConnectableFlowable<String> get() {
        return providesAnalyticsConnectorEvents(this.module, this.analyticsEventsManagerProvider.get());
    }

    public static AnalyticsEventsModule_ProvidesAnalyticsConnectorEventsFactory create(AnalyticsEventsModule module2, Provider<AnalyticsEventsManager> analyticsEventsManagerProvider2) {
        return new AnalyticsEventsModule_ProvidesAnalyticsConnectorEventsFactory(module2, analyticsEventsManagerProvider2);
    }

    public static ConnectableFlowable<String> providesAnalyticsConnectorEvents(AnalyticsEventsModule instance, AnalyticsEventsManager analyticsEventsManager) {
        return (ConnectableFlowable) Preconditions.checkNotNull(instance.providesAnalyticsConnectorEvents(analyticsEventsManager), "Cannot return null from a non-@Nullable @Provides method");
    }
}
