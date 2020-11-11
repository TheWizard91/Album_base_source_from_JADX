package com.google.firebase.inappmessaging;

import android.app.Application;
import android.content.Context;
import com.google.android.datatransport.TransportFactory;
import com.google.firebase.FirebaseApp;
import com.google.firebase.abt.component.AbtComponent;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.events.Subscriber;
import com.google.firebase.inappmessaging.internal.AbtIntegrationHelper;
import com.google.firebase.inappmessaging.internal.ProgramaticContextualTriggers;
import com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent;
import com.google.firebase.inappmessaging.internal.injection.components.DaggerUniversalComponent;
import com.google.firebase.inappmessaging.internal.injection.components.UniversalComponent;
import com.google.firebase.inappmessaging.internal.injection.modules.AnalyticsEventsModule;
import com.google.firebase.inappmessaging.internal.injection.modules.ApiClientModule;
import com.google.firebase.inappmessaging.internal.injection.modules.AppMeasurementModule;
import com.google.firebase.inappmessaging.internal.injection.modules.ApplicationModule;
import com.google.firebase.inappmessaging.internal.injection.modules.GrpcClientModule;
import com.google.firebase.inappmessaging.internal.injection.modules.ProgrammaticContextualTriggerFlowableModule;
import com.google.firebase.installations.FirebaseInstallationsApi;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Arrays;
import java.util.List;

public class FirebaseInAppMessagingRegistrar implements ComponentRegistrar {
    public List<Component<?>> getComponents() {
        return Arrays.asList(new Component[]{Component.builder(FirebaseInAppMessaging.class).add(Dependency.required(Context.class)).add(Dependency.required(FirebaseInstallationsApi.class)).add(Dependency.required(FirebaseApp.class)).add(Dependency.required(AbtComponent.class)).add(Dependency.optional(AnalyticsConnector.class)).add(Dependency.required(TransportFactory.class)).add(Dependency.required(Subscriber.class)).factory(FirebaseInAppMessagingRegistrar$$Lambda$1.lambdaFactory$(this)).eagerInDefaultApp().build(), LibraryVersionComponent.create("fire-fiam", "19.1.0")});
    }

    /* access modifiers changed from: private */
    public FirebaseInAppMessaging providesFirebaseInAppMessaging(ComponentContainer container) {
        FirebaseApp firebaseApp = (FirebaseApp) container.get(FirebaseApp.class);
        UniversalComponent universalComponent = DaggerUniversalComponent.builder().applicationModule(new ApplicationModule((Application) firebaseApp.getApplicationContext())).appMeasurementModule(new AppMeasurementModule((AnalyticsConnector) container.get(AnalyticsConnector.class), (Subscriber) container.get(Subscriber.class))).analyticsEventsModule(new AnalyticsEventsModule()).programmaticContextualTriggerFlowableModule(new ProgrammaticContextualTriggerFlowableModule(new ProgramaticContextualTriggers())).build();
        return DaggerAppComponent.builder().abtIntegrationHelper(new AbtIntegrationHelper(((AbtComponent) container.get(AbtComponent.class)).get("fiam"))).apiClientModule(new ApiClientModule(firebaseApp, (FirebaseInstallationsApi) container.get(FirebaseInstallationsApi.class), universalComponent.clock())).grpcClientModule(new GrpcClientModule(firebaseApp)).universalComponent(universalComponent).transportFactory((TransportFactory) container.get(TransportFactory.class)).build().providesFirebaseInAppMessaging();
    }
}
