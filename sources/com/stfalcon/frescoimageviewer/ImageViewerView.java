package com.stfalcon.frescoimageviewer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.viewpager.widget.ViewPager;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.stfalcon.frescoimageviewer.SwipeDirectionDetector;
import com.stfalcon.frescoimageviewer.SwipeToDismissListener;

class ImageViewerView extends RelativeLayout implements OnDismissListener, SwipeToDismissListener.OnViewMoveListener {
    private ImageViewerAdapter adapter;
    private View backgroundView;
    private GenericDraweeHierarchyBuilder customDraweeHierarchyBuilder;
    private ImageRequestBuilder customImageRequestBuilder;
    /* access modifiers changed from: private */
    public SwipeDirectionDetector.Direction direction;
    private SwipeDirectionDetector directionDetector;
    private ViewGroup dismissContainer;
    private GestureDetectorCompat gestureDetector;
    /* access modifiers changed from: private */
    public boolean isOverlayWasClicked;
    private boolean isSwipeToDismissAllowed = true;
    private boolean isZoomingAllowed = true;
    private OnDismissListener onDismissListener;
    private View overlayView;
    private ViewPager.OnPageChangeListener pageChangeListener;
    /* access modifiers changed from: private */
    public MultiTouchViewPager pager;
    private ScaleGestureDetector scaleDetector;
    private SwipeToDismissListener swipeDismissListener;
    private boolean wasScaled;

    public ImageViewerView(Context context) {
        super(context);
        init();
    }

    public ImageViewerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageViewerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setUrls(ImageViewer.DataSet<?> dataSet, int startPosition) {
        ImageViewerAdapter imageViewerAdapter = new ImageViewerAdapter(getContext(), dataSet, this.customImageRequestBuilder, this.customDraweeHierarchyBuilder, this.isZoomingAllowed);
        this.adapter = imageViewerAdapter;
        this.pager.setAdapter(imageViewerAdapter);
        setStartPosition(startPosition);
    }

    public void setCustomImageRequestBuilder(ImageRequestBuilder customImageRequestBuilder2) {
        this.customImageRequestBuilder = customImageRequestBuilder2;
    }

    public void setCustomDraweeHierarchyBuilder(GenericDraweeHierarchyBuilder customDraweeHierarchyBuilder2) {
        this.customDraweeHierarchyBuilder = customDraweeHierarchyBuilder2;
    }

    public void setBackgroundColor(int color) {
        findViewById(C2508R.C2511id.backgroundView).setBackgroundColor(color);
    }

    public void setOverlayView(View view) {
        this.overlayView = view;
        if (view != null) {
            this.dismissContainer.addView(view);
        }
    }

    public void allowZooming(boolean allowZooming) {
        this.isZoomingAllowed = allowZooming;
    }

    public void allowSwipeToDismiss(boolean allowSwipeToDismiss) {
        this.isSwipeToDismissAllowed = allowSwipeToDismiss;
    }

    public void setImageMargin(int marginPixels) {
        this.pager.setPageMargin(marginPixels);
    }

    public void setContainerPadding(int[] paddingPixels) {
        this.pager.setPadding(paddingPixels[0], paddingPixels[1], paddingPixels[2], paddingPixels[3]);
    }

