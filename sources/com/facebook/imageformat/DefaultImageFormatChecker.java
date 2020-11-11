package com.facebook.imageformat;

import com.facebook.common.internal.Ints;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.webp.WebpSupportStatus;
import com.facebook.imageformat.ImageFormat;
import com.google.common.base.Ascii;
import javax.annotation.Nullable;

public class DefaultImageFormatChecker implements ImageFormat.FormatChecker {
    private static final byte[] BMP_HEADER;
    private static final int BMP_HEADER_LENGTH;
    private static final byte[] DNG_HEADER_II;
    private static final int DNG_HEADER_LENGTH;
    private static final byte[] DNG_HEADER_MM = {77, 77, 0, 42};
    private static final int EXTENDED_WEBP_HEADER_LENGTH = 21;
    private static final byte[] GIF_HEADER_87A = ImageFormatCheckerUtils.asciiBytes("GIF87a");
    private static final byte[] GIF_HEADER_89A = ImageFormatCheckerUtils.asciiBytes("GIF89a");
    private static final int GIF_HEADER_LENGTH = 6;
    private static final int HEIF_HEADER_LENGTH = 12;
    private static final byte[] HEIF_HEADER_PREFIX = ImageFormatCheckerUtils.asciiBytes("ftyp");
    private static final byte[][] HEIF_HEADER_SUFFIXES = {ImageFormatCheckerUtils.asciiBytes("heic"), ImageFormatCheckerUtils.asciiBytes("heix"), ImageFormatCheckerUtils.asciiBytes("hevc"), ImageFormatCheckerUtils.asciiBytes("hevx"), ImageFormatCheckerUtils.asciiBytes("mif1"), ImageFormatCheckerUtils.asciiBytes("msf1")};
    private static final byte[] ICO_HEADER;
    private static final int ICO_HEADER_LENGTH;
    private static final byte[] JPEG_HEADER;
    private static final int JPEG_HEADER_LENGTH;
    private static final byte[] PNG_HEADER;
    private static final int PNG_HEADER_LENGTH;
    private static final int SIMPLE_WEBP_HEADER_LENGTH = 20;
    final int MAX_HEADER_LENGTH = Ints.max(21, 20, JPEG_HEADER_LENGTH, PNG_HEADER_LENGTH, 6, BMP_HEADER_LENGTH, ICO_HEADER_LENGTH, 12);

    public int getHeaderSize() {
        return this.MAX_HEADER_LENGTH;
    }

    @Nullable
    public final ImageFormat determineFormat(byte[] headerBytes, int headerSize) {
        Preconditions.checkNotNull(headerBytes);
        if (WebpSupportStatus.isWebpHeader(headerBytes, 0, headerSize)) {
            return getWebpFormat(headerBytes, headerSize);
        }
        if (isJpegHeader(headerBytes, headerSize)) {
            return DefaultImageFormats.JPEG;
        }
        if (isPngHeader(headerBytes, headerSize)) {
            return DefaultImageFormats.PNG;
        }
        if (isGifHeader(headerBytes, headerSize)) {
            return DefaultImageFormats.GIF;
        }
        if (isBmpHeader(headerBytes, headerSize)) {
            return DefaultImageFormats.BMP;
        }
        if (isIcoHeader(headerBytes, headerSize)) {
            return DefaultImageFormats.ICO;
        }
        if (isHeifHeader(headerBytes, headerSize)) {
            return DefaultImageFormats.HEIF;
        }
        if (isDngHeader(headerBytes, headerSize)) {
            return DefaultImageFormats.DNG;
        }
        return ImageFormat.UNKNOWN;
    }

