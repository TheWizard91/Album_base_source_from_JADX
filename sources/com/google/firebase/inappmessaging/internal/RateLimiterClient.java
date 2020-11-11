package com.google.firebase.inappmessaging.internal;

import com.google.firebase.inappmessaging.internal.RateLimitProto;
import com.google.firebase.inappmessaging.internal.injection.qualifiers.RateLimit;
import com.google.firebase.inappmessaging.internal.time.Clock;
import javax.inject.Inject;
import javax.inject.Singleton;
import p019io.reactivex.Completable;
import p019io.reactivex.Maybe;
import p019io.reactivex.Single;

@Singleton
public class RateLimiterClient {
    private static final RateLimitProto.RateLimit EMPTY_RATE_LIMITS = RateLimitProto.RateLimit.getDefaultInstance();
    private Maybe<RateLimitProto.RateLimit> cachedRateLimts = Maybe.empty();
    private final Clock clock;
    private final ProtoStorageClient storageClient;

    @Inject
    RateLimiterClient(@RateLimit ProtoStorageClient storageClient2, Clock clock2) {
        this.storageClient = storageClient2;
        this.clock = clock2;
    }

    private static RateLimitProto.Counter increment(RateLimitProto.Counter current) {
        return (RateLimitProto.Counter) RateLimitProto.Counter.newBuilder(current).clearValue().setValue(current.getValue() + 1).build();
    }

    public Completable increment(com.google.firebase.inappmessaging.model.RateLimit limit) {
        return getRateLimits().defaultIfEmpty(EMPTY_RATE_LIMITS).flatMapCompletable(RateLimiterClient$$Lambda$1.lambdaFactory$(this, limit));
    }

    static /* synthetic */ boolean lambda$increment$0(RateLimiterClient rateLimiterClient, com.google.firebase.inappmessaging.model.RateLimit limit, RateLimitProto.Counter counter) throws Exception {
        return !rateLimiterClient.isLimitExpired(counter, limit);
    }

    static /* synthetic */ RateLimitProto.RateLimit lambda$increment$1(RateLimitProto.RateLimit storedLimits, com.google.firebase.inappmessaging.model.RateLimit limit, RateLimitProto.Counter current) throws Exception {
        return (RateLimitProto.RateLimit) RateLimitProto.RateLimit.newBuilder(storedLimits).putLimits(limit.limiterKey(), increment(current)).build();
    }

    public Single<Boolean> isRateLimited(com.google.firebase.inappmessaging.model.RateLimit limit) {
        return getRateLimits().switchIfEmpty(Maybe.just(RateLimitProto.RateLimit.getDefaultInstance())).map(RateLimiterClient$$Lambda$2.lambdaFactory$(this, limit)).filter(RateLimiterClient$$Lambda$3.lambdaFactory$(this, limit)).isEmpty();
    }

    static /* synthetic */ boolean lambda$isRateLimited$6(RateLimiterClient rateLimiterClient, com.google.firebase.inappmessaging.model.RateLimit limit, RateLimitProto.Counter counter) throws Exception {
        return rateLimiterClient.isLimitExpired(counter, limit) || counter.getValue() < limit.limit();
    }

    private boolean isLimitExpired(RateLimitProto.Counter counter, com.google.firebase.inappmessaging.model.RateLimit limit) {
        return this.clock.now() - counter.getStartTimeEpoch() > limit.timeToLiveMillis();
    }

    private Maybe<RateLimitProto.RateLimit> getRateLimits() {
        return this.cachedRateLimts.switchIfEmpty(this.storageClient.read(RateLimitProto.RateLimit.parser()).doOnSuccess(RateLimiterClient$$Lambda$4.lambdaFactory$(this))).doOnError(RateLimiterClient$$Lambda$5.lambdaFactory$(this));
    }

    /* access modifiers changed from: private */
    public void initInMemCache(RateLimitProto.RateLimit rateLimits) {
        this.cachedRateLimts = Maybe.just(rateLimits);
    }

    /* access modifiers changed from: private */
    public void clearInMemCache() {
        this.cachedRateLimts = Maybe.empty();
    }

    private RateLimitProto.Counter newCounter() {
        return (RateLimitProto.Counter) RateLimitProto.Counter.newBuilder().setValue(0).setStartTimeEpoch(this.clock.now()).build();
    }
}
