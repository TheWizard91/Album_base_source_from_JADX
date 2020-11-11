package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.ProcessUtils;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzmd;
import com.google.android.gms.internal.measurement.zzor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public final class zzy extends zzgx {
    private Boolean zza;
    private zzaa zzb = zzab.zza;
    private Boolean zzc;

    zzy(zzgd zzgd) {
        super(zzgd);
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzaa zzaa) {
        this.zzb = zzaa;
    }

    public final int zze() {
        if (!zzmd.zzb() || !zzt().zzd((String) null, zzaq.zzck) || zzp().zzj() < 201500) {
            return 25;
        }
        return 100;
    }

    public final int zza(String str) {
        return zza(str, zzaq.zzah, 25, 100);
    }

    /* access modifiers changed from: package-private */
    public final int zzb(String str) {
        if (!zzmd.zzb() || !zzd((String) null, zzaq.zzcj)) {
            return 500;
        }
        return zza(str, zzaq.zzag, 500, 2000);
    }

    public final int zzc(String str) {
        return zzb(str, zzaq.zzn);
    }

    /* access modifiers changed from: package-private */
    public final int zzd(String str) {
        if (!zzmd.zzb() || !zzd((String) null, zzaq.zzcj)) {
            return 25;
        }
        return zza(str, zzaq.zzaf, 25, 100);
    }

    public final long zzf() {
        zzu();
        return 31000;
    }

    public final boolean zzg() {
        if (this.zzc == null) {
            synchronized (this) {
                if (this.zzc == null) {
                    ApplicationInfo applicationInfo = zzn().getApplicationInfo();
                    String myProcessName = ProcessUtils.getMyProcessName();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        this.zzc = Boolean.valueOf(str != null && str.equals(myProcessName));
                    }
                    if (this.zzc == null) {
                        this.zzc = Boolean.TRUE;
                        zzr().zzf().zza("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzc.booleanValue();
    }

    public final long zza(String str, zzeo<Long> zzeo) {
        if (str == null) {
            return zzeo.zza(null).longValue();
        }
        String zza2 = this.zzb.zza(str, zzeo.zza());
        if (TextUtils.isEmpty(zza2)) {
            return zzeo.zza(null).longValue();
        }
        try {
            return zzeo.zza(Long.valueOf(Long.parseLong(zza2))).longValue();
        } catch (NumberFormatException e) {
            return zzeo.zza(null).longValue();
        }
    }

    public final int zzb(String str, zzeo<Integer> zzeo) {
        if (str == null) {
            return zzeo.zza(null).intValue();
        }
        String zza2 = this.zzb.zza(str, zzeo.zza());
        if (TextUtils.isEmpty(zza2)) {
            return zzeo.zza(null).intValue();
        }
        try {
            return zzeo.zza(Integer.valueOf(Integer.parseInt(zza2))).intValue();
        } catch (NumberFormatException e) {
            return zzeo.zza(null).intValue();
        }
    }

    private final int zza(String str, zzeo<Integer> zzeo, int i, int i2) {
        return Math.max(Math.min(zzb(str, zzeo), i2), i);
    }

    public final double zzc(String str, zzeo<Double> zzeo) {
        if (str == null) {
            return zzeo.zza(null).doubleValue();
        }
        String zza2 = this.zzb.zza(str, zzeo.zza());
        if (TextUtils.isEmpty(zza2)) {
            return zzeo.zza(null).doubleValue();
        }
        try {
            return zzeo.zza(Double.valueOf(Double.parseDouble(zza2))).doubleValue();
        } catch (NumberFormatException e) {
            return zzeo.zza(null).doubleValue();
        }
    }

    public final boolean zzd(String str, zzeo<Boolean> zzeo) {
        if (str == null) {
            return zzeo.zza(null).booleanValue();
        }
        String zza2 = this.zzb.zza(str, zzeo.zza());
        if (TextUtils.isEmpty(zza2)) {
            return zzeo.zza(null).booleanValue();
        }
        return zzeo.zza(Boolean.valueOf(Boolean.parseBoolean(zza2))).booleanValue();
    }

    public final boolean zze(String str, zzeo<Boolean> zzeo) {
        return zzd(str, zzeo);
    }

    public final boolean zza(zzeo<Boolean> zzeo) {
        return zzd((String) null, zzeo);
    }

    private final Bundle zzz() {
        try {
            if (zzn().getPackageManager() == null) {
                zzr().zzf().zza("Failed to load metadata: PackageManager is null");
                return null;
            }
            ApplicationInfo applicationInfo = Wrappers.packageManager(zzn()).getApplicationInfo(zzn().getPackageName(), 128);
            if (applicationInfo != null) {
                return applicationInfo.metaData;
            }
            zzr().zzf().zza("Failed to load metadata: ApplicationInfo is null");
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            zzr().zzf().zza("Failed to load metadata: Package name not found", e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public final Boolean zze(String str) {
        Preconditions.checkNotEmpty(str);
        Bundle zzz = zzz();
        if (zzz == null) {
            zzr().zzf().zza("Failed to load metadata: Metadata bundle is null");
            return null;
        } else if (!zzz.containsKey(str)) {
            return null;
        } else {
            return Boolean.valueOf(zzz.getBoolean(str));
        }
    }

    /* access modifiers changed from: package-private */
    public final List<String> zzf(String str) {
        Integer num;
        Preconditions.checkNotEmpty(str);
        Bundle zzz = zzz();
        if (zzz == null) {
            zzr().zzf().zza("Failed to load metadata: Metadata bundle is null");
            num = null;
        } else if (!zzz.containsKey(str)) {
            num = null;
        } else {
            num = Integer.valueOf(zzz.getInt(str));
        }
        if (num == null) {
            return null;
        }
        try {
            String[] stringArray = zzn().getResources().getStringArray(num.intValue());
            if (stringArray == null) {
                return null;
            }
            return Arrays.asList(stringArray);
        } catch (Resources.NotFoundException e) {
            zzr().zzf().zza("Failed to load string array from metadata: resource not found", e);
            return null;
        }
    }

    public final boolean zzh() {
        zzu();
        Boolean zze = zze("firebase_analytics_collection_deactivated");
        return zze != null && zze.booleanValue();
    }

    public final Boolean zzi() {
        zzb();
        Boolean zze = zze("google_analytics_adid_collection_enabled");
        return Boolean.valueOf(zze == null || zze.booleanValue());
    }

    public final Boolean zzj() {
        zzb();
        boolean z = true;
        if (!zzor.zzb() || !zza(zzaq.zzcb)) {
            return true;
        }
        Boolean zze = zze("google_analytics_automatic_screen_reporting_enabled");
        if (zze != null && !zze.booleanValue()) {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    public static long zzk() {
        return zzaq.zzac.zza(null).longValue();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x002d, code lost:
        if (android.text.TextUtils.isEmpty(r1) != false) goto L_0x002f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String zza(com.google.android.gms.measurement.internal.zzf r6) {
        /*
            r5 = this;
            android.net.Uri$Builder r0 = new android.net.Uri$Builder
            r0.<init>()
            java.lang.String r1 = r6.zze()
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 == 0) goto L_0x0033
            boolean r1 = com.google.android.gms.internal.measurement.zzoe.zzb()
            if (r1 == 0) goto L_0x002f
            com.google.android.gms.measurement.internal.zzy r1 = r5.zzt()
            java.lang.String r2 = r6.zzc()
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r3 = com.google.android.gms.measurement.internal.zzaq.zzbn
            boolean r1 = r1.zzd(r2, r3)
            if (r1 == 0) goto L_0x002f
            java.lang.String r1 = r6.zzg()
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 == 0) goto L_0x0033
        L_0x002f:
            java.lang.String r1 = r6.zzf()
        L_0x0033:
            com.google.android.gms.measurement.internal.zzeo<java.lang.String> r2 = com.google.android.gms.measurement.internal.zzaq.zzd
            r3 = 0
            java.lang.Object r2 = r2.zza(r3)
            java.lang.String r2 = (java.lang.String) r2
            android.net.Uri$Builder r2 = r0.scheme(r2)
            com.google.android.gms.measurement.internal.zzeo<java.lang.String> r4 = com.google.android.gms.measurement.internal.zzaq.zze
            java.lang.Object r3 = r4.zza(r3)
            java.lang.String r3 = (java.lang.String) r3
            android.net.Uri$Builder r2 = r2.encodedAuthority(r3)
            java.lang.String r3 = "config/app/"
            java.lang.String r1 = java.lang.String.valueOf(r1)
            int r4 = r1.length()
            if (r4 == 0) goto L_0x005d
            java.lang.String r1 = r3.concat(r1)
            goto L_0x0062
        L_0x005d:
            java.lang.String r1 = new java.lang.String
            r1.<init>(r3)
        L_0x0062:
            android.net.Uri$Builder r1 = r2.path(r1)
            java.lang.String r6 = r6.zzd()
            java.lang.String r2 = "app_instance_id"
            android.net.Uri$Builder r6 = r1.appendQueryParameter(r2, r6)
            java.lang.String r1 = "platform"
            java.lang.String r2 = "android"
            android.net.Uri$Builder r6 = r6.appendQueryParameter(r1, r2)
            long r1 = r5.zzf()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            java.lang.String r2 = "gmp_version"
            r6.appendQueryParameter(r2, r1)
            android.net.Uri r6 = r0.build()
            java.lang.String r6 = r6.toString()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzy.zza(com.google.android.gms.measurement.internal.zzf):java.lang.String");
    }

    public static long zzv() {
        return zzaq.zzc.zza(null).longValue();
    }

    public final String zzw() {
        return zza("debug.firebase.analytics.app", "");
    }

    public final String zzx() {
        return zza("debug.deferred.deeplink", "");
    }

    private final String zza(String str, String str2) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class, String.class}).invoke((Object) null, new Object[]{str, str2});
        } catch (ClassNotFoundException e) {
            zzr().zzf().zza("Could not find SystemProperties class", e);
            return str2;
        } catch (NoSuchMethodException e2) {
            zzr().zzf().zza("Could not find SystemProperties.get() method", e2);
            return str2;
        } catch (IllegalAccessException e3) {
            zzr().zzf().zza("Could not access SystemProperties.get()", e3);
            return str2;
        } catch (InvocationTargetException e4) {
            zzr().zzf().zza("SystemProperties.get() threw an exception", e4);
            return str2;
        }
    }

    public final boolean zzg(String str) {
        return "1".equals(this.zzb.zza(str, "gaia_collection_enabled"));
    }

    public final boolean zzh(String str) {
        return "1".equals(this.zzb.zza(str, "measurement.event_sampling_enabled"));
    }

    /* access modifiers changed from: package-private */
    public final boolean zzi(String str) {
        return zzd(str, zzaq.zzaj);
    }

    /* access modifiers changed from: package-private */
    public final String zzj(String str) {
        zzeo<String> zzeo = zzaq.zzak;
        if (str == null) {
            return zzeo.zza(null);
        }
        return zzeo.zza(this.zzb.zza(str, zzeo.zza()));
    }

    /* access modifiers changed from: package-private */
    public final boolean zzy() {
        if (this.zza == null) {
            Boolean zze = zze("app_measurement_lite");
            this.zza = zze;
            if (zze == null) {
                this.zza = false;
            }
        }
        if (this.zza.booleanValue() || !this.zzy.zzt()) {
            return true;
        }
        return false;
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    public final /* bridge */ /* synthetic */ void zzd() {
        super.zzd();
    }

    public final /* bridge */ /* synthetic */ zzai zzl() {
        return super.zzl();
    }

    public final /* bridge */ /* synthetic */ Clock zzm() {
        return super.zzm();
    }

    public final /* bridge */ /* synthetic */ Context zzn() {
        return super.zzn();
    }

    public final /* bridge */ /* synthetic */ zzex zzo() {
        return super.zzo();
    }

    public final /* bridge */ /* synthetic */ zzkw zzp() {
        return super.zzp();
    }

    public final /* bridge */ /* synthetic */ zzfw zzq() {
        return super.zzq();
    }

    public final /* bridge */ /* synthetic */ zzez zzr() {
        return super.zzr();
    }

    public final /* bridge */ /* synthetic */ zzfl zzs() {
        return super.zzs();
    }

    public final /* bridge */ /* synthetic */ zzy zzt() {
        return super.zzt();
    }

    public final /* bridge */ /* synthetic */ zzx zzu() {
        return super.zzu();
    }
}
