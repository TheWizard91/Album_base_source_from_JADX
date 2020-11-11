package com.google.android.play.core.appupdate;

/* renamed from: com.google.android.play.core.appupdate.n */
final class C2856n extends AppUpdateOptions {

    /* renamed from: a */
    private final int f857a;

    /* renamed from: b */
    private final boolean f858b;

    /* synthetic */ C2856n(int i, boolean z) {
        this.f857a = i;
        this.f858b = z;
    }

    /* renamed from: a */
    public final boolean mo43830a() {
        return this.f858b;
    }

    public final int appUpdateType() {
        return this.f857a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AppUpdateOptions) {
            AppUpdateOptions appUpdateOptions = (AppUpdateOptions) obj;
            return this.f857a == appUpdateOptions.appUpdateType() && this.f858b == appUpdateOptions.mo43830a();
        }
    }

    public final int hashCode() {
        return ((this.f857a ^ 1000003) * 1000003) ^ (!this.f858b ? 1237 : 1231);
    }

    public final String toString() {
        int i = this.f857a;
        boolean z = this.f858b;
        StringBuilder sb = new StringBuilder(68);
        sb.append("AppUpdateOptions{appUpdateType=");
        sb.append(i);
        sb.append(", allowClearStorage=");
        sb.append(z);
        sb.append("}");
        return sb.toString();
    }
}
