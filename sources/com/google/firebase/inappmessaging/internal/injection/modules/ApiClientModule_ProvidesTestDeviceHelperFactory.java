package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.inappmessaging.internal.SharedPreferencesUtils;
import com.google.firebase.inappmessaging.internal.TestDeviceHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ApiClientModule_ProvidesTestDeviceHelperFactory implements Factory<TestDeviceHelper> {
    private final ApiClientModule module;
    private final Provider<SharedPreferencesUtils> sharedPreferencesUtilsProvider;

    public ApiClientModule_ProvidesTestDeviceHelperFactory(ApiClientModule module2, Provider<SharedPreferencesUtils> sharedPreferencesUtilsProvider2) {
        this.module = module2;
        this.sharedPreferencesUtilsProvider = sharedPreferencesUtilsProvider2;
    }

    public TestDeviceHelper get() {
        return providesTestDeviceHelper(this.module, this.sharedPreferencesUtilsProvider.get());
    }

    public static ApiClientModule_ProvidesTestDeviceHelperFactory create(ApiClientModule module2, Provider<SharedPreferencesUtils> sharedPreferencesUtilsProvider2) {
        return new ApiClientModule_ProvidesTestDeviceHelperFactory(module2, sharedPreferencesUtilsProvider2);
    }

    public static TestDeviceHelper providesTestDeviceHelper(ApiClientModule instance, SharedPreferencesUtils sharedPreferencesUtils) {
        return (TestDeviceHelper) Preconditions.checkNotNull(instance.providesTestDeviceHelper(sharedPreferencesUtils), "Cannot return null from a non-@Nullable @Provides method");
    }
}
