package com.rey.material.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import androidx.core.view.ViewCompat;
import com.google.common.primitives.Ints;
import com.rey.material.C2500R;
import com.rey.material.app.Dialog;
import com.rey.material.drawable.BlankDrawable;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.ListView;
import com.rey.material.widget.RadioButton;
import com.rey.material.widget.TextView;

public class SimpleDialog extends Dialog {
    protected static final int MODE_CUSTOM = 4;
    protected static final int MODE_ITEMS = 2;
    protected static final int MODE_MESSAGE = 1;
    protected static final int MODE_MULTI_ITEMS = 3;
    protected static final int MODE_NONE = 0;
    private InternalAdapter mAdapter;
    /* access modifiers changed from: private */
    public int mCheckBoxStyle;
    /* access modifiers changed from: private */
    public int mItemHeight;
    /* access modifiers changed from: private */
    public int mItemTextAppearance;
    /* access modifiers changed from: private */
    public InternalListView mListView;
    /* access modifiers changed from: private */
    public TextView mMessage;
    private int mMessageTextAppearanceId;
    private int mMessageTextColor;
    /* access modifiers changed from: private */
    public int mMode;
    /* access modifiers changed from: private */
    public OnSelectionChangedListener mOnSelectionChangedListener;
    /* access modifiers changed from: private */
    public int mRadioButtonStyle;
    private InternalScrollView mScrollView;

    public interface OnSelectionChangedListener {
        void onSelectionChanged(int i, boolean z);
    }

    public SimpleDialog(Context context) {
        super(context, C2500R.C2505style.Material_App_Dialog_Simple_Light);
    }

    public SimpleDialog(Context context, int style) {
        super(context, style);
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
        messageTextAppearance(C2500R.C2505style.TextAppearance_AppCompat_Body1);
        itemHeight(-2);
        itemTextAppearance(C2500R.C2505style.TextAppearance_AppCompat_Body1);
    }

