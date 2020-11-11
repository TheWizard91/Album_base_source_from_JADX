package p019io.reactivex.internal.operators.single;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import p019io.reactivex.Flowable;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.SingleSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.internal.util.BackpressureHelper;

/* renamed from: io.reactivex.internal.operators.single.SingleFlatMapIterableFlowable */
public final class SingleFlatMapIterableFlowable<T, R> extends Flowable<R> {
    final Function<? super T, ? extends Iterable<? extends R>> mapper;
    final SingleSource<T> source;

    public SingleFlatMapIterableFlowable(SingleSource<T> source2, Function<? super T, ? extends Iterable<? extends R>> mapper2) {
        this.source = source2;
        this.mapper = mapper2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> s) {
        this.source.subscribe(new FlatMapIterableObserver(s, this.mapper));
    }

    /* renamed from: io.reactivex.internal.operators.single.SingleFlatMapIterableFlowable$FlatMapIterableObserver */
    static final class FlatMapIterableObserver<T, R> extends BasicIntQueueSubscription<R> implements SingleObserver<T> {
        private static final long serialVersionUID = -8938804753851907758L;
        final Subscriber<? super R> actual;
        volatile boolean cancelled;

        /* renamed from: d */
        Disposable f548d;

        /* renamed from: it */
        volatile Iterator<? extends R> f549it;
        final Function<? super T, ? extends Iterable<? extends R>> mapper;
        boolean outputFused;
        final AtomicLong requested = new AtomicLong();

        FlatMapIterableObserver(Subscriber<? super R> actual2, Function<? super T, ? extends Iterable<? extends R>> mapper2) {
            this.actual = actual2;
            this.mapper = mapper2;
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f548d, d)) {
                this.f548d = d;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T value) {
            try {
                Iterator<? extends R> iterator = ((Iterable) this.mapper.apply(value)).iterator();
                if (!iterator.hasNext()) {
                    this.actual.onComplete();
                    return;
                }
                this.f549it = iterator;
                drain();
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                this.actual.onError(ex);
            }
        }

        public void onError(Throwable e) {
            this.f548d = DisposableHelper.DISPOSED;
            this.actual.onError(e);
        }

        public void request(long n) {
            if (SubscriptionHelper.validate(n)) {
                BackpressureHelper.add(this.requested, n);
                drain();
            }
        }

        public void cancel() {
            this.cancelled = true;
            this.f548d.dispose();
            this.f548d = DisposableHelper.DISPOSED;
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                Subscriber<? super R> a = this.actual;
                Iterator<? extends R> iterator = this.f549it;
                if (!this.outputFused || iterator == null) {
                    int missed = 1;
                    while (true) {
                        if (iterator != null) {
                            long r = this.requested.get();
                            long e = 0;
                            if (r == Long.MAX_VALUE) {
                                slowPath(a, iterator);
                                return;
                            }
                            while (e != r) {
                                if (!this.cancelled) {
                                    try {
                                        a.onNext(ObjectHelper.requireNonNull(iterator.next(), "The iterator returned a null value"));
                                        if (!this.cancelled) {
                                            e++;
                                            try {
                                                if (!iterator.hasNext()) {
                                                    a.onComplete();
                                                    return;
                                                }
                                            } catch (Throwable ex) {
                                                Exceptions.throwIfFatal(ex);
                                                a.onError(ex);
                                                return;
                                            }
                                        } else {
                                            return;
                                        }
                                    } catch (Throwable ex2) {
                                        Exceptions.throwIfFatal(ex2);
                                        a.onError(ex2);
                                        return;
                                    }
                                } else {
                                    return;
                                }
                            }
                            if (e != 0) {
                                BackpressureHelper.produced(this.requested, e);
                            }
                        }
                        missed = addAndGet(-missed);
                        if (missed != 0) {
                            if (iterator == null) {
                                iterator = this.f549it;
                            }
                        } else {
                            return;
                        }
                    }
                } else {
                    a.onNext(null);
                    a.onComplete();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void slowPath(Subscriber<? super R> a, Iterator<? extends R> iterator) {
            while (!this.cancelled) {
                try {
                    a.onNext(iterator.next());
                    if (!this.cancelled) {
                        try {
                            if (!iterator.hasNext()) {
                                a.onComplete();
                                return;
                            }
                        } catch (Throwable ex) {
                            Exceptions.throwIfFatal(ex);
                            a.onError(ex);
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (Throwable ex2) {
                    Exceptions.throwIfFatal(ex2);
                    a.onError(ex2);
                    return;
                }
            }
        }

        public int requestFusion(int mode) {
            if ((mode & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        public void clear() {
            this.f549it = null;
        }

        public boolean isEmpty() {
            return this.f549it == null;
        }

        public R poll() throws Exception {
            Iterator<? extends R> iterator = this.f549it;
            if (iterator == null) {
                return null;
            }
            R v = ObjectHelper.requireNonNull(iterator.next(), "The iterator returned a null value");
            if (!iterator.hasNext()) {
                this.f549it = null;
            }
            return v;
        }
    }
}
