package com.facebook.drawee.debug;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.facebook.drawee.debug.listener.ImageLoadingTimeListener;
import com.facebook.drawee.drawable.ScalingUtils;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

public class DebugControllerOverlayDrawable extends Drawable implements ImageLoadingTimeListener {
    private static final float IMAGE_SIZE_THRESHOLD_NOT_OK = 0.5f;
    private static final float IMAGE_SIZE_THRESHOLD_OK = 0.1f;
    private static final int MAX_LINE_WIDTH_EM = 8;
    private static final int MAX_NUMBER_OF_LINES = 9;
    private static final int MAX_TEXT_SIZE_PX = 40;
    private static final int MIN_TEXT_SIZE_PX = 10;
    private static final String NO_CONTROLLER_ID = "none";
    private static final int OUTLINE_COLOR = -26624;
    private static final int OUTLINE_STROKE_WIDTH_PX = 2;
    private static final int TEXT_BACKGROUND_COLOR = 1711276032;
    private static final int TEXT_COLOR = -1;
    static final int TEXT_COLOR_IMAGE_ALMOST_OK = -256;
    static final int TEXT_COLOR_IMAGE_NOT_OK = -65536;
    static final int TEXT_COLOR_IMAGE_OK = -16711936;
    private static final int TEXT_LINE_SPACING_PX = 8;
    private static final int TEXT_PADDING_PX = 10;
    private HashMap<String, String> mAdditionalData = new HashMap<>();
    private String mControllerId;
    private int mCurrentTextXPx;
    private int mCurrentTextYPx;
    private long mFinalImageTimeMs;
    private int mFrameCount;
    private int mHeightPx;
    private String mImageFormat;
    private String mImageId;
    private int mImageSizeBytes;
    private int mLineIncrementPx;
    private int mLoopCount;
    private final Matrix mMatrix = new Matrix();
    private int mOriginColor = -1;
    private String mOriginText;
    private final Paint mPaint = new Paint(1);
    private final Rect mRect = new Rect();
    private final RectF mRectF = new RectF();
    private ScalingUtils.ScaleType mScaleType;
    private int mStartTextXPx;
    private int mStartTextYPx;
    private int mTextGravity = 80;
    private int mWidthPx;

    public DebugControllerOverlayDrawable() {
        reset();
    }

    public void reset() {
        this.mWidthPx = -1;
        this.mHeightPx = -1;
        this.mImageSizeBytes = -1;
        this.mAdditionalData = new HashMap<>();
        this.mFrameCount = -1;
        this.mLoopCount = -1;
        this.mImageFormat = null;
        setControllerId((String) null);
        this.mFinalImageTimeMs = -1;
        this.mOriginText = null;
        this.mOriginColor = -1;
        invalidateSelf();
    }

    public void setTextGravity(int textGravity) {
        this.mTextGravity = textGravity;
        invalidateSelf();
    }

    public void setControllerId(@Nullable String controllerId) {
        this.mControllerId = controllerId != null ? controllerId : "none";
        invalidateSelf();
    }

    public void setImageId(@Nullable String imageId) {
        this.mImageId = imageId;
        invalidateSelf();
    }

    public void setDimensions(int widthPx, int heightPx) {
        this.mWidthPx = widthPx;
        this.mHeightPx = heightPx;
        invalidateSelf();
    }

    public void setAnimationInfo(int frameCount, int loopCount) {
        this.mFrameCount = frameCount;
        this.mLoopCount = loopCount;
        invalidateSelf();
    }

    public void setOrigin(String text, int color) {
        this.mOriginText = text;
        this.mOriginColor = color;
        invalidateSelf();
    }

    public void setImageSize(int imageSizeBytes) {
        this.mImageSizeBytes = imageSizeBytes;
    }

    public void addAdditionalData(String key, String value) {
        this.mAdditionalData.put(key, value);
    }

    public void setImageFormat(@Nullable String imageFormat) {
        this.mImageFormat = imageFormat;
    }

    public void setScaleType(ScalingUtils.ScaleType scaleType) {
        this.mScaleType = scaleType;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        prepareDebugTextParameters(bounds, 9, 8);
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(2.0f);
        this.mPaint.setColor(OUTLINE_COLOR);
        canvas.drawRect((float) bounds.left, (float) bounds.top, (float) bounds.right, (float) bounds.bottom, this.mPaint);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setStrokeWidth(0.0f);
        this.mPaint.setColor(-1);
        this.mCurrentTextXPx = this.mStartTextXPx;
        this.mCurrentTextYPx = this.mStartTextYPx;
        String str = this.mImageId;
        if (str != null) {
            addDebugText(canvas, "IDs", format("%s, %s", this.mControllerId, str));
        } else {
            addDebugText(canvas, "ID", this.mControllerId);
        }
        addDebugText(canvas, "D", format("%dx%d", Integer.valueOf(bounds.width()), Integer.valueOf(bounds.height())));
        addDebugText(canvas, "I", format("%dx%d", Integer.valueOf(this.mWidthPx), Integer.valueOf(this.mHeightPx)), determineSizeHintColor(this.mWidthPx, this.mHeightPx, this.mScaleType));
        addDebugText(canvas, "I", format("%d KiB", Integer.valueOf(this.mImageSizeBytes / 1024)));
        String str2 = this.mImageFormat;
        if (str2 != null) {
            addDebugText(canvas, "i format", str2);
        }
        int i = this.mFrameCount;
        if (i > 0) {
            addDebugText(canvas, "anim", format("f %d, l %d", Integer.valueOf(i), Integer.valueOf(this.mLoopCount)));
        }
        ScalingUtils.ScaleType scaleType = this.mScaleType;
        if (scaleType != null) {
            addDebugText(canvas, "scale", (Object) scaleType);
        }
        long j = this.mFinalImageTimeMs;
        if (j >= 0) {
            addDebugText(canvas, "t", format("%d ms", Long.valueOf(j)));
        }
        String str3 = this.mOriginText;
        if (str3 != null) {
            addDebugText(canvas, "origin", str3, this.mOriginColor);
        }
        for (Map.Entry<String, String> entry : this.mAdditionalData.entrySet()) {
            addDebugText(canvas, entry.getKey(), entry.getValue());
        }
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return -3;
    }

