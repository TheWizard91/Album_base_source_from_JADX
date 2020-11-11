package com.facebook.imagepipeline.request;

import android.net.Uri;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Objects;
import com.facebook.common.media.MediaUtils;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.common.BytesRange;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.listener.RequestListener;
import com.google.firebase.messaging.Constants;
import java.io.File;
import javax.annotation.Nullable;

public class ImageRequest {
    @Nullable
    private final BytesRange mBytesRange;
    private final CacheChoice mCacheChoice;
    @Nullable
    private final Boolean mDecodePrefetches;
    private final ImageDecodeOptions mImageDecodeOptions;
    private final boolean mIsDiskCacheEnabled;
    private final boolean mIsMemoryCacheEnabled;
    private final boolean mLocalThumbnailPreviewsEnabled;
    private final RequestLevel mLowestPermittedRequestLevel;
    @Nullable
    private final Postprocessor mPostprocessor;
    private final boolean mProgressiveRenderingEnabled;
    @Nullable
    private final RequestListener mRequestListener;
    private final Priority mRequestPriority;
    @Nullable
    private final ResizeOptions mResizeOptions;
    @Nullable
    private final Boolean mResizingAllowedOverride;
    private final RotationOptions mRotationOptions;
    private File mSourceFile;
    private final Uri mSourceUri;
    private final int mSourceUriType;

    public enum CacheChoice {
        SMALL,
        DEFAULT
    }

    @Nullable
    public static ImageRequest fromFile(@Nullable File file) {
        if (file == null) {
            return null;
        }
        return fromUri(UriUtil.getUriForFile(file));
    }

    @Nullable
    public static ImageRequest fromUri(@Nullable Uri uri) {
        if (uri == null) {
            return null;
        }
        return ImageRequestBuilder.newBuilderWithSource(uri).build();
    }

    @Nullable
    public static ImageRequest fromUri(@Nullable String uriString) {
        if (uriString == null || uriString.length() == 0) {
            return null;
        }
        return fromUri(Uri.parse(uriString));
    }

    protected ImageRequest(ImageRequestBuilder builder) {
        RotationOptions rotationOptions;
        this.mCacheChoice = builder.getCacheChoice();
        Uri sourceUri = builder.getSourceUri();
        this.mSourceUri = sourceUri;
        this.mSourceUriType = getSourceUriType(sourceUri);
        this.mProgressiveRenderingEnabled = builder.isProgressiveRenderingEnabled();
        this.mLocalThumbnailPreviewsEnabled = builder.isLocalThumbnailPreviewsEnabled();
        this.mImageDecodeOptions = builder.getImageDecodeOptions();
        this.mResizeOptions = builder.getResizeOptions();
        if (builder.getRotationOptions() == null) {
            rotationOptions = RotationOptions.autoRotate();
        } else {
            rotationOptions = builder.getRotationOptions();
        }
        this.mRotationOptions = rotationOptions;
        this.mBytesRange = builder.getBytesRange();
        this.mRequestPriority = builder.getRequestPriority();
        this.mLowestPermittedRequestLevel = builder.getLowestPermittedRequestLevel();
        this.mIsDiskCacheEnabled = builder.isDiskCacheEnabled();
        this.mIsMemoryCacheEnabled = builder.isMemoryCacheEnabled();
        this.mDecodePrefetches = builder.shouldDecodePrefetches();
        this.mPostprocessor = builder.getPostprocessor();
        this.mRequestListener = builder.getRequestListener();
        this.mResizingAllowedOverride = builder.getResizingAllowedOverride();
    }

    public CacheChoice getCacheChoice() {
        return this.mCacheChoice;
    }

    public Uri getSourceUri() {
        return this.mSourceUri;
    }

    public int getSourceUriType() {
        return this.mSourceUriType;
    }

    public int getPreferredWidth() {
        ResizeOptions resizeOptions = this.mResizeOptions;
        if (resizeOptions != null) {
            return resizeOptions.width;
        }
        return 2048;
    }

    public int getPreferredHeight() {
        ResizeOptions resizeOptions = this.mResizeOptions;
        if (resizeOptions != null) {
            return resizeOptions.height;
        }
        return 2048;
    }

    @Nullable
    public ResizeOptions getResizeOptions() {
        return this.mResizeOptions;
    }

    public RotationOptions getRotationOptions() {
        return this.mRotationOptions;
    }

    @Deprecated
    public boolean getAutoRotateEnabled() {
        return this.mRotationOptions.useImageMetadata();
    }

    @Nullable
    public BytesRange getBytesRange() {
        return this.mBytesRange;
    }

    public ImageDecodeOptions getImageDecodeOptions() {
        return this.mImageDecodeOptions;
    }

    public boolean getProgressiveRenderingEnabled() {
        return this.mProgressiveRenderingEnabled;
    }

    public boolean getLocalThumbnailPreviewsEnabled() {
        return this.mLocalThumbnailPreviewsEnabled;
    }

    public Priority getPriority() {
        return this.mRequestPriority;
    }

    public RequestLevel getLowestPermittedRequestLevel() {
        return this.mLowestPermittedRequestLevel;
    }

    public boolean isDiskCacheEnabled() {
        return this.mIsDiskCacheEnabled;
    }

    public boolean isMemoryCacheEnabled() {
        return this.mIsMemoryCacheEnabled;
    }

    @Nullable
    public Boolean shouldDecodePrefetches() {
        return this.mDecodePrefetches;
    }

    @Nullable
    public Boolean getResizingAllowedOverride() {
        return this.mResizingAllowedOverride;
    }

