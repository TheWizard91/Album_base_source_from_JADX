package com.theartofdev.edmodo.cropper;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import androidx.exifinterface.media.ExifInterface;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

final class BitmapUtils {
    static final Rect EMPTY_RECT = new Rect();
    static final RectF EMPTY_RECT_F = new RectF();
    static final float[] POINTS = new float[6];
    static final float[] POINTS2 = new float[6];
    static final RectF RECT = new RectF();
    private static int mMaxTextureSize;
    static Pair<String, WeakReference<Bitmap>> mStateBitmap;

    BitmapUtils() {
    }

    static RotateBitmapResult rotateBitmapByExif(Bitmap bitmap, Context context, Uri uri) {
        ExifInterface ei = null;
        try {
            InputStream is = context.getContentResolver().openInputStream(uri);
            if (is != null) {
                ei = new ExifInterface(is);
                is.close();
            }
        } catch (Exception e) {
        }
        return ei != null ? rotateBitmapByExif(bitmap, ei) : new RotateBitmapResult(bitmap, 0);
    }

    static RotateBitmapResult rotateBitmapByExif(Bitmap bitmap, ExifInterface exif) {
        int degrees;
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        if (orientation == 3) {
            degrees = 180;
        } else if (orientation == 6) {
            degrees = 90;
        } else if (orientation != 8) {
            degrees = 0;
        } else {
            degrees = 270;
        }
        return new RotateBitmapResult(bitmap, degrees);
    }

    static BitmapSampled decodeSampledBitmap(Context context, Uri uri, int reqWidth, int reqHeight) {
        try {
            ContentResolver resolver = context.getContentResolver();
            BitmapFactory.Options options = decodeImageForOption(resolver, uri);
            if (options.outWidth == -1) {
                if (options.outHeight == -1) {
                    throw new RuntimeException("File is not a picture");
                }
            }
            options.inSampleSize = Math.max(calculateInSampleSizeByReqestedSize(options.outWidth, options.outHeight, reqWidth, reqHeight), calculateInSampleSizeByMaxTextureSize(options.outWidth, options.outHeight));
            return new BitmapSampled(decodeImage(resolver, uri, options), options.inSampleSize);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load sampled bitmap: " + uri + "\r\n" + e.getMessage(), e);
        }
    }

    static BitmapSampled cropBitmapObjectHandleOOM(Bitmap bitmap, float[] points, int degreesRotated, boolean fixAspectRatio, int aspectRatioX, int aspectRatioY, boolean flipHorizontally, boolean flipVertically) {
        int scale = 1;
        do {
            try {
                return new BitmapSampled(cropBitmapObjectWithScale(bitmap, points, degreesRotated, fixAspectRatio, aspectRatioX, aspectRatioY, 1.0f / ((float) scale), flipHorizontally, flipVertically), scale);
            } catch (OutOfMemoryError e) {
                scale *= 2;
                if (scale > 8) {
                    throw e;
                }
            }
        } while (scale > 8);
        throw e;
    }

