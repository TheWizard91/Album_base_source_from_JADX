package com.google.firebase.inappmessaging;

import com.google.firebase.inappmessaging.ClientAppInfo;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class CampaignAnalytics extends GeneratedMessageLite<CampaignAnalytics, Builder> implements CampaignAnalyticsOrBuilder {
    public static final int CAMPAIGN_ID_FIELD_NUMBER = 2;
    public static final int CLIENT_APP_FIELD_NUMBER = 3;
    public static final int CLIENT_TIMESTAMP_MILLIS_FIELD_NUMBER = 4;
    /* access modifiers changed from: private */
    public static final CampaignAnalytics DEFAULT_INSTANCE;
    public static final int DISMISS_TYPE_FIELD_NUMBER = 6;
    public static final int ENGAGEMENTMETRICS_DELIVERY_RETRY_COUNT_FIELD_NUMBER = 10;
    public static final int EVENT_TYPE_FIELD_NUMBER = 5;
    public static final int FETCH_ERROR_REASON_FIELD_NUMBER = 8;
    public static final int FIAM_SDK_VERSION_FIELD_NUMBER = 9;
    private static volatile Parser<CampaignAnalytics> PARSER = null;
    public static final int PROJECT_NUMBER_FIELD_NUMBER = 1;
    public static final int RENDER_ERROR_REASON_FIELD_NUMBER = 7;
    private int bitField0_;
    private String campaignId_ = "";
    private ClientAppInfo clientApp_;
    private long clientTimestampMillis_;
    private int engagementMetricsDeliveryRetryCount_;
    private int eventCase_ = 0;
    private Object event_;
    private String fiamSdkVersion_ = "";
    private String projectNumber_ = "";

    private CampaignAnalytics() {
    }

    public enum EventCase {
        EVENT_TYPE(5),
        DISMISS_TYPE(6),
        RENDER_ERROR_REASON(7),
        FETCH_ERROR_REASON(8),
        EVENT_NOT_SET(0);
        
        private final int value;

        private EventCase(int value2) {
            this.value = value2;
        }

        @Deprecated
        public static EventCase valueOf(int value2) {
            return forNumber(value2);
        }

        public static EventCase forNumber(int value2) {
            if (value2 == 0) {
                return EVENT_NOT_SET;
            }
            if (value2 == 5) {
                return EVENT_TYPE;
            }
            if (value2 == 6) {
                return DISMISS_TYPE;
            }
            if (value2 == 7) {
                return RENDER_ERROR_REASON;
            }
            if (value2 != 8) {
                return null;
            }
            return FETCH_ERROR_REASON;
        }

        public int getNumber() {
            return this.value;
        }
    }

    public EventCase getEventCase() {
        return EventCase.forNumber(this.eventCase_);
    }

    /* access modifiers changed from: private */
    public void clearEvent() {
        this.eventCase_ = 0;
        this.event_ = null;
    }

    public boolean hasProjectNumber() {
        return (this.bitField0_ & 1) != 0;
    }

    public String getProjectNumber() {
        return this.projectNumber_;
    }

    public ByteString getProjectNumberBytes() {
        return ByteString.copyFromUtf8(this.projectNumber_);
    }

    /* access modifiers changed from: private */
    public void setProjectNumber(String value) {
        value.getClass();
        this.bitField0_ |= 1;
        this.projectNumber_ = value;
    }

    /* access modifiers changed from: private */
    public void clearProjectNumber() {
        this.bitField0_ &= -2;
        this.projectNumber_ = getDefaultInstance().getProjectNumber();
    }

    /* access modifiers changed from: private */
    public void setProjectNumberBytes(ByteString value) {
        this.projectNumber_ = value.toStringUtf8();
        this.bitField0_ |= 1;
    }

    public boolean hasCampaignId() {
        return (this.bitField0_ & 2) != 0;
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
        this.bitField0_ |= 2;
        this.campaignId_ = value;
    }

    /* access modifiers changed from: private */
    public void clearCampaignId() {
        this.bitField0_ &= -3;
        this.campaignId_ = getDefaultInstance().getCampaignId();
    }

    /* access modifiers changed from: private */
    public void setCampaignIdBytes(ByteString value) {
        this.campaignId_ = value.toStringUtf8();
        this.bitField0_ |= 2;
    }

    public boolean hasClientApp() {
        return (this.bitField0_ & 4) != 0;
    }

    public ClientAppInfo getClientApp() {
        ClientAppInfo clientAppInfo = this.clientApp_;
        return clientAppInfo == null ? ClientAppInfo.getDefaultInstance() : clientAppInfo;
    }

    /* access modifiers changed from: private */
    public void setClientApp(ClientAppInfo value) {
        value.getClass();
        this.clientApp_ = value;
        this.bitField0_ |= 4;
    }

    /* access modifiers changed from: private */
    public void mergeClientApp(ClientAppInfo value) {
        value.getClass();
        ClientAppInfo clientAppInfo = this.clientApp_;
        if (clientAppInfo == null || clientAppInfo == ClientAppInfo.getDefaultInstance()) {
            this.clientApp_ = value;
        } else {
            this.clientApp_ = (ClientAppInfo) ((ClientAppInfo.Builder) ClientAppInfo.newBuilder(this.clientApp_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 4;
    }

    /* access modifiers changed from: private */
    public void clearClientApp() {
        this.clientApp_ = null;
        this.bitField0_ &= -5;
    }

    public boolean hasClientTimestampMillis() {
        return (this.bitField0_ & 8) != 0;
    }

    public long getClientTimestampMillis() {
        return this.clientTimestampMillis_;
    }

    /* access modifiers changed from: private */
    public void setClientTimestampMillis(long value) {
        this.bitField0_ |= 8;
        this.clientTimestampMillis_ = value;
    }

    /* access modifiers changed from: private */
    public void clearClientTimestampMillis() {
        this.bitField0_ &= -9;
        this.clientTimestampMillis_ = 0;
    }

    public boolean hasEventType() {
        return this.eventCase_ == 5;
    }

    public EventType getEventType() {
        if (this.eventCase_ != 5) {
            return EventType.UNKNOWN_EVENT_TYPE;
        }
        EventType result = EventType.forNumber(((Integer) this.event_).intValue());
        return result == null ? EventType.UNKNOWN_EVENT_TYPE : result;
    }

    /* access modifiers changed from: private */
    public void setEventType(EventType value) {
        this.event_ = Integer.valueOf(value.getNumber());
        this.eventCase_ = 5;
    }

    /* access modifiers changed from: private */
    public void clearEventType() {
        if (this.eventCase_ == 5) {
            this.eventCase_ = 0;
            this.event_ = null;
        }
    }

    public boolean hasDismissType() {
        return this.eventCase_ == 6;
    }

    public DismissType getDismissType() {
        if (this.eventCase_ != 6) {
            return DismissType.UNKNOWN_DISMISS_TYPE;
        }
        DismissType result = DismissType.forNumber(((Integer) this.event_).intValue());
        return result == null ? DismissType.UNKNOWN_DISMISS_TYPE : result;
    }

    /* access modifiers changed from: private */
    public void setDismissType(DismissType value) {
        this.event_ = Integer.valueOf(value.getNumber());
        this.eventCase_ = 6;
    }

    /* access modifiers changed from: private */
    public void clearDismissType() {
        if (this.eventCase_ == 6) {
            this.eventCase_ = 0;
            this.event_ = null;
        }
    }

    public boolean hasRenderErrorReason() {
        return this.eventCase_ == 7;
    }

    public RenderErrorReason getRenderErrorReason() {
        if (this.eventCase_ != 7) {
            return RenderErrorReason.UNSPECIFIED_RENDER_ERROR;
        }
        RenderErrorReason result = RenderErrorReason.forNumber(((Integer) this.event_).intValue());
        return result == null ? RenderErrorReason.UNSPECIFIED_RENDER_ERROR : result;
    }

    /* access modifiers changed from: private */
    public void setRenderErrorReason(RenderErrorReason value) {
        this.event_ = Integer.valueOf(value.getNumber());
        this.eventCase_ = 7;
    }

    /* access modifiers changed from: private */
    public void clearRenderErrorReason() {
        if (this.eventCase_ == 7) {
            this.eventCase_ = 0;
            this.event_ = null;
        }
    }

    public boolean hasFetchErrorReason() {
        return this.eventCase_ == 8;
    }

    public FetchErrorReason getFetchErrorReason() {
        if (this.eventCase_ != 8) {
            return FetchErrorReason.UNSPECIFIED_FETCH_ERROR;
        }
        FetchErrorReason result = FetchErrorReason.forNumber(((Integer) this.event_).intValue());
        return result == null ? FetchErrorReason.UNSPECIFIED_FETCH_ERROR : result;
    }

    /* access modifiers changed from: private */
    public void setFetchErrorReason(FetchErrorReason value) {
        this.event_ = Integer.valueOf(value.getNumber());
        this.eventCase_ = 8;
    }

    /* access modifiers changed from: private */
    public void clearFetchErrorReason() {
        if (this.eventCase_ == 8) {
            this.eventCase_ = 0;
            this.event_ = null;
        }
    }

    public boolean hasFiamSdkVersion() {
        return (this.bitField0_ & 256) != 0;
    }

    public String getFiamSdkVersion() {
        return this.fiamSdkVersion_;
    }

    public ByteString getFiamSdkVersionBytes() {
        return ByteString.copyFromUtf8(this.fiamSdkVersion_);
    }

    /* access modifiers changed from: private */
    public void setFiamSdkVersion(String value) {
        value.getClass();
        this.bitField0_ |= 256;
        this.fiamSdkVersion_ = value;
    }

    /* access modifiers changed from: private */
    public void clearFiamSdkVersion() {
        this.bitField0_ &= -257;
        this.fiamSdkVersion_ = getDefaultInstance().getFiamSdkVersion();
    }

    /* access modifiers changed from: private */
    public void setFiamSdkVersionBytes(ByteString value) {
        this.fiamSdkVersion_ = value.toStringUtf8();
        this.bitField0_ |= 256;
    }

    public boolean hasEngagementMetricsDeliveryRetryCount() {
        return (this.bitField0_ & 512) != 0;
    }

    public int getEngagementMetricsDeliveryRetryCount() {
        return this.engagementMetricsDeliveryRetryCount_;
    }

    /* access modifiers changed from: private */
    public void setEngagementMetricsDeliveryRetryCount(int value) {
        this.bitField0_ |= 512;
        this.engagementMetricsDeliveryRetryCount_ = value;
    }

    /* access modifiers changed from: private */
    public void clearEngagementMetricsDeliveryRetryCount() {
        this.bitField0_ &= -513;
        this.engagementMetricsDeliveryRetryCount_ = 0;
    }

    public static CampaignAnalytics parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (CampaignAnalytics) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CampaignAnalytics parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CampaignAnalytics) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CampaignAnalytics parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (CampaignAnalytics) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CampaignAnalytics parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CampaignAnalytics) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CampaignAnalytics parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (CampaignAnalytics) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CampaignAnalytics parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CampaignAnalytics) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CampaignAnalytics parseFrom(InputStream input) throws IOException {
        return (CampaignAnalytics) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static CampaignAnalytics parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CampaignAnalytics) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static CampaignAnalytics parseDelimitedFrom(InputStream input) throws IOException {
        return (CampaignAnalytics) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static CampaignAnalytics parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CampaignAnalytics) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static CampaignAnalytics parseFrom(CodedInputStream input) throws IOException {
        return (CampaignAnalytics) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static CampaignAnalytics parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CampaignAnalytics) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(CampaignAnalytics prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<CampaignAnalytics, Builder> implements CampaignAnalyticsOrBuilder {
        /* synthetic */ Builder(C39291 x0) {
            this();
        }

        private Builder() {
            super(CampaignAnalytics.DEFAULT_INSTANCE);
        }

        public EventCase getEventCase() {
            return ((CampaignAnalytics) this.instance).getEventCase();
        }

        public Builder clearEvent() {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).clearEvent();
            return this;
        }

        public boolean hasProjectNumber() {
            return ((CampaignAnalytics) this.instance).hasProjectNumber();
        }

        public String getProjectNumber() {
            return ((CampaignAnalytics) this.instance).getProjectNumber();
        }

        public ByteString getProjectNumberBytes() {
            return ((CampaignAnalytics) this.instance).getProjectNumberBytes();
        }

        public Builder setProjectNumber(String value) {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).setProjectNumber(value);
            return this;
        }

        public Builder clearProjectNumber() {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).clearProjectNumber();
            return this;
        }

        public Builder setProjectNumberBytes(ByteString value) {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).setProjectNumberBytes(value);
            return this;
        }

        public boolean hasCampaignId() {
            return ((CampaignAnalytics) this.instance).hasCampaignId();
        }

        public String getCampaignId() {
            return ((CampaignAnalytics) this.instance).getCampaignId();
        }

        public ByteString getCampaignIdBytes() {
            return ((CampaignAnalytics) this.instance).getCampaignIdBytes();
        }

        public Builder setCampaignId(String value) {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).setCampaignId(value);
            return this;
        }

        public Builder clearCampaignId() {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).clearCampaignId();
            return this;
        }

        public Builder setCampaignIdBytes(ByteString value) {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).setCampaignIdBytes(value);
            return this;
        }

        public boolean hasClientApp() {
            return ((CampaignAnalytics) this.instance).hasClientApp();
        }

        public ClientAppInfo getClientApp() {
            return ((CampaignAnalytics) this.instance).getClientApp();
        }

        public Builder setClientApp(ClientAppInfo value) {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).setClientApp(value);
            return this;
        }

        public Builder setClientApp(ClientAppInfo.Builder builderForValue) {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).setClientApp((ClientAppInfo) builderForValue.build());
            return this;
        }

        public Builder mergeClientApp(ClientAppInfo value) {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).mergeClientApp(value);
            return this;
        }

        public Builder clearClientApp() {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).clearClientApp();
            return this;
        }

        public boolean hasClientTimestampMillis() {
            return ((CampaignAnalytics) this.instance).hasClientTimestampMillis();
        }

        public long getClientTimestampMillis() {
            return ((CampaignAnalytics) this.instance).getClientTimestampMillis();
        }

        public Builder setClientTimestampMillis(long value) {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).setClientTimestampMillis(value);
            return this;
        }

        public Builder clearClientTimestampMillis() {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).clearClientTimestampMillis();
            return this;
        }

        public boolean hasEventType() {
            return ((CampaignAnalytics) this.instance).hasEventType();
        }

        public EventType getEventType() {
            return ((CampaignAnalytics) this.instance).getEventType();
        }

        public Builder setEventType(EventType value) {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).setEventType(value);
            return this;
        }

        public Builder clearEventType() {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).clearEventType();
            return this;
        }

        public boolean hasDismissType() {
            return ((CampaignAnalytics) this.instance).hasDismissType();
        }

        public DismissType getDismissType() {
            return ((CampaignAnalytics) this.instance).getDismissType();
        }

        public Builder setDismissType(DismissType value) {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).setDismissType(value);
            return this;
        }

        public Builder clearDismissType() {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).clearDismissType();
            return this;
        }

        public boolean hasRenderErrorReason() {
            return ((CampaignAnalytics) this.instance).hasRenderErrorReason();
        }

        public RenderErrorReason getRenderErrorReason() {
            return ((CampaignAnalytics) this.instance).getRenderErrorReason();
        }

        public Builder setRenderErrorReason(RenderErrorReason value) {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).setRenderErrorReason(value);
            return this;
        }

        public Builder clearRenderErrorReason() {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).clearRenderErrorReason();
            return this;
        }

        public boolean hasFetchErrorReason() {
            return ((CampaignAnalytics) this.instance).hasFetchErrorReason();
        }

        public FetchErrorReason getFetchErrorReason() {
            return ((CampaignAnalytics) this.instance).getFetchErrorReason();
        }

        public Builder setFetchErrorReason(FetchErrorReason value) {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).setFetchErrorReason(value);
            return this;
        }

        public Builder clearFetchErrorReason() {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).clearFetchErrorReason();
            return this;
        }

        public boolean hasFiamSdkVersion() {
            return ((CampaignAnalytics) this.instance).hasFiamSdkVersion();
        }

        public String getFiamSdkVersion() {
            return ((CampaignAnalytics) this.instance).getFiamSdkVersion();
        }

        public ByteString getFiamSdkVersionBytes() {
            return ((CampaignAnalytics) this.instance).getFiamSdkVersionBytes();
        }

        public Builder setFiamSdkVersion(String value) {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).setFiamSdkVersion(value);
            return this;
        }

        public Builder clearFiamSdkVersion() {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).clearFiamSdkVersion();
            return this;
        }

        public Builder setFiamSdkVersionBytes(ByteString value) {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).setFiamSdkVersionBytes(value);
            return this;
        }

        public boolean hasEngagementMetricsDeliveryRetryCount() {
            return ((CampaignAnalytics) this.instance).hasEngagementMetricsDeliveryRetryCount();
        }

        public int getEngagementMetricsDeliveryRetryCount() {
            return ((CampaignAnalytics) this.instance).getEngagementMetricsDeliveryRetryCount();
        }

        public Builder setEngagementMetricsDeliveryRetryCount(int value) {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).setEngagementMetricsDeliveryRetryCount(value);
            return this;
        }

        public Builder clearEngagementMetricsDeliveryRetryCount() {
            copyOnWrite();
            ((CampaignAnalytics) this.instance).clearEngagementMetricsDeliveryRetryCount();
            return this;
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.CampaignAnalytics$1 */
    static /* synthetic */ class C39291 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        static final /* synthetic */ int[] f1736xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f1736xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1736xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1736xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1736xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1736xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1736xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1736xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (C39291.f1736xa1df5c61[method.ordinal()]) {
            case 1:
                return new CampaignAnalytics();
            case 2:
                return new Builder((C39291) null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0001\n\u0001\u0001\u0001\n\n\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\t\u0002\u0004\u0002\u0003\u0005?\u0000\u0006?\u0000\u0007?\u0000\b?\u0000\t\b\b\n\u0004\t", new Object[]{"event_", "eventCase_", "bitField0_", "projectNumber_", "campaignId_", "clientApp_", "clientTimestampMillis_", EventType.internalGetVerifier(), DismissType.internalGetVerifier(), RenderErrorReason.internalGetVerifier(), FetchErrorReason.internalGetVerifier(), "fiamSdkVersion_", "engagementMetricsDeliveryRetryCount_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<CampaignAnalytics> parser = PARSER;
                if (parser == null) {
                    synchronized (CampaignAnalytics.class) {
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
        CampaignAnalytics defaultInstance = new CampaignAnalytics();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(CampaignAnalytics.class, defaultInstance);
    }

    public static CampaignAnalytics getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<CampaignAnalytics> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
