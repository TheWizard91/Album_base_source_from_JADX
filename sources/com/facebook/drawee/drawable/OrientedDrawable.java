package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class OrientedDrawable extends ForwardingDrawable {
    private int mExifOrientation;
    private int mRotationAngle;
    final Matrix mRotationMatrix;
    private final Matrix mTempMatrix;
    private final RectF mTempRectF;

    public OrientedDrawable(Drawable drawable, int rotationAngle) {
        this(drawable, rotationAngle, 0);
    }

    public OrientedDrawable(Drawable drawable, int rotationAngle, int exifOrientation) {
        super(drawable);
        this.mTempMatrix = new Matrix();
        this.mTempRectF = new RectF();
        this.mRotationMatrix = new Matrix();
        this.mRotationAngle = rotationAngle - (rotationAngle % 90);
        this.mExifOrientation = (exifOrientation < 0 || exifOrientation > 8) ? 0 : exifOrientation;
    }

    public void draw(Canvas canvas) {
        int i;
        if (this.mRotationAngle > 0 || !((i = this.mExifOrientation) == 0 || i == 1)) {
            int saveCount = canvas.save();
            canvas.concat(this.mRotationMatrix);
            super.draw(canvas);
            canvas.restoreToCount(saveCount);
            return;
        }
        super.draw(canvas);
    }

    public int getIntrinsicWidth() {
        int i = this.mExifOrientation;
        if (i == 5 || i == 7 || this.mRotationAngle % 180 != 0) {
            return super.getIntrinsicHeight();
        }
        return super.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() {
        int i = this.mExifOrientation;
        if (i == 5 || i == 7 || this.mRotationAngle % 180 != 0) {
            return super.getIntrinsicWidth();
        }
        return super.getIntrinsicHeight();
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        int i;
        Drawable underlyingDrawable = getCurrent();
        int i2 = this.mRotationAngle;
        if (i2 > 0 || !((i = this.mExifOrientation) == 0 || i == 1)) {
            int i3 = this.mExifOrientation;
            if (i3 == 2) {
                this.mRotationMatrix.setScale(-1.0f, 1.0f);
            } else if (i3 == 7) {
                this.mRotationMatrix.setRotate(270.0f, (float) bounds.centerX(), (float) bounds.centerY());
                this.mRotationMatrix.postScale(-1.0f, 1.0f);
            } else if (i3 == 4) {
                this.mRotationMatrix.setScale(1.0f, -1.0f);
            } else if (i3 != 5) {
                this.mRotationMatrix.setRotate((float) i2, (float) bounds.centerX(), (float) bounds.centerY());
            } else {
                this.mRotationMatrix.setRotate(270.0f, (float) bounds.centerX(), (float) bounds.centerY());
                this.mRotationMatrix.postScale(1.0f, -1.0f);
            }
            this.mTempMatrix.reset();
            this.mRotationMatrix.invert(this.mTempMatrix);
            this.mTempRectF.set(bounds);
            this.mTempMatrix.mapRect(this.mTempRectF);
            underlyingDrawable.setBounds((int) this.mTempRectF.left, (int) this.mTempRectF.top, (int) this.mTempRectF.right, (int) this.mTempRectF.bottom);
            return;
        }
        underlyingDrawable.setBounds(bounds);
    }

    public void getTransform(Matrix transform) {
        getParentTransform(transform);
        if (!this.mRotationMatrix.isIdentity()) {
            transform.preConcat(this.mRotationMatrix);
        }
    }
}
