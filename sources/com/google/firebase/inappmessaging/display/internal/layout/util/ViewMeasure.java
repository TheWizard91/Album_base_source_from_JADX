package com.google.firebase.inappmessaging.display.internal.layout.util;

import android.view.View;
import android.widget.ScrollView;

public class ViewMeasure {
    private boolean flex;
    private int maxHeight;
    private int maxWidth;
    private View view;

    public ViewMeasure(View view2, boolean flex2) {
        this.view = view2;
        this.flex = flex2;
    }

    public void preMeasure(int w, int h) {
        MeasureUtils.measureAtMost(this.view, w, h);
    }

    public View getView() {
        return this.view;
    }

    public boolean isFlex() {
        return this.flex;
    }

    public int getDesiredHeight() {
        if (this.view.getVisibility() == 8) {
            return 0;
        }
        View view2 = this.view;
        if (!(view2 instanceof ScrollView)) {
            return view2.getMeasuredHeight();
        }
        ScrollView sv = (ScrollView) view2;
        return sv.getPaddingBottom() + sv.getPaddingTop() + sv.getChildAt(0).getMeasuredHeight();
    }

    public int getDesiredWidth() {
        if (this.view.getVisibility() == 8) {
            return 0;
        }
        return this.view.getMeasuredHeight();
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public int getMaxWidth() {
        return this.maxWidth;
    }

    public void setMaxDimens(int w, int h) {
        this.maxWidth = w;
        this.maxHeight = h;
    }
}
