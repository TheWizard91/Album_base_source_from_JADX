package com.google.firebase.inappmessaging.display.internal.injection.modules;

import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class HeadlessInAppMessagingModule_ProvidesHeadlesssSingletonFactory implements Factory<FirebaseInAppMessaging> {
    private final HeadlessInAppMessagingModule module;

    public HeadlessInAppMessagingModule_ProvidesHeadlesssSingletonFactory(HeadlessInAppMessagingModule module2) {
        this.module = module2;
    }

    public FirebaseInAppMessaging get() {
        return providesHeadlesssSingleton(this.module);
    }

    public static HeadlessInAppMessagingModule_ProvidesHeadlesssSingletonFactory create(HeadlessInAppMessagingModule module2) {
        return new HeadlessInAppMessagingModule_ProvidesHeadlesssSingletonFactory(module2);
    }

    public static FirebaseInAppMessaging providesHeadlesssSingleton(HeadlessInAppMessagingModule instance) {
        return (FirebaseInAppMessaging) Preconditions.checkNotNull(instance.providesHeadlesssSingleton(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
