package com.facebook.imagepipeline.producers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.BitmapCounterConfig;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imageutils.JfifUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class LocalContentUriThumbnailFetchProducer extends LocalFetchProducer implements ThumbnailProducer<EncodedImage> {
    private static final Rect MICRO_THUMBNAIL_DIMENSIONS = new Rect(0, 0, 96, 96);
    private static final Rect MINI_THUMBNAIL_DIMENSIONS = new Rect(0, 0, 512, BitmapCounterConfig.DEFAULT_MAX_BITMAP_COUNT);
    private static final int NO_THUMBNAIL = 0;
    public static final String PRODUCER_NAME = "LocalContentUriThumbnailFetchProducer";
    private static final String[] PROJECTION = {"_id", "_data"};
    private static final Class<?> TAG = LocalContentUriThumbnailFetchProducer.class;
    private static final String[] THUMBNAIL_PROJECTION = {"_data"};
    private final ContentResolver mContentResolver;

    public LocalContentUriThumbnailFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, ContentResolver contentResolver) {
        super(executor, pooledByteBufferFactory);
        this.mContentResolver = contentResolver;
    }

    public boolean canProvideImageForSize(ResizeOptions resizeOptions) {
        Rect rect = MINI_THUMBNAIL_DIMENSIONS;
        return ThumbnailSizeChecker.isImageBigEnough(rect.width(), rect.height(), resizeOptions);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public EncodedImage getEncodedImage(ImageRequest imageRequest) throws IOException {
        Uri uri = imageRequest.getSourceUri();
        if (UriUtil.isLocalCameraUri(uri)) {
            return getCameraImage(uri, imageRequest.getResizeOptions());
        }
        return null;
    }

    @Nullable
    private EncodedImage getCameraImage(Uri uri, @Nullable ResizeOptions resizeOptions) throws IOException {
        Cursor cursor;
        EncodedImage thumbnail;
        if (resizeOptions == null || (cursor = this.mContentResolver.query(uri, PROJECTION, (String) null, (String[]) null, (String) null)) == null) {
            return null;
        }
        try {
            if (!cursor.moveToFirst() || (thumbnail = getThumbnail(resizeOptions, cursor.getLong(cursor.getColumnIndex("_id")))) == null) {
                cursor.close();
                return null;
            }
            thumbnail.setRotationAngle(getRotationAngle(cursor.getString(cursor.getColumnIndex("_data"))));
            return thumbnail;
        } finally {
            cursor.close();
        }
    }

    @Nullable
    private EncodedImage getThumbnail(ResizeOptions resizeOptions, long imageId) throws IOException {
        Cursor thumbnailCursor;
        int thumbnailKind = getThumbnailKind(resizeOptions);
        if (thumbnailKind == 0 || (thumbnailCursor = MediaStore.Images.Thumbnails.queryMiniThumbnail(this.mContentResolver, imageId, thumbnailKind, THUMBNAIL_PROJECTION)) == null) {
            return null;
        }
        try {
            if (thumbnailCursor.moveToFirst()) {
                String thumbnailUri = thumbnailCursor.getString(thumbnailCursor.getColumnIndex("_data"));
                if (new File(thumbnailUri).exists()) {
                    return getEncodedImage(new FileInputStream(thumbnailUri), getLength(thumbnailUri));
                }
            }
            thumbnailCursor.close();
            return null;
        } finally {
            thumbnailCursor.close();
        }
    }

    private static int getThumbnailKind(ResizeOptions resizeOptions) {
        Rect rect = MICRO_THUMBNAIL_DIMENSIONS;
        if (ThumbnailSizeChecker.isImageBigEnough(rect.width(), rect.height(), resizeOptions)) {
            return 3;
        }
        Rect rect2 = MINI_THUMBNAIL_DIMENSIONS;
        if (ThumbnailSizeChecker.isImageBigEnough(rect2.width(), rect2.height(), resizeOptions)) {
            return 1;
        }
        return 0;
    }

    private static int getLength(String pathname) {
        if (pathname == null) {
            return -1;
        }
        return (int) new File(pathname).length();
    }

    /* access modifiers changed from: protected */
    public String getProducerName() {
        return PRODUCER_NAME;
    }

    private static int getRotationAngle(String pathname) {
        if (pathname != null) {
            try {
                return JfifUtil.getAutoRotateAngleFromOrientation(new ExifInterface(pathname).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1));
            } catch (IOException ioe) {
                FLog.m59e(TAG, (Throwable) ioe, "Unable to retrieve thumbnail rotation for %s", pathname);
            }
        }
        return 0;
    }
}
