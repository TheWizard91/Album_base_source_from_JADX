package com.google.firebase.inappmessaging.internal.injection.components;

import android.app.Application;
import com.google.android.datatransport.TransportFactory;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.events.Subscriber;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging_Factory;
import com.google.firebase.inappmessaging.internal.AbtIntegrationHelper;
import com.google.firebase.inappmessaging.internal.AnalyticsEventsManager;
import com.google.firebase.inappmessaging.internal.ApiClient;
import com.google.firebase.inappmessaging.internal.CampaignCacheClient;
import com.google.firebase.inappmessaging.internal.DataCollectionHelper;
import com.google.firebase.inappmessaging.internal.DeveloperListenerManager;
import com.google.firebase.inappmessaging.internal.DisplayCallbacksFactory;
import com.google.firebase.inappmessaging.internal.DisplayCallbacksFactory_Factory;
import com.google.firebase.inappmessaging.internal.GrpcClient;
import com.google.firebase.inappmessaging.internal.GrpcClient_Factory;
import com.google.firebase.inappmessaging.internal.ImpressionStorageClient;
import com.google.firebase.inappmessaging.internal.InAppMessageStreamManager;
import com.google.firebase.inappmessaging.internal.InAppMessageStreamManager_Factory;
import com.google.firebase.inappmessaging.internal.MetricsLoggerClient;
import com.google.firebase.inappmessaging.internal.ProgramaticContextualTriggers;
import com.google.firebase.inappmessaging.internal.ProviderInstaller;
import com.google.firebase.inappmessaging.internal.RateLimiterClient;
import com.google.firebase.inappmessaging.internal.Schedulers;
import com.google.firebase.inappmessaging.internal.SharedPreferencesUtils;
import com.google.firebase.inappmessaging.internal.TestDeviceHelper;
import com.google.firebase.inappmessaging.internal.injection.components.AppComponent;
import com.google.firebase.inappmessaging.internal.injection.modules.ApiClientModule;
import com.google.firebase.inappmessaging.internal.injection.modules.ApiClientModule_ProvidesApiClientFactory;
import com.google.firebase.inappmessaging.internal.injection.modules.ApiClientModule_ProvidesDataCollectionHelperFactory;
import com.google.firebase.inappmessaging.internal.injection.modules.ApiClientModule_ProvidesFirebaseAppFactory;
import com.google.firebase.inappmessaging.internal.injection.modules.ApiClientModule_ProvidesFirebaseInstallationsFactory;
import com.google.firebase.inappmessaging.internal.injection.modules.ApiClientModule_ProvidesSharedPreferencesUtilsFactory;
import com.google.firebase.inappmessaging.internal.injection.modules.ApiClientModule_ProvidesTestDeviceHelperFactory;
import com.google.firebase.inappmessaging.internal.injection.modules.GrpcClientModule;
import com.google.firebase.inappmessaging.internal.injection.modules.GrpcClientModule_ProvidesApiKeyHeadersFactory;
import com.google.firebase.inappmessaging.internal.injection.modules.GrpcClientModule_ProvidesInAppMessagingSdkServingStubFactory;
import com.google.firebase.inappmessaging.internal.injection.modules.TransportClientModule_ProvidesMetricsLoggerClientFactory;
import com.google.firebase.inappmessaging.internal.time.Clock;
import com.google.firebase.inappmessaging.model.RateLimit;
import com.google.firebase.installations.FirebaseInstallationsApi;
import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.InAppMessagingSdkServingGrpc;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import p019io.grpc.Channel;
import p019io.grpc.Metadata;
import p019io.reactivex.flowables.ConnectableFlowable;

