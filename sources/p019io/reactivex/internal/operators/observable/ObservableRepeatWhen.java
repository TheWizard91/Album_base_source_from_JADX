package p019io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.Observable;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.util.AtomicThrowable;
import p019io.reactivex.internal.util.HalfSerializer;
import p019io.reactivex.subjects.PublishSubject;
import p019io.reactivex.subjects.Subject;

/* renamed from: io.reactivex.internal.operators.observable.ObservableRepeatWhen */
public final class ObservableRepeatWhen<T> extends AbstractObservableWithUpstream<T, T> {
    final Function<? super Observable<Object>, ? extends ObservableSource<?>> handler;

    public ObservableRepeatWhen(ObservableSource<T> source, Function<? super Observable<Object>, ? extends ObservableSource<?>> handler2) {
        super(source);
        this.handler = handler2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        Subject<Object> signaller = PublishSubject.create().toSerialized();
        try {
            ObservableSource<?> other = (ObservableSource) ObjectHelper.requireNonNull(this.handler.apply(signaller), "The handler returned a null ObservableSource");
            RepeatWhenObserver<T> parent = new RepeatWhenObserver<>(observer, signaller, this.source);
            observer.onSubscribe(parent);
            other.subscribe(parent.inner);
            parent.subscribeNext();
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            EmptyDisposable.error(ex, (Observer<?>) observer);
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableRepeatWhen$RepeatWhenObserver */
    static final class RepeatWhenObserver<T> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 802743776666017014L;
        volatile boolean active;
        final Observer<? super T> actual;

        /* renamed from: d */
        final AtomicReference<Disposable> f469d = new AtomicReference<>();
        final AtomicThrowable error = new AtomicThrowable();
        final RepeatWhenObserver<T>.InnerRepeatObserver inner = new InnerRepeatObserver();
        final Subject<Object> signaller;
        final ObservableSource<T> source;
        final AtomicInteger wip = new AtomicInteger();

        RepeatWhenObserver(Observer<? super T> actual2, Subject<Object> signaller2, ObservableSource<T> source2) {
            this.actual = actual2;
            this.signaller = signaller2;
            this.source = source2;
        }

        public void onSubscribe(Disposable d) {
            DisposableHelper.replace(this.f469d, d);
        }

        public void onNext(T t) {
            HalfSerializer.onNext(this.actual, t, (AtomicInteger) this, this.error);
        }

        public void onError(Throwable e) {
            DisposableHelper.dispose(this.inner);
            HalfSerializer.onError((Observer<?>) this.actual, e, (AtomicInteger) this, this.error);
        }

        public void onComplete() {
            this.active = false;
            this.signaller.onNext(0);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.f469d.get());
        }

        public void dispose() {
            DisposableHelper.dispose(this.f469d);
            DisposableHelper.dispose(this.inner);
        }

        /* access modifiers changed from: package-private */
        public void innerNext() {
            subscribeNext();
        }

        /* access modifiers changed from: package-private */
        public void innerError(Throwable ex) {
            DisposableHelper.dispose(this.f469d);
            HalfSerializer.onError((Observer<?>) this.actual, ex, (AtomicInteger) this, this.error);
        }

        /* access modifiers changed from: package-private */
        public void innerComplete() {
            DisposableHelper.dispose(this.f469d);
            HalfSerializer.onComplete((Observer<?>) this.actual, (AtomicInteger) this, this.error);
        }

        /* access modifiers changed from: package-private */
        public void subscribeNext() {
            if (this.wip.getAndIncrement() == 0) {
                while (!isDisposed()) {
                    if (!this.active) {
                        this.active = true;
                        this.source.subscribe(this);
                    }
                    if (this.wip.decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.observable.ObservableRepeatWhen$RepeatWhenObserver$InnerRepeatObserver */
        final class InnerRepeatObserver extends AtomicReference<Disposable> implements Observer<Object> {
            private static final long serialVersionUID = 3254781284376480842L;

            InnerRepeatObserver() {
            }

            public void onSubscribe(Disposable d) {
                DisposableHelper.setOnce(this, d);
            }

            public void onNext(Object t) {
                RepeatWhenObserver.this.innerNext();
            }

            public void onError(Throwable e) {
                RepeatWhenObserver.this.innerError(e);
            }

            public void onComplete() {
                RepeatWhenObserver.this.innerComplete();
            }
        }
    }
}
