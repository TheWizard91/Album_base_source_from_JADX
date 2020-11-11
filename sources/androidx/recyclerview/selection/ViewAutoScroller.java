package androidx.recyclerview.selection;

import android.graphics.Point;
import android.graphics.Rect;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

final class ViewAutoScroller extends AutoScroller {
    private static final float DEFAULT_SCROLL_THRESHOLD_RATIO = 0.125f;
    private static final int MAX_SCROLL_STEP = 70;
    private static final String TAG = "ViewAutoScroller";
    private final ScrollHost mHost;
    private Point mLastLocation;
    private Point mOrigin;
    private boolean mPassedInitialMotionThreshold;
    private final Runnable mRunner;
    private final float mScrollThresholdRatio;

    ViewAutoScroller(ScrollHost scrollHost) {
        this(scrollHost, DEFAULT_SCROLL_THRESHOLD_RATIO);
    }

    ViewAutoScroller(ScrollHost scrollHost, float scrollThresholdRatio) {
        Preconditions.checkArgument(scrollHost != null);
        this.mHost = scrollHost;
        this.mScrollThresholdRatio = scrollThresholdRatio;
        this.mRunner = new Runnable() {
            public void run() {
                ViewAutoScroller.this.runScroll();
            }
        };
    }

    public void reset() {
        this.mHost.removeCallback(this.mRunner);
        this.mOrigin = null;
        this.mLastLocation = null;
        this.mPassedInitialMotionThreshold = false;
    }

    public void scroll(Point location) {
        this.mLastLocation = location;
        if (this.mOrigin == null) {
            this.mOrigin = location;
        }
        this.mHost.runAtNextFrame(this.mRunner);
    }

    /* access modifiers changed from: package-private */
    public void runScroll() {
        int pixelsPastView = 0;
        int verticalThreshold = (int) (((float) this.mHost.getViewHeight()) * this.mScrollThresholdRatio);
        if (this.mLastLocation.y <= verticalThreshold) {
            pixelsPastView = this.mLastLocation.y - verticalThreshold;
        } else if (this.mLastLocation.y >= this.mHost.getViewHeight() - verticalThreshold) {
            pixelsPastView = (this.mLastLocation.y - this.mHost.getViewHeight()) + verticalThreshold;
        }
        if (pixelsPastView != 0) {
            if (this.mPassedInitialMotionThreshold || aboveMotionThreshold(this.mLastLocation)) {
                this.mPassedInitialMotionThreshold = true;
                if (pixelsPastView > verticalThreshold) {
                    pixelsPastView = verticalThreshold;
                }
                this.mHost.scrollBy(computeScrollDistance(pixelsPastView));
                this.mHost.removeCallback(this.mRunner);
                this.mHost.runAtNextFrame(this.mRunner);
            }
        }
    }

    private boolean aboveMotionThreshold(Point location) {
        float f = this.mScrollThresholdRatio;
        return Math.abs(this.mOrigin.y - location.y) >= ((int) ((((float) this.mHost.getViewHeight()) * f) * (f * 2.0f)));
    }

    /* access modifiers changed from: package-private */
    public int computeScrollDistance(int pixelsPastView) {
        int direction = (int) Math.signum((float) pixelsPastView);
        int cappedScrollStep = (int) (((float) (direction * 70)) * smoothOutOfBoundsRatio(Math.min(1.0f, ((float) Math.abs(pixelsPastView)) / ((float) ((int) (((float) this.mHost.getViewHeight()) * this.mScrollThresholdRatio))))));
        return cappedScrollStep != 0 ? cappedScrollStep : direction;
    }

    private float smoothOutOfBoundsRatio(float ratio) {
        return (float) Math.pow((double) ratio, 10.0d);
    }

    static abstract class ScrollHost {
        /* access modifiers changed from: package-private */
        public abstract int getViewHeight();

        /* access modifiers changed from: package-private */
        public abstract void removeCallback(Runnable runnable);

        /* access modifiers changed from: package-private */
        public abstract void runAtNextFrame(Runnable runnable);

        /* access modifiers changed from: package-private */
        public abstract void scrollBy(int i);

        ScrollHost() {
        }
    }

    static ScrollHost createScrollHost(RecyclerView recyclerView) {
        return new RuntimeHost(recyclerView);
    }

    private static final class RuntimeHost extends ScrollHost {
        private final RecyclerView mView;

        RuntimeHost(RecyclerView view) {
            this.mView = view;
        }

        /* access modifiers changed from: package-private */
        public void runAtNextFrame(Runnable r) {
            ViewCompat.postOnAnimation(this.mView, r);
        }

        /* access modifiers changed from: package-private */
        public void removeCallback(Runnable r) {
            this.mView.removeCallbacks(r);
        }

        /* access modifiers changed from: package-private */
        public void scrollBy(int dy) {
            this.mView.scrollBy(0, dy);
        }

        /* access modifiers changed from: package-private */
        public int getViewHeight() {
            Rect r = new Rect();
            this.mView.getGlobalVisibleRect(r);
            return r.height();
        }
    }
}
