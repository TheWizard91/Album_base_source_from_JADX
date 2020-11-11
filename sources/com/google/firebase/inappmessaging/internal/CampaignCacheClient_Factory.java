package com.google.firebase.inappmessaging.internal;

import android.app.Application;
import com.google.firebase.inappmessaging.internal.time.Clock;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class CampaignCacheClient_Factory implements Factory<CampaignCacheClient> {
    private final Provider<Application> applicationProvider;
    private final Provider<Clock> clockProvider;
    private final Provider<ProtoStorageClient> storageClientProvider;

    public CampaignCacheClient_Factory(Provider<ProtoStorageClient> storageClientProvider2, Provider<Application> applicationProvider2, Provider<Clock> clockProvider2) {
        this.storageClientProvider = storageClientProvider2;
        this.applicationProvider = applicationProvider2;
        this.clockProvider = clockProvider2;
    }

    public CampaignCacheClient get() {
        return new CampaignCacheClient(this.storageClientProvider.get(), this.applicationProvider.get(), this.clockProvider.get());
    }

    public static CampaignCacheClient_Factory create(Provider<ProtoStorageClient> storageClientProvider2, Provider<Application> applicationProvider2, Provider<Clock> clockProvider2) {
        return new CampaignCacheClient_Factory(storageClientProvider2, applicationProvider2, clockProvider2);
    }

    public static CampaignCacheClient newInstance(ProtoStorageClient storageClient, Application application, Clock clock) {
        return new CampaignCacheClient(storageClient, application, clock);
    }
}