public final class DaggerAppComponent implements AppComponent {
    private Provider<AbtIntegrationHelper> abtIntegrationHelperProvider;
    private Provider<AnalyticsConnector> analyticsConnectorProvider;
    private Provider<AnalyticsEventsManager> analyticsEventsManagerProvider;
    private final ApiClientModule apiClientModule;
    private Provider<ConnectableFlowable<String>> appForegroundEventFlowableProvider;
    private Provider<RateLimit> appForegroundRateLimitProvider;
    private Provider<Application> applicationProvider;
    private Provider<CampaignCacheClient> campaignCacheClientProvider;
    private Provider<Clock> clockProvider;
    private Provider<DeveloperListenerManager> developerListenerManagerProvider;
    private Provider<DisplayCallbacksFactory> displayCallbacksFactoryProvider;
    private Provider<Subscriber> firebaseEventsSubscriberProvider;
    private Provider<FirebaseInAppMessaging> firebaseInAppMessagingProvider;
    private Provider<Channel> gRPCChannelProvider;
    private Provider<GrpcClient> grpcClientProvider;
    private Provider<ImpressionStorageClient> impressionStorageClientProvider;
    private Provider<InAppMessageStreamManager> inAppMessageStreamManagerProvider;
    private Provider<ProviderInstaller> probiderInstallerProvider;
    private Provider<ConnectableFlowable<String>> programmaticContextualTriggerFlowableProvider;
    private Provider<ProgramaticContextualTriggers> programmaticContextualTriggersProvider;
    private Provider<ApiClient> providesApiClientProvider;
    private Provider<Metadata> providesApiKeyHeadersProvider;
    private Provider<DataCollectionHelper> providesDataCollectionHelperProvider;
    private Provider<FirebaseApp> providesFirebaseAppProvider;
    private Provider<FirebaseInstallationsApi> providesFirebaseInstallationsProvider;
    private Provider<InAppMessagingSdkServingGrpc.InAppMessagingSdkServingBlockingStub> providesInAppMessagingSdkServingStubProvider;
    private Provider<MetricsLoggerClient> providesMetricsLoggerClientProvider;
    private Provider<SharedPreferencesUtils> providesSharedPreferencesUtilsProvider;
    private Provider<TestDeviceHelper> providesTestDeviceHelperProvider;
    private Provider<RateLimiterClient> rateLimiterClientProvider;
    private Provider<Schedulers> schedulersProvider;
    private Provider<TransportFactory> transportFactoryProvider;
    private final UniversalComponent universalComponent;

    private DaggerAppComponent(ApiClientModule apiClientModuleParam, GrpcClientModule grpcClientModuleParam, UniversalComponent universalComponentParam, AbtIntegrationHelper abtIntegrationHelperParam, TransportFactory transportFactoryParam) {
        this.universalComponent = universalComponentParam;
        this.apiClientModule = apiClientModuleParam;
        initialize(apiClientModuleParam, grpcClientModuleParam, universalComponentParam, abtIntegrationHelperParam, transportFactoryParam);
    }

    public static AppComponent.Builder builder() {
        return new Builder();
    }

    private DataCollectionHelper getDataCollectionHelper() {
        ApiClientModule apiClientModule2 = this.apiClientModule;
        return ApiClientModule_ProvidesDataCollectionHelperFactory.providesDataCollectionHelper(apiClientModule2, ApiClientModule_ProvidesSharedPreferencesUtilsFactory.providesSharedPreferencesUtils(apiClientModule2), (Subscriber) Preconditions.checkNotNull(this.universalComponent.firebaseEventsSubscriber(), "Cannot return null from a non-@Nullable component method"));
    }

