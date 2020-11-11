package p019io.grpc.internal;

import java.util.concurrent.Executor;
import p019io.grpc.CallOptions;
import p019io.grpc.InternalChannelz;
import p019io.grpc.InternalInstrumented;
import p019io.grpc.Metadata;
import p019io.grpc.MethodDescriptor;

/* renamed from: io.grpc.internal.ClientTransport */
public interface ClientTransport extends InternalInstrumented<InternalChannelz.SocketStats> {

    /* renamed from: io.grpc.internal.ClientTransport$PingCallback */
    public interface PingCallback {
        void onFailure(Throwable th);

        void onSuccess(long j);
    }

    ClientStream newStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, CallOptions callOptions);

    void ping(PingCallback pingCallback, Executor executor);
}
