package com.google.internal.firebase.inappmessaging.p015v1.sdkserving;

import com.google.developers.mobile.targeting.proto.ClientSignalsProto;
import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.CampaignImpression;
import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.ClientAppInfo;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.FetchEligibleCampaignsRequest */
public final class FetchEligibleCampaignsRequest extends GeneratedMessageLite<FetchEligibleCampaignsRequest, Builder> implements FetchEligibleCampaignsRequestOrBuilder {
    public static final int ALREADY_SEEN_CAMPAIGNS_FIELD_NUMBER = 3;
    public static final int CLIENT_SIGNALS_FIELD_NUMBER = 4;
    /* access modifiers changed from: private */
    public static final FetchEligibleCampaignsRequest DEFAULT_INSTANCE;
    private static volatile Parser<FetchEligibleCampaignsRequest> PARSER = null;
    public static final int PROJECT_NUMBER_FIELD_NUMBER = 1;
    public static final int REQUESTING_CLIENT_APP_FIELD_NUMBER = 2;
    private Internal.ProtobufList<CampaignImpression> alreadySeenCampaigns_ = emptyProtobufList();
    private ClientSignalsProto.ClientSignals clientSignals_;
    private String projectNumber_ = "";
    private ClientAppInfo requestingClientApp_;

    private FetchEligibleCampaignsRequest() {
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
        this.projectNumber_ = value;
    }

    /* access modifiers changed from: private */
    public void clearProjectNumber() {
        this.projectNumber_ = getDefaultInstance().getProjectNumber();
    }

