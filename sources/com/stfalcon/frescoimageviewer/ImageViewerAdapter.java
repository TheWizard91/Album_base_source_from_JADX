package com.stfalcon.frescoimageviewer;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.stfalcon.frescoimageviewer.adapter.RecyclingPagerAdapter;
import com.stfalcon.frescoimageviewer.adapter.ViewHolder;
import com.stfalcon.frescoimageviewer.drawee.ZoomableDraweeView;
import java.util.HashSet;
import java.util.Iterator;
import p023me.relex.photodraweeview.OnScaleChangeListener;

class ImageViewerAdapter extends RecyclingPagerAdapter<ImageViewHolder> {
    private Context context;
    /* access modifiers changed from: private */
    public ImageViewer.DataSet<?> dataSet;
    /* access modifiers changed from: private */
    public GenericDraweeHierarchyBuilder hierarchyBuilder;
    private HashSet<ImageViewHolder> holders = new HashSet<>();
    /* access modifiers changed from: private */
    public ImageRequestBuilder imageRequestBuilder;
    private boolean isZoomingAllowed;

    ImageViewerAdapter(Context context2, ImageViewer.DataSet<?> dataSet2, ImageRequestBuilder imageRequestBuilder2, GenericDraweeHierarchyBuilder hierarchyBuilder2, boolean isZoomingAllowed2) {
        this.context = context2;
        this.dataSet = dataSet2;
        this.imageRequestBuilder = imageRequestBuilder2;
        this.hierarchyBuilder = hierarchyBuilder2;
        this.isZoomingAllowed = isZoomingAllowed2;
    }

    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ZoomableDraweeView drawee = new ZoomableDraweeView(this.context);
        drawee.setEnabled(this.isZoomingAllowed);
        ImageViewHolder holder = new ImageViewHolder(drawee);
        this.holders.add(holder);
        return holder;
    }

    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.bind(position);
    }

    public int getItemCount() {
        return this.dataSet.getData().size();
    }

    /* access modifiers changed from: package-private */
    public boolean isScaled(int index) {
        Iterator<ImageViewHolder> it = this.holders.iterator();
        while (it.hasNext()) {
            ImageViewHolder holder = it.next();
            if (holder.position == index) {
                return holder.isScaled;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void resetScale(int index) {
        Iterator<ImageViewHolder> it = this.holders.iterator();
        while (it.hasNext()) {
            ImageViewHolder holder = it.next();
            if (holder.position == index) {
                holder.resetScale();
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public String getUrl(int index) {
        return this.dataSet.format(index);
    }

    /* access modifiers changed from: private */
    public BaseControllerListener<ImageInfo> getDraweeControllerListener(final ZoomableDraweeView drawee) {
        return new BaseControllerListener<ImageInfo>() {
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo != null) {
                    drawee.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            }
        };
    }

    class ImageViewHolder extends ViewHolder implements OnScaleChangeListener {
        private ZoomableDraweeView drawee;
        /* access modifiers changed from: private */
        public boolean isScaled;
        /* access modifiers changed from: private */
        public int position = -1;

        ImageViewHolder(View itemView) {
            super(itemView);
            this.drawee = (ZoomableDraweeView) itemView;
        }

        /* access modifiers changed from: package-private */
        public void bind(int position2) {
            this.position = position2;
            tryToSetHierarchy();
            setController(ImageViewerAdapter.this.dataSet.format(position2));
            this.drawee.setOnScaleChangeListener(this);
        }

        public void onScaleChange(float scaleFactor, float focusX, float focusY) {
            this.isScaled = this.drawee.getScale() > 1.0f;
        }

        /* access modifiers changed from: package-private */
        public void resetScale() {
            this.drawee.setScale(1.0f, true);
        }

        private void tryToSetHierarchy() {
            if (ImageViewerAdapter.this.hierarchyBuilder != null) {
                ImageViewerAdapter.this.hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
                this.drawee.setHierarchy(ImageViewerAdapter.this.hierarchyBuilder.build());
            }
        }

        private void setController(String url) {
            PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder();
            controllerBuilder.setUri(url);
            controllerBuilder.setOldController(this.drawee.getController());
            controllerBuilder.setControllerListener(ImageViewerAdapter.this.getDraweeControllerListener(this.drawee));
            if (ImageViewerAdapter.this.imageRequestBuilder != null) {
                ImageViewerAdapter.this.imageRequestBuilder.setSource(Uri.parse(url));
                controllerBuilder.setImageRequest(ImageViewerAdapter.this.imageRequestBuilder.build());
            }
            this.drawee.setController(controllerBuilder.build());
        }
    }
}
