package com.google.android.play.core.splitinstall.testing;

import android.content.Intent;
import com.google.android.play.core.splitinstall.C3124b;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.testing.h */
final class C3153h implements C3124b {

    /* renamed from: a */
    final /* synthetic */ List f1507a;

    /* renamed from: b */
    final /* synthetic */ List f1508b;

    /* renamed from: c */
    final /* synthetic */ long f1509c;

    /* renamed from: d */
    final /* synthetic */ boolean f1510d;

    /* renamed from: e */
    final /* synthetic */ List f1511e;

    /* renamed from: f */
    final /* synthetic */ FakeSplitInstallManager f1512f;

    C3153h(FakeSplitInstallManager fakeSplitInstallManager, List list, List list2, long j, boolean z, List list3) {
        this.f1512f = fakeSplitInstallManager;
        this.f1507a = list;
        this.f1508b = list2;
        this.f1509c = j;
        this.f1510d = z;
        this.f1511e = list3;
    }

    /* renamed from: a */
    public final void mo44276a() {
        this.f1512f.f1483k.addAll(this.f1507a);
        this.f1512f.f1484l.addAll(this.f1508b);
        this.f1512f.m1033a(5, 0, Long.valueOf(this.f1509c), (Long) null, (List<String>) null, (Integer) null, (List<String>) null);
    }

    /* renamed from: a */
    public final void mo44277a(int i) {
        this.f1512f.m1033a(6, i, (Long) null, (Long) null, (List<String>) null, (Integer) null, (List<String>) null);
    }

    /* renamed from: b */
    public final void mo44278b() {
        if (!this.f1510d) {
            this.f1512f.m1032a((List<Intent>) this.f1511e, (List<String>) this.f1507a, (List<String>) this.f1508b, this.f1509c, true);
        }
    }
}
