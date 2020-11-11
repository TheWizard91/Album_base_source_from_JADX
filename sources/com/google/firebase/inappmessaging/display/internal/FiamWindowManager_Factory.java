package com.google.firebase.inappmessaging.display.internal;

import dagger.internal.Factory;

public final class FiamWindowManager_Factory implements Factory<FiamWindowManager> {
    private static final FiamWindowManager_Factory INSTANCE = new FiamWindowManager_Factory();

    public FiamWindowManager get() {
        return new FiamWindowManager();
    }

    public static FiamWindowManager_Factory create() {
        return INSTANCE;
    }

    public static FiamWindowManager newInstance() {
        return new FiamWindowManager();
    }
}
