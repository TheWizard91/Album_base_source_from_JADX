package com.google.firebase.inappmessaging;

import com.google.firebase.inappmessaging.MessagesProto;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.type.Date;
import com.google.type.TimeOfDay;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class CommonTypesProto {

    public interface CampaignTimeOrBuilder extends MessageLiteOrBuilder {
        Date getDate();

        TimeOfDay getTime();

        String getTimeZone();

        ByteString getTimeZoneBytes();

        boolean hasDate();

        boolean hasTime();
    }

    public interface DailyAnalyticsSummaryOrBuilder extends MessageLiteOrBuilder {
        int getClicks();

        int getErrors();

        int getImpressions();

        long getStartOfDayMillis();
    }

    public interface DailyConversionSummaryOrBuilder extends MessageLiteOrBuilder {
        int getConversions();

        long getStartOfDayMillis();
    }

    public interface EventOrBuilder extends MessageLiteOrBuilder {
        int getCount();

        String getName();

        ByteString getNameBytes();

        long getPreviousTimestampMillis();

        long getTimestampMillis();

        TriggerParam getTriggerParams(int i);

        int getTriggerParamsCount();

        List<TriggerParam> getTriggerParamsList();
    }

    public interface ExperimentVariantOrBuilder extends MessageLiteOrBuilder {
        MessagesProto.Content getContent();

        int getIndex();

        boolean hasContent();
    }

    public interface PriorityOrBuilder extends MessageLiteOrBuilder {
        int getValue();
    }

    public interface ScionConversionEventOrBuilder extends MessageLiteOrBuilder {
        String getName();

        ByteString getNameBytes();
    }

    public interface TriggerParamOrBuilder extends MessageLiteOrBuilder {
        double getDoubleValue();

        float getFloatValue();

        long getIntValue();

        String getName();

        ByteString getNameBytes();

        String getStringValue();

        ByteString getStringValueBytes();
    }

    public interface TriggeringConditionOrBuilder extends MessageLiteOrBuilder {
        TriggeringCondition.ConditionCase getConditionCase();

        Event getEvent();

        Trigger getFiamTrigger();

        int getFiamTriggerValue();

        boolean hasEvent();
    }

    private CommonTypesProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public enum Trigger implements Internal.EnumLite {
        UNKNOWN_TRIGGER(0),
        APP_LAUNCH(1),
        ON_FOREGROUND(2),
        UNRECOGNIZED(-1);
        
        public static final int APP_LAUNCH_VALUE = 1;
        public static final int ON_FOREGROUND_VALUE = 2;
        public static final int UNKNOWN_TRIGGER_VALUE = 0;
        private static final Internal.EnumLiteMap<Trigger> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new Internal.EnumLiteMap<Trigger>() {
                public Trigger findValueByNumber(int number) {
                    return Trigger.forNumber(number);
                }
            };
        }

        public final int getNumber() {
            if (this != UNRECOGNIZED) {
                return this.value;
            }
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }

        @Deprecated
        public static Trigger valueOf(int value2) {
            return forNumber(value2);
        }

        public static Trigger forNumber(int value2) {
            if (value2 == 0) {
                return UNKNOWN_TRIGGER;
            }
            if (value2 == 1) {
                return APP_LAUNCH;
            }
            if (value2 != 2) {
                return null;
            }
            return ON_FOREGROUND;
        }

        public static Internal.EnumLiteMap<Trigger> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return TriggerVerifier.INSTANCE;
        }

        private static final class TriggerVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = null;

            private TriggerVerifier() {
            }

            static {
                INSTANCE = new TriggerVerifier();
            }

            public boolean isInRange(int number) {
                return Trigger.forNumber(number) != null;
            }
        }

        private Trigger(int value2) {
            this.value = value2;
        }
    }

    public enum CampaignState implements Internal.EnumLite {
        UNKNOWN_CAMPAIGN_STATE(0),
        DRAFT(1),
        PUBLISHED(2),
        STOPPED(3),
        DELETED(4),
        UNRECOGNIZED(-1);
        
        public static final int DELETED_VALUE = 4;
        public static final int DRAFT_VALUE = 1;
        public static final int PUBLISHED_VALUE = 2;
        public static final int STOPPED_VALUE = 3;
        public static final int UNKNOWN_CAMPAIGN_STATE_VALUE = 0;
        private static final Internal.EnumLiteMap<CampaignState> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new Internal.EnumLiteMap<CampaignState>() {
                public CampaignState findValueByNumber(int number) {
                    return CampaignState.forNumber(number);
                }
            };
        }

        public final int getNumber() {
            if (this != UNRECOGNIZED) {
                return this.value;
            }
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }

        @Deprecated
        public static CampaignState valueOf(int value2) {
            return forNumber(value2);
        }

        public static CampaignState forNumber(int value2) {
            if (value2 == 0) {
                return UNKNOWN_CAMPAIGN_STATE;
            }
            if (value2 == 1) {
                return DRAFT;
            }
            if (value2 == 2) {
                return PUBLISHED;
            }
            if (value2 == 3) {
                return STOPPED;
            }
            if (value2 != 4) {
                return null;
            }
            return DELETED;
        }

        public static Internal.EnumLiteMap<CampaignState> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return CampaignStateVerifier.INSTANCE;
        }

        private static final class CampaignStateVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = null;

            private CampaignStateVerifier() {
            }

            static {
                INSTANCE = new CampaignStateVerifier();
            }

            public boolean isInRange(int number) {
                return CampaignState.forNumber(number) != null;
            }
        }

        private CampaignState(int value2) {
            this.value = value2;
        }
    }

    public enum ExperimentalCampaignState implements Internal.EnumLite {
        UNKNOWN_EXPERIMENTAL_CAMPAIGN_STATE(0),
        EXPERIMENT_DRAFT(1),
        EXPERIMENT_RUNNING(2),
        EXPERIMENT_STOPPED(3),
        EXPERIMENT_ROLLED_OUT(4),
        UNRECOGNIZED(-1);
        
        public static final int EXPERIMENT_DRAFT_VALUE = 1;
        public static final int EXPERIMENT_ROLLED_OUT_VALUE = 4;
        public static final int EXPERIMENT_RUNNING_VALUE = 2;
        public static final int EXPERIMENT_STOPPED_VALUE = 3;
        public static final int UNKNOWN_EXPERIMENTAL_CAMPAIGN_STATE_VALUE = 0;
        private static final Internal.EnumLiteMap<ExperimentalCampaignState> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new Internal.EnumLiteMap<ExperimentalCampaignState>() {
                public ExperimentalCampaignState findValueByNumber(int number) {
                    return ExperimentalCampaignState.forNumber(number);
                }
            };
        }

        public final int getNumber() {
            if (this != UNRECOGNIZED) {
                return this.value;
            }
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }

        @Deprecated
        public static ExperimentalCampaignState valueOf(int value2) {
            return forNumber(value2);
        }

        public static ExperimentalCampaignState forNumber(int value2) {
            if (value2 == 0) {
                return UNKNOWN_EXPERIMENTAL_CAMPAIGN_STATE;
            }
            if (value2 == 1) {
                return EXPERIMENT_DRAFT;
            }
            if (value2 == 2) {
                return EXPERIMENT_RUNNING;
            }
            if (value2 == 3) {
                return EXPERIMENT_STOPPED;
            }
            if (value2 != 4) {
                return null;
            }
            return EXPERIMENT_ROLLED_OUT;
        }

        public static Internal.EnumLiteMap<ExperimentalCampaignState> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return ExperimentalCampaignStateVerifier.INSTANCE;
        }

        private static final class ExperimentalCampaignStateVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = null;

            private ExperimentalCampaignStateVerifier() {
            }

            static {
                INSTANCE = new ExperimentalCampaignStateVerifier();
            }

            public boolean isInRange(int number) {
                return ExperimentalCampaignState.forNumber(number) != null;
            }
        }

        private ExperimentalCampaignState(int value2) {
            this.value = value2;
        }
    }

    public static final class CampaignTime extends GeneratedMessageLite<CampaignTime, Builder> implements CampaignTimeOrBuilder {
        public static final int DATE_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final CampaignTime DEFAULT_INSTANCE;
        private static volatile Parser<CampaignTime> PARSER = null;
        public static final int TIME_FIELD_NUMBER = 2;
        public static final int TIME_ZONE_FIELD_NUMBER = 3;
        private Date date_;
        private String timeZone_ = "";
        private TimeOfDay time_;

        private CampaignTime() {
        }

        public boolean hasDate() {
            return this.date_ != null;
        }

        public Date getDate() {
            Date date = this.date_;
            return date == null ? Date.getDefaultInstance() : date;
        }

        /* access modifiers changed from: private */
        public void setDate(Date value) {
            value.getClass();
            this.date_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeDate(Date value) {
            value.getClass();
            Date date = this.date_;
            if (date == null || date == Date.getDefaultInstance()) {
                this.date_ = value;
            } else {
                this.date_ = (Date) ((Date.Builder) Date.newBuilder(this.date_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearDate() {
            this.date_ = null;
        }

        public boolean hasTime() {
            return this.time_ != null;
        }

        public TimeOfDay getTime() {
            TimeOfDay timeOfDay = this.time_;
            return timeOfDay == null ? TimeOfDay.getDefaultInstance() : timeOfDay;
        }

        /* access modifiers changed from: private */
        public void setTime(TimeOfDay value) {
            value.getClass();
            this.time_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeTime(TimeOfDay value) {
            value.getClass();
            TimeOfDay timeOfDay = this.time_;
            if (timeOfDay == null || timeOfDay == TimeOfDay.getDefaultInstance()) {
                this.time_ = value;
            } else {
                this.time_ = (TimeOfDay) ((TimeOfDay.Builder) TimeOfDay.newBuilder(this.time_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearTime() {
            this.time_ = null;
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

        public static CampaignTime parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (CampaignTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CampaignTime parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CampaignTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CampaignTime parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CampaignTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CampaignTime parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CampaignTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CampaignTime parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CampaignTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CampaignTime parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CampaignTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CampaignTime parseFrom(InputStream input) throws IOException {
            return (CampaignTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CampaignTime parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CampaignTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CampaignTime parseDelimitedFrom(InputStream input) throws IOException {
            return (CampaignTime) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static CampaignTime parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CampaignTime) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CampaignTime parseFrom(CodedInputStream input) throws IOException {
            return (CampaignTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CampaignTime parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CampaignTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(CampaignTime prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<CampaignTime, Builder> implements CampaignTimeOrBuilder {
            /* synthetic */ Builder(C39311 x0) {
                this();
            }

            private Builder() {
                super(CampaignTime.DEFAULT_INSTANCE);
            }

            public boolean hasDate() {
                return ((CampaignTime) this.instance).hasDate();
            }

            public Date getDate() {
                return ((CampaignTime) this.instance).getDate();
            }

            public Builder setDate(Date value) {
                copyOnWrite();
                ((CampaignTime) this.instance).setDate(value);
                return this;
            }

            public Builder setDate(Date.Builder builderForValue) {
                copyOnWrite();
                ((CampaignTime) this.instance).setDate((Date) builderForValue.build());
                return this;
            }

            public Builder mergeDate(Date value) {
                copyOnWrite();
                ((CampaignTime) this.instance).mergeDate(value);
                return this;
            }

            public Builder clearDate() {
                copyOnWrite();
                ((CampaignTime) this.instance).clearDate();
                return this;
            }

            public boolean hasTime() {
                return ((CampaignTime) this.instance).hasTime();
            }

            public TimeOfDay getTime() {
                return ((CampaignTime) this.instance).getTime();
            }

            public Builder setTime(TimeOfDay value) {
                copyOnWrite();
                ((CampaignTime) this.instance).setTime(value);
                return this;
            }

            public Builder setTime(TimeOfDay.Builder builderForValue) {
                copyOnWrite();
                ((CampaignTime) this.instance).setTime((TimeOfDay) builderForValue.build());
                return this;
            }

            public Builder mergeTime(TimeOfDay value) {
                copyOnWrite();
                ((CampaignTime) this.instance).mergeTime(value);
                return this;
            }

            public Builder clearTime() {
                copyOnWrite();
                ((CampaignTime) this.instance).clearTime();
                return this;
            }

            public String getTimeZone() {
                return ((CampaignTime) this.instance).getTimeZone();
            }

            public ByteString getTimeZoneBytes() {
                return ((CampaignTime) this.instance).getTimeZoneBytes();
            }

            public Builder setTimeZone(String value) {
                copyOnWrite();
                ((CampaignTime) this.instance).setTimeZone(value);
                return this;
            }

            public Builder clearTimeZone() {
                copyOnWrite();
                ((CampaignTime) this.instance).clearTimeZone();
                return this;
            }

            public Builder setTimeZoneBytes(ByteString value) {
                copyOnWrite();
                ((CampaignTime) this.instance).setTimeZoneBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39311.f1738xa1df5c61[method.ordinal()]) {
                case 1:
                    return new CampaignTime();
                case 2:
                    return new Builder((C39311) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0002\t\u0003Ȉ", new Object[]{"date_", "time_", "timeZone_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<CampaignTime> parser = PARSER;
                    if (parser == null) {
                        synchronized (CampaignTime.class) {
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
            CampaignTime defaultInstance = new CampaignTime();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(CampaignTime.class, defaultInstance);
        }

        public static CampaignTime getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CampaignTime> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.CommonTypesProto$1 */
    static /* synthetic */ class C39311 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        static final /* synthetic */ int[] f1738xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f1738xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1738xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1738xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1738xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1738xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1738xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1738xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public static final class TriggeringCondition extends GeneratedMessageLite<TriggeringCondition, Builder> implements TriggeringConditionOrBuilder {
        /* access modifiers changed from: private */
        public static final TriggeringCondition DEFAULT_INSTANCE;
        public static final int EVENT_FIELD_NUMBER = 2;
        public static final int FIAM_TRIGGER_FIELD_NUMBER = 1;
        private static volatile Parser<TriggeringCondition> PARSER;
        private int conditionCase_ = 0;
        private Object condition_;

        private TriggeringCondition() {
        }

        public enum ConditionCase {
            FIAM_TRIGGER(1),
            EVENT(2),
            CONDITION_NOT_SET(0);
            
            private final int value;

            private ConditionCase(int value2) {
                this.value = value2;
            }

            @Deprecated
            public static ConditionCase valueOf(int value2) {
                return forNumber(value2);
            }

            public static ConditionCase forNumber(int value2) {
                if (value2 == 0) {
                    return CONDITION_NOT_SET;
                }
                if (value2 == 1) {
                    return FIAM_TRIGGER;
                }
                if (value2 != 2) {
                    return null;
                }
                return EVENT;
            }

            public int getNumber() {
                return this.value;
            }
        }

        public ConditionCase getConditionCase() {
            return ConditionCase.forNumber(this.conditionCase_);
        }

        /* access modifiers changed from: private */
        public void clearCondition() {
            this.conditionCase_ = 0;
            this.condition_ = null;
        }

        public int getFiamTriggerValue() {
            if (this.conditionCase_ == 1) {
                return ((Integer) this.condition_).intValue();
            }
            return 0;
        }

        public Trigger getFiamTrigger() {
            if (this.conditionCase_ != 1) {
                return Trigger.UNKNOWN_TRIGGER;
            }
            Trigger result = Trigger.forNumber(((Integer) this.condition_).intValue());
            return result == null ? Trigger.UNRECOGNIZED : result;
        }

        /* access modifiers changed from: private */
        public void setFiamTriggerValue(int value) {
            this.conditionCase_ = 1;
            this.condition_ = Integer.valueOf(value);
        }

        /* access modifiers changed from: private */
        public void setFiamTrigger(Trigger value) {
            this.condition_ = Integer.valueOf(value.getNumber());
            this.conditionCase_ = 1;
        }

        /* access modifiers changed from: private */
        public void clearFiamTrigger() {
            if (this.conditionCase_ == 1) {
                this.conditionCase_ = 0;
                this.condition_ = null;
            }
        }

        public boolean hasEvent() {
            return this.conditionCase_ == 2;
        }

        public Event getEvent() {
            if (this.conditionCase_ == 2) {
                return (Event) this.condition_;
            }
            return Event.getDefaultInstance();
        }

        /* access modifiers changed from: private */
        public void setEvent(Event value) {
            value.getClass();
            this.condition_ = value;
            this.conditionCase_ = 2;
        }

        /* access modifiers changed from: private */
        public void mergeEvent(Event value) {
            value.getClass();
            if (this.conditionCase_ != 2 || this.condition_ == Event.getDefaultInstance()) {
                this.condition_ = value;
            } else {
                this.condition_ = ((Event.Builder) Event.newBuilder((Event) this.condition_).mergeFrom(value)).buildPartial();
            }
            this.conditionCase_ = 2;
        }

        /* access modifiers changed from: private */
        public void clearEvent() {
            if (this.conditionCase_ == 2) {
                this.conditionCase_ = 0;
                this.condition_ = null;
            }
        }

        public static TriggeringCondition parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (TriggeringCondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TriggeringCondition parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TriggeringCondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TriggeringCondition parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (TriggeringCondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TriggeringCondition parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TriggeringCondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TriggeringCondition parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (TriggeringCondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TriggeringCondition parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TriggeringCondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TriggeringCondition parseFrom(InputStream input) throws IOException {
            return (TriggeringCondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TriggeringCondition parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TriggeringCondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TriggeringCondition parseDelimitedFrom(InputStream input) throws IOException {
            return (TriggeringCondition) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static TriggeringCondition parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TriggeringCondition) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TriggeringCondition parseFrom(CodedInputStream input) throws IOException {
            return (TriggeringCondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TriggeringCondition parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TriggeringCondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(TriggeringCondition prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<TriggeringCondition, Builder> implements TriggeringConditionOrBuilder {
            /* synthetic */ Builder(C39311 x0) {
                this();
            }

            private Builder() {
                super(TriggeringCondition.DEFAULT_INSTANCE);
            }

            public ConditionCase getConditionCase() {
                return ((TriggeringCondition) this.instance).getConditionCase();
            }

            public Builder clearCondition() {
                copyOnWrite();
                ((TriggeringCondition) this.instance).clearCondition();
                return this;
            }

            public int getFiamTriggerValue() {
                return ((TriggeringCondition) this.instance).getFiamTriggerValue();
            }

            public Builder setFiamTriggerValue(int value) {
                copyOnWrite();
                ((TriggeringCondition) this.instance).setFiamTriggerValue(value);
                return this;
            }

            public Trigger getFiamTrigger() {
                return ((TriggeringCondition) this.instance).getFiamTrigger();
            }

            public Builder setFiamTrigger(Trigger value) {
                copyOnWrite();
                ((TriggeringCondition) this.instance).setFiamTrigger(value);
                return this;
            }

            public Builder clearFiamTrigger() {
                copyOnWrite();
                ((TriggeringCondition) this.instance).clearFiamTrigger();
                return this;
            }

            public boolean hasEvent() {
                return ((TriggeringCondition) this.instance).hasEvent();
            }

            public Event getEvent() {
                return ((TriggeringCondition) this.instance).getEvent();
            }

            public Builder setEvent(Event value) {
                copyOnWrite();
                ((TriggeringCondition) this.instance).setEvent(value);
                return this;
            }

            public Builder setEvent(Event.Builder builderForValue) {
                copyOnWrite();
                ((TriggeringCondition) this.instance).setEvent((Event) builderForValue.build());
                return this;
            }

            public Builder mergeEvent(Event value) {
                copyOnWrite();
                ((TriggeringCondition) this.instance).mergeEvent(value);
                return this;
            }

            public Builder clearEvent() {
                copyOnWrite();
                ((TriggeringCondition) this.instance).clearEvent();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39311.f1738xa1df5c61[method.ordinal()]) {
                case 1:
                    return new TriggeringCondition();
                case 2:
                    return new Builder((C39311) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0001\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001?\u0000\u0002<\u0000", new Object[]{"condition_", "conditionCase_", Event.class});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<TriggeringCondition> parser = PARSER;
                    if (parser == null) {
                        synchronized (TriggeringCondition.class) {
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
            TriggeringCondition defaultInstance = new TriggeringCondition();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(TriggeringCondition.class, defaultInstance);
        }

        public static TriggeringCondition getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TriggeringCondition> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class Event extends GeneratedMessageLite<Event, Builder> implements EventOrBuilder {
        public static final int COUNT_FIELD_NUMBER = 5;
        /* access modifiers changed from: private */
        public static final Event DEFAULT_INSTANCE;
        public static final int NAME_FIELD_NUMBER = 2;
        private static volatile Parser<Event> PARSER = null;
        public static final int PREVIOUS_TIMESTAMP_MILLIS_FIELD_NUMBER = 4;
        public static final int TIMESTAMP_MILLIS_FIELD_NUMBER = 3;
        public static final int TRIGGER_PARAMS_FIELD_NUMBER = 1;
        private int count_;
        private String name_ = "";
        private long previousTimestampMillis_;
        private long timestampMillis_;
        private Internal.ProtobufList<TriggerParam> triggerParams_ = emptyProtobufList();

        private Event() {
        }

        public List<TriggerParam> getTriggerParamsList() {
            return this.triggerParams_;
        }

        public List<? extends TriggerParamOrBuilder> getTriggerParamsOrBuilderList() {
            return this.triggerParams_;
        }

        public int getTriggerParamsCount() {
            return this.triggerParams_.size();
        }

        public TriggerParam getTriggerParams(int index) {
            return (TriggerParam) this.triggerParams_.get(index);
        }

        public TriggerParamOrBuilder getTriggerParamsOrBuilder(int index) {
            return (TriggerParamOrBuilder) this.triggerParams_.get(index);
        }

        private void ensureTriggerParamsIsMutable() {
            if (!this.triggerParams_.isModifiable()) {
                this.triggerParams_ = GeneratedMessageLite.mutableCopy(this.triggerParams_);
            }
        }

        /* access modifiers changed from: private */
        public void setTriggerParams(int index, TriggerParam value) {
            value.getClass();
            ensureTriggerParamsIsMutable();
            this.triggerParams_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addTriggerParams(TriggerParam value) {
            value.getClass();
            ensureTriggerParamsIsMutable();
            this.triggerParams_.add(value);
        }

        /* access modifiers changed from: private */
        public void addTriggerParams(int index, TriggerParam value) {
            value.getClass();
            ensureTriggerParamsIsMutable();
            this.triggerParams_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllTriggerParams(Iterable<? extends TriggerParam> values) {
            ensureTriggerParamsIsMutable();
            AbstractMessageLite.addAll(values, this.triggerParams_);
        }

        /* access modifiers changed from: private */
        public void clearTriggerParams() {
            this.triggerParams_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeTriggerParams(int index) {
            ensureTriggerParamsIsMutable();
            this.triggerParams_.remove(index);
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            value.getClass();
            this.name_ = value;
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.name_ = value.toStringUtf8();
        }

        public long getTimestampMillis() {
            return this.timestampMillis_;
        }

        /* access modifiers changed from: private */
        public void setTimestampMillis(long value) {
            this.timestampMillis_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTimestampMillis() {
            this.timestampMillis_ = 0;
        }

        public long getPreviousTimestampMillis() {
            return this.previousTimestampMillis_;
        }

        /* access modifiers changed from: private */
        public void setPreviousTimestampMillis(long value) {
            this.previousTimestampMillis_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPreviousTimestampMillis() {
            this.previousTimestampMillis_ = 0;
        }

        public int getCount() {
            return this.count_;
        }

        /* access modifiers changed from: private */
        public void setCount(int value) {
            this.count_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCount() {
            this.count_ = 0;
        }

        public static Event parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Event) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Event parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Event) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Event parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Event) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Event parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Event) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Event parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Event) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Event parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Event) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Event parseFrom(InputStream input) throws IOException {
            return (Event) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Event parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Event) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Event parseDelimitedFrom(InputStream input) throws IOException {
            return (Event) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Event parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Event) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Event parseFrom(CodedInputStream input) throws IOException {
            return (Event) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Event parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Event) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Event prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Event, Builder> implements EventOrBuilder {
            /* synthetic */ Builder(C39311 x0) {
                this();
            }

            private Builder() {
                super(Event.DEFAULT_INSTANCE);
            }

            public List<TriggerParam> getTriggerParamsList() {
                return Collections.unmodifiableList(((Event) this.instance).getTriggerParamsList());
            }

            public int getTriggerParamsCount() {
                return ((Event) this.instance).getTriggerParamsCount();
            }

            public TriggerParam getTriggerParams(int index) {
                return ((Event) this.instance).getTriggerParams(index);
            }

            public Builder setTriggerParams(int index, TriggerParam value) {
                copyOnWrite();
                ((Event) this.instance).setTriggerParams(index, value);
                return this;
            }

            public Builder setTriggerParams(int index, TriggerParam.Builder builderForValue) {
                copyOnWrite();
                ((Event) this.instance).setTriggerParams(index, (TriggerParam) builderForValue.build());
                return this;
            }

            public Builder addTriggerParams(TriggerParam value) {
                copyOnWrite();
                ((Event) this.instance).addTriggerParams(value);
                return this;
            }

            public Builder addTriggerParams(int index, TriggerParam value) {
                copyOnWrite();
                ((Event) this.instance).addTriggerParams(index, value);
                return this;
            }

            public Builder addTriggerParams(TriggerParam.Builder builderForValue) {
                copyOnWrite();
                ((Event) this.instance).addTriggerParams((TriggerParam) builderForValue.build());
                return this;
            }

            public Builder addTriggerParams(int index, TriggerParam.Builder builderForValue) {
                copyOnWrite();
                ((Event) this.instance).addTriggerParams(index, (TriggerParam) builderForValue.build());
                return this;
            }

            public Builder addAllTriggerParams(Iterable<? extends TriggerParam> values) {
                copyOnWrite();
                ((Event) this.instance).addAllTriggerParams(values);
                return this;
            }

            public Builder clearTriggerParams() {
                copyOnWrite();
                ((Event) this.instance).clearTriggerParams();
                return this;
            }

            public Builder removeTriggerParams(int index) {
                copyOnWrite();
                ((Event) this.instance).removeTriggerParams(index);
                return this;
            }

            public String getName() {
                return ((Event) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((Event) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((Event) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((Event) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((Event) this.instance).setNameBytes(value);
                return this;
            }

            public long getTimestampMillis() {
                return ((Event) this.instance).getTimestampMillis();
            }

            public Builder setTimestampMillis(long value) {
                copyOnWrite();
                ((Event) this.instance).setTimestampMillis(value);
                return this;
            }

            public Builder clearTimestampMillis() {
                copyOnWrite();
                ((Event) this.instance).clearTimestampMillis();
                return this;
            }

            public long getPreviousTimestampMillis() {
                return ((Event) this.instance).getPreviousTimestampMillis();
            }

            public Builder setPreviousTimestampMillis(long value) {
                copyOnWrite();
                ((Event) this.instance).setPreviousTimestampMillis(value);
                return this;
            }

            public Builder clearPreviousTimestampMillis() {
                copyOnWrite();
                ((Event) this.instance).clearPreviousTimestampMillis();
                return this;
            }

            public int getCount() {
                return ((Event) this.instance).getCount();
            }

            public Builder setCount(int value) {
                copyOnWrite();
                ((Event) this.instance).setCount(value);
                return this;
            }

            public Builder clearCount() {
                copyOnWrite();
                ((Event) this.instance).clearCount();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39311.f1738xa1df5c61[method.ordinal()]) {
                case 1:
                    return new Event();
                case 2:
                    return new Builder((C39311) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0005\u0000\u0000\u0001\u0005\u0005\u0000\u0001\u0000\u0001\u001b\u0002Ȉ\u0003\u0002\u0004\u0002\u0005\u0004", new Object[]{"triggerParams_", TriggerParam.class, "name_", "timestampMillis_", "previousTimestampMillis_", "count_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<Event> parser = PARSER;
                    if (parser == null) {
                        synchronized (Event.class) {
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
            Event defaultInstance = new Event();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(Event.class, defaultInstance);
        }

        public static Event getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Event> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class TriggerParam extends GeneratedMessageLite<TriggerParam, Builder> implements TriggerParamOrBuilder {
        /* access modifiers changed from: private */
        public static final TriggerParam DEFAULT_INSTANCE;
        public static final int DOUBLE_VALUE_FIELD_NUMBER = 5;
        public static final int FLOAT_VALUE_FIELD_NUMBER = 4;
        public static final int INT_VALUE_FIELD_NUMBER = 3;
        public static final int NAME_FIELD_NUMBER = 1;
        private static volatile Parser<TriggerParam> PARSER = null;
        public static final int STRING_VALUE_FIELD_NUMBER = 2;
        private double doubleValue_;
        private float floatValue_;
        private long intValue_;
        private String name_ = "";
        private String stringValue_ = "";

        private TriggerParam() {
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            value.getClass();
            this.name_ = value;
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.name_ = value.toStringUtf8();
        }

        public String getStringValue() {
            return this.stringValue_;
        }

        public ByteString getStringValueBytes() {
            return ByteString.copyFromUtf8(this.stringValue_);
        }

        /* access modifiers changed from: private */
        public void setStringValue(String value) {
            value.getClass();
            this.stringValue_ = value;
        }

        /* access modifiers changed from: private */
        public void clearStringValue() {
            this.stringValue_ = getDefaultInstance().getStringValue();
        }

        /* access modifiers changed from: private */
        public void setStringValueBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.stringValue_ = value.toStringUtf8();
        }

        public long getIntValue() {
            return this.intValue_;
        }

        /* access modifiers changed from: private */
        public void setIntValue(long value) {
            this.intValue_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIntValue() {
            this.intValue_ = 0;
        }

        public float getFloatValue() {
            return this.floatValue_;
        }

        /* access modifiers changed from: private */
        public void setFloatValue(float value) {
            this.floatValue_ = value;
        }

        /* access modifiers changed from: private */
        public void clearFloatValue() {
            this.floatValue_ = 0.0f;
        }

        public double getDoubleValue() {
            return this.doubleValue_;
        }

        /* access modifiers changed from: private */
        public void setDoubleValue(double value) {
            this.doubleValue_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDoubleValue() {
            this.doubleValue_ = 0.0d;
        }

        public static TriggerParam parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (TriggerParam) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TriggerParam parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TriggerParam) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TriggerParam parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (TriggerParam) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TriggerParam parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TriggerParam) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TriggerParam parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (TriggerParam) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TriggerParam parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TriggerParam) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TriggerParam parseFrom(InputStream input) throws IOException {
            return (TriggerParam) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TriggerParam parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TriggerParam) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TriggerParam parseDelimitedFrom(InputStream input) throws IOException {
            return (TriggerParam) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static TriggerParam parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TriggerParam) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TriggerParam parseFrom(CodedInputStream input) throws IOException {
            return (TriggerParam) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TriggerParam parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TriggerParam) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(TriggerParam prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<TriggerParam, Builder> implements TriggerParamOrBuilder {
            /* synthetic */ Builder(C39311 x0) {
                this();
            }

            private Builder() {
                super(TriggerParam.DEFAULT_INSTANCE);
            }

            public String getName() {
                return ((TriggerParam) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((TriggerParam) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((TriggerParam) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((TriggerParam) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((TriggerParam) this.instance).setNameBytes(value);
                return this;
            }

            public String getStringValue() {
                return ((TriggerParam) this.instance).getStringValue();
            }

            public ByteString getStringValueBytes() {
                return ((TriggerParam) this.instance).getStringValueBytes();
            }

            public Builder setStringValue(String value) {
                copyOnWrite();
                ((TriggerParam) this.instance).setStringValue(value);
                return this;
            }

            public Builder clearStringValue() {
                copyOnWrite();
                ((TriggerParam) this.instance).clearStringValue();
                return this;
            }

            public Builder setStringValueBytes(ByteString value) {
                copyOnWrite();
                ((TriggerParam) this.instance).setStringValueBytes(value);
                return this;
            }

            public long getIntValue() {
                return ((TriggerParam) this.instance).getIntValue();
            }

            public Builder setIntValue(long value) {
                copyOnWrite();
                ((TriggerParam) this.instance).setIntValue(value);
                return this;
            }

            public Builder clearIntValue() {
                copyOnWrite();
                ((TriggerParam) this.instance).clearIntValue();
                return this;
            }

            public float getFloatValue() {
                return ((TriggerParam) this.instance).getFloatValue();
            }

            public Builder setFloatValue(float value) {
                copyOnWrite();
                ((TriggerParam) this.instance).setFloatValue(value);
                return this;
            }

            public Builder clearFloatValue() {
                copyOnWrite();
                ((TriggerParam) this.instance).clearFloatValue();
                return this;
            }

            public double getDoubleValue() {
                return ((TriggerParam) this.instance).getDoubleValue();
            }

            public Builder setDoubleValue(double value) {
                copyOnWrite();
                ((TriggerParam) this.instance).setDoubleValue(value);
                return this;
            }

            public Builder clearDoubleValue() {
                copyOnWrite();
                ((TriggerParam) this.instance).clearDoubleValue();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39311.f1738xa1df5c61[method.ordinal()]) {
                case 1:
                    return new TriggerParam();
                case 2:
                    return new Builder((C39311) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0005\u0000\u0000\u0001\u0005\u0005\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003\u0002\u0004\u0001\u0005\u0000", new Object[]{"name_", "stringValue_", "intValue_", "floatValue_", "doubleValue_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<TriggerParam> parser = PARSER;
                    if (parser == null) {
                        synchronized (TriggerParam.class) {
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
            TriggerParam defaultInstance = new TriggerParam();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(TriggerParam.class, defaultInstance);
        }

        public static TriggerParam getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TriggerParam> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class Priority extends GeneratedMessageLite<Priority, Builder> implements PriorityOrBuilder {
        /* access modifiers changed from: private */
        public static final Priority DEFAULT_INSTANCE;
        private static volatile Parser<Priority> PARSER = null;
        public static final int VALUE_FIELD_NUMBER = 1;
        private int value_;

        private Priority() {
        }

        public int getValue() {
            return this.value_;
        }

        /* access modifiers changed from: private */
        public void setValue(int value) {
            this.value_ = value;
        }

        /* access modifiers changed from: private */
        public void clearValue() {
            this.value_ = 0;
        }

        public static Priority parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Priority) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Priority parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Priority) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Priority parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Priority) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Priority parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Priority) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Priority parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Priority) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Priority parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Priority) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Priority parseFrom(InputStream input) throws IOException {
            return (Priority) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Priority parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Priority) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Priority parseDelimitedFrom(InputStream input) throws IOException {
            return (Priority) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Priority parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Priority) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Priority parseFrom(CodedInputStream input) throws IOException {
            return (Priority) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Priority parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Priority) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Priority prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Priority, Builder> implements PriorityOrBuilder {
            /* synthetic */ Builder(C39311 x0) {
                this();
            }

            private Builder() {
                super(Priority.DEFAULT_INSTANCE);
            }

            public int getValue() {
                return ((Priority) this.instance).getValue();
            }

            public Builder setValue(int value) {
                copyOnWrite();
                ((Priority) this.instance).setValue(value);
                return this;
            }

            public Builder clearValue() {
                copyOnWrite();
                ((Priority) this.instance).clearValue();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39311.f1738xa1df5c61[method.ordinal()]) {
                case 1:
                    return new Priority();
                case 2:
                    return new Builder((C39311) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004", new Object[]{"value_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<Priority> parser = PARSER;
                    if (parser == null) {
                        synchronized (Priority.class) {
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
            Priority defaultInstance = new Priority();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(Priority.class, defaultInstance);
        }

        public static Priority getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Priority> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class ScionConversionEvent extends GeneratedMessageLite<ScionConversionEvent, Builder> implements ScionConversionEventOrBuilder {
        /* access modifiers changed from: private */
        public static final ScionConversionEvent DEFAULT_INSTANCE;
        public static final int NAME_FIELD_NUMBER = 1;
        private static volatile Parser<ScionConversionEvent> PARSER;
        private String name_ = "";

        private ScionConversionEvent() {
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            value.getClass();
            this.name_ = value;
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.name_ = value.toStringUtf8();
        }

        public static ScionConversionEvent parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ScionConversionEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ScionConversionEvent parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ScionConversionEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ScionConversionEvent parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ScionConversionEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ScionConversionEvent parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ScionConversionEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ScionConversionEvent parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ScionConversionEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ScionConversionEvent parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ScionConversionEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ScionConversionEvent parseFrom(InputStream input) throws IOException {
            return (ScionConversionEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ScionConversionEvent parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ScionConversionEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ScionConversionEvent parseDelimitedFrom(InputStream input) throws IOException {
            return (ScionConversionEvent) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ScionConversionEvent parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ScionConversionEvent) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ScionConversionEvent parseFrom(CodedInputStream input) throws IOException {
            return (ScionConversionEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ScionConversionEvent parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ScionConversionEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ScionConversionEvent prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ScionConversionEvent, Builder> implements ScionConversionEventOrBuilder {
            /* synthetic */ Builder(C39311 x0) {
                this();
            }

            private Builder() {
                super(ScionConversionEvent.DEFAULT_INSTANCE);
            }

            public String getName() {
                return ((ScionConversionEvent) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((ScionConversionEvent) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((ScionConversionEvent) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((ScionConversionEvent) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((ScionConversionEvent) this.instance).setNameBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39311.f1738xa1df5c61[method.ordinal()]) {
                case 1:
                    return new ScionConversionEvent();
                case 2:
                    return new Builder((C39311) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ", new Object[]{"name_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ScionConversionEvent> parser = PARSER;
                    if (parser == null) {
                        synchronized (ScionConversionEvent.class) {
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
            ScionConversionEvent defaultInstance = new ScionConversionEvent();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(ScionConversionEvent.class, defaultInstance);
        }

        public static ScionConversionEvent getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ScionConversionEvent> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class ExperimentVariant extends GeneratedMessageLite<ExperimentVariant, Builder> implements ExperimentVariantOrBuilder {
        public static final int CONTENT_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final ExperimentVariant DEFAULT_INSTANCE;
        public static final int INDEX_FIELD_NUMBER = 1;
        private static volatile Parser<ExperimentVariant> PARSER;
        private MessagesProto.Content content_;
        private int index_;

        private ExperimentVariant() {
        }

        public int getIndex() {
            return this.index_;
        }

        /* access modifiers changed from: private */
        public void setIndex(int value) {
            this.index_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIndex() {
            this.index_ = 0;
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

        public static ExperimentVariant parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ExperimentVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExperimentVariant parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExperimentVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExperimentVariant parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ExperimentVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExperimentVariant parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExperimentVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExperimentVariant parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ExperimentVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExperimentVariant parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExperimentVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExperimentVariant parseFrom(InputStream input) throws IOException {
            return (ExperimentVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExperimentVariant parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExperimentVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExperimentVariant parseDelimitedFrom(InputStream input) throws IOException {
            return (ExperimentVariant) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ExperimentVariant parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExperimentVariant) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExperimentVariant parseFrom(CodedInputStream input) throws IOException {
            return (ExperimentVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExperimentVariant parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExperimentVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ExperimentVariant prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ExperimentVariant, Builder> implements ExperimentVariantOrBuilder {
            /* synthetic */ Builder(C39311 x0) {
                this();
            }

            private Builder() {
                super(ExperimentVariant.DEFAULT_INSTANCE);
            }

            public int getIndex() {
                return ((ExperimentVariant) this.instance).getIndex();
            }

            public Builder setIndex(int value) {
                copyOnWrite();
                ((ExperimentVariant) this.instance).setIndex(value);
                return this;
            }

            public Builder clearIndex() {
                copyOnWrite();
                ((ExperimentVariant) this.instance).clearIndex();
                return this;
            }

            public boolean hasContent() {
                return ((ExperimentVariant) this.instance).hasContent();
            }

            public MessagesProto.Content getContent() {
                return ((ExperimentVariant) this.instance).getContent();
            }

            public Builder setContent(MessagesProto.Content value) {
                copyOnWrite();
                ((ExperimentVariant) this.instance).setContent(value);
                return this;
            }

            public Builder setContent(MessagesProto.Content.Builder builderForValue) {
                copyOnWrite();
                ((ExperimentVariant) this.instance).setContent((MessagesProto.Content) builderForValue.build());
                return this;
            }

            public Builder mergeContent(MessagesProto.Content value) {
                copyOnWrite();
                ((ExperimentVariant) this.instance).mergeContent(value);
                return this;
            }

            public Builder clearContent() {
                copyOnWrite();
                ((ExperimentVariant) this.instance).clearContent();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39311.f1738xa1df5c61[method.ordinal()]) {
                case 1:
                    return new ExperimentVariant();
                case 2:
                    return new Builder((C39311) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0002\t", new Object[]{"index_", "content_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ExperimentVariant> parser = PARSER;
                    if (parser == null) {
                        synchronized (ExperimentVariant.class) {
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
            ExperimentVariant defaultInstance = new ExperimentVariant();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(ExperimentVariant.class, defaultInstance);
        }

        public static ExperimentVariant getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ExperimentVariant> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class DailyAnalyticsSummary extends GeneratedMessageLite<DailyAnalyticsSummary, Builder> implements DailyAnalyticsSummaryOrBuilder {
        public static final int CLICKS_FIELD_NUMBER = 3;
        /* access modifiers changed from: private */
        public static final DailyAnalyticsSummary DEFAULT_INSTANCE;
        public static final int ERRORS_FIELD_NUMBER = 4;
        public static final int IMPRESSIONS_FIELD_NUMBER = 2;
        private static volatile Parser<DailyAnalyticsSummary> PARSER = null;
        public static final int START_OF_DAY_MILLIS_FIELD_NUMBER = 1;
        private int clicks_;
        private int errors_;
        private int impressions_;
        private long startOfDayMillis_;

        private DailyAnalyticsSummary() {
        }

        public long getStartOfDayMillis() {
            return this.startOfDayMillis_;
        }

        /* access modifiers changed from: private */
        public void setStartOfDayMillis(long value) {
            this.startOfDayMillis_ = value;
        }

        /* access modifiers changed from: private */
        public void clearStartOfDayMillis() {
            this.startOfDayMillis_ = 0;
        }

        public int getImpressions() {
            return this.impressions_;
        }

        /* access modifiers changed from: private */
        public void setImpressions(int value) {
            this.impressions_ = value;
        }

        /* access modifiers changed from: private */
        public void clearImpressions() {
            this.impressions_ = 0;
        }

        public int getClicks() {
            return this.clicks_;
        }

        /* access modifiers changed from: private */
        public void setClicks(int value) {
            this.clicks_ = value;
        }

        /* access modifiers changed from: private */
        public void clearClicks() {
            this.clicks_ = 0;
        }

        public int getErrors() {
            return this.errors_;
        }

        /* access modifiers changed from: private */
        public void setErrors(int value) {
            this.errors_ = value;
        }

        /* access modifiers changed from: private */
        public void clearErrors() {
            this.errors_ = 0;
        }

        public static DailyAnalyticsSummary parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (DailyAnalyticsSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DailyAnalyticsSummary parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DailyAnalyticsSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DailyAnalyticsSummary parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DailyAnalyticsSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DailyAnalyticsSummary parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DailyAnalyticsSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DailyAnalyticsSummary parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DailyAnalyticsSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DailyAnalyticsSummary parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DailyAnalyticsSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DailyAnalyticsSummary parseFrom(InputStream input) throws IOException {
            return (DailyAnalyticsSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DailyAnalyticsSummary parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DailyAnalyticsSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DailyAnalyticsSummary parseDelimitedFrom(InputStream input) throws IOException {
            return (DailyAnalyticsSummary) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static DailyAnalyticsSummary parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DailyAnalyticsSummary) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DailyAnalyticsSummary parseFrom(CodedInputStream input) throws IOException {
            return (DailyAnalyticsSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DailyAnalyticsSummary parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DailyAnalyticsSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(DailyAnalyticsSummary prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<DailyAnalyticsSummary, Builder> implements DailyAnalyticsSummaryOrBuilder {
            /* synthetic */ Builder(C39311 x0) {
                this();
            }

            private Builder() {
                super(DailyAnalyticsSummary.DEFAULT_INSTANCE);
            }

            public long getStartOfDayMillis() {
                return ((DailyAnalyticsSummary) this.instance).getStartOfDayMillis();
            }

            public Builder setStartOfDayMillis(long value) {
                copyOnWrite();
                ((DailyAnalyticsSummary) this.instance).setStartOfDayMillis(value);
                return this;
            }

            public Builder clearStartOfDayMillis() {
                copyOnWrite();
                ((DailyAnalyticsSummary) this.instance).clearStartOfDayMillis();
                return this;
            }

            public int getImpressions() {
                return ((DailyAnalyticsSummary) this.instance).getImpressions();
            }

            public Builder setImpressions(int value) {
                copyOnWrite();
                ((DailyAnalyticsSummary) this.instance).setImpressions(value);
                return this;
            }

            public Builder clearImpressions() {
                copyOnWrite();
                ((DailyAnalyticsSummary) this.instance).clearImpressions();
                return this;
            }

            public int getClicks() {
                return ((DailyAnalyticsSummary) this.instance).getClicks();
            }

            public Builder setClicks(int value) {
                copyOnWrite();
                ((DailyAnalyticsSummary) this.instance).setClicks(value);
                return this;
            }

            public Builder clearClicks() {
                copyOnWrite();
                ((DailyAnalyticsSummary) this.instance).clearClicks();
                return this;
            }

            public int getErrors() {
                return ((DailyAnalyticsSummary) this.instance).getErrors();
            }

            public Builder setErrors(int value) {
                copyOnWrite();
                ((DailyAnalyticsSummary) this.instance).setErrors(value);
                return this;
            }

            public Builder clearErrors() {
                copyOnWrite();
                ((DailyAnalyticsSummary) this.instance).clearErrors();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39311.f1738xa1df5c61[method.ordinal()]) {
                case 1:
                    return new DailyAnalyticsSummary();
                case 2:
                    return new Builder((C39311) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0002\u0002\u0004\u0003\u0004\u0004\u0004", new Object[]{"startOfDayMillis_", "impressions_", "clicks_", "errors_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<DailyAnalyticsSummary> parser = PARSER;
                    if (parser == null) {
                        synchronized (DailyAnalyticsSummary.class) {
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
            DailyAnalyticsSummary defaultInstance = new DailyAnalyticsSummary();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(DailyAnalyticsSummary.class, defaultInstance);
        }

        public static DailyAnalyticsSummary getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DailyAnalyticsSummary> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class DailyConversionSummary extends GeneratedMessageLite<DailyConversionSummary, Builder> implements DailyConversionSummaryOrBuilder {
        public static final int CONVERSIONS_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final DailyConversionSummary DEFAULT_INSTANCE;
        private static volatile Parser<DailyConversionSummary> PARSER = null;
        public static final int START_OF_DAY_MILLIS_FIELD_NUMBER = 1;
        private int conversions_;
        private long startOfDayMillis_;

        private DailyConversionSummary() {
        }

        public long getStartOfDayMillis() {
            return this.startOfDayMillis_;
        }

        /* access modifiers changed from: private */
        public void setStartOfDayMillis(long value) {
            this.startOfDayMillis_ = value;
        }

        /* access modifiers changed from: private */
        public void clearStartOfDayMillis() {
            this.startOfDayMillis_ = 0;
        }

        public int getConversions() {
            return this.conversions_;
        }

        /* access modifiers changed from: private */
        public void setConversions(int value) {
            this.conversions_ = value;
        }

        /* access modifiers changed from: private */
        public void clearConversions() {
            this.conversions_ = 0;
        }

        public static DailyConversionSummary parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (DailyConversionSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DailyConversionSummary parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DailyConversionSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DailyConversionSummary parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DailyConversionSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DailyConversionSummary parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DailyConversionSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DailyConversionSummary parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DailyConversionSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DailyConversionSummary parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DailyConversionSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DailyConversionSummary parseFrom(InputStream input) throws IOException {
            return (DailyConversionSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DailyConversionSummary parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DailyConversionSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DailyConversionSummary parseDelimitedFrom(InputStream input) throws IOException {
            return (DailyConversionSummary) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static DailyConversionSummary parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DailyConversionSummary) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DailyConversionSummary parseFrom(CodedInputStream input) throws IOException {
            return (DailyConversionSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DailyConversionSummary parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DailyConversionSummary) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(DailyConversionSummary prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<DailyConversionSummary, Builder> implements DailyConversionSummaryOrBuilder {
            /* synthetic */ Builder(C39311 x0) {
                this();
            }

            private Builder() {
                super(DailyConversionSummary.DEFAULT_INSTANCE);
            }

            public long getStartOfDayMillis() {
                return ((DailyConversionSummary) this.instance).getStartOfDayMillis();
            }

            public Builder setStartOfDayMillis(long value) {
                copyOnWrite();
                ((DailyConversionSummary) this.instance).setStartOfDayMillis(value);
                return this;
            }

            public Builder clearStartOfDayMillis() {
                copyOnWrite();
                ((DailyConversionSummary) this.instance).clearStartOfDayMillis();
                return this;
            }

            public int getConversions() {
                return ((DailyConversionSummary) this.instance).getConversions();
            }

            public Builder setConversions(int value) {
                copyOnWrite();
                ((DailyConversionSummary) this.instance).setConversions(value);
                return this;
            }

            public Builder clearConversions() {
                copyOnWrite();
                ((DailyConversionSummary) this.instance).clearConversions();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39311.f1738xa1df5c61[method.ordinal()]) {
                case 1:
                    return new DailyConversionSummary();
                case 2:
                    return new Builder((C39311) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0002\u0002\u0004", new Object[]{"startOfDayMillis_", "conversions_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<DailyConversionSummary> parser = PARSER;
                    if (parser == null) {
                        synchronized (DailyConversionSummary.class) {
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
            DailyConversionSummary defaultInstance = new DailyConversionSummary();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(DailyConversionSummary.class, defaultInstance);
        }

        public static DailyConversionSummary getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DailyConversionSummary> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
