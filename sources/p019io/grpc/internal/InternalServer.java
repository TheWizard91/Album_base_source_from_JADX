package p019io.grpc.internal;

import java.io.IOException;
import java.net.SocketAddress;
import javax.annotation.Nullable;
import p019io.grpc.InternalChannelz;
import p019io.grpc.InternalInstrumented;

/* renamed from: io.grpc.internal.InternalServer */
public interface InternalServer {
    SocketAddress getListenSocketAddress();

    @Nullable
    InternalInstrumented<InternalChannelz.SocketStats> getListenSocketStats();

    void shutdown();

    void start(ServerListener serverListener) throws IOException;
}
