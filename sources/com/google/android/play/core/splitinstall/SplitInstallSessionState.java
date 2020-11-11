package com.google.android.play.core.splitinstall;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.List;

public abstract class SplitInstallSessionState {
    /* renamed from: a */
    public static SplitInstallSessionState m938a(Bundle bundle) {
        return new C3097a(bundle.getInt("session_id"), bundle.getInt(NotificationCompat.CATEGORY_STATUS), bundle.getInt("error_code"), bundle.getLong("bytes_downloaded"), bundle.getLong("total_bytes_to_download"), bundle.getStringArrayList("module_names"), bundle.getStringArrayList("languages"), (PendingIntent) bundle.getParcelable("user_confirmation_intent"), bundle.getParcelableArrayList("split_file_intents"));
    }

    public static SplitInstallSessionState create(int i, int i2, int i3, long j, long j2, List<String> list, List<String> list2) {
        if (i2 != 8) {
            return new C3097a(i, i2, i3, j, j2, list, list2, (PendingIntent) null, (List<Intent>) null);
        }
        throw new IllegalArgumentException("REQUIRES_USER_CONFIRMATION state not supported.");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public abstract List<String> mo44261a();

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public abstract List<String> mo44262b();

    public abstract long bytesDownloaded();

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public abstract List<Intent> mo44264c();

    public abstract int errorCode();

    public boolean hasTerminalStatus() {
        int status = status();
        return status == 0 || status == 5 || status == 6 || status == 7;
    }

    public List<String> languages() {
        return mo44262b() != null ? new ArrayList(mo44262b()) : new ArrayList();
    }

    public List<String> moduleNames() {
        return mo44261a() != null ? new ArrayList(mo44261a()) : new ArrayList();
    }

    @Deprecated
    public abstract PendingIntent resolutionIntent();

    public abstract int sessionId();

    public abstract int status();

    public abstract long totalBytesToDownload();
}