    private static Bitmap cropBitmapObjectWithScale(Bitmap bitmap, float[] points, int degreesRotated, boolean fixAspectRatio, int aspectRatioX, int aspectRatioY, float scale, boolean flipHorizontally, boolean flipVertically) {
        Bitmap result;
        Bitmap bitmap2 = bitmap;
        int i = degreesRotated;
        float f = scale;
        Rect rect = getRectFromPoints(points, bitmap.getWidth(), bitmap.getHeight(), fixAspectRatio, aspectRatioX, aspectRatioY);
        Matrix matrix = new Matrix();
        matrix.setRotate((float) i, (float) (bitmap.getWidth() / 2), (float) (bitmap.getHeight() / 2));
        matrix.postScale(flipHorizontally ? -f : f, flipVertically ? -f : f);
        Bitmap result2 = Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height(), matrix, true);
        if (result2 == bitmap2) {
            result = bitmap.copy(bitmap.getConfig(), false);
        } else {
            result = result2;
        }
        if (i % 90 != 0) {
            return cropForRotatedImage(result, points, rect, degreesRotated, fixAspectRatio, aspectRatioX, aspectRatioY);
        }
        return result;
    }

    static BitmapSampled cropBitmap(Context context, Uri loadedImageUri, float[] points, int degreesRotated, int orgWidth, int orgHeight, boolean fixAspectRatio, int aspectRatioX, int aspectRatioY, int reqWidth, int reqHeight, boolean flipHorizontally, boolean flipVertically) {
        OutOfMemoryError e;
        int sampleMulti = 1;
        do {
            try {
                return cropBitmap(context, loadedImageUri, points, degreesRotated, orgWidth, orgHeight, fixAspectRatio, aspectRatioX, aspectRatioY, reqWidth, reqHeight, flipHorizontally, flipVertically, sampleMulti);
            } catch (OutOfMemoryError e2) {
                e = e2;
                sampleMulti *= 2;
                if (sampleMulti > 16) {
                    throw new RuntimeException("Failed to handle OOM by sampling (" + sampleMulti + "): " + loadedImageUri + "\r\n" + e.getMessage(), e);
                }
            }
        } while (sampleMulti > 16);
        throw new RuntimeException("Failed to handle OOM by sampling (" + sampleMulti + "): " + loadedImageUri + "\r\n" + e.getMessage(), e);
    }

    static float getRectLeft(float[] points) {
        return Math.min(Math.min(Math.min(points[0], points[2]), points[4]), points[6]);
    }

    static float getRectTop(float[] points) {
        return Math.min(Math.min(Math.min(points[1], points[3]), points[5]), points[7]);
    }

    static float getRectRight(float[] points) {
        return Math.max(Math.max(Math.max(points[0], points[2]), points[4]), points[6]);
    }

    static float getRectBottom(float[] points) {
        return Math.max(Math.max(Math.max(points[1], points[3]), points[5]), points[7]);
    }

    static float getRectWidth(float[] points) {
        return getRectRight(points) - getRectLeft(points);
    }

    static float getRectHeight(float[] points) {
        return getRectBottom(points) - getRectTop(points);
    }

    static float getRectCenterX(float[] points) {
        return (getRectRight(points) + getRectLeft(points)) / 2.0f;
    }

    static float getRectCenterY(float[] points) {
        return (getRectBottom(points) + getRectTop(points)) / 2.0f;
    }

    static Rect getRectFromPoints(float[] points, int imageWidth, int imageHeight, boolean fixAspectRatio, int aspectRatioX, int aspectRatioY) {
        Rect rect = new Rect(Math.round(Math.max(0.0f, getRectLeft(points))), Math.round(Math.max(0.0f, getRectTop(points))), Math.round(Math.min((float) imageWidth, getRectRight(points))), Math.round(Math.min((float) imageHeight, getRectBottom(points))));
        if (fixAspectRatio) {
            fixRectForAspectRatio(rect, aspectRatioX, aspectRatioY);
        }
        return rect;
    }

    private static void fixRectForAspectRatio(Rect rect, int aspectRatioX, int aspectRatioY) {
        if (aspectRatioX == aspectRatioY && rect.width() != rect.height()) {
            if (rect.height() > rect.width()) {
                rect.bottom -= rect.height() - rect.width();
            } else {
                rect.right -= rect.width() - rect.height();
            }
        }
    }

    static Uri writeTempStateStoreBitmap(Context context, Bitmap bitmap, Uri uri) {
        boolean needSave = true;
        if (uri == null) {
            try {
                uri = Uri.fromFile(File.createTempFile("aic_state_store_temp", ".jpg", context.getCacheDir()));
            } catch (Exception e) {
                Log.w("AIC", "Failed to write bitmap to temp file for image-cropper save instance state", e);
                return null;
            }
        } else if (new File(uri.getPath()).exists()) {
            needSave = false;
        }
        if (needSave) {
            writeBitmapToUri(context, bitmap, uri, Bitmap.CompressFormat.JPEG, 95);
        }
        return uri;
    }

    static void writeBitmapToUri(Context context, Bitmap bitmap, Uri uri, Bitmap.CompressFormat compressFormat, int compressQuality) throws FileNotFoundException {
        OutputStream outputStream = null;
        try {
            outputStream = context.getContentResolver().openOutputStream(uri);
            bitmap.compress(compressFormat, compressQuality, outputStream);
        } finally {
            closeSafe(outputStream);
        }
    }

    static Bitmap resizeBitmap(Bitmap bitmap, int reqWidth, int reqHeight, CropImageView.RequestSizeOptions options) {
        if (reqWidth > 0 && reqHeight > 0) {
            try {
                if (options == CropImageView.RequestSizeOptions.RESIZE_FIT || options == CropImageView.RequestSizeOptions.RESIZE_INSIDE || options == CropImageView.RequestSizeOptions.RESIZE_EXACT) {
                    Bitmap resized = null;
                    if (options == CropImageView.RequestSizeOptions.RESIZE_EXACT) {
                        resized = Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, false);
                    } else {
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        float scale = Math.max(((float) width) / ((float) reqWidth), ((float) height) / ((float) reqHeight));
                        if (scale > 1.0f || options == CropImageView.RequestSizeOptions.RESIZE_FIT) {
                            resized = Bitmap.createScaledBitmap(bitmap, (int) (((float) width) / scale), (int) (((float) height) / scale), false);
                        }
                    }
                    if (resized != null) {
                        if (resized != bitmap) {
                            bitmap.recycle();
                        }
                        return resized;
                    }
                }
            } catch (Exception e) {
                Log.w("AIC", "Failed to resize cropped image, return bitmap before resize", e);
            }
        }
        return bitmap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0071  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.theartofdev.edmodo.cropper.BitmapUtils.BitmapSampled cropBitmap(android.content.Context r22, android.net.Uri r23, float[] r24, int r25, int r26, int r27, boolean r28, int r29, int r30, int r31, int r32, boolean r33, boolean r34, int r35) {
        /*
            r14 = r25
            r1 = r24
            r2 = r26
            r3 = r27
            r4 = r28
            r5 = r29
            r6 = r30
            android.graphics.Rect r21 = getRectFromPoints(r1, r2, r3, r4, r5, r6)
            if (r31 <= 0) goto L_0x0017
            r18 = r31
            goto L_0x001d
        L_0x0017:
            int r0 = r21.width()
            r18 = r0
        L_0x001d:
            if (r32 <= 0) goto L_0x0022
            r19 = r32
            goto L_0x0028
        L_0x0022:
            int r0 = r21.height()
            r19 = r0
        L_0x0028:
            r1 = 0
            r2 = 1
            r15 = r22
            r16 = r23
            r17 = r21
            r20 = r35
            com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled r0 = decodeSampledBitmapRegion(r15, r16, r17, r18, r19, r20)     // Catch:{ Exception -> 0x0040 }
            android.graphics.Bitmap r3 = r0.bitmap     // Catch:{ Exception -> 0x0040 }
            r1 = r3
            int r3 = r0.sampleSize     // Catch:{ Exception -> 0x0040 }
            r2 = r3
            r15 = r1
            r13 = r2
            goto L_0x0043
        L_0x0040:
            r0 = move-exception
            r15 = r1
            r13 = r2
        L_0x0043:
            if (r15 == 0) goto L_0x0075
            r12 = r33
            r11 = r34
            android.graphics.Bitmap r1 = rotateAndFlipBitmapInt(r15, r14, r12, r11)     // Catch:{ OutOfMemoryError -> 0x006e }
            int r0 = r14 % 90
            if (r0 == 0) goto L_0x0063
            r2 = r24
            r3 = r21
            r4 = r25
            r5 = r28
            r6 = r29
            r7 = r30
            android.graphics.Bitmap r0 = cropForRotatedImage(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ OutOfMemoryError -> 0x006b }
            goto L_0x0064
        L_0x0063:
            r0 = r1
        L_0x0064:
            com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled r1 = new com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled
            r1.<init>(r0, r13)
            return r1
        L_0x006b:
            r0 = move-exception
            r15 = r1
            goto L_0x006f
        L_0x006e:
            r0 = move-exception
        L_0x006f:
            if (r15 == 0) goto L_0x0074
            r15.recycle()
        L_0x0074:
            throw r0
        L_0x0075:
            r12 = r33
            r11 = r34
            r1 = r22
            r2 = r23
            r3 = r24
            r4 = r25
            r5 = r28
            r6 = r29
            r7 = r30
            r8 = r35
            r9 = r21
            r10 = r18
            r11 = r19
            r16 = r13
            r13 = r34
            com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled r0 = cropBitmap((android.content.Context) r1, (android.net.Uri) r2, (float[]) r3, (int) r4, (boolean) r5, (int) r6, (int) r7, (int) r8, (android.graphics.Rect) r9, (int) r10, (int) r11, (boolean) r12, (boolean) r13)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.theartofdev.edmodo.cropper.BitmapUtils.cropBitmap(android.content.Context, android.net.Uri, float[], int, int, int, boolean, int, int, int, int, boolean, boolean, int):com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled");
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x006d A[Catch:{ OutOfMemoryError -> 0x007c, Exception -> 0x007a }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00b1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.theartofdev.edmodo.cropper.BitmapUtils.BitmapSampled cropBitmap(android.content.Context r18, android.net.Uri r19, float[] r20, int r21, boolean r22, int r23, int r24, int r25, android.graphics.Rect r26, int r27, int r28, boolean r29, boolean r30) {
        /*
            r1 = r19
            r2 = r20
            r3 = 0
            android.graphics.BitmapFactory$Options r0 = new android.graphics.BitmapFactory$Options     // Catch:{ OutOfMemoryError -> 0x00aa, Exception -> 0x007e }
            r0.<init>()     // Catch:{ OutOfMemoryError -> 0x00aa, Exception -> 0x007e }
            r4 = r0
            int r0 = r26.width()     // Catch:{ OutOfMemoryError -> 0x00aa, Exception -> 0x007e }
            int r5 = r26.height()     // Catch:{ OutOfMemoryError -> 0x00aa, Exception -> 0x007e }
            r6 = r27
            r7 = r28
            int r0 = calculateInSampleSizeByReqestedSize(r0, r5, r6, r7)     // Catch:{ OutOfMemoryError -> 0x007c, Exception -> 0x007a }
            int r0 = r0 * r25
            r5 = r0
            r4.inSampleSize = r0     // Catch:{ OutOfMemoryError -> 0x007c, Exception -> 0x007a }
            android.content.ContentResolver r0 = r18.getContentResolver()     // Catch:{ OutOfMemoryError -> 0x007c, Exception -> 0x007a }
            android.graphics.Bitmap r0 = decodeImage(r0, r1, r4)     // Catch:{ OutOfMemoryError -> 0x007c, Exception -> 0x007a }
            r15 = r0
            if (r15 == 0) goto L_0x0072
            int r0 = r2.length     // Catch:{ all -> 0x0069 }
            float[] r0 = new float[r0]     // Catch:{ all -> 0x0069 }
            int r8 = r2.length     // Catch:{ all -> 0x0069 }
            r9 = 0
            java.lang.System.arraycopy(r2, r9, r0, r9, r8)     // Catch:{ all -> 0x0069 }
            r8 = 0
        L_0x0035:
            int r9 = r0.length     // Catch:{ all -> 0x0069 }
            if (r8 >= r9) goto L_0x0046
            r9 = r0[r8]     // Catch:{ all -> 0x0043 }
            int r10 = r4.inSampleSize     // Catch:{ all -> 0x0043 }
            float r10 = (float) r10     // Catch:{ all -> 0x0043 }
            float r9 = r9 / r10
            r0[r8] = r9     // Catch:{ all -> 0x0043 }
            int r8 = r8 + 1
            goto L_0x0035
        L_0x0043:
            r0 = move-exception
            r8 = r15
            goto L_0x006b
        L_0x0046:
            r14 = 1065353216(0x3f800000, float:1.0)
            r8 = r15
            r9 = r0
            r10 = r21
            r11 = r22
            r12 = r23
            r13 = r24
            r17 = r15
            r15 = r29
            r16 = r30
            android.graphics.Bitmap r8 = cropBitmapObjectWithScale(r8, r9, r10, r11, r12, r13, r14, r15, r16)     // Catch:{ all -> 0x0065 }
            r3 = r8
            r8 = r17
            if (r3 == r8) goto L_0x0073
            r8.recycle()     // Catch:{ OutOfMemoryError -> 0x007c, Exception -> 0x007a }
            goto L_0x0073
        L_0x0065:
            r0 = move-exception
            r8 = r17
            goto L_0x006b
        L_0x0069:
            r0 = move-exception
            r8 = r15
        L_0x006b:
            if (r3 == r8) goto L_0x0070
            r8.recycle()     // Catch:{ OutOfMemoryError -> 0x007c, Exception -> 0x007a }
        L_0x0070:
            throw r0     // Catch:{ OutOfMemoryError -> 0x007c, Exception -> 0x007a }
        L_0x0072:
            r8 = r15
        L_0x0073:
            com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled r0 = new com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled
            r0.<init>(r3, r5)
            return r0
        L_0x007a:
            r0 = move-exception
            goto L_0x0083
        L_0x007c:
            r0 = move-exception
            goto L_0x00af
        L_0x007e:
            r0 = move-exception
            r6 = r27
            r7 = r28
        L_0x0083:
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r8 = "Failed to load sampled bitmap: "
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.StringBuilder r5 = r5.append(r1)
            java.lang.String r8 = "\r\n"
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r8 = r0.getMessage()
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5, r0)
            throw r4
        L_0x00aa:
            r0 = move-exception
            r6 = r27
            r7 = r28
        L_0x00af:
            if (r3 == 0) goto L_0x00b4
            r3.recycle()
        L_0x00b4:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.theartofdev.edmodo.cropper.BitmapUtils.cropBitmap(android.content.Context, android.net.Uri, float[], int, boolean, int, int, int, android.graphics.Rect, int, int, boolean, boolean):com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled");
    }

    private static BitmapFactory.Options decodeImageForOption(ContentResolver resolver, Uri uri) throws FileNotFoundException {
        InputStream stream = null;
        try {
            stream = resolver.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(stream, EMPTY_RECT, options);
            options.inJustDecodeBounds = false;
            return options;
        } finally {
            closeSafe(stream);
        }
    }

    /* JADX INFO: finally extract failed */
    private static Bitmap decodeImage(ContentResolver resolver, Uri uri, BitmapFactory.Options options) throws FileNotFoundException {
        do {
            try {
                InputStream stream = resolver.openInputStream(uri);
                Bitmap decodeStream = BitmapFactory.decodeStream(stream, EMPTY_RECT, options);
                closeSafe(stream);
                return decodeStream;
            } catch (OutOfMemoryError e) {
                options.inSampleSize *= 2;
                closeSafe((Closeable) null);
                if (options.inSampleSize > 512) {
                    throw new RuntimeException("Failed to decode image: " + uri);
                }
            } catch (Throwable th) {
                closeSafe((Closeable) null);
                throw th;
            }
        } while (options.inSampleSize > 512);
        throw new RuntimeException("Failed to decode image: " + uri);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x004c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.theartofdev.edmodo.cropper.BitmapUtils.BitmapSampled decodeSampledBitmapRegion(android.content.Context r6, android.net.Uri r7, android.graphics.Rect r8, int r9, int r10, int r11) {
        /*
            r0 = 0
            r1 = 0
            android.graphics.BitmapFactory$Options r2 = new android.graphics.BitmapFactory$Options     // Catch:{ Exception -> 0x0059 }
            r2.<init>()     // Catch:{ Exception -> 0x0059 }
            int r3 = r8.width()     // Catch:{ Exception -> 0x0059 }
            int r4 = r8.height()     // Catch:{ Exception -> 0x0059 }
            int r3 = calculateInSampleSizeByReqestedSize(r3, r4, r9, r10)     // Catch:{ Exception -> 0x0059 }
            int r3 = r3 * r11
            r2.inSampleSize = r3     // Catch:{ Exception -> 0x0059 }
            android.content.ContentResolver r3 = r6.getContentResolver()     // Catch:{ Exception -> 0x0059 }
            java.io.InputStream r3 = r3.openInputStream(r7)     // Catch:{ Exception -> 0x0059 }
            r0 = r3
            r3 = 0
            android.graphics.BitmapRegionDecoder r3 = android.graphics.BitmapRegionDecoder.newInstance(r0, r3)     // Catch:{ Exception -> 0x0059 }
            r1 = r3
        L_0x0026:
            com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled r3 = new com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled     // Catch:{ OutOfMemoryError -> 0x003a }
            android.graphics.Bitmap r4 = r1.decodeRegion(r8, r2)     // Catch:{ OutOfMemoryError -> 0x003a }
            int r5 = r2.inSampleSize     // Catch:{ OutOfMemoryError -> 0x003a }
            r3.<init>(r4, r5)     // Catch:{ OutOfMemoryError -> 0x003a }
            closeSafe(r0)
            if (r1 == 0) goto L_0x0039
            r1.recycle()
        L_0x0039:
            return r3
        L_0x003a:
            r3 = move-exception
            int r4 = r2.inSampleSize     // Catch:{ Exception -> 0x0059 }
            int r4 = r4 * 2
            r2.inSampleSize = r4     // Catch:{ Exception -> 0x0059 }
            int r3 = r2.inSampleSize     // Catch:{ Exception -> 0x0059 }
            r4 = 512(0x200, float:7.175E-43)
            if (r3 <= r4) goto L_0x0026
            closeSafe(r0)
            if (r1 == 0) goto L_0x004f
            r1.recycle()
        L_0x004f:
            com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled r2 = new com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled
            r3 = 0
            r4 = 1
            r2.<init>(r3, r4)
            return r2
        L_0x0057:
            r2 = move-exception
            goto L_0x0081
        L_0x0059:
            r2 = move-exception
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ all -> 0x0057 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0057 }
            r4.<init>()     // Catch:{ all -> 0x0057 }
            java.lang.String r5 = "Failed to load sampled bitmap: "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0057 }
            java.lang.StringBuilder r4 = r4.append(r7)     // Catch:{ all -> 0x0057 }
            java.lang.String r5 = "\r\n"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0057 }
            java.lang.String r5 = r2.getMessage()     // Catch:{ all -> 0x0057 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0057 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0057 }
            r3.<init>(r4, r2)     // Catch:{ all -> 0x0057 }
            throw r3     // Catch:{ all -> 0x0057 }
        L_0x0081:
            closeSafe(r0)
            if (r1 == 0) goto L_0x0089
            r1.recycle()
        L_0x0089:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.theartofdev.edmodo.cropper.BitmapUtils.decodeSampledBitmapRegion(android.content.Context, android.net.Uri, android.graphics.Rect, int, int, int):com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled");
    }

    private static Bitmap cropForRotatedImage(Bitmap bitmap, float[] points, Rect rect, int degreesRotated, boolean fixAspectRatio, int aspectRatioX, int aspectRatioY) {
        float[] fArr = points;
        Rect rect2 = rect;
        int i = degreesRotated;
        if (i % 90 != 0) {
            int adjLeft = 0;
            int adjTop = 0;
            int width = 0;
            int height = 0;
            double rads = Math.toRadians((double) i);
            int compareTo = (i < 90 || (i > 180 && i < 270)) ? rect2.left : rect2.right;
            int i2 = 0;
            while (true) {
                if (i2 < fArr.length) {
                    if (fArr[i2] >= ((float) (compareTo - 1)) && fArr[i2] <= ((float) (compareTo + 1))) {
                        adjLeft = (int) Math.abs(Math.sin(rads) * ((double) (((float) rect2.bottom) - fArr[i2 + 1])));
                        adjTop = (int) Math.abs(Math.cos(rads) * ((double) (fArr[i2 + 1] - ((float) rect2.top))));
                        width = (int) Math.abs(((double) (fArr[i2 + 1] - ((float) rect2.top))) / Math.sin(rads));
                        height = (int) Math.abs(((double) (((float) rect2.bottom) - fArr[i2 + 1])) / Math.cos(rads));
                        break;
                    }
                    i2 += 2;
                } else {
                    break;
                }
            }
            rect2.set(adjLeft, adjTop, adjLeft + width, adjTop + height);
            if (fixAspectRatio) {
                fixRectForAspectRatio(rect2, aspectRatioX, aspectRatioY);
            } else {
                int i3 = aspectRatioX;
                int i4 = aspectRatioY;
            }
            Bitmap bitmapTmp = bitmap;
            Bitmap bitmap2 = Bitmap.createBitmap(bitmap, rect2.left, rect2.top, rect.width(), rect.height());
            if (bitmapTmp == bitmap2) {
                return bitmap2;
            }
            bitmapTmp.recycle();
            return bitmap2;
        }
        int i5 = aspectRatioX;
        int i6 = aspectRatioY;
        return bitmap;
    }

    private static int calculateInSampleSizeByReqestedSize(int width, int height, int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            while ((height / 2) / inSampleSize > reqHeight && (width / 2) / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private static int calculateInSampleSizeByMaxTextureSize(int width, int height) {
        int inSampleSize = 1;
        if (mMaxTextureSize == 0) {
            mMaxTextureSize = getMaxTextureSize();
        }
        if (mMaxTextureSize > 0) {
            while (true) {
                int i = height / inSampleSize;
                int i2 = mMaxTextureSize;
                if (i <= i2 && width / inSampleSize <= i2) {
                    break;
                }
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private static Bitmap rotateAndFlipBitmapInt(Bitmap bitmap, int degrees, boolean flipHorizontally, boolean flipVertically) {
        if (degrees <= 0 && !flipHorizontally && !flipVertically) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate((float) degrees);
        float f = -1.0f;
        float f2 = flipHorizontally ? -1.0f : 1.0f;
        if (!flipVertically) {
            f = 1.0f;
        }
        matrix.postScale(f2, f);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        if (newBitmap != bitmap) {
            bitmap.recycle();
        }
        return newBitmap;
    }

    private static int getMaxTextureSize() {
        try {
            EGL10 egl = (EGL10) EGLContext.getEGL();
            EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            egl.eglInitialize(display, new int[2]);
            int[] totalConfigurations = new int[1];
            egl.eglGetConfigs(display, (EGLConfig[]) null, 0, totalConfigurations);
            EGLConfig[] configurationsList = new EGLConfig[totalConfigurations[0]];
            egl.eglGetConfigs(display, configurationsList, totalConfigurations[0], totalConfigurations);
            int[] textureSize = new int[1];
            int maximumTextureSize = 0;
            for (int i = 0; i < totalConfigurations[0]; i++) {
                egl.eglGetConfigAttrib(display, configurationsList[i], 12332, textureSize);
                if (maximumTextureSize < textureSize[0]) {
                    maximumTextureSize = textureSize[0];
                }
            }
            egl.eglTerminate(display);
            return Math.max(maximumTextureSize, 2048);
        } catch (Exception e) {
            return 2048;
        }
    }

    private static void closeSafe(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    static final class BitmapSampled {
        public final Bitmap bitmap;
        final int sampleSize;

        BitmapSampled(Bitmap bitmap2, int sampleSize2) {
            this.bitmap = bitmap2;
            this.sampleSize = sampleSize2;
        }
    }

    static final class RotateBitmapResult {
        public final Bitmap bitmap;
        final int degrees;

        RotateBitmapResult(Bitmap bitmap2, int degrees2) {
            this.bitmap = bitmap2;
            this.degrees = degrees2;
        }
    }
}
