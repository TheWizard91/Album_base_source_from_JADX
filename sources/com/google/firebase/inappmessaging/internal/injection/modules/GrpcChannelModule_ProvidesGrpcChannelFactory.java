package com.google.firebase.inappmessaging.internal.injection.modules;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import p019io.grpc.Channel;

public final class GrpcChannelModule_ProvidesGrpcChannelFactory implements Factory<Channel> {
    private final Provider<String> hostProvider;
    private final GrpcChannelModule module;

    public GrpcChannelModule_ProvidesGrpcChannelFactory(GrpcChannelModule module2, Provider<String> hostProvider2) {
        this.module = module2;
        this.hostProvider = hostProvider2;
    }

    public Channel get() {
        return providesGrpcChannel(this.module, this.hostProvider.get());
    }

    public static GrpcChannelModule_ProvidesGrpcChannelFactory create(GrpcChannelModule module2, Provider<String> hostProvider2) {
        return new GrpcChannelModule_ProvidesGrpcChannelFactory(module2, hostProvider2);
    }

    public static Channel providesGrpcChannel(GrpcChannelModule instance, String host) {
        return (Channel) Preconditions.checkNotNull(instance.providesGrpcChannel(host), "Cannot return null from a non-@Nullable @Provides method");
    }
}
