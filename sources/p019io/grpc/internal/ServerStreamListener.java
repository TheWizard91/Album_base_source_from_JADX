package p019io.grpc.internal;

import p019io.grpc.Status;

/* renamed from: io.grpc.internal.ServerStreamListener */
public interface ServerStreamListener extends StreamListener {
    void closed(Status status);

    void halfClosed();
}
