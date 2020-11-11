package com.rey.material.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import com.google.common.primitives.Ints;
import com.rey.material.C2500R;
import com.rey.material.app.DialogFragment;
import com.rey.material.drawable.BlankDrawable;
import com.rey.material.drawable.RippleDrawable;
import com.rey.material.util.ThemeUtil;
import com.rey.material.util.ViewUtil;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

public class Dialog extends android.app.Dialog {
    public static final int ACTION_NEGATIVE = ViewUtil.generateViewId();
    public static final int ACTION_NEUTRAL = ViewUtil.generateViewId();
    public static final int ACTION_POSITIVE = ViewUtil.generateViewId();
    public static final int TITLE = ViewUtil.generateViewId();
    protected int mActionHeight;
    protected int mActionMinWidth;
    protected int mActionOuterHeight;
    protected int mActionOuterPadding;
    protected int mActionPadding;
    /* access modifiers changed from: private */
    public boolean mCancelable;
    /* access modifiers changed from: private */
    public boolean mCanceledOnTouchOutside;
    /* access modifiers changed from: private */
    public DialogCardView mCardView;
    private ContainerFrameLayout mContainer;
    /* access modifiers changed from: private */
    public View mContent;
    protected int mContentPadding;
    protected int mDialogHorizontalPadding;
    protected int mDialogVerticalPadding;
    /* access modifiers changed from: private */
    public final Runnable mDismissAction;
    /* access modifiers changed from: private */
    public boolean mDismissPending;
    /* access modifiers changed from: private */
    public final Handler mHandler;
    protected int mInAnimationId;
    /* access modifiers changed from: private */
    public boolean mLayoutActionVertical;
    /* access modifiers changed from: private */
    public int mLayoutHeight;
    /* access modifiers changed from: private */
    public int mLayoutWidth;
    /* access modifiers changed from: private */
    public int mMaxHeight;
    /* access modifiers changed from: private */
    public int mMaxWidth;
    protected Button mNegativeAction;
    protected Button mNeutralAction;
    protected int mOutAnimationId;
    protected Button mPositiveAction;
    protected TextView mTitle;

    public Dialog(Context context) {
        this(context, C2500R.C2505style.Material_App_Dialog_Light);
    }

    public Dialog(Context context, int style) {
        super(context, style);
        this.mLayoutWidth = -2;
        this.mLayoutHeight = -2;
        this.mHandler = new Handler();
        this.mDismissAction = new Runnable() {
            public void run() {
                try {
                    Dialog.super.dismiss();
                } catch (IllegalArgumentException e) {
                }
            }
        };
        this.mLayoutActionVertical = false;
        this.mCancelable = true;
        this.mCanceledOnTouchOutside = true;
        this.mDismissPending = false;
        requestWindowFeature(1);
        getWindow().setBackgroundDrawable(BlankDrawable.getInstance());
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.width = -1;
        layout.height = -1;
        layout.windowAnimations = C2500R.C2505style.DialogNoAnimation;
        getWindow().setAttributes(layout);
        init(context, style);
    }

    private void init(Context context, int style) {
        this.mContentPadding = ThemeUtil.dpToPx(context, 24);
        this.mActionMinWidth = ThemeUtil.dpToPx(context, 64);
        this.mActionHeight = ThemeUtil.dpToPx(context, 36);
        this.mActionOuterHeight = ThemeUtil.dpToPx(context, 48);
        this.mActionPadding = ThemeUtil.dpToPx(context, 8);
        this.mActionOuterPadding = ThemeUtil.dpToPx(context, 16);
        this.mDialogHorizontalPadding = ThemeUtil.dpToPx(context, 40);
        this.mDialogVerticalPadding = ThemeUtil.dpToPx(context, 24);
        this.mCardView = new DialogCardView(context);
        this.mContainer = new ContainerFrameLayout(context);
        this.mTitle = new TextView(context);
        this.mPositiveAction = new Button(context);
        this.mNegativeAction = new Button(context);
        this.mNeutralAction = new Button(context);
        this.mCardView.setPreventCornerOverlap(false);
        this.mCardView.setUseCompatPadding(true);
        this.mTitle.setId(TITLE);
        this.mTitle.setGravity(8388611);
        TextView textView = this.mTitle;
        int i = this.mContentPadding;
        textView.setPadding(i, i, i, i - this.mActionPadding);
        this.mPositiveAction.setId(ACTION_POSITIVE);
        Button button = this.mPositiveAction;
        int i2 = this.mActionPadding;
        button.setPadding(i2, 0, i2, 0);
        this.mPositiveAction.setBackgroundResource(0);
        this.mNegativeAction.setId(ACTION_NEGATIVE);
        Button button2 = this.mNegativeAction;
        int i3 = this.mActionPadding;
        button2.setPadding(i3, 0, i3, 0);
        this.mNegativeAction.setBackgroundResource(0);
        this.mNeutralAction.setId(ACTION_NEUTRAL);
        Button button3 = this.mNeutralAction;
        int i4 = this.mActionPadding;
        button3.setPadding(i4, 0, i4, 0);
        this.mNeutralAction.setBackgroundResource(0);
        this.mContainer.addView(this.mCardView);
        this.mCardView.addView(this.mTitle);
        this.mCardView.addView(this.mPositiveAction);
        this.mCardView.addView(this.mNegativeAction);
        this.mCardView.addView(this.mNeutralAction);
        backgroundColor(ThemeUtil.windowBackground(context, -1));
        elevation((float) ThemeUtil.dpToPx(context, 4));
        cornerRadius((float) ThemeUtil.dpToPx(context, 2));
        dimAmount(0.5f);
        layoutDirection(3);
        titleTextAppearance(C2500R.C2505style.TextAppearance_AppCompat_Title);
        actionTextAppearance(C2500R.C2505style.TextAppearance_AppCompat_Button);
        dividerColor(503316480);
        dividerHeight(ThemeUtil.dpToPx(context, 1));
        cancelable(true);
        canceledOnTouchOutside(true);
        clearContent();
        onCreate();
        applyStyle(style);
        super.setContentView(this.mContainer);
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
    }

