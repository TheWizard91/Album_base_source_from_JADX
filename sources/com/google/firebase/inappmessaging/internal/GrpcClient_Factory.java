package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.InAppMessagingSdkServingGrpc;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class GrpcClient_Factory implements Factory<GrpcClient> {
    private final Provider<InAppMessagingSdkServingGrpc.InAppMessagingSdkServingBlockingStub> stubProvider;

    public GrpcClient_Factory(Provider<InAppMessagingSdkServingGrpc.InAppMessagingSdkServingBlockingStub> stubProvider2) {
        this.stubProvider = stubProvider2;
    }

    public GrpcClient get() {
        return new GrpcClient(this.stubProvider.get());
    }

    public static GrpcClient_Factory create(Provider<InAppMessagingSdkServingGrpc.InAppMessagingSdkServingBlockingStub> stubProvider2) {
        return new GrpcClient_Factory(stubProvider2);
    }

    public static GrpcClient newInstance(InAppMessagingSdkServingGrpc.InAppMessagingSdkServingBlockingStub stub) {
        return new GrpcClient(stub);
    }
}
