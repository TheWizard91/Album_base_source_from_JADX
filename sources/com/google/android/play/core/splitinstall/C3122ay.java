package com.google.android.play.core.splitinstall;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C3032bq;
import com.google.android.play.core.tasks.C3169i;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.ay */
class C3122ay<T> extends C3032bq {

    /* renamed from: a */
    final C3169i<T> f1445a;

    /* renamed from: b */
    final /* synthetic */ C3123az f1446b;

    C3122ay(C3123az azVar, C3169i<T> iVar) {
        this.f1446b = azVar;
        this.f1445a = iVar;
    }

    /* renamed from: a */
    public final void mo44126a() throws RemoteException {
        this.f1446b.f1449a.mo44098a();
        C3123az.f1447b.mo44090c("onCompleteInstallForAppUpdate", new Object[0]);
    }

    /* renamed from: a */
    public final void mo44127a(int i) throws RemoteException {
        this.f1446b.f1449a.mo44098a();
        C3123az.f1447b.mo44090c("onCompleteInstall(%d)", Integer.valueOf(i));
    }

    /* renamed from: a */
    public void mo44128a(int i, Bundle bundle) throws RemoteException {
        this.f1446b.f1449a.mo44098a();
        C3123az.f1447b.mo44090c("onCancelInstall(%d)", Integer.valueOf(i));
    }

    /* renamed from: a */
    public void mo44129a(Bundle bundle) throws RemoteException {
        this.f1446b.f1449a.mo44098a();
        C3123az.f1447b.mo44090c("onDeferredInstall", new Object[0]);
    }

    /* renamed from: a */
    public void mo44130a(List<Bundle> list) throws RemoteException {
        this.f1446b.f1449a.mo44098a();
        C3123az.f1447b.mo44090c("onGetSessionStates", new Object[0]);
    }

    /* renamed from: b */
    public final void mo44131b() throws RemoteException {
        this.f1446b.f1449a.mo44098a();
        C3123az.f1447b.mo44090c("onGetSplitsForAppUpdate", new Object[0]);
    }

    /* renamed from: b */
    public void mo44132b(int i, Bundle bundle) throws RemoteException {
        this.f1446b.f1449a.mo44098a();
        C3123az.f1447b.mo44090c("onGetSession(%d)", Integer.valueOf(i));
    }

    /* renamed from: b */
    public void mo44133b(Bundle bundle) throws RemoteException {
        this.f1446b.f1449a.mo44098a();
        C3123az.f1447b.mo44090c("onDeferredLanguageInstall", new Object[0]);
    }

    /* renamed from: c */
    public void mo44134c(int i, Bundle bundle) throws RemoteException {
        this.f1446b.f1449a.mo44098a();
        C3123az.f1447b.mo44090c("onStartInstall(%d)", Integer.valueOf(i));
    }

    /* renamed from: c */
    public void mo44135c(Bundle bundle) throws RemoteException {
        this.f1446b.f1449a.mo44098a();
        C3123az.f1447b.mo44090c("onDeferredLanguageUninstall", new Object[0]);
    }

    /* renamed from: d */
    public void mo44136d(Bundle bundle) throws RemoteException {
        this.f1446b.f1449a.mo44098a();
        C3123az.f1447b.mo44090c("onDeferredUninstall", new Object[0]);
    }

    /* renamed from: e */
    public final void mo44137e(Bundle bundle) throws RemoteException {
        this.f1446b.f1449a.mo44098a();
        int i = bundle.getInt("error_code");
        C3123az.f1447b.mo44089b("onError(%d)", Integer.valueOf(i));
        this.f1445a.mo44331b((Exception) new SplitInstallException(i));
    }
}
