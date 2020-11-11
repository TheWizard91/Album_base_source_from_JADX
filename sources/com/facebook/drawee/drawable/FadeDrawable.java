package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import com.facebook.common.internal.Preconditions;
import java.util.Arrays;

public class FadeDrawable extends ArrayDrawable {
    public static final int TRANSITION_NONE = 2;
    public static final int TRANSITION_RUNNING = 1;
    public static final int TRANSITION_STARTING = 0;
    int mAlpha;
    int[] mAlphas;
    private final int mDefaultLayerAlpha;
    private final boolean mDefaultLayerIsOn;
    int mDurationMs;
    boolean[] mIsLayerOn;
    private final Drawable[] mLayers;
    int mPreventInvalidateCount;
    int[] mStartAlphas;
    long mStartTimeMs;
    int mTransitionState;

    public FadeDrawable(Drawable[] layers) {
        this(layers, false);
    }

    public FadeDrawable(Drawable[] layers, boolean allLayersVisible) {
        super(layers);
        int i = 0;
        Preconditions.checkState(layers.length < 1 ? false : true, "At least one layer required!");
        this.mLayers = layers;
        this.mStartAlphas = new int[layers.length];
        this.mAlphas = new int[layers.length];
        this.mAlpha = 255;
        this.mIsLayerOn = new boolean[layers.length];
        this.mPreventInvalidateCount = 0;
        this.mDefaultLayerIsOn = allLayersVisible;
        this.mDefaultLayerAlpha = allLayersVisible ? 255 : i;
        resetInternal();
    }

    public void invalidateSelf() {
        if (this.mPreventInvalidateCount == 0) {
            super.invalidateSelf();
        }
    }

    public void beginBatchMode() {
        this.mPreventInvalidateCount++;
    }

    public void endBatchMode() {
        this.mPreventInvalidateCount--;
        invalidateSelf();
    }

    public void setTransitionDuration(int durationMs) {
        this.mDurationMs = durationMs;
        if (this.mTransitionState == 1) {
            this.mTransitionState = 0;
        }
    }

    public int getTransitionDuration() {
        return this.mDurationMs;
    }

    private void resetInternal() {
        this.mTransitionState = 2;
        Arrays.fill(this.mStartAlphas, this.mDefaultLayerAlpha);
        this.mStartAlphas[0] = 255;
        Arrays.fill(this.mAlphas, this.mDefaultLayerAlpha);
        this.mAlphas[0] = 255;
        Arrays.fill(this.mIsLayerOn, this.mDefaultLayerIsOn);
        this.mIsLayerOn[0] = true;
    }

    public void reset() {
        resetInternal();
        invalidateSelf();
    }

    public void fadeInLayer(int index) {
        this.mTransitionState = 0;
        this.mIsLayerOn[index] = true;
        invalidateSelf();
    }

    public void fadeOutLayer(int index) {
        this.mTransitionState = 0;
        this.mIsLayerOn[index] = false;
        invalidateSelf();
    }

    public void fadeInAllLayers() {
        this.mTransitionState = 0;
        Arrays.fill(this.mIsLayerOn, true);
        invalidateSelf();
    }

    public void fadeOutAllLayers() {
        this.mTransitionState = 0;
        Arrays.fill(this.mIsLayerOn, false);
        invalidateSelf();
    }

    public void fadeToLayer(int index) {
        this.mTransitionState = 0;
        Arrays.fill(this.mIsLayerOn, false);
        this.mIsLayerOn[index] = true;
        invalidateSelf();
    }

    public void fadeUpToLayer(int index) {
        this.mTransitionState = 0;
        Arrays.fill(this.mIsLayerOn, 0, index + 1, true);
        Arrays.fill(this.mIsLayerOn, index + 1, this.mLayers.length, false);
        invalidateSelf();
    }

    public void showLayerImmediately(int index) {
        this.mIsLayerOn[index] = true;
        this.mAlphas[index] = 255;
        invalidateSelf();
    }

    public void hideLayerImmediately(int index) {
        this.mIsLayerOn[index] = false;
        this.mAlphas[index] = 0;
        invalidateSelf();
    }

    public void finishTransitionImmediately() {
        this.mTransitionState = 2;
        for (int i = 0; i < this.mLayers.length; i++) {
            this.mAlphas[i] = this.mIsLayerOn[i] ? 255 : 0;
        }
        invalidateSelf();
    }

    private boolean updateAlphas(float ratio) {
        boolean done = true;
        for (int i = 0; i < this.mLayers.length; i++) {
            boolean[] zArr = this.mIsLayerOn;
            int dir = zArr[i] ? 1 : -1;
            int[] iArr = this.mAlphas;
            iArr[i] = (int) (((float) this.mStartAlphas[i]) + (((float) (dir * 255)) * ratio));
            if (iArr[i] < 0) {
                iArr[i] = 0;
            }
            if (iArr[i] > 255) {
                iArr[i] = 255;
            }
            if (zArr[i] && iArr[i] < 255) {
                done = false;
            }
            if (!zArr[i] && iArr[i] > 0) {
                done = false;
            }
        }
        return done;
    }

    public void draw(Canvas canvas) {
        boolean done = true;
        int i = this.mTransitionState;
        boolean z = false;
        int i2 = 2;
        if (i == 0) {
            System.arraycopy(this.mAlphas, 0, this.mStartAlphas, 0, this.mLayers.length);
            this.mStartTimeMs = getCurrentTimeMs();
            done = updateAlphas(this.mDurationMs == 0 ? 1.0f : 0.0f);
            if (!done) {
                i2 = 1;
            }
            this.mTransitionState = i2;
        } else if (i == 1) {
            if (this.mDurationMs > 0) {
                z = true;
            }
            Preconditions.checkState(z);
            done = updateAlphas(((float) (getCurrentTimeMs() - this.mStartTimeMs)) / ((float) this.mDurationMs));
            if (!done) {
                i2 = 1;
            }
            this.mTransitionState = i2;
        } else if (i == 2) {
            done = true;
        }
        int i3 = 0;
        while (true) {
            Drawable[] drawableArr = this.mLayers;
            if (i3 >= drawableArr.length) {
                break;
            }
            drawDrawableWithAlpha(canvas, drawableArr[i3], (this.mAlphas[i3] * this.mAlpha) / 255);
            i3++;
        }
        if (!done) {
            invalidateSelf();
        }
    }

    private void drawDrawableWithAlpha(Canvas canvas, Drawable drawable, int alpha) {
        if (drawable != null && alpha > 0) {
            this.mPreventInvalidateCount++;
            drawable.mutate().setAlpha(alpha);
            this.mPreventInvalidateCount--;
            drawable.draw(canvas);
        }
    }

    public void setAlpha(int alpha) {
        if (this.mAlpha != alpha) {
            this.mAlpha = alpha;
            invalidateSelf();
        }
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    /* access modifiers changed from: protected */
    public long getCurrentTimeMs() {
        return SystemClock.uptimeMillis();
    }

    public int getTransitionState() {
        return this.mTransitionState;
    }

    public boolean isLayerOn(int index) {
        return this.mIsLayerOn[index];
    }

    public boolean isDefaultLayerIsOn() {
        return this.mDefaultLayerIsOn;
    }
}
