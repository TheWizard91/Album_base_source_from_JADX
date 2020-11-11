package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
final class zzkw extends zzku<zzkt, zzkt> {
    zzkw() {
    }

    /* access modifiers changed from: package-private */
    public final boolean zza(zzjz zzjz) {
        return false;
    }

    private static void zza(Object obj, zzkt zzkt) {
        ((zzib) obj).zzb = zzkt;
    }

    /* access modifiers changed from: package-private */
    public final void zzd(Object obj) {
        ((zzib) obj).zzb.zzc();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ int zzf(Object obj) {
        return ((zzkt) obj).zze();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ int zze(Object obj) {
        return ((zzkt) obj).zzd();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object zzc(Object obj, Object obj2) {
        zzkt zzkt = (zzkt) obj;
        zzkt zzkt2 = (zzkt) obj2;
        if (zzkt2.equals(zzkt.zza())) {
            return zzkt;
        }
        return zzkt.zza(zzkt, zzkt2);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzb(Object obj, zzln zzln) throws IOException {
        ((zzkt) obj).zza(zzln);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(Object obj, zzln zzln) throws IOException {
        ((zzkt) obj).zzb(zzln);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzb(Object obj, Object obj2) {
        zza(obj, (zzkt) obj2);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object zzc(Object obj) {
        zzkt zzkt = ((zzib) obj).zzb;
        if (zzkt != zzkt.zza()) {
            return zzkt;
        }
        zzkt zzb = zzkt.zzb();
        zza(obj, zzb);
        return zzb;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object zzb(Object obj) {
        return ((zzib) obj).zzb;
    }

    /* access modifiers changed from: package-private */
    public final /* bridge */ /* synthetic */ void zza(Object obj, Object obj2) {
        zza(obj, (zzkt) obj2);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object zza(Object obj) {
        zzkt zzkt = (zzkt) obj;
        zzkt.zzc();
        return zzkt;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object zza() {
        return zzkt.zzb();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(Object obj, int i, Object obj2) {
        ((zzkt) obj).zza((i << 3) | 3, (Object) (zzkt) obj2);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(Object obj, int i, zzgt zzgt) {
        ((zzkt) obj).zza((i << 3) | 2, (Object) zzgt);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzb(Object obj, int i, long j) {
        ((zzkt) obj).zza((i << 3) | 1, (Object) Long.valueOf(j));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(Object obj, int i, int i2) {
        ((zzkt) obj).zza((i << 3) | 5, (Object) Integer.valueOf(i2));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(Object obj, int i, long j) {
        ((zzkt) obj).zza(i << 3, (Object) Long.valueOf(j));
    }
}
