package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public final class zzeo<V> {
    private static final Object zzf = new Object();
    private final String zza;
    private final zzem<V> zzb;
    private final V zzc;
    private final V zzd;
    private final Object zze;
    private volatile V zzg;
    private volatile V zzh;

    private zzeo(String str, V v, V v2, zzem<V> zzem) {
        this.zze = new Object();
        this.zzg = null;
        this.zzh = null;
        this.zza = str;
        this.zzc = v;
        this.zzd = v2;
        this.zzb = zzem;
    }

    public final String zza() {
        return this.zza;
    }

    /* JADX WARNING: CFG modification limit reached, blocks count: 172 */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0024, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r4 = com.google.android.gms.measurement.internal.zzaq.zzct.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0032, code lost:
        if (r4.hasNext() == false) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0034, code lost:
        r0 = (com.google.android.gms.measurement.internal.zzeo) r4.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x003f, code lost:
        if (com.google.android.gms.measurement.internal.zzx.zza() != false) goto L_0x0057;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0041, code lost:
        r1 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r2 = r0.zzb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0044, code lost:
        if (r2 == null) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0046, code lost:
        r1 = r2.zza();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x005e, code lost:
        throw new java.lang.IllegalStateException("Refreshing flag cache must be done on a worker thread.");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final V zza(V r4) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.zze
            monitor-enter(r0)
            monitor-exit(r0)     // Catch:{ all -> 0x0083 }
            if (r4 == 0) goto L_0x0008
            return r4
        L_0x0008:
            com.google.android.gms.measurement.internal.zzx r4 = com.google.android.gms.measurement.internal.zzep.zza
            if (r4 != 0) goto L_0x000f
            V r4 = r3.zzc
            return r4
        L_0x000f:
            com.google.android.gms.measurement.internal.zzx r4 = com.google.android.gms.measurement.internal.zzep.zza
            java.lang.Object r4 = zzf
            monitor-enter(r4)
            boolean r0 = com.google.android.gms.measurement.internal.zzx.zza()     // Catch:{ all -> 0x007e }
            if (r0 == 0) goto L_0x0025
            V r0 = r3.zzh     // Catch:{ all -> 0x007e }
            if (r0 != 0) goto L_0x0021
            V r0 = r3.zzc     // Catch:{ all -> 0x007e }
            goto L_0x0023
        L_0x0021:
            V r0 = r3.zzh     // Catch:{ all -> 0x007e }
        L_0x0023:
            monitor-exit(r4)     // Catch:{ all -> 0x007e }
            return r0
        L_0x0025:
            monitor-exit(r4)     // Catch:{ all -> 0x007e }
            java.util.List r4 = com.google.android.gms.measurement.internal.zzaq.zzct     // Catch:{ SecurityException -> 0x0060 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ SecurityException -> 0x0060 }
        L_0x002e:
            boolean r0 = r4.hasNext()     // Catch:{ SecurityException -> 0x0060 }
            if (r0 == 0) goto L_0x005f
            java.lang.Object r0 = r4.next()     // Catch:{ SecurityException -> 0x0060 }
            com.google.android.gms.measurement.internal.zzeo r0 = (com.google.android.gms.measurement.internal.zzeo) r0     // Catch:{ SecurityException -> 0x0060 }
            boolean r1 = com.google.android.gms.measurement.internal.zzx.zza()     // Catch:{ SecurityException -> 0x0060 }
            if (r1 != 0) goto L_0x0057
            r1 = 0
            com.google.android.gms.measurement.internal.zzem<V> r2 = r0.zzb     // Catch:{ IllegalStateException -> 0x004b }
            if (r2 == 0) goto L_0x004a
            java.lang.Object r1 = r2.zza()     // Catch:{ IllegalStateException -> 0x004b }
        L_0x004a:
            goto L_0x004d
        L_0x004b:
            r2 = move-exception
        L_0x004d:
            java.lang.Object r2 = zzf     // Catch:{ SecurityException -> 0x0060 }
            monitor-enter(r2)     // Catch:{ SecurityException -> 0x0060 }
            r0.zzh = r1     // Catch:{ all -> 0x0054 }
            monitor-exit(r2)     // Catch:{ all -> 0x0054 }
            goto L_0x002e
        L_0x0054:
            r4 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0054 }
            throw r4     // Catch:{ SecurityException -> 0x0060 }
        L_0x0057:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch:{ SecurityException -> 0x0060 }
            java.lang.String r0 = "Refreshing flag cache must be done on a worker thread."
            r4.<init>(r0)     // Catch:{ SecurityException -> 0x0060 }
            throw r4     // Catch:{ SecurityException -> 0x0060 }
        L_0x005f:
            goto L_0x0061
        L_0x0060:
            r4 = move-exception
        L_0x0061:
            com.google.android.gms.measurement.internal.zzem<V> r4 = r3.zzb
            if (r4 != 0) goto L_0x006b
            com.google.android.gms.measurement.internal.zzx r4 = com.google.android.gms.measurement.internal.zzep.zza
            V r4 = r3.zzc
            return r4
        L_0x006b:
            java.lang.Object r4 = r4.zza()     // Catch:{ SecurityException -> 0x0077, IllegalStateException -> 0x0070 }
            return r4
        L_0x0070:
            r4 = move-exception
            com.google.android.gms.measurement.internal.zzx r4 = com.google.android.gms.measurement.internal.zzep.zza
            V r4 = r3.zzc
            return r4
        L_0x0077:
            r4 = move-exception
            com.google.android.gms.measurement.internal.zzx r4 = com.google.android.gms.measurement.internal.zzep.zza
            V r4 = r3.zzc
            return r4
        L_0x007e:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x007e }
            throw r0
        L_0x0081:
            monitor-exit(r0)     // Catch:{ all -> 0x0083 }
            throw r4
        L_0x0083:
            r4 = move-exception
            goto L_0x0081
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzeo.zza(java.lang.Object):java.lang.Object");
    }
}