    public Dialog applyStyle(int resId) {
        int positiveActionTextAppearance;
        Context context = getContext();
        TypedArray a = context.obtainStyledAttributes(resId, C2500R.styleable.Dialog);
        int layout_width = this.mLayoutWidth;
        int layout_height = this.mLayoutHeight;
        boolean layoutParamsDefined = false;
        int titleTextAppearance = 0;
        int titleTextColor = 0;
        boolean titleTextColorDefined = false;
        int actionBackground = 0;
        int actionRipple = 0;
        int actionTextAppearance = 0;
        ColorStateList actionTextColors = null;
        int positiveActionBackground = 0;
        int positiveActionRipple = 0;
        int positiveActionTextAppearance2 = 0;
        Context context2 = context;
        int count = a.getIndexCount();
        ColorStateList positiveActionTextColors = null;
        int negativeActionBackground = 0;
        int negativeActionRipple = 0;
        int negativeActionTextAppearance = 0;
        ColorStateList negativeActionTextColors = null;
        int neutralActionBackground = 0;
        int neutralActionRipple = 0;
        int neutralActionTextAppearance = 0;
        ColorStateList neutralActionTextColors = null;
        int i = 0;
        while (i < count) {
            int count2 = count;
            int count3 = a.getIndex(i);
            int positiveActionRipple2 = positiveActionRipple;
            int positiveActionBackground2 = positiveActionBackground;
            if (count3 == C2500R.styleable.Dialog_android_layout_width) {
                layout_width = a.getLayoutDimension(count3, -2);
                layoutParamsDefined = true;
                positiveActionRipple = positiveActionRipple2;
                positiveActionBackground = positiveActionBackground2;
            } else if (count3 == C2500R.styleable.Dialog_android_layout_height) {
                layout_height = a.getLayoutDimension(count3, -2);
                layoutParamsDefined = true;
                positiveActionRipple = positiveActionRipple2;
                positiveActionBackground = positiveActionBackground2;
            } else {
                if (count3 == C2500R.styleable.Dialog_di_maxWidth) {
                    maxWidth(a.getDimensionPixelOffset(count3, 0));
                } else if (count3 == C2500R.styleable.Dialog_di_maxHeight) {
                    maxHeight(a.getDimensionPixelOffset(count3, 0));
                } else if (count3 == C2500R.styleable.Dialog_di_dimAmount) {
                    dimAmount(a.getFloat(count3, 0.0f));
                } else if (count3 == C2500R.styleable.Dialog_di_backgroundColor) {
                    backgroundColor(a.getColor(count3, 0));
                } else if (count3 == C2500R.styleable.Dialog_di_maxElevation) {
                    maxElevation((float) a.getDimensionPixelOffset(count3, 0));
                } else if (count3 == C2500R.styleable.Dialog_di_elevation) {
                    elevation((float) a.getDimensionPixelOffset(count3, 0));
                } else if (count3 == C2500R.styleable.Dialog_di_cornerRadius) {
                    cornerRadius((float) a.getDimensionPixelOffset(count3, 0));
                } else if (count3 == C2500R.styleable.Dialog_di_layoutDirection) {
                    layoutDirection(a.getInteger(count3, 0));
                } else if (count3 == C2500R.styleable.Dialog_di_titleTextAppearance) {
                    titleTextAppearance = a.getResourceId(count3, 0);
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_titleTextColor) {
                    titleTextColor = a.getColor(count3, 0);
                    titleTextColorDefined = true;
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_actionBackground) {
                    actionBackground = a.getResourceId(count3, 0);
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_actionRipple) {
                    actionRipple = a.getResourceId(count3, 0);
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_actionTextAppearance) {
                    actionTextAppearance = a.getResourceId(count3, 0);
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_actionTextColor) {
                    actionTextColors = a.getColorStateList(count3);
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_positiveActionBackground) {
                    positiveActionBackground = a.getResourceId(count3, 0);
                    positiveActionRipple = positiveActionRipple2;
                } else if (count3 == C2500R.styleable.Dialog_di_positiveActionRipple) {
                    positiveActionRipple = a.getResourceId(count3, 0);
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_positiveActionTextAppearance) {
                    positiveActionTextAppearance2 = a.getResourceId(count3, 0);
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_positiveActionTextColor) {
                    positiveActionTextColors = a.getColorStateList(count3);
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_negativeActionBackground) {
                    negativeActionBackground = a.getResourceId(count3, 0);
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_negativeActionRipple) {
                    negativeActionRipple = a.getResourceId(count3, 0);
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_negativeActionTextAppearance) {
                    negativeActionTextAppearance = a.getResourceId(count3, 0);
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_negativeActionTextColor) {
                    negativeActionTextColors = a.getColorStateList(count3);
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_neutralActionBackground) {
                    neutralActionBackground = a.getResourceId(count3, 0);
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_neutralActionRipple) {
                    neutralActionRipple = a.getResourceId(count3, 0);
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_neutralActionTextAppearance) {
                    neutralActionTextAppearance = a.getResourceId(count3, 0);
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_neutralActionTextColor) {
                    neutralActionTextColors = a.getColorStateList(count3);
                    positiveActionRipple = positiveActionRipple2;
                    positiveActionBackground = positiveActionBackground2;
                } else if (count3 == C2500R.styleable.Dialog_di_inAnimation) {
                    inAnimation(a.getResourceId(count3, 0));
                } else if (count3 == C2500R.styleable.Dialog_di_outAnimation) {
                    outAnimation(a.getResourceId(count3, 0));
                } else if (count3 == C2500R.styleable.Dialog_di_dividerColor) {
                    dividerColor(a.getColor(count3, 0));
                } else if (count3 == C2500R.styleable.Dialog_di_dividerHeight) {
                    dividerHeight(a.getDimensionPixelOffset(count3, 0));
                } else if (count3 == C2500R.styleable.Dialog_di_cancelable) {
                    cancelable(a.getBoolean(count3, true));
                } else if (count3 == C2500R.styleable.Dialog_di_canceledOnTouchOutside) {
                    canceledOnTouchOutside(a.getBoolean(count3, true));
                }
                positiveActionRipple = positiveActionRipple2;
                positiveActionBackground = positiveActionBackground2;
            }
            i++;
            count = count2;
        }
        int positiveActionBackground3 = positiveActionBackground;
        int positiveActionRipple3 = positiveActionRipple;
        a.recycle();
        if (layoutParamsDefined) {
            layoutParams(layout_width, layout_height);
        }
        if (titleTextAppearance != 0) {
            titleTextAppearance(titleTextAppearance);
        }
        if (titleTextColorDefined) {
            titleColor(titleTextColor);
        }
        if (actionBackground != 0) {
            actionBackground(actionBackground);
        }
        if (actionRipple != 0) {
            actionRipple(actionRipple);
        }
        if (actionTextAppearance != 0) {
            actionTextAppearance(actionTextAppearance);
        }
        if (actionTextColors != null) {
            actionTextColor(actionTextColors);
        }
        if (positiveActionBackground3 != 0) {
            positiveActionBackground(positiveActionBackground3);
        }
        if (positiveActionRipple3 != 0) {
            positiveActionRipple(positiveActionRipple3);
        }
        if (positiveActionTextAppearance2 != 0) {
            positiveActionTextAppearance = positiveActionTextAppearance2;
            positiveActionTextAppearance(positiveActionTextAppearance);
        } else {
            positiveActionTextAppearance = positiveActionTextAppearance2;
        }
        ColorStateList positiveActionTextColors2 = positiveActionTextColors;
        if (positiveActionTextColors2 != null) {
            positiveActionTextColor(positiveActionTextColors2);
        }
        int i2 = positiveActionTextAppearance;
        int negativeActionBackground2 = negativeActionBackground;
        if (negativeActionBackground2 != 0) {
            negativeActionBackground(negativeActionBackground2);
        }
        int i3 = negativeActionBackground2;
        int negativeActionBackground3 = negativeActionRipple;
        if (negativeActionBackground3 != 0) {
            negativeActionRipple(negativeActionBackground3);
        }
        int i4 = negativeActionBackground3;
        int negativeActionTextAppearance2 = negativeActionTextAppearance;
        if (negativeActionTextAppearance2 != 0) {
            negativeActionTextAppearance(negativeActionTextAppearance2);
        }
        int i5 = negativeActionTextAppearance2;
        ColorStateList negativeActionTextColors2 = negativeActionTextColors;
        if (negativeActionTextColors2 != null) {
            negativeActionTextColor(negativeActionTextColors2);
        }
        ColorStateList colorStateList = negativeActionTextColors2;
        int neutralActionBackground2 = neutralActionBackground;
        if (neutralActionBackground2 != 0) {
            neutralActionBackground(neutralActionBackground2);
        }
        int i6 = neutralActionBackground2;
        int neutralActionRipple2 = neutralActionRipple;
        if (neutralActionRipple2 != 0) {
            neutralActionRipple(neutralActionRipple2);
        }
        int i7 = neutralActionRipple2;
        int neutralActionTextAppearance2 = neutralActionTextAppearance;
        if (neutralActionTextAppearance2 != 0) {
            neutralActionTextAppearance(neutralActionTextAppearance2);
        }
        int i8 = neutralActionTextAppearance2;
        ColorStateList neutralActionTextColors2 = neutralActionTextColors;
        if (neutralActionTextColors2 != null) {
            neutralActionTextColor(neutralActionTextColors2);
        }
        return this;
    }