    public synchronized File getSourceFile() {
        if (this.mSourceFile == null) {
            this.mSourceFile = new File(this.mSourceUri.getPath());
        }
        return this.mSourceFile;
    }

    @Nullable
    public Postprocessor getPostprocessor() {
        return this.mPostprocessor;
    }

    @Nullable
    public RequestListener getRequestListener() {
        return this.mRequestListener;
    }

    public boolean equals(Object o) {
        if (!(o instanceof ImageRequest)) {
            return false;
        }
        ImageRequest request = (ImageRequest) o;
        if (this.mLocalThumbnailPreviewsEnabled != request.mLocalThumbnailPreviewsEnabled || this.mIsDiskCacheEnabled != request.mIsDiskCacheEnabled || this.mIsMemoryCacheEnabled != request.mIsMemoryCacheEnabled || !Objects.equal(this.mSourceUri, request.mSourceUri) || !Objects.equal(this.mCacheChoice, request.mCacheChoice) || !Objects.equal(this.mSourceFile, request.mSourceFile) || !Objects.equal(this.mBytesRange, request.mBytesRange) || !Objects.equal(this.mImageDecodeOptions, request.mImageDecodeOptions) || !Objects.equal(this.mResizeOptions, request.mResizeOptions) || !Objects.equal(this.mRequestPriority, request.mRequestPriority) || !Objects.equal(this.mLowestPermittedRequestLevel, request.mLowestPermittedRequestLevel) || !Objects.equal(this.mDecodePrefetches, request.mDecodePrefetches) || !Objects.equal(this.mResizingAllowedOverride, request.mResizingAllowedOverride) || !Objects.equal(this.mRotationOptions, request.mRotationOptions)) {
            return false;
        }
        Postprocessor postprocessor = this.mPostprocessor;
        CacheKey thatPostprocessorKey = null;
        CacheKey thisPostprocessorKey = postprocessor != null ? postprocessor.getPostprocessorCacheKey() : null;
        Postprocessor postprocessor2 = request.mPostprocessor;
        if (postprocessor2 != null) {
            thatPostprocessorKey = postprocessor2.getPostprocessorCacheKey();
        }
        return Objects.equal(thisPostprocessorKey, thatPostprocessorKey);
    }

    public int hashCode() {
        Postprocessor postprocessor = this.mPostprocessor;
        return Objects.hashCode(this.mCacheChoice, this.mSourceUri, Boolean.valueOf(this.mLocalThumbnailPreviewsEnabled), this.mBytesRange, this.mRequestPriority, this.mLowestPermittedRequestLevel, Boolean.valueOf(this.mIsDiskCacheEnabled), Boolean.valueOf(this.mIsMemoryCacheEnabled), this.mImageDecodeOptions, this.mDecodePrefetches, this.mResizeOptions, this.mRotationOptions, postprocessor != null ? postprocessor.getPostprocessorCacheKey() : null, this.mResizingAllowedOverride);
    }

    public String toString() {
        return Objects.toStringHelper((Object) this).add("uri", (Object) this.mSourceUri).add("cacheChoice", (Object) this.mCacheChoice).add("decodeOptions", (Object) this.mImageDecodeOptions).add("postprocessor", (Object) this.mPostprocessor).add(Constants.FirelogAnalytics.PARAM_PRIORITY, (Object) this.mRequestPriority).add("resizeOptions", (Object) this.mResizeOptions).add("rotationOptions", (Object) this.mRotationOptions).add("bytesRange", (Object) this.mBytesRange).add("resizingAllowedOverride", (Object) this.mResizingAllowedOverride).add("progressiveRenderingEnabled", this.mProgressiveRenderingEnabled).add("localThumbnailPreviewsEnabled", this.mLocalThumbnailPreviewsEnabled).add("lowestPermittedRequestLevel", (Object) this.mLowestPermittedRequestLevel).add("isDiskCacheEnabled", this.mIsDiskCacheEnabled).add("isMemoryCacheEnabled", this.mIsMemoryCacheEnabled).add("decodePrefetches", (Object) this.mDecodePrefetches).toString();
    }

    public enum RequestLevel {
        FULL_FETCH(1),
        DISK_CACHE(2),
        ENCODED_MEMORY_CACHE(3),
        BITMAP_MEMORY_CACHE(4);
        
        private int mValue;

        private RequestLevel(int value) {
            this.mValue = value;
        }

        public int getValue() {
            return this.mValue;
        }

        public static RequestLevel getMax(RequestLevel requestLevel1, RequestLevel requestLevel2) {
            return requestLevel1.getValue() > requestLevel2.getValue() ? requestLevel1 : requestLevel2;
        }
    }

    private static int getSourceUriType(Uri uri) {
        if (uri == null) {
            return -1;
        }
        if (UriUtil.isNetworkUri(uri)) {
            return 0;
        }
        if (UriUtil.isLocalFileUri(uri)) {
            if (MediaUtils.isVideo(MediaUtils.extractMime(uri.getPath()))) {
                return 2;
            }
            return 3;
        } else if (UriUtil.isLocalContentUri(uri)) {
            return 4;
        } else {
            if (UriUtil.isLocalAssetUri(uri)) {
                return 5;
            }
            if (UriUtil.isLocalResourceUri(uri)) {
                return 6;
            }
            if (UriUtil.isDataUri(uri)) {
                return 7;
            }
            if (UriUtil.isQualifiedResourceUri(uri)) {
                return 8;
            }
            return -1;
        }
    }
}
