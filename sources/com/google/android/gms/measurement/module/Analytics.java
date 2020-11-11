package com.google.android.gms.measurement.module;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzae;
import com.google.android.gms.measurement.internal.zzgd;
import com.google.android.gms.measurement.internal.zzha;
import com.google.android.gms.measurement.internal.zzhb;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public class Analytics {
    public static final String CRASH_ORIGIN = "crash";
    public static final String FCM_ORIGIN = "fcm";
    public static final String FIAM_ORIGIN = "fiam";
    private static volatile Analytics zza;
    private final zzgd zzb;

    /* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
    public static final class Event extends zzhb {
        public static final String AD_REWARD = "_ar";
        public static final String APP_EXCEPTION = "_ae";

        private Event() {
        }
    }

    /* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
    public static final class Param extends zzha {
        public static final String FATAL = "fatal";
        public static final String TIMESTAMP = "timestamp";
        public static final String TYPE = "type";

        private Param() {
        }
    }

    public static Analytics getInstance(Context context) {
        if (zza == null) {
            synchronized (Analytics.class) {
                if (zza == null) {
                    zza = new Analytics(zzgd.zza(context, (zzae) null, (Long) null));
                }
            }
        }
        return zza;
    }

    private Analytics(zzgd zzgd) {
        Preconditions.checkNotNull(zzgd);
        this.zzb = zzgd;
    }
}
