package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzae;
import com.google.android.gms.internal.measurement.zzca;
import com.google.android.gms.internal.measurement.zzcc;
import com.google.android.gms.internal.measurement.zzib;
import com.google.android.gms.internal.measurement.zznn;
import com.google.android.gms.internal.measurement.zzoe;
import com.google.android.gms.internal.measurement.zzof;
import com.google.common.net.HttpHeaders;
import com.theartofdev.edmodo.cropper.CropImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import kotlinx.coroutines.DebugKt;

/* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
public class zzkk implements zzgz {
    private static volatile zzkk zza;
    private zzfx zzb;
    private zzfc zzc;
    private zzad zzd;
    private zzfj zze;
    private zzkg zzf;
    private zzo zzg;
    private final zzks zzh;
    private zzii zzi;
    private final zzgd zzj;
    private boolean zzk;
    private boolean zzl;
    private long zzm;
    private List<Runnable> zzn;
    private int zzo;
    private int zzp;
    private boolean zzq;
    private boolean zzr;
    private boolean zzs;
    private FileLock zzt;
    private FileChannel zzu;
    private List<Long> zzv;
    private List<Long> zzw;
    private long zzx;

    /* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
    class zza implements zzaf {
        zzcc.zzg zza;
        List<Long> zzb;
        List<zzcc.zzc> zzc;
        private long zzd;

        private zza() {
        }

        public final void zza(zzcc.zzg zzg) {
            Preconditions.checkNotNull(zzg);
            this.zza = zzg;
        }

        public final boolean zza(long j, zzcc.zzc zzc2) {
            Preconditions.checkNotNull(zzc2);
            if (this.zzc == null) {
                this.zzc = new ArrayList();
            }
            if (this.zzb == null) {
                this.zzb = new ArrayList();
            }
            if (this.zzc.size() > 0 && zza(this.zzc.get(0)) != zza(zzc2)) {
                return false;
            }
            long zzbm = this.zzd + ((long) zzc2.zzbm());
            if (zzbm >= ((long) Math.max(0, zzaq.zzh.zza(null).intValue()))) {
                return false;
            }
            this.zzd = zzbm;
            this.zzc.add(zzc2);
            this.zzb.add(Long.valueOf(j));
            if (this.zzc.size() >= Math.max(1, zzaq.zzi.zza(null).intValue())) {
                return false;
            }
            return true;
        }

        private static long zza(zzcc.zzc zzc2) {
            return ((zzc2.zze() / 1000) / 60) / 60;
        }

        /* synthetic */ zza(zzkk zzkk, zzkn zzkn) {
            this();
        }
    }

    public static zzkk zza(Context context) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zza == null) {
            synchronized (zzkk.class) {
                if (zza == null) {
                    zza = new zzkk(new zzkq(context));
                }
            }
        }
        return zza;
    }

    private zzkk(zzkq zzkq) {
        this(zzkq, (zzgd) null);
    }

    private zzkk(zzkq zzkq, zzgd zzgd) {
        this.zzk = false;
        Preconditions.checkNotNull(zzkq);
        zzgd zza2 = zzgd.zza(zzkq.zza, (zzae) null, (Long) null);
        this.zzj = zza2;
        this.zzx = -1;
        zzks zzks = new zzks(this);
        zzks.zzal();
        this.zzh = zzks;
        zzfc zzfc = new zzfc(this);
        zzfc.zzal();
        this.zzc = zzfc;
        zzfx zzfx = new zzfx(this);
        zzfx.zzal();
        this.zzb = zzfx;
        zza2.zzq().zza((Runnable) new zzkn(this, zzkq));
    }

    /* access modifiers changed from: private */
    public final void zza(zzkq zzkq) {
        this.zzj.zzq().zzd();
        zzad zzad = new zzad(this);
        zzad.zzal();
        this.zzd = zzad;
        this.zzj.zzb().zza((zzaa) this.zzb);
        zzo zzo2 = new zzo(this);
        zzo2.zzal();
        this.zzg = zzo2;
        zzii zzii = new zzii(this);
        zzii.zzal();
        this.zzi = zzii;
        zzkg zzkg = new zzkg(this);
        zzkg.zzal();
        this.zzf = zzkg;
        this.zze = new zzfj(this);
        if (this.zzo != this.zzp) {
            this.zzj.zzr().zzf().zza("Not all upload components initialized", Integer.valueOf(this.zzo), Integer.valueOf(this.zzp));
        }
        this.zzk = true;
    }

    /* access modifiers changed from: protected */
    public final void zza() {
        this.zzj.zzq().zzd();
        zze().zzv();
        if (this.zzj.zzc().zzc.zza() == 0) {
            this.zzj.zzc().zzc.zza(this.zzj.zzm().currentTimeMillis());
        }
        zzz();
    }

    public final zzx zzu() {
        return this.zzj.zzu();
    }

    public final zzy zzb() {
        return this.zzj.zzb();
    }

    public final zzez zzr() {
        return this.zzj.zzr();
    }

    public final zzfw zzq() {
        return this.zzj.zzq();
    }

    public final zzfx zzc() {
        zzb((zzkl) this.zzb);
        return this.zzb;
    }

    public final zzfc zzd() {
        zzb((zzkl) this.zzc);
        return this.zzc;
    }

    public final zzad zze() {
        zzb((zzkl) this.zzd);
        return this.zzd;
    }

    private final zzfj zzt() {
        zzfj zzfj = this.zze;
        if (zzfj != null) {
            return zzfj;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    private final zzkg zzv() {
        zzb((zzkl) this.zzf);
        return this.zzf;
    }

    public final zzo zzf() {
        zzb((zzkl) this.zzg);
        return this.zzg;
    }

    public final zzii zzg() {
        zzb((zzkl) this.zzi);
        return this.zzi;
    }

    public final zzks zzh() {
        zzb((zzkl) this.zzh);
        return this.zzh;
    }

    public final zzex zzi() {
        return this.zzj.zzj();
    }

    public final Context zzn() {
        return this.zzj.zzn();
    }

    public final Clock zzm() {
        return this.zzj.zzm();
    }

    public final zzkw zzj() {
        return this.zzj.zzi();
    }

    private final void zzw() {
        this.zzj.zzq().zzd();
    }

    /* access modifiers changed from: package-private */
    public final void zzk() {
        if (!this.zzk) {
            throw new IllegalStateException("UploadController is not initialized");
        }
    }

    private static void zzb(zzkl zzkl) {
        if (zzkl == null) {
            throw new IllegalStateException("Upload Component not created");
        } else if (!zzkl.zzaj()) {
            String valueOf = String.valueOf(zzkl.getClass());
            throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 27).append("Component not initialized: ").append(valueOf).toString());
        }
    }

    private final long zzx() {
        long currentTimeMillis = this.zzj.zzm().currentTimeMillis();
        zzfl zzc2 = this.zzj.zzc();
        zzc2.zzaa();
        zzc2.zzd();
        long zza2 = zzc2.zzg.zza();
        if (zza2 == 0) {
            zza2 = 1 + ((long) zzc2.zzp().zzh().nextInt(86400000));
            zzc2.zzg.zza(zza2);
        }
        return ((((currentTimeMillis + zza2) / 1000) / 60) / 60) / 24;
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzao zzao, String str) {
        String str2;
        zzao zzao2 = zzao;
        zzf zzb2 = zze().zzb(str);
        if (zzb2 == null || TextUtils.isEmpty(zzb2.zzl())) {
            this.zzj.zzr().zzw().zza("No app data available; dropping event", str);
            return;
        }
        Boolean zzb3 = zzb(zzb2);
        if (zzb3 == null) {
            if (!"_ui".equals(zzao2.zza)) {
                this.zzj.zzr().zzi().zza("Could not find package. appId", zzez.zza(str));
            }
        } else if (!zzb3.booleanValue()) {
            this.zzj.zzr().zzf().zza("App version does not match; dropping event. appId", zzez.zza(str));
            return;
        }
        String zze2 = zzb2.zze();
        String zzl2 = zzb2.zzl();
        long zzm2 = zzb2.zzm();
        String zzn2 = zzb2.zzn();
        long zzo2 = zzb2.zzo();
        long zzp2 = zzb2.zzp();
        boolean zzr2 = zzb2.zzr();
        String zzi2 = zzb2.zzi();
        long zzae = zzb2.zzae();
        boolean zzaf = zzb2.zzaf();
        boolean zzag = zzb2.zzag();
        String zzf2 = zzb2.zzf();
        Boolean zzah = zzb2.zzah();
        long zzq2 = zzb2.zzq();
        List<String> zzai = zzb2.zzai();
        if (!zzoe.zzb() || !this.zzj.zzb().zze(zzb2.zzc(), zzaq.zzbn)) {
            str2 = null;
        } else {
            str2 = zzb2.zzg();
        }
        zzn zzn3 = r2;
        zzn zzn4 = new zzn(str, zze2, zzl2, zzm2, zzn2, zzo2, zzp2, (String) null, zzr2, false, zzi2, zzae, 0, 0, zzaf, zzag, false, zzf2, zzah, zzq2, zzai, str2);
        zzb(zzao2, zzn3);
    }

    private final void zzb(zzao zzao, zzn zzn2) {
        if (zzof.zzb() && this.zzj.zzb().zza(zzaq.zzcn)) {
            zzfd zza2 = zzfd.zza(zzao);
            this.zzj.zzi().zza(zza2.zzb, zze().zzi(zzn2.zza));
            this.zzj.zzi().zza(zza2, this.zzj.zzb().zza(zzn2.zza));
            zzao = zza2.zza();
        }
        zza(zzao, zzn2);
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzao zzao, zzn zzn2) {
        List<zzw> list;
        List<zzw> list2;
        List<zzw> list3;
        zzao zzao2 = zzao;
        zzn zzn3 = zzn2;
        Preconditions.checkNotNull(zzn2);
        Preconditions.checkNotEmpty(zzn3.zza);
        zzw();
        zzk();
        String str = zzn3.zza;
        long j = zzao2.zzd;
        zzh();
        if (zzks.zza(zzao, zzn2)) {
            if (!zzn3.zzh) {
                zzc(zzn3);
                return;
            }
            if (this.zzj.zzb().zze(str, zzaq.zzbb) && zzn3.zzu != null) {
                if (zzn3.zzu.contains(zzao2.zza)) {
                    Bundle zzb2 = zzao2.zzb.zzb();
                    zzb2.putLong("ga_safelisted", 1);
                    zzao2 = new zzao(zzao2.zza, new zzan(zzb2), zzao2.zzc, zzao2.zzd);
                } else {
                    this.zzj.zzr().zzw().zza("Dropping non-safelisted event. appId, event name, origin", str, zzao2.zza, zzao2.zzc);
                    return;
                }
            }
            zze().zzf();
            try {
                zzad zze2 = zze();
                Preconditions.checkNotEmpty(str);
                zze2.zzd();
                zze2.zzak();
                int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
                if (i < 0) {
                    zze2.zzr().zzi().zza("Invalid time querying timed out conditional properties", zzez.zza(str), Long.valueOf(j));
                    list = Collections.emptyList();
                } else {
                    list = zze2.zza("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str, String.valueOf(j)});
                }
                for (zzw next : list) {
                    if (next != null) {
                        this.zzj.zzr().zzx().zza("User property timed out", next.zza, this.zzj.zzj().zzc(next.zzc.zza), next.zzc.zza());
                        if (next.zzg != null) {
                            zzc(new zzao(next.zzg, j), zzn3);
                        }
                        zze().zze(str, next.zzc.zza);
                    }
                }
                zzad zze3 = zze();
                Preconditions.checkNotEmpty(str);
                zze3.zzd();
                zze3.zzak();
                if (i < 0) {
                    zze3.zzr().zzi().zza("Invalid time querying expired conditional properties", zzez.zza(str), Long.valueOf(j));
                    list2 = Collections.emptyList();
                } else {
                    list2 = zze3.zza("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str, String.valueOf(j)});
                }
                ArrayList arrayList = new ArrayList(list2.size());
                for (zzw next2 : list2) {
                    if (next2 != null) {
                        this.zzj.zzr().zzx().zza("User property expired", next2.zza, this.zzj.zzj().zzc(next2.zzc.zza), next2.zzc.zza());
                        zze().zzb(str, next2.zzc.zza);
                        if (next2.zzk != null) {
                            arrayList.add(next2.zzk);
                        }
                        zze().zze(str, next2.zzc.zza);
                    }
                }
                ArrayList arrayList2 = arrayList;
                int size = arrayList2.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList2.get(i2);
                    i2++;
                    zzc(new zzao((zzao) obj, j), zzn3);
                }
                zzad zze4 = zze();
                String str2 = zzao2.zza;
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotEmpty(str2);
                zze4.zzd();
                zze4.zzak();
                if (i < 0) {
                    zze4.zzr().zzi().zza("Invalid time querying triggered conditional properties", zzez.zza(str), zze4.zzo().zza(str2), Long.valueOf(j));
                    list3 = Collections.emptyList();
                } else {
                    list3 = zze4.zza("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str, str2, String.valueOf(j)});
                }
                ArrayList arrayList3 = new ArrayList(list3.size());
                for (zzw next3 : list3) {
                    if (next3 != null) {
                        zzkr zzkr = next3.zzc;
                        zzkt zzkt = r4;
                        zzkt zzkt2 = new zzkt(next3.zza, next3.zzb, zzkr.zza, j, zzkr.zza());
                        if (zze().zza(zzkt)) {
                            this.zzj.zzr().zzx().zza("User property triggered", next3.zza, this.zzj.zzj().zzc(zzkt.zzc), zzkt.zze);
                        } else {
                            this.zzj.zzr().zzf().zza("Too many active user properties, ignoring", zzez.zza(next3.zza), this.zzj.zzj().zzc(zzkt.zzc), zzkt.zze);
                        }
                        if (next3.zzi != null) {
                            arrayList3.add(next3.zzi);
                        }
                        next3.zzc = new zzkr(zzkt);
                        next3.zze = true;
                        zze().zza(next3);
                    }
                }
                zzc(zzao2, zzn3);
                ArrayList arrayList4 = arrayList3;
                int size2 = arrayList4.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj2 = arrayList4.get(i3);
                    i3++;
                    zzc(new zzao((zzao) obj2, j), zzn3);
                }
                zze().mo40209b_();
            } finally {
                zze().zzh();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:107:0x02f8 A[Catch:{ SQLiteException -> 0x02bf, all -> 0x0989 }] */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0335 A[Catch:{ SQLiteException -> 0x02bf, all -> 0x0989 }] */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x0344  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x036c A[Catch:{ SQLiteException -> 0x02bf, all -> 0x0989 }] */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x037c A[Catch:{ SQLiteException -> 0x02bf, all -> 0x0989 }] */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x03b1 A[Catch:{ SQLiteException -> 0x02bf, all -> 0x0989 }] */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x03df  */
    /* JADX WARNING: Removed duplicated region for block: B:287:0x0936 A[Catch:{ SQLiteException -> 0x02bf, all -> 0x0989 }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0187 A[Catch:{ SQLiteException -> 0x02bf, all -> 0x0989 }] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x018f A[Catch:{ SQLiteException -> 0x02bf, all -> 0x0989 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzc(com.google.android.gms.measurement.internal.zzao r28, com.google.android.gms.measurement.internal.zzn r29) {
        /*
            r27 = this;
            r1 = r27
            r2 = r28
            r3 = r29
            java.lang.String r4 = "_sno"
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r29)
            java.lang.String r5 = r3.zza
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r5)
            long r5 = java.lang.System.nanoTime()
            r27.zzw()
            r27.zzk()
            java.lang.String r15 = r3.zza
            r27.zzh()
            boolean r7 = com.google.android.gms.measurement.internal.zzks.zza((com.google.android.gms.measurement.internal.zzao) r28, (com.google.android.gms.measurement.internal.zzn) r29)
            if (r7 != 0) goto L_0x0026
            return
        L_0x0026:
            boolean r7 = r3.zzh
            if (r7 != 0) goto L_0x002e
            r1.zzc(r3)
            return
        L_0x002e:
            com.google.android.gms.measurement.internal.zzfx r7 = r27.zzc()
            java.lang.String r8 = r2.zza
            boolean r7 = r7.zzb(r15, r8)
            java.lang.String r14 = "_err"
            r13 = 0
            if (r7 == 0) goto L_0x00df
            com.google.android.gms.measurement.internal.zzgd r3 = r1.zzj
            com.google.android.gms.measurement.internal.zzez r3 = r3.zzr()
            com.google.android.gms.measurement.internal.zzfb r3 = r3.zzi()
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r15)
            com.google.android.gms.measurement.internal.zzgd r5 = r1.zzj
            com.google.android.gms.measurement.internal.zzex r5 = r5.zzj()
            java.lang.String r6 = r2.zza
            java.lang.String r5 = r5.zza((java.lang.String) r6)
            java.lang.String r6 = "Dropping blacklisted event. appId"
            r3.zza(r6, r4, r5)
            com.google.android.gms.measurement.internal.zzfx r3 = r27.zzc()
            boolean r3 = r3.zzg(r15)
            if (r3 != 0) goto L_0x0075
            com.google.android.gms.measurement.internal.zzfx r3 = r27.zzc()
            boolean r3 = r3.zzh(r15)
            if (r3 == 0) goto L_0x0073
            goto L_0x0075
        L_0x0073:
            r3 = 0
            goto L_0x0076
        L_0x0075:
            r3 = 1
        L_0x0076:
            if (r3 != 0) goto L_0x0092
            java.lang.String r4 = r2.zza
            boolean r4 = r14.equals(r4)
            if (r4 != 0) goto L_0x0092
            com.google.android.gms.measurement.internal.zzgd r4 = r1.zzj
            com.google.android.gms.measurement.internal.zzkw r7 = r4.zzi()
            r9 = 11
            java.lang.String r11 = r2.zza
            r12 = 0
            java.lang.String r10 = "_ev"
            r8 = r15
            r7.zza((java.lang.String) r8, (int) r9, (java.lang.String) r10, (java.lang.String) r11, (int) r12)
        L_0x0092:
            if (r3 == 0) goto L_0x00de
            com.google.android.gms.measurement.internal.zzad r2 = r27.zze()
            com.google.android.gms.measurement.internal.zzf r2 = r2.zzb(r15)
            if (r2 == 0) goto L_0x00de
            long r3 = r2.zzu()
            long r5 = r2.zzt()
            long r3 = java.lang.Math.max(r3, r5)
            com.google.android.gms.measurement.internal.zzgd r5 = r1.zzj
            com.google.android.gms.common.util.Clock r5 = r5.zzm()
            long r5 = r5.currentTimeMillis()
            long r5 = r5 - r3
            long r3 = java.lang.Math.abs(r5)
            com.google.android.gms.measurement.internal.zzeo<java.lang.Long> r5 = com.google.android.gms.measurement.internal.zzaq.zzy
            java.lang.Object r5 = r5.zza(r13)
            java.lang.Long r5 = (java.lang.Long) r5
            long r5 = r5.longValue()
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 <= 0) goto L_0x00de
            com.google.android.gms.measurement.internal.zzgd r3 = r1.zzj
            com.google.android.gms.measurement.internal.zzez r3 = r3.zzr()
            com.google.android.gms.measurement.internal.zzfb r3 = r3.zzw()
            java.lang.String r4 = "Fetching config for blacklisted app"
            r3.zza(r4)
            r1.zza((com.google.android.gms.measurement.internal.zzf) r2)
        L_0x00de:
            return
        L_0x00df:
            boolean r7 = com.google.android.gms.internal.measurement.zzmd.zzb()
            if (r7 == 0) goto L_0x0110
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj
            com.google.android.gms.measurement.internal.zzy r7 = r7.zzb()
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r8 = com.google.android.gms.measurement.internal.zzaq.zzcj
            boolean r7 = r7.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r8)
            if (r7 == 0) goto L_0x0110
            com.google.android.gms.measurement.internal.zzfd r2 = com.google.android.gms.measurement.internal.zzfd.zza(r28)
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj
            com.google.android.gms.measurement.internal.zzkw r7 = r7.zzi()
            com.google.android.gms.measurement.internal.zzgd r8 = r1.zzj
            com.google.android.gms.measurement.internal.zzy r8 = r8.zzb()
            int r8 = r8.zza((java.lang.String) r15)
            r7.zza((com.google.android.gms.measurement.internal.zzfd) r2, (int) r8)
            com.google.android.gms.measurement.internal.zzao r2 = r2.zza()
        L_0x0110:
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj
            com.google.android.gms.measurement.internal.zzez r7 = r7.zzr()
            r8 = 2
            boolean r7 = r7.zza((int) r8)
            if (r7 == 0) goto L_0x0138
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj
            com.google.android.gms.measurement.internal.zzez r7 = r7.zzr()
            com.google.android.gms.measurement.internal.zzfb r7 = r7.zzx()
            com.google.android.gms.measurement.internal.zzgd r9 = r1.zzj
            com.google.android.gms.measurement.internal.zzex r9 = r9.zzj()
            java.lang.String r9 = r9.zza((com.google.android.gms.measurement.internal.zzao) r2)
            java.lang.String r10 = "Logging event"
            r7.zza(r10, r9)
        L_0x0138:
            com.google.android.gms.measurement.internal.zzad r7 = r27.zze()
            r7.zzf()
            r1.zzc(r3)     // Catch:{ all -> 0x0989 }
            boolean r7 = com.google.android.gms.internal.measurement.zzmo.zzb()     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x0158
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzy r7 = r7.zzb()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r9 = com.google.android.gms.measurement.internal.zzaq.zzci     // Catch:{ all -> 0x0989 }
            boolean r7 = r7.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r9)     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x0158
            r7 = 1
            goto L_0x0159
        L_0x0158:
            r7 = 0
        L_0x0159:
            java.lang.String r9 = "ecommerce_purchase"
            java.lang.String r10 = r2.zza     // Catch:{ all -> 0x0989 }
            boolean r9 = r9.equals(r10)     // Catch:{ all -> 0x0989 }
            java.lang.String r10 = "refund"
            if (r9 != 0) goto L_0x017c
            if (r7 == 0) goto L_0x017a
            java.lang.String r7 = "purchase"
            java.lang.String r9 = r2.zza     // Catch:{ all -> 0x0989 }
            boolean r7 = r7.equals(r9)     // Catch:{ all -> 0x0989 }
            if (r7 != 0) goto L_0x017c
            java.lang.String r7 = r2.zza     // Catch:{ all -> 0x0989 }
            boolean r7 = r10.equals(r7)     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x017a
            goto L_0x017c
        L_0x017a:
            r7 = 0
            goto L_0x017d
        L_0x017c:
            r7 = 1
        L_0x017d:
            java.lang.String r9 = "_iap"
            java.lang.String r11 = r2.zza     // Catch:{ all -> 0x0989 }
            boolean r9 = r9.equals(r11)     // Catch:{ all -> 0x0989 }
            if (r9 != 0) goto L_0x018c
            if (r7 == 0) goto L_0x018a
            goto L_0x018c
        L_0x018a:
            r9 = 0
            goto L_0x018d
        L_0x018c:
            r9 = 1
        L_0x018d:
            if (r9 == 0) goto L_0x0344
            com.google.android.gms.measurement.internal.zzan r9 = r2.zzb     // Catch:{ all -> 0x0989 }
            java.lang.String r11 = "currency"
            java.lang.String r9 = r9.zzd(r11)     // Catch:{ all -> 0x0989 }
            java.lang.String r11 = "value"
            if (r7 == 0) goto L_0x020d
            com.google.android.gms.measurement.internal.zzan r7 = r2.zzb     // Catch:{ all -> 0x0989 }
            java.lang.Double r7 = r7.zzc(r11)     // Catch:{ all -> 0x0989 }
            double r17 = r7.doubleValue()     // Catch:{ all -> 0x0989 }
            r19 = 4696837146684686336(0x412e848000000000, double:1000000.0)
            double r17 = r17 * r19
            r21 = 0
            int r7 = (r17 > r21 ? 1 : (r17 == r21 ? 0 : -1))
            if (r7 != 0) goto L_0x01c0
            com.google.android.gms.measurement.internal.zzan r7 = r2.zzb     // Catch:{ all -> 0x0989 }
            java.lang.Long r7 = r7.zzb(r11)     // Catch:{ all -> 0x0989 }
            long r12 = r7.longValue()     // Catch:{ all -> 0x0989 }
            double r11 = (double) r12     // Catch:{ all -> 0x0989 }
            double r17 = r11 * r19
        L_0x01c0:
            r11 = 4890909195324358656(0x43e0000000000000, double:9.223372036854776E18)
            int r7 = (r17 > r11 ? 1 : (r17 == r11 ? 0 : -1))
            if (r7 > 0) goto L_0x01ef
            r11 = -4332462841530417152(0xc3e0000000000000, double:-9.223372036854776E18)
            int r7 = (r17 > r11 ? 1 : (r17 == r11 ? 0 : -1))
            if (r7 < 0) goto L_0x01ef
            long r11 = java.lang.Math.round(r17)     // Catch:{ all -> 0x0989 }
            boolean r7 = com.google.android.gms.internal.measurement.zzmo.zzb()     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x01ee
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzy r7 = r7.zzb()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r13 = com.google.android.gms.measurement.internal.zzaq.zzci     // Catch:{ all -> 0x0989 }
            boolean r7 = r7.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r13)     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x01ee
            java.lang.String r7 = r2.zza     // Catch:{ all -> 0x0989 }
            boolean r7 = r10.equals(r7)     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x01ee
            long r11 = -r11
            goto L_0x0217
        L_0x01ee:
            goto L_0x0217
        L_0x01ef:
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzez r7 = r7.zzr()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzfb r7 = r7.zzi()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = "Data lost. Currency value is too big. appId"
            java.lang.Object r9 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r15)     // Catch:{ all -> 0x0989 }
            java.lang.Double r10 = java.lang.Double.valueOf(r17)     // Catch:{ all -> 0x0989 }
            r7.zza(r8, r9, r10)     // Catch:{ all -> 0x0989 }
            r23 = r5
            r5 = 0
            r11 = 0
            goto L_0x0333
        L_0x020d:
            com.google.android.gms.measurement.internal.zzan r7 = r2.zzb     // Catch:{ all -> 0x0989 }
            java.lang.Long r7 = r7.zzb(r11)     // Catch:{ all -> 0x0989 }
            long r11 = r7.longValue()     // Catch:{ all -> 0x0989 }
        L_0x0217:
            boolean r7 = android.text.TextUtils.isEmpty(r9)     // Catch:{ all -> 0x0989 }
            if (r7 != 0) goto L_0x032f
            java.util.Locale r7 = java.util.Locale.US     // Catch:{ all -> 0x0989 }
            java.lang.String r7 = r9.toUpperCase(r7)     // Catch:{ all -> 0x0989 }
            java.lang.String r9 = "[A-Z]{3}"
            boolean r9 = r7.matches(r9)     // Catch:{ all -> 0x0989 }
            if (r9 == 0) goto L_0x032b
            java.lang.String r9 = "_ltv_"
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ all -> 0x0989 }
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ all -> 0x0989 }
            int r10 = r7.length()     // Catch:{ all -> 0x0989 }
            if (r10 == 0) goto L_0x0240
            java.lang.String r7 = r9.concat(r7)     // Catch:{ all -> 0x0989 }
            goto L_0x0245
        L_0x0240:
            java.lang.String r7 = new java.lang.String     // Catch:{ all -> 0x0989 }
            r7.<init>(r9)     // Catch:{ all -> 0x0989 }
        L_0x0245:
            r10 = r7
            com.google.android.gms.measurement.internal.zzad r7 = r27.zze()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzkt r7 = r7.zzc(r15, r10)     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x0285
            java.lang.Object r9 = r7.zze     // Catch:{ all -> 0x0989 }
            boolean r9 = r9 instanceof java.lang.Long     // Catch:{ all -> 0x0989 }
            if (r9 != 0) goto L_0x025b
            r23 = r5
            r5 = 0
            r6 = 1
            goto L_0x0289
        L_0x025b:
            java.lang.Object r7 = r7.zze     // Catch:{ all -> 0x0989 }
            java.lang.Long r7 = (java.lang.Long) r7     // Catch:{ all -> 0x0989 }
            long r7 = r7.longValue()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzkt r17 = new com.google.android.gms.measurement.internal.zzkt     // Catch:{ all -> 0x0989 }
            java.lang.String r9 = r2.zzc     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r13 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.common.util.Clock r13 = r13.zzm()     // Catch:{ all -> 0x0989 }
            long r18 = r13.currentTimeMillis()     // Catch:{ all -> 0x0989 }
            long r7 = r7 + r11
            java.lang.Long r13 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x0989 }
            r7 = r17
            r8 = r15
            r23 = r5
            r5 = 0
            r6 = 1
            r11 = r18
            r7.<init>(r8, r9, r10, r11, r13)     // Catch:{ all -> 0x0989 }
            r6 = r17
            goto L_0x02ee
        L_0x0285:
            r23 = r5
            r5 = 0
            r6 = 1
        L_0x0289:
            com.google.android.gms.measurement.internal.zzad r7 = r27.zze()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r9 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzy r9 = r9.zzb()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Integer> r13 = com.google.android.gms.measurement.internal.zzaq.zzad     // Catch:{ all -> 0x0989 }
            int r9 = r9.zzb(r15, r13)     // Catch:{ all -> 0x0989 }
            int r9 = r9 - r6
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r15)     // Catch:{ all -> 0x0989 }
            r7.zzd()     // Catch:{ all -> 0x0989 }
            r7.zzak()     // Catch:{ all -> 0x0989 }
            android.database.sqlite.SQLiteDatabase r13 = r7.mo40210c_()     // Catch:{ SQLiteException -> 0x02bf }
            java.lang.String r8 = "delete from user_attributes where app_id=? and name in (select name from user_attributes where app_id=? and name like '_ltv_%' order by set_timestamp desc limit ?,10);"
            r6 = 3
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ SQLiteException -> 0x02bf }
            r6[r5] = r15     // Catch:{ SQLiteException -> 0x02bf }
            r16 = 1
            r6[r16] = r15     // Catch:{ SQLiteException -> 0x02bf }
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ SQLiteException -> 0x02bf }
            r16 = 2
            r6[r16] = r9     // Catch:{ SQLiteException -> 0x02bf }
            r13.execSQL(r8, r6)     // Catch:{ SQLiteException -> 0x02bf }
            goto L_0x02d2
        L_0x02bf:
            r0 = move-exception
            r6 = r0
            com.google.android.gms.measurement.internal.zzez r7 = r7.zzr()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzfb r7 = r7.zzf()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = "Error pruning currencies. appId"
            java.lang.Object r9 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r15)     // Catch:{ all -> 0x0989 }
            r7.zza(r8, r9, r6)     // Catch:{ all -> 0x0989 }
        L_0x02d2:
            com.google.android.gms.measurement.internal.zzkt r17 = new com.google.android.gms.measurement.internal.zzkt     // Catch:{ all -> 0x0989 }
            java.lang.String r9 = r2.zzc     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r6 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.common.util.Clock r6 = r6.zzm()     // Catch:{ all -> 0x0989 }
            long r18 = r6.currentTimeMillis()     // Catch:{ all -> 0x0989 }
            java.lang.Long r13 = java.lang.Long.valueOf(r11)     // Catch:{ all -> 0x0989 }
            r7 = r17
            r8 = r15
            r11 = r18
            r7.<init>(r8, r9, r10, r11, r13)     // Catch:{ all -> 0x0989 }
            r6 = r17
        L_0x02ee:
            com.google.android.gms.measurement.internal.zzad r7 = r27.zze()     // Catch:{ all -> 0x0989 }
            boolean r7 = r7.zza((com.google.android.gms.measurement.internal.zzkt) r6)     // Catch:{ all -> 0x0989 }
            if (r7 != 0) goto L_0x0332
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzez r7 = r7.zzr()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzfb r7 = r7.zzf()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = "Too many unique user properties are set. Ignoring user property. appId"
            java.lang.Object r9 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r15)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r10 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzex r10 = r10.zzj()     // Catch:{ all -> 0x0989 }
            java.lang.String r11 = r6.zzc     // Catch:{ all -> 0x0989 }
            java.lang.String r10 = r10.zzc(r11)     // Catch:{ all -> 0x0989 }
            java.lang.Object r6 = r6.zze     // Catch:{ all -> 0x0989 }
            r7.zza(r8, r9, r10, r6)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r6 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzkw r7 = r6.zzi()     // Catch:{ all -> 0x0989 }
            r9 = 9
            r10 = 0
            r11 = 0
            r12 = 0
            r8 = r15
            r7.zza((java.lang.String) r8, (int) r9, (java.lang.String) r10, (java.lang.String) r11, (int) r12)     // Catch:{ all -> 0x0989 }
            goto L_0x0332
        L_0x032b:
            r23 = r5
            r5 = 0
            goto L_0x0332
        L_0x032f:
            r23 = r5
            r5 = 0
        L_0x0332:
            r11 = 1
        L_0x0333:
            if (r11 != 0) goto L_0x0347
            com.google.android.gms.measurement.internal.zzad r2 = r27.zze()     // Catch:{ all -> 0x0989 }
            r2.mo40209b_()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzad r2 = r27.zze()
            r2.zzh()
            return
        L_0x0344:
            r23 = r5
            r5 = 0
        L_0x0347:
            java.lang.String r6 = r2.zza     // Catch:{ all -> 0x0989 }
            boolean r6 = com.google.android.gms.measurement.internal.zzkw.zza((java.lang.String) r6)     // Catch:{ all -> 0x0989 }
            java.lang.String r7 = r2.zza     // Catch:{ all -> 0x0989 }
            boolean r18 = r14.equals(r7)     // Catch:{ all -> 0x0989 }
            boolean r7 = com.google.android.gms.internal.measurement.zzmo.zzb()     // Catch:{ all -> 0x0989 }
            r19 = 1
            if (r7 == 0) goto L_0x037c
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzy r7 = r7.zzb()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = r3.zza     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r9 = com.google.android.gms.measurement.internal.zzaq.zzce     // Catch:{ all -> 0x0989 }
            boolean r7 = r7.zze(r8, r9)     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x037c
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            r7.zzi()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzan r7 = r2.zzb     // Catch:{ all -> 0x0989 }
            long r7 = com.google.android.gms.measurement.internal.zzkw.zza((com.google.android.gms.measurement.internal.zzan) r7)     // Catch:{ all -> 0x0989 }
            long r7 = r7 + r19
            r11 = r7
            goto L_0x037e
        L_0x037c:
            r11 = r19
        L_0x037e:
            com.google.android.gms.measurement.internal.zzad r7 = r27.zze()     // Catch:{ all -> 0x0989 }
            long r8 = r27.zzx()     // Catch:{ all -> 0x0989 }
            r13 = 1
            r16 = 0
            r17 = 0
            r10 = r15
            r14 = r6
            r28 = r15
            r15 = r16
            r16 = r18
            com.google.android.gms.measurement.internal.zzac r7 = r7.zza(r8, r10, r11, r13, r14, r15, r16, r17)     // Catch:{ all -> 0x0989 }
            long r8 = r7.zzb     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Integer> r10 = com.google.android.gms.measurement.internal.zzaq.zzj     // Catch:{ all -> 0x0989 }
            r14 = 0
            java.lang.Object r10 = r10.zza(r14)     // Catch:{ all -> 0x0989 }
            java.lang.Integer r10 = (java.lang.Integer) r10     // Catch:{ all -> 0x0989 }
            int r10 = r10.intValue()     // Catch:{ all -> 0x0989 }
            long r10 = (long) r10     // Catch:{ all -> 0x0989 }
            long r8 = r8 - r10
            r12 = 0
            int r10 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            r15 = 1000(0x3e8, double:4.94E-321)
            if (r10 <= 0) goto L_0x03df
            long r8 = r8 % r15
            int r2 = (r8 > r19 ? 1 : (r8 == r19 ? 0 : -1))
            if (r2 != 0) goto L_0x03d0
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzez r2 = r2.zzr()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzf()     // Catch:{ all -> 0x0989 }
            java.lang.String r3 = "Data loss. Too many events logged. appId, count"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r28)     // Catch:{ all -> 0x0989 }
            long r5 = r7.zzb     // Catch:{ all -> 0x0989 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0989 }
            r2.zza(r3, r4, r5)     // Catch:{ all -> 0x0989 }
        L_0x03d0:
            com.google.android.gms.measurement.internal.zzad r2 = r27.zze()     // Catch:{ all -> 0x0989 }
            r2.mo40209b_()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzad r2 = r27.zze()
            r2.zzh()
            return
        L_0x03df:
            if (r6 == 0) goto L_0x0436
            long r8 = r7.zza     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Integer> r10 = com.google.android.gms.measurement.internal.zzaq.zzl     // Catch:{ all -> 0x0989 }
            java.lang.Object r10 = r10.zza(r14)     // Catch:{ all -> 0x0989 }
            java.lang.Integer r10 = (java.lang.Integer) r10     // Catch:{ all -> 0x0989 }
            int r10 = r10.intValue()     // Catch:{ all -> 0x0989 }
            long r10 = (long) r10     // Catch:{ all -> 0x0989 }
            long r8 = r8 - r10
            int r10 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r10 <= 0) goto L_0x0436
            long r8 = r8 % r15
            int r3 = (r8 > r19 ? 1 : (r8 == r19 ? 0 : -1))
            if (r3 != 0) goto L_0x0414
            com.google.android.gms.measurement.internal.zzgd r3 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzez r3 = r3.zzr()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzfb r3 = r3.zzf()     // Catch:{ all -> 0x0989 }
            java.lang.String r4 = "Data loss. Too many public events logged. appId, count"
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r28)     // Catch:{ all -> 0x0989 }
            long r6 = r7.zza     // Catch:{ all -> 0x0989 }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x0989 }
            r3.zza(r4, r5, r6)     // Catch:{ all -> 0x0989 }
        L_0x0414:
            com.google.android.gms.measurement.internal.zzgd r3 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzkw r7 = r3.zzi()     // Catch:{ all -> 0x0989 }
            r9 = 16
            java.lang.String r10 = "_ev"
            java.lang.String r11 = r2.zza     // Catch:{ all -> 0x0989 }
            r12 = 0
            r8 = r28
            r7.zza((java.lang.String) r8, (int) r9, (java.lang.String) r10, (java.lang.String) r11, (int) r12)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzad r2 = r27.zze()     // Catch:{ all -> 0x0989 }
            r2.mo40209b_()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzad r2 = r27.zze()
            r2.zzh()
            return
        L_0x0436:
            if (r18 == 0) goto L_0x0486
            long r8 = r7.zzd     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r10 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzy r10 = r10.zzb()     // Catch:{ all -> 0x0989 }
            java.lang.String r11 = r3.zza     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Integer> r15 = com.google.android.gms.measurement.internal.zzaq.zzk     // Catch:{ all -> 0x0989 }
            int r10 = r10.zzb(r11, r15)     // Catch:{ all -> 0x0989 }
            r11 = 1000000(0xf4240, float:1.401298E-39)
            int r10 = java.lang.Math.min(r11, r10)     // Catch:{ all -> 0x0989 }
            int r10 = java.lang.Math.max(r5, r10)     // Catch:{ all -> 0x0989 }
            long r10 = (long) r10     // Catch:{ all -> 0x0989 }
            long r8 = r8 - r10
            int r10 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r10 <= 0) goto L_0x0486
            int r2 = (r8 > r19 ? 1 : (r8 == r19 ? 0 : -1))
            if (r2 != 0) goto L_0x0477
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzez r2 = r2.zzr()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzf()     // Catch:{ all -> 0x0989 }
            java.lang.String r3 = "Too many error events logged. appId, count"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r28)     // Catch:{ all -> 0x0989 }
            long r5 = r7.zzd     // Catch:{ all -> 0x0989 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0989 }
            r2.zza(r3, r4, r5)     // Catch:{ all -> 0x0989 }
        L_0x0477:
            com.google.android.gms.measurement.internal.zzad r2 = r27.zze()     // Catch:{ all -> 0x0989 }
            r2.mo40209b_()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzad r2 = r27.zze()
            r2.zzh()
            return
        L_0x0486:
            com.google.android.gms.measurement.internal.zzan r7 = r2.zzb     // Catch:{ all -> 0x0989 }
            android.os.Bundle r15 = r7.zzb()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzkw r7 = r7.zzi()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = "_o"
            java.lang.String r9 = r2.zzc     // Catch:{ all -> 0x0989 }
            r7.zza((android.os.Bundle) r15, (java.lang.String) r8, (java.lang.Object) r9)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzkw r7 = r7.zzi()     // Catch:{ all -> 0x0989 }
            r11 = r28
            boolean r7 = r7.zzf(r11)     // Catch:{ all -> 0x0989 }
            java.lang.String r10 = "_r"
            if (r7 == 0) goto L_0x04c9
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzkw r7 = r7.zzi()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = "_dbg"
            java.lang.Long r9 = java.lang.Long.valueOf(r19)     // Catch:{ all -> 0x0989 }
            r7.zza((android.os.Bundle) r15, (java.lang.String) r8, (java.lang.Object) r9)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzkw r7 = r7.zzi()     // Catch:{ all -> 0x0989 }
            java.lang.Long r8 = java.lang.Long.valueOf(r19)     // Catch:{ all -> 0x0989 }
            r7.zza((android.os.Bundle) r15, (java.lang.String) r10, (java.lang.Object) r8)     // Catch:{ all -> 0x0989 }
        L_0x04c9:
            java.lang.String r7 = "_s"
            java.lang.String r8 = r2.zza     // Catch:{ all -> 0x0989 }
            boolean r7 = r7.equals(r8)     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x04f2
            com.google.android.gms.measurement.internal.zzad r7 = r27.zze()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = r3.zza     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzkt r7 = r7.zzc(r8, r4)     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x04f2
            java.lang.Object r8 = r7.zze     // Catch:{ all -> 0x0989 }
            boolean r8 = r8 instanceof java.lang.Long     // Catch:{ all -> 0x0989 }
            if (r8 == 0) goto L_0x04f2
            com.google.android.gms.measurement.internal.zzgd r8 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzkw r8 = r8.zzi()     // Catch:{ all -> 0x0989 }
            java.lang.Object r7 = r7.zze     // Catch:{ all -> 0x0989 }
            r8.zza((android.os.Bundle) r15, (java.lang.String) r4, (java.lang.Object) r7)     // Catch:{ all -> 0x0989 }
        L_0x04f2:
            com.google.android.gms.measurement.internal.zzad r4 = r27.zze()     // Catch:{ all -> 0x0989 }
            long r7 = r4.zzc(r11)     // Catch:{ all -> 0x0989 }
            int r4 = (r7 > r12 ? 1 : (r7 == r12 ? 0 : -1))
            if (r4 <= 0) goto L_0x0516
            com.google.android.gms.measurement.internal.zzgd r4 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzez r4 = r4.zzr()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzfb r4 = r4.zzi()     // Catch:{ all -> 0x0989 }
            java.lang.String r9 = "Data lost. Too many events stored on disk, deleted. appId"
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r11)     // Catch:{ all -> 0x0989 }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x0989 }
            r4.zza(r9, r5, r7)     // Catch:{ all -> 0x0989 }
        L_0x0516:
            com.google.android.gms.measurement.internal.zzal r4 = new com.google.android.gms.measurement.internal.zzal     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r8 = r1.zzj     // Catch:{ all -> 0x0989 }
            java.lang.String r9 = r2.zzc     // Catch:{ all -> 0x0989 }
            java.lang.String r5 = r2.zza     // Catch:{ all -> 0x0989 }
            long r12 = r2.zzd     // Catch:{ all -> 0x0989 }
            r18 = 0
            r7 = r4
            r2 = r10
            r10 = r11
            r26 = r11
            r11 = r5
            r25 = r14
            r5 = r15
            r14 = r18
            r16 = r5
            r7.<init>((com.google.android.gms.measurement.internal.zzgd) r8, (java.lang.String) r9, (java.lang.String) r10, (java.lang.String) r11, (long) r12, (long) r14, (android.os.Bundle) r16)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzad r5 = r27.zze()     // Catch:{ all -> 0x0989 }
            java.lang.String r7 = r4.zzb     // Catch:{ all -> 0x0989 }
            r8 = r26
            com.google.android.gms.measurement.internal.zzak r5 = r5.zza((java.lang.String) r8, (java.lang.String) r7)     // Catch:{ all -> 0x0989 }
            if (r5 != 0) goto L_0x05b8
            com.google.android.gms.measurement.internal.zzad r5 = r27.zze()     // Catch:{ all -> 0x0989 }
            long r9 = r5.zzh(r8)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r5 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzy r5 = r5.zzb()     // Catch:{ all -> 0x0989 }
            int r5 = r5.zzb(r8)     // Catch:{ all -> 0x0989 }
            long r11 = (long) r5     // Catch:{ all -> 0x0989 }
            int r5 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r5 < 0) goto L_0x059f
            if (r6 == 0) goto L_0x059f
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzez r2 = r2.zzr()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzf()     // Catch:{ all -> 0x0989 }
            java.lang.String r3 = "Too many event names used, ignoring event. appId, name, supported count"
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r8)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r6 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzex r6 = r6.zzj()     // Catch:{ all -> 0x0989 }
            java.lang.String r4 = r4.zzb     // Catch:{ all -> 0x0989 }
            java.lang.String r4 = r6.zza((java.lang.String) r4)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r6 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzy r6 = r6.zzb()     // Catch:{ all -> 0x0989 }
            int r6 = r6.zzb(r8)     // Catch:{ all -> 0x0989 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x0989 }
            r2.zza(r3, r5, r4, r6)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzkw r7 = r2.zzi()     // Catch:{ all -> 0x0989 }
            r9 = 8
            r10 = 0
            r11 = 0
            r12 = 0
            r7.zza((java.lang.String) r8, (int) r9, (java.lang.String) r10, (java.lang.String) r11, (int) r12)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzad r2 = r27.zze()
            r2.zzh()
            return
        L_0x059f:
            com.google.android.gms.measurement.internal.zzak r5 = new com.google.android.gms.measurement.internal.zzak     // Catch:{ all -> 0x0989 }
            java.lang.String r9 = r4.zzb     // Catch:{ all -> 0x0989 }
            r10 = 0
            r12 = 0
            long r14 = r4.zzc     // Catch:{ all -> 0x0989 }
            r16 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r21 = 0
            r7 = r5
            r7.<init>(r8, r9, r10, r12, r14, r16, r18, r19, r20, r21)     // Catch:{ all -> 0x0989 }
            goto L_0x05c6
        L_0x05b8:
            com.google.android.gms.measurement.internal.zzgd r6 = r1.zzj     // Catch:{ all -> 0x0989 }
            long r7 = r5.zzf     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzal r4 = r4.zza(r6, r7)     // Catch:{ all -> 0x0989 }
            long r6 = r4.zzc     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzak r5 = r5.zza(r6)     // Catch:{ all -> 0x0989 }
        L_0x05c6:
            com.google.android.gms.measurement.internal.zzad r6 = r27.zze()     // Catch:{ all -> 0x0989 }
            r6.zza((com.google.android.gms.measurement.internal.zzak) r5)     // Catch:{ all -> 0x0989 }
            r27.zzw()     // Catch:{ all -> 0x0989 }
            r27.zzk()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r4)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r29)     // Catch:{ all -> 0x0989 }
            java.lang.String r5 = r4.zza     // Catch:{ all -> 0x0989 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r5)     // Catch:{ all -> 0x0989 }
            java.lang.String r5 = r4.zza     // Catch:{ all -> 0x0989 }
            java.lang.String r6 = r3.zza     // Catch:{ all -> 0x0989 }
            boolean r5 = r5.equals(r6)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.common.internal.Preconditions.checkArgument(r5)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.internal.measurement.zzcc$zzg$zza r5 = com.google.android.gms.internal.measurement.zzcc.zzg.zzbf()     // Catch:{ all -> 0x0989 }
            r6 = 1
            com.google.android.gms.internal.measurement.zzcc$zzg$zza r5 = r5.zza((int) r6)     // Catch:{ all -> 0x0989 }
            java.lang.String r7 = "android"
            com.google.android.gms.internal.measurement.zzcc$zzg$zza r5 = r5.zza((java.lang.String) r7)     // Catch:{ all -> 0x0989 }
            java.lang.String r7 = r3.zza     // Catch:{ all -> 0x0989 }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x0989 }
            if (r7 != 0) goto L_0x0606
            java.lang.String r7 = r3.zza     // Catch:{ all -> 0x0989 }
            r5.zzf((java.lang.String) r7)     // Catch:{ all -> 0x0989 }
        L_0x0606:
            java.lang.String r7 = r3.zzd     // Catch:{ all -> 0x0989 }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x0989 }
            if (r7 != 0) goto L_0x0613
            java.lang.String r7 = r3.zzd     // Catch:{ all -> 0x0989 }
            r5.zze((java.lang.String) r7)     // Catch:{ all -> 0x0989 }
        L_0x0613:
            java.lang.String r7 = r3.zzc     // Catch:{ all -> 0x0989 }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x0989 }
            if (r7 != 0) goto L_0x0620
            java.lang.String r7 = r3.zzc     // Catch:{ all -> 0x0989 }
            r5.zzg((java.lang.String) r7)     // Catch:{ all -> 0x0989 }
        L_0x0620:
            long r7 = r3.zzj     // Catch:{ all -> 0x0989 }
            r9 = -2147483648(0xffffffff80000000, double:NaN)
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 == 0) goto L_0x062f
            long r7 = r3.zzj     // Catch:{ all -> 0x0989 }
            int r7 = (int) r7     // Catch:{ all -> 0x0989 }
            r5.zzh((int) r7)     // Catch:{ all -> 0x0989 }
        L_0x062f:
            long r7 = r3.zze     // Catch:{ all -> 0x0989 }
            r5.zzf((long) r7)     // Catch:{ all -> 0x0989 }
            java.lang.String r7 = r3.zzb     // Catch:{ all -> 0x0989 }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x0989 }
            if (r7 != 0) goto L_0x0641
            java.lang.String r7 = r3.zzb     // Catch:{ all -> 0x0989 }
            r5.zzk((java.lang.String) r7)     // Catch:{ all -> 0x0989 }
        L_0x0641:
            boolean r7 = com.google.android.gms.internal.measurement.zzoe.zzb()     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x0690
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzy r7 = r7.zzb()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = r3.zza     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r9 = com.google.android.gms.measurement.internal.zzaq.zzbn     // Catch:{ all -> 0x0989 }
            boolean r7 = r7.zze(r8, r9)     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x0690
            java.lang.String r7 = r5.zzl()     // Catch:{ all -> 0x0989 }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x066e
            java.lang.String r7 = r3.zzv     // Catch:{ all -> 0x0989 }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x0989 }
            if (r7 != 0) goto L_0x066e
            java.lang.String r7 = r3.zzv     // Catch:{ all -> 0x0989 }
            r5.zzp(r7)     // Catch:{ all -> 0x0989 }
        L_0x066e:
            java.lang.String r7 = r5.zzl()     // Catch:{ all -> 0x0989 }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x06a7
            java.lang.String r7 = r5.zzo()     // Catch:{ all -> 0x0989 }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x06a7
            java.lang.String r7 = r3.zzr     // Catch:{ all -> 0x0989 }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x0989 }
            if (r7 != 0) goto L_0x06a7
            java.lang.String r7 = r3.zzr     // Catch:{ all -> 0x0989 }
            r5.zzo(r7)     // Catch:{ all -> 0x0989 }
            goto L_0x06a7
        L_0x0690:
            java.lang.String r7 = r5.zzl()     // Catch:{ all -> 0x0989 }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x06a7
            java.lang.String r7 = r3.zzr     // Catch:{ all -> 0x0989 }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x0989 }
            if (r7 != 0) goto L_0x06a7
            java.lang.String r7 = r3.zzr     // Catch:{ all -> 0x0989 }
            r5.zzo(r7)     // Catch:{ all -> 0x0989 }
        L_0x06a7:
            long r7 = r3.zzf     // Catch:{ all -> 0x0989 }
            r9 = 0
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 == 0) goto L_0x06b4
            long r7 = r3.zzf     // Catch:{ all -> 0x0989 }
            r5.zzh((long) r7)     // Catch:{ all -> 0x0989 }
        L_0x06b4:
            long r7 = r3.zzt     // Catch:{ all -> 0x0989 }
            r5.zzk((long) r7)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzy r7 = r7.zzb()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = r3.zza     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r11 = com.google.android.gms.measurement.internal.zzaq.zzaw     // Catch:{ all -> 0x0989 }
            boolean r7 = r7.zze(r8, r11)     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x06d7
            com.google.android.gms.measurement.internal.zzks r7 = r27.zzh()     // Catch:{ all -> 0x0989 }
            java.util.List r7 = r7.zzf()     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x06d7
            r5.zzd((java.lang.Iterable<? extends java.lang.Integer>) r7)     // Catch:{ all -> 0x0989 }
        L_0x06d7:
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzfl r7 = r7.zzc()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = r3.zza     // Catch:{ all -> 0x0989 }
            android.util.Pair r7 = r7.zza((java.lang.String) r8)     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x070b
            java.lang.Object r8 = r7.first     // Catch:{ all -> 0x0989 }
            java.lang.CharSequence r8 = (java.lang.CharSequence) r8     // Catch:{ all -> 0x0989 }
            boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ all -> 0x0989 }
            if (r8 != 0) goto L_0x070b
            boolean r8 = r3.zzo     // Catch:{ all -> 0x0989 }
            if (r8 == 0) goto L_0x0772
            java.lang.Object r8 = r7.first     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ all -> 0x0989 }
            r5.zzh((java.lang.String) r8)     // Catch:{ all -> 0x0989 }
            java.lang.Object r8 = r7.second     // Catch:{ all -> 0x0989 }
            if (r8 == 0) goto L_0x0772
            java.lang.Object r7 = r7.second     // Catch:{ all -> 0x0989 }
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ all -> 0x0989 }
            boolean r7 = r7.booleanValue()     // Catch:{ all -> 0x0989 }
            r5.zza((boolean) r7)     // Catch:{ all -> 0x0989 }
            goto L_0x0772
        L_0x070b:
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzai r7 = r7.zzx()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r8 = r1.zzj     // Catch:{ all -> 0x0989 }
            android.content.Context r8 = r8.zzn()     // Catch:{ all -> 0x0989 }
            boolean r7 = r7.zza(r8)     // Catch:{ all -> 0x0989 }
            if (r7 != 0) goto L_0x0772
            boolean r7 = r3.zzp     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x0772
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            android.content.Context r7 = r7.zzn()     // Catch:{ all -> 0x0989 }
            android.content.ContentResolver r7 = r7.getContentResolver()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = "android_id"
            java.lang.String r7 = android.provider.Settings.Secure.getString(r7, r8)     // Catch:{ all -> 0x0989 }
            if (r7 != 0) goto L_0x0751
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzez r7 = r7.zzr()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzfb r7 = r7.zzi()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = "null secure ID. appId"
            java.lang.String r11 = r5.zzj()     // Catch:{ all -> 0x0989 }
            java.lang.Object r11 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r11)     // Catch:{ all -> 0x0989 }
            r7.zza(r8, r11)     // Catch:{ all -> 0x0989 }
            java.lang.String r7 = "null"
            goto L_0x076f
        L_0x0751:
            boolean r8 = r7.isEmpty()     // Catch:{ all -> 0x0989 }
            if (r8 == 0) goto L_0x076f
            com.google.android.gms.measurement.internal.zzgd r8 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzez r8 = r8.zzr()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzfb r8 = r8.zzi()     // Catch:{ all -> 0x0989 }
            java.lang.String r11 = "empty secure ID. appId"
            java.lang.String r12 = r5.zzj()     // Catch:{ all -> 0x0989 }
            java.lang.Object r12 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r12)     // Catch:{ all -> 0x0989 }
            r8.zza(r11, r12)     // Catch:{ all -> 0x0989 }
        L_0x076f:
            r5.zzm(r7)     // Catch:{ all -> 0x0989 }
        L_0x0772:
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzai r7 = r7.zzx()     // Catch:{ all -> 0x0989 }
            r7.zzaa()     // Catch:{ all -> 0x0989 }
            java.lang.String r7 = android.os.Build.MODEL     // Catch:{ all -> 0x0989 }
            com.google.android.gms.internal.measurement.zzcc$zzg$zza r7 = r5.zzc((java.lang.String) r7)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r8 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzai r8 = r8.zzx()     // Catch:{ all -> 0x0989 }
            r8.zzaa()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = android.os.Build.VERSION.RELEASE     // Catch:{ all -> 0x0989 }
            com.google.android.gms.internal.measurement.zzcc$zzg$zza r7 = r7.zzb((java.lang.String) r8)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r8 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzai r8 = r8.zzx()     // Catch:{ all -> 0x0989 }
            long r11 = r8.zzf()     // Catch:{ all -> 0x0989 }
            int r8 = (int) r11     // Catch:{ all -> 0x0989 }
            com.google.android.gms.internal.measurement.zzcc$zzg$zza r7 = r7.zzf((int) r8)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r8 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzai r8 = r8.zzx()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = r8.zzg()     // Catch:{ all -> 0x0989 }
            r7.zzd((java.lang.String) r8)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzy r7 = r7.zzb()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r8 = com.google.android.gms.measurement.internal.zzaq.zzcl     // Catch:{ all -> 0x0989 }
            boolean r7 = r7.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r8)     // Catch:{ all -> 0x0989 }
            if (r7 != 0) goto L_0x07c1
            long r7 = r3.zzl     // Catch:{ all -> 0x0989 }
            r5.zzj((long) r7)     // Catch:{ all -> 0x0989 }
        L_0x07c1:
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x0989 }
            boolean r7 = r7.zzab()     // Catch:{ all -> 0x0989 }
            if (r7 == 0) goto L_0x07d7
            r5.zzj()     // Catch:{ all -> 0x0989 }
            boolean r7 = android.text.TextUtils.isEmpty(r25)     // Catch:{ all -> 0x0989 }
            if (r7 != 0) goto L_0x07d7
            r7 = r25
            r5.zzn(r7)     // Catch:{ all -> 0x0989 }
        L_0x07d7:
            com.google.android.gms.measurement.internal.zzad r7 = r27.zze()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = r3.zza     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzf r7 = r7.zzb(r8)     // Catch:{ all -> 0x0989 }
            if (r7 != 0) goto L_0x085b
            com.google.android.gms.measurement.internal.zzf r7 = new com.google.android.gms.measurement.internal.zzf     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r8 = r1.zzj     // Catch:{ all -> 0x0989 }
            java.lang.String r11 = r3.zza     // Catch:{ all -> 0x0989 }
            r7.<init>(r8, r11)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r8 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzkw r8 = r8.zzi()     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = r8.zzk()     // Catch:{ all -> 0x0989 }
            r7.zza((java.lang.String) r8)     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = r3.zzk     // Catch:{ all -> 0x0989 }
            r7.zzf((java.lang.String) r8)     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = r3.zzb     // Catch:{ all -> 0x0989 }
            r7.zzb((java.lang.String) r8)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r8 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzfl r8 = r8.zzc()     // Catch:{ all -> 0x0989 }
            java.lang.String r11 = r3.zza     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = r8.zzb((java.lang.String) r11)     // Catch:{ all -> 0x0989 }
            r7.zze((java.lang.String) r8)     // Catch:{ all -> 0x0989 }
            r7.zzg((long) r9)     // Catch:{ all -> 0x0989 }
            r7.zza((long) r9)     // Catch:{ all -> 0x0989 }
            r7.zzb((long) r9)     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = r3.zzc     // Catch:{ all -> 0x0989 }
            r7.zzg((java.lang.String) r8)     // Catch:{ all -> 0x0989 }
            long r11 = r3.zzj     // Catch:{ all -> 0x0989 }
            r7.zzc((long) r11)     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = r3.zzd     // Catch:{ all -> 0x0989 }
            r7.zzh((java.lang.String) r8)     // Catch:{ all -> 0x0989 }
            long r11 = r3.zze     // Catch:{ all -> 0x0989 }
            r7.zzd((long) r11)     // Catch:{ all -> 0x0989 }
            long r11 = r3.zzf     // Catch:{ all -> 0x0989 }
            r7.zze((long) r11)     // Catch:{ all -> 0x0989 }
            boolean r8 = r3.zzh     // Catch:{ all -> 0x0989 }
            r7.zza((boolean) r8)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r8 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzy r8 = r8.zzb()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r11 = com.google.android.gms.measurement.internal.zzaq.zzcl     // Catch:{ all -> 0x0989 }
            boolean r8 = r8.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r11)     // Catch:{ all -> 0x0989 }
            if (r8 != 0) goto L_0x084f
            long r11 = r3.zzl     // Catch:{ all -> 0x0989 }
            r7.zzp(r11)     // Catch:{ all -> 0x0989 }
        L_0x084f:
            long r11 = r3.zzt     // Catch:{ all -> 0x0989 }
            r7.zzf((long) r11)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzad r8 = r27.zze()     // Catch:{ all -> 0x0989 }
            r8.zza((com.google.android.gms.measurement.internal.zzf) r7)     // Catch:{ all -> 0x0989 }
        L_0x085b:
            java.lang.String r8 = r7.zzd()     // Catch:{ all -> 0x0989 }
            boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ all -> 0x0989 }
            if (r8 != 0) goto L_0x086c
            java.lang.String r8 = r7.zzd()     // Catch:{ all -> 0x0989 }
            r5.zzi((java.lang.String) r8)     // Catch:{ all -> 0x0989 }
        L_0x086c:
            java.lang.String r8 = r7.zzi()     // Catch:{ all -> 0x0989 }
            boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ all -> 0x0989 }
            if (r8 != 0) goto L_0x087d
            java.lang.String r7 = r7.zzi()     // Catch:{ all -> 0x0989 }
            r5.zzl((java.lang.String) r7)     // Catch:{ all -> 0x0989 }
        L_0x087d:
            com.google.android.gms.measurement.internal.zzad r7 = r27.zze()     // Catch:{ all -> 0x0989 }
            java.lang.String r3 = r3.zza     // Catch:{ all -> 0x0989 }
            java.util.List r3 = r7.zza((java.lang.String) r3)     // Catch:{ all -> 0x0989 }
            r11 = 0
        L_0x0888:
            int r7 = r3.size()     // Catch:{ all -> 0x0989 }
            if (r11 >= r7) goto L_0x08bf
            com.google.android.gms.internal.measurement.zzcc$zzk$zza r7 = com.google.android.gms.internal.measurement.zzcc.zzk.zzj()     // Catch:{ all -> 0x0989 }
            java.lang.Object r8 = r3.get(r11)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzkt r8 = (com.google.android.gms.measurement.internal.zzkt) r8     // Catch:{ all -> 0x0989 }
            java.lang.String r8 = r8.zzc     // Catch:{ all -> 0x0989 }
            com.google.android.gms.internal.measurement.zzcc$zzk$zza r7 = r7.zza((java.lang.String) r8)     // Catch:{ all -> 0x0989 }
            java.lang.Object r8 = r3.get(r11)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzkt r8 = (com.google.android.gms.measurement.internal.zzkt) r8     // Catch:{ all -> 0x0989 }
            long r12 = r8.zzd     // Catch:{ all -> 0x0989 }
            com.google.android.gms.internal.measurement.zzcc$zzk$zza r7 = r7.zza((long) r12)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzks r8 = r27.zzh()     // Catch:{ all -> 0x0989 }
            java.lang.Object r12 = r3.get(r11)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzkt r12 = (com.google.android.gms.measurement.internal.zzkt) r12     // Catch:{ all -> 0x0989 }
            java.lang.Object r12 = r12.zze     // Catch:{ all -> 0x0989 }
            r8.zza((com.google.android.gms.internal.measurement.zzcc.zzk.zza) r7, (java.lang.Object) r12)     // Catch:{ all -> 0x0989 }
            r5.zza((com.google.android.gms.internal.measurement.zzcc.zzk.zza) r7)     // Catch:{ all -> 0x0989 }
            int r11 = r11 + 1
            goto L_0x0888
        L_0x08bf:
            com.google.android.gms.measurement.internal.zzad r3 = r27.zze()     // Catch:{ IOException -> 0x0939 }
            com.google.android.gms.internal.measurement.zzjj r7 = r5.zzv()     // Catch:{ IOException -> 0x0939 }
            com.google.android.gms.internal.measurement.zzib r7 = (com.google.android.gms.internal.measurement.zzib) r7     // Catch:{ IOException -> 0x0939 }
            com.google.android.gms.internal.measurement.zzcc$zzg r7 = (com.google.android.gms.internal.measurement.zzcc.zzg) r7     // Catch:{ IOException -> 0x0939 }
            long r7 = r3.zza((com.google.android.gms.internal.measurement.zzcc.zzg) r7)     // Catch:{ IOException -> 0x0939 }
            com.google.android.gms.measurement.internal.zzad r3 = r27.zze()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzan r5 = r4.zze     // Catch:{ all -> 0x0989 }
            if (r5 == 0) goto L_0x092f
            com.google.android.gms.measurement.internal.zzan r5 = r4.zze     // Catch:{ all -> 0x0989 }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ all -> 0x0989 }
        L_0x08df:
            boolean r11 = r5.hasNext()     // Catch:{ all -> 0x0989 }
            if (r11 == 0) goto L_0x08f4
            java.lang.Object r11 = r5.next()     // Catch:{ all -> 0x0989 }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ all -> 0x0989 }
            boolean r11 = r2.equals(r11)     // Catch:{ all -> 0x0989 }
            if (r11 == 0) goto L_0x08f3
            r11 = r6
            goto L_0x0930
        L_0x08f3:
            goto L_0x08df
        L_0x08f4:
            com.google.android.gms.measurement.internal.zzfx r2 = r27.zzc()     // Catch:{ all -> 0x0989 }
            java.lang.String r5 = r4.zza     // Catch:{ all -> 0x0989 }
            java.lang.String r11 = r4.zzb     // Catch:{ all -> 0x0989 }
            boolean r2 = r2.zzc(r5, r11)     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzad r11 = r27.zze()     // Catch:{ all -> 0x0989 }
            long r12 = r27.zzx()     // Catch:{ all -> 0x0989 }
            java.lang.String r14 = r4.zza     // Catch:{ all -> 0x0989 }
            r15 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            com.google.android.gms.measurement.internal.zzac r5 = r11.zza(r12, r14, r15, r16, r17, r18, r19)     // Catch:{ all -> 0x0989 }
            if (r2 == 0) goto L_0x092f
            long r11 = r5.zze     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzy r2 = r2.zzb()     // Catch:{ all -> 0x0989 }
            java.lang.String r5 = r4.zza     // Catch:{ all -> 0x0989 }
            int r2 = r2.zzc(r5)     // Catch:{ all -> 0x0989 }
            long r13 = (long) r2     // Catch:{ all -> 0x0989 }
            int r2 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r2 >= 0) goto L_0x092f
            r11 = r6
            goto L_0x0930
        L_0x092f:
            r11 = 0
        L_0x0930:
            boolean r2 = r3.zza((com.google.android.gms.measurement.internal.zzal) r4, (long) r7, (boolean) r11)     // Catch:{ all -> 0x0989 }
            if (r2 == 0) goto L_0x0954
            r1.zzm = r9     // Catch:{ all -> 0x0989 }
            goto L_0x0954
        L_0x0939:
            r0 = move-exception
            r2 = r0
            com.google.android.gms.measurement.internal.zzgd r3 = r1.zzj     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzez r3 = r3.zzr()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzfb r3 = r3.zzf()     // Catch:{ all -> 0x0989 }
            java.lang.String r4 = "Data loss. Failed to insert raw event metadata. appId"
            java.lang.String r5 = r5.zzj()     // Catch:{ all -> 0x0989 }
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r5)     // Catch:{ all -> 0x0989 }
            r3.zza(r4, r5, r2)     // Catch:{ all -> 0x0989 }
        L_0x0954:
            com.google.android.gms.measurement.internal.zzad r2 = r27.zze()     // Catch:{ all -> 0x0989 }
            r2.mo40209b_()     // Catch:{ all -> 0x0989 }
            com.google.android.gms.measurement.internal.zzad r2 = r27.zze()
            r2.zzh()
            r27.zzz()
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj
            com.google.android.gms.measurement.internal.zzez r2 = r2.zzr()
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzx()
            long r3 = java.lang.System.nanoTime()
            long r3 = r3 - r23
            r5 = 500000(0x7a120, double:2.47033E-318)
            long r3 = r3 + r5
            r5 = 1000000(0xf4240, double:4.940656E-318)
            long r3 = r3 / r5
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            java.lang.String r4 = "Background event processing time, ms"
            r2.zza(r4, r3)
            return
        L_0x0989:
            r0 = move-exception
            r2 = r0
            com.google.android.gms.measurement.internal.zzad r3 = r27.zze()
            r3.zzh()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzkk.zzc(com.google.android.gms.measurement.internal.zzao, com.google.android.gms.measurement.internal.zzn):void");
    }

    /* access modifiers changed from: package-private */
    public final void zzl() {
        String d_;
        zzf zzb2;
        String str;
        String str2;
        String zza2;
        zzw();
        zzk();
        this.zzs = true;
        try {
            this.zzj.zzu();
            Boolean zzag = this.zzj.zzw().zzag();
            if (zzag == null) {
                this.zzj.zzr().zzi().zza("Upload data called on the client side before use of service was decided");
                this.zzs = false;
                zzaa();
            } else if (zzag.booleanValue()) {
                this.zzj.zzr().zzf().zza("Upload called in the client side when service should be used");
                this.zzs = false;
                zzaa();
            } else if (this.zzm > 0) {
                zzz();
                this.zzs = false;
                zzaa();
            } else {
                zzw();
                if (this.zzv != null) {
                    this.zzj.zzr().zzx().zza("Uploading requested multiple times");
                    this.zzs = false;
                    zzaa();
                } else if (!zzd().zzf()) {
                    this.zzj.zzr().zzx().zza("Network not connected, ignoring upload request");
                    zzz();
                    this.zzs = false;
                    zzaa();
                } else {
                    long currentTimeMillis = this.zzj.zzm().currentTimeMillis();
                    int zzb3 = this.zzj.zzb().zzb((String) null, zzaq.zzap);
                    long zzv2 = currentTimeMillis - zzy.zzv();
                    for (int i = 0; i < zzb3 && zza((String) null, zzv2); i++) {
                    }
                    long zza3 = this.zzj.zzc().zzc.zza();
                    if (zza3 != 0) {
                        this.zzj.zzr().zzw().zza("Uploading events. Elapsed time since last upload attempt (ms)", Long.valueOf(Math.abs(currentTimeMillis - zza3)));
                    }
                    d_ = zze().mo40211d_();
                    if (!TextUtils.isEmpty(d_)) {
                        if (this.zzx == -1) {
                            this.zzx = zze().zzaa();
                        }
                        List<Pair<zzcc.zzg, Long>> zza4 = zze().zza(d_, this.zzj.zzb().zzb(d_, zzaq.zzf), Math.max(0, this.zzj.zzb().zzb(d_, zzaq.zzg)));
                        if (!zza4.isEmpty()) {
                            Iterator<Pair<zzcc.zzg, Long>> it = zza4.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    str = null;
                                    break;
                                }
                                zzcc.zzg zzg2 = (zzcc.zzg) it.next().first;
                                if (!TextUtils.isEmpty(zzg2.zzad())) {
                                    str = zzg2.zzad();
                                    break;
                                }
                            }
                            if (str != null) {
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= zza4.size()) {
                                        break;
                                    }
                                    zzcc.zzg zzg3 = (zzcc.zzg) zza4.get(i2).first;
                                    if (!TextUtils.isEmpty(zzg3.zzad()) && !zzg3.zzad().equals(str)) {
                                        zza4 = zza4.subList(0, i2);
                                        break;
                                    }
                                    i2++;
                                }
                            }
                            zzcc.zzf.zza zzb4 = zzcc.zzf.zzb();
                            int size = zza4.size();
                            ArrayList arrayList = new ArrayList(zza4.size());
                            boolean zzg4 = this.zzj.zzb().zzg(d_);
                            for (int i3 = 0; i3 < size; i3++) {
                                zzcc.zzg.zza zza5 = (zzcc.zzg.zza) ((zzcc.zzg) zza4.get(i3).first).zzbl();
                                arrayList.add((Long) zza4.get(i3).second);
                                zzcc.zzg.zza zza6 = zza5.zzg(this.zzj.zzb().zzf()).zza(currentTimeMillis);
                                this.zzj.zzu();
                                zza6.zzb(false);
                                if (!zzg4) {
                                    zza5.zzn();
                                }
                                if (this.zzj.zzb().zze(d_, zzaq.zzay)) {
                                    zza5.zzl(zzh().zza(((zzcc.zzg) ((zzib) zza5.zzv())).zzbi()));
                                }
                                zzb4.zza(zza5);
                            }
                            if (this.zzj.zzr().zza(2)) {
                                str2 = zzh().zza((zzcc.zzf) ((zzib) zzb4.zzv()));
                            } else {
                                str2 = null;
                            }
                            zzh();
                            byte[] zzbi = ((zzcc.zzf) ((zzib) zzb4.zzv())).zzbi();
                            zza2 = zzaq.zzp.zza(null);
                            URL url = new URL(zza2);
                            Preconditions.checkArgument(!arrayList.isEmpty());
                            if (this.zzv != null) {
                                this.zzj.zzr().zzf().zza("Set uploading progress before finishing the previous upload");
                            } else {
                                this.zzv = new ArrayList(arrayList);
                            }
                            this.zzj.zzc().zzd.zza(currentTimeMillis);
                            String str3 = "?";
                            if (size > 0) {
                                str3 = zzb4.zza(0).zzx();
                            }
                            this.zzj.zzr().zzx().zza("Uploading data. app, uncompressed size, data", str3, Integer.valueOf(zzbi.length), str2);
                            this.zzr = true;
                            zzfc zzd2 = zzd();
                            zzkm zzkm = new zzkm(this, d_);
                            zzd2.zzd();
                            zzd2.zzak();
                            Preconditions.checkNotNull(url);
                            Preconditions.checkNotNull(zzbi);
                            Preconditions.checkNotNull(zzkm);
                            zzd2.zzq().zzb((Runnable) new zzfg(zzd2, d_, url, zzbi, (Map<String, String>) null, zzkm));
                        }
                    } else {
                        this.zzx = -1;
                        String zza7 = zze().zza(currentTimeMillis - zzy.zzv());
                        if (!TextUtils.isEmpty(zza7) && (zzb2 = zze().zzb(zza7)) != null) {
                            zza(zzb2);
                        }
                    }
                    this.zzs = false;
                    zzaa();
                }
            }
        } catch (MalformedURLException e) {
            this.zzj.zzr().zzf().zza("Failed to parse upload URL. Not uploading. appId", zzez.zza(d_), zza2);
        } catch (Throwable th) {
            this.zzs = false;
            zzaa();
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:120:0x026d A[Catch:{ IOException -> 0x0229, all -> 0x1005 }] */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x027b A[Catch:{ IOException -> 0x0229, all -> 0x1005 }] */
    /* JADX WARNING: Removed duplicated region for block: B:173:0x03cf A[Catch:{ IOException -> 0x0229, all -> 0x1005 }] */
    /* JADX WARNING: Removed duplicated region for block: B:174:0x03d1 A[Catch:{ IOException -> 0x0229, all -> 0x1005 }] */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x03d4 A[Catch:{ IOException -> 0x0229, all -> 0x1005 }] */
    /* JADX WARNING: Removed duplicated region for block: B:177:0x03d5 A[Catch:{ IOException -> 0x0229, all -> 0x1005 }] */
    /* JADX WARNING: Removed duplicated region for block: B:226:0x05e8 A[Catch:{ IOException -> 0x0229, all -> 0x1005 }] */
    /* JADX WARNING: Removed duplicated region for block: B:262:0x06b7 A[Catch:{ IOException -> 0x0229, all -> 0x1005 }] */
    /* JADX WARNING: Removed duplicated region for block: B:265:0x06ce A[Catch:{ IOException -> 0x0229, all -> 0x1005 }] */
    /* JADX WARNING: Removed duplicated region for block: B:320:0x088e A[Catch:{ IOException -> 0x0229, all -> 0x1005 }] */
    /* JADX WARNING: Removed duplicated region for block: B:327:0x08aa A[Catch:{ IOException -> 0x0229, all -> 0x1005 }] */
    /* JADX WARNING: Removed duplicated region for block: B:328:0x08c5 A[Catch:{ IOException -> 0x0229, all -> 0x1005 }] */
    /* JADX WARNING: Removed duplicated region for block: B:570:0x0fe8  */
    /* JADX WARNING: Removed duplicated region for block: B:578:0x0fff A[SYNTHETIC, Splitter:B:578:0x0fff] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zza(java.lang.String r44, long r45) {
        /*
            r43 = this;
            r1 = r43
            java.lang.String r2 = "_npa"
            java.lang.String r3 = ""
            com.google.android.gms.measurement.internal.zzad r4 = r43.zze()
            r4.zzf()
            com.google.android.gms.measurement.internal.zzkk$zza r4 = new com.google.android.gms.measurement.internal.zzkk$zza     // Catch:{ all -> 0x1005 }
            r5 = 0
            r4.<init>(r1, r5)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzad r6 = r43.zze()     // Catch:{ all -> 0x1005 }
            long r7 = r1.zzx     // Catch:{ all -> 0x1005 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r4)     // Catch:{ all -> 0x1005 }
            r6.zzd()     // Catch:{ all -> 0x1005 }
            r6.zzak()     // Catch:{ all -> 0x1005 }
            r10 = -1
            r12 = 2
            r13 = 0
            r14 = 1
            android.database.sqlite.SQLiteDatabase r15 = r6.mo40210c_()     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            boolean r16 = android.text.TextUtils.isEmpty(r5)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            if (r16 == 0) goto L_0x009f
            int r16 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r16 == 0) goto L_0x004b
            java.lang.String[] r9 = new java.lang.String[r12]     // Catch:{ SQLiteException -> 0x0046 }
            java.lang.String r17 = java.lang.String.valueOf(r7)     // Catch:{ SQLiteException -> 0x0046 }
            r9[r13] = r17     // Catch:{ SQLiteException -> 0x0046 }
            java.lang.String r17 = java.lang.String.valueOf(r45)     // Catch:{ SQLiteException -> 0x0046 }
            r9[r14] = r17     // Catch:{ SQLiteException -> 0x0046 }
            goto L_0x0053
        L_0x0046:
            r0 = move-exception
            r7 = r0
            r9 = r5
            goto L_0x0252
        L_0x004b:
            java.lang.String[] r9 = new java.lang.String[r14]     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            java.lang.String r17 = java.lang.String.valueOf(r45)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            r9[r13] = r17     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
        L_0x0053:
            if (r16 == 0) goto L_0x005b
            java.lang.String r16 = "rowid <= ? and "
            r45 = r16
            goto L_0x005d
        L_0x005b:
            r45 = r3
        L_0x005d:
            java.lang.String r16 = java.lang.String.valueOf(r45)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            int r5 = r16.length()     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            int r5 = r5 + 148
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            r12.<init>(r5)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            java.lang.String r5 = "select app_id, metadata_fingerprint from raw_events where "
            java.lang.StringBuilder r5 = r12.append(r5)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            r12 = r45
            java.lang.StringBuilder r5 = r5.append(r12)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            java.lang.String r12 = "app_id in (select app_id from apps where config_fetched_time >= ?) order by rowid limit 1;"
            java.lang.StringBuilder r5 = r5.append(r12)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            java.lang.String r5 = r5.toString()     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            android.database.Cursor r5 = r15.rawQuery(r5, r9)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            boolean r9 = r5.moveToFirst()     // Catch:{ SQLiteException -> 0x0245 }
            if (r9 != 0) goto L_0x0093
            if (r5 == 0) goto L_0x0091
            r5.close()     // Catch:{ all -> 0x1005 }
        L_0x0091:
            goto L_0x0268
        L_0x0093:
            java.lang.String r9 = r5.getString(r13)     // Catch:{ SQLiteException -> 0x0245 }
            java.lang.String r12 = r5.getString(r14)     // Catch:{ SQLiteException -> 0x0242 }
            r5.close()     // Catch:{ SQLiteException -> 0x0242 }
            goto L_0x00f8
        L_0x009f:
            int r5 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r5 == 0) goto L_0x00b0
            r9 = 2
            java.lang.String[] r12 = new java.lang.String[r9]     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            r9 = 0
            r12[r13] = r9     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            java.lang.String r9 = java.lang.String.valueOf(r7)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            r12[r14] = r9     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            goto L_0x00b5
        L_0x00b0:
            r9 = 0
            java.lang.String[] r12 = new java.lang.String[]{r9}     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
        L_0x00b5:
            if (r5 == 0) goto L_0x00bb
            java.lang.String r5 = " and rowid <= ?"
            goto L_0x00bc
        L_0x00bb:
            r5 = r3
        L_0x00bc:
            java.lang.String r9 = java.lang.String.valueOf(r5)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            int r9 = r9.length()     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            int r9 = r9 + 84
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            r10.<init>(r9)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            java.lang.String r9 = "select metadata_fingerprint from raw_events where app_id = ?"
            java.lang.StringBuilder r9 = r10.append(r9)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            java.lang.StringBuilder r5 = r9.append(r5)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            java.lang.String r9 = " order by rowid limit 1;"
            java.lang.StringBuilder r5 = r5.append(r9)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            java.lang.String r5 = r5.toString()     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            android.database.Cursor r5 = r15.rawQuery(r5, r12)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0248 }
            boolean r9 = r5.moveToFirst()     // Catch:{ SQLiteException -> 0x0245 }
            if (r9 != 0) goto L_0x00f0
            if (r5 == 0) goto L_0x00ee
            r5.close()     // Catch:{ all -> 0x1005 }
        L_0x00ee:
            goto L_0x0268
        L_0x00f0:
            java.lang.String r12 = r5.getString(r13)     // Catch:{ SQLiteException -> 0x0245 }
            r5.close()     // Catch:{ SQLiteException -> 0x0245 }
            r9 = 0
        L_0x00f8:
            java.lang.String r16 = "raw_events_metadata"
            java.lang.String r10 = "metadata"
            java.lang.String[] r17 = new java.lang.String[]{r10}     // Catch:{ SQLiteException -> 0x0242 }
            java.lang.String r18 = "app_id = ? and metadata_fingerprint = ?"
            r10 = 2
            java.lang.String[] r11 = new java.lang.String[r10]     // Catch:{ SQLiteException -> 0x0242 }
            r11[r13] = r9     // Catch:{ SQLiteException -> 0x0242 }
            r11[r14] = r12     // Catch:{ SQLiteException -> 0x0242 }
            r20 = 0
            r21 = 0
            java.lang.String r22 = "rowid"
            java.lang.String r23 = "2"
            r10 = r15
            r19 = r11
            android.database.Cursor r5 = r15.query(r16, r17, r18, r19, r20, r21, r22, r23)     // Catch:{ SQLiteException -> 0x0242 }
            boolean r11 = r5.moveToFirst()     // Catch:{ SQLiteException -> 0x0242 }
            if (r11 != 0) goto L_0x0136
            com.google.android.gms.measurement.internal.zzez r7 = r6.zzr()     // Catch:{ SQLiteException -> 0x0242 }
            com.google.android.gms.measurement.internal.zzfb r7 = r7.zzf()     // Catch:{ SQLiteException -> 0x0242 }
            java.lang.String r8 = "Raw event metadata record is missing. appId"
            java.lang.Object r10 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r9)     // Catch:{ SQLiteException -> 0x0242 }
            r7.zza(r8, r10)     // Catch:{ SQLiteException -> 0x0242 }
            if (r5 == 0) goto L_0x0134
            r5.close()     // Catch:{ all -> 0x1005 }
        L_0x0134:
            goto L_0x0268
        L_0x0136:
            byte[] r11 = r5.getBlob(r13)     // Catch:{ SQLiteException -> 0x0242 }
            com.google.android.gms.internal.measurement.zzcc$zzg$zza r15 = com.google.android.gms.internal.measurement.zzcc.zzg.zzbf()     // Catch:{ IOException -> 0x0229 }
            com.google.android.gms.internal.measurement.zzjm r11 = com.google.android.gms.measurement.internal.zzks.zza(r15, (byte[]) r11)     // Catch:{ IOException -> 0x0229 }
            com.google.android.gms.internal.measurement.zzcc$zzg$zza r11 = (com.google.android.gms.internal.measurement.zzcc.zzg.zza) r11     // Catch:{ IOException -> 0x0229 }
            com.google.android.gms.internal.measurement.zzjj r11 = r11.zzv()     // Catch:{ IOException -> 0x0229 }
            com.google.android.gms.internal.measurement.zzib r11 = (com.google.android.gms.internal.measurement.zzib) r11     // Catch:{ IOException -> 0x0229 }
            com.google.android.gms.internal.measurement.zzcc$zzg r11 = (com.google.android.gms.internal.measurement.zzcc.zzg) r11     // Catch:{ IOException -> 0x0229 }
            boolean r15 = r5.moveToNext()     // Catch:{ SQLiteException -> 0x0242 }
            if (r15 == 0) goto L_0x0164
            com.google.android.gms.measurement.internal.zzez r15 = r6.zzr()     // Catch:{ SQLiteException -> 0x0242 }
            com.google.android.gms.measurement.internal.zzfb r15 = r15.zzi()     // Catch:{ SQLiteException -> 0x0242 }
            java.lang.String r14 = "Get multiple raw event metadata records, expected one. appId"
            java.lang.Object r13 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r9)     // Catch:{ SQLiteException -> 0x0242 }
            r15.zza(r14, r13)     // Catch:{ SQLiteException -> 0x0242 }
        L_0x0164:
            r5.close()     // Catch:{ SQLiteException -> 0x0242 }
            r4.zza(r11)     // Catch:{ SQLiteException -> 0x0242 }
            r13 = -1
            int r11 = (r7 > r13 ? 1 : (r7 == r13 ? 0 : -1))
            if (r11 == 0) goto L_0x0187
            java.lang.String r11 = "app_id = ? and metadata_fingerprint = ? and rowid <= ?"
            r13 = 3
            java.lang.String[] r14 = new java.lang.String[r13]     // Catch:{ SQLiteException -> 0x0242 }
            r13 = 0
            r14[r13] = r9     // Catch:{ SQLiteException -> 0x0242 }
            r13 = 1
            r14[r13] = r12     // Catch:{ SQLiteException -> 0x0242 }
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ SQLiteException -> 0x0242 }
            r8 = 2
            r14[r8] = r7     // Catch:{ SQLiteException -> 0x0242 }
            r18 = r11
            r19 = r14
            goto L_0x0196
        L_0x0187:
            java.lang.String r7 = "app_id = ? and metadata_fingerprint = ?"
            r8 = 2
            java.lang.String[] r11 = new java.lang.String[r8]     // Catch:{ SQLiteException -> 0x0242 }
            r8 = 0
            r11[r8] = r9     // Catch:{ SQLiteException -> 0x0242 }
            r8 = 1
            r11[r8] = r12     // Catch:{ SQLiteException -> 0x0242 }
            r18 = r7
            r19 = r11
        L_0x0196:
            java.lang.String r16 = "raw_events"
            java.lang.String r7 = "rowid"
            java.lang.String r8 = "name"
            java.lang.String r11 = "timestamp"
            java.lang.String r12 = "data"
            java.lang.String[] r17 = new java.lang.String[]{r7, r8, r11, r12}     // Catch:{ SQLiteException -> 0x0242 }
            r20 = 0
            r21 = 0
            java.lang.String r22 = "rowid"
            r23 = 0
            r15 = r10
            android.database.Cursor r5 = r15.query(r16, r17, r18, r19, r20, r21, r22, r23)     // Catch:{ SQLiteException -> 0x0242 }
            boolean r7 = r5.moveToFirst()     // Catch:{ SQLiteException -> 0x0242 }
            if (r7 != 0) goto L_0x01cf
            com.google.android.gms.measurement.internal.zzez r7 = r6.zzr()     // Catch:{ SQLiteException -> 0x0242 }
            com.google.android.gms.measurement.internal.zzfb r7 = r7.zzi()     // Catch:{ SQLiteException -> 0x0242 }
            java.lang.String r8 = "Raw event data disappeared while in transaction. appId"
            java.lang.Object r10 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r9)     // Catch:{ SQLiteException -> 0x0242 }
            r7.zza(r8, r10)     // Catch:{ SQLiteException -> 0x0242 }
            if (r5 == 0) goto L_0x01cd
            r5.close()     // Catch:{ all -> 0x1005 }
        L_0x01cd:
            goto L_0x0268
        L_0x01cf:
            r7 = 0
            long r10 = r5.getLong(r7)     // Catch:{ SQLiteException -> 0x0242 }
            r7 = 3
            byte[] r8 = r5.getBlob(r7)     // Catch:{ SQLiteException -> 0x0242 }
            com.google.android.gms.internal.measurement.zzcc$zzc$zza r7 = com.google.android.gms.internal.measurement.zzcc.zzc.zzj()     // Catch:{ IOException -> 0x0209 }
            com.google.android.gms.internal.measurement.zzjm r7 = com.google.android.gms.measurement.internal.zzks.zza(r7, (byte[]) r8)     // Catch:{ IOException -> 0x0209 }
            com.google.android.gms.internal.measurement.zzcc$zzc$zza r7 = (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r7     // Catch:{ IOException -> 0x0209 }
            r8 = 1
            java.lang.String r12 = r5.getString(r8)     // Catch:{ SQLiteException -> 0x0242 }
            com.google.android.gms.internal.measurement.zzcc$zzc$zza r8 = r7.zza((java.lang.String) r12)     // Catch:{ SQLiteException -> 0x0242 }
            r12 = 2
            long r13 = r5.getLong(r12)     // Catch:{ SQLiteException -> 0x0242 }
            r8.zza((long) r13)     // Catch:{ SQLiteException -> 0x0242 }
            com.google.android.gms.internal.measurement.zzjj r7 = r7.zzv()     // Catch:{ SQLiteException -> 0x0242 }
            com.google.android.gms.internal.measurement.zzib r7 = (com.google.android.gms.internal.measurement.zzib) r7     // Catch:{ SQLiteException -> 0x0242 }
            com.google.android.gms.internal.measurement.zzcc$zzc r7 = (com.google.android.gms.internal.measurement.zzcc.zzc) r7     // Catch:{ SQLiteException -> 0x0242 }
            boolean r7 = r4.zza(r10, r7)     // Catch:{ SQLiteException -> 0x0242 }
            if (r7 != 0) goto L_0x021d
            if (r5 == 0) goto L_0x0208
            r5.close()     // Catch:{ all -> 0x1005 }
        L_0x0208:
            goto L_0x0268
        L_0x0209:
            r0 = move-exception
            r7 = r0
            com.google.android.gms.measurement.internal.zzez r8 = r6.zzr()     // Catch:{ SQLiteException -> 0x0242 }
            com.google.android.gms.measurement.internal.zzfb r8 = r8.zzf()     // Catch:{ SQLiteException -> 0x0242 }
            java.lang.String r10 = "Data loss. Failed to merge raw event. appId"
            java.lang.Object r11 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r9)     // Catch:{ SQLiteException -> 0x0242 }
            r8.zza(r10, r11, r7)     // Catch:{ SQLiteException -> 0x0242 }
        L_0x021d:
            boolean r7 = r5.moveToNext()     // Catch:{ SQLiteException -> 0x0242 }
            if (r7 != 0) goto L_0x01cf
            if (r5 == 0) goto L_0x0268
            r5.close()     // Catch:{ all -> 0x1005 }
            goto L_0x0268
        L_0x0229:
            r0 = move-exception
            r7 = r0
            com.google.android.gms.measurement.internal.zzez r8 = r6.zzr()     // Catch:{ SQLiteException -> 0x0242 }
            com.google.android.gms.measurement.internal.zzfb r8 = r8.zzf()     // Catch:{ SQLiteException -> 0x0242 }
            java.lang.String r10 = "Data loss. Failed to merge raw event metadata. appId"
            java.lang.Object r11 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r9)     // Catch:{ SQLiteException -> 0x0242 }
            r8.zza(r10, r11, r7)     // Catch:{ SQLiteException -> 0x0242 }
            if (r5 == 0) goto L_0x0241
            r5.close()     // Catch:{ all -> 0x1005 }
        L_0x0241:
            goto L_0x0268
        L_0x0242:
            r0 = move-exception
            r7 = r0
            goto L_0x0252
        L_0x0245:
            r0 = move-exception
            r7 = r0
            goto L_0x0251
        L_0x0248:
            r0 = move-exception
            r2 = r1
            r5 = 0
        L_0x024b:
            r1 = r0
            goto L_0x0ffd
        L_0x024e:
            r0 = move-exception
            r7 = r0
            r5 = 0
        L_0x0251:
            r9 = 0
        L_0x0252:
            com.google.android.gms.measurement.internal.zzez r6 = r6.zzr()     // Catch:{ all -> 0x0ff9 }
            com.google.android.gms.measurement.internal.zzfb r6 = r6.zzf()     // Catch:{ all -> 0x0ff9 }
            java.lang.String r8 = "Data loss. Error selecting raw event. appId"
            java.lang.Object r9 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r9)     // Catch:{ all -> 0x0ff9 }
            r6.zza(r8, r9, r7)     // Catch:{ all -> 0x0ff9 }
            if (r5 == 0) goto L_0x0268
            r5.close()     // Catch:{ all -> 0x1005 }
        L_0x0268:
            java.util.List<com.google.android.gms.internal.measurement.zzcc$zzc> r5 = r4.zzc     // Catch:{ all -> 0x1005 }
            if (r5 == 0) goto L_0x0278
            java.util.List<com.google.android.gms.internal.measurement.zzcc$zzc> r5 = r4.zzc     // Catch:{ all -> 0x1005 }
            boolean r5 = r5.isEmpty()     // Catch:{ all -> 0x1005 }
            if (r5 == 0) goto L_0x0276
            goto L_0x0278
        L_0x0276:
            r5 = 0
            goto L_0x0279
        L_0x0278:
            r5 = 1
        L_0x0279:
            if (r5 != 0) goto L_0x0fe8
            com.google.android.gms.internal.measurement.zzcc$zzg r5 = r4.zza     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib$zza r5 = r5.zzbl()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib$zza r5 = (com.google.android.gms.internal.measurement.zzib.zza) r5     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg$zza r5 = (com.google.android.gms.internal.measurement.zzcc.zzg.zza) r5     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg$zza r5 = r5.zzc()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzgd r6 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzy r6 = r6.zzb()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg r7 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r7 = r7.zzx()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r8 = com.google.android.gms.measurement.internal.zzaq.zzau     // Catch:{ all -> 0x1005 }
            boolean r6 = r6.zze(r7, r8)     // Catch:{ all -> 0x1005 }
            r7 = -1
            r8 = -1
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
        L_0x02ac:
            java.util.List<com.google.android.gms.internal.measurement.zzcc$zzc> r9 = r4.zzc     // Catch:{ all -> 0x1005 }
            int r9 = r9.size()     // Catch:{ all -> 0x1005 }
            r18 = r3
            java.lang.String r3 = "_fr"
            r19 = r13
            java.lang.String r13 = "_et"
            r20 = r2
            java.lang.String r2 = "_e"
            r21 = r14
            r22 = r15
            if (r12 >= r9) goto L_0x0923
            java.util.List<com.google.android.gms.internal.measurement.zzcc$zzc> r9 = r4.zzc     // Catch:{ all -> 0x1005 }
            java.lang.Object r9 = r9.get(r12)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc r9 = (com.google.android.gms.internal.measurement.zzcc.zzc) r9     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib$zza r9 = r9.zzbl()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib$zza r9 = (com.google.android.gms.internal.measurement.zzib.zza) r9     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc$zza r9 = (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r9     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfx r14 = r43.zzc()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg r15 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r15 = r15.zzx()     // Catch:{ all -> 0x1005 }
            r16 = r12
            java.lang.String r12 = r9.zzd()     // Catch:{ all -> 0x1005 }
            boolean r12 = r14.zzb(r15, r12)     // Catch:{ all -> 0x1005 }
            java.lang.String r14 = "_err"
            if (r12 == 0) goto L_0x036d
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzez r2 = r2.zzr()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzi()     // Catch:{ all -> 0x1005 }
            java.lang.String r3 = "Dropping blacklisted raw event. appId"
            com.google.android.gms.internal.measurement.zzcc$zzg r12 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r12 = r12.zzx()     // Catch:{ all -> 0x1005 }
            java.lang.Object r12 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r12)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzgd r13 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzex r13 = r13.zzj()     // Catch:{ all -> 0x1005 }
            java.lang.String r15 = r9.zzd()     // Catch:{ all -> 0x1005 }
            java.lang.String r13 = r13.zza((java.lang.String) r15)     // Catch:{ all -> 0x1005 }
            r2.zza(r3, r12, r13)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfx r2 = r43.zzc()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg r3 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r3 = r3.zzx()     // Catch:{ all -> 0x1005 }
            boolean r2 = r2.zzg(r3)     // Catch:{ all -> 0x1005 }
            if (r2 != 0) goto L_0x0339
            com.google.android.gms.measurement.internal.zzfx r2 = r43.zzc()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg r3 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r3 = r3.zzx()     // Catch:{ all -> 0x1005 }
            boolean r2 = r2.zzh(r3)     // Catch:{ all -> 0x1005 }
            if (r2 == 0) goto L_0x0337
            goto L_0x0339
        L_0x0337:
            r2 = 0
            goto L_0x033a
        L_0x0339:
            r2 = 1
        L_0x033a:
            if (r2 != 0) goto L_0x0360
            java.lang.String r2 = r9.zzd()     // Catch:{ all -> 0x1005 }
            boolean r2 = r14.equals(r2)     // Catch:{ all -> 0x1005 }
            if (r2 != 0) goto L_0x0360
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzkw r24 = r2.zzi()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg r2 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r25 = r2.zzx()     // Catch:{ all -> 0x1005 }
            r26 = 11
            java.lang.String r27 = "_ev"
            java.lang.String r28 = r9.zzd()     // Catch:{ all -> 0x1005 }
            r29 = 0
            r24.zza((java.lang.String) r25, (int) r26, (java.lang.String) r27, (java.lang.String) r28, (int) r29)     // Catch:{ all -> 0x1005 }
        L_0x0360:
            r26 = r6
            r12 = r16
            r13 = r19
            r14 = r21
            r15 = r22
            r6 = r5
            goto L_0x0918
        L_0x036d:
            com.google.android.gms.measurement.internal.zzfx r12 = r43.zzc()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg r15 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r15 = r15.zzx()     // Catch:{ all -> 0x1005 }
            r26 = r6
            java.lang.String r6 = r9.zzd()     // Catch:{ all -> 0x1005 }
            boolean r6 = r12.zzc(r15, r6)     // Catch:{ all -> 0x1005 }
            java.lang.String r12 = "_c"
            if (r6 != 0) goto L_0x03e0
            r43.zzh()     // Catch:{ all -> 0x1005 }
            java.lang.String r15 = r9.zzd()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r15)     // Catch:{ all -> 0x1005 }
            r27 = r7
            int r7 = r15.hashCode()     // Catch:{ all -> 0x1005 }
            r28 = r10
            r10 = 94660(0x171c4, float:1.32647E-40)
            if (r7 == r10) goto L_0x03bc
            r10 = 95025(0x17331, float:1.33158E-40)
            if (r7 == r10) goto L_0x03b2
            r10 = 95027(0x17333, float:1.33161E-40)
            if (r7 == r10) goto L_0x03a8
        L_0x03a7:
            goto L_0x03c6
        L_0x03a8:
            java.lang.String r7 = "_ui"
            boolean r7 = r15.equals(r7)     // Catch:{ all -> 0x1005 }
            if (r7 == 0) goto L_0x03a7
            r7 = 1
            goto L_0x03c7
        L_0x03b2:
            java.lang.String r7 = "_ug"
            boolean r7 = r15.equals(r7)     // Catch:{ all -> 0x1005 }
            if (r7 == 0) goto L_0x03a7
            r7 = 2
            goto L_0x03c7
        L_0x03bc:
            java.lang.String r7 = "_in"
            boolean r7 = r15.equals(r7)     // Catch:{ all -> 0x1005 }
            if (r7 == 0) goto L_0x03a7
            r7 = 0
            goto L_0x03c7
        L_0x03c6:
            r7 = -1
        L_0x03c7:
            if (r7 == 0) goto L_0x03d1
            r10 = 1
            if (r7 == r10) goto L_0x03d1
            r10 = 2
            if (r7 == r10) goto L_0x03d1
            r7 = 0
            goto L_0x03d2
        L_0x03d1:
            r7 = 1
        L_0x03d2:
            if (r7 == 0) goto L_0x03d5
            goto L_0x03e4
        L_0x03d5:
            r30 = r5
            r31 = r8
            r10 = r11
            r29 = r13
            r13 = r2
            r11 = r3
            goto L_0x05e6
        L_0x03e0:
            r27 = r7
            r28 = r10
        L_0x03e4:
            r7 = 0
            r10 = 0
            r15 = 0
        L_0x03e9:
            r29 = r13
            int r13 = r9.zzb()     // Catch:{ all -> 0x1005 }
            r30 = r5
            java.lang.String r5 = "_r"
            if (r15 >= r13) goto L_0x0461
            com.google.android.gms.internal.measurement.zzcc$zze r13 = r9.zza((int) r15)     // Catch:{ all -> 0x1005 }
            java.lang.String r13 = r13.zzb()     // Catch:{ all -> 0x1005 }
            boolean r13 = r12.equals(r13)     // Catch:{ all -> 0x1005 }
            if (r13 == 0) goto L_0x0426
            com.google.android.gms.internal.measurement.zzcc$zze r5 = r9.zza((int) r15)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib$zza r5 = r5.zzbl()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib$zza r5 = (com.google.android.gms.internal.measurement.zzib.zza) r5     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze$zza r5 = (com.google.android.gms.internal.measurement.zzcc.zze.zza) r5     // Catch:{ all -> 0x1005 }
            r13 = r8
            r7 = 1
            com.google.android.gms.internal.measurement.zzcc$zze$zza r5 = r5.zza((long) r7)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzjj r5 = r5.zzv()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib r5 = (com.google.android.gms.internal.measurement.zzib) r5     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze r5 = (com.google.android.gms.internal.measurement.zzcc.zze) r5     // Catch:{ all -> 0x1005 }
            r9.zza((int) r15, (com.google.android.gms.internal.measurement.zzcc.zze) r5)     // Catch:{ all -> 0x1005 }
            r8 = r11
            r7 = 1
            goto L_0x0458
        L_0x0426:
            r13 = r8
            com.google.android.gms.internal.measurement.zzcc$zze r8 = r9.zza((int) r15)     // Catch:{ all -> 0x1005 }
            java.lang.String r8 = r8.zzb()     // Catch:{ all -> 0x1005 }
            boolean r5 = r5.equals(r8)     // Catch:{ all -> 0x1005 }
            if (r5 == 0) goto L_0x0457
            com.google.android.gms.internal.measurement.zzcc$zze r5 = r9.zza((int) r15)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib$zza r5 = r5.zzbl()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib$zza r5 = (com.google.android.gms.internal.measurement.zzib.zza) r5     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze$zza r5 = (com.google.android.gms.internal.measurement.zzcc.zze.zza) r5     // Catch:{ all -> 0x1005 }
            r8 = r11
            r10 = 1
            com.google.android.gms.internal.measurement.zzcc$zze$zza r5 = r5.zza((long) r10)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzjj r5 = r5.zzv()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib r5 = (com.google.android.gms.internal.measurement.zzib) r5     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze r5 = (com.google.android.gms.internal.measurement.zzcc.zze) r5     // Catch:{ all -> 0x1005 }
            r9.zza((int) r15, (com.google.android.gms.internal.measurement.zzcc.zze) r5)     // Catch:{ all -> 0x1005 }
            r10 = 1
            goto L_0x0458
        L_0x0457:
            r8 = r11
        L_0x0458:
            int r15 = r15 + 1
            r11 = r8
            r8 = r13
            r13 = r29
            r5 = r30
            goto L_0x03e9
        L_0x0461:
            r13 = r8
            r8 = r11
            if (r7 != 0) goto L_0x049c
            if (r6 == 0) goto L_0x049c
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzez r7 = r7.zzr()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfb r7 = r7.zzx()     // Catch:{ all -> 0x1005 }
            java.lang.String r11 = "Marking event as conversion"
            com.google.android.gms.measurement.internal.zzgd r15 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzex r15 = r15.zzj()     // Catch:{ all -> 0x1005 }
            r31 = r13
            java.lang.String r13 = r9.zzd()     // Catch:{ all -> 0x1005 }
            java.lang.String r13 = r15.zza((java.lang.String) r13)     // Catch:{ all -> 0x1005 }
            r7.zza(r11, r13)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze$zza r7 = com.google.android.gms.internal.measurement.zzcc.zze.zzm()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze$zza r7 = r7.zza((java.lang.String) r12)     // Catch:{ all -> 0x1005 }
            r13 = r2
            r11 = r3
            r2 = 1
            com.google.android.gms.internal.measurement.zzcc$zze$zza r7 = r7.zza((long) r2)     // Catch:{ all -> 0x1005 }
            r9.zza((com.google.android.gms.internal.measurement.zzcc.zze.zza) r7)     // Catch:{ all -> 0x1005 }
            goto L_0x04a0
        L_0x049c:
            r11 = r3
            r31 = r13
            r13 = r2
        L_0x04a0:
            if (r10 != 0) goto L_0x04d4
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzez r2 = r2.zzr()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzx()     // Catch:{ all -> 0x1005 }
            java.lang.String r3 = "Marking event as real-time"
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzex r7 = r7.zzj()     // Catch:{ all -> 0x1005 }
            java.lang.String r10 = r9.zzd()     // Catch:{ all -> 0x1005 }
            java.lang.String r7 = r7.zza((java.lang.String) r10)     // Catch:{ all -> 0x1005 }
            r2.zza(r3, r7)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze$zza r2 = com.google.android.gms.internal.measurement.zzcc.zze.zzm()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze$zza r2 = r2.zza((java.lang.String) r5)     // Catch:{ all -> 0x1005 }
            r3 = r8
            r7 = 1
            com.google.android.gms.internal.measurement.zzcc$zze$zza r2 = r2.zza((long) r7)     // Catch:{ all -> 0x1005 }
            r9.zza((com.google.android.gms.internal.measurement.zzcc.zze.zza) r2)     // Catch:{ all -> 0x1005 }
            goto L_0x04d5
        L_0x04d4:
            r3 = r8
        L_0x04d5:
            com.google.android.gms.measurement.internal.zzad r32 = r43.zze()     // Catch:{ all -> 0x1005 }
            long r33 = r43.zzx()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg r2 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r35 = r2.zzx()     // Catch:{ all -> 0x1005 }
            r36 = 0
            r37 = 0
            r38 = 0
            r39 = 0
            r40 = 1
            com.google.android.gms.measurement.internal.zzac r2 = r32.zza(r33, r35, r36, r37, r38, r39, r40)     // Catch:{ all -> 0x1005 }
            long r7 = r2.zze     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzy r2 = r2.zzb()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg r10 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r10 = r10.zzx()     // Catch:{ all -> 0x1005 }
            int r2 = r2.zzc(r10)     // Catch:{ all -> 0x1005 }
            r10 = r3
            long r2 = (long) r2     // Catch:{ all -> 0x1005 }
            int r2 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x050f
            zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r9, (java.lang.String) r5)     // Catch:{ all -> 0x1005 }
            goto L_0x0511
        L_0x050f:
            r19 = 1
        L_0x0511:
            java.lang.String r2 = r9.zzd()     // Catch:{ all -> 0x1005 }
            boolean r2 = com.google.android.gms.measurement.internal.zzkw.zza((java.lang.String) r2)     // Catch:{ all -> 0x1005 }
            if (r2 == 0) goto L_0x05e6
            if (r6 == 0) goto L_0x05e6
            com.google.android.gms.measurement.internal.zzad r32 = r43.zze()     // Catch:{ all -> 0x1005 }
            long r33 = r43.zzx()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg r2 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r35 = r2.zzx()     // Catch:{ all -> 0x1005 }
            r36 = 0
            r37 = 0
            r38 = 1
            r39 = 0
            r40 = 0
            com.google.android.gms.measurement.internal.zzac r2 = r32.zza(r33, r35, r36, r37, r38, r39, r40)     // Catch:{ all -> 0x1005 }
            long r2 = r2.zzc     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzgd r5 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzy r5 = r5.zzb()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg r7 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r7 = r7.zzx()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Integer> r8 = com.google.android.gms.measurement.internal.zzaq.zzm     // Catch:{ all -> 0x1005 }
            int r5 = r5.zzb(r7, r8)     // Catch:{ all -> 0x1005 }
            long r7 = (long) r5     // Catch:{ all -> 0x1005 }
            int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r2 <= 0) goto L_0x05e6
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzez r2 = r2.zzr()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzi()     // Catch:{ all -> 0x1005 }
            java.lang.String r3 = "Too many conversions. Not logging as conversion. appId"
            com.google.android.gms.internal.measurement.zzcc$zzg r5 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r5 = r5.zzx()     // Catch:{ all -> 0x1005 }
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r5)     // Catch:{ all -> 0x1005 }
            r2.zza(r3, r5)     // Catch:{ all -> 0x1005 }
            r2 = -1
            r3 = 0
            r5 = 0
            r7 = 0
        L_0x0574:
            int r8 = r9.zzb()     // Catch:{ all -> 0x1005 }
            if (r7 >= r8) goto L_0x05a2
            com.google.android.gms.internal.measurement.zzcc$zze r8 = r9.zza((int) r7)     // Catch:{ all -> 0x1005 }
            java.lang.String r15 = r8.zzb()     // Catch:{ all -> 0x1005 }
            boolean r15 = r12.equals(r15)     // Catch:{ all -> 0x1005 }
            if (r15 == 0) goto L_0x0594
            com.google.android.gms.internal.measurement.zzib$zza r2 = r8.zzbl()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib$zza r2 = (com.google.android.gms.internal.measurement.zzib.zza) r2     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze$zza r2 = (com.google.android.gms.internal.measurement.zzcc.zze.zza) r2     // Catch:{ all -> 0x1005 }
            r3 = r2
            r2 = r7
            goto L_0x059f
        L_0x0594:
            java.lang.String r8 = r8.zzb()     // Catch:{ all -> 0x1005 }
            boolean r8 = r14.equals(r8)     // Catch:{ all -> 0x1005 }
            if (r8 == 0) goto L_0x059f
            r5 = 1
        L_0x059f:
            int r7 = r7 + 1
            goto L_0x0574
        L_0x05a2:
            if (r5 == 0) goto L_0x05ab
            if (r3 == 0) goto L_0x05ab
            r9.zzb((int) r2)     // Catch:{ all -> 0x1005 }
            goto L_0x05e6
        L_0x05ab:
            if (r3 == 0) goto L_0x05cc
            java.lang.Object r3 = r3.clone()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib$zza r3 = (com.google.android.gms.internal.measurement.zzib.zza) r3     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze$zza r3 = (com.google.android.gms.internal.measurement.zzcc.zze.zza) r3     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze$zza r3 = r3.zza((java.lang.String) r14)     // Catch:{ all -> 0x1005 }
            r7 = 10
            com.google.android.gms.internal.measurement.zzcc$zze$zza r3 = r3.zza((long) r7)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzjj r3 = r3.zzv()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib r3 = (com.google.android.gms.internal.measurement.zzib) r3     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze r3 = (com.google.android.gms.internal.measurement.zzcc.zze) r3     // Catch:{ all -> 0x1005 }
            r9.zza((int) r2, (com.google.android.gms.internal.measurement.zzcc.zze) r3)     // Catch:{ all -> 0x1005 }
            goto L_0x05e6
        L_0x05cc:
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzez r2 = r2.zzr()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzf()     // Catch:{ all -> 0x1005 }
            java.lang.String r3 = "Did not find conversion parameter. appId"
            com.google.android.gms.internal.measurement.zzcc$zzg r5 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r5 = r5.zzx()     // Catch:{ all -> 0x1005 }
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r5)     // Catch:{ all -> 0x1005 }
            r2.zza(r3, r5)     // Catch:{ all -> 0x1005 }
        L_0x05e6:
            if (r6 == 0) goto L_0x06b7
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x1005 }
            java.util.List r3 = r9.zza()     // Catch:{ all -> 0x1005 }
            r2.<init>(r3)     // Catch:{ all -> 0x1005 }
            r3 = 0
            r5 = -1
            r6 = -1
        L_0x05f7:
            int r7 = r2.size()     // Catch:{ all -> 0x1005 }
            java.lang.String r8 = "currency"
            java.lang.String r14 = "value"
            if (r3 >= r7) goto L_0x0627
            java.lang.Object r7 = r2.get(r3)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze r7 = (com.google.android.gms.internal.measurement.zzcc.zze) r7     // Catch:{ all -> 0x1005 }
            java.lang.String r7 = r7.zzb()     // Catch:{ all -> 0x1005 }
            boolean r7 = r14.equals(r7)     // Catch:{ all -> 0x1005 }
            if (r7 == 0) goto L_0x0613
            r5 = r3
            goto L_0x0624
        L_0x0613:
            java.lang.Object r7 = r2.get(r3)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze r7 = (com.google.android.gms.internal.measurement.zzcc.zze) r7     // Catch:{ all -> 0x1005 }
            java.lang.String r7 = r7.zzb()     // Catch:{ all -> 0x1005 }
            boolean r7 = r8.equals(r7)     // Catch:{ all -> 0x1005 }
            if (r7 == 0) goto L_0x0624
            r6 = r3
        L_0x0624:
            int r3 = r3 + 1
            goto L_0x05f7
        L_0x0627:
            r3 = -1
            if (r5 == r3) goto L_0x06b5
            java.lang.Object r3 = r2.get(r5)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze r3 = (com.google.android.gms.internal.measurement.zzcc.zze) r3     // Catch:{ all -> 0x1005 }
            boolean r3 = r3.zze()     // Catch:{ all -> 0x1005 }
            if (r3 != 0) goto L_0x0661
            java.lang.Object r3 = r2.get(r5)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze r3 = (com.google.android.gms.internal.measurement.zzcc.zze) r3     // Catch:{ all -> 0x1005 }
            boolean r3 = r3.zzi()     // Catch:{ all -> 0x1005 }
            if (r3 != 0) goto L_0x0661
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzez r2 = r2.zzr()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzk()     // Catch:{ all -> 0x1005 }
            java.lang.String r3 = "Value must be specified with a numeric type."
            r2.zza(r3)     // Catch:{ all -> 0x1005 }
            r9.zzb((int) r5)     // Catch:{ all -> 0x1005 }
            zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r9, (java.lang.String) r12)     // Catch:{ all -> 0x1005 }
            r2 = 18
            zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r9, (int) r2, (java.lang.String) r14)     // Catch:{ all -> 0x1005 }
            r3 = -1
            r7 = 3
            goto L_0x06b9
        L_0x0661:
            r3 = -1
            if (r6 != r3) goto L_0x0668
            r2 = 1
            r7 = 3
            goto L_0x0696
        L_0x0668:
            java.lang.Object r2 = r2.get(r6)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze r2 = (com.google.android.gms.internal.measurement.zzcc.zze) r2     // Catch:{ all -> 0x1005 }
            java.lang.String r2 = r2.zzd()     // Catch:{ all -> 0x1005 }
            int r6 = r2.length()     // Catch:{ all -> 0x1005 }
            r7 = 3
            if (r6 == r7) goto L_0x067b
            r2 = 1
            goto L_0x0696
        L_0x067b:
            r6 = 0
        L_0x067c:
            int r14 = r2.length()     // Catch:{ all -> 0x1005 }
            if (r6 >= r14) goto L_0x0695
            int r14 = r2.codePointAt(r6)     // Catch:{ all -> 0x1005 }
            boolean r15 = java.lang.Character.isLetter(r14)     // Catch:{ all -> 0x1005 }
            if (r15 != 0) goto L_0x068f
            r2 = 1
            goto L_0x0696
        L_0x068f:
            int r14 = java.lang.Character.charCount(r14)     // Catch:{ all -> 0x1005 }
            int r6 = r6 + r14
            goto L_0x067c
        L_0x0695:
            r2 = 0
        L_0x0696:
            if (r2 == 0) goto L_0x06b9
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzez r2 = r2.zzr()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzk()     // Catch:{ all -> 0x1005 }
            java.lang.String r6 = "Value parameter discarded. You must also supply a 3-letter ISO_4217 currency code in the currency parameter."
            r2.zza(r6)     // Catch:{ all -> 0x1005 }
            r9.zzb((int) r5)     // Catch:{ all -> 0x1005 }
            zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r9, (java.lang.String) r12)     // Catch:{ all -> 0x1005 }
            r2 = 19
            zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r9, (int) r2, (java.lang.String) r8)     // Catch:{ all -> 0x1005 }
            goto L_0x06b9
        L_0x06b5:
            r7 = 3
            goto L_0x06b9
        L_0x06b7:
            r3 = -1
            r7 = 3
        L_0x06b9:
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzy r2 = r2.zzb()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg r5 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r5 = r5.zzx()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r6 = com.google.android.gms.measurement.internal.zzaq.zzat     // Catch:{ all -> 0x1005 }
            boolean r2 = r2.zze(r5, r6)     // Catch:{ all -> 0x1005 }
            if (r2 == 0) goto L_0x088e
            java.lang.String r2 = r9.zzd()     // Catch:{ all -> 0x1005 }
            r5 = r13
            boolean r2 = r5.equals(r2)     // Catch:{ all -> 0x1005 }
            r12 = 1000(0x3e8, double:4.94E-321)
            if (r2 == 0) goto L_0x0743
            r43.zzh()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzjj r2 = r9.zzv()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib r2 = (com.google.android.gms.internal.measurement.zzib) r2     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc r2 = (com.google.android.gms.internal.measurement.zzcc.zzc) r2     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze r2 = com.google.android.gms.measurement.internal.zzks.zza((com.google.android.gms.internal.measurement.zzcc.zzc) r2, (java.lang.String) r11)     // Catch:{ all -> 0x1005 }
            if (r2 != 0) goto L_0x0739
            if (r10 == 0) goto L_0x072b
            long r14 = r10.zzf()     // Catch:{ all -> 0x1005 }
            long r24 = r9.zzf()     // Catch:{ all -> 0x1005 }
            long r14 = r14 - r24
            long r14 = java.lang.Math.abs(r14)     // Catch:{ all -> 0x1005 }
            int r2 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r2 > 0) goto L_0x0726
            java.lang.Object r2 = r10.clone()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib$zza r2 = (com.google.android.gms.internal.measurement.zzib.zza) r2     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc$zza r2 = (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r2     // Catch:{ all -> 0x1005 }
            boolean r6 = r1.zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r9, (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r2)     // Catch:{ all -> 0x1005 }
            if (r6 == 0) goto L_0x0720
            r6 = r30
            r8 = r31
            r6.zza((int) r8, (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r2)     // Catch:{ all -> 0x1005 }
            r7 = r27
            r14 = r29
            r10 = 0
            r28 = 0
            goto L_0x0898
        L_0x0720:
            r6 = r30
            r8 = r31
            goto L_0x0731
        L_0x0726:
            r6 = r30
            r8 = r31
            goto L_0x072f
        L_0x072b:
            r6 = r30
            r8 = r31
        L_0x072f:
        L_0x0731:
            r28 = r9
            r7 = r21
            r14 = r29
            goto L_0x0898
        L_0x0739:
            r6 = r30
            r8 = r31
            r11 = r27
            r14 = r29
            goto L_0x0897
        L_0x0743:
            r6 = r30
            r8 = r31
            java.lang.String r2 = "_vs"
            java.lang.String r11 = r9.zzd()     // Catch:{ all -> 0x1005 }
            boolean r2 = r2.equals(r11)     // Catch:{ all -> 0x1005 }
            if (r2 == 0) goto L_0x07aa
            r43.zzh()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzjj r2 = r9.zzv()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib r2 = (com.google.android.gms.internal.measurement.zzib) r2     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc r2 = (com.google.android.gms.internal.measurement.zzcc.zzc) r2     // Catch:{ all -> 0x1005 }
            r14 = r29
            com.google.android.gms.internal.measurement.zzcc$zze r2 = com.google.android.gms.measurement.internal.zzks.zza((com.google.android.gms.internal.measurement.zzcc.zzc) r2, (java.lang.String) r14)     // Catch:{ all -> 0x1005 }
            if (r2 != 0) goto L_0x07a6
            if (r28 == 0) goto L_0x079c
            long r10 = r28.zzf()     // Catch:{ all -> 0x1005 }
            long r24 = r9.zzf()     // Catch:{ all -> 0x1005 }
            long r10 = r10 - r24
            long r10 = java.lang.Math.abs(r10)     // Catch:{ all -> 0x1005 }
            int r2 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r2 > 0) goto L_0x0799
            java.lang.Object r2 = r28.clone()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib$zza r2 = (com.google.android.gms.internal.measurement.zzib.zza) r2     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc$zza r2 = (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r2     // Catch:{ all -> 0x1005 }
            boolean r10 = r1.zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r2, (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r9)     // Catch:{ all -> 0x1005 }
            if (r10 == 0) goto L_0x0795
            r11 = r27
            r6.zza((int) r11, (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r2)     // Catch:{ all -> 0x1005 }
            r7 = r11
            r10 = 0
            r28 = 0
            goto L_0x0898
        L_0x0795:
            r11 = r27
            goto L_0x07a0
        L_0x0799:
            r11 = r27
            goto L_0x079e
        L_0x079c:
            r11 = r27
        L_0x079e:
        L_0x07a0:
            r10 = r9
            r7 = r11
            r8 = r21
            goto L_0x0898
        L_0x07a6:
            r11 = r27
            goto L_0x0897
        L_0x07aa:
            r11 = r27
            r14 = r29
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzy r2 = r2.zzb()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg r12 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r12 = r12.zzx()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r13 = com.google.android.gms.measurement.internal.zzaq.zzbr     // Catch:{ all -> 0x1005 }
            boolean r2 = r2.zze(r12, r13)     // Catch:{ all -> 0x1005 }
            if (r2 == 0) goto L_0x0897
            java.lang.String r2 = "_ab"
            java.lang.String r12 = r9.zzd()     // Catch:{ all -> 0x1005 }
            boolean r2 = r2.equals(r12)     // Catch:{ all -> 0x1005 }
            if (r2 == 0) goto L_0x0897
            r43.zzh()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzjj r2 = r9.zzv()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib r2 = (com.google.android.gms.internal.measurement.zzib) r2     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc r2 = (com.google.android.gms.internal.measurement.zzcc.zzc) r2     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze r2 = com.google.android.gms.measurement.internal.zzks.zza((com.google.android.gms.internal.measurement.zzcc.zzc) r2, (java.lang.String) r14)     // Catch:{ all -> 0x1005 }
            if (r2 != 0) goto L_0x0897
            if (r28 == 0) goto L_0x0897
            long r12 = r28.zzf()     // Catch:{ all -> 0x1005 }
            long r24 = r9.zzf()     // Catch:{ all -> 0x1005 }
            long r12 = r12 - r24
            long r12 = java.lang.Math.abs(r12)     // Catch:{ all -> 0x1005 }
            r24 = 4000(0xfa0, double:1.9763E-320)
            int r2 = (r12 > r24 ? 1 : (r12 == r24 ? 0 : -1))
            if (r2 > 0) goto L_0x0897
            java.lang.Object r2 = r28.clone()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib$zza r2 = (com.google.android.gms.internal.measurement.zzib.zza) r2     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc$zza r2 = (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r2     // Catch:{ all -> 0x1005 }
            r1.zzb((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r2, (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r9)     // Catch:{ all -> 0x1005 }
            java.lang.String r12 = r2.zzd()     // Catch:{ all -> 0x1005 }
            boolean r12 = r5.equals(r12)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.common.internal.Preconditions.checkArgument(r12)     // Catch:{ all -> 0x1005 }
            r43.zzh()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzjj r12 = r2.zzv()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib r12 = (com.google.android.gms.internal.measurement.zzib) r12     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc r12 = (com.google.android.gms.internal.measurement.zzcc.zzc) r12     // Catch:{ all -> 0x1005 }
            java.lang.String r13 = "_sn"
            com.google.android.gms.internal.measurement.zzcc$zze r12 = com.google.android.gms.measurement.internal.zzks.zza((com.google.android.gms.internal.measurement.zzcc.zzc) r12, (java.lang.String) r13)     // Catch:{ all -> 0x1005 }
            r43.zzh()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzjj r13 = r2.zzv()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib r13 = (com.google.android.gms.internal.measurement.zzib) r13     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc r13 = (com.google.android.gms.internal.measurement.zzcc.zzc) r13     // Catch:{ all -> 0x1005 }
            java.lang.String r15 = "_sc"
            com.google.android.gms.internal.measurement.zzcc$zze r13 = com.google.android.gms.measurement.internal.zzks.zza((com.google.android.gms.internal.measurement.zzcc.zzc) r13, (java.lang.String) r15)     // Catch:{ all -> 0x1005 }
            r43.zzh()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzjj r15 = r2.zzv()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib r15 = (com.google.android.gms.internal.measurement.zzib) r15     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc r15 = (com.google.android.gms.internal.measurement.zzcc.zzc) r15     // Catch:{ all -> 0x1005 }
            java.lang.String r3 = "_si"
            com.google.android.gms.internal.measurement.zzcc$zze r3 = com.google.android.gms.measurement.internal.zzks.zza((com.google.android.gms.internal.measurement.zzcc.zzc) r15, (java.lang.String) r3)     // Catch:{ all -> 0x1005 }
            if (r12 == 0) goto L_0x084a
            java.lang.String r12 = r12.zzd()     // Catch:{ all -> 0x1005 }
            goto L_0x084c
        L_0x084a:
            r12 = r18
        L_0x084c:
            boolean r15 = android.text.TextUtils.isEmpty(r12)     // Catch:{ all -> 0x1005 }
            if (r15 != 0) goto L_0x085b
            com.google.android.gms.measurement.internal.zzks r15 = r43.zzh()     // Catch:{ all -> 0x1005 }
            java.lang.String r7 = "_sn"
            r15.zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r9, (java.lang.String) r7, (java.lang.Object) r12)     // Catch:{ all -> 0x1005 }
        L_0x085b:
            if (r13 == 0) goto L_0x0862
            java.lang.String r7 = r13.zzd()     // Catch:{ all -> 0x1005 }
            goto L_0x0864
        L_0x0862:
            r7 = r18
        L_0x0864:
            boolean r12 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x1005 }
            if (r12 != 0) goto L_0x0873
            com.google.android.gms.measurement.internal.zzks r12 = r43.zzh()     // Catch:{ all -> 0x1005 }
            java.lang.String r13 = "_sc"
            r12.zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r9, (java.lang.String) r13, (java.lang.Object) r7)     // Catch:{ all -> 0x1005 }
        L_0x0873:
            if (r3 == 0) goto L_0x0886
            com.google.android.gms.measurement.internal.zzks r7 = r43.zzh()     // Catch:{ all -> 0x1005 }
            java.lang.String r12 = "_si"
            long r24 = r3.zzf()     // Catch:{ all -> 0x1005 }
            java.lang.Long r3 = java.lang.Long.valueOf(r24)     // Catch:{ all -> 0x1005 }
            r7.zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r9, (java.lang.String) r12, (java.lang.Object) r3)     // Catch:{ all -> 0x1005 }
        L_0x0886:
            r6.zza((int) r11, (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r2)     // Catch:{ all -> 0x1005 }
            r7 = r11
            r28 = 0
            goto L_0x0898
        L_0x088e:
            r5 = r13
            r11 = r27
            r14 = r29
            r6 = r30
            r8 = r31
        L_0x0897:
            r7 = r11
        L_0x0898:
            if (r26 != 0) goto L_0x08fc
            java.lang.String r2 = r9.zzd()     // Catch:{ all -> 0x1005 }
            boolean r2 = r5.equals(r2)     // Catch:{ all -> 0x1005 }
            if (r2 == 0) goto L_0x08fc
            int r2 = r9.zzb()     // Catch:{ all -> 0x1005 }
            if (r2 != 0) goto L_0x08c5
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzez r2 = r2.zzr()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzi()     // Catch:{ all -> 0x1005 }
            java.lang.String r3 = "Engagement event does not contain any parameters. appId"
            com.google.android.gms.internal.measurement.zzcc$zzg r5 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r5 = r5.zzx()     // Catch:{ all -> 0x1005 }
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r5)     // Catch:{ all -> 0x1005 }
            r2.zza(r3, r5)     // Catch:{ all -> 0x1005 }
            goto L_0x08fc
        L_0x08c5:
            com.google.android.gms.measurement.internal.zzks r2 = r43.zzh()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzjj r3 = r9.zzv()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib r3 = (com.google.android.gms.internal.measurement.zzib) r3     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc r3 = (com.google.android.gms.internal.measurement.zzcc.zzc) r3     // Catch:{ all -> 0x1005 }
            java.lang.Object r2 = r2.zzb(r3, r14)     // Catch:{ all -> 0x1005 }
            java.lang.Long r2 = (java.lang.Long) r2     // Catch:{ all -> 0x1005 }
            if (r2 != 0) goto L_0x08f5
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzez r2 = r2.zzr()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzi()     // Catch:{ all -> 0x1005 }
            java.lang.String r3 = "Engagement event does not include duration. appId"
            com.google.android.gms.internal.measurement.zzcc$zzg r5 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r5 = r5.zzx()     // Catch:{ all -> 0x1005 }
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r5)     // Catch:{ all -> 0x1005 }
            r2.zza(r3, r5)     // Catch:{ all -> 0x1005 }
            goto L_0x08fc
        L_0x08f5:
            long r2 = r2.longValue()     // Catch:{ all -> 0x1005 }
            long r2 = r22 + r2
            goto L_0x08fe
        L_0x08fc:
            r2 = r22
        L_0x08fe:
            java.util.List<com.google.android.gms.internal.measurement.zzcc$zzc> r5 = r4.zzc     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzjj r11 = r9.zzv()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib r11 = (com.google.android.gms.internal.measurement.zzib) r11     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc r11 = (com.google.android.gms.internal.measurement.zzcc.zzc) r11     // Catch:{ all -> 0x1005 }
            r12 = r16
            r5.set(r12, r11)     // Catch:{ all -> 0x1005 }
            int r14 = r21 + 1
            r6.zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r9)     // Catch:{ all -> 0x1005 }
            r15 = r2
            r11 = r10
            r13 = r19
            r10 = r28
        L_0x0918:
            int r12 = r12 + 1
            r5 = r6
            r3 = r18
            r2 = r20
            r6 = r26
            goto L_0x02ac
        L_0x0923:
            r11 = r3
            r26 = r6
            r14 = r13
            r6 = r5
            r5 = r2
            if (r26 == 0) goto L_0x0981
            r2 = r21
            r15 = r22
            r3 = 0
        L_0x0930:
            if (r3 >= r2) goto L_0x097f
            com.google.android.gms.internal.measurement.zzcc$zzc r7 = r6.zzb((int) r3)     // Catch:{ all -> 0x1005 }
            java.lang.String r8 = r7.zzc()     // Catch:{ all -> 0x1005 }
            boolean r8 = r5.equals(r8)     // Catch:{ all -> 0x1005 }
            if (r8 == 0) goto L_0x0951
            r43.zzh()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze r8 = com.google.android.gms.measurement.internal.zzks.zza((com.google.android.gms.internal.measurement.zzcc.zzc) r7, (java.lang.String) r11)     // Catch:{ all -> 0x1005 }
            if (r8 == 0) goto L_0x0951
            r6.zzc((int) r3)     // Catch:{ all -> 0x1005 }
            int r2 = r2 + -1
            int r3 = r3 + -1
            goto L_0x097c
        L_0x0951:
            r43.zzh()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze r7 = com.google.android.gms.measurement.internal.zzks.zza((com.google.android.gms.internal.measurement.zzcc.zzc) r7, (java.lang.String) r14)     // Catch:{ all -> 0x1005 }
            if (r7 == 0) goto L_0x097c
            boolean r8 = r7.zze()     // Catch:{ all -> 0x1005 }
            if (r8 == 0) goto L_0x096a
            long r7 = r7.zzf()     // Catch:{ all -> 0x1005 }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x1005 }
            goto L_0x096b
        L_0x096a:
            r7 = 0
        L_0x096b:
            if (r7 == 0) goto L_0x097c
            long r8 = r7.longValue()     // Catch:{ all -> 0x1005 }
            r12 = 0
            int r8 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r8 <= 0) goto L_0x097c
            long r7 = r7.longValue()     // Catch:{ all -> 0x1005 }
            long r15 = r15 + r7
        L_0x097c:
            r7 = 1
            int r3 = r3 + r7
            goto L_0x0930
        L_0x097f:
            r2 = r15
            goto L_0x0983
        L_0x0981:
            r2 = r22
        L_0x0983:
            r5 = 0
            r1.zza((com.google.android.gms.internal.measurement.zzcc.zzg.zza) r6, (long) r2, (boolean) r5)     // Catch:{ all -> 0x1005 }
            java.util.List r5 = r6.zza()     // Catch:{ all -> 0x1005 }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ all -> 0x1005 }
        L_0x0990:
            boolean r7 = r5.hasNext()     // Catch:{ all -> 0x1005 }
            if (r7 == 0) goto L_0x09ac
            java.lang.Object r7 = r5.next()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc r7 = (com.google.android.gms.internal.measurement.zzcc.zzc) r7     // Catch:{ all -> 0x1005 }
            java.lang.String r8 = "_s"
            java.lang.String r7 = r7.zzc()     // Catch:{ all -> 0x1005 }
            boolean r7 = r8.equals(r7)     // Catch:{ all -> 0x1005 }
            if (r7 == 0) goto L_0x09ab
            r5 = 1
            goto L_0x09ad
        L_0x09ab:
            goto L_0x0990
        L_0x09ac:
            r5 = 0
        L_0x09ad:
            java.lang.String r7 = "_se"
            if (r5 == 0) goto L_0x09bc
            com.google.android.gms.measurement.internal.zzad r5 = r43.zze()     // Catch:{ all -> 0x1005 }
            java.lang.String r8 = r6.zzj()     // Catch:{ all -> 0x1005 }
            r5.zzb((java.lang.String) r8, (java.lang.String) r7)     // Catch:{ all -> 0x1005 }
        L_0x09bc:
            java.lang.String r5 = "_sid"
            int r5 = com.google.android.gms.measurement.internal.zzks.zza((com.google.android.gms.internal.measurement.zzcc.zzg.zza) r6, (java.lang.String) r5)     // Catch:{ all -> 0x1005 }
            if (r5 < 0) goto L_0x09c6
            r5 = 1
            goto L_0x09c7
        L_0x09c6:
            r5 = 0
        L_0x09c7:
            if (r5 == 0) goto L_0x09ce
            r5 = 1
            r1.zza((com.google.android.gms.internal.measurement.zzcc.zzg.zza) r6, (long) r2, (boolean) r5)     // Catch:{ all -> 0x1005 }
            goto L_0x09f2
        L_0x09ce:
            int r2 = com.google.android.gms.measurement.internal.zzks.zza((com.google.android.gms.internal.measurement.zzcc.zzg.zza) r6, (java.lang.String) r7)     // Catch:{ all -> 0x1005 }
            if (r2 < 0) goto L_0x09f2
            r6.zze((int) r2)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzez r2 = r2.zzr()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzf()     // Catch:{ all -> 0x1005 }
            java.lang.String r3 = "Session engagement user property is in the bundle without session ID. appId"
            com.google.android.gms.internal.measurement.zzcc$zzg r5 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r5 = r5.zzx()     // Catch:{ all -> 0x1005 }
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r5)     // Catch:{ all -> 0x1005 }
            r2.zza(r3, r5)     // Catch:{ all -> 0x1005 }
        L_0x09f2:
            com.google.android.gms.measurement.internal.zzks r2 = r43.zzh()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzez r3 = r2.zzr()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfb r3 = r3.zzx()     // Catch:{ all -> 0x1005 }
            java.lang.String r5 = "Checking account type status for ad personalization signals"
            r3.zza(r5)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfx r3 = r2.zzj()     // Catch:{ all -> 0x1005 }
            java.lang.String r5 = r6.zzj()     // Catch:{ all -> 0x1005 }
            boolean r3 = r3.zze(r5)     // Catch:{ all -> 0x1005 }
            if (r3 == 0) goto L_0x0a86
            com.google.android.gms.measurement.internal.zzad r3 = r2.zzi()     // Catch:{ all -> 0x1005 }
            java.lang.String r5 = r6.zzj()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzf r3 = r3.zzb(r5)     // Catch:{ all -> 0x1005 }
            if (r3 == 0) goto L_0x0a86
            boolean r3 = r3.zzaf()     // Catch:{ all -> 0x1005 }
            if (r3 == 0) goto L_0x0a86
            com.google.android.gms.measurement.internal.zzai r3 = r2.zzl()     // Catch:{ all -> 0x1005 }
            boolean r3 = r3.zzj()     // Catch:{ all -> 0x1005 }
            if (r3 == 0) goto L_0x0a86
            com.google.android.gms.measurement.internal.zzez r3 = r2.zzr()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfb r3 = r3.zzw()     // Catch:{ all -> 0x1005 }
            java.lang.String r5 = "Turning off ad personalization due to account type"
            r3.zza(r5)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzk$zza r3 = com.google.android.gms.internal.measurement.zzcc.zzk.zzj()     // Catch:{ all -> 0x1005 }
            r5 = r20
            com.google.android.gms.internal.measurement.zzcc$zzk$zza r3 = r3.zza((java.lang.String) r5)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzai r2 = r2.zzl()     // Catch:{ all -> 0x1005 }
            long r7 = r2.zzh()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzk$zza r2 = r3.zza((long) r7)     // Catch:{ all -> 0x1005 }
            r7 = 1
            com.google.android.gms.internal.measurement.zzcc$zzk$zza r2 = r2.zzb((long) r7)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzjj r2 = r2.zzv()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib r2 = (com.google.android.gms.internal.measurement.zzib) r2     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzk r2 = (com.google.android.gms.internal.measurement.zzcc.zzk) r2     // Catch:{ all -> 0x1005 }
            r3 = 0
        L_0x0a62:
            int r7 = r6.zze()     // Catch:{ all -> 0x1005 }
            if (r3 >= r7) goto L_0x0a80
            com.google.android.gms.internal.measurement.zzcc$zzk r7 = r6.zzd((int) r3)     // Catch:{ all -> 0x1005 }
            java.lang.String r7 = r7.zzc()     // Catch:{ all -> 0x1005 }
            boolean r7 = r5.equals(r7)     // Catch:{ all -> 0x1005 }
            if (r7 == 0) goto L_0x0a7d
            r6.zza((int) r3, (com.google.android.gms.internal.measurement.zzcc.zzk) r2)     // Catch:{ all -> 0x1005 }
            r3 = 1
            goto L_0x0a81
        L_0x0a7d:
            int r3 = r3 + 1
            goto L_0x0a62
        L_0x0a80:
            r3 = 0
        L_0x0a81:
            if (r3 != 0) goto L_0x0a86
            r6.zza((com.google.android.gms.internal.measurement.zzcc.zzk) r2)     // Catch:{ all -> 0x1005 }
        L_0x0a86:
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzy r2 = r2.zzb()     // Catch:{ all -> 0x1005 }
            java.lang.String r3 = r6.zzj()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r5 = com.google.android.gms.measurement.internal.zzaq.zzbm     // Catch:{ all -> 0x1005 }
            boolean r2 = r2.zze(r3, r5)     // Catch:{ all -> 0x1005 }
            if (r2 == 0) goto L_0x0a9d
            zza((com.google.android.gms.internal.measurement.zzcc.zzg.zza) r6)     // Catch:{ all -> 0x1005 }
        L_0x0a9d:
            com.google.android.gms.internal.measurement.zzcc$zzg$zza r2 = r6.zzm()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzo r7 = r43.zzf()     // Catch:{ all -> 0x1005 }
            java.lang.String r8 = r6.zzj()     // Catch:{ all -> 0x1005 }
            java.util.List r9 = r6.zza()     // Catch:{ all -> 0x1005 }
            java.util.List r10 = r6.zzd()     // Catch:{ all -> 0x1005 }
            long r11 = r6.zzf()     // Catch:{ all -> 0x1005 }
            java.lang.Long r11 = java.lang.Long.valueOf(r11)     // Catch:{ all -> 0x1005 }
            long r12 = r6.zzg()     // Catch:{ all -> 0x1005 }
            java.lang.Long r12 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x1005 }
            java.util.List r3 = r7.zza(r8, r9, r10, r11, r12)     // Catch:{ all -> 0x1005 }
            r2.zzc((java.lang.Iterable<? extends com.google.android.gms.internal.measurement.zzcc.zza>) r3)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzgd r2 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzy r2 = r2.zzb()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg r3 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r3 = r3.zzx()     // Catch:{ all -> 0x1005 }
            boolean r2 = r2.zzh(r3)     // Catch:{ all -> 0x1005 }
            if (r2 == 0) goto L_0x0e38
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ all -> 0x0e33 }
            r2.<init>()     // Catch:{ all -> 0x0e33 }
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ all -> 0x0e33 }
            r3.<init>()     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.measurement.internal.zzgd r5 = r1.zzj     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.measurement.internal.zzkw r5 = r5.zzi()     // Catch:{ all -> 0x0e33 }
            java.security.SecureRandom r5 = r5.zzh()     // Catch:{ all -> 0x0e33 }
            r7 = 0
        L_0x0af3:
            int r8 = r6.zzb()     // Catch:{ all -> 0x0e33 }
            if (r7 >= r8) goto L_0x0dfd
            com.google.android.gms.internal.measurement.zzcc$zzc r8 = r6.zzb((int) r7)     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzib$zza r8 = r8.zzbl()     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzib$zza r8 = (com.google.android.gms.internal.measurement.zzib.zza) r8     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzcc$zzc$zza r8 = (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r8     // Catch:{ all -> 0x0e33 }
            java.lang.String r9 = r8.zzd()     // Catch:{ all -> 0x0e33 }
            java.lang.String r10 = "_ep"
            boolean r9 = r9.equals(r10)     // Catch:{ all -> 0x0e33 }
            java.lang.String r10 = "_sr"
            if (r9 == 0) goto L_0x0b8b
            com.google.android.gms.measurement.internal.zzks r9 = r43.zzh()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzjj r11 = r8.zzv()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib r11 = (com.google.android.gms.internal.measurement.zzib) r11     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc r11 = (com.google.android.gms.internal.measurement.zzcc.zzc) r11     // Catch:{ all -> 0x1005 }
            java.lang.String r12 = "_en"
            java.lang.Object r9 = r9.zzb(r11, r12)     // Catch:{ all -> 0x1005 }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ all -> 0x1005 }
            java.lang.Object r11 = r2.get(r9)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzak r11 = (com.google.android.gms.measurement.internal.zzak) r11     // Catch:{ all -> 0x1005 }
            if (r11 != 0) goto L_0x0b42
            com.google.android.gms.measurement.internal.zzad r11 = r43.zze()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg r12 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r12 = r12.zzx()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzak r11 = r11.zza((java.lang.String) r12, (java.lang.String) r9)     // Catch:{ all -> 0x1005 }
            r2.put(r9, r11)     // Catch:{ all -> 0x1005 }
        L_0x0b42:
            java.lang.Long r9 = r11.zzi     // Catch:{ all -> 0x1005 }
            if (r9 != 0) goto L_0x0b81
            java.lang.Long r9 = r11.zzj     // Catch:{ all -> 0x1005 }
            long r12 = r9.longValue()     // Catch:{ all -> 0x1005 }
            r14 = 1
            int r9 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r9 <= 0) goto L_0x0b5b
            com.google.android.gms.measurement.internal.zzks r9 = r43.zzh()     // Catch:{ all -> 0x1005 }
            java.lang.Long r12 = r11.zzj     // Catch:{ all -> 0x1005 }
            r9.zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r8, (java.lang.String) r10, (java.lang.Object) r12)     // Catch:{ all -> 0x1005 }
        L_0x0b5b:
            java.lang.Boolean r9 = r11.zzk     // Catch:{ all -> 0x1005 }
            if (r9 == 0) goto L_0x0b76
            java.lang.Boolean r9 = r11.zzk     // Catch:{ all -> 0x1005 }
            boolean r9 = r9.booleanValue()     // Catch:{ all -> 0x1005 }
            if (r9 == 0) goto L_0x0b76
            com.google.android.gms.measurement.internal.zzks r9 = r43.zzh()     // Catch:{ all -> 0x1005 }
            java.lang.String r10 = "_efs"
            r11 = 1
            java.lang.Long r13 = java.lang.Long.valueOf(r11)     // Catch:{ all -> 0x1005 }
            r9.zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r8, (java.lang.String) r10, (java.lang.Object) r13)     // Catch:{ all -> 0x1005 }
        L_0x0b76:
            com.google.android.gms.internal.measurement.zzjj r9 = r8.zzv()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib r9 = (com.google.android.gms.internal.measurement.zzib) r9     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc r9 = (com.google.android.gms.internal.measurement.zzcc.zzc) r9     // Catch:{ all -> 0x1005 }
            r3.add(r9)     // Catch:{ all -> 0x1005 }
        L_0x0b81:
            r6.zza((int) r7, (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r8)     // Catch:{ all -> 0x1005 }
            r44 = r4
            r15 = r5
            r1 = r6
            r4 = r7
            goto L_0x0df3
        L_0x0b8b:
            com.google.android.gms.measurement.internal.zzfx r9 = r43.zzc()     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzcc$zzg r11 = r4.zza     // Catch:{ all -> 0x0e33 }
            java.lang.String r11 = r11.zzx()     // Catch:{ all -> 0x0e33 }
            long r11 = r9.zzf(r11)     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.measurement.internal.zzgd r9 = r1.zzj     // Catch:{ all -> 0x0e33 }
            r9.zzi()     // Catch:{ all -> 0x0e33 }
            long r13 = r8.zzf()     // Catch:{ all -> 0x0e33 }
            long r13 = com.google.android.gms.measurement.internal.zzkw.zza((long) r13, (long) r11)     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzjj r9 = r8.zzv()     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzib r9 = (com.google.android.gms.internal.measurement.zzib) r9     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzcc$zzc r9 = (com.google.android.gms.internal.measurement.zzcc.zzc) r9     // Catch:{ all -> 0x0e33 }
            java.lang.String r15 = "_dbg"
            r20 = r11
            r16 = 1
            java.lang.Long r11 = java.lang.Long.valueOf(r16)     // Catch:{ all -> 0x0e33 }
            boolean r12 = android.text.TextUtils.isEmpty(r15)     // Catch:{ all -> 0x0e33 }
            if (r12 != 0) goto L_0x0c20
            if (r11 != 0) goto L_0x0bc4
            goto L_0x0c20
        L_0x0bc4:
            java.util.List r9 = r9.zza()     // Catch:{ all -> 0x1005 }
            java.util.Iterator r9 = r9.iterator()     // Catch:{ all -> 0x1005 }
        L_0x0bcc:
            boolean r12 = r9.hasNext()     // Catch:{ all -> 0x1005 }
            if (r12 == 0) goto L_0x0c1e
            java.lang.Object r12 = r9.next()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zze r12 = (com.google.android.gms.internal.measurement.zzcc.zze) r12     // Catch:{ all -> 0x1005 }
            r44 = r9
            java.lang.String r9 = r12.zzb()     // Catch:{ all -> 0x1005 }
            boolean r9 = r15.equals(r9)     // Catch:{ all -> 0x1005 }
            if (r9 == 0) goto L_0x0c1b
            boolean r9 = r11 instanceof java.lang.Long     // Catch:{ all -> 0x1005 }
            if (r9 == 0) goto L_0x0bf6
            long r15 = r12.zzf()     // Catch:{ all -> 0x1005 }
            java.lang.Long r9 = java.lang.Long.valueOf(r15)     // Catch:{ all -> 0x1005 }
            boolean r9 = r11.equals(r9)     // Catch:{ all -> 0x1005 }
            if (r9 != 0) goto L_0x0c16
        L_0x0bf6:
            boolean r9 = r11 instanceof java.lang.String     // Catch:{ all -> 0x1005 }
            if (r9 == 0) goto L_0x0c04
            java.lang.String r9 = r12.zzd()     // Catch:{ all -> 0x1005 }
            boolean r9 = r11.equals(r9)     // Catch:{ all -> 0x1005 }
            if (r9 != 0) goto L_0x0c16
        L_0x0c04:
            boolean r9 = r11 instanceof java.lang.Double     // Catch:{ all -> 0x1005 }
            if (r9 == 0) goto L_0x0c18
            double r15 = r12.zzj()     // Catch:{ all -> 0x1005 }
            java.lang.Double r9 = java.lang.Double.valueOf(r15)     // Catch:{ all -> 0x1005 }
            boolean r9 = r11.equals(r9)     // Catch:{ all -> 0x1005 }
            if (r9 == 0) goto L_0x0c18
        L_0x0c16:
            r9 = 1
            goto L_0x0c21
        L_0x0c18:
            r9 = 0
            goto L_0x0c21
        L_0x0c1b:
            r9 = r44
            goto L_0x0bcc
        L_0x0c1e:
            r9 = 0
            goto L_0x0c21
        L_0x0c20:
            r9 = 0
        L_0x0c21:
            if (r9 != 0) goto L_0x0c37
            com.google.android.gms.measurement.internal.zzfx r9 = r43.zzc()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzg r11 = r4.zza     // Catch:{ all -> 0x1005 }
            java.lang.String r11 = r11.zzx()     // Catch:{ all -> 0x1005 }
            java.lang.String r12 = r8.zzd()     // Catch:{ all -> 0x1005 }
            int r9 = r9.zzd(r11, r12)     // Catch:{ all -> 0x1005 }
            goto L_0x0c38
        L_0x0c37:
            r9 = 1
        L_0x0c38:
            if (r9 > 0) goto L_0x0c67
            com.google.android.gms.measurement.internal.zzgd r10 = r1.zzj     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzez r10 = r10.zzr()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.measurement.internal.zzfb r10 = r10.zzi()     // Catch:{ all -> 0x1005 }
            java.lang.String r11 = "Sample rate must be positive. event, rate"
            java.lang.String r12 = r8.zzd()     // Catch:{ all -> 0x1005 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x1005 }
            r10.zza(r11, r12, r9)     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzjj r9 = r8.zzv()     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzib r9 = (com.google.android.gms.internal.measurement.zzib) r9     // Catch:{ all -> 0x1005 }
            com.google.android.gms.internal.measurement.zzcc$zzc r9 = (com.google.android.gms.internal.measurement.zzcc.zzc) r9     // Catch:{ all -> 0x1005 }
            r3.add(r9)     // Catch:{ all -> 0x1005 }
            r6.zza((int) r7, (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r8)     // Catch:{ all -> 0x1005 }
            r44 = r4
            r15 = r5
            r1 = r6
            r4 = r7
            goto L_0x0df3
        L_0x0c67:
            java.lang.String r11 = r8.zzd()     // Catch:{ all -> 0x0e33 }
            java.lang.Object r11 = r2.get(r11)     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.measurement.internal.zzak r11 = (com.google.android.gms.measurement.internal.zzak) r11     // Catch:{ all -> 0x0e33 }
            if (r11 != 0) goto L_0x0cc7
            com.google.android.gms.measurement.internal.zzad r11 = r43.zze()     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzcc$zzg r12 = r4.zza     // Catch:{ all -> 0x0e33 }
            java.lang.String r12 = r12.zzx()     // Catch:{ all -> 0x0e33 }
            java.lang.String r15 = r8.zzd()     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.measurement.internal.zzak r11 = r11.zza((java.lang.String) r12, (java.lang.String) r15)     // Catch:{ all -> 0x0e33 }
            if (r11 != 0) goto L_0x0cc7
            com.google.android.gms.measurement.internal.zzgd r11 = r1.zzj     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.measurement.internal.zzez r11 = r11.zzr()     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.measurement.internal.zzfb r11 = r11.zzi()     // Catch:{ all -> 0x0e33 }
            java.lang.String r12 = "Event being bundled has no eventAggregate. appId, eventName"
            com.google.android.gms.internal.measurement.zzcc$zzg r15 = r4.zza     // Catch:{ all -> 0x0e33 }
            java.lang.String r15 = r15.zzx()     // Catch:{ all -> 0x0e33 }
            java.lang.String r1 = r8.zzd()     // Catch:{ all -> 0x0e33 }
            r11.zza(r12, r15, r1)     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.measurement.internal.zzak r11 = new com.google.android.gms.measurement.internal.zzak     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzcc$zzg r1 = r4.zza     // Catch:{ all -> 0x0e33 }
            java.lang.String r27 = r1.zzx()     // Catch:{ all -> 0x0e33 }
            java.lang.String r28 = r8.zzd()     // Catch:{ all -> 0x0e33 }
            r29 = 1
            r31 = 1
            r33 = 1
            long r35 = r8.zzf()     // Catch:{ all -> 0x0e33 }
            r37 = 0
            r39 = 0
            r40 = 0
            r41 = 0
            r42 = 0
            r26 = r11
            r26.<init>(r27, r28, r29, r31, r33, r35, r37, r39, r40, r41, r42)     // Catch:{ all -> 0x0e33 }
        L_0x0cc7:
            com.google.android.gms.measurement.internal.zzks r1 = r43.zzh()     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzjj r12 = r8.zzv()     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzib r12 = (com.google.android.gms.internal.measurement.zzib) r12     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzcc$zzc r12 = (com.google.android.gms.internal.measurement.zzcc.zzc) r12     // Catch:{ all -> 0x0e33 }
            java.lang.String r15 = "_eid"
            java.lang.Object r1 = r1.zzb(r12, r15)     // Catch:{ all -> 0x0e33 }
            java.lang.Long r1 = (java.lang.Long) r1     // Catch:{ all -> 0x0e33 }
            if (r1 == 0) goto L_0x0ce0
            r12 = 1
            goto L_0x0ce1
        L_0x0ce0:
            r12 = 0
        L_0x0ce1:
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r12)     // Catch:{ all -> 0x0e33 }
            r15 = 1
            if (r9 != r15) goto L_0x0d1b
            com.google.android.gms.internal.measurement.zzjj r1 = r8.zzv()     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzib r1 = (com.google.android.gms.internal.measurement.zzib) r1     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzcc$zzc r1 = (com.google.android.gms.internal.measurement.zzcc.zzc) r1     // Catch:{ all -> 0x0e33 }
            r3.add(r1)     // Catch:{ all -> 0x0e33 }
            boolean r1 = r12.booleanValue()     // Catch:{ all -> 0x0e33 }
            if (r1 == 0) goto L_0x0d11
            java.lang.Long r1 = r11.zzi     // Catch:{ all -> 0x0e33 }
            if (r1 != 0) goto L_0x0d05
            java.lang.Long r1 = r11.zzj     // Catch:{ all -> 0x0e33 }
            if (r1 != 0) goto L_0x0d05
            java.lang.Boolean r1 = r11.zzk     // Catch:{ all -> 0x0e33 }
            if (r1 == 0) goto L_0x0d11
        L_0x0d05:
            r1 = 0
            com.google.android.gms.measurement.internal.zzak r9 = r11.zza(r1, r1, r1)     // Catch:{ all -> 0x0e33 }
            java.lang.String r1 = r8.zzd()     // Catch:{ all -> 0x0e33 }
            r2.put(r1, r9)     // Catch:{ all -> 0x0e33 }
        L_0x0d11:
            r6.zza((int) r7, (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r8)     // Catch:{ all -> 0x0e33 }
            r44 = r4
            r15 = r5
            r1 = r6
            r4 = r7
            goto L_0x0df3
        L_0x0d1b:
            int r15 = r5.nextInt(r9)     // Catch:{ all -> 0x0e33 }
            if (r15 != 0) goto L_0x0d61
            com.google.android.gms.measurement.internal.zzks r1 = r43.zzh()     // Catch:{ all -> 0x0e33 }
            r44 = r4
            r15 = r5
            long r4 = (long) r9     // Catch:{ all -> 0x0e33 }
            java.lang.Long r9 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x0e33 }
            r1.zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r8, (java.lang.String) r10, (java.lang.Object) r9)     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzjj r1 = r8.zzv()     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzib r1 = (com.google.android.gms.internal.measurement.zzib) r1     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzcc$zzc r1 = (com.google.android.gms.internal.measurement.zzcc.zzc) r1     // Catch:{ all -> 0x0e33 }
            r3.add(r1)     // Catch:{ all -> 0x0e33 }
            boolean r1 = r12.booleanValue()     // Catch:{ all -> 0x0e33 }
            if (r1 == 0) goto L_0x0d4b
            java.lang.Long r1 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x0e33 }
            r4 = 0
            com.google.android.gms.measurement.internal.zzak r11 = r11.zza(r4, r1, r4)     // Catch:{ all -> 0x0e33 }
        L_0x0d4b:
            java.lang.String r1 = r8.zzd()     // Catch:{ all -> 0x0e33 }
            long r4 = r8.zzf()     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.measurement.internal.zzak r4 = r11.zza(r4, r13)     // Catch:{ all -> 0x0e33 }
            r2.put(r1, r4)     // Catch:{ all -> 0x0e33 }
            r30 = r6
            r16 = r7
            goto L_0x0dec
        L_0x0d61:
            r44 = r4
            r15 = r5
            java.lang.Long r4 = r11.zzh     // Catch:{ all -> 0x0e33 }
            if (r4 == 0) goto L_0x0d73
            java.lang.Long r4 = r11.zzh     // Catch:{ all -> 0x0e33 }
            long r4 = r4.longValue()     // Catch:{ all -> 0x0e33 }
            r30 = r6
            r16 = r7
            goto L_0x0d89
        L_0x0d73:
            r4 = r43
            com.google.android.gms.measurement.internal.zzgd r5 = r4.zzj     // Catch:{ all -> 0x0e33 }
            r5.zzi()     // Catch:{ all -> 0x0e33 }
            long r4 = r8.zzg()     // Catch:{ all -> 0x0e33 }
            r30 = r6
            r16 = r7
            r6 = r20
            long r4 = com.google.android.gms.measurement.internal.zzkw.zza((long) r4, (long) r6)     // Catch:{ all -> 0x0e33 }
        L_0x0d89:
            int r4 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r4 == 0) goto L_0x0dd9
            com.google.android.gms.measurement.internal.zzks r1 = r43.zzh()     // Catch:{ all -> 0x0e33 }
            java.lang.String r4 = "_efs"
            r5 = 1
            java.lang.Long r7 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0e33 }
            r1.zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r8, (java.lang.String) r4, (java.lang.Object) r7)     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.measurement.internal.zzks r1 = r43.zzh()     // Catch:{ all -> 0x0e33 }
            long r5 = (long) r9     // Catch:{ all -> 0x0e33 }
            java.lang.Long r4 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0e33 }
            r1.zza((com.google.android.gms.internal.measurement.zzcc.zzc.zza) r8, (java.lang.String) r10, (java.lang.Object) r4)     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzjj r1 = r8.zzv()     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzib r1 = (com.google.android.gms.internal.measurement.zzib) r1     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.internal.measurement.zzcc$zzc r1 = (com.google.android.gms.internal.measurement.zzcc.zzc) r1     // Catch:{ all -> 0x0e33 }
            r3.add(r1)     // Catch:{ all -> 0x0e33 }
            boolean r1 = r12.booleanValue()     // Catch:{ all -> 0x0e33 }
            if (r1 == 0) goto L_0x0dc8
            java.lang.Long r1 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0e33 }
            r4 = 1
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r4)     // Catch:{ all -> 0x0e33 }
            r4 = 0
            com.google.android.gms.measurement.internal.zzak r11 = r11.zza(r4, r1, r5)     // Catch:{ all -> 0x0e33 }
        L_0x0dc8:
            java.lang.String r1 = r8.zzd()     // Catch:{ all -> 0x0e33 }
            long r4 = r8.zzf()     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.measurement.internal.zzak r4 = r11.zza(r4, r13)     // Catch:{ all -> 0x0e33 }
            r2.put(r1, r4)     // Catch:{ all -> 0x0e33 }
            goto L_0x0dec
        L_0x0dd9:
            boolean r4 = r12.booleanValue()     // Catch:{ all -> 0x0e33 }
            if (r4 == 0) goto L_0x0dec
            java.lang.String r4 = r8.zzd()     // Catch:{ all -> 0x0e33 }
            r5 = 0
            com.google.android.gms.measurement.internal.zzak r1 = r11.zza(r1, r5, r5)     // Catch:{ all -> 0x0e33 }
            r2.put(r4, r1)     // Catch:{ all -> 0x0e33 }
        L_0x0dec:
            r4 = r16
            r1 = r30
            r1.zza((int) r4, (com.google.android.gms.internal.measurement.zzcc.zzc.zza) r8)     // Catch:{ all -> 0x0e33 }
        L_0x0df3:
            int r7 = r4 + 1
            r4 = r44
            r6 = r1
            r5 = r15
            r1 = r43
            goto L_0x0af3
        L_0x0dfd:
            r44 = r4
            r1 = r6
            int r4 = r3.size()     // Catch:{ all -> 0x0e33 }
            int r5 = r1.zzb()     // Catch:{ all -> 0x0e33 }
            if (r4 >= r5) goto L_0x0e11
            com.google.android.gms.internal.measurement.zzcc$zzg$zza r4 = r1.zzc()     // Catch:{ all -> 0x0e33 }
            r4.zza((java.lang.Iterable<? extends com.google.android.gms.internal.measurement.zzcc.zzc>) r3)     // Catch:{ all -> 0x0e33 }
        L_0x0e11:
            java.util.Set r2 = r2.entrySet()     // Catch:{ all -> 0x0e33 }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x0e33 }
        L_0x0e19:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x0e33 }
            if (r3 == 0) goto L_0x0e3b
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x0e33 }
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.measurement.internal.zzad r4 = r43.zze()     // Catch:{ all -> 0x0e33 }
            java.lang.Object r3 = r3.getValue()     // Catch:{ all -> 0x0e33 }
            com.google.android.gms.measurement.internal.zzak r3 = (com.google.android.gms.measurement.internal.zzak) r3     // Catch:{ all -> 0x0e33 }
            r4.zza((com.google.android.gms.measurement.internal.zzak) r3)     // Catch:{ all -> 0x0e33 }
            goto L_0x0e19
        L_0x0e33:
            r0 = move-exception
            r2 = r43
            goto L_0x1007
        L_0x0e38:
            r44 = r4
            r1 = r6
        L_0x0e3b:
            r2 = r43
            com.google.android.gms.measurement.internal.zzgd r3 = r2.zzj     // Catch:{ all -> 0x1003 }
            com.google.android.gms.measurement.internal.zzy r3 = r3.zzb()     // Catch:{ all -> 0x1003 }
            java.lang.String r4 = r1.zzj()     // Catch:{ all -> 0x1003 }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r5 = com.google.android.gms.measurement.internal.zzaq.zzbm     // Catch:{ all -> 0x1003 }
            boolean r3 = r3.zze(r4, r5)     // Catch:{ all -> 0x1003 }
            if (r3 != 0) goto L_0x0e54
            zza((com.google.android.gms.internal.measurement.zzcc.zzg.zza) r1)     // Catch:{ all -> 0x1003 }
        L_0x0e54:
            r3 = r44
            com.google.android.gms.internal.measurement.zzcc$zzg r4 = r3.zza     // Catch:{ all -> 0x1003 }
            java.lang.String r4 = r4.zzx()     // Catch:{ all -> 0x1003 }
            com.google.android.gms.measurement.internal.zzad r5 = r43.zze()     // Catch:{ all -> 0x1003 }
            com.google.android.gms.measurement.internal.zzf r5 = r5.zzb(r4)     // Catch:{ all -> 0x1003 }
            if (r5 != 0) goto L_0x0e81
            com.google.android.gms.measurement.internal.zzgd r5 = r2.zzj     // Catch:{ all -> 0x1003 }
            com.google.android.gms.measurement.internal.zzez r5 = r5.zzr()     // Catch:{ all -> 0x1003 }
            com.google.android.gms.measurement.internal.zzfb r5 = r5.zzf()     // Catch:{ all -> 0x1003 }
            java.lang.String r6 = "Bundling raw events w/o app info. appId"
            com.google.android.gms.internal.measurement.zzcc$zzg r7 = r3.zza     // Catch:{ all -> 0x1003 }
            java.lang.String r7 = r7.zzx()     // Catch:{ all -> 0x1003 }
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r7)     // Catch:{ all -> 0x1003 }
            r5.zza(r6, r7)     // Catch:{ all -> 0x1003 }
            goto L_0x0edc
        L_0x0e81:
            int r6 = r1.zzb()     // Catch:{ all -> 0x1003 }
            if (r6 <= 0) goto L_0x0edc
            long r6 = r5.zzk()     // Catch:{ all -> 0x1003 }
            r8 = 0
            int r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r10 == 0) goto L_0x0e95
            r1.zze((long) r6)     // Catch:{ all -> 0x1003 }
            goto L_0x0e98
        L_0x0e95:
            r1.zzi()     // Catch:{ all -> 0x1003 }
        L_0x0e98:
            long r8 = r5.zzj()     // Catch:{ all -> 0x1003 }
            r10 = 0
            int r12 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r12 != 0) goto L_0x0ea3
            goto L_0x0ea4
        L_0x0ea3:
            r6 = r8
        L_0x0ea4:
            int r8 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r8 == 0) goto L_0x0eac
            r1.zzd((long) r6)     // Catch:{ all -> 0x1003 }
            goto L_0x0eaf
        L_0x0eac:
            r1.zzh()     // Catch:{ all -> 0x1003 }
        L_0x0eaf:
            r5.zzv()     // Catch:{ all -> 0x1003 }
            long r6 = r5.zzs()     // Catch:{ all -> 0x1003 }
            int r6 = (int) r6     // Catch:{ all -> 0x1003 }
            r1.zzg((int) r6)     // Catch:{ all -> 0x1003 }
            long r6 = r1.zzf()     // Catch:{ all -> 0x1003 }
            r5.zza((long) r6)     // Catch:{ all -> 0x1003 }
            long r6 = r1.zzg()     // Catch:{ all -> 0x1003 }
            r5.zzb((long) r6)     // Catch:{ all -> 0x1003 }
            java.lang.String r6 = r5.zzad()     // Catch:{ all -> 0x1003 }
            if (r6 == 0) goto L_0x0ed2
            r1.zzj((java.lang.String) r6)     // Catch:{ all -> 0x1003 }
            goto L_0x0ed5
        L_0x0ed2:
            r1.zzk()     // Catch:{ all -> 0x1003 }
        L_0x0ed5:
            com.google.android.gms.measurement.internal.zzad r6 = r43.zze()     // Catch:{ all -> 0x1003 }
            r6.zza((com.google.android.gms.measurement.internal.zzf) r5)     // Catch:{ all -> 0x1003 }
        L_0x0edc:
            int r5 = r1.zzb()     // Catch:{ all -> 0x1003 }
            if (r5 <= 0) goto L_0x0f47
            com.google.android.gms.measurement.internal.zzgd r5 = r2.zzj     // Catch:{ all -> 0x1003 }
            r5.zzu()     // Catch:{ all -> 0x1003 }
            com.google.android.gms.measurement.internal.zzfx r5 = r43.zzc()     // Catch:{ all -> 0x1003 }
            com.google.android.gms.internal.measurement.zzcc$zzg r6 = r3.zza     // Catch:{ all -> 0x1003 }
            java.lang.String r6 = r6.zzx()     // Catch:{ all -> 0x1003 }
            com.google.android.gms.internal.measurement.zzca$zzb r5 = r5.zza((java.lang.String) r6)     // Catch:{ all -> 0x1003 }
            if (r5 == 0) goto L_0x0f0a
            boolean r6 = r5.zza()     // Catch:{ all -> 0x1003 }
            if (r6 != 0) goto L_0x0f02
            goto L_0x0f0a
        L_0x0f02:
            long r5 = r5.zzb()     // Catch:{ all -> 0x1003 }
            r1.zzi((long) r5)     // Catch:{ all -> 0x1003 }
            goto L_0x0f36
        L_0x0f0a:
            com.google.android.gms.internal.measurement.zzcc$zzg r5 = r3.zza     // Catch:{ all -> 0x1003 }
            java.lang.String r5 = r5.zzam()     // Catch:{ all -> 0x1003 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x1003 }
            if (r5 == 0) goto L_0x0f1c
            r5 = -1
            r1.zzi((long) r5)     // Catch:{ all -> 0x1003 }
            goto L_0x0f36
        L_0x0f1c:
            com.google.android.gms.measurement.internal.zzgd r5 = r2.zzj     // Catch:{ all -> 0x1003 }
            com.google.android.gms.measurement.internal.zzez r5 = r5.zzr()     // Catch:{ all -> 0x1003 }
            com.google.android.gms.measurement.internal.zzfb r5 = r5.zzi()     // Catch:{ all -> 0x1003 }
            java.lang.String r6 = "Did not find measurement config or missing version info. appId"
            com.google.android.gms.internal.measurement.zzcc$zzg r7 = r3.zza     // Catch:{ all -> 0x1003 }
            java.lang.String r7 = r7.zzx()     // Catch:{ all -> 0x1003 }
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r7)     // Catch:{ all -> 0x1003 }
            r5.zza(r6, r7)     // Catch:{ all -> 0x1003 }
        L_0x0f36:
            com.google.android.gms.measurement.internal.zzad r5 = r43.zze()     // Catch:{ all -> 0x1003 }
            com.google.android.gms.internal.measurement.zzjj r1 = r1.zzv()     // Catch:{ all -> 0x1003 }
            com.google.android.gms.internal.measurement.zzib r1 = (com.google.android.gms.internal.measurement.zzib) r1     // Catch:{ all -> 0x1003 }
            com.google.android.gms.internal.measurement.zzcc$zzg r1 = (com.google.android.gms.internal.measurement.zzcc.zzg) r1     // Catch:{ all -> 0x1003 }
            r13 = r19
            r5.zza((com.google.android.gms.internal.measurement.zzcc.zzg) r1, (boolean) r13)     // Catch:{ all -> 0x1003 }
        L_0x0f47:
            com.google.android.gms.measurement.internal.zzad r1 = r43.zze()     // Catch:{ all -> 0x1003 }
            java.util.List<java.lang.Long> r3 = r3.zzb     // Catch:{ all -> 0x1003 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r3)     // Catch:{ all -> 0x1003 }
            r1.zzd()     // Catch:{ all -> 0x1003 }
            r1.zzak()     // Catch:{ all -> 0x1003 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x1003 }
            java.lang.String r6 = "rowid in ("
            r5.<init>(r6)     // Catch:{ all -> 0x1003 }
            r6 = 0
        L_0x0f5e:
            int r7 = r3.size()     // Catch:{ all -> 0x1003 }
            if (r6 >= r7) goto L_0x0f7b
            if (r6 == 0) goto L_0x0f6b
            java.lang.String r7 = ","
            r5.append(r7)     // Catch:{ all -> 0x1003 }
        L_0x0f6b:
            java.lang.Object r7 = r3.get(r6)     // Catch:{ all -> 0x1003 }
            java.lang.Long r7 = (java.lang.Long) r7     // Catch:{ all -> 0x1003 }
            long r7 = r7.longValue()     // Catch:{ all -> 0x1003 }
            r5.append(r7)     // Catch:{ all -> 0x1003 }
            int r6 = r6 + 1
            goto L_0x0f5e
        L_0x0f7b:
            java.lang.String r6 = ")"
            r5.append(r6)     // Catch:{ all -> 0x1003 }
            android.database.sqlite.SQLiteDatabase r6 = r1.mo40210c_()     // Catch:{ all -> 0x1003 }
            java.lang.String r7 = "raw_events"
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x1003 }
            r8 = 0
            int r5 = r6.delete(r7, r5, r8)     // Catch:{ all -> 0x1003 }
            int r6 = r3.size()     // Catch:{ all -> 0x1003 }
            if (r5 == r6) goto L_0x0fae
            com.google.android.gms.measurement.internal.zzez r1 = r1.zzr()     // Catch:{ all -> 0x1003 }
            com.google.android.gms.measurement.internal.zzfb r1 = r1.zzf()     // Catch:{ all -> 0x1003 }
            java.lang.String r6 = "Deleted fewer rows from raw events table than expected"
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x1003 }
            int r3 = r3.size()     // Catch:{ all -> 0x1003 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x1003 }
            r1.zza(r6, r5, r3)     // Catch:{ all -> 0x1003 }
        L_0x0fae:
            com.google.android.gms.measurement.internal.zzad r1 = r43.zze()     // Catch:{ all -> 0x1003 }
            android.database.sqlite.SQLiteDatabase r3 = r1.mo40210c_()     // Catch:{ all -> 0x1003 }
            java.lang.String r5 = "delete from raw_events_metadata where app_id=? and metadata_fingerprint not in (select distinct metadata_fingerprint from raw_events where app_id=?)"
            r6 = 2
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ SQLiteException -> 0x0fc5 }
            r7 = 0
            r6[r7] = r4     // Catch:{ SQLiteException -> 0x0fc5 }
            r7 = 1
            r6[r7] = r4     // Catch:{ SQLiteException -> 0x0fc5 }
            r3.execSQL(r5, r6)     // Catch:{ SQLiteException -> 0x0fc5 }
            goto L_0x0fd8
        L_0x0fc5:
            r0 = move-exception
            r3 = r0
            com.google.android.gms.measurement.internal.zzez r1 = r1.zzr()     // Catch:{ all -> 0x1003 }
            com.google.android.gms.measurement.internal.zzfb r1 = r1.zzf()     // Catch:{ all -> 0x1003 }
            java.lang.String r5 = "Failed to remove unused event metadata. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r4)     // Catch:{ all -> 0x1003 }
            r1.zza(r5, r4, r3)     // Catch:{ all -> 0x1003 }
        L_0x0fd8:
            com.google.android.gms.measurement.internal.zzad r1 = r43.zze()     // Catch:{ all -> 0x1003 }
            r1.mo40209b_()     // Catch:{ all -> 0x1003 }
            com.google.android.gms.measurement.internal.zzad r1 = r43.zze()
            r1.zzh()
            r1 = 1
            return r1
        L_0x0fe8:
            r2 = r1
            com.google.android.gms.measurement.internal.zzad r1 = r43.zze()     // Catch:{ all -> 0x1003 }
            r1.mo40209b_()     // Catch:{ all -> 0x1003 }
            com.google.android.gms.measurement.internal.zzad r1 = r43.zze()
            r1.zzh()
            r1 = 0
            return r1
        L_0x0ff9:
            r0 = move-exception
            r2 = r1
            goto L_0x024b
        L_0x0ffd:
            if (r5 == 0) goto L_0x1002
            r5.close()     // Catch:{ all -> 0x1003 }
        L_0x1002:
            throw r1     // Catch:{ all -> 0x1003 }
        L_0x1003:
            r0 = move-exception
            goto L_0x1007
        L_0x1005:
            r0 = move-exception
            r2 = r1
        L_0x1007:
            r1 = r0
            com.google.android.gms.measurement.internal.zzad r3 = r43.zze()
            r3.zzh()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzkk.zza(java.lang.String, long):boolean");
    }

    private static void zza(zzcc.zzg.zza zza2) {
        zza2.zzb(Long.MAX_VALUE).zzc(Long.MIN_VALUE);
        for (int i = 0; i < zza2.zzb(); i++) {
            zzcc.zzc zzb2 = zza2.zzb(i);
            if (zzb2.zze() < zza2.zzf()) {
                zza2.zzb(zzb2.zze());
            }
            if (zzb2.zze() > zza2.zzg()) {
                zza2.zzc(zzb2.zze());
            }
        }
    }

    private final void zza(zzcc.zzg.zza zza2, long j, boolean z) {
        String str;
        zzkt zzkt;
        String str2;
        if (z) {
            str = "_se";
        } else {
            str = "_lte";
        }
        zzkt zzc2 = zze().zzc(zza2.zzj(), str);
        if (zzc2 == null || zzc2.zze == null) {
            zzkt = new zzkt(zza2.zzj(), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, str, this.zzj.zzm().currentTimeMillis(), Long.valueOf(j));
        } else {
            zzkt = new zzkt(zza2.zzj(), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, str, this.zzj.zzm().currentTimeMillis(), Long.valueOf(((Long) zzc2.zze).longValue() + j));
        }
        zzcc.zzk zzk2 = (zzcc.zzk) ((zzib) zzcc.zzk.zzj().zza(str).zza(this.zzj.zzm().currentTimeMillis()).zzb(((Long) zzkt.zze).longValue()).zzv());
        boolean z2 = false;
        int zza3 = zzks.zza(zza2, str);
        if (zza3 >= 0) {
            zza2.zza(zza3, zzk2);
            z2 = true;
        }
        if (!z2) {
            zza2.zza(zzk2);
        }
        if (j > 0) {
            zze().zza(zzkt);
            if (z) {
                str2 = "session-scoped";
            } else {
                str2 = "lifetime";
            }
            this.zzj.zzr().zzx().zza("Updated engagement user property. scope, value", str2, zzkt.zze);
        }
    }

    private final boolean zza(zzcc.zzc.zza zza2, zzcc.zzc.zza zza3) {
        Preconditions.checkArgument("_e".equals(zza2.zzd()));
        zzh();
        zzcc.zze zza4 = zzks.zza((zzcc.zzc) ((zzib) zza2.zzv()), "_sc");
        String str = null;
        String zzd2 = zza4 == null ? null : zza4.zzd();
        zzh();
        zzcc.zze zza5 = zzks.zza((zzcc.zzc) ((zzib) zza3.zzv()), "_pc");
        if (zza5 != null) {
            str = zza5.zzd();
        }
        if (str == null || !str.equals(zzd2)) {
            return false;
        }
        zzb(zza2, zza3);
        return true;
    }

    private final void zzb(zzcc.zzc.zza zza2, zzcc.zzc.zza zza3) {
        Preconditions.checkArgument("_e".equals(zza2.zzd()));
        zzh();
        zzcc.zze zza4 = zzks.zza((zzcc.zzc) ((zzib) zza2.zzv()), "_et");
        if (zza4.zze() && zza4.zzf() > 0) {
            long zzf2 = zza4.zzf();
            zzh();
            zzcc.zze zza5 = zzks.zza((zzcc.zzc) ((zzib) zza3.zzv()), "_et");
            if (zza5 != null && zza5.zzf() > 0) {
                zzf2 += zza5.zzf();
            }
            zzh().zza(zza3, "_et", (Object) Long.valueOf(zzf2));
            zzh().zza(zza2, "_fr", (Object) 1L);
        }
    }

    private static void zza(zzcc.zzc.zza zza2, String str) {
        List<zzcc.zze> zza3 = zza2.zza();
        for (int i = 0; i < zza3.size(); i++) {
            if (str.equals(zza3.get(i).zzb())) {
                zza2.zzb(i);
                return;
            }
        }
    }

    private static void zza(zzcc.zzc.zza zza2, int i, String str) {
        List<zzcc.zze> zza3 = zza2.zza();
        int i2 = 0;
        while (i2 < zza3.size()) {
            if (!"_err".equals(zza3.get(i2).zzb())) {
                i2++;
            } else {
                return;
            }
        }
        zza2.zza((zzcc.zze) ((zzib) zzcc.zze.zzm().zza("_err").zza(Long.valueOf((long) i).longValue()).zzv())).zza((zzcc.zze) ((zzib) zzcc.zze.zzm().zza("_ev").zzb(str).zzv()));
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public final void zza(int i, Throwable th, byte[] bArr, String str) {
        zzad zze2;
        zzw();
        zzk();
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzr = false;
                zzaa();
                throw th2;
            }
        }
        List<Long> list = this.zzv;
        this.zzv = null;
        boolean z = true;
        if ((i == 200 || i == 204) && th == null) {
            try {
                this.zzj.zzc().zzc.zza(this.zzj.zzm().currentTimeMillis());
                this.zzj.zzc().zzd.zza(0);
                zzz();
                this.zzj.zzr().zzx().zza("Successful upload. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                zze().zzf();
                try {
                    for (Long next : list) {
                        try {
                            zze2 = zze();
                            long longValue = next.longValue();
                            zze2.zzd();
                            zze2.zzak();
                            if (zze2.mo40210c_().delete("queue", "rowid=?", new String[]{String.valueOf(longValue)}) != 1) {
                                throw new SQLiteException("Deleted fewer rows from queue than expected");
                            }
                        } catch (SQLiteException e) {
                            zze2.zzr().zzf().zza("Failed to delete a bundle in a queue table", e);
                            throw e;
                        } catch (SQLiteException e2) {
                            List<Long> list2 = this.zzw;
                            if (list2 == null || !list2.contains(next)) {
                                throw e2;
                            }
                        }
                    }
                    zze().mo40209b_();
                    zze().zzh();
                    this.zzw = null;
                    if (!zzd().zzf() || !zzy()) {
                        this.zzx = -1;
                        zzz();
                    } else {
                        zzl();
                    }
                    this.zzm = 0;
                } catch (Throwable th3) {
                    zze().zzh();
                    throw th3;
                }
            } catch (SQLiteException e3) {
                this.zzj.zzr().zzf().zza("Database error while trying to delete uploaded bundles", e3);
                this.zzm = this.zzj.zzm().elapsedRealtime();
                this.zzj.zzr().zzx().zza("Disable upload, time", Long.valueOf(this.zzm));
            }
        } else {
            this.zzj.zzr().zzx().zza("Network upload failed. Will retry later. code, error", Integer.valueOf(i), th);
            this.zzj.zzc().zzd.zza(this.zzj.zzm().currentTimeMillis());
            if (i != 503) {
                if (i != 429) {
                    z = false;
                }
            }
            if (z) {
                this.zzj.zzc().zze.zza(this.zzj.zzm().currentTimeMillis());
            }
            zze().zza(list);
            zzz();
        }
        this.zzr = false;
        zzaa();
    }

    private final boolean zzy() {
        zzw();
        zzk();
        return zze().zzy() || !TextUtils.isEmpty(zze().mo40211d_());
    }

    private final void zza(zzf zzf2) {
        ArrayMap arrayMap;
        zzw();
        if (!zzoe.zzb() || !this.zzj.zzb().zze(zzf2.zzc(), zzaq.zzbn)) {
            if (TextUtils.isEmpty(zzf2.zze()) && TextUtils.isEmpty(zzf2.zzf())) {
                zza(zzf2.zzc(), CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE, (Throwable) null, (byte[]) null, (Map<String, List<String>>) null);
                return;
            }
        } else if (TextUtils.isEmpty(zzf2.zze()) && TextUtils.isEmpty(zzf2.zzg()) && TextUtils.isEmpty(zzf2.zzf())) {
            zza(zzf2.zzc(), CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE, (Throwable) null, (byte[]) null, (Map<String, List<String>>) null);
            return;
        }
        String zza2 = this.zzj.zzb().zza(zzf2);
        try {
            URL url = new URL(zza2);
            this.zzj.zzr().zzx().zza("Fetching remote configuration", zzf2.zzc());
            zzca.zzb zza3 = zzc().zza(zzf2.zzc());
            String zzb2 = zzc().zzb(zzf2.zzc());
            if (zza3 == null || TextUtils.isEmpty(zzb2)) {
                arrayMap = null;
            } else {
                ArrayMap arrayMap2 = new ArrayMap();
                arrayMap2.put(HttpHeaders.IF_MODIFIED_SINCE, zzb2);
                arrayMap = arrayMap2;
            }
            this.zzq = true;
            zzfc zzd2 = zzd();
            String zzc2 = zzf2.zzc();
            zzkp zzkp = new zzkp(this);
            zzd2.zzd();
            zzd2.zzak();
            Preconditions.checkNotNull(url);
            Preconditions.checkNotNull(zzkp);
            zzd2.zzq().zzb((Runnable) new zzfg(zzd2, zzc2, url, (byte[]) null, arrayMap, zzkp));
        } catch (MalformedURLException e) {
            this.zzj.zzr().zzf().zza("Failed to parse config URL. Not fetching. appId", zzez.zza(zzf2.zzc()), zza2);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0141 A[Catch:{ all -> 0x0198, all -> 0x01a1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0152 A[Catch:{ all -> 0x0198, all -> 0x01a1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x017b A[Catch:{ all -> 0x0198, all -> 0x01a1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x017f A[Catch:{ all -> 0x0198, all -> 0x01a1 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(java.lang.String r7, int r8, java.lang.Throwable r9, byte[] r10, java.util.Map<java.lang.String, java.util.List<java.lang.String>> r11) {
        /*
            r6 = this;
            r6.zzw()
            r6.zzk()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r7)
            r0 = 0
            if (r10 != 0) goto L_0x000e
            byte[] r10 = new byte[r0]     // Catch:{ all -> 0x01a1 }
        L_0x000e:
            com.google.android.gms.measurement.internal.zzgd r1 = r6.zzj     // Catch:{ all -> 0x01a1 }
            com.google.android.gms.measurement.internal.zzez r1 = r1.zzr()     // Catch:{ all -> 0x01a1 }
            com.google.android.gms.measurement.internal.zzfb r1 = r1.zzx()     // Catch:{ all -> 0x01a1 }
            java.lang.String r2 = "onConfigFetched. Response size"
            int r3 = r10.length     // Catch:{ all -> 0x01a1 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x01a1 }
            r1.zza(r2, r3)     // Catch:{ all -> 0x01a1 }
            com.google.android.gms.measurement.internal.zzad r1 = r6.zze()     // Catch:{ all -> 0x01a1 }
            r1.zzf()     // Catch:{ all -> 0x01a1 }
            com.google.android.gms.measurement.internal.zzad r1 = r6.zze()     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzf r1 = r1.zzb(r7)     // Catch:{ all -> 0x0198 }
            r2 = 200(0xc8, float:2.8E-43)
            r3 = 304(0x130, float:4.26E-43)
            r4 = 1
            if (r8 == r2) goto L_0x003f
            r2 = 204(0xcc, float:2.86E-43)
            if (r8 == r2) goto L_0x003f
            if (r8 != r3) goto L_0x0043
        L_0x003f:
            if (r9 != 0) goto L_0x0043
            r2 = r4
            goto L_0x0044
        L_0x0043:
            r2 = r0
        L_0x0044:
            if (r1 != 0) goto L_0x005c
            com.google.android.gms.measurement.internal.zzgd r8 = r6.zzj     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzez r8 = r8.zzr()     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzfb r8 = r8.zzi()     // Catch:{ all -> 0x0198 }
            java.lang.String r9 = "App does not exist in onConfigFetched. appId"
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r7)     // Catch:{ all -> 0x0198 }
            r8.zza(r9, r7)     // Catch:{ all -> 0x0198 }
            goto L_0x0183
        L_0x005c:
            r5 = 404(0x194, float:5.66E-43)
            if (r2 != 0) goto L_0x00d0
            if (r8 != r5) goto L_0x0063
            goto L_0x00d0
        L_0x0063:
            com.google.android.gms.measurement.internal.zzgd r10 = r6.zzj     // Catch:{ all -> 0x0198 }
            com.google.android.gms.common.util.Clock r10 = r10.zzm()     // Catch:{ all -> 0x0198 }
            long r10 = r10.currentTimeMillis()     // Catch:{ all -> 0x0198 }
            r1.zzi((long) r10)     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzad r10 = r6.zze()     // Catch:{ all -> 0x0198 }
            r10.zza((com.google.android.gms.measurement.internal.zzf) r1)     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzgd r10 = r6.zzj     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzez r10 = r10.zzr()     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzfb r10 = r10.zzx()     // Catch:{ all -> 0x0198 }
            java.lang.String r11 = "Fetching config failed. code, error"
            java.lang.Integer r1 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x0198 }
            r10.zza(r11, r1, r9)     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzfx r9 = r6.zzc()     // Catch:{ all -> 0x0198 }
            r9.zzc(r7)     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzgd r7 = r6.zzj     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzfl r7 = r7.zzc()     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzfp r7 = r7.zzd     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzgd r9 = r6.zzj     // Catch:{ all -> 0x0198 }
            com.google.android.gms.common.util.Clock r9 = r9.zzm()     // Catch:{ all -> 0x0198 }
            long r9 = r9.currentTimeMillis()     // Catch:{ all -> 0x0198 }
            r7.zza(r9)     // Catch:{ all -> 0x0198 }
            r7 = 503(0x1f7, float:7.05E-43)
            if (r8 == r7) goto L_0x00b3
            r7 = 429(0x1ad, float:6.01E-43)
            if (r8 != r7) goto L_0x00b2
            goto L_0x00b3
        L_0x00b2:
            r4 = r0
        L_0x00b3:
            if (r4 == 0) goto L_0x00cb
            com.google.android.gms.measurement.internal.zzgd r7 = r6.zzj     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzfl r7 = r7.zzc()     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzfp r7 = r7.zze     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzgd r8 = r6.zzj     // Catch:{ all -> 0x0198 }
            com.google.android.gms.common.util.Clock r8 = r8.zzm()     // Catch:{ all -> 0x0198 }
            long r8 = r8.currentTimeMillis()     // Catch:{ all -> 0x0198 }
            r7.zza(r8)     // Catch:{ all -> 0x0198 }
        L_0x00cb:
            r6.zzz()     // Catch:{ all -> 0x0198 }
            goto L_0x0183
        L_0x00d0:
            r9 = 0
            if (r11 == 0) goto L_0x00dc
            java.lang.String r2 = "Last-Modified"
            java.lang.Object r11 = r11.get(r2)     // Catch:{ all -> 0x0198 }
            java.util.List r11 = (java.util.List) r11     // Catch:{ all -> 0x0198 }
            goto L_0x00dd
        L_0x00dc:
            r11 = r9
        L_0x00dd:
            if (r11 == 0) goto L_0x00ec
            int r2 = r11.size()     // Catch:{ all -> 0x0198 }
            if (r2 <= 0) goto L_0x00ec
            java.lang.Object r11 = r11.get(r0)     // Catch:{ all -> 0x0198 }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ all -> 0x0198 }
            goto L_0x00ed
        L_0x00ec:
            r11 = r9
        L_0x00ed:
            if (r8 == r5) goto L_0x0109
            if (r8 != r3) goto L_0x00f2
            goto L_0x0109
        L_0x00f2:
            com.google.android.gms.measurement.internal.zzfx r9 = r6.zzc()     // Catch:{ all -> 0x0198 }
            boolean r9 = r9.zza(r7, r10, r11)     // Catch:{ all -> 0x0198 }
            if (r9 != 0) goto L_0x012a
            com.google.android.gms.measurement.internal.zzad r7 = r6.zze()     // Catch:{ all -> 0x01a1 }
            r7.zzh()     // Catch:{ all -> 0x01a1 }
            r6.zzq = r0
            r6.zzaa()
            return
        L_0x0109:
            com.google.android.gms.measurement.internal.zzfx r11 = r6.zzc()     // Catch:{ all -> 0x0198 }
            com.google.android.gms.internal.measurement.zzca$zzb r11 = r11.zza((java.lang.String) r7)     // Catch:{ all -> 0x0198 }
            if (r11 != 0) goto L_0x012a
            com.google.android.gms.measurement.internal.zzfx r11 = r6.zzc()     // Catch:{ all -> 0x0198 }
            boolean r9 = r11.zza(r7, r9, r9)     // Catch:{ all -> 0x0198 }
            if (r9 != 0) goto L_0x012a
            com.google.android.gms.measurement.internal.zzad r7 = r6.zze()     // Catch:{ all -> 0x01a1 }
            r7.zzh()     // Catch:{ all -> 0x01a1 }
            r6.zzq = r0
            r6.zzaa()
            return
        L_0x012a:
            com.google.android.gms.measurement.internal.zzgd r9 = r6.zzj     // Catch:{ all -> 0x0198 }
            com.google.android.gms.common.util.Clock r9 = r9.zzm()     // Catch:{ all -> 0x0198 }
            long r2 = r9.currentTimeMillis()     // Catch:{ all -> 0x0198 }
            r1.zzh((long) r2)     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzad r9 = r6.zze()     // Catch:{ all -> 0x0198 }
            r9.zza((com.google.android.gms.measurement.internal.zzf) r1)     // Catch:{ all -> 0x0198 }
            if (r8 != r5) goto L_0x0152
            com.google.android.gms.measurement.internal.zzgd r8 = r6.zzj     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzez r8 = r8.zzr()     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzfb r8 = r8.zzk()     // Catch:{ all -> 0x0198 }
            java.lang.String r9 = "Config not found. Using empty config. appId"
            r8.zza(r9, r7)     // Catch:{ all -> 0x0198 }
            goto L_0x016b
        L_0x0152:
            com.google.android.gms.measurement.internal.zzgd r7 = r6.zzj     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzez r7 = r7.zzr()     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzfb r7 = r7.zzx()     // Catch:{ all -> 0x0198 }
            java.lang.String r9 = "Successfully fetched config. Got network response. code, size"
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x0198 }
            int r10 = r10.length     // Catch:{ all -> 0x0198 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x0198 }
            r7.zza(r9, r8, r10)     // Catch:{ all -> 0x0198 }
        L_0x016b:
            com.google.android.gms.measurement.internal.zzfc r7 = r6.zzd()     // Catch:{ all -> 0x0198 }
            boolean r7 = r7.zzf()     // Catch:{ all -> 0x0198 }
            if (r7 == 0) goto L_0x017f
            boolean r7 = r6.zzy()     // Catch:{ all -> 0x0198 }
            if (r7 == 0) goto L_0x017f
            r6.zzl()     // Catch:{ all -> 0x0198 }
            goto L_0x0183
        L_0x017f:
            r6.zzz()     // Catch:{ all -> 0x0198 }
        L_0x0183:
            com.google.android.gms.measurement.internal.zzad r7 = r6.zze()     // Catch:{ all -> 0x0198 }
            r7.mo40209b_()     // Catch:{ all -> 0x0198 }
            com.google.android.gms.measurement.internal.zzad r7 = r6.zze()     // Catch:{ all -> 0x01a1 }
            r7.zzh()     // Catch:{ all -> 0x01a1 }
            r6.zzq = r0
            r6.zzaa()
            return
        L_0x0198:
            r7 = move-exception
            com.google.android.gms.measurement.internal.zzad r8 = r6.zze()     // Catch:{ all -> 0x01a1 }
            r8.zzh()     // Catch:{ all -> 0x01a1 }
            throw r7     // Catch:{ all -> 0x01a1 }
        L_0x01a1:
            r7 = move-exception
            r6.zzq = r0
            r6.zzaa()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzkk.zza(java.lang.String, int, java.lang.Throwable, byte[], java.util.Map):void");
    }

    private final void zzz() {
        long j;
        long j2;
        zzw();
        zzk();
        if (this.zzm > 0) {
            long abs = 3600000 - Math.abs(this.zzj.zzm().elapsedRealtime() - this.zzm);
            if (abs > 0) {
                this.zzj.zzr().zzx().zza("Upload has been suspended. Will update scheduling later in approximately ms", Long.valueOf(abs));
                zzt().zzb();
                zzv().zzf();
                return;
            }
            this.zzm = 0;
        }
        if (!this.zzj.zzag() || !zzy()) {
            this.zzj.zzr().zzx().zza("Nothing to upload or uploading impossible");
            zzt().zzb();
            zzv().zzf();
            return;
        }
        long currentTimeMillis = this.zzj.zzm().currentTimeMillis();
        long max = Math.max(0, zzaq.zzz.zza(null).longValue());
        boolean z = zze().zzz() || zze().zzk();
        if (z) {
            String zzw2 = this.zzj.zzb().zzw();
            if (TextUtils.isEmpty(zzw2) || ".none.".equals(zzw2)) {
                j = Math.max(0, zzaq.zzt.zza(null).longValue());
            } else {
                j = Math.max(0, zzaq.zzu.zza(null).longValue());
            }
        } else {
            j = Math.max(0, zzaq.zzs.zza(null).longValue());
        }
        long zza2 = this.zzj.zzc().zzc.zza();
        long zza3 = this.zzj.zzc().zzd.zza();
        long j3 = j;
        long j4 = max;
        long max2 = Math.max(zze().zzw(), zze().zzx());
        if (max2 == 0) {
            j2 = 0;
        } else {
            long abs2 = currentTimeMillis - Math.abs(max2 - currentTimeMillis);
            long abs3 = currentTimeMillis - Math.abs(zza2 - currentTimeMillis);
            long abs4 = currentTimeMillis - Math.abs(zza3 - currentTimeMillis);
            long max3 = Math.max(abs3, abs4);
            j2 = abs2 + j4;
            if (z && max3 > 0) {
                j2 = Math.min(abs2, max3) + j3;
            }
            long j5 = j3;
            if (!zzh().zza(max3, j5)) {
                j2 = max3 + j5;
            }
            if (abs4 != 0 && abs4 >= abs2) {
                int i = 0;
                while (true) {
                    if (i >= Math.min(20, Math.max(0, zzaq.zzab.zza(null).intValue()))) {
                        j2 = 0;
                        break;
                    }
                    j2 += Math.max(0, zzaq.zzaa.zza(null).longValue()) * (1 << i);
                    if (j2 > abs4) {
                        break;
                    }
                    i++;
                }
            }
        }
        if (j2 == 0) {
            this.zzj.zzr().zzx().zza("Next upload time is 0");
            zzt().zzb();
            zzv().zzf();
        } else if (!zzd().zzf()) {
            this.zzj.zzr().zzx().zza("No network");
            zzt().zza();
            zzv().zzf();
        } else {
            long zza4 = this.zzj.zzc().zze.zza();
            long max4 = Math.max(0, zzaq.zzq.zza(null).longValue());
            if (!zzh().zza(zza4, max4)) {
                j2 = Math.max(j2, zza4 + max4);
            }
            zzt().zzb();
            long currentTimeMillis2 = j2 - this.zzj.zzm().currentTimeMillis();
            if (currentTimeMillis2 <= 0) {
                currentTimeMillis2 = Math.max(0, zzaq.zzv.zza(null).longValue());
                this.zzj.zzc().zzc.zza(this.zzj.zzm().currentTimeMillis());
            }
            this.zzj.zzr().zzx().zza("Upload scheduled in approximately ms", Long.valueOf(currentTimeMillis2));
            zzv().zza(currentTimeMillis2);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(Runnable runnable) {
        zzw();
        if (this.zzn == null) {
            this.zzn = new ArrayList();
        }
        this.zzn.add(runnable);
    }

    private final void zzaa() {
        zzw();
        if (this.zzq || this.zzr || this.zzs) {
            this.zzj.zzr().zzx().zza("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zzq), Boolean.valueOf(this.zzr), Boolean.valueOf(this.zzs));
            return;
        }
        this.zzj.zzr().zzx().zza("Stopping uploading service(s)");
        List<Runnable> list = this.zzn;
        if (list != null) {
            for (Runnable run : list) {
                run.run();
            }
            this.zzn.clear();
        }
    }

    private final Boolean zzb(zzf zzf2) {
        try {
            if (zzf2.zzm() != -2147483648L) {
                if (zzf2.zzm() == ((long) Wrappers.packageManager(this.zzj.zzn()).getPackageInfo(zzf2.zzc(), 0).versionCode)) {
                    return true;
                }
            } else {
                String str = Wrappers.packageManager(this.zzj.zzn()).getPackageInfo(zzf2.zzc(), 0).versionName;
                if (zzf2.zzl() != null && zzf2.zzl().equals(str)) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzo() {
        zzw();
        zzk();
        if (!this.zzl) {
            this.zzl = true;
            if (zzab()) {
                int zza2 = zza(this.zzu);
                int zzaf = this.zzj.zzy().zzaf();
                zzw();
                if (zza2 > zzaf) {
                    this.zzj.zzr().zzf().zza("Panic: can't downgrade version. Previous, current version", Integer.valueOf(zza2), Integer.valueOf(zzaf));
                } else if (zza2 >= zzaf) {
                } else {
                    if (zza(zzaf, this.zzu)) {
                        this.zzj.zzr().zzx().zza("Storage version upgraded. Previous, current version", Integer.valueOf(zza2), Integer.valueOf(zzaf));
                    } else {
                        this.zzj.zzr().zzf().zza("Storage version upgrade failed. Previous, current version", Integer.valueOf(zza2), Integer.valueOf(zzaf));
                    }
                }
            }
        }
    }

    private final boolean zzab() {
        FileLock fileLock;
        zzw();
        if (!this.zzj.zzb().zza(zzaq.zzbl) || (fileLock = this.zzt) == null || !fileLock.isValid()) {
            try {
                FileChannel channel = new RandomAccessFile(new File(this.zzj.zzn().getFilesDir(), "google_app_measurement.db"), "rw").getChannel();
                this.zzu = channel;
                FileLock tryLock = channel.tryLock();
                this.zzt = tryLock;
                if (tryLock != null) {
                    this.zzj.zzr().zzx().zza("Storage concurrent access okay");
                    return true;
                }
                this.zzj.zzr().zzf().zza("Storage concurrent data access panic");
                return false;
            } catch (FileNotFoundException e) {
                this.zzj.zzr().zzf().zza("Failed to acquire storage lock", e);
                return false;
            } catch (IOException e2) {
                this.zzj.zzr().zzf().zza("Failed to access storage lock file", e2);
                return false;
            } catch (OverlappingFileLockException e3) {
                this.zzj.zzr().zzi().zza("Storage lock already acquired", e3);
                return false;
            }
        } else {
            this.zzj.zzr().zzx().zza("Storage concurrent access okay");
            return true;
        }
    }

    private final int zza(FileChannel fileChannel) {
        zzw();
        if (fileChannel == null || !fileChannel.isOpen()) {
            this.zzj.zzr().zzf().zza("Bad channel to read from");
            return 0;
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        try {
            fileChannel.position(0);
            int read = fileChannel.read(allocate);
            if (read != 4) {
                if (read != -1) {
                    this.zzj.zzr().zzi().zza("Unexpected data length. Bytes read", Integer.valueOf(read));
                }
                return 0;
            }
            allocate.flip();
            return allocate.getInt();
        } catch (IOException e) {
            this.zzj.zzr().zzf().zza("Failed to read from channel", e);
            return 0;
        }
    }

    private final boolean zza(int i, FileChannel fileChannel) {
        zzw();
        if (fileChannel == null || !fileChannel.isOpen()) {
            this.zzj.zzr().zzf().zza("Bad channel to read from");
            return false;
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.putInt(i);
        allocate.flip();
        try {
            fileChannel.truncate(0);
            if (this.zzj.zzb().zza(zzaq.zzby) && Build.VERSION.SDK_INT <= 19) {
                fileChannel.position(0);
            }
            fileChannel.write(allocate);
            fileChannel.force(true);
            if (fileChannel.size() != 4) {
                this.zzj.zzr().zzf().zza("Error writing to channel. Bytes written", Long.valueOf(fileChannel.size()));
            }
            return true;
        } catch (IOException e) {
            this.zzj.zzr().zzf().zza("Failed to write to channel", e);
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzn zzn2) {
        if (this.zzv != null) {
            ArrayList arrayList = new ArrayList();
            this.zzw = arrayList;
            arrayList.addAll(this.zzv);
        }
        zzad zze2 = zze();
        String str = zzn2.zza;
        Preconditions.checkNotEmpty(str);
        zze2.zzd();
        zze2.zzak();
        try {
            SQLiteDatabase c_ = zze2.mo40210c_();
            String[] strArr = {str};
            int delete = c_.delete("apps", "app_id=?", strArr) + 0 + c_.delete("events", "app_id=?", strArr) + c_.delete("user_attributes", "app_id=?", strArr) + c_.delete("conditional_properties", "app_id=?", strArr) + c_.delete("raw_events", "app_id=?", strArr) + c_.delete("raw_events_metadata", "app_id=?", strArr) + c_.delete("queue", "app_id=?", strArr) + c_.delete("audience_filter_values", "app_id=?", strArr) + c_.delete("main_event_params", "app_id=?", strArr) + c_.delete("default_event_params", "app_id=?", strArr);
            if (delete > 0) {
                zze2.zzr().zzx().zza("Reset analytics data. app, records", str, Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zze2.zzr().zzf().zza("Error resetting analytics data. appId, error", zzez.zza(str), e);
        }
        if (zzn2.zzh) {
            zzb(zzn2);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzkr zzkr, zzn zzn2) {
        int i;
        zzw();
        zzk();
        if (zze(zzn2)) {
            if (!zzn2.zzh) {
                zzc(zzn2);
                return;
            }
            int zzc2 = this.zzj.zzi().zzc(zzkr.zza);
            if (zzc2 != 0) {
                this.zzj.zzi();
                this.zzj.zzi().zza(zzn2.zza, zzc2, "_ev", zzkw.zza(zzkr.zza, 24, true), zzkr.zza != null ? zzkr.zza.length() : 0);
                return;
            }
            int zzb2 = this.zzj.zzi().zzb(zzkr.zza, zzkr.zza());
            if (zzb2 != 0) {
                this.zzj.zzi();
                String zza2 = zzkw.zza(zzkr.zza, 24, true);
                Object zza3 = zzkr.zza();
                if (zza3 == null || (!(zza3 instanceof String) && !(zza3 instanceof CharSequence))) {
                    i = 0;
                } else {
                    i = String.valueOf(zza3).length();
                }
                this.zzj.zzi().zza(zzn2.zza, zzb2, "_ev", zza2, i);
                return;
            }
            Object zzc3 = this.zzj.zzi().zzc(zzkr.zza, zzkr.zza());
            if (zzc3 != null) {
                if ("_sid".equals(zzkr.zza)) {
                    long j = zzkr.zzb;
                    String str = zzkr.zze;
                    long j2 = 0;
                    zzkt zzc4 = zze().zzc(zzn2.zza, "_sno");
                    if (zzc4 == null || !(zzc4.zze instanceof Long)) {
                        if (zzc4 != null) {
                            this.zzj.zzr().zzi().zza("Retrieved last session number from database does not contain a valid (long) value", zzc4.zze);
                        }
                        zzak zza4 = zze().zza(zzn2.zza, "_s");
                        if (zza4 != null) {
                            j2 = zza4.zzc;
                            this.zzj.zzr().zzx().zza("Backfill the session number. Last used session number", Long.valueOf(j2));
                        }
                    } else {
                        j2 = ((Long) zzc4.zze).longValue();
                    }
                    zza(new zzkr("_sno", j, Long.valueOf(j2 + 1), str), zzn2);
                }
                zzkt zzkt = new zzkt(zzn2.zza, zzkr.zze, zzkr.zza, zzkr.zzb, zzc3);
                this.zzj.zzr().zzx().zza("Setting user property", this.zzj.zzj().zzc(zzkt.zzc), zzc3);
                zze().zzf();
                try {
                    zzc(zzn2);
                    boolean zza5 = zze().zza(zzkt);
                    zze().mo40209b_();
                    if (!zza5) {
                        this.zzj.zzr().zzf().zza("Too many unique user properties are set. Ignoring user property", this.zzj.zzj().zzc(zzkt.zzc), zzkt.zze);
                        this.zzj.zzi().zza(zzn2.zza, 9, (String) null, (String) null, 0);
                    }
                } finally {
                    zze().zzh();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzb(zzkr zzkr, zzn zzn2) {
        zzw();
        zzk();
        if (zze(zzn2)) {
            if (!zzn2.zzh) {
                zzc(zzn2);
            } else if (!"_npa".equals(zzkr.zza) || zzn2.zzs == null) {
                this.zzj.zzr().zzw().zza("Removing user property", this.zzj.zzj().zzc(zzkr.zza));
                zze().zzf();
                try {
                    zzc(zzn2);
                    zze().zzb(zzn2.zza, zzkr.zza);
                    zze().mo40209b_();
                    this.zzj.zzr().zzw().zza("User property removed", this.zzj.zzj().zzc(zzkr.zza));
                } finally {
                    zze().zzh();
                }
            } else {
                this.zzj.zzr().zzw().zza("Falling back to manifest metadata value for ad personalization");
                zza(new zzkr("_npa", this.zzj.zzm().currentTimeMillis(), Long.valueOf(zzn2.zzs.booleanValue() ? 1 : 0), DebugKt.DEBUG_PROPERTY_VALUE_AUTO), zzn2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzkl zzkl) {
        this.zzo++;
    }

    /* access modifiers changed from: package-private */
    public final void zzp() {
        this.zzp++;
    }

    /* access modifiers changed from: package-private */
    public final zzgd zzs() {
        return this.zzj;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:157:0x04af A[Catch:{ SQLiteException -> 0x01be, all -> 0x04de }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0122 A[Catch:{ SQLiteException -> 0x01be, all -> 0x04de }] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x01d3 A[Catch:{ SQLiteException -> 0x01be, all -> 0x04de }] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0209 A[Catch:{ SQLiteException -> 0x01be, all -> 0x04de }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x020b A[Catch:{ SQLiteException -> 0x01be, all -> 0x04de }] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x020f A[Catch:{ SQLiteException -> 0x01be, all -> 0x04de }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0232 A[Catch:{ SQLiteException -> 0x01be, all -> 0x04de }] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0239 A[Catch:{ SQLiteException -> 0x01be, all -> 0x04de }] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0247 A[Catch:{ SQLiteException -> 0x01be, all -> 0x04de }] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x025b A[Catch:{ SQLiteException -> 0x01be, all -> 0x04de }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzb(com.google.android.gms.measurement.internal.zzn r22) {
        /*
            r21 = this;
            r1 = r21
            r2 = r22
            java.lang.String r3 = "_sysu"
            java.lang.String r4 = "_sys"
            java.lang.String r5 = "_pfo"
            java.lang.String r6 = "_uwa"
            java.lang.String r0 = "app_id=?"
            r21.zzw()
            r21.zzk()
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r22)
            java.lang.String r7 = r2.zza
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r7)
            boolean r7 = r21.zze(r22)
            if (r7 != 0) goto L_0x0023
            return
        L_0x0023:
            com.google.android.gms.measurement.internal.zzad r7 = r21.zze()
            java.lang.String r8 = r2.zza
            com.google.android.gms.measurement.internal.zzf r7 = r7.zzb(r8)
            r8 = 0
            if (r7 == 0) goto L_0x0056
            java.lang.String r10 = r7.zze()
            boolean r10 = android.text.TextUtils.isEmpty(r10)
            if (r10 == 0) goto L_0x0056
            java.lang.String r10 = r2.zzb
            boolean r10 = android.text.TextUtils.isEmpty(r10)
            if (r10 != 0) goto L_0x0056
            r7.zzh((long) r8)
            com.google.android.gms.measurement.internal.zzad r10 = r21.zze()
            r10.zza((com.google.android.gms.measurement.internal.zzf) r7)
            com.google.android.gms.measurement.internal.zzfx r7 = r21.zzc()
            java.lang.String r10 = r2.zza
            r7.zzd(r10)
        L_0x0056:
            boolean r7 = r2.zzh
            if (r7 != 0) goto L_0x005e
            r21.zzc(r22)
            return
        L_0x005e:
            long r10 = r2.zzm
            int r7 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r7 != 0) goto L_0x006f
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj
            com.google.android.gms.common.util.Clock r7 = r7.zzm()
            long r10 = r7.currentTimeMillis()
        L_0x006f:
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj
            com.google.android.gms.measurement.internal.zzai r7 = r7.zzx()
            r7.zzi()
            int r7 = r2.zzn
            r15 = 1
            if (r7 == 0) goto L_0x009b
            if (r7 == r15) goto L_0x009b
            com.google.android.gms.measurement.internal.zzgd r12 = r1.zzj
            com.google.android.gms.measurement.internal.zzez r12 = r12.zzr()
            com.google.android.gms.measurement.internal.zzfb r12 = r12.zzi()
            java.lang.String r13 = r2.zza
            java.lang.Object r13 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r13)
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            java.lang.String r14 = "Incorrect app type, assuming installed app. appId, appType"
            r12.zza(r14, r13, r7)
            r7 = 0
        L_0x009b:
            com.google.android.gms.measurement.internal.zzad r12 = r21.zze()
            r12.zzf()
            com.google.android.gms.measurement.internal.zzad r12 = r21.zze()     // Catch:{ all -> 0x04de }
            java.lang.String r13 = r2.zza     // Catch:{ all -> 0x04de }
            java.lang.String r14 = "_npa"
            com.google.android.gms.measurement.internal.zzkt r14 = r12.zzc(r13, r14)     // Catch:{ all -> 0x04de }
            if (r14 == 0) goto L_0x00c0
            java.lang.String r12 = "auto"
            java.lang.String r13 = r14.zzb     // Catch:{ all -> 0x04de }
            boolean r12 = r12.equals(r13)     // Catch:{ all -> 0x04de }
            if (r12 == 0) goto L_0x00bc
            goto L_0x00c0
        L_0x00bc:
            r18 = r3
            r3 = r15
            goto L_0x0115
        L_0x00c0:
            java.lang.Boolean r12 = r2.zzs     // Catch:{ all -> 0x04de }
            if (r12 == 0) goto L_0x00fd
            com.google.android.gms.measurement.internal.zzkr r13 = new com.google.android.gms.measurement.internal.zzkr     // Catch:{ all -> 0x04de }
            java.lang.String r18 = "_npa"
            java.lang.Boolean r12 = r2.zzs     // Catch:{ all -> 0x04de }
            boolean r12 = r12.booleanValue()     // Catch:{ all -> 0x04de }
            if (r12 == 0) goto L_0x00d3
            r19 = 1
            goto L_0x00d5
        L_0x00d3:
            r19 = r8
        L_0x00d5:
            java.lang.Long r19 = java.lang.Long.valueOf(r19)     // Catch:{ all -> 0x04de }
            java.lang.String r20 = "auto"
            r8 = 1
            r12 = r13
            r8 = r13
            r13 = r18
            r18 = r3
            r9 = r14
            r3 = r15
            r14 = r10
            r16 = r19
            r17 = r20
            r12.<init>(r13, r14, r16, r17)     // Catch:{ all -> 0x04de }
            if (r9 == 0) goto L_0x00f9
            java.lang.Object r9 = r9.zze     // Catch:{ all -> 0x04de }
            java.lang.Long r12 = r8.zzc     // Catch:{ all -> 0x04de }
            boolean r9 = r9.equals(r12)     // Catch:{ all -> 0x04de }
            if (r9 != 0) goto L_0x0114
        L_0x00f9:
            r1.zza((com.google.android.gms.measurement.internal.zzkr) r8, (com.google.android.gms.measurement.internal.zzn) r2)     // Catch:{ all -> 0x04de }
            goto L_0x0114
        L_0x00fd:
            r18 = r3
            r9 = r14
            r3 = r15
            if (r9 == 0) goto L_0x0114
            com.google.android.gms.measurement.internal.zzkr r8 = new com.google.android.gms.measurement.internal.zzkr     // Catch:{ all -> 0x04de }
            java.lang.String r13 = "_npa"
            r16 = 0
            java.lang.String r17 = "auto"
            r12 = r8
            r14 = r10
            r12.<init>(r13, r14, r16, r17)     // Catch:{ all -> 0x04de }
            r1.zzb((com.google.android.gms.measurement.internal.zzkr) r8, (com.google.android.gms.measurement.internal.zzn) r2)     // Catch:{ all -> 0x04de }
            goto L_0x0115
        L_0x0114:
        L_0x0115:
            com.google.android.gms.measurement.internal.zzad r8 = r21.zze()     // Catch:{ all -> 0x04de }
            java.lang.String r9 = r2.zza     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzf r8 = r8.zzb(r9)     // Catch:{ all -> 0x04de }
            if (r8 == 0) goto L_0x01d1
            com.google.android.gms.measurement.internal.zzgd r12 = r1.zzj     // Catch:{ all -> 0x04de }
            r12.zzi()     // Catch:{ all -> 0x04de }
            java.lang.String r12 = r2.zzb     // Catch:{ all -> 0x04de }
            java.lang.String r13 = r8.zze()     // Catch:{ all -> 0x04de }
            java.lang.String r14 = r2.zzr     // Catch:{ all -> 0x04de }
            java.lang.String r15 = r8.zzf()     // Catch:{ all -> 0x04de }
            boolean r12 = com.google.android.gms.measurement.internal.zzkw.zza((java.lang.String) r12, (java.lang.String) r13, (java.lang.String) r14, (java.lang.String) r15)     // Catch:{ all -> 0x04de }
            if (r12 == 0) goto L_0x01d1
            com.google.android.gms.measurement.internal.zzgd r12 = r1.zzj     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzez r12 = r12.zzr()     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzfb r12 = r12.zzi()     // Catch:{ all -> 0x04de }
            java.lang.String r13 = "New GMP App Id passed in. Removing cached database data. appId"
            java.lang.String r14 = r8.zzc()     // Catch:{ all -> 0x04de }
            java.lang.Object r14 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r14)     // Catch:{ all -> 0x04de }
            r12.zza(r13, r14)     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzad r12 = r21.zze()     // Catch:{ all -> 0x04de }
            java.lang.String r8 = r8.zzc()     // Catch:{ all -> 0x04de }
            r12.zzak()     // Catch:{ all -> 0x04de }
            r12.zzd()     // Catch:{ all -> 0x04de }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r8)     // Catch:{ all -> 0x04de }
            android.database.sqlite.SQLiteDatabase r13 = r12.mo40210c_()     // Catch:{ SQLiteException -> 0x01be }
            java.lang.String[] r14 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x01be }
            r15 = 0
            r14[r15] = r8     // Catch:{ SQLiteException -> 0x01be }
            java.lang.String r9 = "events"
            int r9 = r13.delete(r9, r0, r14)     // Catch:{ SQLiteException -> 0x01be }
            int r9 = r9 + r15
            java.lang.String r15 = "user_attributes"
            int r15 = r13.delete(r15, r0, r14)     // Catch:{ SQLiteException -> 0x01be }
            int r9 = r9 + r15
            java.lang.String r15 = "conditional_properties"
            int r15 = r13.delete(r15, r0, r14)     // Catch:{ SQLiteException -> 0x01be }
            int r9 = r9 + r15
            java.lang.String r15 = "apps"
            int r15 = r13.delete(r15, r0, r14)     // Catch:{ SQLiteException -> 0x01be }
            int r9 = r9 + r15
            java.lang.String r15 = "raw_events"
            int r15 = r13.delete(r15, r0, r14)     // Catch:{ SQLiteException -> 0x01be }
            int r9 = r9 + r15
            java.lang.String r15 = "raw_events_metadata"
            int r15 = r13.delete(r15, r0, r14)     // Catch:{ SQLiteException -> 0x01be }
            int r9 = r9 + r15
            java.lang.String r15 = "event_filters"
            int r15 = r13.delete(r15, r0, r14)     // Catch:{ SQLiteException -> 0x01be }
            int r9 = r9 + r15
            java.lang.String r15 = "property_filters"
            int r15 = r13.delete(r15, r0, r14)     // Catch:{ SQLiteException -> 0x01be }
            int r9 = r9 + r15
            java.lang.String r15 = "audience_filter_values"
            int r0 = r13.delete(r15, r0, r14)     // Catch:{ SQLiteException -> 0x01be }
            int r9 = r9 + r0
            if (r9 <= 0) goto L_0x01bd
            com.google.android.gms.measurement.internal.zzez r0 = r12.zzr()     // Catch:{ SQLiteException -> 0x01be }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzx()     // Catch:{ SQLiteException -> 0x01be }
            java.lang.String r13 = "Deleted application data. app, records"
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ SQLiteException -> 0x01be }
            r0.zza(r13, r8, r9)     // Catch:{ SQLiteException -> 0x01be }
        L_0x01bd:
            goto L_0x01d0
        L_0x01be:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzez r9 = r12.zzr()     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzfb r9 = r9.zzf()     // Catch:{ all -> 0x04de }
            java.lang.String r12 = "Error deleting application data. appId, error"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r8)     // Catch:{ all -> 0x04de }
            r9.zza(r12, r8, r0)     // Catch:{ all -> 0x04de }
        L_0x01d0:
            r8 = 0
        L_0x01d1:
            if (r8 == 0) goto L_0x0232
            long r12 = r8.zzm()     // Catch:{ all -> 0x04de }
            r14 = -2147483648(0xffffffff80000000, double:NaN)
            int r0 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r0 == 0) goto L_0x01ec
            long r12 = r8.zzm()     // Catch:{ all -> 0x04de }
            r9 = r4
            long r3 = r2.zzj     // Catch:{ all -> 0x04de }
            int r0 = (r12 > r3 ? 1 : (r12 == r3 ? 0 : -1))
            if (r0 == 0) goto L_0x01ed
            r0 = 1
            goto L_0x01ee
        L_0x01ec:
            r9 = r4
        L_0x01ed:
            r0 = 0
        L_0x01ee:
            long r3 = r8.zzm()     // Catch:{ all -> 0x04de }
            int r3 = (r3 > r14 ? 1 : (r3 == r14 ? 0 : -1))
            if (r3 != 0) goto L_0x020b
            java.lang.String r3 = r8.zzl()     // Catch:{ all -> 0x04de }
            if (r3 == 0) goto L_0x020b
            java.lang.String r3 = r8.zzl()     // Catch:{ all -> 0x04de }
            java.lang.String r4 = r2.zzc     // Catch:{ all -> 0x04de }
            boolean r3 = r3.equals(r4)     // Catch:{ all -> 0x04de }
            if (r3 != 0) goto L_0x020b
            r14 = 1
            goto L_0x020c
        L_0x020b:
            r14 = 0
        L_0x020c:
            r0 = r0 | r14
            if (r0 == 0) goto L_0x0233
            android.os.Bundle r0 = new android.os.Bundle     // Catch:{ all -> 0x04de }
            r0.<init>()     // Catch:{ all -> 0x04de }
            java.lang.String r3 = "_pv"
            java.lang.String r4 = r8.zzl()     // Catch:{ all -> 0x04de }
            r0.putString(r3, r4)     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzao r3 = new com.google.android.gms.measurement.internal.zzao     // Catch:{ all -> 0x04de }
            java.lang.String r13 = "_au"
            com.google.android.gms.measurement.internal.zzan r14 = new com.google.android.gms.measurement.internal.zzan     // Catch:{ all -> 0x04de }
            r14.<init>(r0)     // Catch:{ all -> 0x04de }
            java.lang.String r15 = "auto"
            r12 = r3
            r16 = r10
            r12.<init>(r13, r14, r15, r16)     // Catch:{ all -> 0x04de }
            r1.zza((com.google.android.gms.measurement.internal.zzao) r3, (com.google.android.gms.measurement.internal.zzn) r2)     // Catch:{ all -> 0x04de }
            goto L_0x0233
        L_0x0232:
            r9 = r4
        L_0x0233:
            r21.zzc(r22)     // Catch:{ all -> 0x04de }
            if (r7 != 0) goto L_0x0247
            com.google.android.gms.measurement.internal.zzad r0 = r21.zze()     // Catch:{ all -> 0x04de }
            java.lang.String r3 = r2.zza     // Catch:{ all -> 0x04de }
            java.lang.String r4 = "_f"
            com.google.android.gms.measurement.internal.zzak r0 = r0.zza((java.lang.String) r3, (java.lang.String) r4)     // Catch:{ all -> 0x04de }
            goto L_0x0259
        L_0x0247:
            r3 = 1
            if (r7 != r3) goto L_0x0258
            com.google.android.gms.measurement.internal.zzad r0 = r21.zze()     // Catch:{ all -> 0x04de }
            java.lang.String r3 = r2.zza     // Catch:{ all -> 0x04de }
            java.lang.String r4 = "_v"
            com.google.android.gms.measurement.internal.zzak r0 = r0.zza((java.lang.String) r3, (java.lang.String) r4)     // Catch:{ all -> 0x04de }
            goto L_0x0259
        L_0x0258:
            r0 = 0
        L_0x0259:
            if (r0 != 0) goto L_0x04af
            r3 = 3600000(0x36ee80, double:1.7786363E-317)
            long r12 = r10 / r3
            r14 = 1
            long r12 = r12 + r14
            long r12 = r12 * r3
            java.lang.String r0 = "_dac"
            java.lang.String r3 = "_r"
            java.lang.String r4 = "_c"
            java.lang.String r8 = "_et"
            if (r7 != 0) goto L_0x0407
            com.google.android.gms.measurement.internal.zzkr r7 = new com.google.android.gms.measurement.internal.zzkr     // Catch:{ all -> 0x04de }
            java.lang.String r14 = "_fot"
            java.lang.Long r16 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x04de }
            java.lang.String r17 = "auto"
            r12 = r7
            r13 = r14
            r14 = r10
            r12.<init>(r13, r14, r16, r17)     // Catch:{ all -> 0x04de }
            r1.zza((com.google.android.gms.measurement.internal.zzkr) r7, (com.google.android.gms.measurement.internal.zzn) r2)     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzy r7 = r7.zzb()     // Catch:{ all -> 0x04de }
            java.lang.String r12 = r2.zzb     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r13 = com.google.android.gms.measurement.internal.zzaq.zzar     // Catch:{ all -> 0x04de }
            boolean r7 = r7.zze(r12, r13)     // Catch:{ all -> 0x04de }
            if (r7 == 0) goto L_0x02a1
            r21.zzw()     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzgd r7 = r1.zzj     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzfq r7 = r7.zzf()     // Catch:{ all -> 0x04de }
            java.lang.String r12 = r2.zza     // Catch:{ all -> 0x04de }
            r7.zza(r12)     // Catch:{ all -> 0x04de }
        L_0x02a1:
            r21.zzw()     // Catch:{ all -> 0x04de }
            r21.zzk()     // Catch:{ all -> 0x04de }
            android.os.Bundle r7 = new android.os.Bundle     // Catch:{ all -> 0x04de }
            r7.<init>()     // Catch:{ all -> 0x04de }
            r12 = 1
            r7.putLong(r4, r12)     // Catch:{ all -> 0x04de }
            r7.putLong(r3, r12)     // Catch:{ all -> 0x04de }
            r3 = 0
            r7.putLong(r6, r3)     // Catch:{ all -> 0x04de }
            r7.putLong(r5, r3)     // Catch:{ all -> 0x04de }
            r7.putLong(r9, r3)     // Catch:{ all -> 0x04de }
            r14 = r18
            r7.putLong(r14, r3)     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzgd r3 = r1.zzj     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzy r3 = r3.zzb()     // Catch:{ all -> 0x04de }
            java.lang.String r4 = r2.zza     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r12 = com.google.android.gms.measurement.internal.zzaq.zzat     // Catch:{ all -> 0x04de }
            boolean r3 = r3.zze(r4, r12)     // Catch:{ all -> 0x04de }
            if (r3 == 0) goto L_0x02db
            r3 = 1
            r7.putLong(r8, r3)     // Catch:{ all -> 0x04de }
        L_0x02db:
            boolean r3 = r2.zzq     // Catch:{ all -> 0x04de }
            if (r3 == 0) goto L_0x02e4
            r3 = 1
            r7.putLong(r0, r3)     // Catch:{ all -> 0x04de }
        L_0x02e4:
            com.google.android.gms.measurement.internal.zzad r0 = r21.zze()     // Catch:{ all -> 0x04de }
            java.lang.String r3 = r2.zza     // Catch:{ all -> 0x04de }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r3)     // Catch:{ all -> 0x04de }
            r0.zzd()     // Catch:{ all -> 0x04de }
            r0.zzak()     // Catch:{ all -> 0x04de }
            java.lang.String r4 = "first_open_count"
            long r3 = r0.zzh(r3, r4)     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzgd r0 = r1.zzj     // Catch:{ all -> 0x04de }
            android.content.Context r0 = r0.zzn()     // Catch:{ all -> 0x04de }
            android.content.pm.PackageManager r0 = r0.getPackageManager()     // Catch:{ all -> 0x04de }
            if (r0 != 0) goto L_0x031f
            com.google.android.gms.measurement.internal.zzgd r0 = r1.zzj     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzez r0 = r0.zzr()     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzf()     // Catch:{ all -> 0x04de }
            java.lang.String r6 = "PackageManager is null, first open report might be inaccurate. appId"
            java.lang.String r9 = r2.zza     // Catch:{ all -> 0x04de }
            java.lang.Object r9 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r9)     // Catch:{ all -> 0x04de }
            r0.zza(r6, r9)     // Catch:{ all -> 0x04de }
            goto L_0x03e9
        L_0x031f:
            com.google.android.gms.measurement.internal.zzgd r0 = r1.zzj     // Catch:{ NameNotFoundException -> 0x0333 }
            android.content.Context r0 = r0.zzn()     // Catch:{ NameNotFoundException -> 0x0333 }
            com.google.android.gms.common.wrappers.PackageManagerWrapper r0 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r0)     // Catch:{ NameNotFoundException -> 0x0333 }
            java.lang.String r12 = r2.zza     // Catch:{ NameNotFoundException -> 0x0333 }
            r13 = 0
            android.content.pm.PackageInfo r0 = r0.getPackageInfo(r12, r13)     // Catch:{ NameNotFoundException -> 0x0333 }
            goto L_0x034b
        L_0x0333:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzgd r12 = r1.zzj     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzez r12 = r12.zzr()     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzfb r12 = r12.zzf()     // Catch:{ all -> 0x04de }
            java.lang.String r13 = "Package info is null, first open report might be inaccurate. appId"
            java.lang.String r15 = r2.zza     // Catch:{ all -> 0x04de }
            java.lang.Object r15 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r15)     // Catch:{ all -> 0x04de }
            r12.zza(r13, r15, r0)     // Catch:{ all -> 0x04de }
            r0 = 0
        L_0x034b:
            if (r0 == 0) goto L_0x03a4
            long r12 = r0.firstInstallTime     // Catch:{ all -> 0x04de }
            r15 = 0
            int r12 = (r12 > r15 ? 1 : (r12 == r15 ? 0 : -1))
            if (r12 == 0) goto L_0x03a2
            long r12 = r0.firstInstallTime     // Catch:{ all -> 0x04de }
            r18 = r14
            long r14 = r0.lastUpdateTime     // Catch:{ all -> 0x04de }
            int r0 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r0 == 0) goto L_0x0385
            com.google.android.gms.measurement.internal.zzgd r0 = r1.zzj     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzy r0 = r0.zzb()     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r12 = com.google.android.gms.measurement.internal.zzaq.zzbs     // Catch:{ all -> 0x04de }
            boolean r0 = r0.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r12)     // Catch:{ all -> 0x04de }
            if (r0 == 0) goto L_0x037e
            r12 = 0
            int r0 = (r3 > r12 ? 1 : (r3 == r12 ? 0 : -1))
            if (r0 != 0) goto L_0x037b
            r12 = 1
            r7.putLong(r6, r12)     // Catch:{ all -> 0x04de }
            goto L_0x0383
        L_0x037b:
            r12 = 1
            goto L_0x0383
        L_0x037e:
            r12 = 1
            r7.putLong(r6, r12)     // Catch:{ all -> 0x04de }
        L_0x0383:
            r14 = 0
            goto L_0x0386
        L_0x0385:
            r14 = 1
        L_0x0386:
            com.google.android.gms.measurement.internal.zzkr r0 = new com.google.android.gms.measurement.internal.zzkr     // Catch:{ all -> 0x04de }
            java.lang.String r13 = "_fi"
            if (r14 == 0) goto L_0x038f
            r14 = 1
            goto L_0x0391
        L_0x038f:
            r14 = 0
        L_0x0391:
            java.lang.Long r16 = java.lang.Long.valueOf(r14)     // Catch:{ all -> 0x04de }
            java.lang.String r17 = "auto"
            r12 = r0
            r6 = r18
            r14 = r10
            r12.<init>(r13, r14, r16, r17)     // Catch:{ all -> 0x04de }
            r1.zza((com.google.android.gms.measurement.internal.zzkr) r0, (com.google.android.gms.measurement.internal.zzn) r2)     // Catch:{ all -> 0x04de }
            goto L_0x03a5
        L_0x03a2:
            r6 = r14
            goto L_0x03a5
        L_0x03a4:
            r6 = r14
        L_0x03a5:
            com.google.android.gms.measurement.internal.zzgd r0 = r1.zzj     // Catch:{ NameNotFoundException -> 0x03b9 }
            android.content.Context r0 = r0.zzn()     // Catch:{ NameNotFoundException -> 0x03b9 }
            com.google.android.gms.common.wrappers.PackageManagerWrapper r0 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r0)     // Catch:{ NameNotFoundException -> 0x03b9 }
            java.lang.String r12 = r2.zza     // Catch:{ NameNotFoundException -> 0x03b9 }
            r13 = 0
            android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo(r12, r13)     // Catch:{ NameNotFoundException -> 0x03b9 }
            goto L_0x03d1
        L_0x03b9:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzgd r12 = r1.zzj     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzez r12 = r12.zzr()     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzfb r12 = r12.zzf()     // Catch:{ all -> 0x04de }
            java.lang.String r13 = "Application info is null, first open report might be inaccurate. appId"
            java.lang.String r14 = r2.zza     // Catch:{ all -> 0x04de }
            java.lang.Object r14 = com.google.android.gms.measurement.internal.zzez.zza((java.lang.String) r14)     // Catch:{ all -> 0x04de }
            r12.zza(r13, r14, r0)     // Catch:{ all -> 0x04de }
            r0 = 0
        L_0x03d1:
            if (r0 == 0) goto L_0x03e9
            int r12 = r0.flags     // Catch:{ all -> 0x04de }
            r13 = 1
            r12 = r12 & r13
            if (r12 == 0) goto L_0x03de
            r12 = 1
            r7.putLong(r9, r12)     // Catch:{ all -> 0x04de }
        L_0x03de:
            int r0 = r0.flags     // Catch:{ all -> 0x04de }
            r0 = r0 & 128(0x80, float:1.794E-43)
            if (r0 == 0) goto L_0x03e9
            r12 = 1
            r7.putLong(r6, r12)     // Catch:{ all -> 0x04de }
        L_0x03e9:
            r12 = 0
            int r0 = (r3 > r12 ? 1 : (r3 == r12 ? 0 : -1))
            if (r0 < 0) goto L_0x03f2
            r7.putLong(r5, r3)     // Catch:{ all -> 0x04de }
        L_0x03f2:
            com.google.android.gms.measurement.internal.zzao r0 = new com.google.android.gms.measurement.internal.zzao     // Catch:{ all -> 0x04de }
            java.lang.String r13 = "_f"
            com.google.android.gms.measurement.internal.zzan r14 = new com.google.android.gms.measurement.internal.zzan     // Catch:{ all -> 0x04de }
            r14.<init>(r7)     // Catch:{ all -> 0x04de }
            java.lang.String r15 = "auto"
            r12 = r0
            r16 = r10
            r12.<init>(r13, r14, r15, r16)     // Catch:{ all -> 0x04de }
            r1.zzb((com.google.android.gms.measurement.internal.zzao) r0, (com.google.android.gms.measurement.internal.zzn) r2)     // Catch:{ all -> 0x04de }
            goto L_0x0465
        L_0x0407:
            r5 = 1
            if (r7 != r5) goto L_0x0465
            com.google.android.gms.measurement.internal.zzkr r5 = new com.google.android.gms.measurement.internal.zzkr     // Catch:{ all -> 0x04de }
            java.lang.String r6 = "_fvt"
            java.lang.Long r16 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x04de }
            java.lang.String r17 = "auto"
            r12 = r5
            r13 = r6
            r14 = r10
            r12.<init>(r13, r14, r16, r17)     // Catch:{ all -> 0x04de }
            r1.zza((com.google.android.gms.measurement.internal.zzkr) r5, (com.google.android.gms.measurement.internal.zzn) r2)     // Catch:{ all -> 0x04de }
            r21.zzw()     // Catch:{ all -> 0x04de }
            r21.zzk()     // Catch:{ all -> 0x04de }
            android.os.Bundle r5 = new android.os.Bundle     // Catch:{ all -> 0x04de }
            r5.<init>()     // Catch:{ all -> 0x04de }
            r6 = 1
            r5.putLong(r4, r6)     // Catch:{ all -> 0x04de }
            r5.putLong(r3, r6)     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzgd r3 = r1.zzj     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzy r3 = r3.zzb()     // Catch:{ all -> 0x04de }
            java.lang.String r4 = r2.zza     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r6 = com.google.android.gms.measurement.internal.zzaq.zzat     // Catch:{ all -> 0x04de }
            boolean r3 = r3.zze(r4, r6)     // Catch:{ all -> 0x04de }
            if (r3 == 0) goto L_0x0447
            r3 = 1
            r5.putLong(r8, r3)     // Catch:{ all -> 0x04de }
        L_0x0447:
            boolean r3 = r2.zzq     // Catch:{ all -> 0x04de }
            if (r3 == 0) goto L_0x0450
            r3 = 1
            r5.putLong(r0, r3)     // Catch:{ all -> 0x04de }
        L_0x0450:
            com.google.android.gms.measurement.internal.zzao r0 = new com.google.android.gms.measurement.internal.zzao     // Catch:{ all -> 0x04de }
            java.lang.String r13 = "_v"
            com.google.android.gms.measurement.internal.zzan r14 = new com.google.android.gms.measurement.internal.zzan     // Catch:{ all -> 0x04de }
            r14.<init>(r5)     // Catch:{ all -> 0x04de }
            java.lang.String r15 = "auto"
            r12 = r0
            r16 = r10
            r12.<init>(r13, r14, r15, r16)     // Catch:{ all -> 0x04de }
            r1.zzb((com.google.android.gms.measurement.internal.zzao) r0, (com.google.android.gms.measurement.internal.zzn) r2)     // Catch:{ all -> 0x04de }
            goto L_0x0466
        L_0x0465:
        L_0x0466:
            com.google.android.gms.measurement.internal.zzgd r0 = r1.zzj     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzy r0 = r0.zzb()     // Catch:{ all -> 0x04de }
            java.lang.String r3 = r2.zza     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r4 = com.google.android.gms.measurement.internal.zzaq.zzau     // Catch:{ all -> 0x04de }
            boolean r0 = r0.zze(r3, r4)     // Catch:{ all -> 0x04de }
            if (r0 != 0) goto L_0x04ce
            android.os.Bundle r0 = new android.os.Bundle     // Catch:{ all -> 0x04de }
            r0.<init>()     // Catch:{ all -> 0x04de }
            r3 = 1
            r0.putLong(r8, r3)     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzgd r3 = r1.zzj     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzy r3 = r3.zzb()     // Catch:{ all -> 0x04de }
            java.lang.String r4 = r2.zza     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r5 = com.google.android.gms.measurement.internal.zzaq.zzat     // Catch:{ all -> 0x04de }
            boolean r3 = r3.zze(r4, r5)     // Catch:{ all -> 0x04de }
            if (r3 == 0) goto L_0x049a
            java.lang.String r3 = "_fr"
            r4 = 1
            r0.putLong(r3, r4)     // Catch:{ all -> 0x04de }
        L_0x049a:
            com.google.android.gms.measurement.internal.zzao r3 = new com.google.android.gms.measurement.internal.zzao     // Catch:{ all -> 0x04de }
            java.lang.String r13 = "_e"
            com.google.android.gms.measurement.internal.zzan r14 = new com.google.android.gms.measurement.internal.zzan     // Catch:{ all -> 0x04de }
            r14.<init>(r0)     // Catch:{ all -> 0x04de }
            java.lang.String r15 = "auto"
            r12 = r3
            r16 = r10
            r12.<init>(r13, r14, r15, r16)     // Catch:{ all -> 0x04de }
            r1.zzb((com.google.android.gms.measurement.internal.zzao) r3, (com.google.android.gms.measurement.internal.zzn) r2)     // Catch:{ all -> 0x04de }
            goto L_0x04ce
        L_0x04af:
            boolean r0 = r2.zzi     // Catch:{ all -> 0x04de }
            if (r0 == 0) goto L_0x04ce
            android.os.Bundle r0 = new android.os.Bundle     // Catch:{ all -> 0x04de }
            r0.<init>()     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzao r3 = new com.google.android.gms.measurement.internal.zzao     // Catch:{ all -> 0x04de }
            java.lang.String r13 = "_cd"
            com.google.android.gms.measurement.internal.zzan r14 = new com.google.android.gms.measurement.internal.zzan     // Catch:{ all -> 0x04de }
            r14.<init>(r0)     // Catch:{ all -> 0x04de }
            java.lang.String r15 = "auto"
            r12 = r3
            r16 = r10
            r12.<init>(r13, r14, r15, r16)     // Catch:{ all -> 0x04de }
            r1.zzb((com.google.android.gms.measurement.internal.zzao) r3, (com.google.android.gms.measurement.internal.zzn) r2)     // Catch:{ all -> 0x04de }
            goto L_0x04cf
        L_0x04ce:
        L_0x04cf:
            com.google.android.gms.measurement.internal.zzad r0 = r21.zze()     // Catch:{ all -> 0x04de }
            r0.mo40209b_()     // Catch:{ all -> 0x04de }
            com.google.android.gms.measurement.internal.zzad r0 = r21.zze()
            r0.zzh()
            return
        L_0x04de:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzad r2 = r21.zze()
            r2.zzh()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzkk.zzb(com.google.android.gms.measurement.internal.zzn):void");
    }

    private final zzn zza(String str) {
        String str2;
        String str3 = str;
        zzf zzb2 = zze().zzb(str3);
        if (zzb2 == null || TextUtils.isEmpty(zzb2.zzl())) {
            this.zzj.zzr().zzw().zza("No app data available; dropping", str3);
            return null;
        }
        Boolean zzb3 = zzb(zzb2);
        if (zzb3 == null || zzb3.booleanValue()) {
            String zze2 = zzb2.zze();
            String zzl2 = zzb2.zzl();
            long zzm2 = zzb2.zzm();
            String zzn2 = zzb2.zzn();
            long zzo2 = zzb2.zzo();
            long zzp2 = zzb2.zzp();
            boolean zzr2 = zzb2.zzr();
            String zzi2 = zzb2.zzi();
            long zzae = zzb2.zzae();
            boolean zzaf = zzb2.zzaf();
            boolean zzag = zzb2.zzag();
            String zzf2 = zzb2.zzf();
            Boolean zzah = zzb2.zzah();
            long zzq2 = zzb2.zzq();
            List<String> zzai = zzb2.zzai();
            if (!zzoe.zzb() || !this.zzj.zzb().zze(str3, zzaq.zzbn)) {
                str2 = null;
            } else {
                str2 = zzb2.zzg();
            }
            return new zzn(str, zze2, zzl2, zzm2, zzn2, zzo2, zzp2, (String) null, zzr2, false, zzi2, zzae, 0, 0, zzaf, zzag, false, zzf2, zzah, zzq2, zzai, str2);
        }
        this.zzj.zzr().zzf().zza("App version does not match; dropping. appId", zzez.zza(str));
        return null;
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzw zzw2) {
        zzn zza2 = zza(zzw2.zza);
        if (zza2 != null) {
            zza(zzw2, zza2);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzw zzw2, zzn zzn2) {
        Preconditions.checkNotNull(zzw2);
        Preconditions.checkNotEmpty(zzw2.zza);
        Preconditions.checkNotNull(zzw2.zzb);
        Preconditions.checkNotNull(zzw2.zzc);
        Preconditions.checkNotEmpty(zzw2.zzc.zza);
        zzw();
        zzk();
        if (zze(zzn2)) {
            if (!zzn2.zzh) {
                zzc(zzn2);
                return;
            }
            zzw zzw3 = new zzw(zzw2);
            boolean z = false;
            zzw3.zze = false;
            zze().zzf();
            try {
                zzw zzd2 = zze().zzd(zzw3.zza, zzw3.zzc.zza);
                if (zzd2 != null && !zzd2.zzb.equals(zzw3.zzb)) {
                    this.zzj.zzr().zzi().zza("Updating a conditional user property with different origin. name, origin, origin (from DB)", this.zzj.zzj().zzc(zzw3.zzc.zza), zzw3.zzb, zzd2.zzb);
                }
                if (zzd2 != null && zzd2.zze) {
                    zzw3.zzb = zzd2.zzb;
                    zzw3.zzd = zzd2.zzd;
                    zzw3.zzh = zzd2.zzh;
                    zzw3.zzf = zzd2.zzf;
                    zzw3.zzi = zzd2.zzi;
                    zzw3.zze = zzd2.zze;
                    zzw3.zzc = new zzkr(zzw3.zzc.zza, zzd2.zzc.zzb, zzw3.zzc.zza(), zzd2.zzc.zze);
                } else if (TextUtils.isEmpty(zzw3.zzf)) {
                    zzw3.zzc = new zzkr(zzw3.zzc.zza, zzw3.zzd, zzw3.zzc.zza(), zzw3.zzc.zze);
                    zzw3.zze = true;
                    z = true;
                }
                if (zzw3.zze) {
                    zzkr zzkr = zzw3.zzc;
                    zzkt zzkt = new zzkt(zzw3.zza, zzw3.zzb, zzkr.zza, zzkr.zzb, zzkr.zza());
                    if (zze().zza(zzkt)) {
                        this.zzj.zzr().zzw().zza("User property updated immediately", zzw3.zza, this.zzj.zzj().zzc(zzkt.zzc), zzkt.zze);
                    } else {
                        this.zzj.zzr().zzf().zza("(2)Too many active user properties, ignoring", zzez.zza(zzw3.zza), this.zzj.zzj().zzc(zzkt.zzc), zzkt.zze);
                    }
                    if (z && zzw3.zzi != null) {
                        zzc(new zzao(zzw3.zzi, zzw3.zzd), zzn2);
                    }
                }
                if (zze().zza(zzw3)) {
                    this.zzj.zzr().zzw().zza("Conditional property added", zzw3.zza, this.zzj.zzj().zzc(zzw3.zzc.zza), zzw3.zzc.zza());
                } else {
                    this.zzj.zzr().zzf().zza("Too many conditional properties, ignoring", zzez.zza(zzw3.zza), this.zzj.zzj().zzc(zzw3.zzc.zza), zzw3.zzc.zza());
                }
                zze().mo40209b_();
            } finally {
                zze().zzh();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzb(zzw zzw2) {
        zzn zza2 = zza(zzw2.zza);
        if (zza2 != null) {
            zzb(zzw2, zza2);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzb(zzw zzw2, zzn zzn2) {
        Bundle bundle;
        Preconditions.checkNotNull(zzw2);
        Preconditions.checkNotEmpty(zzw2.zza);
        Preconditions.checkNotNull(zzw2.zzc);
        Preconditions.checkNotEmpty(zzw2.zzc.zza);
        zzw();
        zzk();
        if (zze(zzn2)) {
            if (!zzn2.zzh) {
                zzc(zzn2);
                return;
            }
            zze().zzf();
            try {
                zzc(zzn2);
                zzw zzd2 = zze().zzd(zzw2.zza, zzw2.zzc.zza);
                if (zzd2 != null) {
                    this.zzj.zzr().zzw().zza("Removing conditional user property", zzw2.zza, this.zzj.zzj().zzc(zzw2.zzc.zza));
                    zze().zze(zzw2.zza, zzw2.zzc.zza);
                    if (zzd2.zze) {
                        zze().zzb(zzw2.zza, zzw2.zzc.zza);
                    }
                    if (zzw2.zzk != null) {
                        if (zzw2.zzk.zzb != null) {
                            bundle = zzw2.zzk.zzb.zzb();
                        } else {
                            bundle = null;
                        }
                        zzc(this.zzj.zzi().zza(zzw2.zza, zzw2.zzk.zza, bundle, zzd2.zzb, zzw2.zzk.zzd, true, false), zzn2);
                    }
                } else {
                    this.zzj.zzr().zzi().zza("Conditional user property doesn't exist", zzez.zza(zzw2.zza), this.zzj.zzj().zzc(zzw2.zzc.zza));
                }
                zze().mo40209b_();
            } finally {
                zze().zzh();
            }
        }
    }

    private final zzf zza(zzn zzn2, zzf zzf2, String str) {
        boolean z;
        boolean z2 = true;
        if (zzf2 == null) {
            zzf2 = new zzf(this.zzj, zzn2.zza);
            zzf2.zza(this.zzj.zzi().zzk());
            zzf2.zze(str);
            z = true;
        } else if (!str.equals(zzf2.zzh())) {
            zzf2.zze(str);
            zzf2.zza(this.zzj.zzi().zzk());
            z = true;
        } else {
            z = false;
        }
        if (!TextUtils.equals(zzn2.zzb, zzf2.zze())) {
            zzf2.zzb(zzn2.zzb);
            z = true;
        }
        if (!TextUtils.equals(zzn2.zzr, zzf2.zzf())) {
            zzf2.zzc(zzn2.zzr);
            z = true;
        }
        if (zzoe.zzb() && this.zzj.zzb().zze(zzf2.zzc(), zzaq.zzbn) && !TextUtils.equals(zzn2.zzv, zzf2.zzg())) {
            zzf2.zzd(zzn2.zzv);
            z = true;
        }
        if (!TextUtils.isEmpty(zzn2.zzk) && !zzn2.zzk.equals(zzf2.zzi())) {
            zzf2.zzf(zzn2.zzk);
            z = true;
        }
        if (!(zzn2.zze == 0 || zzn2.zze == zzf2.zzo())) {
            zzf2.zzd(zzn2.zze);
            z = true;
        }
        if (!TextUtils.isEmpty(zzn2.zzc) && !zzn2.zzc.equals(zzf2.zzl())) {
            zzf2.zzg(zzn2.zzc);
            z = true;
        }
        if (zzn2.zzj != zzf2.zzm()) {
            zzf2.zzc(zzn2.zzj);
            z = true;
        }
        if (zzn2.zzd != null && !zzn2.zzd.equals(zzf2.zzn())) {
            zzf2.zzh(zzn2.zzd);
            z = true;
        }
        if (zzn2.zzf != zzf2.zzp()) {
            zzf2.zze(zzn2.zzf);
            z = true;
        }
        if (zzn2.zzh != zzf2.zzr()) {
            zzf2.zza(zzn2.zzh);
            z = true;
        }
        if (!TextUtils.isEmpty(zzn2.zzg) && !zzn2.zzg.equals(zzf2.zzac())) {
            zzf2.zzi(zzn2.zzg);
            z = true;
        }
        if (!this.zzj.zzb().zza(zzaq.zzcl) && zzn2.zzl != zzf2.zzae()) {
            zzf2.zzp(zzn2.zzl);
            z = true;
        }
        if (zzn2.zzo != zzf2.zzaf()) {
            zzf2.zzb(zzn2.zzo);
            z = true;
        }
        if (zzn2.zzp != zzf2.zzag()) {
            zzf2.zzc(zzn2.zzp);
            z = true;
        }
        if (zzn2.zzs != zzf2.zzah()) {
            zzf2.zza(zzn2.zzs);
            z = true;
        }
        if (zzn2.zzt == 0 || zzn2.zzt == zzf2.zzq()) {
            z2 = z;
        } else {
            zzf2.zzf(zzn2.zzt);
        }
        if (z2) {
            zze().zza(zzf2);
        }
        return zzf2;
    }

    /* access modifiers changed from: package-private */
    public final zzf zzc(zzn zzn2) {
        zzw();
        zzk();
        Preconditions.checkNotNull(zzn2);
        Preconditions.checkNotEmpty(zzn2.zza);
        zzf zzb2 = zze().zzb(zzn2.zza);
        String zzb3 = this.zzj.zzc().zzb(zzn2.zza);
        if (!zznn.zzb() || !this.zzj.zzb().zza(zzaq.zzbt)) {
            return zza(zzn2, zzb2, zzb3);
        }
        if (zzb2 == null) {
            zzb2 = new zzf(this.zzj, zzn2.zza);
            zzb2.zza(this.zzj.zzi().zzk());
            zzb2.zze(zzb3);
        } else if (!zzb3.equals(zzb2.zzh())) {
            zzb2.zze(zzb3);
            zzb2.zza(this.zzj.zzi().zzk());
        }
        zzb2.zzb(zzn2.zzb);
        zzb2.zzc(zzn2.zzr);
        if (zzoe.zzb() && this.zzj.zzb().zze(zzb2.zzc(), zzaq.zzbn)) {
            zzb2.zzd(zzn2.zzv);
        }
        if (!TextUtils.isEmpty(zzn2.zzk)) {
            zzb2.zzf(zzn2.zzk);
        }
        if (zzn2.zze != 0) {
            zzb2.zzd(zzn2.zze);
        }
        if (!TextUtils.isEmpty(zzn2.zzc)) {
            zzb2.zzg(zzn2.zzc);
        }
        zzb2.zzc(zzn2.zzj);
        if (zzn2.zzd != null) {
            zzb2.zzh(zzn2.zzd);
        }
        zzb2.zze(zzn2.zzf);
        zzb2.zza(zzn2.zzh);
        if (!TextUtils.isEmpty(zzn2.zzg)) {
            zzb2.zzi(zzn2.zzg);
        }
        if (!this.zzj.zzb().zza(zzaq.zzcl)) {
            zzb2.zzp(zzn2.zzl);
        }
        zzb2.zzb(zzn2.zzo);
        zzb2.zzc(zzn2.zzp);
        zzb2.zza(zzn2.zzs);
        zzb2.zzf(zzn2.zzt);
        if (zzb2.zza()) {
            zze().zza(zzb2);
        }
        return zzb2;
    }

    /* access modifiers changed from: package-private */
    public final String zzd(zzn zzn2) {
        try {
            return (String) this.zzj.zzq().zza(new zzko(this, zzn2)).get(30000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            this.zzj.zzr().zzf().zza("Failed to get app instance id. appId", zzez.zza(zzn2.zza), e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(boolean z) {
        zzz();
    }

    private final boolean zze(zzn zzn2) {
        return (!zzoe.zzb() || !this.zzj.zzb().zze(zzn2.zza, zzaq.zzbn)) ? !TextUtils.isEmpty(zzn2.zzb) || !TextUtils.isEmpty(zzn2.zzr) : !TextUtils.isEmpty(zzn2.zzb) || !TextUtils.isEmpty(zzn2.zzv) || !TextUtils.isEmpty(zzn2.zzr);
    }
}