    private static ImageFormat getWebpFormat(byte[] imageHeaderBytes, int headerSize) {
        Preconditions.checkArgument(WebpSupportStatus.isWebpHeader(imageHeaderBytes, 0, headerSize));
        if (WebpSupportStatus.isSimpleWebpHeader(imageHeaderBytes, 0)) {
            return DefaultImageFormats.WEBP_SIMPLE;
        }
        if (WebpSupportStatus.isLosslessWebpHeader(imageHeaderBytes, 0)) {
            return DefaultImageFormats.WEBP_LOSSLESS;
        }
        if (!WebpSupportStatus.isExtendedWebpHeader(imageHeaderBytes, 0, headerSize)) {
            return ImageFormat.UNKNOWN;
        }
        if (WebpSupportStatus.isAnimatedWebpHeader(imageHeaderBytes, 0)) {
            return DefaultImageFormats.WEBP_ANIMATED;
        }
        if (WebpSupportStatus.isExtendedWebpHeaderWithAlpha(imageHeaderBytes, 0)) {
            return DefaultImageFormats.WEBP_EXTENDED_WITH_ALPHA;
        }
        return DefaultImageFormats.WEBP_EXTENDED;
    }

    static {
        byte[] bArr = {-1, -40, -1};
        JPEG_HEADER = bArr;
        JPEG_HEADER_LENGTH = bArr.length;
        byte[] bArr2 = {-119, 80, 78, 71, Ascii.f1604CR, 10, Ascii.SUB, 10};
        PNG_HEADER = bArr2;
        PNG_HEADER_LENGTH = bArr2.length;
        byte[] asciiBytes = ImageFormatCheckerUtils.asciiBytes("BM");
        BMP_HEADER = asciiBytes;
        BMP_HEADER_LENGTH = asciiBytes.length;
        byte[] bArr3 = {0, 0, 1, 0};
        ICO_HEADER = bArr3;
        ICO_HEADER_LENGTH = bArr3.length;
        byte[] bArr4 = {73, 73, 42, 0};
        DNG_HEADER_II = bArr4;
        DNG_HEADER_LENGTH = bArr4.length;
    }

    private static boolean isJpegHeader(byte[] imageHeaderBytes, int headerSize) {
        byte[] bArr = JPEG_HEADER;
        return headerSize >= bArr.length && ImageFormatCheckerUtils.startsWithPattern(imageHeaderBytes, bArr);
    }

    private static boolean isPngHeader(byte[] imageHeaderBytes, int headerSize) {
        byte[] bArr = PNG_HEADER;
        return headerSize >= bArr.length && ImageFormatCheckerUtils.startsWithPattern(imageHeaderBytes, bArr);
    }

    private static boolean isGifHeader(byte[] imageHeaderBytes, int headerSize) {
        if (headerSize < 6) {
            return false;
        }
        if (ImageFormatCheckerUtils.startsWithPattern(imageHeaderBytes, GIF_HEADER_87A) || ImageFormatCheckerUtils.startsWithPattern(imageHeaderBytes, GIF_HEADER_89A)) {
            return true;
        }
        return false;
    }

    private static boolean isBmpHeader(byte[] imageHeaderBytes, int headerSize) {
        byte[] bArr = BMP_HEADER;
        if (headerSize < bArr.length) {
            return false;
        }
        return ImageFormatCheckerUtils.startsWithPattern(imageHeaderBytes, bArr);
    }

    private static boolean isIcoHeader(byte[] imageHeaderBytes, int headerSize) {
        byte[] bArr = ICO_HEADER;
        if (headerSize < bArr.length) {
            return false;
        }
        return ImageFormatCheckerUtils.startsWithPattern(imageHeaderBytes, bArr);
    }

    private static boolean isHeifHeader(byte[] imageHeaderBytes, int headerSize) {
        if (headerSize < 12 || imageHeaderBytes[3] < 8 || !ImageFormatCheckerUtils.hasPatternAt(imageHeaderBytes, HEIF_HEADER_PREFIX, 4)) {
            return false;
        }
        for (byte[] heifFtype : HEIF_HEADER_SUFFIXES) {
            if (ImageFormatCheckerUtils.hasPatternAt(imageHeaderBytes, heifFtype, 8)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDngHeader(byte[] imageHeaderBytes, int headerSize) {
        return headerSize >= DNG_HEADER_LENGTH && (ImageFormatCheckerUtils.startsWithPattern(imageHeaderBytes, DNG_HEADER_II) || ImageFormatCheckerUtils.startsWithPattern(imageHeaderBytes, DNG_HEADER_MM));
    }
}
