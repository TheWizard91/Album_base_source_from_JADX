package com.google.firebase.inappmessaging;

import com.google.protobuf.Internal;

public enum EventType implements Internal.EnumLite {
    UNKNOWN_EVENT_TYPE(0),
    IMPRESSION_EVENT_TYPE(1),
    CLICK_EVENT_TYPE(2);
    
    public static final int CLICK_EVENT_TYPE_VALUE = 2;
    public static final int IMPRESSION_EVENT_TYPE_VALUE = 1;
    public static final int UNKNOWN_EVENT_TYPE_VALUE = 0;
    private static final Internal.EnumLiteMap<EventType> internalValueMap = null;
    private final int value;

    static {
        internalValueMap = new Internal.EnumLiteMap<EventType>() {
            public EventType findValueByNumber(int number) {
                return EventType.forNumber(number);
            }
        };
    }

    public final int getNumber() {
        return this.value;
    }

    @Deprecated
    public static EventType valueOf(int value2) {
        return forNumber(value2);
    }

    public static EventType forNumber(int value2) {
        if (value2 == 0) {
            return UNKNOWN_EVENT_TYPE;
        }
        if (value2 == 1) {
            return IMPRESSION_EVENT_TYPE;
        }
        if (value2 != 2) {
            return null;
        }
        return CLICK_EVENT_TYPE;
    }

    public static Internal.EnumLiteMap<EventType> internalGetValueMap() {
        return internalValueMap;
    }

    public static Internal.EnumVerifier internalGetVerifier() {
        return EventTypeVerifier.INSTANCE;
    }

    private static final class EventTypeVerifier implements Internal.EnumVerifier {
        static final Internal.EnumVerifier INSTANCE = null;

        private EventTypeVerifier() {
        }

        static {
            INSTANCE = new EventTypeVerifier();
        }

        public boolean isInRange(int number) {
            return EventType.forNumber(number) != null;
        }
    }

    private EventType(int value2) {
        this.value = value2;
    }
}
