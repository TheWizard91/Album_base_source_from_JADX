package com.google.firebase.inappmessaging.display.internal.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.google.firebase.inappmessaging.display.C2472R;
import com.google.firebase.inappmessaging.display.internal.Logging;
import com.google.firebase.inappmessaging.display.internal.layout.util.MeasureUtils;
import java.util.Arrays;
import java.util.List;

public class CardLayoutLandscape extends BaseModalLayout {
    private static double IMAGE_MAX_WIDTH_PCT = 0.6d;
    private View actionBarChild;
    private View imageChild;
    private View scrollChild;
    private View titleChild;

    public CardLayoutLandscape(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.imageChild = findChildById(C2472R.C2475id.image_view);
        this.titleChild = findChildById(C2472R.C2475id.message_title);
        this.scrollChild = findChildById(C2472R.C2475id.body_scroll);
        View findChildById = findChildById(C2472R.C2475id.action_bar);
        this.actionBarChild = findChildById;
        List<View> rightCol = Arrays.asList(new View[]{this.titleChild, this.scrollChild, findChildById});
        int baseLayoutWidth = calculateBaseWidth(widthMeasureSpec);
        int baseLayoutHeight = calculateBaseHeight(heightMeasureSpec);
        int maxImageWidth = roundToNearest((int) (IMAGE_MAX_WIDTH_PCT * ((double) baseLayoutWidth)), 4);
        Logging.logd("Measuring image");
        MeasureUtils.measureFullHeight(this.imageChild, baseLayoutWidth, baseLayoutHeight);
        if (getDesiredWidth(this.imageChild) > maxImageWidth) {
            Logging.logd("Image exceeded maximum width, remeasuring image");
            MeasureUtils.measureFullWidth(this.imageChild, maxImageWidth, baseLayoutHeight);
        }
        int imageHeight = getDesiredHeight(this.imageChild);
        int leftColumnWidth = getDesiredWidth(this.imageChild);
        int rightColumnMaxWidth = baseLayoutWidth - leftColumnWidth;
        Logging.logdPair("Max col widths (l, r)", (float) leftColumnWidth, (float) rightColumnMaxWidth);
        Logging.logd("Measuring title");
        MeasureUtils.measureAtMost(this.titleChild, rightColumnMaxWidth, imageHeight);
        Logging.logd("Measuring action bar");
        MeasureUtils.measureAtMost(this.actionBarChild, rightColumnMaxWidth, imageHeight);
        Logging.logd("Measuring scroll view");
        MeasureUtils.measureFullHeight(this.scrollChild, rightColumnMaxWidth, (imageHeight - getDesiredHeight(this.titleChild)) - getDesiredHeight(this.actionBarChild));
        int rightColumnWidth = 0;
        for (View view : rightCol) {
            rightColumnWidth = Math.max(getDesiredWidth(view), rightColumnWidth);
        }
        Logging.logdPair("Measured columns (l, r)", (float) leftColumnWidth, (float) rightColumnWidth);
        int totalWidth = leftColumnWidth + rightColumnWidth;
        Logging.logdPair("Measured dims", (float) totalWidth, (float) imageHeight);
        setMeasuredDimension(totalWidth, imageHeight);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int CONTAINER_RIGHT = getMeasuredWidth();
        int CONTAINER_BOTTOM = getMeasuredHeight();
        Logging.logd("Layout image");
        int imageRight = getDesiredWidth(this.imageChild);
        layoutChild(this.imageChild, 0, 0, imageRight, getDesiredHeight(this.imageChild));
        int rightColLeft = imageRight;
        Logging.logd("Layout title");
        int titleBottom = getDesiredHeight(this.titleChild);
        layoutChild(this.titleChild, rightColLeft, 0, CONTAINER_RIGHT, titleBottom);
        Logging.logd("Layout scroll");
        int scrollTop = titleBottom;
        layoutChild(this.scrollChild, rightColLeft, scrollTop, CONTAINER_RIGHT, getDesiredHeight(this.scrollChild) + scrollTop);
        Logging.logd("Layout action bar");
        layoutChild(this.actionBarChild, rightColLeft, CONTAINER_BOTTOM - getDesiredHeight(this.actionBarChild), CONTAINER_RIGHT, CONTAINER_BOTTOM);
    }
}
