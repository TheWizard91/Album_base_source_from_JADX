package androidx.recyclerview.selection;

import android.graphics.Point;
import android.view.MotionEvent;

final class MotionEvents {
    private MotionEvents() {
    }

    static boolean isMouseEvent(MotionEvent e) {
        return e.getToolType(0) == 3;
    }

    static boolean isFingerEvent(MotionEvent e) {
        return e.getToolType(0) == 1;
    }

    static boolean isActionDown(MotionEvent e) {
        return e.getActionMasked() == 0;
    }

    static boolean isActionMove(MotionEvent e) {
        return e.getActionMasked() == 2;
    }

    static boolean isActionUp(MotionEvent e) {
        return e.getActionMasked() == 1;
    }

    static boolean isActionPointerUp(MotionEvent e) {
        return e.getActionMasked() == 6;
    }

    static boolean isActionPointerDown(MotionEvent e) {
        return e.getActionMasked() == 5;
    }

    static boolean isActionCancel(MotionEvent e) {
        return e.getActionMasked() == 3;
    }

    static Point getOrigin(MotionEvent e) {
        return new Point((int) e.getX(), (int) e.getY());
    }

    static boolean isPrimaryMouseButtonPressed(MotionEvent e) {
        return isButtonPressed(e, 1);
    }

    static boolean isSecondaryMouseButtonPressed(MotionEvent e) {
        return isButtonPressed(e, 2);
    }

    static boolean isTertiaryMouseButtonPressed(MotionEvent e) {
        return isButtonPressed(e, 4);
    }

    private static boolean isButtonPressed(MotionEvent e, int button) {
        if (button != 0 && (e.getButtonState() & button) == button) {
            return true;
        }
        return false;
    }

    static boolean isShiftKeyPressed(MotionEvent e) {
        return hasBit(e.getMetaState(), 1);
    }

    static boolean isCtrlKeyPressed(MotionEvent e) {
        return hasBit(e.getMetaState(), 4096);
    }

    static boolean isAltKeyPressed(MotionEvent e) {
        return hasBit(e.getMetaState(), 2);
    }

    static boolean isTouchpadScroll(MotionEvent e) {
        return isMouseEvent(e) && isActionMove(e) && e.getButtonState() == 0;
    }

    static boolean isPointerDragEvent(MotionEvent e) {
        return isPrimaryMouseButtonPressed(e) && isActionMove(e);
    }

    private static boolean hasBit(int metaState, int bit) {
        return (metaState & bit) != 0;
    }
}
