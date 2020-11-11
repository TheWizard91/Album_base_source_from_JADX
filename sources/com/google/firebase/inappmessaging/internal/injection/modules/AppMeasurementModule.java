package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.events.Subscriber;
import com.google.firebase.inappmessaging.internal.StubAnalyticsConnector;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class AppMeasurementModule {
    private AnalyticsConnector analyticsConnector;
    private Subscriber firebaseEventsSubscriber;

    public AppMeasurementModule(AnalyticsConnector analyticsConnector2, Subscriber firebaseEventsSubscriber2) {
        this.analyticsConnector = analyticsConnector2 != null ? analyticsConnector2 : StubAnalyticsConnector.instance;
        this.firebaseEventsSubscriber = firebaseEventsSubscriber2;
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public AnalyticsConnector providesAnalyticsConnector() {
        return this.analyticsConnector;
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public Subscriber providesSubsriber() {
        return this.firebaseEventsSubscriber;
    }
}
