package com.rey.material.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.rey.material.util.ColorUtil;

public class RevealDrawable extends Drawable implements Animatable {
    private static final float GRADIENT_RADIUS = 16.0f;
    private static final float[] GRADIENT_STOPS = {0.0f, 0.99f, 1.0f};
    private float mAnimProgress;
    private int mCurColor;
    private boolean mCurColorTransparent;
    private int mCurTask;
    private Paint mFillPaint;
    private Matrix mMatrix;
    private float mMaxRadius;
    private boolean mNextColorTransparent;
    private RectF mRect;
    private boolean mRunning = false;
    private RadialGradient mShader;
    private Paint mShaderPaint;
    private long mStartTime;
    private ColorChangeTask[] mTasks;
    private final Runnable mUpdater = new Runnable() {
        public void run() {
            RevealDrawable.this.update();
        }
    };

    public RevealDrawable(int color) {
        Paint paint = new Paint(1);
        this.mShaderPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.mFillPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        this.mCurColor = color;
        this.mRect = new RectF();
        this.mMatrix = new Matrix();
    }

    public int getCurColor() {
        return this.mCurColor;
    }

    public void setCurColor(int color) {
        if (this.mCurColor != color) {
            this.mCurColor = color;
            this.mCurColorTransparent = Color.alpha(color) == 0;
            invalidateSelf();
        }
    }

    private float getMaxRadius(float x, float y, Rect bounds) {
        return (float) Math.sqrt(Math.pow((double) (((float) (x < ((float) bounds.centerX()) ? bounds.right : bounds.left)) - x), 2.0d) + Math.pow((double) (((float) (y < ((float) bounds.centerY()) ? bounds.bottom : bounds.top)) - y), 2.0d));
    }

    private RadialGradient getShader(ColorChangeTask task) {
        if (this.mShader == null) {
            if (task.isOut) {
                int color_middle = ColorUtil.getColor(this.mCurColor, 0.0f);
                this.mShader = new RadialGradient(task.f180x, task.f181y, GRADIENT_RADIUS, new int[]{0, color_middle, this.mCurColor}, GRADIENT_STOPS, Shader.TileMode.CLAMP);
            } else {
                int color_middle2 = ColorUtil.getColor(task.color, 0.0f);
                this.mShader = new RadialGradient(task.f180x, task.f181y, GRADIENT_RADIUS, new int[]{0, color_middle2, task.color}, GRADIENT_STOPS, Shader.TileMode.CLAMP);
            }
        }
        return this.mShader;
    }

    private void fillCanvas(Canvas canvas, int color, boolean transparent) {
        if (!transparent) {
            this.mFillPaint.setColor(color);
            canvas.drawRect(getBounds(), this.mFillPaint);
        }
    }

    private void fillCanvasWithHole(Canvas canvas, ColorChangeTask task, float radius, boolean transparent) {
        if (!transparent) {
            float scale = radius / GRADIENT_RADIUS;
            this.mMatrix.reset();
            this.mMatrix.postScale(scale, scale, task.f180x, task.f181y);
            RadialGradient shader = getShader(task);
            shader.setLocalMatrix(this.mMatrix);
            this.mShaderPaint.setShader(shader);
            canvas.drawRect(getBounds(), this.mShaderPaint);
        }
    }

    private void fillCircle(Canvas canvas, float x, float y, float radius, int color, boolean transparent) {
        if (!transparent) {
            this.mFillPaint.setColor(color);
            this.mRect.set(x - radius, y - radius, x + radius, y + radius);
            canvas.drawOval(this.mRect, this.mFillPaint);
        }
    }

    public void draw(Canvas canvas) {
        if (!isRunning()) {
            fillCanvas(canvas, this.mCurColor, this.mCurColorTransparent);
            return;
        }
        ColorChangeTask task = this.mTasks[this.mCurTask];
        float f = this.mAnimProgress;
        if (f == 0.0f) {
            fillCanvas(canvas, this.mCurColor, this.mCurColorTransparent);
        } else if (f == 1.0f) {
            fillCanvas(canvas, task.color, this.mNextColorTransparent);
        } else if (task.isOut) {
            float radius = this.mMaxRadius * task.interpolator.getInterpolation(this.mAnimProgress);
            if (Color.alpha(task.color) == 255) {
                fillCanvas(canvas, this.mCurColor, this.mCurColorTransparent);
            } else {
                fillCanvasWithHole(canvas, task, radius, this.mCurColorTransparent);
            }
            fillCircle(canvas, task.f180x, task.f181y, radius, task.color, this.mNextColorTransparent);
        } else {
            float radius2 = this.mMaxRadius * task.interpolator.getInterpolation(this.mAnimProgress);
            if (Color.alpha(this.mCurColor) == 255) {
                fillCanvas(canvas, task.color, this.mNextColorTransparent);
            } else {
                fillCanvasWithHole(canvas, task, radius2, this.mNextColorTransparent);
            }
            fillCircle(canvas, task.f180x, task.f181y, radius2, this.mCurColor, this.mCurColorTransparent);
        }
    }

