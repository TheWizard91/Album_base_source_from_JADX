package com.rey.material.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;

public class ContactChipDrawable extends Drawable {
    private int mBackgroundColor;
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private BoringLayout mBoringLayout;
    private CharSequence mContactName;
    private Matrix mMatrix;
    private BoringLayout.Metrics mMetrics = new BoringLayout.Metrics();
    private int mPaddingLeft;
    private int mPaddingRight;
    private Paint mPaint;
    private RectF mRect;
    private TextPaint mTextPaint = new TextPaint(this.mPaint);

    public ContactChipDrawable(int paddingLeft, int paddingRight, Typeface typeface, int textColor, int textSize, int backgroundColor) {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(textColor);
        this.mPaint.setTypeface(typeface);
        this.mPaint.setTextSize((float) textSize);
        Paint.FontMetricsInt temp = this.mTextPaint.getFontMetricsInt();
        this.mMetrics.ascent = temp.ascent;
        this.mMetrics.bottom = temp.bottom;
        this.mMetrics.descent = temp.descent;
        this.mMetrics.top = temp.top;
        this.mMetrics.leading = temp.leading;
        this.mRect = new RectF();
        this.mMatrix = new Matrix();
        this.mPaddingLeft = paddingLeft;
        this.mPaddingRight = paddingRight;
        this.mBackgroundColor = backgroundColor;
    }

    public void setContactName(CharSequence name) {
        this.mContactName = name;
        updateLayout();
        invalidateSelf();
    }

    public void setImage(Bitmap bm) {
        if (this.mBitmap != bm) {
            this.mBitmap = bm;
            if (bm != null) {
                this.mBitmapShader = new BitmapShader(this.mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                updateMatrix();
            }
            invalidateSelf();
        }
    }

    private void updateLayout() {
        if (this.mContactName != null) {
            Rect bounds = getBounds();
            if (bounds.width() != 0 && bounds.height() != 0) {
                int outerWidth = Math.max(0, ((bounds.width() - bounds.height()) - this.mPaddingLeft) - this.mPaddingRight);
                BoringLayout.Metrics metrics = this.mMetrics;
                TextPaint textPaint = this.mTextPaint;
                CharSequence charSequence = this.mContactName;
                metrics.width = Math.round(textPaint.measureText(charSequence, 0, charSequence.length()) + 0.5f);
                BoringLayout boringLayout = this.mBoringLayout;
                if (boringLayout == null) {
                    this.mBoringLayout = BoringLayout.make(this.mContactName, this.mTextPaint, outerWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 1.0f, this.mMetrics, true, TextUtils.TruncateAt.END, outerWidth);
                    return;
                }
                this.mBoringLayout = boringLayout.replaceOrMake(this.mContactName, this.mTextPaint, outerWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 1.0f, this.mMetrics, true, TextUtils.TruncateAt.END, outerWidth);
            }
        }
    }

    private void updateMatrix() {
        if (this.mBitmap != null) {
            Rect bounds = getBounds();
            if (bounds.width() != 0 && bounds.height() != 0) {
                this.mMatrix.reset();
                float scale = ((float) bounds.height()) / ((float) Math.min(this.mBitmap.getWidth(), this.mBitmap.getHeight()));
                this.mMatrix.setScale(scale, scale, 0.0f, 0.0f);
                this.mMatrix.postTranslate((((float) bounds.height()) - (((float) this.mBitmap.getWidth()) * scale)) / 2.0f, (((float) bounds.height()) - (((float) this.mBitmap.getHeight()) * scale)) / 2.0f);
                this.mBitmapShader.setLocalMatrix(this.mMatrix);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        updateLayout();
        updateMatrix();
    }

    public void draw(Canvas canvas) {
        int saveCount = canvas.save();
        Rect bounds = getBounds();
        float halfHeight = ((float) bounds.height()) / 2.0f;
        this.mPaint.setShader((Shader) null);
        this.mPaint.setColor(this.mBackgroundColor);
        this.mRect.set(1.0f, 0.0f, (float) (bounds.height() + 1), (float) bounds.height());
        canvas.drawArc(this.mRect, 90.0f, 180.0f, true, this.mPaint);
        this.mRect.set((float) (bounds.width() - bounds.height()), 0.0f, (float) bounds.width(), (float) bounds.height());
        canvas.drawArc(this.mRect, 270.0f, 180.0f, true, this.mPaint);
        this.mRect.set(halfHeight, 0.0f, ((float) bounds.width()) - halfHeight, (float) bounds.height());
        canvas.drawRect(this.mRect, this.mPaint);
        if (this.mBitmap != null) {
            this.mPaint.setShader(this.mBitmapShader);
            canvas.drawCircle(halfHeight, halfHeight, halfHeight, this.mPaint);
        }
        if (!(this.mContactName == null || this.mBoringLayout == null)) {
            canvas.translate((float) (bounds.height() + this.mPaddingLeft), ((float) (bounds.height() - this.mBoringLayout.getHeight())) / 2.0f);
            this.mBoringLayout.draw(canvas);
        }
        canvas.restoreToCount(saveCount);
    }

    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.mPaint.setColorFilter(cf);
    }

    public int getOpacity() {
        return -3;
    }
}
