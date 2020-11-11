package developers.mobile.abt;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class FirebaseAbt {

    public interface ExperimentLiteOrBuilder extends MessageLiteOrBuilder {
        String getExperimentId();

        ByteString getExperimentIdBytes();
    }

    public interface ExperimentPayloadOrBuilder extends MessageLiteOrBuilder {
        String getActivateEventToLog();

        ByteString getActivateEventToLogBytes();

        String getClearEventToLog();

        ByteString getClearEventToLogBytes();

        String getExperimentId();

        ByteString getExperimentIdBytes();

        long getExperimentStartTimeMillis();

        ExperimentLite getOngoingExperiments(int i);

        int getOngoingExperimentsCount();

        List<ExperimentLite> getOngoingExperimentsList();

        ExperimentPayload.ExperimentOverflowPolicy getOverflowPolicy();

        int getOverflowPolicyValue();

        String getSetEventToLog();

        ByteString getSetEventToLogBytes();

        long getTimeToLiveMillis();

        String getTimeoutEventToLog();

        ByteString getTimeoutEventToLogBytes();

        String getTriggerEvent();

        ByteString getTriggerEventBytes();

        long getTriggerTimeoutMillis();

        String getTtlExpiryEventToLog();

        ByteString getTtlExpiryEventToLogBytes();

        String getVariantId();

        ByteString getVariantIdBytes();
    }

    private FirebaseAbt() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static final class ExperimentLite extends GeneratedMessageLite<ExperimentLite, Builder> implements ExperimentLiteOrBuilder {
        /* access modifiers changed from: private */
        public static final ExperimentLite DEFAULT_INSTANCE;
        public static final int EXPERIMENT_ID_FIELD_NUMBER = 1;
        private static volatile Parser<ExperimentLite> PARSER;
        private String experimentId_ = "";

        private ExperimentLite() {
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

        public static ExperimentLite parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ExperimentLite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExperimentLite parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExperimentLite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExperimentLite parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ExperimentLite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExperimentLite parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExperimentLite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExperimentLite parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ExperimentLite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExperimentLite parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExperimentLite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExperimentLite parseFrom(InputStream input) throws IOException {
            return (ExperimentLite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExperimentLite parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExperimentLite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExperimentLite parseDelimitedFrom(InputStream input) throws IOException {
            return (ExperimentLite) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ExperimentLite parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExperimentLite) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExperimentLite parseFrom(CodedInputStream input) throws IOException {
            return (ExperimentLite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExperimentLite parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExperimentLite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ExperimentLite prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ExperimentLite, Builder> implements ExperimentLiteOrBuilder {
            /* synthetic */ Builder(C15371 x0) {
                this();
            }

            private Builder() {
                super(ExperimentLite.DEFAULT_INSTANCE);
            }

            public String getExperimentId() {
                return ((ExperimentLite) this.instance).getExperimentId();
            }

            public ByteString getExperimentIdBytes() {
                return ((ExperimentLite) this.instance).getExperimentIdBytes();
            }

            public Builder setExperimentId(String value) {
                copyOnWrite();
                ((ExperimentLite) this.instance).setExperimentId(value);
                return this;
            }

            public Builder clearExperimentId() {
                copyOnWrite();
                ((ExperimentLite) this.instance).clearExperimentId();
                return this;
            }

            public Builder setExperimentIdBytes(ByteString value) {
                copyOnWrite();
                ((ExperimentLite) this.instance).setExperimentIdBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C15371.f191xa1df5c61[method.ordinal()]) {
                case 1:
                    return new ExperimentLite();
                case 2:
                    return new Builder((C15371) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ", new Object[]{"experimentId_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ExperimentLite> parser = PARSER;
                    if (parser == null) {
                        synchronized (ExperimentLite.class) {
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
            ExperimentLite defaultInstance = new ExperimentLite();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(ExperimentLite.class, defaultInstance);
        }

        public static ExperimentLite getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ExperimentLite> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* renamed from: developers.mobile.abt.FirebaseAbt$1 */
    static /* synthetic */ class C15371 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        static final /* synthetic */ int[] f191xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f191xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f191xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f191xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f191xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f191xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f191xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f191xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public static final class ExperimentPayload extends GeneratedMessageLite<ExperimentPayload, Builder> implements ExperimentPayloadOrBuilder {
        public static final int ACTIVATE_EVENT_TO_LOG_FIELD_NUMBER = 8;
        public static final int CLEAR_EVENT_TO_LOG_FIELD_NUMBER = 9;
        /* access modifiers changed from: private */
        public static final ExperimentPayload DEFAULT_INSTANCE;
        public static final int EXPERIMENT_ID_FIELD_NUMBER = 1;
        public static final int EXPERIMENT_START_TIME_MILLIS_FIELD_NUMBER = 3;
        public static final int ONGOING_EXPERIMENTS_FIELD_NUMBER = 13;
        public static final int OVERFLOW_POLICY_FIELD_NUMBER = 12;
        private static volatile Parser<ExperimentPayload> PARSER = null;
        public static final int SET_EVENT_TO_LOG_FIELD_NUMBER = 7;
        public static final int TIMEOUT_EVENT_TO_LOG_FIELD_NUMBER = 10;
        public static final int TIME_TO_LIVE_MILLIS_FIELD_NUMBER = 6;
        public static final int TRIGGER_EVENT_FIELD_NUMBER = 4;
        public static final int TRIGGER_TIMEOUT_MILLIS_FIELD_NUMBER = 5;
        public static final int TTL_EXPIRY_EVENT_TO_LOG_FIELD_NUMBER = 11;
        public static final int VARIANT_ID_FIELD_NUMBER = 2;
        private String activateEventToLog_ = "";
        private String clearEventToLog_ = "";
        private String experimentId_ = "";
        private long experimentStartTimeMillis_;
        private Internal.ProtobufList<ExperimentLite> ongoingExperiments_ = emptyProtobufList();
        private int overflowPolicy_;
        private String setEventToLog_ = "";
        private long timeToLiveMillis_;
        private String timeoutEventToLog_ = "";
        private String triggerEvent_ = "";
        private long triggerTimeoutMillis_;
        private String ttlExpiryEventToLog_ = "";
        private String variantId_ = "";

        private ExperimentPayload() {
        }

        public enum ExperimentOverflowPolicy implements Internal.EnumLite {
            POLICY_UNSPECIFIED(0),
            DISCARD_OLDEST(1),
            IGNORE_NEWEST(2),
            UNRECOGNIZED(-1);
            
            public static final int DISCARD_OLDEST_VALUE = 1;
            public static final int IGNORE_NEWEST_VALUE = 2;
            public static final int POLICY_UNSPECIFIED_VALUE = 0;
            private static final Internal.EnumLiteMap<ExperimentOverflowPolicy> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<ExperimentOverflowPolicy>() {
                    public ExperimentOverflowPolicy findValueByNumber(int number) {
                        return ExperimentOverflowPolicy.forNumber(number);
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
            public static ExperimentOverflowPolicy valueOf(int value2) {
                return forNumber(value2);
            }

            public static ExperimentOverflowPolicy forNumber(int value2) {
                if (value2 == 0) {
                    return POLICY_UNSPECIFIED;
                }
                if (value2 == 1) {
                    return DISCARD_OLDEST;
                }
                if (value2 != 2) {
                    return null;
                }
                return IGNORE_NEWEST;
            }

            public static Internal.EnumLiteMap<ExperimentOverflowPolicy> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return ExperimentOverflowPolicyVerifier.INSTANCE;
            }

            private static final class ExperimentOverflowPolicyVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private ExperimentOverflowPolicyVerifier() {
                }

                static {
                    INSTANCE = new ExperimentOverflowPolicyVerifier();
                }

                public boolean isInRange(int number) {
                    return ExperimentOverflowPolicy.forNumber(number) != null;
                }
            }

            private ExperimentOverflowPolicy(int value2) {
                this.value = value2;
            }
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

        public String getVariantId() {
            return this.variantId_;
        }

        public ByteString getVariantIdBytes() {
            return ByteString.copyFromUtf8(this.variantId_);
        }

        /* access modifiers changed from: private */
        public void setVariantId(String value) {
            value.getClass();
            this.variantId_ = value;
        }

        /* access modifiers changed from: private */
        public void clearVariantId() {
            this.variantId_ = getDefaultInstance().getVariantId();
        }

        /* access modifiers changed from: private */
        public void setVariantIdBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.variantId_ = value.toStringUtf8();
        }

        public long getExperimentStartTimeMillis() {
            return this.experimentStartTimeMillis_;
        }

        /* access modifiers changed from: private */
        public void setExperimentStartTimeMillis(long value) {
            this.experimentStartTimeMillis_ = value;
        }

        /* access modifiers changed from: private */
        public void clearExperimentStartTimeMillis() {
            this.experimentStartTimeMillis_ = 0;
        }

        public String getTriggerEvent() {
            return this.triggerEvent_;
        }

        public ByteString getTriggerEventBytes() {
            return ByteString.copyFromUtf8(this.triggerEvent_);
        }

        /* access modifiers changed from: private */
        public void setTriggerEvent(String value) {
            value.getClass();
            this.triggerEvent_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTriggerEvent() {
            this.triggerEvent_ = getDefaultInstance().getTriggerEvent();
        }

        /* access modifiers changed from: private */
        public void setTriggerEventBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.triggerEvent_ = value.toStringUtf8();
        }

        public long getTriggerTimeoutMillis() {
            return this.triggerTimeoutMillis_;
        }

        /* access modifiers changed from: private */
        public void setTriggerTimeoutMillis(long value) {
            this.triggerTimeoutMillis_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTriggerTimeoutMillis() {
            this.triggerTimeoutMillis_ = 0;
        }

        public long getTimeToLiveMillis() {
            return this.timeToLiveMillis_;
        }

        /* access modifiers changed from: private */
        public void setTimeToLiveMillis(long value) {
            this.timeToLiveMillis_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTimeToLiveMillis() {
            this.timeToLiveMillis_ = 0;
        }

        public String getSetEventToLog() {
            return this.setEventToLog_;
        }

        public ByteString getSetEventToLogBytes() {
            return ByteString.copyFromUtf8(this.setEventToLog_);
        }

        /* access modifiers changed from: private */
        public void setSetEventToLog(String value) {
            value.getClass();
            this.setEventToLog_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSetEventToLog() {
            this.setEventToLog_ = getDefaultInstance().getSetEventToLog();
        }

        /* access modifiers changed from: private */
        public void setSetEventToLogBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.setEventToLog_ = value.toStringUtf8();
        }

        public String getActivateEventToLog() {
            return this.activateEventToLog_;
        }

        public ByteString getActivateEventToLogBytes() {
            return ByteString.copyFromUtf8(this.activateEventToLog_);
        }

        /* access modifiers changed from: private */
        public void setActivateEventToLog(String value) {
            value.getClass();
            this.activateEventToLog_ = value;
        }

        /* access modifiers changed from: private */
        public void clearActivateEventToLog() {
            this.activateEventToLog_ = getDefaultInstance().getActivateEventToLog();
        }

        /* access modifiers changed from: private */
        public void setActivateEventToLogBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.activateEventToLog_ = value.toStringUtf8();
        }

        public String getClearEventToLog() {
            return this.clearEventToLog_;
        }

        public ByteString getClearEventToLogBytes() {
            return ByteString.copyFromUtf8(this.clearEventToLog_);
        }

        /* access modifiers changed from: private */
        public void setClearEventToLog(String value) {
            value.getClass();
            this.clearEventToLog_ = value;
        }

        /* access modifiers changed from: private */
        public void clearClearEventToLog() {
            this.clearEventToLog_ = getDefaultInstance().getClearEventToLog();
        }

        /* access modifiers changed from: private */
        public void setClearEventToLogBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.clearEventToLog_ = value.toStringUtf8();
        }

        public String getTimeoutEventToLog() {
            return this.timeoutEventToLog_;
        }

        public ByteString getTimeoutEventToLogBytes() {
            return ByteString.copyFromUtf8(this.timeoutEventToLog_);
        }

        /* access modifiers changed from: private */
        public void setTimeoutEventToLog(String value) {
            value.getClass();
            this.timeoutEventToLog_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTimeoutEventToLog() {
            this.timeoutEventToLog_ = getDefaultInstance().getTimeoutEventToLog();
        }

        /* access modifiers changed from: private */
        public void setTimeoutEventToLogBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.timeoutEventToLog_ = value.toStringUtf8();
        }

        public String getTtlExpiryEventToLog() {
            return this.ttlExpiryEventToLog_;
        }

        public ByteString getTtlExpiryEventToLogBytes() {
            return ByteString.copyFromUtf8(this.ttlExpiryEventToLog_);
        }

        /* access modifiers changed from: private */
        public void setTtlExpiryEventToLog(String value) {
            value.getClass();
            this.ttlExpiryEventToLog_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTtlExpiryEventToLog() {
            this.ttlExpiryEventToLog_ = getDefaultInstance().getTtlExpiryEventToLog();
        }

        /* access modifiers changed from: private */
        public void setTtlExpiryEventToLogBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.ttlExpiryEventToLog_ = value.toStringUtf8();
        }

        public int getOverflowPolicyValue() {
            return this.overflowPolicy_;
        }

        public ExperimentOverflowPolicy getOverflowPolicy() {
            ExperimentOverflowPolicy result = ExperimentOverflowPolicy.forNumber(this.overflowPolicy_);
            return result == null ? ExperimentOverflowPolicy.UNRECOGNIZED : result;
        }

        /* access modifiers changed from: private */
        public void setOverflowPolicyValue(int value) {
            this.overflowPolicy_ = value;
        }

        /* access modifiers changed from: private */
        public void setOverflowPolicy(ExperimentOverflowPolicy value) {
            this.overflowPolicy_ = value.getNumber();
        }

        /* access modifiers changed from: private */
        public void clearOverflowPolicy() {
            this.overflowPolicy_ = 0;
        }

        public List<ExperimentLite> getOngoingExperimentsList() {
            return this.ongoingExperiments_;
        }

        public List<? extends ExperimentLiteOrBuilder> getOngoingExperimentsOrBuilderList() {
            return this.ongoingExperiments_;
        }

        public int getOngoingExperimentsCount() {
            return this.ongoingExperiments_.size();
        }

        public ExperimentLite getOngoingExperiments(int index) {
            return (ExperimentLite) this.ongoingExperiments_.get(index);
        }

        public ExperimentLiteOrBuilder getOngoingExperimentsOrBuilder(int index) {
            return (ExperimentLiteOrBuilder) this.ongoingExperiments_.get(index);
        }

        private void ensureOngoingExperimentsIsMutable() {
            if (!this.ongoingExperiments_.isModifiable()) {
                this.ongoingExperiments_ = GeneratedMessageLite.mutableCopy(this.ongoingExperiments_);
            }
        }

        /* access modifiers changed from: private */
        public void setOngoingExperiments(int index, ExperimentLite value) {
            value.getClass();
            ensureOngoingExperimentsIsMutable();
            this.ongoingExperiments_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addOngoingExperiments(ExperimentLite value) {
            value.getClass();
            ensureOngoingExperimentsIsMutable();
            this.ongoingExperiments_.add(value);
        }

        /* access modifiers changed from: private */
        public void addOngoingExperiments(int index, ExperimentLite value) {
            value.getClass();
            ensureOngoingExperimentsIsMutable();
            this.ongoingExperiments_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllOngoingExperiments(Iterable<? extends ExperimentLite> values) {
            ensureOngoingExperimentsIsMutable();
            AbstractMessageLite.addAll(values, this.ongoingExperiments_);
        }

        /* access modifiers changed from: private */
        public void clearOngoingExperiments() {
            this.ongoingExperiments_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeOngoingExperiments(int index) {
            ensureOngoingExperimentsIsMutable();
            this.ongoingExperiments_.remove(index);
        }

        public static ExperimentPayload parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ExperimentPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExperimentPayload parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExperimentPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExperimentPayload parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ExperimentPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExperimentPayload parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExperimentPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExperimentPayload parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ExperimentPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExperimentPayload parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExperimentPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExperimentPayload parseFrom(InputStream input) throws IOException {
            return (ExperimentPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExperimentPayload parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExperimentPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExperimentPayload parseDelimitedFrom(InputStream input) throws IOException {
            return (ExperimentPayload) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ExperimentPayload parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExperimentPayload) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExperimentPayload parseFrom(CodedInputStream input) throws IOException {
            return (ExperimentPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExperimentPayload parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExperimentPayload) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ExperimentPayload prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ExperimentPayload, Builder> implements ExperimentPayloadOrBuilder {
            /* synthetic */ Builder(C15371 x0) {
                this();
            }

            private Builder() {
                super(ExperimentPayload.DEFAULT_INSTANCE);
            }

            public String getExperimentId() {
                return ((ExperimentPayload) this.instance).getExperimentId();
            }

            public ByteString getExperimentIdBytes() {
                return ((ExperimentPayload) this.instance).getExperimentIdBytes();
            }

            public Builder setExperimentId(String value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setExperimentId(value);
                return this;
            }

            public Builder clearExperimentId() {
                copyOnWrite();
                ((ExperimentPayload) this.instance).clearExperimentId();
                return this;
            }

            public Builder setExperimentIdBytes(ByteString value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setExperimentIdBytes(value);
                return this;
            }

            public String getVariantId() {
                return ((ExperimentPayload) this.instance).getVariantId();
            }

            public ByteString getVariantIdBytes() {
                return ((ExperimentPayload) this.instance).getVariantIdBytes();
            }

            public Builder setVariantId(String value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setVariantId(value);
                return this;
            }

            public Builder clearVariantId() {
                copyOnWrite();
                ((ExperimentPayload) this.instance).clearVariantId();
                return this;
            }

            public Builder setVariantIdBytes(ByteString value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setVariantIdBytes(value);
                return this;
            }

            public long getExperimentStartTimeMillis() {
                return ((ExperimentPayload) this.instance).getExperimentStartTimeMillis();
            }

            public Builder setExperimentStartTimeMillis(long value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setExperimentStartTimeMillis(value);
                return this;
            }

            public Builder clearExperimentStartTimeMillis() {
                copyOnWrite();
                ((ExperimentPayload) this.instance).clearExperimentStartTimeMillis();
                return this;
            }

            public String getTriggerEvent() {
                return ((ExperimentPayload) this.instance).getTriggerEvent();
            }

            public ByteString getTriggerEventBytes() {
                return ((ExperimentPayload) this.instance).getTriggerEventBytes();
            }

            public Builder setTriggerEvent(String value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setTriggerEvent(value);
                return this;
            }

            public Builder clearTriggerEvent() {
                copyOnWrite();
                ((ExperimentPayload) this.instance).clearTriggerEvent();
                return this;
            }

            public Builder setTriggerEventBytes(ByteString value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setTriggerEventBytes(value);
                return this;
            }

            public long getTriggerTimeoutMillis() {
                return ((ExperimentPayload) this.instance).getTriggerTimeoutMillis();
            }

            public Builder setTriggerTimeoutMillis(long value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setTriggerTimeoutMillis(value);
                return this;
            }

            public Builder clearTriggerTimeoutMillis() {
                copyOnWrite();
                ((ExperimentPayload) this.instance).clearTriggerTimeoutMillis();
                return this;
            }

            public long getTimeToLiveMillis() {
                return ((ExperimentPayload) this.instance).getTimeToLiveMillis();
            }

            public Builder setTimeToLiveMillis(long value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setTimeToLiveMillis(value);
                return this;
            }

            public Builder clearTimeToLiveMillis() {
                copyOnWrite();
                ((ExperimentPayload) this.instance).clearTimeToLiveMillis();
                return this;
            }

            public String getSetEventToLog() {
                return ((ExperimentPayload) this.instance).getSetEventToLog();
            }

            public ByteString getSetEventToLogBytes() {
                return ((ExperimentPayload) this.instance).getSetEventToLogBytes();
            }

            public Builder setSetEventToLog(String value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setSetEventToLog(value);
                return this;
            }

            public Builder clearSetEventToLog() {
                copyOnWrite();
                ((ExperimentPayload) this.instance).clearSetEventToLog();
                return this;
            }

            public Builder setSetEventToLogBytes(ByteString value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setSetEventToLogBytes(value);
                return this;
            }

            public String getActivateEventToLog() {
                return ((ExperimentPayload) this.instance).getActivateEventToLog();
            }

            public ByteString getActivateEventToLogBytes() {
                return ((ExperimentPayload) this.instance).getActivateEventToLogBytes();
            }

            public Builder setActivateEventToLog(String value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setActivateEventToLog(value);
                return this;
            }

            public Builder clearActivateEventToLog() {
                copyOnWrite();
                ((ExperimentPayload) this.instance).clearActivateEventToLog();
                return this;
            }

            public Builder setActivateEventToLogBytes(ByteString value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setActivateEventToLogBytes(value);
                return this;
            }

            public String getClearEventToLog() {
                return ((ExperimentPayload) this.instance).getClearEventToLog();
            }

            public ByteString getClearEventToLogBytes() {
                return ((ExperimentPayload) this.instance).getClearEventToLogBytes();
            }

            public Builder setClearEventToLog(String value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setClearEventToLog(value);
                return this;
            }

            public Builder clearClearEventToLog() {
                copyOnWrite();
                ((ExperimentPayload) this.instance).clearClearEventToLog();
                return this;
            }

            public Builder setClearEventToLogBytes(ByteString value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setClearEventToLogBytes(value);
                return this;
            }

            public String getTimeoutEventToLog() {
                return ((ExperimentPayload) this.instance).getTimeoutEventToLog();
            }

            public ByteString getTimeoutEventToLogBytes() {
                return ((ExperimentPayload) this.instance).getTimeoutEventToLogBytes();
            }

            public Builder setTimeoutEventToLog(String value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setTimeoutEventToLog(value);
                return this;
            }

            public Builder clearTimeoutEventToLog() {
                copyOnWrite();
                ((ExperimentPayload) this.instance).clearTimeoutEventToLog();
                return this;
            }

            public Builder setTimeoutEventToLogBytes(ByteString value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setTimeoutEventToLogBytes(value);
                return this;
            }

            public String getTtlExpiryEventToLog() {
                return ((ExperimentPayload) this.instance).getTtlExpiryEventToLog();
            }

            public ByteString getTtlExpiryEventToLogBytes() {
                return ((ExperimentPayload) this.instance).getTtlExpiryEventToLogBytes();
            }

            public Builder setTtlExpiryEventToLog(String value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setTtlExpiryEventToLog(value);
                return this;
            }

            public Builder clearTtlExpiryEventToLog() {
                copyOnWrite();
                ((ExperimentPayload) this.instance).clearTtlExpiryEventToLog();
                return this;
            }

            public Builder setTtlExpiryEventToLogBytes(ByteString value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setTtlExpiryEventToLogBytes(value);
                return this;
            }

            public int getOverflowPolicyValue() {
                return ((ExperimentPayload) this.instance).getOverflowPolicyValue();
            }

            public Builder setOverflowPolicyValue(int value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setOverflowPolicyValue(value);
                return this;
            }

            public ExperimentOverflowPolicy getOverflowPolicy() {
                return ((ExperimentPayload) this.instance).getOverflowPolicy();
            }

            public Builder setOverflowPolicy(ExperimentOverflowPolicy value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setOverflowPolicy(value);
                return this;
            }

            public Builder clearOverflowPolicy() {
                copyOnWrite();
                ((ExperimentPayload) this.instance).clearOverflowPolicy();
                return this;
            }

            public List<ExperimentLite> getOngoingExperimentsList() {
                return Collections.unmodifiableList(((ExperimentPayload) this.instance).getOngoingExperimentsList());
            }

            public int getOngoingExperimentsCount() {
                return ((ExperimentPayload) this.instance).getOngoingExperimentsCount();
            }

            public ExperimentLite getOngoingExperiments(int index) {
                return ((ExperimentPayload) this.instance).getOngoingExperiments(index);
            }

            public Builder setOngoingExperiments(int index, ExperimentLite value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setOngoingExperiments(index, value);
                return this;
            }

            public Builder setOngoingExperiments(int index, ExperimentLite.Builder builderForValue) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).setOngoingExperiments(index, (ExperimentLite) builderForValue.build());
                return this;
            }

            public Builder addOngoingExperiments(ExperimentLite value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).addOngoingExperiments(value);
                return this;
            }

            public Builder addOngoingExperiments(int index, ExperimentLite value) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).addOngoingExperiments(index, value);
                return this;
            }

            public Builder addOngoingExperiments(ExperimentLite.Builder builderForValue) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).addOngoingExperiments((ExperimentLite) builderForValue.build());
                return this;
            }

            public Builder addOngoingExperiments(int index, ExperimentLite.Builder builderForValue) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).addOngoingExperiments(index, (ExperimentLite) builderForValue.build());
                return this;
            }

            public Builder addAllOngoingExperiments(Iterable<? extends ExperimentLite> values) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).addAllOngoingExperiments(values);
                return this;
            }

            public Builder clearOngoingExperiments() {
                copyOnWrite();
                ((ExperimentPayload) this.instance).clearOngoingExperiments();
                return this;
            }

            public Builder removeOngoingExperiments(int index) {
                copyOnWrite();
                ((ExperimentPayload) this.instance).removeOngoingExperiments(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C15371.f191xa1df5c61[method.ordinal()]) {
                case 1:
                    return new ExperimentPayload();
                case 2:
                    return new Builder((C15371) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\r\u0000\u0000\u0001\r\r\u0000\u0001\u0000\u0001Ȉ\u0002Ȉ\u0003\u0002\u0004Ȉ\u0005\u0002\u0006\u0002\u0007Ȉ\bȈ\tȈ\nȈ\u000bȈ\f\f\r\u001b", new Object[]{"experimentId_", "variantId_", "experimentStartTimeMillis_", "triggerEvent_", "triggerTimeoutMillis_", "timeToLiveMillis_", "setEventToLog_", "activateEventToLog_", "clearEventToLog_", "timeoutEventToLog_", "ttlExpiryEventToLog_", "overflowPolicy_", "ongoingExperiments_", ExperimentLite.class});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ExperimentPayload> parser = PARSER;
                    if (parser == null) {
                        synchronized (ExperimentPayload.class) {
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
            ExperimentPayload defaultInstance = new ExperimentPayload();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(ExperimentPayload.class, defaultInstance);
        }

        public static ExperimentPayload getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ExperimentPayload> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
