package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.p007io.NumberInput;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;

public abstract class ParserMinimalBase extends JsonParser {
    protected static final int INT_APOSTROPHE = 39;
    protected static final int INT_ASTERISK = 42;
    protected static final int INT_BACKSLASH = 92;
    protected static final int INT_COLON = 58;
    protected static final int INT_COMMA = 44;
    protected static final int INT_CR = 13;
    protected static final int INT_LBRACKET = 91;
    protected static final int INT_LCURLY = 123;
    protected static final int INT_LF = 10;
    protected static final int INT_QUOTE = 34;
    protected static final int INT_RBRACKET = 93;
    protected static final int INT_RCURLY = 125;
    protected static final int INT_SLASH = 47;
    protected static final int INT_SPACE = 32;
    protected static final int INT_TAB = 9;
    protected static final int INT_b = 98;
    protected static final int INT_f = 102;
    protected static final int INT_n = 110;
    protected static final int INT_r = 114;
    protected static final int INT_t = 116;
    protected static final int INT_u = 117;
    protected JsonToken _currToken;
    protected JsonToken _lastClearedToken;

    /* access modifiers changed from: protected */
    public abstract void _handleEOF() throws JsonParseException;

    public abstract void close() throws IOException;

    public abstract byte[] getBinaryValue(Base64Variant base64Variant) throws IOException, JsonParseException;

    public abstract String getCurrentName() throws IOException, JsonParseException;

    public abstract JsonStreamContext getParsingContext();

    public abstract String getText() throws IOException, JsonParseException;

    public abstract char[] getTextCharacters() throws IOException, JsonParseException;

    public abstract int getTextLength() throws IOException, JsonParseException;

    public abstract int getTextOffset() throws IOException, JsonParseException;

    public abstract boolean hasTextCharacters();

    public abstract boolean isClosed();

    public abstract JsonToken nextToken() throws IOException, JsonParseException;

    public abstract void overrideCurrentName(String str);

    protected ParserMinimalBase() {
    }

    protected ParserMinimalBase(int i) {
        super(i);
    }

    public Version version() {
        return VersionUtil.versionFor(getClass());
    }

    public JsonToken getCurrentToken() {
        return this._currToken;
    }

    public boolean hasCurrentToken() {
        return this._currToken != null;
    }

    public JsonToken nextValue() throws IOException, JsonParseException {
        JsonToken nextToken = nextToken();
        if (nextToken == JsonToken.FIELD_NAME) {
            return nextToken();
        }
        return nextToken;
    }

