package com.google.firebase.inappmessaging.internal.injection.modules;

import android.app.Application;
import com.google.firebase.inappmessaging.internal.ApiClient;
import com.google.firebase.inappmessaging.internal.GrpcClient;
import com.google.firebase.inappmessaging.internal.ProviderInstaller;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ApiClientModule_ProvidesApiClientFactory implements Factory<ApiClient> {
    private final Provider<Application> applicationProvider;
    private final Provider<GrpcClient> grpcClientProvider;
    private final ApiClientModule module;
    private final Provider<ProviderInstaller> providerInstallerProvider;

    public ApiClientModule_ProvidesApiClientFactory(ApiClientModule module2, Provider<GrpcClient> grpcClientProvider2, Provider<Application> applicationProvider2, Provider<ProviderInstaller> providerInstallerProvider2) {
        this.module = module2;
        this.grpcClientProvider = grpcClientProvider2;
        this.applicationProvider = applicationProvider2;
        this.providerInstallerProvider = providerInstallerProvider2;
    }

    public ApiClient get() {
        return providesApiClient(this.module, DoubleCheck.lazy(this.grpcClientProvider), this.applicationProvider.get(), this.providerInstallerProvider.get());
    }

    public static ApiClientModule_ProvidesApiClientFactory create(ApiClientModule module2, Provider<GrpcClient> grpcClientProvider2, Provider<Application> applicationProvider2, Provider<ProviderInstaller> providerInstallerProvider2) {
        return new ApiClientModule_ProvidesApiClientFactory(module2, grpcClientProvider2, applicationProvider2, providerInstallerProvider2);
    }

    public static ApiClient providesApiClient(ApiClientModule instance, Lazy<GrpcClient> grpcClient, Application application, ProviderInstaller providerInstaller) {
        return (ApiClient) Preconditions.checkNotNull(instance.providesApiClient(grpcClient, application, providerInstaller), "Cannot return null from a non-@Nullable @Provides method");
    }
}
