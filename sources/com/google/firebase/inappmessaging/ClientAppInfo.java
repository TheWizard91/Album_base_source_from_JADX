package com.google.firebase.inappmessaging;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ClientAppInfo extends GeneratedMessageLite<ClientAppInfo, Builder> implements ClientAppInfoOrBuilder {
    /* access modifiers changed from: private */
    public static final ClientAppInfo DEFAULT_INSTANCE;
    public static final int FIREBASE_INSTANCE_ID_FIELD_NUMBER = 2;
    public static final int GOOGLE_APP_ID_FIELD_NUMBER = 1;
    private static volatile Parser<ClientAppInfo> PARSER;
    private int bitField0_;
    private String firebaseInstanceId_ = "";
    private String googleAppId_ = "";

    private ClientAppInfo() {
    }

    public boolean hasGoogleAppId() {
        return (this.bitField0_ & 1) != 0;
    }

    public String getGoogleAppId() {
        return this.googleAppId_;
    }

    public ByteString getGoogleAppIdBytes() {
        return ByteString.copyFromUtf8(this.googleAppId_);
    }

    /* access modifiers changed from: private */
    public void setGoogleAppId(String value) {
        value.getClass();
        this.bitField0_ |= 1;
        this.googleAppId_ = value;
    }

    /* access modifiers changed from: private */
    public void clearGoogleAppId() {
        this.bitField0_ &= -2;
        this.googleAppId_ = getDefaultInstance().getGoogleAppId();
    }

    /* access modifiers changed from: private */
    public void setGoogleAppIdBytes(ByteString value) {
        this.googleAppId_ = value.toStringUtf8();
        this.bitField0_ |= 1;
    }

    public boolean hasFirebaseInstanceId() {
        return (this.bitField0_ & 2) != 0;
    }

    public String getFirebaseInstanceId() {
        return this.firebaseInstanceId_;
    }

    public ByteString getFirebaseInstanceIdBytes() {
        return ByteString.copyFromUtf8(this.firebaseInstanceId_);
    }

    /* access modifiers changed from: private */
    public void setFirebaseInstanceId(String value) {
        value.getClass();
        this.bitField0_ |= 2;
        this.firebaseInstanceId_ = value;
    }

    /* access modifiers changed from: private */
    public void clearFirebaseInstanceId() {
        this.bitField0_ &= -3;
        this.firebaseInstanceId_ = getDefaultInstance().getFirebaseInstanceId();
    }

    /* access modifiers changed from: private */
    public void setFirebaseInstanceIdBytes(ByteString value) {
        this.firebaseInstanceId_ = value.toStringUtf8();
        this.bitField0_ |= 2;
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

    public static final class Builder extends GeneratedMessageLite.Builder<ClientAppInfo, Builder> implements ClientAppInfoOrBuilder {
        /* synthetic */ Builder(C39301 x0) {
            this();
        }

        private Builder() {
            super(ClientAppInfo.DEFAULT_INSTANCE);
        }

        public boolean hasGoogleAppId() {
            return ((ClientAppInfo) this.instance).hasGoogleAppId();
        }

        public String getGoogleAppId() {
            return ((ClientAppInfo) this.instance).getGoogleAppId();
        }

        public ByteString getGoogleAppIdBytes() {
            return ((ClientAppInfo) this.instance).getGoogleAppIdBytes();
        }

        public Builder setGoogleAppId(String value) {
            copyOnWrite();
            ((ClientAppInfo) this.instance).setGoogleAppId(value);
            return this;
        }

        public Builder clearGoogleAppId() {
            copyOnWrite();
            ((ClientAppInfo) this.instance).clearGoogleAppId();
            return this;
        }

        public Builder setGoogleAppIdBytes(ByteString value) {
            copyOnWrite();
            ((ClientAppInfo) this.instance).setGoogleAppIdBytes(value);
            return this;
        }

        public boolean hasFirebaseInstanceId() {
            return ((ClientAppInfo) this.instance).hasFirebaseInstanceId();
        }

        public String getFirebaseInstanceId() {
            return ((ClientAppInfo) this.instance).getFirebaseInstanceId();
        }

        public ByteString getFirebaseInstanceIdBytes() {
            return ((ClientAppInfo) this.instance).getFirebaseInstanceIdBytes();
        }

        public Builder setFirebaseInstanceId(String value) {
            copyOnWrite();
            ((ClientAppInfo) this.instance).setFirebaseInstanceId(value);
            return this;
        }

        public Builder clearFirebaseInstanceId() {
            copyOnWrite();
            ((ClientAppInfo) this.instance).clearFirebaseInstanceId();
            return this;
        }

        public Builder setFirebaseInstanceIdBytes(ByteString value) {
            copyOnWrite();
            ((ClientAppInfo) this.instance).setFirebaseInstanceIdBytes(value);
            return this;
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.ClientAppInfo$1 */
    static /* synthetic */ class C39301 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        static final /* synthetic */ int[] f1737xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f1737xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1737xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1737xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1737xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1737xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1737xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1737xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (C39301.f1737xa1df5c61[method.ordinal()]) {
            case 1:
                return new ClientAppInfo();
            case 2:
                return new Builder((C39301) null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001", new Object[]{"bitField0_", "googleAppId_", "firebaseInstanceId_"});
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
