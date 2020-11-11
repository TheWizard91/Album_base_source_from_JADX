package com.rey.material.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.rey.material.drawable.RadioButtonDrawable;

public class RadioButton extends CompoundButton {
    public RadioButton(Context context) {
        super(context);
    }

    public RadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super.applyStyle(context, attrs, defStyleAttr, defStyleRes);
        RadioButtonDrawable drawable = new RadioButtonDrawable.Builder(context, attrs, defStyleAttr, defStyleRes).build();
        drawable.setInEditMode(isInEditMode());
        drawable.setAnimEnable(false);
        setButtonDrawable(drawable);
        drawable.setAnimEnable(true);
    }

    public void toggle() {
        if (!isChecked()) {
            super.toggle();
        }
    }

    public void setCheckedImmediately(boolean checked) {
        if (getButtonDrawable() instanceof RadioButtonDrawable) {
            RadioButtonDrawable drawable = (RadioButtonDrawable) getButtonDrawable();
            drawable.setAnimEnable(false);
            setChecked(checked);
            drawable.setAnimEnable(true);
            return;
        }
        setChecked(checked);
    }
}
