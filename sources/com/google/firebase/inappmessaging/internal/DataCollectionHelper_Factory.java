package com.google.firebase.inappmessaging.internal;

import com.google.firebase.FirebaseApp;
import com.google.firebase.events.Subscriber;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DataCollectionHelper_Factory implements Factory<DataCollectionHelper> {
    private final Provider<FirebaseApp> firebaseAppProvider;
    private final Provider<Subscriber> firebaseEventsSubscriberProvider;
    private final Provider<SharedPreferencesUtils> sharedPreferencesUtilsProvider;

    public DataCollectionHelper_Factory(Provider<FirebaseApp> firebaseAppProvider2, Provider<SharedPreferencesUtils> sharedPreferencesUtilsProvider2, Provider<Subscriber> firebaseEventsSubscriberProvider2) {
        this.firebaseAppProvider = firebaseAppProvider2;
        this.sharedPreferencesUtilsProvider = sharedPreferencesUtilsProvider2;
        this.firebaseEventsSubscriberProvider = firebaseEventsSubscriberProvider2;
    }

    public DataCollectionHelper get() {
        return new DataCollectionHelper(this.firebaseAppProvider.get(), this.sharedPreferencesUtilsProvider.get(), this.firebaseEventsSubscriberProvider.get());
    }

    public static DataCollectionHelper_Factory create(Provider<FirebaseApp> firebaseAppProvider2, Provider<SharedPreferencesUtils> sharedPreferencesUtilsProvider2, Provider<Subscriber> firebaseEventsSubscriberProvider2) {
        return new DataCollectionHelper_Factory(firebaseAppProvider2, sharedPreferencesUtilsProvider2, firebaseEventsSubscriberProvider2);
    }

    public static DataCollectionHelper newInstance(FirebaseApp firebaseApp, SharedPreferencesUtils sharedPreferencesUtils, Subscriber firebaseEventsSubscriber) {
        return new DataCollectionHelper(firebaseApp, sharedPreferencesUtils, firebaseEventsSubscriber);
    }
}
