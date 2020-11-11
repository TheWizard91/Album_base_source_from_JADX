package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.assetpacks.al */
final class C2869al extends C2868ak<ParcelFileDescriptor> {
    C2869al(C2875ar arVar, C3169i<ParcelFileDescriptor> iVar) {
        super(arVar, iVar);
    }

    /* renamed from: b */
    public final void mo43914b(Bundle bundle, Bundle bundle2) throws RemoteException {
        super.mo43914b(bundle, bundle2);
        this.f920a.mo44332b((ParcelFileDescriptor) bundle.getParcelable("chunk_file_descriptor"));
    }
}
