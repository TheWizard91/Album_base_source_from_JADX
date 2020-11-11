package com.alexvasilkov.gestures.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.alexvasilkov.gestures.GestureController;
import com.alexvasilkov.gestures.GestureControllerForPager;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.State;
import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.internal.DebugOverlay;
import com.alexvasilkov.gestures.internal.GestureDebug;
import com.alexvasilkov.gestures.utils.ClipHelper;
import com.alexvasilkov.gestures.utils.CropUtils;
import com.alexvasilkov.gestures.views.interfaces.AnimatorView;
import com.alexvasilkov.gestures.views.interfaces.ClipBounds;
import com.alexvasilkov.gestures.views.interfaces.ClipView;
import com.alexvasilkov.gestures.views.interfaces.GestureView;

public class GestureImageView extends ImageView implements GestureView, ClipView, ClipBounds, AnimatorView {
    private final ClipHelper clipBoundsHelper;
    private final ClipHelper clipViewHelper;
    private GestureControllerForPager controller;
    private final Matrix imageMatrix;
    private ViewPositionAnimator positionAnimator;

    @Deprecated
    public interface OnSnapshotLoadedListener {
        void onSnapshotLoaded(Bitmap bitmap);
    }

    public GestureImageView(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public GestureImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.clipViewHelper = new ClipHelper(this);
        this.clipBoundsHelper = new ClipHelper(this);
        this.imageMatrix = new Matrix();
        ensureControllerCreated();
        this.controller.getSettings().initFromAttributes(context, attrs);
        this.controller.addOnStateChangeListener(new GestureController.OnStateChangeListener() {
            public void onStateChanged(State state) {
                GestureImageView.this.applyState(state);
            }

            public void onStateReset(State oldState, State newState) {
                GestureImageView.this.applyState(newState);
            }
        });
        setScaleType(ImageView.ScaleType.MATRIX);
    }

    private void ensureControllerCreated() {
        if (this.controller == null) {
            this.controller = new GestureControllerForPager(this);
        }
    }

    public void draw(Canvas canvas) {
        this.clipBoundsHelper.onPreDraw(canvas);
        this.clipViewHelper.onPreDraw(canvas);
        super.draw(canvas);
        this.clipViewHelper.onPostDraw(canvas);
        this.clipBoundsHelper.onPostDraw(canvas);
        if (GestureDebug.isDrawDebugOverlay()) {
            DebugOverlay.drawDebug(this, canvas);
        }
    }

    public GestureControllerForPager getController() {
        return this.controller;
    }

    public ViewPositionAnimator getPositionAnimator() {
        if (this.positionAnimator == null) {
            this.positionAnimator = new ViewPositionAnimator(this);
        }
        return this.positionAnimator;
    }

    public void clipView(RectF rect, float rotation) {
        this.clipViewHelper.clipView(rect, rotation);
    }

    public void clipBounds(RectF rect) {
        this.clipBoundsHelper.clipView(rect, 0.0f);
    }

    @Deprecated
    public void getSnapshot(OnSnapshotLoadedListener listener) {
        if (getDrawable() != null) {
            listener.onSnapshotLoaded(crop());
        }
    }

    public Bitmap crop() {
        return CropUtils.crop(getDrawable(), this.controller);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.controller.onTouch(this, event);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        this.controller.getSettings().setViewport((width - getPaddingLeft()) - getPaddingRight(), (height - getPaddingTop()) - getPaddingBottom());
        this.controller.resetState();
    }

    public void setImageResource(int resId) {
        setImageDrawable(getDrawable(getContext(), resId));
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        ensureControllerCreated();
        Settings settings = this.controller.getSettings();
        float oldWidth = (float) settings.getImageW();
        float oldHeight = (float) settings.getImageH();
        if (drawable == null) {
            settings.setImage(0, 0);
        } else if (drawable.getIntrinsicWidth() == -1 || drawable.getIntrinsicHeight() == -1) {
            settings.setImage(settings.getMovementAreaW(), settings.getMovementAreaH());
        } else {
            settings.setImage(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        float newWidth = (float) settings.getImageW();
        float newHeight = (float) settings.getImageH();
        if (newWidth <= 0.0f || newHeight <= 0.0f || oldWidth <= 0.0f || oldHeight <= 0.0f) {
            this.controller.resetState();
            return;
        }
        this.controller.getStateController().setTempZoomPatch(Math.min(oldWidth / newWidth, oldHeight / newHeight));
        this.controller.updateState();
        this.controller.getStateController().setTempZoomPatch(0.0f);
    }

    /* access modifiers changed from: protected */
    public void applyState(State state) {
        state.get(this.imageMatrix);
        setImageMatrix(this.imageMatrix);
    }

    private static Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getDrawable(id);
        }
        return context.getResources().getDrawable(id);
    }
}
