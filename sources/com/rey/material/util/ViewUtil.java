package com.rey.material.util;

import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import com.rey.material.C2500R;
import java.util.concurrent.atomic.AtomicInteger;

public class ViewUtil {
    public static final long FRAME_DURATION = 16;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId() {
        AtomicInteger atomicInteger;
        int result;
        int newValue;
        if (Build.VERSION.SDK_INT >= 17) {
            return View.generateViewId();
        }
        do {
            atomicInteger = sNextGeneratedId;
            result = atomicInteger.get();
            newValue = result + 1;
            if (newValue > 16777215) {
                newValue = 1;
            }
        } while (!atomicInteger.compareAndSet(result, newValue));
        return result;
    }

    public static boolean hasState(int[] states, int state) {
        if (states == null) {
            return false;
        }
        for (int state1 : states) {
            if (state1 == state) {
                return true;
            }
        }
        return false;
    }

    public static void setBackground(View v, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            v.setBackground(drawable);
        } else {
            v.setBackgroundDrawable(drawable);
        }
    }

    public static void applyStyle(View v, int resId) {
        applyStyle(v, (AttributeSet) null, 0, resId);
    }

    /* JADX WARNING: Removed duplicated region for block: B:236:0x0365  */
    /* JADX WARNING: Removed duplicated region for block: B:237:0x0370  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void applyStyle(android.view.View r22, android.util.AttributeSet r23, int r24, int r25) {
        /*
            r0 = r22
            r1 = r23
            r2 = r24
            r3 = r25
            android.content.Context r4 = r22.getContext()
            int[] r5 = com.rey.material.C2500R.styleable.View
            android.content.res.TypedArray r4 = r4.obtainStyledAttributes(r1, r5, r2, r3)
            r5 = -1
            r6 = -1
            r7 = -1
            r8 = -1
            r9 = -2147483648(0xffffffff80000000, float:-0.0)
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
            r11 = -1
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = r5
            int r5 = r4.getIndexCount()
            r21 = r16
            r16 = r7
            r7 = r21
        L_0x002d:
            if (r7 >= r5) goto L_0x02c5
            int r1 = r4.getIndex(r7)
            r19 = r5
            int r5 = com.rey.material.C2500R.styleable.View_android_background
            if (r1 != r5) goto L_0x0042
            android.graphics.drawable.Drawable r5 = r4.getDrawable(r1)
            setBackground(r0, r5)
            goto L_0x02bb
        L_0x0042:
            int r5 = com.rey.material.C2500R.styleable.View_android_backgroundTint
            r2 = 21
            if (r1 != r5) goto L_0x0055
            int r5 = android.os.Build.VERSION.SDK_INT
            if (r5 < r2) goto L_0x02bb
            android.content.res.ColorStateList r2 = r4.getColorStateList(r1)
            r0.setBackgroundTintList(r2)
            goto L_0x02bb
        L_0x0055:
            int r5 = com.rey.material.C2500R.styleable.View_android_backgroundTintMode
            if (r1 != r5) goto L_0x0097
            int r5 = android.os.Build.VERSION.SDK_INT
            r2 = 21
            if (r5 < r2) goto L_0x02bb
            r2 = 3
            int r5 = r4.getInt(r1, r2)
            if (r5 == r2) goto L_0x008f
            r2 = 5
            if (r5 == r2) goto L_0x0089
            r2 = 9
            if (r5 == r2) goto L_0x0083
            switch(r5) {
                case 14: goto L_0x007d;
                case 15: goto L_0x0077;
                case 16: goto L_0x0071;
                default: goto L_0x0070;
            }
        L_0x0070:
            goto L_0x0095
        L_0x0071:
            android.graphics.PorterDuff$Mode r2 = android.graphics.PorterDuff.Mode.ADD
            r0.setBackgroundTintMode(r2)
            goto L_0x0095
        L_0x0077:
            android.graphics.PorterDuff$Mode r2 = android.graphics.PorterDuff.Mode.SCREEN
            r0.setBackgroundTintMode(r2)
            goto L_0x0095
        L_0x007d:
            android.graphics.PorterDuff$Mode r2 = android.graphics.PorterDuff.Mode.MULTIPLY
            r0.setBackgroundTintMode(r2)
            goto L_0x0095
        L_0x0083:
            android.graphics.PorterDuff$Mode r2 = android.graphics.PorterDuff.Mode.SRC_ATOP
            r0.setBackgroundTintMode(r2)
            goto L_0x0095
        L_0x0089:
            android.graphics.PorterDuff$Mode r2 = android.graphics.PorterDuff.Mode.SRC_IN
            r0.setBackgroundTintMode(r2)
            goto L_0x0095
        L_0x008f:
            android.graphics.PorterDuff$Mode r2 = android.graphics.PorterDuff.Mode.SRC_OVER
            r0.setBackgroundTintMode(r2)
        L_0x0095:
            goto L_0x02bb
        L_0x0097:
            int r2 = com.rey.material.C2500R.styleable.View_android_elevation
            if (r1 != r2) goto L_0x00ac
            int r2 = android.os.Build.VERSION.SDK_INT
            r5 = 21
            if (r2 < r5) goto L_0x02bb
            r2 = 0
            int r2 = r4.getDimensionPixelOffset(r1, r2)
            float r2 = (float) r2
            r0.setElevation(r2)
            goto L_0x02bb
        L_0x00ac:
            int r2 = com.rey.material.C2500R.styleable.View_android_padding
            r5 = -1
            if (r1 != r2) goto L_0x00bc
            int r2 = r4.getDimensionPixelSize(r1, r5)
            r5 = 1
            r11 = 1
            r14 = r5
            r15 = r11
            r11 = r2
            goto L_0x02bb
        L_0x00bc:
            int r2 = com.rey.material.C2500R.styleable.View_android_paddingLeft
            if (r1 != r2) goto L_0x00ca
            int r2 = r4.getDimensionPixelSize(r1, r5)
            r5 = 1
            r17 = r2
            r14 = r5
            goto L_0x02bb
        L_0x00ca:
            int r2 = com.rey.material.C2500R.styleable.View_android_paddingTop
            if (r1 != r2) goto L_0x00d5
            int r2 = r4.getDimensionPixelSize(r1, r5)
            r6 = r2
            goto L_0x02bb
        L_0x00d5:
            int r2 = com.rey.material.C2500R.styleable.View_android_paddingRight
            if (r1 != r2) goto L_0x00e3
            int r2 = r4.getDimensionPixelSize(r1, r5)
            r5 = 1
            r16 = r2
            r15 = r5
            goto L_0x02bb
        L_0x00e3:
            int r2 = com.rey.material.C2500R.styleable.View_android_paddingBottom
            if (r1 != r2) goto L_0x00ee
            int r2 = r4.getDimensionPixelSize(r1, r5)
            r8 = r2
            goto L_0x02bb
        L_0x00ee:
            int r2 = com.rey.material.C2500R.styleable.View_android_paddingStart
            if (r1 != r2) goto L_0x010b
            int r2 = android.os.Build.VERSION.SDK_INT
            r5 = 17
            if (r2 < r5) goto L_0x02bb
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            int r5 = r4.getDimensionPixelSize(r1, r2)
            if (r5 == r2) goto L_0x0103
            r20 = 1
            goto L_0x0105
        L_0x0103:
            r20 = 0
        L_0x0105:
            r2 = r20
            r12 = r2
            r9 = r5
            goto L_0x02bb
        L_0x010b:
            int r2 = com.rey.material.C2500R.styleable.View_android_paddingEnd
            if (r1 != r2) goto L_0x0128
            int r2 = android.os.Build.VERSION.SDK_INT
            r5 = 17
            if (r2 < r5) goto L_0x02bb
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            int r5 = r4.getDimensionPixelSize(r1, r2)
            if (r5 == r2) goto L_0x0120
            r20 = 1
            goto L_0x0122
        L_0x0120:
            r20 = 0
        L_0x0122:
            r2 = r20
            r13 = r2
            r10 = r5
            goto L_0x02bb
        L_0x0128:
            int r2 = com.rey.material.C2500R.styleable.View_android_fadeScrollbars
            if (r1 != r2) goto L_0x0136
            r2 = 1
            boolean r2 = r4.getBoolean(r1, r2)
            r0.setScrollbarFadingEnabled(r2)
            goto L_0x02bb
        L_0x0136:
            int r2 = com.rey.material.C2500R.styleable.View_android_fadingEdgeLength
            if (r1 != r2) goto L_0x0144
            r2 = 0
            int r2 = r4.getDimensionPixelOffset(r1, r2)
            r0.setFadingEdgeLength(r2)
            goto L_0x02bb
        L_0x0144:
            r2 = 0
            int r5 = com.rey.material.C2500R.styleable.View_android_minHeight
            if (r1 != r5) goto L_0x0152
            int r2 = r4.getDimensionPixelSize(r1, r2)
            r0.setMinimumHeight(r2)
            goto L_0x02bb
        L_0x0152:
            int r5 = com.rey.material.C2500R.styleable.View_android_minWidth
            if (r1 != r5) goto L_0x015f
            int r2 = r4.getDimensionPixelSize(r1, r2)
            r0.setMinimumWidth(r2)
            goto L_0x02bb
        L_0x015f:
            int r2 = com.rey.material.C2500R.styleable.View_android_requiresFadingEdge
            if (r1 != r2) goto L_0x016d
            r2 = 1
            boolean r2 = r4.getBoolean(r1, r2)
            r0.setVerticalFadingEdgeEnabled(r2)
            goto L_0x02bb
        L_0x016d:
            int r2 = com.rey.material.C2500R.styleable.View_android_scrollbarDefaultDelayBeforeFade
            r5 = 16
            if (r1 != r2) goto L_0x0181
            int r2 = android.os.Build.VERSION.SDK_INT
            if (r2 < r5) goto L_0x02bb
            r2 = 0
            int r2 = r4.getInteger(r1, r2)
            r0.setScrollBarDefaultDelayBeforeFade(r2)
            goto L_0x02bb
        L_0x0181:
            int r2 = com.rey.material.C2500R.styleable.View_android_scrollbarFadeDuration
            if (r1 != r2) goto L_0x0193
            int r2 = android.os.Build.VERSION.SDK_INT
            if (r2 < r5) goto L_0x02bb
            r2 = 0
            int r2 = r4.getInteger(r1, r2)
            r0.setScrollBarFadeDuration(r2)
            goto L_0x02bb
        L_0x0193:
            int r2 = com.rey.material.C2500R.styleable.View_android_scrollbarSize
            if (r1 != r2) goto L_0x01a5
            int r2 = android.os.Build.VERSION.SDK_INT
            if (r2 < r5) goto L_0x02bb
            r2 = 0
            int r2 = r4.getDimensionPixelSize(r1, r2)
            r0.setScrollBarSize(r2)
            goto L_0x02bb
        L_0x01a5:
            r2 = 0
            int r5 = com.rey.material.C2500R.styleable.View_android_scrollbarStyle
            if (r1 != r5) goto L_0x01d0
            int r5 = r4.getInteger(r1, r2)
            if (r5 == 0) goto L_0x01c9
            r2 = 16777216(0x1000000, float:2.3509887E-38)
            if (r5 == r2) goto L_0x01c5
            r2 = 33554432(0x2000000, float:9.403955E-38)
            if (r5 == r2) goto L_0x01c1
            r2 = 50331648(0x3000000, float:3.761582E-37)
            if (r5 == r2) goto L_0x01bd
            goto L_0x01ce
        L_0x01bd:
            r0.setScrollBarStyle(r2)
            goto L_0x01ce
        L_0x01c1:
            r0.setScrollBarStyle(r2)
            goto L_0x01ce
        L_0x01c5:
            r0.setScrollBarStyle(r2)
            goto L_0x01ce
        L_0x01c9:
            r2 = 0
            r0.setScrollBarStyle(r2)
        L_0x01ce:
            goto L_0x02bb
        L_0x01d0:
            int r2 = com.rey.material.C2500R.styleable.View_android_soundEffectsEnabled
            if (r1 != r2) goto L_0x01de
            r2 = 1
            boolean r2 = r4.getBoolean(r1, r2)
            r0.setSoundEffectsEnabled(r2)
            goto L_0x02bb
        L_0x01de:
            int r2 = com.rey.material.C2500R.styleable.View_android_textAlignment
            if (r1 != r2) goto L_0x0216
            int r2 = android.os.Build.VERSION.SDK_INT
            r5 = 17
            if (r2 < r5) goto L_0x02bb
            r2 = 0
            int r5 = r4.getInteger(r1, r2)
            switch(r5) {
                case 0: goto L_0x020f;
                case 1: goto L_0x020a;
                case 2: goto L_0x0205;
                case 3: goto L_0x0200;
                case 4: goto L_0x01fb;
                case 5: goto L_0x01f6;
                case 6: goto L_0x01f1;
                default: goto L_0x01f0;
            }
        L_0x01f0:
            goto L_0x0214
        L_0x01f1:
            r2 = 6
            r0.setTextAlignment(r2)
            goto L_0x0214
        L_0x01f6:
            r2 = 5
            r0.setTextAlignment(r2)
            goto L_0x0214
        L_0x01fb:
            r2 = 4
            r0.setTextAlignment(r2)
            goto L_0x0214
        L_0x0200:
            r2 = 3
            r0.setTextAlignment(r2)
            goto L_0x0214
        L_0x0205:
            r2 = 2
            r0.setTextAlignment(r2)
            goto L_0x0214
        L_0x020a:
            r2 = 1
            r0.setTextAlignment(r2)
            goto L_0x0214
        L_0x020f:
            r2 = 0
            r0.setTextAlignment(r2)
        L_0x0214:
            goto L_0x02bb
        L_0x0216:
            int r2 = com.rey.material.C2500R.styleable.View_android_textDirection
            if (r1 != r2) goto L_0x0256
            int r2 = android.os.Build.VERSION.SDK_INT
            r5 = 17
            if (r2 < r5) goto L_0x02bb
            r2 = 0
            int r5 = r4.getInteger(r1, r2)
            if (r5 == 0) goto L_0x024f
            r2 = 1
            if (r5 == r2) goto L_0x024a
            r2 = 2
            if (r5 == r2) goto L_0x0245
            r2 = 3
            if (r5 == r2) goto L_0x0240
            r2 = 4
            if (r5 == r2) goto L_0x023b
            r2 = 5
            if (r5 == r2) goto L_0x0237
            goto L_0x0254
        L_0x0237:
            r0.setTextDirection(r2)
            goto L_0x0254
        L_0x023b:
            r2 = 4
            r0.setTextDirection(r2)
            goto L_0x0254
        L_0x0240:
            r2 = 3
            r0.setTextDirection(r2)
            goto L_0x0254
        L_0x0245:
            r2 = 2
            r0.setTextDirection(r2)
            goto L_0x0254
        L_0x024a:
            r2 = 1
            r0.setTextDirection(r2)
            goto L_0x0254
        L_0x024f:
            r2 = 0
            r0.setTextDirection(r2)
        L_0x0254:
            goto L_0x02bb
        L_0x0256:
            r2 = 0
            int r5 = com.rey.material.C2500R.styleable.View_android_visibility
            if (r1 != r5) goto L_0x0279
            int r5 = r4.getInteger(r1, r2)
            if (r5 == 0) goto L_0x0273
            r2 = 1
            if (r5 == r2) goto L_0x026e
            r2 = 2
            if (r5 == r2) goto L_0x0268
            goto L_0x0278
        L_0x0268:
            r2 = 8
            r0.setVisibility(r2)
            goto L_0x0278
        L_0x026e:
            r2 = 4
            r0.setVisibility(r2)
            goto L_0x0278
        L_0x0273:
            r2 = 0
            r0.setVisibility(r2)
        L_0x0278:
            goto L_0x02bb
        L_0x0279:
            int r2 = com.rey.material.C2500R.styleable.View_android_layoutDirection
            if (r1 != r2) goto L_0x02a8
            int r2 = android.os.Build.VERSION.SDK_INT
            r5 = 17
            if (r2 < r5) goto L_0x02bb
            r2 = 0
            int r5 = r4.getInteger(r1, r2)
            if (r5 == 0) goto L_0x02a2
            r2 = 1
            if (r5 == r2) goto L_0x029d
            r2 = 2
            if (r5 == r2) goto L_0x0298
            r2 = 3
            if (r5 == r2) goto L_0x0294
            goto L_0x02a7
        L_0x0294:
            r0.setLayoutDirection(r2)
            goto L_0x02a7
        L_0x0298:
            r2 = 2
            r0.setLayoutDirection(r2)
            goto L_0x02a7
        L_0x029d:
            r2 = 1
            r0.setLayoutDirection(r2)
            goto L_0x02a7
        L_0x02a2:
            r2 = 0
            r0.setLayoutDirection(r2)
        L_0x02a7:
            goto L_0x02bb
        L_0x02a8:
            int r2 = com.rey.material.C2500R.styleable.View_android_src
            if (r1 != r2) goto L_0x02bb
            boolean r2 = r0 instanceof android.widget.ImageView
            if (r2 == 0) goto L_0x02bb
            r2 = 0
            int r2 = r4.getResourceId(r1, r2)
            r5 = r0
            android.widget.ImageView r5 = (android.widget.ImageView) r5
            r5.setImageResource(r2)
        L_0x02bb:
            int r7 = r7 + 1
            r1 = r23
            r2 = r24
            r5 = r19
            goto L_0x002d
        L_0x02c5:
            r19 = r5
            if (r11 < 0) goto L_0x02ce
            r0.setPadding(r11, r11, r11, r11)
            goto L_0x035c
        L_0x02ce:
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 17
            if (r1 >= r2) goto L_0x030c
            if (r12 == 0) goto L_0x02d8
            r17 = r9
        L_0x02d8:
            if (r13 == 0) goto L_0x02dc
            r7 = r10
            goto L_0x02de
        L_0x02dc:
            r7 = r16
        L_0x02de:
            if (r17 < 0) goto L_0x02e3
            r1 = r17
            goto L_0x02e7
        L_0x02e3:
            int r1 = r22.getPaddingLeft()
        L_0x02e7:
            if (r6 < 0) goto L_0x02eb
            r2 = r6
            goto L_0x02ef
        L_0x02eb:
            int r2 = r22.getPaddingTop()
        L_0x02ef:
            if (r7 < 0) goto L_0x02f3
            r5 = r7
            goto L_0x02f7
        L_0x02f3:
            int r5 = r22.getPaddingRight()
        L_0x02f7:
            if (r8 < 0) goto L_0x02fd
            r18 = r7
            r7 = r8
            goto L_0x0305
        L_0x02fd:
            int r16 = r22.getPaddingBottom()
            r18 = r7
            r7 = r16
        L_0x0305:
            r0.setPadding(r1, r2, r5, r7)
            r7 = r18
            goto L_0x035e
        L_0x030c:
            if (r14 != 0) goto L_0x0310
            if (r15 == 0) goto L_0x0335
        L_0x0310:
            if (r14 == 0) goto L_0x0315
            r1 = r17
            goto L_0x0319
        L_0x0315:
            int r1 = r22.getPaddingLeft()
        L_0x0319:
            if (r6 < 0) goto L_0x031d
            r2 = r6
            goto L_0x0321
        L_0x031d:
            int r2 = r22.getPaddingTop()
        L_0x0321:
            if (r15 == 0) goto L_0x0326
            r5 = r16
            goto L_0x032a
        L_0x0326:
            int r5 = r22.getPaddingRight()
        L_0x032a:
            if (r8 < 0) goto L_0x032e
            r7 = r8
            goto L_0x0332
        L_0x032e:
            int r7 = r22.getPaddingBottom()
        L_0x0332:
            r0.setPadding(r1, r2, r5, r7)
        L_0x0335:
            if (r12 != 0) goto L_0x0339
            if (r13 == 0) goto L_0x035c
        L_0x0339:
            if (r12 == 0) goto L_0x033d
            r1 = r9
            goto L_0x0341
        L_0x033d:
            int r1 = r22.getPaddingStart()
        L_0x0341:
            if (r6 < 0) goto L_0x0345
            r2 = r6
            goto L_0x0349
        L_0x0345:
            int r2 = r22.getPaddingTop()
        L_0x0349:
            if (r13 == 0) goto L_0x034d
            r5 = r10
            goto L_0x0351
        L_0x034d:
            int r5 = r22.getPaddingEnd()
        L_0x0351:
            if (r8 < 0) goto L_0x0355
            r7 = r8
            goto L_0x0359
        L_0x0355:
            int r7 = r22.getPaddingBottom()
        L_0x0359:
            r0.setPaddingRelative(r1, r2, r5, r7)
        L_0x035c:
            r7 = r16
        L_0x035e:
            r4.recycle()
            boolean r1 = r0 instanceof android.widget.TextView
            if (r1 == 0) goto L_0x0370
            r1 = r0
            android.widget.TextView r1 = (android.widget.TextView) r1
            r2 = r23
            r5 = r24
            applyStyle((android.widget.TextView) r1, (android.util.AttributeSet) r2, (int) r5, (int) r3)
            goto L_0x0374
        L_0x0370:
            r2 = r23
            r5 = r24
        L_0x0374:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rey.material.util.ViewUtil.applyStyle(android.view.View, android.util.AttributeSet, int, int):void");
    }

    public static void applyFont(TextView v, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = v.getContext().obtainStyledAttributes(attrs, new int[]{C2500R.attr.tv_fontFamily}, defStyleAttr, defStyleRes);
        String fontFamily = a.getString(0);
        if (fontFamily != null) {
            v.setTypeface(TypefaceUtil.load(v.getContext(), fontFamily, 0));
        }
        a.recycle();
    }

    public static void applyTextAppearance(TextView v, int resId) {
        TextView textView = v;
        int i = resId;
        if (i != 0) {
            String fontFamily = null;
            int typefaceIndex = -1;
            int styleIndex = -1;
            int shadowColor = 0;
            float dx = 0.0f;
            float dy = 0.0f;
            float r = 0.0f;
            TypedArray appearance = v.getContext().obtainStyledAttributes(i, C2500R.styleable.TextAppearance);
            if (appearance != null) {
                int n = appearance.getIndexCount();
                for (int i2 = 0; i2 < n; i2++) {
                    int attr = appearance.getIndex(i2);
                    if (attr == C2500R.styleable.TextAppearance_android_textColorHighlight) {
                        textView.setHighlightColor(appearance.getColor(attr, 0));
                    } else if (attr == C2500R.styleable.TextAppearance_android_textColor) {
                        textView.setTextColor(appearance.getColorStateList(attr));
                    } else if (attr == C2500R.styleable.TextAppearance_android_textColorHint) {
                        textView.setHintTextColor(appearance.getColorStateList(attr));
                    } else if (attr == C2500R.styleable.TextAppearance_android_textColorLink) {
                        textView.setLinkTextColor(appearance.getColorStateList(attr));
                    } else if (attr == C2500R.styleable.TextAppearance_android_textSize) {
                        textView.setTextSize(0, (float) appearance.getDimensionPixelSize(attr, 0));
                    } else if (attr == C2500R.styleable.TextAppearance_android_typeface) {
                        typefaceIndex = appearance.getInt(attr, -1);
                    } else if (attr == C2500R.styleable.TextAppearance_android_fontFamily) {
                        fontFamily = appearance.getString(attr);
                    } else if (attr == C2500R.styleable.TextAppearance_tv_fontFamily) {
                        fontFamily = appearance.getString(attr);
                    } else if (attr == C2500R.styleable.TextAppearance_android_textStyle) {
                        styleIndex = appearance.getInt(attr, -1);
                    } else if (attr == C2500R.styleable.TextAppearance_android_textAllCaps) {
                        if (Build.VERSION.SDK_INT >= 14) {
                            textView.setAllCaps(appearance.getBoolean(attr, false));
                        }
                    } else if (attr == C2500R.styleable.TextAppearance_android_shadowColor) {
                        shadowColor = appearance.getInt(attr, 0);
                    } else if (attr == C2500R.styleable.TextAppearance_android_shadowDx) {
                        dx = appearance.getFloat(attr, 0.0f);
                    } else if (attr == C2500R.styleable.TextAppearance_android_shadowDy) {
                        dy = appearance.getFloat(attr, 0.0f);
                    } else if (attr == C2500R.styleable.TextAppearance_android_shadowRadius) {
                        r = appearance.getFloat(attr, 0.0f);
                    } else if (attr == C2500R.styleable.TextAppearance_android_elegantTextHeight) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            textView.setElegantTextHeight(appearance.getBoolean(attr, false));
                        }
                    } else if (attr == C2500R.styleable.TextAppearance_android_letterSpacing) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            textView.setLetterSpacing(appearance.getFloat(attr, 0.0f));
                        }
                    } else if (attr == C2500R.styleable.TextAppearance_android_fontFeatureSettings && Build.VERSION.SDK_INT >= 21) {
                        textView.setFontFeatureSettings(appearance.getString(attr));
                    }
                }
                appearance.recycle();
            }
            if (shadowColor != 0) {
                textView.setShadowLayer(r, dx, dy, shadowColor);
            }
            Typeface tf = null;
            if (!(fontFamily == null || (tf = TypefaceUtil.load(v.getContext(), fontFamily, styleIndex)) == null)) {
                textView.setTypeface(tf);
            }
            if (tf != null) {
                if (typefaceIndex == 1) {
                    tf = Typeface.SANS_SERIF;
                } else if (typefaceIndex == 2) {
                    tf = Typeface.SERIF;
                } else if (typefaceIndex == 3) {
                    tf = Typeface.MONOSPACE;
                }
                textView.setTypeface(tf, styleIndex);
            }
        }
    }

    private static void applyStyle(TextView v, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray appearance;
        int styleIndex;
        int typefaceIndex;
        char c;
        int typefaceIndex2;
        float dx;
        TextView textView = v;
        AttributeSet attributeSet = attrs;
        int i = defStyleAttr;
        int i2 = defStyleRes;
        int shadowColor = 0;
        float dx2 = 0.0f;
        float dy = 0.0f;
        float r = 0.0f;
        String fontFamily = null;
        int typefaceIndex3 = -1;
        TypedArray a = v.getContext().obtainStyledAttributes(attributeSet, C2500R.styleable.TextViewAppearance, i, i2);
        int styleIndex2 = -1;
        int ap = a.getResourceId(C2500R.styleable.TextViewAppearance_android_textAppearance, 0);
        a.recycle();
        if (ap != 0) {
            TypedArray typedArray = a;
            appearance = v.getContext().obtainStyledAttributes(ap, C2500R.styleable.TextAppearance);
        } else {
            appearance = null;
        }
        if (appearance != null) {
            int n = appearance.getIndexCount();
            int i3 = ap;
            int i4 = 0;
            while (i4 < n) {
                int n2 = n;
                int attr = appearance.getIndex(i4);
                int shadowColor2 = shadowColor;
                if (attr == C2500R.styleable.TextAppearance_android_textColorHighlight) {
                    dx = dx2;
                    textView.setHighlightColor(appearance.getColor(attr, 0));
                } else {
                    dx = dx2;
                    if (attr == C2500R.styleable.TextAppearance_android_textColor) {
                        textView.setTextColor(appearance.getColorStateList(attr));
                    } else if (attr == C2500R.styleable.TextAppearance_android_textColorHint) {
                        textView.setHintTextColor(appearance.getColorStateList(attr));
                    } else if (attr == C2500R.styleable.TextAppearance_android_textColorLink) {
                        textView.setLinkTextColor(appearance.getColorStateList(attr));
                    } else if (attr == C2500R.styleable.TextAppearance_android_textSize) {
                        textView.setTextSize(0, (float) appearance.getDimensionPixelSize(attr, 0));
                    } else {
                        if (attr == C2500R.styleable.TextAppearance_android_typeface) {
                            typefaceIndex3 = appearance.getInt(attr, -1);
                            shadowColor = shadowColor2;
                            dx2 = dx;
                        } else if (attr == C2500R.styleable.TextAppearance_android_fontFamily) {
                            fontFamily = appearance.getString(attr);
                            shadowColor = shadowColor2;
                            dx2 = dx;
                        } else if (attr == C2500R.styleable.TextAppearance_tv_fontFamily) {
                            fontFamily = appearance.getString(attr);
                            shadowColor = shadowColor2;
                            dx2 = dx;
                        } else if (attr == C2500R.styleable.TextAppearance_android_textStyle) {
                            styleIndex2 = appearance.getInt(attr, -1);
                            shadowColor = shadowColor2;
                            dx2 = dx;
                        } else if (attr == C2500R.styleable.TextAppearance_android_textAllCaps) {
                            if (Build.VERSION.SDK_INT >= 14) {
                                textView.setAllCaps(appearance.getBoolean(attr, false));
                            }
                        } else if (attr == C2500R.styleable.TextAppearance_android_shadowColor) {
                            shadowColor = appearance.getInt(attr, 0);
                            dx2 = dx;
                        } else if (attr == C2500R.styleable.TextAppearance_android_shadowDx) {
                            dx2 = appearance.getFloat(attr, 0.0f);
                            shadowColor = shadowColor2;
                        } else if (attr == C2500R.styleable.TextAppearance_android_shadowDy) {
                            dy = appearance.getFloat(attr, 0.0f);
                            shadowColor = shadowColor2;
                            dx2 = dx;
                        } else if (attr == C2500R.styleable.TextAppearance_android_shadowRadius) {
                            r = appearance.getFloat(attr, 0.0f);
                            shadowColor = shadowColor2;
                            dx2 = dx;
                        } else if (attr == C2500R.styleable.TextAppearance_android_elegantTextHeight) {
                            if (Build.VERSION.SDK_INT >= 21) {
                                textView.setElegantTextHeight(appearance.getBoolean(attr, false));
                            }
                        } else if (attr == C2500R.styleable.TextAppearance_android_letterSpacing) {
                            if (Build.VERSION.SDK_INT >= 21) {
                                textView.setLetterSpacing(appearance.getFloat(attr, 0.0f));
                            }
                        } else if (attr == C2500R.styleable.TextAppearance_android_fontFeatureSettings && Build.VERSION.SDK_INT >= 21) {
                            textView.setFontFeatureSettings(appearance.getString(attr));
                        }
                        i4++;
                        n = n2;
                    }
                }
                shadowColor = shadowColor2;
                dx2 = dx;
                i4++;
                n = n2;
            }
            int i5 = shadowColor;
            float f = dx2;
            appearance.recycle();
            typefaceIndex = typefaceIndex3;
            styleIndex = styleIndex2;
        } else {
            typefaceIndex = -1;
            styleIndex = -1;
        }
        TypedArray typedArray2 = appearance;
        int typefaceIndex4 = typefaceIndex;
        TypedArray a2 = v.getContext().obtainStyledAttributes(attributeSet, C2500R.styleable.TextView, i, i2);
        int n3 = a2.getIndexCount();
        String fontFamily2 = fontFamily;
        int i6 = 0;
        boolean drawableRelativeDefined = false;
        boolean drawableDefined = false;
        Drawable drawableEnd = null;
        Drawable drawableStart = null;
        Drawable drawableBottom = null;
        Drawable drawableRight = null;
        Drawable drawableTop = null;
        Drawable drawableLeft = null;
        float r2 = r;
        float dy2 = dy;
        float dx3 = dx2;
        int shadowColor3 = shadowColor;
        int styleIndex3 = styleIndex;
        int typefaceIndex5 = typefaceIndex4;
        while (i6 < n3) {
            int attr2 = a2.getIndex(i6);
            int n4 = n3;
            if (attr2 == C2500R.styleable.TextView_android_drawableLeft) {
                drawableDefined = true;
                drawableLeft = a2.getDrawable(attr2);
            } else if (attr2 == C2500R.styleable.TextView_android_drawableTop) {
                drawableDefined = true;
                drawableTop = a2.getDrawable(attr2);
            } else if (attr2 == C2500R.styleable.TextView_android_drawableRight) {
                drawableDefined = true;
                drawableRight = a2.getDrawable(attr2);
            } else if (attr2 == C2500R.styleable.TextView_android_drawableBottom) {
                drawableDefined = true;
                drawableBottom = a2.getDrawable(attr2);
            } else if (attr2 == C2500R.styleable.TextView_android_drawableStart) {
                drawableRelativeDefined = true;
                drawableStart = a2.getDrawable(attr2);
            } else if (attr2 == C2500R.styleable.TextView_android_drawableEnd) {
                drawableRelativeDefined = true;
                drawableEnd = a2.getDrawable(attr2);
            } else {
                if (attr2 == C2500R.styleable.TextView_android_drawablePadding) {
                    typefaceIndex2 = typefaceIndex5;
                    textView.setCompoundDrawablePadding(a2.getDimensionPixelSize(attr2, 0));
                } else {
                    typefaceIndex2 = typefaceIndex5;
                    if (attr2 == C2500R.styleable.TextView_android_maxLines) {
                        textView.setMaxLines(a2.getInt(attr2, -1));
                    } else if (attr2 == C2500R.styleable.TextView_android_maxHeight) {
                        textView.setMaxHeight(a2.getDimensionPixelSize(attr2, -1));
                    } else if (attr2 == C2500R.styleable.TextView_android_lines) {
                        textView.setLines(a2.getInt(attr2, -1));
                    } else if (attr2 == C2500R.styleable.TextView_android_height) {
                        textView.setHeight(a2.getDimensionPixelSize(attr2, -1));
                    } else if (attr2 == C2500R.styleable.TextView_android_minLines) {
                        textView.setMinLines(a2.getInt(attr2, -1));
                    } else if (attr2 == C2500R.styleable.TextView_android_minHeight) {
                        textView.setMinHeight(a2.getDimensionPixelSize(attr2, -1));
                    } else if (attr2 == C2500R.styleable.TextView_android_maxEms) {
                        textView.setMaxEms(a2.getInt(attr2, -1));
                    } else if (attr2 == C2500R.styleable.TextView_android_maxWidth) {
                        textView.setMaxWidth(a2.getDimensionPixelSize(attr2, -1));
                    } else if (attr2 == C2500R.styleable.TextView_android_ems) {
                        textView.setEms(a2.getInt(attr2, -1));
                    } else if (attr2 == C2500R.styleable.TextView_android_width) {
                        textView.setWidth(a2.getDimensionPixelSize(attr2, -1));
                    } else if (attr2 == C2500R.styleable.TextView_android_minEms) {
                        textView.setMinEms(a2.getInt(attr2, -1));
                    } else if (attr2 == C2500R.styleable.TextView_android_minWidth) {
                        textView.setMinWidth(a2.getDimensionPixelSize(attr2, -1));
                    } else if (attr2 == C2500R.styleable.TextView_android_gravity) {
                        textView.setGravity(a2.getInt(attr2, -1));
                    } else if (attr2 == C2500R.styleable.TextView_android_scrollHorizontally) {
                        textView.setHorizontallyScrolling(a2.getBoolean(attr2, false));
                    } else if (attr2 == C2500R.styleable.TextView_android_includeFontPadding) {
                        textView.setIncludeFontPadding(a2.getBoolean(attr2, true));
                    } else if (attr2 == C2500R.styleable.TextView_android_cursorVisible) {
                        textView.setCursorVisible(a2.getBoolean(attr2, true));
                    } else if (attr2 == C2500R.styleable.TextView_android_textScaleX) {
                        textView.setTextScaleX(a2.getFloat(attr2, 1.0f));
                    } else if (attr2 == C2500R.styleable.TextView_android_shadowColor) {
                        shadowColor3 = a2.getInt(attr2, 0);
                        typefaceIndex5 = typefaceIndex2;
                    } else if (attr2 == C2500R.styleable.TextView_android_shadowDx) {
                        dx3 = a2.getFloat(attr2, 0.0f);
                        typefaceIndex5 = typefaceIndex2;
                    } else if (attr2 == C2500R.styleable.TextView_android_shadowDy) {
                        dy2 = a2.getFloat(attr2, 0.0f);
                        typefaceIndex5 = typefaceIndex2;
                    } else if (attr2 == C2500R.styleable.TextView_android_shadowRadius) {
                        r2 = a2.getFloat(attr2, 0.0f);
                        typefaceIndex5 = typefaceIndex2;
                    } else if (attr2 == C2500R.styleable.TextView_android_textColorHighlight) {
                        textView.setHighlightColor(a2.getColor(attr2, 0));
                    } else if (attr2 == C2500R.styleable.TextView_android_textColor) {
                        textView.setTextColor(a2.getColorStateList(attr2));
                    } else if (attr2 == C2500R.styleable.TextView_android_textColorHint) {
                        textView.setHintTextColor(a2.getColorStateList(attr2));
                    } else if (attr2 == C2500R.styleable.TextView_android_textColorLink) {
                        textView.setLinkTextColor(a2.getColorStateList(attr2));
                    } else if (attr2 == C2500R.styleable.TextView_android_textSize) {
                        textView.setTextSize(0, (float) a2.getDimensionPixelSize(attr2, 0));
                    } else if (attr2 == C2500R.styleable.TextView_android_typeface) {
                        typefaceIndex5 = a2.getInt(attr2, -1);
                    } else if (attr2 == C2500R.styleable.TextView_android_textStyle) {
                        styleIndex3 = a2.getInt(attr2, -1);
                        typefaceIndex5 = typefaceIndex2;
                    } else if (attr2 == C2500R.styleable.TextView_android_fontFamily) {
                        fontFamily2 = a2.getString(attr2);
                        typefaceIndex5 = typefaceIndex2;
                    } else if (attr2 == C2500R.styleable.TextView_tv_fontFamily) {
                        fontFamily2 = a2.getString(attr2);
                        typefaceIndex5 = typefaceIndex2;
                    } else if (attr2 == C2500R.styleable.TextView_android_textAllCaps) {
                        if (Build.VERSION.SDK_INT >= 14) {
                            textView.setAllCaps(a2.getBoolean(attr2, false));
                        }
                    } else if (attr2 == C2500R.styleable.TextView_android_elegantTextHeight) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            textView.setElegantTextHeight(a2.getBoolean(attr2, false));
                        }
                    } else if (attr2 == C2500R.styleable.TextView_android_letterSpacing) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            textView.setLetterSpacing(a2.getFloat(attr2, 0.0f));
                        }
                    } else if (attr2 == C2500R.styleable.TextView_android_fontFeatureSettings) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            textView.setFontFeatureSettings(a2.getString(attr2));
                        }
                    }
                }
                typefaceIndex5 = typefaceIndex2;
            }
            i6++;
            int i7 = defStyleRes;
            n3 = n4;
        }
        int typefaceIndex6 = typefaceIndex5;
        a2.recycle();
        if (shadowColor3 != 0) {
            textView.setShadowLayer(r2, dx3, dy2, shadowColor3);
        }
        if (drawableDefined) {
            Drawable[] drawables = v.getCompoundDrawables();
            if (drawableStart != null) {
                drawables[0] = drawableStart;
            } else if (drawableLeft != null) {
                drawables[0] = drawableLeft;
            }
            if (drawableTop != null) {
                drawables[1] = drawableTop;
            }
            if (drawableEnd != null) {
                drawables[2] = drawableEnd;
            } else if (drawableRight != null) {
                drawables[2] = drawableRight;
            }
            if (drawableBottom != null) {
                drawables[3] = drawableBottom;
            }
            TypedArray typedArray3 = a2;
            textView.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3]);
        }
        if (!drawableRelativeDefined || Build.VERSION.SDK_INT < 17) {
        } else {
            Drawable[] drawables2 = v.getCompoundDrawablesRelative();
            if (drawableStart != null) {
                drawables2[0] = drawableStart;
            }
            if (drawableEnd != null) {
                c = 2;
                drawables2[2] = drawableEnd;
            } else {
                c = 2;
            }
            int i8 = shadowColor3;
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawables2[0], drawables2[1], drawables2[c], drawables2[3]);
        }
        Typeface tf = null;
        if (!(fontFamily2 == null || (tf = TypefaceUtil.load(v.getContext(), fontFamily2, styleIndex3)) == null)) {
            textView.setTypeface(tf);
        }
        if (tf != null) {
            int typefaceIndex7 = typefaceIndex6;
            if (typefaceIndex7 == 1) {
                tf = Typeface.SANS_SERIF;
            } else if (typefaceIndex7 == 2) {
                tf = Typeface.SERIF;
            } else if (typefaceIndex7 == 3) {
                tf = Typeface.MONOSPACE;
            }
            textView.setTypeface(tf, styleIndex3);
        }
        if (textView instanceof AutoCompleteTextView) {
            String str = fontFamily2;
            applyStyle((AutoCompleteTextView) textView, attrs, defStyleAttr, defStyleRes);
            return;
        }
        int i9 = defStyleAttr;
        int i10 = defStyleRes;
        String str2 = fontFamily2;
        AttributeSet attributeSet2 = attrs;
    }

    private static void applyStyle(AutoCompleteTextView v, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = v.getContext().obtainStyledAttributes(attrs, C2500R.styleable.AutoCompleteTextView, defStyleAttr, defStyleRes);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == C2500R.styleable.AutoCompleteTextView_android_completionHint) {
                v.setCompletionHint(a.getString(attr));
            } else if (attr == C2500R.styleable.AutoCompleteTextView_android_completionThreshold) {
                v.setThreshold(a.getInteger(attr, 0));
            } else if (attr == C2500R.styleable.AutoCompleteTextView_android_dropDownAnchor) {
                v.setDropDownAnchor(a.getResourceId(attr, 0));
            } else if (attr == C2500R.styleable.AutoCompleteTextView_android_dropDownHeight) {
                v.setDropDownHeight(a.getLayoutDimension(attr, -2));
            } else if (attr == C2500R.styleable.AutoCompleteTextView_android_dropDownWidth) {
                v.setDropDownWidth(a.getLayoutDimension(attr, -2));
            } else if (attr == C2500R.styleable.AutoCompleteTextView_android_dropDownHorizontalOffset) {
                v.setDropDownHorizontalOffset(a.getDimensionPixelSize(attr, 0));
            } else if (attr == C2500R.styleable.AutoCompleteTextView_android_dropDownVerticalOffset) {
                v.setDropDownVerticalOffset(a.getDimensionPixelSize(attr, 0));
            } else if (attr == C2500R.styleable.AutoCompleteTextView_android_popupBackground) {
                v.setDropDownBackgroundDrawable(a.getDrawable(attr));
            }
        }
        a.recycle();
    }
}
