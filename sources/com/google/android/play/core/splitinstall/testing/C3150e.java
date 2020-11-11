package com.google.android.play.core.splitinstall.testing;

import com.google.android.play.core.splitinstall.SplitInstallException;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;

/* renamed from: com.google.android.play.core.splitinstall.testing.e */
final class C3150e implements C3154i {

    /* renamed from: a */
    final /* synthetic */ int f1499a;

    C3150e(int i) {
        this.f1499a = i;
    }

    /* renamed from: a */
    public final SplitInstallSessionState mo44301a(SplitInstallSessionState splitInstallSessionState) {
        if (splitInstallSessionState != null && this.f1499a == splitInstallSessionState.sessionId() && splitInstallSessionState.status() == 1) {
            return SplitInstallSessionState.create(this.f1499a, 7, splitInstallSessionState.errorCode(), splitInstallSessionState.bytesDownloaded(), splitInstallSessionState.totalBytesToDownload(), splitInstallSessionState.moduleNames(), splitInstallSessionState.languages());
        }
        throw new SplitInstallException(-3);
    }
}
