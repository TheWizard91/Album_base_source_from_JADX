package com.google.firebase.inappmessaging.internal;

import dagger.internal.Factory;
import javax.inject.Provider;

public final class TestDeviceHelper_Factory implements Factory<TestDeviceHelper> {
    private final Provider<SharedPreferencesUtils> sharedPreferencesUtilsProvider;

    public TestDeviceHelper_Factory(Provider<SharedPreferencesUtils> sharedPreferencesUtilsProvider2) {
        this.sharedPreferencesUtilsProvider = sharedPreferencesUtilsProvider2;
    }

    public TestDeviceHelper get() {
        return new TestDeviceHelper(this.sharedPreferencesUtilsProvider.get());
    }

    public static TestDeviceHelper_Factory create(Provider<SharedPreferencesUtils> sharedPreferencesUtilsProvider2) {
        return new TestDeviceHelper_Factory(sharedPreferencesUtilsProvider2);
    }

    public static TestDeviceHelper newInstance(SharedPreferencesUtils sharedPreferencesUtils) {
        return new TestDeviceHelper(sharedPreferencesUtils);
    }
}
