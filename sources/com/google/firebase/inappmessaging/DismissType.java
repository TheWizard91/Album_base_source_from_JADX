package com.google.firebase.inappmessaging;

import com.google.protobuf.Internal;

public enum DismissType implements Internal.EnumLite {
    UNKNOWN_DISMISS_TYPE(0),
    AUTO(1),
    CLICK(2),
    SWIPE(3);
    
    public static final int AUTO_VALUE = 1;
    public static final int CLICK_VALUE = 2;
    public static final int SWIPE_VALUE = 3;
    public static final int UNKNOWN_DISMISS_TYPE_VALUE = 0;
    private static final Internal.EnumLiteMap<DismissType> internalValueMap = null;
    private final int value;

    static {
        internalValueMap = new Internal.EnumLiteMap<DismissType>() {
            public DismissType findValueByNumber(int number) {
                return DismissType.forNumber(number);
            }
        };
    }

    public final int getNumber() {
        return this.value;
    }

    @Deprecated
    public static DismissType valueOf(int value2) {
        return forNumber(value2);
    }

    public static DismissType forNumber(int value2) {
        if (value2 == 0) {
            return UNKNOWN_DISMISS_TYPE;
        }
        if (value2 == 1) {
            return AUTO;
        }
        if (value2 == 2) {
            return CLICK;
        }
        if (value2 != 3) {
            return null;
        }
        return SWIPE;
    }

    public static Internal.EnumLiteMap<DismissType> internalGetValueMap() {
        return internalValueMap;
    }

    public static Internal.EnumVerifier internalGetVerifier() {
        return DismissTypeVerifier.INSTANCE;
    }

    private static final class DismissTypeVerifier implements Internal.EnumVerifier {
        static final Internal.EnumVerifier INSTANCE = null;

        private DismissTypeVerifier() {
        }

        static {
            INSTANCE = new DismissTypeVerifier();
        }

        public boolean isInRange(int number) {
            return DismissType.forNumber(number) != null;
        }
    }

    private DismissType(int value2) {
        this.value = value2;
    }
}
