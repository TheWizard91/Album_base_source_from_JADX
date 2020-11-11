package com.google.android.play.core.assetpacks;

import com.google.android.play.core.tasks.C3169i;
import java.util.List;

/* renamed from: com.google.android.play.core.assetpacks.cw */
final /* synthetic */ class C2934cw implements Runnable {

    /* renamed from: a */
    private final C2940db f1137a;

    /* renamed from: b */
    private final List f1138b;

    /* renamed from: c */
    private final C3169i f1139c;

    /* renamed from: d */
    private final List f1140d;

    C2934cw(C2940db dbVar, List list, C3169i iVar, List list2) {
        this.f1137a = dbVar;
        this.f1138b = list;
        this.f1139c = iVar;
        this.f1140d = list2;
    }

    public final void run() {
        this.f1137a.mo44044a(this.f1138b, this.f1139c, this.f1140d);
    }
}
