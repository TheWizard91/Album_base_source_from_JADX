package com.firebase.tubesock;

import com.google.common.base.Ascii;
import java.util.Arrays;

public class Base64 {

    /* renamed from: CA */
    private static final char[] f101CA;

    /* renamed from: IA */
    private static final int[] f102IA;

    static {
        char[] charArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        f101CA = charArray;
        int[] iArr = new int[256];
        f102IA = iArr;
        Arrays.fill(iArr, -1);
        int iS = charArray.length;
        for (int i = 0; i < iS; i++) {
            f102IA[f101CA[i]] = i;
        }
        f102IA[61] = 0;
    }

    public static final char[] encodeToChar(byte[] sArr, boolean lineSep) {
        byte[] bArr = sArr;
        int i = 0;
        int sLen = bArr != null ? bArr.length : 0;
        if (sLen == 0) {
            return new char[0];
        }
        int eLen = (sLen / 3) * 3;
        int cCnt = (((sLen - 1) / 3) + 1) << 2;
        int dLen = (lineSep ? ((cCnt - 1) / 76) << 1 : 0) + cCnt;
        char[] dArr = new char[dLen];
        int i2 = 0;
        int d = 0;
        int cc = 0;
        while (i2 < eLen) {
            int s = i2 + 1;
            int s2 = s + 1;
            int i3 = ((bArr[i2] & 255) << Ascii.DLE) | ((bArr[s] & 255) << 8);
            int s3 = s2 + 1;
            int i4 = i3 | (bArr[s2] & 255);
            int d2 = d + 1;
            char[] cArr = f101CA;
            dArr[d] = cArr[(i4 >>> 18) & 63];
            int d3 = d2 + 1;
            dArr[d2] = cArr[(i4 >>> 12) & 63];
            int d4 = d3 + 1;
            dArr[d3] = cArr[(i4 >>> 6) & 63];
            d = d4 + 1;
            dArr[d4] = cArr[i4 & 63];
            if (lineSep && (cc = cc + 1) == 19 && d < dLen - 2) {
                int d5 = d + 1;
                dArr[d] = 13;
                d = d5 + 1;
                dArr[d5] = 10;
                cc = 0;
            }
            i2 = s3;
        }
        int left = sLen - eLen;
        if (left > 0) {
            int i5 = (bArr[eLen] & 255) << 10;
            if (left == 2) {
                i = (bArr[sLen - 1] & 255) << 2;
            }
            int i6 = i | i5;
            char[] cArr2 = f101CA;
            dArr[dLen - 4] = cArr2[i6 >> 12];
            dArr[dLen - 3] = cArr2[(i6 >>> 6) & 63];
            dArr[dLen - 2] = left == 2 ? cArr2[i6 & 63] : '=';
            dArr[dLen - 1] = '=';
        }
        return dArr;
    }

    public static final byte[] decode(char[] sArr) {
        int sLen = sArr != null ? sArr.length : 0;
        if (sLen == 0) {
            return new byte[0];
        }
        int sepCnt = 0;
        for (int i = 0; i < sLen; i++) {
            if (f102IA[sArr[i]] < 0) {
                sepCnt++;
            }
        }
        if ((sLen - sepCnt) % 4 != 0) {
            return null;
        }
        int pad = 0;
        int i2 = sLen;
        while (i2 > 1) {
            i2--;
            if (f102IA[sArr[i2]] > 0) {
                break;
            } else if (sArr[i2] == '=') {
                pad++;
            }
        }
        int len = (((sLen - sepCnt) * 6) >> 3) - pad;
        byte[] dArr = new byte[len];
        int s = 0;
        int d = 0;
        while (d < len) {
            int i3 = 0;
            int j = 0;
            while (j < 4) {
                int s2 = s + 1;
                int c = f102IA[sArr[s]];
                if (c >= 0) {
                    i3 |= c << (18 - (j * 6));
                } else {
                    j--;
                }
                j++;
                s = s2;
            }
            int j2 = d + 1;
            dArr[d] = (byte) (i3 >> 16);
            if (j2 < len) {
                d = j2 + 1;
                dArr[j2] = (byte) (i3 >> 8);
                if (d < len) {
                    dArr[d] = (byte) i3;
                    d++;
                }
            } else {
                d = j2;
            }
        }
        return dArr;
    }

