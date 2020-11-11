package com.fasterxml.jackson.core.p007io;

/* renamed from: com.fasterxml.jackson.core.io.NumberInput */
public final class NumberInput {
    static final long L_BILLION = 1000000000;
    static final String MAX_LONG_STR = String.valueOf(Long.MAX_VALUE);
    static final String MIN_LONG_STR_NO_SIGN = String.valueOf(Long.MIN_VALUE).substring(1);
    public static final String NASTY_SMALL_DOUBLE = "2.2250738585072012e-308";

    public static int parseInt(char[] cArr, int i, int i2) {
        int i3 = cArr[i] - '0';
        int i4 = i2 + i;
        int i5 = i + 1;
        if (i5 >= i4) {
            return i3;
        }
        int i6 = (i3 * 10) + (cArr[i5] - '0');
        int i7 = i5 + 1;
        if (i7 >= i4) {
            return i6;
        }
        int i8 = (i6 * 10) + (cArr[i7] - '0');
        int i9 = i7 + 1;
        if (i9 >= i4) {
            return i8;
        }
        int i10 = (i8 * 10) + (cArr[i9] - '0');
        int i11 = i9 + 1;
        if (i11 >= i4) {
            return i10;
        }
        int i12 = (i10 * 10) + (cArr[i11] - '0');
        int i13 = i11 + 1;
        if (i13 >= i4) {
            return i12;
        }
        int i14 = (i12 * 10) + (cArr[i13] - '0');
        int i15 = i13 + 1;
        if (i15 >= i4) {
            return i14;
        }
        int i16 = (i14 * 10) + (cArr[i15] - '0');
        int i17 = i15 + 1;
        if (i17 >= i4) {
            return i16;
        }
        int i18 = (i16 * 10) + (cArr[i17] - '0');
        int i19 = i17 + 1;
        if (i19 < i4) {
            return (i18 * 10) + (cArr[i19] - '0');
        }
        return i18;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0076, code lost:
        return java.lang.Integer.parseInt(r9);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int parseInt(java.lang.String r9) {
        /*
            r0 = 0
            char r1 = r9.charAt(r0)
            int r2 = r9.length()
            r3 = 1
            r4 = 45
            if (r1 != r4) goto L_0x000f
            r0 = r3
        L_0x000f:
            r4 = 10
            if (r0 == 0) goto L_0x0027
            if (r2 == r3) goto L_0x0022
            if (r2 <= r4) goto L_0x0019
            goto L_0x0022
        L_0x0019:
            r1 = 2
            char r3 = r9.charAt(r3)
            r8 = r3
            r3 = r1
            r1 = r8
            goto L_0x0030
        L_0x0022:
            int r9 = java.lang.Integer.parseInt(r9)
            return r9
        L_0x0027:
            r5 = 9
            if (r2 <= r5) goto L_0x0030
            int r9 = java.lang.Integer.parseInt(r9)
            return r9
        L_0x0030:
            r5 = 57
            if (r1 > r5) goto L_0x0085
            r6 = 48
            if (r1 >= r6) goto L_0x0039
            goto L_0x0085
        L_0x0039:
            int r1 = r1 - r6
            if (r3 >= r2) goto L_0x0081
            int r7 = r3 + 1
            char r3 = r9.charAt(r3)
            if (r3 > r5) goto L_0x007c
            if (r3 >= r6) goto L_0x0047
            goto L_0x007c
        L_0x0047:
            int r1 = r1 * 10
            int r3 = r3 - r6
            int r1 = r1 + r3
            if (r7 >= r2) goto L_0x0081
            int r3 = r7 + 1
            char r7 = r9.charAt(r7)
            if (r7 > r5) goto L_0x0077
            if (r7 >= r6) goto L_0x0058
            goto L_0x0077
        L_0x0058:
            int r1 = r1 * 10
            int r7 = r7 - r6
            int r1 = r1 + r7
            if (r3 >= r2) goto L_0x0081
        L_0x005e:
            int r7 = r3 + 1
            char r3 = r9.charAt(r3)
            if (r3 > r5) goto L_0x0072
            if (r3 >= r6) goto L_0x0069
            goto L_0x0072
        L_0x0069:
            int r1 = r1 * r4
            int r3 = r3 + -48
            int r1 = r1 + r3
            if (r7 < r2) goto L_0x0070
            goto L_0x0081
        L_0x0070:
            r3 = r7
            goto L_0x005e
        L_0x0072:
            int r9 = java.lang.Integer.parseInt(r9)
            return r9
        L_0x0077:
            int r9 = java.lang.Integer.parseInt(r9)
            return r9
        L_0x007c:
            int r9 = java.lang.Integer.parseInt(r9)
            return r9
        L_0x0081:
            if (r0 == 0) goto L_0x0084
            int r1 = -r1
        L_0x0084:
            return r1
        L_0x0085:
            int r9 = java.lang.Integer.parseInt(r9)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.p007io.NumberInput.parseInt(java.lang.String):int");
    }

    public static long parseLong(char[] cArr, int i, int i2) {
        int i3 = i2 - 9;
        return (((long) parseInt(cArr, i, i3)) * L_BILLION) + ((long) parseInt(cArr, i + i3, 9));
    }

    public static long parseLong(String str) {
        if (str.length() <= 9) {
            return (long) parseInt(str);
        }
        return Long.parseLong(str);
    }

    public static boolean inLongRange(char[] cArr, int i, int i2, boolean z) {
        String str = z ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
        int length = str.length();
        if (i2 < length) {
            return true;
        }
        if (i2 > length) {
            return false;
        }
        int i3 = 0;
        while (i3 < length) {
            int charAt = cArr[i + i3] - str.charAt(i3);
            if (charAt == 0) {
                i3++;
            } else if (charAt < 0) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean inLongRange(String str, boolean z) {
        String str2 = z ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
        int length = str2.length();
        int length2 = str.length();
        if (length2 < length) {
            return true;
        }
        if (length2 > length) {
            return false;
        }
        int i = 0;
        while (i < length) {
            int charAt = str.charAt(i) - str2.charAt(i);
            if (charAt == 0) {
                i++;
            } else if (charAt < 0) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0003, code lost:
        r5 = r5.trim();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int parseAsInt(java.lang.String r5, int r6) {
        /*
            if (r5 != 0) goto L_0x0003
            return r6
        L_0x0003:
            java.lang.String r5 = r5.trim()
            int r0 = r5.length()
            if (r0 != 0) goto L_0x000e
            return r6
        L_0x000e:
            r1 = 0
            r2 = 1
            if (r0 <= 0) goto L_0x0029
            char r3 = r5.charAt(r1)
            r4 = 43
            if (r3 != r4) goto L_0x0024
            java.lang.String r5 = r5.substring(r2)
            int r0 = r5.length()
            goto L_0x0029
        L_0x0024:
            r4 = 45
            if (r3 != r4) goto L_0x0029
            r1 = r2
        L_0x0029:
            if (r1 >= r0) goto L_0x0043
            char r2 = r5.charAt(r1)
            r3 = 57
            if (r2 > r3) goto L_0x003b
            r3 = 48
            if (r2 >= r3) goto L_0x0038
            goto L_0x003b
        L_0x0038:
            int r1 = r1 + 1
            goto L_0x0029
        L_0x003b:
            double r5 = parseDouble(r5)     // Catch:{ NumberFormatException -> 0x0041 }
            int r5 = (int) r5
            return r5
        L_0x0041:
            r5 = move-exception
            return r6
        L_0x0043:
            int r5 = java.lang.Integer.parseInt(r5)     // Catch:{ NumberFormatException -> 0x0048 }
            return r5
        L_0x0048:
            r5 = move-exception
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.p007io.NumberInput.parseAsInt(java.lang.String, int):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0003, code lost:
        r5 = r5.trim();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long parseAsLong(java.lang.String r5, long r6) {
        /*
            if (r5 != 0) goto L_0x0003
            return r6
        L_0x0003:
            java.lang.String r5 = r5.trim()
            int r0 = r5.length()
            if (r0 != 0) goto L_0x000e
            return r6
        L_0x000e:
            r1 = 0
            r2 = 1
            if (r0 <= 0) goto L_0x0029
            char r3 = r5.charAt(r1)
            r4 = 43
            if (r3 != r4) goto L_0x0024
            java.lang.String r5 = r5.substring(r2)
            int r0 = r5.length()
            goto L_0x0029
        L_0x0024:
            r4 = 45
            if (r3 != r4) goto L_0x0029
            r1 = r2
        L_0x0029:
            if (r1 >= r0) goto L_0x0043
            char r2 = r5.charAt(r1)
            r3 = 57
            if (r2 > r3) goto L_0x003b
            r3 = 48
            if (r2 >= r3) goto L_0x0038
            goto L_0x003b
        L_0x0038:
            int r1 = r1 + 1
            goto L_0x0029
        L_0x003b:
            double r5 = parseDouble(r5)     // Catch:{ NumberFormatException -> 0x0041 }
            long r5 = (long) r5
            return r5
        L_0x0041:
            r5 = move-exception
            return r6
        L_0x0043:
            long r5 = java.lang.Long.parseLong(r5)     // Catch:{ NumberFormatException -> 0x0048 }
            return r5
        L_0x0048:
            r5 = move-exception
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.p007io.NumberInput.parseAsLong(java.lang.String, long):long");
    }

    public static double parseAsDouble(String str, double d) {
        if (str == null) {
            return d;
        }
        String trim = str.trim();
        if (trim.length() == 0) {
            return d;
        }
        try {
            return parseDouble(trim);
        } catch (NumberFormatException e) {
            return d;
        }
    }

    public static double parseDouble(String str) throws NumberFormatException {
        if (NASTY_SMALL_DOUBLE.equals(str)) {
            return Double.MIN_VALUE;
        }
        return Double.parseDouble(str);
    }
}
