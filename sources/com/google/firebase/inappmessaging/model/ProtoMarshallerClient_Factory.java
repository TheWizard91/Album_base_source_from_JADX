package com.google.firebase.inappmessaging.model;

import dagger.internal.Factory;

public final class ProtoMarshallerClient_Factory implements Factory<ProtoMarshallerClient> {
    private static final ProtoMarshallerClient_Factory INSTANCE = new ProtoMarshallerClient_Factory();

    public ProtoMarshallerClient get() {
        return new ProtoMarshallerClient();
    }

    public static ProtoMarshallerClient_Factory create() {
        return INSTANCE;
    }

    public static ProtoMarshallerClient newInstance() {
        return new ProtoMarshallerClient();
    }
}
