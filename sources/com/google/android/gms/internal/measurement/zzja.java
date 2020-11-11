package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzib;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
final class zzja implements zzkb {
    private static final zzjk zzb = new zziz();
    private final zzjk zza;

    public zzja() {
        this(new zzjc(zzhz.zza(), zza()));
    }

    private zzja(zzjk zzjk) {
        this.zza = (zzjk) zzie.zza(zzjk, "messageInfoFactory");
    }

    public final <T> zzkc<T> zza(Class<T> cls) {
        zzke.zza((Class<?>) cls);
        zzjh zzb2 = this.zza.zzb(cls);
        if (zzb2.zzb()) {
            if (zzib.class.isAssignableFrom(cls)) {
                return zzjp.zza(zzke.zzc(), zzhs.zza(), zzb2.zzc());
            }
            return zzjp.zza(zzke.zza(), zzhs.zzb(), zzb2.zzc());
        } else if (zzib.class.isAssignableFrom(cls)) {
            if (zza(zzb2)) {
                return zzjn.zza(cls, zzb2, zzjt.zzb(), zzit.zzb(), zzke.zzc(), zzhs.zza(), zzji.zzb());
            }
            return zzjn.zza(cls, zzb2, zzjt.zzb(), zzit.zzb(), zzke.zzc(), (zzhq<?>) null, zzji.zzb());
        } else if (zza(zzb2)) {
            return zzjn.zza(cls, zzb2, zzjt.zza(), zzit.zza(), zzke.zza(), zzhs.zzb(), zzji.zza());
        } else {
            return zzjn.zza(cls, zzb2, zzjt.zza(), zzit.zza(), zzke.zzb(), (zzhq<?>) null, zzji.zza());
        }
    }

    private static boolean zza(zzjh zzjh) {
        return zzjh.zza() == zzib.zzf.zzh;
    }

    private static zzjk zza() {
        try {
            return (zzjk) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke((Object) null, new Object[0]);
        } catch (Exception e) {
            return zzb;
        }
    }
}
