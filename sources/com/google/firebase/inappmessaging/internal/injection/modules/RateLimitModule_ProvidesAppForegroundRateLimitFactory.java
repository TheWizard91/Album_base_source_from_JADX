package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.inappmessaging.model.RateLimit;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class RateLimitModule_ProvidesAppForegroundRateLimitFactory implements Factory<RateLimit> {
    private final RateLimitModule module;

    public RateLimitModule_ProvidesAppForegroundRateLimitFactory(RateLimitModule module2) {
        this.module = module2;
    }

    public RateLimit get() {
        return providesAppForegroundRateLimit(this.module);
    }

    public static RateLimitModule_ProvidesAppForegroundRateLimitFactory create(RateLimitModule module2) {
        return new RateLimitModule_ProvidesAppForegroundRateLimitFactory(module2);
    }

    public static RateLimit providesAppForegroundRateLimit(RateLimitModule instance) {
        return (RateLimit) Preconditions.checkNotNull(instance.providesAppForegroundRateLimit(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
