package p019io.reactivex.internal.operators.maybe;

import p019io.reactivex.Maybe;
import p019io.reactivex.MaybeSource;
import p019io.reactivex.internal.fuseable.HasUpstreamMaybeSource;

/* renamed from: io.reactivex.internal.operators.maybe.AbstractMaybeWithUpstream */
abstract class AbstractMaybeWithUpstream<T, R> extends Maybe<R> implements HasUpstreamMaybeSource<T> {
    protected final MaybeSource<T> source;

    AbstractMaybeWithUpstream(MaybeSource<T> source2) {
        this.source = source2;
    }

    public final MaybeSource<T> source() {
        return this.source;
    }
}
