package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* renamed from: com.google.android.play.core.internal.l */
public final class C3060l extends C3057i implements C3062n {
    C3060l(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.appupdate.protocol.IAppUpdateService");
    }

    /* renamed from: a */
    public final void mo44177a(String str, Bundle bundle, C3064p pVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m806a(a, (IInterface) pVar);
        mo44173a(2, a);
    }

    /* renamed from: b */
    public final void mo44178b(String str, Bundle bundle, C3064p pVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m806a(a, (IInterface) pVar);
        mo44173a(3, a);
    }
}
