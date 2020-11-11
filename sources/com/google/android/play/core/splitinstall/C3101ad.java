package com.google.android.play.core.splitinstall;

import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.ad */
final class C3101ad implements Runnable {

    /* renamed from: a */
    final /* synthetic */ SplitInstallRequest f1412a;

    /* renamed from: b */
    final /* synthetic */ C3103af f1413b;

    C3101ad(C3103af afVar, SplitInstallRequest splitInstallRequest) {
        this.f1413b = afVar;
        this.f1412a = splitInstallRequest;
    }

    public final void run() {
        C3100ac a = this.f1413b.f1416b;
        List<String> moduleNames = this.f1412a.getModuleNames();
        List a2 = C3103af.m954b(this.f1412a.getLanguages());
        Bundle bundle = new Bundle();
        bundle.putInt("session_id", 0);
        bundle.putInt(NotificationCompat.CATEGORY_STATUS, 5);
        bundle.putInt("error_code", 0);
        if (!moduleNames.isEmpty()) {
            bundle.putStringArrayList("module_names", new ArrayList(moduleNames));
        }
        if (!a2.isEmpty()) {
            bundle.putStringArrayList("languages", new ArrayList(a2));
        }
        bundle.putLong("total_bytes_to_download", 0);
        bundle.putLong("bytes_downloaded", 0);
        a.mo44198a(SplitInstallSessionState.m938a(bundle));
    }
}
