package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* renamed from: com.google.android.play.core.internal.x */
public final class C3072x extends C3057i implements C3073y {
    C3072x(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.assetpacks.protocol.IAssetPackExtractionServiceCallback");
    }

    /* renamed from: a */
    public final void mo44189a(Bundle bundle) throws RemoteException {
        Parcel a = mo44172a();
        C3059k.m807a(a, (Parcelable) bundle);
        mo44173a(3, a);
    }

    /* renamed from: a */
    public final void mo44190a(Bundle bundle, Bundle bundle2) throws RemoteException {
        Parcel a = mo44172a();
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m807a(a, (Parcelable) bundle2);
        mo44173a(2, a);
    }

    /* renamed from: b */
    public final void mo44191b(Bundle bundle) throws RemoteException {
        Parcel a = mo44172a();
        C3059k.m807a(a, (Parcelable) bundle);
        mo44173a(4, a);
    }
}
