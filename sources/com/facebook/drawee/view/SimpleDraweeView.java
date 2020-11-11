package com.facebook.drawee.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.C2288R;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import javax.annotation.Nullable;

public class SimpleDraweeView extends GenericDraweeView {
    private static Supplier<? extends AbstractDraweeControllerBuilder> sDraweecontrollerbuildersupplier;
    private AbstractDraweeControllerBuilder mControllerBuilder;

    public static void initialize(Supplier<? extends AbstractDraweeControllerBuilder> draweeControllerBuilderSupplier) {
        sDraweecontrollerbuildersupplier = draweeControllerBuilderSupplier;
    }

    public static void shutDown() {
        sDraweecontrollerbuildersupplier = null;
    }

    public SimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init(context, (AttributeSet) null);
    }

    public SimpleDraweeView(Context context) {
        super(context);
        init(context, (AttributeSet) null);
    }

    public SimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SimpleDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public SimpleDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray gdhAttrs;
        int resId;
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("SimpleDraweeView#init");
            }
            if (isInEditMode()) {
                getTopLevelDrawable().setVisible(true, false);
                getTopLevelDrawable().invalidateSelf();
            } else {
                Preconditions.checkNotNull(sDraweecontrollerbuildersupplier, "SimpleDraweeView was not initialized!");
                this.mControllerBuilder = (AbstractDraweeControllerBuilder) sDraweecontrollerbuildersupplier.get();
            }
            if (attrs != null) {
                gdhAttrs = context.obtainStyledAttributes(attrs, C2288R.styleable.SimpleDraweeView);
                if (gdhAttrs.hasValue(C2288R.styleable.SimpleDraweeView_actualImageUri)) {
                    setImageURI(Uri.parse(gdhAttrs.getString(C2288R.styleable.SimpleDraweeView_actualImageUri)), (Object) null);
                } else if (gdhAttrs.hasValue(C2288R.styleable.SimpleDraweeView_actualImageResource) && (resId = gdhAttrs.getResourceId(C2288R.styleable.SimpleDraweeView_actualImageResource, -1)) != -1) {
                    if (isInEditMode()) {
                        setImageResource(resId);
                    } else {
                        setActualImageResource(resId);
                    }
                }
                gdhAttrs.recycle();
            }
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        } catch (Throwable th) {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public AbstractDraweeControllerBuilder getControllerBuilder() {
        return this.mControllerBuilder;
    }

    public void setImageRequest(ImageRequest request) {
        setController(this.mControllerBuilder.setImageRequest(request).setOldController(getController()).build());
    }

    public void setImageURI(Uri uri) {
        setImageURI(uri, (Object) null);
    }

    public void setImageURI(@Nullable String uriString) {
        setImageURI(uriString, (Object) null);
    }

    public void setImageURI(Uri uri, @Nullable Object callerContext) {
        setController(this.mControllerBuilder.setCallerContext(callerContext).setUri(uri).setOldController(getController()).build());
    }

    public void setImageURI(@Nullable String uriString, @Nullable Object callerContext) {
        setImageURI(uriString != null ? Uri.parse(uriString) : null, callerContext);
    }

    public void setActualImageResource(int resourceId) {
        setActualImageResource(resourceId, (Object) null);
    }

    public void setActualImageResource(int resourceId, @Nullable Object callerContext) {
        setImageURI(UriUtil.getUriForResourceId(resourceId), callerContext);
    }

    public void setImageResource(int resId) {
        super.setImageResource(resId);
    }
}
