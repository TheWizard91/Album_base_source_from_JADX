package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzgi;
import com.google.android.gms.internal.measurement.zzgj;
import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
public abstract class zzgi<MessageType extends zzgj<MessageType, BuilderType>, BuilderType extends zzgi<MessageType, BuilderType>> implements zzjm {
    /* access modifiers changed from: protected */
    public abstract BuilderType zza(MessageType messagetype);

    public abstract BuilderType zza(zzhf zzhf, zzho zzho) throws IOException;

    /* renamed from: zzp */
    public abstract BuilderType clone();

    public BuilderType zza(byte[] bArr, int i, int i2) throws zzij {
        try {
            zzhf zza = zzhf.zza(bArr, 0, i2, false);
            zza(zza, zzho.zza());
            zza.zza(0);
            return this;
        } catch (zzij e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException(zza("byte array"), e2);
        }
    }

    public BuilderType zza(byte[] bArr, int i, int i2, zzho zzho) throws zzij {
        try {
            zzhf zza = zzhf.zza(bArr, 0, i2, false);
            zza(zza, zzho);
            zza.zza(0);
            return this;
        } catch (zzij e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException(zza("byte array"), e2);
        }
    }

    private final String zza(String str) {
        String name = getClass().getName();
        return new StringBuilder(String.valueOf(name).length() + 60 + String.valueOf(str).length()).append("Reading ").append(name).append(" from a ").append(str).append(" threw an IOException (should never happen).").toString();
    }

    public final /* synthetic */ zzjm zza(zzjj zzjj) {
        if (mo38046h_().getClass().isInstance(zzjj)) {
            return zza((zzgj) zzjj);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }

    public final /* synthetic */ zzjm zza(byte[] bArr, zzho zzho) throws zzij {
        return zza(bArr, 0, bArr.length, zzho);
    }

    public final /* synthetic */ zzjm zza(byte[] bArr) throws zzij {
        return zza(bArr, 0, bArr.length);
    }
}
