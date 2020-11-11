package p019io.grpc.internal;

import java.util.concurrent.ScheduledExecutorService;
import p019io.grpc.InternalChannelz;
import p019io.grpc.InternalInstrumented;
import p019io.grpc.Status;

/* renamed from: io.grpc.internal.ServerTransport */
public interface ServerTransport extends InternalInstrumented<InternalChannelz.SocketStats> {
    ScheduledExecutorService getScheduledExecutorService();

    void shutdown();

    void shutdownNow(Status status);
}