    private void initialize(ApiClientModule apiClientModuleParam, GrpcClientModule grpcClientModuleParam, UniversalComponent universalComponentParam, AbtIntegrationHelper abtIntegrationHelperParam, TransportFactory transportFactoryParam) {
        ApiClientModule apiClientModule2 = apiClientModuleParam;
        UniversalComponent universalComponent2 = universalComponentParam;
        this.appForegroundEventFlowableProvider = new C3976x345a1105(universalComponent2);
        this.programmaticContextualTriggerFlowableProvider = new C3986xa33e14f2(universalComponent2);
        this.campaignCacheClientProvider = new C3979x4d59aff6(universalComponent2);
        this.clockProvider = new C3980x55447107(universalComponent2);
        this.gRPCChannelProvider = new C3983x6b6ee15e(universalComponent2);
        GrpcClientModule_ProvidesApiKeyHeadersFactory create = GrpcClientModule_ProvidesApiKeyHeadersFactory.create(grpcClientModuleParam);
        this.providesApiKeyHeadersProvider = create;
        Provider<InAppMessagingSdkServingGrpc.InAppMessagingSdkServingBlockingStub> provider = DoubleCheck.provider(GrpcClientModule_ProvidesInAppMessagingSdkServingStubFactory.create(grpcClientModuleParam, this.gRPCChannelProvider, create));
        this.providesInAppMessagingSdkServingStubProvider = provider;
        this.grpcClientProvider = DoubleCheck.provider(GrpcClient_Factory.create(provider));
        this.applicationProvider = new C3978x8bf33b89(universalComponent2);
        C3985x8f6097a4 com_google_firebase_inappmessaging_internal_injection_components_universalcomponent_probiderinstaller = new C3985x8f6097a4(universalComponent2);
        this.probiderInstallerProvider = com_google_firebase_inappmessaging_internal_injection_components_universalcomponent_probiderinstaller;
        this.providesApiClientProvider = DoubleCheck.provider(ApiClientModule_ProvidesApiClientFactory.create(apiClientModule2, this.grpcClientProvider, this.applicationProvider, com_google_firebase_inappmessaging_internal_injection_components_universalcomponent_probiderinstaller));
        this.analyticsEventsManagerProvider = new C3975xbdbce295(universalComponent2);
        this.schedulersProvider = new C3989xd050f5bf(universalComponent2);
        this.impressionStorageClientProvider = new C3984x7d9fb756(universalComponent2);
        this.rateLimiterClientProvider = new C3988x87c20e0c(universalComponent2);
        this.appForegroundRateLimitProvider = new C3977x727a18fe(universalComponent2);
        ApiClientModule_ProvidesSharedPreferencesUtilsFactory create2 = ApiClientModule_ProvidesSharedPreferencesUtilsFactory.create(apiClientModuleParam);
        this.providesSharedPreferencesUtilsProvider = create2;
        this.providesTestDeviceHelperProvider = ApiClientModule_ProvidesTestDeviceHelperFactory.create(apiClientModule2, create2);
        this.providesFirebaseInstallationsProvider = ApiClientModule_ProvidesFirebaseInstallationsFactory.create(apiClientModuleParam);
        C3982x4a8174ef com_google_firebase_inappmessaging_internal_injection_components_universalcomponent_firebaseeventssubscriber = new C3982x4a8174ef(universalComponent2);
        this.firebaseEventsSubscriberProvider = com_google_firebase_inappmessaging_internal_injection_components_universalcomponent_firebaseeventssubscriber;
        this.providesDataCollectionHelperProvider = ApiClientModule_ProvidesDataCollectionHelperFactory.create(apiClientModule2, this.providesSharedPreferencesUtilsProvider, com_google_firebase_inappmessaging_internal_injection_components_universalcomponent_firebaseeventssubscriber);
        Factory create3 = InstanceFactory.create(abtIntegrationHelperParam);
        this.abtIntegrationHelperProvider = create3;
        this.inAppMessageStreamManagerProvider = DoubleCheck.provider(InAppMessageStreamManager_Factory.create(this.appForegroundEventFlowableProvider, this.programmaticContextualTriggerFlowableProvider, this.campaignCacheClientProvider, this.clockProvider, this.providesApiClientProvider, this.analyticsEventsManagerProvider, this.schedulersProvider, this.impressionStorageClientProvider, this.rateLimiterClientProvider, this.appForegroundRateLimitProvider, this.providesTestDeviceHelperProvider, this.providesFirebaseInstallationsProvider, this.providesDataCollectionHelperProvider, create3));
        this.programmaticContextualTriggersProvider = new C3987x7c101e69(universalComponent2);
        this.providesFirebaseAppProvider = ApiClientModule_ProvidesFirebaseAppFactory.create(apiClientModuleParam);
        this.transportFactoryProvider = InstanceFactory.create(transportFactoryParam);
        this.analyticsConnectorProvider = new C3974x92f0170e(universalComponent2);
        C3981x559e6196 com_google_firebase_inappmessaging_internal_injection_components_universalcomponent_developerlistenermanager = new C3981x559e6196(universalComponent2);
        this.developerListenerManagerProvider = com_google_firebase_inappmessaging_internal_injection_components_universalcomponent_developerlistenermanager;
        Provider<MetricsLoggerClient> provider2 = DoubleCheck.provider(TransportClientModule_ProvidesMetricsLoggerClientFactory.create(this.providesFirebaseAppProvider, this.transportFactoryProvider, this.analyticsConnectorProvider, this.providesFirebaseInstallationsProvider, this.clockProvider, com_google_firebase_inappmessaging_internal_injection_components_universalcomponent_developerlistenermanager));
        this.providesMetricsLoggerClientProvider = provider2;
        DisplayCallbacksFactory_Factory create4 = DisplayCallbacksFactory_Factory.create(this.impressionStorageClientProvider, this.clockProvider, this.schedulersProvider, this.rateLimiterClientProvider, this.campaignCacheClientProvider, this.appForegroundRateLimitProvider, provider2, this.providesDataCollectionHelperProvider);
        this.displayCallbacksFactoryProvider = create4;
        this.firebaseInAppMessagingProvider = DoubleCheck.provider(FirebaseInAppMessaging_Factory.create(this.inAppMessageStreamManagerProvider, this.programmaticContextualTriggersProvider, this.providesDataCollectionHelperProvider, this.providesFirebaseInstallationsProvider, create4, this.developerListenerManagerProvider));
    }

