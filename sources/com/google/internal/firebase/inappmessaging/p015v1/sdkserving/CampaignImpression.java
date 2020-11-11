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

/* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpression */
public final class CampaignImpression extends GeneratedMessageLite<CampaignImpression, Builder> implements CampaignImpressionOrBuilder {
    public static final int CAMPAIGN_ID_FIELD_NUMBER = 1;
    /* access modifiers changed from: private */
    public static final CampaignImpression DEFAULT_INSTANCE;
    public static final int IMPRESSION_TIMESTAMP_MILLIS_FIELD_NUMBER = 2;
    private static volatile Parser<CampaignImpression> PARSER;
    private String campaignId_ = "";
    private long impressionTimestampMillis_;

    private CampaignImpression() {
    }

    public String getCampaignId() {
        return this.campaignId_;
    }

    public ByteString getCampaignIdBytes() {
        return ByteString.copyFromUtf8(this.campaignId_);
    }

    /* access modifiers changed from: private */
    public void setCampaignId(String value) {
        value.getClass();
        this.campaignId_ = value;
    }

    /* access modifiers changed from: private */
    public void clearCampaignId() {
        this.campaignId_ = getDefaultInstance().getCampaignId();
    }

    /* access modifiers changed from: private */
    public void setCampaignIdBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.campaignId_ = value.toStringUtf8();
    }

    public long getImpressionTimestampMillis() {
        return this.impressionTimestampMillis_;
    }

    /* access modifiers changed from: private */
    public void setImpressionTimestampMillis(long value) {
        this.impressionTimestampMillis_ = value;
    }

    /* access modifiers changed from: private */
    public void clearImpressionTimestampMillis() {
        this.impressionTimestampMillis_ = 0;
    }

    public static CampaignImpression parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (CampaignImpression) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CampaignImpression parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CampaignImpression) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CampaignImpression parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (CampaignImpression) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CampaignImpression parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CampaignImpression) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CampaignImpression parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (CampaignImpression) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CampaignImpression parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CampaignImpression) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CampaignImpression parseFrom(InputStream input) throws IOException {
        return (CampaignImpression) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static CampaignImpression parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CampaignImpression) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static CampaignImpression parseDelimitedFrom(InputStream input) throws IOException {
        return (CampaignImpression) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static CampaignImpression parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CampaignImpression) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static CampaignImpression parseFrom(CodedInputStream input) throws IOException {
        return (CampaignImpression) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static CampaignImpression parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CampaignImpression) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(CampaignImpression prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpression$Builder */
    public static final class Builder extends GeneratedMessageLite.Builder<CampaignImpression, Builder> implements CampaignImpressionOrBuilder {
        /* synthetic */ Builder(C12561 x0) {
            this();
        }

        private Builder() {
            super(CampaignImpression.DEFAULT_INSTANCE);
        }

        public String getCampaignId() {
            return ((CampaignImpression) this.instance).getCampaignId();
        }

        public ByteString getCampaignIdBytes() {
            return ((CampaignImpression) this.instance).getCampaignIdBytes();
        }

        public Builder setCampaignId(String value) {
            copyOnWrite();
            ((CampaignImpression) this.instance).setCampaignId(value);
            return this;
        }

        public Builder clearCampaignId() {
            copyOnWrite();
            ((CampaignImpression) this.instance).clearCampaignId();
            return this;
        }

        public Builder setCampaignIdBytes(ByteString value) {
            copyOnWrite();
            ((CampaignImpression) this.instance).setCampaignIdBytes(value);
            return this;
        }

        public long getImpressionTimestampMillis() {
            return ((CampaignImpression) this.instance).getImpressionTimestampMillis();
        }

        public Builder setImpressionTimestampMillis(long value) {
            copyOnWrite();
            ((CampaignImpression) this.instance).setImpressionTimestampMillis(value);
            return this;
        }

        public Builder clearImpressionTimestampMillis() {
            copyOnWrite();
            ((CampaignImpression) this.instance).clearImpressionTimestampMillis();
            return this;
        }
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpression$1 */
    static /* synthetic */ class C12561 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        static final /* synthetic */ int[] f107xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f107xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f107xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f107xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f107xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f107xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f107xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f107xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (C12561.f107xa1df5c61[method.ordinal()]) {
            case 1:
                return new CampaignImpression();
            case 2:
                return new Builder((C12561) null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\u0002", new Object[]{"campaignId_", "impressionTimestampMillis_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<CampaignImpression> parser = PARSER;
                if (parser == null) {
                    synchronized (CampaignImpression.class) {
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
        CampaignImpression defaultInstance = new CampaignImpression();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(CampaignImpression.class, defaultInstance);
    }

    public static CampaignImpression getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<CampaignImpression> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
