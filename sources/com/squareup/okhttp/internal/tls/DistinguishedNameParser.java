package com.squareup.okhttp.internal.tls;

import com.facebook.imageutils.JfifUtil;
import javax.security.auth.x500.X500Principal;

final class DistinguishedNameParser {
    private int beg;
    private char[] chars;
    private int cur;

    /* renamed from: dn */
    private final String f184dn;
    private int end;
    private final int length;
    private int pos;

    public DistinguishedNameParser(X500Principal principal) {
        String name = principal.getName("RFC2253");
        this.f184dn = name;
        this.length = name.length();
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x0015 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0017  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String nextAT() {
        /*
            r7 = this;
        L_0x0000:
            int r0 = r7.pos
            int r1 = r7.length
            r2 = 32
            if (r0 >= r1) goto L_0x0013
            char[] r3 = r7.chars
            char r3 = r3[r0]
            if (r3 != r2) goto L_0x0013
            int r0 = r0 + 1
            r7.pos = r0
            goto L_0x0000
        L_0x0013:
            if (r0 != r1) goto L_0x0017
            r0 = 0
            return r0
        L_0x0017:
            r7.beg = r0
            int r0 = r0 + 1
            r7.pos = r0
        L_0x001d:
            int r0 = r7.pos
            int r1 = r7.length
            r3 = 61
            if (r0 >= r1) goto L_0x0034
            char[] r4 = r7.chars
            char r5 = r4[r0]
            if (r5 == r3) goto L_0x0034
            char r4 = r4[r0]
            if (r4 == r2) goto L_0x0034
            int r0 = r0 + 1
            r7.pos = r0
            goto L_0x001d
        L_0x0034:
            java.lang.String r4 = "Unexpected end of DN: "
            if (r0 >= r1) goto L_0x00dc
            r7.end = r0
            char[] r1 = r7.chars
            char r0 = r1[r0]
            if (r0 != r2) goto L_0x0077
        L_0x0040:
            int r0 = r7.pos
            int r1 = r7.length
            if (r0 >= r1) goto L_0x0055
            char[] r5 = r7.chars
            char r6 = r5[r0]
            if (r6 == r3) goto L_0x0055
            char r5 = r5[r0]
            if (r5 != r2) goto L_0x0055
            int r0 = r0 + 1
            r7.pos = r0
            goto L_0x0040
        L_0x0055:
            char[] r5 = r7.chars
            char r5 = r5[r0]
            if (r5 != r3) goto L_0x005e
            if (r0 == r1) goto L_0x005e
            goto L_0x0077
        L_0x005e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r2 = r7.f184dn
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0077:
            int r0 = r7.pos
            int r0 = r0 + 1
            r7.pos = r0
        L_0x007d:
            int r0 = r7.pos
            int r1 = r7.length
            if (r0 >= r1) goto L_0x008e
            char[] r1 = r7.chars
            char r1 = r1[r0]
            if (r1 != r2) goto L_0x008e
            int r0 = r0 + 1
            r7.pos = r0
            goto L_0x007d
        L_0x008e:
            int r0 = r7.end
            int r1 = r7.beg
            int r0 = r0 - r1
            r2 = 4
            if (r0 <= r2) goto L_0x00cf
            char[] r0 = r7.chars
            int r3 = r1 + 3
            char r3 = r0[r3]
            r4 = 46
            if (r3 != r4) goto L_0x00cf
            char r3 = r0[r1]
            r4 = 79
            if (r3 == r4) goto L_0x00ac
            char r3 = r0[r1]
            r4 = 111(0x6f, float:1.56E-43)
            if (r3 != r4) goto L_0x00cf
        L_0x00ac:
            int r3 = r1 + 1
            char r3 = r0[r3]
            r4 = 73
            if (r3 == r4) goto L_0x00bc
            int r3 = r1 + 1
            char r3 = r0[r3]
            r4 = 105(0x69, float:1.47E-43)
            if (r3 != r4) goto L_0x00cf
        L_0x00bc:
            int r3 = r1 + 2
            char r3 = r0[r3]
            r4 = 68
            if (r3 == r4) goto L_0x00cc
            int r3 = r1 + 2
            char r0 = r0[r3]
            r3 = 100
            if (r0 != r3) goto L_0x00cf
        L_0x00cc:
            int r1 = r1 + r2
            r7.beg = r1
        L_0x00cf:
            java.lang.String r0 = new java.lang.String
            char[] r1 = r7.chars
            int r2 = r7.beg
            int r3 = r7.end
            int r3 = r3 - r2
            r0.<init>(r1, r2, r3)
            return r0
        L_0x00dc:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r2 = r7.f184dn
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.tls.DistinguishedNameParser.nextAT():java.lang.String");
    }

    private String quotedAV() {
        int i = this.pos + 1;
        this.pos = i;
        this.beg = i;
        this.end = i;
        while (true) {
            int i2 = this.pos;
            if (i2 != this.length) {
                char[] cArr = this.chars;
                if (cArr[i2] == '\"') {
                    this.pos = i2 + 1;
                    while (true) {
                        int i3 = this.pos;
                        if (i3 >= this.length || this.chars[i3] != ' ') {
                            char[] cArr2 = this.chars;
                            int i4 = this.beg;
                        } else {
                            this.pos = i3 + 1;
                        }
                    }
                    char[] cArr22 = this.chars;
                    int i42 = this.beg;
                    return new String(cArr22, i42, this.end - i42);
                }
                if (cArr[i2] == '\\') {
                    cArr[this.end] = getEscaped();
                } else {
                    cArr[this.end] = cArr[i2];
                }
                this.pos++;
                this.end++;
            } else {
                throw new IllegalStateException("Unexpected end of DN: " + this.f184dn);
            }
        }
    }

    private String hexAV() {
        int i;
        int i2 = this.pos;
        if (i2 + 4 < this.length) {
            this.beg = i2;
            this.pos = i2 + 1;
            while (true) {
                i = this.pos;
                if (i == this.length) {
                    break;
                }
                char[] cArr = this.chars;
                if (cArr[i] == '+' || cArr[i] == ',' || cArr[i] == ';') {
                    break;
                } else if (cArr[i] == ' ') {
                    this.end = i;
                    this.pos = i + 1;
                    while (true) {
                        int i3 = this.pos;
                        if (i3 >= this.length || this.chars[i3] != ' ') {
                            break;
                        }
                        this.pos = i3 + 1;
                    }
                } else {
                    if (cArr[i] >= 'A' && cArr[i] <= 'F') {
                        cArr[i] = (char) (cArr[i] + ' ');
                    }
                    this.pos = i + 1;
                }
            }
            this.end = i;
            int i4 = this.end;
            int i5 = this.beg;
            int hexLen = i4 - i5;
            if (hexLen < 5 || (hexLen & 1) == 0) {
                throw new IllegalStateException("Unexpected end of DN: " + this.f184dn);
            }
            byte[] encoded = new byte[(hexLen / 2)];
            int p = i5 + 1;
            for (int i6 = 0; i6 < encoded.length; i6++) {
                encoded[i6] = (byte) getByte(p);
                p += 2;
            }
            return new String(this.chars, this.beg, hexLen);
        }
        throw new IllegalStateException("Unexpected end of DN: " + this.f184dn);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0097, code lost:
        r1 = r8.chars;
        r2 = r8.beg;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a3, code lost:
        return new java.lang.String(r1, r2, r8.cur - r2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String escapedAV() {
        /*
            r8 = this;
            int r0 = r8.pos
            r8.beg = r0
            r8.end = r0
        L_0x0006:
            int r0 = r8.pos
            int r1 = r8.length
            if (r0 < r1) goto L_0x0019
            java.lang.String r0 = new java.lang.String
            char[] r1 = r8.chars
            int r2 = r8.beg
            int r3 = r8.end
            int r3 = r3 - r2
            r0.<init>(r1, r2, r3)
            return r0
        L_0x0019:
            char[] r1 = r8.chars
            char r2 = r1[r0]
            r3 = 44
            r4 = 43
            r5 = 59
            r6 = 32
            if (r2 == r6) goto L_0x0060
            if (r2 == r5) goto L_0x0053
            r5 = 92
            if (r2 == r5) goto L_0x0040
            if (r2 == r4) goto L_0x0053
            if (r2 == r3) goto L_0x0053
            int r2 = r8.end
            int r3 = r2 + 1
            r8.end = r3
            char r3 = r1[r0]
            r1[r2] = r3
            int r0 = r0 + 1
            r8.pos = r0
            goto L_0x0006
        L_0x0040:
            int r0 = r8.end
            int r2 = r0 + 1
            r8.end = r2
            char r2 = r8.getEscaped()
            r1[r0] = r2
            int r0 = r8.pos
            int r0 = r0 + 1
            r8.pos = r0
            goto L_0x0006
        L_0x0053:
            java.lang.String r0 = new java.lang.String
            char[] r1 = r8.chars
            int r2 = r8.beg
            int r3 = r8.end
            int r3 = r3 - r2
            r0.<init>(r1, r2, r3)
            return r0
        L_0x0060:
            int r2 = r8.end
            r8.cur = r2
            int r0 = r0 + 1
            r8.pos = r0
            int r0 = r2 + 1
            r8.end = r0
            r1[r2] = r6
        L_0x006e:
            int r0 = r8.pos
            int r1 = r8.length
            if (r0 >= r1) goto L_0x0087
            char[] r2 = r8.chars
            char r7 = r2[r0]
            if (r7 != r6) goto L_0x0087
            int r1 = r8.end
            int r7 = r1 + 1
            r8.end = r7
            r2[r1] = r6
            int r0 = r0 + 1
            r8.pos = r0
            goto L_0x006e
        L_0x0087:
            if (r0 == r1) goto L_0x0097
            char[] r1 = r8.chars
            char r2 = r1[r0]
            if (r2 == r3) goto L_0x0097
            char r2 = r1[r0]
            if (r2 == r4) goto L_0x0097
            char r0 = r1[r0]
            if (r0 != r5) goto L_0x0006
        L_0x0097:
            java.lang.String r0 = new java.lang.String
            char[] r1 = r8.chars
            int r2 = r8.beg
            int r3 = r8.cur
            int r3 = r3 - r2
            r0.<init>(r1, r2, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.tls.DistinguishedNameParser.escapedAV():java.lang.String");
    }

    private char getEscaped() {
        int i = this.pos + 1;
        this.pos = i;
        if (i != this.length) {
            char[] cArr = this.chars;
            char c = cArr[i];
            if (!(c == ' ' || c == '%' || c == '\\' || c == '_' || c == '\"' || c == '#')) {
                switch (c) {
                    case '*':
                    case '+':
                    case ',':
                        break;
                    default:
                        switch (c) {
                            case ';':
                            case '<':
                            case '=':
                            case '>':
                                break;
                            default:
                                return getUTF8();
                        }
                }
            }
            return cArr[i];
        }
        throw new IllegalStateException("Unexpected end of DN: " + this.f184dn);
    }

    private char getUTF8() {
        int count;
        int res;
        int res2 = getByte(this.pos);
        this.pos++;
        if (res2 < 128) {
            return (char) res2;
        }
        if (res2 < 192 || res2 > 247) {
            return '?';
        }
        if (res2 <= 223) {
            count = 1;
            res = res2 & 31;
        } else if (res2 <= 239) {
            count = 2;
            res = res2 & 15;
        } else {
            count = 3;
            res = res2 & 7;
        }
        for (int i = 0; i < count; i++) {
            int i2 = this.pos + 1;
            this.pos = i2;
            if (i2 == this.length || this.chars[i2] != '\\') {
                return '?';
            }
            int i3 = i2 + 1;
            this.pos = i3;
            int b = getByte(i3);
            this.pos++;
            if ((b & JfifUtil.MARKER_SOFn) != 128) {
                return '?';
            }
            res = (res << 6) + (b & 63);
        }
        return (char) res;
    }

    private int getByte(int position) {
        int b1;
        int b2;
        if (position + 1 < this.length) {
            char[] cArr = this.chars;
            char b12 = cArr[position];
            if (b12 >= '0' && b12 <= '9') {
                b1 = b12 - '0';
            } else if (b12 >= 'a' && b12 <= 'f') {
                b1 = b12 - 'W';
            } else if (b12 < 'A' || b12 > 'F') {
                throw new IllegalStateException("Malformed DN: " + this.f184dn);
            } else {
                b1 = b12 - '7';
            }
            char b22 = cArr[position + 1];
            if (b22 >= '0' && b22 <= '9') {
                b2 = b22 - '0';
            } else if (b22 >= 'a' && b22 <= 'f') {
                b2 = b22 - 'W';
            } else if (b22 < 'A' || b22 > 'F') {
                throw new IllegalStateException("Malformed DN: " + this.f184dn);
            } else {
                b2 = b22 - '7';
            }
            return (b1 << 4) + b2;
        }
        throw new IllegalStateException("Malformed DN: " + this.f184dn);
    }

    public String findMostSpecific(String attributeType) {
        this.pos = 0;
        this.beg = 0;
        this.end = 0;
        this.cur = 0;
        this.chars = this.f184dn.toCharArray();
        String attType = nextAT();
        if (attType == null) {
            return null;
        }
        do {
            String attValue = "";
            int i = this.pos;
            if (i == this.length) {
                return null;
            }
            char c = this.chars[i];
            if (c == '\"') {
                attValue = quotedAV();
            } else if (c == '#') {
                attValue = hexAV();
            } else if (!(c == '+' || c == ',' || c == ';')) {
                attValue = escapedAV();
            }
            if (attributeType.equalsIgnoreCase(attType)) {
                return attValue;
            }
            int i2 = this.pos;
            if (i2 >= this.length) {
                return null;
            }
            char[] cArr = this.chars;
            if (cArr[i2] == ',' || cArr[i2] == ';' || cArr[i2] == '+') {
                this.pos = i2 + 1;
                attType = nextAT();
            } else {
                throw new IllegalStateException("Malformed DN: " + this.f184dn);
            }
        } while (attType != null);
        throw new IllegalStateException("Malformed DN: " + this.f184dn);
    }
}