    public Dialog applyStyle(int resId) {
        super.applyStyle(resId);
        if (resId == 0) {
            return this;
        }
        TypedArray a = getContext().obtainStyledAttributes(resId, C2500R.styleable.SimpleDialog);
        int textAppearance = 0;
        int textColor = 0;
        boolean textColorDefined = false;
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == C2500R.styleable.SimpleDialog_di_messageTextAppearance) {
                textAppearance = a.getResourceId(attr, 0);
            } else if (attr == C2500R.styleable.SimpleDialog_di_messageTextColor) {
                textColor = a.getColor(attr, 0);
                textColorDefined = true;
            } else if (attr == C2500R.styleable.SimpleDialog_di_radioButtonStyle) {
                radioButtonStyle(a.getResourceId(attr, 0));
            } else if (attr == C2500R.styleable.SimpleDialog_di_checkBoxStyle) {
                checkBoxStyle(a.getResourceId(attr, 0));
            } else if (attr == C2500R.styleable.SimpleDialog_di_itemHeight) {
                itemHeight(a.getDimensionPixelSize(attr, 0));
            } else if (attr == C2500R.styleable.SimpleDialog_di_itemTextAppearance) {
                itemTextAppearance(a.getResourceId(attr, 0));
            }
        }
        a.recycle();
        if (textAppearance != 0) {
            messageTextAppearance(textAppearance);
        }
        if (textColorDefined) {
            messageTextColor(textColor);
        }
        return this;
    }

    public Dialog clearContent() {
        super.clearContent();
        this.mMode = 0;
        return this;
    }

    public Dialog title(CharSequence title) {
        contentMargin(this.mContentPadding, TextUtils.isEmpty(title) ^ true ? 0 : this.mContentPadding, this.mContentPadding, 0);
        return super.title(title);
    }

    public Dialog contentView(View v) {
        if (this.mScrollView == null) {
            initScrollView();
        }
        if (!(this.mScrollView.getChildAt(0) == v || v == null)) {
            this.mScrollView.removeAllViews();
            this.mScrollView.addView(v);
            this.mMode = 4;
            super.contentView((View) this.mScrollView);
        }
        return this;
    }

    private void initScrollView() {
        InternalScrollView internalScrollView = new InternalScrollView(getContext());
        this.mScrollView = internalScrollView;
        internalScrollView.setPadding(0, 0, 0, this.mContentPadding - this.mActionPadding);
        this.mScrollView.setClipToPadding(false);
        this.mScrollView.setFillViewport(true);
        this.mScrollView.setScrollBarStyle(33554432);
        ViewCompat.setLayoutDirection(this.mScrollView, 2);
    }

    private void initMessageView() {
        TextView textView = new TextView(getContext());
        this.mMessage = textView;
        textView.setTextAppearance(getContext(), this.mMessageTextAppearanceId);
        this.mMessage.setTextColor(this.mMessageTextColor);
        this.mMessage.setGravity(8388627);
    }

    public SimpleDialog message(CharSequence message) {
        if (this.mScrollView == null) {
            initScrollView();
        }
        if (this.mMessage == null) {
            initMessageView();
        }
        if (this.mScrollView.getChildAt(0) != this.mMessage) {
            this.mScrollView.removeAllViews();
            this.mScrollView.addView(this.mMessage);
        }
        this.mMessage.setText(message);
        if (!TextUtils.isEmpty(message)) {
            this.mMode = 1;
            super.contentView((View) this.mScrollView);
        }
        return this;
    }

    public SimpleDialog message(int id) {
        return message((CharSequence) id == 0 ? null : getContext().getResources().getString(id));
    }

    public SimpleDialog messageTextAppearance(int resId) {
        if (this.mMessageTextAppearanceId != resId) {
            this.mMessageTextAppearanceId = resId;
            TextView textView = this.mMessage;
            if (textView != null) {
                textView.setTextAppearance(getContext(), this.mMessageTextAppearanceId);
            }
        }
        return this;
    }

    public SimpleDialog messageTextColor(int color) {
        if (this.mMessageTextColor != color) {
            this.mMessageTextColor = color;
            TextView textView = this.mMessage;
            if (textView != null) {
                textView.setTextColor(color);
            }
        }
        return this;
    }

    public SimpleDialog radioButtonStyle(int resId) {
        if (this.mRadioButtonStyle != resId) {
            this.mRadioButtonStyle = resId;
            InternalAdapter internalAdapter = this.mAdapter;
            if (internalAdapter != null && this.mMode == 2) {
                internalAdapter.notifyDataSetChanged();
            }
        }
        return this;
    }

    public SimpleDialog checkBoxStyle(int resId) {
        if (this.mCheckBoxStyle != resId) {
            this.mCheckBoxStyle = resId;
            InternalAdapter internalAdapter = this.mAdapter;
            if (internalAdapter != null && this.mMode == 3) {
                internalAdapter.notifyDataSetChanged();
            }
        }
        return this;
    }

    public SimpleDialog itemHeight(int height) {
        if (this.mItemHeight != height) {
            this.mItemHeight = height;
            InternalAdapter internalAdapter = this.mAdapter;
            if (internalAdapter != null) {
                internalAdapter.notifyDataSetChanged();
            }
        }
        return this;
    }

    public SimpleDialog itemTextAppearance(int resId) {
        if (this.mItemTextAppearance != resId) {
            this.mItemTextAppearance = resId;
            InternalAdapter internalAdapter = this.mAdapter;
            if (internalAdapter != null) {
                internalAdapter.notifyDataSetChanged();
            }
        }
        return this;
    }

    private void initListView() {
        InternalListView internalListView = new InternalListView(getContext());
        this.mListView = internalListView;
        internalListView.setDividerHeight(0);
        this.mListView.setCacheColorHint(0);
        this.mListView.setScrollBarStyle(33554432);
        this.mListView.setClipToPadding(false);
        this.mListView.setSelector(BlankDrawable.getInstance());
        this.mListView.setPadding(0, 0, 0, this.mContentPadding - this.mActionPadding);
        this.mListView.setVerticalFadingEdgeEnabled(false);
        this.mListView.setOverScrollMode(2);
        ViewCompat.setLayoutDirection(this.mListView, 2);
        InternalAdapter internalAdapter = new InternalAdapter();
        this.mAdapter = internalAdapter;
        this.mListView.setAdapter(internalAdapter);
    }

    public SimpleDialog items(CharSequence[] items, int selectedIndex) {
        if (this.mListView == null) {
            initListView();
        }
        this.mMode = 2;
        this.mAdapter.setItems(items, selectedIndex);
        super.contentView((View) this.mListView);
        return this;
    }

    public SimpleDialog multiChoiceItems(CharSequence[] items, int... selectedIndexes) {
        if (this.mListView == null) {
            initListView();
        }
        this.mMode = 3;
        this.mAdapter.setItems(items, selectedIndexes);
        super.contentView((View) this.mListView);
        return this;
    }

    public SimpleDialog onSelectionChangedListener(OnSelectionChangedListener listener) {
        this.mOnSelectionChangedListener = listener;
        return this;
    }

    public int[] getSelectedIndexes() {
        InternalAdapter internalAdapter = this.mAdapter;
        if (internalAdapter == null) {
            return null;
        }
        return internalAdapter.getSelectedIndexes();
    }

    public CharSequence[] getSelectedValues() {
        return this.mAdapter.getSelectedValues();
    }

    public int getSelectedIndex() {
        InternalAdapter internalAdapter = this.mAdapter;
        if (internalAdapter == null) {
            return -1;
        }
        return internalAdapter.getLastSelectedIndex();
    }

    public CharSequence getSelectedValue() {
        return this.mAdapter.getLastSelectedValue();
    }

    private class InternalScrollView extends ScrollView {
        private boolean mIsRtl = false;

        public InternalScrollView(Context context) {
            super(context);
        }

        public void onRtlPropertiesChanged(int layoutDirection) {
            View v;
            boolean rtl = true;
            if (layoutDirection != 1) {
                rtl = false;
            }
            if (this.mIsRtl != rtl) {
                this.mIsRtl = rtl;
                if (Build.VERSION.SDK_INT >= 17 && (v = getChildAt(0)) != null && v == SimpleDialog.this.mMessage) {
                    SimpleDialog.this.mMessage.setTextDirection(this.mIsRtl ? 4 : 3);
                }
                requestLayout();
            }
        }

        public boolean isLayoutRtl() {
            return this.mIsRtl;
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            boolean z = false;
            View child = getChildAt(0);
            SimpleDialog simpleDialog = SimpleDialog.this;
            if (child != null && child.getMeasuredHeight() > (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) {
                z = true;
            }
            simpleDialog.showDivider(z);
        }
    }

    private class InternalListView extends ListView {
        private boolean mIsRtl = false;

        public InternalListView(Context context) {
            super(context);
        }

        public void onRtlPropertiesChanged(int layoutDirection) {
            boolean rtl = true;
            if (layoutDirection != 1) {
                rtl = false;
            }
            if (this.mIsRtl != rtl) {
                this.mIsRtl = rtl;
                requestLayout();
            }
        }

        public boolean isLayoutRtl() {
            return this.mIsRtl;
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if (View.MeasureSpec.getMode(heightMeasureSpec) == 0 && SimpleDialog.this.mItemHeight != -2) {
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((SimpleDialog.this.mItemHeight * getAdapter().getCount()) + getPaddingTop() + getPaddingBottom(), Ints.MAX_POWER_OF_TWO);
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            int totalHeight = 0;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                totalHeight += getChildAt(i).getMeasuredHeight();
            }
            SimpleDialog.this.showDivider(totalHeight > getMeasuredHeight() || (totalHeight == getMeasuredHeight() && getAdapter().getCount() > childCount));
        }
    }

    private class InternalAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {
        private CharSequence[] mItems;
        private int mLastSelectedIndex;
        private boolean[] mSelected;

        private InternalAdapter() {
        }

        public void setItems(CharSequence[] items, int... selectedIndexes) {
            int i;
            this.mItems = items;
            boolean[] zArr = this.mSelected;
            if (zArr == null || zArr.length != items.length) {
                this.mSelected = new boolean[items.length];
            }
            int i2 = 0;
            while (true) {
                boolean[] zArr2 = this.mSelected;
                if (i2 >= zArr2.length) {
                    break;
                }
                zArr2[i2] = false;
                i2++;
            }
            if (selectedIndexes != null) {
                for (int index : selectedIndexes) {
                    if (index >= 0) {
                        boolean[] zArr3 = this.mSelected;
                        if (index < zArr3.length) {
                            zArr3[index] = true;
                            this.mLastSelectedIndex = index;
                        }
                    }
                }
            }
            notifyDataSetChanged();
        }

        public int getLastSelectedIndex() {
            return this.mLastSelectedIndex;
        }

        public CharSequence getLastSelectedValue() {
            return this.mItems[this.mLastSelectedIndex];
        }

        public int[] getSelectedIndexes() {
            int count = 0;
            int i = 0;
            while (true) {
                boolean[] zArr = this.mSelected;
                if (i >= zArr.length) {
                    break;
                }
                if (zArr[i]) {
                    count++;
                }
                i++;
            }
            if (count == 0) {
                return null;
            }
            int[] result = new int[count];
            int count2 = 0;
            int i2 = 0;
            while (true) {
                boolean[] zArr2 = this.mSelected;
                if (i2 >= zArr2.length) {
                    return result;
                }
                if (zArr2[i2]) {
                    result[count2] = i2;
                    count2++;
                }
                i2++;
            }
        }

        public CharSequence[] getSelectedValues() {
            int count = 0;
            int i = 0;
            while (true) {
                boolean[] zArr = this.mSelected;
                if (i >= zArr.length) {
                    break;
                }
                if (zArr[i]) {
                    count++;
                }
                i++;
            }
            if (count == 0) {
                return null;
            }
            CharSequence[] result = new CharSequence[count];
            int count2 = 0;
            int i2 = 0;
            while (true) {
                boolean[] zArr2 = this.mSelected;
                if (i2 >= zArr2.length) {
                    return result;
                }
                if (zArr2[i2]) {
                    result[count2] = this.mItems[i2];
                    count2++;
                }
                i2++;
            }
        }

        public int getCount() {
            CharSequence[] charSequenceArr = this.mItems;
            if (charSequenceArr == null) {
                return 0;
            }
            return charSequenceArr.length;
        }

        public Object getItem(int position) {
            CharSequence[] charSequenceArr = this.mItems;
            if (charSequenceArr == null) {
                return 0;
            }
            return charSequenceArr[position];
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            com.rey.material.widget.CompoundButton v = (com.rey.material.widget.CompoundButton) convertView;
            if (v == null) {
                int i = 3;
                if (SimpleDialog.this.mMode == 3) {
                    v = new CheckBox(parent.getContext());
                    v.applyStyle(SimpleDialog.this.mCheckBoxStyle);
                } else {
                    v = new RadioButton(parent.getContext());
                    v.applyStyle(SimpleDialog.this.mRadioButtonStyle);
                }
                if (SimpleDialog.this.mItemHeight != -2) {
                    v.setMinHeight(SimpleDialog.this.mItemHeight);
                }
                v.setGravity(8388627);
                if (Build.VERSION.SDK_INT >= 17) {
                    if (((InternalListView) parent).isLayoutRtl()) {
                        i = 4;
                    }
                    v.setTextDirection(i);
                }
                v.setTextAppearance(v.getContext(), SimpleDialog.this.mItemTextAppearance);
                v.setCompoundDrawablePadding(SimpleDialog.this.mContentPadding);
            }
            v.setTag(Integer.valueOf(position));
            v.setText(this.mItems[position]);
            if (v instanceof CheckBox) {
                ((CheckBox) v).setCheckedImmediately(this.mSelected[position]);
            } else {
                ((RadioButton) v).setCheckedImmediately(this.mSelected[position]);
            }
            v.setOnCheckedChangeListener(this);
            return v;
        }

        public void onCheckedChanged(CompoundButton v, boolean isChecked) {
            int i;
            int position = ((Integer) v.getTag()).intValue();
            boolean[] zArr = this.mSelected;
            if (zArr[position] != isChecked) {
                zArr[position] = isChecked;
                if (SimpleDialog.this.mOnSelectionChangedListener != null) {
                    SimpleDialog.this.mOnSelectionChangedListener.onSelectionChanged(position, this.mSelected[position]);
                }
            }
            if (SimpleDialog.this.mMode == 2 && isChecked && (i = this.mLastSelectedIndex) != position) {
                this.mSelected[i] = false;
                if (SimpleDialog.this.mOnSelectionChangedListener != null) {
                    SimpleDialog.this.mOnSelectionChangedListener.onSelectionChanged(this.mLastSelectedIndex, false);
                }
                com.rey.material.widget.CompoundButton child = (com.rey.material.widget.CompoundButton) SimpleDialog.this.mListView.getChildAt(this.mLastSelectedIndex - SimpleDialog.this.mListView.getFirstVisiblePosition());
                if (child != null) {
                    child.setChecked(false);
                }
                this.mLastSelectedIndex = position;
            }
        }
    }

    public static class Builder extends Dialog.Builder implements OnSelectionChangedListener {
        public static final Parcelable.Creator<Builder> CREATOR = new Parcelable.Creator<Builder>() {
            public Builder createFromParcel(Parcel in) {
                return new Builder(in);
            }

            public Builder[] newArray(int size) {
                return new Builder[size];
            }
        };
        protected CharSequence[] mItems;
        protected CharSequence mMessage;
        protected int mMode;
        protected int[] mSelectedIndexes;

        public Builder() {
            super(C2500R.C2505style.Material_App_Dialog_Simple_Light);
        }

        public Builder(int styleId) {
            super(styleId);
        }

        public Builder message(CharSequence message) {
            this.mMode = 1;
            this.mMessage = message;
            return this;
        }

        public Builder items(CharSequence[] items, int selectedIndex) {
            this.mMode = 2;
            this.mItems = items;
            this.mSelectedIndexes = new int[]{selectedIndex};
            return this;
        }

        public Builder multiChoiceItems(CharSequence[] items, int... selectedIndexes) {
            this.mMode = 3;
            this.mItems = items;
            this.mSelectedIndexes = selectedIndexes;
            return this;
        }

        public int getSelectedIndex() {
            int i = this.mMode;
            if (i == 2 || i == 3) {
                return this.mSelectedIndexes[0];
            }
            return -1;
        }

        public CharSequence getSelectedValue() {
            int index = getSelectedIndex();
            if (index >= 0) {
                return this.mItems[index];
            }
            return null;
        }

        public int[] getSelectedIndexes() {
            int i = this.mMode;
            if (i == 2 || i == 3) {
                return this.mSelectedIndexes;
            }
            return null;
        }

        public CharSequence[] getSelectedValues() {
            int[] indexes = getSelectedIndexes();
            if (indexes == null || indexes.length == 0) {
                return null;
            }
            CharSequence[] result = new CharSequence[indexes.length];
            for (int i = 0; i < indexes.length; i++) {
                result[i] = this.mItems[indexes[i]];
            }
            return result;
        }

        /* access modifiers changed from: protected */
        public Dialog onBuild(Context context, int styleId) {
            SimpleDialog dialog = new SimpleDialog(context, styleId);
            int i = this.mMode;
            if (i == 1) {
                dialog.message(this.mMessage);
            } else if (i == 2) {
                CharSequence[] charSequenceArr = this.mItems;
                int[] iArr = this.mSelectedIndexes;
                int i2 = 0;
                if (iArr != null) {
                    i2 = iArr[0];
                }
                dialog.items(charSequenceArr, i2);
                dialog.onSelectionChangedListener(this);
            } else if (i == 3) {
                dialog.multiChoiceItems(this.mItems, this.mSelectedIndexes);
                dialog.onSelectionChangedListener(this);
            }
            return dialog;
        }

        public void onSelectionChanged(int index, boolean selected) {
            int i = this.mMode;
            if (i != 2) {
                if (i == 3) {
                    this.mSelectedIndexes = ((SimpleDialog) this.mDialog).getSelectedIndexes();
                }
            } else if (selected) {
                int[] iArr = this.mSelectedIndexes;
                if (iArr == null) {
                    this.mSelectedIndexes = new int[]{index};
                    return;
                }
                iArr[0] = index;
            }
        }

        protected Builder(Parcel in) {
            super(in);
        }

        /* access modifiers changed from: protected */
        public void onReadFromParcel(Parcel in) {
            int readInt = in.readInt();
            this.mMode = readInt;
            if (readInt == 1) {
                this.mMessage = (CharSequence) in.readParcelable((ClassLoader) null);
            } else if (readInt == 2) {
                Parcelable[] values = in.readParcelableArray((ClassLoader) null);
                if (values != null && values.length > 0) {
                    this.mItems = new CharSequence[values.length];
                    int i = 0;
                    while (true) {
                        CharSequence[] charSequenceArr = this.mItems;
                        if (i >= charSequenceArr.length) {
                            break;
                        }
                        charSequenceArr[i] = (CharSequence) values[i];
                        i++;
                    }
                } else {
                    this.mItems = null;
                }
                this.mSelectedIndexes = new int[]{in.readInt()};
            } else if (readInt == 3) {
                Parcelable[] values2 = in.readParcelableArray((ClassLoader) null);
                if (values2 != null && values2.length > 0) {
                    this.mItems = new CharSequence[values2.length];
                    int i2 = 0;
                    while (true) {
                        CharSequence[] charSequenceArr2 = this.mItems;
                        if (i2 >= charSequenceArr2.length) {
                            break;
                        }
                        charSequenceArr2[i2] = (CharSequence) values2[i2];
                        i2++;
                    }
                } else {
                    this.mItems = null;
                }
                int length = in.readInt();
                if (length > 0) {
                    int[] iArr = new int[length];
                    this.mSelectedIndexes = iArr;
                    in.readIntArray(iArr);
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onWriteToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mMode);
            int i = this.mMode;
            if (i != 1) {
                int i2 = 0;
                if (i == 2) {
                    dest.writeArray(this.mItems);
                    int[] iArr = this.mSelectedIndexes;
                    if (iArr != null) {
                        i2 = iArr[0];
                    }
                    dest.writeInt(i2);
                } else if (i == 3) {
                    dest.writeArray(this.mItems);
                    int[] iArr2 = this.mSelectedIndexes;
                    if (iArr2 != null) {
                        i2 = iArr2.length;
                    }
                    int length = i2;
                    dest.writeInt(length);
                    if (length > 0) {
                        dest.writeIntArray(this.mSelectedIndexes);
                    }
                }
            } else {
                dest.writeValue(this.mMessage);
            }
        }
    }
}
