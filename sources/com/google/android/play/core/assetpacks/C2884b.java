package com.google.android.play.core.assetpacks;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.internal.C3035bt;
import com.google.android.play.core.internal.C3070v;
import com.google.android.play.core.internal.C3073y;
import java.util.Arrays;

/* renamed from: com.google.android.play.core.assetpacks.b */
final class C2884b extends C3070v {

    /* renamed from: a */
    private final C2989aa f965a = new C2989aa("AssetPackExtractionService");

    /* renamed from: b */
    private final Context f966b;

    /* renamed from: c */
    private final AssetPackExtractionService f967c;

    /* renamed from: d */
    private final C2886bb f968d;

    C2884b(Context context, AssetPackExtractionService assetPackExtractionService, C2886bb bbVar) {
        this.f966b = context;
        this.f967c = assetPackExtractionService;
        this.f968d = bbVar;
    }

    /* renamed from: a */
    public final void mo43936a(Bundle bundle, C3073y yVar) throws RemoteException {
        String[] packagesForUid;
        this.f965a.mo44087a("updateServiceState AIDL call", new Object[0]);
        if (C3035bt.m754a(this.f966b) && (packagesForUid = this.f966b.getPackageManager().getPackagesForUid(Binder.getCallingUid())) != null && Arrays.asList(packagesForUid).contains("com.android.vending")) {
            yVar.mo44190a(this.f967c.mo43875a(bundle), new Bundle());
            return;
        }
        yVar.mo44189a(new Bundle());
        this.f967c.mo43876a();
    }

    /* renamed from: a */
    public final void mo43937a(C3073y yVar) throws RemoteException {
        this.f968d.mo43964f();
        yVar.mo44191b(new Bundle());
    }
}
