package com.google.android.play.core.splitcompat;

import android.util.Log;

/* renamed from: com.google.android.play.core.splitcompat.p */
final class C3094p implements Runnable {

    /* renamed from: a */
    final /* synthetic */ SplitCompat f1383a;

    C3094p(SplitCompat splitCompat) {
        this.f1383a = splitCompat;
    }

    public final void run() {
        try {
            this.f1383a.f1355b.mo44222a();
        } catch (Exception e) {
            Log.e("SplitCompat", "Failed to cleanup splitcompat storage", e);
        }
    }
}
