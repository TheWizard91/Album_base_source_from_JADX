package com.google.android.play.core.splitinstall;

import android.app.PendingIntent;
import android.content.Intent;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.a */
final class C3097a extends SplitInstallSessionState {

    /* renamed from: a */
    private final int f1392a;

    /* renamed from: b */
    private final int f1393b;

    /* renamed from: c */
    private final int f1394c;

    /* renamed from: d */
    private final long f1395d;

    /* renamed from: e */
    private final long f1396e;

    /* renamed from: f */
    private final List<String> f1397f;

    /* renamed from: g */
    private final List<String> f1398g;

    /* renamed from: h */
    private final PendingIntent f1399h;

    /* renamed from: i */
    private final List<Intent> f1400i;

    C3097a(int i, int i2, int i3, long j, long j2, List<String> list, List<String> list2, PendingIntent pendingIntent, List<Intent> list3) {
        this.f1392a = i;
        this.f1393b = i2;
        this.f1394c = i3;
        this.f1395d = j;
        this.f1396e = j2;
        this.f1397f = list;
        this.f1398g = list2;
        this.f1399h = pendingIntent;
        this.f1400i = list3;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final List<String> mo44261a() {
        return this.f1397f;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final List<String> mo44262b() {
        return this.f1398g;
    }

    public final long bytesDownloaded() {
        return this.f1395d;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final List<Intent> mo44264c() {
        return this.f1400i;
    }

    public final boolean equals(Object obj) {
        List<String> list;
        List<String> list2;
        PendingIntent pendingIntent;
        if (obj == this) {
            return true;
        }
        if (obj instanceof SplitInstallSessionState) {
            SplitInstallSessionState splitInstallSessionState = (SplitInstallSessionState) obj;
            if (this.f1392a == splitInstallSessionState.sessionId() && this.f1393b == splitInstallSessionState.status() && this.f1394c == splitInstallSessionState.errorCode() && this.f1395d == splitInstallSessionState.bytesDownloaded() && this.f1396e == splitInstallSessionState.totalBytesToDownload() && ((list = this.f1397f) == null ? splitInstallSessionState.mo44261a() == null : list.equals(splitInstallSessionState.mo44261a())) && ((list2 = this.f1398g) == null ? splitInstallSessionState.mo44262b() == null : list2.equals(splitInstallSessionState.mo44262b())) && ((pendingIntent = this.f1399h) == null ? splitInstallSessionState.resolutionIntent() == null : pendingIntent.equals(splitInstallSessionState.resolutionIntent()))) {
                List<Intent> list3 = this.f1400i;
                List<Intent> c = splitInstallSessionState.mo44264c();
                if (list3 == null ? c == null : list3.equals(c)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int errorCode() {
        return this.f1394c;
    }

    public final int hashCode() {
        int i = this.f1392a;
        int i2 = this.f1393b;
        int i3 = this.f1394c;
        long j = this.f1395d;
        long j2 = this.f1396e;
        int i4 = (((((((((i ^ 1000003) * 1000003) ^ i2) * 1000003) ^ i3) * 1000003) ^ ((int) ((j >>> 32) ^ j))) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2))) * 1000003;
        List<String> list = this.f1397f;
        int i5 = 0;
        int hashCode = (i4 ^ (list != null ? list.hashCode() : 0)) * 1000003;
        List<String> list2 = this.f1398g;
        int hashCode2 = (hashCode ^ (list2 != null ? list2.hashCode() : 0)) * 1000003;
        PendingIntent pendingIntent = this.f1399h;
        int hashCode3 = (hashCode2 ^ (pendingIntent != null ? pendingIntent.hashCode() : 0)) * 1000003;
        List<Intent> list3 = this.f1400i;
        if (list3 != null) {
            i5 = list3.hashCode();
        }
        return hashCode3 ^ i5;
    }

    @Deprecated
    public final PendingIntent resolutionIntent() {
        return this.f1399h;
    }

    public final int sessionId() {
        return this.f1392a;
    }

    public final int status() {
        return this.f1393b;
    }

    public final String toString() {
        int i = this.f1392a;
        int i2 = this.f1393b;
        int i3 = this.f1394c;
        long j = this.f1395d;
        long j2 = this.f1396e;
        String valueOf = String.valueOf(this.f1397f);
        String valueOf2 = String.valueOf(this.f1398g);
        String valueOf3 = String.valueOf(this.f1399h);
        String valueOf4 = String.valueOf(this.f1400i);
        int length = String.valueOf(valueOf).length();
        int length2 = String.valueOf(valueOf2).length();
        StringBuilder sb = new StringBuilder(length + 251 + length2 + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length());
        sb.append("SplitInstallSessionState{sessionId=");
        sb.append(i);
        sb.append(", status=");
        sb.append(i2);
        sb.append(", errorCode=");
        sb.append(i3);
        sb.append(", bytesDownloaded=");
        sb.append(j);
        sb.append(", totalBytesToDownload=");
        sb.append(j2);
        sb.append(", moduleNamesNullable=");
        sb.append(valueOf);
        sb.append(", languagesNullable=");
        sb.append(valueOf2);
        sb.append(", resolutionIntent=");
        sb.append(valueOf3);
        sb.append(", splitFileIntents=");
        sb.append(valueOf4);
        sb.append("}");
        return sb.toString();
    }

    public final long totalBytesToDownload() {
        return this.f1396e;
    }
}
