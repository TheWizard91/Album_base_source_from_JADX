package p023me.relex.photodraweeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

/* renamed from: me.relex.photodraweeview.PhotoDraweeView */
public class PhotoDraweeView extends SimpleDraweeView implements IAttacher {
    private Attacher mAttacher;
    /* access modifiers changed from: private */
    public boolean mEnableDraweeMatrix = true;

    public PhotoDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init();
    }

    public PhotoDraweeView(Context context) {
        super(context);
        init();
    }

    public PhotoDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhotoDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /* access modifiers changed from: protected */
    public void init() {
        Attacher attacher = this.mAttacher;
        if (attacher == null || attacher.getDraweeView() == null) {
            this.mAttacher = new Attacher(this);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int saveCount = canvas.save();
        if (this.mEnableDraweeMatrix) {
            canvas.concat(this.mAttacher.getDrawMatrix());
        }
        super.onDraw(canvas);
        canvas.restoreToCount(saveCount);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        init();
        super.onAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.mAttacher.onDetachedFromWindow();
        super.onDetachedFromWindow();
    }

    public float getMinimumScale() {
        return this.mAttacher.getMinimumScale();
    }

    public float getMediumScale() {
        return this.mAttacher.getMediumScale();
    }

    public float getMaximumScale() {
        return this.mAttacher.getMaximumScale();
    }

    public void setMinimumScale(float minimumScale) {
        this.mAttacher.setMinimumScale(minimumScale);
    }

    public void setMediumScale(float mediumScale) {
        this.mAttacher.setMediumScale(mediumScale);
    }

    public void setMaximumScale(float maximumScale) {
        this.mAttacher.setMaximumScale(maximumScale);
    }

    public float getScale() {
        return this.mAttacher.getScale();
    }

    public void setScale(float scale) {
        this.mAttacher.setScale(scale);
    }

    public void setScale(float scale, boolean animate) {
        this.mAttacher.setScale(scale, animate);
    }

    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        this.mAttacher.setScale(scale, focalX, focalY, animate);
    }

    public void setZoomTransitionDuration(long duration) {
        this.mAttacher.setZoomTransitionDuration(duration);
    }

    public void setAllowParentInterceptOnEdge(boolean allow) {
        this.mAttacher.setAllowParentInterceptOnEdge(allow);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener listener) {
        this.mAttacher.setOnDoubleTapListener(listener);
    }

    public void setOnScaleChangeListener(OnScaleChangeListener listener) {
        this.mAttacher.setOnScaleChangeListener(listener);
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        this.mAttacher.setOnLongClickListener(listener);
    }

    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        this.mAttacher.setOnPhotoTapListener(listener);
    }

    public void setOnViewTapListener(OnViewTapListener listener) {
        this.mAttacher.setOnViewTapListener(listener);
    }

    public OnPhotoTapListener getOnPhotoTapListener() {
        return this.mAttacher.getOnPhotoTapListener();
    }

    public OnViewTapListener getOnViewTapListener() {
        return this.mAttacher.getOnViewTapListener();
    }

    public void update(int imageInfoWidth, int imageInfoHeight) {
        this.mAttacher.update(imageInfoWidth, imageInfoHeight);
    }

    public boolean isEnableDraweeMatrix() {
        return this.mEnableDraweeMatrix;
    }

    public void setEnableDraweeMatrix(boolean enableDraweeMatrix) {
        this.mEnableDraweeMatrix = enableDraweeMatrix;
    }

    public void setPhotoUri(Uri uri) {
        setPhotoUri(uri, (Context) null);
    }

    public void setPhotoUri(Uri uri, Context context) {
        this.mEnableDraweeMatrix = false;
        setController(((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) Fresco.newDraweeControllerBuilder().setCallerContext((Object) context)).setUri(uri).setOldController(getController())).setControllerListener(new BaseControllerListener<ImageInfo>() {
            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
                boolean unused = PhotoDraweeView.this.mEnableDraweeMatrix = false;
            }

            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                boolean unused = PhotoDraweeView.this.mEnableDraweeMatrix = true;
                if (imageInfo != null) {
                    PhotoDraweeView.this.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            }

            public void onIntermediateImageFailed(String id, Throwable throwable) {
                super.onIntermediateImageFailed(id, throwable);
                boolean unused = PhotoDraweeView.this.mEnableDraweeMatrix = false;
            }

            public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                super.onIntermediateImageSet(id, imageInfo);
                boolean unused = PhotoDraweeView.this.mEnableDraweeMatrix = true;
                if (imageInfo != null) {
                    PhotoDraweeView.this.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            }
        })).build());
    }
}
