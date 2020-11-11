package com.google.firebase.inappmessaging.internal.injection.modules;

import android.app.Application;
import com.google.firebase.inappmessaging.internal.ProtoStorageClient;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* renamed from: com.google.firebase.inappmessaging.internal.injection.modules.ProtoStorageClientModule_ProvidesProtoStorageClientForImpressionStoreFactory */
public final class C3994x20c71256 implements Factory<ProtoStorageClient> {
    private final Provider<Application> applicationProvider;
    private final ProtoStorageClientModule module;

    public C3994x20c71256(ProtoStorageClientModule module2, Provider<Application> applicationProvider2) {
        this.module = module2;
        this.applicationProvider = applicationProvider2;
    }

    public ProtoStorageClient get() {
        return providesProtoStorageClientForImpressionStore(this.module, this.applicationProvider.get());
    }

    public static C3994x20c71256 create(ProtoStorageClientModule module2, Provider<Application> applicationProvider2) {
        return new C3994x20c71256(module2, applicationProvider2);
    }

    public static ProtoStorageClient providesProtoStorageClientForImpressionStore(ProtoStorageClientModule instance, Application application) {
        return (ProtoStorageClient) Preconditions.checkNotNull(instance.providesProtoStorageClientForImpressionStore(application), "Cannot return null from a non-@Nullable @Provides method");
    }
}
