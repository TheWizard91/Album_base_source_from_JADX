package com.google.android.play.core.internal;

import android.content.Intent;
import android.util.Log;
import com.google.android.play.core.splitinstall.C3124b;
import java.util.List;

/* renamed from: com.google.android.play.core.internal.am */
final class C3001am implements Runnable {

    /* renamed from: a */
    final /* synthetic */ List f1297a;

    /* renamed from: b */
    final /* synthetic */ C3124b f1298b;

    /* renamed from: c */
    final /* synthetic */ C3002an f1299c;

    C3001am(C3002an anVar, List list, C3124b bVar) {
        this.f1299c = anVar;
        this.f1297a = list;
        this.f1298b = bVar;
    }

    public final void run() {
        try {
            if (!this.f1299c.f1302c.mo44106a((List<Intent>) this.f1297a)) {
                C3002an.m652a(this.f1299c, this.f1297a, this.f1298b);
            } else {
                C3002an.m651a(this.f1299c, this.f1298b);
            }
        } catch (Exception e) {
            Log.e("SplitCompat", "Error checking verified files.", e);
            this.f1298b.mo44277a(-11);
        }
    }
}
