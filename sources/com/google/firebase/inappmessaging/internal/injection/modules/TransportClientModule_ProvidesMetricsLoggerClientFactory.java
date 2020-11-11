package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.android.datatransport.TransportFactory;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.inappmessaging.internal.DeveloperListenerManager;
import com.google.firebase.inappmessaging.internal.MetricsLoggerClient;
import com.google.firebase.inappmessaging.internal.time.Clock;
import com.google.firebase.installations.FirebaseInstallationsApi;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class TransportClientModule_ProvidesMetricsLoggerClientFactory implements Factory<MetricsLoggerClient> {
    private final Provider<AnalyticsConnector> analyticsConnectorProvider;
    private final Provider<FirebaseApp> appProvider;
    private final Provider<Clock> clockProvider;
    private final Provider<DeveloperListenerManager> developerListenerManagerProvider;
    private final Provider<FirebaseInstallationsApi> firebaseInstallationsProvider;
    private final Provider<TransportFactory> transportFactoryProvider;

    public TransportClientModule_ProvidesMetricsLoggerClientFactory(Provider<FirebaseApp> appProvider2, Provider<TransportFactory> transportFactoryProvider2, Provider<AnalyticsConnector> analyticsConnectorProvider2, Provider<FirebaseInstallationsApi> firebaseInstallationsProvider2, Provider<Clock> clockProvider2, Provider<DeveloperListenerManager> developerListenerManagerProvider2) {
        this.appProvider = appProvider2;
        this.transportFactoryProvider = transportFactoryProvider2;
        this.analyticsConnectorProvider = analyticsConnectorProvider2;
        this.firebaseInstallationsProvider = firebaseInstallationsProvider2;
        this.clockProvider = clockProvider2;
        this.developerListenerManagerProvider = developerListenerManagerProvider2;
    }

    public MetricsLoggerClient get() {
        return providesMetricsLoggerClient(this.appProvider.get(), this.transportFactoryProvider.get(), this.analyticsConnectorProvider.get(), this.firebaseInstallationsProvider.get(), this.clockProvider.get(), this.developerListenerManagerProvider.get());
    }

    public static TransportClientModule_ProvidesMetricsLoggerClientFactory create(Provider<FirebaseApp> appProvider2, Provider<TransportFactory> transportFactoryProvider2, Provider<AnalyticsConnector> analyticsConnectorProvider2, Provider<FirebaseInstallationsApi> firebaseInstallationsProvider2, Provider<Clock> clockProvider2, Provider<DeveloperListenerManager> developerListenerManagerProvider2) {
        return new TransportClientModule_ProvidesMetricsLoggerClientFactory(appProvider2, transportFactoryProvider2, analyticsConnectorProvider2, firebaseInstallationsProvider2, clockProvider2, developerListenerManagerProvider2);
    }

    public static MetricsLoggerClient providesMetricsLoggerClient(FirebaseApp app, TransportFactory transportFactory, AnalyticsConnector analyticsConnector, FirebaseInstallationsApi firebaseInstallations, Clock clock, DeveloperListenerManager developerListenerManager) {
        return (MetricsLoggerClient) Preconditions.checkNotNull(TransportClientModule.providesMetricsLoggerClient(app, transportFactory, analyticsConnector, firebaseInstallations, clock, developerListenerManager), "Cannot return null from a non-@Nullable @Provides method");
    }
}
