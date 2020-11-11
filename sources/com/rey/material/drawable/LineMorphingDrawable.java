package com.rey.material.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import androidx.core.text.TextUtilsCompat;
import com.rey.material.C2500R;
import com.rey.material.util.ThemeUtil;
import java.util.Locale;

public class LineMorphingDrawable extends Drawable implements Animatable {
    private int mAnimDuration;
    private float mAnimProgress;
    private boolean mClockwise;
    private int mCurState;
    private RectF mDrawBound;
    private int mHeight;
    private Interpolator mInterpolator;
    private boolean mIsRtl;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private Paint mPaint;
    private Path mPath;
    private int mPrevState;
    private boolean mRunning;
    private long mStartTime;
    private State[] mStates;
    private Paint.Cap mStrokeCap;
    private int mStrokeColor;
    private Paint.Join mStrokeJoin;
    private int mStrokeSize;
    private final Runnable mUpdater;
    private int mWidth;

    private LineMorphingDrawable(State[] states, int curState, int width, int height, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom, int animDuration, Interpolator interpolator, int strokeSize, int strokeColor, Paint.Cap strokeCap, Paint.Join strokeJoin, boolean clockwise, boolean isRtl) {
        this.mRunning = false;
        this.mPaddingLeft = 12;
        this.mPaddingTop = 12;
        this.mPaddingRight = 12;
        this.mPaddingBottom = 12;
        this.mUpdater = new Runnable() {
            public void run() {
                LineMorphingDrawable.this.update();
            }
        };
        this.mStates = states;
        this.mWidth = width;
        this.mHeight = height;
        this.mPaddingLeft = paddingLeft;
        this.mPaddingTop = paddingTop;
        this.mPaddingRight = paddingRight;
        this.mPaddingBottom = paddingBottom;
        this.mAnimDuration = animDuration;
        this.mInterpolator = interpolator;
        this.mStrokeSize = strokeSize;
        this.mStrokeColor = strokeColor;
        this.mStrokeCap = strokeCap;
        this.mStrokeJoin = strokeJoin;
        this.mClockwise = clockwise;
        this.mIsRtl = isRtl;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeCap(this.mStrokeCap);
        this.mPaint.setStrokeJoin(this.mStrokeJoin);
        this.mPaint.setColor(this.mStrokeColor);
        this.mPaint.setStrokeWidth((float) this.mStrokeSize);
        this.mDrawBound = new RectF();
        this.mPath = new Path();
        switchLineState(curState, false);
    }