    public static final byte[] decodeFast(char[] sArr) {
        int sLen = sArr.length;
        int sepCnt = 0;
        if (sLen == 0) {
            return new byte[0];
        }
        int sIx = 0;
        int eIx = sLen - 1;
        while (sIx < eIx && f102IA[sArr[sIx]] < 0) {
            sIx++;
        }
        while (eIx > 0 && f102IA[sArr[eIx]] < 0) {
            eIx--;
        }
        int pad = sArr[eIx] == '=' ? sArr[eIx + -1] == '=' ? 2 : 1 : 0;
        int cCnt = (eIx - sIx) + 1;
        if (sLen > 76) {
            if (sArr[76] == 13) {
                sepCnt = cCnt / 78;
            }
            sepCnt <<= 1;
        }
        int len = (((cCnt - sepCnt) * 6) >> 3) - pad;
        byte[] dArr = new byte[len];
        int d = 0;
        int cc = 0;
        int eLen = (len / 3) * 3;
        while (d < eLen) {
            int[] iArr = f102IA;
            int sIx2 = sIx + 1;
            int sIx3 = sIx2 + 1;
            int i = (iArr[sArr[sIx]] << 18) | (iArr[sArr[sIx2]] << 12);
            int sIx4 = sIx3 + 1;
            int i2 = i | (iArr[sArr[sIx3]] << 6);
            int sIx5 = sIx4 + 1;
            int i3 = i2 | iArr[sArr[sIx4]];
            int d2 = d + 1;
            dArr[d] = (byte) (i3 >> 16);
            int d3 = d2 + 1;
            dArr[d2] = (byte) (i3 >> 8);
            int d4 = d3 + 1;
            dArr[d3] = (byte) i3;
            if (sepCnt > 0 && (cc = cc + 1) == 19) {
                sIx5 += 2;
                cc = 0;
            }
            sIx = sIx5;
            d = d4;
        }
        if (d < len) {
            int i4 = 0;
            int j = 0;
            while (sIx <= eIx - pad) {
                i4 |= f102IA[sArr[sIx]] << (18 - (j * 6));
                j++;
                sIx++;
            }
            int r = 16;
            while (d < len) {
                dArr[d] = (byte) (i4 >> r);
                r -= 8;
                d++;
            }
        }
        return dArr;
    }

    public static final byte[] encodeToByte(byte[] sArr, boolean lineSep) {
        byte[] bArr = sArr;
        int i = 0;
        int sLen = bArr != null ? bArr.length : 0;
        if (sLen == 0) {
            return new byte[0];
        }
        int eLen = (sLen / 3) * 3;
        int cCnt = (((sLen - 1) / 3) + 1) << 2;
        int dLen = (lineSep ? ((cCnt - 1) / 76) << 1 : 0) + cCnt;
        byte[] dArr = new byte[dLen];
        int i2 = 0;
        int d = 0;
        int cc = 0;
        while (i2 < eLen) {
            int s = i2 + 1;
            int s2 = s + 1;
            int i3 = ((bArr[i2] & 255) << Ascii.DLE) | ((bArr[s] & 255) << 8);
            int s3 = s2 + 1;
            int i4 = i3 | (bArr[s2] & 255);
            int d2 = d + 1;
            char[] cArr = f101CA;
            dArr[d] = (byte) cArr[(i4 >>> 18) & 63];
            int d3 = d2 + 1;
            dArr[d2] = (byte) cArr[(i4 >>> 12) & 63];
            int d4 = d3 + 1;
            dArr[d3] = (byte) cArr[(i4 >>> 6) & 63];
            d = d4 + 1;
            dArr[d4] = (byte) cArr[i4 & 63];
            if (lineSep && (cc = cc + 1) == 19 && d < dLen - 2) {
                int d5 = d + 1;
                dArr[d] = Ascii.f1604CR;
                d = d5 + 1;
                dArr[d5] = 10;
                cc = 0;
            }
            i2 = s3;
        }
        int left = sLen - eLen;
        if (left > 0) {
            int i5 = (bArr[eLen] & 255) << 10;
            if (left == 2) {
                i = (bArr[sLen - 1] & 255) << 2;
            }
            int i6 = i | i5;
            char[] cArr2 = f101CA;
            dArr[dLen - 4] = (byte) cArr2[i6 >> 12];
            dArr[dLen - 3] = (byte) cArr2[(i6 >>> 6) & 63];
            dArr[dLen - 2] = left == 2 ? (byte) cArr2[i6 & 63] : 61;
            dArr[dLen - 1] = 61;
        }
        return dArr;
    }

