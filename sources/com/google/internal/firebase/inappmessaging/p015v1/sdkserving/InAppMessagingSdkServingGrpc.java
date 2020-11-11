package com.google.internal.firebase.inappmessaging.p015v1.sdkserving;

import com.google.common.util.concurrent.ListenableFuture;
import p019io.grpc.BindableService;
import p019io.grpc.CallOptions;
import p019io.grpc.Channel;
import p019io.grpc.MethodDescriptor;
import p019io.grpc.ServerServiceDefinition;
import p019io.grpc.ServiceDescriptor;
import p019io.grpc.protobuf.lite.ProtoLiteUtils;
import p019io.grpc.stub.AbstractAsyncStub;
import p019io.grpc.stub.AbstractBlockingStub;
import p019io.grpc.stub.AbstractFutureStub;
import p019io.grpc.stub.AbstractStub;
import p019io.grpc.stub.ClientCalls;
import p019io.grpc.stub.ServerCalls;
import p019io.grpc.stub.StreamObserver;

/* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.InAppMessagingSdkServingGrpc */
public final class InAppMessagingSdkServingGrpc {
    private static final int METHODID_FETCH_ELIGIBLE_CAMPAIGNS = 0;
    public static final String SERVICE_NAME = "google.internal.firebase.inappmessaging.v1.sdkserving.InAppMessagingSdkServing";
    private static volatile MethodDescriptor<FetchEligibleCampaignsRequest, FetchEligibleCampaignsResponse> getFetchEligibleCampaignsMethod;
    private static volatile ServiceDescriptor serviceDescriptor;

    private InAppMessagingSdkServingGrpc() {
    }

