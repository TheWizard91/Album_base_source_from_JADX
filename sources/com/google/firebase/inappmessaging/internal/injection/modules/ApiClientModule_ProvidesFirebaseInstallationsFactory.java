package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.installations.FirebaseInstallationsApi;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ApiClientModule_ProvidesFirebaseInstallationsFactory implements Factory<FirebaseInstallationsApi> {
    private final ApiClientModule module;

    public ApiClientModule_ProvidesFirebaseInstallationsFactory(ApiClientModule module2) {
        this.module = module2;
    }

    public FirebaseInstallationsApi get() {
        return providesFirebaseInstallations(this.module);
    }

    public static ApiClientModule_ProvidesFirebaseInstallationsFactory create(ApiClientModule module2) {
        return new ApiClientModule_ProvidesFirebaseInstallationsFactory(module2);
    }

    public static FirebaseInstallationsApi providesFirebaseInstallations(ApiClientModule instance) {
        return (FirebaseInstallationsApi) Preconditions.checkNotNull(instance.providesFirebaseInstallations(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