    private void prepareDebugTextParameters(Rect bounds, int numberOfLines, int maxLineLengthEm) {
        int textSizePx = Math.min(40, Math.max(10, Math.min(bounds.width() / maxLineLengthEm, bounds.height() / numberOfLines)));
        this.mPaint.setTextSize((float) textSizePx);
        int i = textSizePx + 8;
        this.mLineIncrementPx = i;
        if (this.mTextGravity == 80) {
            this.mLineIncrementPx = i * -1;
        }
        this.mStartTextXPx = bounds.left + 10;
        this.mStartTextYPx = this.mTextGravity == 80 ? bounds.bottom - 10 : bounds.top + 10 + 10;
    }

    private static String format(String text, @Nullable Object... args) {
        return args == null ? text : String.format(Locale.US, text, args);
    }

    private void addDebugText(Canvas canvas, String label, Object value) {
        addDebugText(canvas, label, String.valueOf(value), -1);
    }

    private void addDebugText(Canvas canvas, String label, String value) {
        addDebugText(canvas, label, value, -1);
    }

    private void addDebugText(Canvas canvas, String label, String value, int valueColor) {
        Canvas canvas2 = canvas;
        String str = value;
        String labelColon = label + ": ";
        float labelWidth = this.mPaint.measureText(labelColon);
        float valueWidth = this.mPaint.measureText(str);
        this.mPaint.setColor(TEXT_BACKGROUND_COLOR);
        int i = this.mCurrentTextXPx;
        int i2 = this.mCurrentTextYPx;
        canvas.drawRect((float) (i - 4), (float) (i2 + 8), 4.0f + ((float) i) + labelWidth + valueWidth, (float) (i2 + this.mLineIncrementPx + 8), this.mPaint);
        this.mPaint.setColor(-1);
        canvas2.drawText(labelColon, (float) this.mCurrentTextXPx, (float) this.mCurrentTextYPx, this.mPaint);
        this.mPaint.setColor(valueColor);
        canvas2.drawText(str, ((float) this.mCurrentTextXPx) + labelWidth, (float) this.mCurrentTextYPx, this.mPaint);
        this.mCurrentTextYPx += this.mLineIncrementPx;
    }

    /* access modifiers changed from: package-private */
    public int determineSizeHintColor(int imageWidth, int imageHeight, @Nullable ScalingUtils.ScaleType scaleType) {
        int visibleDrawnAreaWidth = getBounds().width();
        int visibleDrawnAreaHeight = getBounds().height();
        if (visibleDrawnAreaWidth <= 0 || visibleDrawnAreaHeight <= 0 || imageWidth <= 0 || imageHeight <= 0) {
            return -65536;
        }
        if (scaleType != null) {
            Rect rect = this.mRect;
            rect.top = 0;
            rect.left = 0;
            this.mRect.right = visibleDrawnAreaWidth;
            this.mRect.bottom = visibleDrawnAreaHeight;
            this.mMatrix.reset();
            scaleType.getTransform(this.mMatrix, this.mRect, imageWidth, imageHeight, 0.0f, 0.0f);
            RectF rectF = this.mRectF;
            rectF.top = 0.0f;
            rectF.left = 0.0f;
            this.mRectF.right = (float) imageWidth;
            this.mRectF.bottom = (float) imageHeight;
            this.mMatrix.mapRect(this.mRectF);
            visibleDrawnAreaWidth = Math.min(visibleDrawnAreaWidth, (int) this.mRectF.width());
            visibleDrawnAreaHeight = Math.min(visibleDrawnAreaHeight, (int) this.mRectF.height());
        }
        float scaledImageWidthThresholdOk = ((float) visibleDrawnAreaWidth) * IMAGE_SIZE_THRESHOLD_OK;
        float scaledImageWidthThresholdNotOk = ((float) visibleDrawnAreaWidth) * IMAGE_SIZE_THRESHOLD_NOT_OK;
        float scaledImageHeightThresholdOk = ((float) visibleDrawnAreaHeight) * IMAGE_SIZE_THRESHOLD_OK;
        float scaledImageHeightThresholdNotOk = ((float) visibleDrawnAreaHeight) * IMAGE_SIZE_THRESHOLD_NOT_OK;
        int absWidthDifference = Math.abs(imageWidth - visibleDrawnAreaWidth);
        int absHeightDifference = Math.abs(imageHeight - visibleDrawnAreaHeight);
        if (((float) absWidthDifference) < scaledImageWidthThresholdOk && ((float) absHeightDifference) < scaledImageHeightThresholdOk) {
            return TEXT_COLOR_IMAGE_OK;
        }
        if (((float) absWidthDifference) >= scaledImageWidthThresholdNotOk || ((float) absHeightDifference) >= scaledImageHeightThresholdNotOk) {
            return -65536;
        }
        return -256;
    }

    public void setFinalImageTimeMs(long finalImageTimeMs) {
        this.mFinalImageTimeMs = finalImageTimeMs;
    }

    public void onFinalImageSet(long finalImageTimeMs) {
        this.mFinalImageTimeMs = finalImageTimeMs;
        invalidateSelf();
    }
}
