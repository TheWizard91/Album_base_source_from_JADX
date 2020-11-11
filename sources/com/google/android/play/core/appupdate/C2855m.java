package com.google.android.play.core.appupdate;

import com.google.android.play.core.appupdate.AppUpdateOptions;

/* renamed from: com.google.android.play.core.appupdate.m */
final class C2855m extends AppUpdateOptions.Builder {

    /* renamed from: a */
    private Integer f855a;

    /* renamed from: b */
    private Boolean f856b;

    C2855m() {
    }

    /* renamed from: a */
    public final void mo43832a() {
        this.f856b = false;
    }

    public final AppUpdateOptions build() {
        String str = "";
        if (this.f855a == null) {
            str = str.concat(" appUpdateType");
        }
        if (this.f856b == null) {
            str = String.valueOf(str).concat(" allowClearStorage");
        }
        if (str.isEmpty()) {
            return new C2856n(this.f855a.intValue(), this.f856b.booleanValue());
        }
        String valueOf = String.valueOf(str);
        throw new IllegalStateException(valueOf.length() == 0 ? new String("Missing required properties:") : "Missing required properties:".concat(valueOf));
    }

    public final AppUpdateOptions.Builder setAppUpdateType(int i) {
        this.f855a = Integer.valueOf(i);
        return this;
    }
}
