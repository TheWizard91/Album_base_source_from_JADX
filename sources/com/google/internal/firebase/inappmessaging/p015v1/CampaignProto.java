package com.google.internal.firebase.inappmessaging.p015v1;

import com.google.firebase.inappmessaging.CommonTypesProto;
import com.google.firebase.inappmessaging.MessagesProto;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.MapFieldLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.WireFormat;
import developers.mobile.abt.FirebaseAbt;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto */
public final class CampaignProto {

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto$ExperimentalCampaignPayloadOrBuilder */
    public interface ExperimentalCampaignPayloadOrBuilder extends MessageLiteOrBuilder {
        long getCampaignEndTimeMillis();

        String getCampaignId();

        ByteString getCampaignIdBytes();

        String getCampaignName();

        ByteString getCampaignNameBytes();

        long getCampaignStartTimeMillis();

        FirebaseAbt.ExperimentPayload getExperimentPayload();

        boolean hasExperimentPayload();
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto$ExperimentalCampaignRolloutOrBuilder */
    public interface ExperimentalCampaignRolloutOrBuilder extends MessageLiteOrBuilder {
        CommonTypesProto.CampaignTime getEndTime();

        String getExperimentId();

        ByteString getExperimentIdBytes();

        CommonTypesProto.Priority getPriority();

        int getSelectedVariantIndex();

        CommonTypesProto.CampaignTime getStartTime();

        boolean hasEndTime();

        boolean hasPriority();

        boolean hasStartTime();
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto$ThickContentOrBuilder */
    public interface ThickContentOrBuilder extends MessageLiteOrBuilder {
        boolean containsDataBundle(String str);

        MessagesProto.Content getContent();

        @Deprecated
        Map<String, String> getDataBundle();

        int getDataBundleCount();

        Map<String, String> getDataBundleMap();

        String getDataBundleOrDefault(String str, String str2);

        String getDataBundleOrThrow(String str);

        ExperimentalCampaignPayload getExperimentalPayload();

        boolean getIsTestCampaign();

        ThickContent.PayloadCase getPayloadCase();

        CommonTypesProto.Priority getPriority();

        CommonTypesProto.TriggeringCondition getTriggeringConditions(int i);

        int getTriggeringConditionsCount();

        List<CommonTypesProto.TriggeringCondition> getTriggeringConditionsList();

        VanillaCampaignPayload getVanillaPayload();

        boolean hasContent();

        boolean hasExperimentalPayload();

        boolean hasPriority();

        boolean hasVanillaPayload();
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto$VanillaCampaignPayloadOrBuilder */
    public interface VanillaCampaignPayloadOrBuilder extends MessageLiteOrBuilder {
        long getCampaignEndTimeMillis();

        String getCampaignId();

        ByteString getCampaignIdBytes();

        String getCampaignName();

        ByteString getCampaignNameBytes();

        long getCampaignStartTimeMillis();

        String getExperimentalCampaignId();

        ByteString getExperimentalCampaignIdBytes();
    }

    private CampaignProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto$ExperimentalCampaignRollout */
    public static final class ExperimentalCampaignRollout extends GeneratedMessageLite<ExperimentalCampaignRollout, Builder> implements ExperimentalCampaignRolloutOrBuilder {
        /* access modifiers changed from: private */
        public static final ExperimentalCampaignRollout DEFAULT_INSTANCE;
        public static final int END_TIME_FIELD_NUMBER = 5;
        public static final int EXPERIMENT_ID_FIELD_NUMBER = 1;
        private static volatile Parser<ExperimentalCampaignRollout> PARSER = null;
        public static final int PRIORITY_FIELD_NUMBER = 3;
        public static final int SELECTED_VARIANT_INDEX_FIELD_NUMBER = 2;
        public static final int START_TIME_FIELD_NUMBER = 4;
        private CommonTypesProto.CampaignTime endTime_;
        private String experimentId_ = "";
        private CommonTypesProto.Priority priority_;
        private int selectedVariantIndex_;
        private CommonTypesProto.CampaignTime startTime_;

        private ExperimentalCampaignRollout() {
        }

        public String getExperimentId() {
            return this.experimentId_;
        }

        public ByteString getExperimentIdBytes() {
            return ByteString.copyFromUtf8(this.experimentId_);
        }

        /* access modifiers changed from: private */
        public void setExperimentId(String value) {
            value.getClass();
            this.experimentId_ = value;
        }

        /* access modifiers changed from: private */
        public void clearExperimentId() {
            this.experimentId_ = getDefaultInstance().getExperimentId();
        }

        /* access modifiers changed from: private */
        public void setExperimentIdBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.experimentId_ = value.toStringUtf8();
        }

        public int getSelectedVariantIndex() {
            return this.selectedVariantIndex_;
        }

