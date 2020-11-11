package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.widget.Button;
import androidx.core.graphics.drawable.DrawableCompat;
import com.google.android.gms.base.C2393R;
import com.google.android.gms.common.util.DeviceProperties;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class SignInButtonImpl extends Button {
    public SignInButtonImpl(Context context) {
        this(context, (AttributeSet) null);
    }

    public SignInButtonImpl(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842824);
    }

    public final void configure(Resources resources, SignInButtonConfig signInButtonConfig) {
        configure(resources, signInButtonConfig.getButtonSize(), signInButtonConfig.getColorScheme());
    }

    public final void configure(Resources resources, int i, int i2) {
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(14.0f);
        int i3 = (int) ((resources.getDisplayMetrics().density * 48.0f) + 0.5f);
        setMinHeight(i3);
        setMinWidth(i3);
        int zaa = zaa(i2, C2393R.C2395drawable.common_google_signin_btn_icon_dark, C2393R.C2395drawable.common_google_signin_btn_icon_light, C2393R.C2395drawable.common_google_signin_btn_icon_light);
        int zaa2 = zaa(i2, C2393R.C2395drawable.common_google_signin_btn_text_dark, C2393R.C2395drawable.common_google_signin_btn_text_light, C2393R.C2395drawable.common_google_signin_btn_text_light);
        if (i == 0 || i == 1) {
            zaa = zaa2;
        } else if (i != 2) {
            throw new IllegalStateException(new StringBuilder(32).append("Unknown button size: ").append(i).toString());
        }
        Drawable wrap = DrawableCompat.wrap(resources.getDrawable(zaa));
        DrawableCompat.setTintList(wrap, resources.getColorStateList(C2393R.C2394color.common_google_signin_btn_tint));
        DrawableCompat.setTintMode(wrap, PorterDuff.Mode.SRC_ATOP);
        setBackgroundDrawable(wrap);
        setTextColor((ColorStateList) Preconditions.checkNotNull(resources.getColorStateList(zaa(i2, C2393R.C2394color.common_google_signin_btn_text_dark, C2393R.C2394color.common_google_signin_btn_text_light, C2393R.C2394color.common_google_signin_btn_text_light))));
        if (i == 0) {
            setText(resources.getString(C2393R.string.common_signin_button_text));
        } else if (i == 1) {
            setText(resources.getString(C2393R.string.common_signin_button_text_long));
        } else if (i == 2) {
            setText((CharSequence) null);
        } else {
            throw new IllegalStateException(new StringBuilder(32).append("Unknown button size: ").append(i).toString());
        }
        setTransformationMethod((TransformationMethod) null);
        if (DeviceProperties.isWearable(getContext())) {
            setGravity(19);
        }
    }

    private static int zaa(int i, int i2, int i3, int i4) {
        if (i == 0) {
            return i2;
        }
        if (i == 1) {
            return i3;
        }
        if (i == 2) {
            return i4;
        }
        throw new IllegalStateException(new StringBuilder(33).append("Unknown color scheme: ").append(i).toString());
    }
}
