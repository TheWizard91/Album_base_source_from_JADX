package com.bumptech.glide.load.resource.bitmap;

import android.util.Log;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.util.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public final class DefaultImageHeaderParser implements ImageHeaderParser {
    private static final int[] BYTES_PER_FORMAT = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8};
    static final int EXIF_MAGIC_NUMBER = 65496;
    static final int EXIF_SEGMENT_TYPE = 225;
    private static final int GIF_HEADER = 4671814;
    private static final int INTEL_TIFF_MAGIC_NUMBER = 18761;
    private static final String JPEG_EXIF_SEGMENT_PREAMBLE = "Exif\u0000\u0000";
    static final byte[] JPEG_EXIF_SEGMENT_PREAMBLE_BYTES = JPEG_EXIF_SEGMENT_PREAMBLE.getBytes(Charset.forName(Key.STRING_CHARSET_NAME));
    private static final int MARKER_EOI = 217;
    private static final int MOTOROLA_TIFF_MAGIC_NUMBER = 19789;
    private static final int ORIENTATION_TAG_TYPE = 274;
    private static final int PNG_HEADER = -1991225785;
    private static final int RIFF_HEADER = 1380533830;
    private static final int SEGMENT_SOS = 218;
    static final int SEGMENT_START_ID = 255;
    private static final String TAG = "DfltImageHeaderParser";
    private static final int VP8_HEADER = 1448097792;
    private static final int VP8_HEADER_MASK = -256;
    private static final int VP8_HEADER_TYPE_EXTENDED = 88;
    private static final int VP8_HEADER_TYPE_LOSSLESS = 76;
    private static final int VP8_HEADER_TYPE_MASK = 255;
    private static final int WEBP_EXTENDED_ALPHA_FLAG = 16;
    private static final int WEBP_HEADER = 1464156752;
    private static final int WEBP_LOSSLESS_ALPHA_FLAG = 8;

    public ImageHeaderParser.ImageType getType(InputStream is) throws IOException {
        return getType((Reader) new StreamReader((InputStream) Preconditions.checkNotNull(is)));
    }

    public ImageHeaderParser.ImageType getType(ByteBuffer byteBuffer) throws IOException {
        return getType((Reader) new ByteBufferReader((ByteBuffer) Preconditions.checkNotNull(byteBuffer)));
    }

    public int getOrientation(InputStream is, ArrayPool byteArrayPool) throws IOException {
        return getOrientation((Reader) new StreamReader((InputStream) Preconditions.checkNotNull(is)), (ArrayPool) Preconditions.checkNotNull(byteArrayPool));
    }

    public int getOrientation(ByteBuffer byteBuffer, ArrayPool byteArrayPool) throws IOException {
        return getOrientation((Reader) new ByteBufferReader((ByteBuffer) Preconditions.checkNotNull(byteBuffer)), (ArrayPool) Preconditions.checkNotNull(byteArrayPool));
    }

    private ImageHeaderParser.ImageType getType(Reader reader) throws IOException {
        try {
            int firstTwoBytes = reader.getUInt16();
            if (firstTwoBytes == EXIF_MAGIC_NUMBER) {
                return ImageHeaderParser.ImageType.JPEG;
            }
            int firstThreeBytes = (firstTwoBytes << 8) | reader.getUInt8();
            if (firstThreeBytes == GIF_HEADER) {
                return ImageHeaderParser.ImageType.GIF;
            }
            int firstFourBytes = (firstThreeBytes << 8) | reader.getUInt8();
            if (firstFourBytes == PNG_HEADER) {
                reader.skip(21);
                try {
                    return reader.getUInt8() >= 3 ? ImageHeaderParser.ImageType.PNG_A : ImageHeaderParser.ImageType.PNG;
                } catch (Reader.EndOfFileException e) {
                    return ImageHeaderParser.ImageType.PNG;
                }
            } else if (firstFourBytes != RIFF_HEADER) {
                return ImageHeaderParser.ImageType.UNKNOWN;
            } else {
                reader.skip(4);
                if (((reader.getUInt16() << 16) | reader.getUInt16()) != WEBP_HEADER) {
                    return ImageHeaderParser.ImageType.UNKNOWN;
                }
                int fourthFourBytes = (reader.getUInt16() << 16) | reader.getUInt16();
                if ((fourthFourBytes & -256) != VP8_HEADER) {
                    return ImageHeaderParser.ImageType.UNKNOWN;
                }
                if ((fourthFourBytes & 255) == 88) {
                    reader.skip(4);
                    return (reader.getUInt8() & 16) != 0 ? ImageHeaderParser.ImageType.WEBP_A : ImageHeaderParser.ImageType.WEBP;
                } else if ((fourthFourBytes & 255) != 76) {
                    return ImageHeaderParser.ImageType.WEBP;
                } else {
                    reader.skip(4);
                    return (reader.getUInt8() & 8) != 0 ? ImageHeaderParser.ImageType.WEBP_A : ImageHeaderParser.ImageType.WEBP;
                }
            }
        } catch (Reader.EndOfFileException e2) {
            return ImageHeaderParser.ImageType.UNKNOWN;
        }
    }

    private int getOrientation(Reader reader, ArrayPool byteArrayPool) throws IOException {
        byte[] exifData;
        try {
            int magicNumber = reader.getUInt16();
            if (!handles(magicNumber)) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Parser doesn't handle magic number: " + magicNumber);
                }
                return -1;
            }
            int exifSegmentLength = moveToExifSegmentAndGetLength(reader);
            if (exifSegmentLength == -1) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Failed to parse exif segment length, or exif segment not found");
                }
                return -1;
            }
            exifData = (byte[]) byteArrayPool.get(exifSegmentLength, byte[].class);
            int parseExifSegment = parseExifSegment(reader, exifData, exifSegmentLength);
            byteArrayPool.put(exifData);
            return parseExifSegment;
        } catch (Reader.EndOfFileException e) {
            return -1;
        } catch (Throwable th) {
            byteArrayPool.put(exifData);
            throw th;
        }
    }

    private int parseExifSegment(Reader reader, byte[] tempArray, int exifSegmentLength) throws IOException {
        int read = reader.read(tempArray, exifSegmentLength);
        if (read != exifSegmentLength) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Unable to read exif segment data, length: " + exifSegmentLength + ", actually read: " + read);
            }
            return -1;
        } else if (hasJpegExifPreamble(tempArray, exifSegmentLength)) {
            return parseExifSegment(new RandomAccessReader(tempArray, exifSegmentLength));
        } else {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Missing jpeg exif preamble");
            }
            return -1;
        }
    }

    private boolean hasJpegExifPreamble(byte[] exifData, int exifSegmentLength) {
        boolean result = exifData != null && exifSegmentLength > JPEG_EXIF_SEGMENT_PREAMBLE_BYTES.length;
        if (!result) {
            return result;
        }
        int i = 0;
        while (true) {
            byte[] bArr = JPEG_EXIF_SEGMENT_PREAMBLE_BYTES;
            if (i >= bArr.length) {
                return result;
            }
            if (exifData[i] != bArr[i]) {
                return false;
            }
            i++;
        }
    }

    private int moveToExifSegmentAndGetLength(Reader reader) throws IOException {
        short segmentType;
        int segmentContentsLength;
        long skipped;
        do {
            short segmentId = reader.getUInt8();
            if (segmentId != 255) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Unknown segmentId=" + segmentId);
                }
                return -1;
            }
            segmentType = reader.getUInt8();
            if (segmentType == 218) {
                return -1;
            }
            if (segmentType == 217) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Found MARKER_EOI in exif segment");
                }
                return -1;
            }
            segmentContentsLength = reader.getUInt16() - 2;
            if (segmentType == 225) {
                return segmentContentsLength;
            }
            skipped = reader.skip((long) segmentContentsLength);
        } while (skipped == ((long) segmentContentsLength));
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Unable to skip enough data, type: " + segmentType + ", wanted to skip: " + segmentContentsLength + ", but actually skipped: " + skipped);
        }
        return -1;
    }

    private static int parseExifSegment(RandomAccessReader segmentData) {
        ByteOrder byteOrder;
        int i;
        RandomAccessReader randomAccessReader = segmentData;
        int headerOffsetSize = JPEG_EXIF_SEGMENT_PREAMBLE.length();
        short byteOrderIdentifier = randomAccessReader.getInt16(headerOffsetSize);
        int i2 = 3;
        if (byteOrderIdentifier == INTEL_TIFF_MAGIC_NUMBER) {
            byteOrder = ByteOrder.LITTLE_ENDIAN;
        } else if (byteOrderIdentifier != MOTOROLA_TIFF_MAGIC_NUMBER) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Unknown endianness = " + byteOrderIdentifier);
            }
            byteOrder = ByteOrder.BIG_ENDIAN;
        } else {
            byteOrder = ByteOrder.BIG_ENDIAN;
        }
        randomAccessReader.order(byteOrder);
        int firstIfdOffset = randomAccessReader.getInt32(headerOffsetSize + 4) + headerOffsetSize;
        int tagCount = randomAccessReader.getInt16(firstIfdOffset);
        int i3 = 0;
        while (i3 < tagCount) {
            int tagOffset = calcTagOffset(firstIfdOffset, i3);
            int tagType = randomAccessReader.getInt16(tagOffset);
            if (tagType != 274) {
                i = i2;
            } else {
                int formatCode = randomAccessReader.getInt16(tagOffset + 2);
                if (formatCode < 1 || formatCode > 12) {
                    i = 3;
                    if (Log.isLoggable(TAG, 3)) {
                        Log.d(TAG, "Got invalid format code = " + formatCode);
                    }
                } else {
                    int componentCount = randomAccessReader.getInt32(tagOffset + 4);
                    if (componentCount >= 0) {
                        if (Log.isLoggable(TAG, i2)) {
                            Log.d(TAG, "Got tagIndex=" + i3 + " tagType=" + tagType + " formatCode=" + formatCode + " componentCount=" + componentCount);
                        }
                        int byteCount = BYTES_PER_FORMAT[formatCode] + componentCount;
                        if (byteCount <= 4) {
                            int tagValueOffset = tagOffset + 8;
                            if (tagValueOffset < 0 || tagValueOffset > segmentData.length()) {
                                if (Log.isLoggable(TAG, 3)) {
                                    Log.d(TAG, "Illegal tagValueOffset=" + tagValueOffset + " tagType=" + tagType);
                                    i = 3;
                                } else {
                                    i = 3;
                                }
                            } else if (byteCount >= 0 && tagValueOffset + byteCount <= segmentData.length()) {
                                return randomAccessReader.getInt16(tagValueOffset);
                            } else {
                                if (Log.isLoggable(TAG, 3)) {
                                    Log.d(TAG, "Illegal number of bytes for TI tag data tagType=" + tagType);
                                    i = 3;
                                } else {
                                    i = 3;
                                }
                            }
                        } else if (Log.isLoggable(TAG, i2)) {
                            Log.d(TAG, "Got byte count > 4, not orientation, continuing, formatCode=" + formatCode);
                            i = i2;
                        } else {
                            i = i2;
                        }
                    } else if (Log.isLoggable(TAG, i2)) {
                        Log.d(TAG, "Negative tiff component count");
                        i = i2;
                    } else {
                        i = i2;
                    }
                }
            }
            i3++;
            i2 = i;
            randomAccessReader = segmentData;
        }
        return -1;
    }

    private static int calcTagOffset(int ifdOffset, int tagIndex) {
        return ifdOffset + 2 + (tagIndex * 12);
    }

    private static boolean handles(int imageMagicNumber) {
        return (imageMagicNumber & EXIF_MAGIC_NUMBER) == EXIF_MAGIC_NUMBER || imageMagicNumber == MOTOROLA_TIFF_MAGIC_NUMBER || imageMagicNumber == INTEL_TIFF_MAGIC_NUMBER;
    }

    private static final class RandomAccessReader {
        private final ByteBuffer data;

        RandomAccessReader(byte[] data2, int length) {
            this.data = (ByteBuffer) ByteBuffer.wrap(data2).order(ByteOrder.BIG_ENDIAN).limit(length);
        }

        /* access modifiers changed from: package-private */
        public void order(ByteOrder byteOrder) {
            this.data.order(byteOrder);
        }

        /* access modifiers changed from: package-private */
        public int length() {
            return this.data.remaining();
        }

        /* access modifiers changed from: package-private */
        public int getInt32(int offset) {
            if (isAvailable(offset, 4)) {
                return this.data.getInt(offset);
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public short getInt16(int offset) {
            if (isAvailable(offset, 2)) {
                return this.data.getShort(offset);
            }
            return -1;
        }

        private boolean isAvailable(int offset, int byteSize) {
            return this.data.remaining() - offset >= byteSize;
        }
    }

    private interface Reader {
        int getUInt16() throws IOException;

        short getUInt8() throws IOException;

        int read(byte[] bArr, int i) throws IOException;

        long skip(long j) throws IOException;

        public static final class EndOfFileException extends IOException {
            private static final long serialVersionUID = 1;

            EndOfFileException() {
                super("Unexpectedly reached end of a file");
            }
        }
    }

    private static final class ByteBufferReader implements Reader {
        private final ByteBuffer byteBuffer;

        ByteBufferReader(ByteBuffer byteBuffer2) {
            this.byteBuffer = byteBuffer2;
            byteBuffer2.order(ByteOrder.BIG_ENDIAN);
        }

        public short getUInt8() throws Reader.EndOfFileException {
            if (this.byteBuffer.remaining() >= 1) {
                return (short) (this.byteBuffer.get() & 255);
            }
            throw new Reader.EndOfFileException();
        }

        public int getUInt16() throws Reader.EndOfFileException {
            return (getUInt8() << 8) | getUInt8();
        }

        public int read(byte[] buffer, int byteCount) {
            int toRead = Math.min(byteCount, this.byteBuffer.remaining());
            if (toRead == 0) {
                return -1;
            }
            this.byteBuffer.get(buffer, 0, toRead);
            return toRead;
        }

        public long skip(long total) {
            int toSkip = (int) Math.min((long) this.byteBuffer.remaining(), total);
            ByteBuffer byteBuffer2 = this.byteBuffer;
            byteBuffer2.position(byteBuffer2.position() + toSkip);
            return (long) toSkip;
        }
    }

    private static final class StreamReader implements Reader {

        /* renamed from: is */
        private final InputStream f68is;

        StreamReader(InputStream is) {
            this.f68is = is;
        }

        public short getUInt8() throws IOException {
            int readResult = this.f68is.read();
            if (readResult != -1) {
                return (short) readResult;
            }
            throw new Reader.EndOfFileException();
        }

        public int getUInt16() throws IOException {
            return (getUInt8() << 8) | getUInt8();
        }

        public int read(byte[] buffer, int byteCount) throws IOException {
            int numBytesRead = 0;
            int lastReadResult = 0;
            while (numBytesRead < byteCount) {
                int read = this.f68is.read(buffer, numBytesRead, byteCount - numBytesRead);
                lastReadResult = read;
                if (read == -1) {
                    break;
                }
                numBytesRead += lastReadResult;
            }
            if (numBytesRead != 0 || lastReadResult != -1) {
                return numBytesRead;
            }
            throw new Reader.EndOfFileException();
        }

        public long skip(long total) throws IOException {
            if (total < 0) {
                return 0;
            }
            long toSkip = total;
            while (toSkip > 0) {
                long skipped = this.f68is.skip(toSkip);
                if (skipped > 0) {
                    toSkip -= skipped;
                } else if (this.f68is.read() == -1) {
                    break;
                } else {
                    toSkip--;
                }
            }
            return total - toSkip;
        }
    }
}
