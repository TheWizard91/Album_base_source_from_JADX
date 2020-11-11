package com.google.firebase.inappmessaging.internal;

import com.google.firebase.inappmessaging.internal.time.Clock;
import com.google.firebase.inappmessaging.model.RateLimit;
import com.google.firebase.installations.FirebaseInstallationsApi;
import dagger.internal.Factory;
import javax.inject.Provider;
import p019io.reactivex.flowables.ConnectableFlowable;

public final class InAppMessageStreamManager_Factory implements Factory<InAppMessageStreamManager> {
    private final Provider<AbtIntegrationHelper> abtIntegrationHelperProvider;
    private final Provider<AnalyticsEventsManager> analyticsEventsManagerProvider;
    private final Provider<ApiClient> apiClientProvider;
    private final Provider<ConnectableFlowable<String>> appForegroundEventFlowableProvider;
    private final Provider<RateLimit> appForegroundRateLimitProvider;
    private final Provider<CampaignCacheClient> campaignCacheClientProvider;
    private final Provider<Clock> clockProvider;
    private final Provider<DataCollectionHelper> dataCollectionHelperProvider;
    private final Provider<FirebaseInstallationsApi> firebaseInstallationsProvider;
    private final Provider<ImpressionStorageClient> impressionStorageClientProvider;
    private final Provider<ConnectableFlowable<String>> programmaticTriggerEventFlowableProvider;
    private final Provider<RateLimiterClient> rateLimiterClientProvider;
    private final Provider<Schedulers> schedulersProvider;
    private final Provider<TestDeviceHelper> testDeviceHelperProvider;

    public InAppMessageStreamManager_Factory(Provider<ConnectableFlowable<String>> appForegroundEventFlowableProvider2, Provider<ConnectableFlowable<String>> programmaticTriggerEventFlowableProvider2, Provider<CampaignCacheClient> campaignCacheClientProvider2, Provider<Clock> clockProvider2, Provider<ApiClient> apiClientProvider2, Provider<AnalyticsEventsManager> analyticsEventsManagerProvider2, Provider<Schedulers> schedulersProvider2, Provider<ImpressionStorageClient> impressionStorageClientProvider2, Provider<RateLimiterClient> rateLimiterClientProvider2, Provider<RateLimit> appForegroundRateLimitProvider2, Provider<TestDeviceHelper> testDeviceHelperProvider2, Provider<FirebaseInstallationsApi> firebaseInstallationsProvider2, Provider<DataCollectionHelper> dataCollectionHelperProvider2, Provider<AbtIntegrationHelper> abtIntegrationHelperProvider2) {
        this.appForegroundEventFlowableProvider = appForegroundEventFlowableProvider2;
        this.programmaticTriggerEventFlowableProvider = programmaticTriggerEventFlowableProvider2;
        this.campaignCacheClientProvider = campaignCacheClientProvider2;
        this.clockProvider = clockProvider2;
        this.apiClientProvider = apiClientProvider2;
        this.analyticsEventsManagerProvider = analyticsEventsManagerProvider2;
        this.schedulersProvider = schedulersProvider2;
        this.impressionStorageClientProvider = impressionStorageClientProvider2;
        this.rateLimiterClientProvider = rateLimiterClientProvider2;
        this.appForegroundRateLimitProvider = appForegroundRateLimitProvider2;
        this.testDeviceHelperProvider = testDeviceHelperProvider2;
        this.firebaseInstallationsProvider = firebaseInstallationsProvider2;
        this.dataCollectionHelperProvider = dataCollectionHelperProvider2;
        this.abtIntegrationHelperProvider = abtIntegrationHelperProvider2;
    }

    public InAppMessageStreamManager get() {
        return new InAppMessageStreamManager(this.appForegroundEventFlowableProvider.get(), this.programmaticTriggerEventFlowableProvider.get(), this.campaignCacheClientProvider.get(), this.clockProvider.get(), this.apiClientProvider.get(), this.analyticsEventsManagerProvider.get(), this.schedulersProvider.get(), this.impressionStorageClientProvider.get(), this.rateLimiterClientProvider.get(), this.appForegroundRateLimitProvider.get(), this.testDeviceHelperProvider.get(), this.firebaseInstallationsProvider.get(), this.dataCollectionHelperProvider.get(), this.abtIntegrationHelperProvider.get());
    }

    public static InAppMessageStreamManager_Factory create(Provider<ConnectableFlowable<String>> appForegroundEventFlowableProvider2, Provider<ConnectableFlowable<String>> programmaticTriggerEventFlowableProvider2, Provider<CampaignCacheClient> campaignCacheClientProvider2, Provider<Clock> clockProvider2, Provider<ApiClient> apiClientProvider2, Provider<AnalyticsEventsManager> analyticsEventsManagerProvider2, Provider<Schedulers> schedulersProvider2, Provider<ImpressionStorageClient> impressionStorageClientProvider2, Provider<RateLimiterClient> rateLimiterClientProvider2, Provider<RateLimit> appForegroundRateLimitProvider2, Provider<TestDeviceHelper> testDeviceHelperProvider2, Provider<FirebaseInstallationsApi> firebaseInstallationsProvider2, Provider<DataCollectionHelper> dataCollectionHelperProvider2, Provider<AbtIntegrationHelper> abtIntegrationHelperProvider2) {
        return new InAppMessageStreamManager_Factory(appForegroundEventFlowableProvider2, programmaticTriggerEventFlowableProvider2, campaignCacheClientProvider2, clockProvider2, apiClientProvider2, analyticsEventsManagerProvider2, schedulersProvider2, impressionStorageClientProvider2, rateLimiterClientProvider2, appForegroundRateLimitProvider2, testDeviceHelperProvider2, firebaseInstallationsProvider2, dataCollectionHelperProvider2, abtIntegrationHelperProvider2);
    }

    public static InAppMessageStreamManager newInstance(ConnectableFlowable<String> appForegroundEventFlowable, ConnectableFlowable<String> programmaticTriggerEventFlowable, CampaignCacheClient campaignCacheClient, Clock clock, ApiClient apiClient, AnalyticsEventsManager analyticsEventsManager, Schedulers schedulers, ImpressionStorageClient impressionStorageClient, RateLimiterClient rateLimiterClient, RateLimit appForegroundRateLimit, TestDeviceHelper testDeviceHelper, FirebaseInstallationsApi firebaseInstallations, DataCollectionHelper dataCollectionHelper, AbtIntegrationHelper abtIntegrationHelper) {
        return new InAppMessageStreamManager(appForegroundEventFlowable, programmaticTriggerEventFlowable, campaignCacheClient, clock, apiClient, analyticsEventsManager, schedulers, impressionStorageClient, rateLimiterClient, appForegroundRateLimit, testDeviceHelper, firebaseInstallations, dataCollectionHelper, abtIntegrationHelper);
    }
}
