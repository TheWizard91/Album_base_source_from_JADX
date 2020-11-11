package com.stfalcon.frescoimageviewer;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

abstract class SwipeDirectionDetector {
    private boolean isDetected;
    private float startX;
    private float startY;
    private int touchSlop;

    public abstract void onDirectionDetected(Direction direction);

    public SwipeDirectionDetector(Context context) {
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000e, code lost:
        if (r0 != 3) goto L_0x0054;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            int r0 = r6.getAction()
            r1 = 0
            if (r0 == 0) goto L_0x0047
            r2 = 1
            if (r0 == r2) goto L_0x0036
            r3 = 2
            if (r0 == r3) goto L_0x0011
            r2 = 3
            if (r0 == r2) goto L_0x0036
            goto L_0x0054
        L_0x0011:
            boolean r0 = r5.isDetected
            if (r0 != 0) goto L_0x0054
            float r0 = r5.getDistance(r6)
            int r3 = r5.touchSlop
            float r3 = (float) r3
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 <= 0) goto L_0x0054
            r5.isDetected = r2
            float r0 = r6.getX()
            float r2 = r6.getY()
            float r3 = r5.startX
            float r4 = r5.startY
            com.stfalcon.frescoimageviewer.SwipeDirectionDetector$Direction r3 = r5.getDirection(r3, r4, r0, r2)
            r5.onDirectionDetected(r3)
            goto L_0x0054
        L_0x0036:
            boolean r0 = r5.isDetected
            if (r0 != 0) goto L_0x003f
            com.stfalcon.frescoimageviewer.SwipeDirectionDetector$Direction r0 = com.stfalcon.frescoimageviewer.SwipeDirectionDetector.Direction.NOT_DETECTED
            r5.onDirectionDetected(r0)
        L_0x003f:
            r0 = 0
            r5.startY = r0
            r5.startX = r0
            r5.isDetected = r1
            goto L_0x0054
        L_0x0047:
            float r0 = r6.getX()
            r5.startX = r0
            float r0 = r6.getY()
            r5.startY = r0
        L_0x0054:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.stfalcon.frescoimageviewer.SwipeDirectionDetector.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public Direction getDirection(float x1, float y1, float x2, float y2) {
        return Direction.get(getAngle(x1, y1, x2, y2));
    }

    public double getAngle(float x1, float y1, float x2, float y2) {
        return ((((Math.atan2((double) (y1 - y2), (double) (x2 - x1)) + 3.141592653589793d) * 180.0d) / 3.141592653589793d) + 180.0d) % 360.0d;
    }

    private float getDistance(MotionEvent ev) {
        float dx = ev.getX(0) - this.startX;
        float dy = ev.getY(0) - this.startY;
        return (float) (((double) 0.0f) + Math.sqrt((double) ((dx * dx) + (dy * dy))));
    }

    public enum Direction {
        NOT_DETECTED,
        UP,
        DOWN,
        LEFT,
        RIGHT;

        public static Direction get(double angle) {
            if (inRange(angle, 45.0f, 135.0f)) {
                return UP;
            }
            if (inRange(angle, 0.0f, 45.0f) || inRange(angle, 315.0f, 360.0f)) {
                return RIGHT;
            }
            if (inRange(angle, 225.0f, 315.0f)) {
                return DOWN;
            }
            return LEFT;
        }

        private static boolean inRange(double angle, float init, float end) {
            return angle >= ((double) init) && angle < ((double) end);
        }
    }
}
