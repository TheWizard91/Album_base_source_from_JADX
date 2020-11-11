package p019io.grpc.internal;

import p019io.grpc.InternalChannelz;
import p019io.grpc.InternalInstrumented;
import p019io.grpc.LoadBalancer;

/* renamed from: io.grpc.internal.AbstractSubchannel */
abstract class AbstractSubchannel extends LoadBalancer.Subchannel {
    /* access modifiers changed from: package-private */
    public abstract InternalInstrumented<InternalChannelz.ChannelStats> getInstrumentedInternalSubchannel();

    AbstractSubchannel() {
    }
}
