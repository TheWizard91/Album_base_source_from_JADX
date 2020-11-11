package com.rey.material.text.style;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ReplacementSpan;

public class ContactChipSpan extends ReplacementSpan {
    private int mBackgroundColor;
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private BoringLayout mBoringLayout;
    private CharSequence mContactName;
    private int mHeight;
    private Matrix mMatrix = new Matrix();
    private int mPaddingLeft;
    private int mPaddingRight;
    private Paint mPaint;
    private RectF mRect = new RectF();
    private TextPaint mTextPaint = new TextPaint(this.mPaint);
    private int mWidth;

    public ContactChipSpan(CharSequence name, int height, int maxWidth, int paddingLeft, int paddingRight, Typeface typeface, int textColor, int textSize, int backgroundColor) {
        CharSequence charSequence = name;
        int i = height;
        int i2 = paddingLeft;
        int i3 = paddingRight;
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(textColor);
        this.mPaint.setTypeface(typeface);
        this.mPaint.setTextSize((float) textSize);
        this.mContactName = charSequence;
        this.mPaddingLeft = i2;
        this.mPaddingRight = i3;
        this.mBackgroundColor = backgroundColor;
        this.mHeight = i;
        int round = Math.round(Math.min((float) maxWidth, this.mPaint.measureText(charSequence, 0, name.length()) + ((float) i2) + ((float) i3) + ((float) i)));
        this.mWidth = round;
        int outerWidth = Math.max(0, ((round - this.mPaddingLeft) - this.mPaddingRight) - this.mHeight);
        Paint.FontMetricsInt temp = this.mTextPaint.getFontMetricsInt();
        BoringLayout.Metrics mMetrics = new BoringLayout.Metrics();
        TextPaint textPaint = this.mTextPaint;
        CharSequence charSequence2 = this.mContactName;
        mMetrics.width = Math.round(textPaint.measureText(charSequence2, 0, charSequence2.length()) + 0.5f);
        mMetrics.ascent = temp.ascent;
        mMetrics.bottom = temp.bottom;
        mMetrics.descent = temp.descent;
        mMetrics.top = temp.top;
        mMetrics.leading = temp.leading;
        this.mBoringLayout = BoringLayout.make(this.mContactName, this.mTextPaint, outerWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 1.0f, mMetrics, true, TextUtils.TruncateAt.END, outerWidth);
    }

    public void setImage(Bitmap bm) {
        if (this.mBitmap != bm) {
            this.mBitmap = bm;
            if (bm != null) {
                this.mBitmapShader = new BitmapShader(this.mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                this.mMatrix.reset();
                float scale = ((float) this.mHeight) / ((float) Math.min(this.mBitmap.getWidth(), this.mBitmap.getHeight()));
                this.mMatrix.setScale(scale, scale, 0.0f, 0.0f);
                this.mMatrix.postTranslate((((float) this.mHeight) - (((float) this.mBitmap.getWidth()) * scale)) / 2.0f, (((float) this.mHeight) - (((float) this.mBitmap.getHeight()) * scale)) / 2.0f);
                this.mBitmapShader.setLocalMatrix(this.mMatrix);
            }
        }
    }

    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        if (fm != null) {
            int cy = (fm.ascent + fm.descent) / 2;
            fm.ascent = Math.min(fm.ascent, cy - (this.mHeight / 2));
            fm.descent = Math.max(fm.descent, (this.mHeight / 2) + cy);
            fm.top = Math.min(fm.top, fm.ascent);
            fm.bottom = Math.max(fm.bottom, fm.descent);
        }
        return this.mWidth;
    }

    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        BoringLayout boringLayout;
        Canvas canvas2 = canvas;
        canvas.save();
        canvas.translate(x, (float) top);
        float halfHeight = ((float) this.mHeight) / 2.0f;
        this.mPaint.setShader((Shader) null);
        this.mPaint.setColor(this.mBackgroundColor);
        RectF rectF = this.mRect;
        int i = this.mHeight;
        rectF.set(1.0f, 0.0f, (float) (i + 1), (float) i);
        canvas.drawArc(this.mRect, 90.0f, 180.0f, true, this.mPaint);
        RectF rectF2 = this.mRect;
        int i2 = this.mWidth;
        int i3 = this.mHeight;
        rectF2.set((float) (i2 - i3), 0.0f, (float) i2, (float) i3);
        canvas.drawArc(this.mRect, 270.0f, 180.0f, true, this.mPaint);
        this.mRect.set(halfHeight, 0.0f, ((float) this.mWidth) - halfHeight, (float) this.mHeight);
        canvas.drawRect(this.mRect, this.mPaint);
        if (this.mBitmap != null) {
            this.mPaint.setShader(this.mBitmapShader);
            canvas.drawCircle(halfHeight, halfHeight, halfHeight, this.mPaint);
        }
        if (!(this.mContactName == null || (boringLayout = this.mBoringLayout) == null)) {
            int i4 = this.mHeight;
            canvas.translate((float) (this.mPaddingLeft + i4), ((float) (i4 - boringLayout.getHeight())) / 2.0f);
            this.mBoringLayout.draw(canvas);
        }
        canvas.restore();
    }
}
