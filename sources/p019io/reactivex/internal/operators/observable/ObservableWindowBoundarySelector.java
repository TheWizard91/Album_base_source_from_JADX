package p019io.reactivex.internal.operators.observable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.Observable;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.CompositeDisposable;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.observers.QueueDrainObserver;
import p019io.reactivex.internal.queue.MpscLinkedQueue;
import p019io.reactivex.internal.util.NotificationLite;
import p019io.reactivex.observers.DisposableObserver;
import p019io.reactivex.observers.SerializedObserver;
import p019io.reactivex.plugins.RxJavaPlugins;
import p019io.reactivex.subjects.UnicastSubject;

/* renamed from: io.reactivex.internal.operators.observable.ObservableWindowBoundarySelector */
public final class ObservableWindowBoundarySelector<T, B, V> extends AbstractObservableWithUpstream<T, Observable<T>> {
    final int bufferSize;
    final Function<? super B, ? extends ObservableSource<V>> close;
    final ObservableSource<B> open;

    public ObservableWindowBoundarySelector(ObservableSource<T> source, ObservableSource<B> open2, Function<? super B, ? extends ObservableSource<V>> close2, int bufferSize2) {
        super(source);
        this.open = open2;
        this.close = close2;
        this.bufferSize = bufferSize2;
    }

    public void subscribeActual(Observer<? super Observable<T>> t) {
        this.source.subscribe(new WindowBoundaryMainObserver(new SerializedObserver(t), this.open, this.close, this.bufferSize));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableWindowBoundarySelector$WindowBoundaryMainObserver */
    static final class WindowBoundaryMainObserver<T, B, V> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable {
        final AtomicReference<Disposable> boundary = new AtomicReference<>();
        final int bufferSize;
        final Function<? super B, ? extends ObservableSource<V>> close;
        final ObservableSource<B> open;
        final CompositeDisposable resources;

        /* renamed from: s */
        Disposable f508s;
        final AtomicLong windows;

        /* renamed from: ws */
        final List<UnicastSubject<T>> f509ws;

        WindowBoundaryMainObserver(Observer<? super Observable<T>> actual, ObservableSource<B> open2, Function<? super B, ? extends ObservableSource<V>> close2, int bufferSize2) {
            super(actual, new MpscLinkedQueue());
            AtomicLong atomicLong = new AtomicLong();
            this.windows = atomicLong;
            this.open = open2;
            this.close = close2;
            this.bufferSize = bufferSize2;
            this.resources = new CompositeDisposable();
            this.f509ws = new ArrayList();
            atomicLong.lazySet(1);
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f508s, s)) {
                this.f508s = s;
                this.actual.onSubscribe(this);
                if (!this.cancelled) {
                    OperatorWindowBoundaryOpenObserver<T, B> os = new OperatorWindowBoundaryOpenObserver<>(this);
                    if (this.boundary.compareAndSet((Object) null, os)) {
                        this.windows.getAndIncrement();
                        this.open.subscribe(os);
                    }
                }
            }
        }

        public void onNext(T t) {
            if (fastEnter()) {
                for (UnicastSubject<T> w : this.f509ws) {
                    w.onNext(t);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(NotificationLite.next(t));
                if (!enter()) {
                    return;
                }
            }
            drainLoop();
        }

        public void onError(Throwable t) {
            if (this.done) {
                RxJavaPlugins.onError(t);
                return;
            }
            this.error = t;
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            if (this.windows.decrementAndGet() == 0) {
                this.resources.dispose();
            }
            this.actual.onError(t);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                if (enter()) {
                    drainLoop();
                }
                if (this.windows.decrementAndGet() == 0) {
                    this.resources.dispose();
                }
                this.actual.onComplete();
            }
        }

        /* access modifiers changed from: package-private */
        public void error(Throwable t) {
            this.f508s.dispose();
            this.resources.dispose();
            onError(t);
        }

