package p019io.reactivex.internal.fuseable;

import p019io.reactivex.ObservableSource;

/* renamed from: io.reactivex.internal.fuseable.HasUpstreamObservableSource */
public interface HasUpstreamObservableSource<T> {
    ObservableSource<T> source();
}
