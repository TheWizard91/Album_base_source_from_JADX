package com.firebase.client.utilities;

import com.google.common.base.Ascii;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.zip.GZIPOutputStream;
import kotlinx.coroutines.scheduling.WorkQueueKt;

public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DECODE = 0;
    public static final int DONT_GUNZIP = 4;
    public static final int DO_BREAK_LINES = 8;
    public static final int ENCODE = 1;
    private static final byte EQUALS_SIGN = 61;
    private static final byte EQUALS_SIGN_ENC = -1;
    public static final int GZIP = 2;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte NEW_LINE = 10;
    public static final int NO_OPTIONS = 0;
    public static final int ORDERED = 32;
    private static final String PREFERRED_ENCODING = "US-ASCII";
    public static final int URL_SAFE = 16;
    private static final byte WHITE_SPACE_ENC = -5;
    private static final byte[] _ORDERED_ALPHABET = {45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
    private static final byte[] _ORDERED_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -9, -9, -9, -1, -9, -9, -9, Ascii.f1617VT, Ascii.f1606FF, Ascii.f1604CR, Ascii.f1614SO, Ascii.f1613SI, Ascii.DLE, 17, Ascii.DC2, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.f1605EM, Ascii.SUB, Ascii.ESC, Ascii.f1607FS, Ascii.f1608GS, Ascii.f1612RS, Ascii.f1616US, 32, 33, 34, 35, 36, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, EQUALS_SIGN, 62, 63, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
    private static final byte[] _STANDARD_ALPHABET = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] _STANDARD_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, EQUALS_SIGN, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Ascii.f1617VT, Ascii.f1606FF, Ascii.f1604CR, Ascii.f1614SO, Ascii.f1613SI, Ascii.DLE, 17, Ascii.DC2, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.f1605EM, -9, -9, -9, -9, -9, -9, Ascii.SUB, Ascii.ESC, Ascii.f1607FS, Ascii.f1608GS, Ascii.f1612RS, Ascii.f1616US, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
    private static final byte[] _URL_SAFE_ALPHABET = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private static final byte[] _URL_SAFE_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, EQUALS_SIGN, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Ascii.f1617VT, Ascii.f1606FF, Ascii.f1604CR, Ascii.f1614SO, Ascii.f1613SI, Ascii.DLE, 17, Ascii.DC2, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.f1605EM, -9, -9, -9, -9, 63, -9, Ascii.SUB, Ascii.ESC, Ascii.f1607FS, Ascii.f1608GS, Ascii.f1612RS, Ascii.f1616US, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};

    private static final byte[] getAlphabet(int options) {
        if ((options & 16) == 16) {
            return _URL_SAFE_ALPHABET;
        }
        if ((options & 32) == 32) {
            return _ORDERED_ALPHABET;
        }
        return _STANDARD_ALPHABET;
    }

    /* access modifiers changed from: private */
    public static final byte[] getDecodabet(int options) {
        if ((options & 16) == 16) {
            return _URL_SAFE_DECODABET;
        }
        if ((options & 32) == 32) {
            return _ORDERED_DECODABET;
        }
        return _STANDARD_DECODABET;
    }

    private Base64() {
    }

    /* access modifiers changed from: private */
    public static byte[] encode3to4(byte[] b4, byte[] threeBytes, int numSigBytes, int options) {
        encode3to4(threeBytes, 0, numSigBytes, b4, 0, options);
        return b4;
    }

    /* access modifiers changed from: private */
    public static byte[] encode3to4(byte[] source, int srcOffset, int numSigBytes, byte[] destination, int destOffset, int options) {
        byte[] ALPHABET = getAlphabet(options);
        int i = 0;
        int i2 = (numSigBytes > 0 ? (source[srcOffset] << Ascii.CAN) >>> 8 : 0) | (numSigBytes > 1 ? (source[srcOffset + 1] << Ascii.CAN) >>> 16 : 0);
        if (numSigBytes > 2) {
            i = (source[srcOffset + 2] << Ascii.CAN) >>> 24;
        }
        int inBuff = i | i2;
        if (numSigBytes == 1) {
            destination[destOffset] = ALPHABET[inBuff >>> 18];
            destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 63];
            destination[destOffset + 2] = EQUALS_SIGN;
            destination[destOffset + 3] = EQUALS_SIGN;
            return destination;
        } else if (numSigBytes == 2) {
            destination[destOffset] = ALPHABET[inBuff >>> 18];
            destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 63];
            destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 63];
            destination[destOffset + 3] = EQUALS_SIGN;
            return destination;
        } else if (numSigBytes != 3) {
            return destination;
        } else {
            destination[destOffset] = ALPHABET[inBuff >>> 18];
            destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 63];
            destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 63];
            destination[destOffset + 3] = ALPHABET[inBuff & 63];
            return destination;
        }
    }

    public static void encode(ByteBuffer raw, ByteBuffer encoded) {
        byte[] raw3 = new byte[3];
        byte[] enc4 = new byte[4];
        while (raw.hasRemaining()) {
            int rem = Math.min(3, raw.remaining());
            raw.get(raw3, 0, rem);
            encode3to4(enc4, raw3, rem, 0);
            encoded.put(enc4);
        }
    }

    public static void encode(ByteBuffer raw, CharBuffer encoded) {
        byte[] raw3 = new byte[3];
        byte[] enc4 = new byte[4];
        while (raw.hasRemaining()) {
            int rem = Math.min(3, raw.remaining());
            raw.get(raw3, 0, rem);
            encode3to4(enc4, raw3, rem, 0);
            for (int i = 0; i < 4; i++) {
                encoded.put((char) (enc4[i] & 255));
            }
        }
    }

    public static String encodeObject(Serializable serializableObject) throws IOException {
        return encodeObject(serializableObject, 0);
    }

    public static String encodeObject(Serializable serializableObject, int options) throws IOException {
        ObjectOutputStream oos;
        if (serializableObject != null) {
            ByteArrayOutputStream baos = null;
            java.io.OutputStream b64os = null;
            GZIPOutputStream gzos = null;
            ObjectOutputStream oos2 = null;
            try {
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                java.io.OutputStream b64os2 = new OutputStream(baos2, options | 1);
                if ((options & 2) != 0) {
                    gzos = new GZIPOutputStream(b64os2);
                    oos = new ObjectOutputStream(gzos);
                } else {
                    oos = new ObjectOutputStream(b64os2);
                }
                oos.writeObject(serializableObject);
                try {
                    oos.close();
                } catch (Exception e) {
                }
                try {
                    gzos.close();
                } catch (Exception e2) {
                }
                try {
                    b64os2.close();
                } catch (Exception e3) {
                }
                try {
                    baos2.close();
                } catch (Exception e4) {
                }
                try {
                    return new String(baos2.toByteArray(), PREFERRED_ENCODING);
                } catch (UnsupportedEncodingException e5) {
                    return new String(baos2.toByteArray());
                }
            } catch (IOException e6) {
                throw e6;
            } catch (Throwable uue) {
                try {
                    oos2.close();
                } catch (Exception e7) {
                }
                try {
                    gzos.close();
                } catch (Exception e8) {
                }
                try {
                    b64os.close();
                } catch (Exception e9) {
                }
                try {
                    baos.close();
                } catch (Exception e10) {
                }
                throw uue;
            }
        } else {
            throw new NullPointerException("Cannot serialize a null object.");
        }
    }

    public static String encodeBytes(byte[] source) {
        try {
            String encoded = encodeBytes(source, 0, source.length, 0);
            if (encoded != null) {
                return encoded;
            }
            throw new AssertionError();
        } catch (IOException ex) {
            throw new AssertionError(ex.getMessage());
        }
    }

    public static String encodeBytes(byte[] source, int options) throws IOException {
        return encodeBytes(source, 0, source.length, options);
    }

    public static String encodeBytes(byte[] source, int off, int len) {
        try {
            String encoded = encodeBytes(source, off, len, 0);
            if (encoded != null) {
                return encoded;
            }
            throw new AssertionError();
        } catch (IOException ex) {
            throw new AssertionError(ex.getMessage());
        }
    }

    public static String encodeBytes(byte[] source, int off, int len, int options) throws IOException {
        byte[] encoded = encodeBytesToBytes(source, off, len, options);
        try {
            return new String(encoded, PREFERRED_ENCODING);
        } catch (UnsupportedEncodingException e) {
            return new String(encoded);
        }
    }

    public static byte[] encodeBytesToBytes(byte[] source) {
        try {
            return encodeBytesToBytes(source, 0, source.length, 0);
        } catch (IOException ex) {
            throw new AssertionError("IOExceptions only come from GZipping, which is turned off: " + ex.getMessage());
        }
    }

    public static byte[] encodeBytesToBytes(byte[] source, int off, int len, int options) throws IOException {
        int encLen;
        int e;
        byte[] bArr = source;
        int i = off;
        int i2 = len;
        if (bArr == null) {
            throw new NullPointerException("Cannot serialize a null array.");
        } else if (i < 0) {
            throw new IllegalArgumentException("Cannot have negative offset: " + i);
        } else if (i2 < 0) {
            throw new IllegalArgumentException("Cannot have length offset: " + i2);
        } else if (i + i2 > bArr.length) {
            throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", new Object[]{Integer.valueOf(off), Integer.valueOf(len), Integer.valueOf(bArr.length)}));
        } else if ((options & 2) != 0) {
            ByteArrayOutputStream baos = null;
            GZIPOutputStream gzos = null;
            OutputStream b64os = null;
            try {
                baos = new ByteArrayOutputStream();
                b64os = new OutputStream(baos, options | 1);
                gzos = new GZIPOutputStream(b64os);
                gzos.write(bArr, i, i2);
                gzos.close();
                try {
                    gzos.close();
                } catch (Exception e2) {
                }
                try {
                    b64os.close();
                } catch (Exception e3) {
                }
                try {
                    baos.close();
                } catch (Exception e4) {
                }
                return baos.toByteArray();
            } catch (IOException e5) {
                throw e5;
            } catch (Throwable th) {
                Throwable th2 = th;
                try {
                    gzos.close();
                } catch (Exception e6) {
                }
                try {
                    b64os.close();
                } catch (Exception e7) {
                }
                try {
                    baos.close();
                } catch (Exception e8) {
                }
                throw th2;
            }
        } else {
            boolean breakLines = (options & 8) != 0;
            int encLen2 = ((i2 / 3) * 4) + (i2 % 3 > 0 ? 4 : 0);
            if (breakLines) {
                encLen = encLen2 + (encLen2 / 76);
            } else {
                encLen = encLen2;
            }
            byte[] outBuff = new byte[encLen];
            int len2 = i2 - 2;
            int d = 0;
            int e9 = 0;
            int lineLength = 0;
            while (d < len2) {
                int d2 = d;
                encode3to4(source, d + i, 3, outBuff, e9, options);
                int lineLength2 = lineLength + 4;
                if (breakLines && lineLength2 >= 76) {
                    outBuff[e9 + 4] = 10;
                    e9++;
                    lineLength2 = 0;
                }
                lineLength = lineLength2;
                d = d2 + 3;
                e9 += 4;
            }
            int d3 = d;
            if (d3 < i2) {
                encode3to4(source, d3 + i, i2 - d3, outBuff, e9, options);
                e = e9 + 4;
            } else {
                e = e9;
            }
            if (e > outBuff.length - 1) {
                return outBuff;
            }
            byte[] finalOut = new byte[e];
            System.arraycopy(outBuff, 0, finalOut, 0, e);
            return finalOut;
        }
    }

    /* access modifiers changed from: private */
    public static int decode4to3(byte[] source, int srcOffset, byte[] destination, int destOffset, int options) {
        if (source == null) {
            throw new NullPointerException("Source array was null.");
        } else if (destination == null) {
            throw new NullPointerException("Destination array was null.");
        } else if (srcOffset < 0 || srcOffset + 3 >= source.length) {
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", new Object[]{Integer.valueOf(source.length), Integer.valueOf(srcOffset)}));
        } else if (destOffset < 0 || destOffset + 2 >= destination.length) {
            throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", new Object[]{Integer.valueOf(destination.length), Integer.valueOf(destOffset)}));
        } else {
            byte[] DECODABET = getDecodabet(options);
            if (source[srcOffset + 2] == 61) {
                destination[destOffset] = (byte) ((((DECODABET[source[srcOffset]] & 255) << Ascii.DC2) | ((DECODABET[source[srcOffset + 1]] & 255) << Ascii.f1606FF)) >>> 16);
                return 1;
            } else if (source[srcOffset + 3] == 61) {
                int outBuff = ((DECODABET[source[srcOffset]] & 255) << Ascii.DC2) | ((DECODABET[source[srcOffset + 1]] & 255) << Ascii.f1606FF) | ((DECODABET[source[srcOffset + 2]] & 255) << 6);
                destination[destOffset] = (byte) (outBuff >>> 16);
                destination[destOffset + 1] = (byte) (outBuff >>> 8);
                return 2;
            } else {
                int outBuff2 = ((DECODABET[source[srcOffset]] & 255) << Ascii.DC2) | ((DECODABET[source[srcOffset + 1]] & 255) << Ascii.f1606FF) | ((DECODABET[source[srcOffset + 2]] & 255) << 6) | (DECODABET[source[srcOffset + 3]] & 255);
                destination[destOffset] = (byte) (outBuff2 >> 16);
                destination[destOffset + 1] = (byte) (outBuff2 >> 8);
                destination[destOffset + 2] = (byte) outBuff2;
                return 3;
            }
        }
    }

    public static byte[] decode(byte[] source) throws IOException {
        return decode(source, 0, source.length, 0);
    }

    public static byte[] decode(byte[] source, int off, int len, int options) throws IOException {
        byte[] bArr = source;
        int i = len;
        if (bArr != null) {
            int i2 = 3;
            if (off < 0 || off + i > bArr.length) {
                int i3 = options;
                throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and process %d bytes.", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(off), Integer.valueOf(len)}));
            } else if (i == 0) {
                return new byte[0];
            } else {
                if (i >= 4) {
                    byte[] DECODABET = getDecodabet(options);
                    byte[] outBuff = new byte[((i * 3) / 4)];
                    int outBuffPosn = 0;
                    byte[] b4 = new byte[4];
                    int b4Posn = 0;
                    int i4 = off;
                    while (true) {
                        if (i4 >= off + i) {
                            int i5 = options;
                            break;
                        }
                        byte sbiDecode = DECODABET[bArr[i4] & 255];
                        if (sbiDecode >= -5) {
                            if (sbiDecode >= -1) {
                                int b4Posn2 = b4Posn + 1;
                                b4[b4Posn] = bArr[i4];
                                if (b4Posn2 > i2) {
                                    outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn, options);
                                    b4Posn = 0;
                                    if (bArr[i4] == 61) {
                                        break;
                                    }
                                } else {
                                    int i6 = options;
                                    b4Posn = b4Posn2;
                                }
                            } else {
                                int i7 = options;
                            }
                            i4++;
                            i2 = 3;
                        } else {
                            int i8 = options;
                            throw new IOException(String.format("Bad Base64 input character decimal %d in array position %d", new Object[]{Integer.valueOf(bArr[i4] & 255), Integer.valueOf(i4)}));
                        }
                    }
                    byte[] out = new byte[outBuffPosn];
                    System.arraycopy(outBuff, 0, out, 0, outBuffPosn);
                    return out;
                }
                int i9 = options;
                throw new IllegalArgumentException("Base64-encoded string must have at least four characters, but length specified was " + i);
            }
        } else {
            int i10 = options;
            throw new NullPointerException("Cannot decode null source array.");
        }
    }

    public static byte[] decode(String s) throws IOException {
        return decode(s, 0);
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:30:0x006b=Splitter:B:30:0x006b, B:51:0x008d=Splitter:B:51:0x008d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] decode(java.lang.String r10, int r11) throws java.io.IOException {
        /*
            if (r10 == 0) goto L_0x0091
            r0 = 0
            java.lang.String r1 = "US-ASCII"
            byte[] r0 = r10.getBytes(r1)     // Catch:{ UnsupportedEncodingException -> 0x000a }
            goto L_0x000f
        L_0x000a:
            r1 = move-exception
            byte[] r0 = r10.getBytes()
        L_0x000f:
            int r1 = r0.length
            r2 = 0
            byte[] r0 = decode(r0, r2, r1, r11)
            r1 = r11 & 4
            r3 = 1
            if (r1 == 0) goto L_0x001c
            r1 = r3
            goto L_0x001d
        L_0x001c:
            r1 = r2
        L_0x001d:
            if (r0 == 0) goto L_0x0090
            int r4 = r0.length
            r5 = 4
            if (r4 < r5) goto L_0x0090
            if (r1 != 0) goto L_0x0090
            byte r4 = r0[r2]
            r4 = r4 & 255(0xff, float:3.57E-43)
            byte r3 = r0[r3]
            int r3 = r3 << 8
            r5 = 65280(0xff00, float:9.1477E-41)
            r3 = r3 & r5
            r3 = r3 | r4
            r4 = 35615(0x8b1f, float:4.9907E-41)
            if (r4 != r3) goto L_0x0090
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 2048(0x800, float:2.87E-42)
            byte[] r7 = new byte[r7]
            r8 = 0
            java.io.ByteArrayOutputStream r9 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0082, all -> 0x0071 }
            r9.<init>()     // Catch:{ IOException -> 0x0082, all -> 0x0071 }
            r6 = r9
            java.io.ByteArrayInputStream r9 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x0082, all -> 0x0071 }
            r9.<init>(r0)     // Catch:{ IOException -> 0x0082, all -> 0x0071 }
            r4 = r9
            java.util.zip.GZIPInputStream r9 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x0082, all -> 0x0071 }
            r9.<init>(r4)     // Catch:{ IOException -> 0x0082, all -> 0x0071 }
            r5 = r9
        L_0x0051:
            int r9 = r5.read(r7)     // Catch:{ IOException -> 0x0082, all -> 0x0071 }
            r8 = r9
            if (r9 < 0) goto L_0x005c
            r6.write(r7, r2, r8)     // Catch:{ IOException -> 0x0082, all -> 0x0071 }
            goto L_0x0051
        L_0x005c:
            byte[] r2 = r6.toByteArray()     // Catch:{ IOException -> 0x0082, all -> 0x0071 }
            r0 = r2
            r6.close()     // Catch:{ Exception -> 0x0065 }
            goto L_0x0066
        L_0x0065:
            r2 = move-exception
        L_0x0066:
            r5.close()     // Catch:{ Exception -> 0x006a }
            goto L_0x006b
        L_0x006a:
            r2 = move-exception
        L_0x006b:
            r4.close()     // Catch:{ Exception -> 0x006f }
            goto L_0x0090
        L_0x006f:
            r2 = move-exception
            goto L_0x0090
        L_0x0071:
            r2 = move-exception
            r6.close()     // Catch:{ Exception -> 0x0076 }
            goto L_0x0077
        L_0x0076:
            r9 = move-exception
        L_0x0077:
            r5.close()     // Catch:{ Exception -> 0x007b }
            goto L_0x007c
        L_0x007b:
            r9 = move-exception
        L_0x007c:
            r4.close()     // Catch:{ Exception -> 0x0080 }
            goto L_0x0081
        L_0x0080:
            r9 = move-exception
        L_0x0081:
            throw r2
        L_0x0082:
            r2 = move-exception
            r6.close()     // Catch:{ Exception -> 0x0087 }
            goto L_0x0088
        L_0x0087:
            r2 = move-exception
        L_0x0088:
            r5.close()     // Catch:{ Exception -> 0x008c }
            goto L_0x008d
        L_0x008c:
            r2 = move-exception
        L_0x008d:
            r4.close()     // Catch:{ Exception -> 0x006f }
        L_0x0090:
            return r0
        L_0x0091:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "Input string was null."
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.client.utilities.Base64.decode(java.lang.String, int):byte[]");
    }

    public static Object decodeToObject(String encodedObject) throws IOException, ClassNotFoundException {
        return decodeToObject(encodedObject, 0, (ClassLoader) null);
    }

    public static Object decodeToObject(String encodedObject, int options, final ClassLoader loader) throws IOException, ClassNotFoundException {
        ObjectInputStream ois;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois2 = null;
        try {
            ByteArrayInputStream bais2 = new ByteArrayInputStream(decode(encodedObject, options));
            if (loader == null) {
                ois = new ObjectInputStream(bais2);
            } else {
                ois = new ObjectInputStream(bais2) {
                    public Class<?> resolveClass(ObjectStreamClass streamClass) throws IOException, ClassNotFoundException {
                        Class<?> c = Class.forName(streamClass.getName(), false, loader);
                        if (c == null) {
                            return super.resolveClass(streamClass);
                        }
                        return c;
                    }
                };
            }
            Object obj = ois.readObject();
            try {
                bais2.close();
            } catch (Exception e) {
            }
            try {
                ois.close();
            } catch (Exception e2) {
            }
            return obj;
        } catch (IOException e3) {
            throw e3;
        } catch (ClassNotFoundException e4) {
            throw e4;
        } catch (Throwable th) {
            try {
                bais.close();
            } catch (Exception e5) {
            }
            try {
                ois2.close();
            } catch (Exception e6) {
            }
            throw th;
        }
    }

    public static void encodeToFile(byte[] dataToEncode, String filename) throws IOException {
        if (dataToEncode != null) {
            OutputStream bos = null;
            try {
                bos = new OutputStream(new FileOutputStream(filename), 1);
                bos.write(dataToEncode);
                try {
                    bos.close();
                } catch (Exception e) {
                }
            } catch (IOException e2) {
                throw e2;
            } catch (Throwable th) {
                try {
                    bos.close();
                } catch (Exception e3) {
                }
                throw th;
            }
        } else {
            throw new NullPointerException("Data to encode was null.");
        }
    }

    public static void decodeToFile(String dataToDecode, String filename) throws IOException {
        OutputStream bos = null;
        try {
            bos = new OutputStream(new FileOutputStream(filename), 0);
            bos.write(dataToDecode.getBytes(PREFERRED_ENCODING));
            try {
                bos.close();
            } catch (Exception e) {
            }
        } catch (IOException e2) {
            throw e2;
        } catch (Throwable th) {
            try {
                bos.close();
            } catch (Exception e3) {
            }
            throw th;
        }
    }

    public static byte[] decodeFromFile(String filename) throws IOException {
        InputStream bis = null;
        try {
            File file = new File(filename);
            int length = 0;
            if (file.length() <= 2147483647L) {
                byte[] buffer = new byte[((int) file.length())];
                InputStream bis2 = new InputStream(new BufferedInputStream(new FileInputStream(file)), 0);
                while (true) {
                    int read = bis2.read(buffer, length, 4096);
                    int numBytes = read;
                    if (read < 0) {
                        break;
                    }
                    length += numBytes;
                }
                byte[] decodedData = new byte[length];
                System.arraycopy(buffer, 0, decodedData, 0, length);
                try {
                    bis2.close();
                } catch (Exception e) {
                }
                return decodedData;
            }
            throw new IOException("File is too big for this convenience method (" + file.length() + " bytes).");
        } catch (IOException e2) {
            throw e2;
        } catch (Throwable th) {
            try {
                bis.close();
            } catch (Exception e3) {
            }
            throw th;
        }
    }

    public static String encodeFromFile(String filename) throws IOException {
        InputStream bis = null;
        try {
            File file = new File(filename);
            byte[] buffer = new byte[Math.max((int) ((((double) file.length()) * 1.4d) + 1.0d), 40)];
            int length = 0;
            InputStream bis2 = new InputStream(new BufferedInputStream(new FileInputStream(file)), 1);
            while (true) {
                int read = bis2.read(buffer, length, 4096);
                int numBytes = read;
                if (read < 0) {
                    break;
                }
                length += numBytes;
            }
            String encodedData = new String(buffer, 0, length, PREFERRED_ENCODING);
            try {
                bis2.close();
            } catch (Exception e) {
            }
            return encodedData;
        } catch (IOException e2) {
            throw e2;
        } catch (Throwable th) {
            try {
                bis.close();
            } catch (Exception e3) {
            }
            throw th;
        }
    }

    public static void encodeFileToFile(String infile, String outfile) throws IOException {
        String encoded = encodeFromFile(infile);
        java.io.OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(outfile));
            out.write(encoded.getBytes(PREFERRED_ENCODING));
            try {
                out.close();
            } catch (Exception e) {
            }
        } catch (IOException e2) {
            throw e2;
        } catch (Throwable th) {
            try {
                out.close();
            } catch (Exception e3) {
            }
            throw th;
        }
    }

    public static void decodeFileToFile(String infile, String outfile) throws IOException {
        byte[] decoded = decodeFromFile(infile);
        java.io.OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(outfile));
            out.write(decoded);
            try {
                out.close();
            } catch (Exception e) {
            }
        } catch (IOException e2) {
            throw e2;
        } catch (Throwable th) {
            try {
                out.close();
            } catch (Exception e3) {
            }
            throw th;
        }
    }

    public static class InputStream extends FilterInputStream {
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int numSigBytes;
        private int options;
        private int position;

        public InputStream(java.io.InputStream in) {
            this(in, 0);
        }

        public InputStream(java.io.InputStream in, int options2) {
            super(in);
            this.options = options2;
            boolean z = true;
            this.breakLines = (options2 & 8) > 0;
            z = (options2 & 1) <= 0 ? false : z;
            this.encode = z;
            int i = z ? 4 : 3;
            this.bufferLength = i;
            this.buffer = new byte[i];
            this.position = -1;
            this.lineLength = 0;
            this.decodabet = Base64.getDecodabet(options2);
        }

        /* JADX WARNING: Removed duplicated region for block: B:20:0x0052 A[LOOP:1: B:13:0x003b->B:20:0x0052, LOOP_END] */
        /* JADX WARNING: Removed duplicated region for block: B:50:0x0058 A[EDGE_INSN: B:50:0x0058->B:21:0x0058 ?: BREAK  , SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int read() throws java.io.IOException {
            /*
                r12 = this;
                int r0 = r12.position
                r1 = -1
                r2 = 0
                if (r0 >= 0) goto L_0x0072
                boolean r0 = r12.encode
                r3 = 4
                if (r0 == 0) goto L_0x0037
                r0 = 3
                byte[] r10 = new byte[r0]
                r4 = 0
                r5 = 0
                r11 = r4
            L_0x0011:
                if (r5 >= r0) goto L_0x0024
                java.io.InputStream r4 = r12.in
                int r4 = r4.read()
                if (r4 < 0) goto L_0x0024
                byte r6 = (byte) r4
                r10[r5] = r6
                int r11 = r11 + 1
                int r5 = r5 + 1
                goto L_0x0011
            L_0x0024:
                if (r11 <= 0) goto L_0x0036
                r5 = 0
                byte[] r7 = r12.buffer
                r8 = 0
                int r9 = r12.options
                r4 = r10
                r6 = r11
                byte[] unused = com.firebase.client.utilities.Base64.encode3to4(r4, r5, r6, r7, r8, r9)
                r12.position = r2
                r12.numSigBytes = r3
                goto L_0x0072
            L_0x0036:
                return r1
            L_0x0037:
                byte[] r0 = new byte[r3]
                r4 = 0
                r4 = 0
            L_0x003b:
                if (r4 >= r3) goto L_0x0058
                r5 = 0
            L_0x003e:
                java.io.InputStream r6 = r12.in
                int r5 = r6.read()
                if (r5 < 0) goto L_0x004f
                byte[] r6 = r12.decodabet
                r7 = r5 & 127(0x7f, float:1.78E-43)
                byte r6 = r6[r7]
                r7 = -5
                if (r6 <= r7) goto L_0x003e
            L_0x004f:
                if (r5 >= 0) goto L_0x0052
                goto L_0x0058
            L_0x0052:
                byte r6 = (byte) r5
                r0[r4] = r6
                int r4 = r4 + 1
                goto L_0x003b
            L_0x0058:
                if (r4 != r3) goto L_0x0067
                byte[] r3 = r12.buffer
                int r5 = r12.options
                int r3 = com.firebase.client.utilities.Base64.decode4to3(r0, r2, r3, r2, r5)
                r12.numSigBytes = r3
                r12.position = r2
                goto L_0x0072
            L_0x0067:
                if (r4 != 0) goto L_0x006a
                return r1
            L_0x006a:
                java.io.IOException r1 = new java.io.IOException
                java.lang.String r2 = "Improperly padded Base64 input."
                r1.<init>(r2)
                throw r1
            L_0x0072:
                int r0 = r12.position
                if (r0 < 0) goto L_0x00a5
                int r3 = r12.numSigBytes
                if (r0 < r3) goto L_0x007b
                return r1
            L_0x007b:
                boolean r3 = r12.encode
                if (r3 == 0) goto L_0x008e
                boolean r3 = r12.breakLines
                if (r3 == 0) goto L_0x008e
                int r3 = r12.lineLength
                r4 = 76
                if (r3 < r4) goto L_0x008e
                r12.lineLength = r2
                r0 = 10
                return r0
            L_0x008e:
                int r2 = r12.lineLength
                int r2 = r2 + 1
                r12.lineLength = r2
                byte[] r2 = r12.buffer
                int r3 = r0 + 1
                r12.position = r3
                byte r0 = r2[r0]
                int r2 = r12.bufferLength
                if (r3 < r2) goto L_0x00a2
                r12.position = r1
            L_0x00a2:
                r1 = r0 & 255(0xff, float:3.57E-43)
                return r1
            L_0x00a5:
                java.io.IOException r0 = new java.io.IOException
                java.lang.String r1 = "Error in Base64 code reading stream."
                r0.<init>(r1)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.firebase.client.utilities.Base64.InputStream.read():int");
        }

        public int read(byte[] dest, int off, int len) throws IOException {
            int i = 0;
            while (true) {
                if (i >= len) {
                    break;
                }
                int b = read();
                if (b >= 0) {
                    dest[off + i] = (byte) b;
                    i++;
                } else if (i == 0) {
                    return -1;
                }
            }
            return i;
        }
    }

    public static class OutputStream extends FilterOutputStream {

        /* renamed from: b4 */
        private byte[] f98b4;
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int options;
        private int position;
        private boolean suspendEncoding;

        public OutputStream(java.io.OutputStream out) {
            this(out, 1);
        }

        public OutputStream(java.io.OutputStream out, int options2) {
            super(out);
            boolean z = true;
            this.breakLines = (options2 & 8) != 0;
            z = (options2 & 1) == 0 ? false : z;
            this.encode = z;
            int i = z ? 3 : 4;
            this.bufferLength = i;
            this.buffer = new byte[i];
            this.position = 0;
            this.lineLength = 0;
            this.suspendEncoding = false;
            this.f98b4 = new byte[4];
            this.options = options2;
            this.decodabet = Base64.getDecodabet(options2);
        }

        public void write(int theByte) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(theByte);
            } else if (this.encode) {
                byte[] bArr = this.buffer;
                int i = this.position;
                int i2 = i + 1;
                this.position = i2;
                bArr[i] = (byte) theByte;
                if (i2 >= this.bufferLength) {
                    this.out.write(Base64.encode3to4(this.f98b4, this.buffer, this.bufferLength, this.options));
                    int i3 = this.lineLength + 4;
                    this.lineLength = i3;
                    if (this.breakLines && i3 >= 76) {
                        this.out.write(10);
                        this.lineLength = 0;
                    }
                    this.position = 0;
                }
            } else {
                byte[] bArr2 = this.decodabet;
                if (bArr2[theByte & WorkQueueKt.MASK] > -5) {
                    byte[] bArr3 = this.buffer;
                    int i4 = this.position;
                    int i5 = i4 + 1;
                    this.position = i5;
                    bArr3[i4] = (byte) theByte;
                    if (i5 >= this.bufferLength) {
                        this.out.write(this.f98b4, 0, Base64.decode4to3(bArr3, 0, this.f98b4, 0, this.options));
                        this.position = 0;
                    }
                } else if (bArr2[theByte & WorkQueueKt.MASK] != -5) {
                    throw new IOException("Invalid character in Base64 data.");
                }
            }
        }

        public void write(byte[] theBytes, int off, int len) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(theBytes, off, len);
                return;
            }
            for (int i = 0; i < len; i++) {
                write(theBytes[off + i]);
            }
        }

        public void flushBase64() throws IOException {
            if (this.position <= 0) {
                return;
            }
            if (this.encode) {
                this.out.write(Base64.encode3to4(this.f98b4, this.buffer, this.position, this.options));
                this.position = 0;
                return;
            }
            throw new IOException("Base64 input not properly padded.");
        }

        public void close() throws IOException {
            flushBase64();
            super.close();
            this.buffer = null;
            this.out = null;
        }

        public void suspendEncoding() throws IOException {
            flushBase64();
            this.suspendEncoding = true;
        }

        public void resumeEncoding() {
            this.suspendEncoding = false;
        }
    }
}
