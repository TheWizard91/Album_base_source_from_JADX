package com.rey.material.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import androidx.core.text.TextUtilsCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.widget.ListViewAutoScrollHelper;
import androidx.core.widget.PopupWindowCompat;
import com.rey.material.C2500R;
import java.lang.reflect.Method;

public class ListPopupWindow {
    private static final boolean DEBUG = false;
    private static final int EXPAND_LIST_TIMEOUT = 250;
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    public static final int MATCH_PARENT = -1;
    public static final int POSITION_PROMPT_ABOVE = 0;
    public static final int POSITION_PROMPT_BELOW = 1;
    private static final String TAG = "ListPopupWindow";
    public static final int WRAP_CONTENT = -2;
    private static Method sClipToWindowEnabledMethod;
    private ListAdapter mAdapter;
    /* access modifiers changed from: private */
    public Context mContext;
    private boolean mDropDownAlwaysVisible;
    private View mDropDownAnchorView;
    private int mDropDownGravity;
    private int mDropDownHeight;
    private int mDropDownHorizontalOffset;
    /* access modifiers changed from: private */
    public DropDownListView mDropDownList;
    private Drawable mDropDownListHighlight;
    private int mDropDownVerticalOffset;
    private boolean mDropDownVerticalOffsetSet;
    private int mDropDownWidth;
    private boolean mForceIgnoreOutsideTouch;
    /* access modifiers changed from: private */
    public Handler mHandler;
    private final ListSelectorHider mHideSelector;
    /* access modifiers changed from: private */
    public int mItemAnimationId;
    /* access modifiers changed from: private */
    public int mItemAnimationOffset;
    private AdapterView.OnItemClickListener mItemClickListener;
    private AdapterView.OnItemSelectedListener mItemSelectedListener;
    private int mLayoutDirection;
    int mListItemExpandMaximum;
    private boolean mModal;
    private DataSetObserver mObserver;
    /* access modifiers changed from: private */
    public PopupWindow mPopup;
    private int mPromptPosition;
    private View mPromptView;
    /* access modifiers changed from: private */
    public final ResizePopupRunnable mResizePopupRunnable;
    private final PopupScrollListener mScrollListener;
    private Runnable mShowDropDownRunnable;
    private Rect mTempRect;
    private final PopupTouchInterceptor mTouchInterceptor;

