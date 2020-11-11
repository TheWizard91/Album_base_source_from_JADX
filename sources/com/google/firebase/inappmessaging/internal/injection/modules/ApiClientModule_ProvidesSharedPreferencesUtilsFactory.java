package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.inappmessaging.internal.SharedPreferencesUtils;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ApiClientModule_ProvidesSharedPreferencesUtilsFactory implements Factory<SharedPreferencesUtils> {
    private final ApiClientModule module;

    public ApiClientModule_ProvidesSharedPreferencesUtilsFactory(ApiClientModule module2) {
        this.module = module2;
    }

    public SharedPreferencesUtils get() {
        return providesSharedPreferencesUtils(this.module);
    }

    public static ApiClientModule_ProvidesSharedPreferencesUtilsFactory create(ApiClientModule module2) {
        return new ApiClientModule_ProvidesSharedPreferencesUtilsFactory(module2);
    }

    public static SharedPreferencesUtils providesSharedPreferencesUtils(ApiClientModule instance) {
        return (SharedPreferencesUtils) Preconditions.checkNotNull(instance.providesSharedPreferencesUtils(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
