package com.google.firebase.inappmessaging.display.internal.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.google.firebase.inappmessaging.display.C2472R;
import com.google.firebase.inappmessaging.display.internal.Logging;
import com.google.firebase.inappmessaging.display.internal.layout.util.MeasureUtils;
import com.google.firebase.inappmessaging.display.internal.layout.util.VerticalViewGroupMeasure;
import com.google.firebase.inappmessaging.display.internal.layout.util.ViewMeasure;

public class ModalLayoutPortrait extends BaseModalLayout {
    private static final int ITEM_SPACING_DP = 24;
    private int vertItemSpacing;
    private VerticalViewGroupMeasure vgm = new VerticalViewGroupMeasure();

    public ModalLayoutPortrait(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.vertItemSpacing = dpToPixels(24);
        int horizPadding = getPaddingRight() + getPaddingLeft();
        int vertPadding = getPaddingBottom() + getPaddingTop();
        int baseLayoutWidth = calculateBaseWidth(widthMeasureSpec);
        int baseLayoutHeight = calculateBaseHeight(heightMeasureSpec);
        boolean isHeightConstrained = true;
        int reservedHeight = vertPadding + ((getVisibleChildren().size() - 1) * this.vertItemSpacing);
        this.vgm.reset(baseLayoutWidth, baseLayoutHeight);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            this.vgm.add(child, isFlex(child));
        }
        Logging.logd("Screen dimens: " + getDisplayMetrics());
        Logging.logdPair("Max pct", getMaxWidthPct(), getMaxHeightPct());
        Logging.logdPair("Base dimens", (float) baseLayoutWidth, (float) baseLayoutHeight);
        int totalDesiredHeight = reservedHeight;
        for (ViewMeasure vm : this.vgm.getViews()) {
            Logging.logd("Pre-measure child");
            vm.preMeasure(baseLayoutWidth, baseLayoutHeight);
        }
        int totalDesiredHeight2 = totalDesiredHeight + this.vgm.getTotalHeight();
        Logging.logdNumber("Total reserved height", (float) reservedHeight);
        Logging.logdNumber("Total desired height", (float) totalDesiredHeight2);
        if (totalDesiredHeight2 <= baseLayoutHeight) {
            isHeightConstrained = false;
        }
        Logging.logd("Total height constrained: " + isHeightConstrained);
        if (isHeightConstrained) {
            this.vgm.allocateSpace((baseLayoutHeight - reservedHeight) - this.vgm.getTotalFixedHeight());
        }
        int heightUsed = reservedHeight;
        int maxChildWidth = baseLayoutWidth - horizPadding;
        for (ViewMeasure vm2 : this.vgm.getViews()) {
            Logging.logd("Measuring child");
            MeasureUtils.measureAtMost(vm2.getView(), maxChildWidth, vm2.getMaxHeight());
            heightUsed += getDesiredHeight(vm2.getView());
        }
        Logging.logdPair("Measured dims", (float) baseLayoutWidth, (float) heightUsed);
        setMeasuredDimension(baseLayoutWidth, heightUsed);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childLeft;
        int centerOffset;
        super.onLayout(changed, left, top, right, bottom);
        int y = getPaddingTop();
        int x = getPaddingLeft();
        int numVisible = getVisibleChildren().size();
        for (int i = 0; i < numVisible; i++) {
            View child = getVisibleChildren().get(i);
            FrameLayout.LayoutParams layoutParams = getLayoutParams(child);
            int childHeight = child.getMeasuredHeight();
            int childWidth = child.getMeasuredWidth();
            int childTop = y;
            int childBottom = y + childHeight;
            if ((layoutParams.gravity & 1) == 1) {
                int centerOffset2 = (right - left) / 2;
                int halfWidth = childWidth / 2;
                childLeft = centerOffset2 - halfWidth;
                centerOffset = centerOffset2 + halfWidth;
            } else {
                childLeft = x;
                centerOffset = x + childWidth;
            }
            Logging.logd("Layout child " + i);
            Logging.logdPair("\t(top, bottom)", (float) childTop, (float) childBottom);
            Logging.logdPair("\t(left, right)", (float) childLeft, (float) centerOffset);
            child.layout(childLeft, childTop, centerOffset, childBottom);
            y += child.getMeasuredHeight();
            if (i < numVisible - 1) {
                y += this.vertItemSpacing;
            }
        }
    }

    private boolean isFlex(View child) {
        return child.getId() == C2472R.C2475id.body_scroll || child.getId() == C2472R.C2475id.image_view;
    }
}
