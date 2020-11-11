package com.google.firebase.inappmessaging.display.internal.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.google.firebase.inappmessaging.display.C2472R;
import com.google.firebase.inappmessaging.display.internal.Logging;
import com.google.firebase.inappmessaging.display.internal.layout.util.MeasureUtils;

public class CardLayoutPortrait extends BaseModalLayout {
    private static double IMAGE_MAX_HEIGHT_PCT = 0.8d;
    private View actionBarChild;
    private View imageChild;
    private View scrollChild;
    private View titleChild;

    public CardLayoutPortrait(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.imageChild = findChildById(C2472R.C2475id.image_view);
        this.titleChild = findChildById(C2472R.C2475id.message_title);
        this.scrollChild = findChildById(C2472R.C2475id.body_scroll);
        this.actionBarChild = findChildById(C2472R.C2475id.action_bar);
        int baseLayoutWidth = calculateBaseWidth(widthMeasureSpec);
        int baseLayoutHeight = calculateBaseHeight(heightMeasureSpec);
        int maxImageHeight = roundToNearest((int) (IMAGE_MAX_HEIGHT_PCT * ((double) baseLayoutHeight)), 4);
        Logging.logd("Measuring image");
        MeasureUtils.measureFullWidth(this.imageChild, baseLayoutWidth, baseLayoutHeight);
        if (getDesiredHeight(this.imageChild) > maxImageHeight) {
            Logging.logd("Image exceeded maximum height, remeasuring image");
            MeasureUtils.measureFullHeight(this.imageChild, baseLayoutWidth, maxImageHeight);
        }
        int imageWidth = getDesiredWidth(this.imageChild);
        Logging.logd("Measuring title");
        MeasureUtils.measureFullWidth(this.titleChild, imageWidth, baseLayoutHeight);
        Logging.logd("Measuring action bar");
        MeasureUtils.measureFullWidth(this.actionBarChild, imageWidth, baseLayoutHeight);
        Logging.logd("Measuring scroll view");
        MeasureUtils.measureFullWidth(this.scrollChild, imageWidth, ((baseLayoutHeight - getDesiredHeight(this.imageChild)) - getDesiredHeight(this.titleChild)) - getDesiredHeight(this.actionBarChild));
        int totalHeight = 0;
        int numVisible = getVisibleChildren().size();
        for (int i = 0; i < numVisible; i++) {
            totalHeight += getDesiredHeight(getVisibleChildren().get(i));
        }
        setMeasuredDimension(imageWidth, totalHeight);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int y = 0;
        int numVisible = getVisibleChildren().size();
        for (int i = 0; i < numVisible; i++) {
            View child = getVisibleChildren().get(i);
            int childTop = y;
            int childBottom = y + child.getMeasuredHeight();
            int childRight = 0 + child.getMeasuredWidth();
            Logging.logd("Layout child " + i);
            Logging.logdPair("\t(top, bottom)", (float) childTop, (float) childBottom);
            Logging.logdPair("\t(left, right)", (float) 0, (float) childRight);
            child.layout(0, childTop, childRight, childBottom);
            Logging.logdPair("Child " + i + " wants to be ", (float) child.getMeasuredWidth(), (float) child.getMeasuredHeight());
            y += child.getMeasuredHeight();
        }
    }
}