    public JsonParser skipChildren() throws IOException, JsonParseException {
        if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
            return this;
        }
        int i = 1;
        while (true) {
            JsonToken nextToken = nextToken();
            if (nextToken == null) {
                _handleEOF();
                return this;
            }
            int i2 = C07921.$SwitchMap$com$fasterxml$jackson$core$JsonToken[nextToken.ordinal()];
            if (i2 == 1 || i2 == 2) {
                i++;
            } else if ((i2 == 3 || i2 == 4) && i - 1 == 0) {
                return this;
            }
        }
    }

    public void clearCurrentToken() {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != null) {
            this._lastClearedToken = jsonToken;
            this._currToken = null;
        }
    }

    public JsonToken getLastClearedToken() {
        return this._lastClearedToken;
    }

    public boolean getValueAsBoolean(boolean z) throws IOException, JsonParseException {
        if (this._currToken != null) {
            switch (this._currToken) {
                case VALUE_NUMBER_INT:
                    if (getIntValue() != 0) {
                        return true;
                    }
                    return false;
                case VALUE_TRUE:
                    return true;
                case VALUE_FALSE:
                case VALUE_NULL:
                    return false;
                case VALUE_EMBEDDED_OBJECT:
                    Object embeddedObject = getEmbeddedObject();
                    if (embeddedObject instanceof Boolean) {
                        return ((Boolean) embeddedObject).booleanValue();
                    }
                    break;
                case VALUE_STRING:
                    break;
            }
            if ("true".equals(getText().trim())) {
                return true;
            }
        }
        return z;
    }

    public int getValueAsInt(int i) throws IOException, JsonParseException {
        if (this._currToken != null) {
            switch (this._currToken) {
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                    return getIntValue();
                case VALUE_TRUE:
                    return 1;
                case VALUE_FALSE:
                case VALUE_NULL:
                    return 0;
                case VALUE_EMBEDDED_OBJECT:
                    Object embeddedObject = getEmbeddedObject();
                    if (embeddedObject instanceof Number) {
                        return ((Number) embeddedObject).intValue();
                    }
                    break;
                case VALUE_STRING:
                    return NumberInput.parseAsInt(getText(), i);
            }
        }
        return i;
    }

    public long getValueAsLong(long j) throws IOException, JsonParseException {
        if (this._currToken != null) {
            switch (this._currToken) {
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                    return getLongValue();
                case VALUE_TRUE:
                    return 1;
                case VALUE_FALSE:
                case VALUE_NULL:
                    return 0;
                case VALUE_EMBEDDED_OBJECT:
                    Object embeddedObject = getEmbeddedObject();
                    if (embeddedObject instanceof Number) {
                        return ((Number) embeddedObject).longValue();
                    }
                    break;
                case VALUE_STRING:
                    return NumberInput.parseAsLong(getText(), j);
            }
        }
        return j;
    }

    public double getValueAsDouble(double d) throws IOException, JsonParseException {
        if (this._currToken != null) {
            switch (this._currToken) {
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                    return getDoubleValue();
                case VALUE_TRUE:
                    return 1.0d;
                case VALUE_FALSE:
                case VALUE_NULL:
                    return 0.0d;
                case VALUE_EMBEDDED_OBJECT:
                    Object embeddedObject = getEmbeddedObject();
                    if (embeddedObject instanceof Number) {
                        return ((Number) embeddedObject).doubleValue();
                    }
                    break;
                case VALUE_STRING:
                    return NumberInput.parseAsDouble(getText(), d);
            }
        }
        return d;
    }

    public String getValueAsString(String str) throws IOException, JsonParseException {
        JsonToken jsonToken;
        if (this._currToken == JsonToken.VALUE_STRING || ((jsonToken = this._currToken) != null && jsonToken != JsonToken.VALUE_NULL && this._currToken.isScalarValue())) {
            return getText();
        }
        return str;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0024, code lost:
        _reportBase64EOF();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0027, code lost:
        r2 = r3 + 1;
        r3 = r11.charAt(r3);
        r6 = r13.decodeBase64Char(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0031, code lost:
        if (r6 >= 0) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0033, code lost:
        _reportInvalidBase64(r13, r3, 1, (java.lang.String) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0037, code lost:
        r3 = (r4 << 6) | r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003a, code lost:
        if (r2 < r0) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0040, code lost:
        if (r13.usesPadding() != false) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0042, code lost:
        r12.append(r3 >> 4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0049, code lost:
        _reportBase64EOF();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004c, code lost:
        r4 = r2 + 1;
        r2 = r11.charAt(r2);
        r6 = r13.decodeBase64Char(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0059, code lost:
        if (r6 >= 0) goto L_0x0098;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005b, code lost:
        if (r6 == -2) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005d, code lost:
        _reportInvalidBase64(r13, r2, 2, (java.lang.String) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0060, code lost:
        if (r4 < r0) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0062, code lost:
        _reportBase64EOF();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0065, code lost:
        r2 = r4 + 1;
        r4 = r11.charAt(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x006f, code lost:
        if (r13.usesPaddingChar(r4) != false) goto L_0x0091;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0071, code lost:
        _reportInvalidBase64(r13, r4, 3, "expected padding character '" + r13.getPaddingChar() + "'");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0091, code lost:
        r12.append(r3 >> 4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0098, code lost:
        r2 = (r3 << 6) | r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009b, code lost:
        if (r4 < r0) goto L_0x00ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a1, code lost:
        if (r13.usesPadding() != false) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a3, code lost:
        r12.appendTwoBytes(r2 >> 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a9, code lost:
        _reportBase64EOF();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00ac, code lost:
        r3 = r4 + 1;
        r4 = r11.charAt(r4);
        r6 = r13.decodeBase64Char(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00b6, code lost:
        if (r6 >= 0) goto L_0x00c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b8, code lost:
        if (r6 == -2) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ba, code lost:
        _reportInvalidBase64(r13, r4, 3, (java.lang.String) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00bd, code lost:
        r12.appendTwoBytes(r2 >> 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c3, code lost:
        r12.appendThreeBytes((r2 << 6) | r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c9, code lost:
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0017, code lost:
        r4 = r13.decodeBase64Char(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001c, code lost:
        if (r4 >= 0) goto L_0x0022;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001e, code lost:
        _reportInvalidBase64(r13, r2, 0, (java.lang.String) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0022, code lost:
        if (r3 < r0) goto L_0x0027;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void _decodeBase64(java.lang.String r11, com.fasterxml.jackson.core.util.ByteArrayBuilder r12, com.fasterxml.jackson.core.Base64Variant r13) throws java.io.IOException, com.fasterxml.jackson.core.JsonParseException {
        /*
            r10 = this;
            int r0 = r11.length()
            r1 = 0
            r2 = r1
        L_0x0007:
            if (r2 >= r0) goto L_0x00cf
        L_0x0009:
            int r3 = r2 + 1
            char r2 = r11.charAt(r2)
            if (r3 < r0) goto L_0x0013
            goto L_0x00cf
        L_0x0013:
            r4 = 32
            if (r2 <= r4) goto L_0x00cc
            int r4 = r13.decodeBase64Char((char) r2)
            r5 = 0
            if (r4 >= 0) goto L_0x0021
            r10._reportInvalidBase64(r13, r2, r1, r5)
        L_0x0021:
            if (r3 < r0) goto L_0x0027
            r10._reportBase64EOF()
        L_0x0027:
            int r2 = r3 + 1
            char r3 = r11.charAt(r3)
            int r6 = r13.decodeBase64Char((char) r3)
            if (r6 >= 0) goto L_0x0037
            r7 = 1
            r10._reportInvalidBase64(r13, r3, r7, r5)
        L_0x0037:
            int r3 = r4 << 6
            r3 = r3 | r6
            if (r2 < r0) goto L_0x004c
            boolean r4 = r13.usesPadding()
            if (r4 != 0) goto L_0x0049
            int r11 = r3 >> 4
            r12.append(r11)
            goto L_0x00cf
        L_0x0049:
            r10._reportBase64EOF()
        L_0x004c:
            int r4 = r2 + 1
            char r2 = r11.charAt(r2)
            int r6 = r13.decodeBase64Char((char) r2)
            r7 = 3
            r8 = -2
            r9 = 2
            if (r6 >= 0) goto L_0x0098
            if (r6 == r8) goto L_0x0060
            r10._reportInvalidBase64(r13, r2, r9, r5)
        L_0x0060:
            if (r4 < r0) goto L_0x0065
            r10._reportBase64EOF()
        L_0x0065:
            int r2 = r4 + 1
            char r4 = r11.charAt(r4)
            boolean r5 = r13.usesPaddingChar((char) r4)
            if (r5 != 0) goto L_0x0091
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "expected padding character '"
            java.lang.StringBuilder r5 = r5.append(r6)
            char r6 = r13.getPaddingChar()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = "'"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r10._reportInvalidBase64(r13, r4, r7, r5)
        L_0x0091:
            int r3 = r3 >> 4
            r12.append(r3)
            goto L_0x0007
        L_0x0098:
            int r2 = r3 << 6
            r2 = r2 | r6
            if (r4 < r0) goto L_0x00ac
            boolean r3 = r13.usesPadding()
            if (r3 != 0) goto L_0x00a9
            int r11 = r2 >> 2
            r12.appendTwoBytes(r11)
            goto L_0x00cf
        L_0x00a9:
            r10._reportBase64EOF()
        L_0x00ac:
            int r3 = r4 + 1
            char r4 = r11.charAt(r4)
            int r6 = r13.decodeBase64Char((char) r4)
            if (r6 >= 0) goto L_0x00c3
            if (r6 == r8) goto L_0x00bd
            r10._reportInvalidBase64(r13, r4, r7, r5)
        L_0x00bd:
            int r2 = r2 >> 2
            r12.appendTwoBytes(r2)
            goto L_0x00c9
        L_0x00c3:
            int r2 = r2 << 6
            r2 = r2 | r6
            r12.appendThreeBytes(r2)
        L_0x00c9:
            r2 = r3
            goto L_0x0007
        L_0x00cc:
            r2 = r3
            goto L_0x0009
        L_0x00cf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.base.ParserMinimalBase._decodeBase64(java.lang.String, com.fasterxml.jackson.core.util.ByteArrayBuilder, com.fasterxml.jackson.core.Base64Variant):void");
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
        throw _constructError(str2);
    }

    /* access modifiers changed from: protected */
    public void _reportBase64EOF() throws JsonParseException {
        throw _constructError("Unexpected end-of-String in base64 content");
    }

    /* access modifiers changed from: protected */
    public void _reportUnexpectedChar(int i, String str) throws JsonParseException {
        String str2 = "Unexpected character (" + _getCharDesc(i) + ")";
        if (str != null) {
            str2 = str2 + ": " + str;
        }
        _reportError(str2);
    }

    /* access modifiers changed from: protected */
    public void _reportInvalidEOF() throws JsonParseException {
        _reportInvalidEOF(" in " + this._currToken);
    }

    /* access modifiers changed from: protected */
    public void _reportInvalidEOF(String str) throws JsonParseException {
        _reportError("Unexpected end-of-input" + str);
    }

    /* access modifiers changed from: protected */
    public void _reportInvalidEOFInValue() throws JsonParseException {
        _reportInvalidEOF(" in a value");
    }

    /* access modifiers changed from: protected */
    public void _throwInvalidSpace(int i) throws JsonParseException {
        _reportError("Illegal character (" + _getCharDesc((char) i) + "): only regular white space (\\r, \\n, \\t) is allowed between tokens");
    }

    /* access modifiers changed from: protected */
    public void _throwUnquotedSpace(int i, String str) throws JsonParseException {
        if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS) || i >= 32) {
            _reportError("Illegal unquoted character (" + _getCharDesc((char) i) + "): has to be escaped using backslash to be included in " + str);
        }
    }

    /* access modifiers changed from: protected */
    public char _handleUnrecognizedCharacterEscape(char c) throws JsonProcessingException {
        if (isEnabled(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)) {
            return c;
        }
        if (c == '\'' && isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
            return c;
        }
        _reportError("Unrecognized character escape " + _getCharDesc(c));
        return c;
    }

    protected static final String _getCharDesc(int i) {
        char c = (char) i;
        if (Character.isISOControl(c)) {
            return "(CTRL-CHAR, code " + i + ")";
        }
        if (i > 255) {
            return "'" + c + "' (code " + i + " / 0x" + Integer.toHexString(i) + ")";
        }
        return "'" + c + "' (code " + i + ")";
    }

    /* access modifiers changed from: protected */
    public final void _reportError(String str) throws JsonParseException {
        throw _constructError(str);
    }

    /* access modifiers changed from: protected */
    public final void _wrapError(String str, Throwable th) throws JsonParseException {
        throw _constructError(str, th);
    }

    /* access modifiers changed from: protected */
    public final void _throwInternal() {
        VersionUtil.throwInternal();
    }

    /* access modifiers changed from: protected */
    public final JsonParseException _constructError(String str, Throwable th) {
        return new JsonParseException(str, getCurrentLocation(), th);
    }
}