        /* access modifiers changed from: private */
        public void setSelectedVariantIndex(int value) {
            this.selectedVariantIndex_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSelectedVariantIndex() {
            this.selectedVariantIndex_ = 0;
        }

        public boolean hasPriority() {
            return this.priority_ != null;
        }

        public CommonTypesProto.Priority getPriority() {
            CommonTypesProto.Priority priority = this.priority_;
            return priority == null ? CommonTypesProto.Priority.getDefaultInstance() : priority;
        }

        /* access modifiers changed from: private */
        public void setPriority(CommonTypesProto.Priority value) {
            value.getClass();
            this.priority_ = value;
        }

        /* access modifiers changed from: private */
        public void mergePriority(CommonTypesProto.Priority value) {
            value.getClass();
            CommonTypesProto.Priority priority = this.priority_;
            if (priority == null || priority == CommonTypesProto.Priority.getDefaultInstance()) {
                this.priority_ = value;
            } else {
                this.priority_ = (CommonTypesProto.Priority) ((CommonTypesProto.Priority.Builder) CommonTypesProto.Priority.newBuilder(this.priority_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearPriority() {
            this.priority_ = null;
        }

        public boolean hasStartTime() {
            return this.startTime_ != null;
        }

        public CommonTypesProto.CampaignTime getStartTime() {
            CommonTypesProto.CampaignTime campaignTime = this.startTime_;
            return campaignTime == null ? CommonTypesProto.CampaignTime.getDefaultInstance() : campaignTime;
        }

        /* access modifiers changed from: private */
        public void setStartTime(CommonTypesProto.CampaignTime value) {
            value.getClass();
            this.startTime_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeStartTime(CommonTypesProto.CampaignTime value) {
            value.getClass();
            CommonTypesProto.CampaignTime campaignTime = this.startTime_;
            if (campaignTime == null || campaignTime == CommonTypesProto.CampaignTime.getDefaultInstance()) {
                this.startTime_ = value;
            } else {
                this.startTime_ = (CommonTypesProto.CampaignTime) ((CommonTypesProto.CampaignTime.Builder) CommonTypesProto.CampaignTime.newBuilder(this.startTime_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearStartTime() {
            this.startTime_ = null;
        }

        public boolean hasEndTime() {
            return this.endTime_ != null;
        }

        public CommonTypesProto.CampaignTime getEndTime() {
            CommonTypesProto.CampaignTime campaignTime = this.endTime_;
            return campaignTime == null ? CommonTypesProto.CampaignTime.getDefaultInstance() : campaignTime;
        }

        /* access modifiers changed from: private */
        public void setEndTime(CommonTypesProto.CampaignTime value) {
            value.getClass();
            this.endTime_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeEndTime(CommonTypesProto.CampaignTime value) {
            value.getClass();
            CommonTypesProto.CampaignTime campaignTime = this.endTime_;
            if (campaignTime == null || campaignTime == CommonTypesProto.CampaignTime.getDefaultInstance()) {
                this.endTime_ = value;
            } else {
                this.endTime_ = (CommonTypesProto.CampaignTime) ((CommonTypesProto.CampaignTime.Builder) CommonTypesProto.CampaignTime.newBuilder(this.endTime_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearEndTime() {
            this.endTime_ = null;
        }

        public static ExperimentalCampaignRollout parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ExperimentalCampaignRollout) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExperimentalCampaignRollout parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExperimentalCampaignRollout) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExperimentalCampaignRollout parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ExperimentalCampaignRollout) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExperimentalCampaignRollout parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExperimentalCampaignRollout) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExperimentalCampaignRollout parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ExperimentalCampaignRollout) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExperimentalCampaignRollout parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExperimentalCampaignRollout) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExperimentalCampaignRollout parseFrom(InputStream input) throws IOException {
            return (ExperimentalCampaignRollout) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExperimentalCampaignRollout parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExperimentalCampaignRollout) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExperimentalCampaignRollout parseDelimitedFrom(InputStream input) throws IOException {
            return (ExperimentalCampaignRollout) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ExperimentalCampaignRollout parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExperimentalCampaignRollout) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExperimentalCampaignRollout parseFrom(CodedInputStream input) throws IOException {
            return (ExperimentalCampaignRollout) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExperimentalCampaignRollout parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExperimentalCampaignRollout) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ExperimentalCampaignRollout prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        /* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto$ExperimentalCampaignRollout$Builder */
        public static final class Builder extends GeneratedMessageLite.Builder<ExperimentalCampaignRollout, Builder> implements ExperimentalCampaignRolloutOrBuilder {
            /* synthetic */ Builder(C12551 x0) {
                this();
            }

            private Builder() {
                super(ExperimentalCampaignRollout.DEFAULT_INSTANCE);
            }

            public String getExperimentId() {
                return ((ExperimentalCampaignRollout) this.instance).getExperimentId();
            }

            public ByteString getExperimentIdBytes() {
                return ((ExperimentalCampaignRollout) this.instance).getExperimentIdBytes();
            }

            public Builder setExperimentId(String value) {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).setExperimentId(value);
                return this;
            }

            public Builder clearExperimentId() {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).clearExperimentId();
                return this;
            }

            public Builder setExperimentIdBytes(ByteString value) {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).setExperimentIdBytes(value);
                return this;
            }

            public int getSelectedVariantIndex() {
                return ((ExperimentalCampaignRollout) this.instance).getSelectedVariantIndex();
            }

            public Builder setSelectedVariantIndex(int value) {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).setSelectedVariantIndex(value);
                return this;
            }

            public Builder clearSelectedVariantIndex() {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).clearSelectedVariantIndex();
                return this;
            }

            public boolean hasPriority() {
                return ((ExperimentalCampaignRollout) this.instance).hasPriority();
            }

            public CommonTypesProto.Priority getPriority() {
                return ((ExperimentalCampaignRollout) this.instance).getPriority();
            }

            public Builder setPriority(CommonTypesProto.Priority value) {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).setPriority(value);
                return this;
            }

            public Builder setPriority(CommonTypesProto.Priority.Builder builderForValue) {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).setPriority((CommonTypesProto.Priority) builderForValue.build());
                return this;
            }

            public Builder mergePriority(CommonTypesProto.Priority value) {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).mergePriority(value);
                return this;
            }

            public Builder clearPriority() {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).clearPriority();
                return this;
            }

            public boolean hasStartTime() {
                return ((ExperimentalCampaignRollout) this.instance).hasStartTime();
            }

            public CommonTypesProto.CampaignTime getStartTime() {
                return ((ExperimentalCampaignRollout) this.instance).getStartTime();
            }

            public Builder setStartTime(CommonTypesProto.CampaignTime value) {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).setStartTime(value);
                return this;
            }

            public Builder setStartTime(CommonTypesProto.CampaignTime.Builder builderForValue) {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).setStartTime((CommonTypesProto.CampaignTime) builderForValue.build());
                return this;
            }

            public Builder mergeStartTime(CommonTypesProto.CampaignTime value) {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).mergeStartTime(value);
                return this;
            }

            public Builder clearStartTime() {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).clearStartTime();
                return this;
            }

            public boolean hasEndTime() {
                return ((ExperimentalCampaignRollout) this.instance).hasEndTime();
            }

            public CommonTypesProto.CampaignTime getEndTime() {
                return ((ExperimentalCampaignRollout) this.instance).getEndTime();
            }