    public FirebaseInAppMessaging providesFirebaseInAppMessaging() {
        return this.firebaseInAppMessagingProvider.get();
    }

    public DisplayCallbacksFactory displayCallbacksFactory() {
        return new DisplayCallbacksFactory((ImpressionStorageClient) Preconditions.checkNotNull(this.universalComponent.impressionStorageClient(), "Cannot return null from a non-@Nullable component method"), (Clock) Preconditions.checkNotNull(this.universalComponent.clock(), "Cannot return null from a non-@Nullable component method"), (Schedulers) Preconditions.checkNotNull(this.universalComponent.schedulers(), "Cannot return null from a non-@Nullable component method"), (RateLimiterClient) Preconditions.checkNotNull(this.universalComponent.rateLimiterClient(), "Cannot return null from a non-@Nullable component method"), (CampaignCacheClient) Preconditions.checkNotNull(this.universalComponent.campaignCacheClient(), "Cannot return null from a non-@Nullable component method"), (RateLimit) Preconditions.checkNotNull(this.universalComponent.appForegroundRateLimit(), "Cannot return null from a non-@Nullable component method"), this.providesMetricsLoggerClientProvider.get(), getDataCollectionHelper());
    }

    private static final class Builder implements AppComponent.Builder {
        private AbtIntegrationHelper abtIntegrationHelper;
        private ApiClientModule apiClientModule;
        private GrpcClientModule grpcClientModule;
        private TransportFactory transportFactory;
        private UniversalComponent universalComponent;

        private Builder() {
        }

        public Builder abtIntegrationHelper(AbtIntegrationHelper integrationHelper) {
            this.abtIntegrationHelper = (AbtIntegrationHelper) Preconditions.checkNotNull(integrationHelper);
            return this;
        }

        public Builder apiClientModule(ApiClientModule module) {
            this.apiClientModule = (ApiClientModule) Preconditions.checkNotNull(module);
            return this;
        }

        public Builder grpcClientModule(GrpcClientModule module) {
            this.grpcClientModule = (GrpcClientModule) Preconditions.checkNotNull(module);
            return this;
        }

        public Builder universalComponent(UniversalComponent component) {
            this.universalComponent = (UniversalComponent) Preconditions.checkNotNull(component);
            return this;
        }

        public Builder transportFactory(TransportFactory transportFactory2) {
            this.transportFactory = (TransportFactory) Preconditions.checkNotNull(transportFactory2);
            return this;
        }

