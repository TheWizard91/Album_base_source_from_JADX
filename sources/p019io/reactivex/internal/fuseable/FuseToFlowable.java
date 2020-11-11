package p019io.reactivex.internal.fuseable;

import p019io.reactivex.Flowable;

/* renamed from: io.reactivex.internal.fuseable.FuseToFlowable */
public interface FuseToFlowable<T> {
    Flowable<T> fuseToFlowable();
}
