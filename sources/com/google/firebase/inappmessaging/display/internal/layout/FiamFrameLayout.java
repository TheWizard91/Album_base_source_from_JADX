package com.google.firebase.inappmessaging.display.internal.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.google.firebase.inappmessaging.display.internal.layout.util.BackButtonHandler;

public class FiamFrameLayout extends FrameLayout implements BackButtonLayout {
    private BackButtonHandler mBackHandler;

    public FiamFrameLayout(Context context) {
        super(context);
    }

    public FiamFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FiamFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FiamFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setDismissListener(View.OnClickListener listener) {
        this.mBackHandler = new BackButtonHandler(this, listener);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        Boolean handled = this.mBackHandler.dispatchKeyEvent(event);
        if (handled != null) {
            return handled.booleanValue();
        }
        return super.dispatchKeyEvent(event);
    }
}