    public Dialog clearContent() {
        title(0);
        positiveAction(0);
        positiveActionClickListener((View.OnClickListener) null);
        negativeAction(0);
        negativeActionClickListener((View.OnClickListener) null);
        neutralAction(0);
        neutralActionClickListener((View.OnClickListener) null);
        contentView((View) null);
        return this;
    }

    public Dialog layoutParams(int width, int height) {
        this.mLayoutWidth = width;
        this.mLayoutHeight = height;
        return this;
    }

    public Dialog maxWidth(int width) {
        this.mMaxWidth = width;
        return this;
    }

    public Dialog maxHeight(int height) {
        this.mMaxHeight = height;
        return this;
    }

    public Dialog dimAmount(float amount) {
        Window window = getWindow();
        if (amount > 0.0f) {
            window.addFlags(2);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.dimAmount = amount;
            window.setAttributes(lp);
        } else {
            window.clearFlags(2);
        }
        return this;
    }

    public Dialog backgroundColor(int color) {
        this.mCardView.setCardBackgroundColor(color);
        return this;
    }

    public Dialog elevation(float elevation) {
        if (this.mCardView.getMaxCardElevation() < elevation) {
            this.mCardView.setMaxCardElevation(elevation);
        }
        this.mCardView.setCardElevation(elevation);
        return this;
    }

    public Dialog maxElevation(float elevation) {
        this.mCardView.setMaxCardElevation(elevation);
        return this;
    }

    public Dialog cornerRadius(float radius) {
        this.mCardView.setRadius(radius);
        return this;
    }

    public Dialog dividerColor(int color) {
        this.mCardView.setDividerColor(color);
        return this;
    }

    public Dialog dividerHeight(int height) {
        this.mCardView.setDividerHeight(height);
        return this;
    }

    public Dialog title(CharSequence title) {
        this.mTitle.setText(title);
        this.mTitle.setVisibility(TextUtils.isEmpty(title) ? 8 : 0);
        return this;
    }

    public Dialog title(int id) {
        return title((CharSequence) id == 0 ? null : getContext().getResources().getString(id));
    }

    public void setTitle(CharSequence title) {
        title(title);
    }

    public void setTitle(int titleId) {
        title(titleId);
    }

