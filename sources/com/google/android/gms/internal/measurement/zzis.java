package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
public class zzis {
    private static final zzho zza = zzho.zza();
    private zzgt zzb;
    private volatile zzjj zzc;
    private volatile zzgt zzd;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzis)) {
            return false;
        }
        zzis zzis = (zzis) obj;
        zzjj zzjj = this.zzc;
        zzjj zzjj2 = zzis.zzc;
        if (zzjj == null && zzjj2 == null) {
            return zzc().equals(zzis.zzc());
        }
        if (zzjj != null && zzjj2 != null) {
            return zzjj.equals(zzjj2);
        }
        if (zzjj != null) {
            return zzjj.equals(zzis.zzb(zzjj.mo38046h_()));
        }
        return zzb(zzjj2.mo38046h_()).equals(zzjj2);
    }

    public int hashCode() {
        return 1;
    }

    private final zzjj zzb(zzjj zzjj) {
        if (this.zzc == null) {
            synchronized (this) {
                if (this.zzc == null) {
                    try {
                        this.zzc = zzjj;
                        this.zzd = zzgt.zza;
                    } catch (zzij e) {
                        this.zzc = zzjj;
                        this.zzd = zzgt.zza;
                    }
                }
            }
        }
        return this.zzc;
    }

    public final zzjj zza(zzjj zzjj) {
        zzjj zzjj2 = this.zzc;
        this.zzb = null;
        this.zzd = null;
        this.zzc = zzjj;
        return zzjj2;
    }

    public final int zzb() {
        if (this.zzd != null) {
            return this.zzd.zza();
        }
        if (this.zzc != null) {
            return this.zzc.zzbm();
        }
        return 0;
    }

    public final zzgt zzc() {
        if (this.zzd != null) {
            return this.zzd;
        }
        synchronized (this) {
            if (this.zzd != null) {
                zzgt zzgt = this.zzd;
                return zzgt;
            }
            if (this.zzc == null) {
                this.zzd = zzgt.zza;
            } else {
                this.zzd = this.zzc.zzbh();
            }
            zzgt zzgt2 = this.zzd;
            return zzgt2;
        }
    }
}
