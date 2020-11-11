package com.google.firebase.inappmessaging.display.internal.injection.modules;

import android.util.DisplayMetrics;
import com.google.firebase.inappmessaging.display.internal.InAppMessageLayoutConfig;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class InflaterConfigModule_ProvidesCardPortraitConfigFactory implements Factory<InAppMessageLayoutConfig> {
    private final Provider<DisplayMetrics> displayMetricsProvider;
    private final InflaterConfigModule module;

    public InflaterConfigModule_ProvidesCardPortraitConfigFactory(InflaterConfigModule module2, Provider<DisplayMetrics> displayMetricsProvider2) {
        this.module = module2;
        this.displayMetricsProvider = displayMetricsProvider2;
    }

    public InAppMessageLayoutConfig get() {
        return providesCardPortraitConfig(this.module, this.displayMetricsProvider.get());
    }

    public static InflaterConfigModule_ProvidesCardPortraitConfigFactory create(InflaterConfigModule module2, Provider<DisplayMetrics> displayMetricsProvider2) {
        return new InflaterConfigModule_ProvidesCardPortraitConfigFactory(module2, displayMetricsProvider2);
    }

    public static InAppMessageLayoutConfig providesCardPortraitConfig(InflaterConfigModule instance, DisplayMetrics displayMetrics) {
        return (InAppMessageLayoutConfig) Preconditions.checkNotNull(instance.providesCardPortraitConfig(displayMetrics), "Cannot return null from a non-@Nullable @Provides method");
    }
}