    public Dialog titleColor(int color) {
        this.mTitle.setTextColor(color);
        return this;
    }

    public Dialog titleTextAppearance(int resId) {
        this.mTitle.setTextAppearance(getContext(), resId);
        return this;
    }

    public Dialog actionBackground(int id) {
        positiveActionBackground(id);
        negativeActionBackground(id);
        neutralActionBackground(id);
        return this;
    }

    public Dialog actionBackground(Drawable drawable) {
        positiveActionBackground(drawable);
        negativeActionBackground(drawable);
        neutralActionBackground(drawable);
        return this;
    }

    public Dialog actionRipple(int resId) {
        positiveActionRipple(resId);
        negativeActionRipple(resId);
        neutralActionRipple(resId);
        return this;
    }

    public Dialog actionTextAppearance(int resId) {
        positiveActionTextAppearance(resId);
        negativeActionTextAppearance(resId);
        neutralActionTextAppearance(resId);
        return this;
    }

    public Dialog actionTextColor(ColorStateList color) {
        positiveActionTextColor(color);
        negativeActionTextColor(color);
        neutralActionTextColor(color);
        return this;
    }

    public Dialog actionTextColor(int color) {
        positiveActionTextColor(color);
        negativeActionTextColor(color);
        neutralActionTextColor(color);
        return this;
    }

    public Dialog positiveAction(CharSequence action) {
        this.mPositiveAction.setText(action);
        this.mPositiveAction.setVisibility(TextUtils.isEmpty(action) ? 8 : 0);
        return this;
    }

    public Dialog positiveAction(int id) {
        return positiveAction((CharSequence) id == 0 ? null : getContext().getResources().getString(id));
    }

    public Dialog positiveActionBackground(Drawable drawable) {
        ViewUtil.setBackground(this.mPositiveAction, drawable);
        return this;
    }

    public Dialog positiveActionBackground(int id) {
        return positiveActionBackground(id == 0 ? null : getContext().getResources().getDrawable(id));
    }

    public Dialog positiveActionRipple(int resId) {
        return positiveActionBackground((Drawable) new RippleDrawable.Builder(getContext(), resId).build());
    }

    public Dialog positiveActionTextAppearance(int resId) {
        this.mPositiveAction.setTextAppearance(getContext(), resId);
        return this;
    }

    public Dialog positiveActionTextColor(ColorStateList color) {
        this.mPositiveAction.setTextColor(color);
        return this;
    }

    public Dialog positiveActionTextColor(int color) {
        this.mPositiveAction.setTextColor(color);
        return this;
    }

    public Dialog positiveActionClickListener(View.OnClickListener listener) {
        this.mPositiveAction.setOnClickListener(listener);
        return this;
    }

    public Dialog negativeAction(CharSequence action) {
        this.mNegativeAction.setText(action);
        this.mNegativeAction.setVisibility(TextUtils.isEmpty(action) ? 8 : 0);
        return this;
    }

    public Dialog negativeAction(int id) {
        return negativeAction((CharSequence) id == 0 ? null : getContext().getResources().getString(id));
    }

    public Dialog negativeActionBackground(Drawable drawable) {
        ViewUtil.setBackground(this.mNegativeAction, drawable);
        return this;
    }

    public Dialog negativeActionBackground(int id) {
        return negativeActionBackground(id == 0 ? null : getContext().getResources().getDrawable(id));
    }

    public Dialog negativeActionRipple(int resId) {
        return negativeActionBackground((Drawable) new RippleDrawable.Builder(getContext(), resId).build());
    }

    public Dialog negativeActionTextAppearance(int resId) {
        this.mNegativeAction.setTextAppearance(getContext(), resId);
        return this;
    }

    public Dialog negativeActionTextColor(ColorStateList color) {
        this.mNegativeAction.setTextColor(color);
        return this;
    }

    public Dialog negativeActionTextColor(int color) {
        this.mNegativeAction.setTextColor(color);
        return this;
    }

    public Dialog negativeActionClickListener(View.OnClickListener listener) {
        this.mNegativeAction.setOnClickListener(listener);
        return this;
    }

    public Dialog neutralAction(CharSequence action) {
        this.mNeutralAction.setText(action);
        this.mNeutralAction.setVisibility(TextUtils.isEmpty(action) ? 8 : 0);
        return this;
    }

    public Dialog neutralAction(int id) {
        return neutralAction((CharSequence) id == 0 ? null : getContext().getResources().getString(id));
    }

    public Dialog neutralActionBackground(Drawable drawable) {
        ViewUtil.setBackground(this.mNeutralAction, drawable);
        return this;
    }

    public Dialog neutralActionBackground(int id) {
        return neutralActionBackground(id == 0 ? null : getContext().getResources().getDrawable(id));
    }

    public Dialog neutralActionRipple(int resId) {
        return neutralActionBackground((Drawable) new RippleDrawable.Builder(getContext(), resId).build());
    }

    public Dialog neutralActionTextAppearance(int resId) {
        this.mNeutralAction.setTextAppearance(getContext(), resId);
        return this;
    }

    public Dialog neutralActionTextColor(ColorStateList color) {
        this.mNeutralAction.setTextColor(color);
        return this;
    }

    public Dialog neutralActionTextColor(int color) {
        this.mNeutralAction.setTextColor(color);
        return this;
    }

    public Dialog neutralActionClickListener(View.OnClickListener listener) {
        this.mNeutralAction.setOnClickListener(listener);
        return this;
    }

    public Dialog layoutDirection(int direction) {
        ViewCompat.setLayoutDirection(this.mCardView, direction);
        return this;
    }

    public Dialog inAnimation(int resId) {
        this.mInAnimationId = resId;
        return this;
    }

    public Dialog outAnimation(int resId) {
        this.mOutAnimationId = resId;
        return this;
    }

    public Dialog showDivider(boolean show) {
        this.mCardView.setShowDivider(show);
        return this;
    }

