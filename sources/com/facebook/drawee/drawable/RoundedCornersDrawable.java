package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.Preconditions;
import java.util.Arrays;
import javax.annotation.Nullable;

public class RoundedCornersDrawable extends ForwardingDrawable implements Rounded {
    private int mBorderColor = 0;
    private final Path mBorderPath = new Path();
    final float[] mBorderRadii = new float[8];
    private float mBorderWidth = 0.0f;
    private final RectF mBounds = new RectF();
    @Nullable
    private RectF mInsideBorderBounds;
    @Nullable
    private Matrix mInsideBorderTransform;
    private boolean mIsCircle = false;
    private int mOverlayColor = 0;
    private float mPadding = 0.0f;
    final Paint mPaint = new Paint(1);
    private boolean mPaintFilterBitmap = false;
    private final Path mPath = new Path();
    private final float[] mRadii = new float[8];
    private boolean mScaleDownInsideBorders = false;
    private final RectF mTempRectangle = new RectF();
    Type mType = Type.OVERLAY_COLOR;

    public enum Type {
        OVERLAY_COLOR,
        CLIPPING
    }

    public RoundedCornersDrawable(Drawable drawable) {
        super((Drawable) Preconditions.checkNotNull(drawable));
    }

    public void setType(Type type) {
        this.mType = type;
        updatePath();
        invalidateSelf();
    }

    public void setCircle(boolean isCircle) {
        this.mIsCircle = isCircle;
        updatePath();
        invalidateSelf();
    }

    public boolean isCircle() {
        return this.mIsCircle;
    }

    public void setRadius(float radius) {
        Arrays.fill(this.mRadii, radius);
        updatePath();
        invalidateSelf();
    }

    public void setRadii(float[] radii) {
        if (radii == null) {
            Arrays.fill(this.mRadii, 0.0f);
        } else {
            Preconditions.checkArgument(radii.length == 8, "radii should have exactly 8 values");
            System.arraycopy(radii, 0, this.mRadii, 0, 8);
        }
        updatePath();
        invalidateSelf();
    }

    public float[] getRadii() {
        return this.mRadii;
    }

    public void setOverlayColor(int overlayColor) {
        this.mOverlayColor = overlayColor;
        invalidateSelf();
    }

    public int getOverlayColor() {
        return this.mOverlayColor;
    }

