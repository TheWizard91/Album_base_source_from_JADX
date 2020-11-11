package com.theartofdev.edmodo.cropper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.exifinterface.media.ExifInterface;
import com.theartofdev.edmodo.cropper.BitmapCroppingWorkerTask;
import com.theartofdev.edmodo.cropper.BitmapLoadingWorkerTask;
import com.theartofdev.edmodo.cropper.BitmapUtils;
import java.lang.ref.WeakReference;
import java.util.UUID;

public class CropImageView extends FrameLayout {
    private CropImageAnimation mAnimation;
    private boolean mAutoZoomEnabled;
    private Bitmap mBitmap;
    private WeakReference<BitmapCroppingWorkerTask> mBitmapCroppingWorkerTask;
    private WeakReference<BitmapLoadingWorkerTask> mBitmapLoadingWorkerTask;
    private final CropOverlayView mCropOverlayView;
    private int mDegreesRotated;
    private boolean mFlipHorizontally;
    private boolean mFlipVertically;
    private final Matrix mImageInverseMatrix;
    private final Matrix mImageMatrix;
    private final float[] mImagePoints;
    private int mImageResource;
    private final ImageView mImageView;
    private int mInitialDegreesRotated;
    private int mLayoutHeight;
    private int mLayoutWidth;
    private Uri mLoadedImageUri;
    private int mLoadedSampleSize;
    private int mMaxZoom;
    private OnCropImageCompleteListener mOnCropImageCompleteListener;
    /* access modifiers changed from: private */
    public OnSetCropOverlayReleasedListener mOnCropOverlayReleasedListener;
    /* access modifiers changed from: private */
    public OnSetCropOverlayMovedListener mOnSetCropOverlayMovedListener;
    private OnSetCropWindowChangeListener mOnSetCropWindowChangeListener;
    private OnSetImageUriCompleteListener mOnSetImageUriCompleteListener;
    private final ProgressBar mProgressBar;
    private RectF mRestoreCropWindowRect;
    private int mRestoreDegreesRotated;
    private boolean mSaveBitmapToInstanceState;
    private Uri mSaveInstanceStateBitmapUri;
    private final float[] mScaleImagePoints;
    private ScaleType mScaleType;
    private boolean mShowCropOverlay;
    private boolean mShowProgressBar;
    private boolean mSizeChanged;
    private float mZoom;
    private float mZoomOffsetX;
    private float mZoomOffsetY;

    public enum CropShape {
        RECTANGLE,
        OVAL
    }

    public enum Guidelines {
        OFF,
        ON_TOUCH,
        ON
    }

    public interface OnCropImageCompleteListener {
        void onCropImageComplete(CropImageView cropImageView, CropResult cropResult);
    }

    public interface OnSetCropOverlayMovedListener {
        void onCropOverlayMoved(Rect rect);
    }

    public interface OnSetCropOverlayReleasedListener {
        void onCropOverlayReleased(Rect rect);
    }

    public interface OnSetCropWindowChangeListener {
        void onCropWindowChanged();
    }

    public interface OnSetImageUriCompleteListener {
        void onSetImageUriComplete(CropImageView cropImageView, Uri uri, Exception exc);
    }

    public enum RequestSizeOptions {
        NONE,
        SAMPLING,
        RESIZE_INSIDE,
        RESIZE_FIT,
        RESIZE_EXACT
    }

    public enum ScaleType {
        FIT_CENTER,
        CENTER,
        CENTER_CROP,
        CENTER_INSIDE
    }

    public CropImageView(Context context) {
        this(context, (AttributeSet) null);
    }

