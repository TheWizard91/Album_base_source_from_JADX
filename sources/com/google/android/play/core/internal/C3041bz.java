package com.google.android.play.core.internal;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.google.android.play.core.internal.bz */
final class C3041bz {

    /* renamed from: a */
    private final ConcurrentHashMap<C3040by, List<Throwable>> f1317a = new ConcurrentHashMap<>(16, 0.75f, 10);

    /* renamed from: b */
    private final ReferenceQueue<Throwable> f1318b = new ReferenceQueue<>();

    C3041bz() {
    }

    /* renamed from: a */
    public final List<Throwable> mo44144a(Throwable th) {
        while (true) {
            Reference<? extends Throwable> poll = this.f1318b.poll();
            if (poll == null) {
                break;
            }
            this.f1317a.remove(poll);
        }
        List<Throwable> list = this.f1317a.get(new C3040by(th, (ReferenceQueue<Throwable>) null));
        if (list != null) {
            return list;
        }
        Vector vector = new Vector(2);
        List<Throwable> putIfAbsent = this.f1317a.putIfAbsent(new C3040by(th, this.f1318b), vector);
        return putIfAbsent == null ? vector : putIfAbsent;
    }
}
