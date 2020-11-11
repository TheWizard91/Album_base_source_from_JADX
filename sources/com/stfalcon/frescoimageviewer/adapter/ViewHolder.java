package com.stfalcon.frescoimageviewer.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

public abstract class ViewHolder {
    private static final String STATE = ViewHolder.class.getSimpleName();
    public final View itemView;
    boolean mIsAttached;
    int mPosition;

    public ViewHolder(View itemView2) {
        if (itemView2 != null) {
            this.itemView = itemView2;
            return;
        }
        throw new IllegalArgumentException("itemView should not be null");
    }

    /* access modifiers changed from: package-private */
    public void attach(ViewGroup parent, int position) {
        this.mIsAttached = true;
        this.mPosition = position;
        parent.addView(this.itemView);
    }

    /* access modifiers changed from: package-private */
    public void detach(ViewGroup parent) {
        parent.removeView(this.itemView);
        this.mIsAttached = false;
    }

    /* access modifiers changed from: package-private */
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            String str = STATE;
            SparseArray<Parcelable> ss = bundle.containsKey(str) ? bundle.getSparseParcelableArray(str) : null;
            if (ss != null) {
                this.itemView.restoreHierarchyState(ss);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Parcelable onSaveInstanceState() {
        SparseArray<Parcelable> state = new SparseArray<>();
        this.itemView.saveHierarchyState(state);
        Bundle bundle = new Bundle();
        bundle.putSparseParcelableArray(STATE, state);
        return bundle;
    }
}
