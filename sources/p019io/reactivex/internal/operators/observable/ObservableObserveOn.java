package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.Scheduler;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.fuseable.QueueDisposable;
import p019io.reactivex.internal.fuseable.SimpleQueue;
import p019io.reactivex.internal.observers.BasicIntQueueDisposable;
import p019io.reactivex.internal.queue.SpscLinkedArrayQueue;
import p019io.reactivex.internal.schedulers.TrampolineScheduler;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableObserveOn */
public final class ObservableObserveOn<T> extends AbstractObservableWithUpstream<T, T> {
    final int bufferSize;
    final boolean delayError;
    final Scheduler scheduler;

    public ObservableObserveOn(ObservableSource<T> source, Scheduler scheduler2, boolean delayError2, int bufferSize2) {
        super(source);
        this.scheduler = scheduler2;
        this.delayError = delayError2;
        this.bufferSize = bufferSize2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        Scheduler scheduler2 = this.scheduler;
        if (scheduler2 instanceof TrampolineScheduler) {
            this.source.subscribe(observer);
            return;
        }
        this.source.subscribe(new ObserveOnObserver(observer, scheduler2.createWorker(), this.delayError, this.bufferSize));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableObserveOn$ObserveOnObserver */
    static final class ObserveOnObserver<T> extends BasicIntQueueDisposable<T> implements Observer<T>, Runnable {
        private static final long serialVersionUID = 6576896619930983584L;
        final Observer<? super T> actual;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayError;
        volatile boolean done;
        Throwable error;
        boolean outputFused;
        SimpleQueue<T> queue;

        /* renamed from: s */
        Disposable f460s;
        int sourceMode;
        final Scheduler.Worker worker;

        ObserveOnObserver(Observer<? super T> actual2, Scheduler.Worker worker2, boolean delayError2, int bufferSize2) {
            this.actual = actual2;
            this.worker = worker2;
            this.delayError = delayError2;
            this.bufferSize = bufferSize2;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f460s, s)) {
                this.f460s = s;
                if (s instanceof QueueDisposable) {
                    QueueDisposable<T> qd = (QueueDisposable) s;
                    int m = qd.requestFusion(7);
                    if (m == 1) {
                        this.sourceMode = m;
                        this.queue = qd;
                        this.done = true;
                        this.actual.onSubscribe(this);
                        schedule();
                        return;
                    } else if (m == 2) {
                        this.sourceMode = m;
                        this.queue = qd;
                        this.actual.onSubscribe(this);
                        return;
                    }
                }
                this.queue = new SpscLinkedArrayQueue(this.bufferSize);
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode != 2) {
                    this.queue.offer(t);
                }
                schedule();
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                RxJavaPlugins.onError(t);
                return;
            }
            this.error = t;
            this.done = true;
            schedule();
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                schedule();
            }
        }

        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.f460s.dispose();
                this.worker.dispose();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* access modifiers changed from: package-private */
        public void schedule() {
            if (getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }

        /* access modifiers changed from: package-private */
        public void drainNormal() {
            int missed = 1;
            SimpleQueue<T> q = this.queue;
            Observer<? super T> a = this.actual;
            while (!checkTerminated(this.done, q.isEmpty(), a)) {
                while (true) {
                    boolean d = this.done;
                    try {
                        T v = q.poll();
                        boolean empty = v == null;
                        if (!checkTerminated(d, empty, a)) {
                            if (empty) {
                                missed = addAndGet(-missed);
                                if (missed == 0) {
                                    return;
                                }
                            } else {
                                a.onNext(v);
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable ex) {
                        Exceptions.throwIfFatal(ex);
                        this.f460s.dispose();
                        q.clear();
                        a.onError(ex);
                        this.worker.dispose();
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void drainFused() {
            int missed = 1;
            while (!this.cancelled) {
                boolean d = this.done;
                Throwable ex = this.error;
                if (this.delayError || !d || ex == null) {
                    this.actual.onNext(null);
                    if (d) {
                        Throwable ex2 = this.error;
                        if (ex2 != null) {
                            this.actual.onError(ex2);
                        } else {
                            this.actual.onComplete();
                        }
                        this.worker.dispose();
                        return;
                    }
                    missed = addAndGet(-missed);
                    if (missed == 0) {
                        return;
                    }
                } else {
                    this.actual.onError(this.error);
                    this.worker.dispose();
                    return;
                }
            }
        }

        public void run() {
            if (this.outputFused) {
                drainFused();
            } else {
                drainNormal();
            }
        }

        /* access modifiers changed from: package-private */
        public boolean checkTerminated(boolean d, boolean empty, Observer<? super T> a) {
            if (this.cancelled) {
                this.queue.clear();
                return true;
            } else if (!d) {
                return false;
            } else {
                Throwable e = this.error;
                if (this.delayError) {
                    if (!empty) {
                        return false;
                    }
                    if (e != null) {
                        a.onError(e);
                    } else {
                        a.onComplete();
                    }
                    this.worker.dispose();
                    return true;
                } else if (e != null) {
                    this.queue.clear();
                    a.onError(e);
                    this.worker.dispose();
                    return true;
                } else if (!empty) {
                    return false;
                } else {
                    a.onComplete();
                    this.worker.dispose();
                    return true;
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

        public T poll() throws Exception {
            return this.queue.poll();
        }

        public void clear() {
            this.queue.clear();
        }

        public boolean isEmpty() {
            return this.queue.isEmpty();
        }
    }
}
