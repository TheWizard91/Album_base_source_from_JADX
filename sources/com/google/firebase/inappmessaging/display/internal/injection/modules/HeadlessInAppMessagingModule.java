package com.google.firebase.inappmessaging.display.internal.injection.modules;

import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import dagger.Module;
import dagger.Provides;

@Module
public class HeadlessInAppMessagingModule {
    private final FirebaseInAppMessaging headless;

    public HeadlessInAppMessagingModule(FirebaseInAppMessaging headless2) {
        this.headless = headless2;
    }

    /* access modifiers changed from: package-private */
    @Provides
    public FirebaseInAppMessaging providesHeadlesssSingleton() {
        return this.headless;
    }
}
