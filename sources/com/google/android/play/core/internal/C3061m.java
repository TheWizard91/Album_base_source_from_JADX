package com.google.android.play.core.internal;

import android.os.IBinder;
import android.os.IInterface;

/* renamed from: com.google.android.play.core.internal.m */
public abstract class C3061m extends C3058j implements C3062n {
    /* renamed from: a */
    public static C3062n m810a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.play.core.appupdate.protocol.IAppUpdateService");
        return queryLocalInterface instanceof C3062n ? (C3062n) queryLocalInterface : new C3060l(iBinder);
    }
}
