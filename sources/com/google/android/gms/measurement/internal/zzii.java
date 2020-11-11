package com.google.android.gms.measurement.internal;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzcc;
import com.google.android.gms.internal.measurement.zzib;
import com.google.android.gms.internal.measurement.zzoe;
import com.google.android.gms.internal.measurement.zzof;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlinx.coroutines.DebugKt;

/* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
final class zzii extends zzkl {
    public zzii(zzkk zzkk) {
        super(zzkk);
    }

    /* access modifiers changed from: protected */
    public final boolean zze() {
        return false;
    }

    public final byte[] zza(zzao zzao, String str) {
        zzkt zzkt;
        Bundle bundle;
        zzcc.zzf.zza zza;
        zzcc.zzg.zza zza2;
        zzf zzf;
        byte[] bArr;
        Bundle bundle2;
        zzak zzak;
        long j;
        zzao zzao2 = zzao;
        String str2 = str;
        zzd();
        this.zzy.zzae();
        Preconditions.checkNotNull(zzao);
        Preconditions.checkNotEmpty(str);
        if (!zzt().zze(str2, zzaq.zzax)) {
            zzr().zzw().zza("Generating ScionPayload disabled. packageName", str2);
            return new byte[0];
        } else if ("_iap".equals(zzao2.zza) || "_iapx".equals(zzao2.zza)) {
            zzcc.zzf.zza zzb = zzcc.zzf.zzb();
            zzi().zzf();
            try {
                zzf zzb2 = zzi().zzb(str2);
                if (zzb2 == null) {
                    zzr().zzw().zza("Log and bundle not available. package_name", str2);
                    return new byte[0];
                } else if (!zzb2.zzr()) {
                    zzr().zzw().zza("Log and bundle disabled. package_name", str2);
                    byte[] bArr2 = new byte[0];
                    zzi().zzh();
                    return bArr2;
                } else {
                    zzcc.zzg.zza zza3 = zzcc.zzg.zzbf().zza(1).zza(SystemMediaRouteProvider.PACKAGE_NAME);
                    if (!TextUtils.isEmpty(zzb2.zzc())) {
                        zza3.zzf(zzb2.zzc());
                    }
                    if (!TextUtils.isEmpty(zzb2.zzn())) {
                        zza3.zze(zzb2.zzn());
                    }
                    if (!TextUtils.isEmpty(zzb2.zzl())) {
                        zza3.zzg(zzb2.zzl());
                    }
                    if (zzb2.zzm() != -2147483648L) {
                        zza3.zzh((int) zzb2.zzm());
                    }
                    zza3.zzf(zzb2.zzo()).zzk(zzb2.zzq());
                    if (!zzoe.zzb() || !zzt().zze(zzb2.zzc(), zzaq.zzbn)) {
                        if (!TextUtils.isEmpty(zzb2.zze())) {
                            zza3.zzk(zzb2.zze());
                        } else if (!TextUtils.isEmpty(zzb2.zzf())) {
                            zza3.zzo(zzb2.zzf());
                        }
                    } else if (!TextUtils.isEmpty(zzb2.zze())) {
                        zza3.zzk(zzb2.zze());
                    } else if (!TextUtils.isEmpty(zzb2.zzg())) {
                        zza3.zzp(zzb2.zzg());
                    } else if (!TextUtils.isEmpty(zzb2.zzf())) {
                        zza3.zzo(zzb2.zzf());
                    }
                    zza3.zzh(zzb2.zzp());
                    if (this.zzy.zzab() && zzt().zzg(zza3.zzj())) {
                        zza3.zzj();
                        if (!TextUtils.isEmpty((CharSequence) null)) {
                            zza3.zzn((String) null);
                        }
                    }
                    Pair<String, Boolean> zza4 = zzs().zza(zzb2.zzc());
                    if (zzb2.zzaf() && zza4 != null && !TextUtils.isEmpty((CharSequence) zza4.first)) {
                        zza3.zzh(zza((String) zza4.first, Long.toString(zzao2.zzd)));
                        if (zza4.second != null) {
                            zza3.zza(((Boolean) zza4.second).booleanValue());
                        }
                    }
                    zzl().zzaa();
                    zzcc.zzg.zza zzc = zza3.zzc(Build.MODEL);
                    zzl().zzaa();
                    zzc.zzb(Build.VERSION.RELEASE).zzf((int) zzl().zzf()).zzd(zzl().zzg());
                    try {
                        zza3.zzi(zza(zzb2.zzd(), Long.toString(zzao2.zzd)));
                        if (!TextUtils.isEmpty(zzb2.zzi())) {
                            zza3.zzl(zzb2.zzi());
                        }
                        String zzc2 = zzb2.zzc();
                        List<zzkt> zza5 = zzi().zza(zzc2);
                        Iterator<zzkt> it = zza5.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                zzkt = null;
                                break;
                            }
                            zzkt = it.next();
                            if ("_lte".equals(zzkt.zzc)) {
                                break;
                            }
                        }
                        if (zzkt == null || zzkt.zze == null) {
                            zzkt zzkt2 = new zzkt(zzc2, DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_lte", zzm().currentTimeMillis(), 0L);
                            zza5.add(zzkt2);
                            zzi().zza(zzkt2);
                        }
                        zzks zzg = zzg();
                        zzg.zzr().zzx().zza("Checking account type status for ad personalization signals");
                        if (zzg.zzl().zzj()) {
                            String zzc3 = zzb2.zzc();
                            if (zzb2.zzaf() && zzg.zzj().zze(zzc3)) {
                                zzg.zzr().zzw().zza("Turning off ad personalization due to account type");
                                Iterator<zzkt> it2 = zza5.iterator();
                                while (true) {
                                    if (!it2.hasNext()) {
                                        break;
                                    } else if ("_npa".equals(it2.next().zzc)) {
                                        it2.remove();
                                        break;
                                    }
                                }
                                zza5.add(new zzkt(zzc3, DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_npa", zzg.zzm().currentTimeMillis(), 1L));
                            }
                        }
                        zzcc.zzk[] zzkArr = new zzcc.zzk[zza5.size()];
                        for (int i = 0; i < zza5.size(); i++) {
                            zzcc.zzk.zza zza6 = zzcc.zzk.zzj().zza(zza5.get(i).zzc).zza(zza5.get(i).zzd);
                            zzg().zza(zza6, zza5.get(i).zze);
                            zzkArr[i] = (zzcc.zzk) ((zzib) zza6.zzv());
                        }
                        zza3.zzb((Iterable<? extends zzcc.zzk>) Arrays.asList(zzkArr));
                        if (!zzof.zzb() || !zzt().zza(zzaq.zzcn) || !zzt().zza(zzaq.zzco)) {
                            bundle = zzao2.zzb.zzb();
                        } else {
                            zzfd zza7 = zzfd.zza(zzao);
                            zzp().zza(zza7.zzb, zzi().zzi(str2));
                            zzp().zza(zza7, zzt().zza(str2));
                            bundle = zza7.zzb;
                        }
                        bundle.putLong("_c", 1);
                        zzr().zzw().zza("Marking in-app purchase as real-time");
                        bundle.putLong("_r", 1);
                        bundle.putString("_o", zzao2.zzc);
                        if (zzp().zzf(zza3.zzj())) {
                            zzp().zza(bundle, "_dbg", (Object) 1L);
                            zzp().zza(bundle, "_r", (Object) 1L);
                        }
                        zzak zza8 = zzi().zza(str2, zzao2.zza);
                        if (zza8 == null) {
                            zzf = zzb2;
                            zza2 = zza3;
                            zza = zzb;
                            bundle2 = bundle;
                            bArr = null;
                            zzak = new zzak(str, zzao2.zza, 0, 0, zzao2.zzd, 0, (Long) null, (Long) null, (Long) null, (Boolean) null);
                            j = 0;
                        } else {
                            zzf = zzb2;
                            zza2 = zza3;
                            zza = zzb;
                            bundle2 = bundle;
                            bArr = null;
                            j = zza8.zzf;
                            zzak = zza8.zza(zzao2.zzd);
                        }
                        zzi().zza(zzak);
                        zzal zzal = new zzal(this.zzy, zzao2.zzc, str, zzao2.zza, zzao2.zzd, j, bundle2);
                        zzcc.zzc.zza zzb3 = zzcc.zzc.zzj().zza(zzal.zzc).zza(zzal.zzb).zzb(zzal.zzd);
                        Iterator<String> it3 = zzal.zze.iterator();
                        while (it3.hasNext()) {
                            String next = it3.next();
                            zzcc.zze.zza zza9 = zzcc.zze.zzm().zza(next);
                            zzg().zza(zza9, zzal.zze.zza(next));
                            zzb3.zza(zza9);
                        }
                        zzcc.zzg.zza zza10 = zza2;
                        zza10.zza(zzb3).zza(zzcc.zzh.zza().zza(zzcc.zzd.zza().zza(zzak.zzc).zza(zzao2.zza)));
                        zza10.zzc((Iterable<? extends zzcc.zza>) mo40433e_().zza(zzf.zzc(), Collections.emptyList(), zza10.zzd(), Long.valueOf(zzb3.zzf()), Long.valueOf(zzb3.zzf())));
                        if (zzb3.zze()) {
                            zza10.zzb(zzb3.zzf()).zzc(zzb3.zzf());
                        }
                        long zzk = zzf.zzk();
                        int i2 = (zzk > 0 ? 1 : (zzk == 0 ? 0 : -1));
                        if (i2 != 0) {
                            zza10.zze(zzk);
                        }
                        long zzj = zzf.zzj();
                        if (zzj != 0) {
                            zza10.zzd(zzj);
                        } else if (i2 != 0) {
                            zza10.zzd(zzk);
                        }
                        zzf.zzv();
                        zza10.zzg((int) zzf.zzs()).zzg(zzt().zzf()).zza(zzm().currentTimeMillis()).zzb(Boolean.TRUE.booleanValue());
                        zzcc.zzf.zza zza11 = zza;
                        zza11.zza(zza10);
                        zzf zzf2 = zzf;
                        zzf2.zza(zza10.zzf());
                        zzf2.zzb(zza10.zzg());
                        zzi().zza(zzf2);
                        zzi().mo40209b_();
                        zzi().zzh();
                        try {
                            return zzg().zzc(((zzcc.zzf) ((zzib) zza11.zzv())).zzbi());
                        } catch (IOException e) {
                            zzr().zzf().zza("Data loss. Failed to bundle and serialize. appId", zzez.zza(str), e);
                            return bArr;
                        }
                    } catch (SecurityException e2) {
                        zzr().zzw().zza("app instance id encryption failed", e2.getMessage());
                        byte[] bArr3 = new byte[0];
                        zzi().zzh();
                        return bArr3;
                    }
                }
            } catch (SecurityException e3) {
                zzr().zzw().zza("Resettable device id encryption failed", e3.getMessage());
                return new byte[0];
            } finally {
                zzi().zzh();
            }
        } else {
            zzr().zzw().zza("Generating a payload for this event is not available. package_name, event_name", str2, zzao2.zza);
            return null;
        }
    }

    private static String zza(String str, String str2) {
        throw new SecurityException("This implementation should not be used.");
    }
}
