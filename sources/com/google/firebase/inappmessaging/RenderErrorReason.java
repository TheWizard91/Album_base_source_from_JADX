package com.google.firebase.inappmessaging;

import com.google.protobuf.Internal;

public enum RenderErrorReason implements Internal.EnumLite {
    UNSPECIFIED_RENDER_ERROR(0),
    IMAGE_FETCH_ERROR(1),
    IMAGE_DISPLAY_ERROR(2),
    IMAGE_UNSUPPORTED_FORMAT(3);
    
    public static final int IMAGE_DISPLAY_ERROR_VALUE = 2;
    public static final int IMAGE_FETCH_ERROR_VALUE = 1;
    public static final int IMAGE_UNSUPPORTED_FORMAT_VALUE = 3;
    public static final int UNSPECIFIED_RENDER_ERROR_VALUE = 0;
    private static final Internal.EnumLiteMap<RenderErrorReason> internalValueMap = null;
    private final int value;

    static {
        internalValueMap = new Internal.EnumLiteMap<RenderErrorReason>() {
            public RenderErrorReason findValueByNumber(int number) {
                return RenderErrorReason.forNumber(number);
            }
        };
    }

    public final int getNumber() {
        return this.value;
    }

    @Deprecated
    public static RenderErrorReason valueOf(int value2) {
        return forNumber(value2);
    }

    public static RenderErrorReason forNumber(int value2) {
        if (value2 == 0) {
            return UNSPECIFIED_RENDER_ERROR;
        }
        if (value2 == 1) {
            return IMAGE_FETCH_ERROR;
        }
        if (value2 == 2) {
            return IMAGE_DISPLAY_ERROR;
        }
        if (value2 != 3) {
            return null;
        }
        return IMAGE_UNSUPPORTED_FORMAT;
    }

    public static Internal.EnumLiteMap<RenderErrorReason> internalGetValueMap() {
        return internalValueMap;
    }

    public static Internal.EnumVerifier internalGetVerifier() {
        return RenderErrorReasonVerifier.INSTANCE;
    }

    private static final class RenderErrorReasonVerifier implements Internal.EnumVerifier {
        static final Internal.EnumVerifier INSTANCE = null;

        private RenderErrorReasonVerifier() {
        }

        static {
            INSTANCE = new RenderErrorReasonVerifier();
        }

        public boolean isInRange(int number) {
            return RenderErrorReason.forNumber(number) != null;
        }
    }

    private RenderErrorReason(int value2) {
        this.value = value2;
    }
}
