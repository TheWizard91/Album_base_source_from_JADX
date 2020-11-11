package com.google.firebase.inappmessaging.display.internal.injection.modules;

import android.view.LayoutInflater;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class InflaterModule_ProvidesInflaterserviceFactory implements Factory<LayoutInflater> {
    private final InflaterModule module;

    public InflaterModule_ProvidesInflaterserviceFactory(InflaterModule module2) {
        this.module = module2;
    }

    public LayoutInflater get() {
        return providesInflaterservice(this.module);
    }

    public static InflaterModule_ProvidesInflaterserviceFactory create(InflaterModule module2) {
        return new InflaterModule_ProvidesInflaterserviceFactory(module2);
    }

    public static LayoutInflater providesInflaterservice(InflaterModule instance) {
        return (LayoutInflater) Preconditions.checkNotNull(instance.providesInflaterservice(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
