package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
final class zzji {
    private static final zzjg zza = zzc();
    private static final zzjg zzb = new zzjf();

    static zzjg zza() {
        return zza;
    }

    static zzjg zzb() {
        return zzb;
    }

    private static zzjg zzc() {
        try {
            return (zzjg) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