    public Dialog contentView(View v) {
        View view = this.mContent;
        if (view != v) {
            if (view != null) {
                this.mCardView.removeView(view);
            }
            this.mContent = v;
        }
        View view2 = this.mContent;
        if (view2 != null) {
            this.mCardView.addView(view2);
        }
        return this;
    }

    public Dialog contentView(int layoutId) {
        if (layoutId == 0) {
            return this;
        }
        return contentView(LayoutInflater.from(getContext()).inflate(layoutId, (ViewGroup) null));
    }

    public Dialog cancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        this.mCancelable = cancelable;
        return this;
    }

    public Dialog canceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        this.mCanceledOnTouchOutside = cancel;
        return this;
    }

    public Dialog contentMargin(int margin) {
        this.mCardView.setContentMargin(margin);
        return this;
    }

    public Dialog contentMargin(int left, int top, int right, int bottom) {
        this.mCardView.setContentMargin(left, top, right, bottom);
        return this;
    }

    public void setCancelable(boolean flag) {
        cancelable(flag);
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        canceledOnTouchOutside(cancel);
    }

    public void setContentView(View v) {
        contentView(v);
    }

    public void setContentView(int layoutId) {
        contentView(layoutId);
    }

    public void setContentView(View v, ViewGroup.LayoutParams params) {
        contentView(v);
    }

    public void addContentView(View view, ViewGroup.LayoutParams params) {
        contentView(view);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.mCardView.setVisibility(0);
        if (this.mInAnimationId != 0) {
            this.mCardView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    Dialog.this.mCardView.getViewTreeObserver().removeOnPreDrawListener(this);
                    Dialog.this.mCardView.startAnimation(AnimationUtils.loadAnimation(Dialog.this.mCardView.getContext(), Dialog.this.mInAnimationId));
                    return false;
                }
            });
        }
    }

    public void dismissImmediately() {
        super.dismiss();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mDismissAction);
        }
    }

    public void dismiss() {
        if (isShowing() && !this.mDismissPending) {
            if (this.mOutAnimationId != 0) {
                Animation anim = AnimationUtils.loadAnimation(this.mContainer.getContext(), this.mOutAnimationId);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                        boolean unused = Dialog.this.mDismissPending = true;
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        boolean unused = Dialog.this.mDismissPending = false;
                        Dialog.this.mCardView.setVisibility(8);
                        Dialog.this.mHandler.post(Dialog.this.mDismissAction);
                    }
                });
                this.mCardView.startAnimation(anim);
                return;
            }
            this.mHandler.post(this.mDismissAction);
        }
    }

    private class ContainerFrameLayout extends FrameLayout {
        private boolean mClickOutside = false;

        public ContainerFrameLayout(Context context) {
            super(context);
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
            Dialog.this.mCardView.measure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(widthSize, heightSize);
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean changed, int left, int top, int right, int bottom) {
            int childLeft = ((right - left) - Dialog.this.mCardView.getMeasuredWidth()) / 2;
            int childTop = ((bottom - top) - Dialog.this.mCardView.getMeasuredHeight()) / 2;
            Dialog.this.mCardView.layout(childLeft, childTop, Dialog.this.mCardView.getMeasuredWidth() + childLeft, Dialog.this.mCardView.getMeasuredHeight() + childTop);
        }

        private boolean isOutsideDialog(float x, float y) {
            return x < ((float) (Dialog.this.mCardView.getLeft() + Dialog.this.mCardView.getPaddingLeft())) || x > ((float) (Dialog.this.mCardView.getRight() - Dialog.this.mCardView.getPaddingRight())) || y < ((float) (Dialog.this.mCardView.getTop() + Dialog.this.mCardView.getPaddingTop())) || y > ((float) (Dialog.this.mCardView.getBottom() - Dialog.this.mCardView.getPaddingBottom()));
        }

        public boolean onTouchEvent(MotionEvent event) {
            if (super.onTouchEvent(event)) {
                return true;
            }
            int action = event.getAction();
            if (action != 0) {
                if (action != 1) {
                    if (action == 2) {
                        return this.mClickOutside;
                    }
                    if (action != 3) {
                        return false;
                    }
                    this.mClickOutside = false;
                    return false;
                } else if (!this.mClickOutside || !isOutsideDialog(event.getX(), event.getY())) {
                    return false;
                } else {
                    this.mClickOutside = false;
                    if (Dialog.this.mCancelable && Dialog.this.mCanceledOnTouchOutside) {
                        Dialog.this.dismiss();
                    }
                    return true;
                }
            } else if (!isOutsideDialog(event.getX(), event.getY())) {
                return false;
            } else {
                this.mClickOutside = true;
                return true;
            }
        }
    }

    private class DialogCardView extends CardView {
        private int mContentMarginBottom;
        private int mContentMarginLeft;
        private int mContentMarginRight;
        private int mContentMarginTop;
        private Paint mDividerPaint;
        private float mDividerPos = -1.0f;
        private boolean mIsRtl = false;
        private boolean mShowDivider = false;

        public DialogCardView(Context context) {
            super(context);
            Paint paint = new Paint(1);
            this.mDividerPaint = paint;
            paint.setStyle(Paint.Style.STROKE);
            setWillNotDraw(false);
        }

        public void setContentMargin(int margin) {
            setContentMargin(margin, margin, margin, margin);
        }

        public void setContentMargin(int left, int top, int right, int bottom) {
            this.mContentMarginLeft = left;
            this.mContentMarginTop = top;
            this.mContentMarginRight = right;
            this.mContentMarginBottom = bottom;
        }

        public void setDividerColor(int color) {
            this.mDividerPaint.setColor(color);
            invalidate();
        }

        public void setDividerHeight(int height) {
            this.mDividerPaint.setStrokeWidth((float) height);
            invalidate();
        }

        public void setShowDivider(boolean show) {
            if (this.mShowDivider != show) {
                this.mShowDivider = show;
                invalidate();
            }
        }

        public void onRtlPropertiesChanged(int layoutDirection) {
            boolean rtl = true;
            if (layoutDirection != 1) {
                rtl = false;
            }
            if (this.mIsRtl != rtl) {
                this.mIsRtl = rtl;
                if (Build.VERSION.SDK_INT >= 17) {
                    int direction = this.mIsRtl ? 4 : 3;
                    Dialog.this.mTitle.setTextDirection(direction);
                    Dialog.this.mPositiveAction.setTextDirection(direction);
                    Dialog.this.mNegativeAction.setTextDirection(direction);
                    Dialog.this.mNeutralAction.setTextDirection(direction);
                }
                requestLayout();
            }
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int heightMs;
            int nonContentHeight;
            int positiveActionWidth;
            int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
            int paddingLeft = Math.max(Dialog.this.mDialogHorizontalPadding, Dialog.this.mCardView.getPaddingLeft());
            int paddingRight = Math.max(Dialog.this.mDialogHorizontalPadding, Dialog.this.mCardView.getPaddingRight());
            int paddingTop = Math.max(Dialog.this.mDialogVerticalPadding, Dialog.this.mCardView.getPaddingTop());
            int paddingBottom = Math.max(Dialog.this.mDialogVerticalPadding, Dialog.this.mCardView.getPaddingBottom());
            int maxWidth = (widthSize - paddingLeft) - paddingRight;
            if (Dialog.this.mMaxWidth > 0) {
                maxWidth = Math.min(maxWidth, Dialog.this.mMaxWidth);
            }
            int maxHeight = (heightSize - paddingTop) - paddingBottom;
            if (Dialog.this.mMaxHeight > 0) {
                maxHeight = Math.min(maxHeight, Dialog.this.mMaxHeight);
            }
            int width = Dialog.this.mLayoutWidth == -1 ? maxWidth : Dialog.this.mLayoutWidth;
            int height = Dialog.this.mLayoutHeight == -1 ? maxHeight : Dialog.this.mLayoutHeight;
            int titleWidth = 0;
            int titleHeight = 0;
            if (Dialog.this.mTitle.getVisibility() == 0) {
                Dialog.this.mTitle.measure(View.MeasureSpec.makeMeasureSpec(width == -2 ? maxWidth : width, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(maxHeight, Integer.MIN_VALUE));
                titleWidth = Dialog.this.mTitle.getMeasuredWidth();
                titleHeight = Dialog.this.mTitle.getMeasuredHeight();
            }
            int contentWidth = 0;
            int contentHeight = 0;
            if (Dialog.this.mContent != null) {
                int i = widthSize;
                int i2 = heightSize;
                Dialog.this.mContent.measure(View.MeasureSpec.makeMeasureSpec(((width == -2 ? maxWidth : width) - this.mContentMarginLeft) - this.mContentMarginRight, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec((maxHeight - this.mContentMarginTop) - this.mContentMarginBottom, Integer.MIN_VALUE));
                contentWidth = Dialog.this.mContent.getMeasuredWidth();
                contentHeight = Dialog.this.mContent.getMeasuredHeight();
            } else {
                int i3 = heightSize;
            }
            int visibleActions = 0;
            if (Dialog.this.mPositiveAction.getVisibility() == 0) {
                int widthMs = View.MeasureSpec.makeMeasureSpec(0, 0);
                int i4 = paddingLeft;
                int heightMs2 = View.MeasureSpec.makeMeasureSpec(Dialog.this.mActionHeight, Ints.MAX_POWER_OF_TWO);
                Dialog.this.mPositiveAction.measure(widthMs, heightMs2);
                int positiveActionWidth2 = Dialog.this.mPositiveAction.getMeasuredWidth();
                int i5 = paddingRight;
                if (positiveActionWidth2 < Dialog.this.mActionMinWidth) {
                    int i6 = positiveActionWidth2;
                    int i7 = paddingTop;
                    Dialog.this.mPositiveAction.measure(View.MeasureSpec.makeMeasureSpec(Dialog.this.mActionMinWidth, Ints.MAX_POWER_OF_TWO), heightMs2);
                    positiveActionWidth = Dialog.this.mActionMinWidth;
                } else {
                    positiveActionWidth = positiveActionWidth2;
                    int i8 = paddingTop;
                }
                visibleActions = 0 + 1;
                heightMs = positiveActionWidth;
            } else {
                int i9 = paddingRight;
                int i10 = paddingTop;
                heightMs = 0;
            }
            int negativeActionWidth = 0;
            if (Dialog.this.mNegativeAction.getVisibility() == 0) {
                int widthMs2 = View.MeasureSpec.makeMeasureSpec(0, 0);
                int heightMs3 = View.MeasureSpec.makeMeasureSpec(Dialog.this.mActionHeight, Ints.MAX_POWER_OF_TWO);
                Dialog.this.mNegativeAction.measure(widthMs2, heightMs3);
                negativeActionWidth = Dialog.this.mNegativeAction.getMeasuredWidth();
                if (negativeActionWidth < Dialog.this.mActionMinWidth) {
                    int i11 = negativeActionWidth;
                    int i12 = widthMs2;
                    Dialog.this.mNegativeAction.measure(View.MeasureSpec.makeMeasureSpec(Dialog.this.mActionMinWidth, Ints.MAX_POWER_OF_TWO), heightMs3);
                    negativeActionWidth = Dialog.this.mActionMinWidth;
                } else {
                    int i13 = negativeActionWidth;
                    int i14 = widthMs2;
                }
                visibleActions++;
            }
            int neutralActionWidth = 0;
            if (Dialog.this.mNeutralAction.getVisibility() == 0) {
                int widthMs3 = View.MeasureSpec.makeMeasureSpec(0, 0);
                int heightMs4 = View.MeasureSpec.makeMeasureSpec(Dialog.this.mActionHeight, Ints.MAX_POWER_OF_TWO);
                Dialog.this.mNeutralAction.measure(widthMs3, heightMs4);
                neutralActionWidth = Dialog.this.mNeutralAction.getMeasuredWidth();
                int i15 = paddingBottom;
                if (neutralActionWidth < Dialog.this.mActionMinWidth) {
                    int i16 = neutralActionWidth;
                    int i17 = widthMs3;
                    Dialog.this.mNeutralAction.measure(View.MeasureSpec.makeMeasureSpec(Dialog.this.mActionMinWidth, Ints.MAX_POWER_OF_TWO), heightMs4);
                    neutralActionWidth = Dialog.this.mActionMinWidth;
                } else {
                    int i18 = neutralActionWidth;
                    int i19 = widthMs3;
                }
                visibleActions++;
            } else {
                int i20 = paddingBottom;
            }
            int i21 = heightMs;
            int positiveActionWidth3 = 0;
            int actionBarWidth = heightMs + negativeActionWidth + neutralActionWidth + (Dialog.this.mActionOuterPadding * 2) + (Dialog.this.mActionPadding * Math.max(0, visibleActions - 1));
            if (width == -2) {
                width = Math.min(maxWidth, Math.max(titleWidth, Math.max(this.mContentMarginLeft + contentWidth + this.mContentMarginRight, actionBarWidth)));
            }
            boolean unused = Dialog.this.mLayoutActionVertical = actionBarWidth > width;
            int nonContentHeight2 = (visibleActions > 0 ? Dialog.this.mActionPadding : 0) + titleHeight + this.mContentMarginTop + this.mContentMarginBottom;
            if (Dialog.this.mLayoutActionVertical) {
                nonContentHeight = nonContentHeight2 + (Dialog.this.mActionOuterHeight * visibleActions);
            } else {
                if (visibleActions > 0) {
                    positiveActionWidth3 = Dialog.this.mActionOuterHeight;
                }
                nonContentHeight = nonContentHeight2 + positiveActionWidth3;
            }
            if (height == -2) {
                height = Math.min(maxHeight, contentHeight + nonContentHeight);
            }
            if (Dialog.this.mContent != null) {
                int i22 = visibleActions;
                int i23 = negativeActionWidth;
                Dialog.this.mContent.measure(View.MeasureSpec.makeMeasureSpec((width - this.mContentMarginLeft) - this.mContentMarginRight, Ints.MAX_POWER_OF_TWO), View.MeasureSpec.makeMeasureSpec(height - nonContentHeight, Ints.MAX_POWER_OF_TWO));
            } else {
                int i24 = negativeActionWidth;
            }
            setMeasuredDimension(getPaddingLeft() + width + getPaddingRight(), getPaddingTop() + height + getPaddingBottom());
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean changed, int left, int top, int right, int bottom) {
            int childLeft = 0 + getPaddingLeft();
            int childTop = 0 + getPaddingTop();
            int childRight = (right - left) - getPaddingRight();
            int childBottom = (bottom - top) - getPaddingBottom();
            if (Dialog.this.mTitle.getVisibility() == 0) {
                if (this.mIsRtl) {
                    Dialog.this.mTitle.layout(childRight - Dialog.this.mTitle.getMeasuredWidth(), childTop, childRight, Dialog.this.mTitle.getMeasuredHeight() + childTop);
                } else {
                    Dialog.this.mTitle.layout(childLeft, childTop, Dialog.this.mTitle.getMeasuredWidth() + childLeft, Dialog.this.mTitle.getMeasuredHeight() + childTop);
                }
                childTop += Dialog.this.mTitle.getMeasuredHeight();
            }
            boolean hasAction = Dialog.this.mNeutralAction.getVisibility() == 0 || Dialog.this.mNegativeAction.getVisibility() == 0 || Dialog.this.mPositiveAction.getVisibility() == 0;
            if (hasAction) {
                childBottom -= Dialog.this.mActionPadding;
            }
            int temp = (Dialog.this.mActionOuterHeight - Dialog.this.mActionHeight) / 2;
            if (hasAction) {
                if (Dialog.this.mLayoutActionVertical) {
                    if (Dialog.this.mNeutralAction.getVisibility() == 0) {
                        Dialog.this.mNeutralAction.layout((childRight - Dialog.this.mActionOuterPadding) - Dialog.this.mNeutralAction.getMeasuredWidth(), (childBottom - Dialog.this.mActionOuterHeight) + temp, childRight - Dialog.this.mActionOuterPadding, childBottom - temp);
                        childBottom -= Dialog.this.mActionOuterHeight;
                    }
                    if (Dialog.this.mNegativeAction.getVisibility() == 0) {
                        Dialog.this.mNegativeAction.layout((childRight - Dialog.this.mActionOuterPadding) - Dialog.this.mNegativeAction.getMeasuredWidth(), (childBottom - Dialog.this.mActionOuterHeight) + temp, childRight - Dialog.this.mActionOuterPadding, childBottom - temp);
                        childBottom -= Dialog.this.mActionOuterHeight;
                    }
                    if (Dialog.this.mPositiveAction.getVisibility() == 0) {
                        Dialog.this.mPositiveAction.layout((childRight - Dialog.this.mActionOuterPadding) - Dialog.this.mPositiveAction.getMeasuredWidth(), (childBottom - Dialog.this.mActionOuterHeight) + temp, childRight - Dialog.this.mActionOuterPadding, childBottom - temp);
                        childBottom -= Dialog.this.mActionOuterHeight;
                    }
                } else {
                    int actionLeft = Dialog.this.mActionOuterPadding + childLeft;
                    int actionRight = childRight - Dialog.this.mActionOuterPadding;
                    int actionTop = (childBottom - Dialog.this.mActionOuterHeight) + temp;
                    int actionBottom = childBottom - temp;
                    if (this.mIsRtl) {
                        if (Dialog.this.mPositiveAction.getVisibility() == 0) {
                            Dialog.this.mPositiveAction.layout(actionLeft, actionTop, Dialog.this.mPositiveAction.getMeasuredWidth() + actionLeft, actionBottom);
                            actionLeft += Dialog.this.mPositiveAction.getMeasuredWidth() + Dialog.this.mActionPadding;
                        }
                        if (Dialog.this.mNegativeAction.getVisibility() == 0) {
                            Dialog.this.mNegativeAction.layout(actionLeft, actionTop, Dialog.this.mNegativeAction.getMeasuredWidth() + actionLeft, actionBottom);
                        }
                        if (Dialog.this.mNeutralAction.getVisibility() == 0) {
                            Dialog.this.mNeutralAction.layout(actionRight - Dialog.this.mNeutralAction.getMeasuredWidth(), actionTop, actionRight, actionBottom);
                        }
                    } else {
                        if (Dialog.this.mPositiveAction.getVisibility() == 0) {
                            Dialog.this.mPositiveAction.layout(actionRight - Dialog.this.mPositiveAction.getMeasuredWidth(), actionTop, actionRight, actionBottom);
                            actionRight -= Dialog.this.mPositiveAction.getMeasuredWidth() + Dialog.this.mActionPadding;
                        }
                        if (Dialog.this.mNegativeAction.getVisibility() == 0) {
                            Dialog.this.mNegativeAction.layout(actionRight - Dialog.this.mNegativeAction.getMeasuredWidth(), actionTop, actionRight, actionBottom);
                        }
                        if (Dialog.this.mNeutralAction.getVisibility() == 0) {
                            Dialog.this.mNeutralAction.layout(actionLeft, actionTop, Dialog.this.mNeutralAction.getMeasuredWidth() + actionLeft, actionBottom);
                        }
                    }
                    childBottom -= Dialog.this.mActionOuterHeight;
                }
            }
            this.mDividerPos = ((float) childBottom) - (this.mDividerPaint.getStrokeWidth() / 2.0f);
            if (Dialog.this.mContent != null) {
                Dialog.this.mContent.layout(this.mContentMarginLeft + childLeft, this.mContentMarginTop + childTop, childRight - this.mContentMarginRight, childBottom - this.mContentMarginBottom);
            }
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (!this.mShowDivider) {
                return;
            }
            if (Dialog.this.mPositiveAction.getVisibility() == 0 || Dialog.this.mNegativeAction.getVisibility() == 0 || Dialog.this.mNeutralAction.getVisibility() == 0) {
                canvas.drawLine((float) getPaddingLeft(), this.mDividerPos, (float) (getWidth() - getPaddingRight()), this.mDividerPos, this.mDividerPaint);
            }
        }
    }

    public static class Builder implements DialogFragment.Builder, Parcelable {
        public static final Parcelable.Creator<Builder> CREATOR = new Parcelable.Creator<Builder>() {
            public Builder createFromParcel(Parcel in) {
                return new Builder(in);
            }

            public Builder[] newArray(int size) {
                return new Builder[size];
            }
        };
        protected int mContentViewId;
        protected Dialog mDialog;
        protected CharSequence mNegative;
        protected CharSequence mNeutral;
        protected CharSequence mPositive;
        protected int mStyleId;
        protected CharSequence mTitle;

        public Builder() {
            this(C2500R.C2505style.Material_App_Dialog_Light);
        }

        public Builder(int styleId) {
            this.mStyleId = styleId;
        }

        public Builder style(int styleId) {
            this.mStyleId = styleId;
            return this;
        }

        public Builder contentView(int layoutId) {
            this.mContentViewId = layoutId;
            return this;
        }

        public Builder title(CharSequence title) {
            this.mTitle = title;
            return this;
        }

        public Builder positiveAction(CharSequence action) {
            this.mPositive = action;
            return this;
        }

        public Builder negativeAction(CharSequence action) {
            this.mNegative = action;
            return this;
        }

        public Builder neutralAction(CharSequence action) {
            this.mNeutral = action;
            return this;
        }

        public Dialog getDialog() {
            return this.mDialog;
        }

        public void onPositiveActionClicked(DialogFragment fragment) {
            fragment.dismiss();
        }

        public void onNegativeActionClicked(DialogFragment fragment) {
            fragment.dismiss();
        }

        public void onNeutralActionClicked(DialogFragment fragment) {
            fragment.dismiss();
        }

        public void onCancel(DialogInterface dialog) {
        }

        public void onDismiss(DialogInterface dialog) {
        }

        public Dialog build(Context context) {
            Dialog onBuild = onBuild(context, this.mStyleId);
            this.mDialog = onBuild;
            onBuild.title(this.mTitle).positiveAction(this.mPositive).negativeAction(this.mNegative).neutralAction(this.mNeutral);
            int i = this.mContentViewId;
            if (i != 0) {
                this.mDialog.contentView(i);
            }
            onBuildDone(this.mDialog);
            return this.mDialog;
        }

        /* access modifiers changed from: protected */
        public Dialog onBuild(Context context, int styleId) {
            return new Dialog(context, styleId);
        }

        /* access modifiers changed from: protected */
        public void onBuildDone(Dialog dialog) {
        }

        protected Builder(Parcel in) {
            this.mStyleId = in.readInt();
            this.mContentViewId = in.readInt();
            this.mTitle = (CharSequence) in.readParcelable((ClassLoader) null);
            this.mPositive = (CharSequence) in.readParcelable((ClassLoader) null);
            this.mNegative = (CharSequence) in.readParcelable((ClassLoader) null);
            this.mNeutral = (CharSequence) in.readParcelable((ClassLoader) null);
            onReadFromParcel(in);
        }

        /* access modifiers changed from: protected */
        public void onReadFromParcel(Parcel in) {
        }

        /* access modifiers changed from: protected */
        public void onWriteToParcel(Parcel dest, int flags) {
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mStyleId);
            dest.writeInt(this.mContentViewId);
            dest.writeValue(this.mTitle);
            dest.writeValue(this.mPositive);
            dest.writeValue(this.mNegative);
            dest.writeValue(this.mNeutral);
            onWriteToParcel(dest, flags);
        }

        public int describeContents() {
            return 0;
        }
    }
}
