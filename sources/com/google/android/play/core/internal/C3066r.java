package com.google.android.play.core.internal;

import android.os.IBinder;
import android.os.IInterface;

/* renamed from: com.google.android.play.core.internal.r */
public abstract class C3066r extends C3058j implements C3067s {
    /* renamed from: a */
    public static C3067s m826a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.play.core.assetpacks.protocol.IAssetModuleService");
        return queryLocalInterface instanceof C3067s ? (C3067s) queryLocalInterface : new C3065q(iBinder);
    }
}
