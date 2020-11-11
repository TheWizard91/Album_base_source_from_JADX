package com.alexvasilkov.gestures.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import com.alexvasilkov.gestures.GestureController;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.State;
import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.utils.GravityUtils;
import com.alexvasilkov.gestures.views.interfaces.AnimatorView;
import com.alexvasilkov.gestures.views.interfaces.GestureView;
import java.lang.reflect.Field;
import java.util.Locale;

public class DebugOverlay {
    private static final float STROKE_WIDTH = 2.0f;
    private static final float TEXT_SIZE = 16.0f;
    private static final Matrix matrix = new Matrix();
    private static final Paint paint = new Paint();
    private static final Rect rect = new Rect();
    private static final RectF rectF = new RectF();
    private static Field stateSourceField;

    private DebugOverlay() {
    }

    public static void drawDebug(View view, Canvas canvas) {
        GestureController controller = ((GestureView) view).getController();
        ViewPositionAnimator animator = ((AnimatorView) view).getPositionAnimator();
        Settings settings = controller.getSettings();
        Context context = view.getContext();
        float stroke = UnitsUtils.toPixels(context, 2.0f);
        float textSize = UnitsUtils.toPixels(context, TEXT_SIZE);
        canvas.save();
        canvas.translate((float) view.getPaddingLeft(), (float) view.getPaddingTop());
        RectF rectF2 = rectF;
        rectF2.set(0.0f, 0.0f, (float) settings.getViewportW(), (float) settings.getViewportH());
        drawRect(canvas, rectF2, -7829368, stroke);
        Rect rect2 = rect;
        GravityUtils.getMovementAreaPosition(settings, rect2);
        rectF2.set(rect2);
        drawRect(canvas, rectF2, -16711936, stroke);
        State state = controller.getState();
        Matrix matrix2 = matrix;
        state.get(matrix2);
        canvas.save();
        canvas.concat(matrix2);
        rectF2.set(0.0f, 0.0f, (float) settings.getImageW(), (float) settings.getImageH());
        drawRect(canvas, rectF2, InputDeviceCompat.SOURCE_ANY, stroke / controller.getState().getZoom());
        canvas.restore();
        rectF2.set(0.0f, 0.0f, (float) settings.getImageW(), (float) settings.getImageH());
        controller.getState().get(matrix2);
        matrix2.mapRect(rectF2);
        drawRect(canvas, rectF2, SupportMenu.CATEGORY_MASK, stroke);
        float pos = animator.getPosition();
        if (pos == 1.0f || (pos == 0.0f && animator.isLeaving())) {
            GestureController.StateSource source = getStateSource(controller);
            drawText(canvas, settings, source.name(), -16711681, textSize);
            if (source != GestureController.StateSource.NONE) {
                view.invalidate();
            }
        } else if (pos > 0.0f) {
            drawText(canvas, settings, String.format(Locale.US, "%s %.0f%%", new Object[]{animator.isLeaving() ? "EXIT" : "ENTER", Float.valueOf(100.0f * pos)}), -65281, textSize);
        }
        canvas.restore();
    }

    private static void drawRect(Canvas canvas, RectF rect2, int color, float stroke) {
        Paint paint2 = paint;
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(stroke);
        rectF.inset(stroke * 0.5f, 0.5f * stroke);
        paint2.setColor(color);
        canvas.drawRect(rect2, paint2);
    }

    private static void drawText(Canvas canvas, Settings settings, String text, int color, float textSize) {
        Paint paint2 = paint;
        paint2.setTextSize(textSize);
        paint2.setTypeface(Typeface.MONOSPACE);
        paint2.setTextAlign(Paint.Align.CENTER);
        float halfSize = 0.5f * textSize;
        int length = text.length();
        Rect rect2 = rect;
        paint2.getTextBounds(text, 0, length, rect2);
        RectF rectF2 = rectF;
        rectF2.set(rect2);
        rectF2.offset(-rectF2.centerX(), -rectF2.centerY());
        GravityUtils.getMovementAreaPosition(settings, rect2);
        rectF2.offset((float) rect2.centerX(), (float) rect2.centerY());
        rectF2.inset(-halfSize, -halfSize);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(-1);
        canvas.drawRoundRect(rectF2, halfSize, halfSize, paint2);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(-7829368);
        canvas.drawRoundRect(rectF2, halfSize, halfSize, paint2);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(color);
        canvas.drawText(text, rectF2.centerX(), rectF2.bottom - halfSize, paint2);
    }

    private static GestureController.StateSource getStateSource(GestureController controller) {
        if (stateSourceField == null) {
            try {
                Field declaredField = GestureController.class.getDeclaredField("stateSource");
                stateSourceField = declaredField;
                declaredField.setAccessible(true);
            } catch (Exception e) {
            }
        }
        Field field = stateSourceField;
        if (field != null) {
            try {
                return (GestureController.StateSource) field.get(controller);
            } catch (Exception e2) {
            }
        }
        return GestureController.StateSource.NONE;
    }
}
