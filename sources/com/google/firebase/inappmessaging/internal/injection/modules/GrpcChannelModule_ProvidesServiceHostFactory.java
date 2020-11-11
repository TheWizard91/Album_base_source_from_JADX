package com.google.firebase.inappmessaging.internal.injection.modules;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class GrpcChannelModule_ProvidesServiceHostFactory implements Factory<String> {
    private final GrpcChannelModule module;

    public GrpcChannelModule_ProvidesServiceHostFactory(GrpcChannelModule module2) {
        this.module = module2;
    }

    public String get() {
        return providesServiceHost(this.module);
    }

    public static GrpcChannelModule_ProvidesServiceHostFactory create(GrpcChannelModule module2) {
        return new GrpcChannelModule_ProvidesServiceHostFactory(module2);
    }

    public static String providesServiceHost(GrpcChannelModule instance) {
        return (String) Preconditions.checkNotNull(instance.providesServiceHost(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
