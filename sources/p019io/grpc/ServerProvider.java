package p019io.grpc;

import java.util.Collections;
import p019io.grpc.ManagedChannelProvider;
import p019io.grpc.ServiceProviders;

/* renamed from: io.grpc.ServerProvider */
public abstract class ServerProvider {
    private static final ServerProvider provider;

    /* access modifiers changed from: protected */
    public abstract ServerBuilder<?> builderForPort(int i);

    /* access modifiers changed from: protected */
    public abstract boolean isAvailable();

    /* access modifiers changed from: protected */
    public abstract int priority();

    static {
        Class cls = ServerProvider.class;
        provider = (ServerProvider) ServiceProviders.load(cls, Collections.emptyList(), cls.getClassLoader(), new ServiceProviders.PriorityAccessor<ServerProvider>() {
            public boolean isAvailable(ServerProvider provider) {
                return provider.isAvailable();
            }

            public int getPriority(ServerProvider provider) {
                return provider.priority();
            }
        });
    }

    public static ServerProvider provider() {
        ServerProvider serverProvider = provider;
        if (serverProvider != null) {
            return serverProvider;
        }
        throw new ManagedChannelProvider.ProviderNotFoundException("No functional server found. Try adding a dependency on the grpc-netty or grpc-netty-shaded artifact");
    }
}
