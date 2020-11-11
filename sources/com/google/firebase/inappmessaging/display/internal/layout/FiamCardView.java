package com.google.firebase.inappmessaging.display.internal.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import androidx.cardview.widget.CardView;
import com.google.firebase.inappmessaging.display.internal.layout.util.BackButtonHandler;

public class FiamCardView extends CardView implements BackButtonLayout {
    private BackButtonHandler mBackHandler;

    public FiamCardView(Context context) {
        super(context);
    }

    public FiamCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FiamCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
