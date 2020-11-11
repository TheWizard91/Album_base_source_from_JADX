package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.p007io.CharTypes;
import com.fasterxml.jackson.core.p007io.NumberInput;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import kotlin.text.Typography;

public final class TextNode extends ValueNode {
    static final TextNode EMPTY_STRING_NODE = new TextNode("");
    static final int INT_SPACE = 32;
    final String _value;

    public TextNode(String str) {
        this._value = str;
    }

    public static TextNode valueOf(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return EMPTY_STRING_NODE;
        }
        return new TextNode(str);
    }

    public JsonNodeType getNodeType() {
        return JsonNodeType.STRING;
    }

    public JsonToken asToken() {
        return JsonToken.VALUE_STRING;
    }

    public String textValue() {
        return this._value;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002c, code lost:
        _reportBase64EOF();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002f, code lost:
        r4 = r5 + 1;
        r5 = r1.charAt(r5);
        r7 = r12.decodeBase64Char(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0039, code lost:
        if (r7 >= 0) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003b, code lost:
        _reportInvalidBase64(r12, r5, 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003f, code lost:
        r5 = (r6 << 6) | r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0042, code lost:
        if (r4 < r2) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0048, code lost:
        if (r12.usesPadding() != false) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004a, code lost:
        r0.append(r5 >> 4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0051, code lost:
        _reportBase64EOF();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0054, code lost:
        r6 = r4 + 1;
        r4 = r1.charAt(r4);
        r7 = r12.decodeBase64Char(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0061, code lost:
        if (r7 >= 0) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0063, code lost:
        if (r7 == -2) goto L_0x0068;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0065, code lost:
        _reportInvalidBase64(r12, r4, 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0068, code lost:
        if (r6 < r2) goto L_0x006d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006a, code lost:
        _reportBase64EOF();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006d, code lost:
        r4 = r6 + 1;
        r6 = r1.charAt(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0077, code lost:
        if (r12.usesPaddingChar(r6) != false) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0079, code lost:
        _reportInvalidBase64(r12, r6, 3, "expected padding character '" + r12.getPaddingChar() + "'");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0099, code lost:
        r0.append(r5 >> 4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a0, code lost:
        r4 = (r5 << 6) | r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a3, code lost:
        if (r6 < r2) goto L_0x00b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a9, code lost:
        if (r12.usesPadding() != false) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00ab, code lost:
        r0.appendTwoBytes(r4 >> 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b1, code lost:
        _reportBase64EOF();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b4, code lost:
        r5 = r6 + 1;
        r6 = r1.charAt(r6);
        r7 = r12.decodeBase64Char(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00be, code lost:
        if (r7 >= 0) goto L_0x00cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00c0, code lost:
        if (r7 == -2) goto L_0x00c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00c2, code lost:
        _reportInvalidBase64(r12, r6, 3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c5, code lost:
        r0.appendTwoBytes(r4 >> 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00cb, code lost:
        r0.appendThreeBytes((r4 << 6) | r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00d1, code lost:
        r4 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0020, code lost:
        r6 = r12.decodeBase64Char(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0024, code lost:
        if (r6 >= 0) goto L_0x002a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0026, code lost:
        _reportInvalidBase64(r12, r4, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002a, code lost:
        if (r5 < r2) goto L_0x002f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getBinaryValue(com.fasterxml.jackson.core.Base64Variant r12) throws java.io.IOException {
        /*
            r11 = this;
            com.fasterxml.jackson.core.util.ByteArrayBuilder r0 = new com.fasterxml.jackson.core.util.ByteArrayBuilder
            r1 = 100
            r0.<init>((int) r1)
            java.lang.String r1 = r11._value
            int r2 = r1.length()
            r3 = 0
            r4 = r3
        L_0x0010:
            if (r4 >= r2) goto L_0x00d7
        L_0x0012:
            int r5 = r4 + 1
            char r4 = r1.charAt(r4)
            if (r5 < r2) goto L_0x001c
            goto L_0x00d7
        L_0x001c:
            r6 = 32
            if (r4 <= r6) goto L_0x00d4
            int r6 = r12.decodeBase64Char((char) r4)
            if (r6 >= 0) goto L_0x0029
            r11._reportInvalidBase64(r12, r4, r3)
        L_0x0029:
            if (r5 < r2) goto L_0x002f
            r11._reportBase64EOF()
        L_0x002f:
            int r4 = r5 + 1
            char r5 = r1.charAt(r5)
            int r7 = r12.decodeBase64Char((char) r5)
            if (r7 >= 0) goto L_0x003f
            r8 = 1
            r11._reportInvalidBase64(r12, r5, r8)
        L_0x003f:
            int r5 = r6 << 6
            r5 = r5 | r7
            if (r4 < r2) goto L_0x0054
            boolean r6 = r12.usesPadding()
            if (r6 != 0) goto L_0x0051
            int r12 = r5 >> 4
            r0.append(r12)
            goto L_0x00d7
        L_0x0051:
            r11._reportBase64EOF()
        L_0x0054:
            int r6 = r4 + 1
            char r4 = r1.charAt(r4)
            int r7 = r12.decodeBase64Char((char) r4)
            r8 = 3
            r9 = -2
            r10 = 2
            if (r7 >= 0) goto L_0x00a0
            if (r7 == r9) goto L_0x0068
            r11._reportInvalidBase64(r12, r4, r10)
        L_0x0068:
            if (r6 < r2) goto L_0x006d
            r11._reportBase64EOF()
        L_0x006d:
            int r4 = r6 + 1
            char r6 = r1.charAt(r6)
            boolean r7 = r12.usesPaddingChar((char) r6)
            if (r7 != 0) goto L_0x0099
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r9 = "expected padding character '"
            java.lang.StringBuilder r7 = r7.append(r9)
            char r9 = r12.getPaddingChar()
            java.lang.StringBuilder r7 = r7.append(r9)
            java.lang.String r9 = "'"
            java.lang.StringBuilder r7 = r7.append(r9)
            java.lang.String r7 = r7.toString()
            r11._reportInvalidBase64(r12, r6, r8, r7)
        L_0x0099:
            int r5 = r5 >> 4
            r0.append(r5)
            goto L_0x0010
        L_0x00a0:
            int r4 = r5 << 6
            r4 = r4 | r7
            if (r6 < r2) goto L_0x00b4
            boolean r5 = r12.usesPadding()
            if (r5 != 0) goto L_0x00b1
            int r12 = r4 >> 2
            r0.appendTwoBytes(r12)
            goto L_0x00d7
        L_0x00b1:
            r11._reportBase64EOF()
        L_0x00b4:
            int r5 = r6 + 1
            char r6 = r1.charAt(r6)
            int r7 = r12.decodeBase64Char((char) r6)
            if (r7 >= 0) goto L_0x00cb
            if (r7 == r9) goto L_0x00c5
            r11._reportInvalidBase64(r12, r6, r8)
        L_0x00c5:
            int r4 = r4 >> 2
            r0.appendTwoBytes(r4)
            goto L_0x00d1
        L_0x00cb:
            int r4 = r4 << 6
            r4 = r4 | r7
            r0.appendThreeBytes(r4)
        L_0x00d1:
            r4 = r5
            goto L_0x0010
        L_0x00d4:
            r4 = r5
            goto L_0x0012
        L_0x00d7:
            byte[] r12 = r0.toByteArray()
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.node.TextNode.getBinaryValue(com.fasterxml.jackson.core.Base64Variant):byte[]");
    }

    public byte[] binaryValue() throws IOException {
        return getBinaryValue(Base64Variants.getDefaultVariant());
    }

    public String asText() {
        return this._value;
    }

    public boolean asBoolean(boolean z) {
        String str = this._value;
        if (str == null || !"true".equals(str.trim())) {
            return z;
        }
        return true;
    }

    public int asInt(int i) {
        return NumberInput.parseAsInt(this._value, i);
    }

    public long asLong(long j) {
        return NumberInput.parseAsLong(this._value, j);
    }

    public double asDouble(double d) {
        return NumberInput.parseAsDouble(this._value, d);
    }

    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        String str = this._value;
        if (str == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeString(str);
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == getClass()) {
            return ((TextNode) obj)._value.equals(this._value);
        }
        return false;
    }

    public int hashCode() {
        return this._value.hashCode();
    }

    public String toString() {
        int length = this._value.length();
        StringBuilder sb = new StringBuilder(length + 2 + (length >> 4));
        appendQuoted(sb, this._value);
        return sb.toString();
    }

    protected static void appendQuoted(StringBuilder sb, String str) {
        sb.append(Typography.quote);
        CharTypes.appendQuoted(sb, str);
        sb.append(Typography.quote);
    }

    /* access modifiers changed from: protected */
    public void _reportInvalidBase64(Base64Variant base64Variant, char c, int i) throws JsonParseException {
        _reportInvalidBase64(base64Variant, c, i, (String) null);
    }

    /* access modifiers changed from: protected */
    public void _reportInvalidBase64(Base64Variant base64Variant, char c, int i, String str) throws JsonParseException {
        String str2;
        if (c <= ' ') {
            str2 = "Illegal white space character (code 0x" + Integer.toHexString(c) + ") as character #" + (i + 1) + " of 4-char base64 unit: can only used between units";
        } else if (base64Variant.usesPaddingChar(c)) {
            str2 = "Unexpected padding character ('" + base64Variant.getPaddingChar() + "') as character #" + (i + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
        } else if (!Character.isDefined(c) || Character.isISOControl(c)) {
            str2 = "Illegal character (code 0x" + Integer.toHexString(c) + ") in base64 content";
        } else {
            str2 = "Illegal character '" + c + "' (code 0x" + Integer.toHexString(c) + ") in base64 content";
        }
        if (str != null) {
            str2 = str2 + ": " + str;
        }
        throw new JsonParseException(str2, JsonLocation.f87NA);
    }

    /* access modifiers changed from: protected */
    public void _reportBase64EOF() throws JsonParseException {
        throw new JsonParseException("Unexpected end-of-String when base64 content", JsonLocation.f87NA);
    }
}
