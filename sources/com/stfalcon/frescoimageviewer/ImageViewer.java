package com.stfalcon.frescoimageviewer;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageViewer implements OnDismissListener, DialogInterface.OnKeyListener {
    private static final String TAG = ImageViewer.class.getSimpleName();
    /* access modifiers changed from: private */
    public Builder builder;
    private AlertDialog dialog;
    private ImageViewerView viewer;

    public interface Formatter<T> {
        String format(T t);
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    public interface OnImageChangeListener {
        void onImageChange(int i);
    }

    protected ImageViewer(Builder builder2) {
        this.builder = builder2;
        createDialog();
    }

    public void show() {
        if (!this.builder.dataSet.data.isEmpty()) {
            this.dialog.show();
        } else {
            Log.w(TAG, "Images list cannot be empty! Viewer ignored.");
        }
    }

    public String getUrl() {
        return this.viewer.getUrl();
    }

    private void createDialog() {
        ImageViewerView imageViewerView = new ImageViewerView(this.builder.context);
        this.viewer = imageViewerView;
        imageViewerView.setCustomImageRequestBuilder(this.builder.customImageRequestBuilder);
        this.viewer.setCustomDraweeHierarchyBuilder(this.builder.customHierarchyBuilder);
        this.viewer.allowZooming(this.builder.isZoomingAllowed);
        this.viewer.allowSwipeToDismiss(this.builder.isSwipeToDismissAllowed);
        this.viewer.setOnDismissListener(this);
        this.viewer.setBackgroundColor(this.builder.backgroundColor);
        this.viewer.setOverlayView(this.builder.overlayView);
        this.viewer.setImageMargin(this.builder.imageMarginPixels);
        this.viewer.setContainerPadding(this.builder.containerPaddingPixels);
        this.viewer.setUrls(this.builder.dataSet, this.builder.startPosition);
        this.viewer.setPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                if (ImageViewer.this.builder.imageChangeListener != null) {
                    ImageViewer.this.builder.imageChangeListener.onImageChange(position);
                }
            }
        });
        AlertDialog create = new AlertDialog.Builder(this.builder.context, getDialogStyle()).setView((View) this.viewer).setOnKeyListener(this).create();
        this.dialog = create;
        create.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                if (ImageViewer.this.builder.onDismissListener != null) {
                    ImageViewer.this.builder.onDismissListener.onDismiss();
                }
            }
        });
    }

    public void onDismiss() {
        this.dialog.dismiss();
    }

    public boolean onKey(DialogInterface dialog2, int keyCode, KeyEvent event) {
        if (keyCode == 4 && event.getAction() == 1 && !event.isCanceled()) {
            if (this.viewer.isScaled()) {
                this.viewer.resetScale();
            } else {
                dialog2.cancel();
            }
        }
        return true;
    }

    public static ImageRequestBuilder createImageRequestBuilder() {
        return ImageRequestBuilder.newBuilderWithSource(Uri.parse(""));
    }

    private int getDialogStyle() {
        return this.builder.shouldStatusBarHide ? 16973841 : 16973840;
    }

    static class DataSet<T> {
        /* access modifiers changed from: private */
        public List<T> data;
        /* access modifiers changed from: private */
        public Formatter<T> formatter;

        DataSet(List<T> data2) {
            this.data = data2;
        }

        /* access modifiers changed from: package-private */
        public String format(int position) {
            return format(this.data.get(position));
        }

        /* access modifiers changed from: package-private */
        public String format(T t) {
            Formatter<T> formatter2 = this.formatter;
            if (formatter2 == null) {
                return t.toString();
            }
            return formatter2.format(t);
        }

        public List<T> getData() {
            return this.data;
        }
    }

    public static class Builder<T> {
        /* access modifiers changed from: private */
        public int backgroundColor;
        /* access modifiers changed from: private */
        public int[] containerPaddingPixels;
        /* access modifiers changed from: private */
        public Context context;
        /* access modifiers changed from: private */
        public GenericDraweeHierarchyBuilder customHierarchyBuilder;
        /* access modifiers changed from: private */
        public ImageRequestBuilder customImageRequestBuilder;
        /* access modifiers changed from: private */
        public DataSet<T> dataSet;
        /* access modifiers changed from: private */
        public OnImageChangeListener imageChangeListener;
        /* access modifiers changed from: private */
        public int imageMarginPixels;
        /* access modifiers changed from: private */
        public boolean isSwipeToDismissAllowed;
        /* access modifiers changed from: private */
        public boolean isZoomingAllowed;
        /* access modifiers changed from: private */
        public OnDismissListener onDismissListener;
        /* access modifiers changed from: private */
        public View overlayView;
        /* access modifiers changed from: private */
        public boolean shouldStatusBarHide;
        /* access modifiers changed from: private */
        public int startPosition;

        public Builder(Context context2, T[] images) {
            this(context2, new ArrayList(Arrays.asList(images)));
        }

        public Builder(Context context2, List<T> images) {
            this.backgroundColor = ViewCompat.MEASURED_STATE_MASK;
            this.containerPaddingPixels = new int[4];
            this.shouldStatusBarHide = true;
            this.isZoomingAllowed = true;
            this.isSwipeToDismissAllowed = true;
            this.context = context2;
            this.dataSet = new DataSet<>(images);
        }

        public Builder setFormatter(Formatter<T> formatter) {
            Formatter unused = this.dataSet.formatter = formatter;
            return this;
        }

        public Builder setBackgroundColorRes(int color) {
            return setBackgroundColor(this.context.getResources().getColor(color));
        }

        public Builder setBackgroundColor(int color) {
            this.backgroundColor = color;
            return this;
        }

        public Builder setStartPosition(int position) {
            this.startPosition = position;
            return this;
        }

        public Builder setImageChangeListener(OnImageChangeListener imageChangeListener2) {
            this.imageChangeListener = imageChangeListener2;
            return this;
        }

        public Builder setOverlayView(View view) {
            this.overlayView = view;
            return this;
        }

        public Builder setImageMarginPx(int marginPixels) {
            this.imageMarginPixels = marginPixels;
            return this;
        }

        public Builder setImageMargin(Context context2, int dimen) {
            this.imageMarginPixels = Math.round(context2.getResources().getDimension(dimen));
            return this;
        }

        public Builder setContainerPaddingPx(int start, int top, int end, int bottom) {
            this.containerPaddingPixels = new int[]{start, top, end, bottom};
            return this;
        }

        public Builder setContainerPadding(Context context2, int start, int top, int end, int bottom) {
            setContainerPaddingPx(Math.round(context2.getResources().getDimension(start)), Math.round(context2.getResources().getDimension(top)), Math.round(context2.getResources().getDimension(end)), Math.round(context2.getResources().getDimension(bottom)));
            return this;
        }

        public Builder setContainerPaddingPx(int padding) {
            this.containerPaddingPixels = new int[]{padding, padding, padding, padding};
            return this;
        }

        public Builder setContainerPadding(Context context2, int padding) {
            int paddingPx = Math.round(context2.getResources().getDimension(padding));
            setContainerPaddingPx(paddingPx, paddingPx, paddingPx, paddingPx);
            return this;
        }

        public Builder hideStatusBar(boolean shouldHide) {
            this.shouldStatusBarHide = shouldHide;
            return this;
        }

        public Builder allowZooming(boolean value) {
            this.isZoomingAllowed = value;
            return this;
        }

        public Builder allowSwipeToDismiss(boolean value) {
            this.isSwipeToDismissAllowed = value;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener2) {
            this.onDismissListener = onDismissListener2;
            return this;
        }

        public Builder setCustomImageRequestBuilder(ImageRequestBuilder customImageRequestBuilder2) {
            this.customImageRequestBuilder = customImageRequestBuilder2;
            return this;
        }

        public Builder setCustomDraweeHierarchyBuilder(GenericDraweeHierarchyBuilder customHierarchyBuilder2) {
            this.customHierarchyBuilder = customHierarchyBuilder2;
            return this;
        }

        public ImageViewer build() {
            return new ImageViewer(this);
        }

        public ImageViewer show() {
            ImageViewer dialog = build();
            dialog.show();
            return dialog;
        }
    }
}
