package com.stfalcon.frescoimageviewer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

class MultiTouchViewPager extends ViewPager {
    private boolean isDisallowIntercept;
    /* access modifiers changed from: private */
    public boolean isScrolled = true;

    public MultiTouchViewPager(Context context) {
        super(context);
        setScrollStateListener();
    }

    public MultiTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScrollStateListener();
    }

    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        this.isDisallowIntercept = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getPointerCount() <= 1 || !this.isDisallowIntercept) {
            return super.dispatchTouchEvent(ev);
        }
        requestDisallowInterceptTouchEvent(false);
        boolean handled = super.dispatchTouchEvent(ev);
        requestDisallowInterceptTouchEvent(true);
        return handled;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getPointerCount() > 1) {
            return false;
        }
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isScrolled() {
        return this.isScrolled;
    }

    private void setScrollStateListener() {
        addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
                boolean unused = MultiTouchViewPager.this.isScrolled = state == 0;
            }
        });
    }
}
