package p019io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicInteger;
import p019io.reactivex.Observable;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.BiPredicate;
import p019io.reactivex.internal.disposables.ArrayCompositeDisposable;
import p019io.reactivex.internal.queue.SpscLinkedArrayQueue;

/* renamed from: io.reactivex.internal.operators.observable.ObservableSequenceEqual */
public final class ObservableSequenceEqual<T> extends Observable<Boolean> {
    final int bufferSize;
    final BiPredicate<? super T, ? super T> comparer;
    final ObservableSource<? extends T> first;
    final ObservableSource<? extends T> second;

    public ObservableSequenceEqual(ObservableSource<? extends T> first2, ObservableSource<? extends T> second2, BiPredicate<? super T, ? super T> comparer2, int bufferSize2) {
        this.first = first2;
        this.second = second2;
        this.comparer = comparer2;
        this.bufferSize = bufferSize2;
    }

    public void subscribeActual(Observer<? super Boolean> s) {
        EqualCoordinator<T> ec = new EqualCoordinator<>(s, this.bufferSize, this.first, this.second, this.comparer);
        s.onSubscribe(ec);
        ec.subscribe();
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableSequenceEqual$EqualCoordinator */
    static final class EqualCoordinator<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = -6178010334400373240L;
        final Observer<? super Boolean> actual;
        volatile boolean cancelled;
        final BiPredicate<? super T, ? super T> comparer;
        final ObservableSource<? extends T> first;
        final EqualObserver<T>[] observers;
        final ArrayCompositeDisposable resources = new ArrayCompositeDisposable(2);
        final ObservableSource<? extends T> second;

        /* renamed from: v1 */
        T f478v1;

        /* renamed from: v2 */
        T f479v2;

        EqualCoordinator(Observer<? super Boolean> actual2, int bufferSize, ObservableSource<? extends T> first2, ObservableSource<? extends T> second2, BiPredicate<? super T, ? super T> comparer2) {
            this.actual = actual2;
            this.first = first2;
            this.second = second2;
            this.comparer = comparer2;
            EqualObserver<T>[] as = new EqualObserver[2];
            this.observers = as;
            as[0] = new EqualObserver<>(this, 0, bufferSize);
            as[1] = new EqualObserver<>(this, 1, bufferSize);
        }

        /* access modifiers changed from: package-private */
        public boolean setDisposable(Disposable s, int index) {
            return this.resources.setResource(index, s);
        }

        /* access modifiers changed from: package-private */
        public void subscribe() {
            EqualObserver<T>[] as = this.observers;
            this.first.subscribe(as[0]);
            this.second.subscribe(as[1]);
        }

        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.resources.dispose();
                if (getAndIncrement() == 0) {
                    EqualObserver<T>[] as = this.observers;
                    as[0].queue.clear();
                    as[1].queue.clear();
                }
            }
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* access modifiers changed from: package-private */
        public void cancel(SpscLinkedArrayQueue<T> q1, SpscLinkedArrayQueue<T> q2) {
            this.cancelled = true;
            q1.clear();
            q2.clear();
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            Throwable e;
            Throwable e2;
            if (getAndIncrement() == 0) {
                int missed = 1;
                EqualObserver<T>[] as = this.observers;
                EqualObserver<T> s1 = as[0];
                SpscLinkedArrayQueue<T> q1 = s1.queue;
                EqualObserver<T> s2 = as[1];
                SpscLinkedArrayQueue<T> q2 = s2.queue;
                while (!this.cancelled) {
                    boolean d1 = s1.done;
                    if (!d1 || (e2 = s1.error) == null) {
                        boolean d2 = s2.done;
                        if (!d2 || (e = s2.error) == null) {
                            if (this.f478v1 == null) {
                                this.f478v1 = q1.poll();
                            }
                            boolean e1 = this.f478v1 == null;
                            if (this.f479v2 == null) {
                                this.f479v2 = q2.poll();
                            }
                            T t = this.f479v2;
                            boolean e22 = t == null;
                            if (d1 && d2 && e1 && e22) {
                                this.actual.onNext(true);
                                this.actual.onComplete();
                                return;
                            } else if (!d1 || !d2 || e1 == e22) {
                                if (!e1 && !e22) {
                                    try {
                                        if (!this.comparer.test(this.f478v1, t)) {
                                            cancel(q1, q2);
                                            this.actual.onNext(false);
                                            this.actual.onComplete();
                                            return;
                                        }
                                        this.f478v1 = null;
                                        this.f479v2 = null;
                                    } catch (Throwable ex) {
                                        Exceptions.throwIfFatal(ex);
                                        cancel(q1, q2);
                                        this.actual.onError(ex);
                                        return;
                                    }
                                }
                                if ((e1 || e22) && (missed = addAndGet(-missed)) == 0) {
                                    return;
                                }
                            } else {
                                cancel(q1, q2);
                                this.actual.onNext(false);
                                this.actual.onComplete();
                                return;
                            }
                        } else {
                            cancel(q1, q2);
                            this.actual.onError(e);
                            return;
                        }
                    } else {
                        cancel(q1, q2);
                        this.actual.onError(e2);
                        return;
                    }
                }
                q1.clear();
                q2.clear();
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableSequenceEqual$EqualObserver */
    static final class EqualObserver<T> implements Observer<T> {
        volatile boolean done;
        Throwable error;
        final int index;
        final EqualCoordinator<T> parent;
        final SpscLinkedArrayQueue<T> queue;

        EqualObserver(EqualCoordinator<T> parent2, int index2, int bufferSize) {
            this.parent = parent2;
            this.index = index2;
            this.queue = new SpscLinkedArrayQueue<>(bufferSize);
        }

        public void onSubscribe(Disposable s) {
            this.parent.setDisposable(s, this.index);
        }

        public void onNext(T t) {
            this.queue.offer(t);
            this.parent.drain();
        }

        public void onError(Throwable t) {
            this.error = t;
            this.done = true;
            this.parent.drain();
        }

        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }
    }
}
