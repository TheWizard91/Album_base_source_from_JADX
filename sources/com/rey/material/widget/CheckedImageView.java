package com.rey.material.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;

public class CheckedImageView extends ImageView implements Checkable {
    private static final int[] STATE_CHECKED = {16842912};
    private boolean mChecked = false;

    public CheckedImageView(Context context) {
        super(context);
    }

    public CheckedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setChecked(boolean b) {
        if (this.mChecked != b) {
            this.mChecked = b;
            refreshDrawableState();
        }
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    public void toggle() {
        setChecked(!isChecked());
    }

    public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        int[] additionalStates = this.mChecked ? STATE_CHECKED : null;
        if (additionalStates != null) {
            mergeDrawableStates(drawableState, additionalStates);
        }
        return drawableState;
    }
}
