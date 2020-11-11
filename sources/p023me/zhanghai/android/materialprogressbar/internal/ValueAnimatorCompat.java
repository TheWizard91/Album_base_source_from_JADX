package p023me.zhanghai.android.materialprogressbar.internal;

import android.animation.ValueAnimator;
import android.os.Build;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* renamed from: me.zhanghai.android.materialprogressbar.internal.ValueAnimatorCompat */
public class ValueAnimatorCompat {
    private static Method sValueAnimatorGetDurationScaleMethod;
    private static boolean sValueAnimatorGetDurationScaleMethodInitialized;
    private static final Object sValueAnimatorGetDurationScaleMethodLock = new Object();
    private static Field sValueAnimatorSDurationScaleField;
    private static boolean sValueAnimatorSDurationScaleFieldInitialized;
    private static final Object sValueAnimatorSDurationScaleFieldLock = new Object();

    private ValueAnimatorCompat() {
    }

    public static boolean areAnimatorsEnabled() {
        Field valueAnimatorSDurationScaleField;
        Method valueAnimatorGetDurationScaleMethod;
        if (Build.VERSION.SDK_INT >= 26) {
            return ValueAnimator.areAnimatorsEnabled();
        }
        if (Build.VERSION.SDK_INT >= 17 && (valueAnimatorGetDurationScaleMethod = getValueAnimatorGetDurationScaleMethod()) != null) {
            try {
                if (((Float) valueAnimatorGetDurationScaleMethod.invoke((Object) null, new Object[0])).floatValue() != 0.0f) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (Build.VERSION.SDK_INT >= 16 && (valueAnimatorSDurationScaleField = getValueAnimatorSDurationScaleField()) != null) {
            try {
                if (((Float) valueAnimatorSDurationScaleField.get((Object) null)).floatValue() != 0.0f) {
                    return true;
                }
                return false;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return true;
    }

    private static Method getValueAnimatorGetDurationScaleMethod() {
        Method method;
        synchronized (sValueAnimatorGetDurationScaleMethodLock) {
            if (!sValueAnimatorGetDurationScaleMethodInitialized) {
                try {
                    Method declaredMethod = ValueAnimator.class.getDeclaredMethod("getDurationScale", new Class[0]);
                    sValueAnimatorGetDurationScaleMethod = declaredMethod;
                    declaredMethod.setAccessible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sValueAnimatorGetDurationScaleMethodInitialized = true;
            }
            method = sValueAnimatorGetDurationScaleMethod;
        }
        return method;
    }

    private static Field getValueAnimatorSDurationScaleField() {
        Field field;
        synchronized (sValueAnimatorSDurationScaleFieldLock) {
            if (!sValueAnimatorSDurationScaleFieldInitialized) {
                try {
                    Field declaredField = ValueAnimator.class.getDeclaredField("sDurationScale");
                    sValueAnimatorSDurationScaleField = declaredField;
                    declaredField.setAccessible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sValueAnimatorSDurationScaleFieldInitialized = true;
            }
            field = sValueAnimatorSDurationScaleField;
        }
        return field;
    }
}
