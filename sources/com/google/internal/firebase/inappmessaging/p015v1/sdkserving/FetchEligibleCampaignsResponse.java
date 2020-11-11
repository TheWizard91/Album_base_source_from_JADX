package com.google.internal.firebase.inappmessaging.p015v1.sdkserving;

import com.google.internal.firebase.inappmessaging.p015v1.CampaignProto;
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

/* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.FetchEligibleCampaignsResponse */
public final class FetchEligibleCampaignsResponse extends GeneratedMessageLite<FetchEligibleCampaignsResponse, Builder> implements FetchEligibleCampaignsResponseOrBuilder {
    /* access modifiers changed from: private */
    public static final FetchEligibleCampaignsResponse DEFAULT_INSTANCE;
    public static final int EXPIRATION_EPOCH_TIMESTAMP_MILLIS_FIELD_NUMBER = 2;
    public static final int MESSAGES_FIELD_NUMBER = 1;
    private static volatile Parser<FetchEligibleCampaignsResponse> PARSER;
    private long expirationEpochTimestampMillis_;
    private Internal.ProtobufList<CampaignProto.ThickContent> messages_ = emptyProtobufList();

    private FetchEligibleCampaignsResponse() {
    }

    public List<CampaignProto.ThickContent> getMessagesList() {
        return this.messages_;
    }

    public List<? extends CampaignProto.ThickContentOrBuilder> getMessagesOrBuilderList() {
        return this.messages_;
    }

    public int getMessagesCount() {
        return this.messages_.size();
    }

    public CampaignProto.ThickContent getMessages(int index) {
        return (CampaignProto.ThickContent) this.messages_.get(index);
    }

    public CampaignProto.ThickContentOrBuilder getMessagesOrBuilder(int index) {
        return (CampaignProto.ThickContentOrBuilder) this.messages_.get(index);
    }

    private void ensureMessagesIsMutable() {
        if (!this.messages_.isModifiable()) {
            this.messages_ = GeneratedMessageLite.mutableCopy(this.messages_);
        }
    }

    /* access modifiers changed from: private */
    public void setMessages(int index, CampaignProto.ThickContent value) {
        value.getClass();
        ensureMessagesIsMutable();
        this.messages_.set(index, value);
    }

    /* access modifiers changed from: private */
    public void addMessages(CampaignProto.ThickContent value) {
        value.getClass();
        ensureMessagesIsMutable();
        this.messages_.add(value);
    }

    /* access modifiers changed from: private */
    public void addMessages(int index, CampaignProto.ThickContent value) {
        value.getClass();
        ensureMessagesIsMutable();
        this.messages_.add(index, value);
    }

    /* access modifiers changed from: private */
    public void addAllMessages(Iterable<? extends CampaignProto.ThickContent> values) {
        ensureMessagesIsMutable();
        AbstractMessageLite.addAll(values, this.messages_);
    }

