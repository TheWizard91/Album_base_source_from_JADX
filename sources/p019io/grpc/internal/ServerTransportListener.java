package p019io.grpc.internal;

import p019io.grpc.Attributes;
import p019io.grpc.Metadata;

/* renamed from: io.grpc.internal.ServerTransportListener */
public interface ServerTransportListener {
    void streamCreated(ServerStream serverStream, String str, Metadata metadata);

    Attributes transportReady(Attributes attributes);

    void transportTerminated();
}
