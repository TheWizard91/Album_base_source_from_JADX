package com.alexvasilkov.gestures;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import com.alexvasilkov.gestures.internal.MovementBounds;
import com.alexvasilkov.gestures.internal.ZoomBounds;
import com.alexvasilkov.gestures.utils.GravityUtils;
import com.alexvasilkov.gestures.utils.MathUtils;

public class StateController {
    private static final Point tmpPoint = new Point();
    private static final PointF tmpPointF = new PointF();
    private static final Rect tmpRect = new Rect();
    private static final RectF tmpRectF = new RectF();
    private static final State tmpState = new State();
    private boolean isResetRequired = true;
    private final MovementBounds movBounds;
    private final Settings settings;
    private final ZoomBounds zoomBounds;
    private float zoomPatch;

    StateController(Settings settings2) {
        this.settings = settings2;
        this.zoomBounds = new ZoomBounds(settings2);
        this.movBounds = new MovementBounds(settings2);
    }

    /* access modifiers changed from: package-private */
    public boolean resetState(State state) {
        this.isResetRequired = true;
        return updateState(state);
    }

    /* access modifiers changed from: package-private */
    public boolean updateState(State state) {
        boolean z = false;
        if (this.isResetRequired) {
            state.set(0.0f, 0.0f, this.zoomBounds.set(state).getFitZoom(), 0.0f);
            Settings settings2 = this.settings;
            Rect rect = tmpRect;
            GravityUtils.getImagePosition(state, settings2, rect);
            state.translateTo((float) rect.left, (float) rect.top);
            if (!this.settings.hasImageSize() || !this.settings.hasViewportSize()) {
                z = true;
            }
            this.isResetRequired = z;
            return !z;
        }
        restrictStateBounds(state, state, Float.NaN, Float.NaN, false, false, true);
        return false;
    }

    public void setTempZoomPatch(float factor) {
        this.zoomPatch = factor;
    }

    public void applyZoomPatch(State state) {
        if (this.zoomPatch > 0.0f) {
            state.set(state.getX(), state.getY(), state.getZoom() * this.zoomPatch, state.getRotation());
        }
    }

    public float applyZoomPatch(float zoom) {
        float f = this.zoomPatch;
        return f > 0.0f ? f * zoom : zoom;
    }

    /* access modifiers changed from: package-private */
    public State toggleMinMaxZoom(State state, float pivotX, float pivotY) {
        this.zoomBounds.set(state);
        float minZoom = this.zoomBounds.getFitZoom();
        float maxZoom = this.settings.getDoubleTapZoom() > 0.0f ? this.settings.getDoubleTapZoom() : this.zoomBounds.getMaxZoom();
        float targetZoom = state.getZoom() < (minZoom + maxZoom) * 0.5f ? maxZoom : minZoom;
        State end = state.copy();
        end.zoomTo(targetZoom, pivotX, pivotY);
        return end;
    }

