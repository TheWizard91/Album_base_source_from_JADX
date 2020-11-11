package com.google.android.gms.internal.firebase_auth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzah extends zzai {
    private final char zza = '.';

    zzah(char c) {
    }

    public final boolean zza(char c) {
        return c == this.zza;
    }

    public final String toString() {
        String zzb = zzaf.zzc(this.zza);
        return new StringBuilder(String.valueOf(zzb).length() + 18).append("CharMatcher.is('").append(zzb).append("')").toString();
    }
}
