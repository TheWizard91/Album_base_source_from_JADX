package com.google.firebase.inappmessaging.internal;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.MapFieldLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Map;

public final class RateLimitProto {

    public interface CounterOrBuilder extends MessageLiteOrBuilder {
        long getStartTimeEpoch();

        long getValue();
    }

    public interface RateLimitOrBuilder extends MessageLiteOrBuilder {
        boolean containsLimits(String str);

        @Deprecated
        Map<String, Counter> getLimits();

        int getLimitsCount();

        Map<String, Counter> getLimitsMap();

        Counter getLimitsOrDefault(String str, Counter counter);

        Counter getLimitsOrThrow(String str);
    }

    private RateLimitProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static final class RateLimit extends GeneratedMessageLite<RateLimit, Builder> implements RateLimitOrBuilder {
        /* access modifiers changed from: private */
        public static final RateLimit DEFAULT_INSTANCE;
        public static final int LIMITS_FIELD_NUMBER = 1;
        private static volatile Parser<RateLimit> PARSER;
        private MapFieldLite<String, Counter> limits_ = MapFieldLite.emptyMapField();

        private RateLimit() {
        }

        private static final class LimitsDefaultEntryHolder {
            static final MapEntryLite<String, Counter> defaultEntry = MapEntryLite.newDefaultInstance(WireFormat.FieldType.STRING, "", WireFormat.FieldType.MESSAGE, Counter.getDefaultInstance());

            private LimitsDefaultEntryHolder() {
            }
        }

        private MapFieldLite<String, Counter> internalGetLimits() {
            return this.limits_;
        }

        private MapFieldLite<String, Counter> internalGetMutableLimits() {
            if (!this.limits_.isMutable()) {
                this.limits_ = this.limits_.mutableCopy();
            }
            return this.limits_;
        }

        public int getLimitsCount() {
            return internalGetLimits().size();
        }

        public boolean containsLimits(String key) {
            key.getClass();
            return internalGetLimits().containsKey(key);
        }

        @Deprecated
        public Map<String, Counter> getLimits() {
            return getLimitsMap();
        }

        public Map<String, Counter> getLimitsMap() {
            return Collections.unmodifiableMap(internalGetLimits());
        }

        public Counter getLimitsOrDefault(String key, Counter defaultValue) {
            key.getClass();
            Map<String, Counter> map = internalGetLimits();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        public Counter getLimitsOrThrow(String key) {
            key.getClass();
            Map<String, Counter> map = internalGetLimits();
            if (map.containsKey(key)) {
                return map.get(key);
            }
            throw new IllegalArgumentException();
        }

        /* access modifiers changed from: private */
        public Map<String, Counter> getMutableLimitsMap() {
            return internalGetMutableLimits();
        }

        public static RateLimit parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (RateLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static RateLimit parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RateLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static RateLimit parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (RateLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static RateLimit parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RateLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static RateLimit parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (RateLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static RateLimit parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RateLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static RateLimit parseFrom(InputStream input) throws IOException {
            return (RateLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static RateLimit parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RateLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static RateLimit parseDelimitedFrom(InputStream input) throws IOException {
            return (RateLimit) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static RateLimit parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RateLimit) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static RateLimit parseFrom(CodedInputStream input) throws IOException {
            return (RateLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static RateLimit parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RateLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(RateLimit prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<RateLimit, Builder> implements RateLimitOrBuilder {
            /* synthetic */ Builder(C39721 x0) {
                this();
            }

            private Builder() {
                super(RateLimit.DEFAULT_INSTANCE);
            }

            public int getLimitsCount() {
                return ((RateLimit) this.instance).getLimitsMap().size();
            }

            public boolean containsLimits(String key) {
                key.getClass();
                return ((RateLimit) this.instance).getLimitsMap().containsKey(key);
            }

            public Builder clearLimits() {
                copyOnWrite();
                ((RateLimit) this.instance).getMutableLimitsMap().clear();
                return this;
            }

            public Builder removeLimits(String key) {
                key.getClass();
                copyOnWrite();
                ((RateLimit) this.instance).getMutableLimitsMap().remove(key);
                return this;
            }

            @Deprecated
            public Map<String, Counter> getLimits() {
                return getLimitsMap();
            }

            public Map<String, Counter> getLimitsMap() {
                return Collections.unmodifiableMap(((RateLimit) this.instance).getLimitsMap());
            }

            public Counter getLimitsOrDefault(String key, Counter defaultValue) {
                key.getClass();
                Map<String, Counter> map = ((RateLimit) this.instance).getLimitsMap();
                return map.containsKey(key) ? map.get(key) : defaultValue;
            }

            public Counter getLimitsOrThrow(String key) {
                key.getClass();
                Map<String, Counter> map = ((RateLimit) this.instance).getLimitsMap();
                if (map.containsKey(key)) {
                    return map.get(key);
                }
                throw new IllegalArgumentException();
            }

            public Builder putLimits(String key, Counter value) {
                key.getClass();
                value.getClass();
                copyOnWrite();
                ((RateLimit) this.instance).getMutableLimitsMap().put(key, value);
                return this;
            }

            public Builder putAllLimits(Map<String, Counter> values) {
                copyOnWrite();
                ((RateLimit) this.instance).getMutableLimitsMap().putAll(values);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39721.f1746xa1df5c61[method.ordinal()]) {
                case 1:
                    return new RateLimit();
                case 2:
                    return new Builder((C39721) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u00012", new Object[]{"limits_", LimitsDefaultEntryHolder.defaultEntry});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<RateLimit> parser = PARSER;
                    if (parser == null) {
                        synchronized (RateLimit.class) {
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
            RateLimit defaultInstance = new RateLimit();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(RateLimit.class, defaultInstance);
        }

        public static RateLimit getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RateLimit> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.RateLimitProto$1 */
    static /* synthetic */ class C39721 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        static final /* synthetic */ int[] f1746xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f1746xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1746xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1746xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1746xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1746xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1746xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1746xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public static final class Counter extends GeneratedMessageLite<Counter, Builder> implements CounterOrBuilder {
        /* access modifiers changed from: private */
        public static final Counter DEFAULT_INSTANCE;
        private static volatile Parser<Counter> PARSER = null;
        public static final int START_TIME_EPOCH_FIELD_NUMBER = 2;
        public static final int VALUE_FIELD_NUMBER = 1;
        private long startTimeEpoch_;
        private long value_;

        private Counter() {
        }

        public long getValue() {
            return this.value_;
        }

        /* access modifiers changed from: private */
        public void setValue(long value) {
            this.value_ = value;
        }

        /* access modifiers changed from: private */
        public void clearValue() {
            this.value_ = 0;
        }

        public long getStartTimeEpoch() {
            return this.startTimeEpoch_;
        }

        /* access modifiers changed from: private */
        public void setStartTimeEpoch(long value) {
            this.startTimeEpoch_ = value;
        }

        /* access modifiers changed from: private */
        public void clearStartTimeEpoch() {
            this.startTimeEpoch_ = 0;
        }

        public static Counter parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Counter parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Counter parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Counter parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Counter parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Counter parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Counter parseFrom(InputStream input) throws IOException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Counter parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Counter parseDelimitedFrom(InputStream input) throws IOException {
            return (Counter) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Counter parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Counter) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Counter parseFrom(CodedInputStream input) throws IOException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Counter parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Counter prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Counter, Builder> implements CounterOrBuilder {
            /* synthetic */ Builder(C39721 x0) {
                this();
            }

            private Builder() {
                super(Counter.DEFAULT_INSTANCE);
            }

            public long getValue() {
                return ((Counter) this.instance).getValue();
            }

            public Builder setValue(long value) {
                copyOnWrite();
                ((Counter) this.instance).setValue(value);
                return this;
            }

            public Builder clearValue() {
                copyOnWrite();
                ((Counter) this.instance).clearValue();
                return this;
            }

            public long getStartTimeEpoch() {
                return ((Counter) this.instance).getStartTimeEpoch();
            }

            public Builder setStartTimeEpoch(long value) {
                copyOnWrite();
                ((Counter) this.instance).setStartTimeEpoch(value);
                return this;
            }

            public Builder clearStartTimeEpoch() {
                copyOnWrite();
                ((Counter) this.instance).clearStartTimeEpoch();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39721.f1746xa1df5c61[method.ordinal()]) {
                case 1:
                    return new Counter();
                case 2:
                    return new Builder((C39721) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0002\u0002\u0002", new Object[]{"value_", "startTimeEpoch_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<Counter> parser = PARSER;
                    if (parser == null) {
                        synchronized (Counter.class) {
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
            Counter defaultInstance = new Counter();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(Counter.class, defaultInstance);
        }

        public static Counter getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Counter> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
