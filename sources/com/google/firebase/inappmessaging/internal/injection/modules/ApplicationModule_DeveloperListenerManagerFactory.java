package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.inappmessaging.internal.DeveloperListenerManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ApplicationModule_DeveloperListenerManagerFactory implements Factory<DeveloperListenerManager> {
    private final ApplicationModule module;

    public ApplicationModule_DeveloperListenerManagerFactory(ApplicationModule module2) {
        this.module = module2;
    }

    public DeveloperListenerManager get() {
        return developerListenerManager(this.module);
    }

    public static ApplicationModule_DeveloperListenerManagerFactory create(ApplicationModule module2) {
        return new ApplicationModule_DeveloperListenerManagerFactory(module2);
    }

    public static DeveloperListenerManager developerListenerManager(ApplicationModule instance) {
        return (DeveloperListenerManager) Preconditions.checkNotNull(instance.developerListenerManager(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