    /* JADX WARNING: type inference failed for: r5v45, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public CropImageView(android.content.Context r8, android.util.AttributeSet r9) {
        /*
            r7 = this;
            r7.<init>(r8, r9)
            android.graphics.Matrix r0 = new android.graphics.Matrix
            r0.<init>()
            r7.mImageMatrix = r0
            android.graphics.Matrix r0 = new android.graphics.Matrix
            r0.<init>()
            r7.mImageInverseMatrix = r0
            r0 = 8
            float[] r1 = new float[r0]
            r7.mImagePoints = r1
            float[] r0 = new float[r0]
            r7.mScaleImagePoints = r0
            r0 = 0
            r7.mSaveBitmapToInstanceState = r0
            r1 = 1
            r7.mShowCropOverlay = r1
            r7.mShowProgressBar = r1
            r7.mAutoZoomEnabled = r1
            r7.mLoadedSampleSize = r1
            r2 = 1065353216(0x3f800000, float:1.0)
            r7.mZoom = r2
            r2 = 0
            boolean r3 = r8 instanceof android.app.Activity
            if (r3 == 0) goto L_0x0038
            r3 = r8
            android.app.Activity r3 = (android.app.Activity) r3
            android.content.Intent r3 = r3.getIntent()
            goto L_0x0039
        L_0x0038:
            r3 = 0
        L_0x0039:
            if (r3 == 0) goto L_0x004c
            java.lang.String r4 = "CROP_IMAGE_EXTRA_BUNDLE"
            android.os.Bundle r4 = r3.getBundleExtra(r4)
            if (r4 == 0) goto L_0x004c
            java.lang.String r5 = "CROP_IMAGE_EXTRA_OPTIONS"
            android.os.Parcelable r5 = r4.getParcelable(r5)
            r2 = r5
            com.theartofdev.edmodo.cropper.CropImageOptions r2 = (com.theartofdev.edmodo.cropper.CropImageOptions) r2
        L_0x004c:
            if (r2 != 0) goto L_0x01f6
            com.theartofdev.edmodo.cropper.CropImageOptions r4 = new com.theartofdev.edmodo.cropper.CropImageOptions
            r4.<init>()
            r2 = r4
            if (r9 == 0) goto L_0x01f6
            int[] r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView
            android.content.res.TypedArray r0 = r8.obtainStyledAttributes(r9, r4, r0, r0)
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropFixAspectRatio     // Catch:{ all -> 0x01f1 }
            boolean r5 = r2.fixAspectRatio     // Catch:{ all -> 0x01f1 }
            boolean r4 = r0.getBoolean(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.fixAspectRatio = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropAspectRatioX     // Catch:{ all -> 0x01f1 }
            int r5 = r2.aspectRatioX     // Catch:{ all -> 0x01f1 }
            int r4 = r0.getInteger(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.aspectRatioX = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropAspectRatioY     // Catch:{ all -> 0x01f1 }
            int r5 = r2.aspectRatioY     // Catch:{ all -> 0x01f1 }
            int r4 = r0.getInteger(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.aspectRatioY = r4     // Catch:{ all -> 0x01f1 }
            com.theartofdev.edmodo.cropper.CropImageView$ScaleType[] r4 = com.theartofdev.edmodo.cropper.CropImageView.ScaleType.values()     // Catch:{ all -> 0x01f1 }
            int r5 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropScaleType     // Catch:{ all -> 0x01f1 }
            com.theartofdev.edmodo.cropper.CropImageView$ScaleType r6 = r2.scaleType     // Catch:{ all -> 0x01f1 }
            int r6 = r6.ordinal()     // Catch:{ all -> 0x01f1 }
            int r5 = r0.getInt(r5, r6)     // Catch:{ all -> 0x01f1 }
            r4 = r4[r5]     // Catch:{ all -> 0x01f1 }
            r2.scaleType = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropAutoZoomEnabled     // Catch:{ all -> 0x01f1 }
            boolean r5 = r2.autoZoomEnabled     // Catch:{ all -> 0x01f1 }
            boolean r4 = r0.getBoolean(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.autoZoomEnabled = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropMultiTouchEnabled     // Catch:{ all -> 0x01f1 }
            boolean r5 = r2.multiTouchEnabled     // Catch:{ all -> 0x01f1 }
            boolean r4 = r0.getBoolean(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.multiTouchEnabled = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropMaxZoom     // Catch:{ all -> 0x01f1 }
            int r5 = r2.maxZoom     // Catch:{ all -> 0x01f1 }
            int r4 = r0.getInteger(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.maxZoom = r4     // Catch:{ all -> 0x01f1 }
            com.theartofdev.edmodo.cropper.CropImageView$CropShape[] r4 = com.theartofdev.edmodo.cropper.CropImageView.CropShape.values()     // Catch:{ all -> 0x01f1 }
            int r5 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropShape     // Catch:{ all -> 0x01f1 }
            com.theartofdev.edmodo.cropper.CropImageView$CropShape r6 = r2.cropShape     // Catch:{ all -> 0x01f1 }
            int r6 = r6.ordinal()     // Catch:{ all -> 0x01f1 }
            int r5 = r0.getInt(r5, r6)     // Catch:{ all -> 0x01f1 }
            r4 = r4[r5]     // Catch:{ all -> 0x01f1 }
            r2.cropShape = r4     // Catch:{ all -> 0x01f1 }
            com.theartofdev.edmodo.cropper.CropImageView$Guidelines[] r4 = com.theartofdev.edmodo.cropper.CropImageView.Guidelines.values()     // Catch:{ all -> 0x01f1 }
            int r5 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropGuidelines     // Catch:{ all -> 0x01f1 }
            com.theartofdev.edmodo.cropper.CropImageView$Guidelines r6 = r2.guidelines     // Catch:{ all -> 0x01f1 }
            int r6 = r6.ordinal()     // Catch:{ all -> 0x01f1 }
            int r5 = r0.getInt(r5, r6)     // Catch:{ all -> 0x01f1 }
            r4 = r4[r5]     // Catch:{ all -> 0x01f1 }
            r2.guidelines = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropSnapRadius     // Catch:{ all -> 0x01f1 }
            float r5 = r2.snapRadius     // Catch:{ all -> 0x01f1 }
            float r4 = r0.getDimension(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.snapRadius = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropTouchRadius     // Catch:{ all -> 0x01f1 }
            float r5 = r2.touchRadius     // Catch:{ all -> 0x01f1 }
            float r4 = r0.getDimension(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.touchRadius = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropInitialCropWindowPaddingRatio     // Catch:{ all -> 0x01f1 }
            float r5 = r2.initialCropWindowPaddingRatio     // Catch:{ all -> 0x01f1 }
            float r4 = r0.getFloat(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.initialCropWindowPaddingRatio = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropBorderLineThickness     // Catch:{ all -> 0x01f1 }
            float r5 = r2.borderLineThickness     // Catch:{ all -> 0x01f1 }
            float r4 = r0.getDimension(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.borderLineThickness = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropBorderLineColor     // Catch:{ all -> 0x01f1 }
            int r5 = r2.borderLineColor     // Catch:{ all -> 0x01f1 }
            int r4 = r0.getInteger(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.borderLineColor = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropBorderCornerThickness     // Catch:{ all -> 0x01f1 }
            float r5 = r2.borderCornerThickness     // Catch:{ all -> 0x01f1 }
            float r4 = r0.getDimension(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.borderCornerThickness = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropBorderCornerOffset     // Catch:{ all -> 0x01f1 }
            float r5 = r2.borderCornerOffset     // Catch:{ all -> 0x01f1 }
            float r4 = r0.getDimension(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.borderCornerOffset = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropBorderCornerLength     // Catch:{ all -> 0x01f1 }
            float r5 = r2.borderCornerLength     // Catch:{ all -> 0x01f1 }
            float r4 = r0.getDimension(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.borderCornerLength = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropBorderCornerColor     // Catch:{ all -> 0x01f1 }
            int r5 = r2.borderCornerColor     // Catch:{ all -> 0x01f1 }
            int r4 = r0.getInteger(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.borderCornerColor = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropGuidelinesThickness     // Catch:{ all -> 0x01f1 }
            float r5 = r2.guidelinesThickness     // Catch:{ all -> 0x01f1 }
            float r4 = r0.getDimension(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.guidelinesThickness = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropGuidelinesColor     // Catch:{ all -> 0x01f1 }
            int r5 = r2.guidelinesColor     // Catch:{ all -> 0x01f1 }
            int r4 = r0.getInteger(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.guidelinesColor = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropBackgroundColor     // Catch:{ all -> 0x01f1 }
            int r5 = r2.backgroundColor     // Catch:{ all -> 0x01f1 }
            int r4 = r0.getInteger(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.backgroundColor = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropShowCropOverlay     // Catch:{ all -> 0x01f1 }
            boolean r5 = r7.mShowCropOverlay     // Catch:{ all -> 0x01f1 }
            boolean r4 = r0.getBoolean(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.showCropOverlay = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropShowProgressBar     // Catch:{ all -> 0x01f1 }
            boolean r5 = r7.mShowProgressBar     // Catch:{ all -> 0x01f1 }
            boolean r4 = r0.getBoolean(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.showProgressBar = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropBorderCornerThickness     // Catch:{ all -> 0x01f1 }
            float r5 = r2.borderCornerThickness     // Catch:{ all -> 0x01f1 }
            float r4 = r0.getDimension(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.borderCornerThickness = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropMinCropWindowWidth     // Catch:{ all -> 0x01f1 }
            int r5 = r2.minCropWindowWidth     // Catch:{ all -> 0x01f1 }
            float r5 = (float) r5     // Catch:{ all -> 0x01f1 }
            float r4 = r0.getDimension(r4, r5)     // Catch:{ all -> 0x01f1 }
            int r4 = (int) r4     // Catch:{ all -> 0x01f1 }
            r2.minCropWindowWidth = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropMinCropWindowHeight     // Catch:{ all -> 0x01f1 }
            int r5 = r2.minCropWindowHeight     // Catch:{ all -> 0x01f1 }
            float r5 = (float) r5     // Catch:{ all -> 0x01f1 }
            float r4 = r0.getDimension(r4, r5)     // Catch:{ all -> 0x01f1 }
            int r4 = (int) r4     // Catch:{ all -> 0x01f1 }
            r2.minCropWindowHeight = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropMinCropResultWidthPX     // Catch:{ all -> 0x01f1 }
            int r5 = r2.minCropResultWidth     // Catch:{ all -> 0x01f1 }
            float r5 = (float) r5     // Catch:{ all -> 0x01f1 }
            float r4 = r0.getFloat(r4, r5)     // Catch:{ all -> 0x01f1 }
            int r4 = (int) r4     // Catch:{ all -> 0x01f1 }
            r2.minCropResultWidth = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropMinCropResultHeightPX     // Catch:{ all -> 0x01f1 }
            int r5 = r2.minCropResultHeight     // Catch:{ all -> 0x01f1 }
            float r5 = (float) r5     // Catch:{ all -> 0x01f1 }
            float r4 = r0.getFloat(r4, r5)     // Catch:{ all -> 0x01f1 }
            int r4 = (int) r4     // Catch:{ all -> 0x01f1 }
            r2.minCropResultHeight = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropMaxCropResultWidthPX     // Catch:{ all -> 0x01f1 }
            int r5 = r2.maxCropResultWidth     // Catch:{ all -> 0x01f1 }
            float r5 = (float) r5     // Catch:{ all -> 0x01f1 }
            float r4 = r0.getFloat(r4, r5)     // Catch:{ all -> 0x01f1 }
            int r4 = (int) r4     // Catch:{ all -> 0x01f1 }
            r2.maxCropResultWidth = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropMaxCropResultHeightPX     // Catch:{ all -> 0x01f1 }
            int r5 = r2.maxCropResultHeight     // Catch:{ all -> 0x01f1 }
            float r5 = (float) r5     // Catch:{ all -> 0x01f1 }
            float r4 = r0.getFloat(r4, r5)     // Catch:{ all -> 0x01f1 }
            int r4 = (int) r4     // Catch:{ all -> 0x01f1 }
            r2.maxCropResultHeight = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropFlipHorizontally     // Catch:{ all -> 0x01f1 }
            boolean r5 = r2.flipHorizontally     // Catch:{ all -> 0x01f1 }
            boolean r4 = r0.getBoolean(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.flipHorizontally = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropFlipHorizontally     // Catch:{ all -> 0x01f1 }
            boolean r5 = r2.flipVertically     // Catch:{ all -> 0x01f1 }
            boolean r4 = r0.getBoolean(r4, r5)     // Catch:{ all -> 0x01f1 }
            r2.flipVertically = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropSaveBitmapToInstanceState     // Catch:{ all -> 0x01f1 }
            boolean r5 = r7.mSaveBitmapToInstanceState     // Catch:{ all -> 0x01f1 }
            boolean r4 = r0.getBoolean(r4, r5)     // Catch:{ all -> 0x01f1 }
            r7.mSaveBitmapToInstanceState = r4     // Catch:{ all -> 0x01f1 }
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropAspectRatioX     // Catch:{ all -> 0x01f1 }
            boolean r4 = r0.hasValue(r4)     // Catch:{ all -> 0x01f1 }
            if (r4 == 0) goto L_0x01ed
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropAspectRatioX     // Catch:{ all -> 0x01f1 }
            boolean r4 = r0.hasValue(r4)     // Catch:{ all -> 0x01f1 }
            if (r4 == 0) goto L_0x01ed
            int r4 = com.theartofdev.edmodo.cropper.C2514R.styleable.CropImageView_cropFixAspectRatio     // Catch:{ all -> 0x01f1 }
            boolean r4 = r0.hasValue(r4)     // Catch:{ all -> 0x01f1 }
            if (r4 != 0) goto L_0x01ed
            r2.fixAspectRatio = r1     // Catch:{ all -> 0x01f1 }
        L_0x01ed:
            r0.recycle()
            goto L_0x01f6
        L_0x01f1:
            r1 = move-exception
            r0.recycle()
            throw r1
        L_0x01f6:
            r2.validate()
            com.theartofdev.edmodo.cropper.CropImageView$ScaleType r0 = r2.scaleType
            r7.mScaleType = r0
            boolean r0 = r2.autoZoomEnabled
            r7.mAutoZoomEnabled = r0
            int r0 = r2.maxZoom
            r7.mMaxZoom = r0
            boolean r0 = r2.showCropOverlay
            r7.mShowCropOverlay = r0
            boolean r0 = r2.showProgressBar
            r7.mShowProgressBar = r0
            boolean r0 = r2.flipHorizontally
            r7.mFlipHorizontally = r0
            boolean r0 = r2.flipVertically
            r7.mFlipVertically = r0
            android.view.LayoutInflater r0 = android.view.LayoutInflater.from(r8)
            int r4 = com.theartofdev.edmodo.cropper.C2514R.C2518layout.crop_image_view
            android.view.View r1 = r0.inflate(r4, r7, r1)
            int r4 = com.theartofdev.edmodo.cropper.C2514R.C2517id.ImageView_image
            android.view.View r4 = r1.findViewById(r4)
            android.widget.ImageView r4 = (android.widget.ImageView) r4
            r7.mImageView = r4
            android.widget.ImageView$ScaleType r5 = android.widget.ImageView.ScaleType.MATRIX
            r4.setScaleType(r5)
            int r4 = com.theartofdev.edmodo.cropper.C2514R.C2517id.CropOverlayView
            android.view.View r4 = r1.findViewById(r4)
            com.theartofdev.edmodo.cropper.CropOverlayView r4 = (com.theartofdev.edmodo.cropper.CropOverlayView) r4
            r7.mCropOverlayView = r4
            com.theartofdev.edmodo.cropper.CropImageView$1 r5 = new com.theartofdev.edmodo.cropper.CropImageView$1
            r5.<init>()
            r4.setCropWindowChangeListener(r5)
            r4.setInitialAttributeValues(r2)
            int r4 = com.theartofdev.edmodo.cropper.C2514R.C2517id.CropProgressBar
            android.view.View r4 = r1.findViewById(r4)
            android.widget.ProgressBar r4 = (android.widget.ProgressBar) r4
            r7.mProgressBar = r4
            r7.setProgressBarVisibility()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.theartofdev.edmodo.cropper.CropImageView.<init>(android.content.Context, android.util.AttributeSet):void");
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType != this.mScaleType) {
            this.mScaleType = scaleType;
            this.mZoom = 1.0f;
            this.mZoomOffsetY = 0.0f;
            this.mZoomOffsetX = 0.0f;
            this.mCropOverlayView.resetCropOverlayView();
            requestLayout();
        }
    }

    public CropShape getCropShape() {
        return this.mCropOverlayView.getCropShape();
    }

    public void setCropShape(CropShape cropShape) {
        this.mCropOverlayView.setCropShape(cropShape);
    }

    public boolean isAutoZoomEnabled() {
        return this.mAutoZoomEnabled;
    }

    public void setAutoZoomEnabled(boolean autoZoomEnabled) {
        if (this.mAutoZoomEnabled != autoZoomEnabled) {
            this.mAutoZoomEnabled = autoZoomEnabled;
            handleCropWindowChanged(false, false);
            this.mCropOverlayView.invalidate();
        }
    }

    public void setMultiTouchEnabled(boolean multiTouchEnabled) {
        if (this.mCropOverlayView.setMultiTouchEnabled(multiTouchEnabled)) {
            handleCropWindowChanged(false, false);
            this.mCropOverlayView.invalidate();
        }
    }

    public int getMaxZoom() {
        return this.mMaxZoom;
    }

    public void setMaxZoom(int maxZoom) {
        if (this.mMaxZoom != maxZoom && maxZoom > 0) {
            this.mMaxZoom = maxZoom;
            handleCropWindowChanged(false, false);
            this.mCropOverlayView.invalidate();
        }
    }

    public void setMinCropResultSize(int minCropResultWidth, int minCropResultHeight) {
        this.mCropOverlayView.setMinCropResultSize(minCropResultWidth, minCropResultHeight);
    }

    public void setMaxCropResultSize(int maxCropResultWidth, int maxCropResultHeight) {
        this.mCropOverlayView.setMaxCropResultSize(maxCropResultWidth, maxCropResultHeight);
    }

    public int getRotatedDegrees() {
        return this.mDegreesRotated;
    }

    public void setRotatedDegrees(int degrees) {
        int i = this.mDegreesRotated;
        if (i != degrees) {
            rotateImage(degrees - i);
        }
    }

    public boolean isFixAspectRatio() {
        return this.mCropOverlayView.isFixAspectRatio();
    }

    public void setFixedAspectRatio(boolean fixAspectRatio) {
        this.mCropOverlayView.setFixedAspectRatio(fixAspectRatio);
    }

    public boolean isFlippedHorizontally() {
        return this.mFlipHorizontally;
    }

    public void setFlippedHorizontally(boolean flipHorizontally) {
        if (this.mFlipHorizontally != flipHorizontally) {
            this.mFlipHorizontally = flipHorizontally;
            applyImageMatrix((float) getWidth(), (float) getHeight(), true, false);
        }
    }

    public boolean isFlippedVertically() {
        return this.mFlipVertically;
    }

    public void setFlippedVertically(boolean flipVertically) {
        if (this.mFlipVertically != flipVertically) {
            this.mFlipVertically = flipVertically;
            applyImageMatrix((float) getWidth(), (float) getHeight(), true, false);
        }
    }

    public Guidelines getGuidelines() {
        return this.mCropOverlayView.getGuidelines();
    }

    public void setGuidelines(Guidelines guidelines) {
        this.mCropOverlayView.setGuidelines(guidelines);
    }

    public Pair<Integer, Integer> getAspectRatio() {
        return new Pair<>(Integer.valueOf(this.mCropOverlayView.getAspectRatioX()), Integer.valueOf(this.mCropOverlayView.getAspectRatioY()));
    }

    public void setAspectRatio(int aspectRatioX, int aspectRatioY) {
        this.mCropOverlayView.setAspectRatioX(aspectRatioX);
        this.mCropOverlayView.setAspectRatioY(aspectRatioY);
        setFixedAspectRatio(true);
    }

    public void clearAspectRatio() {
        this.mCropOverlayView.setAspectRatioX(1);
        this.mCropOverlayView.setAspectRatioY(1);
        setFixedAspectRatio(false);
    }

    public void setSnapRadius(float snapRadius) {
        if (snapRadius >= 0.0f) {
            this.mCropOverlayView.setSnapRadius(snapRadius);
        }
    }

    public boolean isShowProgressBar() {
        return this.mShowProgressBar;
    }

    public void setShowProgressBar(boolean showProgressBar) {
        if (this.mShowProgressBar != showProgressBar) {
            this.mShowProgressBar = showProgressBar;
            setProgressBarVisibility();
        }
    }

    public boolean isShowCropOverlay() {
        return this.mShowCropOverlay;
    }

    public void setShowCropOverlay(boolean showCropOverlay) {
        if (this.mShowCropOverlay != showCropOverlay) {
            this.mShowCropOverlay = showCropOverlay;
            setCropOverlayVisibility();
        }
    }

    public boolean isSaveBitmapToInstanceState() {
        return this.mSaveBitmapToInstanceState;
    }

    public void setSaveBitmapToInstanceState(boolean saveBitmapToInstanceState) {
        this.mSaveBitmapToInstanceState = saveBitmapToInstanceState;
    }

    public int getImageResource() {
        return this.mImageResource;
    }

    public Uri getImageUri() {
        return this.mLoadedImageUri;
    }

    public Rect getWholeImageRect() {
        int loadedSampleSize = this.mLoadedSampleSize;
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null) {
            return null;
        }
        return new Rect(0, 0, bitmap.getWidth() * loadedSampleSize, bitmap.getHeight() * loadedSampleSize);
    }

    public Rect getCropRect() {
        int loadedSampleSize = this.mLoadedSampleSize;
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null) {
            return null;
        }
        return BitmapUtils.getRectFromPoints(getCropPoints(), bitmap.getWidth() * loadedSampleSize, bitmap.getHeight() * loadedSampleSize, this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY());
    }

    public RectF getCropWindowRect() {
        CropOverlayView cropOverlayView = this.mCropOverlayView;
        if (cropOverlayView == null) {
            return null;
        }
        return cropOverlayView.getCropWindowRect();
    }

    public float[] getCropPoints() {
        RectF cropWindowRect = this.mCropOverlayView.getCropWindowRect();
        float[] points = {cropWindowRect.left, cropWindowRect.top, cropWindowRect.right, cropWindowRect.top, cropWindowRect.right, cropWindowRect.bottom, cropWindowRect.left, cropWindowRect.bottom};
        this.mImageMatrix.invert(this.mImageInverseMatrix);
        this.mImageInverseMatrix.mapPoints(points);
        for (int i = 0; i < points.length; i++) {
            points[i] = points[i] * ((float) this.mLoadedSampleSize);
        }
        return points;
    }

    public void setCropRect(Rect rect) {
        this.mCropOverlayView.setInitialCropWindowRect(rect);
    }

    public void resetCropRect() {
        this.mZoom = 1.0f;
        this.mZoomOffsetX = 0.0f;
        this.mZoomOffsetY = 0.0f;
        this.mDegreesRotated = this.mInitialDegreesRotated;
        this.mFlipHorizontally = false;
        this.mFlipVertically = false;
        applyImageMatrix((float) getWidth(), (float) getHeight(), false, false);
        this.mCropOverlayView.resetCropWindowRect();
    }

    public Bitmap getCroppedImage() {
        return getCroppedImage(0, 0, RequestSizeOptions.NONE);
    }

    public Bitmap getCroppedImage(int reqWidth, int reqHeight) {
        return getCroppedImage(reqWidth, reqHeight, RequestSizeOptions.RESIZE_INSIDE);
    }

    public Bitmap getCroppedImage(int reqWidth, int reqHeight, RequestSizeOptions options) {
        Bitmap croppedBitmap;
        RequestSizeOptions requestSizeOptions = options;
        if (this.mBitmap != null) {
            this.mImageView.clearAnimation();
            int reqHeight2 = 0;
            int reqWidth2 = requestSizeOptions != RequestSizeOptions.NONE ? reqWidth : 0;
            if (requestSizeOptions != RequestSizeOptions.NONE) {
                reqHeight2 = reqHeight;
            }
            if (this.mLoadedImageUri == null || (this.mLoadedSampleSize <= 1 && requestSizeOptions != RequestSizeOptions.SAMPLING)) {
                croppedBitmap = BitmapUtils.cropBitmapObjectHandleOOM(this.mBitmap, getCropPoints(), this.mDegreesRotated, this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY(), this.mFlipHorizontally, this.mFlipVertically).bitmap;
            } else {
                int orgWidth = this.mBitmap.getWidth() * this.mLoadedSampleSize;
                int orgHeight = this.mBitmap.getHeight() * this.mLoadedSampleSize;
                croppedBitmap = BitmapUtils.cropBitmap(getContext(), this.mLoadedImageUri, getCropPoints(), this.mDegreesRotated, orgWidth, orgHeight, this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY(), reqWidth2, reqHeight2, this.mFlipHorizontally, this.mFlipVertically).bitmap;
            }
            return BitmapUtils.resizeBitmap(croppedBitmap, reqWidth2, reqHeight2, requestSizeOptions);
        }
        int i = reqHeight;
        return null;
    }

    public void getCroppedImageAsync() {
        getCroppedImageAsync(0, 0, RequestSizeOptions.NONE);
    }

    public void getCroppedImageAsync(int reqWidth, int reqHeight) {
        getCroppedImageAsync(reqWidth, reqHeight, RequestSizeOptions.RESIZE_INSIDE);
    }

    public void getCroppedImageAsync(int reqWidth, int reqHeight, RequestSizeOptions options) {
        if (this.mOnCropImageCompleteListener != null) {
            startCropWorkerTask(reqWidth, reqHeight, options, (Uri) null, (Bitmap.CompressFormat) null, 0);
            return;
        }
        throw new IllegalArgumentException("mOnCropImageCompleteListener is not set");
    }

    public void saveCroppedImageAsync(Uri saveUri) {
        saveCroppedImageAsync(saveUri, Bitmap.CompressFormat.JPEG, 90, 0, 0, RequestSizeOptions.NONE);
    }

    public void saveCroppedImageAsync(Uri saveUri, Bitmap.CompressFormat saveCompressFormat, int saveCompressQuality) {
        saveCroppedImageAsync(saveUri, saveCompressFormat, saveCompressQuality, 0, 0, RequestSizeOptions.NONE);
    }

    public void saveCroppedImageAsync(Uri saveUri, Bitmap.CompressFormat saveCompressFormat, int saveCompressQuality, int reqWidth, int reqHeight) {
        saveCroppedImageAsync(saveUri, saveCompressFormat, saveCompressQuality, reqWidth, reqHeight, RequestSizeOptions.RESIZE_INSIDE);
    }

    public void saveCroppedImageAsync(Uri saveUri, Bitmap.CompressFormat saveCompressFormat, int saveCompressQuality, int reqWidth, int reqHeight, RequestSizeOptions options) {
        if (this.mOnCropImageCompleteListener != null) {
            startCropWorkerTask(reqWidth, reqHeight, options, saveUri, saveCompressFormat, saveCompressQuality);
            return;
        }
        throw new IllegalArgumentException("mOnCropImageCompleteListener is not set");
    }

    public void setOnSetCropOverlayReleasedListener(OnSetCropOverlayReleasedListener listener) {
        this.mOnCropOverlayReleasedListener = listener;
    }

    public void setOnSetCropOverlayMovedListener(OnSetCropOverlayMovedListener listener) {
        this.mOnSetCropOverlayMovedListener = listener;
    }

    public void setOnCropWindowChangedListener(OnSetCropWindowChangeListener listener) {
        this.mOnSetCropWindowChangeListener = listener;
    }

    public void setOnSetImageUriCompleteListener(OnSetImageUriCompleteListener listener) {
        this.mOnSetImageUriCompleteListener = listener;
    }

    public void setOnCropImageCompleteListener(OnCropImageCompleteListener listener) {
        this.mOnCropImageCompleteListener = listener;
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.mCropOverlayView.setInitialCropWindowRect((Rect) null);
        setBitmap(bitmap, 0, (Uri) null, 1, 0);
    }

    public void setImageBitmap(Bitmap bitmap, ExifInterface exif) {
        Bitmap setBitmap;
        int degreesRotated = 0;
        if (bitmap == null || exif == null) {
            setBitmap = bitmap;
        } else {
            BitmapUtils.RotateBitmapResult result = BitmapUtils.rotateBitmapByExif(bitmap, exif);
            setBitmap = result.bitmap;
            degreesRotated = result.degrees;
            this.mInitialDegreesRotated = result.degrees;
        }
        this.mCropOverlayView.setInitialCropWindowRect((Rect) null);
        setBitmap(setBitmap, 0, (Uri) null, 1, degreesRotated);
    }

    public void setImageResource(int resId) {
        if (resId != 0) {
            this.mCropOverlayView.setInitialCropWindowRect((Rect) null);
            setBitmap(BitmapFactory.decodeResource(getResources(), resId), resId, (Uri) null, 1, 0);
        }
    }

    public void setImageUriAsync(Uri uri) {
        if (uri != null) {
            WeakReference<BitmapLoadingWorkerTask> weakReference = this.mBitmapLoadingWorkerTask;
            BitmapLoadingWorkerTask currentTask = weakReference != null ? (BitmapLoadingWorkerTask) weakReference.get() : null;
            if (currentTask != null) {
                currentTask.cancel(true);
            }
            clearImageInt();
            this.mRestoreCropWindowRect = null;
            this.mRestoreDegreesRotated = 0;
            this.mCropOverlayView.setInitialCropWindowRect((Rect) null);
            WeakReference<BitmapLoadingWorkerTask> weakReference2 = new WeakReference<>(new BitmapLoadingWorkerTask(this, uri));
            this.mBitmapLoadingWorkerTask = weakReference2;
            ((BitmapLoadingWorkerTask) weakReference2.get()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            setProgressBarVisibility();
        }
    }

    public void clearImage() {
        clearImageInt();
        this.mCropOverlayView.setInitialCropWindowRect((Rect) null);
    }

    public void rotateImage(int degrees) {
        int degrees2;
        int i = degrees;
        if (this.mBitmap != null) {
            if (i < 0) {
                degrees2 = (i % 360) + 360;
            } else {
                degrees2 = i % 360;
            }
            boolean flipAxes = !this.mCropOverlayView.isFixAspectRatio() && ((degrees2 > 45 && degrees2 < 135) || (degrees2 > 215 && degrees2 < 305));
            BitmapUtils.RECT.set(this.mCropOverlayView.getCropWindowRect());
            RectF rectF = BitmapUtils.RECT;
            float halfWidth = (flipAxes ? rectF.height() : rectF.width()) / 2.0f;
            RectF rectF2 = BitmapUtils.RECT;
            float halfHeight = (flipAxes ? rectF2.width() : rectF2.height()) / 2.0f;
            if (flipAxes) {
                boolean isFlippedHorizontally = this.mFlipHorizontally;
                this.mFlipHorizontally = this.mFlipVertically;
                this.mFlipVertically = isFlippedHorizontally;
            }
            this.mImageMatrix.invert(this.mImageInverseMatrix);
            BitmapUtils.POINTS[0] = BitmapUtils.RECT.centerX();
            BitmapUtils.POINTS[1] = BitmapUtils.RECT.centerY();
            BitmapUtils.POINTS[2] = 0.0f;
            BitmapUtils.POINTS[3] = 0.0f;
            BitmapUtils.POINTS[4] = 1.0f;
            BitmapUtils.POINTS[5] = 0.0f;
            this.mImageInverseMatrix.mapPoints(BitmapUtils.POINTS);
            this.mDegreesRotated = (this.mDegreesRotated + degrees2) % 360;
            applyImageMatrix((float) getWidth(), (float) getHeight(), true, false);
            this.mImageMatrix.mapPoints(BitmapUtils.POINTS2, BitmapUtils.POINTS);
            float sqrt = (float) (((double) this.mZoom) / Math.sqrt(Math.pow((double) (BitmapUtils.POINTS2[4] - BitmapUtils.POINTS2[2]), 2.0d) + Math.pow((double) (BitmapUtils.POINTS2[5] - BitmapUtils.POINTS2[3]), 2.0d)));
            this.mZoom = sqrt;
            this.mZoom = Math.max(sqrt, 1.0f);
            applyImageMatrix((float) getWidth(), (float) getHeight(), true, false);
            this.mImageMatrix.mapPoints(BitmapUtils.POINTS2, BitmapUtils.POINTS);
            double change = Math.sqrt(Math.pow((double) (BitmapUtils.POINTS2[4] - BitmapUtils.POINTS2[2]), 2.0d) + Math.pow((double) (BitmapUtils.POINTS2[5] - BitmapUtils.POINTS2[3]), 2.0d));
            float halfWidth2 = (float) (((double) halfWidth) * change);
            float halfHeight2 = (float) (((double) halfHeight) * change);
            BitmapUtils.RECT.set(BitmapUtils.POINTS2[0] - halfWidth2, BitmapUtils.POINTS2[1] - halfHeight2, BitmapUtils.POINTS2[0] + halfWidth2, BitmapUtils.POINTS2[1] + halfHeight2);
            this.mCropOverlayView.resetCropOverlayView();
            this.mCropOverlayView.setCropWindowRect(BitmapUtils.RECT);
            applyImageMatrix((float) getWidth(), (float) getHeight(), true, false);
            handleCropWindowChanged(false, false);
            this.mCropOverlayView.fixCurrentCropWindowRect();
        }
    }

    public void flipImageHorizontally() {
        this.mFlipHorizontally = !this.mFlipHorizontally;
        applyImageMatrix((float) getWidth(), (float) getHeight(), true, false);
    }

    public void flipImageVertically() {
        this.mFlipVertically = !this.mFlipVertically;
        applyImageMatrix((float) getWidth(), (float) getHeight(), true, false);
    }

    /* access modifiers changed from: package-private */
    public void onSetImageUriAsyncComplete(BitmapLoadingWorkerTask.Result result) {
        this.mBitmapLoadingWorkerTask = null;
        setProgressBarVisibility();
        if (result.error == null) {
            this.mInitialDegreesRotated = result.degreesRotated;
            setBitmap(result.bitmap, 0, result.uri, result.loadSampleSize, result.degreesRotated);
        }
        OnSetImageUriCompleteListener listener = this.mOnSetImageUriCompleteListener;
        if (listener != null) {
            listener.onSetImageUriComplete(this, result.uri, result.error);
        }
    }

