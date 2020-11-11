package com.google.android.play.core.appupdate;

import android.app.PendingIntent;

public abstract class AppUpdateInfo {
    /* renamed from: a */
    public static AppUpdateInfo m257a(String str, int i, int i2, int i3, Integer num, int i4, long j, long j2, long j3, long j4, PendingIntent pendingIntent, PendingIntent pendingIntent2, PendingIntent pendingIntent3, PendingIntent pendingIntent4) {
        return new C2854l(str, i, i2, i3, num, i4, j, j2, j3, j4, pendingIntent, pendingIntent2, pendingIntent3, pendingIntent4);
    }

    /* renamed from: b */
    private final boolean m258b(AppUpdateOptions appUpdateOptions) {
        return appUpdateOptions.mo43830a() && mo43804a() <= mo43807b();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public abstract long mo43804a();

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final PendingIntent mo43805a(AppUpdateOptions appUpdateOptions) {
        if (appUpdateOptions.appUpdateType() != 0) {
            if (appUpdateOptions.appUpdateType() == 1) {
                if (mo43809c() != null) {
                    return mo43809c();
                }
                if (m258b(appUpdateOptions)) {
                    return mo43812e();
                }
            }
            return null;
        } else if (mo43811d() != null) {
            return mo43811d();
        } else {
            if (m258b(appUpdateOptions)) {
                return mo43813f();
            }
            return null;
        }
    }

    public abstract int availableVersionCode();

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public abstract long mo43807b();

    public abstract long bytesDownloaded();

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public abstract PendingIntent mo43809c();

    public abstract Integer clientVersionStalenessDays();

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public abstract PendingIntent mo43811d();

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public abstract PendingIntent mo43812e();

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public abstract PendingIntent mo43813f();

    public abstract int installStatus();

    public boolean isUpdateTypeAllowed(int i) {
        return mo43805a(AppUpdateOptions.defaultOptions(i)) != null;
    }

    public boolean isUpdateTypeAllowed(AppUpdateOptions appUpdateOptions) {
        return mo43805a(appUpdateOptions) != null;
    }

    public abstract String packageName();

    public abstract long totalBytesToDownload();

    public abstract int updateAvailability();

    public abstract int updatePriority();
}
