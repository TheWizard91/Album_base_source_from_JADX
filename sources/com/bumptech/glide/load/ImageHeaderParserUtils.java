package com.bumptech.glide.load;

import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.data.ParcelFileDescriptorRewinder;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public final class ImageHeaderParserUtils {
    private static final int MARK_READ_LIMIT = 5242880;

    private interface OrientationReader {
        int getOrientation(ImageHeaderParser imageHeaderParser) throws IOException;
    }

    private interface TypeReader {
        ImageHeaderParser.ImageType getType(ImageHeaderParser imageHeaderParser) throws IOException;
    }

    private ImageHeaderParserUtils() {
    }

    public static ImageHeaderParser.ImageType getType(List<ImageHeaderParser> parsers, InputStream is, ArrayPool byteArrayPool) throws IOException {
        if (is == null) {
            return ImageHeaderParser.ImageType.UNKNOWN;
        }
        if (!is.markSupported()) {
            is = new RecyclableBufferedInputStream(is, byteArrayPool);
        }
        is.mark(MARK_READ_LIMIT);
        final InputStream finalIs = is;
        return getTypeInternal(parsers, new TypeReader() {
            public ImageHeaderParser.ImageType getType(ImageHeaderParser parser) throws IOException {
                try {
                    return parser.getType(finalIs);
                } finally {
                    finalIs.reset();
                }
            }
        });
    }

    public static ImageHeaderParser.ImageType getType(List<ImageHeaderParser> parsers, final ByteBuffer buffer) throws IOException {
        if (buffer == null) {
            return ImageHeaderParser.ImageType.UNKNOWN;
        }
        return getTypeInternal(parsers, new TypeReader() {
            public ImageHeaderParser.ImageType getType(ImageHeaderParser parser) throws IOException {
                return parser.getType(buffer);
            }
        });
    }

    public static ImageHeaderParser.ImageType getType(List<ImageHeaderParser> parsers, final ParcelFileDescriptorRewinder parcelFileDescriptorRewinder, final ArrayPool byteArrayPool) throws IOException {
        return getTypeInternal(parsers, new TypeReader() {
            public ImageHeaderParser.ImageType getType(ImageHeaderParser parser) throws IOException {
                InputStream is = null;
                try {
                    InputStream is2 = new RecyclableBufferedInputStream(new FileInputStream(ParcelFileDescriptorRewinder.this.rewindAndGet().getFileDescriptor()), byteArrayPool);
                    ImageHeaderParser.ImageType type = parser.getType(is2);
                    try {
                        is2.close();
                    } catch (IOException e) {
                    }
                    ParcelFileDescriptorRewinder.this.rewindAndGet();
                    return type;
                } catch (Throwable th) {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e2) {
                        }
                    }
                    ParcelFileDescriptorRewinder.this.rewindAndGet();
                    throw th;
                }
            }
        });
    }

    private static ImageHeaderParser.ImageType getTypeInternal(List<ImageHeaderParser> parsers, TypeReader reader) throws IOException {
        int size = parsers.size();
        for (int i = 0; i < size; i++) {
            ImageHeaderParser.ImageType type = reader.getType(parsers.get(i));
            if (type != ImageHeaderParser.ImageType.UNKNOWN) {
                return type;
            }
        }
        return ImageHeaderParser.ImageType.UNKNOWN;
    }

    public static int getOrientation(List<ImageHeaderParser> parsers, InputStream is, final ArrayPool byteArrayPool) throws IOException {
        if (is == null) {
            return -1;
        }
        if (!is.markSupported()) {
            is = new RecyclableBufferedInputStream(is, byteArrayPool);
        }
        is.mark(MARK_READ_LIMIT);
        final InputStream finalIs = is;
        return getOrientationInternal(parsers, new OrientationReader() {
            public int getOrientation(ImageHeaderParser parser) throws IOException {
                try {
                    return parser.getOrientation(finalIs, byteArrayPool);
                } finally {
                    finalIs.reset();
                }
            }
        });
    }

    public static int getOrientation(List<ImageHeaderParser> parsers, final ParcelFileDescriptorRewinder parcelFileDescriptorRewinder, final ArrayPool byteArrayPool) throws IOException {
        return getOrientationInternal(parsers, new OrientationReader() {
            public int getOrientation(ImageHeaderParser parser) throws IOException {
                InputStream is = null;
                try {
                    InputStream is2 = new RecyclableBufferedInputStream(new FileInputStream(ParcelFileDescriptorRewinder.this.rewindAndGet().getFileDescriptor()), byteArrayPool);
                    int orientation = parser.getOrientation(is2, byteArrayPool);
                    try {
                        is2.close();
                    } catch (IOException e) {
                    }
                    ParcelFileDescriptorRewinder.this.rewindAndGet();
                    return orientation;
                } catch (Throwable th) {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e2) {
                        }
                    }
                    ParcelFileDescriptorRewinder.this.rewindAndGet();
                    throw th;
                }
            }
        });
    }

    private static int getOrientationInternal(List<ImageHeaderParser> parsers, OrientationReader reader) throws IOException {
        int size = parsers.size();
        for (int i = 0; i < size; i++) {
            int orientation = reader.getOrientation(parsers.get(i));
            if (orientation != -1) {
                return orientation;
            }
        }
        return -1;
    }
}
