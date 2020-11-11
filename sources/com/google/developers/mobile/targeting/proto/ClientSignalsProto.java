package com.google.developers.mobile.targeting.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ClientSignalsProto {

    public interface AppInstanceClaimOrBuilder extends MessageLiteOrBuilder {
        String getAppInstanceId();

        ByteString getAppInstanceIdBytes();

        String getAppInstanceToken();

        ByteString getAppInstanceTokenBytes();

        String getGmpAppId();

        ByteString getGmpAppIdBytes();
    }

    public interface ClientSignalsOrBuilder extends MessageLiteOrBuilder {
        String getAppVersion();

        ByteString getAppVersionBytes();

        String getLanguageCode();

        ByteString getLanguageCodeBytes();

        String getPlatformVersion();

        ByteString getPlatformVersionBytes();

        String getTimeZone();

        ByteString getTimeZoneBytes();
    }

    private ClientSignalsProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static final class ClientSignals extends GeneratedMessageLite<ClientSignals, Builder> implements ClientSignalsOrBuilder {
        public static final int APP_VERSION_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final ClientSignals DEFAULT_INSTANCE;
        public static final int LANGUAGE_CODE_FIELD_NUMBER = 3;
        private static volatile Parser<ClientSignals> PARSER = null;
        public static final int PLATFORM_VERSION_FIELD_NUMBER = 2;
        public static final int TIME_ZONE_FIELD_NUMBER = 4;
        private String appVersion_ = "";
        private String languageCode_ = "";
        private String platformVersion_ = "";
        private String timeZone_ = "";

        private ClientSignals() {
        }

        public String getAppVersion() {
            return this.appVersion_;
        }

        public ByteString getAppVersionBytes() {
            return ByteString.copyFromUtf8(this.appVersion_);
        }

        /* access modifiers changed from: private */
        public void setAppVersion(String value) {
            value.getClass();
            this.appVersion_ = value;
        }

        /* access modifiers changed from: private */
        public void clearAppVersion() {
            this.appVersion_ = getDefaultInstance().getAppVersion();
        }

        /* access modifiers changed from: private */
        public void setAppVersionBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.appVersion_ = value.toStringUtf8();
        }

        public String getPlatformVersion() {
            return this.platformVersion_;
        }

        public ByteString getPlatformVersionBytes() {
            return ByteString.copyFromUtf8(this.platformVersion_);
        }

        /* access modifiers changed from: private */
        public void setPlatformVersion(String value) {
            value.getClass();
            this.platformVersion_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPlatformVersion() {
            this.platformVersion_ = getDefaultInstance().getPlatformVersion();
        }

        /* access modifiers changed from: private */
        public void setPlatformVersionBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.platformVersion_ = value.toStringUtf8();
        }

        public String getLanguageCode() {
            return this.languageCode_;
        }

        public ByteString getLanguageCodeBytes() {
            return ByteString.copyFromUtf8(this.languageCode_);
        }

        /* access modifiers changed from: private */
        public void setLanguageCode(String value) {
            value.getClass();
            this.languageCode_ = value;
        }

        /* access modifiers changed from: private */
        public void clearLanguageCode() {
            this.languageCode_ = getDefaultInstance().getLanguageCode();
        }

        /* access modifiers changed from: private */
        public void setLanguageCodeBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.languageCode_ = value.toStringUtf8();
        }

        public String getTimeZone() {
            return this.timeZone_;
        }

        public ByteString getTimeZoneBytes() {
            return ByteString.copyFromUtf8(this.timeZone_);
        }

        /* access modifiers changed from: private */
        public void setTimeZone(String value) {
            value.getClass();
            this.timeZone_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTimeZone() {
            this.timeZone_ = getDefaultInstance().getTimeZone();
        }

        /* access modifiers changed from: private */
        public void setTimeZoneBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.timeZone_ = value.toStringUtf8();
        }

        public static ClientSignals parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ClientSignals) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ClientSignals parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClientSignals) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ClientSignals parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ClientSignals) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ClientSignals parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClientSignals) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ClientSignals parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ClientSignals) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ClientSignals parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClientSignals) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ClientSignals parseFrom(InputStream input) throws IOException {
            return (ClientSignals) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ClientSignals parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClientSignals) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ClientSignals parseDelimitedFrom(InputStream input) throws IOException {
            return (ClientSignals) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ClientSignals parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClientSignals) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ClientSignals parseFrom(CodedInputStream input) throws IOException {
            return (ClientSignals) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ClientSignals parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClientSignals) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ClientSignals prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ClientSignals, Builder> implements ClientSignalsOrBuilder {
            /* synthetic */ Builder(C37341 x0) {
                this();
            }

            private Builder() {
                super(ClientSignals.DEFAULT_INSTANCE);
            }

            public String getAppVersion() {
                return ((ClientSignals) this.instance).getAppVersion();
            }

            public ByteString getAppVersionBytes() {
                return ((ClientSignals) this.instance).getAppVersionBytes();
            }

            public Builder setAppVersion(String value) {
                copyOnWrite();
                ((ClientSignals) this.instance).setAppVersion(value);
                return this;
            }

            public Builder clearAppVersion() {
                copyOnWrite();
                ((ClientSignals) this.instance).clearAppVersion();
                return this;
            }

            public Builder setAppVersionBytes(ByteString value) {
                copyOnWrite();
                ((ClientSignals) this.instance).setAppVersionBytes(value);
                return this;
            }

            public String getPlatformVersion() {
                return ((ClientSignals) this.instance).getPlatformVersion();
            }

            public ByteString getPlatformVersionBytes() {
                return ((ClientSignals) this.instance).getPlatformVersionBytes();
            }

            public Builder setPlatformVersion(String value) {
                copyOnWrite();
                ((ClientSignals) this.instance).setPlatformVersion(value);
                return this;
            }

            public Builder clearPlatformVersion() {
                copyOnWrite();
                ((ClientSignals) this.instance).clearPlatformVersion();
                return this;
            }

            public Builder setPlatformVersionBytes(ByteString value) {
                copyOnWrite();
                ((ClientSignals) this.instance).setPlatformVersionBytes(value);
                return this;
            }

            public String getLanguageCode() {
                return ((ClientSignals) this.instance).getLanguageCode();
            }

            public ByteString getLanguageCodeBytes() {
                return ((ClientSignals) this.instance).getLanguageCodeBytes();
            }

            public Builder setLanguageCode(String value) {
                copyOnWrite();
                ((ClientSignals) this.instance).setLanguageCode(value);
                return this;
            }

            public Builder clearLanguageCode() {
                copyOnWrite();
                ((ClientSignals) this.instance).clearLanguageCode();
                return this;
            }

            public Builder setLanguageCodeBytes(ByteString value) {
                copyOnWrite();
                ((ClientSignals) this.instance).setLanguageCodeBytes(value);
                return this;
            }

            public String getTimeZone() {
                return ((ClientSignals) this.instance).getTimeZone();
            }

            public ByteString getTimeZoneBytes() {
                return ((ClientSignals) this.instance).getTimeZoneBytes();
            }

            public Builder setTimeZone(String value) {
                copyOnWrite();
                ((ClientSignals) this.instance).setTimeZone(value);
                return this;
            }

            public Builder clearTimeZone() {
                copyOnWrite();
                ((ClientSignals) this.instance).clearTimeZone();
                return this;
            }

            public Builder setTimeZoneBytes(ByteString value) {
                copyOnWrite();
                ((ClientSignals) this.instance).setTimeZoneBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C37341.f1689xa1df5c61[method.ordinal()]) {
                case 1:
                    return new ClientSignals();
                case 2:
                    return new Builder((C37341) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ\u0004Ȉ", new Object[]{"appVersion_", "platformVersion_", "languageCode_", "timeZone_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ClientSignals> parser = PARSER;
                    if (parser == null) {
                        synchronized (ClientSignals.class) {
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
            ClientSignals defaultInstance = new ClientSignals();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(ClientSignals.class, defaultInstance);
        }

        public static ClientSignals getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ClientSignals> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* renamed from: com.google.developers.mobile.targeting.proto.ClientSignalsProto$1 */
    static /* synthetic */ class C37341 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        static final /* synthetic */ int[] f1689xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f1689xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1689xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1689xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1689xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1689xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1689xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1689xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public static final class AppInstanceClaim extends GeneratedMessageLite<AppInstanceClaim, Builder> implements AppInstanceClaimOrBuilder {
        public static final int APP_INSTANCE_ID_FIELD_NUMBER = 1;
        public static final int APP_INSTANCE_TOKEN_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final AppInstanceClaim DEFAULT_INSTANCE;
        public static final int GMP_APP_ID_FIELD_NUMBER = 3;
        private static volatile Parser<AppInstanceClaim> PARSER;
        private String appInstanceId_ = "";
        private String appInstanceToken_ = "";
        private String gmpAppId_ = "";

        private AppInstanceClaim() {
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

        public String getAppInstanceToken() {
            return this.appInstanceToken_;
        }

        public ByteString getAppInstanceTokenBytes() {
            return ByteString.copyFromUtf8(this.appInstanceToken_);
        }

        /* access modifiers changed from: private */
        public void setAppInstanceToken(String value) {
            value.getClass();
            this.appInstanceToken_ = value;
        }

        /* access modifiers changed from: private */
        public void clearAppInstanceToken() {
            this.appInstanceToken_ = getDefaultInstance().getAppInstanceToken();
        }

        /* access modifiers changed from: private */
        public void setAppInstanceTokenBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.appInstanceToken_ = value.toStringUtf8();
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

        public static AppInstanceClaim parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (AppInstanceClaim) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AppInstanceClaim parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AppInstanceClaim) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AppInstanceClaim parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AppInstanceClaim) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AppInstanceClaim parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AppInstanceClaim) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AppInstanceClaim parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AppInstanceClaim) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AppInstanceClaim parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AppInstanceClaim) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AppInstanceClaim parseFrom(InputStream input) throws IOException {
            return (AppInstanceClaim) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AppInstanceClaim parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AppInstanceClaim) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AppInstanceClaim parseDelimitedFrom(InputStream input) throws IOException {
            return (AppInstanceClaim) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static AppInstanceClaim parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AppInstanceClaim) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AppInstanceClaim parseFrom(CodedInputStream input) throws IOException {
            return (AppInstanceClaim) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AppInstanceClaim parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AppInstanceClaim) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(AppInstanceClaim prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<AppInstanceClaim, Builder> implements AppInstanceClaimOrBuilder {
            /* synthetic */ Builder(C37341 x0) {
                this();
            }

            private Builder() {
                super(AppInstanceClaim.DEFAULT_INSTANCE);
            }

            public String getAppInstanceId() {
                return ((AppInstanceClaim) this.instance).getAppInstanceId();
            }

            public ByteString getAppInstanceIdBytes() {
                return ((AppInstanceClaim) this.instance).getAppInstanceIdBytes();
            }

            public Builder setAppInstanceId(String value) {
                copyOnWrite();
                ((AppInstanceClaim) this.instance).setAppInstanceId(value);
                return this;
            }

            public Builder clearAppInstanceId() {
                copyOnWrite();
                ((AppInstanceClaim) this.instance).clearAppInstanceId();
                return this;
            }

            public Builder setAppInstanceIdBytes(ByteString value) {
                copyOnWrite();
                ((AppInstanceClaim) this.instance).setAppInstanceIdBytes(value);
                return this;
            }

            public String getAppInstanceToken() {
                return ((AppInstanceClaim) this.instance).getAppInstanceToken();
            }

            public ByteString getAppInstanceTokenBytes() {
                return ((AppInstanceClaim) this.instance).getAppInstanceTokenBytes();
            }

            public Builder setAppInstanceToken(String value) {
                copyOnWrite();
                ((AppInstanceClaim) this.instance).setAppInstanceToken(value);
                return this;
            }

            public Builder clearAppInstanceToken() {
                copyOnWrite();
                ((AppInstanceClaim) this.instance).clearAppInstanceToken();
                return this;
            }

            public Builder setAppInstanceTokenBytes(ByteString value) {
                copyOnWrite();
                ((AppInstanceClaim) this.instance).setAppInstanceTokenBytes(value);
                return this;
            }

            public String getGmpAppId() {
                return ((AppInstanceClaim) this.instance).getGmpAppId();
            }

            public ByteString getGmpAppIdBytes() {
                return ((AppInstanceClaim) this.instance).getGmpAppIdBytes();
            }

            public Builder setGmpAppId(String value) {
                copyOnWrite();
                ((AppInstanceClaim) this.instance).setGmpAppId(value);
                return this;
            }

            public Builder clearGmpAppId() {
                copyOnWrite();
                ((AppInstanceClaim) this.instance).clearGmpAppId();
                return this;
            }

            public Builder setGmpAppIdBytes(ByteString value) {
                copyOnWrite();
                ((AppInstanceClaim) this.instance).setGmpAppIdBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C37341.f1689xa1df5c61[method.ordinal()]) {
                case 1:
                    return new AppInstanceClaim();
                case 2:
                    return new Builder((C37341) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ", new Object[]{"appInstanceId_", "appInstanceToken_", "gmpAppId_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<AppInstanceClaim> parser = PARSER;
                    if (parser == null) {
                        synchronized (AppInstanceClaim.class) {
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
            AppInstanceClaim defaultInstance = new AppInstanceClaim();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(AppInstanceClaim.class, defaultInstance);
        }

        public static AppInstanceClaim getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AppInstanceClaim> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