            public Builder setEndTime(CommonTypesProto.CampaignTime value) {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).setEndTime(value);
                return this;
            }

            public Builder setEndTime(CommonTypesProto.CampaignTime.Builder builderForValue) {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).setEndTime((CommonTypesProto.CampaignTime) builderForValue.build());
                return this;
            }

            public Builder mergeEndTime(CommonTypesProto.CampaignTime value) {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).mergeEndTime(value);
                return this;
            }

            public Builder clearEndTime() {
                copyOnWrite();
                ((ExperimentalCampaignRollout) this.instance).clearEndTime();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C12551.f106xa1df5c61[method.ordinal()]) {
                case 1:
                    return new ExperimentalCampaignRollout();
                case 2:
                    return new Builder((C12551) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0005\u0000\u0000\u0001\u0005\u0005\u0000\u0000\u0000\u0001Ȉ\u0002\u0004\u0003\t\u0004\t\u0005\t", new Object[]{"experimentId_", "selectedVariantIndex_", "priority_", "startTime_", "endTime_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ExperimentalCampaignRollout> parser = PARSER;
                    if (parser == null) {
                        synchronized (ExperimentalCampaignRollout.class) {
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
            ExperimentalCampaignRollout defaultInstance = new ExperimentalCampaignRollout();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(ExperimentalCampaignRollout.class, defaultInstance);
        }

        public static ExperimentalCampaignRollout getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ExperimentalCampaignRollout> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto$1 */
    static /* synthetic */ class C12551 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        static final /* synthetic */ int[] f106xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f106xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f106xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f106xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f106xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f106xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f106xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f106xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto$ThickContent */
    public static final class ThickContent extends GeneratedMessageLite<ThickContent, Builder> implements ThickContentOrBuilder {
        public static final int CONTENT_FIELD_NUMBER = 3;
        public static final int DATA_BUNDLE_FIELD_NUMBER = 8;
        /* access modifiers changed from: private */
        public static final ThickContent DEFAULT_INSTANCE;
        public static final int EXPERIMENTAL_PAYLOAD_FIELD_NUMBER = 2;
        public static final int IS_TEST_CAMPAIGN_FIELD_NUMBER = 7;
        private static volatile Parser<ThickContent> PARSER = null;
        public static final int PRIORITY_FIELD_NUMBER = 4;
        public static final int TRIGGERING_CONDITIONS_FIELD_NUMBER = 5;
        public static final int VANILLA_PAYLOAD_FIELD_NUMBER = 1;
        private MessagesProto.Content content_;
        private MapFieldLite<String, String> dataBundle_ = MapFieldLite.emptyMapField();
        private boolean isTestCampaign_;
        private int payloadCase_ = 0;
        private Object payload_;
        private CommonTypesProto.Priority priority_;
        private Internal.ProtobufList<CommonTypesProto.TriggeringCondition> triggeringConditions_ = emptyProtobufList();

        private ThickContent() {
        }

        /* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto$ThickContent$PayloadCase */
        public enum PayloadCase {
            VANILLA_PAYLOAD(1),
            EXPERIMENTAL_PAYLOAD(2),
            PAYLOAD_NOT_SET(0);
            
            private final int value;

            private PayloadCase(int value2) {
                this.value = value2;
            }

            @Deprecated
            public static PayloadCase valueOf(int value2) {
                return forNumber(value2);
            }

            public static PayloadCase forNumber(int value2) {
                if (value2 == 0) {
                    return PAYLOAD_NOT_SET;
                }
                if (value2 == 1) {
                    return VANILLA_PAYLOAD;
                }
                if (value2 != 2) {
                    return null;
                }
                return EXPERIMENTAL_PAYLOAD;
            }

            public int getNumber() {
                return this.value;
            }
        }

        public PayloadCase getPayloadCase() {
            return PayloadCase.forNumber(this.payloadCase_);
        }

        /* access modifiers changed from: private */
        public void clearPayload() {
            this.payloadCase_ = 0;
            this.payload_ = null;
        }

        public boolean hasVanillaPayload() {
            return this.payloadCase_ == 1;
        }

        public VanillaCampaignPayload getVanillaPayload() {
            if (this.payloadCase_ == 1) {
                return (VanillaCampaignPayload) this.payload_;
            }
            return VanillaCampaignPayload.getDefaultInstance();
        }

        /* access modifiers changed from: private */
        public void setVanillaPayload(VanillaCampaignPayload value) {
            value.getClass();
            this.payload_ = value;
            this.payloadCase_ = 1;
        }

        /* access modifiers changed from: private */
        public void mergeVanillaPayload(VanillaCampaignPayload value) {
            value.getClass();
            if (this.payloadCase_ != 1 || this.payload_ == VanillaCampaignPayload.getDefaultInstance()) {
                this.payload_ = value;
            } else {
                this.payload_ = ((VanillaCampaignPayload.Builder) VanillaCampaignPayload.newBuilder((VanillaCampaignPayload) this.payload_).mergeFrom(value)).buildPartial();
            }
            this.payloadCase_ = 1;
        }

        /* access modifiers changed from: private */
        public void clearVanillaPayload() {
            if (this.payloadCase_ == 1) {
                this.payloadCase_ = 0;
                this.payload_ = null;
            }
        }

        public boolean hasExperimentalPayload() {
            return this.payloadCase_ == 2;
        }

        public ExperimentalCampaignPayload getExperimentalPayload() {
            if (this.payloadCase_ == 2) {
                return (ExperimentalCampaignPayload) this.payload_;
            }
            return ExperimentalCampaignPayload.getDefaultInstance();
        }

        /* access modifiers changed from: private */
        public void setExperimentalPayload(ExperimentalCampaignPayload value) {
            value.getClass();
            this.payload_ = value;
            this.payloadCase_ = 2;
        }

        /* access modifiers changed from: private */
        public void mergeExperimentalPayload(ExperimentalCampaignPayload value) {
            value.getClass();
            if (this.payloadCase_ != 2 || this.payload_ == ExperimentalCampaignPayload.getDefaultInstance()) {
                this.payload_ = value;
            } else {
                this.payload_ = ((ExperimentalCampaignPayload.Builder) ExperimentalCampaignPayload.newBuilder((ExperimentalCampaignPayload) this.payload_).mergeFrom(value)).buildPartial();
            }
            this.payloadCase_ = 2;
        }

        /* access modifiers changed from: private */
        public void clearExperimentalPayload() {
            if (this.payloadCase_ == 2) {
                this.payloadCase_ = 0;
                this.payload_ = null;
            }
        }

        public boolean hasContent() {
            return this.content_ != null;
        }

        public MessagesProto.Content getContent() {
            MessagesProto.Content content = this.content_;
            return content == null ? MessagesProto.Content.getDefaultInstance() : content;
        }

        /* access modifiers changed from: private */
        public void setContent(MessagesProto.Content value) {
            value.getClass();
            this.content_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeContent(MessagesProto.Content value) {
            value.getClass();
            MessagesProto.Content content = this.content_;
            if (content == null || content == MessagesProto.Content.getDefaultInstance()) {
                this.content_ = value;
            } else {
                this.content_ = (MessagesProto.Content) ((MessagesProto.Content.Builder) MessagesProto.Content.newBuilder(this.content_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearContent() {
            this.content_ = null;
        }

        public boolean hasPriority() {
            return this.priority_ != null;
        }

        public CommonTypesProto.Priority getPriority() {
            CommonTypesProto.Priority priority = this.priority_;
            return priority == null ? CommonTypesProto.Priority.getDefaultInstance() : priority;
        }

        /* access modifiers changed from: private */
        public void setPriority(CommonTypesProto.Priority value) {
            value.getClass();
            this.priority_ = value;
        }

        /* access modifiers changed from: private */
        public void mergePriority(CommonTypesProto.Priority value) {
            value.getClass();
            CommonTypesProto.Priority priority = this.priority_;
            if (priority == null || priority == CommonTypesProto.Priority.getDefaultInstance()) {
                this.priority_ = value;
            } else {
                this.priority_ = (CommonTypesProto.Priority) ((CommonTypesProto.Priority.Builder) CommonTypesProto.Priority.newBuilder(this.priority_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearPriority() {
            this.priority_ = null;
        }

        public List<CommonTypesProto.TriggeringCondition> getTriggeringConditionsList() {
            return this.triggeringConditions_;
        }

        public List<? extends CommonTypesProto.TriggeringConditionOrBuilder> getTriggeringConditionsOrBuilderList() {
            return this.triggeringConditions_;
        }

        public int getTriggeringConditionsCount() {
            return this.triggeringConditions_.size();
        }

        public CommonTypesProto.TriggeringCondition getTriggeringConditions(int index) {
            return (CommonTypesProto.TriggeringCondition) this.triggeringConditions_.get(index);
        }

        public CommonTypesProto.TriggeringConditionOrBuilder getTriggeringConditionsOrBuilder(int index) {
            return (CommonTypesProto.TriggeringConditionOrBuilder) this.triggeringConditions_.get(index);
        }

        private void ensureTriggeringConditionsIsMutable() {
            if (!this.triggeringConditions_.isModifiable()) {
                this.triggeringConditions_ = GeneratedMessageLite.mutableCopy(this.triggeringConditions_);
            }
        }

        /* access modifiers changed from: private */
        public void setTriggeringConditions(int index, CommonTypesProto.TriggeringCondition value) {
            value.getClass();
            ensureTriggeringConditionsIsMutable();
            this.triggeringConditions_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addTriggeringConditions(CommonTypesProto.TriggeringCondition value) {
            value.getClass();
            ensureTriggeringConditionsIsMutable();
            this.triggeringConditions_.add(value);
        }

        /* access modifiers changed from: private */
        public void addTriggeringConditions(int index, CommonTypesProto.TriggeringCondition value) {
            value.getClass();
            ensureTriggeringConditionsIsMutable();
            this.triggeringConditions_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllTriggeringConditions(Iterable<? extends CommonTypesProto.TriggeringCondition> values) {
            ensureTriggeringConditionsIsMutable();
            AbstractMessageLite.addAll(values, this.triggeringConditions_);
        }

        /* access modifiers changed from: private */
        public void clearTriggeringConditions() {
            this.triggeringConditions_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeTriggeringConditions(int index) {
            ensureTriggeringConditionsIsMutable();
            this.triggeringConditions_.remove(index);
        }

        public boolean getIsTestCampaign() {
            return this.isTestCampaign_;
        }

        /* access modifiers changed from: private */
        public void setIsTestCampaign(boolean value) {
            this.isTestCampaign_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsTestCampaign() {
            this.isTestCampaign_ = false;
        }

        /* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto$ThickContent$DataBundleDefaultEntryHolder */
        private static final class DataBundleDefaultEntryHolder {
            static final MapEntryLite<String, String> defaultEntry = MapEntryLite.newDefaultInstance(WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");

            private DataBundleDefaultEntryHolder() {
            }
        }

        private MapFieldLite<String, String> internalGetDataBundle() {
            return this.dataBundle_;
        }

        private MapFieldLite<String, String> internalGetMutableDataBundle() {
            if (!this.dataBundle_.isMutable()) {
                this.dataBundle_ = this.dataBundle_.mutableCopy();
            }
            return this.dataBundle_;
        }

        public int getDataBundleCount() {
            return internalGetDataBundle().size();
        }

        public boolean containsDataBundle(String key) {
            key.getClass();
            return internalGetDataBundle().containsKey(key);
        }

        @Deprecated
        public Map<String, String> getDataBundle() {
            return getDataBundleMap();
        }

        public Map<String, String> getDataBundleMap() {
            return Collections.unmodifiableMap(internalGetDataBundle());
        }

        public String getDataBundleOrDefault(String key, String defaultValue) {
            key.getClass();
            Map<String, String> map = internalGetDataBundle();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        public String getDataBundleOrThrow(String key) {
            key.getClass();
            Map<String, String> map = internalGetDataBundle();
            if (map.containsKey(key)) {
                return map.get(key);
            }
            throw new IllegalArgumentException();
        }

        /* access modifiers changed from: private */
        public Map<String, String> getMutableDataBundleMap() {
            return internalGetMutableDataBundle();
        }

        public static ThickContent parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ThickContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ThickContent parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ThickContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ThickContent parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ThickContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ThickContent parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ThickContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ThickContent parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ThickContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ThickContent parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ThickContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ThickContent parseFrom(InputStream input) throws IOException {
            return (ThickContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ThickContent parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ThickContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ThickContent parseDelimitedFrom(InputStream input) throws IOException {
            return (ThickContent) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ThickContent parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ThickContent) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ThickContent parseFrom(CodedInputStream input) throws IOException {
            return (ThickContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ThickContent parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ThickContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ThickContent prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        /* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto$ThickContent$Builder */
        public static final class Builder extends GeneratedMessageLite.Builder<ThickContent, Builder> implements ThickContentOrBuilder {
            /* synthetic */ Builder(C12551 x0) {
                this();
            }

            private Builder() {
                super(ThickContent.DEFAULT_INSTANCE);
            }

            public PayloadCase getPayloadCase() {
                return ((ThickContent) this.instance).getPayloadCase();
            }

            public Builder clearPayload() {
                copyOnWrite();
                ((ThickContent) this.instance).clearPayload();
                return this;
            }

            public boolean hasVanillaPayload() {
                return ((ThickContent) this.instance).hasVanillaPayload();
            }

            public VanillaCampaignPayload getVanillaPayload() {
                return ((ThickContent) this.instance).getVanillaPayload();
            }

            public Builder setVanillaPayload(VanillaCampaignPayload value) {
                copyOnWrite();
                ((ThickContent) this.instance).setVanillaPayload(value);
                return this;
            }

            public Builder setVanillaPayload(VanillaCampaignPayload.Builder builderForValue) {
                copyOnWrite();
                ((ThickContent) this.instance).setVanillaPayload((VanillaCampaignPayload) builderForValue.build());
                return this;
            }

            public Builder mergeVanillaPayload(VanillaCampaignPayload value) {
                copyOnWrite();
                ((ThickContent) this.instance).mergeVanillaPayload(value);
                return this;
            }

            public Builder clearVanillaPayload() {
                copyOnWrite();
                ((ThickContent) this.instance).clearVanillaPayload();
                return this;
            }

            public boolean hasExperimentalPayload() {
                return ((ThickContent) this.instance).hasExperimentalPayload();
            }

            public ExperimentalCampaignPayload getExperimentalPayload() {
                return ((ThickContent) this.instance).getExperimentalPayload();
            }

            public Builder setExperimentalPayload(ExperimentalCampaignPayload value) {
                copyOnWrite();
                ((ThickContent) this.instance).setExperimentalPayload(value);
                return this;
            }

            public Builder setExperimentalPayload(ExperimentalCampaignPayload.Builder builderForValue) {
                copyOnWrite();
                ((ThickContent) this.instance).setExperimentalPayload((ExperimentalCampaignPayload) builderForValue.build());
                return this;
            }

            public Builder mergeExperimentalPayload(ExperimentalCampaignPayload value) {
                copyOnWrite();
                ((ThickContent) this.instance).mergeExperimentalPayload(value);
                return this;
            }

            public Builder clearExperimentalPayload() {
                copyOnWrite();
                ((ThickContent) this.instance).clearExperimentalPayload();
                return this;
            }

            public boolean hasContent() {
                return ((ThickContent) this.instance).hasContent();
            }

            public MessagesProto.Content getContent() {
                return ((ThickContent) this.instance).getContent();
            }

            public Builder setContent(MessagesProto.Content value) {
                copyOnWrite();
                ((ThickContent) this.instance).setContent(value);
                return this;
            }

            public Builder setContent(MessagesProto.Content.Builder builderForValue) {
                copyOnWrite();
                ((ThickContent) this.instance).setContent((MessagesProto.Content) builderForValue.build());
                return this;
            }

            public Builder mergeContent(MessagesProto.Content value) {
                copyOnWrite();
                ((ThickContent) this.instance).mergeContent(value);
                return this;
            }

            public Builder clearContent() {
                copyOnWrite();
                ((ThickContent) this.instance).clearContent();
                return this;
            }

            public boolean hasPriority() {
                return ((ThickContent) this.instance).hasPriority();
            }

            public CommonTypesProto.Priority getPriority() {
                return ((ThickContent) this.instance).getPriority();
            }

            public Builder setPriority(CommonTypesProto.Priority value) {
                copyOnWrite();
                ((ThickContent) this.instance).setPriority(value);
                return this;
            }

            public Builder setPriority(CommonTypesProto.Priority.Builder builderForValue) {
                copyOnWrite();
                ((ThickContent) this.instance).setPriority((CommonTypesProto.Priority) builderForValue.build());
                return this;
            }

            public Builder mergePriority(CommonTypesProto.Priority value) {
                copyOnWrite();
                ((ThickContent) this.instance).mergePriority(value);
                return this;
            }

            public Builder clearPriority() {
                copyOnWrite();
                ((ThickContent) this.instance).clearPriority();
                return this;
            }

            public List<CommonTypesProto.TriggeringCondition> getTriggeringConditionsList() {
                return Collections.unmodifiableList(((ThickContent) this.instance).getTriggeringConditionsList());
            }

            public int getTriggeringConditionsCount() {
                return ((ThickContent) this.instance).getTriggeringConditionsCount();
            }

            public CommonTypesProto.TriggeringCondition getTriggeringConditions(int index) {
                return ((ThickContent) this.instance).getTriggeringConditions(index);
            }

            public Builder setTriggeringConditions(int index, CommonTypesProto.TriggeringCondition value) {
                copyOnWrite();
                ((ThickContent) this.instance).setTriggeringConditions(index, value);
                return this;
            }

            public Builder setTriggeringConditions(int index, CommonTypesProto.TriggeringCondition.Builder builderForValue) {
                copyOnWrite();
                ((ThickContent) this.instance).setTriggeringConditions(index, (CommonTypesProto.TriggeringCondition) builderForValue.build());
                return this;
            }

            public Builder addTriggeringConditions(CommonTypesProto.TriggeringCondition value) {
                copyOnWrite();
                ((ThickContent) this.instance).addTriggeringConditions(value);
                return this;
            }

            public Builder addTriggeringConditions(int index, CommonTypesProto.TriggeringCondition value) {
                copyOnWrite();
                ((ThickContent) this.instance).addTriggeringConditions(index, value);
                return this;
            }

            public Builder addTriggeringConditions(CommonTypesProto.TriggeringCondition.Builder builderForValue) {
                copyOnWrite();
                ((ThickContent) this.instance).addTriggeringConditions((CommonTypesProto.TriggeringCondition) builderForValue.build());
                return this;
            }

            public Builder addTriggeringConditions(int index, CommonTypesProto.TriggeringCondition.Builder builderForValue) {
                copyOnWrite();
                ((ThickContent) this.instance).addTriggeringConditions(index, (CommonTypesProto.TriggeringCondition) builderForValue.build());
                return this;
            }

            public Builder addAllTriggeringConditions(Iterable<? extends CommonTypesProto.TriggeringCondition> values) {
                copyOnWrite();
                ((ThickContent) this.instance).addAllTriggeringConditions(values);
                return this;
            }

            public Builder clearTriggeringConditions() {
                copyOnWrite();
                ((ThickContent) this.instance).clearTriggeringConditions();
                return this;
            }

            public Builder removeTriggeringConditions(int index) {
                copyOnWrite();
                ((ThickContent) this.instance).removeTriggeringConditions(index);
                return this;
            }

            public boolean getIsTestCampaign() {
                return ((ThickContent) this.instance).getIsTestCampaign();
            }

            public Builder setIsTestCampaign(boolean value) {
                copyOnWrite();
                ((ThickContent) this.instance).setIsTestCampaign(value);
                return this;
            }

            public Builder clearIsTestCampaign() {
                copyOnWrite();
                ((ThickContent) this.instance).clearIsTestCampaign();
                return this;
            }

            public int getDataBundleCount() {
                return ((ThickContent) this.instance).getDataBundleMap().size();
            }

            public boolean containsDataBundle(String key) {
                key.getClass();
                return ((ThickContent) this.instance).getDataBundleMap().containsKey(key);
            }

            public Builder clearDataBundle() {
                copyOnWrite();
                ((ThickContent) this.instance).getMutableDataBundleMap().clear();
                return this;
            }

            public Builder removeDataBundle(String key) {
                key.getClass();
                copyOnWrite();
                ((ThickContent) this.instance).getMutableDataBundleMap().remove(key);
                return this;
            }

            @Deprecated
            public Map<String, String> getDataBundle() {
                return getDataBundleMap();
            }

            public Map<String, String> getDataBundleMap() {
                return Collections.unmodifiableMap(((ThickContent) this.instance).getDataBundleMap());
            }

            public String getDataBundleOrDefault(String key, String defaultValue) {
                key.getClass();
                Map<String, String> map = ((ThickContent) this.instance).getDataBundleMap();
                return map.containsKey(key) ? map.get(key) : defaultValue;
            }

            public String getDataBundleOrThrow(String key) {
                key.getClass();
                Map<String, String> map = ((ThickContent) this.instance).getDataBundleMap();
                if (map.containsKey(key)) {
                    return map.get(key);
                }
                throw new IllegalArgumentException();
            }

            public Builder putDataBundle(String key, String value) {
                key.getClass();
                value.getClass();
                copyOnWrite();
                ((ThickContent) this.instance).getMutableDataBundleMap().put(key, value);
                return this;
            }

            public Builder putAllDataBundle(Map<String, String> values) {
                copyOnWrite();
                ((ThickContent) this.instance).getMutableDataBundleMap().putAll(values);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C12551.f106xa1df5c61[method.ordinal()]) {
                case 1:
                    return new ThickContent();
                case 2:
                    return new Builder((C12551) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0007\u0001\u0000\u0001\b\u0007\u0001\u0001\u0000\u0001<\u0000\u0002<\u0000\u0003\t\u0004\t\u0005\u001b\u0007\u0007\b2", new Object[]{"payload_", "payloadCase_", VanillaCampaignPayload.class, ExperimentalCampaignPayload.class, "content_", "priority_", "triggeringConditions_", CommonTypesProto.TriggeringCondition.class, "isTestCampaign_", "dataBundle_", DataBundleDefaultEntryHolder.defaultEntry});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ThickContent> parser = PARSER;
                    if (parser == null) {
                        synchronized (ThickContent.class) {
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
            ThickContent defaultInstance = new ThickContent();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(ThickContent.class, defaultInstance);
        }

        public static ThickContent getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ThickContent> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto$VanillaCampaignPayload */
    public static final class VanillaCampaignPayload extends GeneratedMessageLite<VanillaCampaignPayload, Builder> implements VanillaCampaignPayloadOrBuilder {
        public static final int CAMPAIGN_END_TIME_MILLIS_FIELD_NUMBER = 4;
        public static final int CAMPAIGN_ID_FIELD_NUMBER = 1;
        public static final int CAMPAIGN_NAME_FIELD_NUMBER = 5;
        public static final int CAMPAIGN_START_TIME_MILLIS_FIELD_NUMBER = 3;
        /* access modifiers changed from: private */
        public static final VanillaCampaignPayload DEFAULT_INSTANCE;
        public static final int EXPERIMENTAL_CAMPAIGN_ID_FIELD_NUMBER = 2;
        private static volatile Parser<VanillaCampaignPayload> PARSER;
        private long campaignEndTimeMillis_;
        private String campaignId_ = "";
        private String campaignName_ = "";
        private long campaignStartTimeMillis_;
        private String experimentalCampaignId_ = "";

        private VanillaCampaignPayload() {
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

        public String getExperimentalCampaignId() {
            return this.experimentalCampaignId_;
        }

        public ByteString getExperimentalCampaignIdBytes() {
            return ByteString.copyFromUtf8(this.experimentalCampaignId_);
        }

        /* access modifiers changed from: private */
        public void setExperimentalCampaignId(String value) {
            value.getClass();
            this.experimentalCampaignId_ = value;
        }

        /* access modifiers changed from: private */
        public void clearExperimentalCampaignId() {
            this.experimentalCampaignId_ = getDefaultInstance().getExperimentalCampaignId();
        }

        /* access modifiers changed from: private */
        public void setExperimentalCampaignIdBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.experimentalCampaignId_ = value.toStringUtf8();
        }

        public long getCampaignStartTimeMillis() {
            return this.campaignStartTimeMillis_;
        }

        /* access modifiers changed from: private */
        public void setCampaignStartTimeMillis(long value) {
            this.campaignStartTimeMillis_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCampaignStartTimeMillis() {
            this.campaignStartTimeMillis_ = 0;
        }

        public long getCampaignEndTimeMillis() {
            return this.campaignEndTimeMillis_;
        }

        /* access modifiers changed from: private */
        public void setCampaignEndTimeMillis(long value) {
            this.campaignEndTimeMillis_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCampaignEndTimeMillis() {
            this.campaignEndTimeMillis_ = 0;
        }

        public String getCampaignName() {
            return this.campaignName_;
        }

        public ByteString getCampaignNameBytes() {
            return ByteString.copyFromUtf8(this.campaignName_);
        }

        /* access modifiers changed from: private */
        public void setCampaignName(String value) {
            value.getClass();
            this.campaignName_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCampaignName() {
            this.campaignName_ = getDefaultInstance().getCampaignName();
        }

        /* access modifiers changed from: private */
        public void setCampaignNameBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.campaignName_ = value.toStringUtf8();
        }

        public static VanillaCampaignPayload parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (VanillaCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static VanillaCampaignPayload parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (VanillaCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static VanillaCampaignPayload parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (VanillaCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static VanillaCampaignPayload parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (VanillaCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static VanillaCampaignPayload parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (VanillaCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static VanillaCampaignPayload parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (VanillaCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static VanillaCampaignPayload parseFrom(InputStream input) throws IOException {
            return (VanillaCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static VanillaCampaignPayload parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (VanillaCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static VanillaCampaignPayload parseDelimitedFrom(InputStream input) throws IOException {
            return (VanillaCampaignPayload) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static VanillaCampaignPayload parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (VanillaCampaignPayload) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static VanillaCampaignPayload parseFrom(CodedInputStream input) throws IOException {
            return (VanillaCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static VanillaCampaignPayload parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (VanillaCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(VanillaCampaignPayload prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        /* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto$VanillaCampaignPayload$Builder */
        public static final class Builder extends GeneratedMessageLite.Builder<VanillaCampaignPayload, Builder> implements VanillaCampaignPayloadOrBuilder {
            /* synthetic */ Builder(C12551 x0) {
                this();
            }

            private Builder() {
                super(VanillaCampaignPayload.DEFAULT_INSTANCE);
            }

            public String getCampaignId() {
                return ((VanillaCampaignPayload) this.instance).getCampaignId();
            }

            public ByteString getCampaignIdBytes() {
                return ((VanillaCampaignPayload) this.instance).getCampaignIdBytes();
            }

            public Builder setCampaignId(String value) {
                copyOnWrite();
                ((VanillaCampaignPayload) this.instance).setCampaignId(value);
                return this;
            }

            public Builder clearCampaignId() {
                copyOnWrite();
                ((VanillaCampaignPayload) this.instance).clearCampaignId();
                return this;
            }

            public Builder setCampaignIdBytes(ByteString value) {
                copyOnWrite();
                ((VanillaCampaignPayload) this.instance).setCampaignIdBytes(value);
                return this;
            }

            public String getExperimentalCampaignId() {
                return ((VanillaCampaignPayload) this.instance).getExperimentalCampaignId();
            }

            public ByteString getExperimentalCampaignIdBytes() {
                return ((VanillaCampaignPayload) this.instance).getExperimentalCampaignIdBytes();
            }

            public Builder setExperimentalCampaignId(String value) {
                copyOnWrite();
                ((VanillaCampaignPayload) this.instance).setExperimentalCampaignId(value);
                return this;
            }

            public Builder clearExperimentalCampaignId() {
                copyOnWrite();
                ((VanillaCampaignPayload) this.instance).clearExperimentalCampaignId();
                return this;
            }

            public Builder setExperimentalCampaignIdBytes(ByteString value) {
                copyOnWrite();
                ((VanillaCampaignPayload) this.instance).setExperimentalCampaignIdBytes(value);
                return this;
            }

            public long getCampaignStartTimeMillis() {
                return ((VanillaCampaignPayload) this.instance).getCampaignStartTimeMillis();
            }

            public Builder setCampaignStartTimeMillis(long value) {
                copyOnWrite();
                ((VanillaCampaignPayload) this.instance).setCampaignStartTimeMillis(value);
                return this;
            }

            public Builder clearCampaignStartTimeMillis() {
                copyOnWrite();
                ((VanillaCampaignPayload) this.instance).clearCampaignStartTimeMillis();
                return this;
            }

            public long getCampaignEndTimeMillis() {
                return ((VanillaCampaignPayload) this.instance).getCampaignEndTimeMillis();
            }

            public Builder setCampaignEndTimeMillis(long value) {
                copyOnWrite();
                ((VanillaCampaignPayload) this.instance).setCampaignEndTimeMillis(value);
                return this;
            }

            public Builder clearCampaignEndTimeMillis() {
                copyOnWrite();
                ((VanillaCampaignPayload) this.instance).clearCampaignEndTimeMillis();
                return this;
            }

            public String getCampaignName() {
                return ((VanillaCampaignPayload) this.instance).getCampaignName();
            }

            public ByteString getCampaignNameBytes() {
                return ((VanillaCampaignPayload) this.instance).getCampaignNameBytes();
            }

            public Builder setCampaignName(String value) {
                copyOnWrite();
                ((VanillaCampaignPayload) this.instance).setCampaignName(value);
                return this;
            }

            public Builder clearCampaignName() {
                copyOnWrite();
                ((VanillaCampaignPayload) this.instance).clearCampaignName();
                return this;
            }

            public Builder setCampaignNameBytes(ByteString value) {
                copyOnWrite();
                ((VanillaCampaignPayload) this.instance).setCampaignNameBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C12551.f106xa1df5c61[method.ordinal()]) {
                case 1:
                    return new VanillaCampaignPayload();
                case 2:
                    return new Builder((C12551) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0005\u0000\u0000\u0001\u0005\u0005\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003\u0002\u0004\u0002\u0005Ȉ", new Object[]{"campaignId_", "experimentalCampaignId_", "campaignStartTimeMillis_", "campaignEndTimeMillis_", "campaignName_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<VanillaCampaignPayload> parser = PARSER;
                    if (parser == null) {
                        synchronized (VanillaCampaignPayload.class) {
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
            VanillaCampaignPayload defaultInstance = new VanillaCampaignPayload();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(VanillaCampaignPayload.class, defaultInstance);
        }

        public static VanillaCampaignPayload getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<VanillaCampaignPayload> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto$ExperimentalCampaignPayload */
    public static final class ExperimentalCampaignPayload extends GeneratedMessageLite<ExperimentalCampaignPayload, Builder> implements ExperimentalCampaignPayloadOrBuilder {
        public static final int CAMPAIGN_END_TIME_MILLIS_FIELD_NUMBER = 4;
        public static final int CAMPAIGN_ID_FIELD_NUMBER = 1;
        public static final int CAMPAIGN_NAME_FIELD_NUMBER = 5;
        public static final int CAMPAIGN_START_TIME_MILLIS_FIELD_NUMBER = 3;
        /* access modifiers changed from: private */
        public static final ExperimentalCampaignPayload DEFAULT_INSTANCE;
        public static final int EXPERIMENT_PAYLOAD_FIELD_NUMBER = 2;
        private static volatile Parser<ExperimentalCampaignPayload> PARSER;
        private long campaignEndTimeMillis_;
        private String campaignId_ = "";
        private String campaignName_ = "";
        private long campaignStartTimeMillis_;
        private FirebaseAbt.ExperimentPayload experimentPayload_;

        private ExperimentalCampaignPayload() {
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

        public boolean hasExperimentPayload() {
            return this.experimentPayload_ != null;
        }

        public FirebaseAbt.ExperimentPayload getExperimentPayload() {
            FirebaseAbt.ExperimentPayload experimentPayload = this.experimentPayload_;
            return experimentPayload == null ? FirebaseAbt.ExperimentPayload.getDefaultInstance() : experimentPayload;
        }

        /* access modifiers changed from: private */
        public void setExperimentPayload(FirebaseAbt.ExperimentPayload value) {
            value.getClass();
            this.experimentPayload_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeExperimentPayload(FirebaseAbt.ExperimentPayload value) {
            value.getClass();
            FirebaseAbt.ExperimentPayload experimentPayload = this.experimentPayload_;
            if (experimentPayload == null || experimentPayload == FirebaseAbt.ExperimentPayload.getDefaultInstance()) {
                this.experimentPayload_ = value;
            } else {
                this.experimentPayload_ = (FirebaseAbt.ExperimentPayload) ((FirebaseAbt.ExperimentPayload.Builder) FirebaseAbt.ExperimentPayload.newBuilder(this.experimentPayload_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearExperimentPayload() {
            this.experimentPayload_ = null;
        }

        public long getCampaignStartTimeMillis() {
            return this.campaignStartTimeMillis_;
        }

        /* access modifiers changed from: private */
        public void setCampaignStartTimeMillis(long value) {
            this.campaignStartTimeMillis_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCampaignStartTimeMillis() {
            this.campaignStartTimeMillis_ = 0;
        }

        public long getCampaignEndTimeMillis() {
            return this.campaignEndTimeMillis_;
        }

        /* access modifiers changed from: private */
        public void setCampaignEndTimeMillis(long value) {
            this.campaignEndTimeMillis_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCampaignEndTimeMillis() {
            this.campaignEndTimeMillis_ = 0;
        }

        public String getCampaignName() {
            return this.campaignName_;
        }

        public ByteString getCampaignNameBytes() {
            return ByteString.copyFromUtf8(this.campaignName_);
        }

        /* access modifiers changed from: private */
        public void setCampaignName(String value) {
            value.getClass();
            this.campaignName_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCampaignName() {
            this.campaignName_ = getDefaultInstance().getCampaignName();
        }

        /* access modifiers changed from: private */
        public void setCampaignNameBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.campaignName_ = value.toStringUtf8();
        }

        public static ExperimentalCampaignPayload parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ExperimentalCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExperimentalCampaignPayload parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExperimentalCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExperimentalCampaignPayload parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ExperimentalCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExperimentalCampaignPayload parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExperimentalCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExperimentalCampaignPayload parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ExperimentalCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExperimentalCampaignPayload parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExperimentalCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExperimentalCampaignPayload parseFrom(InputStream input) throws IOException {
            return (ExperimentalCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExperimentalCampaignPayload parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExperimentalCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExperimentalCampaignPayload parseDelimitedFrom(InputStream input) throws IOException {
            return (ExperimentalCampaignPayload) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ExperimentalCampaignPayload parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExperimentalCampaignPayload) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExperimentalCampaignPayload parseFrom(CodedInputStream input) throws IOException {
            return (ExperimentalCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExperimentalCampaignPayload parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExperimentalCampaignPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ExperimentalCampaignPayload prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        /* renamed from: com.google.internal.firebase.inappmessaging.v1.CampaignProto$ExperimentalCampaignPayload$Builder */
        public static final class Builder extends GeneratedMessageLite.Builder<ExperimentalCampaignPayload, Builder> implements ExperimentalCampaignPayloadOrBuilder {
            /* synthetic */ Builder(C12551 x0) {
                this();
            }

            private Builder() {
                super(ExperimentalCampaignPayload.DEFAULT_INSTANCE);
            }

            public String getCampaignId() {
                return ((ExperimentalCampaignPayload) this.instance).getCampaignId();
            }

            public ByteString getCampaignIdBytes() {
                return ((ExperimentalCampaignPayload) this.instance).getCampaignIdBytes();
            }

            public Builder setCampaignId(String value) {
                copyOnWrite();
                ((ExperimentalCampaignPayload) this.instance).setCampaignId(value);
                return this;
            }

            public Builder clearCampaignId() {
                copyOnWrite();
                ((ExperimentalCampaignPayload) this.instance).clearCampaignId();
                return this;
            }

            public Builder setCampaignIdBytes(ByteString value) {
                copyOnWrite();
                ((ExperimentalCampaignPayload) this.instance).setCampaignIdBytes(value);
                return this;
            }

            public boolean hasExperimentPayload() {
                return ((ExperimentalCampaignPayload) this.instance).hasExperimentPayload();
            }

            public FirebaseAbt.ExperimentPayload getExperimentPayload() {
                return ((ExperimentalCampaignPayload) this.instance).getExperimentPayload();
            }

            public Builder setExperimentPayload(FirebaseAbt.ExperimentPayload value) {
                copyOnWrite();
                ((ExperimentalCampaignPayload) this.instance).setExperimentPayload(value);
                return this;
            }

            public Builder setExperimentPayload(FirebaseAbt.ExperimentPayload.Builder builderForValue) {
                copyOnWrite();
                ((ExperimentalCampaignPayload) this.instance).setExperimentPayload((FirebaseAbt.ExperimentPayload) builderForValue.build());
                return this;
            }

            public Builder mergeExperimentPayload(FirebaseAbt.ExperimentPayload value) {
                copyOnWrite();
                ((ExperimentalCampaignPayload) this.instance).mergeExperimentPayload(value);
                return this;
            }

            public Builder clearExperimentPayload() {
                copyOnWrite();
                ((ExperimentalCampaignPayload) this.instance).clearExperimentPayload();
                return this;
            }

            public long getCampaignStartTimeMillis() {
                return ((ExperimentalCampaignPayload) this.instance).getCampaignStartTimeMillis();
            }

            public Builder setCampaignStartTimeMillis(long value) {
                copyOnWrite();
                ((ExperimentalCampaignPayload) this.instance).setCampaignStartTimeMillis(value);
                return this;
            }

            public Builder clearCampaignStartTimeMillis() {
                copyOnWrite();
                ((ExperimentalCampaignPayload) this.instance).clearCampaignStartTimeMillis();
                return this;
            }

            public long getCampaignEndTimeMillis() {
                return ((ExperimentalCampaignPayload) this.instance).getCampaignEndTimeMillis();
            }

            public Builder setCampaignEndTimeMillis(long value) {
                copyOnWrite();
                ((ExperimentalCampaignPayload) this.instance).setCampaignEndTimeMillis(value);
                return this;
            }

            public Builder clearCampaignEndTimeMillis() {
                copyOnWrite();
                ((ExperimentalCampaignPayload) this.instance).clearCampaignEndTimeMillis();
                return this;
            }

            public String getCampaignName() {
                return ((ExperimentalCampaignPayload) this.instance).getCampaignName();
            }

            public ByteString getCampaignNameBytes() {
                return ((ExperimentalCampaignPayload) this.instance).getCampaignNameBytes();
            }

            public Builder setCampaignName(String value) {
                copyOnWrite();
                ((ExperimentalCampaignPayload) this.instance).setCampaignName(value);
                return this;
            }

            public Builder clearCampaignName() {
                copyOnWrite();
                ((ExperimentalCampaignPayload) this.instance).clearCampaignName();
                return this;
            }

            public Builder setCampaignNameBytes(ByteString value) {
                copyOnWrite();
                ((ExperimentalCampaignPayload) this.instance).setCampaignNameBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C12551.f106xa1df5c61[method.ordinal()]) {
                case 1:
                    return new ExperimentalCampaignPayload();
                case 2:
                    return new Builder((C12551) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0005\u0000\u0000\u0001\u0005\u0005\u0000\u0000\u0000\u0001Ȉ\u0002\t\u0003\u0002\u0004\u0002\u0005Ȉ", new Object[]{"campaignId_", "experimentPayload_", "campaignStartTimeMillis_", "campaignEndTimeMillis_", "campaignName_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ExperimentalCampaignPayload> parser = PARSER;
                    if (parser == null) {
                        synchronized (ExperimentalCampaignPayload.class) {
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
            ExperimentalCampaignPayload defaultInstance = new ExperimentalCampaignPayload();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(ExperimentalCampaignPayload.class, defaultInstance);
        }

        public static ExperimentalCampaignPayload getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ExperimentalCampaignPayload> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
