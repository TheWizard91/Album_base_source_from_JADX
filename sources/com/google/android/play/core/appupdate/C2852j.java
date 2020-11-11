package com.google.android.play.core.appupdate;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.install.InstallException;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.appupdate.j */
final class C2852j extends C2850h<AppUpdateInfo> {

    /* renamed from: d */
    final /* synthetic */ C2853k f833d;

    /* renamed from: e */
    private final String f834e;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2852j(C2853k kVar, C3169i<AppUpdateInfo> iVar, String str) {
        super(kVar, new C2989aa("OnRequestInstallCallback"), iVar);
        this.f833d = kVar;
        this.f834e = str;
    }

    /* renamed from: a */
    public final void mo43840a(Bundle bundle) throws RemoteException {
        super.mo43840a(bundle);
        if (bundle.getInt("error.code", -2) == 0 || bundle.getInt("error.code", -2) == 1) {
            this.f831b.mo44332b(C2853k.m278a(this.f833d, bundle, this.f834e));
        } else {
            this.f831b.mo44331b((Exception) new InstallException(bundle.getInt("error.code", -2)));
        }
    }
}