    public void changeColor(int color, int duration, Interpolator interpolator, float x, float y, boolean out) {
        changeColor(new ColorChangeTask(color, duration, interpolator, x, y, out));
    }

    public void changeColor(ColorChangeTask... tasks) {
        synchronized (RevealDrawable.class) {
            if (!isRunning()) {
                int i = 0;
                while (true) {
                    if (i >= tasks.length) {
                        break;
                    } else if (tasks[i].color != this.mCurColor) {
                        this.mCurTask = i;
                        this.mTasks = tasks;
                        start();
                        break;
                    } else {
                        i++;
                    }
                }
            } else {
                ColorChangeTask[] colorChangeTaskArr = this.mTasks;
                int length = colorChangeTaskArr.length;
                int i2 = this.mCurTask;
                int curLength = length - i2;
                ColorChangeTask[] newTasks = new ColorChangeTask[(tasks.length + curLength)];
                System.arraycopy(colorChangeTaskArr, i2, newTasks, 0, curLength);
                System.arraycopy(tasks, 0, newTasks, curLength, tasks.length);
                this.mTasks = newTasks;
                this.mCurTask = 0;
            }
        }
    }

    public void setAlpha(int alpha) {
        this.mShaderPaint.setAlpha(alpha);
        this.mFillPaint.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.mShaderPaint.setColorFilter(cf);
        this.mFillPaint.setColorFilter(cf);
    }

    public int getOpacity() {
        return -3;
    }

    private void resetAnimation() {
        this.mStartTime = SystemClock.uptimeMillis();
        this.mAnimProgress = 0.0f;
        boolean z = true;
        this.mCurColorTransparent = Color.alpha(this.mCurColor) == 0;
        if (Color.alpha(this.mTasks[this.mCurTask].color) != 0) {
            z = false;
        }
        this.mNextColorTransparent = z;
        this.mMaxRadius = getMaxRadius(this.mTasks[this.mCurTask].f180x, this.mTasks[this.mCurTask].f181y, getBounds());
        this.mShader = null;
    }

    public void start() {
        if (!isRunning()) {
            resetAnimation();
            scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
            invalidateSelf();
        }
    }

    public void stop() {
        if (isRunning()) {
            this.mTasks = null;
            this.mRunning = false;
            unscheduleSelf(this.mUpdater);
            invalidateSelf();
        }
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    public void scheduleSelf(Runnable what, long when) {
        this.mRunning = true;
        super.scheduleSelf(what, when);
    }

    /* access modifiers changed from: private */
    public void update() {
        long curTime = SystemClock.uptimeMillis();
        synchronized (RevealDrawable.class) {
            float min = Math.min(1.0f, ((float) (curTime - this.mStartTime)) / ((float) this.mTasks[this.mCurTask].duration));
            this.mAnimProgress = min;
            if (min == 1.0f) {
                setCurColor(this.mTasks[this.mCurTask].color);
                this.mCurTask++;
                while (true) {
                    int i = this.mCurTask;
                    ColorChangeTask[] colorChangeTaskArr = this.mTasks;
                    if (i >= colorChangeTaskArr.length) {
                        break;
                    } else if (colorChangeTaskArr[i].color != this.mCurColor) {
                        resetAnimation();
                        break;
                    } else {
                        this.mCurTask++;
                    }
                }
                if (this.mCurTask == this.mTasks.length) {
                    stop();
                }
            }
        }
        invalidateSelf();
        if (isRunning()) {
            scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
        }
    }

    public static class ColorChangeTask {
        public final int color;
        public final int duration;
        public final Interpolator interpolator;
        public final boolean isOut;

        /* renamed from: x */
        public final float f180x;

        /* renamed from: y */
        public final float f181y;

        public ColorChangeTask(int color2, int duration2, Interpolator interpolator2, float x, float y, boolean out) {
            this.color = color2;
            this.duration = duration2;
            this.interpolator = interpolator2 == null ? new DecelerateInterpolator() : interpolator2;
            this.f180x = x;
            this.f181y = y;
            this.isOut = out;
        }
    }
}
