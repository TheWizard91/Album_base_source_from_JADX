package com.facebook.imagepipeline.decoder;

import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Throwables;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.memory.PooledByteArrayBufferedInputStream;
import com.facebook.common.util.StreamUtil;
import com.facebook.imagepipeline.image.EncodedImage;
import java.io.IOException;
import java.io.InputStream;

public class ProgressiveJpegParser {
    private static final int BUFFER_SIZE = 16384;
    private static final int NOT_A_JPEG = 6;
    private static final int READ_FIRST_JPEG_BYTE = 0;
    private static final int READ_MARKER_FIRST_BYTE_OR_ENTROPY_DATA = 2;
    private static final int READ_MARKER_SECOND_BYTE = 3;
    private static final int READ_SECOND_JPEG_BYTE = 1;
    private static final int READ_SIZE_FIRST_BYTE = 4;
    private static final int READ_SIZE_SECOND_BYTE = 5;
    private int mBestScanEndOffset = 0;
    private int mBestScanNumber = 0;
    private final ByteArrayPool mByteArrayPool;
    private int mBytesParsed = 0;
    private boolean mEndMarkerRead;
    private int mLastByteRead = 0;
    private int mNextFullScanNumber = 0;
    private int mParserState = 0;

    public ProgressiveJpegParser(ByteArrayPool byteArrayPool) {
        this.mByteArrayPool = (ByteArrayPool) Preconditions.checkNotNull(byteArrayPool);
    }

    public boolean parseMoreData(EncodedImage encodedImage) {
        if (this.mParserState == 6 || encodedImage.getSize() <= this.mBytesParsed) {
            return false;
        }
        InputStream bufferedDataStream = new PooledByteArrayBufferedInputStream(encodedImage.getInputStream(), (byte[]) this.mByteArrayPool.get(16384), this.mByteArrayPool);
        try {
            StreamUtil.skip(bufferedDataStream, (long) this.mBytesParsed);
            return doParseMoreData(bufferedDataStream);
        } catch (IOException ioe) {
            Throwables.propagate(ioe);
            return false;
        } finally {
            Closeables.closeQuietly(bufferedDataStream);
        }
    }

    private boolean doParseMoreData(InputStream inputStream) {
        int oldBestScanNumber = this.mBestScanNumber;
        while (this.mParserState != 6) {
            try {
                int read = inputStream.read();
                int nextByte = read;
                if (read == -1) {
                    break;
                }
                int i = this.mBytesParsed + 1;
                this.mBytesParsed = i;
                if (this.mEndMarkerRead) {
                    this.mParserState = 6;
                    this.mEndMarkerRead = false;
                    return false;
                }
                int i2 = this.mParserState;
                if (i2 != 0) {
                    if (i2 != 1) {
                        if (i2 != 2) {
                            if (i2 != 3) {
                                if (i2 == 4) {
                                    this.mParserState = 5;
                                } else if (i2 != 5) {
                                    Preconditions.checkState(false);
                                } else {
                                    int bytesToSkip = ((this.mLastByteRead << 8) + nextByte) - 2;
                                    StreamUtil.skip(inputStream, (long) bytesToSkip);
                                    this.mBytesParsed += bytesToSkip;
                                    this.mParserState = 2;
                                }
                            } else if (nextByte == 255) {
                                this.mParserState = 3;
                            } else if (nextByte == 0) {
                                this.mParserState = 2;
                            } else if (nextByte == 217) {
                                this.mEndMarkerRead = true;
                                newScanOrImageEndFound(i - 2);
                                this.mParserState = 2;
                            } else {
                                if (nextByte == 218) {
                                    newScanOrImageEndFound(i - 2);
                                }
                                if (doesMarkerStartSegment(nextByte)) {
                                    this.mParserState = 4;
                                } else {
                                    this.mParserState = 2;
                                }
                            }
                        } else if (nextByte == 255) {
                            this.mParserState = 3;
                        }
                    } else if (nextByte == 216) {
                        this.mParserState = 2;
                    } else {
                        this.mParserState = 6;
                    }
                } else if (nextByte == 255) {
                    this.mParserState = 1;
                } else {
                    this.mParserState = 6;
                }
                this.mLastByteRead = nextByte;
            } catch (IOException ioe) {
                Throwables.propagate(ioe);
            }
        }
        if (this.mParserState == 6 || this.mBestScanNumber == oldBestScanNumber) {
            return false;
        }
        return true;
    }

    private static boolean doesMarkerStartSegment(int markerSecondByte) {
        if (markerSecondByte == 1) {
            return false;
        }
        if (markerSecondByte >= 208 && markerSecondByte <= 215) {
            return false;
        }
        if (markerSecondByte == 217 || markerSecondByte == 216) {
            return false;
        }
        return true;
    }

    private void newScanOrImageEndFound(int offset) {
        int i = this.mNextFullScanNumber;
        if (i > 0) {
            this.mBestScanEndOffset = offset;
        }
        this.mNextFullScanNumber = i + 1;
        this.mBestScanNumber = i;
    }

    public boolean isJpeg() {
        return this.mBytesParsed > 1 && this.mParserState != 6;
    }

    public int getBestScanEndOffset() {
        return this.mBestScanEndOffset;
    }

    public int getBestScanNumber() {
        return this.mBestScanNumber;
    }

    public boolean isEndMarkerRead() {
        return this.mEndMarkerRead;
    }
}
