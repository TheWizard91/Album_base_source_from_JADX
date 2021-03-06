package p023me.zhanghai.android.materialprogressbar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import p023me.zhanghai.android.materialprogressbar.Interpolators;
import p023me.zhanghai.android.materialprogressbar.internal.ObjectAnimatorCompat;

/* renamed from: me.zhanghai.android.materialprogressbar.Animators */
class Animators {
    private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X;
    private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X;
    private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X;
    private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X;

    private Animators() {
    }

    static {
        Path path = new Path();
        PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X = path;
        path.moveTo(-522.6f, 0.0f);
        Path path2 = path;
        path2.rCubicTo(48.89972f, 0.0f, 166.02657f, 0.0f, 301.2173f, 0.0f);
        path2.rCubicTo(197.58128f, 0.0f, 420.9827f, 0.0f, 420.9827f, 0.0f);
        Path path3 = new Path();
        PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X = path3;
        path3.moveTo(0.0f, 0.1f);
        path3.lineTo(1.0f, 0.8268492f);
        path3.lineTo(2.0f, 0.1f);
        Path path4 = new Path();
        PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X = path4;
        path4.moveTo(-197.6f, 0.0f);
        Path path5 = path4;
        path5.rCubicTo(14.28182f, 0.0f, 85.07782f, 0.0f, 135.54689f, 0.0f);
        path5.rCubicTo(54.26191f, 0.0f, 90.42461f, 0.0f, 168.24332f, 0.0f);
        path5.rCubicTo(144.72154f, 0.0f, 316.40982f, 0.0f, 316.40982f, 0.0f);
        Path path6 = new Path();
        PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X = path6;
        path6.moveTo(0.0f, 0.1f);
        path6.lineTo(1.0f, 0.5713795f);
        path6.lineTo(2.0f, 0.90995026f);
        path6.lineTo(3.0f, 0.1f);
    }

    public static Animator createIndeterminateHorizontalRect1(Object target) {
        ObjectAnimator translateXAnimator = ObjectAnimatorCompat.ofFloat(target, "translateX", (String) null, PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X);
        translateXAnimator.setDuration(2000);
        translateXAnimator.setInterpolator(Interpolators.INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X.INSTANCE);
        translateXAnimator.setRepeatCount(-1);
        ObjectAnimator scaleXAnimator = ObjectAnimatorCompat.ofFloat(target, (String) null, "scaleX", PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X);
        scaleXAnimator.setDuration(2000);
        scaleXAnimator.setInterpolator(Interpolators.INDETERMINATE_HORIZONTAL_RECT1_SCALE_X.INSTANCE);
        scaleXAnimator.setRepeatCount(-1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{translateXAnimator, scaleXAnimator});
        return animatorSet;
    }

    public static Animator createIndeterminateHorizontalRect2(Object target) {
        ObjectAnimator translateXAnimator = ObjectAnimatorCompat.ofFloat(target, "translateX", (String) null, PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X);
        translateXAnimator.setDuration(2000);
        translateXAnimator.setInterpolator(Interpolators.INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X.INSTANCE);
        translateXAnimator.setRepeatCount(-1);
        ObjectAnimator scaleXAnimator = ObjectAnimatorCompat.ofFloat(target, (String) null, "scaleX", PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X);
        scaleXAnimator.setDuration(2000);
        scaleXAnimator.setInterpolator(Interpolators.INDETERMINATE_HORIZONTAL_RECT2_SCALE_X.INSTANCE);
        scaleXAnimator.setRepeatCount(-1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{translateXAnimator, scaleXAnimator});
        return animatorSet;
    }

    public static Animator createIndeterminate(Object target) {
        ObjectAnimator trimPathStartAnimator = ObjectAnimator.ofFloat(target, "trimPathStart", new float[]{0.0f, 0.75f});
        trimPathStartAnimator.setDuration(1333);
        trimPathStartAnimator.setInterpolator(Interpolators.TRIM_PATH_START.INSTANCE);
        trimPathStartAnimator.setRepeatCount(-1);
        ObjectAnimator trimPathEndAnimator = ObjectAnimator.ofFloat(target, "trimPathEnd", new float[]{0.0f, 0.75f});
        trimPathEndAnimator.setDuration(1333);
        trimPathEndAnimator.setInterpolator(Interpolators.TRIM_PATH_END.INSTANCE);
        trimPathEndAnimator.setRepeatCount(-1);
        ObjectAnimator trimPathOffsetAnimator = ObjectAnimator.ofFloat(target, "trimPathOffset", new float[]{0.0f, 0.25f});
        trimPathOffsetAnimator.setDuration(1333);
        trimPathOffsetAnimator.setInterpolator(Interpolators.LINEAR.INSTANCE);
        trimPathOffsetAnimator.setRepeatCount(-1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{trimPathStartAnimator, trimPathEndAnimator, trimPathOffsetAnimator});
        return animatorSet;
    }

    public static Animator createIndeterminateRotation(Object target) {
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(target, "rotation", new float[]{0.0f, 720.0f});
        rotationAnimator.setDuration(6665);
        rotationAnimator.setInterpolator(Interpolators.LINEAR.INSTANCE);
        rotationAnimator.setRepeatCount(-1);
        return rotationAnimator;
    }
}
