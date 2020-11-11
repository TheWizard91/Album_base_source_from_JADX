package com.google.android.play.core.assetpacks;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.google.android.play.core.assetpacks.cb */
final class C2913cb {

    /* renamed from: a */
    private final Map<String, Double> f1082a = new HashMap();

    C2913cb() {
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final synchronized double mo44014a(String str, C2931ct ctVar) {
        double d;
        d = 1.0d;
        if (ctVar instanceof C2906bv) {
            d = (((double) ((C2906bv) ctVar).f1047e) + 1.0d) / ((double) ((C2906bv) ctVar).f1048f);
        }
        this.f1082a.put(str, Double.valueOf(d));
        return d;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final synchronized void mo44015a(String str) {
        this.f1082a.put(str, Double.valueOf(0.0d));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final synchronized double mo44016b(String str) {
        Double d = this.f1082a.get(str);
        if (d == null) {
            return 0.0d;
        }
        return d.doubleValue();
    }
}
