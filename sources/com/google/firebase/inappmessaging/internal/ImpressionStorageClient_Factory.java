package com.google.firebase.inappmessaging.internal;

import dagger.internal.Factory;
import javax.inject.Provider;

public final class ImpressionStorageClient_Factory implements Factory<ImpressionStorageClient> {
    private final Provider<ProtoStorageClient> storageClientProvider;

    public ImpressionStorageClient_Factory(Provider<ProtoStorageClient> storageClientProvider2) {
        this.storageClientProvider = storageClientProvider2;
    }

    public ImpressionStorageClient get() {
        return new ImpressionStorageClient(this.storageClientProvider.get());
    }

    public static ImpressionStorageClient_Factory create(Provider<ProtoStorageClient> storageClientProvider2) {
        return new ImpressionStorageClient_Factory(storageClientProvider2);
    }

    public static ImpressionStorageClient newInstance(ProtoStorageClient storageClient) {
        return new ImpressionStorageClient(storageClient);
    }
}