    public static final byte[] decode(byte[] sArr) {
        int sepCnt = 0;
        for (byte b : sArr) {
            if (f102IA[b & 255] < 0) {
                sepCnt++;
            }
        }
        if ((sLen - sepCnt) % 4 != 0) {
            return null;
        }
        int pad = 0;
        int i = sLen;
        while (i > 1) {
            i--;
            if (f102IA[sArr[i] & 255] > 0) {
                break;
            } else if (sArr[i] == 61) {
                pad++;
            }
        }
        int len = (((sLen - sepCnt) * 6) >> 3) - pad;
        byte[] dArr = new byte[len];
        int s = 0;
        int d = 0;
        while (d < len) {
            int i2 = 0;
            int j = 0;
            while (j < 4) {
                int s2 = s + 1;
                int c = f102IA[sArr[s] & 255];
                if (c >= 0) {
                    i2 |= c << (18 - (j * 6));
                } else {
                    j--;
                }
                j++;
                s = s2;
            }
            int j2 = d + 1;
            dArr[d] = (byte) (i2 >> 16);
            if (j2 < len) {
                d = j2 + 1;
                dArr[j2] = (byte) (i2 >> 8);
                if (d < len) {
                    dArr[d] = (byte) i2;
                    d++;
                }
            } else {
                d = j2;
            }
        }
        return dArr;
    }

    public static final byte[] decodeFast(byte[] sArr) {
        int sLen = sArr.length;
        int sepCnt = 0;
        if (sLen == 0) {
            return new byte[0];
        }
        int sIx = 0;
        int eIx = sLen - 1;
        while (sIx < eIx && f102IA[sArr[sIx] & 255] < 0) {
            sIx++;
        }
        while (eIx > 0 && f102IA[sArr[eIx] & 255] < 0) {
            eIx--;
        }
        int pad = sArr[eIx] == 61 ? sArr[eIx + -1] == 61 ? 2 : 1 : 0;
        int cCnt = (eIx - sIx) + 1;
        if (sLen > 76) {
            if (sArr[76] == 13) {
                sepCnt = cCnt / 78;
            }
            sepCnt <<= 1;
        }
        int len = (((cCnt - sepCnt) * 6) >> 3) - pad;
        byte[] dArr = new byte[len];
        int d = 0;
        int cc = 0;
        int eLen = (len / 3) * 3;
        while (d < eLen) {
            int[] iArr = f102IA;
            int sIx2 = sIx + 1;
            int sIx3 = sIx2 + 1;
            int i = (iArr[sArr[sIx]] << 18) | (iArr[sArr[sIx2]] << 12);
            int sIx4 = sIx3 + 1;
            int i2 = i | (iArr[sArr[sIx3]] << 6);
            int sIx5 = sIx4 + 1;
            int i3 = i2 | iArr[sArr[sIx4]];
            int d2 = d + 1;
            dArr[d] = (byte) (i3 >> 16);
            int d3 = d2 + 1;
            dArr[d2] = (byte) (i3 >> 8);
            int d4 = d3 + 1;
            dArr[d3] = (byte) i3;
            if (sepCnt > 0 && (cc = cc + 1) == 19) {
                sIx5 += 2;
                cc = 0;
            }
            sIx = sIx5;
            d = d4;
        }
        if (d < len) {
            int i4 = 0;
            int j = 0;
            while (sIx <= eIx - pad) {
                i4 |= f102IA[sArr[sIx]] << (18 - (j * 6));
                j++;
                sIx++;
            }
            int r = 16;
            while (d < len) {
                dArr[d] = (byte) (i4 >> r);
                r -= 8;
                d++;
            }
        }
        return dArr;
    }

