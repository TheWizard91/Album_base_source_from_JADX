package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.play.core.internal.o */
public abstract class C3063o extends C3058j implements C3064p {
    public C3063o() {
        super("com.google.android.play.core.appupdate.protocol.IAppUpdateServiceCallback");
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final boolean mo44125a(int i, Parcel parcel) throws RemoteException {
        if (i == 2) {
            mo43840a((Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
            return true;
        } else if (i != 3) {
            return false;
        } else {
            mo43841b((Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
            return true;
        }
    }
}
