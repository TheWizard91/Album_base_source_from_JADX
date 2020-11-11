package com.google.android.play.core.splitinstall.testing;

import android.content.Intent;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.testing.g */
final class C3152g implements Runnable {

    /* renamed from: a */
    final /* synthetic */ long f1502a;

    /* renamed from: b */
    final /* synthetic */ List f1503b;

    /* renamed from: c */
    final /* synthetic */ List f1504c;

    /* renamed from: d */
    final /* synthetic */ List f1505d;

    /* renamed from: e */
    final /* synthetic */ FakeSplitInstallManager f1506e;

    C3152g(FakeSplitInstallManager fakeSplitInstallManager, long j, List list, List list2, List list3) {
        this.f1506e = fakeSplitInstallManager;
        this.f1502a = j;
        this.f1503b = list;
        this.f1504c = list2;
        this.f1505d = list3;
    }

    public final void run() {
        long j = this.f1502a / 3;
        long j2 = 0;
        int i = 0;
        while (((long) i) < 3) {
            j2 = Math.min(this.f1502a, j2 + j);
            this.f1506e.m1033a(2, 0, Long.valueOf(j2), (Long) null, (List<String>) null, (Integer) null, (List<String>) null);
            i++;
            C3146a.m1042a();
        }
        if (this.f1506e.f1485m.get()) {
            this.f1506e.m1033a(6, -6, (Long) null, (Long) null, (List<String>) null, (Integer) null, (List<String>) null);
        } else {
            this.f1506e.m1032a((List<Intent>) this.f1503b, (List<String>) this.f1504c, (List<String>) this.f1505d, this.f1502a, false);
        }
    }
}
