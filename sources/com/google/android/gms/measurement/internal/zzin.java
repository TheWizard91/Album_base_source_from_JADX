package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.measurement.zznh;
import com.google.android.gms.internal.measurement.zznt;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlinx.coroutines.DebugKt;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public final class zzin extends zzg {
    protected zzik zza;
    private volatile zzik zzb;
    private zzik zzc;
    private final Map<Activity, zzik> zzd = new ConcurrentHashMap();
    private Activity zze;
    private volatile boolean zzf;
    private volatile zzik zzg;
    /* access modifiers changed from: private */
    public zzik zzh;
    private boolean zzi;
    private final Object zzj = new Object();
    private zzik zzk;
    private String zzl;

    public zzin(zzgd zzgd) {
        super(zzgd);
    }

    /* access modifiers changed from: protected */
    public final boolean zzz() {
        return false;
    }

    public final zzik zza(boolean z) {
        zzw();
        zzd();
        if (!zzt().zza(zzaq.zzcc) || !z) {
            return this.zza;
        }
        zzik zzik = this.zza;
        return zzik != null ? zzik : this.zzh;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00dc, code lost:
        r1 = zzr().zzx();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00e6, code lost:
        if (r10 != null) goto L_0x00eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00e8, code lost:
        r3 = "null";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00eb, code lost:
        r3 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00ec, code lost:
        if (r11 != null) goto L_0x00f1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00ee, code lost:
        r4 = "null";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00f1, code lost:
        r4 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00f2, code lost:
        r1.zza("Logging screen view with name, class", r3, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00f7, code lost:
        if (r8.zzb != null) goto L_0x00fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00f9, code lost:
        r1 = r8.zzc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00fc, code lost:
        r1 = r8.zzb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00fe, code lost:
        r5 = r1;
        r9 = new com.google.android.gms.measurement.internal.zzik(r10, r11, zzp().zzg(), true, r19);
        r8.zzb = r9;
        r8.zzc = r5;
        r8.zzg = r9;
        zzq().zza((java.lang.Runnable) new com.google.android.gms.measurement.internal.zzim(r17, r18, r9, r5, zzm().elapsedRealtime()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x012f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(android.os.Bundle r18, long r19) {
        /*
            r17 = this;
            r8 = r17
            r0 = r18
            com.google.android.gms.measurement.internal.zzy r1 = r17.zzt()
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r2 = com.google.android.gms.measurement.internal.zzaq.zzcc
            boolean r1 = r1.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r2)
            if (r1 != 0) goto L_0x001e
            com.google.android.gms.measurement.internal.zzez r0 = r17.zzr()
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzk()
            java.lang.String r1 = "Manual screen reporting is disabled."
            r0.zza(r1)
            return
        L_0x001e:
            java.lang.Object r1 = r8.zzj
            monitor-enter(r1)
            boolean r2 = r8.zzi     // Catch:{ all -> 0x0130 }
            if (r2 != 0) goto L_0x0036
            com.google.android.gms.measurement.internal.zzez r0 = r17.zzr()     // Catch:{ all -> 0x0130 }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzk()     // Catch:{ all -> 0x0130 }
            java.lang.String r2 = "Cannot log screen view event when the app is in the background."
            r0.zza(r2)     // Catch:{ all -> 0x0130 }
            monitor-exit(r1)     // Catch:{ all -> 0x0130 }
            return
        L_0x0036:
            r2 = 0
            if (r0 == 0) goto L_0x0094
            java.lang.String r2 = "screen_name"
            java.lang.String r2 = r0.getString(r2)     // Catch:{ all -> 0x0130 }
            r3 = 100
            if (r2 == 0) goto L_0x0066
            int r4 = r2.length()     // Catch:{ all -> 0x0130 }
            if (r4 <= 0) goto L_0x004f
            int r4 = r2.length()     // Catch:{ all -> 0x0130 }
            if (r4 <= r3) goto L_0x0066
        L_0x004f:
            com.google.android.gms.measurement.internal.zzez r0 = r17.zzr()     // Catch:{ all -> 0x0130 }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzk()     // Catch:{ all -> 0x0130 }
            java.lang.String r3 = "Invalid screen name length for screen view. Length"
            int r2 = r2.length()     // Catch:{ all -> 0x0130 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x0130 }
            r0.zza(r3, r2)     // Catch:{ all -> 0x0130 }
            monitor-exit(r1)     // Catch:{ all -> 0x0130 }
            return
        L_0x0066:
            java.lang.String r4 = "screen_class"
            java.lang.String r4 = r0.getString(r4)     // Catch:{ all -> 0x0130 }
            if (r4 == 0) goto L_0x0091
            int r5 = r4.length()     // Catch:{ all -> 0x0130 }
            if (r5 <= 0) goto L_0x007a
            int r5 = r4.length()     // Catch:{ all -> 0x0130 }
            if (r5 <= r3) goto L_0x0091
        L_0x007a:
            com.google.android.gms.measurement.internal.zzez r0 = r17.zzr()     // Catch:{ all -> 0x0130 }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzk()     // Catch:{ all -> 0x0130 }
            java.lang.String r2 = "Invalid screen class length for screen view. Length"
            int r3 = r4.length()     // Catch:{ all -> 0x0130 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0130 }
            r0.zza(r2, r3)     // Catch:{ all -> 0x0130 }
            monitor-exit(r1)     // Catch:{ all -> 0x0130 }
            return
        L_0x0091:
            r10 = r2
            r2 = r4
            goto L_0x0095
        L_0x0094:
            r10 = r2
        L_0x0095:
            if (r2 != 0) goto L_0x00ac
            android.app.Activity r2 = r8.zze     // Catch:{ all -> 0x0130 }
            if (r2 == 0) goto L_0x00a8
            java.lang.Class r2 = r2.getClass()     // Catch:{ all -> 0x0130 }
            java.lang.String r2 = r2.getCanonicalName()     // Catch:{ all -> 0x0130 }
            java.lang.String r2 = zza((java.lang.String) r2)     // Catch:{ all -> 0x0130 }
            goto L_0x00aa
        L_0x00a8:
            java.lang.String r2 = "Activity"
        L_0x00aa:
            r11 = r2
            goto L_0x00ad
        L_0x00ac:
            r11 = r2
        L_0x00ad:
            boolean r2 = r8.zzf     // Catch:{ all -> 0x0130 }
            if (r2 == 0) goto L_0x00db
            com.google.android.gms.measurement.internal.zzik r2 = r8.zzb     // Catch:{ all -> 0x0130 }
            if (r2 == 0) goto L_0x00db
            r2 = 0
            r8.zzf = r2     // Catch:{ all -> 0x0130 }
            com.google.android.gms.measurement.internal.zzik r2 = r8.zzb     // Catch:{ all -> 0x0130 }
            java.lang.String r2 = r2.zzb     // Catch:{ all -> 0x0130 }
            boolean r2 = com.google.android.gms.measurement.internal.zzkw.zzc((java.lang.String) r2, (java.lang.String) r11)     // Catch:{ all -> 0x0130 }
            com.google.android.gms.measurement.internal.zzik r3 = r8.zzb     // Catch:{ all -> 0x0130 }
            java.lang.String r3 = r3.zza     // Catch:{ all -> 0x0130 }
            boolean r3 = com.google.android.gms.measurement.internal.zzkw.zzc((java.lang.String) r3, (java.lang.String) r10)     // Catch:{ all -> 0x0130 }
            if (r2 == 0) goto L_0x00db
            if (r3 == 0) goto L_0x00db
            com.google.android.gms.measurement.internal.zzez r0 = r17.zzr()     // Catch:{ all -> 0x0130 }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzk()     // Catch:{ all -> 0x0130 }
            java.lang.String r2 = "Ignoring call to log screen view event with duplicate parameters."
            r0.zza(r2)     // Catch:{ all -> 0x0130 }
            monitor-exit(r1)     // Catch:{ all -> 0x0130 }
            return
        L_0x00db:
            monitor-exit(r1)     // Catch:{ all -> 0x0130 }
            com.google.android.gms.measurement.internal.zzez r1 = r17.zzr()
            com.google.android.gms.measurement.internal.zzfb r1 = r1.zzx()
            java.lang.String r2 = "Logging screen view with name, class"
            if (r10 != 0) goto L_0x00eb
            java.lang.String r3 = "null"
            goto L_0x00ec
        L_0x00eb:
            r3 = r10
        L_0x00ec:
            if (r11 != 0) goto L_0x00f1
            java.lang.String r4 = "null"
            goto L_0x00f2
        L_0x00f1:
            r4 = r11
        L_0x00f2:
            r1.zza(r2, r3, r4)
            com.google.android.gms.measurement.internal.zzik r1 = r8.zzb
            if (r1 != 0) goto L_0x00fc
            com.google.android.gms.measurement.internal.zzik r1 = r8.zzc
            goto L_0x00fe
        L_0x00fc:
            com.google.android.gms.measurement.internal.zzik r1 = r8.zzb
        L_0x00fe:
            r5 = r1
            com.google.android.gms.measurement.internal.zzik r4 = new com.google.android.gms.measurement.internal.zzik
            com.google.android.gms.measurement.internal.zzkw r1 = r17.zzp()
            long r12 = r1.zzg()
            r14 = 1
            r9 = r4
            r15 = r19
            r9.<init>(r10, r11, r12, r14, r15)
            r8.zzb = r4
            r8.zzc = r5
            r8.zzg = r4
            com.google.android.gms.common.util.Clock r1 = r17.zzm()
            long r6 = r1.elapsedRealtime()
            com.google.android.gms.measurement.internal.zzfw r9 = r17.zzq()
            com.google.android.gms.measurement.internal.zzim r10 = new com.google.android.gms.measurement.internal.zzim
            r1 = r10
            r2 = r17
            r3 = r18
            r1.<init>(r2, r3, r4, r5, r6)
            r9.zza((java.lang.Runnable) r10)
            return
        L_0x0130:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0130 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzin.zza(android.os.Bundle, long):void");
    }

    /* access modifiers changed from: private */
    public final void zza(Bundle bundle, zzik zzik, zzik zzik2, long j) {
        if (bundle != null) {
            bundle.remove("screen_name");
            bundle.remove("screen_class");
        }
        zza(zzik, zzik2, j, true, zzp().zza((String) null, "screen_view", bundle, (List<String>) null, true, true));
    }

    public final void zza(Activity activity, String str, String str2) {
        if (!zzt().zzj().booleanValue()) {
            zzr().zzk().zza("setCurrentScreen cannot be called while screen reporting is disabled.");
        } else if (this.zzb == null) {
            zzr().zzk().zza("setCurrentScreen cannot be called while no activity active");
        } else if (this.zzd.get(activity) == null) {
            zzr().zzk().zza("setCurrentScreen must be called with an activity in the activity lifecycle");
        } else {
            if (str2 == null) {
                str2 = zza(activity.getClass().getCanonicalName());
            }
            boolean zzc2 = zzkw.zzc(this.zzb.zzb, str2);
            boolean zzc3 = zzkw.zzc(this.zzb.zza, str);
            if (zzc2 && zzc3) {
                zzr().zzk().zza("setCurrentScreen cannot be called with the same class and name");
            } else if (str != null && (str.length() <= 0 || str.length() > 100)) {
                zzr().zzk().zza("Invalid screen name length in setCurrentScreen. Length", Integer.valueOf(str.length()));
            } else if (str2 == null || (str2.length() > 0 && str2.length() <= 100)) {
                zzr().zzx().zza("Setting current screen to name, class", str == null ? "null" : str, str2);
                zzik zzik = new zzik(str, str2, zzp().zzg());
                this.zzd.put(activity, zzik);
                zza(activity, zzik, true);
            } else {
                zzr().zzk().zza("Invalid class name length in setCurrentScreen. Length", Integer.valueOf(str2.length()));
            }
        }
    }

    public final zzik zzab() {
        zzb();
        return this.zzb;
    }

    private final void zza(Activity activity, zzik zzik, boolean z) {
        zzik zzik2;
        zzik zzik3 = zzik;
        zzik zzik4 = this.zzb == null ? this.zzc : this.zzb;
        if (zzik3.zzb == null) {
            zzik2 = new zzik(zzik3.zza, activity != null ? zza(activity.getClass().getCanonicalName()) : null, zzik3.zzc, zzik3.zze, zzik3.zzf);
        } else {
            zzik2 = zzik3;
        }
        this.zzc = this.zzb;
        this.zzb = zzik2;
        zzq().zza((Runnable) new zzip(this, zzik2, zzik4, zzm().elapsedRealtime(), z));
    }

    /* access modifiers changed from: private */
    public final void zza(zzik zzik, zzik zzik2, long j, boolean z, Bundle bundle) {
        boolean z2;
        Bundle bundle2;
        String str;
        long j2;
        long j3;
        zzik zzik3;
        zzd();
        boolean z3 = false;
        if (zzt().zza(zzaq.zzat)) {
            z2 = z && this.zza != null;
            if (z2) {
                zza(this.zza, true, j);
            }
        } else {
            if (z && (zzik3 = this.zza) != null) {
                zza(zzik3, true, j);
            }
            z2 = false;
        }
        if (zzik2 == null || zzik2.zzc != zzik.zzc || !zzkw.zzc(zzik2.zzb, zzik.zzb) || !zzkw.zzc(zzik2.zza, zzik.zza)) {
            z3 = true;
        }
        if (z3) {
            Bundle bundle3 = new Bundle();
            if (zzt().zza(zzaq.zzcc)) {
                bundle2 = bundle != null ? new Bundle(bundle) : new Bundle();
            } else {
                bundle2 = bundle3;
            }
            zza(zzik, bundle2, true);
            if (zzik2 != null) {
                if (zzik2.zza != null) {
                    bundle2.putString("_pn", zzik2.zza);
                }
                if (zzik2.zzb != null) {
                    bundle2.putString("_pc", zzik2.zzb);
                }
                bundle2.putLong("_pi", zzik2.zzc);
            }
            if (zzt().zza(zzaq.zzat) && z2) {
                if (!zznt.zzb() || !zzt().zza(zzaq.zzav) || !zznh.zzb() || !zzt().zza(zzaq.zzbz)) {
                    j3 = zzk().zzb.zzb();
                } else {
                    j3 = zzk().zza(j);
                }
                if (j3 > 0) {
                    zzp().zza(bundle2, j3);
                }
            }
            boolean zza2 = zzt().zza(zzaq.zzcc);
            String str2 = DebugKt.DEBUG_PROPERTY_VALUE_AUTO;
            if (zza2) {
                if (!zzt().zzj().booleanValue()) {
                    bundle2.putLong("_mt", 1);
                }
                if (zzik.zze) {
                    str2 = "app";
                }
                str = str2;
            } else {
                str = str2;
            }
            if (zzt().zza(zzaq.zzcc)) {
                long currentTimeMillis = zzm().currentTimeMillis();
                if (!zzik.zze || zzik.zzf == 0) {
                    j2 = currentTimeMillis;
                } else {
                    j2 = zzik.zzf;
                }
                zzf().zza(str, "_vs", j2, bundle2);
            } else {
                zzf().zzb(str, "_vs", bundle2);
            }
        }
        this.zza = zzik;
        if (zzt().zza(zzaq.zzcc) && zzik.zze) {
            this.zzh = zzik;
        }
        zzh().zza(zzik);
    }

    /* access modifiers changed from: private */
    public final void zza(zzik zzik, boolean z, long j) {
        zze().zza(zzm().elapsedRealtime());
        if (zzk().zza(zzik != null && zzik.zzd, z, j) && zzik != null) {
            zzik.zzd = false;
        }
    }

    public static void zza(zzik zzik, Bundle bundle, boolean z) {
        if (bundle != null && zzik != null && (!bundle.containsKey("_sc") || z)) {
            if (zzik.zza != null) {
                bundle.putString("_sn", zzik.zza);
            } else {
                bundle.remove("_sn");
            }
            if (zzik.zzb != null) {
                bundle.putString("_sc", zzik.zzb);
            } else {
                bundle.remove("_sc");
            }
            bundle.putLong("_si", zzik.zzc);
        } else if (bundle != null && zzik == null && z) {
            bundle.remove("_sn");
            bundle.remove("_sc");
            bundle.remove("_si");
        }
    }

    public final void zza(String str, zzik zzik) {
        zzd();
        synchronized (this) {
            String str2 = this.zzl;
            if (str2 == null || str2.equals(str) || zzik != null) {
                this.zzl = str;
                this.zzk = zzik;
            }
        }
    }

    private static String zza(String str) {
        String str2;
        String[] split = str.split("\\.");
        if (split.length > 0) {
            str2 = split[split.length - 1];
        } else {
            str2 = "";
        }
        if (str2.length() > 100) {
            return str2.substring(0, 100);
        }
        return str2;
    }

    private final zzik zzd(Activity activity) {
        Preconditions.checkNotNull(activity);
        zzik zzik = this.zzd.get(activity);
        if (zzik == null) {
            zzik zzik2 = new zzik((String) null, zza(activity.getClass().getCanonicalName()), zzp().zzg());
            this.zzd.put(activity, zzik2);
            zzik = zzik2;
        }
        return (zzt().zza(zzaq.zzcc) && this.zzg != null) ? this.zzg : zzik;
    }

    public final void zza(Activity activity, Bundle bundle) {
        Bundle bundle2;
        if (zzt().zzj().booleanValue() && bundle != null && (bundle2 = bundle.getBundle("com.google.app_measurement.screen_service")) != null) {
            this.zzd.put(activity, new zzik(bundle2.getString(AppMeasurementSdk.ConditionalUserProperty.NAME), bundle2.getString("referrer_name"), bundle2.getLong("id")));
        }
    }

    public final void zza(Activity activity) {
        if (zzt().zza(zzaq.zzcc)) {
            synchronized (this.zzj) {
                this.zzi = true;
                if (activity != this.zze) {
                    synchronized (this.zzj) {
                        this.zze = activity;
                        this.zzf = false;
                    }
                    if (zzt().zza(zzaq.zzcb) && zzt().zzj().booleanValue()) {
                        this.zzg = null;
                        zzq().zza((Runnable) new zzit(this));
                    }
                }
            }
        }
        if (!zzt().zza(zzaq.zzcb) || zzt().zzj().booleanValue()) {
            zza(activity, zzd(activity), false);
            zza zze2 = zze();
            zze2.zzq().zza((Runnable) new zze(zze2, zze2.zzm().elapsedRealtime()));
            return;
        }
        this.zzb = this.zzg;
        zzq().zza((Runnable) new zzio(this));
    }

    public final void zzb(Activity activity) {
        if (zzt().zza(zzaq.zzcc)) {
            synchronized (this.zzj) {
                this.zzi = false;
                this.zzf = true;
            }
        }
        long elapsedRealtime = zzm().elapsedRealtime();
        if (!zzt().zza(zzaq.zzcb) || zzt().zzj().booleanValue()) {
            zzik zzd2 = zzd(activity);
            this.zzc = this.zzb;
            this.zzb = null;
            zzq().zza((Runnable) new zziq(this, zzd2, elapsedRealtime));
            return;
        }
        this.zzb = null;
        zzq().zza((Runnable) new zzir(this, elapsedRealtime));
    }

    public final void zzb(Activity activity, Bundle bundle) {
        zzik zzik;
        if (zzt().zzj().booleanValue() && bundle != null && (zzik = this.zzd.get(activity)) != null) {
            Bundle bundle2 = new Bundle();
            bundle2.putLong("id", zzik.zzc);
            bundle2.putString(AppMeasurementSdk.ConditionalUserProperty.NAME, zzik.zza);
            bundle2.putString("referrer_name", zzik.zzb);
            bundle.putBundle("com.google.app_measurement.screen_service", bundle2);
        }
    }

    public final void zzc(Activity activity) {
        synchronized (this.zzj) {
            if (activity == this.zze) {
                this.zze = null;
            }
        }
        if (zzt().zzj().booleanValue()) {
            this.zzd.remove(activity);
        }
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

    public final /* bridge */ /* synthetic */ zza zze() {
        return super.zze();
    }

    public final /* bridge */ /* synthetic */ zzhh zzf() {
        return super.zzf();
    }

    public final /* bridge */ /* synthetic */ zzes zzg() {
        return super.zzg();
    }

    public final /* bridge */ /* synthetic */ zzis zzh() {
        return super.zzh();
    }

    public final /* bridge */ /* synthetic */ zzin zzi() {
        return super.zzi();
    }

    public final /* bridge */ /* synthetic */ zzev zzj() {
        return super.zzj();
    }

    public final /* bridge */ /* synthetic */ zzjw zzk() {
        return super.zzk();
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