    /* access modifiers changed from: package-private */
    public void onImageCroppingAsyncComplete(BitmapCroppingWorkerTask.Result result) {
        this.mBitmapCroppingWorkerTask = null;
        setProgressBarVisibility();
        OnCropImageCompleteListener listener = this.mOnCropImageCompleteListener;
        if (listener != null) {
            listener.onCropImageComplete(this, new CropResult(this.mBitmap, this.mLoadedImageUri, result.bitmap, result.uri, result.error, getCropPoints(), getCropRect(), getWholeImageRect(), getRotatedDegrees(), result.sampleSize));
        }
    }

    private void setBitmap(Bitmap bitmap, int imageResource, Uri imageUri, int loadSampleSize, int degreesRotated) {
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 == null || !bitmap2.equals(bitmap)) {
            this.mImageView.clearAnimation();
            clearImageInt();
            this.mBitmap = bitmap;
            this.mImageView.setImageBitmap(bitmap);
            this.mLoadedImageUri = imageUri;
            this.mImageResource = imageResource;
            this.mLoadedSampleSize = loadSampleSize;
            this.mDegreesRotated = degreesRotated;
            applyImageMatrix((float) getWidth(), (float) getHeight(), true, false);
            CropOverlayView cropOverlayView = this.mCropOverlayView;
            if (cropOverlayView != null) {
                cropOverlayView.resetCropOverlayView();
                setCropOverlayVisibility();
            }
        }
    }

    private void clearImageInt() {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null && (this.mImageResource > 0 || this.mLoadedImageUri != null)) {
            bitmap.recycle();
        }
        this.mBitmap = null;
        this.mImageResource = 0;
        this.mLoadedImageUri = null;
        this.mLoadedSampleSize = 1;
        this.mDegreesRotated = 0;
        this.mZoom = 1.0f;
        this.mZoomOffsetX = 0.0f;
        this.mZoomOffsetY = 0.0f;
        this.mImageMatrix.reset();
        this.mSaveInstanceStateBitmapUri = null;
        this.mImageView.setImageBitmap((Bitmap) null);
        setCropOverlayVisibility();
    }

    public void startCropWorkerTask(int reqWidth, int reqHeight, RequestSizeOptions options, Uri saveUri, Bitmap.CompressFormat saveCompressFormat, int saveCompressQuality) {
        CropImageView cropImageView;
        Bitmap bitmap;
        RequestSizeOptions requestSizeOptions = options;
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 != null) {
            this.mImageView.clearAnimation();
            WeakReference<BitmapCroppingWorkerTask> weakReference = this.mBitmapCroppingWorkerTask;
            BitmapCroppingWorkerTask currentTask = weakReference != null ? (BitmapCroppingWorkerTask) weakReference.get() : null;
            if (currentTask != null) {
                currentTask.cancel(true);
            }
            int reqWidth2 = requestSizeOptions != RequestSizeOptions.NONE ? reqWidth : 0;
            int reqHeight2 = requestSizeOptions != RequestSizeOptions.NONE ? reqHeight : 0;
            int orgWidth = bitmap2.getWidth() * this.mLoadedSampleSize;
            int height = bitmap2.getHeight();
            int i = this.mLoadedSampleSize;
            int orgHeight = height * i;
            if (this.mLoadedImageUri == null) {
                bitmap = bitmap2;
                cropImageView = this;
            } else if (i > 1 || requestSizeOptions == RequestSizeOptions.SAMPLING) {
                BitmapCroppingWorkerTask bitmapCroppingWorkerTask = r0;
                BitmapCroppingWorkerTask bitmapCroppingWorkerTask2 = currentTask;
                Bitmap bitmap3 = bitmap2;
                BitmapCroppingWorkerTask bitmapCroppingWorkerTask3 = new BitmapCroppingWorkerTask(this, this.mLoadedImageUri, getCropPoints(), this.mDegreesRotated, orgWidth, orgHeight, this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY(), reqWidth2, reqHeight2, this.mFlipHorizontally, this.mFlipVertically, options, saveUri, saveCompressFormat, saveCompressQuality);
                cropImageView = this;
                cropImageView.mBitmapCroppingWorkerTask = new WeakReference<>(bitmapCroppingWorkerTask);
                ((BitmapCroppingWorkerTask) cropImageView.mBitmapCroppingWorkerTask.get()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                setProgressBarVisibility();
                return;
            } else {
                BitmapCroppingWorkerTask bitmapCroppingWorkerTask4 = currentTask;
                bitmap = bitmap2;
                cropImageView = this;
            }
            cropImageView.mBitmapCroppingWorkerTask = new WeakReference<>(new BitmapCroppingWorkerTask(this, bitmap, getCropPoints(), cropImageView.mDegreesRotated, cropImageView.mCropOverlayView.isFixAspectRatio(), cropImageView.mCropOverlayView.getAspectRatioX(), cropImageView.mCropOverlayView.getAspectRatioY(), reqWidth2, reqHeight2, cropImageView.mFlipHorizontally, cropImageView.mFlipVertically, options, saveUri, saveCompressFormat, saveCompressQuality));
            ((BitmapCroppingWorkerTask) cropImageView.mBitmapCroppingWorkerTask.get()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            setProgressBarVisibility();
            return;
        }
        int i2 = reqWidth;
        int i3 = reqHeight;
    }

    public Parcelable onSaveInstanceState() {
        BitmapLoadingWorkerTask task;
        if (this.mLoadedImageUri == null && this.mBitmap == null && this.mImageResource < 1) {
            return super.onSaveInstanceState();
        }
        Bundle bundle = new Bundle();
        Uri imageUri = this.mLoadedImageUri;
        if (this.mSaveBitmapToInstanceState && imageUri == null && this.mImageResource < 1) {
            Uri writeTempStateStoreBitmap = BitmapUtils.writeTempStateStoreBitmap(getContext(), this.mBitmap, this.mSaveInstanceStateBitmapUri);
            imageUri = writeTempStateStoreBitmap;
            this.mSaveInstanceStateBitmapUri = writeTempStateStoreBitmap;
        }
        if (!(imageUri == null || this.mBitmap == null)) {
            String key = UUID.randomUUID().toString();
            BitmapUtils.mStateBitmap = new Pair<>(key, new WeakReference(this.mBitmap));
            bundle.putString("LOADED_IMAGE_STATE_BITMAP_KEY", key);
        }
        WeakReference<BitmapLoadingWorkerTask> weakReference = this.mBitmapLoadingWorkerTask;
        if (!(weakReference == null || (task = (BitmapLoadingWorkerTask) weakReference.get()) == null)) {
            bundle.putParcelable("LOADING_IMAGE_URI", task.getUri());
        }
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putParcelable("LOADED_IMAGE_URI", imageUri);
        bundle.putInt("LOADED_IMAGE_RESOURCE", this.mImageResource);
        bundle.putInt("LOADED_SAMPLE_SIZE", this.mLoadedSampleSize);
        bundle.putInt("DEGREES_ROTATED", this.mDegreesRotated);
        bundle.putParcelable("INITIAL_CROP_RECT", this.mCropOverlayView.getInitialCropWindowRect());
        BitmapUtils.RECT.set(this.mCropOverlayView.getCropWindowRect());
        this.mImageMatrix.invert(this.mImageInverseMatrix);
        this.mImageInverseMatrix.mapRect(BitmapUtils.RECT);
        bundle.putParcelable("CROP_WINDOW_RECT", BitmapUtils.RECT);
        bundle.putString("CROP_SHAPE", this.mCropOverlayView.getCropShape().name());
        bundle.putBoolean("CROP_AUTO_ZOOM_ENABLED", this.mAutoZoomEnabled);
        bundle.putInt("CROP_MAX_ZOOM", this.mMaxZoom);
        bundle.putBoolean("CROP_FLIP_HORIZONTALLY", this.mFlipHorizontally);
        bundle.putBoolean("CROP_FLIP_VERTICALLY", this.mFlipVertically);
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            if (this.mBitmapLoadingWorkerTask == null && this.mLoadedImageUri == null && this.mBitmap == null && this.mImageResource == 0) {
                Uri uri = (Uri) bundle.getParcelable("LOADED_IMAGE_URI");
                if (uri != null) {
                    String key = bundle.getString("LOADED_IMAGE_STATE_BITMAP_KEY");
                    if (key != null) {
                        Bitmap stateBitmap = (BitmapUtils.mStateBitmap == null || !((String) BitmapUtils.mStateBitmap.first).equals(key)) ? null : (Bitmap) ((WeakReference) BitmapUtils.mStateBitmap.second).get();
                        BitmapUtils.mStateBitmap = null;
                        if (stateBitmap != null && !stateBitmap.isRecycled()) {
                            setBitmap(stateBitmap, 0, uri, bundle.getInt("LOADED_SAMPLE_SIZE"), 0);
                        }
                    }
                    if (this.mLoadedImageUri == null) {
                        setImageUriAsync(uri);
                    }
                } else {
                    int resId = bundle.getInt("LOADED_IMAGE_RESOURCE");
                    if (resId > 0) {
                        setImageResource(resId);
                    } else {
                        Uri uri2 = (Uri) bundle.getParcelable("LOADING_IMAGE_URI");
                        if (uri2 != null) {
                            setImageUriAsync(uri2);
                        }
                    }
                }
                int i = bundle.getInt("DEGREES_ROTATED");
                this.mRestoreDegreesRotated = i;
                this.mDegreesRotated = i;
                Rect initialCropRect = (Rect) bundle.getParcelable("INITIAL_CROP_RECT");
                if (initialCropRect != null && (initialCropRect.width() > 0 || initialCropRect.height() > 0)) {
                    this.mCropOverlayView.setInitialCropWindowRect(initialCropRect);
                }
                RectF cropWindowRect = (RectF) bundle.getParcelable("CROP_WINDOW_RECT");
                if (cropWindowRect != null && (cropWindowRect.width() > 0.0f || cropWindowRect.height() > 0.0f)) {
                    this.mRestoreCropWindowRect = cropWindowRect;
                }
                this.mCropOverlayView.setCropShape(CropShape.valueOf(bundle.getString("CROP_SHAPE")));
                this.mAutoZoomEnabled = bundle.getBoolean("CROP_AUTO_ZOOM_ENABLED");
                this.mMaxZoom = bundle.getInt("CROP_MAX_ZOOM");
                this.mFlipHorizontally = bundle.getBoolean("CROP_FLIP_HORIZONTALLY");
                this.mFlipVertically = bundle.getBoolean("CROP_FLIP_VERTICALLY");
            }
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredHeight;
        int desiredWidth;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            if (heightSize == 0) {
                heightSize = bitmap.getHeight();
            }
            double viewToBitmapWidthRatio = Double.POSITIVE_INFINITY;
            double viewToBitmapHeightRatio = Double.POSITIVE_INFINITY;
            if (widthSize < this.mBitmap.getWidth()) {
                viewToBitmapWidthRatio = ((double) widthSize) / ((double) this.mBitmap.getWidth());
            }
            if (heightSize < this.mBitmap.getHeight()) {
                viewToBitmapHeightRatio = ((double) heightSize) / ((double) this.mBitmap.getHeight());
            }
            if (viewToBitmapWidthRatio == Double.POSITIVE_INFINITY && viewToBitmapHeightRatio == Double.POSITIVE_INFINITY) {
                desiredWidth = this.mBitmap.getWidth();
                desiredHeight = this.mBitmap.getHeight();
            } else if (viewToBitmapWidthRatio <= viewToBitmapHeightRatio) {
                desiredWidth = widthSize;
                desiredHeight = (int) (((double) this.mBitmap.getHeight()) * viewToBitmapWidthRatio);
            } else {
                desiredHeight = heightSize;
                desiredWidth = (int) (((double) this.mBitmap.getWidth()) * viewToBitmapHeightRatio);
            }
            int width = getOnMeasureSpec(widthMode, widthSize, desiredWidth);
            int height = getOnMeasureSpec(heightMode, heightSize, desiredHeight);
            this.mLayoutWidth = width;
            this.mLayoutHeight = height;
            setMeasuredDimension(width, height);
            return;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.mLayoutWidth <= 0 || this.mLayoutHeight <= 0) {
            updateImageBounds(true);
            return;
        }
        ViewGroup.LayoutParams origParams = getLayoutParams();
        origParams.width = this.mLayoutWidth;
        origParams.height = this.mLayoutHeight;
        setLayoutParams(origParams);
        if (this.mBitmap != null) {
            applyImageMatrix((float) (r - l), (float) (b - t), true, false);
            if (this.mRestoreCropWindowRect != null) {
                int i = this.mRestoreDegreesRotated;
                if (i != this.mInitialDegreesRotated) {
                    this.mDegreesRotated = i;
                    applyImageMatrix((float) (r - l), (float) (b - t), true, false);
                }
                this.mImageMatrix.mapRect(this.mRestoreCropWindowRect);
                this.mCropOverlayView.setCropWindowRect(this.mRestoreCropWindowRect);
                handleCropWindowChanged(false, false);
                this.mCropOverlayView.fixCurrentCropWindowRect();
                this.mRestoreCropWindowRect = null;
            } else if (this.mSizeChanged) {
                this.mSizeChanged = false;
                handleCropWindowChanged(false, false);
            }
        } else {
            updateImageBounds(true);
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mSizeChanged = oldw > 0 && oldh > 0;
    }

    /* access modifiers changed from: private */
    public void handleCropWindowChanged(boolean inProgress, boolean animate) {
        int width = getWidth();
        int height = getHeight();
        if (this.mBitmap != null && width > 0 && height > 0) {
            RectF cropRect = this.mCropOverlayView.getCropWindowRect();
            if (inProgress) {
                if (cropRect.left < 0.0f || cropRect.top < 0.0f || cropRect.right > ((float) width) || cropRect.bottom > ((float) height)) {
                    applyImageMatrix((float) width, (float) height, false, false);
                }
            } else if (this.mAutoZoomEnabled || this.mZoom > 1.0f) {
                float newZoom = 0.0f;
                if (this.mZoom < ((float) this.mMaxZoom) && cropRect.width() < ((float) width) * 0.5f && cropRect.height() < ((float) height) * 0.5f) {
                    newZoom = Math.min((float) this.mMaxZoom, Math.min(((float) width) / ((cropRect.width() / this.mZoom) / 0.64f), ((float) height) / ((cropRect.height() / this.mZoom) / 0.64f)));
                }
                if (this.mZoom > 1.0f && (cropRect.width() > ((float) width) * 0.65f || cropRect.height() > ((float) height) * 0.65f)) {
                    newZoom = Math.max(1.0f, Math.min(((float) width) / ((cropRect.width() / this.mZoom) / 0.51f), ((float) height) / ((cropRect.height() / this.mZoom) / 0.51f)));
                }
                if (!this.mAutoZoomEnabled) {
                    newZoom = 1.0f;
                }
                if (newZoom > 0.0f && newZoom != this.mZoom) {
                    if (animate) {
                        if (this.mAnimation == null) {
                            this.mAnimation = new CropImageAnimation(this.mImageView, this.mCropOverlayView);
                        }
                        this.mAnimation.setStartState(this.mImagePoints, this.mImageMatrix);
                    }
                    this.mZoom = newZoom;
                    applyImageMatrix((float) width, (float) height, true, animate);
                }
            }
            OnSetCropWindowChangeListener onSetCropWindowChangeListener = this.mOnSetCropWindowChangeListener;
            if (onSetCropWindowChangeListener != null && !inProgress) {
                onSetCropWindowChangeListener.onCropWindowChanged();
            }
        }
    }

    private void applyImageMatrix(float width, float height, boolean center, boolean animate) {
        float f;
        if (this.mBitmap != null) {
            float f2 = 0.0f;
            if (width > 0.0f && height > 0.0f) {
                this.mImageMatrix.invert(this.mImageInverseMatrix);
                RectF cropRect = this.mCropOverlayView.getCropWindowRect();
                this.mImageInverseMatrix.mapRect(cropRect);
                this.mImageMatrix.reset();
                this.mImageMatrix.postTranslate((width - ((float) this.mBitmap.getWidth())) / 2.0f, (height - ((float) this.mBitmap.getHeight())) / 2.0f);
                mapImagePointsByImageMatrix();
                int i = this.mDegreesRotated;
                if (i > 0) {
                    this.mImageMatrix.postRotate((float) i, BitmapUtils.getRectCenterX(this.mImagePoints), BitmapUtils.getRectCenterY(this.mImagePoints));
                    mapImagePointsByImageMatrix();
                }
                float scale = Math.min(width / BitmapUtils.getRectWidth(this.mImagePoints), height / BitmapUtils.getRectHeight(this.mImagePoints));
                if (this.mScaleType == ScaleType.FIT_CENTER || ((this.mScaleType == ScaleType.CENTER_INSIDE && scale < 1.0f) || (scale > 1.0f && this.mAutoZoomEnabled))) {
                    this.mImageMatrix.postScale(scale, scale, BitmapUtils.getRectCenterX(this.mImagePoints), BitmapUtils.getRectCenterY(this.mImagePoints));
                    mapImagePointsByImageMatrix();
                }
                float scaleX = this.mFlipHorizontally ? -this.mZoom : this.mZoom;
                float scaleY = this.mFlipVertically ? -this.mZoom : this.mZoom;
                this.mImageMatrix.postScale(scaleX, scaleY, BitmapUtils.getRectCenterX(this.mImagePoints), BitmapUtils.getRectCenterY(this.mImagePoints));
                mapImagePointsByImageMatrix();
                this.mImageMatrix.mapRect(cropRect);
                if (center) {
                    if (width > BitmapUtils.getRectWidth(this.mImagePoints)) {
                        f = 0.0f;
                    } else {
                        f = Math.max(Math.min((width / 2.0f) - cropRect.centerX(), -BitmapUtils.getRectLeft(this.mImagePoints)), ((float) getWidth()) - BitmapUtils.getRectRight(this.mImagePoints)) / scaleX;
                    }
                    this.mZoomOffsetX = f;
                    if (height <= BitmapUtils.getRectHeight(this.mImagePoints)) {
                        f2 = Math.max(Math.min((height / 2.0f) - cropRect.centerY(), -BitmapUtils.getRectTop(this.mImagePoints)), ((float) getHeight()) - BitmapUtils.getRectBottom(this.mImagePoints)) / scaleY;
                    }
                    this.mZoomOffsetY = f2;
                } else {
                    this.mZoomOffsetX = Math.min(Math.max(this.mZoomOffsetX * scaleX, -cropRect.left), (-cropRect.right) + width) / scaleX;
                    this.mZoomOffsetY = Math.min(Math.max(this.mZoomOffsetY * scaleY, -cropRect.top), (-cropRect.bottom) + height) / scaleY;
                }
                this.mImageMatrix.postTranslate(this.mZoomOffsetX * scaleX, this.mZoomOffsetY * scaleY);
                cropRect.offset(this.mZoomOffsetX * scaleX, this.mZoomOffsetY * scaleY);
                this.mCropOverlayView.setCropWindowRect(cropRect);
                mapImagePointsByImageMatrix();
                this.mCropOverlayView.invalidate();
                if (animate) {
                    this.mAnimation.setEndState(this.mImagePoints, this.mImageMatrix);
                    this.mImageView.startAnimation(this.mAnimation);
                } else {
                    this.mImageView.setImageMatrix(this.mImageMatrix);
                }
                updateImageBounds(false);
            }
        }
    }

    private void mapImagePointsByImageMatrix() {
        float[] fArr = this.mImagePoints;
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        fArr[2] = (float) this.mBitmap.getWidth();
        float[] fArr2 = this.mImagePoints;
        fArr2[3] = 0.0f;
        fArr2[4] = (float) this.mBitmap.getWidth();
        this.mImagePoints[5] = (float) this.mBitmap.getHeight();
        float[] fArr3 = this.mImagePoints;
        fArr3[6] = 0.0f;
        fArr3[7] = (float) this.mBitmap.getHeight();
        this.mImageMatrix.mapPoints(this.mImagePoints);
        float[] fArr4 = this.mScaleImagePoints;
        fArr4[0] = 0.0f;
        fArr4[1] = 0.0f;
        fArr4[2] = 100.0f;
        fArr4[3] = 0.0f;
        fArr4[4] = 100.0f;
        fArr4[5] = 100.0f;
        fArr4[6] = 0.0f;
        fArr4[7] = 100.0f;
        this.mImageMatrix.mapPoints(fArr4);
    }

    private static int getOnMeasureSpec(int measureSpecMode, int measureSpecSize, int desiredSize) {
        if (measureSpecMode == 1073741824) {
            return measureSpecSize;
        }
        if (measureSpecMode == Integer.MIN_VALUE) {
            return Math.min(desiredSize, measureSpecSize);
        }
        return desiredSize;
    }

    private void setCropOverlayVisibility() {
        CropOverlayView cropOverlayView = this.mCropOverlayView;
        if (cropOverlayView != null) {
            cropOverlayView.setVisibility((!this.mShowCropOverlay || this.mBitmap == null) ? 4 : 0);
        }
    }

    private void setProgressBarVisibility() {
        int i = 0;
        boolean visible = this.mShowProgressBar && ((this.mBitmap == null && this.mBitmapLoadingWorkerTask != null) || this.mBitmapCroppingWorkerTask != null);
        ProgressBar progressBar = this.mProgressBar;
        if (!visible) {
            i = 4;
        }
        progressBar.setVisibility(i);
    }

    private void updateImageBounds(boolean clear) {
        if (this.mBitmap != null && !clear) {
            this.mCropOverlayView.setCropWindowLimits((float) getWidth(), (float) getHeight(), (((float) this.mLoadedSampleSize) * 100.0f) / BitmapUtils.getRectWidth(this.mScaleImagePoints), (((float) this.mLoadedSampleSize) * 100.0f) / BitmapUtils.getRectHeight(this.mScaleImagePoints));
        }
        this.mCropOverlayView.setBounds(clear ? null : this.mImagePoints, getWidth(), getHeight());
    }

    public static class CropResult {
        private final Bitmap mBitmap;
        private final float[] mCropPoints;
        private final Rect mCropRect;
        private final Exception mError;
        private final Bitmap mOriginalBitmap;
        private final Uri mOriginalUri;
        private final int mRotation;
        private final int mSampleSize;
        private final Uri mUri;
        private final Rect mWholeImageRect;

        CropResult(Bitmap originalBitmap, Uri originalUri, Bitmap bitmap, Uri uri, Exception error, float[] cropPoints, Rect cropRect, Rect wholeImageRect, int rotation, int sampleSize) {
            this.mOriginalBitmap = originalBitmap;
            this.mOriginalUri = originalUri;
            this.mBitmap = bitmap;
            this.mUri = uri;
            this.mError = error;
            this.mCropPoints = cropPoints;
            this.mCropRect = cropRect;
            this.mWholeImageRect = wholeImageRect;
            this.mRotation = rotation;
            this.mSampleSize = sampleSize;
        }

        public Bitmap getOriginalBitmap() {
            return this.mOriginalBitmap;
        }

        public Uri getOriginalUri() {
            return this.mOriginalUri;
        }

        public boolean isSuccessful() {
            return this.mError == null;
        }

        public Bitmap getBitmap() {
            return this.mBitmap;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public Exception getError() {
            return this.mError;
        }

        public float[] getCropPoints() {
            return this.mCropPoints;
        }

        public Rect getCropRect() {
            return this.mCropRect;
        }

        public Rect getWholeImageRect() {
            return this.mWholeImageRect;
        }

        public int getRotation() {
            return this.mRotation;
        }

        public int getSampleSize() {
            return this.mSampleSize;
        }
    }
}
