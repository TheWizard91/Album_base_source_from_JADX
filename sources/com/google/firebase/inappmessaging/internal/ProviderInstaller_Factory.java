package com.google.firebase.inappmessaging.internal;

import android.app.Application;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ProviderInstaller_Factory implements Factory<ProviderInstaller> {
    private final Provider<Application> applicationProvider;

    public ProviderInstaller_Factory(Provider<Application> applicationProvider2) {
        this.applicationProvider = applicationProvider2;
    }

    public ProviderInstaller get() {
        return new ProviderInstaller(this.applicationProvider.get());
    }

    public static ProviderInstaller_Factory create(Provider<Application> applicationProvider2) {
        return new ProviderInstaller_Factory(applicationProvider2);
    }

    public static ProviderInstaller newInstance(Application application) {
        return new ProviderInstaller(application);
    }
}
