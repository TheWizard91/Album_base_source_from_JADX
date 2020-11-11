package com.google.firebase.inappmessaging.internal;

import com.google.firebase.inappmessaging.internal.RateLimitProto;
import com.google.firebase.inappmessaging.model.RateLimit;
import p019io.reactivex.functions.Function;

/* compiled from: RateLimiterClient */
final /* synthetic */ class RateLimiterClient$$Lambda$2 implements Function {
    private final RateLimiterClient arg$1;
    private final RateLimit arg$2;

    private RateLimiterClient$$Lambda$2(RateLimiterClient rateLimiterClient, RateLimit rateLimit) {
        this.arg$1 = rateLimiterClient;
        this.arg$2 = rateLimit;
    }

    public static Function lambdaFactory$(RateLimiterClient rateLimiterClient, RateLimit rateLimit) {
        return new RateLimiterClient$$Lambda$2(rateLimiterClient, rateLimit);
    }

    public Object apply(Object obj) {
        return ((RateLimitProto.RateLimit) obj).getLimitsOrDefault(this.arg$2.limiterKey(), this.arg$1.newCounter());
    }
}
