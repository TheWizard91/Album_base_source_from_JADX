package com.google.android.play.core.internal;

import android.os.IBinder;
import android.os.IInterface;

/* renamed from: com.google.android.play.core.internal.bo */
public abstract class C3030bo extends C3058j implements C3031bp {
    /* renamed from: a */
    public static C3031bp m730a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.play.core.splitinstall.protocol.ISplitInstallService");
        return queryLocalInterface instanceof C3031bp ? (C3031bp) queryLocalInterface : new C3029bn(iBinder);
    }
}