    public void setBorder(int color, float width) {
        this.mBorderColor = color;
        this.mBorderWidth = width;
        updatePath();
        invalidateSelf();
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public float getBorderWidth() {
        return this.mBorderWidth;
    }

    public void setPadding(float padding) {
        this.mPadding = padding;
        updatePath();
        invalidateSelf();
    }

    public float getPadding() {
        return this.mPadding;
    }

    public void setScaleDownInsideBorders(boolean scaleDownInsideBorders) {
        this.mScaleDownInsideBorders = scaleDownInsideBorders;
        updatePath();
        invalidateSelf();
    }

    public boolean getScaleDownInsideBorders() {
        return this.mScaleDownInsideBorders;
    }

    public void setPaintFilterBitmap(boolean paintFilterBitmap) {
        if (this.mPaintFilterBitmap != paintFilterBitmap) {
            this.mPaintFilterBitmap = paintFilterBitmap;
            invalidateSelf();
        }
    }

    public boolean getPaintFilterBitmap() {
        return this.mPaintFilterBitmap;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        updatePath();
    }

    private void updatePath() {
        float[] fArr;
        this.mPath.reset();
        this.mBorderPath.reset();
        this.mTempRectangle.set(getBounds());
        RectF rectF = this.mTempRectangle;
        float f = this.mPadding;
        rectF.inset(f, f);
        if (this.mType == Type.OVERLAY_COLOR) {
            this.mPath.addRect(this.mTempRectangle, Path.Direction.CW);
        }
        if (this.mIsCircle) {
            this.mPath.addCircle(this.mTempRectangle.centerX(), this.mTempRectangle.centerY(), Math.min(this.mTempRectangle.width(), this.mTempRectangle.height()) / 2.0f, Path.Direction.CW);
        } else {
            this.mPath.addRoundRect(this.mTempRectangle, this.mRadii, Path.Direction.CW);
        }
        RectF rectF2 = this.mTempRectangle;
        float f2 = this.mPadding;
        rectF2.inset(-f2, -f2);
        RectF rectF3 = this.mTempRectangle;
        float f3 = this.mBorderWidth;
        rectF3.inset(f3 / 2.0f, f3 / 2.0f);
        if (this.mIsCircle) {
            this.mBorderPath.addCircle(this.mTempRectangle.centerX(), this.mTempRectangle.centerY(), Math.min(this.mTempRectangle.width(), this.mTempRectangle.height()) / 2.0f, Path.Direction.CW);
        } else {
            int i = 0;
            while (true) {
                fArr = this.mBorderRadii;
                if (i >= fArr.length) {
                    break;
                }
                fArr[i] = (this.mRadii[i] + this.mPadding) - (this.mBorderWidth / 2.0f);
                i++;
            }
            this.mBorderPath.addRoundRect(this.mTempRectangle, fArr, Path.Direction.CW);
        }
        RectF rectF4 = this.mTempRectangle;
        float f4 = this.mBorderWidth;
        rectF4.inset((-f4) / 2.0f, (-f4) / 2.0f);
    }

    /* renamed from: com.facebook.drawee.drawable.RoundedCornersDrawable$1 */
    static /* synthetic */ class C06961 {

        /* renamed from: $SwitchMap$com$facebook$drawee$drawable$RoundedCornersDrawable$Type */
        static final /* synthetic */ int[] f80xda0cffae;

        static {
            int[] iArr = new int[Type.values().length];
            f80xda0cffae = iArr;
            try {
                iArr[Type.CLIPPING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f80xda0cffae[Type.OVERLAY_COLOR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public void draw(Canvas canvas) {
        this.mBounds.set(getBounds());
        int i = C06961.f80xda0cffae[this.mType.ordinal()];
        if (i == 1) {
            int saveCount = canvas.save();
            canvas.clipPath(this.mPath);
            super.draw(canvas);
            canvas.restoreToCount(saveCount);
        } else if (i == 2) {
            if (this.mScaleDownInsideBorders) {
                RectF rectF = this.mInsideBorderBounds;
                if (rectF == null) {
                    this.mInsideBorderBounds = new RectF(this.mBounds);
                    this.mInsideBorderTransform = new Matrix();
                } else {
                    rectF.set(this.mBounds);
                }
                RectF rectF2 = this.mInsideBorderBounds;
                float f = this.mBorderWidth;
                rectF2.inset(f, f);
                this.mInsideBorderTransform.setRectToRect(this.mBounds, this.mInsideBorderBounds, Matrix.ScaleToFit.FILL);
                int saveCount2 = canvas.save();
                canvas.clipRect(this.mBounds);
                canvas.concat(this.mInsideBorderTransform);
                super.draw(canvas);
                canvas.restoreToCount(saveCount2);
            } else {
                super.draw(canvas);
            }
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setColor(this.mOverlayColor);
            this.mPaint.setStrokeWidth(0.0f);
            this.mPaint.setFilterBitmap(getPaintFilterBitmap());
            this.mPath.setFillType(Path.FillType.EVEN_ODD);
            canvas.drawPath(this.mPath, this.mPaint);
            if (this.mIsCircle) {
                float paddingH = ((this.mBounds.width() - this.mBounds.height()) + this.mBorderWidth) / 2.0f;
                float paddingV = ((this.mBounds.height() - this.mBounds.width()) + this.mBorderWidth) / 2.0f;
                if (paddingH > 0.0f) {
                    Canvas canvas2 = canvas;
                    canvas2.drawRect(this.mBounds.left, this.mBounds.top, this.mBounds.left + paddingH, this.mBounds.bottom, this.mPaint);
                    canvas2.drawRect(this.mBounds.right - paddingH, this.mBounds.top, this.mBounds.right, this.mBounds.bottom, this.mPaint);
                }
                if (paddingV > 0.0f) {
                    Canvas canvas3 = canvas;
                    canvas3.drawRect(this.mBounds.left, this.mBounds.top, this.mBounds.right, this.mBounds.top + paddingV, this.mPaint);
                    canvas3.drawRect(this.mBounds.left, this.mBounds.bottom - paddingV, this.mBounds.right, this.mBounds.bottom, this.mPaint);
                }
            }
        }
        if (this.mBorderColor != 0) {
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setColor(this.mBorderColor);
            this.mPaint.setStrokeWidth(this.mBorderWidth);
            this.mPath.setFillType(Path.FillType.EVEN_ODD);
            canvas.drawPath(this.mBorderPath, this.mPaint);
        }
    }
}
