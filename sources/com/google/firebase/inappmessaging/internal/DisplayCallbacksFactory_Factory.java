package com.google.firebase.inappmessaging.internal;

import com.google.firebase.inappmessaging.internal.time.Clock;
import com.google.firebase.inappmessaging.model.RateLimit;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DisplayCallbacksFactory_Factory implements Factory<DisplayCallbacksFactory> {
    private final Provider<RateLimit> appForegroundRateLimitProvider;
    private final Provider<CampaignCacheClient> campaignCacheClientProvider;
    private final Provider<Clock> clockProvider;
    private final Provider<DataCollectionHelper> dataCollectionHelperProvider;
    private final Provider<ImpressionStorageClient> impressionStorageClientProvider;
    private final Provider<MetricsLoggerClient> metricsLoggerClientProvider;
    private final Provider<RateLimiterClient> rateLimiterClientProvider;
    private final Provider<Schedulers> schedulersProvider;

    public DisplayCallbacksFactory_Factory(Provider<ImpressionStorageClient> impressionStorageClientProvider2, Provider<Clock> clockProvider2, Provider<Schedulers> schedulersProvider2, Provider<RateLimiterClient> rateLimiterClientProvider2, Provider<CampaignCacheClient> campaignCacheClientProvider2, Provider<RateLimit> appForegroundRateLimitProvider2, Provider<MetricsLoggerClient> metricsLoggerClientProvider2, Provider<DataCollectionHelper> dataCollectionHelperProvider2) {
        this.impressionStorageClientProvider = impressionStorageClientProvider2;
        this.clockProvider = clockProvider2;
        this.schedulersProvider = schedulersProvider2;
        this.rateLimiterClientProvider = rateLimiterClientProvider2;
        this.campaignCacheClientProvider = campaignCacheClientProvider2;
        this.appForegroundRateLimitProvider = appForegroundRateLimitProvider2;
        this.metricsLoggerClientProvider = metricsLoggerClientProvider2;
        this.dataCollectionHelperProvider = dataCollectionHelperProvider2;
    }

    public DisplayCallbacksFactory get() {
        return new DisplayCallbacksFactory(this.impressionStorageClientProvider.get(), this.clockProvider.get(), this.schedulersProvider.get(), this.rateLimiterClientProvider.get(), this.campaignCacheClientProvider.get(), this.appForegroundRateLimitProvider.get(), this.metricsLoggerClientProvider.get(), this.dataCollectionHelperProvider.get());
    }

    public static DisplayCallbacksFactory_Factory create(Provider<ImpressionStorageClient> impressionStorageClientProvider2, Provider<Clock> clockProvider2, Provider<Schedulers> schedulersProvider2, Provider<RateLimiterClient> rateLimiterClientProvider2, Provider<CampaignCacheClient> campaignCacheClientProvider2, Provider<RateLimit> appForegroundRateLimitProvider2, Provider<MetricsLoggerClient> metricsLoggerClientProvider2, Provider<DataCollectionHelper> dataCollectionHelperProvider2) {
        return new DisplayCallbacksFactory_Factory(impressionStorageClientProvider2, clockProvider2, schedulersProvider2, rateLimiterClientProvider2, campaignCacheClientProvider2, appForegroundRateLimitProvider2, metricsLoggerClientProvider2, dataCollectionHelperProvider2);
    }

    public static DisplayCallbacksFactory newInstance(ImpressionStorageClient impressionStorageClient, Clock clock, Schedulers schedulers, RateLimiterClient rateLimiterClient, CampaignCacheClient campaignCacheClient, RateLimit appForegroundRateLimit, MetricsLoggerClient metricsLoggerClient, DataCollectionHelper dataCollectionHelper) {
        return new DisplayCallbacksFactory(impressionStorageClient, clock, schedulers, rateLimiterClient, campaignCacheClient, appForegroundRateLimit, metricsLoggerClient, dataCollectionHelper);
    }
}
