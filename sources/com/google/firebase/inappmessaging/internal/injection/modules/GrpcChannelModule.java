package com.google.firebase.inappmessaging.internal.injection.modules;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import p019io.grpc.Channel;
import p019io.grpc.ManagedChannelBuilder;

@Module
public class GrpcChannelModule {
    @Singleton
    @Provides
    @Named("host")
    public String providesServiceHost() {
        return "firebaseinappmessaging.googleapis.com";
    }

    @Singleton
    @Provides
    public Channel providesGrpcChannel(@Named("host") String host) {
        return ManagedChannelBuilder.forTarget(host).build();
    }
}
