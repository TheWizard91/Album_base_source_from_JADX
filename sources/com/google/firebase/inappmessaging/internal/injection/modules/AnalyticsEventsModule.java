package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.inappmessaging.internal.AnalyticsEventsManager;
import com.google.firebase.inappmessaging.internal.injection.qualifiers.AnalyticsListener;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import p019io.reactivex.flowables.ConnectableFlowable;

@Module
public class AnalyticsEventsModule {
    /* access modifiers changed from: package-private */
    @Singleton
    @AnalyticsListener
    @Provides
    public ConnectableFlowable<String> providesAnalyticsConnectorEvents(AnalyticsEventsManager analyticsEventsManager) {
        return analyticsEventsManager.getAnalyticsEventsFlowable();
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public AnalyticsEventsManager providesAnalyticsEventsManager(AnalyticsConnector analyticsConnector) {
        return new AnalyticsEventsManager(analyticsConnector);
    }
}
