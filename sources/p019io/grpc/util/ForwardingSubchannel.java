package p019io.grpc.util;

import com.google.common.base.MoreObjects;
import java.util.List;
import p019io.grpc.Attributes;
import p019io.grpc.Channel;
import p019io.grpc.ChannelLogger;
import p019io.grpc.EquivalentAddressGroup;
import p019io.grpc.LoadBalancer;

/* renamed from: io.grpc.util.ForwardingSubchannel */
public abstract class ForwardingSubchannel extends LoadBalancer.Subchannel {
    /* access modifiers changed from: protected */
    public abstract LoadBalancer.Subchannel delegate();

    public void start(LoadBalancer.SubchannelStateListener listener) {
        delegate().start(listener);
    }

    public void shutdown() {
        delegate().shutdown();
    }

    public void requestConnection() {
        delegate().requestConnection();
    }

    public List<EquivalentAddressGroup> getAllAddresses() {
        return delegate().getAllAddresses();
    }

    public Attributes getAttributes() {
        return delegate().getAttributes();
    }

    public Channel asChannel() {
        return delegate().asChannel();
    }

    public ChannelLogger getChannelLogger() {
        return delegate().getChannelLogger();
    }

    public Object getInternalSubchannel() {
        return delegate().getInternalSubchannel();
    }

    public void updateAddresses(List<EquivalentAddressGroup> addrs) {
        delegate().updateAddresses(addrs);
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("delegate", (Object) delegate()).toString();
    }
}