    static {
        Class<PopupWindow> cls = PopupWindow.class;
        try {
            sClipToWindowEnabledMethod = cls.getDeclaredMethod("setClipToScreenEnabled", new Class[]{Boolean.TYPE});
        } catch (NoSuchMethodException e) {
            Log.i(TAG, "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
        }
    }

    public ListPopupWindow(Context context) {
        this(context, (AttributeSet) null, C2500R.attr.listPopupWindowStyle, 0);
    }

    public ListPopupWindow(Context context, AttributeSet attrs) {
        this(context, attrs, C2500R.attr.listPopupWindowStyle, 0);
    }

    public ListPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ListPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mDropDownHeight = -2;
        this.mDropDownWidth = -2;
        this.mDropDownGravity = 0;
        this.mDropDownAlwaysVisible = false;
        this.mForceIgnoreOutsideTouch = false;
        this.mListItemExpandMaximum = Integer.MAX_VALUE;
        this.mPromptPosition = 0;
        this.mResizePopupRunnable = new ResizePopupRunnable();
        this.mTouchInterceptor = new PopupTouchInterceptor();
        this.mScrollListener = new PopupScrollListener();
        this.mHideSelector = new ListSelectorHider();
        this.mHandler = new Handler();
        this.mTempRect = new Rect();
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, C2500R.styleable.ListPopupWindow, defStyleAttr, defStyleRes);
        this.mDropDownHorizontalOffset = a.getDimensionPixelOffset(C2500R.styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        int dimensionPixelOffset = a.getDimensionPixelOffset(C2500R.styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
        this.mDropDownVerticalOffset = dimensionPixelOffset;
        if (dimensionPixelOffset != 0) {
            this.mDropDownVerticalOffsetSet = true;
        }
        a.recycle();
        PopupWindow popupWindow = new PopupWindow(context, attrs, defStyleAttr);
        this.mPopup = popupWindow;
        popupWindow.setInputMethodMode(1);
        this.mLayoutDirection = TextUtilsCompat.getLayoutDirectionFromLocale(this.mContext.getResources().getConfiguration().locale);
    }

    public void setItemAnimation(int id) {
        this.mItemAnimationId = id;
    }

    public void setItemAnimationOffset(int offset) {
        this.mItemAnimationOffset = offset;
    }

    public void setBackgroundDrawable(Drawable background) {
        this.mPopup.setBackgroundDrawable(background);
    }

    public Drawable getBackground() {
        return this.mPopup.getBackground();
    }

    public void setAdapter(ListAdapter adapter) {
        DataSetObserver dataSetObserver = this.mObserver;
        if (dataSetObserver == null) {
            this.mObserver = new PopupDataSetObserver();
        } else {
            ListAdapter listAdapter = this.mAdapter;
            if (listAdapter != null) {
                listAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.mAdapter = adapter;
        if (adapter != null) {
            adapter.registerDataSetObserver(this.mObserver);
        }
        DropDownListView dropDownListView = this.mDropDownList;
        if (dropDownListView != null) {
            dropDownListView.setAdapter(this.mAdapter);
        }
    }

    public void setPromptPosition(int position) {
        this.mPromptPosition = position;
    }

    public int getPromptPosition() {
        return this.mPromptPosition;
    }

    public void setModal(boolean modal) {
        this.mModal = modal;
        this.mPopup.setFocusable(modal);
    }

    public boolean isModal() {
        return this.mModal;
    }

    public void setForceIgnoreOutsideTouch(boolean forceIgnoreOutsideTouch) {
        this.mForceIgnoreOutsideTouch = forceIgnoreOutsideTouch;
    }

    public void setDropDownAlwaysVisible(boolean dropDownAlwaysVisible) {
        this.mDropDownAlwaysVisible = dropDownAlwaysVisible;
    }

    public boolean isDropDownAlwaysVisible() {
        return this.mDropDownAlwaysVisible;
    }

    public void setSoftInputMode(int mode) {
        this.mPopup.setSoftInputMode(mode);
    }

    public int getSoftInputMode() {
        return this.mPopup.getSoftInputMode();
    }

    public void setListSelector(Drawable selector) {
        this.mDropDownListHighlight = selector;
    }

    public void setAnimationStyle(int animationStyle) {
        this.mPopup.setAnimationStyle(animationStyle);
    }

    public int getAnimationStyle() {
        return this.mPopup.getAnimationStyle();
    }

    public View getAnchorView() {
        return this.mDropDownAnchorView;
    }

    public void setAnchorView(View anchor) {
        this.mDropDownAnchorView = anchor;
    }

    public int getHorizontalOffset() {
        return this.mDropDownHorizontalOffset;
    }

    public void setHorizontalOffset(int offset) {
        this.mDropDownHorizontalOffset = offset;
    }

    public int getVerticalOffset() {
        if (!this.mDropDownVerticalOffsetSet) {
            return 0;
        }
        return this.mDropDownVerticalOffset;
    }

    public void setVerticalOffset(int offset) {
        this.mDropDownVerticalOffset = offset;
        this.mDropDownVerticalOffsetSet = true;
    }

    public void setDropDownGravity(int gravity) {
        this.mDropDownGravity = gravity;
    }

    public int getWidth() {
        return this.mDropDownWidth;
    }

    public void setWidth(int width) {
        this.mDropDownWidth = width;
    }

    public void setContentWidth(int width) {
        Drawable popupBackground = this.mPopup.getBackground();
        if (popupBackground != null) {
            popupBackground.getPadding(this.mTempRect);
            this.mDropDownWidth = this.mTempRect.left + this.mTempRect.right + width;
            return;
        }
        setWidth(width);
    }

    public int getHeight() {
        return this.mDropDownHeight;
    }

    public void setHeight(int height) {
        this.mDropDownHeight = height;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener clickListener) {
        this.mItemClickListener = clickListener;
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener selectedListener) {
        this.mItemSelectedListener = selectedListener;
    }

    public void setPromptView(View prompt) {
        boolean showing = isShowing();
        if (showing) {
            removePromptView();
        }
        this.mPromptView = prompt;
        if (showing) {
            show();
        }
    }

    public void postShow() {
        this.mHandler.post(this.mShowDropDownRunnable);
    }

    public void show() {
        int widthSpec;
        int heightSpec;
        int height = buildDropDown();
        int widthSpec2 = 0;
        int heightSpec2 = 0;
        boolean noInputMethod = isInputMethodNotNeeded();
        boolean z = true;
        int i = -1;
        if (this.mPopup.isShowing()) {
            int i2 = this.mDropDownWidth;
            if (i2 == -1) {
                widthSpec = -1;
            } else if (i2 == -2) {
                widthSpec = getAnchorView().getWidth();
            } else {
                widthSpec = this.mDropDownWidth;
            }
            int i3 = this.mDropDownHeight;
            if (i3 == -1) {
                heightSpec = noInputMethod ? height : -1;
                if (noInputMethod) {
                    PopupWindow popupWindow = this.mPopup;
                    if (this.mDropDownWidth != -1) {
                        i = 0;
                    }
                    popupWindow.setWindowLayoutMode(i, 0);
                } else {
                    this.mPopup.setWindowLayoutMode(this.mDropDownWidth == -1 ? -1 : 0, -1);
                }
            } else {
                heightSpec = i3 == -2 ? height : this.mDropDownHeight;
            }
            PopupWindow popupWindow2 = this.mPopup;
            if (this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) {
                z = false;
            }
            popupWindow2.setOutsideTouchable(z);
            this.mPopup.update(getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, widthSpec, heightSpec);
            return;
        }
        int i4 = this.mDropDownWidth;
        if (i4 == -1) {
            widthSpec2 = -1;
        } else if (i4 == -2) {
            this.mPopup.setWidth(getAnchorView().getWidth());
        } else {
            this.mPopup.setWidth(i4);
        }
        int i5 = this.mDropDownHeight;
        if (i5 == -1) {
            heightSpec2 = -1;
        } else if (i5 == -2) {
            this.mPopup.setHeight(height);
        } else {
            this.mPopup.setHeight(i5);
        }
        this.mPopup.setWindowLayoutMode(widthSpec2, heightSpec2);
        setPopupClipToScreenEnabled(true);
        PopupWindow popupWindow3 = this.mPopup;
        if (this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) {
            z = false;
        }
        popupWindow3.setOutsideTouchable(z);
        this.mPopup.setTouchInterceptor(this.mTouchInterceptor);
        PopupWindowCompat.showAsDropDown(this.mPopup, getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, this.mDropDownGravity);
        this.mDropDownList.setSelection(-1);
        if (!this.mModal || this.mDropDownList.isInTouchMode()) {
            clearListSelection();
        }
        if (!this.mModal) {
            this.mHandler.post(this.mHideSelector);
        }
        if (this.mItemAnimationId != 0) {
            this.mPopup.getContentView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    ListPopupWindow.this.mPopup.getContentView().getViewTreeObserver().removeOnPreDrawListener(this);
                    int count = ListPopupWindow.this.mDropDownList.getChildCount();
                    for (int i = 0; i < count; i++) {
                        View v = ListPopupWindow.this.mDropDownList.getChildAt(i);
                        Animation anim = AnimationUtils.loadAnimation(ListPopupWindow.this.mContext, ListPopupWindow.this.mItemAnimationId);
                        anim.setStartOffset((long) (ListPopupWindow.this.mItemAnimationOffset * i));
                        v.startAnimation(anim);
                    }
                    return false;
                }
            });
        }
    }

