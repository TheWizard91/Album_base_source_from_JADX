package p019io.grpc.internal;

import java.util.Map;
import p019io.grpc.LoadBalancer;
import p019io.grpc.LoadBalancerProvider;
import p019io.grpc.NameResolver;

/* renamed from: io.grpc.internal.PickFirstLoadBalancerProvider */
public final class PickFirstLoadBalancerProvider extends LoadBalancerProvider {
    private static final String NO_CONFIG = "no service config";

    public boolean isAvailable() {
        return true;
    }

    public int getPriority() {
        return 5;
    }

    public String getPolicyName() {
        return GrpcUtil.DEFAULT_LB_POLICY;
    }

    public LoadBalancer newLoadBalancer(LoadBalancer.Helper helper) {
        return new PickFirstLoadBalancer(helper);
    }

    public NameResolver.ConfigOrError parseLoadBalancingPolicyConfig(Map<String, ?> map) {
        return NameResolver.ConfigOrError.fromConfig(NO_CONFIG);
    }
}
