package com.google.android.gms.internal.firebase_auth;

import kotlin.text.Typography;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public enum zzaa implements zzil {
    VERIFY_OP_UNSPECIFIED(0),
    SIGN_UP_OR_IN(1),
    REAUTH(2),
    UPDATE(3),
    LINK(4);
    
    private static final zzik<zzaa> zzf = null;
    private final int zzg;

    public final int zza() {
        return this.zzg;
    }

    public static zzaa zza(int i) {
        if (i == 0) {
            return VERIFY_OP_UNSPECIFIED;
        }
        if (i == 1) {
            return SIGN_UP_OR_IN;
        }
        if (i == 2) {
            return REAUTH;
        }
        if (i == 3) {
            return UPDATE;
        }
        if (i != 4) {
            return null;
        }
        return LINK;
    }

    public static zzin zzb() {
        return zzab.zza;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("<");
        sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" number=").append(this.zzg);
        return sb.append(" name=").append(name()).append(Typography.greater).toString();
    }

    private zzaa(int i) {
        this.zzg = i;
    }

    static {
        zzf = new zzac();
    }
}
