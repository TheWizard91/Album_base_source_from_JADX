package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.net.URL;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
final class zzfg implements Runnable {
    private final URL zza;
    private final byte[] zzb;
    private final zzfe zzc;
    private final String zzd;
    private final Map<String, String> zze;
    private final /* synthetic */ zzfc zzf;

    public zzfg(zzfc zzfc, String str, URL url, byte[] bArr, Map<String, String> map, zzfe zzfe) {
        this.zzf = zzfc;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(zzfe);
        this.zza = url;
        this.zzb = bArr;
        this.zzc = zzfe;
        this.zzd = str;
        this.zze = map;
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x00d4 A[SYNTHETIC, Splitter:B:45:0x00d4] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ee  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x010f A[SYNTHETIC, Splitter:B:58:0x010f] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0129  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r13 = this;
            java.lang.String r0 = "Error closing HTTP compressed POST connection output stream. appId"
            com.google.android.gms.measurement.internal.zzfc r1 = r13.zzf
            r1.zzc()
            r1 = 0
            r2 = 0
            com.google.android.gms.measurement.internal.zzfc r3 = r13.zzf     // Catch:{ IOException -> 0x0108, all -> 0x00cd }
            java.net.URL r4 = r13.zza     // Catch:{ IOException -> 0x0108, all -> 0x00cd }
            java.net.HttpURLConnection r3 = r3.zza((java.net.URL) r4)     // Catch:{ IOException -> 0x0108, all -> 0x00cd }
            java.util.Map<java.lang.String, java.lang.String> r4 = r13.zze     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            if (r4 == 0) goto L_0x003d
            java.util.Set r4 = r4.entrySet()     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
        L_0x0021:
            boolean r5 = r4.hasNext()     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            if (r5 == 0) goto L_0x003d
            java.lang.Object r5 = r4.next()     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            java.lang.Object r6 = r5.getKey()     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            java.lang.Object r5 = r5.getValue()     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            r3.addRequestProperty(r6, r5)     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            goto L_0x0021
        L_0x003d:
            byte[] r4 = r13.zzb     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            if (r4 == 0) goto L_0x008c
            com.google.android.gms.measurement.internal.zzfc r4 = r13.zzf     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            com.google.android.gms.measurement.internal.zzks r4 = r4.zzg()     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            byte[] r5 = r13.zzb     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            byte[] r4 = r4.zzc(r5)     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            com.google.android.gms.measurement.internal.zzfc r5 = r13.zzf     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            com.google.android.gms.measurement.internal.zzez r5 = r5.zzr()     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            com.google.android.gms.measurement.internal.zzfb r5 = r5.zzx()     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            java.lang.String r6 = "Uploading data. size"
            int r7 = r4.length     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            r5.zza(r6, r7)     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            r5 = 1
            r3.setDoOutput(r5)     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            java.lang.String r5 = "Content-Encoding"
            java.lang.String r6 = "gzip"
            r3.addRequestProperty(r5, r6)     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            int r5 = r4.length     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            r3.setFixedLengthStreamingMode(r5)     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            r3.connect()     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            java.io.OutputStream r5 = r3.getOutputStream()     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            r5.write(r4)     // Catch:{ IOException -> 0x0085, all -> 0x007e }
            r5.close()     // Catch:{ IOException -> 0x0085, all -> 0x007e }
            goto L_0x008c
        L_0x007e:
            r4 = move-exception
            r10 = r1
            r7 = r2
            r2 = r4
            r1 = r5
            goto L_0x00d2
        L_0x0085:
            r4 = move-exception
            r10 = r1
            r7 = r2
            r8 = r4
            r1 = r5
            goto L_0x010d
        L_0x008c:
            int r8 = r3.getResponseCode()     // Catch:{ IOException -> 0x00ca, all -> 0x00c7 }
            java.util.Map r11 = r3.getHeaderFields()     // Catch:{ IOException -> 0x00c3, all -> 0x00be }
            com.google.android.gms.measurement.internal.zzfc r2 = r13.zzf     // Catch:{ IOException -> 0x00ba, all -> 0x00b5 }
            byte[] r10 = com.google.android.gms.measurement.internal.zzfc.zza((java.net.HttpURLConnection) r3)     // Catch:{ IOException -> 0x00ba, all -> 0x00b5 }
            if (r3 == 0) goto L_0x009f
            r3.disconnect()
        L_0x009f:
            com.google.android.gms.measurement.internal.zzfc r0 = r13.zzf
            com.google.android.gms.measurement.internal.zzfw r0 = r0.zzq()
            com.google.android.gms.measurement.internal.zzfh r1 = new com.google.android.gms.measurement.internal.zzfh
            java.lang.String r6 = r13.zzd
            com.google.android.gms.measurement.internal.zzfe r7 = r13.zzc
            r9 = 0
            r12 = 0
            r5 = r1
            r5.<init>(r6, r7, r8, r9, r10, r11)
            r0.zza((java.lang.Runnable) r1)
            return
        L_0x00b5:
            r4 = move-exception
            r2 = r4
            r7 = r8
            r10 = r11
            goto L_0x00d2
        L_0x00ba:
            r4 = move-exception
            r7 = r8
            r10 = r11
            goto L_0x010c
        L_0x00be:
            r4 = move-exception
            r10 = r1
            r2 = r4
            r7 = r8
            goto L_0x00d2
        L_0x00c3:
            r4 = move-exception
            r10 = r1
            r7 = r8
            goto L_0x010c
        L_0x00c7:
            r4 = move-exception
            r10 = r1
            goto L_0x00d0
        L_0x00ca:
            r4 = move-exception
            r10 = r1
            goto L_0x010b
        L_0x00cd:
            r4 = move-exception
            r3 = r1
            r10 = r3
        L_0x00d0:
            r7 = r2
            r2 = r4
        L_0x00d2:
            if (r1 == 0) goto L_0x00ec
            r1.close()     // Catch:{ IOException -> 0x00d8 }
            goto L_0x00ec
        L_0x00d8:
            r1 = move-exception
            com.google.android.gms.measurement.internal.zzfc r4 = r13.zzf
            com.google.android.gms.measurement.internal.zzez r4 = r4.zzr()
            com.google.android.gms.measurement.internal.zzfb r4 = r4.zzf()
            java.lang.String r5 = r13.zzd
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r5)
            r4.zza(r0, r5, r1)
        L_0x00ec:
            if (r3 == 0) goto L_0x00f1
            r3.disconnect()
        L_0x00f1:
            com.google.android.gms.measurement.internal.zzfc r0 = r13.zzf
            com.google.android.gms.measurement.internal.zzfw r0 = r0.zzq()
            com.google.android.gms.measurement.internal.zzfh r1 = new com.google.android.gms.measurement.internal.zzfh
            java.lang.String r5 = r13.zzd
            com.google.android.gms.measurement.internal.zzfe r6 = r13.zzc
            r8 = 0
            r9 = 0
            r11 = 0
            r4 = r1
            r4.<init>(r5, r6, r7, r8, r9, r10)
            r0.zza((java.lang.Runnable) r1)
            throw r2
        L_0x0108:
            r4 = move-exception
            r3 = r1
            r10 = r3
        L_0x010b:
            r7 = r2
        L_0x010c:
            r8 = r4
        L_0x010d:
            if (r1 == 0) goto L_0x0127
            r1.close()     // Catch:{ IOException -> 0x0113 }
            goto L_0x0127
        L_0x0113:
            r1 = move-exception
            com.google.android.gms.measurement.internal.zzfc r2 = r13.zzf
            com.google.android.gms.measurement.internal.zzez r2 = r2.zzr()
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzf()
            java.lang.String r4 = r13.zzd
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r4)
            r2.zza(r0, r4, r1)
        L_0x0127:
            if (r3 == 0) goto L_0x012c
            r3.disconnect()
        L_0x012c:
            com.google.android.gms.measurement.internal.zzfc r0 = r13.zzf
            com.google.android.gms.measurement.internal.zzfw r0 = r0.zzq()
            com.google.android.gms.measurement.internal.zzfh r1 = new com.google.android.gms.measurement.internal.zzfh
            java.lang.String r5 = r13.zzd
            com.google.android.gms.measurement.internal.zzfe r6 = r13.zzc
            r9 = 0
            r11 = 0
            r4 = r1
            r4.<init>(r5, r6, r7, r8, r9, r10)
            r0.zza((java.lang.Runnable) r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfg.run():void");
    }
}
