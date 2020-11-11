package com.google.firebase.inappmessaging.internal.injection.modules;

import android.app.Application;
import com.google.firebase.inappmessaging.internal.ProtoStorageClient;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* renamed from: com.google.firebase.inappmessaging.internal.injection.modules.ProtoStorageClientModule_ProvidesProtoStorageClientForCampaignFactory */
public final class C3993x58415996 implements Factory<ProtoStorageClient> {
    private final Provider<Application> applicationProvider;
    private final ProtoStorageClientModule module;

    public C3993x58415996(ProtoStorageClientModule module2, Provider<Application> applicationProvider2) {
        this.module = module2;
        this.applicationProvider = applicationProvider2;
    }

    public ProtoStorageClient get() {
        return providesProtoStorageClientForCampaign(this.module, this.applicationProvider.get());
    }

    public static C3993x58415996 create(ProtoStorageClientModule module2, Provider<Application> applicationProvider2) {
        return new C3993x58415996(module2, applicationProvider2);
    }

    public static ProtoStorageClient providesProtoStorageClientForCampaign(ProtoStorageClientModule instance, Application application) {
        return (ProtoStorageClient) Preconditions.checkNotNull(instance.providesProtoStorageClientForCampaign(application), "Cannot return null from a non-@Nullable @Provides method");
    }
}
