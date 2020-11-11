package com.google.android.play.core.appupdate;

import android.app.PendingIntent;

/* renamed from: com.google.android.play.core.appupdate.l */
final class C2854l extends AppUpdateInfo {

    /* renamed from: a */
    private final String f841a;

    /* renamed from: b */
    private final int f842b;

    /* renamed from: c */
    private final int f843c;

    /* renamed from: d */
    private final int f844d;

    /* renamed from: e */
    private final Integer f845e;

    /* renamed from: f */
    private final int f846f;

    /* renamed from: g */
    private final long f847g;

    /* renamed from: h */
    private final long f848h;

    /* renamed from: i */
    private final long f849i;

    /* renamed from: j */
    private final long f850j;

    /* renamed from: k */
    private final PendingIntent f851k;

    /* renamed from: l */
    private final PendingIntent f852l;

    /* renamed from: m */
    private final PendingIntent f853m;

    /* renamed from: n */
    private final PendingIntent f854n;

    C2854l(String str, int i, int i2, int i3, Integer num, int i4, long j, long j2, long j3, long j4, PendingIntent pendingIntent, PendingIntent pendingIntent2, PendingIntent pendingIntent3, PendingIntent pendingIntent4) {
        String str2 = str;
        if (str2 != null) {
            this.f841a = str2;
            this.f842b = i;
            this.f843c = i2;
            this.f844d = i3;
            this.f845e = num;
            this.f846f = i4;
            this.f847g = j;
            this.f848h = j2;
            this.f849i = j3;
            this.f850j = j4;
            this.f851k = pendingIntent;
            this.f852l = pendingIntent2;
            this.f853m = pendingIntent3;
            this.f854n = pendingIntent4;
            return;
        }
        throw new NullPointerException("Null packageName");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final long mo43804a() {
        return this.f849i;
    }

    public final int availableVersionCode() {
        return this.f842b;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final long mo43807b() {
        return this.f850j;
    }

    public final long bytesDownloaded() {
        return this.f847g;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final PendingIntent mo43809c() {
        return this.f851k;
    }

    public final Integer clientVersionStalenessDays() {
        return this.f845e;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final PendingIntent mo43811d() {
        return this.f852l;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final PendingIntent mo43812e() {
        return this.f853m;
    }

    public final boolean equals(Object obj) {
        Integer num;
        PendingIntent pendingIntent;
        PendingIntent pendingIntent2;
        PendingIntent pendingIntent3;
        if (obj == this) {
            return true;
        }
        if (obj instanceof AppUpdateInfo) {
            AppUpdateInfo appUpdateInfo = (AppUpdateInfo) obj;
            if (this.f841a.equals(appUpdateInfo.packageName()) && this.f842b == appUpdateInfo.availableVersionCode() && this.f843c == appUpdateInfo.updateAvailability() && this.f844d == appUpdateInfo.installStatus() && ((num = this.f845e) == null ? appUpdateInfo.clientVersionStalenessDays() == null : num.equals(appUpdateInfo.clientVersionStalenessDays())) && this.f846f == appUpdateInfo.updatePriority() && this.f847g == appUpdateInfo.bytesDownloaded() && this.f848h == appUpdateInfo.totalBytesToDownload() && this.f849i == appUpdateInfo.mo43804a() && this.f850j == appUpdateInfo.mo43807b() && ((pendingIntent = this.f851k) == null ? appUpdateInfo.mo43809c() == null : pendingIntent.equals(appUpdateInfo.mo43809c())) && ((pendingIntent2 = this.f852l) == null ? appUpdateInfo.mo43811d() == null : pendingIntent2.equals(appUpdateInfo.mo43811d())) && ((pendingIntent3 = this.f853m) == null ? appUpdateInfo.mo43812e() == null : pendingIntent3.equals(appUpdateInfo.mo43812e()))) {
                PendingIntent pendingIntent4 = this.f854n;
                PendingIntent f = appUpdateInfo.mo43813f();
                if (pendingIntent4 == null ? f == null : pendingIntent4.equals(f)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public final PendingIntent mo43813f() {
        return this.f854n;
    }

    public final int hashCode() {
        int hashCode = (((((((this.f841a.hashCode() ^ 1000003) * 1000003) ^ this.f842b) * 1000003) ^ this.f843c) * 1000003) ^ this.f844d) * 1000003;
        Integer num = this.f845e;
        int i = 0;
        int hashCode2 = num != null ? num.hashCode() : 0;
        int i2 = this.f846f;
        long j = this.f847g;
        long j2 = this.f848h;
        long j3 = this.f849i;
        long j4 = this.f850j;
        int i3 = (((((((((((hashCode ^ hashCode2) * 1000003) ^ i2) * 1000003) ^ ((int) ((j >>> 32) ^ j))) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2))) * 1000003) ^ ((int) ((j3 >>> 32) ^ j3))) * 1000003) ^ ((int) ((j4 >>> 32) ^ j4))) * 1000003;
        PendingIntent pendingIntent = this.f851k;
        int hashCode3 = (i3 ^ (pendingIntent != null ? pendingIntent.hashCode() : 0)) * 1000003;
        PendingIntent pendingIntent2 = this.f852l;
        int hashCode4 = (hashCode3 ^ (pendingIntent2 != null ? pendingIntent2.hashCode() : 0)) * 1000003;
        PendingIntent pendingIntent3 = this.f853m;
        int hashCode5 = (hashCode4 ^ (pendingIntent3 != null ? pendingIntent3.hashCode() : 0)) * 1000003;
        PendingIntent pendingIntent4 = this.f854n;
        if (pendingIntent4 != null) {
            i = pendingIntent4.hashCode();
        }
        return hashCode5 ^ i;
    }

    public final int installStatus() {
        return this.f844d;
    }

    public final String packageName() {
        return this.f841a;
    }

    public final String toString() {
        String str = this.f841a;
        int i = this.f842b;
        int i2 = this.f843c;
        int i3 = this.f844d;
        String valueOf = String.valueOf(this.f845e);
        int i4 = this.f846f;
        long j = this.f847g;
        long j2 = this.f848h;
        long j3 = this.f849i;
        long j4 = this.f850j;
        String valueOf2 = String.valueOf(this.f851k);
        long j5 = j4;
        String valueOf3 = String.valueOf(this.f852l);
        String valueOf4 = String.valueOf(this.f853m);
        long j6 = j3;
        String valueOf5 = String.valueOf(this.f854n);
        int length = str.length();
        int length2 = String.valueOf(valueOf).length();
        int length3 = String.valueOf(valueOf2).length();
        int length4 = String.valueOf(valueOf3).length();
        StringBuilder sb = new StringBuilder(length + 463 + length2 + length3 + length4 + String.valueOf(valueOf4).length() + String.valueOf(valueOf5).length());
        sb.append("AppUpdateInfo{packageName=");
        sb.append(str);
        sb.append(", availableVersionCode=");
        sb.append(i);
        sb.append(", updateAvailability=");
        sb.append(i2);
        sb.append(", installStatus=");
        sb.append(i3);
        sb.append(", clientVersionStalenessDays=");
        sb.append(valueOf);
        sb.append(", updatePriority=");
        sb.append(i4);
        sb.append(", bytesDownloaded=");
        sb.append(j);
        sb.append(", totalBytesToDownload=");
        sb.append(j2);
        sb.append(", additionalSpaceRequired=");
        sb.append(j6);
        sb.append(", assetPackStorageSize=");
        sb.append(j5);
        sb.append(", immediateUpdateIntent=");
        sb.append(valueOf2);
        sb.append(", flexibleUpdateIntent=");
        sb.append(valueOf3);
        sb.append(", immediateDestructiveUpdateIntent=");
        sb.append(valueOf4);
        sb.append(", flexibleDestructiveUpdateIntent=");
        sb.append(valueOf5);
        sb.append("}");
        return sb.toString();
    }

    public final long totalBytesToDownload() {
        return this.f848h;
    }

    public final int updateAvailability() {
        return this.f843c;
    }

    public final int updatePriority() {
        return this.f846f;
    }
}
