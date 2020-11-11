package com.fasterxml.jackson.core.p007io;

import com.google.common.base.Ascii;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.fasterxml.jackson.core.io.UTF32Reader */
public class UTF32Reader extends BaseReader {
    protected final boolean _bigEndian;
    protected int _byteCount = 0;
    protected int _charCount = 0;
    protected final boolean _managedBuffers;
    protected char _surrogate = 0;

    public /* bridge */ /* synthetic */ void close() throws IOException {
        super.close();
    }

    public /* bridge */ /* synthetic */ int read() throws IOException {
        return super.read();
    }

    public UTF32Reader(IOContext iOContext, InputStream inputStream, byte[] bArr, int i, int i2, boolean z) {
        super(iOContext, inputStream, bArr, i, i2);
        boolean z2 = false;
        this._bigEndian = z;
        this._managedBuffers = inputStream != null ? true : z2;
    }

    public int read(char[] cArr, int i, int i2) throws IOException {
        int i3;
        byte b;
        if (this._buffer == null) {
            return -1;
        }
        if (i2 < 1) {
            return i2;
        }
        if (i < 0 || i + i2 > cArr.length) {
            reportBounds(cArr, i, i2);
        }
        int i4 = i2 + i;
        char c = this._surrogate;
        if (c != 0) {
            i3 = i + 1;
            cArr[i] = c;
            this._surrogate = 0;
        } else {
            int i5 = this._length - this._ptr;
            if (i5 < 4 && !loadMore(i5)) {
                return -1;
            }
            i3 = i;
        }
        while (true) {
            if (i3 >= i4) {
                break;
            }
            int i6 = this._ptr;
            if (this._bigEndian) {
                b = (this._buffer[i6 + 3] & 255) | (this._buffer[i6] << Ascii.CAN) | ((this._buffer[i6 + 1] & 255) << Ascii.DLE) | ((this._buffer[i6 + 2] & 255) << 8);
            } else {
                b = (this._buffer[i6 + 3] << Ascii.CAN) | (this._buffer[i6] & 255) | ((this._buffer[i6 + 1] & 255) << 8) | ((this._buffer[i6 + 2] & 255) << Ascii.DLE);
            }
            this._ptr += 4;
            if (b > 65535) {
                if (b > 1114111) {
                    reportInvalid(b, i3 - i, "(above " + Integer.toHexString(1114111) + ") ");
                }
                int i7 = b - 65536;
                int i8 = i3 + 1;
                cArr[i3] = (char) ((i7 >> 10) + 55296);
                b = (i7 & 1023) | 56320;
                if (i8 >= i4) {
                    this._surrogate = (char) b;
                    i3 = i8;
                    break;
                }
                i3 = i8;
            }
            int i9 = i3 + 1;
            cArr[i3] = (char) b;
            if (this._ptr >= this._length) {
                i3 = i9;
                break;
            }
            i3 = i9;
        }
        int i10 = i3 - i;
        this._charCount += i10;
        return i10;
    }

    private void reportUnexpectedEOF(int i, int i2) throws IOException {
        throw new CharConversionException("Unexpected EOF in the middle of a 4-byte UTF-32 char: got " + i + ", needed " + i2 + ", at char #" + this._charCount + ", byte #" + (this._byteCount + i) + ")");
    }

    private void reportInvalid(int i, int i2, String str) throws IOException {
        throw new CharConversionException("Invalid UTF-32 character 0x" + Integer.toHexString(i) + str + " at char #" + (this._charCount + i2) + ", byte #" + ((this._byteCount + this._ptr) - 1) + ")");
    }

    private boolean loadMore(int i) throws IOException {
        this._byteCount += this._length - i;
        if (i > 0) {
            if (this._ptr > 0) {
                for (int i2 = 0; i2 < i; i2++) {
                    this._buffer[i2] = this._buffer[this._ptr + i2];
                }
                this._ptr = 0;
            }
            this._length = i;
        } else {
            this._ptr = 0;
            int read = this._in == null ? -1 : this._in.read(this._buffer);
            if (read < 1) {
                this._length = 0;
                if (read < 0) {
                    if (this._managedBuffers) {
                        freeBuffers();
                    }
                    return false;
                }
                reportStrangeStream();
            }
            this._length = read;
        }
        while (this._length < 4) {
            int read2 = this._in == null ? -1 : this._in.read(this._buffer, this._length, this._buffer.length - this._length);
            if (read2 < 1) {
                if (read2 < 0) {
                    if (this._managedBuffers) {
                        freeBuffers();
                    }
                    reportUnexpectedEOF(this._length, 4);
                }
                reportStrangeStream();
            }
            this._length += read2;
        }
        return true;
    }
}
