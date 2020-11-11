package com.fasterxml.jackson.core.p007io;

import com.facebook.imageutils.JfifUtil;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.lang.ref.SoftReference;

/* renamed from: com.fasterxml.jackson.core.io.JsonStringEncoder */
public final class JsonStringEncoder {
    private static final byte[] HEX_BYTES = CharTypes.copyHexBytes();
    private static final char[] HEX_CHARS = CharTypes.copyHexChars();
    private static final int INT_0 = 48;
    private static final int INT_BACKSLASH = 92;
    private static final int INT_U = 117;
    private static final int SURR1_FIRST = 55296;
    private static final int SURR1_LAST = 56319;
    private static final int SURR2_FIRST = 56320;
    private static final int SURR2_LAST = 57343;
    protected static final ThreadLocal<SoftReference<JsonStringEncoder>> _threadEncoder = new ThreadLocal<>();
    protected ByteArrayBuilder _byteBuilder;
    protected final char[] _quoteBuffer;
    protected TextBuffer _textBuffer;

    public JsonStringEncoder() {
        char[] cArr = new char[6];
        this._quoteBuffer = cArr;
        cArr[0] = '\\';
        cArr[2] = '0';
        cArr[3] = '0';
    }

    public static JsonStringEncoder getInstance() {
        ThreadLocal<SoftReference<JsonStringEncoder>> threadLocal = _threadEncoder;
        SoftReference softReference = threadLocal.get();
        JsonStringEncoder jsonStringEncoder = softReference == null ? null : (JsonStringEncoder) softReference.get();
        if (jsonStringEncoder != null) {
            return jsonStringEncoder;
        }
        JsonStringEncoder jsonStringEncoder2 = new JsonStringEncoder();
        threadLocal.set(new SoftReference(jsonStringEncoder2));
        return jsonStringEncoder2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0032, code lost:
        if (r9 >= 0) goto L_0x003b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0034, code lost:
        r6 = _appendNumericEscape(r6, r11._quoteBuffer);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003b, code lost:
        r6 = _appendNamedEscape(r9, r11._quoteBuffer);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0041, code lost:
        r9 = r7 + r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0044, code lost:
        if (r9 <= r1.length) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0046, code lost:
        r9 = r1.length - r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0048, code lost:
        if (r9 <= 0) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004a, code lost:
        java.lang.System.arraycopy(r11._quoteBuffer, 0, r1, r7, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004f, code lost:
        r1 = r0.finishCurrentSegment();
        r6 = r6 - r9;
        java.lang.System.arraycopy(r11._quoteBuffer, r9, r1, 0, r6);
        r7 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005c, code lost:
        java.lang.System.arraycopy(r11._quoteBuffer, 0, r1, r7, r6);
        r7 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0029, code lost:
        r8 = r6 + 1;
        r6 = r12.charAt(r6);
        r9 = r2[r6];
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public char[] quoteAsString(java.lang.String r12) {
        /*
            r11 = this;
            com.fasterxml.jackson.core.util.TextBuffer r0 = r11._textBuffer
            if (r0 != 0) goto L_0x000c
            com.fasterxml.jackson.core.util.TextBuffer r0 = new com.fasterxml.jackson.core.util.TextBuffer
            r1 = 0
            r0.<init>(r1)
            r11._textBuffer = r0
        L_0x000c:
            char[] r1 = r0.emptyAndGetCurrentSegment()
            int[] r2 = com.fasterxml.jackson.core.p007io.CharTypes.get7BitOutputEscapes()
            int r3 = r2.length
            int r4 = r12.length()
            r5 = 0
            r6 = r5
            r7 = r6
        L_0x001d:
            if (r6 >= r4) goto L_0x0078
        L_0x001f:
            char r8 = r12.charAt(r6)
            if (r8 >= r3) goto L_0x0064
            r9 = r2[r8]
            if (r9 == 0) goto L_0x0064
            int r8 = r6 + 1
            char r6 = r12.charAt(r6)
            r9 = r2[r6]
            if (r9 >= 0) goto L_0x003b
            char[] r9 = r11._quoteBuffer
            int r6 = r11._appendNumericEscape(r6, r9)
            goto L_0x0041
        L_0x003b:
            char[] r6 = r11._quoteBuffer
            int r6 = r11._appendNamedEscape(r9, r6)
        L_0x0041:
            int r9 = r7 + r6
            int r10 = r1.length
            if (r9 <= r10) goto L_0x005c
            int r9 = r1.length
            int r9 = r9 - r7
            if (r9 <= 0) goto L_0x004f
            char[] r10 = r11._quoteBuffer
            java.lang.System.arraycopy(r10, r5, r1, r7, r9)
        L_0x004f:
            char[] r1 = r0.finishCurrentSegment()
            int r6 = r6 - r9
            char[] r7 = r11._quoteBuffer
            java.lang.System.arraycopy(r7, r9, r1, r5, r6)
            r7 = r6
            goto L_0x0062
        L_0x005c:
            char[] r10 = r11._quoteBuffer
            java.lang.System.arraycopy(r10, r5, r1, r7, r6)
            r7 = r9
        L_0x0062:
            r6 = r8
            goto L_0x001d
        L_0x0064:
            int r9 = r1.length
            if (r7 < r9) goto L_0x006c
            char[] r1 = r0.finishCurrentSegment()
            r7 = r5
        L_0x006c:
            int r9 = r7 + 1
            r1[r7] = r8
            int r6 = r6 + 1
            if (r6 < r4) goto L_0x0076
            r7 = r9
            goto L_0x0078
        L_0x0076:
            r7 = r9
            goto L_0x001f
        L_0x0078:
            r0.setCurrentLength(r7)
            char[] r12 = r0.contentsAsArray()
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.p007io.JsonStringEncoder.quoteAsString(java.lang.String):char[]");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0043, code lost:
        if (r5 < r2.length) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0045, code lost:
        r2 = r0.finishCurrentSegment();
        r5 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004a, code lost:
        r7 = r4 + 1;
        r4 = r11.charAt(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0050, code lost:
        if (r4 > 127) goto L_0x005e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0052, code lost:
        r5 = _appendByteEscape(r4, r6[r4], r0, r5);
        r2 = r0.getCurrentSegment();
        r4 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0060, code lost:
        if (r4 > 2047) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0062, code lost:
        r6 = r5 + 1;
        r2[r5] = (byte) ((r4 >> 6) | com.facebook.imageutils.JfifUtil.MARKER_SOFn);
        r5 = (r4 & '?') | 128;
        r4 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0076, code lost:
        if (r4 < SURR1_FIRST) goto L_0x00d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x007b, code lost:
        if (r4 <= SURR2_LAST) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0081, code lost:
        if (r4 <= SURR1_LAST) goto L_0x0086;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0083, code lost:
        _illegalSurrogate(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0086, code lost:
        if (r7 < r1) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0088, code lost:
        _illegalSurrogate(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x008b, code lost:
        r6 = r7 + 1;
        r4 = _convertSurrogate(r4, r11.charAt(r7));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0098, code lost:
        if (r4 <= 1114111) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009a, code lost:
        _illegalSurrogate(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x009d, code lost:
        r7 = r5 + 1;
        r2[r5] = (byte) ((r4 >> 18) | 240);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a7, code lost:
        if (r7 < r2.length) goto L_0x00ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00a9, code lost:
        r2 = r0.finishCurrentSegment();
        r7 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ae, code lost:
        r5 = r7 + 1;
        r2[r7] = (byte) (((r4 >> 12) & 63) | 128);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00ba, code lost:
        if (r5 < r2.length) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00bc, code lost:
        r2 = r0.finishCurrentSegment();
        r5 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00c1, code lost:
        r7 = r5 + 1;
        r2[r5] = (byte) (((r4 >> 6) & 63) | 128);
        r5 = (r4 & '?') | 128;
        r4 = r6;
        r6 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d4, code lost:
        r6 = r5 + 1;
        r2[r5] = (byte) ((r4 >> 12) | 224);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00de, code lost:
        if (r6 < r2.length) goto L_0x00e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00e0, code lost:
        r2 = r0.finishCurrentSegment();
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00e5, code lost:
        r2[r6] = (byte) (((r4 >> 6) & 63) | 128);
        r6 = r6 + 1;
        r5 = (r4 & '?') | 128;
        r4 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00f8, code lost:
        if (r6 < r2.length) goto L_0x00ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00fa, code lost:
        r2 = r0.finishCurrentSegment();
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00ff, code lost:
        r2[r6] = (byte) r5;
        r5 = r6 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] quoteAsUTF8(java.lang.String r11) {
        /*
            r10 = this;
            com.fasterxml.jackson.core.util.ByteArrayBuilder r0 = r10._byteBuilder
            if (r0 != 0) goto L_0x000c
            com.fasterxml.jackson.core.util.ByteArrayBuilder r0 = new com.fasterxml.jackson.core.util.ByteArrayBuilder
            r1 = 0
            r0.<init>((com.fasterxml.jackson.core.util.BufferRecycler) r1)
            r10._byteBuilder = r0
        L_0x000c:
            int r1 = r11.length()
            byte[] r2 = r0.resetAndGetFirstSegment()
            r3 = 0
            r4 = r3
            r5 = r4
        L_0x0019:
            if (r4 >= r1) goto L_0x0107
            int[] r6 = com.fasterxml.jackson.core.p007io.CharTypes.get7BitOutputEscapes()
        L_0x001f:
            char r7 = r11.charAt(r4)
            r8 = 127(0x7f, float:1.78E-43)
            if (r7 > r8) goto L_0x0042
            r9 = r6[r7]
            if (r9 == 0) goto L_0x002c
            goto L_0x0042
        L_0x002c:
            int r8 = r2.length
            if (r5 < r8) goto L_0x0034
            byte[] r2 = r0.finishCurrentSegment()
            r5 = r3
        L_0x0034:
            int r8 = r5 + 1
            byte r7 = (byte) r7
            r2[r5] = r7
            int r4 = r4 + 1
            if (r4 < r1) goto L_0x0040
            r5 = r8
            goto L_0x0107
        L_0x0040:
            r5 = r8
            goto L_0x001f
        L_0x0042:
            int r7 = r2.length
            if (r5 < r7) goto L_0x004a
            byte[] r2 = r0.finishCurrentSegment()
            r5 = r3
        L_0x004a:
            int r7 = r4 + 1
            char r4 = r11.charAt(r4)
            if (r4 > r8) goto L_0x005e
            r2 = r6[r4]
            int r5 = r10._appendByteEscape(r4, r2, r0, r5)
            byte[] r2 = r0.getCurrentSegment()
            r4 = r7
            goto L_0x0019
        L_0x005e:
            r6 = 2047(0x7ff, float:2.868E-42)
            if (r4 > r6) goto L_0x0073
            int r6 = r5 + 1
            int r8 = r4 >> 6
            r8 = r8 | 192(0xc0, float:2.69E-43)
            byte r8 = (byte) r8
            r2[r5] = r8
            r4 = r4 & 63
            r4 = r4 | 128(0x80, float:1.794E-43)
            r5 = r4
            r4 = r7
            goto L_0x00f7
        L_0x0073:
            r6 = 55296(0xd800, float:7.7486E-41)
            if (r4 < r6) goto L_0x00d4
            r6 = 57343(0xdfff, float:8.0355E-41)
            if (r4 <= r6) goto L_0x007e
            goto L_0x00d4
        L_0x007e:
            r6 = 56319(0xdbff, float:7.892E-41)
            if (r4 <= r6) goto L_0x0086
            _illegalSurrogate(r4)
        L_0x0086:
            if (r7 < r1) goto L_0x008b
            _illegalSurrogate(r4)
        L_0x008b:
            int r6 = r7 + 1
            char r7 = r11.charAt(r7)
            int r4 = _convertSurrogate(r4, r7)
            r7 = 1114111(0x10ffff, float:1.561202E-39)
            if (r4 <= r7) goto L_0x009d
            _illegalSurrogate(r4)
        L_0x009d:
            int r7 = r5 + 1
            int r8 = r4 >> 18
            r8 = r8 | 240(0xf0, float:3.36E-43)
            byte r8 = (byte) r8
            r2[r5] = r8
            int r5 = r2.length
            if (r7 < r5) goto L_0x00ae
            byte[] r2 = r0.finishCurrentSegment()
            r7 = r3
        L_0x00ae:
            int r5 = r7 + 1
            int r8 = r4 >> 12
            r8 = r8 & 63
            r8 = r8 | 128(0x80, float:1.794E-43)
            byte r8 = (byte) r8
            r2[r7] = r8
            int r7 = r2.length
            if (r5 < r7) goto L_0x00c1
            byte[] r2 = r0.finishCurrentSegment()
            r5 = r3
        L_0x00c1:
            int r7 = r5 + 1
            int r8 = r4 >> 6
            r8 = r8 & 63
            r8 = r8 | 128(0x80, float:1.794E-43)
            byte r8 = (byte) r8
            r2[r5] = r8
            r4 = r4 & 63
            r4 = r4 | 128(0x80, float:1.794E-43)
            r5 = r4
            r4 = r6
            r6 = r7
            goto L_0x00f7
        L_0x00d4:
            int r6 = r5 + 1
            int r8 = r4 >> 12
            r8 = r8 | 224(0xe0, float:3.14E-43)
            byte r8 = (byte) r8
            r2[r5] = r8
            int r5 = r2.length
            if (r6 < r5) goto L_0x00e5
            byte[] r2 = r0.finishCurrentSegment()
            r6 = r3
        L_0x00e5:
            int r5 = r6 + 1
            int r8 = r4 >> 6
            r8 = r8 & 63
            r8 = r8 | 128(0x80, float:1.794E-43)
            byte r8 = (byte) r8
            r2[r6] = r8
            r4 = r4 & 63
            r4 = r4 | 128(0x80, float:1.794E-43)
            r6 = r5
            r5 = r4
            r4 = r7
        L_0x00f7:
            int r7 = r2.length
            if (r6 < r7) goto L_0x00ff
            byte[] r2 = r0.finishCurrentSegment()
            r6 = r3
        L_0x00ff:
            int r7 = r6 + 1
            byte r5 = (byte) r5
            r2[r6] = r5
            r5 = r7
            goto L_0x0019
        L_0x0107:
            com.fasterxml.jackson.core.util.ByteArrayBuilder r11 = r10._byteBuilder
            byte[] r11 = r11.completeAndCoalesce(r5)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.p007io.JsonStringEncoder.quoteAsUTF8(java.lang.String):byte[]");
    }

    public byte[] encodeAsUTF8(String str) {
        int i;
        char c;
        ByteArrayBuilder byteArrayBuilder = this._byteBuilder;
        if (byteArrayBuilder == null) {
            byteArrayBuilder = new ByteArrayBuilder((BufferRecycler) null);
            this._byteBuilder = byteArrayBuilder;
        }
        int length = str.length();
        byte[] resetAndGetFirstSegment = byteArrayBuilder.resetAndGetFirstSegment();
        int length2 = resetAndGetFirstSegment.length;
        int i2 = 0;
        int i3 = 0;
        loop0:
        while (true) {
            if (i2 >= length) {
                break;
            }
            int i4 = i2 + 1;
            char charAt = str.charAt(i2);
            while (charAt <= 127) {
                if (i3 >= length2) {
                    resetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                    length2 = resetAndGetFirstSegment.length;
                    i3 = 0;
                }
                int i5 = i3 + 1;
                resetAndGetFirstSegment[i3] = (byte) charAt;
                if (i4 >= length) {
                    i3 = i5;
                    break loop0;
                }
                char charAt2 = str.charAt(i4);
                i4++;
                charAt = charAt2;
                i3 = i5;
            }
            if (i3 >= length2) {
                resetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                length2 = resetAndGetFirstSegment.length;
                i3 = 0;
            }
            if (charAt < 2048) {
                i = i3 + 1;
                resetAndGetFirstSegment[i3] = (byte) ((charAt >> 6) | JfifUtil.MARKER_SOFn);
                c = charAt;
                i2 = i4;
            } else if (charAt < SURR1_FIRST || charAt > SURR2_LAST) {
                int i6 = i3 + 1;
                resetAndGetFirstSegment[i3] = (byte) ((charAt >> 12) | 224);
                if (i6 >= length2) {
                    resetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                    length2 = resetAndGetFirstSegment.length;
                    i6 = 0;
                }
                resetAndGetFirstSegment[i6] = (byte) (((charAt >> 6) & 63) | 128);
                i = i6 + 1;
                c = charAt;
                i2 = i4;
            } else {
                if (charAt > SURR1_LAST) {
                    _illegalSurrogate(charAt);
                }
                if (i4 >= length) {
                    _illegalSurrogate(charAt);
                }
                int i7 = i4 + 1;
                int _convertSurrogate = _convertSurrogate(charAt, str.charAt(i4));
                if (_convertSurrogate > 1114111) {
                    _illegalSurrogate(_convertSurrogate);
                }
                int i8 = i3 + 1;
                resetAndGetFirstSegment[i3] = (byte) ((_convertSurrogate >> 18) | 240);
                if (i8 >= length2) {
                    resetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                    length2 = resetAndGetFirstSegment.length;
                    i8 = 0;
                }
                int i9 = i8 + 1;
                resetAndGetFirstSegment[i8] = (byte) (((_convertSurrogate >> 12) & 63) | 128);
                if (i9 >= length2) {
                    resetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                    length2 = resetAndGetFirstSegment.length;
                    i9 = 0;
                }
                int i10 = i9 + 1;
                resetAndGetFirstSegment[i9] = (byte) (((_convertSurrogate >> 6) & 63) | 128);
                c = _convertSurrogate;
                i2 = i7;
                i = i10;
            }
            if (i >= length2) {
                resetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                length2 = resetAndGetFirstSegment.length;
                i = 0;
            }
            resetAndGetFirstSegment[i] = (byte) ((c & '?') | 128);
            i3 = i + 1;
        }
        return this._byteBuilder.completeAndCoalesce(i3);
    }

    private int _appendNumericEscape(int i, char[] cArr) {
        cArr[1] = 'u';
        char[] cArr2 = HEX_CHARS;
        cArr[4] = cArr2[i >> 4];
        cArr[5] = cArr2[i & 15];
        return 6;
    }

    private int _appendNamedEscape(int i, char[] cArr) {
        cArr[1] = (char) i;
        return 2;
    }

    private int _appendByteEscape(int i, int i2, ByteArrayBuilder byteArrayBuilder, int i3) {
        byteArrayBuilder.setCurrentSegmentLength(i3);
        byteArrayBuilder.append(92);
        if (i2 < 0) {
            byteArrayBuilder.append(117);
            if (i > 255) {
                int i4 = i >> 8;
                byte[] bArr = HEX_BYTES;
                byteArrayBuilder.append(bArr[i4 >> 4]);
                byteArrayBuilder.append(bArr[i4 & 15]);
                i &= 255;
            } else {
                byteArrayBuilder.append(48);
                byteArrayBuilder.append(48);
            }
            byte[] bArr2 = HEX_BYTES;
            byteArrayBuilder.append(bArr2[i >> 4]);
            byteArrayBuilder.append(bArr2[i & 15]);
        } else {
            byteArrayBuilder.append((byte) i2);
        }
        return byteArrayBuilder.getCurrentSegmentLength();
    }

    protected static int _convertSurrogate(int i, int i2) {
        if (i2 >= SURR2_FIRST && i2 <= SURR2_LAST) {
            return ((i - SURR1_FIRST) << 10) + 65536 + (i2 - SURR2_FIRST);
        }
        throw new IllegalArgumentException("Broken surrogate pair: first char 0x" + Integer.toHexString(i) + ", second 0x" + Integer.toHexString(i2) + "; illegal combination");
    }

    protected static void _illegalSurrogate(int i) {
        throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(i));
    }
}
