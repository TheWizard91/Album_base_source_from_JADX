package com.google.android.datatransport.cct.p011a;

/* renamed from: com.google.android.datatransport.cct.a.zzl */
final class zzl extends zzs {
    private final long zza;

    zzl(long j) {
        this.zza = j;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzs)) {
            return false;
        }
        if (this.zza == ((zzs) obj).zza()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        long j = this.zza;
        return ((int) (j ^ (j >>> 32))) ^ 1000003;
    }

    public String toString() {
        return "LogResponse{nextRequestWaitMillis=" + this.zza + "}";
    }

    public long zza() {
        return this.zza;
    }
}
