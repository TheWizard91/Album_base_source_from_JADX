package com.google.firebase.inappmessaging;

import com.google.protobuf.Internal;

public enum FetchErrorReason implements Internal.EnumLite {
    UNSPECIFIED_FETCH_ERROR(0),
    SERVER_ERROR(1),
    CLIENT_ERROR(2),
    NETWORK_ERROR(3);
    
    public static final int CLIENT_ERROR_VALUE = 2;
    public static final int NETWORK_ERROR_VALUE = 3;
    public static final int SERVER_ERROR_VALUE = 1;
    public static final int UNSPECIFIED_FETCH_ERROR_VALUE = 0;
    private static final Internal.EnumLiteMap<FetchErrorReason> internalValueMap = null;
    private final int value;

    static {
        internalValueMap = new Internal.EnumLiteMap<FetchErrorReason>() {
            public FetchErrorReason findValueByNumber(int number) {
                return FetchErrorReason.forNumber(number);
            }
        };
    }

    public final int getNumber() {
        return this.value;
    }

    @Deprecated
    public static FetchErrorReason valueOf(int value2) {
        return forNumber(value2);
    }

    public static FetchErrorReason forNumber(int value2) {
        if (value2 == 0) {
            return UNSPECIFIED_FETCH_ERROR;
        }
        if (value2 == 1) {
            return SERVER_ERROR;
        }
        if (value2 == 2) {
            return CLIENT_ERROR;
        }
        if (value2 != 3) {
            return null;
        }
        return NETWORK_ERROR;
    }

    public static Internal.EnumLiteMap<FetchErrorReason> internalGetValueMap() {
        return internalValueMap;
    }

    public static Internal.EnumVerifier internalGetVerifier() {
        return FetchErrorReasonVerifier.INSTANCE;
    }

    private static final class FetchErrorReasonVerifier implements Internal.EnumVerifier {
        static final Internal.EnumVerifier INSTANCE = null;

        private FetchErrorReasonVerifier() {
        }

        static {
            INSTANCE = new FetchErrorReasonVerifier();
        }

        public boolean isInRange(int number) {
            return FetchErrorReason.forNumber(number) != null;
        }
    }

    private FetchErrorReason(int value2) {
        this.value = value2;
    }
}
