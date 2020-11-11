package com.google.internal.firebase.inappmessaging.p015v1.sdkserving;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.CampaignImpression;
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

/* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList */
public final class CampaignImpressionList extends GeneratedMessageLite<CampaignImpressionList, Builder> implements CampaignImpressionListOrBuilder {
    public static final int ALREADY_SEEN_CAMPAIGNS_FIELD_NUMBER = 1;
    /* access modifiers changed from: private */
    public static final CampaignImpressionList DEFAULT_INSTANCE;
    private static volatile Parser<CampaignImpressionList> PARSER;
    private Internal.ProtobufList<CampaignImpression> alreadySeenCampaigns_ = emptyProtobufList();

    private CampaignImpressionList() {
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

    public static CampaignImpressionList parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (CampaignImpressionList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CampaignImpressionList parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CampaignImpressionList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CampaignImpressionList parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (CampaignImpressionList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CampaignImpressionList parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CampaignImpressionList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CampaignImpressionList parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (CampaignImpressionList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CampaignImpressionList parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CampaignImpressionList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CampaignImpressionList parseFrom(InputStream input) throws IOException {
        return (CampaignImpressionList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static CampaignImpressionList parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CampaignImpressionList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static CampaignImpressionList parseDelimitedFrom(InputStream input) throws IOException {
        return (CampaignImpressionList) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static CampaignImpressionList parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CampaignImpressionList) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static CampaignImpressionList parseFrom(CodedInputStream input) throws IOException {
        return (CampaignImpressionList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static CampaignImpressionList parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CampaignImpressionList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(CampaignImpressionList prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList$Builder */
    public static final class Builder extends GeneratedMessageLite.Builder<CampaignImpressionList, Builder> implements CampaignImpressionListOrBuilder {
        /* synthetic */ Builder(C12571 x0) {
            this();
        }

        private Builder() {
            super(CampaignImpressionList.DEFAULT_INSTANCE);
        }

        public List<CampaignImpression> getAlreadySeenCampaignsList() {
            return Collections.unmodifiableList(((CampaignImpressionList) this.instance).getAlreadySeenCampaignsList());
        }

        public int getAlreadySeenCampaignsCount() {
            return ((CampaignImpressionList) this.instance).getAlreadySeenCampaignsCount();
        }

        public CampaignImpression getAlreadySeenCampaigns(int index) {
            return ((CampaignImpressionList) this.instance).getAlreadySeenCampaigns(index);
        }

        public Builder setAlreadySeenCampaigns(int index, CampaignImpression value) {
            copyOnWrite();
            ((CampaignImpressionList) this.instance).setAlreadySeenCampaigns(index, value);
            return this;
        }

        public Builder setAlreadySeenCampaigns(int index, CampaignImpression.Builder builderForValue) {
            copyOnWrite();
            ((CampaignImpressionList) this.instance).setAlreadySeenCampaigns(index, (CampaignImpression) builderForValue.build());
            return this;
        }

        public Builder addAlreadySeenCampaigns(CampaignImpression value) {
            copyOnWrite();
            ((CampaignImpressionList) this.instance).addAlreadySeenCampaigns(value);
            return this;
        }

        public Builder addAlreadySeenCampaigns(int index, CampaignImpression value) {
            copyOnWrite();
            ((CampaignImpressionList) this.instance).addAlreadySeenCampaigns(index, value);
            return this;
        }

        public Builder addAlreadySeenCampaigns(CampaignImpression.Builder builderForValue) {
            copyOnWrite();
            ((CampaignImpressionList) this.instance).addAlreadySeenCampaigns((CampaignImpression) builderForValue.build());
            return this;
        }

        public Builder addAlreadySeenCampaigns(int index, CampaignImpression.Builder builderForValue) {
            copyOnWrite();
            ((CampaignImpressionList) this.instance).addAlreadySeenCampaigns(index, (CampaignImpression) builderForValue.build());
            return this;
        }

        public Builder addAllAlreadySeenCampaigns(Iterable<? extends CampaignImpression> values) {
            copyOnWrite();
            ((CampaignImpressionList) this.instance).addAllAlreadySeenCampaigns(values);
            return this;
        }

        public Builder clearAlreadySeenCampaigns() {
            copyOnWrite();
            ((CampaignImpressionList) this.instance).clearAlreadySeenCampaigns();
            return this;
        }

        public Builder removeAlreadySeenCampaigns(int index) {
            copyOnWrite();
            ((CampaignImpressionList) this.instance).removeAlreadySeenCampaigns(index);
            return this;
        }
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList$1 */
    static /* synthetic */ class C12571 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        static final /* synthetic */ int[] f108xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f108xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f108xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f108xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f108xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f108xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f108xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f108xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (C12571.f108xa1df5c61[method.ordinal()]) {
            case 1:
                return new CampaignImpressionList();
            case 2:
                return new Builder((C12571) null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"alreadySeenCampaigns_", CampaignImpression.class});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<CampaignImpressionList> parser = PARSER;
                if (parser == null) {
                    synchronized (CampaignImpressionList.class) {
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
        CampaignImpressionList defaultInstance = new CampaignImpressionList();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(CampaignImpressionList.class, defaultInstance);
    }

    public static CampaignImpressionList getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<CampaignImpressionList> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