    public static final String encodeToString(byte[] sArr, boolean lineSep) {
        return new String(encodeToChar(sArr, lineSep));
    }

    public static final byte[] decode(String str) {
        int sLen = str != null ? str.length() : 0;
        if (sLen == 0) {
            return new byte[0];
        }
        int sepCnt = 0;
        for (int i = 0; i < sLen; i++) {
            if (f102IA[str.charAt(i)] < 0) {
                sepCnt++;
            }
        }
        if ((sLen - sepCnt) % 4 != 0) {
            return null;
        }
        int pad = 0;
        int i2 = sLen;
        while (i2 > 1) {
            i2--;
            if (f102IA[str.charAt(i2)] > 0) {
                break;
            } else if (str.charAt(i2) == '=') {
                pad++;
            }
        }
        int len = (((sLen - sepCnt) * 6) >> 3) - pad;
        byte[] dArr = new byte[len];
        int s = 0;
        int d = 0;
        while (d < len) {
            int i3 = 0;
            int j = 0;
            while (j < 4) {
                int s2 = s + 1;
                int c = f102IA[str.charAt(s)];
                if (c >= 0) {
                    i3 |= c << (18 - (j * 6));
                } else {
                    j--;
                }
                j++;
                s = s2;
            }
            int j2 = d + 1;
            dArr[d] = (byte) (i3 >> 16);
            if (j2 < len) {
                d = j2 + 1;
                dArr[j2] = (byte) (i3 >> 8);
                if (d < len) {
                    dArr[d] = (byte) i3;
                    d++;
                }
            } else {
                d = j2;
            }
        }
        return dArr;
    }

    public static final byte[] decodeFast(String s) {
        int sLen = s.length();
        int sepCnt = 0;
        if (sLen == 0) {
            return new byte[0];
        }
        int sIx = 0;
        int eIx = sLen - 1;
        while (sIx < eIx && f102IA[s.charAt(sIx) & 255] < 0) {
            sIx++;
        }
        while (eIx > 0 && f102IA[s.charAt(eIx) & 255] < 0) {
            eIx--;
        }
        int pad = s.charAt(eIx) == '=' ? s.charAt(eIx + -1) == '=' ? 2 : 1 : 0;
        int cCnt = (eIx - sIx) + 1;
        if (sLen > 76) {
            if (s.charAt(76) == 13) {
                sepCnt = cCnt / 78;
            }
            sepCnt <<= 1;
        }
        int len = (((cCnt - sepCnt) * 6) >> 3) - pad;
        byte[] dArr = new byte[len];
        int d = 0;
        int cc = 0;
        int eLen = (len / 3) * 3;
        while (d < eLen) {
            int[] iArr = f102IA;
            int sIx2 = sIx + 1;
            int sIx3 = sIx2 + 1;
            int i = (iArr[s.charAt(sIx)] << 18) | (iArr[s.charAt(sIx2)] << 12);
            int sIx4 = sIx3 + 1;
            int i2 = i | (iArr[s.charAt(sIx3)] << 6);
            int sIx5 = sIx4 + 1;
            int i3 = i2 | iArr[s.charAt(sIx4)];
            int d2 = d + 1;
            dArr[d] = (byte) (i3 >> 16);
            int d3 = d2 + 1;
            dArr[d2] = (byte) (i3 >> 8);
            int d4 = d3 + 1;
            dArr[d3] = (byte) i3;
            if (sepCnt > 0 && (cc = cc + 1) == 19) {
                sIx5 += 2;
                cc = 0;
            }
            sIx = sIx5;
            d = d4;
        }
        if (d < len) {
            int i4 = 0;
            int j = 0;
            while (sIx <= eIx - pad) {
                i4 |= f102IA[s.charAt(sIx)] << (18 - (j * 6));
                j++;
                sIx++;
            }
            int r = 16;
            while (d < len) {
                dArr[d] = (byte) (i4 >> r);
                r -= 8;
                d++;
            }
        }
        return dArr;
    }
}
