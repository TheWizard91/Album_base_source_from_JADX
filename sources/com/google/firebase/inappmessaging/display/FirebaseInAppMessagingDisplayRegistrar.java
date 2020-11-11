package com.google.firebase.inappmessaging.display;

import android.app.Application;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import com.google.firebase.inappmessaging.display.internal.injection.components.DaggerAppComponent;
import com.google.firebase.inappmessaging.display.internal.injection.components.DaggerUniversalComponent;
import com.google.firebase.inappmessaging.display.internal.injection.modules.ApplicationModule;
import com.google.firebase.inappmessaging.display.internal.injection.modules.HeadlessInAppMessagingModule;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Arrays;
import java.util.List;

public class FirebaseInAppMessagingDisplayRegistrar implements ComponentRegistrar {
    public List<Component<?>> getComponents() {
        return Arrays.asList(new Component[]{Component.builder(FirebaseInAppMessagingDisplay.class).add(Dependency.required(FirebaseApp.class)).add(Dependency.required(AnalyticsConnector.class)).add(Dependency.required(FirebaseInAppMessaging.class)).factory(FirebaseInAppMessagingDisplayRegistrar$$Lambda$1.lambdaFactory$(this)).eagerInDefaultApp().build(), LibraryVersionComponent.create("fire-fiamd", "19.1.0")});
    }

    /* access modifiers changed from: private */
    public FirebaseInAppMessagingDisplay buildFirebaseInAppMessagingUI(ComponentContainer container) {
        Application firebaseApplication = (Application) FirebaseApp.getInstance().getApplicationContext();
        FirebaseInAppMessagingDisplay firebaseInAppMessagingDisplay = DaggerAppComponent.builder().universalComponent(DaggerUniversalComponent.builder().applicationModule(new ApplicationModule(firebaseApplication)).build()).headlessInAppMessagingModule(new HeadlessInAppMessagingModule((FirebaseInAppMessaging) container.get(FirebaseInAppMessaging.class))).build().providesFirebaseInAppMessagingUI();
        firebaseApplication.registerActivityLifecycleCallbacks(firebaseInAppMessagingDisplay);
        return firebaseInAppMessagingDisplay;
    }
}
