package com.google.internal.firebase.inappmessaging.p015v1.sdkserving;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.ClientAppInfo */
public final class ClientAppInfo extends GeneratedMessageLite<ClientAppInfo, Builder> implements ClientAppInfoOrBuilder {
    public static final int APP_INSTANCE_ID_FIELD_NUMBER = 2;
    public static final int APP_INSTANCE_ID_TOKEN_FIELD_NUMBER = 3;
    /* access modifiers changed from: private */
    public static final ClientAppInfo DEFAULT_INSTANCE;
    public static final int GMP_APP_ID_FIELD_NUMBER = 1;
    private static volatile Parser<ClientAppInfo> PARSER;
    private String appInstanceIdToken_ = "";
    private String appInstanceId_ = "";
    private String gmpAppId_ = "";

    private ClientAppInfo() {
    }

    public String getGmpAppId() {
        return this.gmpAppId_;
    }

    public ByteString getGmpAppIdBytes() {
        return ByteString.copyFromUtf8(this.gmpAppId_);
    }

    /* access modifiers changed from: private */
    public void setGmpAppId(String value) {
        value.getClass();
        this.gmpAppId_ = value;
    }

    /* access modifiers changed from: private */
    public void clearGmpAppId() {
        this.gmpAppId_ = getDefaultInstance().getGmpAppId();
    }

    /* access modifiers changed from: private */
    public void setGmpAppIdBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.gmpAppId_ = value.toStringUtf8();
    }

    public String getAppInstanceId() {
        return this.appInstanceId_;
    }

    public ByteString getAppInstanceIdBytes() {
        return ByteString.copyFromUtf8(this.appInstanceId_);
    }

    /* access modifiers changed from: private */
    public void setAppInstanceId(String value) {
        value.getClass();
        this.appInstanceId_ = value;
    }

    /* access modifiers changed from: private */
    public void clearAppInstanceId() {
        this.appInstanceId_ = getDefaultInstance().getAppInstanceId();
    }

    /* access modifiers changed from: private */
    public void setAppInstanceIdBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.appInstanceId_ = value.toStringUtf8();
    }

    public String getAppInstanceIdToken() {
        return this.appInstanceIdToken_;
    }

    public ByteString getAppInstanceIdTokenBytes() {
        return ByteString.copyFromUtf8(this.appInstanceIdToken_);
    }

    /* access modifiers changed from: private */
    public void setAppInstanceIdToken(String value) {
        value.getClass();
        this.appInstanceIdToken_ = value;
    }

    /* access modifiers changed from: private */
    public void clearAppInstanceIdToken() {
        this.appInstanceIdToken_ = getDefaultInstance().getAppInstanceIdToken();
    }

    /* access modifiers changed from: private */
    public void setAppInstanceIdTokenBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.appInstanceIdToken_ = value.toStringUtf8();
    }

    public static ClientAppInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (ClientAppInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static ClientAppInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (ClientAppInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static ClientAppInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (ClientAppInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static ClientAppInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (ClientAppInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static ClientAppInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (ClientAppInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static ClientAppInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (ClientAppInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static ClientAppInfo parseFrom(InputStream input) throws IOException {
        return (ClientAppInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static ClientAppInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (ClientAppInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static ClientAppInfo parseDelimitedFrom(InputStream input) throws IOException {
        return (ClientAppInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static ClientAppInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (ClientAppInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static ClientAppInfo parseFrom(CodedInputStream input) throws IOException {
        return (ClientAppInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static ClientAppInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (ClientAppInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(ClientAppInfo prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.ClientAppInfo$Builder */
    public static final class Builder extends GeneratedMessageLite.Builder<ClientAppInfo, Builder> implements ClientAppInfoOrBuilder {
        /* synthetic */ Builder(C12581 x0) {
            this();
        }

        private Builder() {
            super(ClientAppInfo.DEFAULT_INSTANCE);
        }

        public String getGmpAppId() {
            return ((ClientAppInfo) this.instance).getGmpAppId();
        }

        public ByteString getGmpAppIdBytes() {
            return ((ClientAppInfo) this.instance).getGmpAppIdBytes();
        }

        public Builder setGmpAppId(String value) {
            copyOnWrite();
            ((ClientAppInfo) this.instance).setGmpAppId(value);
            return this;
        }

        public Builder clearGmpAppId() {
            copyOnWrite();
            ((ClientAppInfo) this.instance).clearGmpAppId();
            return this;
        }

        public Builder setGmpAppIdBytes(ByteString value) {
            copyOnWrite();
            ((ClientAppInfo) this.instance).setGmpAppIdBytes(value);
            return this;
        }

        public String getAppInstanceId() {
            return ((ClientAppInfo) this.instance).getAppInstanceId();
        }

        public ByteString getAppInstanceIdBytes() {
            return ((ClientAppInfo) this.instance).getAppInstanceIdBytes();
        }

        public Builder setAppInstanceId(String value) {
            copyOnWrite();
            ((ClientAppInfo) this.instance).setAppInstanceId(value);
            return this;
        }

        public Builder clearAppInstanceId() {
            copyOnWrite();
            ((ClientAppInfo) this.instance).clearAppInstanceId();
            return this;
        }

        public Builder setAppInstanceIdBytes(ByteString value) {
            copyOnWrite();
            ((ClientAppInfo) this.instance).setAppInstanceIdBytes(value);
            return this;
        }

        public String getAppInstanceIdToken() {
            return ((ClientAppInfo) this.instance).getAppInstanceIdToken();
        }

        public ByteString getAppInstanceIdTokenBytes() {
            return ((ClientAppInfo) this.instance).getAppInstanceIdTokenBytes();
        }

        public Builder setAppInstanceIdToken(String value) {
            copyOnWrite();
            ((ClientAppInfo) this.instance).setAppInstanceIdToken(value);
            return this;
        }

        public Builder clearAppInstanceIdToken() {
            copyOnWrite();
            ((ClientAppInfo) this.instance).clearAppInstanceIdToken();
            return this;
        }

        public Builder setAppInstanceIdTokenBytes(ByteString value) {
            copyOnWrite();
            ((ClientAppInfo) this.instance).setAppInstanceIdTokenBytes(value);
            return this;
        }
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.ClientAppInfo$1 */
    static /* synthetic */ class C12581 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        static final /* synthetic */ int[] f109xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f109xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f109xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f109xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f109xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f109xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f109xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f109xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (C12581.f109xa1df5c61[method.ordinal()]) {
            case 1:
                return new ClientAppInfo();
            case 2:
                return new Builder((C12581) null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ", new Object[]{"gmpAppId_", "appInstanceId_", "appInstanceIdToken_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<ClientAppInfo> parser = PARSER;
                if (parser == null) {
                    synchronized (ClientAppInfo.class) {
                        parser = PARSER;
                        if (parser == null) {
                            parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                            PARSER = parser;
                        }
                    }
                }
                return parser;
            case 6:
                return (byte) 1;
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    static {
        ClientAppInfo defaultInstance = new ClientAppInfo();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(ClientAppInfo.class, defaultInstance);
    }

    public static ClientAppInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ClientAppInfo> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
