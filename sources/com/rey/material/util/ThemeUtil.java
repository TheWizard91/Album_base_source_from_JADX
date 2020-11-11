package com.rey.material.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.TypedValue;
import com.rey.material.C2500R;

public class ThemeUtil {
    private static TypedValue value;

    public static int dpToPx(Context context, int dp) {
        return (int) (TypedValue.applyDimension(1, (float) dp, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static int spToPx(Context context, int sp) {
        return (int) (TypedValue.applyDimension(2, (float) sp, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    private static int getColor(Context context, int id, int defaultValue) {
        if (value == null) {
            value = new TypedValue();
        }
        try {
            Resources.Theme theme = context.getTheme();
            if (theme != null && theme.resolveAttribute(id, value, true)) {
                if (value.type >= 16 && value.type <= 31) {
                    return value.data;
                }
                if (value.type == 3) {
                    return context.getResources().getColor(value.resourceId);
                }
            }
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static int windowBackground(Context context, int defaultValue) {
        return getColor(context, 16842836, defaultValue);
    }

    public static int textColorPrimary(Context context, int defaultValue) {
        return getColor(context, 16842806, defaultValue);
    }

    public static int textColorSecondary(Context context, int defaultValue) {
        return getColor(context, 16842808, defaultValue);
    }

    public static int colorPrimary(Context context, int defaultValue) {
        if (Build.VERSION.SDK_INT >= 21) {
            return getColor(context, 16843827, defaultValue);
        }
        return getColor(context, C2500R.attr.colorPrimary, defaultValue);
    }

    public static int colorPrimaryDark(Context context, int defaultValue) {
        if (Build.VERSION.SDK_INT >= 21) {
            return getColor(context, 16843828, defaultValue);
        }
        return getColor(context, C2500R.attr.colorPrimaryDark, defaultValue);
    }

    public static int colorAccent(Context context, int defaultValue) {
        if (Build.VERSION.SDK_INT >= 21) {
            return getColor(context, 16843829, defaultValue);
        }
        return getColor(context, C2500R.attr.colorAccent, defaultValue);
    }

    public static int colorControlNormal(Context context, int defaultValue) {
        if (Build.VERSION.SDK_INT >= 21) {
            return getColor(context, 16843817, defaultValue);
        }
        return getColor(context, C2500R.attr.colorControlNormal, defaultValue);
    }

    public static int colorControlActivated(Context context, int defaultValue) {
        if (Build.VERSION.SDK_INT >= 21) {
            return getColor(context, 16843818, defaultValue);
        }
        return getColor(context, C2500R.attr.colorControlActivated, defaultValue);
    }

    public static int colorControlHighlight(Context context, int defaultValue) {
        if (Build.VERSION.SDK_INT >= 21) {
            return getColor(context, 16843820, defaultValue);
        }
        return getColor(context, C2500R.attr.colorControlHighlight, defaultValue);
    }

    public static int colorButtonNormal(Context context, int defaultValue) {
        if (Build.VERSION.SDK_INT >= 21) {
            return getColor(context, 16843819, defaultValue);
        }
        return getColor(context, C2500R.attr.colorButtonNormal, defaultValue);
    }

    public static int colorSwitchThumbNormal(Context context, int defaultValue) {
        return getColor(context, C2500R.attr.colorSwitchThumbNormal, defaultValue);
    }

    public static int getType(TypedArray array, int index) {
        if (Build.VERSION.SDK_INT >= 21) {
            return array.getType(index);
        }
        TypedValue value2 = array.peekValue(index);
        if (value2 == null) {
            return 0;
        }
        return value2.type;
    }

    public static CharSequence getString(TypedArray array, int index, CharSequence defaultValue) {
        String result = array.getString(index);
        return result == null ? defaultValue : result;
    }
}
