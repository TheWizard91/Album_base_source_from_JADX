package com.google.android.play.core.appupdate;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.install.InstallException;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.appupdate.i */
final class C2851i extends C2850h<Void> {
    C2851i(C2853k kVar, C3169i<Void> iVar) {
        super(kVar, new C2989aa("OnCompleteUpdateCallback"), iVar);
    }

    /* renamed from: b */
    public final void mo43841b(Bundle bundle) throws RemoteException {
        super.mo43841b(bundle);
        if (bundle.getInt("error.code", -2) != 0) {
            this.f831b.mo44331b((Exception) new InstallException(bundle.getInt("error.code", -2)));
        } else {
            this.f831b.mo44332b(null);
        }
    }
}