        public void dispose() {
            this.cancelled = true;
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* access modifiers changed from: package-private */
        public void disposeBoundary() {
            this.resources.dispose();
            DisposableHelper.dispose(this.boundary);
        }

        /* access modifiers changed from: package-private */
        public void drainLoop() {
            MpscLinkedQueue<Object> q = (MpscLinkedQueue) this.queue;
            Observer<? super Observable<T>> a = this.actual;
            List<UnicastSubject<T>> ws = this.f509ws;
            int missed = 1;
            while (true) {
                boolean d = this.done;
                Object o = q.poll();
                boolean empty = o == null;
                if (d && empty) {
                    disposeBoundary();
                    Throwable e = this.error;
                    if (e != null) {
                        for (UnicastSubject<T> w : ws) {
                            w.onError(e);
                        }
                    } else {
                        for (UnicastSubject<T> w2 : ws) {
                            w2.onComplete();
                        }
                    }
                    ws.clear();
                    return;
                } else if (empty) {
                    missed = leave(-missed);
                    if (missed == 0) {
                        return;
                    }
                } else if (o instanceof WindowOperation) {
                    WindowOperation<T, B> wo = (WindowOperation) o;
                    if (wo.f510w != null) {
                        if (ws.remove(wo.f510w)) {
                            wo.f510w.onComplete();
                            if (this.windows.decrementAndGet() == 0) {
                                disposeBoundary();
                                return;
                            }
                        } else {
                            continue;
                        }
                    } else if (!this.cancelled) {
                        UnicastSubject<T> w3 = UnicastSubject.create(this.bufferSize);
                        ws.add(w3);
                        a.onNext(w3);
                        try {
                            ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.close.apply(wo.open), "The ObservableSource supplied is null");
                            OperatorWindowBoundaryCloseObserver<T, V> cl = new OperatorWindowBoundaryCloseObserver<>(this, w3);
                            if (this.resources.add(cl)) {
                                this.windows.getAndIncrement();
                                observableSource.subscribe(cl);
                            }
                        } catch (Throwable e2) {
                            Exceptions.throwIfFatal(e2);
                            this.cancelled = true;
                            a.onError(e2);
                        }
                    }
                } else {
                    for (UnicastSubject<T> w4 : ws) {
                        w4.onNext(NotificationLite.getValue(o));
                    }
                }
            }
        }

        public void accept(Observer<? super Observable<T>> observer, Object v) {
        }

        /* access modifiers changed from: package-private */
        public void open(B b) {
            this.queue.offer(new WindowOperation((UnicastSubject) null, b));
            if (enter()) {
                drainLoop();
            }
        }

        /* access modifiers changed from: package-private */
        public void close(OperatorWindowBoundaryCloseObserver<T, V> w) {
            this.resources.delete(w);
            this.queue.offer(new WindowOperation(w.f507w, null));
            if (enter()) {
                drainLoop();
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableWindowBoundarySelector$WindowOperation */
    static final class WindowOperation<T, B> {
        final B open;

        /* renamed from: w */
        final UnicastSubject<T> f510w;

        WindowOperation(UnicastSubject<T> w, B open2) {
            this.f510w = w;
            this.open = open2;
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableWindowBoundarySelector$OperatorWindowBoundaryOpenObserver */
    static final class OperatorWindowBoundaryOpenObserver<T, B> extends DisposableObserver<B> {
        final WindowBoundaryMainObserver<T, B, ?> parent;

        OperatorWindowBoundaryOpenObserver(WindowBoundaryMainObserver<T, B, ?> parent2) {
            this.parent = parent2;
        }

        public void onNext(B t) {
            this.parent.open(t);
        }

        public void onError(Throwable t) {
            this.parent.error(t);
        }

        public void onComplete() {
            this.parent.onComplete();
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableWindowBoundarySelector$OperatorWindowBoundaryCloseObserver */
    static final class OperatorWindowBoundaryCloseObserver<T, V> extends DisposableObserver<V> {
        boolean done;
        final WindowBoundaryMainObserver<T, ?, V> parent;

        /* renamed from: w */
        final UnicastSubject<T> f507w;

        OperatorWindowBoundaryCloseObserver(WindowBoundaryMainObserver<T, ?, V> parent2, UnicastSubject<T> w) {
            this.parent = parent2;
            this.f507w = w;
        }

        public void onNext(V v) {
            dispose();
            onComplete();
        }

        public void onError(Throwable t) {
            if (this.done) {
                RxJavaPlugins.onError(t);
                return;
            }
            this.done = true;
            this.parent.error(t);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.parent.close(this);
            }
        }
    }
}
