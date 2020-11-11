package com.google.firebase.inappmessaging.display.internal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Application;
import android.graphics.Point;
import android.view.View;
import javax.inject.Inject;

public class FiamAnimator {

    public interface AnimationCompleteListener {
        void onComplete();
    }

    @Inject
    FiamAnimator() {
    }

    public void slideIntoView(final Application app, final View view, Position startPosition) {
        view.setAlpha(0.0f);
        Point start = Position.getPoint(startPosition, view);
        view.animate().translationX((float) start.x).translationY((float) start.y).setDuration(1).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.animate().translationX(0.0f).translationY(0.0f).alpha(1.0f).setDuration((long) app.getResources().getInteger(17694722)).setListener((Animator.AnimatorListener) null);
            }
        });
    }

    public void slideOutOfView(Application app, View view, Position end, final AnimationCompleteListener completeListener) {
        Point start = Position.getPoint(end, view);
        view.animate().translationX((float) start.x).translationY((float) start.y).setDuration((long) app.getResources().getInteger(17694722)).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                completeListener.onComplete();
            }
        });
    }

    public enum Position {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM;

        /* access modifiers changed from: private */
        public static Point getPoint(Position d, View view) {
            view.measure(-2, -2);
            int i = C39513.f1740x1fbc48f9[d.ordinal()];
            if (i == 1) {
                return new Point(view.getMeasuredWidth() * -1, 0);
            }
            if (i == 2) {
                return new Point(view.getMeasuredWidth() * 1, 0);
            }
            if (i == 3) {
                return new Point(0, view.getMeasuredHeight() * -1);
            }
            if (i != 4) {
                return new Point(0, view.getMeasuredHeight() * -1);
            }
            return new Point(0, view.getMeasuredHeight() * 1);
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.display.internal.FiamAnimator$3 */
    static /* synthetic */ class C39513 {

        /* renamed from: $SwitchMap$com$google$firebase$inappmessaging$display$internal$FiamAnimator$Position */
        static final /* synthetic */ int[] f1740x1fbc48f9;

        static {
            int[] iArr = new int[Position.values().length];
            f1740x1fbc48f9 = iArr;
            try {
                iArr[Position.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1740x1fbc48f9[Position.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1740x1fbc48f9[Position.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1740x1fbc48f9[Position.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }
}
