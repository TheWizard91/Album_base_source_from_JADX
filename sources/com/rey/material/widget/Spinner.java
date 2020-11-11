package com.rey.material.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.google.common.primitives.Ints;
import com.rey.material.C2500R;
import com.rey.material.app.ThemeManager;
import com.rey.material.drawable.ArrowDrawable;
import com.rey.material.drawable.DividerDrawable;

public class Spinner extends FrameLayout implements ThemeManager.OnThemeChangedListener {
    private static final int INVALID_POSITION = -1;
    private static final int MAX_ITEMS_MEASURED = 15;
    private SpinnerAdapter mAdapter;
    private boolean mArrowAnimSwitchMode;
    /* access modifiers changed from: private */
    public ArrowDrawable mArrowDrawable;
    private int mArrowPadding;
    private int mArrowSize;
    private SpinnerDataSetObserver mDataSetObserver = new SpinnerDataSetObserver();
    private boolean mDisableChildrenWhenDisabled;
    private DividerDrawable mDividerDrawable;
    private int mDividerHeight;
    private int mDividerPadding;
    /* access modifiers changed from: private */
    public int mDropDownWidth;
    private int mGravity;
    /* access modifiers changed from: private */
    public boolean mIsRtl;
    private boolean mLabelEnable;
    private TextView mLabelView;
    private int mMinHeight;
    private int mMinWidth;
    private OnItemClickListener mOnItemClickListener;
    private OnItemSelectedListener mOnItemSelectedListener;
    private DropdownPopup mPopup;
    private RecycleBin mRecycler = new RecycleBin();
    private int mSelectedPosition;
    private DropDownAdapter mTempAdapter;
    /* access modifiers changed from: private */
    public Rect mTempRect = new Rect();

    public interface OnItemClickListener {
        boolean onItemClick(Spinner spinner, View view, int i, long j);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(Spinner spinner, View view, int i, long j);
    }

    public Spinner(Context context) {
        super(context, (AttributeSet) null, C2500R.attr.listPopupWindowStyle);
    }

    public Spinner(Context context, AttributeSet attrs) {
        super(context, attrs, C2500R.attr.listPopupWindowStyle);
    }

