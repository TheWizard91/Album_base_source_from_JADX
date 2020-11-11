package p019io.reactivex.internal.operators.maybe;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import p019io.reactivex.Flowable;
import p019io.reactivex.MaybeObserver;
import p019io.reactivex.MaybeSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.internal.util.BackpressureHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeFlatMapIterableFlowable */
public final class MaybeFlatMapIterableFlowable<T, R> extends Flowable<R> {
    final Function<? super T, ? extends Iterable<? extends R>> mapper;
    final MaybeSource<T> source;

    public MaybeFlatMapIterableFlowable(MaybeSource<T> source2, Function<? super T, ? extends Iterable<? extends R>> mapper2) {
        this.source = source2;
        this.mapper = mapper2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> s) {
        this.source.subscribe(new FlatMapIterableObserver(s, this.mapper));
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeFlatMapIterableFlowable$FlatMapIterableObserver */
    static final class FlatMapIterableObserver<T, R> extends BasicIntQueueSubscription<R> implements MaybeObserver<T> {
        private static final long serialVersionUID = -8938804753851907758L;
        final Subscriber<? super R> actual;
        volatile boolean cancelled;

        /* renamed from: d */
        Disposable f383d;

        /* renamed from: it */
        volatile Iterator<? extends R> f384it;
        final Function<? super T, ? extends Iterable<? extends R>> mapper;
        boolean outputFused;
        final AtomicLong requested = new AtomicLong();

        FlatMapIterableObserver(Subscriber<? super R> actual2, Function<? super T, ? extends Iterable<? extends R>> mapper2) {
            this.actual = actual2;
            this.mapper = mapper2;
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f383d, d)) {
                this.f383d = d;
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
                this.f384it = iterator;
                drain();
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                this.actual.onError(ex);
            }
        }

        public void onError(Throwable e) {
            this.f383d = DisposableHelper.DISPOSED;
            this.actual.onError(e);
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public void request(long n) {
            if (SubscriptionHelper.validate(n)) {
                BackpressureHelper.add(this.requested, n);
                drain();
            }
        }

        public void cancel() {
            this.cancelled = true;
            this.f383d.dispose();
            this.f383d = DisposableHelper.DISPOSED;
        }

        /* access modifiers changed from: package-private */
        public void fastPath(Subscriber<? super R> a, Iterator<? extends R> iterator) {
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

        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                Subscriber<? super R> a = this.actual;
                Iterator<? extends R> iterator = this.f384it;
                if (!this.outputFused || iterator == null) {
                    int missed = 1;
                    while (true) {
                        if (iterator != null) {
                            long r = this.requested.get();
                            if (r == Long.MAX_VALUE) {
                                fastPath(a, iterator);
                                return;
                            }
                            long e = 0;
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
                                iterator = this.f384it;
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

        public int requestFusion(int mode) {
            if ((mode & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        public void clear() {
            this.f384it = null;
        }

        public boolean isEmpty() {
            return this.f384it == null;
        }

        public R poll() throws Exception {
            Iterator<? extends R> iterator = this.f384it;
            if (iterator == null) {
                return null;
            }
            R v = ObjectHelper.requireNonNull(iterator.next(), "The iterator returned a null value");
            if (!iterator.hasNext()) {
                this.f384it = null;
            }
            return v;
        }
    }
}
