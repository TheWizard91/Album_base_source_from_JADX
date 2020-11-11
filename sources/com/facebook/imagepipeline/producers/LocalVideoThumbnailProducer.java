package com.facebook.imagepipeline.producers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.bitmaps.SimpleBitmapReleaser;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.OriginalEncodedImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.google.android.gms.common.internal.ImagesContract;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class LocalVideoThumbnailProducer implements Producer<CloseableReference<CloseableImage>> {
    static final String CREATED_THUMBNAIL = "createdThumbnail";
    public static final String PRODUCER_NAME = "VideoThumbnailProducer";
    /* access modifiers changed from: private */
    public final ContentResolver mContentResolver;
    private final Executor mExecutor;

    public LocalVideoThumbnailProducer(Executor executor, ContentResolver contentResolver) {
        this.mExecutor = executor;
        this.mContentResolver = contentResolver;
    }

    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
        ProducerListener2 listener = producerContext.getProducerListener();
        final ProducerListener2 producerListener2 = listener;
        final ProducerContext producerContext2 = producerContext;
        final ImageRequest imageRequest = producerContext.getImageRequest();
        final StatefulProducerRunnable cancellableProducerRunnable = new StatefulProducerRunnable<CloseableReference<CloseableImage>>(consumer, listener, producerContext, PRODUCER_NAME) {
            /* access modifiers changed from: protected */
            public void onSuccess(CloseableReference<CloseableImage> result) {
                super.onSuccess(result);
                producerListener2.onUltimateProducerReached(producerContext2, LocalVideoThumbnailProducer.PRODUCER_NAME, result != null);
                producerContext2.setExtra(1, ImagesContract.LOCAL);
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                super.onFailure(e);
                producerListener2.onUltimateProducerReached(producerContext2, LocalVideoThumbnailProducer.PRODUCER_NAME, false);
                producerContext2.setExtra(1, ImagesContract.LOCAL);
            }

            /* access modifiers changed from: protected */
            @Nullable
            public CloseableReference<CloseableImage> getResult() throws Exception {
                String path;
                Bitmap thumbnailBitmap;
                try {
                    path = LocalVideoThumbnailProducer.this.getLocalFilePath(imageRequest);
                } catch (IllegalArgumentException e) {
                    path = null;
                }
                if (path != null) {
                    thumbnailBitmap = ThumbnailUtils.createVideoThumbnail(path, LocalVideoThumbnailProducer.calculateKind(imageRequest));
                } else {
                    thumbnailBitmap = LocalVideoThumbnailProducer.createThumbnailFromContentProvider(LocalVideoThumbnailProducer.this.mContentResolver, imageRequest.getSourceUri());
                }
                if (thumbnailBitmap == null) {
                    return null;
                }
                CloseableStaticBitmap closeableStaticBitmap = new CloseableStaticBitmap(thumbnailBitmap, (ResourceReleaser<Bitmap>) SimpleBitmapReleaser.getInstance(), ImmutableQualityInfo.FULL_QUALITY, 0);
                closeableStaticBitmap.setOriginalEncodedImageInfo(new OriginalEncodedImageInfo(producerContext2.getImageRequest().getSourceUri(), producerContext2.getEncodedImageOrigin(), producerContext2.getCallerContext(), 0, 0, 0));
                return CloseableReference.m124of(closeableStaticBitmap);
            }

            /* access modifiers changed from: protected */
            public Map<String, String> getExtraMapOnSuccess(CloseableReference<CloseableImage> result) {
                return ImmutableMap.m32of(LocalVideoThumbnailProducer.CREATED_THUMBNAIL, String.valueOf(result != null));
            }

            /* access modifiers changed from: protected */
            public void disposeResult(CloseableReference<CloseableImage> result) {
                CloseableReference.closeSafely((CloseableReference<?>) result);
            }
        };
        producerContext.addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
                cancellableProducerRunnable.cancel();
            }
        });
        this.mExecutor.execute(cancellableProducerRunnable);
    }

    /* access modifiers changed from: private */
    public static int calculateKind(ImageRequest imageRequest) {
        if (imageRequest.getPreferredWidth() > 96 || imageRequest.getPreferredHeight() > 96) {
            return 1;
        }
        return 3;
    }

    /* access modifiers changed from: private */
    @Nullable
    public String getLocalFilePath(ImageRequest imageRequest) {
        Uri uri = imageRequest.getSourceUri();
        if (UriUtil.isLocalFileUri(uri)) {
            return imageRequest.getSourceFile().getPath();
        }
        if (!UriUtil.isLocalContentUri(uri)) {
            return null;
        }
        String selection = null;
        String[] selectionArgs = null;
        if (Build.VERSION.SDK_INT >= 19 && "com.android.providers.media.documents".equals(uri.getAuthority())) {
            String documentId = DocumentsContract.getDocumentId(uri);
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            selection = "_id=?";
            selectionArgs = new String[]{documentId.split(":")[1]};
        }
        Cursor cursor = this.mContentResolver.query(uri, new String[]{"_data"}, selection, selectionArgs, (String) null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (cursor == null) {
            return null;
        }
        cursor.close();
        return null;
    }

    /* access modifiers changed from: private */
    @Nullable
    public static Bitmap createThumbnailFromContentProvider(ContentResolver contentResolver, Uri uri) {
        if (Build.VERSION.SDK_INT < 10) {
            return null;
        }
        try {
            ParcelFileDescriptor videoFile = contentResolver.openFileDescriptor(uri, "r");
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(videoFile.getFileDescriptor());
            return mediaMetadataRetriever.getFrameAtTime(-1);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
