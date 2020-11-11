package com.google.firebase.inappmessaging.display.internal.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import com.google.firebase.inappmessaging.display.C2472R;
import com.google.firebase.inappmessaging.display.internal.Logging;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseModalLayout extends FrameLayout {
    private static final float DEFAULT_MAX_HEIGHT_PCT = -1.0f;
    private static final float DEFAULT_MAX_WIDTH_PCT = -1.0f;
    private DisplayMetrics mDisplay;
    private float mMaxHeightPct;
    private float mMaxWidthPct;
    private List<View> mVisibleChildren = new ArrayList();

    /* JADX INFO: finally extract failed */
    public BaseModalLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, C2472R.styleable.ModalLayout, 0, 0);
        try {
            this.mMaxWidthPct = a.getFloat(C2472R.styleable.ModalLayout_maxWidthPct, -1.0f);
            this.mMaxHeightPct = a.getFloat(C2472R.styleable.ModalLayout_maxHeightPct, -1.0f);
            a.recycle();
            this.mDisplay = context.getResources().getDisplayMetrics();
        } catch (Throwable th) {
            a.recycle();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Logging.logdHeader("BEGIN LAYOUT");
        Logging.logd("onLayout: l: " + left + ", t: " + top + ", r: " + right + ", b: " + bottom);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Logging.logdHeader("BEGIN MEASURE");
        Logging.logdPair("Display", (float) getDisplayMetrics().widthPixels, (float) getDisplayMetrics().heightPixels);
        this.mVisibleChildren.clear();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                this.mVisibleChildren.add(child);
            } else {
                Logging.logdNumber("Skipping GONE child", (float) i);
            }
        }
    }

    /* access modifiers changed from: protected */
    public float getMaxWidthPct() {
        return this.mMaxWidthPct;
    }

    /* access modifiers changed from: protected */
    public float getMaxHeightPct() {
        return this.mMaxHeightPct;
    }

    /* access modifiers changed from: protected */
    public DisplayMetrics getDisplayMetrics() {
        return this.mDisplay;
    }

    /* access modifiers changed from: protected */
    public List<View> getVisibleChildren() {
        return this.mVisibleChildren;
    }

    /* access modifiers changed from: protected */
    public int calculateBaseWidth(int widthMeasureSpec) {
        if (getMaxWidthPct() > 0.0f) {
            Logging.logd("Width: restrict by pct");
            return roundToNearest((int) (((float) getDisplayMetrics().widthPixels) * getMaxWidthPct()), 4);
        }
        Logging.logd("Width: restrict by spec");
        return View.MeasureSpec.getSize(widthMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public int calculateBaseHeight(int heightMeasureSpec) {
        if (getMaxHeightPct() > 0.0f) {
            Logging.logd("Height: restrict by pct");
            return roundToNearest((int) (((float) getDisplayMetrics().heightPixels) * getMaxHeightPct()), 4);
        }
        Logging.logd("Height: restrict by spec");
        return View.MeasureSpec.getSize(heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        Logging.logdPair("\tdesired (w,h)", (float) child.getMeasuredWidth(), (float) child.getMeasuredHeight());
        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        Logging.logdPair("\tactual  (w,h)", (float) child.getMeasuredWidth(), (float) child.getMeasuredHeight());
    }

    /* access modifiers changed from: protected */
    public void layoutChild(View view, int left, int top) {
        layoutChild(view, left, top, left + getDesiredWidth(view), top + getDesiredHeight(view));
    }

    /* access modifiers changed from: protected */
    public void layoutChild(View view, int left, int top, int right, int bottom) {
        Logging.logdPair("\tleft, right", (float) left, (float) right);
        Logging.logdPair("\ttop, bottom", (float) top, (float) bottom);
        view.layout(left, top, right, bottom);
    }

    /* access modifiers changed from: protected */
    public View findChildById(int id) {
        View v = findViewById(id);
        if (v != null) {
            return v;
        }
        throw new IllegalStateException("No such child: " + id);
    }

    /* access modifiers changed from: protected */
    public int getHeightWithMargins(View child) {
        if (child.getVisibility() == 8) {
            return 0;
        }
        FrameLayout.LayoutParams params = getLayoutParams(child);
        return getDesiredHeight(child) + params.topMargin + params.bottomMargin;
    }

    /* access modifiers changed from: protected */
    public int getMarginBottom(View child) {
        if (child.getVisibility() == 8) {
            return 0;
        }
        return getLayoutParams(child).bottomMargin;
    }

    /* access modifiers changed from: protected */
    public int getMarginTop(View child) {
        if (child.getVisibility() == 8) {
            return 0;
        }
        return getLayoutParams(child).topMargin;
    }

    /* access modifiers changed from: protected */
    public int getWidthWithMargins(View child) {
        if (child.getVisibility() == 8) {
            return 0;
        }
        FrameLayout.LayoutParams params = getLayoutParams(child);
        return getDesiredWidth(child) + params.leftMargin + params.rightMargin;
    }

    /* access modifiers changed from: protected */
    public int getDesiredWidth(View child) {
        if (child.getVisibility() == 8) {
            return 0;
        }
        return child.getMeasuredWidth();
    }

    /* access modifiers changed from: protected */
    public int getDesiredHeight(View child) {
        if (child.getVisibility() == 8) {
            return 0;
        }
        return child.getMeasuredHeight();
    }

    /* access modifiers changed from: protected */
    public FrameLayout.LayoutParams getLayoutParams(View child) {
        return (FrameLayout.LayoutParams) child.getLayoutParams();
    }

    /* access modifiers changed from: protected */
    public int roundToNearest(int num, int unit) {
        return Math.round(((float) num) / ((float) unit)) * unit;
    }

    /* access modifiers changed from: protected */
    public int dpToPixels(int dp) {
        return (int) Math.floor((double) TypedValue.applyDimension(1, (float) dp, this.mDisplay));
    }
}
