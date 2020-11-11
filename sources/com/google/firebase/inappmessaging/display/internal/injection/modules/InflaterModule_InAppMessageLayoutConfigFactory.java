package com.google.firebase.inappmessaging.display.internal.injection.modules;

import com.google.firebase.inappmessaging.display.internal.InAppMessageLayoutConfig;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class InflaterModule_InAppMessageLayoutConfigFactory implements Factory<InAppMessageLayoutConfig> {
    private final InflaterModule module;

    public InflaterModule_InAppMessageLayoutConfigFactory(InflaterModule module2) {
        this.module = module2;
    }

    public InAppMessageLayoutConfig get() {
        return inAppMessageLayoutConfig(this.module);
    }

    public static InflaterModule_InAppMessageLayoutConfigFactory create(InflaterModule module2) {
        return new InflaterModule_InAppMessageLayoutConfigFactory(module2);
    }

    public static InAppMessageLayoutConfig inAppMessageLayoutConfig(InflaterModule instance) {
        return (InAppMessageLayoutConfig) Preconditions.checkNotNull(instance.inAppMessageLayoutConfig(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
