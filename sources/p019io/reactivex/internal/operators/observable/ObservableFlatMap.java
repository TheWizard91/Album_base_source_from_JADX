package p019io.reactivex.internal.operators.observable;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.fuseable.QueueDisposable;
import p019io.reactivex.internal.fuseable.SimplePlainQueue;
import p019io.reactivex.internal.fuseable.SimpleQueue;
import p019io.reactivex.internal.queue.SpscArrayQueue;
import p019io.reactivex.internal.queue.SpscLinkedArrayQueue;
import p019io.reactivex.internal.util.AtomicThrowable;
import p019io.reactivex.internal.util.ExceptionHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMap */
public final class ObservableFlatMap<T, U> extends AbstractObservableWithUpstream<T, U> {
    final int bufferSize;
    final boolean delayErrors;
    final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
    final int maxConcurrency;

    public ObservableFlatMap(ObservableSource<T> source, Function<? super T, ? extends ObservableSource<? extends U>> mapper2, boolean delayErrors2, int maxConcurrency2, int bufferSize2) {
        super(source);
        this.mapper = mapper2;
        this.delayErrors = delayErrors2;
        this.maxConcurrency = maxConcurrency2;
        this.bufferSize = bufferSize2;
    }

    public void subscribeActual(Observer<? super U> t) {
        if (!ObservableScalarXMap.tryScalarXMapSubscribe(this.source, t, this.mapper)) {
            this.source.subscribe(new MergeObserver(t, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMap$MergeObserver */
    static final class MergeObserver<T, U> extends AtomicInteger implements Disposable, Observer<T> {
        static final InnerObserver<?, ?>[] CANCELLED = new InnerObserver[0];
        static final InnerObserver<?, ?>[] EMPTY = new InnerObserver[0];
        private static final long serialVersionUID = -2117620485640801370L;
        final Observer<? super U> actual;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final AtomicThrowable errors = new AtomicThrowable();
        long lastId;
        int lastIndex;
        final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
        final int maxConcurrency;
        final AtomicReference<InnerObserver<?, ?>[]> observers;
        volatile SimplePlainQueue<U> queue;

        /* renamed from: s */
        Disposable f443s;
        Queue<ObservableSource<? extends U>> sources;
        long uniqueId;
        int wip;

        MergeObserver(Observer<? super U> actual2, Function<? super T, ? extends ObservableSource<? extends U>> mapper2, boolean delayErrors2, int maxConcurrency2, int bufferSize2) {
            this.actual = actual2;
            this.mapper = mapper2;
            this.delayErrors = delayErrors2;
            this.maxConcurrency = maxConcurrency2;
            this.bufferSize = bufferSize2;
            if (maxConcurrency2 != Integer.MAX_VALUE) {
                this.sources = new ArrayDeque(maxConcurrency2);
            }
            this.observers = new AtomicReference<>(EMPTY);
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f443s, s)) {
                this.f443s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    ObservableSource<? extends U> p = (ObservableSource) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null ObservableSource");
                    if (this.maxConcurrency != Integer.MAX_VALUE) {
                        synchronized (this) {
                            int i = this.wip;
                            if (i == this.maxConcurrency) {
                                this.sources.offer(p);
                                return;
                            }
                            this.wip = i + 1;
                        }
                    }
                    subscribeInner(p);
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    this.f443s.dispose();
                    onError(e);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void subscribeInner(ObservableSource<? extends U> p) {
            while (p instanceof Callable) {
                if (tryEmitScalar((Callable) p) && this.maxConcurrency != Integer.MAX_VALUE) {
                    boolean empty = false;
                    synchronized (this) {
                        p = this.sources.poll();
                        if (p == null) {
                            this.wip--;
                            empty = true;
                        }
                    }
                    if (empty) {
                        drain();
                        return;
                    }
                } else {
                    return;
                }
            }
            long j = this.uniqueId;
            this.uniqueId = 1 + j;
            InnerObserver<T, U> inner = new InnerObserver<>(this, j);
            if (addInner(inner)) {
                p.subscribe(inner);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean addInner(InnerObserver<T, U> inner) {
            InnerObserver<?, ?>[] a;
            InnerObserver<?, ?>[] b;
            do {
                a = (InnerObserver[]) this.observers.get();
                if (a == CANCELLED) {
                    inner.dispose();
                    return false;
                }
                int n = a.length;
                b = new InnerObserver[(n + 1)];
                System.arraycopy(a, 0, b, 0, n);
                b[n] = inner;
            } while (!this.observers.compareAndSet(a, b));
            return true;
        }

        /* access modifiers changed from: package-private */
        public void removeInner(InnerObserver<T, U> inner) {
            InnerObserver<?, ?>[] a;
            InnerObserver<?, ?>[] b;
            do {
                a = (InnerObserver[]) this.observers.get();
                int n = a.length;
                if (n != 0) {
                    int j = -1;
                    int i = 0;
                    while (true) {
                        if (i >= n) {
                            break;
                        } else if (a[i] == inner) {
                            j = i;
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (j >= 0) {
                        if (n == 1) {
                            b = EMPTY;
                        } else {
                            InnerObserver<?, ?>[] b2 = new InnerObserver[(n - 1)];
                            System.arraycopy(a, 0, b2, 0, j);
                            System.arraycopy(a, j + 1, b2, j, (n - j) - 1);
                            b = b2;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.observers.compareAndSet(a, b));
        }

        /* access modifiers changed from: package-private */
        public boolean tryEmitScalar(Callable<? extends U> value) {
            try {
                U u = value.call();
                if (u == null) {
                    return true;
                }
                if (get() != 0 || !compareAndSet(0, 1)) {
                    SimplePlainQueue<U> q = this.queue;
                    if (q == null) {
                        if (this.maxConcurrency == Integer.MAX_VALUE) {
                            q = new SpscLinkedArrayQueue<>(this.bufferSize);
                        } else {
                            q = new SpscArrayQueue<>(this.maxConcurrency);
                        }
                        this.queue = q;
                    }
                    if (!q.offer(u)) {
                        onError(new IllegalStateException("Scalar queue full?!"));
                        return true;
                    } else if (getAndIncrement() != 0) {
                        return false;
                    }
                } else {
                    this.actual.onNext(u);
                    if (decrementAndGet() == 0) {
                        return true;
                    }
                }
                drainLoop();
                return true;
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                this.errors.addThrowable(ex);
                drain();
                return true;
            }
        }

        /* access modifiers changed from: package-private */
        public void tryEmit(U value, InnerObserver<T, U> inner) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                SimpleQueue<U> q = inner.queue;
                if (q == null) {
                    q = new SpscLinkedArrayQueue<>(this.bufferSize);
                    inner.queue = q;
                }
                q.offer(value);
                if (getAndIncrement() != 0) {
                    return;
                }
            } else {
                this.actual.onNext(value);
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            drainLoop();
        }

        public void onError(Throwable t) {
            if (this.done) {
                RxJavaPlugins.onError(t);
            } else if (this.errors.addThrowable(t)) {
                this.done = true;
                drain();
            } else {
                RxJavaPlugins.onError(t);
            }
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                drain();
            }
        }

        public void dispose() {
            Throwable ex;
            if (!this.cancelled) {
                this.cancelled = true;
                if (disposeAll() && (ex = this.errors.terminate()) != null && ex != ExceptionHelper.TERMINATED) {
                    RxJavaPlugins.onError(ex);
                }
            }
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:67:0x00d5, code lost:
            if (r0 != null) goto L_0x00c1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void drainLoop() {
            /*
                r19 = this;
                r1 = r19
                io.reactivex.Observer<? super U> r2 = r1.actual
                r0 = 1
                r3 = r0
            L_0x0006:
                boolean r0 = r19.checkTerminate()
                if (r0 == 0) goto L_0x000d
                return
            L_0x000d:
                io.reactivex.internal.fuseable.SimplePlainQueue<U> r0 = r1.queue
                if (r0 == 0) goto L_0x0027
            L_0x0011:
                boolean r4 = r19.checkTerminate()
                if (r4 == 0) goto L_0x0018
                return
            L_0x0018:
                java.lang.Object r4 = r0.poll()
                if (r4 != 0) goto L_0x0023
                if (r4 != 0) goto L_0x0022
                goto L_0x0027
            L_0x0022:
                goto L_0x0011
            L_0x0023:
                r2.onNext(r4)
                goto L_0x0011
            L_0x0027:
                boolean r4 = r1.done
                io.reactivex.internal.fuseable.SimplePlainQueue<U> r5 = r1.queue
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservableFlatMap$InnerObserver<?, ?>[]> r0 = r1.observers
                java.lang.Object r0 = r0.get()
                r6 = r0
                io.reactivex.internal.operators.observable.ObservableFlatMap$InnerObserver[] r6 = (p019io.reactivex.internal.operators.observable.ObservableFlatMap.InnerObserver[]) r6
                int r7 = r6.length
                r8 = 0
                int r0 = r1.maxConcurrency
                r9 = 2147483647(0x7fffffff, float:NaN)
                if (r0 == r9) goto L_0x004a
                monitor-enter(r19)
                java.util.Queue<io.reactivex.ObservableSource<? extends U>> r0 = r1.sources     // Catch:{ all -> 0x0047 }
                int r0 = r0.size()     // Catch:{ all -> 0x0047 }
                r8 = r0
                monitor-exit(r19)     // Catch:{ all -> 0x0047 }
                goto L_0x004a
            L_0x0047:
                r0 = move-exception
                monitor-exit(r19)     // Catch:{ all -> 0x0047 }
                throw r0
            L_0x004a:
                if (r4 == 0) goto L_0x006c
                if (r5 == 0) goto L_0x0054
                boolean r0 = r5.isEmpty()
                if (r0 == 0) goto L_0x006c
            L_0x0054:
                if (r7 != 0) goto L_0x006c
                if (r8 != 0) goto L_0x006c
                io.reactivex.internal.util.AtomicThrowable r0 = r1.errors
                java.lang.Throwable r0 = r0.terminate()
                java.lang.Throwable r9 = p019io.reactivex.internal.util.ExceptionHelper.TERMINATED
                if (r0 == r9) goto L_0x006b
                if (r0 != 0) goto L_0x0068
                r2.onComplete()
                goto L_0x006b
            L_0x0068:
                r2.onError(r0)
            L_0x006b:
                return
            L_0x006c:
                r0 = 0
                if (r7 == 0) goto L_0x012f
                long r10 = r1.lastId
                int r12 = r1.lastIndex
                if (r7 <= r12) goto L_0x0083
                r13 = r6[r12]
                long r13 = r13.f442id
                int r13 = (r13 > r10 ? 1 : (r13 == r10 ? 0 : -1))
                if (r13 == 0) goto L_0x007e
                goto L_0x0083
            L_0x007e:
                r16 = r4
                r17 = r5
                goto L_0x00b1
            L_0x0083:
                if (r7 > r12) goto L_0x0086
                r12 = 0
            L_0x0086:
                r13 = r12
                r14 = 0
            L_0x0088:
                if (r14 >= r7) goto L_0x00a4
                r15 = r6[r13]
                r16 = r4
                r17 = r5
                long r4 = r15.f442id
                int r4 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
                if (r4 != 0) goto L_0x0097
                goto L_0x00a8
            L_0x0097:
                int r13 = r13 + 1
                if (r13 != r7) goto L_0x009d
                r4 = 0
                r13 = r4
            L_0x009d:
                int r14 = r14 + 1
                r4 = r16
                r5 = r17
                goto L_0x0088
            L_0x00a4:
                r16 = r4
                r17 = r5
            L_0x00a8:
                r12 = r13
                r1.lastIndex = r13
                r4 = r6[r13]
                long r4 = r4.f442id
                r1.lastId = r4
            L_0x00b1:
                r4 = r12
                r5 = 0
                r13 = r5
                r5 = r4
                r4 = r0
            L_0x00b6:
                if (r13 >= r7) goto L_0x0126
                boolean r0 = r19.checkTerminate()
                if (r0 == 0) goto L_0x00bf
                return
            L_0x00bf:
                r14 = r6[r5]
            L_0x00c1:
                boolean r0 = r19.checkTerminate()
                if (r0 == 0) goto L_0x00c8
                return
            L_0x00c8:
                io.reactivex.internal.fuseable.SimpleQueue<U> r15 = r14.queue
                if (r15 != 0) goto L_0x00cd
                goto L_0x00d8
            L_0x00cd:
                java.lang.Object r0 = r15.poll()     // Catch:{ all -> 0x0102 }
                if (r0 != 0) goto L_0x00f8
                if (r0 != 0) goto L_0x00f7
            L_0x00d8:
                boolean r0 = r14.done
                io.reactivex.internal.fuseable.SimpleQueue<U> r15 = r14.queue
                if (r0 == 0) goto L_0x00f1
                if (r15 == 0) goto L_0x00e6
                boolean r18 = r15.isEmpty()
                if (r18 == 0) goto L_0x00f1
            L_0x00e6:
                r1.removeInner(r14)
                boolean r18 = r19.checkTerminate()
                if (r18 == 0) goto L_0x00f0
                return
            L_0x00f0:
                r4 = 1
            L_0x00f1:
                int r5 = r5 + 1
                if (r5 != r7) goto L_0x0120
                r5 = 0
                goto L_0x0120
            L_0x00f7:
                goto L_0x00c1
            L_0x00f8:
                r2.onNext(r0)
                boolean r18 = r19.checkTerminate()
                if (r18 == 0) goto L_0x00cd
                return
            L_0x0102:
                r0 = move-exception
                r18 = r0
                r0 = r18
                p019io.reactivex.exceptions.Exceptions.throwIfFatal(r0)
                r14.dispose()
                io.reactivex.internal.util.AtomicThrowable r9 = r1.errors
                r9.addThrowable(r0)
                boolean r9 = r19.checkTerminate()
                if (r9 == 0) goto L_0x0119
                return
            L_0x0119:
                r1.removeInner(r14)
                r4 = 1
                int r13 = r13 + 1
            L_0x0120:
                int r13 = r13 + 1
                r9 = 2147483647(0x7fffffff, float:NaN)
                goto L_0x00b6
            L_0x0126:
                r1.lastIndex = r5
                r0 = r6[r5]
                long r13 = r0.f442id
                r1.lastId = r13
                goto L_0x0134
            L_0x012f:
                r16 = r4
                r17 = r5
                r4 = r0
            L_0x0134:
                if (r4 == 0) goto L_0x015a
                int r0 = r1.maxConcurrency
                r5 = 2147483647(0x7fffffff, float:NaN)
                if (r0 == r5) goto L_0x0006
                monitor-enter(r19)
                java.util.Queue<io.reactivex.ObservableSource<? extends U>> r0 = r1.sources     // Catch:{ all -> 0x0157 }
                java.lang.Object r0 = r0.poll()     // Catch:{ all -> 0x0157 }
                io.reactivex.ObservableSource r0 = (p019io.reactivex.ObservableSource) r0     // Catch:{ all -> 0x0157 }
                if (r0 != 0) goto L_0x0151
                int r5 = r1.wip     // Catch:{ all -> 0x0157 }
                int r5 = r5 + -1
                r1.wip = r5     // Catch:{ all -> 0x0157 }
                monitor-exit(r19)     // Catch:{ all -> 0x0157 }
                goto L_0x0006
            L_0x0151:
                monitor-exit(r19)     // Catch:{ all -> 0x0157 }
                r1.subscribeInner(r0)
                goto L_0x0006
            L_0x0157:
                r0 = move-exception
                monitor-exit(r19)     // Catch:{ all -> 0x0157 }
                throw r0
            L_0x015a:
                int r0 = -r3
                int r3 = r1.addAndGet(r0)
                if (r3 != 0) goto L_0x0163
                return
            L_0x0163:
                goto L_0x0006
            */
            throw new UnsupportedOperationException("Method not decompiled: p019io.reactivex.internal.operators.observable.ObservableFlatMap.MergeObserver.drainLoop():void");
        }

        /* access modifiers changed from: package-private */
        public boolean checkTerminate() {
            if (this.cancelled) {
                return true;
            }
            Throwable e = (Throwable) this.errors.get();
            if (this.delayErrors || e == null) {
                return false;
            }
            disposeAll();
            Throwable e2 = this.errors.terminate();
            if (e2 != ExceptionHelper.TERMINATED) {
                this.actual.onError(e2);
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public boolean disposeAll() {
            InnerObserver<?, ?>[] a;
            this.f443s.dispose();
            InnerObserver<?, ?>[] a2 = (InnerObserver[]) this.observers.get();
            InnerObserver<?, ?>[] innerObserverArr = CANCELLED;
            if (a2 == innerObserverArr || (a = (InnerObserver[]) this.observers.getAndSet(innerObserverArr)) == innerObserverArr) {
                return false;
            }
            for (InnerObserver<?, ?> inner : a) {
                inner.dispose();
            }
            return true;
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMap$InnerObserver */
    static final class InnerObserver<T, U> extends AtomicReference<Disposable> implements Observer<U> {
        private static final long serialVersionUID = -4606175640614850599L;
        volatile boolean done;
        int fusionMode;

        /* renamed from: id */
        final long f442id;
        final MergeObserver<T, U> parent;
        volatile SimpleQueue<U> queue;

        InnerObserver(MergeObserver<T, U> parent2, long id) {
            this.f442id = id;
            this.parent = parent2;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.setOnce(this, s) && (s instanceof QueueDisposable)) {
                QueueDisposable<U> qd = (QueueDisposable) s;
                int m = qd.requestFusion(7);
                if (m == 1) {
                    this.fusionMode = m;
                    this.queue = qd;
                    this.done = true;
                    this.parent.drain();
                } else if (m == 2) {
                    this.fusionMode = m;
                    this.queue = qd;
                }
            }
        }

        public void onNext(U t) {
            if (this.fusionMode == 0) {
                this.parent.tryEmit(t, this);
            } else {
                this.parent.drain();
            }
        }

        public void onError(Throwable t) {
            if (this.parent.errors.addThrowable(t)) {
                if (!this.parent.delayErrors) {
                    this.parent.disposeAll();
                }
                this.done = true;
                this.parent.drain();
                return;
            }
            RxJavaPlugins.onError(t);
        }

        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }
    }
}