    /* access modifiers changed from: package-private */
    public State restrictStateBoundsCopy(State state, State prevState, float pivotX, float pivotY, boolean allowOverscroll, boolean allowOverzoom, boolean restrictRotation) {
        State state2 = tmpState;
        State state3 = state;
        state2.set(state);
        if (restrictStateBounds(state2, prevState, pivotX, pivotY, allowOverscroll, allowOverzoom, restrictRotation)) {
            return state2.copy();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00a1  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00fa  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ff  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x012b  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0141  */
    /* JADX WARNING: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean restrictStateBounds(com.alexvasilkov.gestures.State r23, com.alexvasilkov.gestures.State r24, float r25, float r26, boolean r27, boolean r28, boolean r29) {
        /*
            r22 = this;
            r6 = r22
            r7 = r23
            com.alexvasilkov.gestures.Settings r0 = r6.settings
            boolean r0 = r0.isRestrictBounds()
            if (r0 != 0) goto L_0x000e
            r0 = 0
            return r0
        L_0x000e:
            boolean r0 = java.lang.Float.isNaN(r25)
            if (r0 != 0) goto L_0x0020
            boolean r0 = java.lang.Float.isNaN(r26)
            if (r0 == 0) goto L_0x001b
            goto L_0x0020
        L_0x001b:
            r8 = r25
            r9 = r26
            goto L_0x002f
        L_0x0020:
            com.alexvasilkov.gestures.Settings r0 = r6.settings
            android.graphics.Point r1 = tmpPoint
            com.alexvasilkov.gestures.utils.GravityUtils.getDefaultPivot(r0, r1)
            int r0 = r1.x
            float r0 = (float) r0
            int r1 = r1.y
            float r1 = (float) r1
            r8 = r0
            r9 = r1
        L_0x002f:
            r0 = 0
            if (r29 == 0) goto L_0x0057
            com.alexvasilkov.gestures.Settings r1 = r6.settings
            boolean r1 = r1.isRestrictRotation()
            if (r1 == 0) goto L_0x0057
            float r1 = r23.getRotation()
            r2 = 1119092736(0x42b40000, float:90.0)
            float r1 = r1 / r2
            int r1 = java.lang.Math.round(r1)
            float r1 = (float) r1
            float r1 = r1 * r2
            float r2 = r23.getRotation()
            boolean r2 = com.alexvasilkov.gestures.State.equals(r1, r2)
            if (r2 != 0) goto L_0x0057
            r7.rotateTo(r1, r8, r9)
            r0 = 1
            r10 = r0
            goto L_0x0058
        L_0x0057:
            r10 = r0
        L_0x0058:
            com.alexvasilkov.gestures.internal.ZoomBounds r0 = r6.zoomBounds
            r0.set(r7)
            com.alexvasilkov.gestures.internal.ZoomBounds r0 = r6.zoomBounds
            float r11 = r0.getMinZoom()
            com.alexvasilkov.gestures.internal.ZoomBounds r0 = r6.zoomBounds
            float r12 = r0.getMaxZoom()
            r13 = 1065353216(0x3f800000, float:1.0)
            if (r28 == 0) goto L_0x0074
            com.alexvasilkov.gestures.Settings r0 = r6.settings
            float r0 = r0.getOverzoomFactor()
            goto L_0x0075
        L_0x0074:
            r0 = r13
        L_0x0075:
            r14 = r0
            com.alexvasilkov.gestures.internal.ZoomBounds r0 = r6.zoomBounds
            float r1 = r23.getZoom()
            float r15 = r0.restrict(r1, r14)
            if (r24 == 0) goto L_0x0090
            float r2 = r24.getZoom()
            r0 = r22
            r1 = r15
            r3 = r11
            r4 = r12
            r5 = r14
            float r15 = r0.applyZoomResilience(r1, r2, r3, r4, r5)
        L_0x0090:
            float r0 = r23.getZoom()
            boolean r0 = com.alexvasilkov.gestures.State.equals(r15, r0)
            if (r0 != 0) goto L_0x009e
            r7.zoomTo(r15, r8, r9)
            r10 = 1
        L_0x009e:
            r0 = 0
            if (r27 == 0) goto L_0x00aa
            com.alexvasilkov.gestures.Settings r1 = r6.settings
            float r1 = r1.getOverscrollDistanceX()
            r19 = r1
            goto L_0x00ac
        L_0x00aa:
            r19 = r0
        L_0x00ac:
            if (r27 == 0) goto L_0x00b4
            com.alexvasilkov.gestures.Settings r0 = r6.settings
            float r0 = r0.getOverscrollDistanceY()
        L_0x00b4:
            r20 = r0
            com.alexvasilkov.gestures.internal.MovementBounds r0 = r6.movBounds
            r0.set(r7)
            com.alexvasilkov.gestures.internal.MovementBounds r0 = r6.movBounds
            float r17 = r23.getX()
            float r18 = r23.getY()
            android.graphics.PointF r1 = tmpPointF
            r16 = r0
            r21 = r1
            r16.restrict(r17, r18, r19, r20, r21)
            float r0 = r1.x
            float r2 = r1.y
            int r3 = (r15 > r11 ? 1 : (r15 == r11 ? 0 : -1))
            if (r3 >= 0) goto L_0x00fa
            float r3 = r14 * r15
            float r3 = r3 / r11
            float r3 = r3 - r13
            float r4 = r14 - r13
            float r3 = r3 / r4
            double r4 = (double) r3
            double r4 = java.lang.Math.sqrt(r4)
            float r3 = (float) r4
            com.alexvasilkov.gestures.internal.MovementBounds r4 = r6.movBounds
            r4.restrict(r0, r2, r1)
            float r4 = r1.x
            float r1 = r1.y
            float r5 = r0 - r4
            float r5 = r5 * r3
            float r0 = r4 + r5
            float r5 = r2 - r1
            float r5 = r5 * r3
            float r2 = r1 + r5
            r13 = r0
            r16 = r2
            goto L_0x00fd
        L_0x00fa:
            r13 = r0
            r16 = r2
        L_0x00fd:
            if (r24 == 0) goto L_0x012b
            com.alexvasilkov.gestures.internal.MovementBounds r0 = r6.movBounds
            android.graphics.RectF r5 = tmpRectF
            r0.getExternalBounds(r5)
            float r2 = r24.getX()
            float r3 = r5.left
            float r4 = r5.right
            r0 = r22
            r1 = r13
            r6 = r5
            r5 = r19
            float r13 = r0.applyTranslationResilience(r1, r2, r3, r4, r5)
            float r2 = r24.getY()
            float r3 = r6.top
            float r4 = r6.bottom
            r1 = r16
            r5 = r20
            float r16 = r0.applyTranslationResilience(r1, r2, r3, r4, r5)
            r0 = r16
            goto L_0x012d
        L_0x012b:
            r0 = r16
        L_0x012d:
            float r1 = r23.getX()
            boolean r1 = com.alexvasilkov.gestures.State.equals(r13, r1)
            if (r1 == 0) goto L_0x0141
            float r1 = r23.getY()
            boolean r1 = com.alexvasilkov.gestures.State.equals(r0, r1)
            if (r1 != 0) goto L_0x0145
        L_0x0141:
            r7.translateTo(r13, r0)
            r10 = 1
        L_0x0145:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alexvasilkov.gestures.StateController.restrictStateBounds(com.alexvasilkov.gestures.State, com.alexvasilkov.gestures.State, float, float, boolean, boolean, boolean):boolean");
    }

    private float applyZoomResilience(float zoom, float prevZoom, float minZoom, float maxZoom, float overzoom) {
        if (overzoom == 1.0f) {
            return zoom;
        }
        float minZoomOver = minZoom / overzoom;
        float maxZoomOver = maxZoom * overzoom;
        float resilience = 0.0f;
        if (zoom < minZoom && zoom < prevZoom) {
            resilience = (minZoom - zoom) / (minZoom - minZoomOver);
        } else if (zoom > maxZoom && zoom > prevZoom) {
            resilience = (zoom - maxZoom) / (maxZoomOver - maxZoom);
        }
        if (resilience == 0.0f) {
            return zoom;
        }
        return ((prevZoom - zoom) * ((float) Math.sqrt((double) resilience))) + zoom;
    }

    private float applyTranslationResilience(float value, float prevValue, float boundsMin, float boundsMax, float overscroll) {
        if (overscroll == 0.0f) {
            return value;
        }
        float resilience = 0.0f;
        float avg = (value + prevValue) * 0.5f;
        if (avg < boundsMin && value < prevValue) {
            resilience = (boundsMin - avg) / overscroll;
        } else if (avg > boundsMax && value > prevValue) {
            resilience = (avg - boundsMax) / overscroll;
        }
        if (resilience == 0.0f) {
            return value;
        }
        if (resilience > 1.0f) {
            resilience = 1.0f;
        }
        return value - ((value - prevValue) * ((float) Math.sqrt((double) resilience)));
    }

    public float getMinZoom(State state) {
        return this.zoomBounds.set(state).getMinZoom();
    }

    public float getMaxZoom(State state) {
        return this.zoomBounds.set(state).getMaxZoom();
    }

    public float getFitZoom(State state) {
        return this.zoomBounds.set(state).getFitZoom();
    }

    public void getMovementArea(State state, RectF out) {
        this.movBounds.set(state).getExternalBounds(out);
    }

    @Deprecated
    public float getEffectiveMinZoom() {
        return this.zoomBounds.getMinZoom();
    }

    @Deprecated
    public float getEffectiveMaxZoom() {
        return this.zoomBounds.getMaxZoom();
    }

    @Deprecated
    public void getEffectiveMovementArea(RectF out, State state) {
        getMovementArea(state, out);
    }

    @Deprecated
    public static float restrict(float value, float minValue, float maxValue) {
        return Math.max(minValue, Math.min(value, maxValue));
    }

    @Deprecated
    public static void interpolate(State out, State start, State end, float factor) {
        MathUtils.interpolate(out, start, end, factor);
    }

    @Deprecated
    public static void interpolate(State out, State start, float startPivotX, float startPivotY, State end, float endPivotX, float endPivotY, float factor) {
        MathUtils.interpolate(out, start, startPivotX, startPivotY, end, endPivotX, endPivotY, factor);
    }

    @Deprecated
    public static float interpolate(float start, float end, float factor) {
        return MathUtils.interpolate(start, end, factor);
    }

    @Deprecated
    public static void interpolate(RectF out, RectF start, RectF end, float factor) {
        MathUtils.interpolate(out, start, end, factor);
    }
}
