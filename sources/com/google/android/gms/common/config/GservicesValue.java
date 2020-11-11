package com.google.android.gms.common.config;

import android.content.Context;
import android.os.Binder;
import android.os.StrictMode;
import android.util.Log;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-basement@@17.2.1 */
public abstract class GservicesValue<T> {
    private static final Object lock = new Object();
    private static int sharedUserId = 0;
    private static zza zzbx = null;
    private static Context zzby;
    private static Set<String> zzbz;
    protected final String mKey;
    protected final T zzca;
    private T zzcb = null;

    /* compiled from: com.google.android.gms:play-services-basement@@17.2.1 */
    private interface zza {
        Long getLong(String str, Long l);

        String getString(String str, String str2);

        Boolean zza(String str, Boolean bool);

        Float zza(String str, Float f);

        Integer zza(String str, Integer num);
    }

    private static boolean zzi() {
        synchronized (lock) {
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public abstract T zzd(String str);

    public static boolean isInitialized() {
        synchronized (lock) {
        }
        return false;
    }

    protected GservicesValue(String str, T t) {
        this.mKey = str;
        this.zzca = t;
    }

    public void override(T t) {
        Log.w("GservicesValue", "GservicesValue.override(): test should probably call initForTests() first");
        this.zzcb = t;
        synchronized (lock) {
            zzi();
        }
    }

    public void resetOverride() {
        this.zzcb = null;
    }

    public final T get() {
        long clearCallingIdentity;
        T t = this.zzcb;
        if (t != null) {
            return t;
        }
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        Object obj = lock;
        synchronized (obj) {
        }
        synchronized (obj) {
            zzbz = null;
            zzby = null;
        }
        try {
            T zzd = zzd(this.mKey);
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            return zzd;
        } catch (SecurityException e) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            T zzd2 = zzd(this.mKey);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            return zzd2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @Deprecated
    public final T getBinderSafe() {
        return get();
    }

    public static GservicesValue<Boolean> value(String str, boolean z) {
        return new zzb(str, Boolean.valueOf(z));
    }

    public static GservicesValue<Long> value(String str, Long l) {
        return new zza(str, l);
    }

    public static GservicesValue<Integer> value(String str, Integer num) {
        return new zzd(str, num);
    }

    public static GservicesValue<Float> value(String str, Float f) {
        return new zzc(str, f);
    }

    public static GservicesValue<String> value(String str, String str2) {
        return new zze(str, str2);
    }
}
