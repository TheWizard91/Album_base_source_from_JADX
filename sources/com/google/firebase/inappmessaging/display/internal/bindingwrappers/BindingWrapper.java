package com.google.firebase.inappmessaging.display.internal.bindingwrappers;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import androidx.core.graphics.drawable.DrawableCompat;
import com.google.firebase.inappmessaging.display.internal.InAppMessageLayoutConfig;
import com.google.firebase.inappmessaging.display.internal.Logging;
import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.InAppMessage;
import java.util.Map;

public abstract class BindingWrapper {
    final InAppMessageLayoutConfig config;
    final LayoutInflater inflater;
    protected final InAppMessage message;

    public abstract View getDialogView();

    public abstract ImageView getImageView();

    public abstract ViewGroup getRootView();

    public abstract ViewTreeObserver.OnGlobalLayoutListener inflate(Map<Action, View.OnClickListener> map, View.OnClickListener onClickListener);

    protected BindingWrapper(InAppMessageLayoutConfig config2, LayoutInflater inflater2, InAppMessage message2) {
        this.config = config2;
        this.inflater = inflater2;
        this.message = message2;
    }

    public boolean canSwipeToDismiss() {
        return false;
    }

    public View.OnClickListener getDismissListener() {
        return null;
    }

    public InAppMessageLayoutConfig getConfig() {
        return this.config;
    }

    /* access modifiers changed from: protected */
    public void setViewBgColorFromHex(View view, String hexColor) {
        if (view != null && !TextUtils.isEmpty(hexColor)) {
            try {
                view.setBackgroundColor(Color.parseColor(hexColor));
            } catch (IllegalArgumentException e) {
                Logging.loge("Error parsing background color: " + e.toString() + " color: " + hexColor);
            }
        }
    }

    public static void setButtonBgColorFromHex(Button button, String hexColor) {
        try {
            Drawable compatDrawable = DrawableCompat.wrap(button.getBackground());
            DrawableCompat.setTint(compatDrawable, Color.parseColor(hexColor));
            button.setBackground(compatDrawable);
        } catch (IllegalArgumentException e) {
            Logging.loge("Error parsing background color: " + e.toString());
        }
    }

    public static void setupViewButtonFromModel(Button viewButton, com.google.firebase.inappmessaging.model.Button modelButton) {
        String buttonTextHexColor = modelButton.getText().getHexColor();
        setButtonBgColorFromHex(viewButton, modelButton.getButtonHexColor());
        viewButton.setText(modelButton.getText().getText());
        viewButton.setTextColor(Color.parseColor(buttonTextHexColor));
    }

    /* access modifiers changed from: protected */
    public void setButtonActionListener(Button button, View.OnClickListener actionListener) {
        if (button != null) {
            button.setOnClickListener(actionListener);
        }
    }
}
