package com.rey.material.widget;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Layout;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.MovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Scroller;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import com.google.common.primitives.Ints;
import com.rey.material.C2500R;
import com.rey.material.app.ThemeManager;
import com.rey.material.drawable.DividerDrawable;
import com.rey.material.util.ThemeUtil;
import com.rey.material.util.ViewUtil;
import com.rey.material.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParserException;

public class EditText extends FrameLayout implements ThemeManager.OnThemeChangedListener {
    public static final int AUTOCOMPLETE_MODE_MULTI = 2;
    public static final int AUTOCOMPLETE_MODE_NONE = 0;
    public static final int AUTOCOMPLETE_MODE_SINGLE = 1;
    public static final int SUPPORT_MODE_CHAR_COUNTER = 3;
    public static final int SUPPORT_MODE_HELPER = 1;
    public static final int SUPPORT_MODE_HELPER_WITH_ERROR = 2;
    public static final int SUPPORT_MODE_NONE = 0;
    protected int mAutoCompleteMode;
    private DividerDrawable mDivider;
    private ColorStateList mDividerColors;
    private boolean mDividerCompoundPadding;
    private ColorStateList mDividerErrorColors;
    private int mDividerPadding;
    protected android.widget.EditText mInputView;
    private boolean mIsRtl;
    private boolean mLabelEnable;
    private int mLabelInAnimId;
    private int mLabelOutAnimId;
    protected LabelView mLabelView;
    private boolean mLabelVisible;
    private TextView.OnSelectionChangedListener mOnSelectionChangedListener;
    private ColorStateList mSupportColors;
    private CharSequence mSupportError;
    private ColorStateList mSupportErrorColors;
    private CharSequence mSupportHelper;
    private int mSupportMaxChars;
    protected int mSupportMode;
    protected LabelView mSupportView;

    public EditText(Context context) {
        super(context);
    }

