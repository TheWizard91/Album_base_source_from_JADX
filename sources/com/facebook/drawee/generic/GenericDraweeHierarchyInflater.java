package com.facebook.drawee.generic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import com.facebook.drawee.C2288R;
import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import javax.annotation.Nullable;

public class GenericDraweeHierarchyInflater {
    public static GenericDraweeHierarchy inflateHierarchy(Context context, @Nullable AttributeSet attrs) {
        return inflateBuilder(context, attrs).build();
    }

    public static GenericDraweeHierarchyBuilder inflateBuilder(Context context, @Nullable AttributeSet attrs) {
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("GenericDraweeHierarchyBuilder#inflateBuilder");
        }
        GenericDraweeHierarchyBuilder builder = updateBuilder(new GenericDraweeHierarchyBuilder(context.getResources()), context, attrs);
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.endSection();
        }
        return builder;
    }

    public static GenericDraweeHierarchyBuilder updateBuilder(GenericDraweeHierarchyBuilder builder, Context context, @Nullable AttributeSet attrs) {
        boolean z;
        boolean z2;
        int indexCount;
        int i;
        boolean z3;
        GenericDraweeHierarchyBuilder genericDraweeHierarchyBuilder = builder;
        Context context2 = context;
        AttributeSet attributeSet = attrs;
        int progressBarAutoRotateInterval = 0;
        int roundedCornerRadius = 0;
        boolean roundTopLeft = true;
        boolean roundTopRight = true;
        boolean roundBottomLeft = true;
        boolean roundBottomRight = true;
        boolean roundTopStart = true;
        boolean roundTopEnd = true;
        boolean roundBottomStart = true;
        boolean roundBottomEnd = true;
        if (attributeSet != null) {
            TypedArray gdhAttrs = context2.obtainStyledAttributes(attributeSet, C2288R.styleable.GenericDraweeHierarchy);
            try {
                int indexCount2 = gdhAttrs.getIndexCount();
                int i2 = 0;
                while (true) {
                    indexCount = indexCount2;
                    if (i2 >= indexCount) {
                        break;
                    }
                    indexCount2 = indexCount;
                    int indexCount3 = gdhAttrs.getIndex(i2);
                    if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_actualImageScaleType) {
                        genericDraweeHierarchyBuilder.setActualImageScaleType(getScaleTypeFromXml(gdhAttrs, indexCount3));
                        i = i2;
                    } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_placeholderImage) {
                        genericDraweeHierarchyBuilder.setPlaceholderImage(getDrawable(context2, gdhAttrs, indexCount3));
                        i = i2;
                    } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_pressedStateOverlayImage) {
                        genericDraweeHierarchyBuilder.setPressedStateOverlay(getDrawable(context2, gdhAttrs, indexCount3));
                        i = i2;
                    } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_progressBarImage) {
                        genericDraweeHierarchyBuilder.setProgressBarImage(getDrawable(context2, gdhAttrs, indexCount3));
                        i = i2;
                    } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_fadeDuration) {
                        i = i2;
                        z = false;
                        genericDraweeHierarchyBuilder.setFadeDuration(gdhAttrs.getInt(indexCount3, 0));
                    } else {
                        i = i2;
                        if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_viewAspectRatio) {
                            genericDraweeHierarchyBuilder.setDesiredAspectRatio(gdhAttrs.getFloat(indexCount3, 0.0f));
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_placeholderImageScaleType) {
                            genericDraweeHierarchyBuilder.setPlaceholderImageScaleType(getScaleTypeFromXml(gdhAttrs, indexCount3));
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_retryImage) {
                            genericDraweeHierarchyBuilder.setRetryImage(getDrawable(context2, gdhAttrs, indexCount3));
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_retryImageScaleType) {
                            genericDraweeHierarchyBuilder.setRetryImageScaleType(getScaleTypeFromXml(gdhAttrs, indexCount3));
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_failureImage) {
                            genericDraweeHierarchyBuilder.setFailureImage(getDrawable(context2, gdhAttrs, indexCount3));
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_failureImageScaleType) {
                            genericDraweeHierarchyBuilder.setFailureImageScaleType(getScaleTypeFromXml(gdhAttrs, indexCount3));
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_progressBarImageScaleType) {
                            genericDraweeHierarchyBuilder.setProgressBarImageScaleType(getScaleTypeFromXml(gdhAttrs, indexCount3));
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_progressBarAutoRotateInterval) {
                            progressBarAutoRotateInterval = gdhAttrs.getInteger(indexCount3, progressBarAutoRotateInterval);
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_backgroundImage) {
                            genericDraweeHierarchyBuilder.setBackground(getDrawable(context2, gdhAttrs, indexCount3));
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_overlayImage) {
                            genericDraweeHierarchyBuilder.setOverlay(getDrawable(context2, gdhAttrs, indexCount3));
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_roundAsCircle) {
                            z3 = false;
                            try {
                                getRoundingParams(builder).setRoundAsCircle(gdhAttrs.getBoolean(indexCount3, false));
                            } catch (Throwable th) {
                                th = th;
                                z = z3;
                                gdhAttrs.recycle();
                                if (Build.VERSION.SDK_INT >= 17) {
                                    z2 = true;
                                    if (context.getResources().getConfiguration().getLayoutDirection() == 1) {
                                        if (!roundTopLeft || !roundTopEnd) {
                                            boolean z4 = z;
                                        }
                                        if (!roundTopRight || !roundTopStart) {
                                            boolean z5 = z;
                                        }
                                        if (!roundBottomRight || !roundBottomStart) {
                                            boolean z6 = z;
                                        }
                                        if (!roundBottomLeft || !roundBottomEnd) {
                                            z2 = z;
                                        }
                                        boolean z7 = z2;
                                        throw th;
                                    }
                                } else {
                                    z2 = true;
                                }
                                if (!roundTopLeft || !roundTopStart) {
                                    boolean z8 = z;
                                } else {
                                    boolean z9 = z2;
                                }
                                if (!roundTopRight || !roundTopEnd) {
                                    boolean z10 = z;
                                } else {
                                    boolean z11 = z2;
                                }
                                if (!roundBottomRight || !roundBottomEnd) {
                                    boolean z12 = z;
                                } else {
                                    boolean z13 = z2;
                                }
                                if (!roundBottomLeft || !roundBottomStart) {
                                    z2 = z;
                                }
                                boolean z14 = z2;
                                throw th;
                            }
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_roundedCornerRadius) {
                            roundedCornerRadius = gdhAttrs.getDimensionPixelSize(indexCount3, roundedCornerRadius);
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_roundTopLeft) {
                            roundTopLeft = gdhAttrs.getBoolean(indexCount3, roundTopLeft);
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_roundTopRight) {
                            roundTopRight = gdhAttrs.getBoolean(indexCount3, roundTopRight);
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_roundBottomLeft) {
                            roundBottomLeft = gdhAttrs.getBoolean(indexCount3, roundBottomLeft);
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_roundBottomRight) {
                            roundBottomRight = gdhAttrs.getBoolean(indexCount3, roundBottomRight);
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_roundTopStart) {
                            roundTopStart = gdhAttrs.getBoolean(indexCount3, roundTopStart);
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_roundTopEnd) {
                            roundTopEnd = gdhAttrs.getBoolean(indexCount3, roundTopEnd);
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_roundBottomStart) {
                            roundBottomStart = gdhAttrs.getBoolean(indexCount3, roundBottomStart);
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_roundBottomEnd) {
                            roundBottomEnd = gdhAttrs.getBoolean(indexCount3, roundBottomEnd);
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_roundWithOverlayColor) {
                            z3 = false;
                            getRoundingParams(builder).setOverlayColor(gdhAttrs.getColor(indexCount3, 0));
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_roundingBorderWidth) {
                            z3 = false;
                            getRoundingParams(builder).setBorderWidth((float) gdhAttrs.getDimensionPixelSize(indexCount3, 0));
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_roundingBorderColor) {
                            z3 = false;
                            getRoundingParams(builder).setBorderColor(gdhAttrs.getColor(indexCount3, 0));
                        } else if (indexCount3 == C2288R.styleable.GenericDraweeHierarchy_roundingBorderPadding) {
                            z = false;
                            getRoundingParams(builder).setPadding((float) gdhAttrs.getDimensionPixelSize(indexCount3, 0));
                        }
                    }
                    i2 = i + 1;
                    context2 = context;
                    AttributeSet attributeSet2 = attrs;
                }
                int i3 = i2;
                int i4 = indexCount;
                gdhAttrs.recycle();
                if (Build.VERSION.SDK_INT < 17 || context.getResources().getConfiguration().getLayoutDirection() != 1) {
                    roundTopLeft = roundTopLeft && roundTopStart;
                    roundTopRight = roundTopRight && roundTopEnd;
                    roundBottomRight = roundBottomRight && roundBottomEnd;
                    roundBottomLeft = roundBottomLeft && roundBottomStart;
                } else {
                    roundTopLeft = roundTopLeft && roundTopEnd;
                    roundTopRight = roundTopRight && roundTopStart;
                    roundBottomRight = roundBottomRight && roundBottomStart;
                    roundBottomLeft = roundBottomLeft && roundBottomEnd;
                }
            } catch (Throwable th2) {
                th = th2;
                z = false;
            }
        }
        if (builder.getProgressBarImage() != null && progressBarAutoRotateInterval > 0) {
            genericDraweeHierarchyBuilder.setProgressBarImage((Drawable) new AutoRotateDrawable(builder.getProgressBarImage(), progressBarAutoRotateInterval));
        }
        if (roundedCornerRadius > 0) {
            getRoundingParams(builder).setCornersRadii(roundTopLeft ? (float) roundedCornerRadius : 0.0f, roundTopRight ? (float) roundedCornerRadius : 0.0f, roundBottomRight ? (float) roundedCornerRadius : 0.0f, roundBottomLeft ? (float) roundedCornerRadius : 0.0f);
        }
        return genericDraweeHierarchyBuilder;
    }

    private static RoundingParams getRoundingParams(GenericDraweeHierarchyBuilder builder) {
        if (builder.getRoundingParams() == null) {
            builder.setRoundingParams(new RoundingParams());
        }
        return builder.getRoundingParams();
    }

    @Nullable
    private static Drawable getDrawable(Context context, TypedArray gdhAttrs, int attrId) {
        int resourceId = gdhAttrs.getResourceId(attrId, 0);
        if (resourceId == 0) {
            return null;
        }
        return context.getResources().getDrawable(resourceId);
    }

    @Nullable
    private static ScalingUtils.ScaleType getScaleTypeFromXml(TypedArray gdhAttrs, int attrId) {
        switch (gdhAttrs.getInt(attrId, -2)) {
            case -1:
                return null;
            case 0:
                return ScalingUtils.ScaleType.FIT_XY;
            case 1:
                return ScalingUtils.ScaleType.FIT_START;
            case 2:
                return ScalingUtils.ScaleType.FIT_CENTER;
            case 3:
                return ScalingUtils.ScaleType.FIT_END;
            case 4:
                return ScalingUtils.ScaleType.CENTER;
            case 5:
                return ScalingUtils.ScaleType.CENTER_INSIDE;
            case 6:
                return ScalingUtils.ScaleType.CENTER_CROP;
            case 7:
                return ScalingUtils.ScaleType.FOCUS_CROP;
            case 8:
                return ScalingUtils.ScaleType.FIT_BOTTOM_START;
            default:
                throw new RuntimeException("XML attribute not specified!");
        }
    }
}
