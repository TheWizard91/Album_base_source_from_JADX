package com.rey.material.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import androidx.appcompat.widget.ListViewCompat;
import com.rey.material.app.ThemeManager;
import com.rey.material.util.ViewUtil;

public class ListView extends ListViewCompat implements ThemeManager.OnThemeChangedListener {
    protected int mCurrentStyle = Integer.MIN_VALUE;
    /* access modifiers changed from: private */
    public AbsListView.RecyclerListener mRecyclerListener;
    protected int mStyleId;

    public ListView(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0, 0);
    }

    public ListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super.setRecyclerListener(new AbsListView.RecyclerListener() {
            public void onMovedToScrapHeap(View view) {
                RippleManager.cancelRipple(view);
                if (ListView.this.mRecyclerListener != null) {
                    ListView.this.mRecyclerListener.onMovedToScrapHeap(view);
                }
            }
        });
        if (!isInEditMode()) {
            this.mStyleId = ThemeManager.getStyleId(context, attrs, defStyleAttr, defStyleRes);
        }
    }

    public void applyStyle(int resId) {
        ViewUtil.applyStyle(this, resId);
        applyStyle(getContext(), (AttributeSet) null, 0, resId);
    }

    /* access modifiers changed from: protected */
    public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    }

    public void onThemeChanged(ThemeManager.OnThemeChangedEvent event) {
        int style = ThemeManager.getInstance().getCurrentStyle(this.mStyleId);
        if (this.mCurrentStyle != style) {
            this.mCurrentStyle = style;
            applyStyle(style);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mStyleId != 0) {
            ThemeManager.getInstance().registerOnThemeChangedListener(this);
            onThemeChanged((ThemeManager.OnThemeChangedEvent) null);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mStyleId != 0) {
            ThemeManager.getInstance().unregisterOnThemeChangedListener(this);
        }
    }

    public void setRecyclerListener(AbsListView.RecyclerListener listener) {
        this.mRecyclerListener = listener;
    }
}
