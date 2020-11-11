package com.google.android.play.core.splitinstall;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.splitinstall.av */
final class C3119av extends C3122ay<SplitInstallSessionState> {
    C3119av(C3123az azVar, C3169i<SplitInstallSessionState> iVar) {
        super(azVar, iVar);
    }

    /* renamed from: b */
    public final void mo44132b(int i, Bundle bundle) throws RemoteException {
        super.mo44132b(i, bundle);
        this.f1445a.mo44332b(SplitInstallSessionState.m938a(bundle));
    }
}
