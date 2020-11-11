package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
final class zzhs {
    private static final zzhq<?> zza = new zzhp();
    private static final zzhq<?> zzb = zzc();

    private static zzhq<?> zzc() {
        try {
            return (zzhq) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    static zzhq<?> zza() {
        return zza;
    }

    static zzhq<?> zzb() {
        zzhq<?> zzhq = zzb;
        if (zzhq != null) {
            return zzhq;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
