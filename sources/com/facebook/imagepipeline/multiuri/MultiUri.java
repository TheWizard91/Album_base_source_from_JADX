package com.facebook.imagepipeline.multiuri;

import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSources;
import com.facebook.datasource.FirstAvailableDataSourceSupplier;
import com.facebook.datasource.IncreasingQualityDataSourceSupplier;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;

public class MultiUri {
    private static final NullPointerException NO_REQUEST_EXCEPTION = new NullPointerException("No image request was specified!");
    @Nullable
    private ImageRequest mLowResImageRequest;
    @Nullable
    private ImageRequest[] mMultiImageRequests;

    private MultiUri(Builder builder) {
        this.mLowResImageRequest = builder.mLowResImageRequest;
        this.mMultiImageRequests = builder.mMultiImageRequests;
    }

    @Nullable
    public ImageRequest getLowResImageRequest() {
        return this.mLowResImageRequest;
    }

    @Nullable
    public ImageRequest[] getMultiImageRequests() {
        return this.mMultiImageRequests;
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {
        /* access modifiers changed from: private */
        @Nullable
        public ImageRequest mLowResImageRequest;
        /* access modifiers changed from: private */
        @Nullable
        public ImageRequest[] mMultiImageRequests;

        private Builder() {
        }

        public MultiUri build() {
            return new MultiUri(this);
        }

        public Builder setLowResImageRequest(@Nullable ImageRequest lowResImageRequest) {
            this.mLowResImageRequest = lowResImageRequest;
            return this;
        }

        public Builder setImageRequests(@Nullable ImageRequest... multiImageRequests) {
            this.mMultiImageRequests = multiImageRequests;
            return this;
        }
    }

    public static Supplier<DataSource<CloseableReference<CloseableImage>>> getMultiUriDatasource(ImagePipeline imagePipeline, ImageRequest lowResImageRequest, ImageRequest mainImageRequest, Object callerContext) {
        return getMultiUriDatasourceSupplier(imagePipeline, create().setLowResImageRequest(lowResImageRequest).setImageRequests(mainImageRequest).build(), (ImageRequest) null, callerContext, (RequestListener) null, (String) null);
    }

    public static Supplier<DataSource<CloseableReference<CloseableImage>>> getMultiUriDatasourceSupplier(ImagePipeline imagePipeline, MultiUri multiUri, @Nullable ImageRequest imageRequest, Object callerContext, @Nullable RequestListener requestListener, @Nullable String id) {
        Supplier<DataSource<CloseableReference<CloseableImage>>> supplier = null;
        if (imageRequest != null) {
            supplier = getImageRequestDataSourceSupplier(imagePipeline, imageRequest, callerContext, requestListener, id);
        } else if (multiUri.getMultiImageRequests() != null) {
            supplier = getFirstAvailableDataSourceSupplier(imagePipeline, callerContext, requestListener, multiUri.getMultiImageRequests(), true, id);
        }
        if (!(supplier == null || multiUri.getLowResImageRequest() == null)) {
            LinkedList linkedList = new LinkedList();
            linkedList.add(supplier);
            linkedList.add(getImageRequestDataSourceSupplier(imagePipeline, multiUri.getLowResImageRequest(), callerContext, requestListener, id));
            supplier = IncreasingQualityDataSourceSupplier.create(linkedList, false);
        }
        if (supplier == null) {
            return DataSources.getFailedDataSourceSupplier(NO_REQUEST_EXCEPTION);
        }
        return supplier;
    }

    public static DataSource<CloseableReference<CloseableImage>> getImageRequestDataSource(ImagePipeline imagePipeline, ImageRequest imageRequest, Object callerContext, @Nullable RequestListener requestListener, @Nullable String uiComponentId) {
        return imagePipeline.fetchDecodedImage(imageRequest, callerContext, ImageRequest.RequestLevel.FULL_FETCH, requestListener, uiComponentId);
    }

    private static Supplier<DataSource<CloseableReference<CloseableImage>>> getFirstAvailableDataSourceSupplier(ImagePipeline imagePipeline, Object callerContext, @Nullable RequestListener requestListener, ImageRequest[] imageRequests, boolean tryBitmapCacheOnlyFirst, @Nullable String uiComponentId) {
        List<Supplier<DataSource<CloseableReference<CloseableImage>>>> suppliers = new ArrayList<>(imageRequests.length * 2);
        if (tryBitmapCacheOnlyFirst) {
            for (ImageRequest imageRequestDataSourceSupplier : imageRequests) {
                suppliers.add(getImageRequestDataSourceSupplier(imagePipeline, imageRequestDataSourceSupplier, callerContext, ImageRequest.RequestLevel.BITMAP_MEMORY_CACHE, requestListener, uiComponentId));
            }
        }
        for (ImageRequest imageRequestDataSourceSupplier2 : imageRequests) {
            suppliers.add(getImageRequestDataSourceSupplier(imagePipeline, imageRequestDataSourceSupplier2, callerContext, requestListener, uiComponentId));
        }
        return FirstAvailableDataSourceSupplier.create(suppliers);
    }

    private static Supplier<DataSource<CloseableReference<CloseableImage>>> getImageRequestDataSourceSupplier(ImagePipeline imagePipeline, ImageRequest imageRequest, Object callerContext, ImageRequest.RequestLevel requestLevel, RequestListener requestListener, @Nullable String uiComponentId) {
        final ImagePipeline imagePipeline2 = imagePipeline;
        final ImageRequest imageRequest2 = imageRequest;
        final Object obj = callerContext;
        final RequestListener requestListener2 = requestListener;
        final String str = uiComponentId;
        return new Supplier<DataSource<CloseableReference<CloseableImage>>>() {
            public DataSource<CloseableReference<CloseableImage>> get() {
                return MultiUri.getImageRequestDataSource(imagePipeline2, imageRequest2, obj, requestListener2, str);
            }
        };
    }

    private static Supplier<DataSource<CloseableReference<CloseableImage>>> getImageRequestDataSourceSupplier(ImagePipeline imagePipeline, ImageRequest imageRequest, Object callerContext, RequestListener requestListener, @Nullable String uiComponentId) {
        return getImageRequestDataSourceSupplier(imagePipeline, imageRequest, callerContext, ImageRequest.RequestLevel.FULL_FETCH, requestListener, uiComponentId);
    }
}
