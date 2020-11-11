package com.google.android.play.core.appupdate;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.internal.C3063o;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.appupdate.h */
class C2850h<T> extends C3063o {

    /* renamed from: a */
    final C2989aa f830a;

    /* renamed from: b */
    final C3169i<T> f831b;

    /* renamed from: c */
    final /* synthetic */ C2853k f832c;

    C2850h(C2853k kVar, C2989aa aaVar, C3169i<T> iVar) {
        this.f832c = kVar;
        this.f830a = aaVar;
        this.f831b = iVar;
    }

    /* renamed from: a */
    public void mo43840a(Bundle bundle) throws RemoteException {
        this.f832c.f837a.mo44098a();
        this.f830a.mo44090c("onRequestInfo", new Object[0]);
    }

    /* renamed from: b */
    public void mo43841b(Bundle bundle) throws RemoteException {
        this.f832c.f837a.mo44098a();
        this.f830a.mo44090c("onCompleteUpdate", new Object[0]);
    }
}
