package p019io.reactivex.internal.operators.single;

import p019io.reactivex.SingleObserver;
import p019io.reactivex.SingleSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.BiPredicate;

/* renamed from: io.reactivex.internal.operators.single.SingleContains */
public final class SingleContains<T> extends p019io.reactivex.Single<Boolean> {
    final BiPredicate<Object, Object> comparer;
    final SingleSource<T> source;
    final Object value;

    public SingleContains(SingleSource<T> source2, Object value2, BiPredicate<Object, Object> comparer2) {
        this.source = source2;
        this.value = value2;
        this.comparer = comparer2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super Boolean> s) {
        this.source.subscribe(new Single(s));
    }

    /* renamed from: io.reactivex.internal.operators.single.SingleContains$Single */
    final class Single implements SingleObserver<T> {

        /* renamed from: s */
        private final SingleObserver<? super Boolean> f534s;

        Single(SingleObserver<? super Boolean> s) {
            this.f534s = s;
        }

        public void onSubscribe(Disposable d) {
            this.f534s.onSubscribe(d);
        }

        public void onSuccess(T v) {
            try {
                this.f534s.onSuccess(Boolean.valueOf(SingleContains.this.comparer.test(v, SingleContains.this.value)));
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                this.f534s.onError(ex);
            }
        }

        public void onError(Throwable e) {
            this.f534s.onError(e);
        }
    }
}
