package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.observers.BasicFuseableObserver;

/* renamed from: io.reactivex.internal.operators.observable.ObservableMap */
public final class ObservableMap<T, U> extends AbstractObservableWithUpstream<T, U> {
    final Function<? super T, ? extends U> function;

    public ObservableMap(ObservableSource<T> source, Function<? super T, ? extends U> function2) {
        super(source);
        this.function = function2;
    }

    public void subscribeActual(Observer<? super U> t) {
        this.source.subscribe(new MapObserver(t, this.function));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableMap$MapObserver */
    static final class MapObserver<T, U> extends BasicFuseableObserver<T, U> {
        final Function<? super T, ? extends U> mapper;

        MapObserver(Observer<? super U> actual, Function<? super T, ? extends U> mapper2) {
            super(actual);
            this.mapper = mapper2;
        }

        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode != 0) {
                    this.actual.onNext(null);
                    return;
                }
                try {
                    this.actual.onNext(ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper function returned a null value."));
                } catch (Throwable ex) {
                    fail(ex);
                }
            }
        }

        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }

        public U poll() throws Exception {
            T t = this.f211qs.poll();
            if (t != null) {
                return ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper function returned a null value.");
            }
            return null;
        }
    }
}
