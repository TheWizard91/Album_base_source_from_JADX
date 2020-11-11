package p023me.zhanghai.android.materialprogressbar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/* renamed from: me.zhanghai.android.materialprogressbar.BaseSingleCircularProgressDrawable */
abstract class BaseSingleCircularProgressDrawable extends BaseProgressDrawable {
    private static final RectF RECT_BOUND = new RectF(-21.0f, -21.0f, 21.0f, 21.0f);
    private static final RectF RECT_PADDED_BOUND = new RectF(-24.0f, -24.0f, 24.0f, 24.0f);
    private static final RectF RECT_PROGRESS = new RectF(-19.0f, -19.0f, 19.0f, 19.0f);

    /* access modifiers changed from: protected */
    public abstract void onDrawRing(Canvas canvas, Paint paint);

    BaseSingleCircularProgressDrawable() {
    }

    /* access modifiers changed from: protected */
    public void onPreparePaint(Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4.0f);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas, int width, int height, Paint paint) {
        if (this.mUseIntrinsicPadding) {
            RectF rectF = RECT_PADDED_BOUND;
            canvas.scale(((float) width) / rectF.width(), ((float) height) / rectF.height());
            canvas.translate(rectF.width() / 2.0f, rectF.height() / 2.0f);
        } else {
            RectF rectF2 = RECT_BOUND;
            canvas.scale(((float) width) / rectF2.width(), ((float) height) / rectF2.height());
            canvas.translate(rectF2.width() / 2.0f, rectF2.height() / 2.0f);
        }
        onDrawRing(canvas, paint);
    }

    /* access modifiers changed from: protected */
    public void drawRing(Canvas canvas, Paint paint, float startAngle, float sweepAngle) {
        canvas.drawArc(RECT_PROGRESS, startAngle - 0.049804688f, sweepAngle, false, paint);
    }
}