    public Spinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mLabelEnable = false;
        this.mDropDownWidth = -2;
        this.mArrowAnimSwitchMode = false;
        this.mGravity = 17;
        this.mDisableChildrenWhenDisabled = false;
        this.mSelectedPosition = -1;
        this.mIsRtl = false;
        setWillNotDraw(false);
        DropdownPopup dropdownPopup = new DropdownPopup(context, attrs, defStyleAttr, defStyleRes);
        this.mPopup = dropdownPopup;
        dropdownPopup.setModal(true);
        if (isInEditMode()) {
            applyStyle(C2500R.C2505style.Material_Widget_Spinner);
        }
        setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Spinner.this.showPopup();
            }
        });
        super.init(context, attrs, defStyleAttr, defStyleRes);
    }

    private TextView getLabelView() {
        if (this.mLabelView == null) {
            this.mLabelView = new TextView(getContext());
            if (Build.VERSION.SDK_INT >= 17) {
                this.mLabelView.setTextDirection(this.mIsRtl ? 4 : 3);
            }
            this.mLabelView.setSingleLine(true);
            this.mLabelView.setDuplicateParentStateEnabled(true);
        }
        return this.mLabelView;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x0338  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x0344  */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x034d  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x035d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void applyStyle(android.content.Context r26, android.util.AttributeSet r27, int r28, int r29) {
        /*
            r25 = this;
            r0 = r25
            r1 = r26
            r2 = r27
            r3 = r28
            super.applyStyle(r26, r27, r28, r29)
            r25.removeAllViews()
            int[] r4 = com.rey.material.C2500R.styleable.Spinner
            r5 = r29
            android.content.res.TypedArray r4 = r1.obtainStyledAttributes(r2, r4, r3, r5)
            r6 = -1
            r7 = 0
            r8 = 0
            r9 = 1
            r10 = -1
            r11 = 0
            r12 = 0
            r13 = -1
            r14 = 0
            int r15 = r4.getIndexCount()
            r22 = r14
            r14 = r8
            r8 = r22
            r23 = r13
            r13 = r9
            r9 = r23
            r24 = r12
            r12 = r10
            r10 = r24
        L_0x0032:
            if (r8 >= r15) goto L_0x0219
            int r5 = r4.getIndex(r8)
            r17 = r11
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_labelEnable
            if (r5 != r11) goto L_0x004b
            r11 = 0
            boolean r11 = r4.getBoolean(r5, r11)
            r0.mLabelEnable = r11
            r18 = r12
            r19 = r13
            goto L_0x020d
        L_0x004b:
            r11 = 0
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_labelPadding
            if (r5 != r11) goto L_0x0062
            android.widget.TextView r11 = r25.getLabelView()
            r18 = r12
            r19 = r13
            r12 = 0
            int r13 = r4.getDimensionPixelSize(r5, r12)
            r11.setPadding(r12, r12, r12, r13)
            goto L_0x020d
        L_0x0062:
            r18 = r12
            r19 = r13
            r12 = 0
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_labelTextSize
            if (r5 != r11) goto L_0x0077
            int r9 = r4.getDimensionPixelSize(r5, r12)
            r11 = r17
            r12 = r18
            r13 = r19
            goto L_0x0213
        L_0x0077:
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_labelTextColor
            if (r5 != r11) goto L_0x0087
            android.content.res.ColorStateList r10 = r4.getColorStateList(r5)
            r11 = r17
            r12 = r18
            r13 = r19
            goto L_0x0213
        L_0x0087:
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_labelTextAppearance
            if (r5 != r11) goto L_0x0099
            android.widget.TextView r11 = r25.getLabelView()
            r12 = 0
            int r12 = r4.getResourceId(r5, r12)
            r11.setTextAppearance(r1, r12)
            goto L_0x020d
        L_0x0099:
            r12 = 0
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_labelEllipsize
            if (r5 != r11) goto L_0x00e2
            int r11 = r4.getInteger(r5, r12)
            r12 = 1
            if (r11 == r12) goto L_0x00d6
            r12 = 2
            if (r11 == r12) goto L_0x00cc
            r12 = 3
            if (r11 == r12) goto L_0x00c2
            r12 = 4
            if (r11 == r12) goto L_0x00b8
            android.widget.TextView r12 = r25.getLabelView()
            android.text.TextUtils$TruncateAt r13 = android.text.TextUtils.TruncateAt.END
            r12.setEllipsize(r13)
            goto L_0x00e0
        L_0x00b8:
            android.widget.TextView r12 = r25.getLabelView()
            android.text.TextUtils$TruncateAt r13 = android.text.TextUtils.TruncateAt.MARQUEE
            r12.setEllipsize(r13)
            goto L_0x00e0
        L_0x00c2:
            android.widget.TextView r12 = r25.getLabelView()
            android.text.TextUtils$TruncateAt r13 = android.text.TextUtils.TruncateAt.END
            r12.setEllipsize(r13)
            goto L_0x00e0
        L_0x00cc:
            android.widget.TextView r12 = r25.getLabelView()
            android.text.TextUtils$TruncateAt r13 = android.text.TextUtils.TruncateAt.MIDDLE
            r12.setEllipsize(r13)
            goto L_0x00e0
        L_0x00d6:
            android.widget.TextView r12 = r25.getLabelView()
            android.text.TextUtils$TruncateAt r13 = android.text.TextUtils.TruncateAt.START
            r12.setEllipsize(r13)
        L_0x00e0:
            goto L_0x020d
        L_0x00e2:
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_label
            if (r5 != r11) goto L_0x00f3
            android.widget.TextView r11 = r25.getLabelView()
            java.lang.String r12 = r4.getString(r5)
            r11.setText(r12)
            goto L_0x020d
        L_0x00f3:
            int r11 = com.rey.material.C2500R.styleable.Spinner_android_gravity
            if (r5 != r11) goto L_0x0100
            r11 = 0
            int r11 = r4.getInt(r5, r11)
            r0.mGravity = r11
            goto L_0x020d
        L_0x0100:
            r11 = 0
            int r12 = com.rey.material.C2500R.styleable.Spinner_android_minWidth
            if (r5 != r12) goto L_0x010e
            int r11 = r4.getDimensionPixelOffset(r5, r11)
            r0.setMinimumWidth(r11)
            goto L_0x020d
        L_0x010e:
            int r12 = com.rey.material.C2500R.styleable.Spinner_android_minHeight
            if (r5 != r12) goto L_0x011b
            int r11 = r4.getDimensionPixelOffset(r5, r11)
            r0.setMinimumHeight(r11)
            goto L_0x020d
        L_0x011b:
            int r11 = com.rey.material.C2500R.styleable.Spinner_android_dropDownWidth
            if (r5 != r11) goto L_0x0128
            r11 = -2
            int r11 = r4.getLayoutDimension(r5, r11)
            r0.mDropDownWidth = r11
            goto L_0x020d
        L_0x0128:
            int r11 = com.rey.material.C2500R.styleable.Spinner_android_popupBackground
            if (r5 != r11) goto L_0x0137
            com.rey.material.widget.Spinner$DropdownPopup r11 = r0.mPopup
            android.graphics.drawable.Drawable r12 = r4.getDrawable(r5)
            r11.setBackgroundDrawable(r12)
            goto L_0x020d
        L_0x0137:
            int r11 = com.rey.material.C2500R.styleable.Spinner_android_prompt
            if (r5 != r11) goto L_0x0146
            com.rey.material.widget.Spinner$DropdownPopup r11 = r0.mPopup
            java.lang.String r12 = r4.getString(r5)
            r11.setPromptText(r12)
            goto L_0x020d
        L_0x0146:
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_popupItemAnimation
            if (r5 != r11) goto L_0x0156
            com.rey.material.widget.Spinner$DropdownPopup r11 = r0.mPopup
            r12 = 0
            int r12 = r4.getResourceId(r5, r12)
            r11.setItemAnimation(r12)
            goto L_0x020d
        L_0x0156:
            r12 = 0
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_popupItemAnimOffset
            if (r5 != r11) goto L_0x0166
            com.rey.material.widget.Spinner$DropdownPopup r11 = r0.mPopup
            int r12 = r4.getInteger(r5, r12)
            r11.setItemAnimationOffset(r12)
            goto L_0x020d
        L_0x0166:
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_disableChildrenWhenDisabled
            if (r5 != r11) goto L_0x0172
            boolean r11 = r4.getBoolean(r5, r12)
            r0.mDisableChildrenWhenDisabled = r11
            goto L_0x020d
        L_0x0172:
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_arrowSwitchMode
            if (r5 != r11) goto L_0x017e
            boolean r11 = r4.getBoolean(r5, r12)
            r0.mArrowAnimSwitchMode = r11
            goto L_0x020d
        L_0x017e:
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_arrowAnimDuration
            if (r5 != r11) goto L_0x018e
            int r6 = r4.getInteger(r5, r12)
            r11 = r17
            r12 = r18
            r13 = r19
            goto L_0x0213
        L_0x018e:
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_arrowSize
            if (r5 != r11) goto L_0x019a
            int r11 = r4.getDimensionPixelSize(r5, r12)
            r0.mArrowSize = r11
            goto L_0x020d
        L_0x019a:
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_arrowPadding
            if (r5 != r11) goto L_0x01a6
            int r11 = r4.getDimensionPixelSize(r5, r12)
            r0.mArrowPadding = r11
            goto L_0x020d
        L_0x01a6:
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_arrowColor
            if (r5 != r11) goto L_0x01b6
            android.content.res.ColorStateList r7 = r4.getColorStateList(r5)
            r11 = r17
            r12 = r18
            r13 = r19
            goto L_0x0213
        L_0x01b6:
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_arrowInterpolator
            if (r5 != r11) goto L_0x01cb
            r11 = 0
            int r11 = r4.getResourceId(r5, r11)
            android.view.animation.Interpolator r11 = android.view.animation.AnimationUtils.loadInterpolator(r1, r11)
            r14 = r11
            r11 = r17
            r12 = r18
            r13 = r19
            goto L_0x0213
        L_0x01cb:
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_arrowAnimClockwise
            if (r5 != r11) goto L_0x01da
            r11 = 1
            boolean r11 = r4.getBoolean(r5, r11)
            r13 = r11
            r11 = r17
            r12 = r18
            goto L_0x0213
        L_0x01da:
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_dividerHeight
            if (r5 != r11) goto L_0x01e6
            r11 = 0
            int r11 = r4.getDimensionPixelOffset(r5, r11)
            r0.mDividerHeight = r11
            goto L_0x020d
        L_0x01e6:
            r11 = 0
            int r12 = com.rey.material.C2500R.styleable.Spinner_spn_dividerPadding
            if (r5 != r12) goto L_0x01f2
            int r11 = r4.getDimensionPixelOffset(r5, r11)
            r0.mDividerPadding = r11
            goto L_0x020d
        L_0x01f2:
            int r12 = com.rey.material.C2500R.styleable.Spinner_spn_dividerAnimDuration
            if (r5 != r12) goto L_0x0200
            int r11 = r4.getInteger(r5, r11)
            r12 = r11
            r11 = r17
            r13 = r19
            goto L_0x0213
        L_0x0200:
            int r11 = com.rey.material.C2500R.styleable.Spinner_spn_dividerColor
            if (r5 != r11) goto L_0x020d
            android.content.res.ColorStateList r11 = r4.getColorStateList(r5)
            r12 = r18
            r13 = r19
            goto L_0x0213
        L_0x020d:
            r11 = r17
            r12 = r18
            r13 = r19
        L_0x0213:
            int r8 = r8 + 1
            r5 = r29
            goto L_0x0032
        L_0x0219:
            r17 = r11
            r18 = r12
            r19 = r13
            r4.recycle()
            if (r10 == 0) goto L_0x022b
            android.widget.TextView r5 = r25.getLabelView()
            r5.setTextColor(r10)
        L_0x022b:
            if (r9 < 0) goto L_0x0237
            android.widget.TextView r5 = r25.getLabelView()
            float r8 = (float) r9
            r11 = 0
            r5.setTextSize(r11, r8)
            goto L_0x0238
        L_0x0237:
            r11 = 0
        L_0x0238:
            boolean r5 = r0.mLabelEnable
            if (r5 == 0) goto L_0x0249
            android.widget.TextView r5 = r25.getLabelView()
            android.view.ViewGroup$LayoutParams r8 = new android.view.ViewGroup$LayoutParams
            r12 = -2
            r8.<init>(r12, r12)
            r0.addView(r5, r11, r8)
        L_0x0249:
            int r5 = r0.mArrowSize
            r15 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r13 = 0
            if (r5 <= 0) goto L_0x02b6
            com.rey.material.drawable.ArrowDrawable r8 = r0.mArrowDrawable
            if (r8 != 0) goto L_0x0289
            if (r7 != 0) goto L_0x025e
            int r5 = com.rey.material.util.ThemeUtil.colorControlNormal(r1, r15)
            android.content.res.ColorStateList r7 = android.content.res.ColorStateList.valueOf(r5)
        L_0x025e:
            if (r6 >= 0) goto L_0x0261
            r6 = 0
        L_0x0261:
            com.rey.material.drawable.ArrowDrawable r5 = new com.rey.material.drawable.ArrowDrawable
            int r11 = com.rey.material.drawable.ArrowDrawable.MODE_DOWN
            int r12 = r0.mArrowSize
            r8 = r5
            r16 = r9
            r9 = r11
            r20 = r10
            r10 = r12
            r12 = r17
            r11 = r7
            r15 = r18
            r18 = r4
            r4 = r12
            r12 = r6
            r2 = r13
            r21 = r19
            r13 = r14
            r2 = r14
            r14 = r21
            r8.<init>(r9, r10, r11, r12, r13, r14)
            r0.mArrowDrawable = r5
            r5.setCallback(r0)
            r13 = r21
            goto L_0x02cd
        L_0x0289:
            r16 = r9
            r20 = r10
            r2 = r14
            r15 = r18
            r21 = r19
            r18 = r4
            r4 = r17
            r8.setArrowSize(r5)
            com.rey.material.drawable.ArrowDrawable r5 = r0.mArrowDrawable
            r13 = r21
            r5.setClockwise(r13)
            if (r7 == 0) goto L_0x02a7
            com.rey.material.drawable.ArrowDrawable r5 = r0.mArrowDrawable
            r5.setColor(r7)
        L_0x02a7:
            if (r6 < 0) goto L_0x02ae
            com.rey.material.drawable.ArrowDrawable r5 = r0.mArrowDrawable
            r5.setAnimationDuration(r6)
        L_0x02ae:
            if (r2 == 0) goto L_0x02cd
            com.rey.material.drawable.ArrowDrawable r5 = r0.mArrowDrawable
            r5.setInterpolator(r2)
            goto L_0x02cd
        L_0x02b6:
            r16 = r9
            r20 = r10
            r2 = r14
            r15 = r18
            r13 = r19
            r18 = r4
            r4 = r17
            com.rey.material.drawable.ArrowDrawable r5 = r0.mArrowDrawable
            if (r5 == 0) goto L_0x02cd
            r8 = 0
            r5.setCallback(r8)
            r0.mArrowDrawable = r8
        L_0x02cd:
            int r5 = r0.mDividerHeight
            if (r5 <= 0) goto L_0x0328
            com.rey.material.drawable.DividerDrawable r8 = r0.mDividerDrawable
            if (r8 != 0) goto L_0x0316
            if (r15 >= 0) goto L_0x02d9
            r12 = 0
            goto L_0x02da
        L_0x02d9:
            r12 = r15
        L_0x02da:
            if (r4 != 0) goto L_0x0308
            r5 = 2
            int[][] r8 = new int[r5][]
            r9 = 1
            int[] r10 = new int[r9]
            r11 = -16842919(0xfffffffffefeff59, float:-1.6947488E38)
            r14 = 0
            r10[r14] = r11
            r8[r14] = r10
            int[] r10 = new int[r5]
            r10 = {16842919, 16842910} // fill-array
            r8[r9] = r10
            int[] r5 = new int[r5]
            r10 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            int r11 = com.rey.material.util.ThemeUtil.colorControlNormal(r1, r10)
            r5[r14] = r11
            int r10 = com.rey.material.util.ThemeUtil.colorControlActivated(r1, r10)
            r5[r9] = r10
            android.content.res.ColorStateList r9 = new android.content.res.ColorStateList
            r9.<init>(r8, r5)
            r11 = r9
            goto L_0x0309
        L_0x0308:
            r11 = r4
        L_0x0309:
            com.rey.material.drawable.DividerDrawable r4 = new com.rey.material.drawable.DividerDrawable
            int r5 = r0.mDividerHeight
            r4.<init>(r5, r11, r12)
            r0.mDividerDrawable = r4
            r4.setCallback(r0)
            goto L_0x0334
        L_0x0316:
            r8.setDividerHeight(r5)
            if (r4 == 0) goto L_0x0320
            com.rey.material.drawable.DividerDrawable r5 = r0.mDividerDrawable
            r5.setColor(r4)
        L_0x0320:
            if (r15 < 0) goto L_0x0332
            com.rey.material.drawable.DividerDrawable r5 = r0.mDividerDrawable
            r5.setAnimationDuration(r15)
            goto L_0x0332
        L_0x0328:
            com.rey.material.drawable.DividerDrawable r5 = r0.mDividerDrawable
            if (r5 == 0) goto L_0x0332
            r8 = 0
            r5.setCallback(r8)
            r0.mDividerDrawable = r8
        L_0x0332:
            r11 = r4
            r12 = r15
        L_0x0334:
            com.rey.material.widget.Spinner$DropDownAdapter r4 = r0.mTempAdapter
            if (r4 == 0) goto L_0x0340
            com.rey.material.widget.Spinner$DropdownPopup r5 = r0.mPopup
            r5.setAdapter(r4)
            r4 = 0
            r0.mTempAdapter = r4
        L_0x0340:
            android.widget.SpinnerAdapter r4 = r0.mAdapter
            if (r4 == 0) goto L_0x0347
            r0.setAdapter(r4)
        L_0x0347:
            boolean r4 = r25.isInEditMode()
            if (r4 == 0) goto L_0x035d
            com.rey.material.widget.TextView r4 = new com.rey.material.widget.TextView
            r5 = r27
            r4.<init>(r1, r5, r3)
            java.lang.String r8 = "Item 1"
            r4.setText(r8)
            super.addView(r4)
            goto L_0x035f
        L_0x035d:
            r5 = r27
        L_0x035f:
            r25.requestLayout()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rey.material.widget.Spinner.applyStyle(android.content.Context, android.util.AttributeSet, int, int):void");
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        boolean rtl = true;
        if (layoutDirection != 1) {
            rtl = false;
        }
        if (this.mIsRtl != rtl) {
            this.mIsRtl = rtl;
            if (this.mLabelView != null && Build.VERSION.SDK_INT >= 17) {
                this.mLabelView.setTextDirection(this.mIsRtl ? 4 : 3);
            }
            requestLayout();
        }
    }

    public View getSelectedView() {
        View v = getChildAt(getChildCount() - 1);
        if (v == this.mLabelView) {
            return null;
        }
        return v;
    }

    public void setSelection(int position) {
        SpinnerAdapter spinnerAdapter = this.mAdapter;
        if (spinnerAdapter != null) {
            position = Math.max(0, Math.min(position, spinnerAdapter.getCount() - 1));
        }
        if (this.mSelectedPosition != position) {
            this.mSelectedPosition = position;
            OnItemSelectedListener onItemSelectedListener = this.mOnItemSelectedListener;
            if (onItemSelectedListener != null) {
                View selectedView = getSelectedView();
                SpinnerAdapter spinnerAdapter2 = this.mAdapter;
                onItemSelectedListener.onItemSelected(this, selectedView, position, spinnerAdapter2 == null ? -1 : spinnerAdapter2.getItemId(position));
            }
            onDataInvalidated();
        }
    }

    public int getSelectedItemPosition() {
        return this.mSelectedPosition;
    }

    public Object getSelectedItem() {
        SpinnerAdapter spinnerAdapter = this.mAdapter;
        if (spinnerAdapter == null) {
            return null;
        }
        return spinnerAdapter.getItem(this.mSelectedPosition);
    }

    public SpinnerAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(SpinnerAdapter adapter) {
        SpinnerAdapter spinnerAdapter = this.mAdapter;
        if (spinnerAdapter != null) {
            spinnerAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        this.mRecycler.clear();
        this.mAdapter = adapter;
        adapter.registerDataSetObserver(this.mDataSetObserver);
        onDataChanged();
        DropdownPopup dropdownPopup = this.mPopup;
        if (dropdownPopup != null) {
            dropdownPopup.setAdapter(new DropDownAdapter(adapter));
        } else {
            this.mTempAdapter = new DropDownAdapter(adapter);
        }
    }

    public void setPopupBackgroundDrawable(Drawable background) {
        this.mPopup.setBackgroundDrawable(background);
    }

    public void setPopupBackgroundResource(int resId) {
        setPopupBackgroundDrawable(getContext().getDrawable(resId));
    }

    public Drawable getPopupBackground() {
        return this.mPopup.getBackground();
    }

    public void setDropDownVerticalOffset(int pixels) {
        this.mPopup.setVerticalOffset(pixels);
    }

    public int getDropDownVerticalOffset() {
        return this.mPopup.getVerticalOffset();
    }

    public void setDropDownHorizontalOffset(int pixels) {
        this.mPopup.setHorizontalOffset(pixels);
    }

    public int getDropDownHorizontalOffset() {
        return this.mPopup.getHorizontalOffset();
    }

    public void setDropDownWidth(int pixels) {
        this.mDropDownWidth = pixels;
    }

    public int getDropDownWidth() {
        return this.mDropDownWidth;
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (this.mDisableChildrenWhenDisabled) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                getChildAt(i).setEnabled(enabled);
            }
        }
    }

    public void setMinimumHeight(int minHeight) {
        this.mMinHeight = minHeight;
        super.setMinimumHeight(minHeight);
    }

    public void setMinimumWidth(int minWidth) {
        this.mMinWidth = minWidth;
        super.setMinimumWidth(minWidth);
    }

    public void setGravity(int gravity) {
        if (this.mGravity != gravity) {
            if ((gravity & 7) == 0) {
                gravity |= 8388611;
            }
            this.mGravity = gravity;
            requestLayout();
        }
    }

    public int getBaseline() {
        int childBaseline;
        View child = getSelectedView();
        if (child == null || (childBaseline = child.getBaseline()) < 0) {
            return -1;
        }
        int paddingTop = getPaddingTop();
        TextView textView = this.mLabelView;
        if (textView != null) {
            paddingTop += textView.getMeasuredHeight();
        }
        int remainHeight = ((getMeasuredHeight() - paddingTop) - getPaddingBottom()) - getDividerDrawableHeight();
        int verticalGravity = this.mGravity & 112;
        if (verticalGravity == 48) {
            return paddingTop + childBaseline;
        }
        if (verticalGravity != 80) {
            return ((remainHeight - child.getMeasuredHeight()) / 2) + paddingTop + childBaseline;
        }
        return ((paddingTop + remainHeight) - child.getMeasuredHeight()) + childBaseline;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        DropdownPopup dropdownPopup = this.mPopup;
        if (dropdownPopup != null && dropdownPopup.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.mOnItemClickListener = l;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener l) {
        this.mOnItemSelectedListener = l;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || this.mArrowDrawable == who || this.mDividerDrawable == who;
    }

    private int getArrowDrawableWidth() {
        if (this.mArrowDrawable != null) {
            return this.mArrowSize + (this.mArrowPadding * 2);
        }
        return 0;
    }

    private int getDividerDrawableHeight() {
        int i = this.mDividerHeight;
        if (i > 0) {
            return i + this.mDividerPadding;
        }
        return 0;
    }

    private int getSpec(int availableSize, int size) {
        if (size != -2) {
            if (size != -1) {
                return View.MeasureSpec.makeMeasureSpec(size, Ints.MAX_POWER_OF_TWO);
            }
            if (availableSize > 0) {
                return View.MeasureSpec.makeMeasureSpec(availableSize, Ints.MAX_POWER_OF_TWO);
            }
            return View.MeasureSpec.makeMeasureSpec(0, 0);
        } else if (availableSize > 0) {
            return View.MeasureSpec.makeMeasureSpec(availableSize, Integer.MIN_VALUE);
        } else {
            return View.MeasureSpec.makeMeasureSpec(0, 0);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int viewWidth;
        int viewHeight;
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int paddingHorizontal = getPaddingLeft() + getPaddingRight() + getArrowDrawableWidth();
        int paddingVertical = getPaddingTop() + getPaddingBottom() + getDividerDrawableHeight();
        int labelWidth = 0;
        int labelHeight = 0;
        TextView textView = this.mLabelView;
        if (!(textView == null || textView.getLayoutParams() == null)) {
            this.mLabelView.measure(View.MeasureSpec.makeMeasureSpec(widthMode == 0 ? 0 : widthSize - paddingHorizontal, widthMode), View.MeasureSpec.makeMeasureSpec(0, 0));
            labelWidth = this.mLabelView.getMeasuredWidth();
            labelHeight = this.mLabelView.getMeasuredHeight();
        }
        int width = 0;
        int height = 0;
        View v = getSelectedView();
        if (v != null) {
            ViewGroup.LayoutParams params = v.getLayoutParams();
            v.measure(getSpec(widthSize - paddingHorizontal, params.width), getSpec((heightSize - paddingVertical) - this.mLabelView.getMeasuredHeight(), params.height));
            width = v.getMeasuredWidth();
            height = v.getMeasuredHeight();
        }
        int width2 = Math.max(this.mMinWidth, Math.max(labelWidth, width) + paddingHorizontal);
        int height2 = Math.max(this.mMinHeight, height + labelHeight + paddingVertical);
        if (widthMode == Integer.MIN_VALUE) {
            width2 = Math.min(widthSize, width2);
        } else if (widthMode == 1073741824) {
            width2 = widthSize;
        }
        if (heightMode == Integer.MIN_VALUE) {
            height2 = Math.min(heightSize, height2);
        } else if (heightMode == 1073741824) {
            height2 = heightSize;
        }
        setMeasuredDimension(width2, height2);
        if (v != null) {
            ViewGroup.LayoutParams params2 = v.getLayoutParams();
            int i = params2.width;
            if (i == -2) {
                viewWidth = v.getMeasuredWidth();
            } else if (i != -1) {
                viewWidth = params2.width;
            } else {
                viewWidth = width2 - paddingHorizontal;
            }
            int i2 = params2.height;
            if (i2 == -2) {
                viewHeight = v.getMeasuredHeight();
            } else if (i2 != -1) {
                viewHeight = params2.height;
            } else {
                viewHeight = (height2 - labelHeight) - paddingVertical;
            }
            if (v.getMeasuredWidth() != viewWidth || v.getMeasuredHeight() != viewHeight) {
                v.measure(View.MeasureSpec.makeMeasureSpec(viewWidth, Ints.MAX_POWER_OF_TWO), View.MeasureSpec.makeMeasureSpec(viewHeight, Ints.MAX_POWER_OF_TWO));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        int x;
        int y;
        int w = r - l;
        int h = b - t;
        int arrowWidth = getArrowDrawableWidth();
        if (this.mArrowDrawable != null) {
            int paddingTop = getPaddingTop();
            TextView textView = this.mLabelView;
            int top = paddingTop + (textView == null ? 0 : textView.getMeasuredHeight());
            int bottom = (h - getDividerDrawableHeight()) - getPaddingBottom();
            if (this.mIsRtl) {
                this.mArrowDrawable.setBounds(getPaddingLeft(), top, getPaddingLeft() + arrowWidth, bottom);
            } else {
                this.mArrowDrawable.setBounds((getWidth() - getPaddingRight()) - arrowWidth, top, getWidth() - getPaddingRight(), bottom);
            }
        }
        DividerDrawable dividerDrawable = this.mDividerDrawable;
        if (dividerDrawable != null) {
            dividerDrawable.setBounds(getPaddingLeft(), (h - this.mDividerHeight) - getPaddingBottom(), w - getPaddingRight(), h - getPaddingBottom());
        }
        int childLeft = this.mIsRtl ? getPaddingLeft() + arrowWidth : getPaddingLeft();
        int childRight = this.mIsRtl ? w - getPaddingRight() : (w - getPaddingRight()) - arrowWidth;
        int childTop = getPaddingTop();
        int childBottom = h - getPaddingBottom();
        TextView textView2 = this.mLabelView;
        if (textView2 != null) {
            if (this.mIsRtl) {
                textView2.layout(childRight - textView2.getMeasuredWidth(), childTop, childRight, this.mLabelView.getMeasuredHeight() + childTop);
            } else {
                textView2.layout(childLeft, childTop, textView2.getMeasuredWidth() + childLeft, this.mLabelView.getMeasuredHeight() + childTop);
            }
            childTop += this.mLabelView.getMeasuredHeight();
        }
        View v = getSelectedView();
        if (v != null) {
            int horizontalGravity = this.mGravity & 7;
            if (horizontalGravity == 8388611) {
                horizontalGravity = this.mIsRtl ? 5 : 3;
            } else if (horizontalGravity == 8388613) {
                horizontalGravity = this.mIsRtl ? 3 : 5;
            }
            if (horizontalGravity == 1) {
                x = (((childRight - childLeft) - v.getMeasuredWidth()) / 2) + childLeft;
            } else if (horizontalGravity == 3) {
                x = childLeft;
            } else if (horizontalGravity != 5) {
                x = (((childRight - childLeft) - v.getMeasuredWidth()) / 2) + childLeft;
            } else {
                x = childRight - v.getMeasuredWidth();
            }
            int verticalGravity = this.mGravity & 112;
            if (verticalGravity == 16) {
                y = (((childBottom - childTop) - v.getMeasuredHeight()) / 2) + childTop;
            } else if (verticalGravity == 48) {
                y = childTop;
            } else if (verticalGravity != 80) {
                y = (((childBottom - childTop) - v.getMeasuredHeight()) / 2) + childTop;
            } else {
                y = childBottom - v.getMeasuredHeight();
            }
            v.layout(x, y, v.getMeasuredWidth() + x, v.getMeasuredHeight() + y);
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        DividerDrawable dividerDrawable = this.mDividerDrawable;
        if (dividerDrawable != null) {
            dividerDrawable.draw(canvas);
        }
        ArrowDrawable arrowDrawable = this.mArrowDrawable;
        if (arrowDrawable != null) {
            arrowDrawable.draw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        ArrowDrawable arrowDrawable = this.mArrowDrawable;
        if (arrowDrawable != null) {
            arrowDrawable.setState(getDrawableState());
        }
        DividerDrawable dividerDrawable = this.mDividerDrawable;
        if (dividerDrawable != null) {
            dividerDrawable.setState(getDrawableState());
        }
    }

    public boolean performItemClick(View view, int position, long id) {
        OnItemClickListener onItemClickListener = this.mOnItemClickListener;
        if (onItemClickListener == null) {
            setSelection(position);
            return false;
        } else if (!onItemClickListener.onItemClick(this, view, position, id)) {
            return true;
        } else {
            setSelection(position);
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void onDataChanged() {
        int i = this.mSelectedPosition;
        if (i == -1) {
            setSelection(0);
        } else if (i < this.mAdapter.getCount()) {
            onDataInvalidated();
        } else {
            setSelection(this.mAdapter.getCount() - 1);
        }
    }

    /* access modifiers changed from: private */
    public void onDataInvalidated() {
        if (this.mAdapter != null) {
            if (this.mLabelView == null) {
                removeAllViews();
            } else {
                for (int i = getChildCount() - 1; i > 0; i--) {
                    removeViewAt(i);
                }
            }
            int type = this.mAdapter.getItemViewType(this.mSelectedPosition);
            View v = this.mAdapter.getView(this.mSelectedPosition, this.mRecycler.get(type), this);
            v.setFocusable(false);
            v.setClickable(false);
            if (v.getParent() != null) {
                ((ViewGroup) v.getParent()).removeView(v);
            }
            super.addView(v);
            this.mRecycler.put(type, v);
        }
    }

    /* access modifiers changed from: private */
    public void showPopup() {
        if (!this.mPopup.isShowing()) {
            this.mPopup.show();
            final ListView lv = this.mPopup.getListView();
            if (lv != null) {
                if (Build.VERSION.SDK_INT >= 11) {
                    lv.setChoiceMode(1);
                }
                lv.setSelection(getSelectedItemPosition());
                if (this.mArrowDrawable != null && this.mArrowAnimSwitchMode) {
                    lv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        public boolean onPreDraw() {
                            lv.getViewTreeObserver().removeOnPreDrawListener(this);
                            Spinner.this.mArrowDrawable.setMode(ArrowDrawable.MODE_UP, true);
                            return true;
                        }
                    });
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void onPopupDismissed() {
        ArrowDrawable arrowDrawable = this.mArrowDrawable;
        if (arrowDrawable != null) {
            arrowDrawable.setMode(ArrowDrawable.MODE_DOWN, true);
        }
    }

    /* access modifiers changed from: private */
    public int measureContentWidth(SpinnerAdapter adapter, Drawable background) {
        if (adapter == null) {
            return 0;
        }
        int width = 0;
        View itemView = null;
        int itemType = 0;
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        int start = Math.max(0, getSelectedItemPosition());
        int end = Math.min(adapter.getCount(), start + 15);
        for (int i = Math.max(0, start - (15 - (end - start))); i < end; i++) {
            int positionType = adapter.getItemViewType(i);
            if (positionType != itemType) {
                itemType = positionType;
                itemView = null;
            }
            itemView = adapter.getView(i, itemView, this);
            if (itemView.getLayoutParams() == null) {
                itemView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            }
            itemView.measure(widthMeasureSpec, heightMeasureSpec);
            width = Math.max(width, itemView.getMeasuredWidth());
        }
        if (background == null) {
            return width;
        }
        background.getPadding(this.mTempRect);
        return width + this.mTempRect.left + this.mTempRect.right;
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int position;
        boolean showDropdown;

        SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel in) {
            super(in);
            this.position = in.readInt();
            this.showDropdown = in.readByte() != 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.position);
            out.writeByte(this.showDropdown ? (byte) 1 : 0);
        }

        public String toString() {
            return "AbsSpinner.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + " showDropdown=" + this.showDropdown + "}";
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.position = getSelectedItemPosition();
        DropdownPopup dropdownPopup = this.mPopup;
        ss.showDropdown = dropdownPopup != null && dropdownPopup.isShowing();
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        ViewTreeObserver vto;
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setSelection(ss.position);
        if (ss.showDropdown && (vto = getViewTreeObserver()) != null) {
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    Spinner.this.showPopup();
                    ViewTreeObserver vto = Spinner.this.getViewTreeObserver();
                    if (vto != null) {
                        vto.removeGlobalOnLayoutListener(this);
                    }
                }
            });
        }
    }

    private class SpinnerDataSetObserver extends DataSetObserver {
        private SpinnerDataSetObserver() {
        }

        public void onChanged() {
            Spinner.this.onDataChanged();
        }

        public void onInvalidated() {
            Spinner.this.onDataInvalidated();
        }
    }

    private class RecycleBin {
        private final SparseArray<View> mScrapHeap;

        private RecycleBin() {
            this.mScrapHeap = new SparseArray<>();
        }

        public void put(int type, View v) {
            this.mScrapHeap.put(type, v);
        }

        /* access modifiers changed from: package-private */
        public View get(int type) {
            View result = this.mScrapHeap.get(type);
            if (result != null) {
                this.mScrapHeap.delete(type);
            }
            return result;
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            this.mScrapHeap.clear();
        }
    }

    private static class DropDownAdapter implements ListAdapter, SpinnerAdapter, View.OnClickListener {
        private SpinnerAdapter mAdapter;
        private ListAdapter mListAdapter;
        private AdapterView.OnItemClickListener mOnItemClickListener;

        public DropDownAdapter(SpinnerAdapter adapter) {
            this.mAdapter = adapter;
            if (adapter instanceof ListAdapter) {
                this.mListAdapter = (ListAdapter) adapter;
            }
        }

        public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
            this.mOnItemClickListener = listener;
        }

        public void onClick(View v) {
            int position = ((Integer) v.getTag()).intValue();
            AdapterView.OnItemClickListener onItemClickListener = this.mOnItemClickListener;
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick((AdapterView) null, v, position, 0);
            }
        }

        public int getCount() {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return 0;
            }
            return spinnerAdapter.getCount();
        }

        public Object getItem(int position) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getItem(position);
        }

        public long getItemId(int position) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return -1;
            }
            return spinnerAdapter.getItemId(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = getDropDownView(position, convertView, parent);
            v.setOnClickListener(this);
            v.setTag(Integer.valueOf(position));
            return v;
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getDropDownView(position, convertView, parent);
        }

        public boolean hasStableIds() {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            return spinnerAdapter != null && spinnerAdapter.hasStableIds();
        }

        public boolean areAllItemsEnabled() {
            ListAdapter adapter = this.mListAdapter;
            return adapter == null || adapter.areAllItemsEnabled();
        }

        public boolean isEnabled(int position) {
            ListAdapter adapter = this.mListAdapter;
            return adapter == null || adapter.isEnabled(position);
        }

        public int getItemViewType(int position) {
            ListAdapter adapter = this.mListAdapter;
            if (adapter != null) {
                return adapter.getItemViewType(position);
            }
            return 0;
        }

        public int getViewTypeCount() {
            ListAdapter adapter = this.mListAdapter;
            if (adapter != null) {
                return adapter.getViewTypeCount();
            }
            return 1;
        }

        public boolean isEmpty() {
            return getCount() == 0;
        }

        public void registerDataSetObserver(DataSetObserver observer) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter != null) {
                spinnerAdapter.registerDataSetObserver(observer);
            }
        }

        public void unregisterDataSetObserver(DataSetObserver observer) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter != null) {
                spinnerAdapter.unregisterDataSetObserver(observer);
            }
        }
    }

    private class DropdownPopup extends ListPopupWindow {
        /* access modifiers changed from: private */
        public ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                DropdownPopup.this.computeContentWidth();
                DropdownPopup.super.show();
            }
        };
        /* access modifiers changed from: private */
        public DropDownAdapter mAdapter;
        private CharSequence mHintText;

        public DropdownPopup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            setAnchorView(Spinner.this);
            setModal(true);
            setPromptPosition(0);
            setOnDismissListener(new PopupWindow.OnDismissListener(Spinner.this) {
                public void onDismiss() {
                    ViewTreeObserver vto = Spinner.this.getViewTreeObserver();
                    if (vto != null) {
                        if (Build.VERSION.SDK_INT >= 16) {
                            vto.removeOnGlobalLayoutListener(DropdownPopup.this.layoutListener);
                        } else {
                            vto.removeGlobalOnLayoutListener(DropdownPopup.this.layoutListener);
                        }
                    }
                    Spinner.this.onPopupDismissed();
                }
            });
        }

        public void setAdapter(ListAdapter adapter) {
            super.setAdapter(adapter);
            DropDownAdapter dropDownAdapter = (DropDownAdapter) adapter;
            this.mAdapter = dropDownAdapter;
            dropDownAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                    Spinner.this.performItemClick(v, position, DropdownPopup.this.mAdapter.getItemId(position));
                    DropdownPopup.this.dismiss();
                }
            });
        }

        public CharSequence getHintText() {
            return this.mHintText;
        }

        public void setPromptText(CharSequence hintText) {
            this.mHintText = hintText;
        }

        /* access modifiers changed from: package-private */
        public void computeContentWidth() {
            int hOffset;
            Drawable background = getBackground();
            int hOffset2 = 0;
            if (background != null) {
                background.getPadding(Spinner.this.mTempRect);
                hOffset2 = Spinner.this.mIsRtl ? Spinner.this.mTempRect.right : -Spinner.this.mTempRect.left;
            } else {
                Rect access$1000 = Spinner.this.mTempRect;
                Spinner.this.mTempRect.right = 0;
                access$1000.left = 0;
            }
            int spinnerPaddingLeft = Spinner.this.getPaddingLeft();
            int spinnerPaddingRight = Spinner.this.getPaddingRight();
            int spinnerWidth = Spinner.this.getWidth();
            if (Spinner.this.mDropDownWidth == -2) {
                int contentWidth = Spinner.this.measureContentWidth(this.mAdapter, getBackground());
                int contentWidthLimit = (Spinner.this.getContext().getResources().getDisplayMetrics().widthPixels - Spinner.this.mTempRect.left) - Spinner.this.mTempRect.right;
                if (contentWidth > contentWidthLimit) {
                    contentWidth = contentWidthLimit;
                }
                setContentWidth(Math.max(contentWidth, (spinnerWidth - spinnerPaddingLeft) - spinnerPaddingRight));
            } else if (Spinner.this.mDropDownWidth == -1) {
                setContentWidth((spinnerWidth - spinnerPaddingLeft) - spinnerPaddingRight);
            } else {
                setContentWidth(Spinner.this.mDropDownWidth);
            }
            if (Spinner.this.mIsRtl) {
                hOffset = hOffset2 + ((spinnerWidth - spinnerPaddingRight) - getWidth());
            } else {
                hOffset = hOffset2 + spinnerPaddingLeft;
            }
            setHorizontalOffset(hOffset);
        }

        public void show() {
            ViewTreeObserver vto;
            boolean wasShowing = isShowing();
            computeContentWidth();
            setInputMethodMode(2);
            super.show();
            if (!wasShowing && (vto = Spinner.this.getViewTreeObserver()) != null) {
                vto.addOnGlobalLayoutListener(this.layoutListener);
            }
        }
    }
}
