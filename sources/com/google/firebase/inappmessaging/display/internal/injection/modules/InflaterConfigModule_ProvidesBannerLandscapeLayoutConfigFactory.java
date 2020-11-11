package com.google.firebase.inappmessaging.display.internal.injection.modules;

import android.util.DisplayMetrics;
import com.google.firebase.inappmessaging.display.internal.InAppMessageLayoutConfig;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class InflaterConfigModule_ProvidesBannerLandscapeLayoutConfigFactory implements Factory<InAppMessageLayoutConfig> {
    private final Provider<DisplayMetrics> displayMetricsProvider;
    private final InflaterConfigModule module;

    public InflaterConfigModule_ProvidesBannerLandscapeLayoutConfigFactory(InflaterConfigModule module2, Provider<DisplayMetrics> displayMetricsProvider2) {
        this.module = module2;
        this.displayMetricsProvider = displayMetricsProvider2;
    }

    public InAppMessageLayoutConfig get() {
        return providesBannerLandscapeLayoutConfig(this.module, this.displayMetricsProvider.get());
    }

    public static InflaterConfigModule_ProvidesBannerLandscapeLayoutConfigFactory create(InflaterConfigModule module2, Provider<DisplayMetrics> displayMetricsProvider2) {
        return new InflaterConfigModule_ProvidesBannerLandscapeLayoutConfigFactory(module2, displayMetricsProvider2);
    }

    public static InAppMessageLayoutConfig providesBannerLandscapeLayoutConfig(InflaterConfigModule instance, DisplayMetrics displayMetrics) {
        return (InAppMessageLayoutConfig) Preconditions.checkNotNull(instance.providesBannerLandscapeLayoutConfig(displayMetrics), "Cannot return null from a non-@Nullable @Provides method");
    }
}
