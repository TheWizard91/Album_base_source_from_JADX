package p019io.reactivex.internal.fuseable;

import p019io.reactivex.SingleSource;

/* renamed from: io.reactivex.internal.fuseable.HasUpstreamSingleSource */
public interface HasUpstreamSingleSource<T> {
    SingleSource<T> source();
}
