package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.FirebaseApp;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ApiClientModule_ProvidesFirebaseAppFactory implements Factory<FirebaseApp> {
    private final ApiClientModule module;

    public ApiClientModule_ProvidesFirebaseAppFactory(ApiClientModule module2) {
        this.module = module2;
    }

    public FirebaseApp get() {
        return providesFirebaseApp(this.module);
    }

    public static ApiClientModule_ProvidesFirebaseAppFactory create(ApiClientModule module2) {
        return new ApiClientModule_ProvidesFirebaseAppFactory(module2);
    }

    public static FirebaseApp providesFirebaseApp(ApiClientModule instance) {
        return (FirebaseApp) Preconditions.checkNotNull(instance.providesFirebaseApp(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
