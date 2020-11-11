package com.facebook.common.util;

import javax.annotation.Nullable;

public enum TriState {
    YES,
    NO,
    UNSET;

    public boolean isSet() {
        return this != UNSET;
    }

    public static TriState valueOf(boolean bool) {
        return bool ? YES : NO;
    }

    public static TriState valueOf(Boolean bool) {
        return bool != null ? valueOf(bool.booleanValue()) : UNSET;
    }

    /* renamed from: com.facebook.common.util.TriState$1 */
    static /* synthetic */ class C06811 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$common$util$TriState = null;

        static {
            int[] iArr = new int[TriState.values().length];
            $SwitchMap$com$facebook$common$util$TriState = iArr;
            try {
                iArr[TriState.YES.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$facebook$common$util$TriState[TriState.NO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$facebook$common$util$TriState[TriState.UNSET.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public boolean asBoolean() {
        int i = C06811.$SwitchMap$com$facebook$common$util$TriState[ordinal()];
        if (i == 1) {
            return true;
        }
        if (i == 2) {
            return false;
        }
        if (i != 3) {
            throw new IllegalStateException("Unrecognized TriState value: " + this);
        }
        throw new IllegalStateException("No boolean equivalent for UNSET");
    }

    public boolean asBoolean(boolean defaultValue) {
        int i = C06811.$SwitchMap$com$facebook$common$util$TriState[ordinal()];
        if (i == 1) {
            return true;
        }
        if (i == 2) {
            return false;
        }
        if (i == 3) {
            return defaultValue;
        }
        throw new IllegalStateException("Unrecognized TriState value: " + this);
    }

    @Nullable
    public Boolean asBooleanObject() {
        int i = C06811.$SwitchMap$com$facebook$common$util$TriState[ordinal()];
        if (i == 1) {
            return Boolean.TRUE;
        }
        if (i == 2) {
            return Boolean.FALSE;
        }
        if (i == 3) {
            return null;
        }
        throw new IllegalStateException("Unrecognized TriState value: " + this);
    }

    public int getDbValue() {
        int i = C06811.$SwitchMap$com$facebook$common$util$TriState[ordinal()];
        if (i == 1) {
            return 1;
        }
        if (i != 2) {
            return 3;
        }
        return 2;
    }

    public static TriState fromDbValue(int value) {
        if (value == 1) {
            return YES;
        }
        if (value != 2) {
            return UNSET;
        }
        return NO;
    }
}