        public AppComponent build() {
            Preconditions.checkBuilderRequirement(this.abtIntegrationHelper, AbtIntegrationHelper.class);
            Preconditions.checkBuilderRequirement(this.apiClientModule, ApiClientModule.class);
            Preconditions.checkBuilderRequirement(this.grpcClientModule, GrpcClientModule.class);
            Preconditions.checkBuilderRequirement(this.universalComponent, UniversalComponent.class);
            Preconditions.checkBuilderRequirement(this.transportFactory, TransportFactory.class);
            return new DaggerAppComponent(this.apiClientModule, this.grpcClientModule, this.universalComponent, this.abtIntegrationHelper, this.transportFactory);
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_appForegroundEventFlowable */
    private static class C3976x345a1105 implements Provider<ConnectableFlowable<String>> {
        private final UniversalComponent universalComponent;

        C3976x345a1105(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public ConnectableFlowable<String> get() {
            return (ConnectableFlowable) Preconditions.checkNotNull(this.universalComponent.appForegroundEventFlowable(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_programmaticContextualTriggerFlowable */
    private static class C3986xa33e14f2 implements Provider<ConnectableFlowable<String>> {
        private final UniversalComponent universalComponent;

        C3986xa33e14f2(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public ConnectableFlowable<String> get() {
            return (ConnectableFlowable) Preconditions.checkNotNull(this.universalComponent.programmaticContextualTriggerFlowable(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_campaignCacheClient */
    private static class C3979x4d59aff6 implements Provider<CampaignCacheClient> {
        private final UniversalComponent universalComponent;

        C3979x4d59aff6(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public CampaignCacheClient get() {
            return (CampaignCacheClient) Preconditions.checkNotNull(this.universalComponent.campaignCacheClient(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_clock */
    private static class C3980x55447107 implements Provider<Clock> {
        private final UniversalComponent universalComponent;

        C3980x55447107(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public Clock get() {
            return (Clock) Preconditions.checkNotNull(this.universalComponent.clock(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_gRPCChannel */
    private static class C3983x6b6ee15e implements Provider<Channel> {
        private final UniversalComponent universalComponent;

        C3983x6b6ee15e(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public Channel get() {
            return (Channel) Preconditions.checkNotNull(this.universalComponent.gRPCChannel(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_application */
    private static class C3978x8bf33b89 implements Provider<Application> {
        private final UniversalComponent universalComponent;

        C3978x8bf33b89(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public Application get() {
            return (Application) Preconditions.checkNotNull(this.universalComponent.application(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_probiderInstaller */
    private static class C3985x8f6097a4 implements Provider<ProviderInstaller> {
        private final UniversalComponent universalComponent;

        C3985x8f6097a4(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public ProviderInstaller get() {
            return (ProviderInstaller) Preconditions.checkNotNull(this.universalComponent.probiderInstaller(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_analyticsEventsManager */
    private static class C3975xbdbce295 implements Provider<AnalyticsEventsManager> {
        private final UniversalComponent universalComponent;

        C3975xbdbce295(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public AnalyticsEventsManager get() {
            return (AnalyticsEventsManager) Preconditions.checkNotNull(this.universalComponent.analyticsEventsManager(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_schedulers */
    private static class C3989xd050f5bf implements Provider<Schedulers> {
        private final UniversalComponent universalComponent;

        C3989xd050f5bf(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public Schedulers get() {
            return (Schedulers) Preconditions.checkNotNull(this.universalComponent.schedulers(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_impressionStorageClient */
    private static class C3984x7d9fb756 implements Provider<ImpressionStorageClient> {
        private final UniversalComponent universalComponent;

        C3984x7d9fb756(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public ImpressionStorageClient get() {
            return (ImpressionStorageClient) Preconditions.checkNotNull(this.universalComponent.impressionStorageClient(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_rateLimiterClient */
    private static class C3988x87c20e0c implements Provider<RateLimiterClient> {
        private final UniversalComponent universalComponent;

        C3988x87c20e0c(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public RateLimiterClient get() {
            return (RateLimiterClient) Preconditions.checkNotNull(this.universalComponent.rateLimiterClient(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_appForegroundRateLimit */
    private static class C3977x727a18fe implements Provider<RateLimit> {
        private final UniversalComponent universalComponent;

        C3977x727a18fe(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public RateLimit get() {
            return (RateLimit) Preconditions.checkNotNull(this.universalComponent.appForegroundRateLimit(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_firebaseEventsSubscriber */
    private static class C3982x4a8174ef implements Provider<Subscriber> {
        private final UniversalComponent universalComponent;

        C3982x4a8174ef(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public Subscriber get() {
            return (Subscriber) Preconditions.checkNotNull(this.universalComponent.firebaseEventsSubscriber(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_programmaticContextualTriggers */
    private static class C3987x7c101e69 implements Provider<ProgramaticContextualTriggers> {
        private final UniversalComponent universalComponent;

        C3987x7c101e69(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public ProgramaticContextualTriggers get() {
            return (ProgramaticContextualTriggers) Preconditions.checkNotNull(this.universalComponent.programmaticContextualTriggers(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_analyticsConnector */
    private static class C3974x92f0170e implements Provider<AnalyticsConnector> {
        private final UniversalComponent universalComponent;

        C3974x92f0170e(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public AnalyticsConnector get() {
            return (AnalyticsConnector) Preconditions.checkNotNull(this.universalComponent.analyticsConnector(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_internal_injection_components_UniversalComponent_developerListenerManager */
    private static class C3981x559e6196 implements Provider<DeveloperListenerManager> {
        private final UniversalComponent universalComponent;

        C3981x559e6196(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public DeveloperListenerManager get() {
            return (DeveloperListenerManager) Preconditions.checkNotNull(this.universalComponent.developerListenerManager(), "Cannot return null from a non-@Nullable component method");
        }
    }
}
