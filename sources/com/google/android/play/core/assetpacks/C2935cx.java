package com.google.android.play.core.assetpacks;

import com.google.android.play.core.tasks.C3169i;
import java.util.List;

/* renamed from: com.google.android.play.core.assetpacks.cx */
final /* synthetic */ class C2935cx implements Runnable {

    /* renamed from: a */
    private final C2940db f1141a;

    /* renamed from: b */
    private final List f1142b;

    /* renamed from: c */
    private final C2883az f1143c;

    /* renamed from: d */
    private final C3169i f1144d;

    C2935cx(C2940db dbVar, List list, C2883az azVar, C3169i iVar) {
        this.f1141a = dbVar;
        this.f1142b = list;
        this.f1143c = azVar;
        this.f1144d = iVar;
    }

    public final void run() {
        this.f1141a.mo44043a(this.f1142b, this.f1143c, this.f1144d);
    }
}