    public static MethodDescriptor<FetchEligibleCampaignsRequest, FetchEligibleCampaignsResponse> getFetchEligibleCampaignsMethod() {
        MethodDescriptor<FetchEligibleCampaignsRequest, FetchEligibleCampaignsResponse> methodDescriptor = getFetchEligibleCampaignsMethod;
        MethodDescriptor<FetchEligibleCampaignsRequest, FetchEligibleCampaignsResponse> getFetchEligibleCampaignsMethod2 = methodDescriptor;
        if (methodDescriptor == null) {
            synchronized (InAppMessagingSdkServingGrpc.class) {
                MethodDescriptor<FetchEligibleCampaignsRequest, FetchEligibleCampaignsResponse> methodDescriptor2 = getFetchEligibleCampaignsMethod;
                getFetchEligibleCampaignsMethod2 = methodDescriptor2;
                if (methodDescriptor2 == null) {
                    MethodDescriptor<FetchEligibleCampaignsRequest, FetchEligibleCampaignsResponse> build = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "FetchEligibleCampaigns")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(FetchEligibleCampaignsRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(FetchEligibleCampaignsResponse.getDefaultInstance())).build();
                    getFetchEligibleCampaignsMethod2 = build;
                    getFetchEligibleCampaignsMethod = build;
                }
            }
        }
        return getFetchEligibleCampaignsMethod2;
    }

    public static InAppMessagingSdkServingStub newStub(Channel channel) {
        return (InAppMessagingSdkServingStub) InAppMessagingSdkServingStub.newStub(new AbstractStub.StubFactory<InAppMessagingSdkServingStub>() {
            public InAppMessagingSdkServingStub newStub(Channel channel, CallOptions callOptions) {
                return new InAppMessagingSdkServingStub(channel, callOptions);
            }
        }, channel);
    }

    public static InAppMessagingSdkServingBlockingStub newBlockingStub(Channel channel) {
        return (InAppMessagingSdkServingBlockingStub) InAppMessagingSdkServingBlockingStub.newStub(new AbstractStub.StubFactory<InAppMessagingSdkServingBlockingStub>() {
            public InAppMessagingSdkServingBlockingStub newStub(Channel channel, CallOptions callOptions) {
                return new InAppMessagingSdkServingBlockingStub(channel, callOptions);
            }
        }, channel);
    }

    public static InAppMessagingSdkServingFutureStub newFutureStub(Channel channel) {
        return (InAppMessagingSdkServingFutureStub) InAppMessagingSdkServingFutureStub.newStub(new AbstractStub.StubFactory<InAppMessagingSdkServingFutureStub>() {
            public InAppMessagingSdkServingFutureStub newStub(Channel channel, CallOptions callOptions) {
                return new InAppMessagingSdkServingFutureStub(channel, callOptions);
            }
        }, channel);
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.InAppMessagingSdkServingGrpc$InAppMessagingSdkServingImplBase */
    public static abstract class InAppMessagingSdkServingImplBase implements BindableService {
        public void fetchEligibleCampaigns(FetchEligibleCampaignsRequest request, StreamObserver<FetchEligibleCampaignsResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(InAppMessagingSdkServingGrpc.getFetchEligibleCampaignsMethod(), responseObserver);
        }

        public final ServerServiceDefinition bindService() {
            return ServerServiceDefinition.builder(InAppMessagingSdkServingGrpc.getServiceDescriptor()).addMethod(InAppMessagingSdkServingGrpc.getFetchEligibleCampaignsMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(this, 0))).build();
        }
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.InAppMessagingSdkServingGrpc$InAppMessagingSdkServingStub */
    public static final class InAppMessagingSdkServingStub extends AbstractAsyncStub<InAppMessagingSdkServingStub> {
        private InAppMessagingSdkServingStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        /* access modifiers changed from: protected */
        public InAppMessagingSdkServingStub build(Channel channel, CallOptions callOptions) {
            return new InAppMessagingSdkServingStub(channel, callOptions);
        }

        public void fetchEligibleCampaigns(FetchEligibleCampaignsRequest request, StreamObserver<FetchEligibleCampaignsResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(getChannel().newCall(InAppMessagingSdkServingGrpc.getFetchEligibleCampaignsMethod(), getCallOptions()), request, responseObserver);
        }
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.InAppMessagingSdkServingGrpc$InAppMessagingSdkServingBlockingStub */
    public static final class InAppMessagingSdkServingBlockingStub extends AbstractBlockingStub<InAppMessagingSdkServingBlockingStub> {
        private InAppMessagingSdkServingBlockingStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        /* access modifiers changed from: protected */
        public InAppMessagingSdkServingBlockingStub build(Channel channel, CallOptions callOptions) {
            return new InAppMessagingSdkServingBlockingStub(channel, callOptions);
        }

        public FetchEligibleCampaignsResponse fetchEligibleCampaigns(FetchEligibleCampaignsRequest request) {
            return (FetchEligibleCampaignsResponse) ClientCalls.blockingUnaryCall(getChannel(), InAppMessagingSdkServingGrpc.getFetchEligibleCampaignsMethod(), getCallOptions(), request);
        }
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.InAppMessagingSdkServingGrpc$InAppMessagingSdkServingFutureStub */
    public static final class InAppMessagingSdkServingFutureStub extends AbstractFutureStub<InAppMessagingSdkServingFutureStub> {
        private InAppMessagingSdkServingFutureStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        /* access modifiers changed from: protected */
        public InAppMessagingSdkServingFutureStub build(Channel channel, CallOptions callOptions) {
            return new InAppMessagingSdkServingFutureStub(channel, callOptions);
        }

        public ListenableFuture<FetchEligibleCampaignsResponse> fetchEligibleCampaigns(FetchEligibleCampaignsRequest request) {
            return ClientCalls.futureUnaryCall(getChannel().newCall(InAppMessagingSdkServingGrpc.getFetchEligibleCampaignsMethod(), getCallOptions()), request);
        }
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.InAppMessagingSdkServingGrpc$MethodHandlers */
    private static final class MethodHandlers<Req, Resp> implements ServerCalls.UnaryMethod<Req, Resp>, ServerCalls.ServerStreamingMethod<Req, Resp>, ServerCalls.ClientStreamingMethod<Req, Resp>, ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final int methodId;
        private final InAppMessagingSdkServingImplBase serviceImpl;

        MethodHandlers(InAppMessagingSdkServingImplBase serviceImpl2, int methodId2) {
            this.serviceImpl = serviceImpl2;
            this.methodId = methodId2;
        }

        public void invoke(Req request, StreamObserver<Resp> responseObserver) {
            if (this.methodId == 0) {
                this.serviceImpl.fetchEligibleCampaigns((FetchEligibleCampaignsRequest) request, responseObserver);
                return;
            }
            throw new AssertionError();
        }

        public StreamObserver<Req> invoke(StreamObserver<Resp> streamObserver) {
            throw new AssertionError();
        }
    }

    public static ServiceDescriptor getServiceDescriptor() {
        ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            synchronized (InAppMessagingSdkServingGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    ServiceDescriptor build = ServiceDescriptor.newBuilder(SERVICE_NAME).addMethod(getFetchEligibleCampaignsMethod()).build();
                    result = build;
                    serviceDescriptor = build;
                }
            }
        }
        return result;
    }
}
