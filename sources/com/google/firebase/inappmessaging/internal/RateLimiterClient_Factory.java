package com.google.firebase.inappmessaging.internal;

import com.google.firebase.inappmessaging.internal.time.Clock;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class RateLimiterClient_Factory implements Factory<RateLimiterClient> {
    private final Provider<Clock> clockProvider;
    private final Provider<ProtoStorageClient> storageClientProvider;

    public RateLimiterClient_Factory(Provider<ProtoStorageClient> storageClientProvider2, Provider<Clock> clockProvider2) {
        this.storageClientProvider = storageClientProvider2;
        this.clockProvider = clockProvider2;
    }

    public RateLimiterClient get() {
        return new RateLimiterClient(this.storageClientProvider.get(), this.clockProvider.get());
    }

    public static RateLimiterClient_Factory create(Provider<ProtoStorageClient> storageClientProvider2, Provider<Clock> clockProvider2) {
        return new RateLimiterClient_Factory(storageClientProvider2, clockProvider2);
    }

    public static RateLimiterClient newInstance(ProtoStorageClient storageClient, Clock clock) {
        return new RateLimiterClient(storageClient, clock);
    }
}
