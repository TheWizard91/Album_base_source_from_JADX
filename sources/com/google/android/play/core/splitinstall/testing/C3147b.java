package com.google.android.play.core.splitinstall.testing;

import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.testing.b */
final class C3147b implements C3154i {

    /* renamed from: a */
    final /* synthetic */ Integer f1488a;

    /* renamed from: b */
    final /* synthetic */ int f1489b;

    /* renamed from: c */
    final /* synthetic */ int f1490c;

    /* renamed from: d */
    final /* synthetic */ Long f1491d;

    /* renamed from: e */
    final /* synthetic */ Long f1492e;

    /* renamed from: f */
    final /* synthetic */ List f1493f;

    /* renamed from: g */
    final /* synthetic */ List f1494g;

    C3147b(Integer num, int i, int i2, Long l, Long l2, List list, List list2) {
        this.f1488a = num;
        this.f1489b = i;
        this.f1490c = i2;
        this.f1491d = l;
        this.f1492e = l2;
        this.f1493f = list;
        this.f1494g = list2;
    }

    /* renamed from: a */
    public final SplitInstallSessionState mo44301a(SplitInstallSessionState splitInstallSessionState) {
        if (splitInstallSessionState == null) {
            splitInstallSessionState = SplitInstallSessionState.create(0, 0, 0, 0, 0, new ArrayList(), new ArrayList());
        }
        Integer num = this.f1488a;
        int intValue = num != null ? num.intValue() : splitInstallSessionState.sessionId();
        int i = this.f1489b;
        int i2 = this.f1490c;
        Long l = this.f1491d;
        long longValue = l != null ? l.longValue() : splitInstallSessionState.bytesDownloaded();
        Long l2 = this.f1492e;
        long longValue2 = l2 == null ? splitInstallSessionState.totalBytesToDownload() : l2.longValue();
        List<String> list = this.f1493f;
        if (list == null) {
            list = splitInstallSessionState.moduleNames();
        }
        List<String> list2 = list;
        List<String> list3 = this.f1494g;
        return SplitInstallSessionState.create(intValue, i, i2, longValue, longValue2, list2, list3 == null ? splitInstallSessionState.languages() : list3);
    }
}
