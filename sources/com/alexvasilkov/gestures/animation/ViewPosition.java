package com.alexvasilkov.gestures.animation;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import java.util.regex.Pattern;

public class ViewPosition {
    private static final String DELIMITER = "#";
    private static final Pattern SPLIT_PATTERN = Pattern.compile(DELIMITER);
    private static final RectF tmpDst = new RectF();
    private static final int[] tmpLocation = new int[2];
    private static final Matrix tmpMatrix = new Matrix();
    private static final RectF tmpSrc = new RectF();
    private static final Rect tmpViewRect = new Rect();
    public final Rect image;
    public final Rect view;
    public final Rect viewport;
    public final Rect visible;

    private ViewPosition() {
        this.view = new Rect();
        this.viewport = new Rect();
        this.visible = new Rect();
        this.image = new Rect();
    }

    private ViewPosition(Rect view2, Rect viewport2, Rect visible2, Rect image2) {
        this.view = view2;
        this.viewport = viewport2;
        this.visible = visible2;
        this.image = image2;
    }

    public void set(ViewPosition pos) {
        this.view.set(pos.view);
        this.viewport.set(pos.viewport);
        this.visible.set(pos.visible);
        this.image.set(pos.image);
    }

    private boolean init(View targetView) {
        View view2 = targetView;
        if (targetView.getWindowToken() == null) {
            return false;
        }
        Rect rect = tmpViewRect;
        rect.set(this.view);
        int[] iArr = tmpLocation;
        view2.getLocationOnScreen(iArr);
        this.view.set(0, 0, targetView.getWidth(), targetView.getHeight());
        this.view.offset(iArr[0], iArr[1]);
        this.viewport.set(targetView.getPaddingLeft(), targetView.getPaddingTop(), targetView.getWidth() - targetView.getPaddingRight(), targetView.getHeight() - targetView.getPaddingBottom());
        this.viewport.offset(iArr[0], iArr[1]);
        if (!view2.getGlobalVisibleRect(this.visible)) {
            this.visible.set(this.view.centerX(), this.view.centerY(), this.view.centerX() + 1, this.view.centerY() + 1);
        }
        if (view2 instanceof ImageView) {
            ImageView imageView = (ImageView) view2;
            Drawable drawable = imageView.getDrawable();
            if (drawable == null) {
                this.image.set(this.viewport);
            } else {
                int drawableWidth = drawable.getIntrinsicWidth();
                int drawableHeight = drawable.getIntrinsicHeight();
                ImageView.ScaleType scaleType = imageView.getScaleType();
                int width = this.viewport.width();
                int height = this.viewport.height();
                Matrix imageMatrix = imageView.getImageMatrix();
                Matrix matrix = tmpMatrix;
                ImageViewHelper.applyScaleType(scaleType, drawableWidth, drawableHeight, width, height, imageMatrix, matrix);
                RectF rectF = tmpSrc;
                rectF.set(0.0f, 0.0f, (float) drawableWidth, (float) drawableHeight);
                RectF rectF2 = tmpDst;
                matrix.mapRect(rectF2, rectF);
                this.image.left = this.viewport.left + ((int) rectF2.left);
                this.image.top = this.viewport.top + ((int) rectF2.top);
                this.image.right = this.viewport.left + ((int) rectF2.right);
                this.image.bottom = this.viewport.top + ((int) rectF2.bottom);
            }
        } else {
            this.image.set(this.viewport);
        }
        return !rect.equals(this.view);
    }

    public static ViewPosition newInstance() {
        return new ViewPosition();
    }

    public static ViewPosition from(View view2) {
        ViewPosition pos = new ViewPosition();
        pos.init(view2);
        return pos;
    }

    public static boolean apply(ViewPosition pos, View view2) {
        return pos.init(view2);
    }

    public static void apply(ViewPosition pos, Point point) {
        pos.view.set(point.x, point.y, point.x + 1, point.y + 1);
        pos.viewport.set(pos.view);
        pos.visible.set(pos.view);
        pos.image.set(pos.view);
    }

    public String pack() {
        return TextUtils.join(DELIMITER, new String[]{this.view.flattenToString(), this.viewport.flattenToString(), this.visible.flattenToString(), this.image.flattenToString()});
    }

    public static ViewPosition unpack(String str) {
        String[] parts = TextUtils.split(str, SPLIT_PATTERN);
        if (parts.length == 4) {
            Rect view2 = Rect.unflattenFromString(parts[0]);
            Rect viewport2 = Rect.unflattenFromString(parts[1]);
            Rect visible2 = Rect.unflattenFromString(parts[2]);
            Rect image2 = Rect.unflattenFromString(parts[3]);
            if (view2 != null && viewport2 != null && image2 != null) {
                return new ViewPosition(view2, viewport2, visible2, image2);
            }
            throw new IllegalArgumentException("Wrong ViewPosition string: " + str);
        }
        throw new IllegalArgumentException("Wrong ViewPosition string: " + str);
    }
}
