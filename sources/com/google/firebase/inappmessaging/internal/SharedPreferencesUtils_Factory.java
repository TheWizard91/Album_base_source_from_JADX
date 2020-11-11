package com.google.firebase.inappmessaging.internal;

import com.google.firebase.FirebaseApp;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SharedPreferencesUtils_Factory implements Factory<SharedPreferencesUtils> {
    private final Provider<FirebaseApp> firebaseAppProvider;

    public SharedPreferencesUtils_Factory(Provider<FirebaseApp> firebaseAppProvider2) {
        this.firebaseAppProvider = firebaseAppProvider2;
    }

    public SharedPreferencesUtils get() {
        return new SharedPreferencesUtils(this.firebaseAppProvider.get());
    }

    public static SharedPreferencesUtils_Factory create(Provider<FirebaseApp> firebaseAppProvider2) {
        return new SharedPreferencesUtils_Factory(firebaseAppProvider2);
    }

    public static SharedPreferencesUtils newInstance(FirebaseApp firebaseApp) {
        return new SharedPreferencesUtils(firebaseApp);
    }
}
