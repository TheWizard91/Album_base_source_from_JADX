package com.google.firebase.inappmessaging.internal.injection.modules;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ApplicationModule_ProvidesApplicationFactory implements Factory<Application> {
    private final ApplicationModule module;

    public ApplicationModule_ProvidesApplicationFactory(ApplicationModule module2) {
        this.module = module2;
    }

    public Application get() {
        return providesApplication(this.module);
    }

    public static ApplicationModule_ProvidesApplicationFactory create(ApplicationModule module2) {
        return new ApplicationModule_ProvidesApplicationFactory(module2);
    }

    public static Application providesApplication(ApplicationModule instance) {
        return (Application) Preconditions.checkNotNull(instance.providesApplication(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
