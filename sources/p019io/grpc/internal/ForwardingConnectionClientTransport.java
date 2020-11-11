package p019io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;
import p019io.grpc.Attributes;
import p019io.grpc.CallOptions;
import p019io.grpc.InternalChannelz;
import p019io.grpc.InternalLogId;
import p019io.grpc.Metadata;
import p019io.grpc.MethodDescriptor;
import p019io.grpc.Status;
import p019io.grpc.internal.ClientTransport;
import p019io.grpc.internal.ManagedClientTransport;

/* renamed from: io.grpc.internal.ForwardingConnectionClientTransport */
abstract class ForwardingConnectionClientTransport implements ConnectionClientTransport {
    /* access modifiers changed from: protected */
    public abstract ConnectionClientTransport delegate();

    ForwardingConnectionClientTransport() {
    }

    public Runnable start(ManagedClientTransport.Listener listener) {
        return delegate().start(listener);
    }

    public void shutdown(Status status) {
        delegate().shutdown(status);
    }

    public void shutdownNow(Status status) {
        delegate().shutdownNow(status);
    }

    public ClientStream newStream(MethodDescriptor<?, ?> method, Metadata headers, CallOptions callOptions) {
        return delegate().newStream(method, headers, callOptions);
    }

    public void ping(ClientTransport.PingCallback callback, Executor executor) {
        delegate().ping(callback, executor);
    }

    public InternalLogId getLogId() {
        return delegate().getLogId();
    }

    public Attributes getAttributes() {
        return delegate().getAttributes();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("delegate", (Object) delegate()).toString();
    }

    public ListenableFuture<InternalChannelz.SocketStats> getStats() {
        return delegate().getStats();
    }
}
