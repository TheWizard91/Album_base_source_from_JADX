package com.google.firebase.inappmessaging.display.internal.injection.modules;

import com.google.firebase.inappmessaging.model.InAppMessage;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class InflaterModule_ProvidesBannerMessageFactory implements Factory<InAppMessage> {
    private final InflaterModule module;

    public InflaterModule_ProvidesBannerMessageFactory(InflaterModule module2) {
        this.module = module2;
    }

    public InAppMessage get() {
        return providesBannerMessage(this.module);
    }

    public static InflaterModule_ProvidesBannerMessageFactory create(InflaterModule module2) {
        return new InflaterModule_ProvidesBannerMessageFactory(module2);
    }

    public static InAppMessage providesBannerMessage(InflaterModule instance) {
        return (InAppMessage) Preconditions.checkNotNull(instance.providesBannerMessage(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
