package com.google.android.play.core.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.play.core.internal.i */
public class C3057i implements IInterface {

    /* renamed from: a */
    private final IBinder f1335a;

    /* renamed from: b */
    private final String f1336b;

    protected C3057i(IBinder iBinder, String str) {
        this.f1335a = iBinder;
        this.f1336b = str;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final Parcel mo44172a() {
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken(this.f1336b);
        return obtain;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo44173a(int i, Parcel parcel) throws RemoteException {
        try {
            this.f1335a.transact(i, parcel, (Parcel) null, 1);
        } finally {
            parcel.recycle();
        }
    }

    public final IBinder asBinder() {
        return this.f1335a;
    }
}
