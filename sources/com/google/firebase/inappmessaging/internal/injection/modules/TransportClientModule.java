package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.android.datatransport.TransportFactory;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.inappmessaging.internal.DeveloperListenerManager;
import com.google.firebase.inappmessaging.internal.MetricsLoggerClient;
import com.google.firebase.inappmessaging.internal.time.Clock;
import com.google.firebase.installations.FirebaseInstallationsApi;
import dagger.Module;
import dagger.Provides;

@Module
public class TransportClientModule {
    private static final String TRANSPORT_NAME = "FIREBASE_INAPPMESSAGING";

    @Provides
    static MetricsLoggerClient providesMetricsLoggerClient(FirebaseApp app, TransportFactory transportFactory, AnalyticsConnector analyticsConnector, FirebaseInstallationsApi firebaseInstallations, Clock clock, DeveloperListenerManager developerListenerManager) {
        return new MetricsLoggerClient(TransportClientModule$$Lambda$2.lambdaFactory$(transportFactory.getTransport(TRANSPORT_NAME, byte[].class, TransportClientModule$$Lambda$1.lambdaFactory$())), analyticsConnector, app, firebaseInstallations, clock, developerListenerManager);
    }

    static /* synthetic */ byte[] lambda$providesMetricsLoggerClient$0(byte[] b) {
        return b;
    }
}
