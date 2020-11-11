package com.google.android.gms.internal.firebase_auth;

import java.util.NoSuchElementException;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzgy extends zzha {
    private int zza = 0;
    private final int zzb;
    private final /* synthetic */ zzgv zzc;

    zzgy(zzgv zzgv) {
        this.zzc = zzgv;
        this.zzb = zzgv.zza();
    }

    public final boolean hasNext() {
        return this.zza < this.zzb;
    }

    public final byte zza() {
        int i = this.zza;
        if (i < this.zzb) {
            this.zza = i + 1;
            return this.zzc.zzb(i);
        }
        throw new NoSuchElementException();
    }
}
