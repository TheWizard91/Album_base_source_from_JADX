package androidx.mediarouter.app;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.C2018R;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.mediarouter.C2142R;

final class MediaRouterThemeHelper {
    static final int COLOR_DARK_ON_LIGHT_BACKGROUND = -570425344;
    private static final int COLOR_DARK_ON_LIGHT_BACKGROUND_RES_ID = C2142R.C2143color.mr_dynamic_dialog_icon_light;
    static final int COLOR_WHITE_ON_DARK_BACKGROUND = -1;
    private static final float MIN_CONTRAST = 3.0f;

    private MediaRouterThemeHelper() {
    }

    static Drawable getMuteButtonDrawableIcon(Context context) {
        return getIconByDrawableId(context, C2142R.C2144drawable.mr_cast_mute_button);
    }

    static Drawable getCheckBoxDrawableIcon(Context context) {
        return getIconByDrawableId(context, C2142R.C2144drawable.mr_cast_checkbox);
    }

    static Drawable getDefaultDrawableIcon(Context context) {
        return getIconByAttrId(context, C2142R.attr.mediaRouteDefaultIconDrawable);
    }

    static Drawable getTvDrawableIcon(Context context) {
        return getIconByAttrId(context, C2142R.attr.mediaRouteTvIconDrawable);
    }

    static Drawable getSpeakerDrawableIcon(Context context) {
        return getIconByAttrId(context, C2142R.attr.mediaRouteSpeakerIconDrawable);
    }

    static Drawable getSpeakerGroupDrawableIcon(Context context) {
        return getIconByAttrId(context, C2142R.attr.mediaRouteSpeakerGroupIconDrawable);
    }

    private static Drawable getIconByDrawableId(Context context, int drawableId) {
        Drawable icon = DrawableCompat.wrap(ContextCompat.getDrawable(context, drawableId));
        if (isLightTheme(context)) {
            DrawableCompat.setTint(icon, ContextCompat.getColor(context, COLOR_DARK_ON_LIGHT_BACKGROUND_RES_ID));
        }
        return icon;
    }

    private static Drawable getIconByAttrId(Context context, int attrId) {
        TypedArray styledAttributes = context.obtainStyledAttributes(new int[]{attrId});
        Drawable icon = DrawableCompat.wrap(styledAttributes.getDrawable(0));
        if (isLightTheme(context)) {
            DrawableCompat.setTint(icon, ContextCompat.getColor(context, COLOR_DARK_ON_LIGHT_BACKGROUND_RES_ID));
        }
        styledAttributes.recycle();
        return icon;
    }

    static Context createThemedButtonContext(Context context) {
        Context context2 = new ContextThemeWrapper(context, getRouterThemeId(context));
        int style = getThemeResource(context2, C2142R.attr.mediaRouteTheme);
        if (style != 0) {
            return new ContextThemeWrapper(context2, style);
        }
        return context2;
    }

    static Context createThemedDialogContext(Context context, int theme, boolean alertDialog) {
        if (theme == 0) {
            theme = getThemeResource(context, !alertDialog ? C2018R.attr.dialogTheme : C2018R.attr.alertDialogTheme);
        }
        Context context2 = new ContextThemeWrapper(context, theme);
        if (getThemeResource(context2, C2142R.attr.mediaRouteTheme) != 0) {
            return new ContextThemeWrapper(context2, getRouterThemeId(context2));
        }
        return context2;
    }

    static int createThemedDialogStyle(Context context) {
        int theme = getThemeResource(context, C2142R.attr.mediaRouteTheme);
        if (theme == 0) {
            return getRouterThemeId(context);
        }
        return theme;
    }

    static int getThemeResource(Context context, int attr) {
        TypedValue value = new TypedValue();
        if (context.getTheme().resolveAttribute(attr, value, true)) {
            return value.resourceId;
        }
        return 0;
    }

    static float getDisabledAlpha(Context context) {
        TypedValue value = new TypedValue();
        if (context.getTheme().resolveAttribute(16842803, value, true)) {
            return value.getFloat();
        }
        return 0.5f;
    }

    static int getControllerColor(Context context, int style) {
        if (ColorUtils.calculateContrast(-1, getThemeColor(context, style, C2018R.attr.colorPrimary)) >= 3.0d) {
            return -1;
        }
        return COLOR_DARK_ON_LIGHT_BACKGROUND;
    }

