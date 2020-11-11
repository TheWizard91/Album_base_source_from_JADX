package com.google.firebase.inappmessaging.internal.injection.modules;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import p019io.reactivex.flowables.ConnectableFlowable;

public final class ForegroundFlowableModule_ProvidesAppForegroundEventStreamFactory implements Factory<ConnectableFlowable<String>> {
    private final Provider<Application> applicationProvider;
    private final ForegroundFlowableModule module;

    public ForegroundFlowableModule_ProvidesAppForegroundEventStreamFactory(ForegroundFlowableModule module2, Provider<Application> applicationProvider2) {
        this.module = module2;
        this.applicationProvider = applicationProvider2;
    }

    public ConnectableFlowable<String> get() {
        return providesAppForegroundEventStream(this.module, this.applicationProvider.get());
    }

    public static ForegroundFlowableModule_ProvidesAppForegroundEventStreamFactory create(ForegroundFlowableModule module2, Provider<Application> applicationProvider2) {
        return new ForegroundFlowableModule_ProvidesAppForegroundEventStreamFactory(module2, applicationProvider2);
    }

    public static ConnectableFlowable<String> providesAppForegroundEventStream(ForegroundFlowableModule instance, Application application) {
        return (ConnectableFlowable) Preconditions.checkNotNull(instance.providesAppForegroundEventStream(application), "Cannot return null from a non-@Nullable @Provides method");
    }
}