    private void init() {
        inflate(getContext(), C2508R.C2512layout.image_viewer, this);
        this.backgroundView = findViewById(C2508R.C2511id.backgroundView);
        this.pager = (MultiTouchViewPager) findViewById(C2508R.C2511id.pager);
        this.dismissContainer = (ViewGroup) findViewById(C2508R.C2511id.container);
        SwipeToDismissListener swipeToDismissListener = new SwipeToDismissListener(findViewById(C2508R.C2511id.dismissView), this, this);
        this.swipeDismissListener = swipeToDismissListener;
        this.dismissContainer.setOnTouchListener(swipeToDismissListener);
        this.directionDetector = new SwipeDirectionDetector(getContext()) {
            public void onDirectionDetected(SwipeDirectionDetector.Direction direction) {
                SwipeDirectionDetector.Direction unused = ImageViewerView.this.direction = direction;
            }
        };
        this.scaleDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener());
        this.gestureDetector = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (!ImageViewerView.this.pager.isScrolled()) {
                    return false;
                }
                ImageViewerView imageViewerView = ImageViewerView.this;
                imageViewerView.onClick(e, imageViewerView.isOverlayWasClicked);
                return false;
            }
        });
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        onUpDownEvent(event);
        if (this.direction == null && (this.scaleDetector.isInProgress() || event.getPointerCount() > 1)) {
            this.wasScaled = true;
            return this.pager.dispatchTouchEvent(event);
        } else if (this.adapter.isScaled(this.pager.getCurrentItem())) {
            return super.dispatchTouchEvent(event);
        } else {
            this.directionDetector.onTouchEvent(event);
            if (this.direction != null) {
                int i = C15233.f187x4ca8b052[this.direction.ordinal()];
                if (i == 1 || i == 2) {
                    if (this.isSwipeToDismissAllowed && !this.wasScaled && this.pager.isScrolled()) {
                        return this.swipeDismissListener.onTouch(this.dismissContainer, event);
                    }
                } else if (i == 3 || i == 4) {
                    return this.pager.dispatchTouchEvent(event);
                }
            }
            return true;
        }
    }

    /* renamed from: com.stfalcon.frescoimageviewer.ImageViewerView$3 */
    static /* synthetic */ class C15233 {

        /* renamed from: $SwitchMap$com$stfalcon$frescoimageviewer$SwipeDirectionDetector$Direction */
        static final /* synthetic */ int[] f187x4ca8b052;

        static {
            int[] iArr = new int[SwipeDirectionDetector.Direction.values().length];
            f187x4ca8b052 = iArr;
            try {
                iArr[SwipeDirectionDetector.Direction.UP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f187x4ca8b052[SwipeDirectionDetector.Direction.DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f187x4ca8b052[SwipeDirectionDetector.Direction.LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f187x4ca8b052[SwipeDirectionDetector.Direction.RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public void onDismiss() {
        OnDismissListener onDismissListener2 = this.onDismissListener;
        if (onDismissListener2 != null) {
            onDismissListener2.onDismiss();
        }
    }

    public void onViewMove(float translationY, int translationLimit) {
        float alpha = 1.0f - (((1.0f / ((float) translationLimit)) / 4.0f) * Math.abs(translationY));
        this.backgroundView.setAlpha(alpha);
        View view = this.overlayView;
        if (view != null) {
            view.setAlpha(alpha);
        }
    }

    public void setOnDismissListener(OnDismissListener onDismissListener2) {
        this.onDismissListener = onDismissListener2;
    }

    public void resetScale() {
        this.adapter.resetScale(this.pager.getCurrentItem());
    }

    public boolean isScaled() {
        return this.adapter.isScaled(this.pager.getCurrentItem());
    }

    public String getUrl() {
        return this.adapter.getUrl(this.pager.getCurrentItem());
    }

    public void setPageChangeListener(ViewPager.OnPageChangeListener pageChangeListener2) {
        this.pager.removeOnPageChangeListener(this.pageChangeListener);
        this.pageChangeListener = pageChangeListener2;
        this.pager.addOnPageChangeListener(pageChangeListener2);
        pageChangeListener2.onPageSelected(this.pager.getCurrentItem());
    }

    private void setStartPosition(int position) {
        this.pager.setCurrentItem(position);
    }

    private void onUpDownEvent(MotionEvent event) {
        if (event.getAction() == 1) {
            onActionUp(event);
        }
        if (event.getAction() == 0) {
            onActionDown(event);
        }
        this.scaleDetector.onTouchEvent(event);
        this.gestureDetector.onTouchEvent(event);
    }

    private void onActionDown(MotionEvent event) {
        this.direction = null;
        this.wasScaled = false;
        this.pager.dispatchTouchEvent(event);
        this.swipeDismissListener.onTouch(this.dismissContainer, event);
        this.isOverlayWasClicked = dispatchOverlayTouch(event);
    }

    private void onActionUp(MotionEvent event) {
        this.swipeDismissListener.onTouch(this.dismissContainer, event);
        this.pager.dispatchTouchEvent(event);
        this.isOverlayWasClicked = dispatchOverlayTouch(event);
    }

    /* access modifiers changed from: private */
    public void onClick(MotionEvent event, boolean isOverlayWasClicked2) {
        View view = this.overlayView;
        if (view != null && !isOverlayWasClicked2) {
            AnimationUtils.animateVisibility(view);
            super.dispatchTouchEvent(event);
        }
    }

    private boolean dispatchOverlayTouch(MotionEvent event) {
        View view = this.overlayView;
        return view != null && view.getVisibility() == 0 && this.overlayView.dispatchTouchEvent(event);
    }
}