    static int getButtonTextColor(Context context) {
        int primaryColor = getThemeColor(context, 0, C2018R.attr.colorPrimary);
        if (ColorUtils.calculateContrast(primaryColor, getThemeColor(context, 0, 16842801)) < 3.0d) {
            return getThemeColor(context, 0, C2018R.attr.colorAccent);
        }
        return primaryColor;
    }

    static TypedArray getStyledAttributes(Context context) {
        return context.obtainStyledAttributes(new int[]{C2142R.attr.mediaRouteDefaultIconDrawable, C2142R.attr.mediaRouteTvIconDrawable, C2142R.attr.mediaRouteSpeakerIconDrawable, C2142R.attr.mediaRouteSpeakerGroupIconDrawable});
    }

    static void setDialogBackgroundColor(Context context, Dialog dialog) {
        dialog.getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(context, isLightTheme(context) ? C2142R.C2143color.mr_dynamic_dialog_background_light : C2142R.C2143color.mr_dynamic_dialog_background_dark));
    }

    static void setMediaControlsBackgroundColor(Context context, View mainControls, View groupControls, boolean hasGroup) {
        int primaryColor = getThemeColor(context, 0, C2018R.attr.colorPrimary);
        int primaryDarkColor = getThemeColor(context, 0, C2018R.attr.colorPrimaryDark);
        if (hasGroup && getControllerColor(context, 0) == COLOR_DARK_ON_LIGHT_BACKGROUND) {
            primaryDarkColor = primaryColor;
            primaryColor = -1;
        }
        mainControls.setBackgroundColor(primaryColor);
        groupControls.setBackgroundColor(primaryDarkColor);
        mainControls.setTag(Integer.valueOf(primaryColor));
        groupControls.setTag(Integer.valueOf(primaryDarkColor));
    }

    static void setVolumeSliderColor(Context context, MediaRouteVolumeSlider volumeSlider, View backgroundView) {
        int controllerColor = getControllerColor(context, 0);
        if (Color.alpha(controllerColor) != 255) {
            controllerColor = ColorUtils.compositeColors(controllerColor, ((Integer) backgroundView.getTag()).intValue());
        }
        volumeSlider.setColor(controllerColor);
    }

    static void setVolumeSliderColor(Context context, MediaRouteVolumeSlider volumeSlider) {
        int backgroundColor;
        int progressAndThumbColor;
        if (isLightTheme(context)) {
            progressAndThumbColor = ContextCompat.getColor(context, C2142R.C2143color.mr_cast_progressbar_progress_and_thumb_light);
            backgroundColor = ContextCompat.getColor(context, C2142R.C2143color.mr_cast_progressbar_background_light);
        } else {
            progressAndThumbColor = ContextCompat.getColor(context, C2142R.C2143color.mr_cast_progressbar_progress_and_thumb_dark);
            backgroundColor = ContextCompat.getColor(context, C2142R.C2143color.mr_cast_progressbar_background_dark);
        }
        volumeSlider.setColor(progressAndThumbColor, backgroundColor);
    }

    static void setIndeterminateProgressBarColor(Context context, ProgressBar progressBar) {
        if (progressBar.isIndeterminate()) {
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, isLightTheme(context) ? C2142R.C2143color.mr_cast_progressbar_progress_and_thumb_light : C2142R.C2143color.mr_cast_progressbar_progress_and_thumb_dark), PorterDuff.Mode.SRC_IN);
        }
    }

    private static boolean isLightTheme(Context context) {
        TypedValue value = new TypedValue();
        return context.getTheme().resolveAttribute(C2018R.attr.isLightTheme, value, true) && value.data != 0;
    }

    private static int getThemeColor(Context context, int style, int attr) {
        if (style != 0) {
            TypedArray ta = context.obtainStyledAttributes(style, new int[]{attr});
            int color = ta.getColor(0, 0);
            ta.recycle();
            if (color != 0) {
                return color;
            }
        }
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        if (value.resourceId != 0) {
            return context.getResources().getColor(value.resourceId);
        }
        return value.data;
    }

    private static int getRouterThemeId(Context context) {
        if (isLightTheme(context)) {
            if (getControllerColor(context, 0) == COLOR_DARK_ON_LIGHT_BACKGROUND) {
                return C2142R.C2148style.Theme_MediaRouter_Light;
            }
            return C2142R.C2148style.Theme_MediaRouter_Light_DarkControlPanel;
        } else if (getControllerColor(context, 0) == COLOR_DARK_ON_LIGHT_BACKGROUND) {
            return C2142R.C2148style.Theme_MediaRouter_LightControlPanel;
        } else {
            return C2142R.C2148style.Theme_MediaRouter;
        }
    }
}
