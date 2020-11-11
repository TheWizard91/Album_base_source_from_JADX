package com.google.android.gms.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-tasks@@17.0.2 */
final class zzy implements Continuation<Void, List<TResult>> {
    private final /* synthetic */ Collection zza;

    zzy(Collection collection) {
        this.zza = collection;
    }

    public final /* synthetic */ Object then(Task task) throws Exception {
        ArrayList arrayList = new ArrayList();
        for (Task result : this.zza) {
            arrayList.add(result.getResult());
        }
        return arrayList;
    }
}
