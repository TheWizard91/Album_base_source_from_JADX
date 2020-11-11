package com.google.firebase.inappmessaging.internal.injection.modules;

import android.app.Application;
import com.google.firebase.inappmessaging.internal.ProtoStorageClient;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* renamed from: com.google.firebase.inappmessaging.internal.injection.modules.ProtoStorageClientModule_ProvidesProtoStorageClientForLimiterStoreFactory */
public final class C3995x2680546d implements Factory<ProtoStorageClient> {
    private final Provider<Application> applicationProvider;
    private final ProtoStorageClientModule module;

    public C3995x2680546d(ProtoStorageClientModule module2, Provider<Application> applicationProvider2) {
        this.module = module2;
        this.applicationProvider = applicationProvider2;
    }

    public ProtoStorageClient get() {
        return providesProtoStorageClientForLimiterStore(this.module, this.applicationProvider.get());
    }

    public static C3995x2680546d create(ProtoStorageClientModule module2, Provider<Application> applicationProvider2) {
        return new C3995x2680546d(module2, applicationProvider2);
    }

    public static ProtoStorageClient providesProtoStorageClientForLimiterStore(ProtoStorageClientModule instance, Application application) {
        return (ProtoStorageClient) Preconditions.checkNotNull(instance.providesProtoStorageClientForLimiterStore(application), "Cannot return null from a non-@Nullable @Provides method");
    }
}