    /* access modifiers changed from: private */
    public void clearMessages() {
        this.messages_ = emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void removeMessages(int index) {
        ensureMessagesIsMutable();
        this.messages_.remove(index);
    }

    public long getExpirationEpochTimestampMillis() {
        return this.expirationEpochTimestampMillis_;
    }

    /* access modifiers changed from: private */
    public void setExpirationEpochTimestampMillis(long value) {
        this.expirationEpochTimestampMillis_ = value;
    }

    /* access modifiers changed from: private */
    public void clearExpirationEpochTimestampMillis() {
        this.expirationEpochTimestampMillis_ = 0;
    }

    public static FetchEligibleCampaignsResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (FetchEligibleCampaignsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static FetchEligibleCampaignsResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (FetchEligibleCampaignsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static FetchEligibleCampaignsResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (FetchEligibleCampaignsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static FetchEligibleCampaignsResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (FetchEligibleCampaignsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static FetchEligibleCampaignsResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (FetchEligibleCampaignsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static FetchEligibleCampaignsResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (FetchEligibleCampaignsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static FetchEligibleCampaignsResponse parseFrom(InputStream input) throws IOException {
        return (FetchEligibleCampaignsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static FetchEligibleCampaignsResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (FetchEligibleCampaignsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static FetchEligibleCampaignsResponse parseDelimitedFrom(InputStream input) throws IOException {
        return (FetchEligibleCampaignsResponse) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static FetchEligibleCampaignsResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (FetchEligibleCampaignsResponse) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static FetchEligibleCampaignsResponse parseFrom(CodedInputStream input) throws IOException {
        return (FetchEligibleCampaignsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static FetchEligibleCampaignsResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (FetchEligibleCampaignsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(FetchEligibleCampaignsResponse prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.FetchEligibleCampaignsResponse$Builder */
    public static final class Builder extends GeneratedMessageLite.Builder<FetchEligibleCampaignsResponse, Builder> implements FetchEligibleCampaignsResponseOrBuilder {
        /* synthetic */ Builder(C12601 x0) {
            this();
        }

        private Builder() {
            super(FetchEligibleCampaignsResponse.DEFAULT_INSTANCE);
        }

        public List<CampaignProto.ThickContent> getMessagesList() {
            return Collections.unmodifiableList(((FetchEligibleCampaignsResponse) this.instance).getMessagesList());
        }

        public int getMessagesCount() {
            return ((FetchEligibleCampaignsResponse) this.instance).getMessagesCount();
        }

        public CampaignProto.ThickContent getMessages(int index) {
            return ((FetchEligibleCampaignsResponse) this.instance).getMessages(index);
        }

        public Builder setMessages(int index, CampaignProto.ThickContent value) {
            copyOnWrite();
            ((FetchEligibleCampaignsResponse) this.instance).setMessages(index, value);
            return this;
        }

        public Builder setMessages(int index, CampaignProto.ThickContent.Builder builderForValue) {
            copyOnWrite();
            ((FetchEligibleCampaignsResponse) this.instance).setMessages(index, (CampaignProto.ThickContent) builderForValue.build());
            return this;
        }

        public Builder addMessages(CampaignProto.ThickContent value) {
            copyOnWrite();
            ((FetchEligibleCampaignsResponse) this.instance).addMessages(value);
            return this;
        }

        public Builder addMessages(int index, CampaignProto.ThickContent value) {
            copyOnWrite();
            ((FetchEligibleCampaignsResponse) this.instance).addMessages(index, value);
            return this;
        }

        public Builder addMessages(CampaignProto.ThickContent.Builder builderForValue) {
            copyOnWrite();
            ((FetchEligibleCampaignsResponse) this.instance).addMessages((CampaignProto.ThickContent) builderForValue.build());
            return this;
        }

        public Builder addMessages(int index, CampaignProto.ThickContent.Builder builderForValue) {
            copyOnWrite();
            ((FetchEligibleCampaignsResponse) this.instance).addMessages(index, (CampaignProto.ThickContent) builderForValue.build());
            return this;
        }

        public Builder addAllMessages(Iterable<? extends CampaignProto.ThickContent> values) {
            copyOnWrite();
            ((FetchEligibleCampaignsResponse) this.instance).addAllMessages(values);
            return this;
        }

        public Builder clearMessages() {
            copyOnWrite();
            ((FetchEligibleCampaignsResponse) this.instance).clearMessages();
            return this;
        }

        public Builder removeMessages(int index) {
            copyOnWrite();
            ((FetchEligibleCampaignsResponse) this.instance).removeMessages(index);
            return this;
        }

        public long getExpirationEpochTimestampMillis() {
            return ((FetchEligibleCampaignsResponse) this.instance).getExpirationEpochTimestampMillis();
        }

        public Builder setExpirationEpochTimestampMillis(long value) {
            copyOnWrite();
            ((FetchEligibleCampaignsResponse) this.instance).setExpirationEpochTimestampMillis(value);
            return this;
        }

        public Builder clearExpirationEpochTimestampMillis() {
            copyOnWrite();
            ((FetchEligibleCampaignsResponse) this.instance).clearExpirationEpochTimestampMillis();
            return this;
        }
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.FetchEligibleCampaignsResponse$1 */
    static /* synthetic */ class C12601 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        static final /* synthetic */ int[] f111xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f111xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f111xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f111xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f111xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f111xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f111xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f111xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (C12601.f111xa1df5c61[method.ordinal()]) {
            case 1:
                return new FetchEligibleCampaignsResponse();
            case 2:
                return new Builder((C12601) null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002\u0002", new Object[]{"messages_", CampaignProto.ThickContent.class, "expirationEpochTimestampMillis_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<FetchEligibleCampaignsResponse> parser = PARSER;
                if (parser == null) {
                    synchronized (FetchEligibleCampaignsResponse.class) {
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
        FetchEligibleCampaignsResponse defaultInstance = new FetchEligibleCampaignsResponse();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(FetchEligibleCampaignsResponse.class, defaultInstance);
    }

    public static FetchEligibleCampaignsResponse getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<FetchEligibleCampaignsResponse> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
