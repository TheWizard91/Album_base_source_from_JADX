package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzib;
import java.util.List;
import kotlin.text.Typography;

/* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
public final class zzbu {

    /* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
    public static final class zza extends zzib<zza, C4079zza> implements zzjl {
        /* access modifiers changed from: private */
        public static final zza zzi;
        private static volatile zzjw<zza> zzj;
        private int zzc;
        private int zzd;
        private zzik<zze> zze = zzbp();
        private zzik<zzb> zzf = zzbp();
        private boolean zzg;
        private boolean zzh;

        private zza() {
        }

        /* renamed from: com.google.android.gms.internal.measurement.zzbu$zza$zza  reason: collision with other inner class name */
        /* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
        public static final class C4079zza extends zzib.zza<zza, C4079zza> implements zzjl {
            private C4079zza() {
                super(zza.zzi);
            }

            public final int zza() {
                return ((zza) this.zza).zzd();
            }

            public final zze zza(int i) {
                return ((zza) this.zza).zza(i);
            }

            public final C4079zza zza(int i, zze.zza zza) {
                if (this.zzb) {
                    zzq();
                    this.zzb = false;
                }
                ((zza) this.zza).zza(i, (zze) ((zzib) zza.zzv()));
                return this;
            }

            public final int zzb() {
                return ((zza) this.zza).zzf();
            }

            public final zzb zzb(int i) {
                return ((zza) this.zza).zzb(i);
            }

            public final C4079zza zza(int i, zzb.zza zza) {
                if (this.zzb) {
                    zzq();
                    this.zzb = false;
                }
                ((zza) this.zza).zza(i, (zzb) ((zzib) zza.zzv()));
                return this;
            }

            /* synthetic */ C4079zza(zzbt zzbt) {
                this();
            }
        }

        public final boolean zza() {
            return (this.zzc & 1) != 0;
        }

        public final int zzb() {
            return this.zzd;
        }

        public final List<zze> zzc() {
            return this.zze;
        }

        public final int zzd() {
            return this.zze.size();
        }

        public final zze zza(int i) {
            return (zze) this.zze.get(i);
        }

        /* access modifiers changed from: private */
        public final void zza(int i, zze zze2) {
            zze2.getClass();
            zzik<zze> zzik = this.zze;
            if (!zzik.zza()) {
                this.zze = zzib.zza(zzik);
            }
            this.zze.set(i, zze2);
        }

        public final List<zzb> zze() {
            return this.zzf;
        }

        public final int zzf() {
            return this.zzf.size();
        }

        public final zzb zzb(int i) {
            return (zzb) this.zzf.get(i);
        }

        /* access modifiers changed from: private */
        public final void zza(int i, zzb zzb) {
            zzb.getClass();
            zzik<zzb> zzik = this.zzf;
            if (!zzik.zza()) {
                this.zzf = zzib.zza(zzik);
            }
            this.zzf.set(i, zzb);
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzbt.zza[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C4079zza((zzbt) null);
                case 3:
                    return zza((zzjj) zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001င\u0000\u0002\u001b\u0003\u001b\u0004ဇ\u0001\u0005ဇ\u0002", new Object[]{"zzc", "zzd", "zze", zze.class, "zzf", zzb.class, "zzg", "zzh"});
                case 4:
                    return zzi;
                case 5:
                    zzjw<zza> zzjw = zzj;
                    if (zzjw == null) {
                        synchronized (zza.class) {
                            zzjw = zzj;
                            if (zzjw == null) {
                                zzjw = new zzib.zzc<>(zzi);
                                zzj = zzjw;
                            }
                        }
                    }
                    return zzjw;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zza zza = new zza();
            zzi = zza;
            zzib.zza(zza.class, zza);
        }
    }

    /* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
    public static final class zzb extends zzib<zzb, zza> implements zzjl {
        /* access modifiers changed from: private */
        public static final zzb zzl;
        private static volatile zzjw<zzb> zzm;
        private int zzc;
        private int zzd;
        private String zze = "";
        private zzik<zzc> zzf = zzbp();
        private boolean zzg;
        private zzd zzh;
        private boolean zzi;
        private boolean zzj;
        private boolean zzk;

        private zzb() {
        }

        /* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
        public static final class zza extends zzib.zza<zzb, zza> implements zzjl {
            private zza() {
                super(zzb.zzl);
            }

            public final String zza() {
                return ((zzb) this.zza).zzc();
            }

            public final zza zza(String str) {
                if (this.zzb) {
                    zzq();
                    this.zzb = false;
                }
                ((zzb) this.zza).zza(str);
                return this;
            }

            public final int zzb() {
                return ((zzb) this.zza).zze();
            }

            public final zzc zza(int i) {
                return ((zzb) this.zza).zza(i);
            }

            public final zza zza(int i, zzc zzc) {
                if (this.zzb) {
                    zzq();
                    this.zzb = false;
                }
                ((zzb) this.zza).zza(i, zzc);
                return this;
            }

            /* synthetic */ zza(zzbt zzbt) {
                this();
            }
        }

        public final boolean zza() {
            return (this.zzc & 1) != 0;
        }

        public final int zzb() {
            return this.zzd;
        }

        public final String zzc() {
            return this.zze;
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 2;
            this.zze = str;
        }

        public final List<zzc> zzd() {
            return this.zzf;
        }

        public final int zze() {
            return this.zzf.size();
        }

        public final zzc zza(int i) {
            return (zzc) this.zzf.get(i);
        }

        /* access modifiers changed from: private */
        public final void zza(int i, zzc zzc2) {
            zzc2.getClass();
            zzik<zzc> zzik = this.zzf;
            if (!zzik.zza()) {
                this.zzf = zzib.zza(zzik);
            }
            this.zzf.set(i, zzc2);
        }

        public final boolean zzf() {
            return (this.zzc & 8) != 0;
        }

        public final zzd zzg() {
            zzd zzd2 = this.zzh;
            return zzd2 == null ? zzd.zzk() : zzd2;
        }

        public final boolean zzh() {
            return this.zzi;
        }

        public final boolean zzi() {
            return this.zzj;
        }

        public final boolean zzj() {
            return (this.zzc & 64) != 0;
        }

        public final boolean zzk() {
            return this.zzk;
        }

        public static zza zzl() {
            return (zza) zzl.zzbk();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzbt.zza[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza((zzbt) null);
                case 3:
                    return zza((zzjj) zzl, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0001\u0000\u0001င\u0000\u0002ဈ\u0001\u0003\u001b\u0004ဇ\u0002\u0005ဉ\u0003\u0006ဇ\u0004\u0007ဇ\u0005\bဇ\u0006", new Object[]{"zzc", "zzd", "zze", "zzf", zzc.class, "zzg", "zzh", "zzi", "zzj", "zzk"});
                case 4:
                    return zzl;
                case 5:
                    zzjw<zzb> zzjw = zzm;
                    if (zzjw == null) {
                        synchronized (zzb.class) {
                            zzjw = zzm;
                            if (zzjw == null) {
                                zzjw = new zzib.zzc<>(zzl);
                                zzm = zzjw;
                            }
                        }
                    }
                    return zzjw;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzb zzb = new zzb();
            zzl = zzb;
            zzib.zza(zzb.class, zzb);
        }
    }

    /* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
    public static final class zzc extends zzib<zzc, zza> implements zzjl {
        /* access modifiers changed from: private */
        public static final zzc zzh;
        private static volatile zzjw<zzc> zzi;
        private int zzc;
        private zzf zzd;
        private zzd zze;
        private boolean zzf;
        private String zzg = "";

        private zzc() {
        }

        /* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
        public static final class zza extends zzib.zza<zzc, zza> implements zzjl {
            private zza() {
                super(zzc.zzh);
            }

            public final zza zza(String str) {
                if (this.zzb) {
                    zzq();
                    this.zzb = false;
                }
                ((zzc) this.zza).zza(str);
                return this;
            }

            /* synthetic */ zza(zzbt zzbt) {
                this();
            }
        }

        public final boolean zza() {
            return (this.zzc & 1) != 0;
        }

        public final zzf zzb() {
            zzf zzf2 = this.zzd;
            return zzf2 == null ? zzf.zzi() : zzf2;
        }

        public final boolean zzc() {
            return (this.zzc & 2) != 0;
        }

        public final zzd zzd() {
            zzd zzd2 = this.zze;
            return zzd2 == null ? zzd.zzk() : zzd2;
        }

        public final boolean zze() {
            return (this.zzc & 4) != 0;
        }

        public final boolean zzf() {
            return this.zzf;
        }

        public final boolean zzg() {
            return (this.zzc & 8) != 0;
        }

        public final String zzh() {
            return this.zzg;
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 8;
            this.zzg = str;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzbt.zza[i - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new zza((zzbt) null);
                case 3:
                    return zza((zzjj) zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဇ\u0002\u0004ဈ\u0003", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg"});
                case 4:
                    return zzh;
                case 5:
                    zzjw<zzc> zzjw = zzi;
                    if (zzjw == null) {
                        synchronized (zzc.class) {
                            zzjw = zzi;
                            if (zzjw == null) {
                                zzjw = new zzib.zzc<>(zzh);
                                zzi = zzjw;
                            }
                        }
                    }
                    return zzjw;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzc zzi() {
            return zzh;
        }

        static {
            zzc zzc2 = new zzc();
            zzh = zzc2;
            zzib.zza(zzc.class, zzc2);
        }
    }

    /* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
    public static final class zzd extends zzib<zzd, zza> implements zzjl {
        /* access modifiers changed from: private */
        public static final zzd zzi;
        private static volatile zzjw<zzd> zzj;
        private int zzc;
        private int zzd;
        private boolean zze;
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";

        /* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
        public enum zzb implements zzid {
            UNKNOWN_COMPARISON_TYPE(0),
            LESS_THAN(1),
            GREATER_THAN(2),
            EQUAL(3),
            BETWEEN(4);
            
            private static final zzig<zzb> zzf = null;
            private final int zzg;

            public final int zza() {
                return this.zzg;
            }

            public static zzb zza(int i) {
                if (i == 0) {
                    return UNKNOWN_COMPARISON_TYPE;
                }
                if (i == 1) {
                    return LESS_THAN;
                }
                if (i == 2) {
                    return GREATER_THAN;
                }
                if (i == 3) {
                    return EQUAL;
                }
                if (i != 4) {
                    return null;
                }
                return BETWEEN;
            }

            public static zzif zzb() {
                return zzbw.zza;
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzg);
                return sb.append(" name=").append(name()).append(Typography.greater).toString();
            }

            private zzb(int i) {
                this.zzg = i;
            }

            static {
                zzf = new zzbv();
            }
        }

        private zzd() {
        }

        /* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
        public static final class zza extends zzib.zza<zzd, zza> implements zzjl {
            private zza() {
                super(zzd.zzi);
            }

            /* synthetic */ zza(zzbt zzbt) {
                this();
            }
        }

        public final boolean zza() {
            return (this.zzc & 1) != 0;
        }

        public final zzb zzb() {
            zzb zza2 = zzb.zza(this.zzd);
            return zza2 == null ? zzb.UNKNOWN_COMPARISON_TYPE : zza2;
        }

        public final boolean zzc() {
            return (this.zzc & 2) != 0;
        }

        public final boolean zzd() {
            return this.zze;
        }

        public final boolean zze() {
            return (this.zzc & 4) != 0;
        }

        public final String zzf() {
            return this.zzf;
        }

        public final boolean zzg() {
            return (this.zzc & 8) != 0;
        }

        public final String zzh() {
            return this.zzg;
        }

        public final boolean zzi() {
            return (this.zzc & 16) != 0;
        }

        public final String zzj() {
            return this.zzh;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzbt.zza[i - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zza((zzbt) null);
                case 3:
                    return zza((zzjj) zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004", new Object[]{"zzc", "zzd", zzb.zzb(), "zze", "zzf", "zzg", "zzh"});
                case 4:
                    return zzi;
                case 5:
                    zzjw<zzd> zzjw = zzj;
                    if (zzjw == null) {
                        synchronized (zzd.class) {
                            zzjw = zzj;
                            if (zzjw == null) {
                                zzjw = new zzib.zzc<>(zzi);
                                zzj = zzjw;
                            }
                        }
                    }
                    return zzjw;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzd zzk() {
            return zzi;
        }

        static {
            zzd zzd2 = new zzd();
            zzi = zzd2;
            zzib.zza(zzd.class, zzd2);
        }
    }

    /* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
    public static final class zze extends zzib<zze, zza> implements zzjl {
        /* access modifiers changed from: private */
        public static final zze zzj;
        private static volatile zzjw<zze> zzk;
        private int zzc;
        private int zzd;
        private String zze = "";
        private zzc zzf;
        private boolean zzg;
        private boolean zzh;
        private boolean zzi;

        private zze() {
        }

        /* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
        public static final class zza extends zzib.zza<zze, zza> implements zzjl {
            private zza() {
                super(zze.zzj);
            }

            public final zza zza(String str) {
                if (this.zzb) {
                    zzq();
                    this.zzb = false;
                }
                ((zze) this.zza).zza(str);
                return this;
            }

            /* synthetic */ zza(zzbt zzbt) {
                this();
            }
        }

        public final boolean zza() {
            return (this.zzc & 1) != 0;
        }

        public final int zzb() {
            return this.zzd;
        }

        public final String zzc() {
            return this.zze;
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 2;
            this.zze = str;
        }

        public final zzc zzd() {
            zzc zzc2 = this.zzf;
            return zzc2 == null ? zzc.zzi() : zzc2;
        }

        public final boolean zze() {
            return this.zzg;
        }

        public final boolean zzf() {
            return this.zzh;
        }

        public final boolean zzg() {
            return (this.zzc & 32) != 0;
        }

        public final boolean zzh() {
            return this.zzi;
        }

        public static zza zzi() {
            return (zza) zzj.zzbk();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzbt.zza[i - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zza((zzbt) null);
                case 3:
                    return zza((zzjj) zzj, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001င\u0000\u0002ဈ\u0001\u0003ဉ\u0002\u0004ဇ\u0003\u0005ဇ\u0004\u0006ဇ\u0005", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi"});
                case 4:
                    return zzj;
                case 5:
                    zzjw<zze> zzjw = zzk;
                    if (zzjw == null) {
                        synchronized (zze.class) {
                            zzjw = zzk;
                            if (zzjw == null) {
                                zzjw = new zzib.zzc<>(zzj);
                                zzk = zzjw;
                            }
                        }
                    }
                    return zzjw;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zze zze2 = new zze();
            zzj = zze2;
            zzib.zza(zze.class, zze2);
        }
    }

    /* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
    public static final class zzf extends zzib<zzf, zzb> implements zzjl {
        /* access modifiers changed from: private */
        public static final zzf zzh;
        private static volatile zzjw<zzf> zzi;
        private int zzc;
        private int zzd;
        private String zze = "";
        private boolean zzf;
        private zzik<String> zzg = zzib.zzbp();

        /* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
        public enum zza implements zzid {
            UNKNOWN_MATCH_TYPE(0),
            REGEXP(1),
            BEGINS_WITH(2),
            ENDS_WITH(3),
            PARTIAL(4),
            EXACT(5),
            IN_LIST(6);
            
            private static final zzig<zza> zzh = null;
            private final int zzi;

            public final int zza() {
                return this.zzi;
            }

            public static zza zza(int i) {
                switch (i) {
                    case 0:
                        return UNKNOWN_MATCH_TYPE;
                    case 1:
                        return REGEXP;
                    case 2:
                        return BEGINS_WITH;
                    case 3:
                        return ENDS_WITH;
                    case 4:
                        return PARTIAL;
                    case 5:
                        return EXACT;
                    case 6:
                        return IN_LIST;
                    default:
                        return null;
                }
            }

            public static zzif zzb() {
                return zzbx.zza;
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzi);
                return sb.append(" name=").append(name()).append(Typography.greater).toString();
            }

            private zza(int i) {
                this.zzi = i;
            }

            static {
                zzh = new zzby();
            }
        }

        private zzf() {
        }

        /* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
        public static final class zzb extends zzib.zza<zzf, zzb> implements zzjl {
            private zzb() {
                super(zzf.zzh);
            }

            /* synthetic */ zzb(zzbt zzbt) {
                this();
            }
        }

        public final boolean zza() {
            return (this.zzc & 1) != 0;
        }

        public final zza zzb() {
            zza zza2 = zza.zza(this.zzd);
            return zza2 == null ? zza.UNKNOWN_MATCH_TYPE : zza2;
        }

        public final boolean zzc() {
            return (this.zzc & 2) != 0;
        }

        public final String zzd() {
            return this.zze;
        }

        public final boolean zze() {
            return (this.zzc & 4) != 0;
        }

        public final boolean zzf() {
            return this.zzf;
        }

        public final List<String> zzg() {
            return this.zzg;
        }

        public final int zzh() {
            return this.zzg.size();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzbt.zza[i - 1]) {
                case 1:
                    return new zzf();
                case 2:
                    return new zzb((zzbt) null);
                case 3:
                    return zza((zzjj) zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001ဌ\u0000\u0002ဈ\u0001\u0003ဇ\u0002\u0004\u001a", new Object[]{"zzc", "zzd", zza.zzb(), "zze", "zzf", "zzg"});
                case 4:
                    return zzh;
                case 5:
                    zzjw<zzf> zzjw = zzi;
                    if (zzjw == null) {
                        synchronized (zzf.class) {
                            zzjw = zzi;
                            if (zzjw == null) {
                                zzjw = new zzib.zzc<>(zzh);
                                zzi = zzjw;
                            }
                        }
                    }
                    return zzjw;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzf zzi() {
            return zzh;
        }

        static {
            zzf zzf2 = new zzf();
            zzh = zzf2;
            zzib.zza(zzf.class, zzf2);
        }
    }
}
