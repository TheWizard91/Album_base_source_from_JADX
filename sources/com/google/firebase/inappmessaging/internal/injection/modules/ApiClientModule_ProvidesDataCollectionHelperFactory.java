package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.events.Subscriber;
import com.google.firebase.inappmessaging.internal.DataCollectionHelper;
import com.google.firebase.inappmessaging.internal.SharedPreferencesUtils;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ApiClientModule_ProvidesDataCollectionHelperFactory implements Factory<DataCollectionHelper> {
    private final Provider<Subscriber> firebaseEventSubscriberProvider;
    private final ApiClientModule module;
    private final Provider<SharedPreferencesUtils> sharedPreferencesUtilsProvider;

    public ApiClientModule_ProvidesDataCollectionHelperFactory(ApiClientModule module2, Provider<SharedPreferencesUtils> sharedPreferencesUtilsProvider2, Provider<Subscriber> firebaseEventSubscriberProvider2) {
        this.module = module2;
        this.sharedPreferencesUtilsProvider = sharedPreferencesUtilsProvider2;
        this.firebaseEventSubscriberProvider = firebaseEventSubscriberProvider2;
    }

    public DataCollectionHelper get() {
        return providesDataCollectionHelper(this.module, this.sharedPreferencesUtilsProvider.get(), this.firebaseEventSubscriberProvider.get());
    }

    public static ApiClientModule_ProvidesDataCollectionHelperFactory create(ApiClientModule module2, Provider<SharedPreferencesUtils> sharedPreferencesUtilsProvider2, Provider<Subscriber> firebaseEventSubscriberProvider2) {
        return new ApiClientModule_ProvidesDataCollectionHelperFactory(module2, sharedPreferencesUtilsProvider2, firebaseEventSubscriberProvider2);
    }

    public static DataCollectionHelper providesDataCollectionHelper(ApiClientModule instance, SharedPreferencesUtils sharedPreferencesUtils, Subscriber firebaseEventSubscriber) {
        return (DataCollectionHelper) Preconditions.checkNotNull(instance.providesDataCollectionHelper(sharedPreferencesUtils, firebaseEventSubscriber), "Cannot return null from a non-@Nullable @Provides method");
    }
}
