package com.google.internal.firebase.inappmessaging.p015v1.sdkserving;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;

/* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.ClientAppInfoOrBuilder */
public interface ClientAppInfoOrBuilder extends MessageLiteOrBuilder {
    String getAppInstanceId();

    ByteString getAppInstanceIdBytes();

    String getAppInstanceIdToken();

    ByteString getAppInstanceIdTokenBytes();

    String getGmpAppId();

    ByteString getGmpAppIdBytes();
}