    public void dismiss() {
        this.mPopup.dismiss();
        removePromptView();
        this.mPopup.setContentView((View) null);
        this.mDropDownList = null;
        this.mHandler.removeCallbacks(this.mResizePopupRunnable);
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        this.mPopup.setOnDismissListener(listener);
    }

    private void removePromptView() {
        View view = this.mPromptView;
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.mPromptView);
            }
        }
    }

    public void setInputMethodMode(int mode) {
        this.mPopup.setInputMethodMode(mode);
    }

    public int getInputMethodMode() {
        return this.mPopup.getInputMethodMode();
    }

    public void setSelection(int position) {
        DropDownListView list = this.mDropDownList;
        if (isShowing() && list != null) {
            boolean unused = list.mListSelectionHidden = false;
            list.setSelection(position);
            if (Build.VERSION.SDK_INT >= 11 && list.getChoiceMode() != 0) {
                list.setItemChecked(position, true);
            }
        }
    }

    public void clearListSelection() {
        DropDownListView list = this.mDropDownList;
        if (list != null) {
            boolean unused = list.mListSelectionHidden = true;
            list.requestLayout();
        }
    }

    public boolean isShowing() {
        return this.mPopup.isShowing();
    }

    public boolean isInputMethodNotNeeded() {
        return this.mPopup.getInputMethodMode() == 2;
    }

    public boolean performItemClick(int position) {
        if (!isShowing()) {
            return false;
        }
        if (this.mItemClickListener == null) {
            return true;
        }
        DropDownListView list = this.mDropDownList;
        View child = list.getChildAt(position - list.getFirstVisiblePosition());
        DropDownListView dropDownListView = list;
        View view = child;
        int i = position;
        this.mItemClickListener.onItemClick(dropDownListView, view, i, list.getAdapter().getItemId(position));
        return true;
    }

    public Object getSelectedItem() {
        if (!isShowing()) {
            return null;
        }
        return this.mDropDownList.getSelectedItem();
    }

    public int getSelectedItemPosition() {
        if (!isShowing()) {
            return -1;
        }
        return this.mDropDownList.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        if (!isShowing()) {
            return Long.MIN_VALUE;
        }
        return this.mDropDownList.getSelectedItemId();
    }

    public View getSelectedView() {
        if (!isShowing()) {
            return null;
        }
        return this.mDropDownList.getSelectedView();
    }

    public ListView getListView() {
        return this.mDropDownList;
    }

    public PopupWindow getPopup() {
        return this.mPopup;
    }

    /* access modifiers changed from: package-private */
    public void setListItemExpandMax(int max) {
        this.mListItemExpandMaximum = max;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int i;
        int i2;
        if (isShowing() && keyCode != 62 && (this.mDropDownList.getSelectedItemPosition() >= 0 || !isConfirmKey(keyCode))) {
            int curIndex = this.mDropDownList.getSelectedItemPosition();
            boolean below = !this.mPopup.isAboveAnchor();
            ListAdapter adapter = this.mAdapter;
            int firstItem = Integer.MAX_VALUE;
            int lastItem = Integer.MIN_VALUE;
            if (adapter != null) {
                boolean allEnabled = adapter.areAllItemsEnabled();
                if (allEnabled) {
                    i = 0;
                } else {
                    i = this.mDropDownList.lookForSelectablePosition(0, true);
                }
                firstItem = i;
                if (allEnabled) {
                    i2 = adapter.getCount() - 1;
                } else {
                    i2 = this.mDropDownList.lookForSelectablePosition(adapter.getCount() - 1, false);
                }
                lastItem = i2;
            }
            if ((!below || keyCode != 19 || curIndex > firstItem) && (below || keyCode != 20 || curIndex < lastItem)) {
                boolean unused = this.mDropDownList.mListSelectionHidden = false;
                if (this.mDropDownList.onKeyDown(keyCode, event)) {
                    this.mPopup.setInputMethodMode(2);
                    this.mDropDownList.requestFocusFromTouch();
                    show();
                    if (keyCode == 19 || keyCode == 20 || keyCode == 23 || keyCode == 66) {
                        return true;
                    }
                } else if (!below || keyCode != 20) {
                    if (!below && keyCode == 19 && curIndex == firstItem) {
                        return true;
                    }
                    return false;
                } else if (curIndex == lastItem) {
                    return true;
                }
            } else {
                clearListSelection();
                this.mPopup.setInputMethodMode(1);
                show();
                return true;
            }
        }
        return false;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (!isShowing() || this.mDropDownList.getSelectedItemPosition() < 0) {
            return false;
        }
        boolean consumed = this.mDropDownList.onKeyUp(keyCode, event);
        if (consumed && isConfirmKey(keyCode)) {
            dismiss();
        }
        return consumed;
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode != 4 || !isShowing()) {
            return false;
        }
        View anchorView = this.mDropDownAnchorView;
        if (event.getAction() == 0 && event.getRepeatCount() == 0) {
            KeyEvent.DispatcherState state = anchorView.getKeyDispatcherState();
            if (state != null) {
                state.startTracking(event, this);
            }
            return true;
        } else if (event.getAction() != 1) {
            return false;
        } else {
            KeyEvent.DispatcherState state2 = anchorView.getKeyDispatcherState();
            if (state2 != null) {
                state2.handleUpEvent(event);
            }
            if (!event.isTracking() || event.isCanceled()) {
                return false;
            }
            dismiss();
            return true;
        }
    }

    public View.OnTouchListener createDragToOpenListener(View src) {
        return new ForwardingListener(src) {
            public ListPopupWindow getPopup() {
                return ListPopupWindow.this;
            }
        };
    }

    private int getSystemBarHeight(String resourceName) {
        int resourceId = this.mContext.getResources().getIdentifier(resourceName, "dimen", SystemMediaRouteProvider.PACKAGE_NAME);
        if (resourceId > 0) {
            return this.mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v13, resolved type: com.rey.material.widget.ListPopupWindow$DropDownListView} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v14, resolved type: com.rey.material.widget.ListPopupWindow$DropDownListView} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v22, resolved type: android.widget.LinearLayout} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v15, resolved type: com.rey.material.widget.ListPopupWindow$DropDownListView} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int buildDropDown() {
        /*
            r14 = this;
            r0 = 0
            com.rey.material.widget.ListPopupWindow$DropDownListView r1 = r14.mDropDownList
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = 0
            r4 = -1
            r5 = 1
            if (r1 != 0) goto L_0x00bd
            android.content.Context r1 = r14.mContext
            com.rey.material.widget.ListPopupWindow$3 r6 = new com.rey.material.widget.ListPopupWindow$3
            r6.<init>()
            r14.mShowDropDownRunnable = r6
            com.rey.material.widget.ListPopupWindow$DropDownListView r6 = new com.rey.material.widget.ListPopupWindow$DropDownListView
            boolean r7 = r14.mModal
            r7 = r7 ^ r5
            r6.<init>(r1, r7)
            r14.mDropDownList = r6
            android.graphics.drawable.Drawable r7 = r14.mDropDownListHighlight
            if (r7 == 0) goto L_0x0024
            r6.setSelector(r7)
        L_0x0024:
            com.rey.material.widget.ListPopupWindow$DropDownListView r6 = r14.mDropDownList
            android.widget.ListAdapter r7 = r14.mAdapter
            r6.setAdapter(r7)
            com.rey.material.widget.ListPopupWindow$DropDownListView r6 = r14.mDropDownList
            android.widget.AdapterView$OnItemClickListener r7 = r14.mItemClickListener
            r6.setOnItemClickListener(r7)
            com.rey.material.widget.ListPopupWindow$DropDownListView r6 = r14.mDropDownList
            r6.setFocusable(r5)
            com.rey.material.widget.ListPopupWindow$DropDownListView r6 = r14.mDropDownList
            r6.setFocusableInTouchMode(r5)
            com.rey.material.widget.ListPopupWindow$DropDownListView r6 = r14.mDropDownList
            com.rey.material.widget.ListPopupWindow$4 r7 = new com.rey.material.widget.ListPopupWindow$4
            r7.<init>()
            r6.setOnItemSelectedListener(r7)
            com.rey.material.widget.ListPopupWindow$DropDownListView r6 = r14.mDropDownList
            com.rey.material.widget.ListPopupWindow$PopupScrollListener r7 = r14.mScrollListener
            r6.setOnScrollListener(r7)
            android.widget.AdapterView$OnItemSelectedListener r6 = r14.mItemSelectedListener
            if (r6 == 0) goto L_0x0056
            com.rey.material.widget.ListPopupWindow$DropDownListView r7 = r14.mDropDownList
            r7.setOnItemSelectedListener(r6)
        L_0x0056:
            com.rey.material.widget.ListPopupWindow$DropDownListView r6 = r14.mDropDownList
            android.view.View r7 = r14.mPromptView
            if (r7 == 0) goto L_0x00b7
            android.widget.LinearLayout r8 = new android.widget.LinearLayout
            r8.<init>(r1)
            r8.setOrientation(r5)
            android.widget.LinearLayout$LayoutParams r9 = new android.widget.LinearLayout$LayoutParams
            r10 = 1065353216(0x3f800000, float:1.0)
            r9.<init>(r4, r3, r10)
            int r10 = r14.mPromptPosition
            if (r10 == 0) goto L_0x0093
            if (r10 == r5) goto L_0x008c
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Invalid hint position "
            java.lang.StringBuilder r10 = r10.append(r11)
            int r11 = r14.mPromptPosition
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            java.lang.String r11 = "ListPopupWindow"
            android.util.Log.e(r11, r10)
            goto L_0x009a
        L_0x008c:
            r8.addView(r6, r9)
            r8.addView(r7)
            goto L_0x009a
        L_0x0093:
            r8.addView(r7)
            r8.addView(r6, r9)
        L_0x009a:
            int r10 = r14.mDropDownWidth
            int r10 = android.view.View.MeasureSpec.makeMeasureSpec(r10, r2)
            r11 = 0
            r7.measure(r10, r11)
            android.view.ViewGroup$LayoutParams r12 = r7.getLayoutParams()
            r9 = r12
            android.widget.LinearLayout$LayoutParams r9 = (android.widget.LinearLayout.LayoutParams) r9
            int r12 = r7.getMeasuredHeight()
            int r13 = r9.topMargin
            int r12 = r12 + r13
            int r13 = r9.bottomMargin
            int r0 = r12 + r13
            r6 = r8
        L_0x00b7:
            com.rey.material.widget.PopupWindow r8 = r14.mPopup
            r8.setContentView(r6)
            goto L_0x00d3
        L_0x00bd:
            android.view.View r1 = r14.mPromptView
            if (r1 == 0) goto L_0x00d3
            android.view.ViewGroup$LayoutParams r6 = r1.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r6 = (android.widget.LinearLayout.LayoutParams) r6
            int r7 = r1.getMeasuredHeight()
            int r8 = r6.topMargin
            int r7 = r7 + r8
            int r8 = r6.bottomMargin
            int r0 = r7 + r8
        L_0x00d3:
            r1 = 0
            com.rey.material.widget.PopupWindow r6 = r14.mPopup
            android.graphics.drawable.Drawable r6 = r6.getBackground()
            if (r6 == 0) goto L_0x00f7
            android.graphics.Rect r7 = r14.mTempRect
            r6.getPadding(r7)
            android.graphics.Rect r7 = r14.mTempRect
            int r7 = r7.top
            android.graphics.Rect r8 = r14.mTempRect
            int r8 = r8.bottom
            int r1 = r7 + r8
            boolean r7 = r14.mDropDownVerticalOffsetSet
            if (r7 != 0) goto L_0x00fc
            android.graphics.Rect r7 = r14.mTempRect
            int r7 = r7.top
            int r7 = -r7
            r14.mDropDownVerticalOffset = r7
            goto L_0x00fc
        L_0x00f7:
            android.graphics.Rect r7 = r14.mTempRect
            r7.setEmpty()
        L_0x00fc:
            r7 = 0
            int r8 = android.os.Build.VERSION.SDK_INT
            r9 = 21
            if (r8 < r9) goto L_0x0114
            java.lang.String r8 = "status_bar_height"
            int r8 = r14.getSystemBarHeight(r8)
            java.lang.String r9 = "navigation_bar_height"
            int r9 = r14.getSystemBarHeight(r9)
            int r7 = java.lang.Math.max(r8, r9)
        L_0x0114:
            com.rey.material.widget.PopupWindow r8 = r14.mPopup
            int r8 = r8.getInputMethodMode()
            r9 = 2
            if (r8 != r9) goto L_0x011e
            r3 = r5
        L_0x011e:
            com.rey.material.widget.PopupWindow r5 = r14.mPopup
            android.view.View r8 = r14.getAnchorView()
            int r9 = r14.mDropDownVerticalOffset
            int r5 = r5.getMaxAvailableHeight(r8, r9)
            int r5 = r5 - r7
            boolean r8 = r14.mDropDownAlwaysVisible
            if (r8 != 0) goto L_0x018a
            int r8 = r14.mDropDownHeight
            if (r8 != r4) goto L_0x0134
            goto L_0x018a
        L_0x0134:
            int r8 = r14.mDropDownWidth
            r9 = -2
            if (r8 == r9) goto L_0x015d
            r2 = 1073741824(0x40000000, float:2.0)
            if (r8 == r4) goto L_0x0142
            int r2 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r2)
            goto L_0x0178
        L_0x0142:
            android.content.Context r4 = r14.mContext
            android.content.res.Resources r4 = r4.getResources()
            android.util.DisplayMetrics r4 = r4.getDisplayMetrics()
            int r4 = r4.widthPixels
            android.graphics.Rect r8 = r14.mTempRect
            int r8 = r8.left
            android.graphics.Rect r9 = r14.mTempRect
            int r9 = r9.right
            int r8 = r8 + r9
            int r4 = r4 - r8
            int r2 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r2)
            goto L_0x0178
        L_0x015d:
            android.content.Context r4 = r14.mContext
            android.content.res.Resources r4 = r4.getResources()
            android.util.DisplayMetrics r4 = r4.getDisplayMetrics()
            int r4 = r4.widthPixels
            android.graphics.Rect r8 = r14.mTempRect
            int r8 = r8.left
            android.graphics.Rect r9 = r14.mTempRect
            int r9 = r9.right
            int r8 = r8 + r9
            int r4 = r4 - r8
            int r2 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r2)
        L_0x0178:
            com.rey.material.widget.ListPopupWindow$DropDownListView r8 = r14.mDropDownList
            r10 = 0
            r11 = -1
            int r12 = r5 - r0
            r13 = -1
            r9 = r2
            int r4 = r8.measureHeightOfChildrenCompat(r9, r10, r11, r12, r13)
            if (r4 <= 0) goto L_0x0187
            int r0 = r0 + r1
        L_0x0187:
            int r8 = r4 + r0
            return r8
        L_0x018a:
            int r2 = r5 + r1
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rey.material.widget.ListPopupWindow.buildDropDown():int");
    }

    public static abstract class ForwardingListener implements View.OnTouchListener {
        private int mActivePointerId;
        private Runnable mDisallowIntercept;
        private boolean mForwarding;
        private final int mLongPressTimeout;
        private final float mScaledTouchSlop;
        /* access modifiers changed from: private */
        public final View mSrc;
        private final int mTapTimeout;
        private final int[] mTmpLocation = new int[2];
        private Runnable mTriggerLongPress;
        private boolean mWasLongPress;

        public abstract ListPopupWindow getPopup();

        public ForwardingListener(View src) {
            this.mSrc = src;
            this.mScaledTouchSlop = (float) ViewConfiguration.get(src.getContext()).getScaledTouchSlop();
            int tapTimeout = ViewConfiguration.getTapTimeout();
            this.mTapTimeout = tapTimeout;
            this.mLongPressTimeout = (tapTimeout + ViewConfiguration.getLongPressTimeout()) / 2;
        }

        public boolean onTouch(View v, MotionEvent event) {
            boolean forwarding;
            MotionEvent motionEvent = event;
            boolean wasForwarding = this.mForwarding;
            if (wasForwarding) {
                forwarding = this.mWasLongPress ? onTouchForwarded(motionEvent) : onTouchForwarded(motionEvent) || !onForwardingStopped();
            } else {
                forwarding = onTouchObserved(motionEvent) && onForwardingStarted();
                if (forwarding) {
                    long now = SystemClock.uptimeMillis();
                    MotionEvent e = MotionEvent.obtain(now, now, 3, 0.0f, 0.0f, 0);
                    this.mSrc.onTouchEvent(e);
                    e.recycle();
                }
            }
            this.mForwarding = forwarding;
            if (forwarding || wasForwarding) {
                return true;
            }
            return false;
        }

        /* access modifiers changed from: protected */
        public boolean onForwardingStarted() {
            ListPopupWindow popup = getPopup();
            if (popup == null || popup.isShowing()) {
                return true;
            }
            popup.show();
            return true;
        }

        /* access modifiers changed from: protected */
        public boolean onForwardingStopped() {
            ListPopupWindow popup = getPopup();
            if (popup == null || !popup.isShowing()) {
                return true;
            }
            popup.dismiss();
            return true;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0017, code lost:
            if (r1 != 3) goto L_0x0072;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean onTouchObserved(android.view.MotionEvent r9) {
            /*
                r8 = this;
                android.view.View r0 = r8.mSrc
                boolean r1 = r0.isEnabled()
                r2 = 0
                if (r1 != 0) goto L_0x000a
                return r2
            L_0x000a:
                int r1 = androidx.core.view.MotionEventCompat.getActionMasked(r9)
                if (r1 == 0) goto L_0x0042
                r3 = 1
                if (r1 == r3) goto L_0x003e
                r4 = 2
                if (r1 == r4) goto L_0x001a
                r3 = 3
                if (r1 == r3) goto L_0x003e
                goto L_0x0072
            L_0x001a:
                int r4 = r8.mActivePointerId
                int r4 = r9.findPointerIndex(r4)
                if (r4 < 0) goto L_0x0072
                float r5 = r9.getX(r4)
                float r6 = r9.getY(r4)
                float r7 = r8.mScaledTouchSlop
                boolean r7 = pointInView(r0, r5, r6, r7)
                if (r7 != 0) goto L_0x003d
                r8.clearCallbacks()
                android.view.ViewParent r2 = r0.getParent()
                r2.requestDisallowInterceptTouchEvent(r3)
                return r3
            L_0x003d:
                goto L_0x0072
            L_0x003e:
                r8.clearCallbacks()
                goto L_0x0072
            L_0x0042:
                int r3 = r9.getPointerId(r2)
                r8.mActivePointerId = r3
                r8.mWasLongPress = r2
                java.lang.Runnable r3 = r8.mDisallowIntercept
                r4 = 0
                if (r3 != 0) goto L_0x0056
                com.rey.material.widget.ListPopupWindow$ForwardingListener$DisallowIntercept r3 = new com.rey.material.widget.ListPopupWindow$ForwardingListener$DisallowIntercept
                r3.<init>()
                r8.mDisallowIntercept = r3
            L_0x0056:
                java.lang.Runnable r3 = r8.mDisallowIntercept
                int r5 = r8.mTapTimeout
                long r5 = (long) r5
                r0.postDelayed(r3, r5)
                java.lang.Runnable r3 = r8.mTriggerLongPress
                if (r3 != 0) goto L_0x0069
                com.rey.material.widget.ListPopupWindow$ForwardingListener$TriggerLongPress r3 = new com.rey.material.widget.ListPopupWindow$ForwardingListener$TriggerLongPress
                r3.<init>()
                r8.mTriggerLongPress = r3
            L_0x0069:
                java.lang.Runnable r3 = r8.mTriggerLongPress
                int r4 = r8.mLongPressTimeout
                long r4 = (long) r4
                r0.postDelayed(r3, r4)
            L_0x0072:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.rey.material.widget.ListPopupWindow.ForwardingListener.onTouchObserved(android.view.MotionEvent):boolean");
        }

        private void clearCallbacks() {
            Runnable runnable = this.mTriggerLongPress;
            if (runnable != null) {
                this.mSrc.removeCallbacks(runnable);
            }
            Runnable runnable2 = this.mDisallowIntercept;
            if (runnable2 != null) {
                this.mSrc.removeCallbacks(runnable2);
            }
        }

        /* access modifiers changed from: private */
        public void onLongPress() {
            clearCallbacks();
            if (this.mSrc.isEnabled() && onForwardingStarted()) {
                this.mSrc.getParent().requestDisallowInterceptTouchEvent(true);
                long now = SystemClock.uptimeMillis();
                MotionEvent e = MotionEvent.obtain(now, now, 3, 0.0f, 0.0f, 0);
                this.mSrc.onTouchEvent(e);
                e.recycle();
                this.mForwarding = true;
                this.mWasLongPress = true;
            }
        }

        private boolean onTouchForwarded(MotionEvent srcEvent) {
            DropDownListView dst;
            View src = this.mSrc;
            ListPopupWindow popup = getPopup();
            if (popup == null || !popup.isShowing() || (dst = popup.mDropDownList) == null || !dst.isShown()) {
                return false;
            }
            MotionEvent dstEvent = MotionEvent.obtainNoHistory(srcEvent);
            toGlobalMotionEvent(src, dstEvent);
            toLocalMotionEvent(dst, dstEvent);
            boolean handled = dst.onForwardedEvent(dstEvent, this.mActivePointerId);
            dstEvent.recycle();
            int action = MotionEventCompat.getActionMasked(srcEvent);
            boolean keepForwarding = (action == 1 || action == 3) ? false : true;
            if (!handled || !keepForwarding) {
                return false;
            }
            return true;
        }

        private static boolean pointInView(View view, float localX, float localY, float slop) {
            return localX >= (-slop) && localY >= (-slop) && localX < ((float) (view.getRight() - view.getLeft())) + slop && localY < ((float) (view.getBottom() - view.getTop())) + slop;
        }

        private boolean toLocalMotionEvent(View view, MotionEvent event) {
            int[] loc = this.mTmpLocation;
            view.getLocationOnScreen(loc);
            event.offsetLocation((float) (-loc[0]), (float) (-loc[1]));
            return true;
        }

        private boolean toGlobalMotionEvent(View view, MotionEvent event) {
            int[] loc = this.mTmpLocation;
            view.getLocationOnScreen(loc);
            event.offsetLocation((float) loc[0], (float) loc[1]);
            return true;
        }

        private class DisallowIntercept implements Runnable {
            private DisallowIntercept() {
            }

            public void run() {
                ForwardingListener.this.mSrc.getParent().requestDisallowInterceptTouchEvent(true);
            }
        }

        private class TriggerLongPress implements Runnable {
            private TriggerLongPress() {
            }

            public void run() {
                ForwardingListener.this.onLongPress();
            }
        }
    }

    private static class DropDownListView extends ListView {
        private ViewPropertyAnimatorCompat mClickAnimation;
        private boolean mDrawsInPressedState;
        private boolean mHijackFocus;
        /* access modifiers changed from: private */
        public boolean mListSelectionHidden;
        private ListViewAutoScrollHelper mScrollHelper;

        public DropDownListView(Context context, boolean hijackFocus) {
            super(context, (AttributeSet) null, C2500R.attr.dropDownListViewStyle);
            this.mHijackFocus = hijackFocus;
            setCacheColorHint(0);
        }

        /* JADX WARNING: Removed duplicated region for block: B:21:0x004c  */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x0062  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onForwardedEvent(android.view.MotionEvent r12, int r13) {
            /*
                r11 = this;
                r0 = 1
                r1 = 0
                int r2 = androidx.core.view.MotionEventCompat.getActionMasked(r12)
                r3 = 1
                if (r2 == r3) goto L_0x0012
                r4 = 2
                if (r2 == r4) goto L_0x0013
                r4 = 3
                if (r2 == r4) goto L_0x0010
                goto L_0x0043
            L_0x0010:
                r0 = 0
                goto L_0x0043
            L_0x0012:
                r0 = 0
            L_0x0013:
                int r4 = r12.findPointerIndex(r13)
                if (r4 >= 0) goto L_0x001b
                r0 = 0
                goto L_0x0043
            L_0x001b:
                float r5 = r12.getX(r4)
                int r5 = (int) r5
                float r6 = r12.getY(r4)
                int r6 = (int) r6
                int r7 = r11.pointToPosition(r5, r6)
                r8 = -1
                if (r7 != r8) goto L_0x002e
                r1 = 1
                goto L_0x0043
            L_0x002e:
                int r8 = r11.getFirstVisiblePosition()
                int r8 = r7 - r8
                android.view.View r8 = r11.getChildAt(r8)
                float r9 = (float) r5
                float r10 = (float) r6
                r11.setPressedItem(r8, r7, r9, r10)
                r0 = 1
                if (r2 != r3) goto L_0x0043
                r11.clickPressedItem(r8, r7)
            L_0x0043:
                if (r0 == 0) goto L_0x0047
                if (r1 == 0) goto L_0x004a
            L_0x0047:
                r11.clearPressedItem()
            L_0x004a:
                if (r0 == 0) goto L_0x0062
                androidx.core.widget.ListViewAutoScrollHelper r4 = r11.mScrollHelper
                if (r4 != 0) goto L_0x0057
                androidx.core.widget.ListViewAutoScrollHelper r4 = new androidx.core.widget.ListViewAutoScrollHelper
                r4.<init>(r11)
                r11.mScrollHelper = r4
            L_0x0057:
                androidx.core.widget.ListViewAutoScrollHelper r4 = r11.mScrollHelper
                r4.setEnabled(r3)
                androidx.core.widget.ListViewAutoScrollHelper r3 = r11.mScrollHelper
                r3.onTouch(r11, r12)
                goto L_0x006a
            L_0x0062:
                androidx.core.widget.ListViewAutoScrollHelper r3 = r11.mScrollHelper
                if (r3 == 0) goto L_0x006a
                r4 = 0
                r3.setEnabled(r4)
            L_0x006a:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.rey.material.widget.ListPopupWindow.DropDownListView.onForwardedEvent(android.view.MotionEvent, int):boolean");
        }

        private void clickPressedItem(View child, int position) {
            performItemClick(child, position, getItemIdAtPosition(position));
        }

        private void clearPressedItem() {
            this.mDrawsInPressedState = false;
            setPressed(false);
            drawableStateChanged();
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mClickAnimation;
            if (viewPropertyAnimatorCompat != null) {
                viewPropertyAnimatorCompat.cancel();
                this.mClickAnimation = null;
            }
        }

        private void setPressedItem(View child, int position, float x, float y) {
            this.mDrawsInPressedState = true;
            setPressed(true);
            layoutChildren();
            setSelection(position);
            positionSelectorLikeTouchCompat(position, child, x, y);
            setSelectorEnabled(false);
            refreshDrawableState();
        }

        /* access modifiers changed from: protected */
        public boolean touchModeDrawsInPressedStateCompat() {
            return this.mDrawsInPressedState || super.touchModeDrawsInPressedStateCompat();
        }

        public boolean isInTouchMode() {
            return (this.mHijackFocus && this.mListSelectionHidden) || super.isInTouchMode();
        }

        public boolean hasWindowFocus() {
            return this.mHijackFocus || super.hasWindowFocus();
        }

        public boolean isFocused() {
            return this.mHijackFocus || super.isFocused();
        }

        public boolean hasFocus() {
            return this.mHijackFocus || super.hasFocus();
        }
    }

    private class PopupDataSetObserver extends DataSetObserver {
        private PopupDataSetObserver() {
        }

        public void onChanged() {
            if (ListPopupWindow.this.isShowing()) {
                ListPopupWindow.this.show();
            }
        }

        public void onInvalidated() {
            ListPopupWindow.this.dismiss();
        }
    }

    private class ListSelectorHider implements Runnable {
        private ListSelectorHider() {
        }

        public void run() {
            ListPopupWindow.this.clearListSelection();
        }
    }

    private class ResizePopupRunnable implements Runnable {
        private ResizePopupRunnable() {
        }

        public void run() {
            if (ListPopupWindow.this.mDropDownList != null && ListPopupWindow.this.mDropDownList.getCount() > ListPopupWindow.this.mDropDownList.getChildCount() && ListPopupWindow.this.mDropDownList.getChildCount() <= ListPopupWindow.this.mListItemExpandMaximum) {
                ListPopupWindow.this.mPopup.setInputMethodMode(2);
                ListPopupWindow.this.show();
            }
        }
    }

    private class PopupTouchInterceptor implements View.OnTouchListener {
        private PopupTouchInterceptor() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (action == 0 && ListPopupWindow.this.mPopup != null && ListPopupWindow.this.mPopup.isShowing() && x >= 0 && x < ListPopupWindow.this.mPopup.getWidth() && y >= 0 && y < ListPopupWindow.this.mPopup.getHeight()) {
                ListPopupWindow.this.mHandler.postDelayed(ListPopupWindow.this.mResizePopupRunnable, 250);
                return false;
            } else if (action != 1) {
                return false;
            } else {
                ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable);
                return false;
            }
        }
    }

    private class PopupScrollListener implements AbsListView.OnScrollListener {
        private PopupScrollListener() {
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 1 && !ListPopupWindow.this.isInputMethodNotNeeded() && ListPopupWindow.this.mPopup.getContentView() != null) {
                ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable);
                ListPopupWindow.this.mResizePopupRunnable.run();
            }
        }
    }

    private static boolean isConfirmKey(int keyCode) {
        return keyCode == 66 || keyCode == 23;
    }

    private void setPopupClipToScreenEnabled(boolean clip) {
        Method method = sClipToWindowEnabledMethod;
        if (method != null) {
            try {
                method.invoke(this.mPopup, new Object[]{Boolean.valueOf(clip)});
            } catch (Exception e) {
                Log.i(TAG, "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
        } else if (clip && Build.VERSION.SDK_INT >= 3) {
            this.mPopup.setClippingEnabled(false);
        }
    }
}
