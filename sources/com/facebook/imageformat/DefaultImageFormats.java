package com.facebook.imageformat;

import com.facebook.common.internal.ImmutableList;
import java.util.ArrayList;
import java.util.List;

public final class DefaultImageFormats {
    public static final ImageFormat BMP = new ImageFormat("BMP", "bmp");
    public static final ImageFormat DNG = new ImageFormat("DNG", "dng");
    public static final ImageFormat GIF = new ImageFormat("GIF", "gif");
    public static final ImageFormat HEIF = new ImageFormat("HEIF", "heif");
    public static final ImageFormat ICO = new ImageFormat("ICO", "ico");
    public static final ImageFormat JPEG = new ImageFormat("JPEG", "jpeg");
    public static final ImageFormat PNG = new ImageFormat("PNG", "png");
    public static final ImageFormat WEBP_ANIMATED = new ImageFormat("WEBP_ANIMATED", "webp");
    public static final ImageFormat WEBP_EXTENDED = new ImageFormat("WEBP_EXTENDED", "webp");
    public static final ImageFormat WEBP_EXTENDED_WITH_ALPHA = new ImageFormat("WEBP_EXTENDED_WITH_ALPHA", "webp");
    public static final ImageFormat WEBP_LOSSLESS = new ImageFormat("WEBP_LOSSLESS", "webp");
    public static final ImageFormat WEBP_SIMPLE = new ImageFormat("WEBP_SIMPLE", "webp");
    private static ImmutableList<ImageFormat> sAllDefaultFormats;

    public static boolean isWebpFormat(ImageFormat imageFormat) {
        return isStaticWebpFormat(imageFormat) || imageFormat == WEBP_ANIMATED;
    }

    public static boolean isStaticWebpFormat(ImageFormat imageFormat) {
        return imageFormat == WEBP_SIMPLE || imageFormat == WEBP_LOSSLESS || imageFormat == WEBP_EXTENDED || imageFormat == WEBP_EXTENDED_WITH_ALPHA;
    }

    public static List<ImageFormat> getDefaultFormats() {
        if (sAllDefaultFormats == null) {
            List<ImageFormat> mDefaultFormats = new ArrayList<>(9);
            mDefaultFormats.add(JPEG);
            mDefaultFormats.add(PNG);
            mDefaultFormats.add(GIF);
            mDefaultFormats.add(BMP);
            mDefaultFormats.add(ICO);
            mDefaultFormats.add(WEBP_SIMPLE);
            mDefaultFormats.add(WEBP_LOSSLESS);
            mDefaultFormats.add(WEBP_EXTENDED);
            mDefaultFormats.add(WEBP_EXTENDED_WITH_ALPHA);
            mDefaultFormats.add(WEBP_ANIMATED);
            mDefaultFormats.add(HEIF);
            sAllDefaultFormats = ImmutableList.copyOf(mDefaultFormats);
        }
        return sAllDefaultFormats;
    }

    private DefaultImageFormats() {
    }
}