    public EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mLabelEnable = false;
        this.mLabelVisible = false;
        this.mSupportMode = 0;
        this.mAutoCompleteMode = 0;
        this.mDividerCompoundPadding = true;
        this.mDividerPadding = -1;
        this.mIsRtl = false;
        super.init(context, attrs, defStyleAttr, defStyleRes);
        if (isInEditMode()) {
            applyStyle(C2500R.C2505style.Material_Widget_EditText);
        }
    }

    private LabelView getLabelView() {
        if (this.mLabelView == null) {
            this.mLabelView = new LabelView(getContext());
            if (Build.VERSION.SDK_INT >= 17) {
                this.mLabelView.setTextDirection(this.mIsRtl ? 4 : 3);
            }
            this.mLabelView.setGravity(8388611);
            this.mLabelView.setSingleLine(true);
        }
        return this.mLabelView;
    }

    private LabelView getSupportView() {
        if (this.mSupportView == null) {
            this.mSupportView = new LabelView(getContext());
        }
        return this.mSupportView;
    }

    private boolean needCreateInputView(int autoCompleteMode) {
        android.widget.EditText editText = this.mInputView;
        if (editText == null) {
            return true;
        }
        if (autoCompleteMode == 0) {
            return !(editText instanceof InternalEditText);
        }
        if (autoCompleteMode == 1) {
            return !(editText instanceof InternalAutoCompleteTextView);
        }
        if (autoCompleteMode != 2) {
            return false;
        }
        return !(editText instanceof InternalMultiAutoCompleteTextView);
    }

    /* access modifiers changed from: protected */
    public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        int supportPadding;
        int i;
        int i2;
        int supportPadding2;
        String supportHelper;
        int i3;
        int supportPadding3;
        int dividerHeight;
        ColorStateList supportColors;
        ColorStateList supportErrorColors;
        Context context2 = context;
        AttributeSet attributeSet = attrs;
        int i4 = defStyleAttr;
        int i5 = defStyleRes;
        super.applyStyle(context, attrs, defStyleAttr, defStyleRes);
        android.widget.EditText editText = this.mInputView;
        CharSequence text = editText == null ? null : editText.getText();
        removeAllViews();
        TypedArray a = context2.obtainStyledAttributes(attributeSet, C2500R.styleable.EditText, i4, i5);
        int labelTextSize = -1;
        ColorStateList labelTextColor = null;
        int attr = -1;
        int supportTextSize = -1;
        ColorStateList supportColors2 = null;
        ColorStateList supportErrorColors2 = null;
        String supportError = null;
        int dividerHeight2 = -1;
        int integer = a.getInteger(C2500R.styleable.EditText_et_autoCompleteMode, this.mAutoCompleteMode);
        this.mAutoCompleteMode = integer;
        if (needCreateInputView(integer)) {
            int i6 = this.mAutoCompleteMode;
            if (i6 == 1) {
                this.mInputView = new InternalAutoCompleteTextView(context2, attributeSet, i4);
            } else if (i6 != 2) {
                this.mInputView = new InternalEditText(context2, attributeSet, i4);
            } else {
                this.mInputView = new InternalMultiAutoCompleteTextView(context2, attributeSet, i4);
            }
            ViewUtil.applyFont(this.mInputView, attributeSet, i4, i5);
            if (text != null) {
                this.mInputView.setText(text);
            }
            Editable editable = text;
            this.mInputView.addTextChangedListener(new InputTextWatcher());
            DividerDrawable dividerDrawable = this.mDivider;
            if (dividerDrawable != null) {
                dividerDrawable.setAnimEnable(false);
                ViewUtil.setBackground(this.mInputView, this.mDivider);
                this.mDivider.setAnimEnable(true);
            }
        } else {
            ViewUtil.applyStyle((View) this.mInputView, attributeSet, i4, i5);
        }
        this.mInputView.setVisibility(0);
        this.mInputView.setFocusableInTouchMode(true);
        int i7 = 0;
        int count = a.getIndexCount();
        ColorStateList dividerColors = null;
        ColorStateList dividerErrorColors = null;
        int dividerPadding = -1;
        int labelPadding = -1;
        String supportHelper2 = null;
        int dividerAnimDuration = -1;
        while (true) {
            supportPadding = attr;
            if (i7 >= count) {
                break;
            }
            int attr2 = a.getIndex(i7);
            int count2 = count;
            if (attr2 == C2500R.styleable.EditText_et_labelEnable) {
                supportErrorColors = supportErrorColors2;
                this.mLabelEnable = a.getBoolean(attr2, false);
                supportColors = supportColors2;
            } else {
                supportErrorColors = supportErrorColors2;
                if (attr2 == C2500R.styleable.EditText_et_labelPadding) {
                    labelPadding = a.getDimensionPixelSize(attr2, 0);
                    attr = supportPadding;
                    supportErrorColors2 = supportErrorColors;
                } else if (attr2 == C2500R.styleable.EditText_et_labelTextSize) {
                    labelTextSize = a.getDimensionPixelSize(attr2, 0);
                    attr = supportPadding;
                    supportErrorColors2 = supportErrorColors;
                } else if (attr2 == C2500R.styleable.EditText_et_labelTextColor) {
                    labelTextColor = a.getColorStateList(attr2);
                    attr = supportPadding;
                    supportErrorColors2 = supportErrorColors;
                } else if (attr2 == C2500R.styleable.EditText_et_labelTextAppearance) {
                    supportColors = supportColors2;
                    getLabelView().setTextAppearance(context2, a.getResourceId(attr2, 0));
                } else {
                    supportColors = supportColors2;
                    if (attr2 == C2500R.styleable.EditText_et_labelEllipsize) {
                        int labelEllipsize = a.getInteger(attr2, 0);
                        if (labelEllipsize == 1) {
                            getLabelView().setEllipsize(TextUtils.TruncateAt.START);
                        } else if (labelEllipsize == 2) {
                            getLabelView().setEllipsize(TextUtils.TruncateAt.MIDDLE);
                        } else if (labelEllipsize == 3) {
                            getLabelView().setEllipsize(TextUtils.TruncateAt.END);
                        } else if (labelEllipsize != 4) {
                            getLabelView().setEllipsize(TextUtils.TruncateAt.END);
                        } else {
                            getLabelView().setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        }
                    } else if (attr2 == C2500R.styleable.EditText_et_labelInAnim) {
                        this.mLabelInAnimId = a.getResourceId(attr2, 0);
                    } else if (attr2 == C2500R.styleable.EditText_et_labelOutAnim) {
                        this.mLabelOutAnimId = a.getResourceId(attr2, 0);
                    } else if (attr2 == C2500R.styleable.EditText_et_supportMode) {
                        this.mSupportMode = a.getInteger(attr2, 0);
                    } else if (attr2 == C2500R.styleable.EditText_et_supportPadding) {
                        attr = a.getDimensionPixelSize(attr2, 0);
                        supportErrorColors2 = supportErrorColors;
                        supportColors2 = supportColors;
                    } else if (attr2 == C2500R.styleable.EditText_et_supportTextSize) {
                        supportTextSize = a.getDimensionPixelSize(attr2, 0);
                        attr = supportPadding;
                        supportErrorColors2 = supportErrorColors;
                        supportColors2 = supportColors;
                    } else if (attr2 == C2500R.styleable.EditText_et_supportTextColor) {
                        supportColors2 = a.getColorStateList(attr2);
                        attr = supportPadding;
                        supportErrorColors2 = supportErrorColors;
                    } else if (attr2 == C2500R.styleable.EditText_et_supportTextErrorColor) {
                        supportErrorColors2 = a.getColorStateList(attr2);
                        attr = supportPadding;
                        supportColors2 = supportColors;
                    } else if (attr2 == C2500R.styleable.EditText_et_supportTextAppearance) {
                        getSupportView().setTextAppearance(context2, a.getResourceId(attr2, 0));
                    } else if (attr2 == C2500R.styleable.EditText_et_supportEllipsize) {
                        int supportEllipsize = a.getInteger(attr2, 0);
                        if (supportEllipsize == 1) {
                            getSupportView().setEllipsize(TextUtils.TruncateAt.START);
                        } else if (supportEllipsize == 2) {
                            getSupportView().setEllipsize(TextUtils.TruncateAt.MIDDLE);
                        } else if (supportEllipsize == 3) {
                            getSupportView().setEllipsize(TextUtils.TruncateAt.END);
                        } else if (supportEllipsize != 4) {
                            getSupportView().setEllipsize(TextUtils.TruncateAt.END);
                        } else {
                            getSupportView().setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        }
                    } else if (attr2 == C2500R.styleable.EditText_et_supportMaxLines) {
                        getSupportView().setMaxLines(a.getInteger(attr2, 0));
                    } else if (attr2 == C2500R.styleable.EditText_et_supportLines) {
                        getSupportView().setLines(a.getInteger(attr2, 0));
                    } else if (attr2 == C2500R.styleable.EditText_et_supportSingleLine) {
                        getSupportView().setSingleLine(a.getBoolean(attr2, false));
                    } else if (attr2 == C2500R.styleable.EditText_et_supportMaxChars) {
                        this.mSupportMaxChars = a.getInteger(attr2, 0);
                    } else if (attr2 == C2500R.styleable.EditText_et_helper) {
                        supportHelper2 = a.getString(attr2);
                        attr = supportPadding;
                        supportErrorColors2 = supportErrorColors;
                        supportColors2 = supportColors;
                    } else if (attr2 == C2500R.styleable.EditText_et_error) {
                        supportError = a.getString(attr2);
                        attr = supportPadding;
                        supportErrorColors2 = supportErrorColors;
                        supportColors2 = supportColors;
                    } else if (attr2 == C2500R.styleable.EditText_et_inputId) {
                        this.mInputView.setId(a.getResourceId(attr2, 0));
                    } else if (attr2 == C2500R.styleable.EditText_et_dividerColor) {
                        dividerColors = a.getColorStateList(attr2);
                        attr = supportPadding;
                        supportErrorColors2 = supportErrorColors;
                        supportColors2 = supportColors;
                    } else if (attr2 == C2500R.styleable.EditText_et_dividerErrorColor) {
                        dividerErrorColors = a.getColorStateList(attr2);
                        attr = supportPadding;
                        supportErrorColors2 = supportErrorColors;
                        supportColors2 = supportColors;
                    } else if (attr2 == C2500R.styleable.EditText_et_dividerHeight) {
                        dividerHeight2 = a.getDimensionPixelSize(attr2, 0);
                        attr = supportPadding;
                        supportErrorColors2 = supportErrorColors;
                        supportColors2 = supportColors;
                    } else if (attr2 == C2500R.styleable.EditText_et_dividerPadding) {
                        dividerPadding = a.getDimensionPixelSize(attr2, 0);
                        attr = supportPadding;
                        supportErrorColors2 = supportErrorColors;
                        supportColors2 = supportColors;
                    } else if (attr2 == C2500R.styleable.EditText_et_dividerAnimDuration) {
                        dividerAnimDuration = a.getInteger(attr2, 0);
                        attr = supportPadding;
                        supportErrorColors2 = supportErrorColors;
                        supportColors2 = supportColors;
                    } else if (attr2 == C2500R.styleable.EditText_et_dividerCompoundPadding) {
                        this.mDividerCompoundPadding = a.getBoolean(attr2, true);
                    }
                }
                i7++;
                count = count2;
            }
            attr = supportPadding;
            supportErrorColors2 = supportErrorColors;
            supportColors2 = supportColors;
            i7++;
            count = count2;
        }
        ColorStateList supportColors3 = supportColors2;
        ColorStateList supportErrorColors3 = supportErrorColors2;
        a.recycle();
        if (this.mInputView.getId() == 0) {
            this.mInputView.setId(ViewUtil.generateViewId());
        }
        DividerDrawable dividerDrawable2 = this.mDivider;
        if (dividerDrawable2 == null) {
            this.mDividerColors = dividerColors;
            this.mDividerErrorColors = dividerErrorColors;
            if (dividerColors == null) {
                this.mDividerColors = new ColorStateList(new int[][]{new int[]{-16842908}, new int[]{16842908, 16842910}}, new int[]{ThemeUtil.colorControlNormal(context2, ViewCompat.MEASURED_STATE_MASK), ThemeUtil.colorControlActivated(context2, ViewCompat.MEASURED_STATE_MASK)});
            }
            if (this.mDividerErrorColors == null) {
                this.mDividerErrorColors = ColorStateList.valueOf(ThemeUtil.colorAccent(context2, SupportMenu.CATEGORY_MASK));
            }
            if (dividerHeight2 < 0) {
                dividerHeight2 = 0;
            }
            if (dividerPadding < 0) {
                dividerPadding = 0;
            }
            if (dividerAnimDuration < 0) {
                dividerAnimDuration = context.getResources().getInteger(17694720);
            }
            this.mDividerPadding = dividerPadding;
            this.mInputView.setPadding(0, 0, 0, dividerPadding + dividerHeight2);
            DividerDrawable dividerDrawable3 = new DividerDrawable(dividerHeight2, this.mDividerCompoundPadding ? this.mInputView.getTotalPaddingLeft() : 0, this.mDividerCompoundPadding ? this.mInputView.getTotalPaddingRight() : 0, this.mDividerColors, dividerAnimDuration);
            this.mDivider = dividerDrawable3;
            dividerDrawable3.setInEditMode(isInEditMode());
            this.mDivider.setAnimEnable(false);
            ViewUtil.setBackground(this.mInputView, this.mDivider);
            this.mDivider.setAnimEnable(true);
        } else {
            if (dividerHeight2 >= 0 || dividerPadding >= 0) {
                if (dividerHeight2 < 0) {
                    dividerHeight = dividerDrawable2.getDividerHeight();
                } else {
                    dividerHeight = dividerHeight2;
                }
                if (dividerPadding >= 0) {
                    this.mDividerPadding = dividerPadding;
                }
                this.mInputView.setPadding(0, 0, 0, this.mDividerPadding + dividerHeight);
                this.mDivider.setDividerHeight(dividerHeight);
                this.mDivider.setPadding(this.mDividerCompoundPadding ? this.mInputView.getTotalPaddingLeft() : 0, this.mDividerCompoundPadding ? this.mInputView.getTotalPaddingRight() : 0);
                int i8 = dividerHeight;
            }
            if (dividerColors != null) {
                this.mDividerColors = dividerColors;
            }
            if (dividerErrorColors != null) {
                this.mDividerErrorColors = dividerErrorColors;
            }
            this.mDivider.setColor(getError() == null ? this.mDividerColors : this.mDividerErrorColors);
            if (dividerAnimDuration >= 0) {
                this.mDivider.setAnimationDuration(dividerAnimDuration);
            }
        }
        if (labelPadding >= 0) {
            i = 0;
            getLabelView().setPadding(this.mDivider.getPaddingLeft(), 0, this.mDivider.getPaddingRight(), labelPadding);
        } else {
            i = 0;
        }
        if (labelTextSize >= 0) {
            getLabelView().setTextSize(i, (float) labelTextSize);
        }
        if (labelTextColor != null) {
            getLabelView().setTextColor(labelTextColor);
        }
        if (this.mLabelEnable) {
            this.mLabelVisible = true;
            getLabelView().setText(this.mInputView.getHint());
            i2 = 0;
            addView(getLabelView(), 0, new ViewGroup.LayoutParams(-1, -2));
            setLabelVisible(!TextUtils.isEmpty(this.mInputView.getText().toString()), false);
        } else {
            i2 = 0;
        }
        if (supportTextSize >= 0) {
            getSupportView().setTextSize(i2, (float) supportTextSize);
        }
        if (supportColors3 != null) {
            this.mSupportColors = supportColors3;
        } else {
            if (this.mSupportColors == null) {
                this.mSupportColors = context.getResources().getColorStateList(C2500R.C2501color.abc_secondary_text_material_light);
            }
        }
        if (supportErrorColors3 != null) {
            this.mSupportErrorColors = supportErrorColors3;
        } else {
            if (this.mSupportErrorColors == null) {
                this.mSupportErrorColors = ColorStateList.valueOf(ThemeUtil.colorAccent(context2, SupportMenu.CATEGORY_MASK));
            }
        }
        if (supportPadding >= 0) {
            supportPadding2 = supportPadding;
            int supportPadding4 = dividerPadding;
            getSupportView().setPadding(this.mDivider.getPaddingLeft(), supportPadding2, this.mDivider.getPaddingRight(), 0);
        } else {
            supportPadding2 = supportPadding;
            int supportPadding5 = dividerPadding;
        }
        if (supportHelper2 == null && supportError == null) {
            getSupportView().setTextColor(getError() == null ? this.mSupportColors : this.mSupportErrorColors);
            String str = supportError;
            supportHelper = supportHelper2;
        } else if (supportHelper2 != null) {
            supportHelper = supportHelper2;
            setHelper(supportHelper);
            String str2 = supportError;
        } else {
            supportHelper = supportHelper2;
            setError(supportError);
        }
        int i9 = this.mSupportMode;
        if (i9 != 0) {
            if (i9 == 1 || i9 == 2) {
                getSupportView().setGravity(8388611);
            } else if (i9 == 3) {
                getSupportView().setGravity(GravityCompat.END);
                updateCharCounter(this.mInputView.getText().length());
            }
            int i10 = supportPadding2;
            String str3 = supportHelper;
            supportPadding3 = -2;
            i3 = -1;
            addView(getSupportView(), new ViewGroup.LayoutParams(-1, -2));
        } else {
            String str4 = supportHelper;
            supportPadding3 = -2;
            i3 = -1;
        }
        addView(this.mInputView, new ViewGroup.LayoutParams(i3, supportPadding3));
        requestLayout();
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        boolean rtl = true;
        if (layoutDirection != 1) {
            rtl = false;
        }
        if (this.mIsRtl != rtl) {
            this.mIsRtl = rtl;
            if (Build.VERSION.SDK_INT >= 17) {
                LabelView labelView = this.mLabelView;
                int i = 4;
                if (labelView != null) {
                    labelView.setTextDirection(this.mIsRtl ? 4 : 3);
                }
                LabelView labelView2 = this.mSupportView;
                if (labelView2 != null) {
                    if (!this.mIsRtl) {
                        i = 3;
                    }
                    labelView2.setTextDirection(i);
                }
            }
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int labelHeight;
        int height;
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int tempWidthSpec = widthMode == 0 ? widthMeasureSpec : View.MeasureSpec.makeMeasureSpec((widthSize - getPaddingLeft()) - getPaddingRight(), widthMode);
        int tempHeightSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        int labelWidth = 0;
        int labelHeight2 = 0;
        int supportWidth = 0;
        int supportHeight = 0;
        LabelView labelView = this.mLabelView;
        boolean measureLabelView = (labelView == null || labelView.getLayoutParams() == null) ? false : true;
        LabelView labelView2 = this.mSupportView;
        boolean measureSupportView = (labelView2 == null || labelView2.getLayoutParams() == null) ? false : true;
        if (measureLabelView) {
            this.mLabelView.measure(tempWidthSpec, tempHeightSpec);
            labelWidth = this.mLabelView.getMeasuredWidth();
            labelHeight2 = this.mLabelView.getMeasuredHeight();
        }
        this.mInputView.measure(tempWidthSpec, tempHeightSpec);
        int inputWidth = this.mInputView.getMeasuredWidth();
        int inputHeight = this.mInputView.getMeasuredHeight();
        if (measureSupportView) {
            this.mSupportView.measure(tempWidthSpec, tempHeightSpec);
            supportWidth = this.mSupportView.getMeasuredWidth();
            supportHeight = this.mSupportView.getMeasuredHeight();
        }
        int width = 0;
        int i = tempWidthSpec;
        int labelHeight3 = labelHeight2;
        if (widthMode == Integer.MIN_VALUE) {
            width = Math.min(widthSize, Math.max(labelWidth, Math.max(inputWidth, supportWidth)) + getPaddingLeft() + getPaddingRight());
        } else if (widthMode == 0) {
            width = Math.max(labelWidth, Math.max(inputWidth, supportWidth)) + getPaddingLeft() + getPaddingRight();
        } else if (widthMode == 1073741824) {
            width = widthSize;
        }
        int inputWidth2 = (width - getPaddingLeft()) - getPaddingRight();
        int inputWidth3 = View.MeasureSpec.makeMeasureSpec(inputWidth2, Ints.MAX_POWER_OF_TWO);
        if (!measureLabelView || this.mLabelView.getWidth() == inputWidth2) {
            labelHeight = labelHeight3;
        } else {
            this.mLabelView.measure(inputWidth3, tempHeightSpec);
            labelHeight = this.mLabelView.getMeasuredHeight();
        }
        if (measureSupportView) {
            int i2 = widthMode;
            if (this.mSupportView.getWidth() != inputWidth2) {
                this.mSupportView.measure(inputWidth3, tempHeightSpec);
                supportHeight = this.mSupportView.getMeasuredHeight();
            }
        }
        if (heightMode == Integer.MIN_VALUE) {
            height = Math.min(heightSize, labelHeight + inputHeight + supportHeight + getPaddingTop() + getPaddingBottom());
        } else if (heightMode != 0) {
            height = heightMode != 1073741824 ? 0 : heightSize;
        } else {
            height = labelHeight + inputHeight + supportHeight + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
        int inputHeight2 = (((height - labelHeight) - supportHeight) - getPaddingTop()) - getPaddingBottom();
        int i3 = height;
        if (this.mInputView.getMeasuredWidth() != inputWidth2 || this.mInputView.getMeasuredHeight() != inputHeight2) {
            this.mInputView.measure(inputWidth3, View.MeasureSpec.makeMeasureSpec(inputHeight2, Ints.MAX_POWER_OF_TWO));
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = getPaddingLeft();
        int childRight = (r - l) - getPaddingRight();
        int childTop = getPaddingTop();
        int childBottom = (b - t) - getPaddingBottom();
        LabelView labelView = this.mLabelView;
        if (labelView != null) {
            labelView.layout(childLeft, childTop, childRight, labelView.getMeasuredHeight() + childTop);
            childTop += this.mLabelView.getMeasuredHeight();
        }
        LabelView labelView2 = this.mSupportView;
        if (labelView2 != null) {
            labelView2.layout(childLeft, childBottom - labelView2.getMeasuredHeight(), childRight, childBottom);
            childBottom -= this.mSupportView.getMeasuredHeight();
        }
        this.mInputView.layout(childLeft, childTop, childRight, childBottom);
    }

    public void setHelper(CharSequence helper) {
        this.mSupportHelper = helper;
        setError(this.mSupportError);
    }

    public CharSequence getHelper() {
        return this.mSupportHelper;
    }

    public void setError(CharSequence error) {
        CharSequence charSequence;
        this.mSupportError = error;
        int i = this.mSupportMode;
        if (i != 1 && i != 2) {
            return;
        }
        if (error != null) {
            getSupportView().setTextColor(this.mSupportErrorColors);
            this.mDivider.setColor(this.mDividerErrorColors);
            LabelView supportView = getSupportView();
            if (this.mSupportMode == 1) {
                charSequence = this.mSupportError;
            } else {
                charSequence = TextUtils.concat(new CharSequence[]{this.mSupportHelper, ", ", this.mSupportError});
            }
            supportView.setText(charSequence);
            return;
        }
        getSupportView().setTextColor(this.mSupportColors);
        this.mDivider.setColor(this.mDividerColors);
        getSupportView().setText(this.mSupportHelper);
    }

    public CharSequence getError() {
        return this.mSupportError;
    }

    public void clearError() {
        setError((CharSequence) null);
    }

    /* access modifiers changed from: private */
    public void updateCharCounter(int count) {
        if (count == 0) {
            getSupportView().setTextColor(this.mSupportColors);
            this.mDivider.setColor(this.mDividerColors);
            getSupportView().setText((CharSequence) null);
        } else if (this.mSupportMaxChars > 0) {
            getSupportView().setTextColor(count > this.mSupportMaxChars ? this.mSupportErrorColors : this.mSupportColors);
            this.mDivider.setColor(count > this.mSupportMaxChars ? this.mDividerErrorColors : this.mDividerColors);
            getSupportView().setText(count + " / " + this.mSupportMaxChars);
        } else {
            getSupportView().setText(String.valueOf(count));
        }
    }

    /* access modifiers changed from: private */
    public void setLabelVisible(boolean visible, boolean animation) {
        if (this.mLabelEnable && this.mLabelVisible != visible) {
            this.mLabelVisible = visible;
            int i = 0;
            if (!animation) {
                LabelView labelView = this.mLabelView;
                if (!visible) {
                    i = 4;
                }
                labelView.setVisibility(i);
            } else if (visible) {
                if (this.mLabelInAnimId != 0) {
                    Animation anim = AnimationUtils.loadAnimation(getContext(), this.mLabelInAnimId);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        public void onAnimationStart(Animation animation) {
                            EditText.this.mLabelView.setVisibility(0);
                        }

                        public void onAnimationRepeat(Animation animation) {
                        }

                        public void onAnimationEnd(Animation animation) {
                        }
                    });
                    this.mLabelView.clearAnimation();
                    this.mLabelView.startAnimation(anim);
                    return;
                }
                this.mLabelView.setVisibility(0);
            } else if (this.mLabelOutAnimId != 0) {
                Animation anim2 = AnimationUtils.loadAnimation(getContext(), this.mLabelOutAnimId);
                anim2.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                        EditText.this.mLabelView.setVisibility(0);
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        EditText.this.mLabelView.setVisibility(4);
                    }
                });
                this.mLabelView.clearAnimation();
                this.mLabelView.startAnimation(anim2);
            } else {
                this.mLabelView.setVisibility(4);
            }
        }
    }

    /* access modifiers changed from: protected */
    public CharSequence convertSelectionToString(Object selectedItem) {
        int i = this.mAutoCompleteMode;
        if (i == 1) {
            return ((InternalAutoCompleteTextView) this.mInputView).superConvertSelectionToString(selectedItem);
        }
        if (i != 2) {
            return null;
        }
        return ((InternalMultiAutoCompleteTextView) this.mInputView).superConvertSelectionToString(selectedItem);
    }

    /* access modifiers changed from: protected */
    public void performFiltering(CharSequence text, int keyCode) {
        int i = this.mAutoCompleteMode;
        if (i == 1) {
            ((InternalAutoCompleteTextView) this.mInputView).superPerformFiltering(text, keyCode);
        } else if (i == 2) {
            ((InternalMultiAutoCompleteTextView) this.mInputView).superPerformFiltering(text, keyCode);
        }
    }

    /* access modifiers changed from: protected */
    public void replaceText(CharSequence text) {
        int i = this.mAutoCompleteMode;
        if (i == 1) {
            ((InternalAutoCompleteTextView) this.mInputView).superReplaceText(text);
        } else if (i == 2) {
            ((InternalMultiAutoCompleteTextView) this.mInputView).superReplaceText(text);
        }
    }

    /* access modifiers changed from: protected */
    public Filter getFilter() {
        int i = this.mAutoCompleteMode;
        if (i == 1) {
            return ((InternalAutoCompleteTextView) this.mInputView).superGetFilter();
        }
        if (i != 2) {
            return null;
        }
        return ((InternalMultiAutoCompleteTextView) this.mInputView).superGetFilter();
    }

    /* access modifiers changed from: protected */
    public void performFiltering(CharSequence text, int start, int end, int keyCode) {
        if (this.mAutoCompleteMode == 2) {
            ((InternalMultiAutoCompleteTextView) this.mInputView).superPerformFiltering(text, start, end, keyCode);
        }
    }

    public void setCompletionHint(CharSequence hint) {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).setCompletionHint(hint);
        }
    }

    public CharSequence getCompletionHint() {
        if (this.mAutoCompleteMode == 0 || Build.VERSION.SDK_INT < 16) {
            return null;
        }
        return ((AutoCompleteTextView) this.mInputView).getCompletionHint();
    }

    public int getDropDownWidth() {
        if (this.mAutoCompleteMode == 0) {
            return 0;
        }
        return ((AutoCompleteTextView) this.mInputView).getDropDownWidth();
    }

    public void setDropDownWidth(int width) {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).setDropDownWidth(width);
        }
    }

    public int getDropDownHeight() {
        if (this.mAutoCompleteMode == 0) {
            return 0;
        }
        return ((AutoCompleteTextView) this.mInputView).getDropDownHeight();
    }

    public void setDropDownHeight(int height) {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).setDropDownHeight(height);
        }
    }

    public int getDropDownAnchor() {
        if (this.mAutoCompleteMode == 0) {
            return 0;
        }
        return ((AutoCompleteTextView) this.mInputView).getDropDownAnchor();
    }

    public void setDropDownAnchor(int id) {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).setDropDownAnchor(id);
        }
    }

    public Drawable getDropDownBackground() {
        if (this.mAutoCompleteMode == 0) {
            return null;
        }
        return ((AutoCompleteTextView) this.mInputView).getDropDownBackground();
    }

    public void setDropDownBackgroundDrawable(Drawable d) {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).setDropDownBackgroundDrawable(d);
        }
    }

    public void setDropDownBackgroundResource(int id) {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).setDropDownBackgroundResource(id);
        }
    }

    public void setDropDownVerticalOffset(int offset) {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).setDropDownVerticalOffset(offset);
        }
    }

    public int getDropDownVerticalOffset() {
        if (this.mAutoCompleteMode == 0) {
            return 0;
        }
        return ((AutoCompleteTextView) this.mInputView).getDropDownVerticalOffset();
    }

    public void setDropDownHorizontalOffset(int offset) {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).setDropDownHorizontalOffset(offset);
        }
    }

    public int getDropDownHorizontalOffset() {
        if (this.mAutoCompleteMode == 0) {
            return 0;
        }
        return ((AutoCompleteTextView) this.mInputView).getDropDownHorizontalOffset();
    }

    public int getThreshold() {
        if (this.mAutoCompleteMode == 0) {
            return 0;
        }
        return ((AutoCompleteTextView) this.mInputView).getThreshold();
    }

    public void setThreshold(int threshold) {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).setThreshold(threshold);
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener l) {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).setOnItemClickListener(l);
        }
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener l) {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).setOnItemSelectedListener(l);
        }
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        if (this.mAutoCompleteMode == 0) {
            return null;
        }
        return ((AutoCompleteTextView) this.mInputView).getOnItemClickListener();
    }

    public AdapterView.OnItemSelectedListener getOnItemSelectedListener() {
        if (this.mAutoCompleteMode == 0) {
            return null;
        }
        return ((AutoCompleteTextView) this.mInputView).getOnItemSelectedListener();
    }

    public ListAdapter getAdapter() {
        if (this.mAutoCompleteMode == 0) {
            return null;
        }
        return ((AutoCompleteTextView) this.mInputView).getAdapter();
    }

    public <T extends ListAdapter & Filterable> void setAdapter(T adapter) {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).setAdapter(adapter);
        }
    }

    public boolean enoughToFilter() {
        if (this.mAutoCompleteMode == 0) {
            return false;
        }
        return ((AutoCompleteTextView) this.mInputView).enoughToFilter();
    }

    public boolean isPopupShowing() {
        if (this.mAutoCompleteMode == 0) {
            return false;
        }
        return ((AutoCompleteTextView) this.mInputView).isPopupShowing();
    }

    public void clearListSelection() {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).clearListSelection();
        }
    }

    public void setListSelection(int position) {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).setListSelection(position);
        }
    }

    public int getListSelection() {
        if (this.mAutoCompleteMode == 0) {
            return 0;
        }
        return ((AutoCompleteTextView) this.mInputView).getListSelection();
    }

    public void performCompletion() {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).performCompletion();
        }
    }

    public boolean isPerformingCompletion() {
        if (this.mAutoCompleteMode == 0) {
            return false;
        }
        return ((AutoCompleteTextView) this.mInputView).isPerformingCompletion();
    }

    public void onFilterComplete(int count) {
        int i = this.mAutoCompleteMode;
        if (i == 1) {
            ((InternalAutoCompleteTextView) this.mInputView).superOnFilterComplete(count);
        } else if (i == 2) {
            ((InternalMultiAutoCompleteTextView) this.mInputView).superOnFilterComplete(count);
        }
    }

    public void dismissDropDown() {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).dismissDropDown();
        }
    }

    public void showDropDown() {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).showDropDown();
        }
    }

    public void setValidator(AutoCompleteTextView.Validator validator) {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).setValidator(validator);
        }
    }

    public AutoCompleteTextView.Validator getValidator() {
        if (this.mAutoCompleteMode == 0) {
            return null;
        }
        return ((AutoCompleteTextView) this.mInputView).getValidator();
    }

    public void performValidation() {
        if (this.mAutoCompleteMode != 0) {
            ((AutoCompleteTextView) this.mInputView).performValidation();
        }
    }

    public void setTokenizer(MultiAutoCompleteTextView.Tokenizer t) {
        if (this.mAutoCompleteMode == 2) {
            ((MultiAutoCompleteTextView) this.mInputView).setTokenizer(t);
        }
    }

    public void setEnabled(boolean enabled) {
        this.mInputView.setEnabled(enabled);
    }

    public void extendSelection(int index) {
        this.mInputView.extendSelection(index);
    }

    public Editable getText() {
        return this.mInputView.getText();
    }

    public void selectAll() {
        this.mInputView.selectAll();
    }

    public void setEllipsize(TextUtils.TruncateAt ellipsis) {
        this.mInputView.setEllipsize(ellipsis);
    }

    public void setSelection(int index) {
        this.mInputView.setSelection(index);
    }

    public void setSelection(int start, int stop) {
        this.mInputView.setSelection(start, stop);
    }

    public void setText(CharSequence text, TextView.BufferType type) {
        this.mInputView.setText(text, type);
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        this.mInputView.addTextChangedListener(textWatcher);
    }

    public final void append(CharSequence text) {
        this.mInputView.append(text);
    }

    public void append(CharSequence text, int start, int end) {
        this.mInputView.append(text, start, end);
    }

    public void beginBatchEdit() {
        this.mInputView.beginBatchEdit();
    }

    public boolean bringPointIntoView(int offset) {
        return this.mInputView.bringPointIntoView(offset);
    }

    public void cancelLongPress() {
        this.mInputView.cancelLongPress();
    }

    public void clearComposingText() {
        this.mInputView.clearComposingText();
    }

    public void computeScroll() {
        this.mInputView.computeScroll();
    }

    public void debug(int depth) {
        this.mInputView.debug(depth);
    }

    public boolean didTouchFocusSelect() {
        return this.mInputView.didTouchFocusSelect();
    }

    public void endBatchEdit() {
        this.mInputView.endBatchEdit();
    }

    public boolean extractText(ExtractedTextRequest request, ExtractedText outText) {
        return this.mInputView.extractText(request, outText);
    }

    public void findViewsWithText(ArrayList<View> outViews, CharSequence searched, int flags) {
        if (Build.VERSION.SDK_INT >= 14) {
            this.mInputView.findViewsWithText(outViews, searched, flags);
        }
    }

    public final int getAutoLinkMask() {
        return this.mInputView.getAutoLinkMask();
    }

    public int getBaseline() {
        return this.mInputView.getBaseline();
    }

    public int getCompoundDrawablePadding() {
        return this.mInputView.getCompoundDrawablePadding();
    }

    public Drawable[] getCompoundDrawables() {
        return this.mInputView.getCompoundDrawables();
    }

    public Drawable[] getCompoundDrawablesRelative() {
        if (Build.VERSION.SDK_INT >= 17) {
            return this.mInputView.getCompoundDrawablesRelative();
        }
        return this.mInputView.getCompoundDrawables();
    }

    public int getCompoundPaddingBottom() {
        return this.mInputView.getCompoundPaddingBottom();
    }

    public int getCompoundPaddingEnd() {
        if (Build.VERSION.SDK_INT >= 17) {
            return this.mInputView.getCompoundPaddingEnd();
        }
        return this.mInputView.getCompoundPaddingRight();
    }

    public int getCompoundPaddingLeft() {
        return this.mInputView.getCompoundPaddingLeft();
    }

    public int getCompoundPaddingRight() {
        return this.mInputView.getCompoundPaddingRight();
    }

    public int getCompoundPaddingStart() {
        if (Build.VERSION.SDK_INT >= 17) {
            return this.mInputView.getCompoundPaddingStart();
        }
        return this.mInputView.getCompoundPaddingLeft();
    }

    public int getCompoundPaddingTop() {
        return this.mInputView.getCompoundPaddingTop();
    }

    public final int getCurrentHintTextColor() {
        return this.mInputView.getCurrentHintTextColor();
    }

    public final int getCurrentTextColor() {
        return this.mInputView.getCurrentTextColor();
    }

    public ActionMode.Callback getCustomSelectionActionModeCallback() {
        if (Build.VERSION.SDK_INT >= 11) {
            return this.mInputView.getCustomSelectionActionModeCallback();
        }
        return null;
    }

    public Editable getEditableText() {
        return this.mInputView.getEditableText();
    }

    public TextUtils.TruncateAt getEllipsize() {
        return this.mInputView.getEllipsize();
    }

    public int getExtendedPaddingBottom() {
        return this.mInputView.getExtendedPaddingBottom();
    }

    public int getExtendedPaddingTop() {
        return this.mInputView.getExtendedPaddingTop();
    }

    public InputFilter[] getFilters() {
        return this.mInputView.getFilters();
    }

    public void getFocusedRect(Rect r) {
        this.mInputView.getFocusedRect(r);
    }

    public String getFontFeatureSettings() {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInputView.getFontFeatureSettings();
        }
        return null;
    }

    public boolean getFreezesText() {
        return this.mInputView.getFreezesText();
    }

    public int getGravity() {
        return this.mInputView.getGravity();
    }

    public int getHighlightColor() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getHighlightColor();
        }
        return 0;
    }

    public CharSequence getHint() {
        return this.mInputView.getHint();
    }

    public final ColorStateList getHintTextColors() {
        return this.mInputView.getHintTextColors();
    }

    public int getImeActionId() {
        return this.mInputView.getImeActionId();
    }

    public CharSequence getImeActionLabel() {
        return this.mInputView.getImeActionLabel();
    }

    public int getImeOptions() {
        return this.mInputView.getImeOptions();
    }

    public boolean getIncludeFontPadding() {
        return Build.VERSION.SDK_INT >= 16 && this.mInputView.getIncludeFontPadding();
    }

    public Bundle getInputExtras(boolean create) {
        return this.mInputView.getInputExtras(create);
    }

    public int getInputType() {
        return this.mInputView.getInputType();
    }

    public final KeyListener getKeyListener() {
        return this.mInputView.getKeyListener();
    }

    public final Layout getLayout() {
        return this.mInputView.getLayout();
    }

    public float getLetterSpacing() {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInputView.getLetterSpacing();
        }
        return 0.0f;
    }

    public int getLineBounds(int line, Rect bounds) {
        return this.mInputView.getLineBounds(line, bounds);
    }

    public int getLineCount() {
        return this.mInputView.getLineCount();
    }

    public int getLineHeight() {
        return this.mInputView.getLineHeight();
    }

    public float getLineSpacingExtra() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getLineSpacingExtra();
        }
        return 0.0f;
    }

    public float getLineSpacingMultiplier() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getLineSpacingMultiplier();
        }
        return 0.0f;
    }

    public final ColorStateList getLinkTextColors() {
        return this.mInputView.getLinkTextColors();
    }

    public final boolean getLinksClickable() {
        return this.mInputView.getLinksClickable();
    }

    public int getMarqueeRepeatLimit() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getMarqueeRepeatLimit();
        }
        return -1;
    }

    public int getMaxEms() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getMaxEms();
        }
        return -1;
    }

    public int getMaxHeight() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getMaxHeight();
        }
        return -1;
    }

    public int getMaxLines() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getMaxLines();
        }
        return -1;
    }

    public int getMaxWidth() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getMaxWidth();
        }
        return -1;
    }

    public int getMinEms() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getMinEms();
        }
        return -1;
    }

    public int getMinHeight() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getMinHeight();
        }
        return -1;
    }

    public int getMinLines() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getMinLines();
        }
        return -1;
    }

    public int getMinWidth() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getMinWidth();
        }
        return -1;
    }

    public final MovementMethod getMovementMethod() {
        return this.mInputView.getMovementMethod();
    }

    public int getOffsetForPosition(float x, float y) {
        if (getLayout() == null) {
            return -1;
        }
        return getOffsetAtCoordinate(getLineAtCoordinate(y), x);
    }

    /* access modifiers changed from: protected */
    public float convertToLocalHorizontalCoordinate(float x) {
        return Math.min((float) ((getWidth() - getTotalPaddingRight()) - 1), Math.max(0.0f, x - ((float) getTotalPaddingLeft()))) + ((float) getScrollX());
    }

    /* access modifiers changed from: protected */
    public int getLineAtCoordinate(float y) {
        return getLayout().getLineForVertical((int) (Math.min((float) ((getHeight() - getTotalPaddingBottom()) - 1), Math.max(0.0f, y - ((float) getTotalPaddingTop()))) + ((float) getScrollY())));
    }

    /* access modifiers changed from: protected */
    public int getOffsetAtCoordinate(int line, float x) {
        return getLayout().getOffsetForHorizontal(line, convertToLocalHorizontalCoordinate(x));
    }

    public TextPaint getPaint() {
        return this.mInputView.getPaint();
    }

    public int getPaintFlags() {
        return this.mInputView.getPaintFlags();
    }

    public String getPrivateImeOptions() {
        return this.mInputView.getPrivateImeOptions();
    }

    public int getSelectionEnd() {
        return this.mInputView.getSelectionEnd();
    }

    public int getSelectionStart() {
        return this.mInputView.getSelectionStart();
    }

    public int getShadowColor() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getShadowColor();
        }
        return 0;
    }

    public float getShadowDx() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getShadowDx();
        }
        return 0.0f;
    }

    public float getShadowDy() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getShadowDy();
        }
        return 0.0f;
    }

    public float getShadowRadius() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInputView.getShadowRadius();
        }
        return 0.0f;
    }

    public final boolean getShowSoftInputOnFocus() {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInputView.getShowSoftInputOnFocus();
        }
        return true;
    }

    public final ColorStateList getTextColors() {
        return this.mInputView.getTextColors();
    }

    public Locale getTextLocale() {
        if (Build.VERSION.SDK_INT >= 17) {
            return this.mInputView.getTextLocale();
        }
        return Locale.getDefault();
    }

    public float getTextScaleX() {
        return this.mInputView.getTextScaleX();
    }

    public float getTextSize() {
        return this.mInputView.getTextSize();
    }

    public int getTotalPaddingBottom() {
        return getPaddingBottom() + this.mInputView.getTotalPaddingBottom() + (this.mSupportMode != 0 ? this.mSupportView.getHeight() : 0);
    }

    public int getTotalPaddingEnd() {
        if (Build.VERSION.SDK_INT >= 17) {
            return getPaddingEnd() + this.mInputView.getTotalPaddingEnd();
        }
        return getTotalPaddingRight();
    }

    public int getTotalPaddingLeft() {
        return getPaddingLeft() + this.mInputView.getTotalPaddingLeft();
    }

    public int getTotalPaddingRight() {
        return getPaddingRight() + this.mInputView.getTotalPaddingRight();
    }

    public int getTotalPaddingStart() {
        if (Build.VERSION.SDK_INT >= 17) {
            return getPaddingStart() + this.mInputView.getTotalPaddingStart();
        }
        return getTotalPaddingLeft();
    }

    public int getTotalPaddingTop() {
        return getPaddingTop() + this.mInputView.getTotalPaddingTop() + (this.mLabelEnable ? this.mLabelView.getHeight() : 0);
    }

    public final TransformationMethod getTransformationMethod() {
        return this.mInputView.getTransformationMethod();
    }

    public Typeface getTypeface() {
        return this.mInputView.getTypeface();
    }

    public URLSpan[] getUrls() {
        return this.mInputView.getUrls();
    }

    public boolean hasOverlappingRendering() {
        return Build.VERSION.SDK_INT >= 16 && this.mInputView.hasOverlappingRendering();
    }

    public boolean hasSelection() {
        return this.mInputView.hasSelection();
    }

    public boolean isCursorVisible() {
        return Build.VERSION.SDK_INT < 16 || this.mInputView.isCursorVisible();
    }

    public boolean isInputMethodTarget() {
        return this.mInputView.isInputMethodTarget();
    }

    public boolean isSuggestionsEnabled() {
        return Build.VERSION.SDK_INT >= 14 && this.mInputView.isSuggestionsEnabled();
    }

    public boolean isTextSelectable() {
        return Build.VERSION.SDK_INT < 11 || this.mInputView.isTextSelectable();
    }

    public int length() {
        return this.mInputView.length();
    }

    public boolean moveCursorToVisibleOffset() {
        return this.mInputView.moveCursorToVisibleOffset();
    }

    public void onCommitCompletion(CompletionInfo text) {
        int i = this.mAutoCompleteMode;
        if (i == 0) {
            ((InternalEditText) this.mInputView).superOnCommitCompletion(text);
        } else if (i == 1) {
            ((InternalAutoCompleteTextView) this.mInputView).superOnCommitCompletion(text);
        } else {
            ((InternalMultiAutoCompleteTextView) this.mInputView).superOnCommitCompletion(text);
        }
    }

    public void onCommitCorrection(CorrectionInfo info) {
        int i = this.mAutoCompleteMode;
        if (i == 0) {
            ((InternalEditText) this.mInputView).superOnCommitCorrection(info);
        } else if (i == 1) {
            ((InternalAutoCompleteTextView) this.mInputView).superOnCommitCorrection(info);
        } else {
            ((InternalMultiAutoCompleteTextView) this.mInputView).superOnCommitCorrection(info);
        }
    }

    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        int i = this.mAutoCompleteMode;
        if (i == 0) {
            return ((InternalEditText) this.mInputView).superOnCreateInputConnection(outAttrs);
        }
        if (i == 1) {
            return ((InternalAutoCompleteTextView) this.mInputView).superOnCreateInputConnection(outAttrs);
        }
        return ((InternalMultiAutoCompleteTextView) this.mInputView).superOnCreateInputConnection(outAttrs);
    }

    public void onEditorAction(int actionCode) {
        int i = this.mAutoCompleteMode;
        if (i == 0) {
            ((InternalEditText) this.mInputView).superOnEditorAction(actionCode);
        } else if (i == 1) {
            ((InternalAutoCompleteTextView) this.mInputView).superOnEditorAction(actionCode);
        } else {
            ((InternalMultiAutoCompleteTextView) this.mInputView).superOnEditorAction(actionCode);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int i = this.mAutoCompleteMode;
        if (i == 0) {
            return ((InternalEditText) this.mInputView).superOnKeyDown(keyCode, event);
        }
        if (i == 1) {
            return ((InternalAutoCompleteTextView) this.mInputView).superOnKeyDown(keyCode, event);
        }
        return ((InternalMultiAutoCompleteTextView) this.mInputView).superOnKeyDown(keyCode, event);
    }

    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        int i = this.mAutoCompleteMode;
        if (i == 0) {
            return ((InternalEditText) this.mInputView).superOnKeyMultiple(keyCode, repeatCount, event);
        }
        if (i == 1) {
            return ((InternalAutoCompleteTextView) this.mInputView).superOnKeyMultiple(keyCode, repeatCount, event);
        }
        return ((InternalMultiAutoCompleteTextView) this.mInputView).superOnKeyMultiple(keyCode, repeatCount, event);
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        int i = this.mAutoCompleteMode;
        if (i == 0) {
            return ((InternalEditText) this.mInputView).superOnKeyPreIme(keyCode, event);
        }
        if (i == 1) {
            return ((InternalAutoCompleteTextView) this.mInputView).superOnKeyPreIme(keyCode, event);
        }
        return ((InternalMultiAutoCompleteTextView) this.mInputView).superOnKeyPreIme(keyCode, event);
    }

    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        int i = this.mAutoCompleteMode;
        if (i == 0) {
            return ((InternalEditText) this.mInputView).superOnKeyShortcut(keyCode, event);
        }
        if (i == 1) {
            return ((InternalAutoCompleteTextView) this.mInputView).superOnKeyShortcut(keyCode, event);
        }
        return ((InternalMultiAutoCompleteTextView) this.mInputView).superOnKeyShortcut(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        int i = this.mAutoCompleteMode;
        if (i == 0) {
            return ((InternalEditText) this.mInputView).superOnKeyUp(keyCode, event);
        }
        if (i == 1) {
            return ((InternalAutoCompleteTextView) this.mInputView).superOnKeyUp(keyCode, event);
        }
        return ((InternalMultiAutoCompleteTextView) this.mInputView).superOnKeyUp(keyCode, event);
    }

    public void setOnSelectionChangedListener(TextView.OnSelectionChangedListener listener) {
        this.mOnSelectionChangedListener = listener;
    }

    /* access modifiers changed from: protected */
    public void onSelectionChanged(int selStart, int selEnd) {
        android.widget.EditText editText = this.mInputView;
        if (editText != null) {
            if (editText instanceof InternalEditText) {
                ((InternalEditText) editText).superOnSelectionChanged(selStart, selEnd);
            } else if (editText instanceof InternalAutoCompleteTextView) {
                ((InternalAutoCompleteTextView) editText).superOnSelectionChanged(selStart, selEnd);
            } else {
                ((InternalMultiAutoCompleteTextView) editText).superOnSelectionChanged(selStart, selEnd);
            }
            TextView.OnSelectionChangedListener onSelectionChangedListener = this.mOnSelectionChangedListener;
            if (onSelectionChangedListener != null) {
                onSelectionChangedListener.onSelectionChanged(this, selStart, selEnd);
            }
        }
    }

    public void removeTextChangedListener(TextWatcher watcher) {
        this.mInputView.removeTextChangedListener(watcher);
    }

    public void setAllCaps(boolean allCaps) {
        if (Build.VERSION.SDK_INT >= 14) {
            this.mInputView.setAllCaps(allCaps);
        }
    }

    public final void setAutoLinkMask(int mask) {
        this.mInputView.setAutoLinkMask(mask);
    }

    public void setCompoundDrawablePadding(int pad) {
        this.mInputView.setCompoundDrawablePadding(pad);
        if (this.mDividerCompoundPadding) {
            this.mDivider.setPadding(this.mInputView.getTotalPaddingLeft(), this.mInputView.getTotalPaddingRight());
            if (this.mLabelEnable) {
                this.mLabelView.setPadding(this.mDivider.getPaddingLeft(), this.mLabelView.getPaddingTop(), this.mDivider.getPaddingRight(), this.mLabelView.getPaddingBottom());
            }
            if (this.mSupportMode != 0) {
                this.mSupportView.setPadding(this.mDivider.getPaddingLeft(), this.mSupportView.getPaddingTop(), this.mDivider.getPaddingRight(), this.mSupportView.getPaddingBottom());
            }
        }
    }

    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        this.mInputView.setCompoundDrawables(left, top, right, bottom);
        if (this.mDividerCompoundPadding) {
            this.mDivider.setPadding(this.mInputView.getTotalPaddingLeft(), this.mInputView.getTotalPaddingRight());
            if (this.mLabelEnable) {
                this.mLabelView.setPadding(this.mDivider.getPaddingLeft(), this.mLabelView.getPaddingTop(), this.mDivider.getPaddingRight(), this.mLabelView.getPaddingBottom());
            }
            if (this.mSupportMode != 0) {
                this.mSupportView.setPadding(this.mDivider.getPaddingLeft(), this.mSupportView.getPaddingTop(), this.mDivider.getPaddingRight(), this.mSupportView.getPaddingBottom());
            }
        }
    }

    public void setCompoundDrawablesRelative(Drawable start, Drawable top, Drawable end, Drawable bottom) {
        if (Build.VERSION.SDK_INT >= 17) {
            this.mInputView.setCompoundDrawablesRelative(start, top, end, bottom);
        } else {
            this.mInputView.setCompoundDrawables(start, top, end, bottom);
        }
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable start, Drawable top, Drawable end, Drawable bottom) {
        if (Build.VERSION.SDK_INT >= 17) {
            this.mInputView.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        } else {
            this.mInputView.setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom);
        }
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int start, int top, int end, int bottom) {
        if (Build.VERSION.SDK_INT >= 17) {
            this.mInputView.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        } else {
            this.mInputView.setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom);
        }
    }

    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        this.mInputView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        this.mInputView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    public void setCursorVisible(boolean visible) {
        this.mInputView.setCursorVisible(visible);
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        if (Build.VERSION.SDK_INT >= 11) {
            this.mInputView.setCustomSelectionActionModeCallback(actionModeCallback);
        }
    }

    public final void setEditableFactory(Editable.Factory factory) {
        this.mInputView.setEditableFactory(factory);
    }

    public void setElegantTextHeight(boolean elegant) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mInputView.setElegantTextHeight(elegant);
        }
    }

    public void setEms(int ems) {
        this.mInputView.setEms(ems);
    }

    public void setExtractedText(ExtractedText text) {
        this.mInputView.setExtractedText(text);
    }

    public void setFilters(InputFilter[] filters) {
        this.mInputView.setFilters(filters);
    }

    public void setFontFeatureSettings(String fontFeatureSettings) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mInputView.setFontFeatureSettings(fontFeatureSettings);
        }
    }

    public void setFreezesText(boolean freezesText) {
        this.mInputView.setFreezesText(freezesText);
    }

    public void setGravity(int gravity) {
        this.mInputView.setGravity(gravity);
    }

    public void setHighlightColor(int color) {
        this.mInputView.setHighlightColor(color);
    }

    public final void setHint(CharSequence hint) {
        this.mInputView.setHint(hint);
        LabelView labelView = this.mLabelView;
        if (labelView != null) {
            labelView.setText(hint);
        }
    }

    public final void setHint(int resid) {
        this.mInputView.setHint(resid);
        LabelView labelView = this.mLabelView;
        if (labelView != null) {
            labelView.setText(resid);
        }
    }

    public final void setHintTextColor(ColorStateList colors) {
        this.mInputView.setHintTextColor(colors);
    }

    public final void setHintTextColor(int color) {
        this.mInputView.setHintTextColor(color);
    }

    public void setHorizontallyScrolling(boolean whether) {
        this.mInputView.setHorizontallyScrolling(whether);
    }

    public void setImeActionLabel(CharSequence label, int actionId) {
        this.mInputView.setImeActionLabel(label, actionId);
    }

    public void setImeOptions(int imeOptions) {
        this.mInputView.setImeOptions(imeOptions);
    }

    public void setIncludeFontPadding(boolean includepad) {
        this.mInputView.setIncludeFontPadding(includepad);
    }

    public void setInputExtras(int xmlResId) throws XmlPullParserException, IOException {
        this.mInputView.setInputExtras(xmlResId);
    }

    public void setInputType(int type) {
        this.mInputView.setInputType(type);
    }

    public void setKeyListener(KeyListener input) {
        this.mInputView.setKeyListener(input);
    }

    public void setLetterSpacing(float letterSpacing) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mInputView.setLetterSpacing(letterSpacing);
        }
    }

    public void setLineSpacing(float add, float mult) {
        this.mInputView.setLineSpacing(add, mult);
    }

    public void setLines(int lines) {
        this.mInputView.setLines(lines);
    }

    public final void setLinkTextColor(ColorStateList colors) {
        this.mInputView.setLinkTextColor(colors);
    }

    public final void setLinkTextColor(int color) {
        this.mInputView.setLinkTextColor(color);
    }

    public final void setLinksClickable(boolean whether) {
        this.mInputView.setLinksClickable(whether);
    }

    public void setMarqueeRepeatLimit(int marqueeLimit) {
        this.mInputView.setMarqueeRepeatLimit(marqueeLimit);
    }

    public void setMaxEms(int maxems) {
        this.mInputView.setMaxEms(maxems);
    }

    public void setMaxHeight(int maxHeight) {
        this.mInputView.setMaxHeight(maxHeight);
    }

    public void setMaxLines(int maxlines) {
        this.mInputView.setMaxLines(maxlines);
    }

    public void setMaxWidth(int maxpixels) {
        this.mInputView.setMaxWidth(maxpixels);
    }

    public void setMinEms(int minems) {
        this.mInputView.setMinEms(minems);
    }

    public void setMinHeight(int minHeight) {
        this.mInputView.setMinHeight(minHeight);
    }

    public void setMinLines(int minlines) {
        this.mInputView.setMinLines(minlines);
    }

    public void setMinWidth(int minpixels) {
        this.mInputView.setMinWidth(minpixels);
    }

    public final void setMovementMethod(MovementMethod movement) {
        this.mInputView.setMovementMethod(movement);
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener l) {
        this.mInputView.setOnEditorActionListener(l);
    }

    public void setOnKeyListener(View.OnKeyListener l) {
        this.mInputView.setOnKeyListener(l);
    }

    public void setOnFocusChangeListener(View.OnFocusChangeListener l) {
        this.mInputView.setOnFocusChangeListener(l);
    }

    public void setRawInputType(int type) {
        this.mInputView.setRawInputType(type);
    }

    public void setScroller(Scroller s) {
        this.mInputView.setScroller(s);
    }

    public void setSelectAllOnFocus(boolean selectAllOnFocus) {
        this.mInputView.setSelectAllOnFocus(selectAllOnFocus);
    }

    public void setSelected(boolean selected) {
        this.mInputView.setSelected(selected);
    }

    public void setShadowLayer(float radius, float dx, float dy, int color) {
        this.mInputView.setShadowLayer(radius, dx, dy, color);
    }

    public final void setShowSoftInputOnFocus(boolean show) {
        this.mInputView.setShowSoftInputOnFocus(show);
    }

    public void setSingleLine() {
        this.mInputView.setSingleLine();
    }

    public final void setSpannableFactory(Spannable.Factory factory) {
        this.mInputView.setSpannableFactory(factory);
    }

    public final void setText(int resid) {
        this.mInputView.setText(resid);
    }

    public final void setText(char[] text, int start, int len) {
        this.mInputView.setText(text, start, len);
    }

    public final void setText(int resid, TextView.BufferType type) {
        this.mInputView.setText(resid, type);
    }

    public final void setText(CharSequence text) {
        this.mInputView.setText(text);
    }

    public void setTextAppearance(Context context, int resid) {
        this.mInputView.setTextAppearance(context, resid);
    }

    public void setTextColor(ColorStateList colors) {
        this.mInputView.setTextColor(colors);
    }

    public void setTextColor(int color) {
        this.mInputView.setTextColor(color);
    }

    public void setTextIsSelectable(boolean selectable) {
        if (Build.VERSION.SDK_INT >= 11) {
            this.mInputView.setTextIsSelectable(selectable);
        }
    }

    public final void setTextKeepState(CharSequence text) {
        this.mInputView.setTextKeepState(text);
    }

    public final void setTextKeepState(CharSequence text, TextView.BufferType type) {
        this.mInputView.setTextKeepState(text, type);
    }

    public void setTextLocale(Locale locale) {
        if (Build.VERSION.SDK_INT >= 17) {
            this.mInputView.setTextLocale(locale);
        }
    }

    public void setTextScaleX(float size) {
        this.mInputView.setTextScaleX(size);
    }

    public void setTextSize(float size) {
        this.mInputView.setTextSize(size);
    }

    public void setTextSize(int unit, float size) {
        this.mInputView.setTextSize(unit, size);
    }

    public final void setTransformationMethod(TransformationMethod method) {
        this.mInputView.setTransformationMethod(method);
    }

    public void setTypeface(Typeface tf, int style) {
        this.mInputView.setTypeface(tf, style);
    }

    public void setTypeface(Typeface tf) {
        this.mInputView.setTypeface(tf);
    }

    private boolean hasPasswordTransformationMethod() {
        return getTransformationMethod() != null && (getTransformationMethod() instanceof PasswordTransformationMethod);
    }

    public boolean canCut() {
        return !hasPasswordTransformationMethod() && getText().length() > 0 && hasSelection() && getKeyListener() != null;
    }

    public boolean canCopy() {
        return !hasPasswordTransformationMethod() && getText().length() > 0 && hasSelection();
    }

    public boolean canPaste() {
        return getKeyListener() != null && getSelectionStart() >= 0 && getSelectionEnd() >= 0 && ((ClipboardManager) getContext().getSystemService("clipboard")).hasPrimaryClip();
    }

    private class InputTextWatcher implements TextWatcher {
        private InputTextWatcher() {
        }

        public void afterTextChanged(Editable s) {
            int count = s.length();
            EditText.this.setLabelVisible(count != 0, true);
            if (EditText.this.mSupportMode == 3) {
                EditText.this.updateCharCounter(count);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

    private class LabelView extends TextView {
        public LabelView(Context context) {
            super(context);
        }

        public void setTextAppearance(int resId) {
            ViewUtil.applyTextAppearance(this, resId);
        }

        public void setTextAppearance(Context context, int resId) {
            ViewUtil.applyTextAppearance(this, resId);
        }

        /* access modifiers changed from: protected */
        public int[] onCreateDrawableState(int extraSpace) {
            return EditText.this.mInputView.getDrawableState();
        }
    }

    private class InternalEditText extends AppCompatEditText {
        public InternalEditText(Context context) {
            super(context);
        }

        public InternalEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public InternalEditText(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public void setTextAppearance(int resId) {
            ViewUtil.applyTextAppearance(this, resId);
        }

        public void setTextAppearance(Context context, int resId) {
            ViewUtil.applyTextAppearance(this, resId);
        }

        public void refreshDrawableState() {
            super.refreshDrawableState();
            if (EditText.this.mLabelView != null) {
                EditText.this.mLabelView.refreshDrawableState();
            }
            if (EditText.this.mSupportView != null) {
                EditText.this.mSupportView.refreshDrawableState();
            }
        }

        public void onCommitCompletion(CompletionInfo text) {
            EditText.this.onCommitCompletion(text);
        }

        public void onCommitCorrection(CorrectionInfo info) {
            EditText.this.onCommitCorrection(info);
        }

        public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
            return EditText.this.onCreateInputConnection(outAttrs);
        }

        public void onEditorAction(int actionCode) {
            EditText.this.onEditorAction(actionCode);
        }

        public boolean onKeyDown(int keyCode, KeyEvent event) {
            return EditText.this.onKeyDown(keyCode, event);
        }

        public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
            return EditText.this.onKeyMultiple(keyCode, repeatCount, event);
        }

        public boolean onKeyPreIme(int keyCode, KeyEvent event) {
            return EditText.this.onKeyPreIme(keyCode, event);
        }

        public boolean onKeyShortcut(int keyCode, KeyEvent event) {
            return EditText.this.onKeyShortcut(keyCode, event);
        }

        public boolean onKeyUp(int keyCode, KeyEvent event) {
            return EditText.this.onKeyUp(keyCode, event);
        }

        /* access modifiers changed from: protected */
        public void onSelectionChanged(int selStart, int selEnd) {
            EditText.this.onSelectionChanged(selStart, selEnd);
        }

        /* access modifiers changed from: package-private */
        public void superOnCommitCompletion(CompletionInfo text) {
            super.onCommitCompletion(text);
        }

        /* access modifiers changed from: package-private */
        public void superOnCommitCorrection(CorrectionInfo info) {
            if (Build.VERSION.SDK_INT >= 11) {
                super.onCommitCorrection(info);
            }
        }

        /* access modifiers changed from: package-private */
        public InputConnection superOnCreateInputConnection(EditorInfo outAttrs) {
            return super.onCreateInputConnection(outAttrs);
        }

        /* access modifiers changed from: package-private */
        public void superOnEditorAction(int actionCode) {
            super.onEditorAction(actionCode);
        }

        /* access modifiers changed from: package-private */
        public boolean superOnKeyDown(int keyCode, KeyEvent event) {
            return super.onKeyDown(keyCode, event);
        }

        /* access modifiers changed from: package-private */
        public boolean superOnKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
            return super.onKeyMultiple(keyCode, repeatCount, event);
        }

        /* access modifiers changed from: package-private */
        public boolean superOnKeyPreIme(int keyCode, KeyEvent event) {
            return super.onKeyPreIme(keyCode, event);
        }

        /* access modifiers changed from: package-private */
        public boolean superOnKeyShortcut(int keyCode, KeyEvent event) {
            return super.onKeyShortcut(keyCode, event);
        }

        /* access modifiers changed from: package-private */
        public boolean superOnKeyUp(int keyCode, KeyEvent event) {
            return super.onKeyUp(keyCode, event);
        }

        /* access modifiers changed from: package-private */
        public void superOnSelectionChanged(int selStart, int selEnd) {
            super.onSelectionChanged(selStart, selEnd);
        }
    }

    private class InternalAutoCompleteTextView extends AppCompatAutoCompleteTextView {
        public InternalAutoCompleteTextView(Context context) {
            super(context);
        }

        public InternalAutoCompleteTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public InternalAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public void setTextAppearance(int resId) {
            ViewUtil.applyTextAppearance(this, resId);
        }

        public void setTextAppearance(Context context, int resId) {
            ViewUtil.applyTextAppearance(this, resId);
        }

        public void refreshDrawableState() {
            super.refreshDrawableState();
            if (EditText.this.mLabelView != null) {
                EditText.this.mLabelView.refreshDrawableState();
            }
            if (EditText.this.mSupportView != null) {
                EditText.this.mSupportView.refreshDrawableState();
            }
        }

        public void onCommitCompletion(CompletionInfo text) {
            EditText.this.onCommitCompletion(text);
        }

        public void onCommitCorrection(CorrectionInfo info) {
            EditText.this.onCommitCorrection(info);
        }

        public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
            return EditText.this.onCreateInputConnection(outAttrs);
        }

        public void onEditorAction(int actionCode) {
            EditText.this.onEditorAction(actionCode);
        }

        public boolean onKeyDown(int keyCode, KeyEvent event) {
            return EditText.this.onKeyDown(keyCode, event);
        }

        public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
            return EditText.this.onKeyMultiple(keyCode, repeatCount, event);
        }

        public boolean onKeyPreIme(int keyCode, KeyEvent event) {
            return EditText.this.onKeyPreIme(keyCode, event);
        }

        public boolean onKeyShortcut(int keyCode, KeyEvent event) {
            return EditText.this.onKeyShortcut(keyCode, event);
        }

        public boolean onKeyUp(int keyCode, KeyEvent event) {
            return EditText.this.onKeyUp(keyCode, event);
        }

        /* access modifiers changed from: protected */
        public void onSelectionChanged(int selStart, int selEnd) {
            EditText.this.onSelectionChanged(selStart, selEnd);
        }

        /* access modifiers changed from: protected */
        public CharSequence convertSelectionToString(Object selectedItem) {
            return EditText.this.convertSelectionToString(selectedItem);
        }

        /* access modifiers changed from: protected */
        public void performFiltering(CharSequence text, int keyCode) {
            EditText.this.performFiltering(text, keyCode);
        }

        /* access modifiers changed from: protected */
        public void replaceText(CharSequence text) {
            EditText.this.replaceText(text);
        }

        /* access modifiers changed from: protected */
        public Filter getFilter() {
            return EditText.this.getFilter();
        }

        public void onFilterComplete(int count) {
            EditText.this.onFilterComplete(count);
        }

        /* access modifiers changed from: package-private */
        public void superOnCommitCompletion(CompletionInfo text) {
            super.onCommitCompletion(text);
        }

        /* access modifiers changed from: package-private */
        public void superOnCommitCorrection(CorrectionInfo info) {
            if (Build.VERSION.SDK_INT >= 11) {
                super.onCommitCorrection(info);
            }
        }

        /* access modifiers changed from: package-private */
        public InputConnection superOnCreateInputConnection(EditorInfo outAttrs) {
            return super.onCreateInputConnection(outAttrs);
        }

        /* access modifiers changed from: package-private */
        public void superOnEditorAction(int actionCode) {
            super.onEditorAction(actionCode);
        }

        /* access modifiers changed from: package-private */
        public boolean superOnKeyDown(int keyCode, KeyEvent event) {
            return super.onKeyDown(keyCode, event);
        }

        /* access modifiers changed from: package-private */
        public boolean superOnKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
            return super.onKeyMultiple(keyCode, repeatCount, event);
        }

        /* access modifiers changed from: package-private */
        public boolean superOnKeyPreIme(int keyCode, KeyEvent event) {
            return super.onKeyPreIme(keyCode, event);
        }

        /* access modifiers changed from: package-private */
        public boolean superOnKeyShortcut(int keyCode, KeyEvent event) {
            return super.onKeyShortcut(keyCode, event);
        }

        /* access modifiers changed from: package-private */
        public boolean superOnKeyUp(int keyCode, KeyEvent event) {
            return super.onKeyUp(keyCode, event);
        }

        /* access modifiers changed from: package-private */
        public void superOnFilterComplete(int count) {
            super.onFilterComplete(count);
        }

        /* access modifiers changed from: package-private */
        public CharSequence superConvertSelectionToString(Object selectedItem) {
            return super.convertSelectionToString(selectedItem);
        }

        /* access modifiers changed from: package-private */
        public void superPerformFiltering(CharSequence text, int keyCode) {
            super.performFiltering(text, keyCode);
        }

        /* access modifiers changed from: package-private */
        public void superReplaceText(CharSequence text) {
            super.replaceText(text);
        }

        /* access modifiers changed from: package-private */
        public Filter superGetFilter() {
            return super.getFilter();
        }

        /* access modifiers changed from: package-private */
        public void superOnSelectionChanged(int selStart, int selEnd) {
            super.onSelectionChanged(selStart, selEnd);
        }
    }

    private class InternalMultiAutoCompleteTextView extends AppCompatMultiAutoCompleteTextView {
        public InternalMultiAutoCompleteTextView(Context context) {
            super(context);
        }

        public InternalMultiAutoCompleteTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public InternalMultiAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public void setTextAppearance(int resId) {
            ViewUtil.applyTextAppearance(this, resId);
        }

        public void setTextAppearance(Context context, int resId) {
            ViewUtil.applyTextAppearance(this, resId);
        }

        public void refreshDrawableState() {
            super.refreshDrawableState();
            if (EditText.this.mLabelView != null) {
                EditText.this.mLabelView.refreshDrawableState();
            }
            if (EditText.this.mSupportView != null) {
                EditText.this.mSupportView.refreshDrawableState();
            }
        }

        public void onCommitCompletion(CompletionInfo text) {
            EditText.this.onCommitCompletion(text);
        }

        public void onCommitCorrection(CorrectionInfo info) {
            EditText.this.onCommitCorrection(info);
        }

        public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
            return EditText.this.onCreateInputConnection(outAttrs);
        }

        public void onEditorAction(int actionCode) {
            EditText.this.onEditorAction(actionCode);
        }

        public boolean onKeyDown(int keyCode, KeyEvent event) {
            return EditText.this.onKeyDown(keyCode, event);
        }

        public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
            return EditText.this.onKeyMultiple(keyCode, repeatCount, event);
        }

        public boolean onKeyPreIme(int keyCode, KeyEvent event) {
            return EditText.this.onKeyPreIme(keyCode, event);
        }

        public boolean onKeyShortcut(int keyCode, KeyEvent event) {
            return EditText.this.onKeyShortcut(keyCode, event);
        }

        public boolean onKeyUp(int keyCode, KeyEvent event) {
            return EditText.this.onKeyUp(keyCode, event);
        }

        /* access modifiers changed from: protected */
        public void onSelectionChanged(int selStart, int selEnd) {
            EditText.this.onSelectionChanged(selStart, selEnd);
        }

        public void onFilterComplete(int count) {
            EditText.this.onFilterComplete(count);
        }

        /* access modifiers changed from: protected */
        public CharSequence convertSelectionToString(Object selectedItem) {
            return EditText.this.convertSelectionToString(selectedItem);
        }

        /* access modifiers changed from: protected */
        public void performFiltering(CharSequence text, int keyCode) {
            EditText.this.performFiltering(text, keyCode);
        }

        /* access modifiers changed from: protected */
        public void replaceText(CharSequence text) {
            EditText.this.replaceText(text);
        }

        /* access modifiers changed from: protected */
        public Filter getFilter() {
            return EditText.this.getFilter();
        }

        /* access modifiers changed from: protected */
        public void performFiltering(CharSequence text, int start, int end, int keyCode) {
            EditText.this.performFiltering(text, start, end, keyCode);
        }

        /* access modifiers changed from: package-private */
        public void superOnCommitCompletion(CompletionInfo text) {
            super.onCommitCompletion(text);
        }

        /* access modifiers changed from: package-private */
        public void superOnCommitCorrection(CorrectionInfo info) {
            if (Build.VERSION.SDK_INT >= 11) {
                super.onCommitCorrection(info);
            }
        }

        /* access modifiers changed from: package-private */
        public InputConnection superOnCreateInputConnection(EditorInfo outAttrs) {
            return super.onCreateInputConnection(outAttrs);
        }

        /* access modifiers changed from: package-private */
        public void superOnEditorAction(int actionCode) {
            super.onEditorAction(actionCode);
        }

        /* access modifiers changed from: package-private */
        public boolean superOnKeyDown(int keyCode, KeyEvent event) {
            return super.onKeyDown(keyCode, event);
        }

        /* access modifiers changed from: package-private */
        public boolean superOnKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
            return super.onKeyMultiple(keyCode, repeatCount, event);
        }

        /* access modifiers changed from: package-private */
        public boolean superOnKeyPreIme(int keyCode, KeyEvent event) {
            return super.onKeyPreIme(keyCode, event);
        }

        /* access modifiers changed from: package-private */
        public boolean superOnKeyShortcut(int keyCode, KeyEvent event) {
            return super.onKeyShortcut(keyCode, event);
        }

        /* access modifiers changed from: package-private */
        public boolean superOnKeyUp(int keyCode, KeyEvent event) {
            return super.onKeyUp(keyCode, event);
        }

        /* access modifiers changed from: package-private */
        public void superOnFilterComplete(int count) {
            super.onFilterComplete(count);
        }

        /* access modifiers changed from: package-private */
        public CharSequence superConvertSelectionToString(Object selectedItem) {
            return super.convertSelectionToString(selectedItem);
        }

        /* access modifiers changed from: package-private */
        public void superPerformFiltering(CharSequence text, int keyCode) {
            super.performFiltering(text, keyCode);
        }

        /* access modifiers changed from: package-private */
        public void superReplaceText(CharSequence text) {
            super.replaceText(text);
        }

        /* access modifiers changed from: package-private */
        public Filter superGetFilter() {
            return super.getFilter();
        }

        /* access modifiers changed from: package-private */
        public void superPerformFiltering(CharSequence text, int start, int end, int keyCode) {
            super.performFiltering(text, start, end, keyCode);
        }

        /* access modifiers changed from: package-private */
        public void superOnSelectionChanged(int selStart, int selEnd) {
            super.onSelectionChanged(selStart, selEnd);
        }
    }
}
