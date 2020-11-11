package com.google.firebase.inappmessaging.display.internal.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.google.firebase.inappmessaging.display.C2472R;
import com.google.firebase.inappmessaging.display.internal.Logging;
import com.google.firebase.inappmessaging.display.internal.layout.util.MeasureUtils;
import java.util.Arrays;
import java.util.List;

public class ModalLayoutLandscape extends BaseModalLayout {
    private static final int ITEM_SPACING_DP = 24;
    private static final float MAX_IMG_WIDTH_PCT = 0.4f;
    private int barrierWidth;
    private View buttonChild;
    private View imageChild;
    private int leftContentHeight;
    private int rightContentHeight;
    private View scrollChild;
    private View titleChild;
    private int vertItemSpacing;

    public ModalLayoutLandscape(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.imageChild = findChildById(C2472R.C2475id.image_view);
        this.titleChild = findChildById(C2472R.C2475id.message_title);
        this.scrollChild = findChildById(C2472R.C2475id.body_scroll);
        this.buttonChild = findChildById(C2472R.C2475id.button);
        this.barrierWidth = this.imageChild.getVisibility() == 8 ? 0 : dpToPixels(24);
        this.vertItemSpacing = dpToPixels(24);
        List<View> rightCol = Arrays.asList(new View[]{this.titleChild, this.scrollChild, this.buttonChild});
        int horizPadding = getPaddingLeft() + getPaddingRight();
        int vertPadding = getPaddingBottom() + getPaddingTop();
        int baseLayoutWidth = calculateBaseWidth(widthMeasureSpec);
        int innerHeight = calculateBaseHeight(heightMeasureSpec) - vertPadding;
        int innerWidth = baseLayoutWidth - horizPadding;
        Logging.logd("Measuring image");
        MeasureUtils.measureAtMost(this.imageChild, (int) (((float) innerWidth) * MAX_IMG_WIDTH_PCT), innerHeight);
        int leftColumnWidth = getDesiredWidth(this.imageChild);
        int rightColumnMaxWidth = innerWidth - (this.barrierWidth + leftColumnWidth);
        Logging.logdPair("Max col widths (l, r)", (float) leftColumnWidth, (float) rightColumnMaxWidth);
        int rightVisible = 0;
        for (View view : rightCol) {
            if (view.getVisibility() != 8) {
                rightVisible++;
            }
        }
        int rightSpacingTotal = Math.max(0, (rightVisible - 1) * this.vertItemSpacing);
        int rightHeightAvail = innerHeight - rightSpacingTotal;
        Logging.logd("Measuring getTitle");
        MeasureUtils.measureAtMost(this.titleChild, rightColumnMaxWidth, rightHeightAvail);
        Logging.logd("Measuring button");
        MeasureUtils.measureAtMost(this.buttonChild, rightColumnMaxWidth, rightHeightAvail);
        Logging.logd("Measuring scroll view");
        MeasureUtils.measureAtMost(this.scrollChild, rightColumnMaxWidth, (rightHeightAvail - getDesiredHeight(this.titleChild)) - getDesiredHeight(this.buttonChild));
        this.leftContentHeight = getDesiredHeight(this.imageChild);
        this.rightContentHeight = rightSpacingTotal;
        for (View view2 : rightCol) {
            this.rightContentHeight += getDesiredHeight(view2);
            rightHeightAvail = rightHeightAvail;
            rightSpacingTotal = rightSpacingTotal;
        }
        int i = rightHeightAvail;
        int leftHeight = this.leftContentHeight + vertPadding;
        int rightHeight = this.rightContentHeight + vertPadding;
        int totalHeight = Math.max(leftHeight, rightHeight);
        List<View> list = rightCol;
        int rightColumnWidth = 0;
        for (View view3 : rightCol) {
            rightColumnWidth = Math.max(getDesiredWidth(view3), rightColumnWidth);
            rightHeight = rightHeight;
            leftHeight = leftHeight;
        }
        int i2 = rightHeight;
        int i3 = vertPadding;
        Logging.logdPair("Measured columns (l, r)", (float) leftColumnWidth, (float) rightColumnWidth);
        int totalWidth = leftColumnWidth + rightColumnWidth + this.barrierWidth + horizPadding;
        int i4 = rightColumnWidth;
        Logging.logdPair("Measured dims", (float) totalWidth, (float) totalHeight);
        setMeasuredDimension(totalWidth, totalHeight);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int rightTopOffset;
        int leftTopOffset;
        super.onLayout(changed, left, top, right, bottom);
        int childrenLeft = getPaddingLeft();
        int childrenTop = getPaddingTop();
        int childrenRight = getMeasuredWidth() - getPaddingRight();
        int i = this.leftContentHeight;
        int i2 = this.rightContentHeight;
        if (i < i2) {
            leftTopOffset = (i2 - i) / 2;
            rightTopOffset = 0;
        } else {
            leftTopOffset = 0;
            rightTopOffset = (i - i2) / 2;
        }
        Logging.logd("Layout image");
        int imageLeft = childrenLeft;
        int imageTop = childrenTop + leftTopOffset;
        int imageRight = imageLeft + getDesiredWidth(this.imageChild);
        layoutChild(this.imageChild, imageLeft, imageTop, imageRight, imageTop + getDesiredHeight(this.imageChild));
        int rightColLeft = imageRight + this.barrierWidth;
        Logging.logd("Layout getTitle");
        int titleTop = childrenTop + rightTopOffset;
        int titleBottom = titleTop + getDesiredHeight(this.titleChild);
        layoutChild(this.titleChild, rightColLeft, titleTop, childrenRight, titleBottom);
        Logging.logd("Layout getBody");
        int buttonMarginTop = 0;
        int scrollTop = titleBottom + (this.titleChild.getVisibility() == 8 ? 0 : this.vertItemSpacing);
        int scrollBottom = scrollTop + getDesiredHeight(this.scrollChild);
        layoutChild(this.scrollChild, rightColLeft, scrollTop, childrenRight, scrollBottom);
        Logging.logd("Layout button");
        if (this.scrollChild.getVisibility() != 8) {
            buttonMarginTop = this.vertItemSpacing;
        }
        layoutChild(this.buttonChild, rightColLeft, scrollBottom + buttonMarginTop);
    }

    /* access modifiers changed from: protected */
    public void layoutCenterHorizontal(View child, int left, int top, int right, int bottom) {
        int centerOffset = (right - left) / 2;
        int halfWidth = child.getMeasuredWidth() / 2;
        layoutChild(child, (left + centerOffset) - halfWidth, top, left + centerOffset + halfWidth, bottom);
    }
}
