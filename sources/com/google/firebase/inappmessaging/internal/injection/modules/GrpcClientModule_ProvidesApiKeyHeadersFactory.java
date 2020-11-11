package com.google.firebase.inappmessaging.internal.injection.modules;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import p019io.grpc.Metadata;

public final class GrpcClientModule_ProvidesApiKeyHeadersFactory implements Factory<Metadata> {
    private final GrpcClientModule module;

    public GrpcClientModule_ProvidesApiKeyHeadersFactory(GrpcClientModule module2) {
        this.module = module2;
    }

    public Metadata get() {
        return providesApiKeyHeaders(this.module);
    }

    public static GrpcClientModule_ProvidesApiKeyHeadersFactory create(GrpcClientModule module2) {
        return new GrpcClientModule_ProvidesApiKeyHeadersFactory(module2);
    }

    public static Metadata providesApiKeyHeaders(GrpcClientModule instance) {
        return (Metadata) Preconditions.checkNotNull(instance.providesApiKeyHeaders(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
