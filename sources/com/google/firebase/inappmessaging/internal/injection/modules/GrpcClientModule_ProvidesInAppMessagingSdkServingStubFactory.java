package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.InAppMessagingSdkServingGrpc;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import p019io.grpc.Channel;
import p019io.grpc.Metadata;

public final class GrpcClientModule_ProvidesInAppMessagingSdkServingStubFactory implements Factory<InAppMessagingSdkServingGrpc.InAppMessagingSdkServingBlockingStub> {
    private final Provider<Channel> channelProvider;
    private final Provider<Metadata> metadataProvider;
    private final GrpcClientModule module;

    public GrpcClientModule_ProvidesInAppMessagingSdkServingStubFactory(GrpcClientModule module2, Provider<Channel> channelProvider2, Provider<Metadata> metadataProvider2) {
        this.module = module2;
        this.channelProvider = channelProvider2;
        this.metadataProvider = metadataProvider2;
    }

    public InAppMessagingSdkServingGrpc.InAppMessagingSdkServingBlockingStub get() {
        return providesInAppMessagingSdkServingStub(this.module, this.channelProvider.get(), this.metadataProvider.get());
    }

    public static GrpcClientModule_ProvidesInAppMessagingSdkServingStubFactory create(GrpcClientModule module2, Provider<Channel> channelProvider2, Provider<Metadata> metadataProvider2) {
        return new GrpcClientModule_ProvidesInAppMessagingSdkServingStubFactory(module2, channelProvider2, metadataProvider2);
    }

    public static InAppMessagingSdkServingGrpc.InAppMessagingSdkServingBlockingStub providesInAppMessagingSdkServingStub(GrpcClientModule instance, Channel channel, Metadata metadata) {
        return (InAppMessagingSdkServingGrpc.InAppMessagingSdkServingBlockingStub) Preconditions.checkNotNull(instance.providesInAppMessagingSdkServingStub(channel, metadata), "Cannot return null from a non-@Nullable @Provides method");
    }
}
