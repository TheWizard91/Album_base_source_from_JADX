package com.google.android.play.core.splitcompat;

import android.util.Log;
import java.util.Set;

/* renamed from: com.google.android.play.core.splitcompat.q */
final class C3095q implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Set f1384a;

    /* renamed from: b */
    final /* synthetic */ SplitCompat f1385b;

    C3095q(SplitCompat splitCompat, Set set) {
        this.f1385b = splitCompat;
        this.f1384a = set;
    }

    public final void run() {
        try {
            for (String f : this.f1384a) {
                this.f1385b.f1355b.mo44232f(f);
            }
        } catch (Exception e) {
            Log.e("SplitCompat", "Failed to remove from splitcompat storage split that is already installed", e);
        }
    }
}
