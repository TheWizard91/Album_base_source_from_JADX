package com.google.android.datatransport.cct.p011a;

import com.google.firebase.encoders.annotations.Encodable;
import java.util.List;

@Encodable
/* renamed from: com.google.android.datatransport.cct.a.zzo */
public abstract class zzo {
    public static zzo zza(List<zzr> list) {
        return new zze(list);
    }

    @Encodable.Field(name = "logRequest")
    public abstract List<zzr> zza();
}