    /* access modifiers changed from: private */
    public void setProjectNumberBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.projectNumber_ = value.toStringUtf8();
    }

    public boolean hasRequestingClientApp() {
        return this.requestingClientApp_ != null;
    }

    public ClientAppInfo getRequestingClientApp() {
        ClientAppInfo clientAppInfo = this.requestingClientApp_;
        return clientAppInfo == null ? ClientAppInfo.getDefaultInstance() : clientAppInfo;
    }

    /* access modifiers changed from: private */
    public void setRequestingClientApp(ClientAppInfo value) {
        value.getClass();
        this.requestingClientApp_ = value;
    }

    /* access modifiers changed from: private */
    public void mergeRequestingClientApp(ClientAppInfo value) {
        value.getClass();
        ClientAppInfo clientAppInfo = this.requestingClientApp_;
        if (clientAppInfo == null || clientAppInfo == ClientAppInfo.getDefaultInstance()) {
            this.requestingClientApp_ = value;
        } else {
            this.requestingClientApp_ = (ClientAppInfo) ((ClientAppInfo.Builder) ClientAppInfo.newBuilder(this.requestingClientApp_).mergeFrom(value)).buildPartial();
        }
    }

    /* access modifiers changed from: private */
    public void clearRequestingClientApp() {
        this.requestingClientApp_ = null;
    }

    public List<CampaignImpression> getAlreadySeenCampaignsList() {
        return this.alreadySeenCampaigns_;
    }

    public List<? extends CampaignImpressionOrBuilder> getAlreadySeenCampaignsOrBuilderList() {
        return this.alreadySeenCampaigns_;
    }

    public int getAlreadySeenCampaignsCount() {
        return this.alreadySeenCampaigns_.size();
    }

    public CampaignImpression getAlreadySeenCampaigns(int index) {
        return (CampaignImpression) this.alreadySeenCampaigns_.get(index);
    }

    public CampaignImpressionOrBuilder getAlreadySeenCampaignsOrBuilder(int index) {
        return (CampaignImpressionOrBuilder) this.alreadySeenCampaigns_.get(index);
    }

    private void ensureAlreadySeenCampaignsIsMutable() {
        if (!this.alreadySeenCampaigns_.isModifiable()) {
            this.alreadySeenCampaigns_ = GeneratedMessageLite.mutableCopy(this.alreadySeenCampaigns_);
        }
    }

    /* access modifiers changed from: private */
    public void setAlreadySeenCampaigns(int index, CampaignImpression value) {
        value.getClass();
        ensureAlreadySeenCampaignsIsMutable();
        this.alreadySeenCampaigns_.set(index, value);
    }

    /* access modifiers changed from: private */
    public void addAlreadySeenCampaigns(CampaignImpression value) {
        value.getClass();
        ensureAlreadySeenCampaignsIsMutable();
        this.alreadySeenCampaigns_.add(value);
    }

    /* access modifiers changed from: private */
    public void addAlreadySeenCampaigns(int index, CampaignImpression value) {
        value.getClass();
        ensureAlreadySeenCampaignsIsMutable();
        this.alreadySeenCampaigns_.add(index, value);
    }

    /* access modifiers changed from: private */
    public void addAllAlreadySeenCampaigns(Iterable<? extends CampaignImpression> values) {
        ensureAlreadySeenCampaignsIsMutable();
        AbstractMessageLite.addAll(values, this.alreadySeenCampaigns_);
    }

    /* access modifiers changed from: private */
    public void clearAlreadySeenCampaigns() {
        this.alreadySeenCampaigns_ = emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void removeAlreadySeenCampaigns(int index) {
        ensureAlreadySeenCampaignsIsMutable();
        this.alreadySeenCampaigns_.remove(index);
    }

    public boolean hasClientSignals() {
        return this.clientSignals_ != null;
    }

    public ClientSignalsProto.ClientSignals getClientSignals() {
        ClientSignalsProto.ClientSignals clientSignals = this.clientSignals_;
        return clientSignals == null ? ClientSignalsProto.ClientSignals.getDefaultInstance() : clientSignals;
    }

    /* access modifiers changed from: private */
    public void setClientSignals(ClientSignalsProto.ClientSignals value) {
        value.getClass();
        this.clientSignals_ = value;
    }

    /* access modifiers changed from: private */
    public void mergeClientSignals(ClientSignalsProto.ClientSignals value) {
        value.getClass();
        ClientSignalsProto.ClientSignals clientSignals = this.clientSignals_;
        if (clientSignals == null || clientSignals == ClientSignalsProto.ClientSignals.getDefaultInstance()) {
            this.clientSignals_ = value;
        } else {
            this.clientSignals_ = (ClientSignalsProto.ClientSignals) ((ClientSignalsProto.ClientSignals.Builder) ClientSignalsProto.ClientSignals.newBuilder(this.clientSignals_).mergeFrom(value)).buildPartial();
        }
    }

    /* access modifiers changed from: private */
    public void clearClientSignals() {
        this.clientSignals_ = null;
    }

    public static FetchEligibleCampaignsRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (FetchEligibleCampaignsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static FetchEligibleCampaignsRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (FetchEligibleCampaignsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static FetchEligibleCampaignsRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (FetchEligibleCampaignsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static FetchEligibleCampaignsRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (FetchEligibleCampaignsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static FetchEligibleCampaignsRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (FetchEligibleCampaignsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static FetchEligibleCampaignsRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (FetchEligibleCampaignsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static FetchEligibleCampaignsRequest parseFrom(InputStream input) throws IOException {
        return (FetchEligibleCampaignsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static FetchEligibleCampaignsRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (FetchEligibleCampaignsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static FetchEligibleCampaignsRequest parseDelimitedFrom(InputStream input) throws IOException {
        return (FetchEligibleCampaignsRequest) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static FetchEligibleCampaignsRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (FetchEligibleCampaignsRequest) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static FetchEligibleCampaignsRequest parseFrom(CodedInputStream input) throws IOException {
        return (FetchEligibleCampaignsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static FetchEligibleCampaignsRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (FetchEligibleCampaignsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(FetchEligibleCampaignsRequest prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.FetchEligibleCampaignsRequest$Builder */
    public static final class Builder extends GeneratedMessageLite.Builder<FetchEligibleCampaignsRequest, Builder> implements FetchEligibleCampaignsRequestOrBuilder {
        /* synthetic */ Builder(C12591 x0) {
            this();
        }

        private Builder() {
            super(FetchEligibleCampaignsRequest.DEFAULT_INSTANCE);
        }

        public String getProjectNumber() {
            return ((FetchEligibleCampaignsRequest) this.instance).getProjectNumber();
        }

        public ByteString getProjectNumberBytes() {
            return ((FetchEligibleCampaignsRequest) this.instance).getProjectNumberBytes();
        }

        public Builder setProjectNumber(String value) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).setProjectNumber(value);
            return this;
        }

        public Builder clearProjectNumber() {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).clearProjectNumber();
            return this;
        }

        public Builder setProjectNumberBytes(ByteString value) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).setProjectNumberBytes(value);
            return this;
        }

        public boolean hasRequestingClientApp() {
            return ((FetchEligibleCampaignsRequest) this.instance).hasRequestingClientApp();
        }

        public ClientAppInfo getRequestingClientApp() {
            return ((FetchEligibleCampaignsRequest) this.instance).getRequestingClientApp();
        }

        public Builder setRequestingClientApp(ClientAppInfo value) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).setRequestingClientApp(value);
            return this;
        }

        public Builder setRequestingClientApp(ClientAppInfo.Builder builderForValue) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).setRequestingClientApp((ClientAppInfo) builderForValue.build());
            return this;
        }

        public Builder mergeRequestingClientApp(ClientAppInfo value) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).mergeRequestingClientApp(value);
            return this;
        }

        public Builder clearRequestingClientApp() {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).clearRequestingClientApp();
            return this;
        }

        public List<CampaignImpression> getAlreadySeenCampaignsList() {
            return Collections.unmodifiableList(((FetchEligibleCampaignsRequest) this.instance).getAlreadySeenCampaignsList());
        }

        public int getAlreadySeenCampaignsCount() {
            return ((FetchEligibleCampaignsRequest) this.instance).getAlreadySeenCampaignsCount();
        }

        public CampaignImpression getAlreadySeenCampaigns(int index) {
            return ((FetchEligibleCampaignsRequest) this.instance).getAlreadySeenCampaigns(index);
        }

        public Builder setAlreadySeenCampaigns(int index, CampaignImpression value) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).setAlreadySeenCampaigns(index, value);
            return this;
        }

        public Builder setAlreadySeenCampaigns(int index, CampaignImpression.Builder builderForValue) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).setAlreadySeenCampaigns(index, (CampaignImpression) builderForValue.build());
            return this;
        }

        public Builder addAlreadySeenCampaigns(CampaignImpression value) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).addAlreadySeenCampaigns(value);
            return this;
        }

        public Builder addAlreadySeenCampaigns(int index, CampaignImpression value) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).addAlreadySeenCampaigns(index, value);
            return this;
        }

        public Builder addAlreadySeenCampaigns(CampaignImpression.Builder builderForValue) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).addAlreadySeenCampaigns((CampaignImpression) builderForValue.build());
            return this;
        }

        public Builder addAlreadySeenCampaigns(int index, CampaignImpression.Builder builderForValue) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).addAlreadySeenCampaigns(index, (CampaignImpression) builderForValue.build());
            return this;
        }

        public Builder addAllAlreadySeenCampaigns(Iterable<? extends CampaignImpression> values) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).addAllAlreadySeenCampaigns(values);
            return this;
        }

        public Builder clearAlreadySeenCampaigns() {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).clearAlreadySeenCampaigns();
            return this;
        }

        public Builder removeAlreadySeenCampaigns(int index) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).removeAlreadySeenCampaigns(index);
            return this;
        }

        public boolean hasClientSignals() {
            return ((FetchEligibleCampaignsRequest) this.instance).hasClientSignals();
        }

        public ClientSignalsProto.ClientSignals getClientSignals() {
            return ((FetchEligibleCampaignsRequest) this.instance).getClientSignals();
        }

        public Builder setClientSignals(ClientSignalsProto.ClientSignals value) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).setClientSignals(value);
            return this;
        }

        public Builder setClientSignals(ClientSignalsProto.ClientSignals.Builder builderForValue) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).setClientSignals((ClientSignalsProto.ClientSignals) builderForValue.build());
            return this;
        }

        public Builder mergeClientSignals(ClientSignalsProto.ClientSignals value) {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).mergeClientSignals(value);
            return this;
        }

        public Builder clearClientSignals() {
            copyOnWrite();
            ((FetchEligibleCampaignsRequest) this.instance).clearClientSignals();
            return this;
        }
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.FetchEligibleCampaignsRequest$1 */
    static /* synthetic */ class C12591 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        static final /* synthetic */ int[] f110xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f110xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f110xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f110xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f110xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f110xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f110xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f110xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (C12591.f110xa1df5c61[method.ordinal()]) {
            case 1:
                return new FetchEligibleCampaignsRequest();
            case 2:
                return new Builder((C12591) null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0001\u0000\u0001Ȉ\u0002\t\u0003\u001b\u0004\t", new Object[]{"projectNumber_", "requestingClientApp_", "alreadySeenCampaigns_", CampaignImpression.class, "clientSignals_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<FetchEligibleCampaignsRequest> parser = PARSER;
                if (parser == null) {
                    synchronized (FetchEligibleCampaignsRequest.class) {
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
        FetchEligibleCampaignsRequest defaultInstance = new FetchEligibleCampaignsRequest();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(FetchEligibleCampaignsRequest.class, defaultInstance);
    }

    public static FetchEligibleCampaignsRequest getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<FetchEligibleCampaignsRequest> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
