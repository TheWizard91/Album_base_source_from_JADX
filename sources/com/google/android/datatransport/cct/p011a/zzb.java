package com.google.android.datatransport.cct.p011a;

import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import com.google.firebase.encoders.config.Configurator;
import com.google.firebase.encoders.config.EncoderConfig;
import java.io.IOException;

/* renamed from: com.google.android.datatransport.cct.a.zzb */
public final class zzb implements Configurator {
    public static final Configurator zza = new zzb();

    /* renamed from: com.google.android.datatransport.cct.a.zzb$zza */
    private static final class zza implements ObjectEncoder<zza> {
        static final zza zza = new zza();

        private zza() {
        }

        public void encode(Object obj, Object obj2) throws IOException {
            zza zza2 = (zza) obj;
            ObjectEncoderContext objectEncoderContext = (ObjectEncoderContext) obj2;
            objectEncoderContext.add("sdkVersion", (Object) zza2.zzi());
            objectEncoderContext.add("model", (Object) zza2.zzf());
            objectEncoderContext.add("hardware", (Object) zza2.zzd());
            objectEncoderContext.add("device", (Object) zza2.zzb());
            objectEncoderContext.add("product", (Object) zza2.zzh());
            objectEncoderContext.add("osBuild", (Object) zza2.zzg());
            objectEncoderContext.add("manufacturer", (Object) zza2.zze());
            objectEncoderContext.add("fingerprint", (Object) zza2.zzc());
        }
    }

    /* renamed from: com.google.android.datatransport.cct.a.zzb$zzb  reason: collision with other inner class name */
    private static final class C4072zzb implements ObjectEncoder<zzo> {
        static final C4072zzb zza = new C4072zzb();

        private C4072zzb() {
        }

        public void encode(Object obj, Object obj2) throws IOException {
            ((ObjectEncoderContext) obj2).add("logRequest", (Object) ((zzo) obj).zza());
        }
    }

    /* renamed from: com.google.android.datatransport.cct.a.zzb$zzc */
    private static final class zzc implements ObjectEncoder<zzp> {
        static final zzc zza = new zzc();

        private zzc() {
        }

        public void encode(Object obj, Object obj2) throws IOException {
            zzp zzp = (zzp) obj;
            ObjectEncoderContext objectEncoderContext = (ObjectEncoderContext) obj2;
            objectEncoderContext.add("clientType", (Object) zzp.zzc());
            objectEncoderContext.add("androidClientInfo", (Object) zzp.zzb());
        }
    }

    /* renamed from: com.google.android.datatransport.cct.a.zzb$zzd */
    private static final class zzd implements ObjectEncoder<zzq> {
        static final zzd zza = new zzd();

        private zzd() {
        }

        public void encode(Object obj, Object obj2) throws IOException {
            zzq zzq = (zzq) obj;
            ObjectEncoderContext objectEncoderContext = (ObjectEncoderContext) obj2;
            objectEncoderContext.add("eventTimeMs", zzq.zzb());
            objectEncoderContext.add("eventCode", (Object) zzq.zza());
            objectEncoderContext.add("eventUptimeMs", zzq.zzc());
            objectEncoderContext.add("sourceExtension", (Object) zzq.zze());
            objectEncoderContext.add("sourceExtensionJsonProto3", (Object) zzq.zzf());
            objectEncoderContext.add("timezoneOffsetSeconds", zzq.zzg());
            objectEncoderContext.add("networkConnectionInfo", (Object) zzq.zzd());
        }
    }

    /* renamed from: com.google.android.datatransport.cct.a.zzb$zze */
    private static final class zze implements ObjectEncoder<zzr> {
        static final zze zza = new zze();

        private zze() {
        }

        public void encode(Object obj, Object obj2) throws IOException {
            zzr zzr = (zzr) obj;
            ObjectEncoderContext objectEncoderContext = (ObjectEncoderContext) obj2;
            objectEncoderContext.add("requestTimeMs", zzr.zzg());
            objectEncoderContext.add("requestUptimeMs", zzr.zzh());
            objectEncoderContext.add("clientInfo", (Object) zzr.zzb());
            objectEncoderContext.add("logSource", (Object) zzr.zzd());
            objectEncoderContext.add("logSourceName", (Object) zzr.zze());
            objectEncoderContext.add("logEvent", (Object) zzr.zzc());
            objectEncoderContext.add("qosTier", (Object) zzr.zzf());
        }
    }

    /* renamed from: com.google.android.datatransport.cct.a.zzb$zzf */
    private static final class zzf implements ObjectEncoder<zzt> {
        static final zzf zza = new zzf();

        private zzf() {
        }

        public void encode(Object obj, Object obj2) throws IOException {
            zzt zzt = (zzt) obj;
            ObjectEncoderContext objectEncoderContext = (ObjectEncoderContext) obj2;
            objectEncoderContext.add("networkType", (Object) zzt.zzc());
            objectEncoderContext.add("mobileSubtype", (Object) zzt.zzb());
        }
    }

    private zzb() {
    }

    public void configure(EncoderConfig<?> encoderConfig) {
        encoderConfig.registerEncoder((Class<U>) zzo.class, (ObjectEncoder<? super U>) C4072zzb.zza);
        encoderConfig.registerEncoder((Class<U>) zze.class, (ObjectEncoder<? super U>) C4072zzb.zza);
        encoderConfig.registerEncoder((Class<U>) zzr.class, (ObjectEncoder<? super U>) zze.zza);
        encoderConfig.registerEncoder((Class<U>) zzk.class, (ObjectEncoder<? super U>) zze.zza);
        encoderConfig.registerEncoder((Class<U>) zzp.class, (ObjectEncoder<? super U>) zzc.zza);
        encoderConfig.registerEncoder((Class<U>) zzg.class, (ObjectEncoder<? super U>) zzc.zza);
        encoderConfig.registerEncoder((Class<U>) zza.class, (ObjectEncoder<? super U>) zza.zza);
        encoderConfig.registerEncoder((Class<U>) zzd.class, (ObjectEncoder<? super U>) zza.zza);
        encoderConfig.registerEncoder((Class<U>) zzq.class, (ObjectEncoder<? super U>) zzd.zza);
        encoderConfig.registerEncoder((Class<U>) zzi.class, (ObjectEncoder<? super U>) zzd.zza);
        encoderConfig.registerEncoder((Class<U>) zzt.class, (ObjectEncoder<? super U>) zzf.zza);
        encoderConfig.registerEncoder((Class<U>) zzn.class, (ObjectEncoder<? super U>) zzf.zza);
    }
}
