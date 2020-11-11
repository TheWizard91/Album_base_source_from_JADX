package com.google.android.play.core.splitinstall;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.tasks.C3169i;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.aw */
final class C3120aw extends C3122ay<List<SplitInstallSessionState>> {
    C3120aw(C3123az azVar, C3169i<List<SplitInstallSessionState>> iVar) {
        super(azVar, iVar);
    }

    /* renamed from: a */
    public final void mo44130a(List<Bundle> list) throws RemoteException {
        super.mo44130a(list);
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(SplitInstallSessionState.m938a(list.get(i)));
        }
        this.f1445a.mo44332b(arrayList);
    }
}
