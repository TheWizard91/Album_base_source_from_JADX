package com.facebook.drawee.backends.pipeline.info;

import androidx.core.p003os.EnvironmentCompat;
import com.google.android.gms.common.internal.ImagesContract;

public class ImageOriginUtils {
    public static String toString(int imageOrigin) {
        if (imageOrigin == 2) {
            return "network";
        }
        if (imageOrigin == 3) {
            return "disk";
        }
        if (imageOrigin == 4) {
            return "memory_encoded";
        }
        if (imageOrigin == 5) {
            return "memory_bitmap";
        }
        if (imageOrigin != 6) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        return ImagesContract.LOCAL;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int mapProducerNameToImageOrigin(java.lang.String r7) {
        /*
            int r0 = r7.hashCode()
            r1 = 1
            r2 = 6
            r3 = 2
            r4 = 3
            r5 = 4
            r6 = 5
            switch(r0) {
                case -1917159454: goto L_0x00a7;
                case -1914072202: goto L_0x009d;
                case -1683996557: goto L_0x0092;
                case -1579985851: goto L_0x0087;
                case -1307634203: goto L_0x007d;
                case -1224383234: goto L_0x0073;
                case 473552259: goto L_0x0068;
                case 656304759: goto L_0x005e;
                case 957714404: goto L_0x0054;
                case 1019542023: goto L_0x0048;
                case 1023071510: goto L_0x003d;
                case 1721672898: goto L_0x0032;
                case 1793127518: goto L_0x0026;
                case 2109593398: goto L_0x001b;
                case 2113652014: goto L_0x000f;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x00b2
        L_0x000f:
            java.lang.String r0 = "LocalContentUriFetchProducer"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000d
            r0 = 9
            goto L_0x00b3
        L_0x001b:
            java.lang.String r0 = "PartialDiskCacheProducer"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000d
            r0 = r6
            goto L_0x00b3
        L_0x0026:
            java.lang.String r0 = "LocalContentUriThumbnailFetchProducer"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000d
            r0 = 10
            goto L_0x00b3
        L_0x0032:
            java.lang.String r0 = "DataFetchProducer"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000d
            r0 = 7
            goto L_0x00b3
        L_0x003d:
            java.lang.String r0 = "PostprocessedBitmapMemoryCacheProducer"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000d
            r0 = r3
            goto L_0x00b3
        L_0x0048:
            java.lang.String r0 = "LocalAssetFetchProducer"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000d
            r0 = 8
            goto L_0x00b3
        L_0x0054:
            java.lang.String r0 = "BitmapMemoryCacheProducer"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000d
            r0 = r1
            goto L_0x00b3
        L_0x005e:
            java.lang.String r0 = "DiskCacheProducer"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000d
            r0 = r5
            goto L_0x00b3
        L_0x0068:
            java.lang.String r0 = "VideoThumbnailProducer"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000d
            r0 = 13
            goto L_0x00b3
        L_0x0073:
            java.lang.String r0 = "NetworkFetchProducer"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000d
            r0 = r2
            goto L_0x00b3
        L_0x007d:
            java.lang.String r0 = "EncodedMemoryCacheProducer"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000d
            r0 = r4
            goto L_0x00b3
        L_0x0087:
            java.lang.String r0 = "LocalFileFetchProducer"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000d
            r0 = 11
            goto L_0x00b3
        L_0x0092:
            java.lang.String r0 = "LocalResourceFetchProducer"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000d
            r0 = 12
            goto L_0x00b3
        L_0x009d:
            java.lang.String r0 = "BitmapMemoryCacheGetProducer"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000d
            r0 = 0
            goto L_0x00b3
        L_0x00a7:
            java.lang.String r0 = "QualifiedResourceFetchProducer"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000d
            r0 = 14
            goto L_0x00b3
        L_0x00b2:
            r0 = -1
        L_0x00b3:
            switch(r0) {
                case 0: goto L_0x00bb;
                case 1: goto L_0x00bb;
                case 2: goto L_0x00bb;
                case 3: goto L_0x00ba;
                case 4: goto L_0x00b9;
                case 5: goto L_0x00b9;
                case 6: goto L_0x00b8;
                case 7: goto L_0x00b7;
                case 8: goto L_0x00b7;
                case 9: goto L_0x00b7;
                case 10: goto L_0x00b7;
                case 11: goto L_0x00b7;
                case 12: goto L_0x00b7;
                case 13: goto L_0x00b7;
                case 14: goto L_0x00b7;
                default: goto L_0x00b6;
            }
        L_0x00b6:
            return r1
        L_0x00b7:
            return r2
        L_0x00b8:
            return r3
        L_0x00b9:
            return r4
        L_0x00ba:
            return r5
        L_0x00bb:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.drawee.backends.pipeline.info.ImageOriginUtils.mapProducerNameToImageOrigin(java.lang.String):int");
    }

    private ImageOriginUtils() {
    }
}