    public void draw(Canvas canvas) {
        int restoreCount = canvas.save();
        float degrees = ((float) (this.mClockwise ? 180 : -180)) * ((this.mPrevState < this.mCurState ? 0.0f : 1.0f) + this.mAnimProgress);
        if (this.mIsRtl) {
            canvas.scale(-1.0f, 1.0f, this.mDrawBound.centerX(), this.mDrawBound.centerY());
        }
        canvas.rotate(degrees, this.mDrawBound.centerX(), this.mDrawBound.centerY());
        canvas.drawPath(this.mPath, this.mPaint);
        canvas.restoreToCount(restoreCount);
    }

    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.mPaint.setColorFilter(cf);
    }

    public int getOpacity() {
        return -3;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        if (this.mWidth <= 0 || this.mHeight <= 0) {
            this.mDrawBound.left = (float) (bounds.left + this.mPaddingLeft);
            this.mDrawBound.top = (float) (bounds.top + this.mPaddingTop);
            this.mDrawBound.right = (float) (bounds.right - this.mPaddingRight);
            this.mDrawBound.bottom = (float) (bounds.bottom - this.mPaddingBottom);
        } else {
            this.mDrawBound.left = ((float) bounds.left) + (((float) (bounds.width() - this.mWidth)) / 2.0f);
            this.mDrawBound.top = ((float) bounds.top) + (((float) (bounds.height() - this.mHeight)) / 2.0f);
            RectF rectF = this.mDrawBound;
            rectF.right = rectF.left + ((float) this.mWidth);
            RectF rectF2 = this.mDrawBound;
            rectF2.bottom = rectF2.top + ((float) this.mHeight);
        }
        updatePath();
    }

    public void switchLineState(int state, boolean animation) {
        int i = this.mCurState;
        if (i != state) {
            this.mPrevState = i;
            this.mCurState = state;
            if (animation) {
                start();
                return;
            }
            this.mAnimProgress = 1.0f;
            updatePath();
        } else if (!animation) {
            this.mAnimProgress = 1.0f;
            updatePath();
        }
    }

    public boolean setLineState(int state, float progress) {
        int i = this.mCurState;
        if (i != state) {
            this.mPrevState = i;
            this.mCurState = state;
            this.mAnimProgress = progress;
            updatePath();
            return true;
        } else if (this.mAnimProgress == progress) {
            return false;
        } else {
            this.mAnimProgress = progress;
            updatePath();
            return true;
        }
    }

    public int getLineState() {
        return this.mCurState;
    }

    public int getLineStateCount() {
        State[] stateArr = this.mStates;
        if (stateArr == null) {
            return 0;
        }
        return stateArr.length;
    }

    public float getAnimProgress() {
        return this.mAnimProgress;
    }

    private void updatePath() {
        this.mPath.reset();
        State[] stateArr = this.mStates;
        if (stateArr != null) {
            if (this.mAnimProgress == 0.0f || (stateArr[this.mPrevState].links != null && this.mAnimProgress < 0.05f)) {
                updatePathWithState(this.mPath, this.mStates[this.mPrevState]);
            } else if (this.mAnimProgress == 1.0f || (this.mStates[this.mCurState].links != null && this.mAnimProgress > 0.95f)) {
                updatePathWithState(this.mPath, this.mStates[this.mCurState]);
            } else {
                Path path = this.mPath;
                State[] stateArr2 = this.mStates;
                updatePathBetweenStates(path, stateArr2[this.mPrevState], stateArr2[this.mCurState], this.mInterpolator.getInterpolation(this.mAnimProgress));
            }
            invalidateSelf();
        }
    }

    private void updatePathWithState(Path path, State state) {
        if (state.links != null) {
            for (int i = 0; i < state.links.length; i += 2) {
                int index1 = state.links[i] * 4;
                int index2 = state.links[i + 1] * 4;
                float x1 = getX(state.points[index1]);
                float y1 = getY(state.points[index1 + 1]);
                float x2 = getX(state.points[index1 + 2]);
                float y2 = getY(state.points[index1 + 3]);
                float x3 = getX(state.points[index2]);
                float y3 = getY(state.points[index2 + 1]);
                float x4 = getX(state.points[index2 + 2]);
                float y4 = getY(state.points[index2 + 3]);
                if (x1 == x3 && y1 == y3) {
                    path.moveTo(x2, y2);
                    path.lineTo(x1, y1);
                    path.lineTo(x4, y4);
                } else if (x1 == x4 && y1 == y4) {
                    path.moveTo(x2, y2);
                    path.lineTo(x1, y1);
                    path.lineTo(x3, y3);
                } else if (x2 == x3 && y2 == y3) {
                    path.moveTo(x1, y1);
                    path.lineTo(x2, y2);
                    path.lineTo(x4, y4);
                } else {
                    path.moveTo(x1, y1);
                    path.lineTo(x2, y2);
                    path.lineTo(x3, y3);
                }
            }
            int count = state.points.length / 4;
            for (int i2 = 0; i2 < count; i2++) {
                boolean exist = false;
                int j = 0;
                while (true) {
                    if (j >= state.links.length) {
                        break;
                    } else if (state.links[j] == i2) {
                        exist = true;
                        break;
                    } else {
                        j++;
                    }
                }
                if (!exist) {
                    int index = i2 * 4;
                    path.moveTo(getX(state.points[index]), getY(state.points[index + 1]));
                    path.lineTo(getX(state.points[index + 2]), getY(state.points[index + 3]));
                }
            }
            return;
        }
        int count2 = state.points.length / 4;
        for (int i3 = 0; i3 < count2; i3++) {
            int index3 = i3 * 4;
            path.moveTo(getX(state.points[index3]), getY(state.points[index3 + 1]));
            path.lineTo(getX(state.points[index3 + 2]), getY(state.points[index3 + 3]));
        }
    }

    private void updatePathBetweenStates(Path path, State prev, State cur, float progress) {
        float y2;
        float x2;
        float y1;
        float x1;
        float y4;
        float x4;
        float y3;
        float x3;
        State state = prev;
        State state2 = cur;
        int count = Math.max(state.points.length, state2.points.length) / 4;
        int i = 0;
        while (i < count) {
            int index = i * 4;
            if (index >= state.points.length) {
                x1 = 0.5f;
                y1 = 0.5f;
                x2 = 0.5f;
                y2 = 0.5f;
            } else {
                x1 = state.points[index];
                y1 = state.points[index + 1];
                x2 = state.points[index + 2];
                y2 = state.points[index + 3];
            }
            if (index >= state2.points.length) {
                x3 = 0.5f;
                y3 = 0.5f;
                x4 = 0.5f;
                y4 = 0.5f;
            } else {
                x3 = state2.points[index];
                y3 = state2.points[index + 1];
                x4 = state2.points[index + 2];
                y4 = state2.points[index + 3];
            }
            this.mPath.moveTo(getX(((x3 - x1) * progress) + x1), getY(y1 + ((y3 - y1) * progress)));
            this.mPath.lineTo(getX(((x4 - x2) * progress) + x2), getY(((y4 - y2) * progress) + y2));
            i++;
            state = prev;
        }
    }

    private float getX(float value) {
        return this.mDrawBound.left + (this.mDrawBound.width() * value);
    }

    private float getY(float value) {
        return this.mDrawBound.top + (this.mDrawBound.height() * value);
    }

    private void resetAnimation() {
        this.mStartTime = SystemClock.uptimeMillis();
        this.mAnimProgress = 0.0f;
    }

    public void cancel() {
        stop();
        setLineState(this.mCurState, 1.0f);
    }

    public void start() {
        resetAnimation();
        scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
        invalidateSelf();
    }

    public void stop() {
        if (isRunning()) {
            this.mRunning = false;
            unscheduleSelf(this.mUpdater);
            invalidateSelf();
        }
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    public void scheduleSelf(Runnable what, long when) {
        this.mRunning = true;
        super.scheduleSelf(what, when);
    }

    /* access modifiers changed from: private */
    public void update() {
        float value = Math.min(1.0f, ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / ((float) this.mAnimDuration));
        if (value == 1.0f) {
            setLineState(this.mCurState, 1.0f);
            this.mRunning = false;
        } else {
            setLineState(this.mCurState, this.mInterpolator.getInterpolation(value));
        }
        if (isRunning()) {
            scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
        }
    }

    public static class State {
        int[] links;
        float[] points;

        public State() {
        }

        public State(float[] points2, int[] links2) {
            this.points = points2;
            this.links = links2;
        }
    }

    public static class Builder {
        private static final String TAG_ITEM = "item";
        private static final String TAG_LINKS = "links";
        private static final String TAG_POINTS = "points";
        private static final String TAG_STATE = "state";
        private static final String TAG_STATE_LIST = "state-list";
        private int mAnimDuration;
        private boolean mClockwise;
        private int mCurState;
        private int mHeight;
        private Interpolator mInterpolator;
        private boolean mIsRtl;
        private int mPaddingBottom;
        private int mPaddingLeft;
        private int mPaddingRight;
        private int mPaddingTop;
        private State[] mStates;
        private Paint.Cap mStrokeCap;
        private int mStrokeColor;
        private Paint.Join mStrokeJoin;
        private int mStrokeSize;
        private int mWidth;

        public Builder() {
        }

        public Builder(Context context, int defStyleRes) {
            this(context, (AttributeSet) null, 0, defStyleRes);
        }

        public Builder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            TypedArray a = context.obtainStyledAttributes(attrs, C2500R.styleable.LineMorphingDrawable, defStyleAttr, defStyleRes);
            boolean z = false;
            int resourceId = a.getResourceId(C2500R.styleable.LineMorphingDrawable_lmd_state, 0);
            int resId = resourceId;
            if (resourceId != 0) {
                states(readStates(context, resId));
            }
            curState(a.getInteger(C2500R.styleable.LineMorphingDrawable_lmd_curState, 0));
            width(a.getDimensionPixelSize(C2500R.styleable.LineMorphingDrawable_lmd_width, 0));
            height(a.getDimensionPixelSize(C2500R.styleable.LineMorphingDrawable_lmd_height, 0));
            padding(a.getDimensionPixelSize(C2500R.styleable.LineMorphingDrawable_lmd_padding, 0));
            paddingLeft(a.getDimensionPixelSize(C2500R.styleable.LineMorphingDrawable_lmd_paddingLeft, this.mPaddingLeft));
            paddingTop(a.getDimensionPixelSize(C2500R.styleable.LineMorphingDrawable_lmd_paddingTop, this.mPaddingTop));
            paddingRight(a.getDimensionPixelSize(C2500R.styleable.LineMorphingDrawable_lmd_paddingRight, this.mPaddingRight));
            paddingBottom(a.getDimensionPixelSize(C2500R.styleable.LineMorphingDrawable_lmd_paddingBottom, this.mPaddingBottom));
            animDuration(a.getInteger(C2500R.styleable.LineMorphingDrawable_lmd_animDuration, context.getResources().getInteger(17694721)));
            int resourceId2 = a.getResourceId(C2500R.styleable.LineMorphingDrawable_lmd_interpolator, 0);
            int resId2 = resourceId2;
            if (resourceId2 != 0) {
                interpolator(AnimationUtils.loadInterpolator(context, resId2));
            }
            strokeSize(a.getDimensionPixelSize(C2500R.styleable.LineMorphingDrawable_lmd_strokeSize, ThemeUtil.dpToPx(context, 3)));
            strokeColor(a.getColor(C2500R.styleable.LineMorphingDrawable_lmd_strokeColor, -1));
            int cap = a.getInteger(C2500R.styleable.LineMorphingDrawable_lmd_strokeCap, 0);
            if (cap == 0) {
                strokeCap(Paint.Cap.BUTT);
            } else if (cap == 1) {
                strokeCap(Paint.Cap.ROUND);
            } else {
                strokeCap(Paint.Cap.SQUARE);
            }
            int join = a.getInteger(C2500R.styleable.LineMorphingDrawable_lmd_strokeJoin, 0);
            if (join == 0) {
                strokeJoin(Paint.Join.MITER);
            } else if (join == 1) {
                strokeJoin(Paint.Join.ROUND);
            } else {
                strokeJoin(Paint.Join.BEVEL);
            }
            clockwise(a.getBoolean(C2500R.styleable.LineMorphingDrawable_lmd_clockwise, true));
            int direction = a.getInteger(C2500R.styleable.LineMorphingDrawable_lmd_layoutDirection, 0);
            if (direction == 3) {
                rtl(TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1 ? true : z);
            } else {
                rtl(direction == 1 ? true : z);
            }
            a.recycle();
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:103:0x018a, code lost:
            if (r1 != null) goto L_0x018c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:104:0x018c, code lost:
            r1.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:111:0x019c, code lost:
            if (r1 == null) goto L_0x019f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:113:0x01a3, code lost:
            if (r2.isEmpty() == false) goto L_0x01a7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:114:0x01a5, code lost:
            return null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:116:0x01b3, code lost:
            return (com.rey.material.drawable.LineMorphingDrawable.State[]) r2.toArray(new com.rey.material.drawable.LineMorphingDrawable.State[r2.size()]);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0046, code lost:
            throw new java.lang.RuntimeException("Expecting menu, got " + r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            r9 = r1.getName();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0026, code lost:
            if (r9.equals(TAG_STATE_LIST) == false) goto L_0x002e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0028, code lost:
            r0 = r1.next();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private com.rey.material.drawable.LineMorphingDrawable.State[] readStates(android.content.Context r20, int r21) {
            /*
                r19 = this;
                r1 = 0
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                r2 = r0
                android.content.res.Resources r0 = r20.getResources()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r3 = r21
                android.content.res.XmlResourceParser r0 = r0.getXml(r3)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r1 = r0
                int r0 = r1.getEventType()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r4 = 0
                r5 = 0
            L_0x0018:
                java.lang.String r6 = "state-list"
                r7 = 2
                r8 = 1
                if (r0 != r7) goto L_0x0047
                java.lang.String r9 = r1.getName()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                boolean r10 = r9.equals(r6)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                if (r10 == 0) goto L_0x002e
                int r10 = r1.next()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r0 = r10
                goto L_0x004e
            L_0x002e:
                java.lang.RuntimeException r6 = new java.lang.RuntimeException     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r7.<init>()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                java.lang.String r8 = "Expecting menu, got "
                java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                java.lang.StringBuilder r7 = r7.append(r9)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r6.<init>(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                throw r6     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
            L_0x0047:
                int r9 = r1.next()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r0 = r9
                if (r0 != r8) goto L_0x0190
            L_0x004e:
                r9 = 0
                r10 = 0
                java.util.ArrayList r11 = new java.util.ArrayList     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r11.<init>()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r12.<init>()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
            L_0x005a:
                if (r9 != 0) goto L_0x018a
                if (r0 == r8) goto L_0x017b
                java.lang.String r13 = "state"
                java.lang.String r14 = "links"
                java.lang.String r15 = "item"
                java.lang.String r8 = "points"
                r16 = -1
                r3 = 3
                if (r0 == r7) goto L_0x0122
                r7 = 4
                if (r0 == r3) goto L_0x007b
                if (r0 == r7) goto L_0x0072
                goto L_0x0125
            L_0x0072:
                java.lang.String r3 = r1.getText()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r12.append(r3)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                goto L_0x0125
            L_0x007b:
                java.lang.String r17 = r1.getName()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r18 = r17
                if (r4 == 0) goto L_0x008e
                r7 = r18
                boolean r18 = r7.equals(r5)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                if (r18 == 0) goto L_0x0090
                r4 = 0
                r5 = 0
                goto L_0x0090
            L_0x008e:
                r7 = r18
            L_0x0090:
                int r18 = r7.hashCode()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                switch(r18) {
                    case -982754077: goto L_0x00b8;
                    case -273989542: goto L_0x00b0;
                    case 3242771: goto L_0x00a8;
                    case 102977465: goto L_0x00a0;
                    case 109757585: goto L_0x0098;
                    default: goto L_0x0097;
                }     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
            L_0x0097:
                goto L_0x00c0
            L_0x0098:
                boolean r8 = r7.equals(r13)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                if (r8 == 0) goto L_0x0097
                r8 = 1
                goto L_0x00c2
            L_0x00a0:
                boolean r8 = r7.equals(r14)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                if (r8 == 0) goto L_0x0097
                r8 = r3
                goto L_0x00c2
            L_0x00a8:
                boolean r8 = r7.equals(r15)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                if (r8 == 0) goto L_0x0097
                r8 = 4
                goto L_0x00c2
            L_0x00b0:
                boolean r8 = r7.equals(r6)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                if (r8 == 0) goto L_0x0097
                r8 = 0
                goto L_0x00c2
            L_0x00b8:
                boolean r8 = r7.equals(r8)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                if (r8 == 0) goto L_0x0097
                r8 = 2
                goto L_0x00c2
            L_0x00c0:
                r8 = r16
            L_0x00c2:
                if (r8 == 0) goto L_0x011c
                r13 = 1
                if (r8 == r13) goto L_0x0118
                r13 = 2
                if (r8 == r13) goto L_0x00f8
                if (r8 == r3) goto L_0x00d8
                r3 = 4
                if (r8 == r3) goto L_0x00d0
                goto L_0x011e
            L_0x00d0:
                java.lang.String r3 = r12.toString()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r11.add(r3)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                goto L_0x011e
            L_0x00d8:
                int r3 = r11.size()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                int[] r3 = new int[r3]     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r10.links = r3     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r3 = 0
            L_0x00e1:
                int[] r8 = r10.links     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                int r8 = r8.length     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                if (r3 >= r8) goto L_0x00f7
                int[] r8 = r10.links     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                java.lang.Object r13 = r11.get(r3)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                java.lang.String r13 = (java.lang.String) r13     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                int r13 = java.lang.Integer.parseInt(r13)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r8[r3] = r13     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                int r3 = r3 + 1
                goto L_0x00e1
            L_0x00f7:
                goto L_0x011e
            L_0x00f8:
                int r3 = r11.size()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                float[] r3 = new float[r3]     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r10.points = r3     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r3 = 0
            L_0x0101:
                float[] r8 = r10.points     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                int r8 = r8.length     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                if (r3 >= r8) goto L_0x0117
                float[] r8 = r10.points     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                java.lang.Object r13 = r11.get(r3)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                java.lang.String r13 = (java.lang.String) r13     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                float r13 = java.lang.Float.parseFloat(r13)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r8[r3] = r13     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                int r3 = r3 + 1
                goto L_0x0101
            L_0x0117:
                goto L_0x011e
            L_0x0118:
                r2.add(r10)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                goto L_0x011e
            L_0x011c:
                r9 = 1
            L_0x011e:
                r13 = 1
                r14 = 2
                goto L_0x017f
            L_0x0122:
                if (r4 == 0) goto L_0x0129
            L_0x0125:
                r13 = 1
                r14 = 2
                goto L_0x017f
            L_0x0129:
                java.lang.String r7 = r1.getName()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                int r17 = r7.hashCode()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                switch(r17) {
                    case -982754077: goto L_0x014d;
                    case 3242771: goto L_0x0145;
                    case 102977465: goto L_0x013d;
                    case 109757585: goto L_0x0135;
                    default: goto L_0x0134;
                }     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
            L_0x0134:
                goto L_0x0155
            L_0x0135:
                boolean r8 = r7.equals(r13)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                if (r8 == 0) goto L_0x0134
                r8 = 0
                goto L_0x0157
            L_0x013d:
                boolean r8 = r7.equals(r14)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                if (r8 == 0) goto L_0x0134
                r8 = 2
                goto L_0x0157
            L_0x0145:
                boolean r8 = r7.equals(r15)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                if (r8 == 0) goto L_0x0134
                r8 = r3
                goto L_0x0157
            L_0x014d:
                boolean r8 = r7.equals(r8)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                if (r8 == 0) goto L_0x0134
                r8 = 1
                goto L_0x0157
            L_0x0155:
                r8 = r16
            L_0x0157:
                if (r8 == 0) goto L_0x0171
                r13 = 1
                r14 = 2
                if (r8 == r13) goto L_0x016d
                if (r8 == r14) goto L_0x016d
                if (r8 == r3) goto L_0x0164
                r4 = 1
                r5 = r7
                goto L_0x017a
            L_0x0164:
                int r3 = r12.length()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r8 = 0
                r12.delete(r8, r3)     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                goto L_0x017a
            L_0x016d:
                r11.clear()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                goto L_0x017a
            L_0x0171:
                r13 = 1
                r14 = 2
                com.rey.material.drawable.LineMorphingDrawable$State r3 = new com.rey.material.drawable.LineMorphingDrawable$State     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r3.<init>()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r10 = r3
            L_0x017a:
                goto L_0x017f
            L_0x017b:
                r14 = r7
                r13 = r8
                r3 = 1
                r9 = r3
            L_0x017f:
                int r3 = r1.next()     // Catch:{ Exception -> 0x019b, all -> 0x0194 }
                r0 = r3
                r3 = r21
                r8 = r13
                r7 = r14
                goto L_0x005a
            L_0x018a:
                if (r1 == 0) goto L_0x019f
            L_0x018c:
                r1.close()
                goto L_0x019f
            L_0x0190:
                r3 = r21
                goto L_0x0018
            L_0x0194:
                r0 = move-exception
                if (r1 == 0) goto L_0x019a
                r1.close()
            L_0x019a:
                throw r0
            L_0x019b:
                r0 = move-exception
                if (r1 == 0) goto L_0x019f
                goto L_0x018c
            L_0x019f:
                boolean r0 = r2.isEmpty()
                if (r0 == 0) goto L_0x01a7
                r0 = 0
                return r0
            L_0x01a7:
                int r0 = r2.size()
                com.rey.material.drawable.LineMorphingDrawable$State[] r0 = new com.rey.material.drawable.LineMorphingDrawable.State[r0]
                java.lang.Object[] r0 = r2.toArray(r0)
                com.rey.material.drawable.LineMorphingDrawable$State[] r0 = (com.rey.material.drawable.LineMorphingDrawable.State[]) r0
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.rey.material.drawable.LineMorphingDrawable.Builder.readStates(android.content.Context, int):com.rey.material.drawable.LineMorphingDrawable$State[]");
        }

        public LineMorphingDrawable build() {
            if (this.mStrokeCap == null) {
                this.mStrokeCap = Paint.Cap.BUTT;
            }
            if (this.mStrokeJoin == null) {
                this.mStrokeJoin = Paint.Join.MITER;
            }
            if (this.mInterpolator == null) {
                this.mInterpolator = new AccelerateInterpolator();
            }
            LineMorphingDrawable lineMorphingDrawable = r2;
            LineMorphingDrawable lineMorphingDrawable2 = new LineMorphingDrawable(this.mStates, this.mCurState, this.mWidth, this.mHeight, this.mPaddingLeft, this.mPaddingTop, this.mPaddingRight, this.mPaddingBottom, this.mAnimDuration, this.mInterpolator, this.mStrokeSize, this.mStrokeColor, this.mStrokeCap, this.mStrokeJoin, this.mClockwise, this.mIsRtl);
            return lineMorphingDrawable;
        }

        public Builder states(State... states) {
            this.mStates = states;
            return this;
        }

        public Builder curState(int state) {
            this.mCurState = state;
            return this;
        }

        public Builder width(int width) {
            this.mWidth = width;
            return this;
        }

        public Builder height(int height) {
            this.mHeight = height;
            return this;
        }

        public Builder padding(int padding) {
            this.mPaddingLeft = padding;
            this.mPaddingTop = padding;
            this.mPaddingRight = padding;
            this.mPaddingBottom = padding;
            return this;
        }

        public Builder paddingLeft(int padding) {
            this.mPaddingLeft = padding;
            return this;
        }

        public Builder paddingTop(int padding) {
            this.mPaddingTop = padding;
            return this;
        }

        public Builder paddingRight(int padding) {
            this.mPaddingRight = padding;
            return this;
        }

        public Builder paddingBottom(int padding) {
            this.mPaddingBottom = padding;
            return this;
        }

        public Builder animDuration(int duration) {
            this.mAnimDuration = duration;
            return this;
        }

        public Builder interpolator(Interpolator interpolator) {
            this.mInterpolator = interpolator;
            return this;
        }

        public Builder strokeSize(int size) {
            this.mStrokeSize = size;
            return this;
        }

        public Builder strokeColor(int strokeColor) {
            this.mStrokeColor = strokeColor;
            return this;
        }

        public Builder strokeCap(Paint.Cap cap) {
            this.mStrokeCap = cap;
            return this;
        }

        public Builder strokeJoin(Paint.Join join) {
            this.mStrokeJoin = join;
            return this;
        }

        public Builder clockwise(boolean clockwise) {
            this.mClockwise = clockwise;
            return this;
        }

        public Builder rtl(boolean rtl) {
            this.mIsRtl = rtl;
            return this;
        }
    }
}
