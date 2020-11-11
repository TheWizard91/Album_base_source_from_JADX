package com.google.firebase.inappmessaging.display.internal.injection.modules;

import android.app.Application;
import android.util.DisplayMetrics;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class InflaterConfigModule_ProvidesDisplayMetricsFactory implements Factory<DisplayMetrics> {
    private final Provider<Application> applicationProvider;
    private final InflaterConfigModule module;

    public InflaterConfigModule_ProvidesDisplayMetricsFactory(InflaterConfigModule module2, Provider<Application> applicationProvider2) {
        this.module = module2;
        this.applicationProvider = applicationProvider2;
    }

    public DisplayMetrics get() {
        return providesDisplayMetrics(this.module, this.applicationProvider.get());
    }

    public static InflaterConfigModule_ProvidesDisplayMetricsFactory create(InflaterConfigModule module2, Provider<Application> applicationProvider2) {
        return new InflaterConfigModule_ProvidesDisplayMetricsFactory(module2, applicationProvider2);
    }

    public static DisplayMetrics providesDisplayMetrics(InflaterConfigModule instance, Application application) {
        return (DisplayMetrics) Preconditions.checkNotNull(instance.providesDisplayMetrics(application), "Cannot return null from a non-@Nullable @Provides method");
    }
}
