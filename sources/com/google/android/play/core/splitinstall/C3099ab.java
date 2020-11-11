package com.google.android.play.core.splitinstall;

/* renamed from: com.google.android.play.core.splitinstall.ab */
final class C3099ab implements Runnable {

    /* renamed from: a */
    final /* synthetic */ SplitInstallSessionState f1405a;

    /* renamed from: b */
    final /* synthetic */ int f1406b;

    /* renamed from: c */
    final /* synthetic */ int f1407c;

    /* renamed from: d */
    final /* synthetic */ C3100ac f1408d;

    C3099ab(C3100ac acVar, SplitInstallSessionState splitInstallSessionState, int i, int i2) {
        this.f1408d = acVar;
        this.f1405a = splitInstallSessionState;
        this.f1406b = i;
        this.f1407c = i2;
    }

    public final void run() {
        C3100ac acVar = this.f1408d;
        SplitInstallSessionState splitInstallSessionState = this.f1405a;
        acVar.mo44198a(new C3097a(splitInstallSessionState.sessionId(), this.f1406b, this.f1407c, splitInstallSessionState.bytesDownloaded(), splitInstallSessionState.totalBytesToDownload(), splitInstallSessionState.mo44261a(), splitInstallSessionState.mo44262b(), splitInstallSessionState.resolutionIntent(), splitInstallSessionState.mo44264c()));
    }
}
