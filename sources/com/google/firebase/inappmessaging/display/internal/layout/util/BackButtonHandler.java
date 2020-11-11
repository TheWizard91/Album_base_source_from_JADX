package com.google.firebase.inappmessaging.display.internal.layout.util;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

public class BackButtonHandler {
    private View.OnClickListener listener;
    private ViewGroup viewGroup;

    public BackButtonHandler(ViewGroup viewGroup2, View.OnClickListener listener2) {
        this.viewGroup = viewGroup2;
        this.listener = listener2;
    }

    public Boolean dispatchKeyEvent(KeyEvent event) {
        if (event == null || event.getKeyCode() != 4 || event.getAction() != 1) {
            return null;
        }
        View.OnClickListener onClickListener = this.listener;
        if (onClickListener == null) {
            return false;
        }
        onClickListener.onClick(this.viewGroup);
        return true;
    }
}
