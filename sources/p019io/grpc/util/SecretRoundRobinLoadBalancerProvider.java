package p019io.grpc.util;

import java.util.Map;
import p019io.grpc.LoadBalancer;
import p019io.grpc.LoadBalancerProvider;
import p019io.grpc.NameResolver;

/* renamed from: io.grpc.util.SecretRoundRobinLoadBalancerProvider */
final class SecretRoundRobinLoadBalancerProvider {
    private SecretRoundRobinLoadBalancerProvider() {
    }

    /* renamed from: io.grpc.util.SecretRoundRobinLoadBalancerProvider$Provider */
    public static final class Provider extends LoadBalancerProvider {
        private static final String NO_CONFIG = "no service config";

        public boolean isAvailable() {
            return true;
        }

        public int getPriority() {
            return 5;
        }

        public String getPolicyName() {
            return "round_robin";
        }

        public LoadBalancer newLoadBalancer(LoadBalancer.Helper helper) {
            return new RoundRobinLoadBalancer(helper);
        }

        public NameResolver.ConfigOrError parseLoadBalancingPolicyConfig(Map<String, ?> map) {
            return NameResolver.ConfigOrError.fromConfig(NO_CONFIG);
        }
    }
}
