package com.google.firebase.inappmessaging.internal;

import com.google.firebase.abt.FirebaseABTesting;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class AbtIntegrationHelper_Factory implements Factory<AbtIntegrationHelper> {
    private final Provider<FirebaseABTesting> abTestingProvider;

    public AbtIntegrationHelper_Factory(Provider<FirebaseABTesting> abTestingProvider2) {
        this.abTestingProvider = abTestingProvider2;
    }

    public AbtIntegrationHelper get() {
        return new AbtIntegrationHelper(this.abTestingProvider.get());
    }

    public static AbtIntegrationHelper_Factory create(Provider<FirebaseABTesting> abTestingProvider2) {
        return new AbtIntegrationHelper_Factory(abTestingProvider2);
    }

    public static AbtIntegrationHelper newInstance(FirebaseABTesting abTesting) {
        return new AbtIntegrationHelper(abTesting);
    }
}
